package spring_boot_tutorial.app.model;

import lombok.Data;

@Data
public class UserModel {
  
  private int id;
  private String email;
  private String username;
  private String password;

}
