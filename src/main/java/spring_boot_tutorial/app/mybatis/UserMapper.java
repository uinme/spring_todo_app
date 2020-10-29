package spring_boot_tutorial.app.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring_boot_tutorial.app.model.UserModel;

@Mapper
public interface UserMapper {
  UserModel selectOne(int id);

  List<UserModel> selectMany();

  int insertOne(UserModel user);
}
