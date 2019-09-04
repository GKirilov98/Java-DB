package app.ccb.domain.dtos.card;

import org.springframework.data.annotation.AccessType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "card")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardImportDto implements Serializable {
    @XmlAttribute(name = "status")
    private String cardStatus;
    @XmlAttribute(name = "account-number")
    private String accountNumber;
    @XmlElement(name = "card-number")
    private String cardNumber;

    public CardImportDto() {
    }

    @NotNull
    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @NotNull
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
