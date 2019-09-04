package weddingplaner.domain.dto.wedding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import weddingplaner.domain.dto.invitation.InvitationSeedDto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


public class WeddingSeedDto implements Serializable {
    @SerializedName("Bride")
    @Expose
    private String bride;
    @SerializedName("Bridegroom")
    @Expose
    private String bridegroom;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Agency")
    @Expose
    private String agency;
    @SerializedName("Guests")
    @Expose
    private List<InvitationSeedDto> guests;

    public WeddingSeedDto() {
    }

    @NotNull
    public String getBride() {
        return bride;
    }

    public void setBride(String bride) {
        this.bride = bride;
    }

    @NotNull
    public String getBridegroom() {
        return bridegroom;
    }

    public void setBridegroom(String bridegroom) {
        this.bridegroom = bridegroom;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public List<InvitationSeedDto> getGuests() {
        return guests;
    }

    public void setGuests(List<InvitationSeedDto> guests) {
        this.guests = guests;
    }
}
