package softuni.carshop.domain.dtos.carDto;

import com.google.gson.annotations.Expose;
import softuni.carshop.domain.dtos.partDto.PartSeedDto;
import softuni.carshop.domain.entities.Part;

import java.io.Serializable;
import java.util.List;

public class CarSeedDto implements Serializable {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Long travelledDistance;
    @Expose
    private List<PartSeedDto> parts;

    public CarSeedDto() {
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
