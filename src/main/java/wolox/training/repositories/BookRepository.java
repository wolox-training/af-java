package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import wolox.training.models.Book;

public interface BookRepository extends Repository<Book, Long> {
    Optional<Book> findOneByAuthor(String author);
    Book save(Book book);
    Book findByIsbn(String isbn);
    void delete(Book book);
}
