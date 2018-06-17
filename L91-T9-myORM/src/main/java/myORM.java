/*
* ДЗ-09: myORM
Создайте в базе таблицу с полями:
id bigint(20) NOT NULL auto_increment
name varchar(255)
age int(3)

Создайте абстрактный класс DataSet. Поместите long id в DataSet.
Добавьте класс UserDataSet (с полями, которые соответствуют таблице) унаследуйте его от DataSet.

Напишите Executor, который сохраняет наследников DataSet в базу и читает их из базы по id и классу.

<T extends="" DataSet=""> void save(T user){…}
<T extends="" DataSet=""> T load(long id, Class<T> clazz){…}
*/


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.Properties;

public class myORM {

    static final String configFile = "/Users/ds/Downloads/_MYLIB/_OTUS/Java/otus_java_201804/L91-T9-myORM/db.properties";


    public myORM(){
        //Connection con = dbConnection(configFile);
    }

    /**
     * @name resultset_PrintAll
     * print all data from ResultSet
     * @param resultSet java.sql.ResultSet
     * */
    public void resultset_PrintAll(ResultSet resultSet){
        try {
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

    /**
     * @name dbConnection
     * Established connection by ConfigFile
     * @param configFile file of parameters of configuration connection
     * */
    public Connection dbConnection(String configFile){
        Properties prop = new Properties();
        Connection conn = null;

        try {
            FileInputStream input = new FileInputStream(configFile);
            prop.load(input);
        } catch (IOException e) {
                e.printStackTrace();
        }

        if (prop.size() == 0) {
            throw new InvalidParameterException("Property file is empty");

        }

        //String driver = props.getProperty("h2.jdbc.driver");
        String url = prop.getProperty("h2.jdbc.url");
        String user = prop.getProperty("h2.jdbc.username");
        String password = prop.getProperty("h2.jdbc.password");

        System.out.print("Connecting to database...");

        try {
            conn = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        System.out.println("..successfully");
        return conn;
    }

    public void createDB(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE users";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void createUserTable(Connection conn) {
        try {
            System.out.print("Connecting to table...");
            Statement stmt = conn.createStatement();
            String sql =  "CREATE TABLE user " +
                    "(id Bigint(20) not NULL auto_increment, " +
                    " name VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
            System.out.println("..successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showAllTables(Connection conn) {

        try {
            Statement stmt = conn.createStatement();
            String sql = "SHOW TABLES";
            ResultSet rs = stmt.executeQuery(sql);

            resultset_PrintAll(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}


