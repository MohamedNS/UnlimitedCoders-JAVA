/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Facteur;
import entities.FicheAssurance;
import java.sql.Connection;
import java.sql.Date;
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
public class FicheAssuranceGrud {
    public static Connection cnx2;

    public FicheAssuranceGrud() {
        cnx2 = Connexion.getInstance().getCnx();
    }

    public void ajouterFicheAssurance(FicheAssurance c) {
        try {
            String requete2 = "INSERT INTO fiche_assurance (cin,nom,prenom,addresse,matricule_cnam,matricule_fiscal,honoraires,designation,date,total)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = cnx2.prepareStatement(requete2);
              statement.setString(1, c.getCin());
              statement.setString(2, c.getNom());
              statement.setString(3, c.getPrenom());
              statement.setString(4, c.getAddresse());
              statement.setString(5, c.getMatricule_cnam());
              statement.setInt(6, c.getMatricule_fiscal());
              statement.setInt(7, c.getHonoraires());
              statement.setString(8, c.getDesignation());
             statement.setDate(9,  (Date) c.getDate());
              statement.setInt(10, c.getTotal());
            statement.executeUpdate();
            System.out.println(" fiche_assurance ajoutée avec succes");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<FicheAssurance> afficherFicheAssurance(int id) {
        List<FicheAssurance> myList = new ArrayList<>();
        try {
            String requete3 = "SELECT * FROM fiche_assurance WHERE id=?";
            PreparedStatement statement = cnx2.prepareStatement(requete3);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FicheAssurance cal = new FicheAssurance();
                cal.setId(rs.getInt(1));
                cal.setCin(rs.getString("cin"));
                cal.setNom(rs.getString("nom"));
                cal.setPrenom(rs.getString("prenom"));
                cal.setAddresse(rs.getString(5));
                cal.setMatricule_cnam(rs.getString(6));
                cal.setMatricule_fiscal(rs.getInt("matricule_fiscal"));
                cal.setHonoraires(rs.getInt("honoraires"));
                cal.setDesignation(rs.getString(8));
                cal.setDate(rs.getDate("date"));
                cal.setTotal(rs.getInt("total"));
                myList.add(cal);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
 public List<FicheAssurance> afficherFicheAssurances() {
        List<FicheAssurance> myList = new ArrayList<>();
        try {
            String requete3 = "SELECT * FROM fiche_assurance";
            PreparedStatement statement = cnx2.prepareStatement(requete3);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                 FicheAssurance cal = new FicheAssurance();
                cal.setId(rs.getInt(1));
                cal.setCin(rs.getString("cin"));
                cal.setNom(rs.getString("nom"));
                cal.setPrenom(rs.getString("prenom"));
                cal.setAddresse(rs.getString(5));
                cal.setMatricule_cnam(rs.getString(6));
                cal.setMatricule_fiscal(rs.getInt("matricule_fiscal"));
                cal.setHonoraires(rs.getInt("honoraires"));
                cal.setDesignation(rs.getString(8));
                cal.setDate(rs.getDate("date"));
                cal.setTotal(rs.getInt("total"));
                myList.add(cal);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    public void supprimerFicheAssurance(int id) {
        try {
            String requete = "DELETE FROM fiche_assurance WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            int resultat = pst.executeUpdate();
            if (resultat == 1) {
                System.out.println("fiche_assurance supprimé avec succès");
            } else {
                System.out.println("Impossible de supprimer le fiche_assurance");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierFicheAssurance(FicheAssurance c) {
        try {
            String requete = "UPDATE fiche_assurance SET  cin=?,nom=?,prenom=?,addresse=?,matricule_cnam=?,matricule_fiscal=?,honoraires=?,designation=?,date=?,total=?   WHERE id=?";
            
            
            PreparedStatement ps = cnx2.prepareStatement(requete);
            ps.setString(1, c.getCin());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getPrenom());
            ps.setString(4, c.getAddresse());
            ps.setString(5, c.matricule_cnam);
            ps.setInt(6, c.getMatricule_fiscal());
            ps.setInt(7, c.getHonoraires());
            ps.setString(8, c.getDesignation());
            ps.setDate(9, (Date) c.getDate());
            ps.setInt(10, c.getTotal());
            
            System.out.println("Modifier SQL : " + ps.toString());
             ps.executeUpdate();
           
                System.out.println("fiche_assurance modifiée avec succès");
            
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
