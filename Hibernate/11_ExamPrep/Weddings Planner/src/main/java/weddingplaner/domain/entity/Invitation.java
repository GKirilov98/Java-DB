package weddingplaner.domain.entity;

import weddingplaner.domain.entity.enums.Family;
import weddingplaner.domain.entity.present.Present;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "invitations")
public class Invitation extends BaseEntity {
    private Wedding wedding;
    private Person guest;
    private Present present;
    private boolean attending;
    private Family family;

    public Invitation() {
    }

    @ManyToOne
    @JoinColumn(name = "wedding_id", referencedColumnName = "id")
    public Wedding getWedding() {
        return wedding;
    }

    public void setWedding(Wedding wedding) {
        this.wedding = wedding;
    }

    @OneToOne
    @NotNull
    public Person getGuest() {
        return guest;
    }

    public void setGuest(Person guest) {
        this.guest = guest;
    }

    @OneToOne
    @JoinColumn(name = "present_id", referencedColumnName = "id")
    public Present getPresent() {
        return present;
    }

    public void setPresent(Present present) {
        this.present = present;
    }

    @Column
    public boolean isAttending() {
        return attending;
    }

    public void setAttending(boolean attending) {
        this.attending = attending;
    }

    @Enumerated(value = EnumType.STRING)
    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
}
