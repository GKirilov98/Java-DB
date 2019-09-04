package softuni.shop.web.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.shop.domain.dto.categoryDto.CategoriesProductCountDto;
import softuni.shop.domain.dto.categoryDto.CategoryAddDto;
import softuni.shop.domain.dto.productDto.ProductInRangeDto;
import softuni.shop.domain.dto.productDto.ProductSeedDto;
import softuni.shop.domain.dto.userDto.UserSeedDto;
import softuni.shop.domain.dto.userDto.UserSoldProductDto;
import softuni.shop.domain.dto.userDto.UsersAndProductDto;
import softuni.shop.service.CategoryService;
import softuni.shop.service.ProductService;
import softuni.shop.service.UserService;
import softuni.shop.util.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {
    private final static String USER_JSON_FILE_PATH = ".\\src\\main\\resources\\json\\input\\users.json";
    private final static String CATEGORIES_JSON_FILE_PATH = ".\\src\\main\\resources\\json\\input\\categories.json";
    private final static String PRODUCTS_JSON_FILE_PATH = ".\\src\\main\\resources\\json\\input\\products.json";

    private final Gson gson;
    private final FileUtil fileUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private ProductService productService;
    private final BufferedReader reader;

    @Autowired
    public ProductShopController(Gson gson, FileUtil fileUtil, UserService userService, CategoryService categoryService, ProductService productService, BufferedReader reader) {
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.reader = reader;
    }

    @Override
    public void run(String... args) throws Exception {
        readAndAddUsers();
        readAndAddCategories();
        readAndAddProducts();
        //Queries
//        p01_ProductsInRange();
//        p02_SuccessfullySoldProducts();
//        p03_CategoriesByProductsCount();
//        p04_UsersAndProducts();
    }

    private void p04_UsersAndProducts() {
        final String WRITE_PATH =  ".\\src\\main\\resources\\json\\output\\users-and-products.json";
        UsersAndProductDto usersAndProductsForSale = this.userService.getUsersAndProductsForSale();
        String cpcJson = this.gson.toJson(usersAndProductsForSale);
        System.out.println(this.fileUtil.fileWriter(WRITE_PATH, cpcJson));
    }

    private void p03_CategoriesByProductsCount() {
        final String WRITE_PATH =  ".\\src\\main\\resources\\json\\output\\categories-by-products.json";
        List<CategoriesProductCountDto> categoriesProductCountDtos = this.categoryService.categoryByProductGetCount();
        String cpcJson = this.gson.toJson(categoriesProductCountDtos);
        System.out.println(this.fileUtil.fileWriter(WRITE_PATH, cpcJson));
    }

    private void p02_SuccessfullySoldProducts() {
        final String WRITER_PATH = ".\\src\\main\\resources\\json\\output\\users-sold-products.json";
        List<UserSoldProductDto> userSoldProductDtos = this.userService.userSoldProduct();
        String uspToJSON = this.gson.toJson(userSoldProductDtos);
        System.out.println(fileUtil.fileWriter(WRITER_PATH, uspToJSON));
    }

    private void p01_ProductsInRange() {
        final String WRITER_PATH = ".\\src\\main\\resources\\json\\output\\products-in-range.json";
        try {
            BigDecimal more = new BigDecimal(reader.readLine());
            BigDecimal less = new BigDecimal(reader.readLine());
            List<ProductInRangeDto> productInRangeDtos = this.productService.productsInRange(more, less);
            String pirJson = this.gson.toJson(productInRangeDtos);
            System.out.println(fileUtil.fileWriter(WRITER_PATH, pirJson));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readAndAddProducts() {
        String content = this.fileUtil.fileContent(PRODUCTS_JSON_FILE_PATH);
        ProductSeedDto[] productSeedDtos = this.gson.fromJson(content, ProductSeedDto[].class);
        this.productService.seedProduct(productSeedDtos);

    }

    private void readAndAddCategories() {
        String content = this.fileUtil.fileContent(CATEGORIES_JSON_FILE_PATH);
        CategoryAddDto[] categoryAddDtos = this.gson.fromJson(content, CategoryAddDto[].class);
        this.categoryService.addCategory(categoryAddDtos);
    }

    private void readAndAddUsers() {
        String content = this.fileUtil.fileContent(USER_JSON_FILE_PATH);
        UserSeedDto[] userSeedDtos = this.gson.fromJson(content, UserSeedDto[].class);
        this.userService.addUsers(userSeedDtos);
    }
}
