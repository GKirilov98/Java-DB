package softuni.shop.web.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import softuni.shop.domain.dto.json.categoryDto.CategorySeedDto;
import softuni.shop.domain.dto.json.productDto.ProductSeedDto;
import softuni.shop.domain.dto.json.userDto.UserSeedDto;
import softuni.shop.domain.dto.xml.importDto.CategoriesImportRootDto;
import softuni.shop.domain.dto.xml.importDto.ProductsImporRootDto;
import softuni.shop.domain.dto.xml.importDto.UsersImportRootDto;
import softuni.shop.service.CategoryService;
import softuni.shop.service.ProductService;
import softuni.shop.service.UserService;
import softuni.shop.util.FileUtil;
import softuni.shop.util.XmlParser;

@Controller
public class ImportController {


    private final FileUtil fileUtil;
    private final Gson gson;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final XmlParser xmlParser;

    @Autowired
    public ImportController(FileUtil fileUtil, Gson gson, ProductService productService, CategoryService categoryService, UserService userService, XmlParser xmlParser) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.xmlParser = xmlParser;
    }

    void start(){
//        importFromJSONFiles();
        importFromXMLFiles();
    }

    private void importFromXMLFiles() {
        importUsersXML();
        importCategoriesXML();
        importProductsXML();
    }

    private void importProductsXML() {
        final String READ_XML_PATH = ".\\src\\main\\resources\\xml\\input\\products.xml";
        ProductsImporRootDto productsImporRootDto = this.xmlParser.objectFromFile(ProductsImporRootDto.class, READ_XML_PATH);
        this.productService.importProductsXml(productsImporRootDto);

    }

    private void importCategoriesXML() {
        final String READ_XML_PATH = ".\\src\\main\\resources\\xml\\input\\categories.xml";
        CategoriesImportRootDto categoriesImportRootDto = this.xmlParser.objectFromFile(CategoriesImportRootDto.class, READ_XML_PATH);
        this.categoryService.importCategoriesXml(categoriesImportRootDto);
    }

    private void importUsersXML() {
        final String READ_XML_PATH = ".\\src\\main\\resources\\xml\\input\\users.xml";
        UsersImportRootDto usersImportRootDto = this.xmlParser.objectFromFile(UsersImportRootDto.class, READ_XML_PATH);
        this.userService.importUsersXml(usersImportRootDto);
    }

    private void importFromJSONFiles() {
        seedUsersJSON();
        seedCategoriesJSON();
        seedProductsJSON();
    }

    private void seedProductsJSON() {
        final String PRODUCTS_JSON_FILE_PATH = ".\\src\\main\\resources\\json\\input\\products.json";

        String content = this.fileUtil.fileReader(PRODUCTS_JSON_FILE_PATH);
        ProductSeedDto[] productSeedDtos = this.gson.fromJson(content, ProductSeedDto[].class);
        this.productService.seedProduct(productSeedDtos);

    }

    private void seedCategoriesJSON() {
        final String CATEGORIES_JSON_FILE_PATH = ".\\src\\main\\resources\\json\\input\\categories.json";

        String content = this.fileUtil.fileReader(CATEGORIES_JSON_FILE_PATH);
        CategorySeedDto[] categoryAddDtos = this.gson.fromJson(content, CategorySeedDto[].class);
        this.categoryService.seedCategory(categoryAddDtos);
    }

    private void seedUsersJSON() {
        final String USER_JSON_FILE_PATH = ".\\src\\main\\resources\\json\\input\\users.json";


        String content = this.fileUtil.fileReader(USER_JSON_FILE_PATH);
        UserSeedDto[] userSeedDtos = this.gson.fromJson(content, UserSeedDto[].class);
        this.userService.seedUsersJson(userSeedDtos);
    }
}
