package photography.domain.dto.workshop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


@XmlRootElement(name = "participants")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParticipantsReedRootDto implements Serializable {
    @XmlElement(name = "participant")
    private List<ParticipantReadDto> participantReadDtos;

    public ParticipantsReedRootDto() {
    }

    public List<ParticipantReadDto> getParticipantReadDtos() {
        return participantReadDtos;
    }

    public void setParticipantReadDtos(List<ParticipantReadDto> participantReadDtos) {
        this.participantReadDtos = participantReadDtos;
    }
}
