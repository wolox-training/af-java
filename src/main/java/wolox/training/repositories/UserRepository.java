package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import wolox.training.models.User;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findOneByName(String name);
}
