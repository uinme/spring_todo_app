package spring_boot_tutorial.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring_boot_tutorial.app.model.TodoListModel;
import spring_boot_tutorial.app.mybatis.TodoMapper;

@Service
public class TodoService {

  @Autowired
  private TodoMapper todoMapper;

  public TodoListModel selectOne(int id) {

    return todoMapper.selectOne(id);

  }

  public List<TodoListModel> selectMany() {

    return todoMapper.selectMany();

  }

  public void insertTodoList(TodoListModel todoList) {

    todoMapper.insertTodoList(todoList);

  }

}
