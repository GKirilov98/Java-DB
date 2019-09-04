package softuni.carshop.domain.dtos.XML.importDto;

import softuni.carshop.domain.dtos.JSON.customerDto.CustomerSeedDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerImportRootDto implements Serializable {
    @XmlElement(name = "customer")
    private List<CustomerSeedDto> customers;

    public CustomerImportRootDto() {
    }

    public List<CustomerSeedDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerSeedDto> customers) {
        this.customers = customers;
    }
}
