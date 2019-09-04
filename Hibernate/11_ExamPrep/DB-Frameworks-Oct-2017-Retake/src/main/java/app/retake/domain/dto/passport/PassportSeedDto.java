package app.retake.domain.dto.passport;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


public class PassportSeedDto implements Serializable {
    @Expose
    private String serialNumber;
    @Expose
    private String ownerPhoneNumber;
    @Expose
    private String ownerName;
    @Expose
    private String registrationDate;

    public PassportSeedDto() {
    }

    @NotNull
    @Pattern(regexp = "[a-zA-z]{7}[0-9]{3}")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @NotNull
    @Pattern(regexp = "(\\+359|0)[0-9]{9}")
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    @NotNull
    @Length(min = 2, max = 30)
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
