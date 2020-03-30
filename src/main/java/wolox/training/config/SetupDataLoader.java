package wolox.training.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wolox.training.models.Privilege;
import wolox.training.models.Role;
import wolox.training.models.User;
import wolox.training.repositories.PrivilegeRepository;
import wolox.training.repositories.RoleRepository;
import wolox.training.repositories.UserRepository;

@Component
public class SetupDataLoader implements
    ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PrivilegeRepository privilegeRepository;

  @Autowired
  @Transient
  private PasswordEncoder passwordEncoder;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {

    if (alreadySetup)
      return;
    Privilege readPrivilege
        = createPrivilegeIfNotFound("READ_PRIVILEGE");
    Privilege writePrivilege
        = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

    Collection<Privilege> adminPrivileges = new ArrayList<>();
    adminPrivileges.add(readPrivilege);
    adminPrivileges.add(writePrivilege);
    Collection<Privilege> userPrivileges = new ArrayList<>();
    userPrivileges.add(readPrivilege);
    createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
    createRoleIfNotFound("ROLE_USER", userPrivileges);

    Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
    User user = new User("Test", "Test",
        LocalDate
            .of(1994, 10, 25), "123456");
    user.setRoles(adminRole);
    userRepository.save(user);

    alreadySetup = true;
  }

  @Transactional
  private Privilege createPrivilegeIfNotFound(String name) {

    Optional<Privilege> privilege = privilegeRepository.findByName(name);
    if (privilege.isEmpty()) {
      Privilege newPrivilege = new Privilege(name);
      return privilegeRepository.save(newPrivilege);
    }else {
      return privilege.get();
    }
  }

  @Transactional
  private Role createRoleIfNotFound(
      String name, Collection<Privilege> privileges) {

    Optional<Role> role = roleRepository.findByName(name);
    if (role.isEmpty()) {
      Role newRole = new Role(name);
      privileges.forEach(
          newRole::setPrivileges
      );
      return roleRepository.save(newRole);
    } else {
      return role.get();
    }
  }
}
