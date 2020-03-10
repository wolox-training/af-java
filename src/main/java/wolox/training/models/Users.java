package wolox.training.models;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import wolox.training.errors.book.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JoinTable(name = "users",
        joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private List<Book> books;

    public Users() {
        this.setBooks(null);
    }

    public Users(String username, String name, LocalDate birthday) {
        this.setUsername(username);
        this.setName(name);
        this.setBirthday(birthday);
        this.setBooks(null);
    }

    private long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    private String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private LocalDate getBirthday() {
        return birthday;
    }

    private void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    private List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(this.getBooks());
    }

    private void setBooks(List<Book> books) {
        this.books = books;
    }

    public Exception addBookToUser(Book book){
        return this.addBook(book);
    }

    private Exception addBook(Book book){
        if (this.getBooks().contains(book) == true)
            return new BookAddedToList();
        else
            return new BookAlreadyOwnedException();
    }

}
