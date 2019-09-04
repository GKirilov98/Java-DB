package softuni.carshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.JSON.supplierDto.SupplierLocalDto;
import softuni.carshop.domain.dtos.JSON.supplierDto.SupplierSeedDto;
import softuni.carshop.domain.dtos.XML.exportDto.suppliersDto.SupplierExportRootDto;
import softuni.carshop.domain.dtos.XML.importDto.SupplierImportRootDto;
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
    public void seedSupplierJSON(SupplierSeedDto[] supplierSeedDtos) {
        if (this.supplierRepository.count() != 0) {
            return;
        }

        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            validateAndSave(supplierSeedDto);
        }
    }

    @Override
    public void importSuppliersXML(SupplierImportRootDto supplierImportRootDto) {
        if (this.supplierRepository.count() != 0) {
            return;
        }

        for (SupplierSeedDto supplierSeedDto : supplierImportRootDto.getSuppliers()) {
            validateAndSave(supplierSeedDto);
        }
    }

    private void validateAndSave(SupplierSeedDto supplierSeedDto) {
        if (!this.validatorUtil.isValid(supplierSeedDto)) {
            this.validatorUtil.violations(supplierSeedDto)
                    .forEach(e -> System.out.println(e.getMessage()));
            return;
        }

        Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
        this.supplierRepository.saveAndFlush(supplier);
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

    @Override
    public SupplierExportRootDto xmlExportLocalSuppliers() {
        List<SupplierLocalDto> supplierLocalDtos = this.getAllLocalSupplier();
        SupplierExportRootDto supplierExportRootDto = new SupplierExportRootDto();
        supplierExportRootDto.setSupplierLocalDtos(supplierLocalDtos);
        return supplierExportRootDto;
    }
}
