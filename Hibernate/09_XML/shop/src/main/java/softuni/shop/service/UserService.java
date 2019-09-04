package softuni.shop.service;


import softuni.shop.domain.dto.json.userDto.UserSeedDto;
import softuni.shop.domain.dto.json.userDto.UserSoldProductDto;
import softuni.shop.domain.dto.json.userDto.UsersAndProductDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UserSoldProductImportRootDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UsersAndProductsImportRootDto;
import softuni.shop.domain.dto.xml.importDto.UsersImportRootDto;

import java.util.List;

public interface UserService {
    void seedUsersJson(UserSeedDto[] userSeedDtos);

    List<UserSoldProductDto> userSoldProduct();

    UsersAndProductDto getUsersAndProductsForSale();

    void importUsersXml(UsersImportRootDto usersImportRootDto);

    UserSoldProductImportRootDto exportUserSoldProductXml();

    UsersAndProductsImportRootDto exportUserSoldProductWithCountXml();
}
