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
    http.httpBasic().and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/users/login" ).permitAll()
        .antMatchers(HttpMethod.DELETE, "/api/users").hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/api/users").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/api/books/**").permitAll()
        .antMatchers(HttpMethod.PUT, "/api/books").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/api/books").hasRole("ADMIN")
        .anyRequest().authenticated()
    .and()
    .csrf()
    .disable();
  }
}
