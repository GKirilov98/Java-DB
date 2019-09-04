package softuni.workshop.service;

public interface CompanyService {

    void importCompanies();

    boolean areImported();

    String readCompaniesXmlFile();
}
