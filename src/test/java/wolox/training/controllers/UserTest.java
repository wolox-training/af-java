package wolox.training.controllers;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

public class UserTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserRepository userRepository;
  private User oneTestUser;
  private Book oneTestBook;

  @Before
  public void setUp() {
    oneTestBook = new Book ();
    oneTestBook.setAtributes("Terror", "Yo Soy El Autor", "Mi Imagen", "Mi Super Titulo", "Mi SubTitulo", "Publicador", "2020", 3, "999");
    oneTestUser = new User ();
    oneTestUser.setAtributes("Alex", "Alito", LocalDate.of(1994, 10, 25));
    oneTestUser.addBookToUser(oneTestBook);
  }

  @Test
  public void whenFindByIsbnThatNotExist_thenReturnAnError() {
    
  }

}
