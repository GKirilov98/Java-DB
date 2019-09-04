package weddingplaner.web.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController implements CommandLineRunner {
    private final ExportController exportController;
    private final ImportController importController;

    public BaseController(ExportController exportController, ImportController importController) {
        this.exportController = exportController;
        this.importController = importController;
    }

    @Override
    public void run(String... args) throws Exception {
        importController.start();
    }
}
