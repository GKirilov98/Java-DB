package softuni.sprin_intro2.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.sprin_intro2.entities.User;
import softuni.sprin_intro2.repositories.UserRepository;
import softuni.sprin_intro2.services.UserService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsersByEmailProvider(String provider) {
        return this.userRepository.findAllByEmailEndingWith(provider);
    }

    @Override
    public Integer deactivateInactiveUsers(Date date) {
        final int[] countOfDeleted = {0};
        this.userRepository
                .findAllByLastTimeLoggedInBefore(date)
                .forEach(user -> {
                    countOfDeleted[0] +=1 ;
                    user.setDeleted(true);
                });
        return countOfDeleted[0];
    }

    @Override
    public void save(final User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public long getUsersCount() {
        return this.userRepository.count();
    }


}