package weddingplaner.domain.entity.present;

import weddingplaner.domain.entity.BaseEntity;
import weddingplaner.domain.entity.Invitation;
import weddingplaner.domain.entity.Person;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "presents")
public abstract class Present extends BaseEntity {
    private Person owner;
    private Invitation invitation;

    public Present() {
    }

    @OneToOne
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @OneToOne(mappedBy = "present")
    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }
}
