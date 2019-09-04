package softuni.carshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.carshop.domain.entities.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

//    @Query(value = "SELECT c FROM softuni.carshop.domain.entities.Customer AS c ORDER BY c.birthDate, c.isYoungDriver")
//    List<Customer> findAllAndOrderByBirthDateAndYoungDriver();
    List<Customer> findAllByIdIsNotNullOrderByBirthDateAscYoungDriverAsc();
    List<Customer> findAllBySalesIsNotNull();
}
