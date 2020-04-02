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
  protected void configure(
      AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder =
        PasswordEncoderFactories
            .createDelegatingPasswordEncoder();
    auth
        .inMemoryAuthentication()
        .withUser("user")
        .password(encoder.encode("password"))
        .roles("USER")
        .and()
        .withUser("admin")
        .password(encoder.encode("admin"))
        .roles("USER", "ADMIN");
  }

  @Override
  protected void configure(
      HttpSecurity http) throws Exception {
    String userUrl = VariablesConstants.USER_URL;
    String bookUrl = VariablesConstants.BOOK_URL;
    http.httpBasic().and().authorizeRequests()
        .antMatchers(String.valueOf(HttpMethod.POST), userUrl, bookUrl  )
        .permitAll()
        .antMatchers(String.valueOf(HttpMethod.DELETE), userUrl.concat("/*"), bookUrl.concat("/*"))
        .hasRole("ADMIN")
        .antMatchers(
            String.valueOf(HttpMethod.GET), userUrl.concat("/login"))
        .permitAll()
        .and()
        .csrf();
  }
}