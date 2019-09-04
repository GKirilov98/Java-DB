package softuni.shop.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.shop.domain.dto.productDto.ProductInRangeDto;
import softuni.shop.domain.dto.productDto.ProductSeedDto;
import softuni.shop.domain.entities.Category;
import softuni.shop.domain.entities.Product;
import softuni.shop.domain.entities.User;
import softuni.shop.repository.CategoryRepository;
import softuni.shop.repository.ProductRepository;
import softuni.shop.repository.UserRepository;
import softuni.shop.service.ProductService;
import softuni.shop.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProduct(ProductSeedDto[] productSeedDtos) {
        if (this.productRepository.count() != 0) {
            return;
        }

        for (ProductSeedDto productSeedDto : productSeedDtos) {

            if (!this.validatorUtil.isValid(productSeedDto)) {
                this.validatorUtil.violations(productSeedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Product product = this.modelMapper.map(productSeedDto, Product.class);
            product.setBuyer(getRandomBuyer());
            product.setSeller(getRandomSeller());
            product.setCategories(getRandomCategories());

            this.productRepository.saveAndFlush(product);
        }
    }

    private User getRandomBuyer() {
        User buyer = getRandomSeller();
        if (buyer.getId() % 3 == 0) {
            buyer = null;
        }

        return buyer;
    }

    private User getRandomSeller() {
        Random random = new Random();

        int id = random.nextInt((int) this.userRepository.count() - 1) + 1;

        return this.userRepository.getOne(id);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt((int) this.categoryRepository.count() - 1) + 1;

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<ProductInRangeDto> productsInRange(BigDecimal more, BigDecimal less) {
        List<Product> productEntities = this.productRepository.findAllByPriceBetweenAndBuyerOrderByPrice(more, less, null);
        List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();
        for (Product pe : productEntities) {
            ProductInRangeDto productInRangeDto = this.modelMapper.map(pe, ProductInRangeDto.class);
            productInRangeDto.setSeller(
                    String.format("%s %s", pe.getSeller().getFirstName(), pe.getSeller().getLastName())
            );

            productInRangeDtos.add(productInRangeDto);
        }

        return productInRangeDtos;
    }


}
