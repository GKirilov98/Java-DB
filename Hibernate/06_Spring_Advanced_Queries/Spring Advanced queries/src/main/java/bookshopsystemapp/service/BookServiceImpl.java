package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH =
            "C:\\Users\\Gogo\\Desktop\\Spring Advanced queries\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final BufferedReader reader;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        ;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));

        return books.stream().map(b -> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor().getLastName())).collect(Collectors.toSet());
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<String> getAllTitlesByAgeRestriction() {
        try {
            String input = reader.readLine().toUpperCase();
            AgeRestriction ageRestriction = AgeRestriction.valueOf(input);
            return this.bookRepository.findAllByAgeRestriction(ageRestriction)
                    .stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<String> getAllGoldenBookLessThan5000copies() {
        return this.bookRepository
                .findBooksByEditionTypeAndCopiesLessThan(EditionType.valueOf("GOLD"), 5000)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getBooksByPrice() {
        return this.bookRepository
                .findBooksByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5.00), BigDecimal.valueOf(40.00))
                .stream()
                .map(b -> String.format("%s - $%.2f", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getNotReleasedBooks() {
        try {
            int inputYear = Integer.parseInt(reader.readLine());
            return this.bookRepository
                    .findBooksByReleaseDateAfterOrReleaseDateBefore(
                            LocalDate.parse((inputYear + 1) + "-01-01"),
            LocalDate.parse((inputYear - 1) + "-12-31"))
                    .stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<String> getAllBooksReleasedBeforeDate() {
        try {
            String[] tokens = this.reader.readLine().split("-");
            String date = tokens[2] + "-" + tokens[1] + "-"  + tokens[0];
            return this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse(date))
                    .stream()
                    .map(b -> String.format("%s %s %.2f", b.getTitle(), b.getEditionType(), b.getPrice()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<String> getAllTitleBooksContainLetters() {
        try {
            String inputLetters= this.reader.readLine();
            return this.bookRepository.findBooksByTitleContaining(inputLetters)
                    .stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getAllTittlesByAuthorsStartWithLetter() {
        try {
            String inputLetters = reader.readLine() + "%";
            return this.bookRepository.findAllBooksWithAuthors(inputLetters )
                    .stream()
                    .map(b -> String.format("%s (%s %s)", b.getTitle(),
                                                        b.getAuthor().getFirstName(),
                                                         b.getAuthor().getLastName()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer countBooks() {
        try {
            int length = Integer.parseInt(this.reader.readLine());
            return this.bookRepository.findBooksByTittleLongerThan(length).size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getAllBooksByTitle() {
        try {
            String title = this.reader.readLine();
            Book b = this.bookRepository.findByTitle(title);
            if (b == null){
                return "The book is not in DB";
            }
            return String.format("%s %s %s %.2f",b.getTitle(), b.getEditionType().name(),
                                                b.getAgeRestriction().name(), b.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
