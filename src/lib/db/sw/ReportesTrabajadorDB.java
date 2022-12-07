package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import lib.classSW.ExcelSW;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class ReportesTrabajadorDB {

	
	public static ExcelSW getInformeTrabajadorDuplicados(ArrayList<filterSql> filter) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		ExcelSW excel = new ExcelSW();
		LinkedHashMap<String, ArrayList<String>> mapOfKeys = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<String> titulos = new ArrayList<>();
		
		
		try {

			sql = " SELECT T1.codigo_trabajador, T1.suma, " 
				 + "	tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno, tr.rut, tr.rutTemporal,  " 
				 + "	ct.id as codigo_contrato, ct.fechaInicio_actividad, ct.FechaTerminoContrato, "
				 + "	sc.denominacionSociedad, cmp.descripcion, ct.idCECOContrato, "
				 + "	CASE WHEN ct.EstadoContrato = 0 THEN 'INACTIVO' ELSE 'ACTIVO' END AS EstadoContrato "
				 + "	FROM trabajadores tr INNER JOIN ( "
				 + "	SELECT codigo_trabajador, COUNT(*) AS suma "
				 + "	FROM contratos ct "
				 + "	WHERE  ct.EstadoContrato = 1 "
				 + "	GROUP BY ct.codigo_trabajador "
				 + "	HAVING suma > 1 "
				 + "	order by suma desc "
				 + "	) AS T1 "
				 + "	ON (T1.codigo_trabajador = tr.codigo) "
				 + "	INNER JOIN contratos ct ON (ct.codigo_trabajador = tr.codigo) " 
				 + "	INNER JOIN sociedad sc ON (ct.idSociedad = sc.idSociedad) "
				 + "	INNER JOIN campo cmp ON (cmp.campo = ct.idHuertoContrato) ";

			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().endsWith("periodo")) {

							SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMM");
							sql += andSql + row.getCampo() + " ='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}

						else if (row.getCampo().endsWith("_date")) {

							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + row.getCampo() + " ='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}
						else if (GeneralUtility.isNumeric(row.getValue())){
							sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
						}
						else if (GeneralUtility.isArray(row.getValue())){
							sql += andSql + row.getCampo() + " in ( " + row.getValue() + " )";
						}
						else if (  row.getCampo().equals("sociedad")){
							sql += andSql + " sc."+row.getCampo() + " like '%" + row.getValue() + "%'";
						}
						else{
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						}

						andSql = " and ";
					}
				} // Fin While

			}

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			
			int count = metaData.getColumnCount();
			
			int row = 1;
			
			
			while (rs.next()) {
				String key = row+"";
				ArrayList<String> datos = new ArrayList<>();
				
				for (int i = 1; i <= count; i++) {
					datos.add(rs.getString(i));
				}
				
				 mapOfKeys.put(key, datos);
				 row++;
			}
			
			//Obtener los Titulos
			for (int i = 1; i <= count; i++) {
				titulos.add(metaData.getColumnName(i));
			}
			
			excel.setDatos(mapOfKeys);	
			excel.setTitulos(titulos);
		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
		}		
		
		return excel;
	}
	
	
	
}
