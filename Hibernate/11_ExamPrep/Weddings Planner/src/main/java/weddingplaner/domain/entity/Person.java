package weddingplaner.domain.entity;

import org.hibernate.validator.constraints.Length;
import weddingplaner.domain.entity.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity(name = "persons")
public class Person extends BaseEntity {
    private String firstName;
    private String middleNameInitial;
    private String lastName;
    private String fullName; // not included
    private Gender gender;
    private LocalDate birthDate;
    private Integer age;
    private String phone;
    private String email;

    public Person() {
    }

    @Column(name = "first_name", nullable = false, length = 60)
    @Length(min = 1)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "middle_name_initial", nullable = false, length = 1)
    @Length(min = 1)
    public String getMiddleNameInitial() {
        return middleNameInitial;
    }

    public void setMiddleNameInitial(String middleNameInitial) {
        this.middleNameInitial = middleNameInitial;
    }

    @Column(name = "last_name", nullable = false)
    @Length(min = 2)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName() {
        return fullName = firstName + " " + middleNameInitial + " " + lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Enumerated(value = EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Transient
    public Integer getAge() {
        return age = Period.between(getBirthDate(), LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column()
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
