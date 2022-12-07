package lib.db.sw;

import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.SASW.parametrosDB;

public class InstitucionDB extends parametrosDB{
	public static ArrayList<parametros> getInstituciones() throws Exception
	{
		return getParamsByCode("INSTITUCIONES");
	}
}
