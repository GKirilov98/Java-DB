package bookshopsystemapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    List<String> getAllTitlesByAgeRestriction();

    List<String> getAllGoldenBookLessThan5000copies();

    List<String> getBooksByPrice();

    List<String> getNotReleasedBooks();

    List<String> getAllBooksReleasedBeforeDate();

    List<String> getAllTitleBooksContainLetters();

    List<String> getAllTittlesByAuthorsStartWithLetter();

    Integer countBooks();

    String getAllBooksByTitle();
}
