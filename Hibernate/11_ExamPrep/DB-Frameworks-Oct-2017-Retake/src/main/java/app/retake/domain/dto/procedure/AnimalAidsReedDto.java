package app.retake.domain.dto.procedure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "animal-aids")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalAidsReedDto implements Serializable {
    @XmlElement(name = "animal-aid")
    private List<AnimalAidReadDto> animalAidReadDtos;

    public AnimalAidsReedDto() {
    }

    public List<AnimalAidReadDto> getAnimalAidReadDtos() {
        return animalAidReadDtos;
    }

    public void setAnimalAidReadDtos(List<AnimalAidReadDto> animalAidReadDtos) {
        this.animalAidReadDtos = animalAidReadDtos;
    }
}
