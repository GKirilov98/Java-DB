package softuni.carshop.services;

import softuni.carshop.domain.dtos.supplierDto.SupplierLocalDto;
import softuni.carshop.domain.dtos.supplierDto.SupplierSeedDto;

import java.util.List;

public interface SupplierService {
    void seedSupplier(SupplierSeedDto[] supplierSeedDtos);

    List<SupplierLocalDto> getAllLocalSupplier();
}
