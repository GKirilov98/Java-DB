package app.retake.domain.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "passports")
public class Passport  {
    private String serialNumber;
    private Animal animal;
    private String ownerPhoneNumber;
    private String ownerName;
    private LocalDate registrationDate;

    public Passport() {
    }

    @Id
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @OneToOne(mappedBy = "passport")
    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Column(name = "owner_phone_number", nullable = false)
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    @Column(name = "owner_name")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Column(name = "registration_date")
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
