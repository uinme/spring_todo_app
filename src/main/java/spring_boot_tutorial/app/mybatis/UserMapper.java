package spring_boot_tutorial.app.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import spring_boot_tutorial.app.model.UserModel;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM todo_user WHERE id = #{id}")
  UserModel selectOne(int id);

  @Select("SELECT * FROM todo_user")
  List<UserModel> selectMany();

  @Insert("INSERT INTO todo_user (id, username, email, password)" +
          "VALUES (TODOUSER_ID_SEQ.NEXTVAL, #{username}, #{email}, #{password})")
  int insertOne(UserModel user);
}
