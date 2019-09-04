package softuni.carshop.domain.dtos.JSON.customerDto;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesDto implements Serializable {
    @XmlAttribute(name = "full-name")
    @Expose
    private String fullName;
    @XmlAttribute(name = "bought-cars")
    @Expose
    private Integer boughtCar;
    @XmlAttribute(name = "spent-money")
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
