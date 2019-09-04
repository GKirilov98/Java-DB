package softuni.shop.domain.dto.xml.ExportDto.categoryDto;

import softuni.shop.domain.dto.json.categoryDto.CategoriesProductCountDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesByProductsImportRootDto implements Serializable {
    @XmlElement(name = "category")
    private List<CategoriesProductCountDto> categories;

    public CategoriesByProductsImportRootDto() {
    }

    public List<CategoriesProductCountDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesProductCountDto> categories) {
        this.categories = categories;
    }
}
