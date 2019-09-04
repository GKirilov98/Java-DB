package mostwanted.service.impl;

import mostwanted.domain.dtos.entry.RaceEntryImortDto;
import mostwanted.domain.dtos.entry.RaceEntryImportRootDto;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.service.RaceEntryService;
import mostwanted.util.ValidatorUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
@Transactional
public class RaceEntryServiceImpl implements RaceEntryService {

    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;

    private final RaceEntryRepository raceEntryRepository;
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;

    public RaceEntryServiceImpl(ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser, RaceEntryRepository raceEntryRepository, CarRepository carRepository, RacerRepository racerRepository) {
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.raceEntryRepository = raceEntryRepository;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() {
        final String READ_PATH = ".\\src\\main\\resources\\files\\race-entries.xml";
        try {
            return Files.lines(Paths.get(READ_PATH)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String importRaceEntries() {
        final String READ_PATH = ".\\src\\main\\resources\\files\\race-entries.xml";
        RaceEntryImportRootDto raceEntryImportRootDto = 
                this.xmlParser.objectFromFile(RaceEntryImportRootDto.class, READ_PATH);
        for (RaceEntryImortDto reid : raceEntryImportRootDto.getRaceEntryImortDtos()) {
            RaceEntry raceEntry = new RaceEntry();
            raceEntry.setHasFinished(reid.getHasFinished());
            raceEntry.setFinishTime(reid.getFinishTime());
            raceEntry.setCar(this.carRepository.findCarById(reid.getCarId()));
            raceEntry.setRacer(this.racerRepository.findRacerByName(reid.getRacer()));

            this.raceEntryRepository.saveAndFlush(raceEntry);
        }

        return this.raceEntryRepository.count() + "";
    }
}

