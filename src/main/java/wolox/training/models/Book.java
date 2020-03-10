package wolox.training.models;
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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ")
    @SequenceGenerator(name = "BOOK_SEQ", sequenceName = "BOOK_SEQ")
    private long id;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String author;

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

    private List<User> getUsers() {
        return users;
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
}
