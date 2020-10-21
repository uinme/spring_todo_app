package spring_boot_tutorial.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import spring_boot_tutorial.app.model.UserModel;
import spring_boot_tutorial.app.mybatis.UserMapper;

@Service
public class UserService {
  
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserModel selectOne(int id) {
    
    return userMapper.selectOne(id);

  }

  public List<UserModel> selectMany() {

    return userMapper.selectMany();

  }

  public boolean insertOne(UserModel user) {

    String encryptedPassword = passwordEncoder.encode(user.getPassword());

    user.setPassword(encryptedPassword);

    int rowNumber = userMapper.insertOne(user);

    if (rowNumber > 0) {

      return true;

    }

    return false;

  }

  // public boolean updateOne(UserModel user) {

  //   int rowNumber = dao.updateOne(user);

  //   if (rowNumber > 0) {

  //     return true;

  //   }

  //   return false;

  // }

  // public boolean deleteOne(UserModel user) {

  //   int rowNumber = dao.deleteOne(user);

  //   if (rowNumber > 0) {

  //     return true;

  //   }

  //   return false;

  // }

}
