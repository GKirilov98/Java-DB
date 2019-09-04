package app.retake.domain.dto.procedure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "procedure")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcedureSeedDto implements Serializable {
    @XmlElement(name = "vet")
    private String vetName;
    @XmlElement(name = "animal")
    private String animalPassportNumber;
    @XmlElement(name = "animal-aids")
    private AnimalAidsReedDto animalAidsReedDto;

    public ProcedureSeedDto() {
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getAnimalPassportNumber() {
        return animalPassportNumber;
    }

    public void setAnimalPassportNumber(String animalPassportNumber) {
        this.animalPassportNumber = animalPassportNumber;
    }

    public AnimalAidsReedDto getAnimalAidsReedDto() {
        return animalAidsReedDto;
    }

    public void setAnimalAidsReedDto(AnimalAidsReedDto animalAidsReedDto) {
        this.animalAidsReedDto = animalAidsReedDto;
    }
}
