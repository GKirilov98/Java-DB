package softuni.sprin_intro2.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity{
    private String name;
    private String country;
    private Set<User> born;
    private Set<User> living;

    public Town() {
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @OneToMany(mappedBy = "bornTown")
    public Set<User> getBorn() {
        return born;
    }

    public void setBorn(Set<User> born) {
        this.born = born;
    }

    @OneToMany(mappedBy = "currentlyLiving")
    public Set<User> getLiving() {
        return living;
    }

    public void setLiving(Set<User> living) {
        this.living = living;
    }
}
