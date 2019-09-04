package softuni.shop.domain.dto.productDto;

import com.google.gson.annotations.Expose;
import softuni.shop.domain.Messages;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

public class ProductSeedDto implements Serializable {
    @Expose
    private String name;
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
