/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;
import java.sql.Connection;
import java.lang.reflect.Array;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;

/**
 *
 * @author Mega-PC
 */
public class UsersController {
    Connection connection;
    Statement st;
    PreparedStatement sta;
    public List<User> getAllUsers()
   {
        List<User> userlist = new ArrayList<>();
        try {
            st = DataSource.openConnection().createStatement();
            ResultSet result =  st.executeQuery("SELECT * FROM user");
            
            while(result.next())
            {
                 userlist.add(new User(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(5),
                        result.getString(7),
                        result.getString(8),
                        result.getString(9)
                ));
                System.out.println(result.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
       return userlist;
   }
     public void SupprimerUser(int id)
    {
        try {
            st = DataSource.openConnection().createStatement();
            st.executeUpdate("Delete FROM `user` WHERE id = " + id);
            DataSource.closeConnection();
        } catch (SQLException ex) {
            DataSource.closeConnection();
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
