package wolox.training.models;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name="books")
@ApiModel(description = "Books from my API")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ")
    @SequenceGenerator(name = "BOOK_SEQ", sequenceName = "BOOK_SEQ")
    private long id;

    @ApiModelProperty(notes = "The book genre: could be horror, comedy, drama, etc")
    @Column(nullable = false)
    private String genre;

    @ApiModelProperty(notes = "The book author: this is the author of the book")
    @Column(nullable = false)
    private String author;

    @ApiModelProperty(notes = "The book image: this is the image the book")
    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private Integer page;

    @ApiModelProperty(notes = "The book isbn: this is the book identificator")
    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users;

    public Book() {
    }

    public Book(String genre, String author, String image, String title, String subtitle, String publisher, String year, Integer page, String isbn) {
        this.setGenre(genre);
        this.setAuthor(author);
        this.setImage(image);
        this.setTitle(title);
        this.setSubtitle(subtitle);
        this.setPublisher(publisher);
        this.setYear(year);
        this.setPage(page);
        this.setIsbn(isbn);
        this.setUsers(new ArrayList<>());
    }

    public List<User> getUsers() {
        return users;
    }

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getYear() {
        return year;
    }

    public Integer getPage() {
        return page;
    }

    public String getIsbn() {
        return isbn;
    }

    private void setGenre(String genre) {
        this.genre = genre;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    private void setImage(String image) {
        this.image = image;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    private void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    private void setYear(String year) {
        this.year = year;
    }

    private void setPage(Integer page) {
        this.page = page;
    }

    private void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    private void setUsers(List<User> list) {
        this.users = list;
    }

    public void update (String genre, String author, String image, String title, String subtitle, String publisher, String year, Integer page){
        this.setGenre(genre);
        this.setAuthor(author);
        this.setImage(image);
        this.setTitle(title);
        this.setSubtitle(subtitle);
        this.setPublisher(publisher);
        this.setYear(year);
        this.setPage(page);
    }

}
