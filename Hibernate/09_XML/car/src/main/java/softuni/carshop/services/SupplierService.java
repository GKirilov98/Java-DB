package softuni.carshop.services;

import softuni.carshop.domain.dtos.JSON.supplierDto.SupplierLocalDto;
import softuni.carshop.domain.dtos.JSON.supplierDto.SupplierSeedDto;
import softuni.carshop.domain.dtos.XML.exportDto.suppliersDto.SupplierExportRootDto;
import softuni.carshop.domain.dtos.XML.importDto.SupplierImportRootDto;

import java.util.List;

public interface SupplierService {
    void seedSupplierJSON(SupplierSeedDto[] supplierSeedDtos);
    void importSuppliersXML(SupplierImportRootDto supplierImportRootDto);

    List<SupplierLocalDto> getAllLocalSupplier();

    SupplierExportRootDto xmlExportLocalSuppliers();
}
