package softuni.shop.service;

import softuni.shop.domain.dto.json.categoryDto.CategoriesProductCountDto;
import softuni.shop.domain.dto.json.categoryDto.CategorySeedDto;
import softuni.shop.domain.dto.xml.ExportDto.categoryDto.CategoriesByProductsImportRootDto;
import softuni.shop.domain.dto.xml.importDto.CategoriesImportRootDto;

import java.util.List;

public interface CategoryService {
    void seedCategory(CategorySeedDto[] categoryAddDtos) ;

    List<CategoriesProductCountDto> categoryByProductGetCount();

    void importCategoriesXml(CategoriesImportRootDto categoriesImportRootDto);

    CategoriesByProductsImportRootDto ExportCategoryByProductGetCountXml();
}
