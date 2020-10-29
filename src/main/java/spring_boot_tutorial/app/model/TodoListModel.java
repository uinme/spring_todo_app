package spring_boot_tutorial.app.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TodoListModel {
  private int todoId;
  private int userId;
  @NotBlank(message = "{notblank_todo_title}")
  private String title;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private List<ActionModel> actions;

  TodoListModel () {
    actions = new ArrayList<>();
  }
}
