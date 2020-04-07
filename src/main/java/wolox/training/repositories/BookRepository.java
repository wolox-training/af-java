package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import wolox.training.models.Book;

public interface BookRepository extends Repository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByPublisher(String editor);
    List<Book> findByGenre(String genre);
    List<Book> findByYear(String year);

    @Query(value = "SELECT * FROM BOOKS B WHERE B.PUBLISHER = ?1 OR B.GENRE = ?2 OR B.YEAR = ?3",
        nativeQuery = true)
    List<Book> findAllBooksWithFilter(String editor, String genre, String year);

    Book save(Book book);
    void delete(Book book);
}
