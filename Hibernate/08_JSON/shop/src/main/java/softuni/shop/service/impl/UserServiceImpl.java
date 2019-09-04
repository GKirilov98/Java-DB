package softuni.shop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.shop.domain.dto.productDto.ProductSeedDto;
import softuni.shop.domain.dto.productDto.ProductViewDto;
import softuni.shop.domain.dto.userDto.UserSeedDto;
import softuni.shop.domain.dto.userDto.UserSoldProductDto;
import softuni.shop.domain.dto.userDto.UserViewDto;
import softuni.shop.domain.dto.userDto.UsersAndProductDto;
import softuni.shop.domain.entities.Product;
import softuni.shop.domain.entities.User;
import softuni.shop.repository.UserRepository;
import softuni.shop.service.UserService;
import softuni.shop.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @Override
    public void addUsers(UserSeedDto[] userAddDtos) {
        if (this.userRepository.count() != 0) {
            return;
        }
        for (UserSeedDto userAddDto : userAddDtos) {
            if (!this.validatorUtil.isValid(userAddDto)) {
                this.validatorUtil
                        .violations(userAddDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            User user = this.modelMapper.map(userAddDto, User.class);
            this.userRepository.saveAndFlush(user);
        }

        List<User> allUsers = this.userRepository.findAll();
        for (User user : allUsers) {
            user.setFriends(getRandomFriends());
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public List<UserSoldProductDto> userSoldProduct() {
        // Mapping soldProduct fromt UserSoldProductDto.class with sold in User.class
        TypeMap<User, UserSoldProductDto> typeMap = this.modelMapper
                .createTypeMap(User.class, UserSoldProductDto.class);
        typeMap.addMappings(m -> m.map(User::getSold, UserSoldProductDto::setSoldProducts));
        //

        List<UserSoldProductDto> userSoldProductDtos = new ArrayList<>();
        List<User> users = this.userRepository.findAllBySoldNotNullOrderByLastNameAscFirstName();
        for (User u : users) {
            UserSoldProductDto usp = this.modelMapper.map(u, UserSoldProductDto.class);
            userSoldProductDtos.add(usp);
        }


        return userSoldProductDtos;
    }

    private List<User> getRandomFriends() {
        List<User> friendList = new ArrayList<>();

        Random random = new Random();
        int length = random.nextInt((int) this.userRepository.count() - 1) + 1;

        for (int i = 0; i < length; i++) {
            User user = this.getRandomUser();

            friendList.add(user);
        }

        return friendList;
    }

    private User getRandomUser() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.userRepository.count() - 1)) + 1;

        return this.userRepository.findById(randomId).orElse(null);
    }

    @Override
    public UsersAndProductDto getUsersAndProductsForSale() {
        UsersAndProductDto usersAndProductDto = new UsersAndProductDto();
        List<User> users = this.userRepository
                .findAll()
                .stream()
                .filter(u -> u.getSold().stream().anyMatch(p -> p.getBuyer() == null))
                .collect(Collectors.toList());
        usersAndProductDto.setUsersCount(users.size());

        List<UserViewDto> userViewDtos = new ArrayList<>();

        for (User user : users) {
            UserViewDto uvd = this.modelMapper.map(user, UserViewDto.class);
            ProductViewDto pvd = new ProductViewDto();

            List<ProductSeedDto> productSeedDtos = new ArrayList<>();
            user.getSold()
                    .stream()
                    .filter(product -> product.getBuyer() == null)
                    .forEach( product -> {
                        ProductSeedDto productSeedDto = this.modelMapper.map(product, ProductSeedDto.class);
                        productSeedDtos.add(productSeedDto);
                    });

            pvd.setCount(productSeedDtos.size());
            pvd.setProducts(productSeedDtos);

            uvd.setSoldProducts(pvd);
            userViewDtos.add(uvd);
        }

       usersAndProductDto.setUsers(userViewDtos);
        return usersAndProductDto;
    }
}
