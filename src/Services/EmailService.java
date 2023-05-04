/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

/**
 *
 * @author ASUS
 */
import Entity.Remboursement;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utils.MyConnection;

public class EmailService {

    public static void sendEmail(String recipient, Remboursement selectedRemboursement) {
        final String username = "healthified.consultation.module@gmail.com";
        final String password = "cqdebkknidkqytzj";
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.protocols","TLSv1.2");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // récupérer le nom, le prénom du patient et le nom de l'assurance
        String nomPatient = null;
        String prenomPatient = null;
        String nomAssurance = null;

        try {
            Connection conn = MyConnection.getInstance().getConnection();
           PreparedStatement ps = conn.prepareStatement("SELECT p.nom_patient, p.prenom, a.nom_assurance FROM patient p, assurance a, depot d, remboursement r WHERE d.id_depot = ? AND r.id_depot = d.id_depot AND d.id_assurance = a.id_assurance AND d.id_patient = p.id_patient");
            ps.setInt(1, selectedRemboursement.getIdDepot());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nomPatient = rs.getString("nom_patient");
                prenomPatient = rs.getString("prenom");
                nomAssurance = rs.getString("nom_assurance");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipient)
            );
            
            message.setSubject("Notification de remboursement");
            message.setText("Bonjour " + prenomPatient + " " + nomPatient + ",\n\n"
                    + "Nous vous informons que le montant de votre remboursement est de " + selectedRemboursement.getMontantRembourse() + " Dinars.\n\n"
                    + "Cordialement,\nL'équipe de " + nomAssurance + ".");

            Transport.send(message);

            System.out.println("Email sent to " + recipient + " successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    
    public String getEmailPatientById(int idPatient) {
    String email = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = MyConnection.getInstance().getConnection();
        String query = "SELECT email FROM patient WHERE id_patient = ?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, idPatient);
        rs = stmt.executeQuery();
        if (rs.next()) {
            email = rs.getString("email");
        }
   } catch (SQLException ex) {

            System.out.println(ex.getMessage());

        }
    return email;
}

}