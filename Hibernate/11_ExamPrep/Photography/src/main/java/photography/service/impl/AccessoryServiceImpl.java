package photography.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import photography.domain.dto.accessories.AccessoriesSeedRootDto;
import photography.domain.dto.accessories.AccessorySeedDto;
import photography.domain.entity.Accessory;
import photography.domain.entity.Photographer;
import photography.repository.AccessoryRepository;
import photography.repository.PhotographerRepository;
import photography.service.AccessoryService;
import photography.util.ValidatorUtil;
import photography.util.XmlParser;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class AccessoryServiceImpl implements AccessoryService {

    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    private final AccessoryRepository accessoryRepository;
    private final PhotographerRepository photographerRepository;

    public AccessoryServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, AccessoryRepository accessoryRepository, PhotographerRepository photographerRepository) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.accessoryRepository = accessoryRepository;
        this.photographerRepository = photographerRepository;
    }

    @Override
    public void seedAccessories() {
        if (this.accessoryRepository.count() != 0){
            return;
        }

        final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\input\\accessories.xml";

        AccessoriesSeedRootDto accessoriesSeedRootDto = this.xmlParser
                .objectFromFile(AccessoriesSeedRootDto.class, READ_FILE_PATH);
        for (AccessorySeedDto seedDto : accessoriesSeedRootDto.getAccessory()) {
            if (!validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Accessory accessory = this.modelMapper.map(seedDto, Accessory.class);
            accessory.setOwner(getRandomPhotographer());
            this.accessoryRepository.saveAndFlush(accessory);
            accessory.getOwner().getAccessories().add(accessory);
            this.photographerRepository.saveAndFlush(accessory.getOwner());
        }
    }

    private Photographer getRandomPhotographer() {

        Random random = new Random();
        int randomId = random.nextInt((int) (this.photographerRepository.count() - 1)) + 1;

        return this.photographerRepository.findById(randomId).orElse(null);
    }


}
