package softuni.shop.domain.dto.xml.importDto;

import softuni.shop.domain.dto.json.userDto.UserSeedDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersImportRootDto implements Serializable {
    @XmlElement(name = "user")
    private List<UserSeedDto> users;

    public UsersImportRootDto() {
    }

    public List<UserSeedDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserSeedDto> users) {
        this.users = users;
    }
}
