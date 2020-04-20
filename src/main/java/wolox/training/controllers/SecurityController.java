package wolox.training.controllers;

import io.swagger.annotations.Api;
import java.security.Principal;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.errors.user.UserHttpErrors;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@RestController
@Api
@RequestMapping("/api/details")
public class SecurityController extends ApiController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public User currentUserName(
      Principal principal) {
    Authentication authentication = SecurityContextHolder
         .getContext()
        .getAuthentication();
    if (authentication instanceof AnonymousAuthenticationToken) {
    new UserHttpErrors("User not logged").userNotFound();
    }
    User user = foundUser(authentication.getDetails().toString(), userRepository);
    return user;
  }
}