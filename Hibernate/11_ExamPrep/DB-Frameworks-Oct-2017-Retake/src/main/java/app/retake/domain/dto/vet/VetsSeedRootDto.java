package app.retake.domain.dto.vet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "vets")
@XmlAccessorType(XmlAccessType.FIELD)
public class VetsSeedRootDto implements Serializable {
    @XmlElement(name = "vet")
    private List<VetSeedDto> vetSeedDtos;

    public VetsSeedRootDto() {
    }

    public List<VetSeedDto> getVetSeedDtos() {
        return vetSeedDtos;
    }

    public void setVetSeedDtos(List<VetSeedDto> vetSeedDtos) {
        this.vetSeedDtos = vetSeedDtos;
    }
}
