package softuni.carshop.domain.dtos.supplierDto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SupplierLocalDto implements Serializable {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private int partsCount;

    public SupplierLocalDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
