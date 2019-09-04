package softuni.exam.domain.dtos.player;

import com.google.gson.annotations.Expose;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public class PictureReadDto implements Serializable {
    @Expose
    private String url;

    public PictureReadDto() {
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
