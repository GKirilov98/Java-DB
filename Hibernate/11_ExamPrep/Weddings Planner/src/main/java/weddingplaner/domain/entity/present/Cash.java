package weddingplaner.domain.entity.present;

import weddingplaner.domain.entity.present.Present;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "cashes")
public class Cash extends Present {
    private BigDecimal cashAmount;

    public Cash() {
    }

    @Column
    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }
}
