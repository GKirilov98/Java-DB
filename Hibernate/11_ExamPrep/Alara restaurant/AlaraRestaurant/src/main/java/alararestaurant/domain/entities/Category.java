package alararestaurant.domain.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "categories")
public class Category extends BaseEntity {
    private String name;
    private List<Item> items;

    public Category() {
    }

    @Column(nullable = false, unique = true)
    @Length(min = 3, max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
