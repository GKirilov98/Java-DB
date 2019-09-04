package softuni.shop.domain.dto.json.categoryDto;

import com.google.gson.annotations.Expose;
import softuni.shop.domain.Messages;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorySeedDto implements Serializable {
    @XmlElement
    @Expose
    private String name;

    public CategorySeedDto() {
    }

    @Size(min = 3, max = 15, message = Messages.NAME_CATEGORY_IS_INVALID)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
