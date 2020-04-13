package wolox.training.controllers;

import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.errors.book.BookHttpErrors;
import wolox.training.errors.user.UserHttpErrors;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

public abstract class ApiController {

    @ApiOperation(value = "Given the isbn of a book, return the book or an exception", response = Book.class)
    protected Book foundBook(String isbn, BookRepository bookRepository){
        Optional<Book> list = bookRepository.findByIsbn(isbn);
        if (list.isEmpty()){
            new BookHttpErrors("Book Not Found").bookNotFound();
        }
        return list.get();
    }

    @ApiOperation(value = "Given the editor of the book, return the books or an exception", response = User.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected List<Book> foundBookForPublisher(String editor, BookRepository bookRepository){
        List<Book> list = bookRepository.findByPublisher(editor);
        if (list.isEmpty()){
            new BookHttpErrors("Book Not Found").bookNotFound();
        }
        return list;
    }

    @ApiOperation(value = "Given the year of the book, return the books or an exception", response = User.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected List<Book> foundBookForYear(String year, BookRepository bookRepository){
        List<Book> list = bookRepository.findByYear(year);
        if (list.isEmpty()){
            new BookHttpErrors("Book Not Found").bookNotFound();
        }
        return list;
    }

    @ApiOperation(value = "Given the genre of the book, return the books or an exception", response = User.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected List<Book> foundBookForGenre(String editor, BookRepository bookRepository){
        List<Book> list = bookRepository.findByGenre(editor);
        if (list.isEmpty()){
            new BookHttpErrors("Book Not Found").bookNotFound();
        }
        return list;
    }

    @ApiOperation(value = "Given the username of a user, return the user or an exception", response = User.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected User foundUser(String username, UserRepository userRepository){
        Optional<User> list = userRepository.findByUsername(username);
        if (list.isEmpty()){
            new UserHttpErrors("User not found").userNotFound();
        }
        return list.get();
    }

    @ApiOperation(value = "Given two dates , find by birthday, and return the user list or an exception", response = User.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected List<User> foundUserByBetweenBirthday(
        LocalDate date1, LocalDate date2, UserRepository userRepository){
        List<User> list = userRepository.findByBirthdayBetween(date1, date2);
        if (list.isEmpty()){
            new UserHttpErrors("Users not found").userNotFound();
        }
        return list;
    }

    @ApiOperation(value = "Given a sequence, find by contains name, and return the user list or an exception", response = User.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected List<User> foundUserByContainsName(String sequence, UserRepository userRepository){
        List<User> list = userRepository.findByNameContaining(sequence);
        if (list.isEmpty()){
            new UserHttpErrors("Users not found").userNotFound();
        }
        return list;
    }
}
