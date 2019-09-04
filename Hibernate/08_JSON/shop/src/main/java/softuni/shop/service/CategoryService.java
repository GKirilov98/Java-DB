package softuni.shop.service;

import softuni.shop.domain.dto.categoryDto.CategoriesProductCountDto;
import softuni.shop.domain.dto.categoryDto.CategoryAddDto;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryAddDto[] categoryAddDtos) ;

    List<CategoriesProductCountDto> categoryByProductGetCount();
}
