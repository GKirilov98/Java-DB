package com.exercise.spring_intro.services;



import java.util.List;

public interface AuthorService {
    void seedAuthors();

    List<String> findAllAuthorsAndSortByCountBook();
}
