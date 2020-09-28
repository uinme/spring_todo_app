package spring_boot_tutorial.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring_boot_tutorial.app.model.UserModel;
import spring_boot_tutorial.app.repository.UserDao;

@Service
public class UserService {
  
  @Autowired
  private UserDao dao;

  public UserModel selectOne(int id) {
    
    return dao.selectOne(id);

  }

  public List<UserModel> selectMany() {

    return dao.selectMany();

  }

  public boolean insertOne(UserModel user) {

    int rowNumber = dao.insertOne(user);

    if (rowNumber > 0) {

      return true;

    }

    return false;

  }

  public boolean updateOne(UserModel user) {

    int rowNumber = dao.updateOne(user);

    if (rowNumber > 0) {

      return true;

    }

    return false;

  }

  public boolean deleteOne(UserModel user) {

    int rowNumber = dao.deleteOne(user);

    if (rowNumber > 0) {

      return true;

    }

    return false;

  }

}
