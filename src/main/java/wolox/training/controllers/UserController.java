package wolox.training.controllers;
import com.sun.xml.bind.v2.model.core.ID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@Controller
public class UserController {

    DateTimeFormatter formatter_date = DateTimeFormatter.ofPattern("d/MM/yyyy");

    @Autowired
    private UserRepository userRepository;
    private BookRepository bookRepository;

    @GetMapping("/create")
    public String create(@RequestParam(name="name", required=true) String name, @RequestParam(name="username", required=true) String username, @RequestParam(name="birthday", required=true)String birthday, Model model) {
        User user = new User(name, username, LocalDate.parse(birthday, formatter_date));
        userRepository.save(user);
        model.addAttribute("name", user.getName());
        return "greeting";
    }

    @GetMapping("/update")
    public String update(@RequestParam (name="name", required=false) String name, @RequestParam(name="username", required=true) String username, @RequestParam(name="birthday", required=false)String birthday, Model model) {
        User user = userRepository.findByUsername(username);
        user.update(name, LocalDate.parse(birthday, formatter_date));
        model.addAttribute("name", user.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("birthday", user.getBirthday());
        userRepository.save(user);

        return "modified_user";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name="username", required=true) String username, Model model) {
        User user = userRepository.findByUsername(username);
        userRepository.delete(user);
        model.addAttribute("username", user.getUsername());
        return "deleted_user";
    }

    @GetMapping("/read")
    public String read(@RequestParam(name="username", required=true) String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("name", user.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("birthday", user.getBirthday().toString());
        return "user_info";
    }

    @GetMapping("/list_books")
    public String list_book(@RequestParam(name="username", required=true) String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("books", user.getListBooks());
        return "user_books";
    }

    @GetMapping("/add_book_to_user")
    public String add_book_to_user(@RequestParam(name="username", required=true) String username, @RequestParam(name="isbn", required=true) String isbn, Model model) {
        Book book = bookRepository.findByIsbn(isbn);
        User user = userRepository.findByUsername(username);
        Exception message = user.addBookToUser(book);
        model.addAttribute("message", message);
        return "added_book";
    }
}
