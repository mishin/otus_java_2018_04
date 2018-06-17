import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class myORMTest extends myORM{

  private myORM orm = new myORM();
  private Connection conn = orm.dbConnection(configFile);


  @Test
  public void test_resultset_PrintAll(){

      Statement stmt = null;
      try {
          stmt = conn.createStatement();
          String sql = "Select 1";
          ResultSet resultSet = stmt.executeQuery(sql);

          ResultSetMetaData rsmd = resultSet.getMetaData();
          int columnsNumber = rsmd.getColumnCount();
          orm.resultset_PrintAll(resultSet);
      } catch (SQLException e) {
          e.printStackTrace();
      }

  }


  @Test
  public void test_stmt(){

      System.out.println("Creating statement...");
      try {
          Statement stmt = conn.createStatement();
          String sql = "Select 1";
          ResultSet resultSet = stmt.executeQuery(sql);

          ResultSetMetaData rsmd = resultSet.getMetaData();
          int columnsNumber = rsmd.getColumnCount();


          while (resultSet.next()) {
              for (int i = 1; i <= columnsNumber; i++) {
                  if (i > 1) System.out.print(",  ");
                  String columnValue = resultSet.getString(i);
                  System.out.print(columnValue + " " + rsmd.getColumnName(i));
              }
              System.out.println("");
          }

      } catch (SQLException e) {
          e.printStackTrace();
      }

  }


  @Test
  public void test_createDB(){
      orm.createDB(conn);
  };

  @Test
  public void test_createUserTable(){
      orm.createUserTable(conn);

  };

  @Test
  public void test_showAlltables(){
        orm.showAllTables(conn);

  };





}