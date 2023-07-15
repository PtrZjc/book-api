package pl.coderslab.domain;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(Long id);
    List<Book> findAll();
    Book save(Book book);

    Optional<Book> update(Book book);
    boolean deleteById(Long id);
}