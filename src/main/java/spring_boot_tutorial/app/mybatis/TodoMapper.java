package spring_boot_tutorial.app.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import spring_boot_tutorial.app.model.TodoModel;

@Mapper
public interface TodoMapper {
  
  @Select("SELECT * FROM todo_todo WHERE id = #{id}")
  TodoModel selectOne(int id);

  @Select("SELECT * FROM todo_todo")
  List<TodoModel> selectMany();

  @Insert("INSERT INTO todo_todo id, title VALUES (TODOTODO_ID_SEQ.NEXTVAL, #{title})")
  int insertOne(TodoModel todo);

}
