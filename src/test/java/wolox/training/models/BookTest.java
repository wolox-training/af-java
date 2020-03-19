package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Preconditions;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class BookTest {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookRepository bookRepository;

  @Test
  public void whenFindByIsbn_thenReturnBook() {
    // given
    Book book = new Book("Terror", "Yo Soy El Autor", "Mi Imagen", "Mi Super Titulo", "Mi SubTitulo", "Publicador", "2020", 3,"99");
    bookRepository.save(book);

    // when
    Optional<Book> bookFound = bookRepository.findByIsbn(book.getIsbn());

    // then
    assertThat(book.equal_book(bookFound.get())).isTrue();
  }

  @Test
  public void whenCreateBookWithFieldNull_thenReturnError() {
    Assertions
        .assertThrows(IllegalArgumentException.class, () -> {
          new Book("Terror", "Yo Soy El Autor", "Mi Imagen", "Mi Super Titulo", "Mi SubTitulo", "Publicador", "2020", 3,"");
    });
  }

  @Test
  public void whenFindByIsbnThatNotExist_thenReturnAnError() {
    // given
    String isbn = "900000";

    // when
    Optional<Book> bookFound = bookRepository.findByIsbn(isbn);

    // then
    assertThat(bookFound.isPresent()).isFalse();
  }
}
