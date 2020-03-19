package wolox.training.controllers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.PageAttributes.MediaType;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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
  private UserRepository mockedUserRepository;
  private Optional<User> listTestUser;
  private Optional<Book> listTestBook;
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

  @WithMockedUser
  @Test
  public void whenFindByUsernameWichExist_thenUserIsReturned() {
    Mockito.when(mockedUserRepository.findByUsername(oneTestUser.getUsername()).get()).thenReturn(oneTestUser);
    String url = "/api/users/".concat(oneTestUser.getUsername());
    mvc.perform(get(url)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json(
         "{\"id\": 1052,\"username\": \"Alito\",\"name\": \"Alex\","
             + "\"birthday\": \"1994-10-25\",\"listBooks\": [{\"id\": 1,\"genre\": \"asd\","
             + "\"author\": \"asd\", \"image\": \"asd\","
             + "\"title\": \"asd\",\"subtitle\": \"asd\",\"publisher\": \"asd\", "
             + "\"year\": \"qqqq\", \"page\": 3, \"isbn\": \"10\", \"users\": [{\"id\": 1,"
             + "\"username\": \"Alito1\",\"name\": \"Alex\",\"birthday\": \"1994-10-25\",
          "listBooks": [
        {
          "id": 1,
            "genre": "asd",
            "author": "asd",
            "image": "asd",
            "title": "asd",
            "subtitle": "asd",
            "publisher": "asd",
            "year": "qqqq",
            "page": 3,
            "isbn": "10",
            "users": [
          1,
              1052
                            ]
        }
                    ]
      },
      1052
            ]
    }
    ]
  }
             + "}"
      ));
  }

}
