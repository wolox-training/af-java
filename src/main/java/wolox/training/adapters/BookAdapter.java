package wolox.training.adapters;

import wolox.training.external.dtos.BookDTO;
import wolox.training.models.Book;

public class BookAdapter {

  public BookDTO transformRequestToBookDTO(String request) {
    return new BookDTO();
  }

  public Book transformBookDTOToBook(BookDTO bookDTO) {
    return new Book();
  }
}
