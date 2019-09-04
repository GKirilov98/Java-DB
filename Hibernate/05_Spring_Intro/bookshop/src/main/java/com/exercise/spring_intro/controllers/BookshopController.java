package com.exercise.spring_intro.controllers;

import com.exercise.spring_intro.services.AuthorService;
import com.exercise.spring_intro.services.BookService;
import com.exercise.spring_intro.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

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
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();

        //Queries
        p01_booksAfter2000();
        p02_authorsReleaseDateBefore1990();
        p03_getAllAuthorsByCountsOfBook();
        p04_getAllBooksOfGeorge();

    }

    private void p04_getAllBooksOfGeorge() {
        List<String> titles = this.bookService.findAllBooksAtAuthor();
        titles.forEach(System.out::println);
    }

    private void p03_getAllAuthorsByCountsOfBook() {
        List<String> authors = this.authorService.findAllAuthorsAndSortByCountBook();
        authors.forEach(System.out::println);
    }

    private void p02_authorsReleaseDateBefore1990() {
        List<String> authors = this.bookService.findAllAuthors();
        authors.forEach(System.out::println);
    }

    private void p01_booksAfter2000() {
        List<String> titles = this.bookService.findAllTitles();
        titles.forEach(System.out::println);
    }
}
