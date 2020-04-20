package wolox.training.external.dtos;

import java.io.Serializable;

public class BookDTO implements Serializable {

  private String isbn;
  private String title;
  private String subtitle;
  private String publishers;
  private String publishDate;
  private Integer numberPages;
  private String[] authors;

  public BookDTO (){};

  public BookDTO (String isbn, String title, String subtitle, String publishers, String publishDate, Integer numberPages, String[] authors){
    this.setIsbn(isbn);
    this.setTitle(title);
    this.setSubtitle(subtitle);
    this.setPublishers(publishers);
    this.setPublishDate(publishDate);
    this.setNumberPages(numberPages);
    this.setAuthors(authors);
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getPublishers() {
    return publishers;
  }

  public void setPublishers(
      String publishers) {
    this.publishers = publishers;
  }

  public String getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }

  public Integer getNumberPages() {
    return numberPages;
  }

  public void setNumberPages(Integer numberPages) {
    this.numberPages = numberPages;
  }

  public String[] getAuthors() {
    return authors;
  }

  public void setAuthors(String[] authors) {
    this.authors = authors;
  }


}
