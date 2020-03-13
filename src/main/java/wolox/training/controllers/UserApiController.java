package wolox.training.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@Controller
@RequestMapping("/api/users")
public class UserApiController extends ApiController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        User new_user = new User(user.getName(), user.getUsername(), user.getBirthday());
        userRepository.save(new_user);
        return new_user;
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        User found_user = found_user(user.getUsername(), userRepository);
        user.update(user.getName(), user.getBirthday());
        userRepository.save(found_user);

        return found_user;
    }

    @DeleteMapping("/{username}")
    public void delete(@PathVariable String username) {
        userRepository.delete(found_user(username, userRepository));
    }

    @GetMapping("/details/{username}")
    public User read(@PathVariable String username) {
        return found_user(username, userRepository);
    }

    @GetMapping("/list_books/{username}")
    public User list_book(@PathVariable String username) {
        return found_user(username, userRepository);
    }

    @PutMapping("/add_book/{username}")
    public User add_book(@PathVariable String username, @RequestBody Book book) {
        Book found_book = found_book(book.getIsbn(), bookRepository);
        User found_user = found_user(username, userRepository);
        Exception message = found_user.addBookToUser(found_book);
        userRepository.save(found_user);
        bookRepository.save(found_book);
        return found_user;
    }
}
