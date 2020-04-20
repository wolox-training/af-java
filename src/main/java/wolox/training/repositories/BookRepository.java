package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface BookRepository extends Repository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByPublisher(String editor);
    List<Book> findByGenre(String genre);
    List<Book> findByYear(String year);

    @Query("SELECT b FROM Book b "
        + "WHERE "
        + "(:publisher is null OR b.publisher = :publisher) AND "
        + "(:genre is null OR b.genre = :genre) AND "
        + "(:year is null OR b.year = :year)")
    List<Book> getAllBook(@Param("publisher") String publisher, @Param("genre") String genre, @Param("year") String year);

    Book save(Book book);
    void delete(Book book);
}
