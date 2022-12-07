package lib.db.sw;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.json.XML;

public class EnvioMensaje {
	
	public static void main(String args[])throws Throwable {

		
//###################################################################################
//######## Límite diario de mensajes, por defecto hasta 50,000 sms / por día. #######
//######## Límite de mensajes por lote, por defecto hasta 10,000 sms / envío. #######
//###################################################################################
		
// consultar saldo mensajes
//		String url = "http://api.labsmobile.com/get/balance.php?username=jose.rodriguez@goplicity.com&password=FerroN1*";
//				
// consultar precios 
//		String url = "http://api.labsmobile.com/get/prices.php?username=jose.rodriguez@goplicity.com&password=FerroN1*&countries=CL";
//RESPUESTA     CL,56,Chile,0.606
		
// CONOCER ESTADOS DE LOS MENSAJES
// String url = "http://api.labsmobile.com/get/ack.php?username=jose.rodriguez@goplicity.com&password=FerroN1*&subid=*&msisdn=56950545738";


// msisdn =   Variable que incluye un destinatario de número móvil. El número debe incluir el código del país 
//            sin '+' ó '00'. Cada cuenta de cliente tiene un número máximo de msisdn por envío. Consulte los 
		    //términos de su cuenta para ver este límite.

// message =  El mensaje a enviar. La longitud máxima del mensaje es de 160 caracteres. Solo los caracteres en 
//            el alfabeto GSM 3.38 de 7 bits, que se encuentran al final de este documento, son válidos. De lo 
//            contrario, debe enviar ucs2variable.
		
// sender = Opcional. Remitente del mensaje no puede haber espacios en blanco
		
// si quiero agregar una fecha programable para el envio se agrega a la url ejemplo &scheduled=2012-11-07%20%17:34:00
		
		// envio mensajes masivos	
		
		String mes_ = "WULTU jose";
		String url = "https://api.labsmobile.com/get/send.php?"
				+ "username=jose.rodriguez@goplicity.com&"
				+ "password=FerroN1*&"
				+ "message="+mes_+"&"
				+ "msisdn=56988030153,56968457545&"
				+ "sender=WULTU";

		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
        System.out.println(responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		
		// convertir xml en json
		JSONObject xmlJSONObj = XML.toJSONObject(response.toString());
        System.out.println(xmlJSONObj);
		
		
		
	}
	
		
}
