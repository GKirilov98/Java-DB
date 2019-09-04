package softuni.exam.domain.dtos.team;

import org.hibernate.validator.constraints.Length;
import softuni.exam.domain.dtos.picture.PicturesSeedDto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedDto implements Serializable {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "picture")
    private PicturesSeedDto picturesSeedDto;

    public TeamSeedDto() {
    }

    @NotNull
    @Length(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public PicturesSeedDto getPicturesSeedDto() {
        return picturesSeedDto;
    }

    public void setPicturesSeedDto(PicturesSeedDto picturesSeedDto) {
        this.picturesSeedDto = picturesSeedDto;
    }
}
