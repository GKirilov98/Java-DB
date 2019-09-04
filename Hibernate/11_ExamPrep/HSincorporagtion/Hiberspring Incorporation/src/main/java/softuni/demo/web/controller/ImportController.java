package softuni.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import softuni.demo.service.*;

@Controller
public class ImportController {

    private final TownService townService;
    private final BranchService branchService;
    private final CardService cardService;
    private final ProductService productService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportController(TownService townService, BranchService branchService, CardService cardService, ProductService productService, EmployeeService employeeService) {
        this.townService = townService;
        this.branchService = branchService;
        this.cardService = cardService;
        this.productService = productService;
        this.employeeService = employeeService;
    }

    void start(){
        seedTown();
        seedBranch();
        seedCard();
        seedProduct();
        seedEmployee();
    }

    private void seedEmployee() {
        this.employeeService.seedEmployee();
    }

    private void seedProduct() {
        this.productService.seedProduct();
    }

    private void seedCard() {
        this.cardService.seedEmployeeCard();
    }

    private void seedBranch() {
        this.branchService.seedBranch();
    }

    private void seedTown() {
        townService.seedTown();
    }
}
