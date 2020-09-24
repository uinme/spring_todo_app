package spring_boot_tutorial.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import spring_boot_tutorial.app.model.TodoModel;

@Repository
public class TodoDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbc;

  public TodoModel selectOne(int id) {

    String sql = "SELECT * FROM todo WHERE id = :id";

    // SQL文の:idに引数のidを割り当てる
    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("id", id);

    // O/R マッパー
    RowMapper<TodoModel> rowMapper = new BeanPropertyRowMapper<TodoModel>(TodoModel.class);

    return jdbc.queryForObject(sql, paramSource, rowMapper);

  }

  public List<TodoModel> selectMany() {

    String sql = "SELECT * FROM todo";

    RowMapper<TodoModel> rowMapper = new BeanPropertyRowMapper<TodoModel>(TodoModel.class);

    return jdbc.query(sql, rowMapper);

  }

  public int insert(TodoModel todo) {

    String sql = "INSERT INTO todo VALUES (NULL, :content)";

    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("content", todo.getContent());

    return jdbc.update(sql, paramSource);

  }

  public int update(TodoModel todo) {

    String sql = "UPDATE todo SET content = :content";

    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("content", todo.getContent());

    return jdbc.update(sql, paramSource);

  }

  public int delete(TodoModel todo) {

    String sql = "DELETE FROM todo WHERE id = :id";

    SqlParameterSource paramSource = new MapSqlParameterSource()
      .addValue("id", todo.getId());

    return jdbc.update(sql, paramSource);

  }

}
