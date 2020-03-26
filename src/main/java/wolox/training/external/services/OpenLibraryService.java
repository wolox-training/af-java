package wolox.training.external.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import wolox.training.adapters.BookAdapter;
import wolox.training.errors.book.BookHttpErrors;
import wolox.training.external.dtos.BookDTO;
import wolox.training.models.Book;
import wolox.training.utils.VariablesConstants;

public class OpenLibraryService {

  @Autowired
  private BookAdapter bookAdapter = new BookAdapter();

  public OpenLibraryService(){
  }

  public Book bookInfo(
      String isbn) {
    RestTemplate restTemplate = new RestTemplate();
    String fooResourceUrl
        = String.format(VariablesConstants.OPEN_LIBRARY_URL, isbn);
    ResponseEntity<String> response
        = restTemplate.getForEntity(fooResourceUrl, String.class);
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(response.getBody());
      BookDTO bookDTO = bookAdapter.transformBookDTOToBook(isbn, root.iterator().next());
      return bookAdapter.transformBookDTOToBook(bookDTO);
    } catch (JsonProcessingException e) {
      new BookHttpErrors("Book Not Found").bookNotFound();
    }
    return null;
  }
}
