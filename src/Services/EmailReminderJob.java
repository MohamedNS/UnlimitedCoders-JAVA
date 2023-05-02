/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import Entity.RendezVous;
import Entity.Utilisateur;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import Services.RendezVousCrud;
import static Services.RendezVousCrud.cnx2;

public class EmailReminderJob implements Job {

    private final String username = "healthified.consultation.module@gmail.com";
    private final String password = "cqdebkknidkqytzj";

    private final String mailSubject = "Appointment Reminder";

    private final String mailBody = "This is a reminder for your appointment tomorrow ";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Get the current date and add one day to get tomorrow's date
        Timestamp tomorrow = new Timestamp(System.currentTimeMillis());
        Timestamp end = new Timestamp(System.currentTimeMillis() + 48 * 60 * 60 * 1000);
        // Get the list of rendezvous for tomorrow
        List<RendezVous> rdvList = new RendezVousCrud().getAllRendezVousForDate(tomorrow, end);
        System.out.println(rdvList);

        // Send a reminder email to each patient with a rendezvous
        for (RendezVous rdv : rdvList) {
            // Get the patient email address
            String patientEmail = rdv.getPatient().getEmail();
            String patientName = rdv.getPatient().getNom();
            String medecinName = rdv.getMedecin().getNom();
            Date dateRdv = rdv.getDate();
            System.out.println(patientEmail);

            // Create a new session and message object
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(patientEmail));
                message.setSubject(mailSubject);
                String htmlBody = "<html>"
                        + "<head>"
                        + "<style>"
                        + "body { text-align: center; }"
                        + "p { font-size: 14px; }"
                        + "span.date { color: blue; }"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "<img src='https://i.ibb.co/BTt0Y50/LogoApp.png' alt='logo' width='100' height='100'>"
                        + "<p>Dear " + patientName + ",</p>"
                        + "<p>" + mailBody + " with doctor " + medecinName + " at <span class='date'>" + dateRdv + "</span></p>"
                        + "</body>"
                        + "</html>";
                message.setContent(htmlBody, "text/html");

                // Send the message
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
