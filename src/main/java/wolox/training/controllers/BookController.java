package wolox.training.controllers;
import io.swagger.annotations.Api;
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
import wolox.training.repositories.BookRepository;

@Controller
@Api
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @PostMapping("/create")
    public Book create(@RequestBody Book book, Model model) {
        Book new_book = new Book(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(), book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage(), book.getIsbn());
        bookRepository.save(new_book);
        return new_book;
    }

    @PutMapping("/update")
    public Book update(@RequestBody Book book, Model model) {
        Book found_book = bookRepository.findByIsbn(book.getIsbn());
        found_book.update(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(), book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage());
        bookRepository.save(found_book);

        return found_book;
    }

    @DeleteMapping("/delete")
    public Book delete(@RequestParam(name="isbn", required=true) String isbn, Model model) {
        Book book = bookRepository.findByIsbn(isbn);
        bookRepository.delete(book);
        return book;
    }

    @GetMapping("/read")
    public Book read(@RequestParam(name="isbn", required=true) String isbn, Model model) {
        Book book = bookRepository.findByIsbn(isbn);
        return book;
    }
}
