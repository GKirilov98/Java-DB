package softuni.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.demo.domain.entities.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {
    Town findTownByName(java.lang.String name);
}
