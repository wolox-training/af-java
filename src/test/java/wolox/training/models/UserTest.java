package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.UserRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class UserTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void whenFindByUsername_thenReturnUser() {
    // given
    User user = new User("Alex", "Alito21", LocalDate
        .of(1994, 10, 25));
    userRepository.save(user);

    // when
    User userFound = userRepository.findByUsername(user.getUsername()).get();

    // then
    assertThat(userFound.getUsername())
        .isEqualTo(user.getUsername());
    assertThat(userFound.getName())
        .isEqualTo(user.getName());
  }

  @Test
  public void whenFindByUsernameThatNotExist_thenReturnAnError() {
    // given
    String userName = "UsuarioInexistente";

    // when
    Optional<User> userFound = userRepository.findOneByName(userName);

    // then
    assertThat(userFound.isPresent()).isFalse();
  }
}
