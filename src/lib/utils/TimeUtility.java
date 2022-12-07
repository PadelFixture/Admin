package lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Metodos relacionados con conversion de tiempo
 * @author Juan_
 *
 */
public class TimeUtility {

	/**
	 * @param decimalHora - String
	 * @return String
	 * @throws String "00:00"
	 */
	public static String convertDecimalTimeToHours(String decimalHora){

		try {
			
			double doubleHora = Double.parseDouble(decimalHora);
			double taktTime = doubleHora;
			long timeInMilliSeconds = (long) Math.floor(taktTime * 60 * 1000);
			Date date = new Date(timeInMilliSeconds);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			return sdf.format(date);
		
		} catch (Exception e) {
			return "00:00";
		}
	
	}
	
	public static boolean isDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (Exception pe) {
            return false;
        }
        return true;
    }
	
	/**
	 * Retorna String Convertido en YYYY-MM-DD Si el valor es null o vacio
	 * retorna null
	 * 
	 * @param fecha
	 * @return String
	 * @throws ParseException
	 */
	public static String convertStringToDDMMYYYY(String fecha) throws ParseException {

		if (fecha == null || fecha.isEmpty()) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = output.parse(fecha.replace("/", "-"));

		if (fecha.equals(output.format(date))) {
			return fecha;
		}

		java.util.Date data = sdf.parse(fecha.replace("/", "-"));
		String formattedDate = output.format(data);

		return formattedDate;

	}
	
	/**
	 * Retorna String Convertido en YYYYMM. Si el valor es null o vacio
	 * retorna null
	 * 
	 * @param fecha
	 * @return String
	 * @throws ParseException
	 */
	public static String convertStringToYYYYMM(String fecha) throws ParseException {

		try {
			
			if (fecha == null || fecha.isEmpty()) {
				return null;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat output = new SimpleDateFormat("yyyyMM");
			java.util.Date date = output.parse(fecha);

			if (fecha.equals(output.format(date))) {
				return fecha;
			}

			java.util.Date data = sdf.parse(fecha);
			String formattedDate = output.format(data);

			return formattedDate;
			
		} catch (Exception e) {
			return null;
		}

	}
	
	
}
