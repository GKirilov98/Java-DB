package softuni.carshop.domain.dtos.XML.exportDto.carDto;

import com.google.gson.annotations.Expose;
import softuni.carshop.domain.dtos.XML.exportDto.partDto.PartExportRootDto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportDto implements Serializable {
    @XmlAttribute(name = "make")
    @Expose
    private String make;

    @XmlAttribute(name = "model")
    @Expose
    private String model;

    @XmlAttribute(name = "travelled-distance")
    @Expose
    private Long travelledDistance;

    @XmlElement(name = "parts")
    @Expose
    private PartExportRootDto parts;

    public CarExportDto() {
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

    public PartExportRootDto getParts() {
        return parts;
    }

    public void setParts(PartExportRootDto parts) {
        this.parts = parts;
    }
}
