package wolox.training.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.errors.book.BookHttpErrors;
import wolox.training.errors.user.UserHttpErrors;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

public abstract class ApiController {

    protected Book found_book(String isbn, BookRepository bookRepository){
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null){
            new BookHttpErrors("Book Not Found").bookNotFound();
        }
        return book;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected User found_user(String username, UserRepository userRepository){
        User user = userRepository.findByUsername(username);
        if (user == null){
            new UserHttpErrors("User not found").userNotFound();
        }
        return user;
    }
}
