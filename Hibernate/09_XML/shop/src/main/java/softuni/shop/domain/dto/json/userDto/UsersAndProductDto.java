package softuni.shop.domain.dto.json.userDto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class UsersAndProductDto implements Serializable {
    @Expose
    private Integer usersCount;
    @Expose
    private List<UserViewDto> users;

    public UsersAndProductDto() {
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserViewDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserViewDto> users) {
        this.users = users;
    }
}
