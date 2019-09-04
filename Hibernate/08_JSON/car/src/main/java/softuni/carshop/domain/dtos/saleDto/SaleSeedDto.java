package softuni.carshop.domain.dtos.saleDto;

import com.google.gson.annotations.Expose;
import softuni.carshop.domain.dtos.carDto.CarSeedDto;
import softuni.carshop.domain.dtos.customerDto.CustomerSeedDto;

import java.io.Serializable;

public class SaleSeedDto implements Serializable {
    @Expose
    private double discount;
    @Expose
    private CarSeedDto carId;
    @Expose
    private CustomerSeedDto customerId;

    public SaleSeedDto() {
    }

    public SaleSeedDto(double discount, CarSeedDto carId, CustomerSeedDto customerId) {
        setCustomerId(customerId);
        setCarId(carId);
        setDiscount(discount);
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        if (this.customerId.isYoungDriver()){
            discount += 0.05;
        }

        this.discount = discount;
    }

    public CarSeedDto getCarId() {
        return carId;
    }

    public void setCarId(CarSeedDto carId) {
        this.carId = carId;
    }

    public CustomerSeedDto getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerSeedDto customerId) {
        this.customerId = customerId;
    }
}
