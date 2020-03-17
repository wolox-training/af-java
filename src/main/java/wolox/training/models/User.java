package wolox.training.models;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import wolox.training.errors.user.UserHttpErrors;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ")
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "books_users",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<Book> books;

    public User() {
        this.setBooks(null);
    }

    public User(String name, String username, LocalDate birthday) {
        this.setUsername(username);
        this.setName(name);
        this.setBirthday(birthday);
        this.setBooks(new ArrayList<>());
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = checkNotNull(username);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = checkNotNull(name);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    private void setBirthday(LocalDate birthday) {
        this.birthday = checkNotNull(birthday);
    }

    public List<Book> getListBooks() {
        return (List<Book>) Collections.unmodifiableList(this.getBooks());
    }

    private List<Book> getBooks() {
        return books;
    }

    private void setBooks(List<Book> books) {
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
}
