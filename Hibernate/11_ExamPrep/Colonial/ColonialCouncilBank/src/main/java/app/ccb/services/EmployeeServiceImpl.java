package app.ccb.services;

import app.ccb.domain.dtos.employee.EmployeeSeedDto;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public EmployeeServiceImpl(FileUtil fileUtil, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper, EmployeeRepository employeeRepository, BranchRepository branchRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public Boolean employeesAreImported() {

        return this.employeeRepository.count() != 0;
    }

    @Override
    public String readEmployeesJsonFile() {
        final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\json\\employees.json";
        return this.fileUtil.fileReader(READ_PATH_FILE);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder errors = new StringBuilder();

        EmployeeSeedDto[] EmployeeSeedDto = this.gson.fromJson(employees, EmployeeSeedDto[].class);
        for (EmployeeSeedDto seedDto : EmployeeSeedDto) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil
                        .violations(seedDto)
                        .forEach(v -> errors.append(v.getMessage()).append(System.lineSeparator()));

                continue;
            }

            Employee employee = this.modelMapper.map(seedDto, Employee.class);
            employee.setBranch(this.branchRepository.findBranchByName(seedDto.getBranchName()));
            employee.setFirstName(seedDto.getFullName().split(" ")[0]);
            employee.setLastName(seedDto.getFullName().split(" ")[1]);
            employee.setStartedOn(LocalDate.parse(seedDto.getStartedOn(), DateTimeFormatter.ofPattern("yyyy-M-dd")));

            this.employeeRepository.saveAndFlush(employee);
        }

        return errors.toString().trim();
    }

    @Override
    public String exportTopEmployees() {
        Comparator<? super Employee> comparator = (Comparator<Employee>) (o1, o2) -> {
            if (o2.getClients().size() - o1.getClients().size() == 0) {
                return o1.getId() - o2.getId();
            } else {
                return o2.getClients().size() - o1.getClients().size();
            }
        };
        return this.employeeRepository.findAll()
                .stream()
                .filter(e -> e.getClients().size() > 0)
                .sorted(comparator)
                .map(e -> String.format("Name: %s %s\nClients count: %d\n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getClients().size()))
                .collect(Collectors.joining("") );

    }
}
