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
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 *
 * @author L390
 */
public class RendezVousCrud {
     public static Connection cnx2;

    public RendezVousCrud() {
        cnx2 = MyConnection.getInstance().getConnection();
    }
    
       public void ajouterRdv() throws SQLException {

        String requete = "INSERT INTO rendez_vous (date, utilisateur_id, patient_id, etat, description) VALUES ('2023-03-11 08:30:00', '6',' 8',' urgent', 'java') ";
        Statement st = cnx2.createStatement();
        st.executeUpdate(requete);

        System.out.println(" YAAY RendezVous ajoutée");

    }

    public void ajouterRdv2(RendezVous r) throws SQLException {

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
            rdv.setDescription(rs.getString("description"));
            rdv.setEtat(rs.getString("etat"));
            Utilisateur medecin = getUtilisateurById(rs.getInt("utilisateur_id"), cnx2);
            rdv.setMedecin(medecin);
            Utilisateur patient = getUtilisateurById(rs.getInt("patient_id"), cnx2);
            rdv.setPatient(patient);
            myList.add(rdv);
        }

        return myList;
    }

    public static Utilisateur getUtilisateurById(int id, Connection cnx) throws SQLException {
        String requete = "SELECT * FROM Utilisateur WHERE id = " + id;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            Utilisateur utilisateur = new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("role"),
                    rs.getString("email")
            );
            return utilisateur;
        } else {
            return null;
        }
    }

    public void supprimerRdv(int id) throws SQLException {

        String requete = "DELETE FROM rendez_vous WHERE id=?";
        PreparedStatement pst = cnx2.prepareStatement(requete);
        pst.setInt(1, id);
        int resultat = pst.executeUpdate();
        if (resultat == 1) {
            System.out.println("Rendez-vous supprimé avec succès");
        } else {
            System.out.println("Impossible de supprimer le rendez-vous");
        }

    }

    public void modifierRdv(int id, Date date, Utilisateur medecin, Utilisateur patient, String etat, String description) throws SQLException {

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

    }

    public void modifierRdv2(int id, Timestamp date, Utilisateur patient, String etat, String description) throws SQLException {

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
    }

    public List<RendezVous> getRendezVousByUtilisateurId(int utilisateurId) throws SQLException {
        List<RendezVous> myList = new ArrayList<>();

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
            Utilisateur medecin = getUtilisateurById(rs.getInt("utilisateur_id"), cnx2);
            rdv.setMedecin(medecin);
            Utilisateur patient = getUtilisateurById(rs.getInt("patient_id"), cnx2);
            rdv.setPatient(patient);
            myList.add(rdv);
        }

        return myList;
    }

    public boolean isRdvAlreadyReserved(int medecinId, Timestamp dateTime) throws SQLException {
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

    public List<RendezVous> getAllRendezVousOfDoctor(int utilisateurId) throws SQLException {
        List<RendezVous> myList = new ArrayList<>();

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

        return myList;
    }

    public List<RendezVous> getAllRendezVousOfPatient(int utilisateurId) throws SQLException {
        List<RendezVous> myList = new ArrayList<>();

        String requete = "SELECT * FROM rendez_vous WHERE patient_id = ?";
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

        return myList;
    }

    public RendezVous getRendezVousById(int id) throws SQLException {

        String requete = "SELECT * FROM rendez_vous WHERE id = ?";
        PreparedStatement pst = cnx2.prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            RendezVous rdv = new RendezVous();
            rdv.setId(rs.getInt("id"));
            rdv.setDate(rs.getTimestamp("date"));
            rdv.setDescription(rs.getString("description"));
            rdv.setEtat(rs.getString("etat"));
            rdv.setMedecin(new Utilisateur(rs.getInt("utilisateur_id")));
            rdv.setPatient(new Utilisateur(rs.getInt("patient_id")));
            return rdv;
        }
        return null;
       
    }

    public List<RendezVous> getAllRendezVousForDate(Timestamp start, Timestamp end) {
        List<RendezVous> myList = new ArrayList<>();
        try {

            String requete = "SELECT * FROM rendez_vous WHERE date BETWEEN ? AND ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setTimestamp(1, start);
            pst.setTimestamp(2, end);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                RendezVous rdv = new RendezVous();
                rdv.setId(rs.getInt(1));
                rdv.setDate(rs.getTimestamp("date"));
                rdv.setDescription(rs.getString("Description"));
                rdv.setEtat(rs.getString("Etat"));

                Utilisateur medecin = getUtilisateurById(rs.getInt("utilisateur_id"), cnx2);
                rdv.setMedecin(medecin);
                Utilisateur patient = getUtilisateurById(rs.getInt("patient_id"), cnx2);
                rdv.setPatient(patient);
                myList.add(rdv);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RendezVousCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myList;
    }

    public int[] getNumAppointmentsPerDay(int utilisateurId, LocalDate startDate) throws SQLException {
        int[] numAppointments = new int[7]; // initialize an array to store the number of appointments for each day of the week

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

        return numAppointments;
    }

    public int[] getNumAppointmentsPerWeekInMonth(int utilisateurId, LocalDate date) throws SQLException {
        int[] numAppointments = new int[5]; // initialize an array to store the number of appointments for each week in the month
        LocalDate firstDayOfMonth = date.withDayOfMonth(1); // get the first day of the month
        int numDaysInMonth = firstDayOfMonth.lengthOfMonth(); // get the number of days in the month
        LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(numDaysInMonth - 1); // get the last day of the month
        int weekIndex = 0; // initialize an index to keep track of the current week
        int[] appointmentsPerWeek = new int[7]; // initialize an array to store the number of appointments for each day of the week
        for (LocalDate currentDate = firstDayOfMonth; !currentDate.isAfter(lastDayOfMonth); currentDate = currentDate.plusDays(1)) {
            int dayOfWeek = currentDate.getDayOfWeek().getValue() - 1; // get the day of the week as an index in the array
            appointmentsPerWeek[dayOfWeek]++;
            if (dayOfWeek == 6) { // if the current day is Saturday
                numAppointments[weekIndex] = IntStream.of(appointmentsPerWeek).sum(); // sum the appointments for the current week
                appointmentsPerWeek = new int[7]; // reset the array for the next week
                weekIndex++;
            }
        }
        // handle the last week of the month, which may not be a full week
        if (weekIndex < 5) {
            numAppointments[weekIndex] = IntStream.of(appointmentsPerWeek).sum();
        }
        return numAppointments;
    }

    public int[] getNumAppointmentsPerMonthInYear(int utilisateurId, int year) throws SQLException {
        int[] numAppointments = new int[12]; // initialize an array to store the number of appointments for each month of the year

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1);

        System.out.println("Start date: " + startDate);
        System.out.println("End date: " + endDate);

        while (startDate.isBefore(endDate)) {
            int monthOfYear = startDate.getMonthValue() - 1;
            int[] appointmentsPerDay = getNumAppointmentsPerDay(utilisateurId, startDate);
            numAppointments[monthOfYear] += Arrays.stream(appointmentsPerDay).sum();
            System.out.println("Appointments in " + startDate.getMonth() + ": " + numAppointments[monthOfYear]);
            startDate = startDate.plusMonths(1);
        }

        return numAppointments;
    }

    public List<Timestamp> getAvailableTimesForDoctor(int doctorId) throws SQLException {
        List<Timestamp> availableTimes = new ArrayList<>();

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Loop through the next 3 days
        for (int i = 0; i < 3; i++) {
            // Get the start and end times for the current day
            LocalDateTime startOfDay = LocalDateTime.of(currentDate.plusDays(i), LocalTime.of(8, 0));
            LocalDateTime endOfDay = LocalDateTime.of(currentDate.plusDays(i), LocalTime.of(17, 0));

            // Add all available timeslots to the list of available times
            LocalDateTime currentTime = startOfDay;
            while (currentTime.isBefore(endOfDay)) {
                Timestamp currentTimestamp = Timestamp.valueOf(currentTime);
                availableTimes.add(currentTimestamp);
                currentTime = currentTime.plusMinutes(60);
            }

            String query = "SELECT date FROM rendez_vous WHERE utilisateur_id = ? AND date BETWEEN ? AND ?";
            PreparedStatement statement = cnx2.prepareStatement(query);
            statement.setInt(1, doctorId);
            statement.setTimestamp(2, Timestamp.valueOf(startOfDay));
            statement.setTimestamp(3, Timestamp.valueOf(endOfDay));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Timestamp appointmentTime = resultSet.getTimestamp("date");
                availableTimes.removeIf(time -> time.equals(appointmentTime));
            }
        }
        return availableTimes;
    }


}


