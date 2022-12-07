package lib.db.sw;

import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.SASW.parametrosDB;

public class EtniaDB extends parametrosDB{
	public static ArrayList<parametros> getEtnias() throws Exception
	{
		return getParamsByCode("ETNIA");
	}
	public static parametros getEtniaById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("ETNIA", id);
	}
	public static boolean deleteEtniaById(String id) throws SQLException
	{
		return deleteParById("ETNIA", id);
	}
}