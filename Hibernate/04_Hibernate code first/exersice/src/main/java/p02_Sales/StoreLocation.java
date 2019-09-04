//package p02_Sales;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "store_locations")
//public class StoreLocation {
//    private int id;
//    private String locationName;
//    private Set<Sale> sales;
//
//    public StoreLocation() {
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
//    @Column(name = "location", length = 50, nullable = false, unique = true)
//    public String getLocationName() {
//        return locationName;
//    }
//
//    public void setLocationName(String locationName) {
//        this.locationName = locationName;
//    }
//
//    @OneToMany(mappedBy = "storeLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    public Set<Sale> getSales() {
//        return sales;
//    }
//
//    public void setSales(Set<Sale> sales) {
//        this.sales = sales;
//    }
//}
