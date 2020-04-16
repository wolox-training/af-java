package wolox.training.errors.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserHttpErrors extends RuntimeException {

    public UserHttpErrors(String message) {
        super(message);
    }

    public void userNotFound() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, this.getMessage());
    }

    public void BookAlreadyOwnedException() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, this.getMessage());
    }
    public void userNotLogged() {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, this.getMessage());
    }

}