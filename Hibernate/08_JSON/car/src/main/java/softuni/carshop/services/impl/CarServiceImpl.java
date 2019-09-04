package softuni.carshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.carDto.CarSeedDto;
import softuni.carshop.domain.dtos.carDto.CarToyotaDto;
import softuni.carshop.domain.dtos.carDto.CarWithPartsDto;
import softuni.carshop.domain.entities.Car;
import softuni.carshop.domain.entities.Part;
import softuni.carshop.repositories.CarRepository;
import softuni.carshop.repositories.PartRepository;
import softuni.carshop.services.CarService;
import softuni.carshop.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final PartRepository partRepository;

    @Autowired
    public CarServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, CarRepository carRepository, PartRepository partRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
    }

    @Override
    public void seedCar(CarSeedDto[] carSeedDtos) {
        if (this.carRepository.count() != 0) {
            return;
        }

        for ( CarSeedDto carSeedDto : carSeedDtos) {
            if (!this.validatorUtil.isValid(carSeedDto)) {
                this.validatorUtil.violations(carSeedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }
            Car car = this.modelMapper.map(carSeedDto, Car.class);
                car.setParts(getRandomParts());
            this.carRepository.saveAndFlush(car);
        }
    }

    private List<Part> getRandomParts() {
        List<Part> parts = new ArrayList<>();

        Random random = new Random();
        int length = random.nextInt((20 - 10) + 1) + 10;

        for (int i = 0; i < length; i++) {
            Part partDto = this.getRandomPart();
            parts.add(partDto);
        }

        return parts;
    }

    private Part getRandomPart() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.partRepository.count() - 1)) + 1;

        return this.partRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CarToyotaDto> getAllCarToyota() {
        List<CarToyotaDto> toyotas = new ArrayList<>();
        this.carRepository.toyotaCars()
                .forEach( c -> {
                    CarToyotaDto toyota = this.modelMapper.map(c, CarToyotaDto.class);
                    toyotas.add(toyota);

        });

        return toyotas;
    }

    @Override
    public List<CarWithPartsDto> getAllCarWithParts() {
        List<CarWithPartsDto>carWithPartsDtos = new ArrayList<>();
        this.carRepository.findAll()
                .forEach(c ->{
                    CarWithPartsDto cwp = this.modelMapper.map(c, CarWithPartsDto.class);
                    carWithPartsDtos.add(cwp);
                });
        return carWithPartsDtos;
    }
}
