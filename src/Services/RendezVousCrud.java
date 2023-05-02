/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.RendezVous;
import Entity.Utilisateur;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.MyConnection;

/**
 *
 * @author L390
 */
public class RendezVousCrud {
     public static Connection cnx2;

    public RendezVousCrud() {
        cnx2 = MyConnection.getInstance().getConnection();
    }
    
    public void ajouterRdv() {
        try {
            String requete = "INSERT INTO rendez_vous (date, utilisateur_id, patient_id, etat, description) VALUES ('2023-03-11 08:30:00', '6',' 8',' urgent', 'java') ";
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete);

            System.out.println(" YAAY RendezVous ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ajouterRdv2(RendezVous r) {
        try {
            String requete2 = "INSERT INTO rendez_vous (date,utilisateur_id, patient_id,etat, description)"
                    + " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = cnx2.prepareStatement(requete2);
            statement.setTimestamp(1, new java.sql.Timestamp(r.getDate().getTime()));
            statement.setInt(2, r.getMedecin().getId());
            System.out.println(r.getMedecin().getId());
            statement.setInt(3, r.getPatient().getId());
            statement.setString(4, r.getEtat());
            statement.setString(5, r.getDescription());
            statement.executeUpdate();
            System.out.println(" YAAY RendezVous2 ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<RendezVous> afficherRendezvouses() throws SQLException {
        List<RendezVous> myList = new ArrayList<>();
            String requete3 = "SELECT * FROM rendez_vous ";
            Statement st = cnx2.createStatement();

            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                RendezVous rdv = new RendezVous();
                rdv.setId(rs.getInt(1));
                rdv.setDate(rs.getTimestamp("date"));
                rdv.setDescription(rs.getString("Description"));
                rdv.setEtat(rs.getString("Etat"));
                Utilisateur medecin = new Utilisateur(rs.getInt("utilisateur_id"));
                rdv.setMedecin(medecin);
                Utilisateur patient = new Utilisateur(rs.getInt("patient_id"));
                rdv.setPatient(patient);
                myList.add(rdv);
            }
       
        return myList;

    }

    public void supprimerRdv(int id) {
        try {
            String requete = "DELETE FROM rendez_vous WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            int resultat = pst.executeUpdate();
            if (resultat == 1) {
                System.out.println("Rendez-vous supprimé avec succès");
            } else {
                System.out.println("Impossible de supprimer le rendez-vous");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierRdv(int id, Date date, Utilisateur medecin, Utilisateur patient, String etat, String description) {
        try {
            String requete = "UPDATE rendez_vous SET date=?, utilisateur_id=?, patient_id=?, etat=?, description=? WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setDate(1, new java.sql.Date(date.getTime()));
            pst.setInt(2, medecin.getId());
            pst.setInt(3, patient.getId());
            pst.setString(4, etat);
            pst.setString(5, description);
            pst.setInt(6, id);
            int resultat = pst.executeUpdate();
            if (resultat == 1) {
                System.out.println("Rendez-vous modifié avec succès");
            } else {
                System.out.println("Impossible de modifier le rendez-vous");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierRdv2(int id, Timestamp date, Utilisateur patient, String etat, String description) {
        try {
            String requete = "UPDATE rendez_vous SET date=?, patient_id=?, etat=?, description=? WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setTimestamp(1, date);
            pst.setInt(2, patient.getId());
            pst.setString(3, etat);
            pst.setString(4, description);
            pst.setInt(5, id);
            int resultat = pst.executeUpdate();
            if (resultat == 1) {
                System.out.println("Rendez-vous modifié avec succès");
            } else {
                System.out.println("Impossible de modifier le rendez-vous");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<RendezVous> getRendezVousByUtilisateurId(int utilisateurId) {
        List<RendezVous> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM rendez_vous WHERE patient_id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, utilisateurId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RendezVous rdv = new RendezVous();
                rdv.setId(rs.getInt(1));
                rdv.setDate(rs.getTimestamp("date"));
                rdv.setDescription(rs.getString("Description"));
                rdv.setEtat(rs.getString("Etat"));
                Utilisateur medecin = new Utilisateur(rs.getInt("utilisateur_id"));
                rdv.setMedecin(medecin);
                Utilisateur patient = new Utilisateur(rs.getInt("patient_id"));
                rdv.setPatient(patient);
                myList.add(rdv);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public boolean isRdvAlreadyReserved(int medecinId, Timestamp dateTime) {
        try {
            String requete = "SELECT * FROM rendez_vous WHERE utilisateur_id=? AND date=?";
            PreparedStatement statement = cnx2.prepareStatement(requete);
            statement.setInt(1, medecinId);
            statement.setTimestamp(2, dateTime);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
            return false;
        }
    }

    public List<RendezVous> getAllRendezVousOfDoctor(int utilisateurId) {
        List<RendezVous> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM rendez_vous WHERE utilisateur_id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, utilisateurId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RendezVous rdv = new RendezVous();
                rdv.setId(rs.getInt("id"));
                rdv.setDate(rs.getTimestamp("date"));
                rdv.setDescription(rs.getString("description"));
                rdv.setEtat(rs.getString("etat"));
                rdv.setMedecin(new Utilisateur(rs.getInt("utilisateur_id")));
                rdv.setPatient(new Utilisateur(rs.getInt("patient_id")));
                myList.add(rdv);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<RendezVous> getAllRendezVousForDate(Timestamp start, Timestamp end) {
        List<RendezVous> myList = new ArrayList<>();
        try {
            // Define the SQL query with a BETWEEN clause to get all rendezvous between start and end timestamps
            String requete = "SELECT * FROM rendez_vous WHERE date BETWEEN ? AND ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);

            // Set the values of the start and end placeholders in the prepared statement
            pst.setTimestamp(1, start);
            pst.setTimestamp(2, end);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                // Create a new RendezVous object for each row in the result set
                RendezVous rdv = new RendezVous();
                rdv.setId(rs.getInt(1));
                rdv.setDate(rs.getTimestamp("date"));
                rdv.setDescription(rs.getString("Description"));
                rdv.setEtat(rs.getString("Etat"));

                // Create Utilisateur objects for the medecin and patient IDs
                Utilisateur medecin = new Utilisateur(rs.getInt("utilisateur_id"));
                rdv.setMedecin(medecin);
                Utilisateur patient = new Utilisateur(rs.getInt("patient_id"));
                rdv.setPatient(patient);

                // Add the RendezVous object to the list
                myList.add(rdv);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public int[] getNumAppointmentsPerDay(int utilisateurId, LocalDate startDate) {
        int[] numAppointments = new int[7]; // initialize an array to store the number of appointments for each day of the week
        try {
            String requete = "SELECT * FROM rendez_vous WHERE utilisateur_id = ? AND date >= ? AND date < ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, utilisateurId);
            pst.setTimestamp(2, Timestamp.valueOf(startDate.atStartOfDay()));
            pst.setTimestamp(3, Timestamp.valueOf(startDate.plusDays(7).atStartOfDay())); // get appointments for the next 7 days
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LocalDateTime appointmentDateTime = rs.getTimestamp("date").toLocalDateTime();
                int dayOfWeek = appointmentDateTime.getDayOfWeek().getValue() - 1; // get the day of the week as an index in the array
                numAppointments[dayOfWeek]++;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return numAppointments;
    }

}


