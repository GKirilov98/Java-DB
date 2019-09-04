//package p02_Sales;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "sales")
//public class Sale {
//    private int id;
//    private Product product;
//    private Customer customer;
//    private StoreLocation storeLocation;
//    private Date date;
//
//    public Sale() {
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "product", referencedColumnName = "id")
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "customer", referencedColumnName = "id")
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "store_location", referencedColumnName = "id")
//    public StoreLocation getStoreLocation() {
//        return storeLocation;
//    }
//
//    public void setStoreLocation(StoreLocation storeLocation) {
//        this.storeLocation = storeLocation;
//    }
//
//    @Column(nullable = false)
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//}
//
