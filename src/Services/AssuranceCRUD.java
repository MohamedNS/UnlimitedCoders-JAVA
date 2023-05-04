/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Assurance;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Utils.MyConnection;

/**
 *
 * @author asus
 */
public class AssuranceCRUD implements IAssuranceService<Assurance> {

    Connection cnx;
    Statement stm;
    ArrayList<Assurance> list;

    public AssuranceCRUD() {
        cnx = MyConnection.getInstance().getConnection();
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
 public Map<String, Number> getPatientsByAssurance() {
    Map<String, Number> assurancePatients = new HashMap<>();
    try {
        String query = "SELECT assurance.nom_assurance, COUNT(DISTINCT depot.id_patient) AS nb_patients " +
                       "FROM depot " +
                       "INNER JOIN assurance ON assurance.id_assurance = depot.id_assurance " +
                       "GROUP BY assurance.nom_assurance";
        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String assurance = rs.getString("nom_assurance");
            int nbPatients = rs.getInt("nb_patients");
            assurancePatients.put(assurance, nbPatients);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return assurancePatients;
}


}
