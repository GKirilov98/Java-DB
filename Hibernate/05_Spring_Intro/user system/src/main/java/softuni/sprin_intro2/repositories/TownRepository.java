package softuni.sprin_intro2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.sprin_intro2.entities.Town;


@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
}