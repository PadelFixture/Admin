package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.RutNoAutorizados;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class RutNoAutorizadosDB {

	
	public static ArrayList<RutNoAutorizados> getRutNoAutorizadosWithFilter(ArrayList<filterSql> filter) throws SQLException{
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<RutNoAutorizados> lista = new ArrayList<RutNoAutorizados>();

		try {
			
			sql = " SELECT * FROM sw_m_rutNoAutorizados ";
			
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

				RutNoAutorizados rutAutorizados = new RutNoAutorizados();

				rutAutorizados.setIdRutNoAutorizados(rs.getInt("idRutNoAutorizados"));
				rutAutorizados.setAutorizados(rs.getString("autorizados"));
				rutAutorizados.setCodigoGenerado(rs.getString("codigoGenerado"));
				rutAutorizados.setRutNoAutorizados(rs.getString("rutNoAutorizados"));
				rutAutorizados.setUsuario(rs.getString("usuario"));

				lista.add(rutAutorizados);

			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
		
	}

	public static String insertRutNoAutorizados(RutNoAutorizados rutNoAutorizados) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;
		
		try {

			int i = 1;
			sql = " SELECT idRutNoAutorizados FROM sw_m_rutNoAutorizados WHERE idRutNoAutorizados = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, rutNoAutorizados.getIdRutNoAutorizados());
			rs = ps.executeQuery();
			
			if (!rs.next()) {
				i = 1;
				rs.close();
				ps.close();
				
				sql = " INSERT INTO sw_m_rutNoAutorizados (idRutNoAutorizados) VALUES (?) ";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(i++, rutNoAutorizados.getIdRutNoAutorizados());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if (rs.next()) {
					key = rs.getInt(1);
				}
				
				rutNoAutorizados.setIdRutNoAutorizados(key);
			}
			
			i = 1;
			rs.close();
			ps.close();
			
			sql = " UPDATE sw_m_rutNoAutorizados SET "
					+ " rutNoAutorizados = ? , "
					+ " autorizados = ? , "
					+ " usuario = ? , "
					+ " codigoGenerado = ?  "
					+ " WHERE idRutNoAutorizados = ? ";
			
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(i++, rutNoAutorizados.getRutNoAutorizados());
			ps.setString(i++, rutNoAutorizados.getAutorizados());
			ps.setString(i++, rutNoAutorizados.getUsuario());
			ps.setString(i++, rutNoAutorizados.getCodigoGenerado());
			ps.setInt(i++, rutNoAutorizados.getIdRutNoAutorizados());

			ps.execute();

			
		} catch (Exception e) {
			return "Error al Actualizar";
		} finally {
			ps.close();
			db.close();
		}
		return "Se Actualizo el Registro";
		
	}

	public static ArrayList<RutNoAutorizados> getAllRutNoAutorizados() {
		return null;
	}

	public static String deleteRutNoAutorizados(String id) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try {
			
			sql = " DELETE FROM sw_m_rutNoAutorizados WHERE idRutNoAutorizados = "+id;
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
