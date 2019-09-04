package softuni.shop.domain.dto.xml.importDto;

import softuni.shop.domain.dto.json.productDto.ProductSeedDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsImporRootDto implements Serializable {
    @XmlElement(name = "product")
    private List<ProductSeedDto> products;

    public ProductsImporRootDto() {
    }

    public List<ProductSeedDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSeedDto> products) {
        this.products = products;
    }
}
