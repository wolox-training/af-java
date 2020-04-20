package wolox.training.controllers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
import wolox.training.errors.book.BookHttpErrors;
import wolox.training.external.services.OpenLibraryService;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.BookService;

@RestController
@Api
@RequestMapping("/api/books")
public class BookController extends ApiController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryService openLibraryService;

    @Autowired
    private BookService bookService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @PostMapping
    @ApiOperation(value = "Given a book, create one, and return the book.", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created book."),
        @ApiResponse(code = 500, message = "Error created book, exist book."),
        @ApiResponse(code = 405, message = "Method Not Allowed."),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book, Model model) {
        Book newBook = new Book(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(),
            book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage(), book.getIsbn());
        bookRepository.save(newBook);
        return newBook;
    }

    @PutMapping
    @ApiOperation(value = "Given the isbn of the book, you can update the book, and return the book.", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated book"),
        @ApiResponse(code = 404, message = "Book not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public Book update(@RequestBody Book book) {
        Book bookFounded = foundBook(book.getIsbn(), bookRepository);
        bookFounded.update(book.getGenre(), book.getAuthor(), book.getImage(), book.getTitle(),
            book.getSubtitle(), book.getPublisher(), book.getYear(), book.getPage());
        return bookRepository.save(bookFounded);
    }

    @DeleteMapping
    @ApiOperation(value = "Given the isbn of the book, you delete the book, and return void", response = void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted book"),
        @ApiResponse(code = 404, message = "Book not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(name="isbn", required=true) String isbn) {
        bookRepository.delete(foundBook(isbn, bookRepository));
    }

    @GetMapping("/getall")
    @ApiOperation(value = "Given a filter type and a param for filter, return the book asked", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully founded book"),
        @ApiResponse(code = 404, message = "Book not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<Book> getAll(@RequestParam(name="editor", required=false) String editor,
        @RequestParam(name="author", required=false) String author,
        @RequestParam(name="genre", required=false) String genre,
        @RequestParam(name="year", required=false) String year,
        @RequestParam(name="image", required=false) String image,
        @RequestParam(name="title", required=false) String title,
        @RequestParam(name="subtitle", required=false) String subtitle,
        @RequestParam(name="page", required=false) String page,
        @RequestParam(name="isbn", required=false) String isbn,
        @RequestParam(name="orderByField", required=false, defaultValue = "isbn") String orderByField,
        @RequestParam(name="order", required=false, defaultValue = "ASC") String orderBy,
        @RequestParam(name="offset", required=false, defaultValue = "0") String offset,
        @RequestParam(name="limit") String limit
    ) {
        return bookService.getAllBooks(editor, author, genre, year, image, title, subtitle, page, isbn, orderByField, orderBy,offset, limit,bookRepository);
    }

    @GetMapping("/{isbn}")
    @ApiOperation(value = "Given the isbn of the book, return the book asked", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully founded book"),
        @ApiResponse(code = 404, message = "Book not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public Book read(@PathVariable String isbn) {
        return bookService.foundBookForGet(isbn, bookRepository, openLibraryService);
    }
}