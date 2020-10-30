package spring_boot_tutorial.app.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ActionModel {

  private int id;
  private int todoId;
  
  @NotBlank(message = "{notblack_todo_content}")
  @Length(max = 500, message = "{length_todo_content}")
  private String content;

  private String finished;

}
