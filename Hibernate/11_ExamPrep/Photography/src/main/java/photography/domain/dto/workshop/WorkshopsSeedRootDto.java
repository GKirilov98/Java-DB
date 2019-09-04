package photography.domain.dto.workshop;

import photography.domain.entity.Workshop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "workshops")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkshopsSeedRootDto implements Serializable {
    @XmlElement(name = "workshop")
    private List<WorkshopSeedDto> workshop;

    public WorkshopsSeedRootDto() {
    }

    public List<WorkshopSeedDto> getWorkshop() {
        return workshop;
    }

    public void setWorkshop(List<WorkshopSeedDto> workshop) {
        this.workshop = workshop;
    }
}
