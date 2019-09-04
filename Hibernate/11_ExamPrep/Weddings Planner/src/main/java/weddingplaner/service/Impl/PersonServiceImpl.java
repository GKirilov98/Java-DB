package weddingplaner.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import weddingplaner.domain.dto.person.PersonSeedDto;
import weddingplaner.domain.entity.Person;
import weddingplaner.domain.entity.enums.Gender;
import weddingplaner.repository.PersonRepository;
import weddingplaner.service.PersonService;
import weddingplaner.util.FileUtil;
import weddingplaner.util.ValidatorUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PersonServiceImpl implements PersonService {
    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\json\\input\\people.json";

    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    private final PersonRepository personRepository;

    public PersonServiceImpl(ValidatorUtil validatorUtil, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, PersonRepository personRepository) {
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.personRepository = personRepository;
    }


    @Override
    public void seedPerson() {
        if (personRepository.count() != 0){
            return;
        }

        String content = this.fileUtil.fileReader(READ_FILE_PATH);
        PersonSeedDto[] personSeedDtos = this.gson.fromJson(content, PersonSeedDto[].class);
        for (PersonSeedDto seedDto : personSeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)){
                this.validatorUtil.violations(seedDto)
                        .forEach( e -> System.out.println(e.getMessage()));
                continue;
            }


                Person person = this.modelMapper.map(seedDto, Person.class);
                person.setGender(Gender.valueOf(seedDto.getGender().toUpperCase()));
                if (seedDto.getBirthDate() != null){
                    person.setBirthDate(LocalDate.parse(seedDto.getBirthDate().replace('T', ' '),
                            DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss")));
                }

                this.personRepository.saveAndFlush(person);
        }
    }
}
