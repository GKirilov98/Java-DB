package softuni.carshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.carshop.domain.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);

    @Query("" +
            "SELECT c FROM softuni.carshop.domain.entities.Car AS c WHERE c.make like 'Toyota'" +
            "ORDER BY c.model ASC, c.travelledDistance DESC")
    List<Car> toyotaCars();
}
