/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Calendrier;
import Entity.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Utils.MyConnexion;

/**
 *
 * @author L390
 */
public class CalendrierCrud {

    public static Connection cnx2;

    public CalendrierCrud() {
        cnx2 = Connexion.getInstance().getCnx();
    }

    public void ajouterDispo(Calendrier c) {
        try {
            String requete2 = "INSERT INTO calendrier (utilisateur_id,heure_debut,heure_fin)"
                    + " VALUES (?, ?, ?)";

            PreparedStatement statement = cnx2.prepareStatement(requete2);
            statement.setInt(1, c.getMedecin().getId());
            statement.setTimestamp(2, Timestamp.valueOf(c.getHeure_debut().toLocalDateTime()));
            statement.setTimestamp(3, Timestamp.valueOf(c.getHeure_fin().toLocalDateTime()));

            statement.executeUpdate();
            System.out.println(" YAAY Disponibilité ajoutée");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Calendrier> afficherDispo(int utilisateurId) {
        List<Calendrier> myList = new ArrayList<>();
        try {
            String requete3 = "SELECT * FROM calendrier WHERE utilisateur_id=?";
            PreparedStatement statement = cnx2.prepareStatement(requete3);
            statement.setInt(1, utilisateurId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Calendrier cal = new Calendrier();
                cal.setId(rs.getInt(1));
                cal.setHeure_debut(rs.getTimestamp("heure_debut"));
                cal.setHeure_fin(rs.getTimestamp("heure_fin"));

                Utilisateur medecin = new Utilisateur(rs.getInt("utilisateur_id"));
                cal.setMedecin(medecin);
                myList.add(cal);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void supprimerDispo(int id) {
        try {
            String requete = "DELETE FROM calendrier WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            int resultat = pst.executeUpdate();
            if (resultat == 1) {
                System.out.println("Disponibilité supprimé avec succès");
            } else {
                System.out.println("Impossible de supprimer la disponibilité");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierDispo(int id, Timestamp heureDebut, Timestamp heureFin) {
        try {
            String requete = "UPDATE calendrier SET heure_debut=?, heure_fin=? WHERE id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setTimestamp(1, heureDebut);
            pst.setTimestamp(2, heureFin);
            pst.setInt(3, id);
            int resultat = pst.executeUpdate();
            if (resultat == 1) {
                System.out.println("Disponibilité modifiée avec succès");
            } else {
                System.out.println("Impossible de modifier la disponibilité");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Calendrier> getDispoByMedecinId(int medecinId) throws SQLException {
        List<Calendrier> calendrierList = new ArrayList<>();
        String query = "SELECT * FROM calendrier WHERE utilisateur_id = ?";
        try (PreparedStatement statement = cnx2.prepareStatement(query)) {

            statement.setInt(1, medecinId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp debut = rs.getTimestamp("heure_debut");
                Timestamp fin = rs.getTimestamp("heure_fin");
                Utilisateur medecin = new Utilisateur(rs.getInt("utilisateur_id"));
                Calendrier calendrier = new Calendrier(id, debut, fin, medecin);
                calendrierList.add(calendrier);
            }
            return calendrierList;
        }
    }

    public int getDispoByDateTimeRange(Timestamp heureDebut, Timestamp heureFin) throws SQLException {
        String query = "SELECT id FROM calendrier WHERE heure_debut = ? AND heure_fin = ?";
        PreparedStatement statement = cnx2.prepareStatement(query);
        statement.setTimestamp(1, heureDebut);
        statement.setTimestamp(2, heureFin);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            return -1; // or throw an exception, depending on your needs
        }
    }

}
