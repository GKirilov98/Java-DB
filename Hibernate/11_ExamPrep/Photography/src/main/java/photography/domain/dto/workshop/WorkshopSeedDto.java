package photography.domain.dto.workshop;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "workshop")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkshopSeedDto implements Serializable {
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "start-date")
    private String startDate;
    @XmlAttribute(name = "end-date")
    private String  endDate;
    @XmlAttribute
    private String location;
    @XmlAttribute(name = "price")
    private BigDecimal pricePerParticipant;
    @XmlElement(name = "trainer")
    private String trainer;
    @XmlElement(name = "participants")
    private ParticipantsReedRootDto participants;

    public WorkshopSeedDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @NotNull
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NotNull
    public BigDecimal getPricePerParticipant() {
        return pricePerParticipant;
    }

    public void setPricePerParticipant(BigDecimal pricePerParticipant) {
        this.pricePerParticipant = pricePerParticipant;
    }

    @NotNull
    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public ParticipantsReedRootDto getParticipants() {
        return participants;
    }

    public void setParticipants(ParticipantsReedRootDto participants) {
        this.participants = participants;
    }
}

