package softuni.demo.domain.dtos.employee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmployeeProductiveDto implements Serializable {
    @SerializedName(value = "full_name")
    @Expose
    private String fullName;
    @Expose
    private String position;
    @SerializedName(value = "number")
    @Expose
    private String cardNumber;

    public EmployeeProductiveDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNumber() {
        return cardNumber;
    }

    public void setNumber(String number) {
        this.cardNumber = number;
    }
}
