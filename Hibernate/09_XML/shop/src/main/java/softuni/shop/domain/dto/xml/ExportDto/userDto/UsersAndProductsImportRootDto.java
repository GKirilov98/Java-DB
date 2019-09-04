package softuni.shop.domain.dto.xml.ExportDto.userDto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersAndProductsImportRootDto implements Serializable {
    @XmlAttribute
    private Integer count;
    @XmlElement(name = "user")
    private List<UserAndProductImportDto> users;

    public UsersAndProductsImportRootDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserAndProductImportDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserAndProductImportDto> users) {
        this.users = users;
    }
}
