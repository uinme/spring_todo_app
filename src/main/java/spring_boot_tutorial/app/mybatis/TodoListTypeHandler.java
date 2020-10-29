package spring_boot_tutorial.app.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import oracle.jdbc.OracleArray;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleStruct;
import spring_boot_tutorial.app.model.TodoListModel;

public class TodoListTypeHandler implements TypeHandler {

  @Override
  public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
    OracleConnection oracleConnection = ps.getConnection().unwrap(OracleConnection.class);

    TodoListModel todoList = (TodoListModel) parameter;

    OracleStruct actionStructs[] = new OracleStruct[todoList.getActions().size()];
    for (int ix = 0; ix < todoList.getActions().size(); ix++) {
      Object actionAttributes[] = {
        todoList.getActions().get(ix).getId(),
        todoList.getActions().get(ix).getTodoId(),
        todoList.getActions().get(ix).getContent()
      };

      actionStructs[ix] = (OracleStruct) oracleConnection.createStruct("ACTION_OBJ", actionAttributes);
    }

    OracleArray actionArray = (OracleArray) oracleConnection.createOracleArray("ACTION_ARY", actionStructs);

    Object todoListAttributes[] = {
      todoList.getTodoId(),
      todoList.getUserId(),
      todoList.getTitle(),
      todoList.getCreatedAt(),
      todoList.getUpdatedAt(),
      actionArray
    };

    OracleStruct struct = (OracleStruct) oracleConnection.createStruct("TODO_LIST_OBJ", todoListAttributes);

    ps.setObject(i, struct);
  }

  @Override
  public Object getResult(ResultSet rs, String columnName) throws SQLException {
    return null;
  }

  @Override
  public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
    return null;
  }

  @Override
  public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return null;
  }
  
}
