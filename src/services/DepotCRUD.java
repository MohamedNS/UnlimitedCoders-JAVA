/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Depot;
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
import utils.MyConnection;

/**
 *
 * @author asus
 */
public class DepotCRUD implements IDepotService<Depot> {

    Connection cnx;
    Statement stm;
    ArrayList<Depot> list;

    public DepotCRUD() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public Boolean ajouter(Depot d) {
        int res = 0;
        try {
            String qry = "INSERT INTO `depot`(`date_depot`, `regime`, `total_depense`, `etat`, `id_assurance`, `id_fiche`, `id_patient`) VALUES ('" + d.getDateDepot() + "','" + d.getRegime() + "','" + d.getTotalDepense() + "','" + d.getEtat() + "'," + d.getIdAssurance() + "," + d.getIdFiche() + "," + d.getIdPatient() + ")";
            stm = cnx.createStatement();
            res = stm.executeUpdate(qry);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

        }

        return res != 0;
    }

    @Override
    public Boolean supprimer(Depot d) {
        int res = 0;
        try {

            stm = cnx.createStatement();
            String qry = "DELETE FROM`depot` WHERE id_depot = '" + d.getIdDepot() + "';";
            res = stm.executeUpdate(qry);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return res != 0;
    }

    @Override
    public Boolean modifier(Depot d) {
        int res = 0;
        try {

            stm = cnx.createStatement();
            String qry = "UPDATE `depot` SET `date_depot`='" + d.getDateDepot() + "',`regime`='" + d.getRegime() + "',`total_depense`='" + d.getTotalDepense() + "',`etat`='" + d.getEtat() + "' WHERE `id_depot`='" + d.getIdDepot() + "';";
            res = stm.executeUpdate(qry);

        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return res != 0;
    }

    @Override
    public ObservableList<Depot> afficher() {
        List<Depot> depots = new ArrayList<>();

        try {
            String qry = "SELECT * FROM `depot`";
            stm = cnx.createStatement();

            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Depot d = new Depot();
                d.setIdDepot(rs.getInt("id_depot"));
                d.setDateDepot(rs.getDate("date_depot").toLocalDate());
                d.setRegime(rs.getString("regime"));
                d.setTotalDepense(rs.getFloat("total_depense"));
                d.setEtat(rs.getString("etat"));
                d.setIdAssurance(rs.getInt("id_assurance"));
                d.setIdFiche(rs.getInt("id_fiche"));
                d.setIdPatient(rs.getInt("id_patient"));
                depots.add(d);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return FXCollections.observableArrayList(depots);

    }

    @Override
    public void calculerTotalDepense(Depot d) {
        try {
            String qry = "SELECT montant_consultation, montant_medicaments FROM fiche WHERE id_fiche = " + d.getIdFiche();
            stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            if (rs.next()) {
                float montantConsultation = rs.getFloat("montant_consultation");
                float montantMedicaments = rs.getFloat("montant_medicaments");
                float totalDepense = montantConsultation + montantMedicaments;
                d.setTotalDepense(totalDepense);
                modifier(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Depot> listDepots() {
        List<Depot> depots = new ArrayList<>();
        // code to retrieve depots from data source and add them to list
        return depots;
    }

    @Override
    public List<String> listerIDsDepots() {
        List<String> depots = new ArrayList<>();
        try {
            String qry = "SELECT id_depot FROM depot";
            stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                depots.add(rs.getString("id_depot"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return depots;
    }

    public Depot getDepotById(int id) {
        Depot depot = null;
        String sql = "SELECT * FROM depot WHERE id_depot = " + id;

        try {
            stm = cnx.createStatement();
            ResultSet result = stm.executeQuery(sql);

            if (result.next()) {
                depot = new Depot();
                depot.setIdDepot(result.getInt("id_depot"));
                depot.setDateDepot(result.getDate("date_depot").toLocalDate());
                depot.setRegime(result.getString("regime"));
                depot.setTotalDepense(result.getFloat("total_depense"));
                depot.setEtat(result.getString("etat"));
                depot.setIdAssurance(result.getInt("id_assurance"));
                depot.setIdFiche(result.getInt("id_fiche"));
                depot.setIdPatient(result.getInt("id_patient"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return depot;
    }

    public void getInfoDepot(int idDepot) {
        try {
            Connection cnx = MyConnection.getInstance().getCnx();
            Statement stm = cnx.createStatement();

            // Récupération des informations du dépôt
            String qryDepot = "SELECT * FROM depot WHERE id_depot = " + idDepot;
            ResultSet rsDepot = stm.executeQuery(qryDepot);
            if (rsDepot.next()) {
                int idPatient = rsDepot.getInt("id_patient");
                int idAssurance = rsDepot.getInt("id_assurance");

                // Récupération du nom et du prénom du patient
                String qryPatient = "SELECT nom_patient, prenom FROM patient WHERE id_patient = " + idPatient;
                ResultSet rsPatient = stm.executeQuery(qryPatient);
                if (rsPatient.next()) {
                    String nomPatient = rsPatient.getString("nom_patient");
                    String prenomPatient = rsPatient.getString("prenom");

                    // Récupération du nom de l'assurance
                    String qryAssurance = "SELECT nom_assurance FROM assurance WHERE id_assurance = " + idAssurance;
                    ResultSet rsAssurance = stm.executeQuery(qryAssurance);
                    if (rsAssurance.next()) {
                        String nomAssurance = rsAssurance.getString("nom_assurance");

                        // Affichage des informations
                        System.out.println("Nom du patient : " + nomPatient);
                        System.out.println("Prénom du patient : " + prenomPatient);
                        System.out.println("Nom de l'assurance : " + nomAssurance);
                    } else {
                        System.out.println("Aucune assurance trouvée pour l'id " + idAssurance);
                    }
                } else {
                    System.out.println("Aucun patient trouvé pour l'id " + idPatient);
                }
            } else {
                System.out.println("Aucun dépôt trouvé pour l'id " + idDepot);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

  public Map<String, Integer> getDepositsByState() {
    Map<String, Integer> depositsByState = new HashMap<>();
    try {
        String query = "SELECT etat, COUNT(*) AS nb_deposits FROM depot GROUP BY etat";
        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String state = rs.getString("etat");
            int nbDeposits = rs.getInt("nb_deposits");
            depositsByState.put(state, nbDeposits);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return depositsByState;
}

}
