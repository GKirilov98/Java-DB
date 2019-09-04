package photography.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.text.DecimalFormat;

@Entity(name = "lens")
public class Len extends BaseEntity {
    private String make;
    private Integer focalLength;
    private Double maxAperture;
    private String  compatibleWith;
    private Photographer owner;

    public Len() {
    }

    @Column
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(name = "focal_length")
    public Integer getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(Integer focalLength) {
        this.focalLength = focalLength;
    }

    @Column(name = "max_aperture")
    public Double getMaxAperture() {
        return  Double.parseDouble(new DecimalFormat("##.#").format(maxAperture));
    }

    public void setMaxAperture(Double maxAperture) {
        this.maxAperture = maxAperture;
    }

    @Column(name = "compatible_with")
    public String getCompatibleWith() {
        return compatibleWith;
    }

    public void setCompatibleWith(String compatibleWith) {
        this.compatibleWith = compatibleWith;
    }

    @ManyToOne
    @JoinColumn(name = "owner_photographer_id", referencedColumnName = "id")
    public Photographer getOwner() {
        return owner;
    }

    public void setOwner(Photographer owner) {
        this.owner = owner;
    }
}
