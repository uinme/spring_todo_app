package spring_boot_tutorial.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring_boot_tutorial.app.model.TodoModel;
import spring_boot_tutorial.app.repository.TodoDao;

@Service
public class TodoService {

  @Autowired
  private TodoDao dao;

  public TodoModel selectOne(int id) {

    return dao.selectOne(id);

  }

  public List<TodoModel> selectMany() {

    return dao.selectMany();

  }

  public boolean insert(TodoModel todo) {

    int rowNumber = dao.insert(todo);

    if (rowNumber > 0) {
      return true;
    }

    return false;

  }

  public boolean update(TodoModel todo) {

    int rowNumber = dao.update(todo);

    if (rowNumber > 0) {
      return true;
    }

    return false;

  }

  public boolean delete(TodoModel todo) {

    int rowNumber = dao.delete(todo);

    if (rowNumber > 0) {
      return true;
    }

    return false;

  }

}
