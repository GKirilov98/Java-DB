//package p02_Sales;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "customers")
//public class Customer {
//   private int id;
//   private String name;
//   private String email;
//   private String creditCardNumber;
//   private Set<Sale> sales;
//
//   public Customer() {
//   }
//
//   @Id
//   @GeneratedValue(strategy = GenerationType.IDENTITY)
//   public int getId() {
//      return id;
//   }
//
//   public void setId(int id) {
//      this.id = id;
//   }
//
//   @Column(length = 50, nullable = false)
//   public String getName() {
//      return name;
//   }
//
//   public void setName(String name) {
//      this.name = name;
//   }
//
//   @Column(length = 50, unique = true, nullable = false)
//   public String getEmail() {
//      return email;
//   }
//
//   public void setEmail(String email) {
//      this.email = email;
//   }
//
//   @Column(name = "credit_card_number", length = 30)
//   public String getCreditCardNumber() {
//      return creditCardNumber;
//   }
//
//   public void setCreditCardNumber(String creditCardNumber) {
//      this.creditCardNumber = creditCardNumber;
//   }
//
//   @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//   public Set<Sale> getSales() {
//      return sales;
//   }
//
//   public void setSales(Set<Sale> sales) {
//      this.sales = sales;
//   }
//}
