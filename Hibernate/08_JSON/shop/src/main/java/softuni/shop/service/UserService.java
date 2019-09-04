package softuni.shop.service;


import softuni.shop.domain.dto.userDto.UserSeedDto;
import softuni.shop.domain.dto.userDto.UserSoldProductDto;
import softuni.shop.domain.dto.userDto.UsersAndProductDto;

import java.util.List;

public interface UserService {
    void addUsers(UserSeedDto[] userAddDtos);

    List<UserSoldProductDto> userSoldProduct();

    UsersAndProductDto getUsersAndProductsForSale();
}
