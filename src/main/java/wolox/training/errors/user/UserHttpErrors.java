package wolox.training.errors.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserHttpErrors extends RuntimeException {

    public UserHttpErrors(String message, HttpStatus error) {
        super(message);
        throw new ResponseStatusException(error, this.getMessage());
    }

}