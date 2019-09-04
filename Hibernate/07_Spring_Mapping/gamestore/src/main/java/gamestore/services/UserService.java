package gamestore.services;

import gamestore.domain.dtos.UserLoginDto;
import gamestore.domain.dtos.UserRegisterDto;

public interface UserService {
    String registerUser(UserRegisterDto userRegisterDto);

    String[] loginUser(UserLoginDto userLoginDto);

    boolean isAdmin(String loggedInUserEmail);
}

