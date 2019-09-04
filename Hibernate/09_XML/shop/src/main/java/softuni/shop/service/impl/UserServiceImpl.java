package softuni.shop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.shop.domain.dto.json.productDto.ProductSeedDto;
import softuni.shop.domain.dto.json.productDto.ProductUserSoldDto;
import softuni.shop.domain.dto.json.productDto.ProductViewDto;
import softuni.shop.domain.dto.json.userDto.UserSeedDto;
import softuni.shop.domain.dto.json.userDto.UserSoldProductDto;
import softuni.shop.domain.dto.json.userDto.UserViewDto;
import softuni.shop.domain.dto.json.userDto.UsersAndProductDto;
import softuni.shop.domain.dto.xml.ExportDto.productDto.ProductSoldAttributeImportDto;
import softuni.shop.domain.dto.xml.ExportDto.productDto.ProductSoldImportRootDto;
import softuni.shop.domain.dto.xml.ExportDto.productDto.ProductSoldWithCountImportRootDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UserAndProductImportDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UserSoldProductImportDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UserSoldProductImportRootDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UsersAndProductsImportRootDto;
import softuni.shop.domain.dto.xml.importDto.UsersImportRootDto;
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
    public void seedUsersJson(UserSeedDto[] userAddDtos) {
        if (this.userRepository.count() != 0) {
            return;
        }
        for (UserSeedDto userAddDto : userAddDtos) {
            validateAndSave(userAddDto);
        }

        addFriends();
    }

    @Override
    public void importUsersXml(UsersImportRootDto usersImportRootDto) {
        if (this.userRepository.count() != 0) {
            return;
        }
        for (UserSeedDto userAddDto : usersImportRootDto.getUsers()) {
            validateAndSave(userAddDto);
        }

        addFriends();
    }

    private void addFriends() {
        List<User> allUsers = this.userRepository.findAll();
        for (User user : allUsers) {
            user.setFriends(getRandomFriends());
            this.userRepository.saveAndFlush(user);
        }
    }

    private void validateAndSave(UserSeedDto userAddDto) {
        if (!this.validatorUtil.isValid(userAddDto)) {
            this.validatorUtil
                    .violations(userAddDto)
                    .forEach(v -> System.out.println(v.getMessage()));

            return;
        }

        User user = this.modelMapper.map(userAddDto, User.class);
        this.userRepository.saveAndFlush(user);
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

    @Override
    public UserSoldProductImportRootDto exportUserSoldProductXml() {
        UserSoldProductImportRootDto uspir = new UserSoldProductImportRootDto();
        List<UserSoldProductImportDto> uspi = new ArrayList<>();

        List<User> users = this.userRepository.findAllBySoldNotNullOrderByLastNameAscFirstName();
        for (User u : users) {
            UserSoldProductImportDto usp = this.modelMapper.map(u, UserSoldProductImportDto.class);

            ProductSoldImportRootDto soldProducts = new ProductSoldImportRootDto();
            List<ProductUserSoldDto> products = new ArrayList<>();

            for (Product p : u.getSold()) {
                ProductUserSoldDto product = this.modelMapper.map(p, ProductUserSoldDto.class);
                products.add(product);
            }
            soldProducts.setProducts(products);
            usp.setSoldProducts(soldProducts);
            uspi.add(usp);
        }

        uspir.setUsers(uspi);
        return uspir;
    }

    @Override
    public UsersAndProductsImportRootDto exportUserSoldProductWithCountXml() {
        UsersAndProductsImportRootDto uapir = new UsersAndProductsImportRootDto();
        List<UserAndProductImportDto> uapis = new ArrayList<>();
        List<User> users = this.userRepository
                .findAll()
                .stream()
                .filter(u -> u.getSold().stream().anyMatch(p -> p.getBuyer() == null))
                .collect(Collectors.toList());
        for (User user : users) {
            UserAndProductImportDto uapi = this.modelMapper.map(user, UserAndProductImportDto.class);
            ProductSoldWithCountImportRootDto pswcir = new ProductSoldWithCountImportRootDto();
            List<ProductSoldAttributeImportDto> psais = new ArrayList<>();
            for (Product p : user.getSold()) {
                ProductSoldAttributeImportDto product = this.modelMapper.map(p, ProductSoldAttributeImportDto.class);
                psais.add(product);
            }
            pswcir.setCount(psais.size());
            pswcir.setProducts(psais);
            uapi.setSoldProducts(pswcir);
            uapis.add(uapi);
        }
        uapir.setCount(uapis.size());
        uapir.setUsers(uapis);
        return uapir;
    }
}
