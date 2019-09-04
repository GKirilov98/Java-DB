package softuni.carshop.domain.entities;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity(name = "sales")
public class Sale extends BaseEntity {
    private Double discount;
    private Car carId;
    private Customer customerId;

    public Sale() {
    }

    @Column
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (this.customerId.isYoungDriver()){
            discount += 0.05;
        }
        this.discount = Double.parseDouble(df.format(discount));
    }

    @OneToOne
    @JoinColumn(name = "car_id")
    public Car getCarId() {
        return carId;
    }

    public void setCarId(Car carId) {
        this.carId = carId;
    }

    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
}
