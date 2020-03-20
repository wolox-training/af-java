package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import wolox.training.errors.user.UserHttpErrors;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import wolox.training.utils.ErrorConstants;

@Entity
@Table(name="users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ")
    private long id;

    @ApiModelProperty(notes = "user", required = true, example = "MySuperUser")
    @Column(nullable = false, unique = true)
    private String username;

    @ApiModelProperty(notes = "name", required = true, example = "Alex")
    @Column(nullable = false)
    private String name;

    @ApiModelProperty(notes = "birthday", required = true, example = "1994-10-25")
    @Column(nullable = false)
    private LocalDate birthday;

    @ApiModelProperty(notes = "This Field is generated automatically")
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Book> books = new ArrayList<>();

    public User() {
    }

    public User(String name, String username, LocalDate birthday) {
        this.setUsername(username);
        this.setName(name);
        this.setBirthday(birthday);
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Preconditions
            .checkNotNull(username,
                String.format(ErrorConstants.NOT_NULL,"username"));
        Preconditions
            .checkArgument(!username.isEmpty(), String.format(ErrorConstants.NOT_EMPTY, "username"));
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions
            .checkNotNull(name, String.format(ErrorConstants.NOT_NULL,"name"));
        Preconditions
            .checkArgument(!name.isEmpty(), String.format(ErrorConstants.NOT_EMPTY, "name"));
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

  public void setBirthday(LocalDate birthday) {
        Preconditions
            .checkNotNull(birthday, String.format(ErrorConstants.NOT_NULL,"birthday"));
        Preconditions
            .checkArgument(!birthday.isAfter(LocalDate.now()), ErrorConstants.NOT_LATER_CURRENT_DAY);
        this.birthday = birthday;
    }

    public List<Book> getListBooks() {
        return (List<Book>) Collections.unmodifiableList(this.getBooks());
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBookToUser(Book book){
        if (!this.getBooks().contains(book)) {
            this.getBooks().add(book);
        }else {
            new UserHttpErrors("The book already exists").BookAlreadyOwnedException();
        }
    }

    public void update(String name, LocalDate birthday){
        this.setName(name);
        this.setBirthday(birthday);
    }

    public void setAtributes(String name, String userName, LocalDate birthday){
        this.update(name, birthday);
        this.setUsername(userName);
    }
}
