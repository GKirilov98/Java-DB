package softuni.carshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.JSON.saleDto.CarAppliedDiscountDto;
import softuni.carshop.domain.dtos.XML.exportDto.saleDto.SaleExportRootDto;
import softuni.carshop.domain.entities.Car;
import softuni.carshop.domain.entities.Customer;
import softuni.carshop.domain.entities.Part;
import softuni.carshop.domain.entities.Sale;
import softuni.carshop.repositories.CarRepository;
import softuni.carshop.repositories.CustomerRepository;
import softuni.carshop.repositories.SaleRepository;
import softuni.carshop.services.SaleService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedService() {
        for (int i = 0; i < 100; i++) {
            Sale sale = new Sale();
            sale.setCustomerId(getRandomCustomer());
            sale.setCarId(getRandomCar());
            sale.setDiscount(getRandomDiscount());
            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public List<CarAppliedDiscountDto> getAllSalesWithAppliedDiscount() {


        List<CarAppliedDiscountDto> carAppliedDiscountDtos = new ArrayList<>();
        this.saleRepository.findAll()
                .forEach(s -> {
                    CarAppliedDiscountDto cad = this.modelMapper.map(s, CarAppliedDiscountDto.class);

                    BigDecimal priceWithoutDiscount =
                            s.getCarId().getParts().stream()
                                    .map(Part::getPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                                    .setScale(2);

                    cad.setPriceWithDiscount(priceWithoutDiscount);
                    cad.setPrice( priceWithoutDiscount.subtract(
                                    priceWithoutDiscount
                                            .multiply(BigDecimal.valueOf(s.getDiscount()))));

                    carAppliedDiscountDtos.add(cad);
                });
        return carAppliedDiscountDtos;
    }

    @Override
    public SaleExportRootDto xmlExportSalesDiscount() {
        List<CarAppliedDiscountDto> carAppliedDiscountDtos = this.getAllSalesWithAppliedDiscount();
        SaleExportRootDto saleExportRootDto = new SaleExportRootDto();
        saleExportRootDto.setSale(carAppliedDiscountDtos);
        return saleExportRootDto;
    }

    private Car getRandomCar() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.carRepository.count() - 1)) + 1;

        return this.carRepository.findById(randomId).orElse(null);
    }

    private Customer getRandomCustomer() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.customerRepository.count() - 1)) + 1;

        return this.customerRepository.findById(randomId).orElse(null);
    }

    private double[] discounts = new double[]{
            0.0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0, 5
    };

    private double getRandomDiscount() {
        Random r = new Random();
        int i = r.nextInt(discounts.length - 1);
        return discounts[i];
    }
}
