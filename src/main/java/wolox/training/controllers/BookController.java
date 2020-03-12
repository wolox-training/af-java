package wolox.training.controllers;
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
    public String create(@RequestBody Book book, Model model) {
        Book new_book = new Book(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(), book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage(), book.getIsbn());
        bookRepository.save(book);
        model.addAttribute("genre", new_book.getGenre());
        model.addAttribute("author", new_book.getAuthor());
        model.addAttribute("image", new_book.getImage());
        model.addAttribute("title", new_book.getTitle());
        model.addAttribute("subtitle", new_book.getSubtitle());
        model.addAttribute("publisher", new_book.getPublisher());
        model.addAttribute("year", new_book.getYear());
        model.addAttribute("page", new_book.getPage().toString());
        model.addAttribute("isbn", new_book.getIsbn());
        return "created_book";
    }

    @PutMapping("/update")
    public String update(@RequestBody Book book, Model model) {
        Book found_book = bookRepository.findByIsbn(book.getIsbn());
        found_book.update(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(), book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage());
        model.addAttribute("genre", book.getGenre());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("image", book.getImage());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("subtitle", book.getSubtitle());
        model.addAttribute("publisher", book.getPublisher());
        model.addAttribute("year", book.getYear());
        model.addAttribute("page", book.getPage().toString());
        model.addAttribute("isbn", book.getIsbn());
        bookRepository.save(found_book);

        return "modified_book";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(name="isbn", required=true) String isbn, Model model) {
        Book book = bookRepository.findByIsbn(isbn);
        bookRepository.delete(book);
        model.addAttribute("isbn", book.getIsbn());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("publisher", book.getPublisher());
        return "deleted_book";
    }

    @GetMapping("/read")
    public String read(@RequestParam(name="isbn", required=true) String isbn, Model model) {
        Book book = bookRepository.findByIsbn(isbn);
        model.addAttribute("genre", book.getGenre());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("image", book.getImage());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("subtitle", book.getSubtitle());
        model.addAttribute("publisher", book.getPublisher());
        model.addAttribute("year", book.getYear());
        model.addAttribute("page", book.getPage().toString());
        model.addAttribute("isbn", book.getIsbn());
        return "book_info";
    }
}
