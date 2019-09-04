package softuni.demo.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.demo.domain.dtos.town.TownSeedDto;
import softuni.demo.domain.entities.Town;
import softuni.demo.repository.TownRepository;
import softuni.demo.service.TownService;
import softuni.demo.util.FileUtil;
import softuni.demo.util.ValidatorUtil;

@Service
public class TownServiceImpl implements TownService {

    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;

    private final TownRepository townRepository;

    @Autowired
    public TownServiceImpl(FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson, TownRepository townRepository) {
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.townRepository = townRepository;
    }

    @Override
    public void seedTown() {
        if (townRepository.count() != 0) {
            return;
        }

        String READ_FILE_PATH = ".\\src\\main\\resources\\json\\input\\towns.json";
        String content = this.fileUtil.fileReader(READ_FILE_PATH);
        TownSeedDto[] townSeedDtos = this.gson.fromJson(content, TownSeedDto[].class);
        for (TownSeedDto townSeedDto : townSeedDtos) {
            if (!this.validatorUtil.isValid(townSeedDto)) {
                this.validatorUtil.violations(townSeedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            } else if (this.townRepository.findTownByName(townSeedDto.getName()) != null) {
                continue;
            }

            Town town = this.modelMapper.map(townSeedDto, Town.class);
            this.townRepository.saveAndFlush(town);
        }
    }
}
