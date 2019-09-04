package softuni.carshop.domain.dtos.JSON.carDto;

import com.google.gson.annotations.Expose;
import com.sun.xml.txw2.annotation.XmlValue;
import softuni.carshop.domain.dtos.JSON.partDto.PartSeedDto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedElemetDto implements Serializable {
    @XmlElement(name = "make")
    @Expose
    private String make;

    @XmlElement(name = "model")
    @Expose
    private String model;

    @XmlElement(name = "travelled-distance")
    @Expose
    private Long travelledDistance;

    @Expose
    private List<PartSeedDto> parts;

    public CarSeedElemetDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartSeedDto> getParts() {
        return parts;
    }

    public void setParts(List<PartSeedDto> parts) {
        this.parts = parts;
    }
}
