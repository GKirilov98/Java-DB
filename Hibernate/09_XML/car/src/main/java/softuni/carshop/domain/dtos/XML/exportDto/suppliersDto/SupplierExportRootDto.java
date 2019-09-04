package softuni.carshop.domain.dtos.XML.exportDto.suppliersDto;

import softuni.carshop.domain.dtos.JSON.supplierDto.SupplierLocalDto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierExportRootDto {
    @XmlElement(name = "supplier")
    private List<SupplierLocalDto> supplierLocalDtos;

    public SupplierExportRootDto() {
    }

    public List<SupplierLocalDto> getSupplierLocalDtos() {
        return supplierLocalDtos;
    }

    public void setSupplierLocalDtos(List<SupplierLocalDto> supplierLocalDtos) {
        this.supplierLocalDtos = supplierLocalDtos;
    }
}
