package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface BookRepository extends
    JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    @Query("SELECT b FROM Book b "
        + "WHERE "
        + "(:publisher is null OR b.publisher = :publisher) AND "
        + "(:author is null OR b.author = :author) AND "
        + "(:genre is null OR b.genre = :genre) AND "
        + "(:year is null OR b.year = :year) AND "
        + "(:image is null OR b.image = :image) AND "
        + "(:title is null OR b.title = :title) AND "
        + "(:subtitle is null OR b.subtitle = :subtitle) AND "
        + "(:page is null OR b.page = :page) AND "
        + "(:isbn is null OR b.isbn = :isbn)")
    Page<Book> getAllBook(@Param("publisher") String publisher,
        @Param("author") String author,
        @Param("genre") String genre,
        @Param("year") String year,
        @Param("image") String image,
        @Param("title") String title,
        @Param("subtitle") String subtitle,
        @Param("page") String page,
        @Param("isbn") String isbn,
        Pageable pageable);
    Book save(Book book);
    void delete(Book book);
}