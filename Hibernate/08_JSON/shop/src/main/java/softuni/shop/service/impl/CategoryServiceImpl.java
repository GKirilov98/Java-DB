package softuni.shop.service.impl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.shop.domain.dto.categoryDto.CategoriesProductCountDto;
import softuni.shop.domain.dto.categoryDto.CategoryAddDto;
import softuni.shop.domain.entities.Category;
import softuni.shop.domain.entities.Product;
import softuni.shop.repository.CategoryRepository;
import softuni.shop.service.CategoryService;
import softuni.shop.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper modelMapper;
    private final ValidatorUtil validator;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, ValidatorUtil validator, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(CategoryAddDto[] categoryAddDtos) {
        if (this.categoryRepository.count() != 0) {
            return;
        }
        for (CategoryAddDto categoryAddDto : categoryAddDtos) {
            if (!this.validator.isValid(categoryAddDto)){
                this.validator.violations(categoryAddDto)
                        .forEach( m -> System.out.println(m.getMessage()));
                continue;
            }

            Category category = this.modelMapper.map(categoryAddDto, Category.class);
            this.categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public List<CategoriesProductCountDto> categoryByProductGetCount() {

        TypeMap<Category, CategoriesProductCountDto> typeMap = this.modelMapper
                .createTypeMap(Category.class, CategoriesProductCountDto.class);
        typeMap.addMappings(m -> m.map(Category::getName, CategoriesProductCountDto::setCategory));

        List<CategoriesProductCountDto> categoriesProductCountDtos = new ArrayList<>();
        List<Category> categories = this.categoryRepository.findAll();
        for (Category c  : categories) {
            CategoriesProductCountDto cpc = this.modelMapper.map(c, CategoriesProductCountDto.class);
            int count = c.getProducts().size();
            cpc.setProductsCount(count);
            BigDecimal sum  = new BigDecimal(0.0);
            List<BigDecimal> bigDecimals = c.getProducts().stream().map(Product::getPrice).collect(Collectors.toList());
            for (BigDecimal bd : bigDecimals) {
               sum = sum.add(bd);
            }
            cpc.setTotalRevenue(sum);
            try {
                BigDecimal avPrice = sum.divide(BigDecimal.valueOf(count),2, RoundingMode.FLOOR);
                cpc.setAveragePrice(avPrice);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }


            categoriesProductCountDtos.add(cpc);
        }


        return categoriesProductCountDtos;
    }
}
