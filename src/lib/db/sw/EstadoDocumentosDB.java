package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.EstadoDocumentos;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class EstadoDocumentosDB {

	
public static ArrayList<EstadoDocumentos> getEstadoDocumentosWithFilter(ArrayList<filterSql> filter) throws SQLException{
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<EstadoDocumentos> lista = new ArrayList<EstadoDocumentos>();

		try {
			
			sql = " "
					+ " SELECT T1.documento, T1.codigo as codTrabajador, T1.idContrato, T1.periodo,"
				    + " CASE WHEN ed.impreso IS NULL THEN 0 ELSE ed.impreso END as impreso, "
				    + " CASE WHEN ed.firmado IS NULL THEN 0 ELSE ed.firmado END as firmado, "
				    + " CASE WHEN ed.entregado IS NULL THEN 0 ELSE ed.entregado END as entregado "
					+ " FROM ( SELECT DISTINCT "
					+ "			concat('Contrato_',tr.codigo,'_',ct.id) as documento, "
					+ "			tr.codigo as codigo, ct.id as idContrato, "
					+ "         '' as periodo "
					+ "			FROM trabajadores tr "
					+ "			INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador) "
					+ "			INNER JOIN sw_liquidacion l ON (ct.id = l.id_contrato) "
					+ " UNION ALL "
					+ " SELECT DISTINCT "
					+ "			concat('Liquiacion_',tr.codigo,'_',l.periodo,'_',l.id_contrato ) as documento, "
					+ "			tr.codigo as codigo, ct.id as idContrato, "
					+ "            l.periodo "
					+ "			FROM trabajadores tr "
					+ "			INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador) "
					+ "			INNER JOIN sw_liquidacion l ON (ct.id = l.id_contrato) ) AS T1 "
					+ "         LEFT JOIN sw_estadodocumentos ed ON ( CAST(T1.documento AS CHAR(50)) = ed.documento ) ";
			
			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
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
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				EstadoDocumentos EstadoDocumentos = new EstadoDocumentos();

				EstadoDocumentos.setDocumento(rs.getString("documento"));
				EstadoDocumentos.setCodTrabajador(rs.getString("codTrabajador"));
				EstadoDocumentos.setIdContrato(rs.getString("idContrato"));
				EstadoDocumentos.setPeriodo(rs.getString("periodo"));
				EstadoDocumentos.setImpreso(rs.getString("impreso"));
				EstadoDocumentos.setEntregado(rs.getString("entregado"));
				EstadoDocumentos.setFirmado(rs.getString("firmado"));

				lista.add(EstadoDocumentos);

			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
		
	}

	public static String insertEstadoDocumentos(EstadoDocumentos EstadoDocumentos) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;
		
		try {

			int i = 1;
			sql = " SELECT idEstadoDocumentos FROM sw_estadoDocumentos WHERE idEstadoDocumentos = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setString(i++, EstadoDocumentos.getIdEstadoContrato());
			rs = ps.executeQuery();
			
			if (!rs.next()) {
				i = 1;
				rs.close();
				ps.close();
				
				sql = " INSERT INTO sw_estadoDocumentos (idEstadoDocumentos) VALUES (?) ";
				ps = db.conn.prepareStatement(sql);
				ps.setString(i++, EstadoDocumentos.getIdEstadoContrato());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if (rs.next()) {
					key = rs.getInt(1);
				}
				
				EstadoDocumentos.setIdEstadoContrato(key+"");
			}
			
			i = 1;
			rs.close();
			ps.close();
			
			sql = " UPDATE sw_estadoDocumentos SET "
					+ " firmado = ? , "
					+ " entregado = ? , "
					+ " firmado = ? "
					+ " WHERE idEstadoDocumentos = ? ";
			
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(i++, EstadoDocumentos.getFirmado());
			ps.setString(i++, EstadoDocumentos.getEntregado());
			ps.setString(i++, EstadoDocumentos.getImpreso());
			ps.setString(i++, EstadoDocumentos.getIdEstadoContrato());

			ps.execute();

			
		} catch (Exception e) {
			return "Error al Actualizar";
		} finally {
			ps.close();
			db.close();
		}
		return "Se Actualizo el Registro";
		
	}

	public static ArrayList<EstadoDocumentos> getAllEstadoDocumentos() {
		return null;
	}

	public static String deleteEstadoDocumentos(String id) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try {
			
			sql = " DELETE FROM sw_estadoDocumentos WHERE idEstadoDocumentos = "+id;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			
		}catch(Exception e){
			return "No Se Elimino";
		}finally {
			ps.close();
			db.conn.close();
		}
		
		return "Eliminado";
		
	}
	
	
	
	
	
}
