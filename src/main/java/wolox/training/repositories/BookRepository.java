package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import wolox.training.models.Book;

public interface BookRepository extends Repository<Book, Long> {
    Book save(Book book);
    Optional<Book> findByIsbn(String isbn);
    void delete(Book book);
}
