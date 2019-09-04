package app.ccb.domain.dtos.braches;

import com.google.gson.annotations.Expose;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public class BrachSeedDto implements Serializable {
    @Expose
    private String name;

    public BrachSeedDto() {
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
