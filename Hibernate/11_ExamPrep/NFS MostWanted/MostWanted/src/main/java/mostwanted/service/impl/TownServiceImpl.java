package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.town.TownSeedDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.service.TownService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;

    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
    }


    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() {
        final String READ_PATH = ".\\src\\main\\resources\\files\\towns.json";
        return this.fileUtil.fileReader(READ_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {

        TownSeedDto[] townSeedDtos = this.gson.fromJson(townsFileContent, TownSeedDto[].class);
        for (TownSeedDto townSeedDto : townSeedDtos) {
            if (!this.validatorUtil.isValid(townSeedDto)) {
                this.validatorUtil
                        .violations(townSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Town town = this.modelMapper.map(townSeedDto, Town.class);
            this.townRepository.saveAndFlush(town);
        }


        return "Success";
    }

    @Override
    public String exportRacingTowns() {

        Comparator comperator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                Integer x1 = ((Town) o1).getRacers().size();
                Integer x2 = ((Town) o2).getRacers().size();
                int sComp = x2.compareTo(x1);

                if (sComp != 0) {
                    return sComp;
                }

                String y1 = ((Town) o1).getName();
                String y2 = ((Town) o2).getName();
                return y1.compareTo(y2);
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };
        Set<String> result  = new LinkedHashSet<>();
                this.townRepository
                .findTownsByRacersIsNotNull()
                .stream()
                .sorted(comperator)
                .map(t -> String.format("Name: %s\nRacers: %d\n\n", ((Town) t).getName(),
                        ((Town) t).getRacers().size()))
                .forEach( e -> result.add((String) e));
        return String.join("", result).trim() ;

    }
}
