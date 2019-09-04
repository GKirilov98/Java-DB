package softuni.carshop.web.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.carshop.domain.dtos.carDto.CarSeedDto;
import softuni.carshop.domain.dtos.carDto.CarToyotaDto;
import softuni.carshop.domain.dtos.carDto.CarWithPartsDto;
import softuni.carshop.domain.dtos.customerDto.CustomerSeedDto;
import softuni.carshop.domain.dtos.customerDto.CustomerTotalSalesDto;
import softuni.carshop.domain.dtos.customerDto.CustomersOrderedDto;
import softuni.carshop.domain.dtos.partDto.PartSeedDto;
import softuni.carshop.domain.dtos.saleDto.CarAppliedDiscountDto;
import softuni.carshop.domain.dtos.supplierDto.SupplierLocalDto;
import softuni.carshop.domain.dtos.supplierDto.SupplierSeedDto;
import softuni.carshop.services.*;
import softuni.carshop.util.FileUtil;

import java.util.List;

@Controller
public class CarShopController implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public CarShopController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, FileUtil fileUtil, Gson gson) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        seedSupplier();
        seedPart();
        seedCar();
        seedCustomer();
        seedSale();

        //Queries ----------------------->
//        p01_OrderedCustomers();
//        p02_CarsFromMakeToyota();
//        p03_LocalSuppliers();
//        p04_CarsWithTheirParts();
//        p05_TotalSalesByCustomers();
//        p06_SalesWithAppliedDiscount();
    }

    private void p06_SalesWithAppliedDiscount() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\output\\sales-discounts.json";

        List<CarAppliedDiscountDto> carWithParts = this.saleService.getAllSalesWithAppliedDiscount();
        String content = this.gson.toJson(carWithParts);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));

    }

    private void p05_TotalSalesByCustomers() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\output\\customers-total-sales.json";

        List<CustomerTotalSalesDto> carWithParts = this.customerService.getAllCustmersTotalSales();
        String content = this.gson.toJson(carWithParts);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));

    }

    private void p04_CarsWithTheirParts() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\output\\cars-and-parts.json";

        List<CarWithPartsDto> carWithParts = this.carService.getAllCarWithParts();
        String content = this.gson.toJson(carWithParts);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));

    }

    private void p03_LocalSuppliers() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\output\\local-suppliers.json";

        List<SupplierLocalDto> allLocalSupplier = this.supplierService.getAllLocalSupplier();
        String content = this.gson.toJson(allLocalSupplier);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));

    }

    private void p02_CarsFromMakeToyota() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\output\\toyota-cars.json";

        List<CarToyotaDto> carsToyota = this.carService.getAllCarToyota();
        String content = this.gson.toJson(carsToyota);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));
    }

    private void p01_OrderedCustomers() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\output\\ordered-customers.json";

        List<CustomersOrderedDto> allCustmersOrdered = this.customerService.getAllCustmersOrdered();
        String content = this.gson.toJson(allCustmersOrdered);
        System.out.println(this.fileUtil.fileWriter(WRITE_FILE_PATH, content));
    }

    private void seedSale() {
        this.saleService.seedService();
    }

    private void seedCustomer() {
        final String CUSTOMER_JSON_FILE_PATH = ".\\src\\main\\resources\\input\\customers.json";
        String content = this.fileUtil.fileContent(CUSTOMER_JSON_FILE_PATH);
        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(content, CustomerSeedDto[].class);
        this.customerService.seedCustomer(customerSeedDtos);
    }

    private void seedCar() {
        final String CAR_JSON_FILE_PATH = ".\\src\\main\\resources\\input\\cars.json";
        String content = this.fileUtil.fileContent(CAR_JSON_FILE_PATH);
        CarSeedDto[] carSeedDtos = this.gson.fromJson(content, CarSeedDto[].class);
        this.carService.seedCar(carSeedDtos);
    }

    private void seedPart() {
        final String PART_JSON_FILE_PATH = ".\\src\\main\\resources\\input\\parts.json";
        String content = this.fileUtil.fileContent(PART_JSON_FILE_PATH);
        PartSeedDto[] partSeedDtros = this.gson.fromJson(content, PartSeedDto[].class);
        this.partService.seedPart(partSeedDtros);
    }

    private void seedSupplier() {
        final String SUPPLIER_READ_JSON_PATH = ".\\src\\main\\resources\\input\\suppliers.json";
        String content = this.fileUtil.fileContent(SUPPLIER_READ_JSON_PATH);
        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content, SupplierSeedDto[].class);
        this.supplierService.seedSupplier(supplierSeedDtos);
    }
}
