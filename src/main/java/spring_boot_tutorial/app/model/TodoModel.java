package spring_boot_tutorial.app.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class TodoModel {
  
  private int id;
  
  @NotBlank(message = "{notblack_todo_content}")
  @Length(max = 500, message = "{length_todo_content}")
  private String content;

  private Timestamp createdAt;
  private Timestamp updatedAt;

}
