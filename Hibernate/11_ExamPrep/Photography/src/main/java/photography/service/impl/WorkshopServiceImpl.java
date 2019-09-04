package photography.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import photography.domain.dto.workshop.ParticipantReadDto;
import photography.domain.dto.workshop.WorkshopSeedDto;
import photography.domain.dto.workshop.WorkshopsSeedRootDto;
import photography.domain.entity.Photographer;
import photography.domain.entity.Workshop;
import photography.repository.PhotographerRepository;
import photography.repository.WorkshopRepository;
import photography.service.WorkshopService;
import photography.util.ValidatorUtil;
import photography.util.XmlParser;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class WorkshopServiceImpl implements WorkshopService {
    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\input\\workshops.xml";


    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    private final WorkshopRepository workshopRepository;
    private final PhotographerRepository photographerRepository;

    public WorkshopServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, WorkshopRepository workshopRepository, PhotographerRepository photographerRepository) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.workshopRepository = workshopRepository;
        this.photographerRepository = photographerRepository;
    }

    @Override
    public void seedWorkshops() {
        if (this.workshopRepository.count() != 0){
            return;
        }

        WorkshopsSeedRootDto workshopsSeedRootDto = this.xmlParser.objectFromFile(WorkshopsSeedRootDto.class, READ_FILE_PATH);
        for (WorkshopSeedDto seedDto : workshopsSeedRootDto.getWorkshop()) {
            if (!validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Workshop workshop = this.modelMapper.map(seedDto, Workshop.class);
            if (seedDto.getStartDate() != null){
                workshop.setStartDate(LocalDateTime.parse(seedDto.getStartDate().replace('T', ' '),
                        DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss")));
            }

            if (seedDto.getEndDate() != null){
                workshop.setEndDate(LocalDateTime.parse(seedDto.getEndDate().replace('T', ' '),
                        DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss")));
            }

            String[] tokens = seedDto.getTrainer().split(" ");
            workshop.setTrainer(this.photographerRepository
                    .findPhotographerByFirstNameAndLastName(tokens[0], tokens[1]));

            if (seedDto.getParticipants() != null &&
                    seedDto.getParticipants().getParticipantReadDtos() != null){
                for (ParticipantReadDto prd : seedDto.getParticipants().getParticipantReadDtos()) {
                    workshop.getParticipants().add(this.photographerRepository.findPhotographerByFirstNameAndLastName(
                            prd.getFirstName(), prd.getLastName()
                    ));
                }
            }


            this.workshopRepository.saveAndFlush(workshop);
            Photographer photographer = workshop.getTrainer();
            photographer.getWorkshopsTrainer().add(workshop);
            this.photographerRepository.saveAndFlush(photographer);
            for (Photographer participant : workshop.getParticipants()) {
                participant.getWorkshopsParticipant().add(workshop);
                this.photographerRepository.saveAndFlush(participant);
            }
        }
    }
}
