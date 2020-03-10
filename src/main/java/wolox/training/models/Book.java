package wolox.training.models;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String genre;

    @Column(nullable = false, unique = true)
    private String author;

    @Column(nullable = false, unique = true)
    private String image;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String subtitle;

    @Column(nullable = false, unique = true)
    private String publisher;

    @Column(nullable = false, unique = true)
    private String year;

    @Column(nullable = false, unique = true)
    private Integer page;

    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<Users> users;

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
    }

    private long getId() {
        return id;
    }

    private String getGenre() {
        return genre;
    }

    private String getAuthor() {
        return author;
    }

    private String getImage() {
        return image;
    }

    private String getTitle() {
        return title;
    }

    private String getSubtitle() {
        return subtitle;
    }

    private String getPublisher() {
        return publisher;
    }

    private String getYear() {
        return year;
    }

    private Integer getPage() {
        return page;
    }

    private String getIsbn() {
        return isbn;
    }

    private void setId(long id) {
        this.id = id;
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
}
