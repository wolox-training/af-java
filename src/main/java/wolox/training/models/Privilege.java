package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="privileges")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Privilege {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRIVILEGE_SEQ")
  @SequenceGenerator(name = "PRIVILEGE_SEQ", sequenceName = "PRIVILEGE_SEQ")
  private long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "privileges")
  private List<Role> roles = new ArrayList<>();

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

  public List<Role> getRoles(){
    return this.roles;
  }

  public long getId(){
    return this.id;
  }
}
