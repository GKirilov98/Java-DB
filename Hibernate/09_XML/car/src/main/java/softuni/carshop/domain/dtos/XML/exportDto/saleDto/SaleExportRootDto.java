package softuni.carshop.domain.dtos.XML.exportDto.saleDto;

import softuni.carshop.domain.dtos.JSON.saleDto.CarAppliedDiscountDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleExportRootDto implements Serializable {
    @XmlElement(name = "sale")
    private List<CarAppliedDiscountDto> sale;

    public SaleExportRootDto() {
    }

    public List<CarAppliedDiscountDto> getSale() {
        return sale;
    }

    public void setSale(List<CarAppliedDiscountDto> sale) {
        this.sale = sale;
    }
}
