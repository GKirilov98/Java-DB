package softuni.carshop.web.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import softuni.carshop.domain.dtos.JSON.carDto.CarSeedElemetDto;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomerSeedDto;
import softuni.carshop.domain.dtos.JSON.partDto.PartSeedDto;
import softuni.carshop.domain.dtos.JSON.supplierDto.SupplierSeedDto;
import softuni.carshop.domain.dtos.XML.importDto.CarImportRootDto;
import softuni.carshop.domain.dtos.XML.importDto.CustomerImportRootDto;
import softuni.carshop.domain.dtos.XML.importDto.PartImportRootDto;
import softuni.carshop.domain.dtos.XML.importDto.SupplierImportRootDto;
import softuni.carshop.services.*;
import softuni.carshop.util.FileUtil;
import softuni.carshop.util.XmlParser;

@Controller
public class ImportController {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public ImportController(XmlParser xmlParser, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, FileUtil fileUtil, Gson gson) {
        this.xmlParser = xmlParser;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    void start(){
       // seedFromJsonFiles();
        importFromXmlFiles();
    }

    private void importFromXmlFiles() {
        importSuppliersXml();
        importPartsXml();
        importCarsXml();
        importCustomersXml();
        seedSale();


    }

    private void importCustomersXml() {
        final String READ_XML_PATH = ".\\src\\main\\resources\\XML\\input\\customers.xml";
        CustomerImportRootDto customerImportRootDto = this.xmlParser
                .objectFromFile(CustomerImportRootDto.class, READ_XML_PATH);
        this.customerService.importCustomersXML(customerImportRootDto);
    }

    private void importCarsXml() {
        final String READ_XML_PATH = ".\\src\\main\\resources\\XML\\input\\cars.xml";
        CarImportRootDto carImportRootDto = this.xmlParser
                .objectFromFile(CarImportRootDto.class, READ_XML_PATH);
        this.carService.importCarsXML(carImportRootDto);
    }

    private void importPartsXml() {
        final String READ_XML_PATH = ".\\src\\main\\resources\\XML\\input\\parts.xml";
        PartImportRootDto partImportRootDto = this.xmlParser
                .objectFromFile(PartImportRootDto.class, READ_XML_PATH);
        this.partService.importPartsXML(partImportRootDto);
    }

    private void importSuppliersXml(){
        final String READ_XML_PATH = ".\\src\\main\\resources\\XML\\input\\suppliers.xml";
        SupplierImportRootDto supplierImportRootDto = this.xmlParser
                .objectFromFile(SupplierImportRootDto.class, READ_XML_PATH);
        this.supplierService.importSuppliersXML(supplierImportRootDto);
    }

    private void seedFromJsonFiles() {
        seedSupplierJSON();
        seedPartJSON();
        seedCarJSON();
        seedCustomerJSON();
        seedSale();
    }

    private void seedSale() {
        this.saleService.seedService();
    }

    private void seedCustomerJSON() {
        final String CUSTOMER_JSON_FILE_PATH = ".\\src\\main\\resources\\JSON\\input\\customers.json";
        String content = this.fileUtil.fileContent(CUSTOMER_JSON_FILE_PATH);
        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(content, CustomerSeedDto[].class);
        this.customerService.seedCustomer(customerSeedDtos);
    }

    private void seedCarJSON() {
        final String CAR_JSON_FILE_PATH = ".\\src\\main\\resources" +
                "\\JSON\\input\\cars.json";
        String content = this.fileUtil.fileContent(CAR_JSON_FILE_PATH);
        CarSeedElemetDto[] carSeedDtos = this.gson.fromJson(content, CarSeedElemetDto[].class);
        this.carService.seedCar(carSeedDtos);
    }

    private void seedPartJSON() {
        final String PART_JSON_FILE_PATH = ".\\src\\main\\resources\\JSON\\input\\parts.json";
        String content = this.fileUtil.fileContent(PART_JSON_FILE_PATH);
        PartSeedDto[] partSeedDtros = this.gson.fromJson(content, PartSeedDto[].class);
        this.partService.seedPart(partSeedDtros);
    }

    private void seedSupplierJSON() {
        final String SUPPLIER_READ_JSON_PATH = ".\\src\\main\\resources\\JSON\\input\\suppliers.json";
        String content = this.fileUtil.fileContent(SUPPLIER_READ_JSON_PATH);
        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content, SupplierSeedDto[].class);
        this.supplierService.seedSupplierJSON(supplierSeedDtos);
    }

}
