package spring_boot_tutorial.app.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TodoModel {
  
  private int id;
  private int userId;
  
  @NotBlank(message = "{notblack_todo_title}")
  private String title;

}
