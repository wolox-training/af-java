package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import wolox.training.models.User;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findOneByName(String name);
    Optional<User> findByUsername(String username);
    List<User> findByBirthdayBetween(LocalDate date1, LocalDate date2);
    List<User> findByNameContaining(String sequence);
    User save(User user);
    void delete(User user);
}
