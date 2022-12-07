package lib.db.sw;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class ActSueldoMinimoDB {

	public static String modificarSueldoMinimo(double sueldoMinimo, double nuevoSueldo, ArrayList<filterSql> filter) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try {

			sql =  " UPDATE contratos SET sueldoBase = ? WHERE id IN "
				  +" ( SELECT id FROM (SELECT * FROM contratos) AS ct " 
				  +" WHERE ct.fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM (SELECT * FROM contratos) AS cntt "
				  +" WHERE ct.codigo_trabajador = cntt.codigo_trabajador ) "
				  +" AND EstadoContrato = 1 AND partTime != 1 AND sueldoBase = ? ) ";
			
				// Si contiene datos asignarlo al WHERE
					if (filter.size() > 0) {
						String andSql = "";
						andSql += " AND ";
						Iterator<filterSql> f = filter.iterator();

						while (f.hasNext()) {
							filterSql row = f.next();

							if (!row.getValue().equals("")) {

								if (row.getCampo().endsWith("_to")) {

									SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
									SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
									sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
											+ sqlDate.format(formatter.parse(row.getValue())) + "'";
								}

								else if (row.getCampo().endsWith("_from")) {

									SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
									SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
									sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
											+ sqlDate.format(formatter.parse(row.getValue())) + "'";
								}
								else if (GeneralUtility.isArray(row.getValue())){
									sql += andSql + row.getCampo() + " in ( "+GeneralUtility.convertJSONArrayToArray(row.getValue())+" ) ";
								}
								else if (GeneralUtility.isNumeric(row.getValue())){
									sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
								}
								else{
									sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
								}
								andSql = " and ";
							}
						} // Fin While

					}
					

			ps = db.conn.prepareStatement(sql);

			ps.setDouble(i++, nuevoSueldo);
			ps.setDouble(i++, sueldoMinimo);

			ps.execute();

			return "Se Modifico el SueldoMinimo";

		} catch (Exception e) {
			return "No se puedo Modificar el SueldoMinimo por: " + e.getMessage();
		} finally {
			ps.close();
			db.close();
		}

	}

}
