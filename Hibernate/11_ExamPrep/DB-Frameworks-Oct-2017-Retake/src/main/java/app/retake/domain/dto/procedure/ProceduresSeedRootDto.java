package app.retake.domain.dto.procedure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "procedures")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProceduresSeedRootDto implements Serializable {
    @XmlElement(name = "procedure")
    private List<ProcedureSeedDto> procedureSeedDtos;

    public ProceduresSeedRootDto() {
    }

    public List<ProcedureSeedDto> getProcedureSeedDtos() {
        return procedureSeedDtos;
    }

    public void setProcedureSeedDtos(List<ProcedureSeedDto> procedureSeedDtos) {
        this.procedureSeedDtos = procedureSeedDtos;
    }
}
