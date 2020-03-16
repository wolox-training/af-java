package wolox.training.errors.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Actor Not Found")
public class BookNotFoundException extends Exception {

    public BookNotFoundException() {
        super("Book not found");
    }
}