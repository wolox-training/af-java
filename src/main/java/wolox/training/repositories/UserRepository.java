package wolox.training.repositories;

import org.springframework.data.repository.Repository;
import wolox.training.models.User;

public interface UserRepository extends Repository<User, Long> {
    User findOneByName(String name);
}
