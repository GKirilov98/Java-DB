package softuni.carshop.domain.dtos.XML.exportDto.partDto;

import softuni.carshop.domain.dtos.JSON.partDto.PartViewDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartExportRootDto implements Serializable {
    @XmlElement(name = "part")
    private List<PartViewDto> parts;

    public PartExportRootDto() {
    }

    public List<PartViewDto> getParts() {
        return parts;
    }

    public void setParts(List<PartViewDto> parts) {
        this.parts = parts;
    }
}
