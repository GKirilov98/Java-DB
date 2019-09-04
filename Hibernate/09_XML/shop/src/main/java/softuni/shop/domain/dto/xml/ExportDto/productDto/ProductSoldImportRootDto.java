package softuni.shop.domain.dto.xml.ExportDto.productDto;

import softuni.shop.domain.dto.json.productDto.ProductUserSoldDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSoldImportRootDto implements Serializable {
    @XmlElement(name = "product")
    private List<ProductUserSoldDto> products;

    public ProductSoldImportRootDto() {
    }

    public List<ProductUserSoldDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductUserSoldDto> products) {
        this.products = products;
    }
}
