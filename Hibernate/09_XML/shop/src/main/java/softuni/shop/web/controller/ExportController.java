package softuni.shop.web.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import softuni.shop.domain.dto.json.categoryDto.CategoriesProductCountDto;
import softuni.shop.domain.dto.json.productDto.ProductInRangeDto;
import softuni.shop.domain.dto.json.userDto.UserSoldProductDto;
import softuni.shop.domain.dto.json.userDto.UsersAndProductDto;
import softuni.shop.domain.dto.xml.ExportDto.categoryDto.CategoriesByProductsImportRootDto;
import softuni.shop.domain.dto.xml.ExportDto.productDto.ProductInRangeImportRootDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UserSoldProductImportDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UserSoldProductImportRootDto;
import softuni.shop.domain.dto.xml.ExportDto.userDto.UsersAndProductsImportRootDto;
import softuni.shop.service.CategoryService;
import softuni.shop.service.ProductService;
import softuni.shop.service.UserService;
import softuni.shop.util.FileUtil;
import softuni.shop.util.XmlParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ExportController {

    private final Gson gson;
    private final FileUtil fileUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private ProductService productService;
    private final BufferedReader reader;
    private final XmlParser xmlParser;

    @Autowired
    public ExportController(Gson gson, FileUtil fileUtil, UserService userService, CategoryService categoryService, ProductService productService, BufferedReader reader, XmlParser xmlParser) {
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.reader = reader;
        this.xmlParser = xmlParser;
    }

    void start(){
//        exportQueriesToJSON();
        exportQueriesToXML();
    }

    private void exportQueriesToXML() {
        p01_ProductsInRangeXML();
        p02_SuccessfullySoldProductsXML();
        p03_CategoriesByProductsCountXML();
        p04_UsersAndProductsXML();
    }

    private void p04_UsersAndProductsXML() {
        final  String WRITE_XML_PATH = ".\\src\\main\\resources\\xml\\output\\users-and-products.xml";
        UsersAndProductsImportRootDto categories = this.userService.exportUserSoldProductWithCountXml();
        this.xmlParser.objectToFile(categories, WRITE_XML_PATH);
    }

    private void p03_CategoriesByProductsCountXML() {
        final  String WRITE_XML_PATH = ".\\src\\main\\resources\\xml\\output\\categories-by-products.xml";
        CategoriesByProductsImportRootDto categories = this.categoryService
                .ExportCategoryByProductGetCountXml();
        this.xmlParser.objectToFile(categories, WRITE_XML_PATH);
    }

    private void p02_SuccessfullySoldProductsXML() {
        final  String WRITE_XML_PATH = ".\\src\\main\\resources\\xml\\output\\users-sold-products.xml";
        UserSoldProductImportRootDto user = this.userService.exportUserSoldProductXml();
        this.xmlParser.objectToFile(user, WRITE_XML_PATH);
    }

    private void p01_ProductsInRangeXML() {
        final String WRITE_XML_PATH = ".\\src\\main\\resources\\xml\\output\\products-in-range.xml";
        ProductInRangeImportRootDto productInRangeImportRootDto = this.productService.exportProductsInRangeXml();
        this.xmlParser.objectToFile(productInRangeImportRootDto, WRITE_XML_PATH);
    }

    private void exportQueriesToJSON() {
        //Queries
        p01_ProductsInRangeJSON();
        p02_SuccessfullySoldProductsJSON();
        p03_CategoriesByProductsCountJSON();
        p04_UsersAndProductsJSON();
    }

    private void p04_UsersAndProductsJSON() {
        final String WRITE_PATH =  ".\\src\\main\\resources\\json\\output\\users-and-products.json";
        UsersAndProductDto usersAndProductsForSale = this.userService.getUsersAndProductsForSale();
        String cpcJson = this.gson.toJson(usersAndProductsForSale);
        System.out.println(this.fileUtil.fileWriter(WRITE_PATH, cpcJson));
    }

    private void p03_CategoriesByProductsCountJSON() {
        final String WRITE_PATH =  ".\\src\\main\\resources\\json\\output\\categories-by-products.json";
        List<CategoriesProductCountDto> categoriesProductCountDtos = this.categoryService.categoryByProductGetCount();
        String cpcJson = this.gson.toJson(categoriesProductCountDtos);
        System.out.println(this.fileUtil.fileWriter(WRITE_PATH, cpcJson));
    }

    private void p02_SuccessfullySoldProductsJSON() {
        final String WRITER_PATH = ".\\src\\main\\resources\\json\\output\\users-sold-products.json";
        List<UserSoldProductDto> userSoldProductDtos = this.userService.userSoldProduct();
        String uspToJSON = this.gson.toJson(userSoldProductDtos);
        System.out.println(fileUtil.fileWriter(WRITER_PATH, uspToJSON));
    }

    private void p01_ProductsInRangeJSON() {
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
}
