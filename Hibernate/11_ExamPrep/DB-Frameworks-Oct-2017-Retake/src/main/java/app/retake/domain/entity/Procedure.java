package app.retake.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "procedures")
public class Procedure extends BaseEntity {
    private List<AnimalAid> services;
    private Animal animal;
    @Transient
    private BigDecimal cost;
    private Vet vet;
    private LocalDate date;

    public Procedure() {
        services = new ArrayList<>();
    }

    @ManyToMany
    @JoinTable(name = "animal_aid_procedures",
        joinColumns = @JoinColumn(name = "procedure_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "animal_aid_id", referencedColumnName = "id"))
    public List<AnimalAid> getServices() {
        return services;
    }


    public void setServices(List<AnimalAid> services) {
        this.services = services;
    }

    @ManyToOne()
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @ManyToOne
    @JoinColumn(name = "vet_id", referencedColumnName = "id")
    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
