package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.VariablesConstants;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class UserModelTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;
  private User user;


  @BeforeEach
  public void init(){
    user = new User("Alex", "Alito", LocalDate
          .of(1994, 10, 25), "123456");
    entityManager.persist(user);
    entityManager.flush();
  }

  @Test
  public void whenFindByUsername_thenReturnUser() {
    Optional<User> userFound = userRepository.findByUsername(user.getUsername());
    assertThat(userFound.get().getUsername())
        .isEqualTo(user.getUsername());
    assertThat(userFound.get().getName())
        .isEqualTo(user.getName());
  }

  @Test
  public void whenFindByUsernameThatNotExist_thenReturnAnError() {
    String username = VariablesConstants.USER_NOT_EXIST;
    Optional<User> userFound = userRepository.findOneByName(username);
    assertThat(userFound.isPresent()).isFalse();
  }
}
