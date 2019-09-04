package mostwanted.domain.dtos.districts;

import com.google.gson.annotations.Expose;
import mostwanted.domain.entities.Town;

import java.io.Serializable;

public class DistrictSeedDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private String townName;

    public DistrictSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return townName;
    }

    public void setTown(String town) {
        this.townName = town;
    }
}
