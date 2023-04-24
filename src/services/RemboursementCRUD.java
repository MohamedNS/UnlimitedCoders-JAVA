/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Remboursement;
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
public class RemboursementCRUD implements IRemboursementService<Remboursement> {

    Connection cnx;
    Statement stm;
    ArrayList<Remboursement> list;

    public RemboursementCRUD() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public Boolean ajouter(Remboursement r) {
        int res = 0;
        try {
            String qry = "INSERT INTO `remboursement`(`date_remboursement`, `reponse`, `montant_rembourse`, `id_depot`) VALUES ('" + r.getDateRemboursement() + "','" + r.getReponse() + "','" + r.getMontantRembourse() + "','" + r.getIdDepot() + "')";
            stm = cnx.createStatement();
            res = stm.executeUpdate(qry);
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

        }

        return res != 0;
    }

    @Override
    public Boolean supprimer(Remboursement r) {
        int res = 0;
        try {

            stm = cnx.createStatement();
            String qry = "DELETE FROM`remboursement` WHERE id_remboursement = '" + r.getIdRemboursement() + "';";
            res = stm.executeUpdate(qry);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return res != 0;
    }

    @Override
    public Boolean modifier(Remboursement r) {
        int res = 0;
        try {

            stm = cnx.createStatement();
            String qry = "UPDATE `remboursement` SET `date_remboursement`='" + r.getDateRemboursement() + "',`reponse`='" + r.getReponse() + "',`montant_rembourse`='" + r.getMontantRembourse() + "' WHERE `id_remboursement`='" + r.getIdRemboursement() + "';";
            res = stm.executeUpdate(qry);

        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return res != 0;
    }

    @Override
    public ObservableList<Remboursement> afficher() {
        List<Remboursement> remboursements = new ArrayList<>();

        try {
            String qry = "SELECT * FROM `remboursement`";
            stm = cnx.createStatement();

            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Remboursement r = new Remboursement();
                r.setIdRemboursement(rs.getInt("id_remboursement"));
                r.setDateRemboursement(rs.getDate("date_remboursement").toLocalDate());
                r.setReponse(rs.getString("reponse"));
                r.setMontantRembourse(rs.getFloat("montant_rembourse"));
                r.setIdDepot(rs.getInt("id_depot"));
                remboursements.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return FXCollections.observableArrayList(remboursements);

    }

    @Override
    public float calculerMontantRembourse(Remboursement r) {
        float montantConsultation = 0;
        float montantMedicaments = 0;

        try {
            // Get the corresponding fiche
            String qry = "SELECT id_fiche FROM depot WHERE id_depot = " + r.getIdDepot();
            stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            if (rs.next()) {
                int idFiche = rs.getInt("id_fiche");

                // get the consultation amount from the corresponding fiche
                qry = "SELECT montant_consultation FROM fiche WHERE id_fiche = " + idFiche;
                rs = stm.executeQuery(qry);
                if (rs.next()) {
                    montantConsultation = rs.getFloat("montant_consultation");
                }

                // get the reimbursement amount for each medicament in the corresponding fiche
                qry = "SELECT m.prix, m.type FROM medicament m JOIN fiche_medicament fm ON m.id_medicament = fm.id_medicament WHERE fm.id_fiche = " + idFiche;
                rs = stm.executeQuery(qry);
                while (rs.next()) {
                    float prix = rs.getFloat("prix");
                    String type = rs.getString("type");
                    if (type.equals("V")) {
                        montantMedicaments += prix * 1;
                    } else if (type.equals("E")) {
                        montantMedicaments += prix * 0.85;
                    } else if (type.equals("I")) {
                        montantMedicaments += prix * 0.4;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // calculate the total reimbursement amount
        return (float) ((montantConsultation*0.7) + montantMedicaments);
    }
    
    public double getSommeRemboursementsByIdPatient(int idPatient) {
    double sommeRemboursements = 0.0;
    try {
        String qry = "SELECT * FROM `remboursement` WHERE `id_depot` = " + idPatient;
        stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery(qry);
        while (rs.next()) {
            Remboursement r = new Remboursement();
            r.setIdRemboursement(rs.getInt("id_remboursement"));
            r.setIdDepot(rs.getInt("id_depot"));
            r.setMontantRembourse(rs.getFloat("montant_remboursement"));
            // récupérer l'ID du patient à partir de l'objet remboursement
            int idPatientRemboursement = r.getIdDepot();
            if (idPatientRemboursement == idPatient) {
                sommeRemboursements += r.getMontantRembourse();
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return sommeRemboursements;
}

    
    
}
