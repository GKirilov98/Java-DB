package softuni.carshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.partDto.PartSeedDto;
import softuni.carshop.domain.entities.Part;
import softuni.carshop.domain.entities.Supplier;
import softuni.carshop.repositories.PartRepository;
import softuni.carshop.repositories.SupplierRepository;
import softuni.carshop.services.PartService;
import softuni.carshop.util.ValidatorUtil;

import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public PartServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, PartRepository partRepository, SupplierRepository supplierRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void seedPart(PartSeedDto[] partSeedDtros) {
        if (this.partRepository.count() != 0) {
            return;
        }

        for ( PartSeedDto partSeedDto : partSeedDtros) {
            if (!this.validatorUtil.isValid(partSeedDto)) {
                this.validatorUtil.violations(partSeedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Part part = this.modelMapper.map(partSeedDto, Part.class);
            part.setSupplierId(getRandomSupplier());
            this.partRepository.saveAndFlush(part);
        }
    }

    private Supplier getRandomSupplier() {
        Random random = new Random();

        int id = random.nextInt((int) this.supplierRepository.count() - 1) + 1;

        return this.supplierRepository.getOne(id);
    }
}
