package softuni.shop.domain.dto.productDto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ProductUserSoldDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private String price;
    @Expose
    private String buyerFirstName;
    @Expose
    private String buyerLastName;

    public ProductUserSoldDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBuyerFirstName() {
        return buyerFirstName;
    }

    public void setBuyerFirstName(String buyerFirstName) {
        this.buyerFirstName = buyerFirstName;
    }

    public String getBuyerLastName() {
        return buyerLastName;
    }

    public void setBuyerLastName(String buyerLastName) {
        this.buyerLastName = buyerLastName;
    }
}
