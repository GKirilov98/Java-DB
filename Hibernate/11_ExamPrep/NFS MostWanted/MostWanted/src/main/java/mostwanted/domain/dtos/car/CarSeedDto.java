package mostwanted.domain.dtos.car;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;

public class CarSeedDto implements Serializable {
    @Expose
    private String brand;
    @Expose
    private String model;
    @Expose
    private BigDecimal price;
    @Expose
    private Integer yearOfProduction;
    @Expose
    private Double maxSpeed;
    @Expose
    private Double zeroToSixty;
    @Expose
    private String racerName;

    public CarSeedDto() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getZeroToSixty() {
        return zeroToSixty;
    }

    public void setZeroToSixty(Double zeroToSixty) {
        this.zeroToSixty = zeroToSixty;
    }

    public String getRacer() {
        return racerName;
    }

    public void setRacer(String racerName) {
        this.racerName = racerName;
    }
}