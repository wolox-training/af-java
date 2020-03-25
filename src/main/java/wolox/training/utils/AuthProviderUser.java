package wolox.training.utils;

public class AuthProviderUser {
  private String username;
  private String password;

  public AuthProviderUser() {
  }

  public AuthProviderUser(String username, String password) {
    this.setPassword(password);
    this.setUsername(username);
  }

  public void setUsername(String username){
    this.username = username;
  }

  public void setPassword(String password){
    this.password = password;
  }
  public String getUsername(){
    return username;
  }

  public String getPassword(){
    return password;
  }
}
