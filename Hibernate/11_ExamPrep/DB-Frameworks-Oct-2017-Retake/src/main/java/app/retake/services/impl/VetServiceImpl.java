package app.retake.services.impl;

import app.retake.domain.dto.vet.VetSeedDto;
import app.retake.domain.dto.vet.VetsSeedRootDto;
import app.retake.domain.entity.Vet;
import app.retake.repositories.VetRepository;
import app.retake.services.api.VetService;
import app.retake.util.ValidatorUtil;
import app.retake.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class VetServiceImpl implements VetService {
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    private final VetRepository vetRepository;

    public VetServiceImpl(XmlParser xmlParser, ValidatorUtil validatorUtil, ModelMapper modelMapper, VetRepository vetRepository) {
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.vetRepository = vetRepository;
    }

    @Override
    public void seedVet() {
        if (this.vetRepository.count() != 0){
            return;
        }
        final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\xml\\vets.xml";

        VetsSeedRootDto vetsSeedRootDtos = this.xmlParser.objectFromFile(VetsSeedRootDto.class, READ_FILE_PATH );
        for (VetSeedDto seedDto : vetsSeedRootDtos.getVetSeedDtos()) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Vet vet = this.modelMapper.map(seedDto, Vet.class);
            this.vetRepository.saveAndFlush(vet);
        }
    }
}
