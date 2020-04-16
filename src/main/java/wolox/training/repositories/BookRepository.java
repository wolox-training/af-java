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
        + "(:genre is null OR b.author = :author) AND "
        + "(:genre is null OR b.genre = :genre) AND "
        + "(:genre is null OR b.year = :year) AND "
        + "(:genre is null OR b.image = :image) AND "
        + "(:genre is null OR b.title = :title) AND "
        + "(:genre is null OR b.subtitle = :subtitle) AND "
        + "(:genre is null OR b.page = :page) AND "
        + "(:year is null OR b.isbn = :isbn)")
    List<Book> getAllBook(@Param("publisher") String publisher, @Param("author") String author, @Param("genre") String genre, @Param("year") String year,
        @Param("image") String image, @Param("title") String title, @Param("subtitle") String subtitle, @Param("page") String page, @Param("isbn") String isbn);
    Book save(Book book);
    void delete(Book book);
}
