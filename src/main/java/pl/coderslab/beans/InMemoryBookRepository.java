package pl.coderslab.beans;

import org.springframework.stereotype.Repository;
import pl.coderslab.model.Book;
import pl.coderslab.model.BookRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private final Map<Long, Book> bookDatabase = new HashMap<>();

    public InMemoryBookRepository() {
        bookDatabase.putAll(Map.of(
                1L, new Book(1L, "9788324631766", "Thiniking in Java", "Bruce Eckel", "Helion", "programming"),
                2L, new Book(2L, "9788324627738", "Rusz glowa Java.", "Sierra Kathy, Bates Bert", "Helion", "programming"),
                3L, new Book(3L, "9780130819338", "Java 2. Podstawy", "Cay Horstmann, Gary Cornell", "Helion", "programming")
        ));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(bookDatabase.values());
    }

    @Override
    public Book save(Book book) {
        Long id = book.getId();
        if (id == null || bookDatabase.containsKey(id)) {
            id = bookDatabase
                    .keySet()
                    .stream()
                    .mapToLong(i -> i + 1)
                    .max()
                    .orElse(1L);
            book.setId(id);
        }
        bookDatabase.put(id, book);
        return book;
    }

    @Override
    public Optional<Book> update(Book book) {
        Long id = book.getId();
        if (id != null && bookDatabase.containsKey(id)) {
            bookDatabase.put(id, book);
            return Optional.of(book);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookDatabase.get(id));
    }

    @Override
    public boolean deleteById(Long id) {
        return bookDatabase.remove(id) != null;
    }
}
