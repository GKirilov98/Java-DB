package com.exercise.spring_intro.repositories;

import com.exercise.spring_intro.entities.Author;
import com.exercise.spring_intro.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findBooksByAuthorOrderByReleaseDateDescTitleAsc(Author author);
}
