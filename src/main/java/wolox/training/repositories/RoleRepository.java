package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import wolox.training.models.Role;

public interface RoleRepository extends Repository<Role, Long> {
  Optional<Role> findByName(String name);
  Role save(Role role);
}
