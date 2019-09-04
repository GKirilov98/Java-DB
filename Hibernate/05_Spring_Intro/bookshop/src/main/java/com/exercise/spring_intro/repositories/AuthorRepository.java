package com.exercise.spring_intro.repositories;

import com.exercise.spring_intro.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findAuthorsByFirstNameAndLastName(String firstName, String lastName);
}
