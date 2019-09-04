package app.ccb.domain.dtos.bankAccount;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "bank-accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountsRootDto implements Serializable {
    @XmlElement(name = "bank-account")
    private List<BankAccountDto> bankAccountDtos;

    public BankAccountsRootDto() {
    }

    public List<BankAccountDto> getBankAccountDtos() {
        return bankAccountDtos;
    }

    public void setBankAccountDtos(List<BankAccountDto> bankAccountDtos) {
        this.bankAccountDtos = bankAccountDtos;
    }
}
