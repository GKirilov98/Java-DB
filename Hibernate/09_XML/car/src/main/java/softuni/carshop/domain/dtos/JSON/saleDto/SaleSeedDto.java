package softuni.carshop.domain.dtos.JSON.saleDto;

import com.google.gson.annotations.Expose;
import softuni.carshop.domain.dtos.JSON.carDto.CarSeedElemetDto;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomerSeedDto;

import java.io.Serializable;

public class SaleSeedDto implements Serializable {
    @Expose
    private double discount;
    @Expose
    private CarSeedElemetDto carId;
    @Expose
    private CustomerSeedDto customerId;

    public SaleSeedDto() {
    }

    public SaleSeedDto(double discount, CarSeedElemetDto carId, CustomerSeedDto customerId) {
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

    public CarSeedElemetDto getCarId() {
        return carId;
    }

    public void setCarId(CarSeedElemetDto carId) {
        this.carId = carId;
    }

    public CustomerSeedDto getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerSeedDto customerId) {
        this.customerId = customerId;
    }
}
