package softuni.exam.domain.dtos.picture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PicturesSeedRootDto implements Serializable {
    @XmlElement(name = "picture")
    private List<PicturesSeedDto> picturesSeedDtos;

    public PicturesSeedRootDto() {
    }

    public List<PicturesSeedDto> getPicturesSeedDtos() {
        return picturesSeedDtos;
    }

    public void setPicturesSeedDtos(List<PicturesSeedDto> picturesSeedDtos) {
        this.picturesSeedDtos = picturesSeedDtos;
    }
}
