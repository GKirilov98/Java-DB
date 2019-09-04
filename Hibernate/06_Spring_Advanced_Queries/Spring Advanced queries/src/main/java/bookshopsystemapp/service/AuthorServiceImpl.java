package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final String AUTHORS_FILE_PATH = "C:\\Users\\Gogo\\Desktop\\Spring Advanced queries\\src\\main\\resources\\files\\authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;
    private final BufferedReader reader;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
        this.reader = new BufferedReader( new InputStreamReader(System.in));
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authorFileContent = this.fileUtil.getFileContent(AUTHORS_FILE_PATH);
        for (String line : authorFileContent) {
            String[] names = line.split("\\s+");

            Author author = new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public List<String> authorNameEndsWithLetter() {
        try {
            String letter = this.reader.readLine().toLowerCase();
            return this.authorRepository.findAllByFirstNameEndingWith(letter)
                    .stream()
                    .map(a -> String.format("%s %s", a.getFirstName(), a.getLastName()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getAuthorsByBookCopiesCount() {
        return this.authorRepository
                .getAuthorsByBooksCopies()
                .stream()
                .map(obj -> (String) obj)
                .collect(Collectors.toList());
    }

//    @Override
//    public Integer getAuthorBooksCount(final String firstName, final String lastName) {
//        return this.authorRepository.getAuthorBooksCount(firstName, lastName);
//    }

}
