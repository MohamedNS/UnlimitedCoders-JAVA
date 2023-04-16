/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;
import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author bytesudoer
 */
public class ServiceMail {

	public static void sendMail(String to,String subject,String text)
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


}
