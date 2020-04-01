package wolox.training.models;

import org.apache.commons.lang3.StringUtils;
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
import wolox.training.utils.ErrorConstants;

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

    public void setGenre(String genre) {
        Preconditions
            .checkNotNull(genre,
                String.format(ErrorConstants.NOT_NULL,"genre"));
        Preconditions
            .checkArgument(
                !genre.isEmpty(), String.format(ErrorConstants.NOT_EMPTY, "genre"));
        this.genre = genre;
    }

    public void setAuthor(String author) {
        Preconditions
            .checkNotNull(author, String.format(ErrorConstants.NOT_NULL,"author"));
        Preconditions
            .checkArgument(
                !author.isEmpty(), String.format(ErrorConstants.NOT_EMPTY,"author"));
        this.author = author;
    }

    public void setImage(String image) {
        Preconditions
            .checkNotNull(image, String.format(ErrorConstants.NOT_NULL,"image"));
        Preconditions
            .checkArgument(
                !image.isEmpty(), String.format(ErrorConstants.NOT_EMPTY,"image"));
        this.image = image;
    }

    public void setTitle(String title) {
        Preconditions
            .checkNotNull(title, String.format(ErrorConstants.NOT_NULL,"title"));
        Preconditions
            .checkArgument(
                !title.isEmpty(), String.format(ErrorConstants.NOT_EMPTY,"title"));
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions
            .checkNotNull(subtitle, String.format(ErrorConstants.NOT_NULL,"subtitle"));
        Preconditions
            .checkArgument(
                !subtitle.isEmpty(), String.format(ErrorConstants.NOT_EMPTY,"subtitle"));
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions
            .checkNotNull(publisher, String.format(ErrorConstants.NOT_NULL,"publisher"));
        Preconditions
            .checkArgument(
                !publisher.isEmpty(), String.format(ErrorConstants.NOT_EMPTY,"publisher"));
        this.publisher = publisher;
    }

    public void setYear(String year) {
        Preconditions
            .checkNotNull(year, String.format(ErrorConstants.NOT_NULL,"year"));
        Preconditions
            .checkArgument(
                StringUtils.isNumeric(year),  String.format(ErrorConstants.NOT_NUMERIC, "year"));
        this.year = year;
    }

    public void setPage(Integer page) {
        Preconditions
            .checkNotNull(page, String.format(ErrorConstants.NOT_NULL,"page"));
        Preconditions
            .checkArgument(
                page > 0, ErrorConstants.NOT_GRADER_THAN_ZERO);
        this.page = page;
    }

    public void setIsbn(String isbn) {
        Preconditions
            .checkNotNull(isbn, String.format(ErrorConstants.NOT_NULL,"isbn"));
        Preconditions
            .checkArgument(
                StringUtils.isNumeric(isbn), String.format(ErrorConstants.NOT_NUMERIC, "isbn"));
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
