package wolox.training.repositories;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.Repository;
import wolox.training.models.User;

public interface UserRepository extends Repository<User, Long> {
    User findOneByName(String name);
    User findByUsername(String username);
    User save(User user);
    void delete(User user);
}
