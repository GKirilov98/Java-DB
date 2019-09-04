package com.exercise.spring_intro.services.impl;

import com.exercise.spring_intro.entities.Author;
import com.exercise.spring_intro.repositories.AuthorRepository;
import com.exercise.spring_intro.services.AuthorService;
import com.exercise.spring_intro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final static String AUTHOR_FILE_PATH =
        "src\\main\\resources\\files\\authors.txt";
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() {
        if (this.authorRepository.count() != 0){
            return;
        }

        String[] authors = this.fileUtil.fileContent(AUTHOR_FILE_PATH);

        for (String s : authors) {
            String[] tokens = s.split("\\s+");
            Author author = new Author();
            author.setFirstName(tokens[0]);
            author.setLastName(tokens[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public List<String> findAllAuthorsAndSortByCountBook() {
        return this.authorRepository
                .findAll()
               .stream()
               .sorted((a1, a2) -> Integer.compare(a2.getBooks().size(), a1.getBooks().size()))
               .map(author -> String.format("%s %s - %d books",
                      author.getFirstName(), author.getLastName(), author.getBooks().size()))
                .collect(Collectors.toList());
    }
}
