package wolox.training.services;

import io.swagger.annotations.ApiOperation;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.external.services.OpenLibraryService;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;

@Service
public class BookService {

  @ApiOperation(value = "Given the isbn of a book, search in DB or external service and return the book or an exception", response = User.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Book foundBookForGet(String isbn, BookRepository bookRepository, OpenLibraryService openLibraryService){
    Optional<Book> list = bookRepository.findByIsbn(isbn);
    if (list.isEmpty()){
      return openLibraryService.bookInfo(isbn);
    }
    return list.get();
  }
}
