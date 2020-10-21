package spring_boot_tutorial.app.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TodoFormModel {

  private int todoId;
  private String title;
  private List<TodoListModel> todoLists;

  TodoFormModel() {

    todoLists = new ArrayList<TodoListModel>();

  }

}
