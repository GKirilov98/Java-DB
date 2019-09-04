package photography.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import photography.domain.entity.Accessory;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Integer> {
}
