package photography.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "workshops")
public class Workshop extends BaseEntity {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private BigDecimal pricePerParticipant;
    private Photographer trainer;
    private Set<Photographer> participants;

    public Workshop() {
        this.participants = new HashSet<>();
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "start_date")
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Column
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "price_per_participant")
    public BigDecimal getPricePerParticipant() {
        return pricePerParticipant;
    }

    public void setPricePerParticipant(BigDecimal pricePerParticipant) {
        this.pricePerParticipant = pricePerParticipant;
    }

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    public Photographer getTrainer() {
        return trainer;
    }

    public void setTrainer(Photographer trainer) {
        this.trainer = trainer;
    }

    @ManyToMany
    @JoinTable(name = "workshop_participants",
            joinColumns = @JoinColumn(name = "workshop_id", referencedColumnName = "id") ,
            inverseJoinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "id")
    )

    public Set<Photographer> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Photographer> participants) {
        this.participants = participants;
    }
}
