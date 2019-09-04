package gamestore.services;

import gamestore.domain.Messages;
import gamestore.domain.dtos.UserLoginDto;
import gamestore.domain.dtos.UserRegisterDto;
import gamestore.domain.entities.Role;
import gamestore.domain.entities.User;
import gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        Validator validator = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();

        StringBuilder sb = new StringBuilder();
        if ((!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword()))) {
            sb.append(Messages.PASSWORD_DONT_MATCH);
        } else if (validator.validate(userRegisterDto).size() > 0) {
            for (ConstraintViolation<UserRegisterDto> violation : validator.validate(userRegisterDto)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else {
            User alraedyExist = this.userRepository.findByEmail(userRegisterDto.getEmail()).orElse(null);
            if (alraedyExist != null) {
                return Messages.USER_ALREADY_EXIST;
            }

            User entity = this.modelMapper.map(userRegisterDto, User.class);
            if (this.userRepository.count() == 0) {
                entity.setRole(Role.ADMIN);
            } else {
                entity.setRole(Role.USER);
            }

            this.userRepository.saveAndFlush(entity);
            sb.append(String.format(Messages.SUCCESS_REGISTERED, entity.getFullName()));
        }

        return sb.toString().trim();
    }

    @Override
    public String[] loginUser(UserLoginDto userLoginDto) {
        String[] emailFullName = new String[2];
        StringBuilder sb = new StringBuilder();
        Validator validator = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();
        Set<ConstraintViolation<UserLoginDto>> validations = validator.validate(userLoginDto);
        if (validations.size() > 0) {
            for (ConstraintViolation<UserLoginDto> validation : validations) {
                sb.append(validation.getMessage()).append(System.lineSeparator());
            }
        } else {
            User entity = this.userRepository.findByEmail(userLoginDto.getEmail()).orElse(null);
            if (entity == null || !entity.getPassword().trim().equals(userLoginDto.getPassword().trim())) {
                sb.append(Messages.INCORECT_PASSWORD_EMAIL);
            } else {
                sb.append(Messages.SUCCESS_LOG_IN);
                emailFullName[1] = entity.getFullName();
            }
        }

        emailFullName[0] = sb.toString();
        return emailFullName;
    }

    @Override
    public boolean isAdmin(String loggedInUserEmail) {
        if (loggedInUserEmail == null) {
            return false;
        }

        User user = this.userRepository.findByEmail(loggedInUserEmail).orElse(null);

        if (user == null) {
            System.out.println(Messages.NO_SUCH_USER);
            return false;
        } else {
            return user.getRole() == Role.ADMIN;
        }
    }
}
