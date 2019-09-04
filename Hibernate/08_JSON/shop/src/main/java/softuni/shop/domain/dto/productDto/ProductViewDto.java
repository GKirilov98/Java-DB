package softuni.shop.domain.dto.productDto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class ProductViewDto implements Serializable {
    @Expose
    private Integer count;
    @Expose
    private List<ProductSeedDto> products;

    public ProductViewDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductSeedDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSeedDto> products) {
        this.products = products;
    }
}
