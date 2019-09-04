package photography.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import photography.domain.entity.Len;

@Repository
public interface LenRepository extends JpaRepository<Len, Integer> {
}
