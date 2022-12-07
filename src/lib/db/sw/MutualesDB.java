package lib.db.sw;

import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.SASW.parametrosDB;

public class MutualesDB extends parametrosDB{
	public static ArrayList<parametros> getMutuales() throws Exception
	{
		return getParamsByCode("MUTUALES");
	}
	public static parametros getMutualById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("MUTUALES", id);
	}
	public static boolean deleteMutualById(String id) throws SQLException
	{
		return deleteParById("MUTUALES", id);
	}
}