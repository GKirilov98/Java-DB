package app.retake.services.impl;

import app.retake.domain.dto.animalAid.AnimalAidSeedDto;
import app.retake.domain.entity.AnimalAid;
import app.retake.repositories.AnimalAidRepository;
import app.retake.services.api.AnimalAidService;
import app.retake.util.FileUtil;
import app.retake.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AnimalAidServiceImpl implements AnimalAidService {
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;

    private final AnimalAidRepository animalAidRepository;

    public AnimalAidServiceImpl(ModelMapper modelMapper, FileUtil fileUtil, ValidatorUtil validatorUtil, Gson gson, AnimalAidRepository animalAidRepository) {
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.animalAidRepository = animalAidRepository;
    }

    @Override
    public void seedAnimalAid() {
        if (this.animalAidRepository.count()!= 0){
            return;
        }

        final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\json\\animal_aids.json";
        String content = this.fileUtil.fileReader(READ_FILE_PATH);

        AnimalAidSeedDto[] animalAidSeedDtos = this.gson.fromJson(content, AnimalAidSeedDto[].class);
        for (AnimalAidSeedDto seedDto : animalAidSeedDtos) {
            AnimalAid animalAid = this.animalAidRepository.findAnimalAidByName(seedDto.getName()).orElse(null);
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            } else if (animalAid != null){
                animalAid.setPrice(seedDto.getPrice());
                continue;
            }

            try {
                animalAid = this.modelMapper.map(seedDto, AnimalAid.class);
                this.animalAidRepository.saveAndFlush(animalAid);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
}
