package wolox.training.controllers;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/create")
    public String create(@RequestParam(name="genre", required=true) String genre, @RequestParam(name="author", required=true) String author, @RequestParam(name="image", required=true)String image,
        @RequestParam(name="title", required=true) String title, @RequestParam(name="subtitle", required=true) String subtitle, @RequestParam(name="publisher", required=true)String publisher,
        @RequestParam(name="year", required=true) String year, @RequestParam(name="page", required=true) Integer page, @RequestParam(name="isbn", required=true)String isbn, Model model) {
        Book book = new Book(genre, author, image, title, subtitle, publisher, year, page, isbn);
        bookRepository.save(book);
        model.addAttribute("genre", book.getGenre());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("image", book.getImage());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("subtitle", book.getSubtitle());
        model.addAttribute("publisher", book.getPublisher());
        model.addAttribute("year", book.getYear());
        model.addAttribute("page", book.getPage());
        model.addAttribute("isbn", book.getIsbn());
        return "created_book";
    }

    @GetMapping("/update")
    public String update(@RequestParam(name="genre", required=false) String genre, @RequestParam(name="author", required=false) String author, @RequestParam(name="image", required=false)String image,
        @RequestParam(name="title", required=false) String title, @RequestParam(name="subtitle", required=false) String subtitle, @RequestParam(name="publisher", required=false)String publisher,
        @RequestParam(name="year", required=false) String year, @RequestParam(name="page", required=false) Integer page, @RequestParam(name="isbn", required=true)String isbn, Model model) {
        Book book = bookRepository.findByIsbn(isbn);
        book.update(genre, author, image, title, subtitle, publisher, year, page);
        model.addAttribute("genre", book.getGenre());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("image", book.getImage());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("subtitle", book.getSubtitle());
        model.addAttribute("publisher", book.getPublisher());
        model.addAttribute("year", book.getYear());
        model.addAttribute("page", book.getPage());
        model.addAttribute("isbn", book.getIsbn());
        bookRepository.save(book);

        return "modified_book";
    }

    @GetMapping("/delete")
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
        model.addAttribute("page", book.getPage());
        model.addAttribute("isbn", book.getIsbn());
        return "book_info";
    }
}
