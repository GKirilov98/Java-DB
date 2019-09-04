package app.retake.web.controllers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController implements CommandLineRunner {
        private final ImportController importController;

    public BaseController(ImportController importController) {
        this.importController = importController;
    }

    @Override
    public void run(String... args) throws Exception {
        importController.start();
    }
}
