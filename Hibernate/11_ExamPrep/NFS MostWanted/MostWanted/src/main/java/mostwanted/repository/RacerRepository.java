package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Integer> {
    Racer findRacerByName(String name);
    Set<Racer> findRacersByCarsIsNotNull();
}
