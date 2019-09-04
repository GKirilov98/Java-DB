package softuni.shop.domain.dto.xml.ExportDto.userDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductImportRootDto implements Serializable {
    @XmlElement(name = "user")
    private List<UserSoldProductImportDto> users;

    public UserSoldProductImportRootDto() {
    }

    public List<UserSoldProductImportDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldProductImportDto> users) {
        this.users = users;
    }
}
