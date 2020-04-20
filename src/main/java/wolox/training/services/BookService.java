package wolox.training.services;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.errors.book.BookHttpErrors;
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

  @ApiOperation(value = "Given the filter for book, return the books or an exception", response = User.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Page<Book> getAllBooks(String editor, String author, String genre, String year, String image,
      String title, String subtitle, String page, String isbn, String orderByField, String orderBy,
      String offset, String limit, BookRepository bookRepository) {
    Page<Book> list = executeQuery(editor, author, genre, year, image, title, subtitle, page, isbn,
        orderByField, orderBy.substring(0, 3).toUpperCase(), offset, limit,bookRepository);
    if (list.isEmpty()){
      new BookHttpErrors("Book Not Found").bookNotFound();
    }
    return list;
  }

  private Page<Book> executeQuery(String editor, String author, String genre, String year, String image,
      String title, String subtitle, String page, String isbn, String orderByField, String orderBy,
      String offset, String limit,BookRepository bookRepository) {
    if (orderBy.equals("ASC")) {
      return bookRepository.getAllBook(editor, author, genre, year, image, title, subtitle, page, isbn, PageRequest
          .of(Integer.parseInt(offset), Integer.parseInt(limit), Sort.by(Direction.ASC, orderByField)));
    } else {
      if (!orderBy.equals("DES")) {
        new BookHttpErrors("type of wrong ordering. This should be: asc or desc").bookOrderFail();
      }
    }
    return bookRepository.getAllBook(editor, author, genre, year, image, title, subtitle, page, isbn, PageRequest
        .of(Integer.parseInt(offset), Integer.parseInt(limit), Sort.by(Direction.DESC, orderByField)));
  }
}