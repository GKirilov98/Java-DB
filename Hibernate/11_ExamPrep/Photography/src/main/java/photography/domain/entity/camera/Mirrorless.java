package photography.domain.entity.camera;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Mirrorless extends Camera {
    private String maxVideoResolution;
    private Integer maxFrameRate;

    public Mirrorless() {
    }

    @Column(name = "max_video_resolution")
    public String getMaxVideoResolution() {
        return maxVideoResolution;
    }

    public void setMaxVideoResolution(String maxVideoResolution) {
        this.maxVideoResolution = maxVideoResolution;
    }

    @Column(name = "max_frame_rate")
    public Integer getMaxFrameRate() {
        return maxFrameRate;
    }

    public void setMaxFrameRate(Integer maxFrameRate) {
        this.maxFrameRate = maxFrameRate;
    }
}
