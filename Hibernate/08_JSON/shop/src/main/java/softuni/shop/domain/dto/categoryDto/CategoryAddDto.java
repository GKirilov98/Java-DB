package softuni.shop.domain.dto.categoryDto;

import com.google.gson.annotations.Expose;
import softuni.shop.domain.Messages;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class CategoryAddDto implements Serializable {
    @Expose
    private String name;

    public CategoryAddDto() {
    }

    @Size(min = 3, max = 15, message = Messages.NAME_CATEGORY_IS_INVALID)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
