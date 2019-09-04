package app.retake.services.impl;

import app.retake.domain.dto.procedure.AnimalAidReadDto;
import app.retake.domain.dto.procedure.ProcedureSeedDto;
import app.retake.domain.dto.procedure.ProceduresSeedRootDto;
import app.retake.domain.entity.Animal;
import app.retake.domain.entity.AnimalAid;
import app.retake.domain.entity.Passport;
import app.retake.domain.entity.Procedure;
import app.retake.repositories.*;
import app.retake.services.api.ProcedureService;
import app.retake.util.ValidatorUtil;
import app.retake.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProcedureServiceImpl implements ProcedureService {

    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    private final ProcedureRepository procedureRepository;
    private final AnimalAidRepository animalAidRepository;
    private final VetRepository vetRepository;
    private final AnimalRepository animalRepository;
    private final PassportRepository passportRepository;

    public ProcedureServiceImpl(XmlParser xmlParser, ValidatorUtil validatorUtil, ModelMapper modelMapper, ProcedureRepository procedureRepository, AnimalAidRepository animalAidRepository, VetRepository vetRepository, AnimalRepository animalRepository, PassportRepository passportRepository) {
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.procedureRepository = procedureRepository;
        this.animalAidRepository = animalAidRepository;
        this.vetRepository = vetRepository;
        this.animalRepository = animalRepository;
        this.passportRepository = passportRepository;
    }

    @Override
    public void seedProcedures() {
        if (this.procedureRepository.count() != 0) {
            return;
        }

        final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\xml\\procedures.xml";

        ProceduresSeedRootDto proceduresSeedRootDto = this.xmlParser.objectFromFile(ProceduresSeedRootDto.class, READ_FILE_PATH);

        for (ProcedureSeedDto seedDto : proceduresSeedRootDto.getProcedureSeedDtos()) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Procedure procedure = new Procedure();
            procedure.setVet(this.vetRepository.findVetByName(seedDto.getVetName()));

            Passport passport = this.passportRepository
                    .findPassportBySerialNumber(seedDto.getAnimalPassportNumber());
            Animal animal = this.animalRepository
                    .findAnimalByPassport(passport);
            procedure.setAnimal(animal);
            for (AnimalAidReadDto readDto : seedDto.getAnimalAidsReedDto().getAnimalAidReadDtos()) {
                this.animalAidRepository
                        .findAnimalAidByName(readDto.getName())
                        .ifPresent(animalAid -> procedure.getServices().add(animalAid));
            }

            this.procedureRepository.saveAndFlush(procedure);
        }
    }
}

