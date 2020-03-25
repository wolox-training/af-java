package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.VariablesConstants;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserController.class)
public class UserTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserRepository mockedUserRepository;
  @MockBean
  private BookRepository mockedBookRepository;
  private User oneTestUser;
  private Book oneTestBook;

  @BeforeEach
  public void setUp() {
    oneTestBook = new Book ("Terror", "Yo Soy El Autor", "Mi Imagen", "Mi Super Titulo", "Mi SubTitulo", "Publicador", "2020", 3, "999");
    oneTestUser = new User ("Alex", "Alito", LocalDate
        .of(1994, 10, 25), "123456");
  }

  @Test
  public void whenFindByUsernameWichExist_thenUserIsReturned() throws Exception{
    oneTestUser.addBookToUser(oneTestBook);
    Mockito.when(mockedUserRepository.findByUsername(oneTestUser.getUsername()))
        .thenReturn(
            Optional
                .of(oneTestUser));
    String url = VariablesConstants.USER_URL.concat(oneTestUser.getUsername());
    mvc.perform(get(url)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json(
         "{\"id\": 0,\"username\": \"Alito\",\"name\": \"Alex\","
             + "\"birthday\": \"1994-10-25\",\"listBooks\": [{\"id\": 0,\"genre\": \"Terror\","
             + "\"author\": \"Yo Soy El Autor\", \"image\": \"Mi Imagen\","
             + "\"title\": \"Mi Super Titulo\",\"subtitle\": \"Mi SubTitulo\",\"publisher\": \"Publicador\", "
             + "\"year\": \"2020\", \"page\": 3, \"isbn\": \"999\", \"users\": [0]}],"
             + "\"listBooks\": [0]}"
             + "}"
      ));
  }

  @Test
  public void whenCreateUser_thenUserIsReturned() throws Exception{
    Mockito.when(mockedUserRepository.save(oneTestUser))
        .thenReturn((oneTestUser));
    mvc.perform(post(VariablesConstants.USER_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\": \"Alito\",\"name\": \"Alex\","
            + "\"birthday\": \"1994-10-25\""
            + "}"))
        .andExpect(status().isCreated())
        .andExpect(content().json(
            "{\"id\": 0,\"username\": \"Alito\",\"name\": \"Alex\","
                + "\"birthday\": \"1994-10-25\",\"listBooks\": [],"
                + "\"listBooks\": []}"
                + "}"
        ));
  }

  @Test
  public void whenUpdateUser_thenUserIsReturned() throws Exception{
    Mockito.when(mockedUserRepository.findByUsername(oneTestUser.getUsername()))
        .thenReturn(
            Optional
                .of(oneTestUser));
    oneTestUser.setName("Alex Nicolas");
    mvc.perform(put(VariablesConstants.USER_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\": \"Alito\",\"name\": \"Alex Nicolas\","
            + "\"birthday\": \"1994-10-25\""
            + "}"))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{\"id\": 0,\"username\": \"Alito\",\"name\": \"Alex Nicolas\","
                + "\"birthday\": \"1994-10-25\",\"listBooks\": [],"
                + "\"listBooks\": []}"
                + "}"
        ));
  }

  @Test
  public void whenUpdateUserThatNotExist_thenMessageIsReturned() throws Exception{
    mvc.perform(put(VariablesConstants.USER_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\": \"Alito\",\"name\": \"Alex Nicolas\","
            + "\"birthday\": \"1994-10-25\""
            + "}"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void whenDeleteUser_thenUserIsReturned() throws Exception{
    Mockito.when(mockedUserRepository.findByUsername(oneTestUser.getUsername()))
        .thenReturn(
            Optional
                .of(oneTestUser));
    mvc.perform(delete(VariablesConstants.USER_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("username", oneTestUser.getUsername()))
        .andExpect(status().isOk());
  }

  @Test
  public void whenAUserAddBook_thenUserIsReturned() throws Exception{
    Mockito.when(mockedUserRepository.findByUsername(oneTestUser.getUsername()))
        .thenReturn(
            Optional
                .of(oneTestUser));
    Mockito.when(mockedBookRepository.findByIsbn(oneTestBook.getIsbn()))
        .thenReturn(
            Optional
                .of(oneTestBook));
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("username", oneTestUser.getUsername());
    requestParams.add("isbn", oneTestBook.getIsbn());
    String url = VariablesConstants.USER_URL.concat("add_book");
    mvc.perform(put(url)
        .contentType(MediaType.APPLICATION_JSON)
        .queryParams(requestParams))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{\"id\": 0,\"username\": \"Alito\",\"name\": \"Alex\","
                + "\"birthday\": \"1994-10-25\",\"listBooks\": [{\"id\": 0,\"genre\": \"Terror\","
                + "\"author\": \"Yo Soy El Autor\", \"image\": \"Mi Imagen\","
                + "\"title\": \"Mi Super Titulo\",\"subtitle\": \"Mi SubTitulo\",\"publisher\": \"Publicador\", "
                + "\"year\": \"2020\", \"page\": 3, \"isbn\": \"999\", \"users\": [0]}],"
                + "\"listBooks\": [0]}"
                + "}"
        ));
  }
}
