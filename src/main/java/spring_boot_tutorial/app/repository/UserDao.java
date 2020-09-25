package spring_boot_tutorial.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import spring_boot_tutorial.app.model.UserModel;

@Repository
public class UserDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbc;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserModel selectOne(int id) {

    String sql = "SELECT * FROM user WHERE id = :id";

    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("id", id);

    RowMapper<UserModel> rowMapper = new BeanPropertyRowMapper<UserModel>(UserModel.class);

    return jdbc.queryForObject(sql, paramSource, rowMapper);

  }

  public List<UserModel> selectMany() {

    String sql = "SELECT * FROM user";

    RowMapper<UserModel> rowMapper = new BeanPropertyRowMapper<UserModel>(UserModel.class);

    return jdbc.query(sql, rowMapper);

  }

  public int insertOne(UserModel user) {

    String sql = "INSERT INTO user "
               + "email, "
               + "username, "
               + "password, "
               + "VALUES (:email, :username, :password)";

    String password = passwordEncoder.encode(user.getPassword());

    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("email", user.getEmail())
      .addValue("username", user.getUsername())
      .addValue("password", password);

    return jdbc.update(sql, paramSource);

  }

  public int updateOne(UserModel user) {

    String sql = "UPDATE user SET "
               + "email = :email, "
               + "username = :username, "
               + "password = :password";

    String password = passwordEncoder.encode(user.getPassword());

    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("email", user.getEmail())
      .addValue("username", user.getUsername())
      .addValue("password", password);

    return jdbc.update(sql, paramSource);

  }

  public int deleteOne(UserModel user) {

    String sql = "DELETE FROM user WHERE id = :id";

    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("id", user.getId());

    return jdbc.update(sql, paramSource);

  }

}
