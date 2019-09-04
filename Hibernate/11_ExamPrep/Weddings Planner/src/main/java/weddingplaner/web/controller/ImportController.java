package weddingplaner.web.controller;

import org.springframework.stereotype.Controller;
import weddingplaner.service.AgencyService;
import weddingplaner.service.PersonService;
import weddingplaner.service.VenueService;
import weddingplaner.service.WeddingService;

@Controller
public class ImportController {

    private final AgencyService agencyService;
    private final PersonService personService;
    private final WeddingService weddingService;
    private final VenueService venueService;

    public ImportController(AgencyService agencyService, PersonService personService, WeddingService weddingService, VenueService venueService) {
        this.agencyService = agencyService;
        this.personService = personService;
        this.weddingService = weddingService;
        this.venueService = venueService;
    }

    void start(){
        seedAgency();
        seedPerson();
        seedWedding();
        seedVenue();
    }

    private void seedVenue() {
      boolean seededVenue =  this.venueService.seedVenue();
        if (seededVenue){
            this.venueService.addRandomVenueToWedding();
        }
    }

    private void seedWedding() {
        this.weddingService.seedWedding();
    }

    private void seedPerson() {
        this.personService.seedPerson();
    }

    private void seedAgency() {
        this.agencyService.seedAgency();
    }
}
