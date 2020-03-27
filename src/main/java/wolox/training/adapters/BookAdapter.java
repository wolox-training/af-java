package wolox.training.adapters;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.atomic.AtomicReference;
import wolox.training.external.dtos.BookDTO;
import wolox.training.models.Book;

public class BookAdapter {

  public BookDTO transformBookDTOToBook(String isbn, JsonNode request){
    String title = request.path("title").textValue();
    String subtitle = request.path("subtitle").textValue();
    String publishDate = request.path("publish_date").textValue();
    int numberPages = request.path("number_of_pages").intValue();
    String[] authors = convertJsonNodeToArrayString(request.path("authors"));
    String publishers = convertJsonNodeToString(request.path("publishers"));
    return new BookDTO(isbn, title, subtitle, publishers, publishDate,
        Integer
            .toString(
                numberPages), authors);
  }

  private String convertJsonNodeToString(JsonNode publishersNode){
    AtomicReference<String> publishers = new AtomicReference<>(
        "");
    publishersNode.forEach(publisherNode -> publishers.set(publishers.get().concat(publisherNode.path("name").textValue().concat(", "))));
    return publishers.get().substring(0, publishers.get().length()-2);
  }

  private String[] convertJsonNodeToArrayString(JsonNode authorsNode){
    String[] list = new String[authorsNode.size()];
    AtomicReference<Integer> count = new AtomicReference<>(
        0);
    authorsNode.forEach(author -> {
      list[Integer.parseInt(count.toString())] = author.path("name").textValue();
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
