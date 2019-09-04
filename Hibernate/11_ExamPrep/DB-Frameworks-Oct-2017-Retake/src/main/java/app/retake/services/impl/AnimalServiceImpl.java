package app.retake.services.impl;


import app.retake.domain.dto.animal.AnimalSeedDto;
import app.retake.domain.entity.Animal;
import app.retake.domain.entity.Passport;
import app.retake.repositories.AnimalRepository;
import app.retake.repositories.PassportRepository;
import app.retake.services.api.AnimalService;
import app.retake.util.FileUtil;
import app.retake.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AnimalServiceImpl implements AnimalService {
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;
    private final AnimalRepository animalRepository;
    private final PassportRepository passportRepository;

    public AnimalServiceImpl(ModelMapper modelMapper, FileUtil fileUtil, ValidatorUtil validatorUtil, Gson gson, AnimalRepository animalRepository, PassportRepository passportRepository) {
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.animalRepository = animalRepository;
        this.passportRepository = passportRepository;
    }


    @Override
    public void seedAnimals() {
        if (this.animalRepository.count()!= 0){
            return;
        }

        final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\json\\animals.json";
        String content = this.fileUtil.fileReader(READ_FILE_PATH);

        AnimalSeedDto[] animalSeedDtos = this.gson.fromJson(content, AnimalSeedDto[].class);
        for (AnimalSeedDto seedDto : animalSeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            } else if (!this.validatorUtil.isValid(seedDto.getPassport())) {
                this.validatorUtil.violations(seedDto.getPassport())
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            try {
                Animal animal = this.modelMapper.map(seedDto, Animal.class);
                Passport passport = this.modelMapper
                        .map(seedDto.getPassport(), Passport.class);

                if (seedDto.getPassport().getRegistrationDate() != null){
                    passport.setRegistrationDate(LocalDate.parse(
                            seedDto.getPassport().getRegistrationDate(),
                            DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    ));
                }
                this.passportRepository.saveAndFlush(passport);
                animal.setPassport(passport);
                this.animalRepository.saveAndFlush(animal);
                passport.setAnimal(animal);
                this.passportRepository.saveAndFlush(passport);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
}
