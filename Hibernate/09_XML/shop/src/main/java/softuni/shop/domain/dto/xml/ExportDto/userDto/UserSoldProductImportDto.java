package softuni.shop.domain.dto.xml.ExportDto.userDto;

import com.google.gson.annotations.Expose;
import softuni.shop.domain.dto.xml.ExportDto.productDto.ProductSoldImportRootDto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductImportDto implements Serializable {
    @XmlAttribute(name = "first-name")
    @Expose
    private String firstName;
    @XmlAttribute(name = "last-name")
    @Expose
    private String lastName;
    @XmlElement(name = "sold-products")
    @Expose
    private ProductSoldImportRootDto soldProducts;

    public UserSoldProductImportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ProductSoldImportRootDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductSoldImportRootDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
