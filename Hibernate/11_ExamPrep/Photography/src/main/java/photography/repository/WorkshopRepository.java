package photography.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import photography.domain.entity.Workshop;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {
}
