package alararestaurant.domain.dtos.orders.importDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemImportRootDto implements Serializable {
    @XmlElement(name = "item")
    private List<ItemImportDto> items;

    public ItemImportRootDto() {
    }

    public List<ItemImportDto> getItems() {
        return items;
    }

    public void setItems(List<ItemImportDto> items) {
        this.items = items;
    }
}
