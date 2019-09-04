package weddingplaner.domain.dto.person;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Length;
import weddingplaner.domain.entity.enums.Gender;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

public class PersonSeedDto implements Serializable {
    @Expose
    private String firstName;
    @SerializedName("middleInitial")
    @Expose
    private String middleNameInitial;
    @Expose
    private String lastName;
    @Expose
    private String gender;
    @SerializedName("birthday")
    @Expose
    private String birthDate;
    @Expose
    private String phone;
    @Expose
    private String email;

    public PersonSeedDto() {
    }

    @NotNull
    @Length(min = 1, max = 60)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Length(min = 1, max = 1)
    public String getMiddleNameInitial() {
        return middleNameInitial;
    }

    public void setMiddleNameInitial(String middleNameInitial) {
        this.middleNameInitial = middleNameInitial;
    }

    @NotNull
    @Length(min = 2)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Pattern(regexp = "^([a-zA-Z0-9]+)@([a-z]+\\.[a-z]+$)")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
