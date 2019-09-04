package softuni.shop.domain.dto.userDto;

import com.google.gson.annotations.Expose;
import softuni.shop.domain.dto.productDto.ProductViewDto;
import java.io.Serializable;


public class UserViewDto implements Serializable {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    private ProductViewDto soldProducts;

    public UserViewDto() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductViewDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductViewDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
