package wolox.training.utils;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wolox.training.errors.user.UserHttpErrors;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  public Authentication authenticate(Authentication authentication, String username)
      throws AuthenticationException {

    String password = authentication.getCredentials().toString();
    User user = userRepository.findByUsername(username).orElseThrow(()->new UserHttpErrors("User not found"));

    if (!passwordEncoder.matches(password,user.getPassword())) {
      new UserHttpErrors("User or Password incorrect").userNotLogged();
    }
    return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
