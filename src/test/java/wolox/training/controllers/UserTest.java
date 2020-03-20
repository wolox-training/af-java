package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest(UserController.class)
public class UserTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserRepository mockedUserRepository;
  private User oneTestUser;
  private Book oneTestBook;

  @Before
  public void setUp() {
    oneTestBook = new Book ("Terror", "Yo Soy El Autor", "Mi Imagen", "Mi Super Titulo", "Mi SubTitulo", "Publicador", "2020", 3, "999");
    oneTestUser = new User ("Alex", "Alito", LocalDate
        .of(1994, 10, 25));
    oneTestUser.addBookToUser(oneTestBook);
  }

  @Test
  public void whenFindByUsernameWichExist_thenUserIsReturned() throws Exception{
//    Mockito
//        .when(mockedUserRepository.findByUsername(oneTestUser.getUsername()).get()).thenReturn(oneTestUser);
    Mockito.when(mockedUserRepository.findByUsername(oneTestUser.getUsername()))
        .thenReturn(
            Optional
                .of(oneTestUser));
    String url = "/api/users/".concat(oneTestUser.getUsername());
    mvc.perform(get(url)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json(
         "{\"id\": 1052,\"username\": \"Alito\",\"name\": \"Alex\","
             + "\"birthday\": \"1994-10-25\",\"listBooks\": [{\"id\": 1,\"genre\": \"Terror\","
             + "\"author\": \"Yo Soy El Autor\", \"image\": \"Mi Imagen\","
             + "\"title\": \"Mi Super Titulo\",\"subtitle\": \"Mi SubTitulo\",\"publisher\": \"Publicador\", "
             + "\"year\": \"2020\", \"page\": 3, \"isbn\": \"999\", \"users\": [{\"id\": 1,"
             + "\"username\": \"Alito1\",\"name\": \"Alex\",\"birthday\": \"1994-10-25\","
             + "\"listBooks\": [1]},1052]\"id\": 1"
             + "}"
      ));
  }
}
