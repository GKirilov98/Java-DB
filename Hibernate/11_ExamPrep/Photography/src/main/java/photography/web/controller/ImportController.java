package photography.web.controller;

import org.springframework.stereotype.Controller;
import photography.service.*;

@Controller
public class ImportController {
    private final LenService lenService;
    private final CameraService cameraService;
    private final PhotographerService photographerService;
    private final AccessoryService accessoryService;
    private final WorkshopService workshopService;

    public ImportController(LenService lenService, CameraService cameraService, PhotographerService photographerService, AccessoryService accessoryService, WorkshopService workshopService) {
        this.lenService = lenService;
        this.cameraService = cameraService;
        this.photographerService = photographerService;
        this.accessoryService = accessoryService;
        this.workshopService = workshopService;
    }

    void start() {
        seedLens();
        seedCameras();
        seedPhotographers();
        seedAccessories();
        seedWorkshops();
    }

    private void seedWorkshops() {
        this.workshopService.seedWorkshops();
    }

    private void seedAccessories() {
        this.accessoryService.seedAccessories();
    }

    private void seedPhotographers() {
        this.photographerService.seedPhotographers();
    }

    private void seedCameras() {
        this.cameraService.seedCameras();
    }

    private void seedLens() {
        this.lenService.seedLens();
    }
}
