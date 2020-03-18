package wolox.training.controllers;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserApiController extends ApiController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Given a user, create one, and return the user.", response = User.class)
    public User create(@RequestBody User user) {
        User newUser = new User(user.getName(), user.getUsername(), user.getBirthday());
        userRepository.save(newUser);
        return newUser;
    }


    @PutMapping
    @ApiOperation(value = "Given the username of a user, you can update the user, and return the user.", response = User.class)
    public User update(@RequestBody User user) {
        User userFound = found_user(user.getUsername(), userRepository);
        userFound.update(user.getName(), user.getBirthday());
        userRepository.save(userFound);

        return userFound;
    }

    @DeleteMapping
    @ApiOperation(value = "Given the username of a user, you delete the user, and return void", response = void.class)
    public void delete(@RequestParam(name="username", required=true) String username) {
        userRepository.delete(found_user(username, userRepository));
    }

    @GetMapping("{username}")
    @ApiOperation(value = "Given the username of a user, return the user asked", response = User.class)
    public User read(@PathVariable String username) {
        return found_user(username, userRepository);
    }

    @GetMapping("/list_books/{username}")
    @ApiOperation(value = "Given the username of a user, return a book list of the user", response = Book.class)
    public List<Book> list_book(@PathVariable String username) {
        return found_user(username, userRepository).getListBooks();
    }

    @PutMapping("/add_book")
    @ApiOperation(value = "Given the username of a user and the isbn of a book, the book is added to user, return the user", response = void.class)
    public User add_book(@RequestParam(name="username", required=true) String username,
                         @RequestParam(name="isbn", required=true) String isbn) {
        User userFound = found_user(username, userRepository);
        userFound.addBookToUser(found_book(isbn, bookRepository));
        userRepository.save(userFound);
        return userFound;
    }
}
