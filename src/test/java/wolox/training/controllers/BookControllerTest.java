package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.junit.Before;
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
import springfox.documentation.spring.web.json.Json;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.VariablesConstants;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserRepository mockedUserRepository;
  @MockBean
  private BookRepository mockedBookRepository;
  private Book oneTestBook;
  private ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  public void setUp(){
    oneTestBook = new Book ("Terror", "Yo Soy El Autor", "Mi Imagen", "Mi Super Titulo", "Mi SubTitulo", "Publicador", "2020", 3, "999");
  }

  @Test
  public void whenFindByIsbnWichExist_thenBookIsReturned() throws Exception{
    JsonNode jsonBook = mapper.readValue(new File("./JsonFiles/Books/BookJson.json"), JsonNode.class);
    Mockito.when(mockedBookRepository.findByIsbn(oneTestBook.getIsbn()))
        .thenReturn(
            Optional
                .of(oneTestBook));
    String url = VariablesConstants.BOOK_URL.concat(oneTestBook.getIsbn());
    mvc.perform(get(url)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonBook.toString()
        ));
  }

  @Test
  public void whenCreateBook_thenBookIsReturned() throws Exception{
    JsonNode jsonCreateBook = mapper.readValue(new File("./JsonFiles/Books/CreateBookJson.json"), JsonNode.class);
    JsonNode jsonBook = mapper.readValue(new File("./JsonFiles/Books/BookJson.json"), JsonNode.class);
    Mockito.when(mockedBookRepository.save(oneTestBook))
        .thenReturn((oneTestBook));
    mvc.perform(post(VariablesConstants.BOOK_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonCreateBook.toString()))
        .andExpect(status().isCreated())
        .andExpect(content().json(jsonBook.toString()
        ));
  }

  @Test
  public void whenUpdateBook_thenBookIsReturned() throws Exception{
    JsonNode jsonBook = mapper.readValue(new File("./JsonFiles/Books/UpdateBookJson.json"), JsonNode.class);
    Mockito.when(mockedBookRepository.findByIsbn(oneTestBook.getIsbn()))
        .thenReturn(
            Optional
                .of(oneTestBook));
    oneTestBook.setPage(4);
    mvc.perform(put(VariablesConstants.BOOK_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonBook.toString()))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonBook.toString()
        ));
  }

  @Test
  public void whenUpdateBookThatNotExist_thenMessageIsReturned() throws Exception{
    JsonNode jsonBook = mapper.readValue(new File("./JsonFiles/Books/BookJson.json"), JsonNode.class);
    mvc.perform(put(VariablesConstants.BOOK_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonBook.toString()))
        .andExpect(status().isNotFound());
  }

  @Test
  public void whenDeleteBook_thenBookIsReturned() throws Exception{
    Mockito.when(mockedBookRepository.findByIsbn(oneTestBook.getIsbn()))
        .thenReturn(
            Optional
                .of(oneTestBook));
    mvc.perform(delete(VariablesConstants.BOOK_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("isbn", oneTestBook.getIsbn()))
        .andExpect(status().isOk());
  }
}
