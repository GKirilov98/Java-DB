package softuni.carshop.domain.dtos.XML.importDto;

import softuni.carshop.domain.dtos.JSON.carDto.CarSeedElemetDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportRootDto implements Serializable {
    @XmlElement(name = "car")
    private List<CarSeedElemetDto> cars;

    public CarImportRootDto() {
    }

    public List<CarSeedElemetDto> getCars() {
        return cars;
    }

    public void setCars(List<CarSeedElemetDto> cars) {
        this.cars = cars;
    }
}
