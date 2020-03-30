package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="roles")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
  @SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ")
  private long id;

  @ApiModelProperty(notes = "name", required = true, example = "ROL_USER")
  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private List<User> users = new ArrayList<>();

  @ApiModelProperty(notes = "This Field is generated automatically, but this are the privileges of the roles")
  @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
  private List<Privilege> privileges = new ArrayList<>();

  public Role() {
  }

  public Role(String name){
    this.setName(name);
  }

  public void setName(String name){
    this.name = name;
  }

  public List<User> getUsers(){
    return this.users;
  }

  public void setUsers(User user){
    this.users.add(user);
  }

  public List<Privilege> getPrivileges(){
    return this.privileges;
  }

  public void setPrivileges(Privilege privilege){
    this.privileges.add(privilege);
  }

  public long getId(){
    return this.id;
  }

}
