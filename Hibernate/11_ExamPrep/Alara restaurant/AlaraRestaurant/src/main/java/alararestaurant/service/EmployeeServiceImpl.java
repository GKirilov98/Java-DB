package alararestaurant.service;

import alararestaurant.domain.dtos.employee.EmployeeSeedDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    @Autowired
    public EmployeeServiceImpl(FileUtil fileUtil, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper, EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }


    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() {
        final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\employees.json";
        return this.fileUtil.fileReader(READ_PATH_FILE);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder errors = new StringBuilder();

        EmployeeSeedDto[] employeeSeedDtos = this.gson.fromJson(employees, EmployeeSeedDto[].class);
        for (EmployeeSeedDto seedDto : employeeSeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil
                        .violations(seedDto)
                        .forEach(v -> errors.append(v.getMessage()).append(System.lineSeparator()));

                continue;
            }

            Position position = this.positionRepository.findPositionByName(seedDto.getPosition()).orElse(null);
            if (position == null){
                position = new Position();
                position.setName(seedDto.getPosition());
                position.setEmployees(new ArrayList<>());
            }

            Employee employee = this.modelMapper.map(seedDto, Employee.class);
            employee.setPosition(position);
            position.getEmployees().add(employee);

            this.positionRepository.saveAndFlush(position);
            this.employeeRepository.saveAndFlush(employee);
        }

        return errors.toString().trim();
    }
}
