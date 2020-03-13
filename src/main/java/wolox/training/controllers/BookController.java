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
import wolox.training.errors.book.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
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
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book, Model model) {
        Book new_book = new Book(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(), book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage(), book.getIsbn());
        bookRepository.save(new_book);
        return new_book;
    }

    @PutMapping("/{isbn}")
    public Book update(@RequestBody Book book, @PathVariable String isbn) {
        Book found_book = found_book(isbn);
        found_book.update(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(),
            book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage());
        return bookRepository.save(found_book);
    }

    @DeleteMapping("/{isbn}")
    public void delete(@PathVariable String isbn) {
        bookRepository.delete(found_book(isbn));
    }

    @GetMapping("/{isbn}")
    public Book read(@PathVariable String isbn) {
        return found_book(isbn);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private Book found_book(String isbn){
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null){
            throw new BookNotFoundException("Book not found");
        }else{
            return book;
        }
    }
}
