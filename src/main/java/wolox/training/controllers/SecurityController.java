package wolox.training.controllers;

import io.swagger.annotations.Api;
import java.security.Principal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.errors.user.UserHttpErrors;

@RestController
@Api
@RequestMapping("/api/detail")
public class SecurityController extends ApiController {

  @GetMapping
  public String currentUserName(Principal principal) {
    Authentication authentication = SecurityContextHolder
        .getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      return authentication.getName();
    } else {
      new UserHttpErrors("User not logged").userNotFound();;
    }
  }
}
