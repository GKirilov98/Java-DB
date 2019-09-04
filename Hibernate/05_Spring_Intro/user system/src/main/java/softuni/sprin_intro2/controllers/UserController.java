package softuni.sprin_intro2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import softuni.sprin_intro2.entities.User;
import softuni.sprin_intro2.services.UserService;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@Controller
public class UserController implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(final String... args) {
        if (this.userService.getUsersCount() == 0L) {
            addUsers(50);
        }
        //01_Get Users by Email Provider
      //  printAllUsersByEmailProvider("abv.bg");
        //02_Remove Inactive Users
        Date date = new Date("01 Nov 2016"); // No one deleted
       deactivateUsersInactiveSinceDate(new Date());
    }


    private void deactivateUsersInactiveSinceDate( Date date) {
       int count = this.userService.deactivateInactiveUsers(date);
       if (count > 0){
           System.out.println(count + " users have been deleted");
       } else {
           System.out.println("No users have been deleted");
       }

    }

    private void printAllUsersByEmailProvider( String provider) {
        List<User> users = this.userService.getAllUsersByEmailProvider(provider);
        if (users.isEmpty()){
            System.out.println("No users found with email domain " + provider);
        }

        for (User user : users) {
            System.out.println(user.getUsername() + " " + user.getEmail());
        }
    }

    private void addUsers( int count) {
        for (int i = 1; i <= count; i++) {
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("pasSword%" + i);
            if (i >= count/2){
                user.setEmail("mail" + i + "x@abv.bg");
            } else {
                user.setEmail("mail" + i + "x@mail.bg");
            }
            user.setAge(i % 120 + 1);
            user.setFirstName("First" + i);
            user.setLastName("Last" + i);
            user.setRegisteredOn(new Date());
            user.setLastTimeLoggedIn(new Date());
            user.setDeleted(false);
            this.userService.save(user);
        }
    }

}