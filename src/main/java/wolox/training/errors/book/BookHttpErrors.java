package wolox.training.errors.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookHttpErrors extends Exception {

    public BookHttpErrors(String message, HttpStatus error) {
        super(message);
        throw new ResponseStatusException(error, this.getMessage());
    }
}