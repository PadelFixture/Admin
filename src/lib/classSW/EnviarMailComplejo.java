package lib.classSW;

import java.util.Properties;

import javax.activation.DataHandler;

import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviarMailComplejo {

	public static void main(String[] args) {

		final String username = "jose.lui.rod.gon@gmail.com";
		final String password = "ferroazulagu23";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			BodyPart texto = new MimeBodyPart();
			texto.setText("Texto del mensaje");
	 
		        BodyPart adjunto = new MimeBodyPart();
		        adjunto.setDataHandler(new DataHandler(new FileDataSource("C:/temp/NominaAnticipos/totalbanco/ArchivoTotalbanco0-27-21.csv")));
		        adjunto.setFileName("ArchivoTotalbanco0-27-21.csv");
		        
		        MimeMultipart multiParte = new MimeMultipart();

		        multiParte.addBodyPart(texto);
		        multiParte.addBodyPart(adjunto);
		        multiParte.addBodyPart(adjunto);
		        
		        MimeMessage message = new MimeMessage(session);

		     // Se rellena el From
		     message.setFrom(new InternetAddress("yo@yo.com"));

		     // Se rellenan los destinatarios
		     message.addRecipient(Message.RecipientType.TO, new InternetAddress("jose.lui.rod.gon@gmail.com"));

		     // Se rellena el subject
		     message.setSubject("Confirmación Nomomina Pago Anticipos");

		     // Se mete el texto y la foto adjunta.
		     message.setContent(multiParte);
		     
		     Transport t = session.getTransport("smtp");
		     t.connect("jose,lui.rod.gon@gmail.com","ferroazulagu23");
		     t.sendMessage(message,message.getAllRecipients());
		     t.close();
		        
		        System.out.println("Sending");
	 
		        Transport.send(message);
	 
		        System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}