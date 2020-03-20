package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
            .checkNotNull(genre,
                ErrorConstants.NOT_NULL_GENRE);
        Preconditions
            .checkArgument(
                !genre.isEmpty(), ErrorConstants.NOT_EMPTY_GENRE);
        this.genre = genre;
    }

    private void setAuthor(String author) {
        Preconditions
            .checkNotNull(author, ErrorConstants.NOT_NULL_AUTHOR);
        Preconditions
            .checkArgument(
                !author.isEmpty(), ErrorConstants.NOT_EMPTY_AUTHOR);
        this.author = author;
    }

    private void setImage(String image) {
        Preconditions
            .checkNotNull(image, ErrorConstants.NOT_NULL_IMAGE);
        Preconditions
            .checkArgument(
                !image.isEmpty(), ErrorConstants.NOT_EMPTY_IMAGE);
        this.image = image;
    }

    private void setTitle(String title) {
        Preconditions
            .checkNotNull(title, ErrorConstants.NOT_NULL_TITLE);
        Preconditions
            .checkArgument(
                !title.isEmpty(), ErrorConstants.NOT_EMPTY_TITLE);
        this.title = title;
    }

    private void setSubtitle(String subtitle) {
        Preconditions
            .checkNotNull(subtitle, ErrorConstants.NOT_NULL_SUBTITLE);
        Preconditions
            .checkArgument(
                !subtitle.isEmpty(), ErrorConstants.NOT_EMPTY_SUBTITLE);
        this.subtitle = subtitle;
    }

    private void setPublisher(String publisher) {
        Preconditions
            .checkNotNull(publisher, ErrorConstants.NOT_NULL_PUBLISHER);
        Preconditions
            .checkArgument(
                !publisher.isEmpty(), ErrorConstants.NOT_EMPTY_PUBLISHER);
        this.publisher = publisher;
    }

    private void setYear(String year) {
        Preconditions
            .checkNotNull(year, ErrorConstants.NOT_NULL_YEAR);
        Preconditions
            .checkArgument(
                StringUtils.isNumeric(year),  ErrorConstants.NOT_NUMERIC);
        this.year = year;
    }

    private void setPage(Integer page) {
        Preconditions
            .checkNotNull(page, ErrorConstants.NOT_NULL_PAGE);
        Preconditions
            .checkArgument(
                page > 0, ErrorConstants.NOT_GRADER_THAN_ZERO);
        this.page = page;
    }

    private void setIsbn(String isbn) {
        Preconditions
            .checkNotNull(isbn, ErrorConstants.NOT_NULL_ISBN);
        Preconditions
            .checkArgument(
                StringUtils.isNumeric(isbn), ErrorConstants.NOT_NUMERIC);
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

    public void setAtributes(String genre, String author, String image, String title, String subtitle, String publisher, String year, Integer page, String isbn){
        this.setIsbn(isbn);
        this.update(genre, author, image, title, subtitle, publisher, year, page);
    }

    public boolean equal_book (Book book){
        return
            this.getAuthor()
                .equals(
                    book.getAuthor())
                &&
                this.getGenre()
                    .equals(
                        book.getGenre())
                &&
                this.getImage()
                    .equals(
                        book.getImage())
                &&
                this.getTitle()
                    .equals(
                        book.getTitle())
                &&
                this.getSubtitle()
                    .equals(
                        book.getSubtitle())
                &&
                this.getPublisher()
                    .equals(
                        book.getPublisher())
                &&
                this.getYear()
                    .equals(
                        book.getYear())
                &&
                this.getPage()
                    .equals(
                        book.getPage());
    }

}
