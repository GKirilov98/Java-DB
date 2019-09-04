package mostwanted.service.impl;

import mostwanted.domain.dtos.entry.EntryImportRaceDto;
import mostwanted.domain.dtos.race.RaceImportDto;
import mostwanted.domain.dtos.race.RaceImportRootDto;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.service.RaceService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidatorUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RaceServiceImpl implements RaceService {

    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;

    private final RaceRepository raceRepository;
    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;

    public RaceServiceImpl(ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser, FileUtil fileUtil, RaceRepository raceRepository, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.raceRepository = raceRepository;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
    }


    @Override
    public Boolean racesAreImported() {
        return raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() {
        final String READ_PATH = ".\\src\\main\\resources\\files\\races.xml";

        try {
            return Files.lines(Paths.get(READ_PATH)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String importRaces() {

        final String READ_PATH = ".\\src\\main\\resources\\files\\races.xml";
        RaceImportRootDto raceImportRootDto =
                this.xmlParser.objectFromFile(RaceImportRootDto.class, READ_PATH);

        for (RaceImportDto rid : raceImportRootDto.getRaces()) {
            Race race = new Race();
            race.setLaps(rid.getLaps());
            race.setDistrict(this.districtRepository.findDistinctFirstByName(rid.getDistrictName()));

            Set<RaceEntry> raceEntries = new LinkedHashSet<>();
            for (EntryImportRaceDto entry : rid.getEntries().getEntries()) {
                raceEntries.add(this.raceEntryRepository.findById(entry.getId()).orElse(null));
            }

            race.setEntries(raceEntries);
            this.raceRepository.saveAndFlush(race);

            for (RaceEntry entry : raceEntries) {
                entry.setRace(race);
                this.raceEntryRepository.save(entry);
            }

        }

        return this.raceEntryRepository.count() + "";
    }
}
