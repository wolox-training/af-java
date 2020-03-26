package wolox.training.adapters;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.atomic.AtomicReference;
import wolox.training.external.dtos.BookDTO;
import wolox.training.models.Book;

public class BookAdapter {

  public BookDTO transformBookDTOToBook(String isbn, JsonNode request){
    String[] authors = convertJsonNodeToArrayString(request.path("authors"));
    String title = request.path("title").textValue();
    String subtitle = request.path("subtitle").textValue();
    String publishers = convertJsonNodeToString(request.path("publishers"));
    String publishDate = request.path("publish_date").textValue();
    Integer numberPages = request.path("number_of_pages").intValue();
    return new BookDTO(isbn, title, subtitle, publishers, publishDate, numberPages.toString(), authors);
  }

  private String convertJsonNodeToString(JsonNode publishersNode){
    String publishers = new String();
    publishersNode.forEach(publisher -> publishers.concat(
        publisher.path("name").textValue()).concat(","));
    publishers.substring(0, publishers.length()-1);
    return publishers;
  }

  private String[] convertJsonNodeToArrayString(JsonNode authorsNode){
    String[] list = new String[authorsNode.size()];
    AtomicReference<Integer> count = new AtomicReference<>(
        0);
    authorsNode.forEach(author -> {
      list[Integer.parseInt(count.toString())] = author.toString();
      count.set(count.get()+ 1);
    });
    return list;
  }

  public Book transformBookDTOToBook(BookDTO bookDTO) {
    String author = String
        .join(
            "",
            bookDTO
                .getAuthors());
    return new Book("null", author,"null", bookDTO.getTitle(), bookDTO.getSubtitle(),
        bookDTO.getPublishers(), bookDTO.getPublishDate(), bookDTO.getNumberPages(), bookDTO.getIsbn());
  }
}
