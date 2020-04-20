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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.adapters.BookAdapter;
import wolox.training.errors.book.BookHttpErrors;
import wolox.training.external.dtos.BookDTO;
import wolox.training.models.Book;
import wolox.training.utils.VariablesConstants;

@Service
public class OpenLibraryService {

  @Autowired
  private BookAdapter bookAdapter;

  @Value("${openLibrary.baseUrl}")
  private String urlWithoutIsbn;

  @Value("&format=json&jscmd=data")
  private String urlFormat;

  public OpenLibraryService(){
  }

  public Book bookInfo(String isbn) {
    RestTemplate restTemplate = new RestTemplate();
    String fooResourceUrl = urlWithoutIsbn + isbn + urlFormat;
    ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode root = mapper.readTree(response.getBody());
      return bookAdapter.transformBookDTOToBook(bookAdapter.createBookDTO(isbn, root.iterator().next()));
    } catch (JsonProcessingException e) {
      new BookHttpErrors("Book Not Found").bookNotFound();
    }
    return null;
  }
}
