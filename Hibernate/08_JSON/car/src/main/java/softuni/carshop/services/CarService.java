package softuni.carshop.services;

import softuni.carshop.domain.dtos.carDto.CarSeedDto;
import softuni.carshop.domain.dtos.carDto.CarToyotaDto;
import softuni.carshop.domain.dtos.carDto.CarWithPartsDto;

import java.util.List;

public interface CarService {
    void seedCar(CarSeedDto[] carSeedDtos);

    List<CarToyotaDto> getAllCarToyota();

    List<CarWithPartsDto> getAllCarWithParts();
}
