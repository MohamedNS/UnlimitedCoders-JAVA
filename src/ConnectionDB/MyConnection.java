/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
     static String url ="jdbc:mysql://localhost/bankin";
   static String login ="root";
   static String password = "";
   static Connection myConnDB;
   public static MyConnection instance;

    private MyConnection() {
        try {
           myConnDB = DriverManager.getConnection(url, login, password);
           System.out.println("Successful connection");
       } catch (SQLException ex) {
           System.out.println(ex);
       }
    }
    public Connection getConnection(){
        return myConnDB;
    }
    
    public static MyConnection getInstance(){
      
        if(instance == null){
            instance = new MyConnection();
        }
        return instance;
    }
    
}