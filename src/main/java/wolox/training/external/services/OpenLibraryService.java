package wolox.training.external.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import wolox.training.adapters.BookAdapter;
import wolox.training.errors.book.BookHttpErrors;
import wolox.training.external.dtos.BookDTO;
import wolox.training.models.Book;
import wolox.training.utils.VariablesConstants;

public class OpenLibraryService {

  @Autowired
  private BookAdapter bookAdapter;

  public Book bookInfo(String isbn) {
    HttpClient client = HttpClient
        .newHttpClient();
    HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(String.format(VariablesConstants.OPEN_LIBRARY_URL, isbn)))
        .build();
    try {
      HttpResponse<String> response = client
          .send(request, HttpResponse.BodyHandlers.ofString());
      BookDTO bookDTO = bookAdapter.transformRequestToBookDTO(response.toString());
      return bookAdapter.transformBookDTOToBook(bookDTO);
    } catch (IOException | InterruptedException e) {
      new BookHttpErrors("Book Not Found").bookNotFound();
    }
     return null;
  }
}
