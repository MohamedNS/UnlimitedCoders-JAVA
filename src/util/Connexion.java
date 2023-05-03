/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MSI
 */
public class Connexion {
       public String url="jdbc:mysql://localhost:3306/healthified";
    public String login="root";
    public String pwd="";
        Connection cnx;
public static util.Connexion instance;
     public Connexion(){
        try {
           cnx = DriverManager.getConnection(url ,login ,pwd);
           System.out.println("Connexion etablie!");
        } catch (SQLException ex) {
           System.err.print(ex.getMessage());
        }
    }
     public Connection getCnx() {
        return cnx;
    }

    public static util.Connexion getInstance(){
        if(instance == null){
        instance = new util.Connexion();
        }
        return instance;
    }
}
