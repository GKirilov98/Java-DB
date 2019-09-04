package alararestaurant.service;

import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {

       return this.categoryRepository.findAll()
                .stream()
                .sorted((a, b) -> {
                    if (b.getItems().size() - a.getItems().size() == 0) {
                        BigDecimal sumB = new BigDecimal(0);
                        for (Item e : b.getItems()) {
                            sumB = sumB.add(e.getPrice());
                        }

                        BigDecimal sumA = new BigDecimal(0);
                        for (Item e : a.getItems()) {
                            sumA = sumA.add(e.getPrice());
                        }



                        return Integer.parseInt(sumB.subtract(sumA).setScale(0, RoundingMode.FLOOR).toString());
                    } else {
                        return b.getItems().size() - a.getItems().size();
                    }
                })
                .map(c -> String.format("Category: %s\n%s\n", c.getName(),
                        c.getItems()
                                .stream()
                                .map(i -> String.format("--- Item Name: %s\n" + "--- Item Price: %.2f\n",
                                        i.getName(), i.getPrice()))
                                .collect(Collectors.joining("")).trim())
                ).collect(Collectors.joining(""));

    }
}
