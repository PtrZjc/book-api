package pl.coderslab.beans;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.model.Book;
import pl.coderslab.model.BookRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {


    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Book createBook(@RequestBody Book newBook) {
        return bookRepository.save(newBook);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(() -> getBookNotFoundException(id));
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        return bookRepository.update(book)
                .orElseGet(() -> bookRepository.save(book));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        if (!bookRepository.deleteById(id)) {
            throw getBookNotFoundException(id);
        }
    }

    private static ResponseStatusException getBookNotFoundException(Long id) {
        return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Book with id %s not found", id)
        );
    }
}