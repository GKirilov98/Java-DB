package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findBooksByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findBooksByPriceLessThanOrPriceGreaterThan(BigDecimal less, BigDecimal greater);

    List<Book> findBooksByReleaseDateAfterOrReleaseDateBefore(LocalDate date, LocalDate date2);

    List<Book> findBooksByTitleContaining(String letters);


    @Query(value = "SELECT b FROM bookshopsystemapp.domain.entities.Book b " +
            "JOIN b.author a WHERE a.lastName LIKE :letters")
    List<Book> findAllBooksWithAuthors(@Param(value = "letters") String letters);


    @Query(value = "SELECT b FROM bookshopsystemapp.domain.entities.Book b " +
            "WHERE length(b.title) > :lengthTitle")
    List<Book> findBooksByTittleLongerThan(@Param(value = "lengthTitle") int lengthTitle);

    Book findByTitle(String title);
}
