package lib.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumericUtility {

	public static String formatDoubleToStringWithoutDecimal(double monto){
		
		try{
		
		 DecimalFormat df = new DecimalFormat("#"); 
		 String formatted = df.format(monto);
		 
		 return formatted;
		
		}catch(Exception e){
			
			return monto+"";
			
			
		} 
		
	}
	
	
	public static String formatStringNumberWithDotAnd2Decimals(String numberString) {

		try{
		
		String number = numberString;
		double amount = Double.parseDouble(number);
		String numberFormated = String.format("%,.2f", amount);
		return numberFormated;

		}catch(Exception e){
			
			return "";
			
		}

	}
	
	
	public static String formatStringNumberWithTest(String numberString) {
	
		Locale currentLocale = Locale.getDefault();
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator(',');
		otherSymbols.setGroupingSeparator('.'); 
		DecimalFormat df = new DecimalFormat("###,###.##", otherSymbols);
		
	
		return df.format(Double.valueOf(numberString));
	
	}
	
	
	public static String formatStringNumberWithTest2(String numberString) {
		
		Locale currentLocale = Locale.getDefault();
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator(',');
		otherSymbols.setGroupingSeparator('.'); 
		DecimalFormat df = new DecimalFormat("###,###", otherSymbols);
		
	
		return df.format(Double.valueOf(numberString));
	
	}
	
}
