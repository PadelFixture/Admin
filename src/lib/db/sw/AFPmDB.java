package lib.db.sw;

import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.SASW.parametrosDB;


public class AFPmDB  extends parametrosDB{
	public static ArrayList<parametros> getAFPms() throws Exception
	{
		return getParamsByCode("AFP");
	}
	public static parametros getAFPmById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("AFP", id);
	}
	public static boolean deleteAFPmById(String id) throws SQLException
	{
		return deleteParById("AFP", id);
	}
}
