package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDtos.CompaniesRootDto;
import softuni.workshop.domain.dtos.importDtos.CompanyDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;


@Service
public class CompanyServiceImpl implements CompanyService {
    private final String READ_XML_PATH = ".\\src\\main\\resources\\files\\xmls\\companies.xml";

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validator;
    private final FileUtil fileUtil;


    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validator, FileUtil fileUtil) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.fileUtil = fileUtil;
    }


    @Override
    public void importCompanies() {


        if (this.companyRepository.count() > 0){ return;}
        CompaniesRootDto companyImportRootDto = this.xmlParser.objectFromFile(CompaniesRootDto.class, READ_XML_PATH);

        for (CompanyDto companyDto : companyImportRootDto.getCompanies()) {
            Company company = this.modelMapper.map(companyDto, Company.class);
            if (!this.validator.isValid(company)) {
                this.validator.violations(company)
                        .forEach(m -> System.out.println(m.getMessage()));
                continue;
            }


            this.companyRepository.saveAndFlush(company);
        }
    }

    @Override
    public boolean areImported() {

        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() {
        return  this.fileUtil.fileReader(READ_XML_PATH);
    }
}
