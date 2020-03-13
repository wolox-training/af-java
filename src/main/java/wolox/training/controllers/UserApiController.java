package wolox.training.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
        found_user.update(user.getName(), user.getBirthday());
        userRepository.save(found_user);

        return found_user;
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody User user) {
        User found_user = found_user(user.getUsername(), userRepository);
        userRepository.delete(found_user);
    }

    @GetMapping("/details")
    public User read(@RequestBody User user) {
        return found_user(user.getUsername(), userRepository);
    }

    @GetMapping("/list_books")
    public List<Book> list_book(@RequestBody User user) {
        return found_user(user.getUsername(), userRepository).getListBooks();
    }

    @PutMapping("/add_book")
    public User add_book(@RequestBody User user, @RequestBody Book book) {
        Book found_book = found_book(book.getIsbn(), bookRepository);
        User found_user = found_user(user.getUsername(), userRepository);
        Exception message = found_user.addBookToUser(found_book);
        userRepository.save(found_user);
        bookRepository.save(found_book);
        return found_user;
    }
}
