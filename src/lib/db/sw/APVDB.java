package lib.db.sw;

import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.SASW.parametrosDB;

public class APVDB extends parametrosDB{
	
	public static ArrayList<parametros> getAPVs() throws Exception
	{
		return getParamsByCode("APV");
	}
	public static parametros getAPVById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("APV", id);
	}
	public static boolean deleteAPVById(String id) throws SQLException
	{
		return deleteParById("APV", id);
	}
	
	
}
