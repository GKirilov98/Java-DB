package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDtos.EmployeeDto;
import softuni.workshop.domain.dtos.importDtos.EmployeeRootDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final String READ_XML_PATH = ".\\src\\main\\resources\\files\\xmls\\employees.xml";
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidatorUtil validator;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, FileUtil fileUtil, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validator = validatorUtil;
    }


    @Override
    public void importEmployees() {
        EmployeeRootDto employeeRootDto = this.xmlParser.objectFromFile(EmployeeRootDto.class, READ_XML_PATH);

        for (EmployeeDto employeeDto : employeeRootDto.getEmployeeDtos()) {
            Employee employee = this.modelMapper.map(employeeDto, Employee.class);
            if (!this.validator.isValid(employee)) {
                this.validator.violations(employee)
                        .forEach(m -> System.out.println(m.getMessage()));
                continue;
            }

            Project project = this.projectRepository.findProjectByName(employeeDto.getProject().getName());
            employee.setProject(project);

            this.employeeRepository.saveAndFlush(employee);
        }
    }


    @Override
    public boolean areImported() {

       return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() {
        return this.fileUtil.fileReader(READ_XML_PATH);
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        StringBuilder sb = new StringBuilder();
        List<Employee> employees = this.employeeRepository.findAllByAgeGreaterThan(25);
        for (Employee employee : employees) {
            sb.append(String.format("Name: %s %s", employee.getFirstName(), employee.getLastName()))
                    .append(System.lineSeparator())
                    .append(String.format("\tAge: %d", employee.getAge()))
                    .append(System.lineSeparator())
                    .append(String.format("\tProject name: %s", employee.getProject().getName()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
