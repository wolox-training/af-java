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
        User new_user = new User(user.getName(), user.getUsername(), user.getBirthday());
        userRepository.save(new_user);
        return new_user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        User found_user = found_user(user.getUsername(), userRepository);
        found_user.update(user.getName(), user.getBirthday());
        userRepository.save(found_user);

        return found_user;
    }

    @DeleteMapping
    public void delete(@RequestParam(name="username", required=true) String username) {
        userRepository.delete(found_user(username, userRepository));
    }

    @GetMapping("{username}")
    public User read(@PathVariable String username) {
        return found_user(username, userRepository);
    }

    @GetMapping("/list_books/{username}")
    public List<Book> list_book(@PathVariable String username) {
        return found_user(username, userRepository).getListBooks();
    }

    @PutMapping("/add_book")
    public User add_book(@RequestParam(name="username", required=true) String username,
                         @RequestParam(name="isbn", required=true) String isbn) {
        User found_user = found_user(username, userRepository);
        found_user.addBookToUser(found_book(isbn, bookRepository));
        userRepository.save(found_user);
        return found_user;
    }
}
