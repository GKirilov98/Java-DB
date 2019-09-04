package softuni.carshop.services;

import softuni.carshop.domain.dtos.partDto.PartSeedDto;

public interface PartService {

    void seedPart(PartSeedDto[] partSeedDtros);
}
