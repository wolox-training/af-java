package wolox.training.models;

import com.google.common.base.Preconditions;
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

    @ApiModelProperty(notes = "genre", required = true, example = "Horror, Comedy, Drama")
    @Column(nullable = false)
    private String genre;

    @ApiModelProperty(notes = "The book author: this is the author of the book", required = true)
    @Column(nullable = false)
    private String author;

    @ApiModelProperty(notes = "The book image: this is the image the book", required = true)
    @Column(nullable = false)
    private String image;

    @ApiModelProperty(notes = "The book title: this is the title the book", required = true)
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(notes = "The book subtitle: this is the subtitle the book", required = true)
    @Column(nullable = false)
    private String subtitle;

    @ApiModelProperty(notes = "The book publisher: this is the publisher the book", required = true)
    @Column(nullable = false)
    private String publisher;

    @ApiModelProperty(notes = "The book year: this is the year the book", required = true)
    @Column(nullable = false)
    private String year;

    @ApiModelProperty(notes = "The book page: this is the page the book", required = true)
    @Column(nullable = false)
    private Integer page;

    @ApiModelProperty(notes = "The book isbn: this is the book identificator")
    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

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
        Preconditions
            .checkNotNull(genre, "The genre field cannot be null.");
        Preconditions
            .checkArgument(! genre.isEmpty(), "The genre field cannot be empty.");
        this.genre = genre;
    }

    private void setAuthor(String author) {
        Preconditions
            .checkNotNull(author, "The author field cannot be null.");
        Preconditions
            .checkArgument(! author.isEmpty(), "The author field cannot be empty.");
        this.author = author;
    }

    private void setImage(String image) {
        Preconditions
            .checkNotNull(image, "The image field cannot be null.");
        Preconditions
            .checkArgument(! image.isEmpty(), "The image field cannot be empty.");
        this.image = image;
    }

    private void setTitle(String title) {
        Preconditions
            .checkNotNull(title, "The title field cannot be null.");
        Preconditions
            .checkArgument(! title.isEmpty(), "The title field cannot be empty.");
        this.title = title;
    }

    private void setSubtitle(String subtitle) {
        Preconditions
            .checkNotNull(subtitle, "The subtitle field cannot be null.");
        Preconditions
            .checkArgument(! subtitle.isEmpty(), "The subtitle field cannot be empty.");
        this.subtitle = subtitle;
    }

    private void setPublisher(String publisher) {
        Preconditions
            .checkNotNull(publisher, "The publisher field cannot be null.");
        Preconditions
            .checkArgument(! publisher.isEmpty(), "The publisher field cannot be empty.");
        this.publisher = publisher;
    }

    private void setYear(String year) {
        Preconditions
            .checkNotNull(year, "The year field cannot be null.");
        Preconditions
            .checkArgument(! year.isEmpty(), "The year field cannot be empty.");
        this.year = year;
    }

    private void setPage(Integer page) {
        Preconditions
            .checkNotNull(page, "The author field cannot be null.");
        Preconditions
            .checkArgument(! (page > 0), "The author field cannot be empty.");
        this.page = page;
    }

    private void setIsbn(String isbn) {
        Preconditions
            .checkNotNull(isbn, "The isbn field cannot be null.");
        Preconditions
            .checkArgument(! isbn.isEmpty(), "The isbn field cannot be empty.");
        this.isbn = isbn;
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
