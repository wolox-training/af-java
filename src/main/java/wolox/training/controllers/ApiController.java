package wolox.training.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Model;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.errors.book.BookHttpErrors;
import wolox.training.errors.user.UserHttpErrors;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.AuthProviderUser;
import wolox.training.utils.CustomAuthenticationProvider;

public abstract class ApiController {

    @ApiOperation(value = "Given the isbn of a book, return the book or an exception", response = Book.class)
    protected Book foundBook(String isbn, BookRepository bookRepository){
        Optional<Book> list = bookRepository.findByIsbn(isbn);
        if (list.isEmpty()){
            new BookHttpErrors("Book Not Found").bookNotFound();
        }
        return list.get();
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
}
