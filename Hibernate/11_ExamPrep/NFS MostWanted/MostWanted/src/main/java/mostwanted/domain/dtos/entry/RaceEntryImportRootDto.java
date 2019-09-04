package mostwanted.domain.dtos.entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportRootDto implements Serializable {
    @XmlElement(name = "race-entry")
    private List<RaceEntryImortDto> raceEntryImortDtos;

    public RaceEntryImportRootDto() {
    }

    public List<RaceEntryImortDto> getRaceEntryImortDtos() {
        return raceEntryImortDtos;
    }

    public void setRaceEntryImortDtos(List<RaceEntryImortDto> raceEntryImortDtos) {
        this.raceEntryImortDtos = raceEntryImortDtos;
    }
}
