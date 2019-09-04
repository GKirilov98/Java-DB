package softuni.carshop.domain.dtos.XML.exportDto.customerDto;

import softuni.carshop.domain.dtos.JSON.customerDto.CustomerTotalSalesDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSaleExportRootDto implements Serializable {
    @XmlElement(name = "customer")
    private List<CustomerTotalSalesDto> customer;

    public CustomerTotalSaleExportRootDto() {
    }

    public List<CustomerTotalSalesDto> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerTotalSalesDto> customer) {
        this.customer = customer;
    }
}
