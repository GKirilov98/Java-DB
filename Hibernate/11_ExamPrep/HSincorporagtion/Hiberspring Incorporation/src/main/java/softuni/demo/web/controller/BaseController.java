package softuni.demo.web.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController implements CommandLineRunner {
    private final ImportController importController;
    private final ExportController exportController;

    public BaseController(ImportController importControler, ExportController exportController) {
        this.importController = importControler;
        this.exportController = exportController;
    }

    @Override
    public void run(String... args) throws Exception {
        importController.start();
        exportController.start();
    }
}
