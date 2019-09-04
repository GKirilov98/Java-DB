package softuni.shop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ProductShopController implements CommandLineRunner {

   private final ImportController importController;
   private final ExportController exportController;
    @Autowired
    public ProductShopController(ImportController importController, ExportController exportController) {
        this.importController = importController;
        this.exportController = exportController;
    }

    @Override
    public void run(String... args) throws Exception {
        importController.start();
        exportController.start();
    }
}
