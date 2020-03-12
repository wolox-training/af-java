package wolox.training.errors.book;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}