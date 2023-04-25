/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aziz3
 */
public class DataSource {
    
    private static Connection con =null;

    public DataSource() {
        
    }
    
    
    public static Connection openConnection() throws SQLException{
        if (con == null)
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/healthified","root","");
        return con;
    }
     public static void closeConnection()
    {       
        if(con != null)
          con = null;
    }
}