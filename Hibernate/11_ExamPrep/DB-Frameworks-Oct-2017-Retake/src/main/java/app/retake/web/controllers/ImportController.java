package app.retake.web.controllers;

import app.retake.services.api.AnimalAidService;
import app.retake.services.api.AnimalService;
import app.retake.services.api.ProcedureService;
import app.retake.services.api.VetService;
import org.springframework.stereotype.Controller;

@Controller
public class ImportController {
    private final AnimalAidService animalAidService;
    private final AnimalService animalService;
    private final VetService vetService;
    private final ProcedureService procedureService;

    public ImportController(AnimalAidService animalAidService, AnimalService animalService, VetService vetService, ProcedureService procedureService) {
        this.animalAidService = animalAidService;
        this.animalService = animalService;
        this.vetService = vetService;
        this.procedureService = procedureService;
    }

     void start() {
        animalAidService.seedAnimalAid();
        animalService.seedAnimals();
        vetService.seedVet();
        procedureService.seedProcedures();
    }
}
