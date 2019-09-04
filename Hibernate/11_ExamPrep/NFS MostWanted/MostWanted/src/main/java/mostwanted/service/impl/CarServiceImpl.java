package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.car.CarSeedDto;
import mostwanted.domain.entities.Car;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.service.CarService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final RacerRepository racerRepository;

    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile()  {
        final String READ_PATH = ".\\src\\main\\resources\\files\\cars.json";
        return this.fileUtil.fileReader(READ_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {
        CarSeedDto[] carSeedDtos = this.gson.fromJson(carsFileContent, CarSeedDto[].class);
        for (CarSeedDto carSeedDto : carSeedDtos) {
            if (!this.validatorUtil.isValid(carSeedDto)) {
                this.validatorUtil
                        .violations(carSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Car car = this.modelMapper.map(carSeedDto, Car.class);
            car.setRacer(this.racerRepository.findRacerByName(carSeedDto.getRacer()));
            this.carRepository.saveAndFlush(car);
        }

        return "Success";
    }
}
