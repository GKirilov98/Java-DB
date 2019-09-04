package photography.web.controller;

import org.springframework.stereotype.Controller;
import photography.service.PhotographerService;

@Controller
public class ExportController {
    private final PhotographerService photographerService;

    public ExportController(PhotographerService photographerService) {
        this.photographerService = photographerService;
    }

    void start() {
       // readPhotographerJSON();
        exportLensesPhotoJSON();
    }

    private void exportLensesPhotoJSON() {
        this.photographerService.exportLensesPhotographers();
    }

    private void readPhotographerJSON() {
        this.photographerService.exportPhotographersOrdered();
    }
}
