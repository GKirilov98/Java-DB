package softuni.carshop.domain.dtos.XML.exportDto.customerDto;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerExportOrderDto implements Serializable {
    @XmlElement(name = "id")
    @Expose
    private Integer id;
    @XmlElement(name = "name")
    @Expose
    private String name;
    @XmlElement(name = "birth-date")
    @Expose
    private String birthDate;
    @XmlElement(name = "is-young-driver")
    @Expose
    private boolean isYoungDriver;

    public CustomerExportOrderDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBirthDate() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
