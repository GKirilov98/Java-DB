package softuni.demo.domain.dtos.branch;

import com.google.gson.annotations.Expose;


import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class BranchSeedDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private String town;

    public BranchSeedDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
