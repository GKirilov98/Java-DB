package softuni.sprin_intro2.services;

import softuni.sprin_intro2.entities.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    List<User> getAllUsersByEmailProvider(String provider);

    Integer deactivateInactiveUsers(Date date);

    void save(User user);

    long getUsersCount();

}
