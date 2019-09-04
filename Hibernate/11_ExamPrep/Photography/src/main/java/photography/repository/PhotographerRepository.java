package photography.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import photography.domain.entity.Photographer;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Integer> {
    Photographer findPhotographerByFirstNameAndLastName(String firstName, String lastName);
}
