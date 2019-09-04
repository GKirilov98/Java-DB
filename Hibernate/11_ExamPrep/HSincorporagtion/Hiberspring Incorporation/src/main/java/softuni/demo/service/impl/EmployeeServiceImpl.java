package softuni.demo.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.demo.domain.dtos.employee.EmployeeProductiveDto;
import softuni.demo.domain.dtos.employee.EmployeeSeedDto;
import softuni.demo.domain.dtos.employee.EmployeeSeedRootDto;
import softuni.demo.domain.entities.Branch;
import softuni.demo.domain.entities.Card;
import softuni.demo.domain.entities.Employee;
import softuni.demo.repository.BranchRepository;
import softuni.demo.repository.CardRepository;
import softuni.demo.repository.EmployeeRepository;
import softuni.demo.service.EmployeeService;
import softuni.demo.util.FileUtil;
import softuni.demo.util.ValidatorUtil;
import softuni.demo.util.XmlParser;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;
    private final FileUtil fileUtil;

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final CardRepository cardRepository;


    public EmployeeServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson, FileUtil fileUtil, EmployeeRepository employeeRepository, BranchRepository branchRepository, CardRepository cardRepository) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.cardRepository = cardRepository;
    }


    @Override
    public void seedEmployee() {
        if (this.employeeRepository.count() != 0) {
            return;
        }

        final String READ_FILE_PATH = ".\\src\\main\\resources\\xml\\input\\employees.xml";
       EmployeeSeedRootDto employeeSeedRootDto = this.xmlParser.objectFromFile(EmployeeSeedRootDto.class, READ_FILE_PATH);
        for (EmployeeSeedDto seedDto : employeeSeedRootDto.getEmployeeSeedDtos()) {
            Branch branch = this.branchRepository.findBranchByName(seedDto.getBranch());
            Card card = this.cardRepository.findCardByNumber(seedDto.getCard());
            if (!this.validatorUtil.isValid(seedDto) || branch == null || card == null) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            try {
                Employee employee = this.modelMapper.map(seedDto, Employee.class);
                employee.setCard(card);
                employee.setBranch(branch);
                this.employeeRepository.saveAndFlush(employee);
                branch.getEmployees().add(employee);
                this.branchRepository.saveAndFlush(branch);
                card.setEmployee(employee);
                this.cardRepository.saveAndFlush(card);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void exportProductiveEmployees() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\json\\output\\productive-employees.json";

        List<Employee> employees = this.employeeRepository.findAll()
                .stream()
                .filter(e -> e.getBranch().getProducts().size() > 0)
                .collect(Collectors.toList());

        List<EmployeeProductiveDto> employeeProductiveDtos = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeProductiveDto epd = this.modelMapper.map(employee, EmployeeProductiveDto.class);
            epd.setFullName(employee.getFirstName() + " " + employee.getLastName());
            epd.setNumber(employee.getCard().getNumber());
            employeeProductiveDtos.add(epd);
        }

        String jsonFormat = this.gson.toJson(employeeProductiveDtos);
        this.fileUtil.fileWriter(WRITE_FILE_PATH, jsonFormat);
    }
}
