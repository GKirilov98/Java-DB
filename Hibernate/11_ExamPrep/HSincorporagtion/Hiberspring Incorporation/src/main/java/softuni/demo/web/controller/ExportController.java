package softuni.demo.web.controller;

import org.springframework.stereotype.Controller;
import softuni.demo.service.CardService;
import softuni.demo.service.EmployeeService;

@Controller
public class ExportController {
    private final CardService cardService;
    private final EmployeeService employeeService;

    public ExportController(CardService cardService, EmployeeService employeeService) {
        this.cardService = cardService;
        this.employeeService = employeeService;
    }

    void start() {
        freeCard();
        productiveEmployee();
    }

    private void productiveEmployee() {
        this.employeeService.exportProductiveEmployees();
    }

    private void freeCard() {
        this.cardService.exportAllFreeCards();
    }
}
