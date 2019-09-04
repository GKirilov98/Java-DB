package mostwanted.domain.dtos.race;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "races")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportRootDto implements Serializable {
    @XmlElement(name = "race")
    List<RaceImportDto> races;

    public RaceImportRootDto() {
    }

    public List<RaceImportDto> getRaces() {
        return races;
    }

    public void setRaces(List<RaceImportDto> races) {
        this.races = races;
    }
}
