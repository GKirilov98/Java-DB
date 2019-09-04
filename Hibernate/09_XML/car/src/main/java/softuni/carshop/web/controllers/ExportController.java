package softuni.carshop.web.controllers;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import softuni.carshop.domain.dtos.JSON.carDto.CarToyotaDto;
import softuni.carshop.domain.dtos.JSON.carDto.CarWithPartsDto;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomerTotalSalesDto;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomersOrderedDto;
import softuni.carshop.domain.dtos.JSON.saleDto.CarAppliedDiscountDto;
import softuni.carshop.domain.dtos.JSON.supplierDto.SupplierLocalDto;
import softuni.carshop.domain.dtos.XML.exportDto.carDto.CarToyotaExportRootDto;
import softuni.carshop.domain.dtos.XML.exportDto.customerDto.CustomerExportRootDto;
import softuni.carshop.domain.dtos.XML.exportDto.carDto.CarExportRootDto;
import softuni.carshop.domain.dtos.XML.exportDto.customerDto.CustomerTotalSaleExportRootDto;
import softuni.carshop.domain.dtos.XML.exportDto.saleDto.SaleExportRootDto;
import softuni.carshop.domain.dtos.XML.exportDto.suppliersDto.SupplierExportRootDto;
import softuni.carshop.services.*;
import softuni.carshop.util.FileUtil;
import softuni.carshop.util.XmlParser;

import java.util.List;

@Controller
public class ExportController  {

    private final SupplierService supplierService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final Gson gson;

    public ExportController(SupplierService supplierService, CarService carService, CustomerService customerService, SaleService saleService, XmlParser xmlParser, FileUtil fileUtil, Gson gson) {
        this.supplierService = supplierService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    void start() {
      //  jsonTasks();
        xmlTasks();
    }

    private void xmlTasks() {
        p01_OrderedCustomersXML();
        p02_CarsFromMakeToyotaXML();
        p03_LocalSuppliersXML();
        p04_CarsWithTheirPartsXML();
        p05_TotalSalesByCustomersXML();
        p06_SalesWithAppliedDiscountXML();
    }

    private void p06_SalesWithAppliedDiscountXML() {
        final String XML_WRITER_PATH = ".\\src\\main\\resources\\XML\\output\\sales-discounts.xml";
        SaleExportRootDto saleExportRootDto = this.saleService.xmlExportSalesDiscount();
        this.xmlParser.objectToFile(saleExportRootDto, XML_WRITER_PATH);
    }

    private void p05_TotalSalesByCustomersXML() {
        final String XML_WRITER_PATH = ".\\src\\main\\resources\\XML\\output\\customers-total-sales.xml";
        CustomerTotalSaleExportRootDto customerTotalSaleExportRootDto = this.customerService.xmlExportCustomerTotalSales();
        this.xmlParser.objectToFile(customerTotalSaleExportRootDto, XML_WRITER_PATH);
    }

    private void p04_CarsWithTheirPartsXML() {
        final String XML_WRITER_PATH = ".\\src\\main\\resources\\XML\\output\\cars-and-parts.xml";
        CarExportRootDto carExportRootDto = this.carService.exportCars();
        this.xmlParser.objectToFile(carExportRootDto, XML_WRITER_PATH);
    }

    private void p03_LocalSuppliersXML() {
        final String XML_WRITER_PATH = ".\\src\\main\\resources\\XML\\output\\local-suppliers.xml";
        SupplierExportRootDto supplierExportRootDto = this.supplierService.xmlExportLocalSuppliers();
        this.xmlParser.objectToFile(supplierExportRootDto, XML_WRITER_PATH);
    }

    private void p02_CarsFromMakeToyotaXML() {
        final String XML_WRITER_PATH = ".\\src\\main\\resources\\XML\\output\\toyota-cars.xml";
        CarToyotaExportRootDto carToyotaExportRootDto = this.carService.exportCarsToyota();
        this.xmlParser.objectToFile(carToyotaExportRootDto, XML_WRITER_PATH);
    }

    private void p01_OrderedCustomersXML() {
        final String XML_WRITER_PATH = ".\\src\\main\\resources\\XML\\output\\ordered-customers.xml";
        CustomerExportRootDto customerExportRootDto = this.customerService.xmlExecuteOrderedCustomers();
        this.xmlParser.objectToFile(customerExportRootDto, XML_WRITER_PATH);
    }

    private void jsonTasks() {
        p01_OrderedCustomersJSON();
        p02_CarsFromMakeToyotaJSON();
        p03_LocalSuppliersJSON();
        p04_CarsWithTheirPartsJSON();
        p05_TotalSalesByCustomersJSON();
        p06_SalesWithAppliedDiscountJSON();
    }

    private void p06_SalesWithAppliedDiscountJSON() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\JSON\\output\\sales-discounts.json";

        List<CarAppliedDiscountDto> carWithParts = this.saleService.getAllSalesWithAppliedDiscount();
        String content = this.gson.toJson(carWithParts);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));

    }

    private void p05_TotalSalesByCustomersJSON() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\JSON\\output\\customers-total-sales.json";

        List<CustomerTotalSalesDto> carWithParts = this.customerService.getAllCustmersTotalSales();
        String content = this.gson.toJson(carWithParts);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));

    }

    private void p04_CarsWithTheirPartsJSON() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\JSON\\output\\cars-and-parts.json";

        List<CarWithPartsDto> carWithParts = this.carService.getAllCarWithParts();
        String content = this.gson.toJson(carWithParts);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));

    }

    private void p03_LocalSuppliersJSON() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\JSON\\output\\local-suppliers.json";

        List<SupplierLocalDto> allLocalSupplier = this.supplierService.getAllLocalSupplier();
        String content = this.gson.toJson(allLocalSupplier);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));

    }

    private void p02_CarsFromMakeToyotaJSON() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\JSON\\output\\toyota-cars.json";

        List<CarToyotaDto> carsToyota = this.carService.getAllCarToyota();
        String content = this.gson.toJson(carsToyota);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));
    }

    private void p01_OrderedCustomersJSON() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\JSON\\output\\ordered-customers.json";

        List<CustomersOrderedDto> allCustmersOrdered = this.customerService.getAllCustmersOrdered();
        String content = this.gson.toJson(allCustmersOrdered);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));
    }
}
