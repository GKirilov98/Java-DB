package softuni.shop.domain.dto.json.categoryDto;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesProductCountDto implements Serializable {
    @XmlAttribute
    @Expose
    private String category;
    @XmlElement(name = "products-count")
    @Expose
    private int productsCount;
    @XmlElement(name = "average-price")
    @Expose
    private BigDecimal averagePrice;
    @XmlElement(name = "total-revenue")
    @Expose
    private BigDecimal totalRevenue;

    public CategoriesProductCountDto() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(int productsCount) {
        this.productsCount = productsCount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }


    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
