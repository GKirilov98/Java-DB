package softuni.shop.service;

import softuni.shop.domain.dto.json.productDto.ProductInRangeDto;
import softuni.shop.domain.dto.json.productDto.ProductSeedDto;
import softuni.shop.domain.dto.xml.ExportDto.productDto.ProductInRangeImportRootDto;
import softuni.shop.domain.dto.xml.importDto.ProductsImporRootDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProduct(ProductSeedDto[] productSeedDtos);
    List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less);

    void importProductsXml(ProductsImporRootDto productsImporRootDto);

    ProductInRangeImportRootDto exportProductsInRangeXml();
}
