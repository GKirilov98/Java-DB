package weddingplaner.domain.dto.venue;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "venue")
@XmlAccessorType(XmlAccessType.FIELD)
public class VenueSeedDto implements Serializable {
    @XmlAttribute
    private String name;
    @XmlElement
    private Integer capacity;
    @XmlElement
    private String town;

    public VenueSeedDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Min(value = 2)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @NotNull
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
