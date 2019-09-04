package softuni.carshop.domain.dtos.XML.exportDto.carDto;

import softuni.carshop.domain.dtos.JSON.carDto.CarToyotaDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarToyotaExportRootDto {
    @XmlElement(name = "car")
    private List<CarToyotaDto> cars;

    public CarToyotaExportRootDto() {
    }

    public List<CarToyotaDto> getCars() {
        return cars;
    }

    public void setCars(List<CarToyotaDto> cars) {
        this.cars = cars;
    }
}
