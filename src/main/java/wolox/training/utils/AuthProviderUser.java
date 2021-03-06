package wolox.training.utils;

import java.util.ArrayList;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderUser implements
    AuthenticationProvider {

  private String username;
  private String password;

  public AuthProviderUser() {
  }

  public AuthProviderUser(String username, String password) {
    this.setPassword(password);
    this.setUsername(username);
  }

  public void setUsername(String username){
    this.username = username;
  }

  public void setPassword(String password){
    this.password = password;
  }
  public String getUsername(){
    return username;
  }

  public String getPassword(){
    return password;
  }

  @Override
  public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {

    String name = authentication.getName();
    String password = authentication.getCredentials().toString();

    return new UsernamePasswordAuthenticationToken(
          name, password, new ArrayList<>());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
