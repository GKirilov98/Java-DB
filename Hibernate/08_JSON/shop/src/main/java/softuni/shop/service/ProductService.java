package softuni.shop.service;

import softuni.shop.domain.dto.productDto.ProductInRangeDto;
import softuni.shop.domain.dto.productDto.ProductSeedDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProduct(ProductSeedDto[] productSeedDtos);
    List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less);
}
