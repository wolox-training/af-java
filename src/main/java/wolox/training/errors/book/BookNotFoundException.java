package wolox.training.errors.book;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Book not found");
    }
}