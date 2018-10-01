package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

  Config config = new main.Config("default.config");
  Connection connection;
  PreparedStatement preparedStatement;
  ResultSet resultSet;

  public Database() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection(config.getProperty("host"), config.getProperty("user"), config.getProperty("pass"));
    } catch (ClassNotFoundException | SQLException ex) {
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public ResultSet runQuery(String query, Object... parameters) {
//    System.out.println(query);//DEV ONLY
    try {
      preparedStatement = connection.prepareStatement(query);
      for (int i = 0; i < parameters.length; i++) {
//        System.err.println(parameter);
        preparedStatement.setObject(i + 1, parameters[i]);
      }
      resultSet = preparedStatement.executeQuery();
    } catch (SQLException ex) {
      System.out.println(preparedStatement);
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
      
    }
    return resultSet;
  }

  public Long executeUpdate(String query, Object... parameters) {
    Long result = null;
//    System.out.println(query);//DEV ONLY
    try {
      preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      for (int i = 0; i < parameters.length; i++) {
        preparedStatement.setObject(i + 1, parameters[i]);
      }
      preparedStatement.executeUpdate();
      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        result = rs.getLong(1);
      }
      preparedStatement.close();
    } catch (SQLException ex) {
      System.out.println(preparedStatement);
      System.err.println("query gagal");
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }
  public boolean cekExist(String table, String field_name, String value){
//    ResultSet resultSet = runQuery("SELECT * FROM ? WHERE ? = ?", table, field_name, value);//error
    ResultSet resultSet = runQuery("SELECT * FROM "+table+" WHERE "+field_name+" = ?", value);
    try {
      if (resultSet.next()) {
        return true;
      }else{
        return false;
      }
    } catch (SQLException ex) {
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
  }
}
