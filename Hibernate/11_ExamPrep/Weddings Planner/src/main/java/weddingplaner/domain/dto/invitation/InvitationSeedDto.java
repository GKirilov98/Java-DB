package weddingplaner.domain.dto.invitation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class InvitationSeedDto implements Serializable {
    @SerializedName("Name")
    @Expose
    private String guest;
    @SerializedName("RSVP")
    @Expose
    private Boolean attending;
    @SerializedName("Family")
    @Expose
    private String  family;

    public InvitationSeedDto() {
    }

    @NotNull
    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    @NotNull
    public Boolean getAttending() {
        return attending;
    }

    public void setAttending(Boolean attending) {
        this.attending = attending;
    }

    @NotNull
    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
