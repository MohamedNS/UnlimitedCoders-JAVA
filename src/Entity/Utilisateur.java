/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author L390
 */
public class Utilisateur {
    private int id ; 
   private String nom ;
   private String prenom ;
   private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Utilisateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Utilisateur(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    public Utilisateur() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
  /*  
public  Utilisateur(int id) {
    // Retrieve user data from the database using the provided ID
    try {
        String query = "SELECT * FROM utilisateur WHERE id = ?";
        PreparedStatement statement = services.RendezVousCrud.cnx2.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            this.id = rs.getInt("id");
            this.nom = rs.getString("nom");
            this.prenom = rs.getString("prenom");
            
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
*/

    @Override
    public String toString() {
        return   nom  + prenom ;
    }

}
