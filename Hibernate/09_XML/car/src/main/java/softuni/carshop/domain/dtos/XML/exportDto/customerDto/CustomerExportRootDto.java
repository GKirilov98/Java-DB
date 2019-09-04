package softuni.carshop.domain.dtos.XML.exportDto.customerDto;

import softuni.carshop.domain.dtos.JSON.customerDto.CustomersOrderedDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerExportRootDto implements Serializable {
    @XmlElement(name = "customer")
    private List<CustomerExportOrderDto> customer;

    public CustomerExportRootDto() {
    }

    public List<CustomerExportOrderDto> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerExportOrderDto> customer) {
        this.customer = customer;
    }
}
