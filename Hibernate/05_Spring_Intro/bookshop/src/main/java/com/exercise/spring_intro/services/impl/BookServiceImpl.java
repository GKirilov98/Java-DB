package com.exercise.spring_intro.services.impl;

import com.exercise.spring_intro.entities.Author;
import com.exercise.spring_intro.entities.Book;
import com.exercise.spring_intro.entities.Category;
import com.exercise.spring_intro.entities.enums.AgeRestriction;
import com.exercise.spring_intro.entities.enums.EditionType;
import com.exercise.spring_intro.repositories.AuthorRepository;
import com.exercise.spring_intro.repositories.BookRepository;
import com.exercise.spring_intro.repositories.CategoryRepository;
import com.exercise.spring_intro.services.BookService;
import com.exercise.spring_intro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final String BOOKS_FILE_PATH  = "src\\main\\resources\\files\\books.txt";
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks() {
        if (this.bookRepository.count() != 0){
            return;
        }

        String[] books = this.fileUtil.fileContent(BOOKS_FILE_PATH);
        for (String s : books) {
            String[] params = s.split("\\s+");
            Book book = new Book();
            book.setAuthor(getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate =  LocalDate.parse(params[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            book.setCopies(Integer.parseInt(params[2]));
            book.setPrice(new BigDecimal(params[3]));

            book.setAgeRestriction(AgeRestriction.values()[Integer.parseInt(params[4])]);

            book.setTitle(String.join(" ", Arrays.copyOfRange(params, 5, params.length)));

            book.setCategories(getRandomCategories());

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> findAllTitles() {
        LocalDate releaseDate = LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy"));
       return this.bookRepository
                .findAllByReleaseDateAfter(releaseDate)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthors() {
        LocalDate releaseDate = LocalDate.parse("1/1/1990", DateTimeFormatter.ofPattern("d/M/yyyy"));
       return this.bookRepository.findAllByReleaseDateBefore(releaseDate)
            .stream()
            .map( b -> String.format("%s %s", b.getAuthor().getFirstName(),
                    b.getAuthor().getLastName()))
            .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksAtAuthor() {
        Author author = this.authorRepository.findAuthorsByFirstNameAndLastName("George", "Powell");
        return this.bookRepository
                .findBooksByAuthorOrderByReleaseDateDescTitleAsc(author)
                .stream()
                .map(b -> String
                        .format("Tittle: %s  Release date: %s => %d copies of book",
                                b.getTitle(), b.getReleaseDate(), b.getCopies()
                                ))
                .collect(Collectors.toList());
    }


    private Author getRandomAuthor() {
        long count = this.authorRepository.count();
        if (count < 1L) {
            throw new RuntimeException("No authors in database");
        }

        List<Author> authors = this.authorRepository.findAll();
        Random random = new Random();
        int index = (int) ((random.nextLong() & Long.MAX_VALUE) % count);

        return authors.get(index);
    }

    private Set<Category> getRandomCategories() {
        long count = this.categoryRepository.count();
        if (count < 1L) {
            throw new RuntimeException("No categories in database");
        }

        List<Category> categories = this.categoryRepository.findAll();
        Random random = new Random();
        Set<Category> result = new HashSet<>();

        while (random.nextInt(2) > 0) {
            int index = (int) ((random.nextLong() & Long.MAX_VALUE) % count);
            result.add(categories.get(index));
        }

        return result;
    }
}
