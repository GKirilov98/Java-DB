package softuni.demo.domain.dtos.product;

import softuni.demo.domain.entities.Product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedRootDto implements Serializable {
    @XmlElement(name = "product")
    private List<ProductSeedDto> productList;

    public ProductSeedRootDto() {
    }

    public List<ProductSeedDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductSeedDto> productList) {
        this.productList = productList;
    }
}
