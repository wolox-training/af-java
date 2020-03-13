package wolox.training.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.errors.book.BookNotFoundException;
import wolox.training.errors.user.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

public abstract class ApiController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiOperation(value = "Given the isbn of a book, return the book or an exception", response = Book.class)
    protected Book found_book(String isbn, BookRepository bookRepository){
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null){
            throw new BookNotFoundException("Book not found");
        }else{
            return book;
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiOperation(value = "Given the username of a user, return the user or an exception", response = User.class)
    protected User found_user(String username, UserRepository userRepository){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UserNotFoundException("User not found");
        }else{
            return user;
        }
    }
}
