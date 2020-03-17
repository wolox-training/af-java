package wolox.training.models;
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
import wolox.training.errors.book.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
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
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JoinTable(
        name = "books_users",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<Book> books;

    public User() {
        this.setBooks(null);
    }

    public User(String username, String name, LocalDate birthday) {
        this.setUsername(username);
        this.setName(name);
        this.setBirthday(birthday);
        this.setBooks(new ArrayList<>());
    }

    private long getId() {
        return id;
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

    public List<Book> getListBooks() {
        return (List<Book>) Collections.unmodifiableList(this.getBooks());
    }

    private List<Book> getBooks() {
        return books;
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
