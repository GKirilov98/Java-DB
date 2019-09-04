package softuni.shop.domain.dto.userDto;

import com.google.gson.annotations.Expose;
import softuni.shop.domain.dto.productDto.ProductUserSoldDto;

import java.io.Serializable;
import java.util.List;

public class UserSoldProductDto implements Serializable {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private List<ProductUserSoldDto> soldProducts;

    public UserSoldProductDto() {
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

    public List<ProductUserSoldDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductUserSoldDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
