package photography.domain.entity;

import photography.domain.entity.camera.Camera;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "photographers")
public class Photographer extends BaseEntity {
    private String firstName;
    private String lastName;
    private String phone;
    private Camera primaryCamera;
    private Camera secondaryCamera;
    private List<Len> lens;
    private List<Accessory> accessories;
    private Set<Workshop> workshopsTrainer;
    private Set<Workshop> workshopsParticipant;

    public Photographer() {
        this.lens = new ArrayList<>();
        this.accessories = new ArrayList<>();
        this.workshopsTrainer = new HashSet<>();
        this.workshopsParticipant = new HashSet<>();
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToOne()
    @JoinColumn(name = "primary_camera", nullable = false)
    public Camera getPrimaryCamera() {
        return primaryCamera;
    }

    public void setPrimaryCamera(Camera primaryCamera) {
        this.primaryCamera = primaryCamera;
    }

    @ManyToOne()
    @JoinColumn(name = "secondary_camera", nullable = false)
    public Camera getSecondaryCamera() {
        return secondaryCamera;
    }

    public void setSecondaryCamera(Camera secondaryCamera) {
        this.secondaryCamera = secondaryCamera;
    }

    @OneToMany(mappedBy = "owner")
    public List<Len> getLens() {
        return lens;
    }

    public void setLens(List<Len> lens) {
        this.lens = lens;
    }
    @OneToMany(mappedBy = "owner")
    public List<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }

    @OneToMany(mappedBy = "trainer")
    public Set<Workshop> getWorkshopsTrainer() {
        return workshopsTrainer;
    }

    public void setWorkshopsTrainer(Set<Workshop> workshopsTrainer) {
        this.workshopsTrainer = workshopsTrainer;
    }

    @ManyToMany(mappedBy = "participants")
    public Set<Workshop> getWorkshopsParticipant() {
        return workshopsParticipant;
    }

    public void setWorkshopsParticipant(Set<Workshop> workshopsParticipant) {
        this.workshopsParticipant = workshopsParticipant;
    }
}
