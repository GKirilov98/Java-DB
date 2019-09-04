package weddingplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weddingplaner.domain.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonByFirstNameAndMiddleNameInitialAndLastName(String firstName, String middle, String lastName);
}
