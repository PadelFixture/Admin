package lib.db.sw;

import lib.db.SASW.parametrosDB;

import java.sql.SQLException;
import java.util.ArrayList;
import lib.ClassSASW.parametros;

public class NacionalidadDB extends parametrosDB{
	public static ArrayList<parametros> getNacionalidades() throws Exception
	{
		return getParamsByCode("NACIONALIDAD");
	}
	public static parametros getNacionalidadById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("NACIONALIDAD", id);
	}
	public static boolean deleteNacionalidadById(String id) throws SQLException
	{
		return deleteParById("NACIONALIDAD", id);
	}
}
