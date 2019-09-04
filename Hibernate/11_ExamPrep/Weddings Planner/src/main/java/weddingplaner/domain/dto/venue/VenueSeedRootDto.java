package weddingplaner.domain.dto.venue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "venues")
@XmlAccessorType(XmlAccessType.FIELD)
public class VenueSeedRootDto implements Serializable {
    @XmlElement(name = "venue")
    private List<VenueSeedDto> venues;

    public VenueSeedRootDto() {
    }

    public List<VenueSeedDto> getVenues() {
        return venues;
    }

    public void setVenues(List<VenueSeedDto> venues) {
        this.venues = venues;
    }
}
