package softuni.shop.domain.dto.json.productDto;

import com.google.gson.annotations.Expose;
import softuni.shop.domain.Messages;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedDto implements Serializable {
    @XmlElement
    @Expose
    private String name;
    @XmlElement
    @Expose
    private BigDecimal price;

    public ProductSeedDto() {
    }

    @NotNull(message = Messages.PRODUCT_NAME_CANNOT_BE_NULL)
    @Size(min = 3, message =  Messages.PRODUCT_NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = Messages.PRODUCT_PRICE_CANNOT_BE_NULL)
    @Min(value = 0, message = Messages.PRODUCT_PRICE_CANNOT_BE_NEGATIVE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
