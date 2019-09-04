package app.retake.domain.dto.animal;

import app.retake.domain.dto.passport.PassportSeedDto;
import app.retake.domain.entity.Passport;
import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class AnimalSeedDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private String type;
    @Expose
    private Integer age;
    @Expose
    private PassportSeedDto passport;

    public AnimalSeedDto() {
    }

    @NotNull
    @Length(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Length(min = 3, max = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotNull
    @Min(1)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

//    public PassportSeedDto getPassport() {
//        return passport;
//    }
//
//    public void setPassport(Passport passport) {
//        this.passport = passport;
//    }

        public PassportSeedDto getPassport() {
        return passport;
    }

    public void setPassport(PassportSeedDto passport) {
        this.passport = passport;
    }
}
