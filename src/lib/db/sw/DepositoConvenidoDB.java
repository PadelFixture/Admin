package lib.db.sw;

import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.SASW.parametrosDB;

public class DepositoConvenidoDB extends parametrosDB{
	public static ArrayList<parametros> getDepositosConvenidos() throws Exception
	{
		return getParamsByCode("DEPOSITO_CONVENIDO");
	}
	public static parametros getDepositoConvenidoById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("DEPOSITO_CONVENIDO", id);
	}
	public static boolean deleteDepositoConvenidoById(String id) throws SQLException
	{
		return deleteParById("DEPOSITO_CONVENIDO", id);
	}
}
