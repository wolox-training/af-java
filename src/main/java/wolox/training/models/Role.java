package wolox.training.models;

import java.awt.desktop.UserSessionEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  @ManyToMany(mappedBy = "roles")
  private Collection<User> users = new ArrayList<>();

  @ManyToMany
  private Collection<Privilege> privileges = new ArrayList<>();

  public Role() {
  }

  public Role(String name){
    this.setName(name);
  }

  public void setName(String name){
    this.name = name;
  }

  public Collection<User> getUsers(){
    return this.users;
  }

  public void setUsers(User user){
    this.users.add(user);
  }

  public Collection<Privilege> getPrivileges(){
    return this.privileges;
  }

  public void setPrivileges(Privilege privilege){
    this.privileges.add(privilege);
  }

}
