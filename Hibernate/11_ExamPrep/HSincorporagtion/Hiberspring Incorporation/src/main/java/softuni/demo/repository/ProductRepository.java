package softuni.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.demo.domain.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
