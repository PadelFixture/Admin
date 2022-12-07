package lib.classSW;

import java.util.Properties;

import javax.activation.DataHandler;

import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//import org.junit.Test;

import SWDB.impexp_trabajador;
import SWDB.sw_NominaAnticiposDB;


public class EnviarMailNominaAnticipo {
	@SuppressWarnings("unused")
	private int numero;


	public  void EnviarMailAnticipos(int numero,String rutaArchivo,String NombreArchivo) throws Exception{
		
		this.numero = numero;
		
		NominaAnticipos from = sw_NominaAnticiposDB.getFromCorreoAnticipos();
	  	String CorreoFrom = from.getCorreo_from_nomina_anticipo();
	  	
	  	NominaAnticipos pass = sw_NominaAnticiposDB.getPasswordCorreoAnticipos();
	  	String CorreoPass = pass.getCorreo_contrasena();
	  	
	  	
	  	
		final String username = CorreoFrom;
		final String password = CorreoPass;
       
		 Properties props = new Properties();
		    props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.debug", "true");

			props.put("mail.smtp.ssl.trust", "mail.simpleagro.cl");
			props.put("mail.smtp.host", "mail.simpleagro.cl");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
		 
		    Session session = Session.getInstance(props, null);
		    session.setDebug(true);

		try {
			

			BodyPart texto = new MimeBodyPart();
		
			
		 	NominaAnticipos url = sw_NominaAnticiposDB.getURLNominaPago();
		  	String urlNomina = url.getUrlnomina();
		  	
			texto.setText("text/html; charset=utf-8");
			texto.setContent(
					"<h3>Señores Tesorería, envío solicitud para</h3>"
					+ "<h4><spand>Aprobación o Rechazo de Nómina de Pago.</spand></h4>"
							+ "<h3>Para Aprobar o rechazar haga click en el siguiente <a href='"+urlNomina+"?nomina="+numero+"'>link.</a></h3>"
									+ "<h3>Atte.,</h3>"
									+ "<h3><spand>Área de Remuneraciones</spand></h3>",
					"text/html; charset=utf-8");


			BodyPart adjunto2 = new MimeBodyPart();

			
			String ruta = rutaArchivo;
//			impexp_trabajador.updateNOmbreArchivoSW_nomina(ruta,numero);

			adjunto2.setDataHandler(
					new DataHandler(new FileDataSource(""+ruta+"")));
			adjunto2.setFileName(NombreArchivo);

			 Multipart multiParte = new MimeMultipart();
		     MimeMessage message = new MimeMessage(session);
		      
		     multiParte.addBodyPart(texto);
		     multiParte.addBodyPart(adjunto2);
		 
		      
				message.setFrom(new InternetAddress(username));

			NominaAnticipos co = sw_NominaAnticiposDB.getTOCorreoAnticipos();
		  	String CorreoTO = co.getCorreo_to_nomina_anticipo();
			
		  	  message.addRecipient(Message.RecipientType.TO,new InternetAddress(CorreoTO));
			  message.setSubject("Solicitud Confirmación Nómina Pago Anticipos");
			  message.setContent(multiParte);

			  Transport transport = session.getTransport("smtp"); 
		      transport.connect("mail.simpleagro.cl", username, password);
		      transport.sendMessage(message, message.getAllRecipients());
		      transport.close();
		 
		 
		    }catch(AddressException ae) {
		      ae.printStackTrace();
		    }catch(NoSuchProviderException nspe){
		      nspe.printStackTrace();
		    }catch(MessagingException me){
		      me.printStackTrace();
		    }
		
	}

}