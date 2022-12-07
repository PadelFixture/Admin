package lib.db.sw;

import lib.db.SASW.parametrosDB;

import java.sql.SQLException;
import java.util.ArrayList;
import lib.ClassSASW.parametros;

public class BancoDB extends parametrosDB{
	public static ArrayList<parametros> getBancos() throws Exception
	{
		return getParamsByCode("BANCO");
	}
	public static parametros getBancoById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("BANCO", id);
	}
	public static boolean deleteBancoById(String id) throws SQLException
	{
		return deleteParById("BANCO", id);
	}
}