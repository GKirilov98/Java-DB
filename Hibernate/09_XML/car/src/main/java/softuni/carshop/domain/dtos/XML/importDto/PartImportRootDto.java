package softuni.carshop.domain.dtos.XML.importDto;

import softuni.carshop.domain.dtos.JSON.partDto.PartSeedDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartImportRootDto implements Serializable {
    @XmlElement(name = "part")
    private List<PartSeedDto> part;

    public PartImportRootDto() {
    }

    public List<PartSeedDto> getPart() {
        return part;
    }

    public void setPart(List<PartSeedDto> part) {
        this.part = part;
    }
}
