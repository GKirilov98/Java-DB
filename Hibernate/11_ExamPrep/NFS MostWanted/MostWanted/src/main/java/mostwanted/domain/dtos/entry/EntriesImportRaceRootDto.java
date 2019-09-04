package mostwanted.domain.dtos.entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntriesImportRaceRootDto implements Serializable {
    @XmlElement(name = "entry")
    private List<EntryImportRaceDto> entries;

    public EntriesImportRaceRootDto() {
    }

    public List<EntryImportRaceDto> getEntries() {
        return entries;
    }

    public void setEntries(List<EntryImportRaceDto> entries) {
        this.entries = entries;
    }
}
