//package p05_BillsPaymentSystem;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "credit_cards")
//public class CreditCard extends BaseBillDetail {
//
//    @Column(name = "type", length = 30)
//    private String cardType;
//
//    @Column(name = "expiration_month")
//    private byte expirationMonth;
//
//    @Column(name = "expiration_year")
//    private short expirationYear;
//
//    public String getCardType() {
//        return this.cardType;
//    }
//
//    public void setCardType(String cardType) {
//        this.cardType = cardType;
//    }
//
//    public byte getExpirationMonth() {
//        return this.expirationMonth;
//    }
//
//    public void setExpirationMonth(byte expirationMonth) {
//        this.expirationMonth = expirationMonth;
//    }
//
//    public short getExpirationYear() {
//        return this.expirationYear;
//    }
//
//    public void setExpirationYear(short expirationYear) {
//        this.expirationYear = expirationYear;
//    }
//}