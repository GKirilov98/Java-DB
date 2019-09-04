package softuni.carshop.services;

import softuni.carshop.domain.dtos.JSON.carDto.CarSeedElemetDto;
import softuni.carshop.domain.dtos.JSON.carDto.CarToyotaDto;
import softuni.carshop.domain.dtos.JSON.carDto.CarWithPartsDto;
import softuni.carshop.domain.dtos.XML.exportDto.carDto.CarExportRootDto;
import softuni.carshop.domain.dtos.XML.exportDto.carDto.CarToyotaExportRootDto;
import softuni.carshop.domain.dtos.XML.importDto.CarImportRootDto;

import java.util.List;

public interface CarService {
    void seedCar(CarSeedElemetDto[] carSeedDtos);

    List<CarToyotaDto> getAllCarToyota();

    List<CarWithPartsDto> getAllCarWithParts();

    void importCarsXML(CarImportRootDto carImportRootDto);
    CarExportRootDto exportCars();

    CarToyotaExportRootDto exportCarsToyota();
}
