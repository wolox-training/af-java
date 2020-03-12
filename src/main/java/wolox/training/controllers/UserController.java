package wolox.training.controllers;
import com.sun.xml.bind.v2.model.core.ID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        User new_user = new User(user.getName(), user.getUsername(), user.getBirthday());
        userRepository.save(new_user);
        return new_user;
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        User found_user = userRepository.findByUsername(user.getUsername());
        user.update(user.getName(), user.getBirthday());
        userRepository.save(found_user);

        return found_user;
    }

    @DeleteMapping("/delete")
    public User delete(@RequestParam(name="username", required=true) String username) {
        User user = userRepository.findByUsername(username);
        userRepository.delete(user);
        return user;
    }

    @GetMapping("/read")
    public User read(@RequestParam(name="username", required=true) String username) {
        return userRepository.findByUsername(username);
    }

    @GetMapping("/list_books")
    public User list_book(@RequestParam(name="username", required=true) String username, Model model) {
        return userRepository.findByUsername(username);
    }

    @PutMapping("/add_book")
    public User add_book(@RequestParam(name="username", required=true) String username, @RequestBody Book book) {
        Book found_book = bookRepository.findByIsbn(book.getIsbn());
        User found_user = userRepository.findByUsername(username);
        Exception message = found_user.addBookToUser(found_book);
        userRepository.save(found_user);
        bookRepository.save(found_book);
        return found_user;
    }
}
