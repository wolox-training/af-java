package wolox.training.errors.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookHttpErrors extends Exception {

    public BookHttpErrors(String message) {
        super(message);
    }

    public void bookNotFound () {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, this.getMessage());
    }

    public void bookOrderFail() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.getMessage());
    }
}