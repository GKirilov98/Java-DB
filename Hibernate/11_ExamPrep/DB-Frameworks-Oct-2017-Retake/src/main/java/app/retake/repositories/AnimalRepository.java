package app.retake.repositories;

import app.retake.domain.entity.Animal;
import app.retake.domain.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository  extends JpaRepository<Animal, Integer> {
    Animal findAnimalByPassport(Passport passport);
}
