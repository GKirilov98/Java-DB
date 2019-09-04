package softuni.shop.domain.dto.json.userDto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.shop.domain.Messages;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSeedDto implements Serializable {
    @XmlAttribute(name = "first-name")
    @Expose
    private String firstName;
    @XmlAttribute(name = "last-name")
    @Expose
    private String lastName;
    @XmlAttribute
    @Expose
    private Integer age;

    public UserSeedDto() {
    }

    public UserSeedDto(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Length(min = 3, message = Messages.SHORT_LAST_NAME)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Min(value = 0, message = Messages.AGE_CANT_BE_NEGATIVE)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
