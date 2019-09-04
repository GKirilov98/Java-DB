package weddingplaner.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "venues")
public class Venue extends BaseEntity {
    private String name;
    private Integer capacity;
    private String town;
    private Set<Wedding> weddings;

    public Venue() {
        this.weddings = new HashSet<>();
    }


    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    @Min(2)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Column
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }


    @ManyToMany(mappedBy = "venues")
    public Set<Wedding> getWeddings() {
        return weddings;
    }

    public void setWeddings(Set<Wedding> weddings) {
        this.weddings = weddings;
    }
}
