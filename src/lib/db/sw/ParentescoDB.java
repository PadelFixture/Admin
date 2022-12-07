package lib.db.sw;

import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.SASW.parametrosDB;

public class ParentescoDB extends parametrosDB{
	public static ArrayList<parametros> getParentescos() throws Exception
	{
		return getParamsByCode("PARENTESCO");
	}
	public static parametros getParentescoById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("PARENTESCO", id);
	}
	public static boolean deleteParentescoById(String id) throws SQLException
	{
		return deleteParById("PARENTESCO", id);
	}
	public static boolean createParentesco(parametros param, String string) throws SQLException {
		return createParam(param, string);
	}
}
