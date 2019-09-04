package weddingplaner.domain.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "weddings")
public class Wedding extends BaseEntity {
    private Person bride;
    private Person bridegroom;
    private LocalDate date;
    private Agency agency;
    private Set<Venue> venues;
    private Set<Invitation> invitations;

    public Wedding() {
        this.venues = new HashSet<>();
        this.invitations = new HashSet<>();
    }

    @OneToOne
    public Person getBride() {
        return bride;
    }

    public void setBride(Person bride) {
        this.bride = bride;
    }

    @OneToOne
    public Person getBridegroom() {
        return bridegroom;
    }

    public void setBridegroom(Person bridegroom) {
        this.bridegroom = bridegroom;
    }

    @Column(nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @ManyToOne()
    @JoinColumn(name = "agency_id", referencedColumnName = "id")
    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @ManyToMany
    @JoinTable( name = "wedding_venue",
            joinColumns = @JoinColumn(name = "wedding_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "venue_id", referencedColumnName = "id")
    )
    public Set<Venue> getVenues() {
        return venues;
    }

    public void setVenues(Set<Venue> venues) {
        this.venues = venues;
    }

    @OneToMany(mappedBy = "wedding")
    public Set<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Set<Invitation> invitations) {
        this.invitations = invitations;
    }
}
