/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Facteur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.Connexion;

/**
 *
 * @author MSI
 */
public class FacteurGrud {
   public static Connection cnx2;

    public FacteurGrud() {
        cnx2 = Connexion.getInstance().getCnx();
    }

    public void ajouterfacteur(Facteur c) {
        try {
            String requete2 = "INSERT INTO facteur (cin,nom,prenom,id_patient,id_medicament,nom_med,dosage,prix)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = cnx2.prepareStatement(requete2);
              statement.setString(1, c.getCin());
              statement.setString(2, c.getNom());
              statement.setString(3, c.getPrenom());
              statement.setInt(4, c.getId_patient());
              statement.setInt(5, c.getId_medicament());
              statement.setString(6, c.getNom_med());
              statement.setString(7, c.getDosage());
              statement.setInt(8, c.getPrix());
            statement.executeUpdate();
            System.out.println(" facteur ajoutée avec succes");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Facteur> afficherfacteur(int id) {
        List<Facteur> myList = new ArrayList<>();
        try {
            String requete3 = "SELECT * FROM facteur WHERE id=?";
            PreparedStatement statement = cnx2.prepareStatement(requete3);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Facteur cal = new Facteur();
                cal.setId(rs.getInt(1));
                cal.setCin(rs.getString("cin"));
                cal.setNom(rs.getString("nom"));
                cal.setPrenom(rs.getString("prenom"));
                cal.setId_patient(rs.getInt(5));
                cal.setId_medicament(rs.getInt(6));
                cal.setNom_med(rs.getString("nom med"));
                cal.setDosage(rs.getString("dosage"));
                cal.setPrix(rs.getInt(8));
                myList.add(cal);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
 public List<Facteur> afficherfacteurs() {
        List<Facteur> myList = new ArrayList<>();
        try {
            String requete3 = "SELECT * FROM facteur";
            PreparedStatement statement = cnx2.prepareStatement(requete3);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Facteur cal = new Facteur();
                cal.setId(rs.getInt(1));
                cal.setCin(rs.getString("cin"));
                cal.setNom(rs.getString("nom"));
                cal.setPrenom(rs.getString("prenom"));
                cal.setId_patient(rs.getInt(5));
                cal.setId_medicament(rs.getInt(6));
                cal.setNom_med(rs.getString("nom_med"));
                cal.setDosage(rs.getString("dosage"));
                cal.setPrix(rs.getInt(9));
                myList.add(cal);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    public void supprimerfacteur(int id) {
        try {
            String requete = "DELETE FROM facteur WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            int resultat = pst.executeUpdate();
            if (resultat == 1) {
                System.out.println("facteur supprimé avec succès");
            } else {
                System.out.println("Impossible de supprimer le facteur");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierfacteur(Facteur c) {
        try {
            String requete = "UPDATE facteur SET cin=?,nom=?,prenom=?,id_patient=?,id_medicament=?,nom_med=?,dosage=?,prix=?  WHERE id=?";
            PreparedStatement ps = cnx2.prepareStatement(requete);
            ps.setString(1, c.getCin());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getPrenom());
            ps.setInt(4, c.getId_patient());
            ps.setInt(5, c.getId_medicament());
            ps.setString(6, c.getNom_med());
            ps.setString(7, c.getDosage());
            ps.setInt(7, c.getPrix());
            System.out.println("Modifier SQL : " + ps.toString());
             ps.executeUpdate();
           
                System.out.println("facteur modifiée avec succès");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 
    public int getprix(String s) {
    int price = 0;
    try {
        String req = "SELECT prix FROM medicament WHERE nom ='" + s + "'";

        Statement st = cnx2.createStatement(); 
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            price = rs.getInt("prix");
        }

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return price;
}
}
