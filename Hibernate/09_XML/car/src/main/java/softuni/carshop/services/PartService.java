package softuni.carshop.services;

import softuni.carshop.domain.dtos.JSON.partDto.PartSeedDto;
import softuni.carshop.domain.dtos.XML.importDto.PartImportRootDto;

public interface PartService {

    void seedPart(PartSeedDto[] partSeedDtros);

    void importPartsXML(PartImportRootDto partImportRootDto);
}
