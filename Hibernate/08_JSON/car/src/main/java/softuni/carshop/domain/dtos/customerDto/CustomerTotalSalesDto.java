package softuni.carshop.domain.dtos.customerDto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerTotalSalesDto implements Serializable {
    @Expose
    private String fullName;
    @Expose
    private Integer boughtCar;
    @Expose
    private BigDecimal spentMoney;

    public CustomerTotalSalesDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public int getBoughtCar() {
        return boughtCar;
    }

    public void setBoughtCar(int boughtCar) {
        this.boughtCar = boughtCar;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }
}
