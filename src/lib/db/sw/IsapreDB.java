package lib.db.sw;

import lib.db.SASW.parametrosDB;

import java.sql.SQLException;
import java.util.ArrayList;
import lib.ClassSASW.parametros;

public class IsapreDB extends parametrosDB{
	public static ArrayList<parametros> getIsapres() throws Exception
	{
		return getParamsByCode("ISAPRE");
	}
	public static parametros getIsapreById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("ISAPRE", id);
	}
	public static boolean deleteIsapreById(String id) throws SQLException
	{
		return deleteParById("ISAPRE", id);
	}
}