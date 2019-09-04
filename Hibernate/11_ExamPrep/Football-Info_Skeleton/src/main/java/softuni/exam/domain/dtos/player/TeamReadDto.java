package softuni.exam.domain.dtos.player;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TeamReadDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private PictureReadDto pictureReadDto;

    public TeamReadDto() {
    }

    @NotNull
    @Length(min = 3, max = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public PictureReadDto getPictureReadDto() {
        return pictureReadDto;
    }

    public void setPictureReadDto(PictureReadDto pictureReadDto) {
        this.pictureReadDto = pictureReadDto;
    }
}
