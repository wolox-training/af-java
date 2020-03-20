package wolox.training.controllers;
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
    public User create(@RequestBody User user) {
        User newUser = new User(user.getName(), user.getUsername(), user.getBirthday());
        userRepository.save(newUser);
        return newUser;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        User userFounded = foundUser(user.getUsername(), userRepository);
        userFounded.update(user.getName(), user.getBirthday());
        userRepository.save(userFounded);

        return userFounded;
    }

    @DeleteMapping
    public void delete(@RequestParam(name="username", required=true) String username) {
        userRepository.delete(foundUser(username, userRepository));
    }

    @GetMapping("/{username}")
    public User read(@PathVariable String username) {
        return foundUser(username, userRepository);
    }

    @GetMapping("/list_books/{username}")
    public List<Book> list_book(@PathVariable String username) {
        return foundUser(username, userRepository).getListBooks();
    }

    @PutMapping("/add_book")
    public User add_book(@RequestParam(name="username", required=true) String username,
                         @RequestParam(name="isbn", required=true) String isbn) {
        User userFounded = foundUser(username, userRepository);
        userFounded.addBookToUser(foundBook(isbn, bookRepository));
        userRepository.save(userFounded);
        return userFounded;
    }
}
