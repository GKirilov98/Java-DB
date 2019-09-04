package com.exercise.spring_intro.services;

import java.util.List;

public interface BookService  {
    void seedBooks();

    List<String> findAllTitles();
    List<String> findAllAuthors();
    List<String> findAllBooksAtAuthor();
}
