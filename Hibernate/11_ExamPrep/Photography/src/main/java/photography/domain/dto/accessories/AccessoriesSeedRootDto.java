package photography.domain.dto.accessories;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "accessories")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccessoriesSeedRootDto implements Serializable {
    @XmlElement(name = "accessory")
    private List<AccessorySeedDto> accessory;

    public AccessoriesSeedRootDto() {
    }

    public List<AccessorySeedDto> getAccessory() {
        return accessory;
    }

    public void setAccessory(List<AccessorySeedDto> accessory) {
        this.accessory = accessory;
    }
}
