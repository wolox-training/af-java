package wolox.training.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import wolox.training.utils.CustomAuthenticationProvider;
import wolox.training.utils.ErrorConstants;
import wolox.training.utils.VariablesConstants;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends
    WebSecurityConfigurerAdapter {

  @Autowired
  private CustomAuthenticationProvider authProvider;

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authProvider);
  }

  @Override
  protected void configure(
      HttpSecurity http) throws Exception {
    String userUrl = VariablesConstants.USER_URL.concat("/*");
    String bookUrl = VariablesConstants.BOOK_URL;
    String userLoginUrl = VariablesConstants.USER_URL.concat("/login");
    http
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, userUrl, bookUrl).permitAll()
        .antMatchers(HttpMethod.DELETE, userUrl, bookUrl).hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, userUrl, bookUrl).hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.GET, userLoginUrl).permitAll()
        .antMatchers(userUrl).authenticated()
        .anyRequest().authenticated();
  }
}
