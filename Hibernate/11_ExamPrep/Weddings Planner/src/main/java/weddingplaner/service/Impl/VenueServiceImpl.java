package weddingplaner.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import weddingplaner.domain.dto.venue.VenueSeedDto;
import weddingplaner.domain.dto.venue.VenueSeedRootDto;
import weddingplaner.domain.entity.Venue;
import weddingplaner.domain.entity.Wedding;
import weddingplaner.repository.VenueRepository;
import weddingplaner.repository.WeddingRepository;
import weddingplaner.service.VenueService;
import weddingplaner.util.ValidatorUtil;
import weddingplaner.util.XmlParser;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class VenueServiceImpl  implements VenueService {
    private String READ_FILE_PATH = ".\\src\\main\\resources\\files\\xml\\input\\venues.xml";

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    private final VenueRepository venueRepository;
    private final WeddingRepository weddingRepository;

    public VenueServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, XmlParser xmlParser, VenueRepository venueRepository, WeddingRepository weddingRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.venueRepository = venueRepository;
        this.weddingRepository = weddingRepository;
    }

    @Override
    public boolean seedVenue() {
        if (venueRepository.count() != 0){
            return false;
        }

        VenueSeedRootDto venueSeedRootDtos = this.xmlParser.objectFromFile(VenueSeedRootDto.class, READ_FILE_PATH);
        for (VenueSeedDto seedDto : venueSeedRootDtos.getVenues()) {
            if (!this.validatorUtil.isValid(seedDto)){
                this.validatorUtil.violations(seedDto)
                        .forEach( e -> System.out.println(e.getMessage()));
                continue;
            }

           try {
               Venue venue = this.modelMapper.map(seedDto, Venue.class);
               this.venueRepository.saveAndFlush(venue);
           } catch (Exception e){
               System.out.println(e.getMessage());
           }
        }

        return true;
    }

    public void addRandomVenueToWedding() {

        for (Wedding wedding : this.weddingRepository.findAll()) {
            for (int i = 0; i < 2; i++) {
                Venue venue = getRandomVenue();
                wedding.getVenues().add(venue);
                venue.getWeddings().add(wedding);
                this.venueRepository.saveAndFlush(venue);
                this.weddingRepository.saveAndFlush(wedding);
            }
        }
    }

    private Venue getRandomVenue() {
        Random random = new Random();
        int randomId = random.nextInt((int) (this.venueRepository.count() - 1)) + 1;
        return this.venueRepository.findById(randomId).orElse(null);
    }
}
