package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();

        //Queries ---------------------------->
//        p01_bookTitleByAgeRestriction();
//        p02_goldenBooksLessThen5000Copies();
//        p03_BooksByPrice();
//        p04_NotReleasedBooks();
//        p05_BooksReleasedBeforeDate();
//        p06_AuthorSearchByLetter();
//        p07_BooksSearch();
//        p08_BookTitlesSearch();
//        p09_CountBooks();
//           p10_TotalBookCopies();
//        p11_ReducedBook();
    }

    private void p11_ReducedBook() {
        System.out.println(this.bookService.getAllBooksByTitle());
    }

    private void p10_TotalBookCopies() {
        this.authorService.getAuthorsByBookCopiesCount().forEach(System.out::println);
    }

    private void p09_CountBooks() {
        System.out.println(this.bookService.countBooks());
    }

    private void p08_BookTitlesSearch() {
        this.bookService.getAllTittlesByAuthorsStartWithLetter()
                .forEach(System.out::println);
    }

    private void p07_BooksSearch() {
        this.bookService.getAllTitleBooksContainLetters()
                .forEach(System.out::println);
    }

    private void p06_AuthorSearchByLetter() {
        this.authorService.authorNameEndsWithLetter()
                .forEach(System.out::println);
    }

    private void p05_BooksReleasedBeforeDate() {
        this.bookService.getAllBooksReleasedBeforeDate()
                .forEach(System.out::println);
    }

    private void p04_NotReleasedBooks() {
        this.bookService.getNotReleasedBooks()
                .forEach(System.out::println);
    }

    private void p03_BooksByPrice() {
        this.bookService.getBooksByPrice()
                .forEach(System.out::println);
    }

    private void p02_goldenBooksLessThen5000Copies() {
        this.bookService.getAllGoldenBookLessThan5000copies()
                .forEach(System.out::println);
    }

    private void p01_bookTitleByAgeRestriction() {
         this.bookService.getAllTitlesByAgeRestriction()
         .forEach(System.out::println);
    }
}
