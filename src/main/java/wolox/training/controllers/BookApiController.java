package wolox.training.controllers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api
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
    @ApiOperation(value = "Given a book, create one, and return the book.", response = Book.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book, Model model) {
        Book new_book = new Book(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(), book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage(), book.getIsbn());
        bookRepository.save(new_book);
        return new_book;
    }

    @PutMapping
    @ApiOperation(value = "Given the isbn of the book, you can update the book, and return the book.", response = Book.class)
    public Book update(@RequestBody Book book) {
        Book found_book = found_book(book.getIsbn(), bookRepository);
        found_book.update(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(),
            book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage());
        return bookRepository.save(found_book);
    }

    @DeleteMapping
    @ApiOperation(value = "Given the isbn of the book, you delete the book, and return void", response = void.class)
    public void delete(@RequestParam(name="isbn", required=true) String isbn) {
        bookRepository.delete(found_book(isbn, bookRepository));
    }

    @GetMapping("{isbn}")
    @ApiOperation(value = "Given the isbn of the book, return the book asked", response = Book.class)
    public Book read(@PathVariable String isbn) {
        return found_book(isbn, bookRepository);
    }
}
