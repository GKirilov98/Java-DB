package softuni.shop.domain.dto.xml.importDto;

import softuni.shop.domain.dto.json.categoryDto.CategorySeedDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesImportRootDto {
    @XmlElement(name = "category")
    private List<CategorySeedDto> categories;

    public CategoriesImportRootDto() {
    }

    public List<CategorySeedDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorySeedDto> categories) {
        this.categories = categories;
    }
}
