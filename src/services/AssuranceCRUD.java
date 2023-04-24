/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Assurance;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnection;

/**
 *
 * @author asus
 */
public class AssuranceCRUD implements IAssuranceService<Assurance> {

    Connection cnx;
    Statement stm;
    ArrayList<Assurance> list;

    public AssuranceCRUD() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public Boolean ajouter(Assurance a) {
        int res = 0;
        try {
            String qry = "INSERT INTO `assurance`( `id_assurance`, `nom_assurance`, `plafond`, `adresse_assurance`) VALUES ('" + a.getIdAssurance() + "','" + a.getNomAssurance() + "','" + a.getPlafond() + "','" + a.getAdresseAssurance() + "')";
            stm = cnx.createStatement();
            res = stm.executeUpdate(qry);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

        }

        return res != 0;

    }

    @Override
    public Boolean supprimer(Assurance a) {
        int res = 0;
        try {

            stm = cnx.createStatement();
            String qry = "DELETE FROM`assurance` WHERE id_assurance = '" + a.getIdAssurance() + "';";
            res = stm.executeUpdate(qry);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return res != 0;
    }

    @Override
    public Boolean modifier(Assurance a) {
        int res = 0;
        try {

            stm = cnx.createStatement();
            String qry = "UPDATE `assurance` SET `nom_assurance`='" + a.getNomAssurance() + "',`plafond`='" + a.getPlafond() + "',`adresse_assurance`='" + a.getAdresseAssurance() + "' WHERE `id_assurance`='" + a.getIdAssurance() + "';";
            res = stm.executeUpdate(qry);

        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return res != 0;
    }

    @Override
    public ObservableList<Assurance> afficher() {
        List<Assurance> assurances = new ArrayList<>();

        try {
            String qry = "SELECT * FROM `assurance`";
            stm = cnx.createStatement();

            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Assurance a = new Assurance();
                a.setIdAssurance(rs.getInt("id_assurance"));
                a.setNomAssurance(rs.getString("nom_assurance"));
                a.setPlafond(rs.getFloat("plafond"));
                a.setAdresseAssurance(rs.getString("adresse_assurance"));
                assurances.add(a);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return FXCollections.observableArrayList(assurances);
    }

    @Override
    public List<Assurance> listerAssurance() {
        List<Assurance> assurances = new ArrayList<>();
        try {
            String qry = "SELECT * FROM assurance";
            stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Assurance assurance = new Assurance(rs.getInt("id_assurance"), rs.getString("nom_assurance"));
                assurances.add(assurance);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return assurances;
    }
    
 public Assurance getAssuranceById(int id) {
    Assurance assurance = null;
    String sql = "SELECT * FROM assurance WHERE id_assurance = " + id;

    try {
        stm = cnx.createStatement();
        ResultSet result = stm.executeQuery(sql);

        if (result.next()) {
            assurance = new Assurance();
            assurance.setIdAssurance(result.getInt("id_assurance"));
            assurance.setNomAssurance(result.getString("nom_assurance"));
            assurance.setPlafond(result.getFloat("plafond"));
            assurance.setAdresseAssurance(result.getString("adresse_assurance"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return assurance;
}

}
