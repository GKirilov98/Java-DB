package softuni.carshop.domain.dtos.JSON.supplierDto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedDto implements Serializable {
    @XmlAttribute(name = "name")
    @Expose
    private String name;

    @XmlAttribute(name = "is-importer")
    @Expose
    private boolean isImporter;

    public SupplierSeedDto() {
    }

    @NotNull(message = "Supplier name can not be null!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}
