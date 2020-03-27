package wolox.training.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Privilege {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "privileges")
  private Collection<Role> roles = new ArrayList<>();

  public Privilege (){
  }

  public Privilege (String name){
    this.setName(name);
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  public void setRoles(Role role){
    this.roles.add(role);
  }

  public Collection<Role> getRoles(){
    return this.roles;
  }
}
