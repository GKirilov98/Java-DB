package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.racer.RacerSeedDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.service.RacerService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RacerServiceImpl implements RacerService {
    private final RacerRepository racerRepository;
    private final TownRepository townRepository;


    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public Boolean racersAreImported() {
        return racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() {
        final String READ_PATH = ".\\src\\main\\resources\\files\\racers.json";
        return this.fileUtil.fileReader(READ_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        RacerSeedDto[] racerSeedDtos = this.gson.fromJson(racersFileContent, RacerSeedDto[].class);
        for (RacerSeedDto racerSeedDto : racerSeedDtos) {
            if (!this.validatorUtil.isValid(racerSeedDto)) {
                this.validatorUtil
                        .violations(racerSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Racer racer = this.modelMapper.map(racerSeedDto, Racer.class);
            racer.setHomeTown(this.townRepository.findTownByName(racerSeedDto.getHomeTown()));
            this.racerRepository.saveAndFlush(racer);
        }

        return "Success";
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public String exportRacingCars() {
        Comparator comperator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                Integer x1 = ((Racer) o1).getCars().size();
                Integer x2 = ((Racer) o2).getCars().size();
                int sComp = x2.compareTo(x1);

                if (sComp != 0) {
                    return sComp;
                }

                String y1 = ((Racer) o1).getName();
                String y2 = ((Racer) o2).getName();
                return y1.compareTo(y2);
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };

        Set<String> result =  new HashSet<>();
                this.racerRepository
                .findRacersByCarsIsNotNull()
                .stream()
                .sorted(comperator)
                .map(r ->{
                    if (((Racer) r).getAge() != null){
                      return   String.format("Name: %s\nAge: %d\nCars:\n%s\n",
                        ((Racer) r).getName(),
                        ((Racer) r).getAge(),
                        String.join("",
                                ((Racer) r).getCars()
                                .stream()
                                .map(c -> String.format("%s %s %d\n",
                                        c.getBrand(),
                                        c.getModel(),
                                        c.getYearOfProduction()))
                                .collect(Collectors.toList())
                        ));
                    } else {
                      return   String.format("Name: %s\nCars:\n%s\n",
                                ((Racer) r).getName(),
                                String.join("",
                                        ((Racer) r).getCars()
                                                .stream()
                                                .map(c -> String.format("%s %s %d\n",
                                                        c.getBrand(),
                                                        c.getModel(),
                                                        c.getYearOfProduction()))
                                                .collect(Collectors.toList())
                                ));
                    }

                })
                .forEach( r -> result.add((String) r));

        return String.join("", result).trim();
    }
}
