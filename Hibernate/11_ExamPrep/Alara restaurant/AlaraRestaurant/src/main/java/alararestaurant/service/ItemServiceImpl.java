package alararestaurant.service;

import alararestaurant.domain.dtos.item.ItemSeedDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ItemServiceImpl implements ItemService {

    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemServiceImpl(FileUtil fileUtil, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper, ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean itemsAreImported() {
    return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() {
        final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\items.json";
        return this.fileUtil.fileReader(READ_PATH_FILE);
    }

    @Override
    public String importItems(String items) {
        StringBuilder errors = new StringBuilder();

        ItemSeedDto[] itemSeedDtos = this.gson.fromJson(items, ItemSeedDto[].class);
        for (ItemSeedDto seedDto : itemSeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil
                        .violations(seedDto)
                        .forEach(v -> errors.append(v.getMessage()).append(System.lineSeparator()));

                continue;
            } else if (this.itemRepository.findItemByName(seedDto.getName()) != null){
                errors.append("Dublicate\n");
                continue;
            }

            Item item = this.modelMapper.map(seedDto, Item.class);
            Category category = this.categoryRepository.findCategoryByName(seedDto.getCategory()).orElse(null);
            if (category == null){
                category = new Category();
                category.setName(seedDto.getCategory());
                category.setItems(new ArrayList<>());
            }

            item.setCategory(category);
            category.getItems().add(item);

            this.categoryRepository.saveAndFlush(category);
            this.itemRepository.saveAndFlush(item);
        }

        return errors.toString().trim();
    }
}
