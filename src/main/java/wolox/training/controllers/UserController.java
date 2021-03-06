package wolox.training.controllers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.services.UserService;
import wolox.training.utils.AuthProviderUser;

@RestController
@Api
@RequestMapping("/api/users")
public class UserController extends ApiController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthProviderUser authenticateProvider;
    @Autowired
    private SecurityController securityController;
    @Autowired
    private UserService userService;

    @PostMapping
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created user"),
        @ApiResponse(code = 500, message = "Error created user, exist user"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Given a user, create one, and return the user.", response = User.class)
    public User create(@RequestBody User user) {
        User newUser = new User(user.getName(), user.getUsername(), user.getBirthday(), user.getPassword());
        userRepository.save(newUser);
        return newUser;
    }


    @PutMapping
    @ApiOperation(value = "Given the username of a user, you can update the user, and return the user.", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated user"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody User user) {
        User userFounded = foundUser(user.getUsername(), userRepository);
        userFounded.update(user.getName(), user.getBirthday());
        userRepository.save(userFounded);

        return userFounded;
    }

    @DeleteMapping
    @ApiOperation(value = "Given the username of a user, you delete the user, and return void", response = void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted user"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(name="username", required=true) String username) {
        userRepository.delete(foundUser(username, userRepository));
    }


    @ApiOperation(value = "Given the username of a user, return the user asked", response = User.class)
    @GetMapping("/{username}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully founded user"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public User read(@PathVariable String username) {
        return foundUser(username, userRepository);
    }

    @GetMapping("/list_books/{username}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully founded user"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Given the username of a user, return a book list of the user", response = Book.class)
    public List<Book> list_book(@PathVariable String username) {
        return foundUser(username, userRepository).getBooks();
    }

    @PutMapping("/add_book")
    @ApiOperation(value = "Given the username of a user and the isbn of a book, the book is added to user, return the user", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book added to user"),
        @ApiResponse(code = 404, message = "The book is added to user"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public User add_book(@RequestParam(name="username", required=true) String username,
                         @RequestParam(name="isbn", required=true) String isbn) {
        User userFounded = foundUser(username, userRepository);
        userFounded.addBookToUser(foundBook(isbn, bookRepository));
        userRepository.save(userFounded);
        return userFounded;
    }

    @PutMapping("/password")
    @ApiOperation(value = "Given the username of a user and password, the password from user will updated, return the user", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Passwrod user updated"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody AuthProviderUser user) {
        User userFounded = foundUser(user.getUsername(), userRepository);
        userFounded.setPassword(user.getPassword());
        userRepository.save(userFounded);
        return userFounded;
    }


    @ApiOperation(value = "Given the username of a user, return the user logged", response = User.class)
    @GetMapping("/user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully login user"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public User login(@RequestBody AuthProviderUser user) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user, user.getPassword());
        Authentication auth = authenticateProvider.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        ((UsernamePasswordAuthenticationToken) auth).setDetails(user.getUsername());
        sc.setAuthentication(auth);
        return foundUser(user.getUsername(), userRepository);
    }

    @ApiOperation(value = "Given two dates, filter users by birthday and return a list to user", response = User.class)
    @GetMapping("/filterByDates")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully login user"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<User> filterByDates(@RequestParam (name="startDate", required=true) String date1,
                                @RequestParam (name="endDate", required=true) String date2) {
        return userService.foundUserByBetweenBirthday(
            LocalDate.parse(date1), LocalDate.parse(date2), userRepository);
    }

    @ApiOperation(value = "Given a sequence of chars, filter users by name and return a list to user", response = User.class)
    @GetMapping("/filterBySequence")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully login user"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 405, message = "Method Not Allowed"),
        @ApiResponse(code = 401, message = "Access unauthorized."),
        @ApiResponse(code = 403, message = "Access unauthorized."),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<User> filterBySequence(@RequestParam (name="sequence", required=true) String sequence) {
        return userService.foundUserByContainsName(sequence, userRepository);
    }
}
