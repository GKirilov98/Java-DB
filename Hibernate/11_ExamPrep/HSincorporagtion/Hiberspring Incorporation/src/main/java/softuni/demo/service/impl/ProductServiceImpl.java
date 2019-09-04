package softuni.demo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.demo.domain.dtos.product.ProductSeedDto;
import softuni.demo.domain.dtos.product.ProductSeedRootDto;
import softuni.demo.domain.entities.Branch;
import softuni.demo.domain.entities.Product;
import softuni.demo.repository.BranchRepository;
import softuni.demo.repository.ProductRepository;
import softuni.demo.service.ProductService;
import softuni.demo.util.ValidatorUtil;
import softuni.demo.util.XmlParser;

@Service
public class ProductServiceImpl implements ProductService {

    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;


    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public ProductServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, ProductRepository productRepository, BranchRepository branchRepository) {
        this.xmlParser = xmlParser;

        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
    }


    @Override
    public void seedProduct() {
        if (this.productRepository.count() != 0) {
            return;
        }

        final String READ_FILE_PATH = ".\\src\\main\\resources\\xml\\input\\products.xml";
        ProductSeedRootDto productSeedRootDto = this.xmlParser.objectFromFile(ProductSeedRootDto.class, READ_FILE_PATH);
        for (ProductSeedDto seedDto : productSeedRootDto.getProductList()) {
            Branch branch = this.branchRepository.findBranchByName(seedDto.getBranch());
            if (!this.validatorUtil.isValid(seedDto) || branch == null) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            try {
                Product product = this.modelMapper.map(seedDto, Product.class);
                product.setBranch(branch);
                this.productRepository.saveAndFlush(product);
                branch.getProducts().add(product);
                this.branchRepository.saveAndFlush(branch);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
