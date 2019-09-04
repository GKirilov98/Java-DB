package softuni.carshop.domain.dtos.customerDto;

import com.google.gson.annotations.Expose;
import softuni.carshop.domain.dtos.saleDto.SaleSeedDto;

import java.io.Serializable;
import java.util.List;

public class CustomersOrderedDto implements Serializable {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private boolean isYoungDriver;
    @Expose
    private List<SaleSeedDto> sales;

    public CustomersOrderedDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }


    public List<SaleSeedDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleSeedDto> sales) {
        this.sales = sales;
    }
}
