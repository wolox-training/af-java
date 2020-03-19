package wolox.training.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookApiController extends ApiController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book, Model model) {
        Book newBook = new Book(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(), book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage(), book.getIsbn());
        bookRepository.save(newBook);
        return newBook;
    }

    @PutMapping
    public Book update(@RequestBody Book book) {
        Book bookFounded = foundBook(book.getIsbn(), bookRepository);
        bookFounded.update(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(),
            book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage());
        return bookRepository.save(bookFounded);
    }

    @DeleteMapping
    public void delete(@RequestParam(name="isbn", required=true) String isbn) {
        bookRepository.delete(foundBook(isbn, bookRepository));
    }

    @GetMapping("/{isbn}")
    public Book read(@PathVariable String isbn) {
        return foundBook(isbn, bookRepository);
    }
}
