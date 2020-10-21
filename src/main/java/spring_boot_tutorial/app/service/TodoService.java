package spring_boot_tutorial.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring_boot_tutorial.app.model.TodoModel;
import spring_boot_tutorial.app.mybatis.TodoMapper;

@Service
public class TodoService {

  @Autowired
  private TodoMapper todoMapper;

  public TodoModel selectOne(int id) {

    return todoMapper.selectOne(id);

  }

  public List<TodoModel> selectMany() {

    return todoMapper.selectMany();

  }

  public boolean insert(TodoModel todo) {

    int rowNumber = todoMapper.insertOne(todo);

    if (rowNumber > 0) {
      return true;
    }

    return false;

  }

  // public boolean update(TodoModel todo) {

  //   int rowNumber = todoMapper.update(todo);

  //   if (rowNumber > 0) {
  //     return true;
  //   }

  //   return false;

  // }

  // public boolean delete(TodoModel todo) {

  //   int rowNumber = todoMapper.delete(todo);

  //   if (rowNumber > 0) {
  //     return true;
  //   }

  //   return false;

  // }

}
