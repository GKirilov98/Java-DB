package softuni.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.demo.domain.entities.Card;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    Card findCardByNumber(String number);
    List<Card> findCardsByEmployeeIsNull();
}
