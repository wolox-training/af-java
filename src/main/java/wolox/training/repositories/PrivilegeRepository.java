package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import wolox.training.models.Privilege;

public interface PrivilegeRepository extends Repository<Privilege, Long> {
  Optional<Privilege> findByName(String name);
  Privilege save(Privilege privilege);
}
