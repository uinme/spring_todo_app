package spring_boot_tutorial.app.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring_boot_tutorial.app.model.TodoListModel;

@Mapper
public interface TodoMapper {

  TodoListModel selectOne(int id);

  List<TodoListModel> selectMany();

  void insertTodoList(@Param("todoList") TodoListModel todoList);

}
