package softuni.carshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.supplierDto.SupplierLocalDto;
import softuni.carshop.domain.dtos.supplierDto.SupplierSeedDto;
import softuni.carshop.domain.entities.Supplier;
import softuni.carshop.repositories.SupplierRepository;
import softuni.carshop.services.SupplierService;
import softuni.carshop.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, SupplierRepository supplierRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void seedSupplier(SupplierSeedDto[] supplierSeedDtos) {
        if (this.supplierRepository.count() != 0) {
            return;
        }

        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            if (!this.validatorUtil.isValid(supplierSeedDto)) {
                this.validatorUtil.violations(supplierSeedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
            this.supplierRepository.saveAndFlush(supplier);
        }
    }

    @Override
    public List<SupplierLocalDto> getAllLocalSupplier() {
        List<SupplierLocalDto> locals = new ArrayList<>();
        this.supplierRepository.findAllByImporterIsFalse()
                .forEach(s -> {
                    SupplierLocalDto supplierLocalDto = this.modelMapper.map(s, SupplierLocalDto.class);
                    supplierLocalDto.setPartsCount(s.getParts().size());
                    locals.add(supplierLocalDto);
                });
        return locals;
    }
}
