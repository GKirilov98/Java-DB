package photography.domain.entity.camera;

import photography.domain.entity.BaseEntity;
import photography.domain.entity.Photographer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "cameras")
public class Camera extends BaseEntity {
    private String make;
    private String model;
    private Boolean isFullFrame;
    private Integer minISO;
    private Integer maxISO;
    private Set<Photographer> primeryPhotoCameras;
    private Set<Photographer> secondaryPhotoCameras;

    public Camera() {
        this.primeryPhotoCameras = new HashSet<>();
        this.secondaryPhotoCameras = new HashSet<>();
    }

    @Column(nullable = false)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "is_full_frame")
    public Boolean getFullFrame() {
        return isFullFrame;
    }

    public void setFullFrame(Boolean fullFrame) {
        isFullFrame = fullFrame;
    }

    @Column(name = "min_iso", nullable = false)
    @Min(100)
    public Integer getMinISO() {
        return minISO;
    }

    public void setMinISO(Integer minISO) {
        this.minISO = minISO;
    }

    @Column(name = "max_iso")
    public Integer getMaxISO() {
        return maxISO;
    }

    public void setMaxISO(Integer maxISO) {
        this.maxISO = maxISO;
    }

    @OneToMany(mappedBy = "primaryCamera")
    public Set<Photographer> getPrimeryPhotoCameras() {
        return primeryPhotoCameras;
    }

    public void setPrimeryPhotoCameras(Set<Photographer> primeryPhotoCameras) {
        this.primeryPhotoCameras = primeryPhotoCameras;
    }

    @OneToMany(mappedBy = "secondaryCamera")
    public Set<Photographer> getSecondaryPhotoCameras() {
        return secondaryPhotoCameras;
    }

    public void setSecondaryPhotoCameras(Set<Photographer> secondaryPhotoCameras) {
        this.secondaryPhotoCameras = secondaryPhotoCameras;
    }
}
