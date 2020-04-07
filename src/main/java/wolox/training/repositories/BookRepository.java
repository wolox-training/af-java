package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import wolox.training.models.Book;

public interface BookRepository extends Repository<Book, Long> {
    //Optional<Book> findByAuthor(String author);
    //Optional<Book> findOneByAuthor(String author);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByPublisher(String editor);
    List<Book> findByGenre(String gender);
    List<Book> findByYear(String year);
    Book save(Book book);
    void delete(Book book);
}
