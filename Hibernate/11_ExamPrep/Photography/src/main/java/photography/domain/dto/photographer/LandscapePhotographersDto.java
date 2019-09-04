package photography.domain.dto.photographer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LandscapePhotographersDto implements Serializable {
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("CameraMake")
    @Expose
    private String primaryCameraMake;
    @SerializedName("LensesCount")
    @Expose
    private Integer lensesCount;

    public LandscapePhotographersDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrimaryCameraMake() {
        return primaryCameraMake;
    }

    public void setPrimaryCameraMake(String primaryCameraMake) {
        this.primaryCameraMake = primaryCameraMake;
    }

    public Integer getLensesCount() {
        return lensesCount;
    }

    public void setLensesCount(int lensesCount) {
        this.lensesCount = lensesCount;
    }
}
