package photography.domain.entity.camera;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DSLR extends Camera {
    private Integer maxShutterSpeed;

    public DSLR() {
    }

    @Column(name = "max_shutter_speed")
    public Integer getMaxShutterSpeed() {
        return maxShutterSpeed;
    }

    public void setMaxShutterSpeed(Integer maxShutterSpeed) {
        this.maxShutterSpeed = maxShutterSpeed;
    }
}
