package softuni.shop.domain.dto.xml.ExportDto.productDto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSoldWithCountImportRootDto implements Serializable {
    @XmlAttribute
    private Integer count;
    @XmlElement(name = "product")
    private List<ProductSoldAttributeImportDto> products;

    public ProductSoldWithCountImportRootDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductSoldAttributeImportDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSoldAttributeImportDto> products) {
        this.products = products;
    }
}
