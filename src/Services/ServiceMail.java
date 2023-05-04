/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Entity.Consultation;
import Entity.Facteur;
import Entity.FicheAssurance;
import Entity.Medicament;
import Entity.Ordonnance;
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
public class ServiceMail {
	public void ServiceMail()
	{
	}

	public void sendMail(String to,String subject,String text)
	{
		String senderMail = "healthified.consultation.module@gmail.com";
		String senderPassword = "cqdebkknidkqytzj";
		Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(senderMail,senderPassword);
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

	public void notifierFacteur(String to,Facteur o)
	{

		String subject = "Factur : ";

		String textMail = "\n"
				+ "\n"
				+ "\n"
				+ "Identifiant  : "+o.getCin()+"\n"
				+ "NOM: "+o.getNom()+"\n"
				+ "PRNOM : "+o.getPrenom()+"\n"
				+ " ID Patient : "+o.getId_patient()+"\n"
				+ "Liste des Medicaments : "+"\n"
				+ "--------------------------------------------------------------------\n"
				+ "Nom               |         Dosage     |     Prix\n"
				+ o.getNom_med()+"\t\t\t" + o.getDosage()+"\t\t\t"+o.getPrix()+"\n"
				+ "\n"
				+ "\n"
				
			+"\n"
				+ "Généré le "+LocalDate.now().toString();
		this.sendMail(to, subject, textMail);
	}
	
public void notifierAssurance(String to,FicheAssurance o)
	{

		String subject = "FicheAssurance : ";

		String textMail = "\n"
				+ "\n"
				+ "\n"
				+ "Identifiant  : "+o.getCin()+"\n"
				+ "NOM: "+o.getNom()+"\n"
				+ "PRNOM : "+o.getPrenom()+"\n"
				+ " cnam : "+o.getMatricule_cnam()+"\n"
				+ " : "+"\n"
				+ "--------------------------------------------------------------------\n"
				+ "fiscal               |         Designation     |     Prix\n"
				+ o.getMatricule_fiscal()+"\t\t\t" + o.getDesignation()+"\t\t\t"+o.getTotal()+"\n"
				+ "\n"
				+ "\n"
				
			+"\n"
				+ "Généré le "+LocalDate.now().toString();
		this.sendMail(to, subject, textMail);
	}

}

