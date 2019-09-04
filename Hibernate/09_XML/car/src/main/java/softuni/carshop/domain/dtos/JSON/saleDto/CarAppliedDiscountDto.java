package softuni.carshop.domain.dtos.JSON.saleDto;

import com.google.gson.annotations.Expose;
import softuni.carshop.domain.dtos.JSON.carDto.CarViewDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAppliedDiscountDto implements Serializable {
    @XmlElement
    @Expose
    private CarViewDto car;
    @XmlElement(name = "customer-name")
    @Expose
    private String customerName;
    @XmlElement
    @Expose
    private double discount;
    @XmlElement
    @Expose
    private BigDecimal price;
    @XmlElement(name = "price-with-discount")
    @Expose
    private BigDecimal priceWithDiscount;

    public CarAppliedDiscountDto() {
    }

    public CarViewDto getCar() {
        return car;
    }

    public void setCar(CarViewDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
