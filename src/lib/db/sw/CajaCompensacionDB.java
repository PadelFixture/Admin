package lib.db.sw;

import lib.db.SASW.parametrosDB;

import java.sql.SQLException;
import java.util.ArrayList;
import lib.ClassSASW.parametros;

public class CajaCompensacionDB extends parametrosDB{
	public static ArrayList<parametros> getCajasCompensacion() throws Exception
	{
		return getParamsByCode("CAJA_COMPENSACION");
	}
	public static parametros getCajaCompensacionById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("CAJA_COMPENSACION", id);
	}
	public static boolean deleteCajaCompensacionById(String id) throws SQLException
	{
		return deleteParById("CAJA_COMPENSACION", id);
	}
}