package app.retake.repositories;

import app.retake.domain.entity.AnimalAid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalAidRepository extends JpaRepository<AnimalAid, Integer> {
    Optional<AnimalAid> findAnimalAidByName(String name);

}
