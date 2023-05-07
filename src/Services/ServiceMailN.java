/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entity.Consultation;
import Entity.MedicamentN;
import Entity.OrdonnanceN;

import Entity.MedicamentN;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author bytesudoer
 */
public class ServiceMailN {

    public void ServiceMailN() {
    }

    public void sendMail(String to, String subject, String text) {
        String senderMail = "healthified.consultation.module@gmail.com";
        String senderPassword = "cqdebkknidkqytzj";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderMail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            // Sending the message
            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void notifierValidite(OrdonnanceN o, Consultation c, MedicamentN m) {
        Date dateConsultation = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
        int validite = o.getValidite();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateConsultation);
        calendar.add(Calendar.DAY_OF_MONTH, validite);
        Date dateLimite = calendar.getTime();

        String subject = "Rappel Ordonnance : " + formatter.format(dateLimite);

        String textMail = "\n"
                + "Votre médecin a affecté une ordonnance à votre dernière consultation." + "\n"
                + "\n"
                + "\n"
                + "Notification Ordonnance\n"
                + "Identifiant Consultation : " + c.getDateConsultation() + "\n"
                + "Matricule Medecin : " + c.getMatriculeMedecin() + "\n"
                + "Identifiant Patient : " + c.getIdPatient() + "\n"
                + "Date Consultation : " + c.getDateConsultation().toString() + "\n"
                + "Liste des Medicaments : " + "\n"
                + "--------------------------------------------------------------------\n"
                + "Nom               |         Prix        |                Description\n"
                + m.getNom() + "\t\t\t" + m.getPrix() + "\t\t\t" + m.getDescription() + "\n"
                + "\n"
                + "\n"
                + "Votre Ordonnance Sera valable jusqu'a " + formatter.format(dateLimite)
                + "\n"
                + "Généré le " + LocalDate.now().toString();
        this.sendMail("mohamednour.soussi@esprit.tn", subject, textMail);
    }

}
