package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDtos.ProjectDto;
import softuni.workshop.domain.dtos.importDtos.ProjectsRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final String READ_XML_PATH = ".\\src\\main\\resources\\files\\xmls\\projects.xml";

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validator;
    private final FileUtil fileUtil;

    public ProjectServiceImpl(ProjectRepository companyRepository, CompanyRepository companyRepository1, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validator, FileUtil fileUtil) {
        this.projectRepository = companyRepository;
        this.companyRepository = companyRepository1;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importProjects(){
        ProjectsRootDto projectsRootDto = this.xmlParser.objectFromFile(ProjectsRootDto.class, READ_XML_PATH);

        for (ProjectDto projectDto : projectsRootDto.getProjects()) {
            Project project = this.modelMapper.map(projectDto, Project.class);
            if (!this.validator.isValid(project)) {
                this.validator.violations(project)
                        .forEach(m -> System.out.println(m.getMessage()));
                continue;
            }

            Company company = this.companyRepository.findByName(projectDto.getCompany().getName());
            project.setCompany(company);
            this.projectRepository.saveAndFlush(project);
        }
    }


    @Override
    public boolean areImported() {

       return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() {

      return this.fileUtil.fileReader(READ_XML_PATH);
    }

    @Override
    public String exportFinishedProjects(){
        StringBuilder sb = new StringBuilder();
        List<Project> projects = this.projectRepository.findAllByFinishedIsTrue();
        for (Project project : projects) {
            sb.append(String.format("Project Name: %s", project.getName()))
                    .append(System.lineSeparator())
                    .append(String.format("\tDescription: %s", project.getDescription()))
                    .append(System.lineSeparator())
                    .append(String.format("\tPayment: %.2f", project.getPayment()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
