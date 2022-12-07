package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import lib.classSW.TrabajadorPostulanteLicenciaAplicador;
import lib.db.ConnectionDB;

public class TrabajadorPostulanteLicenciaAplicadorDB {

	// insertar trabajador licencia donducir
	public static boolean insertTrabajadorPostulanteLicenciaAplicador(TrabajadorPostulanteLicenciaAplicador TrabajadorLicenciaAplicador)
			throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {
			sql = " INSERT into sw_trabajadorPostulanteLicenciaAplicador " + " VALUES (?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, TrabajadorLicenciaAplicador.getIdTrabajadorLicenciaAplicador());
			ps.setInt(2, TrabajadorLicenciaAplicador.getIdTrabajador());
			ps.setInt(3, TrabajadorLicenciaAplicador.getIdTipoLicenciaAplicador());
			ps.setString(4, convertStringToYYYYMMDD(TrabajadorLicenciaAplicador.getFechaVencimientoSAG()));
			ps.setString(5, TrabajadorLicenciaAplicador.getNumeroLicenciaSAG());

			ps.execute();
			return true;
		} catch (Exception e) {

			System.out.println("Error al ingresar Trabajador licencia conducir:" + e.getMessage());
			e.printStackTrace();

		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}// fin clase insert

	// get trabajador licencia conducir
	public static ArrayList<TrabajadorPostulanteLicenciaAplicador> getTrabajadorPostulanteLicenciaAplicador() throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<TrabajadorPostulanteLicenciaAplicador> lista = new ArrayList<TrabajadorPostulanteLicenciaAplicador>();

		try {
			sql = "select * from sw_trabajadorPostulanteLicenciaAplicador ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				TrabajadorPostulanteLicenciaAplicador TrabajadorLicenciaAplicador = new TrabajadorPostulanteLicenciaAplicador();

				TrabajadorLicenciaAplicador.setIdTrabajadorLicenciaAplicador(rs.getInt("idTrabajadorLicenciaAplicador"));
				TrabajadorLicenciaAplicador.setIdTrabajador(rs.getInt("idTrabajador"));
				TrabajadorLicenciaAplicador.setIdTipoLicenciaAplicador(rs.getInt("idTipoLicenciaAplicador"));
				TrabajadorLicenciaAplicador.setFechaVencimientoSAG(rs.getString("fechaVencimientoSAG"));
				TrabajadorLicenciaAplicador.setNumeroLicenciaSAG(rs.getString("numeroLicenciaSAG"));

				lista.add(TrabajadorLicenciaAplicador);

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}// fin metodo get

	public static TrabajadorPostulanteLicenciaAplicador getTrabajadorPostulanteLicenciaAplicadorByIdTrabajador(int id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		TrabajadorPostulanteLicenciaAplicador TrabajadorLicenciaAplicador = new TrabajadorPostulanteLicenciaAplicador();

		try {
			sql = "select * from sw_trabajadorPostulanteLicenciaAplicador " + " where idTrabajador = '" + id + "' ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				TrabajadorLicenciaAplicador.setIdTrabajadorLicenciaAplicador(rs.getInt("idTrabajadorLicenciaAplicador"));
				TrabajadorLicenciaAplicador.setIdTrabajador(rs.getInt("idTrabajador"));
				TrabajadorLicenciaAplicador.setIdTipoLicenciaAplicador(rs.getInt("idTipoLicenciaAplicador"));
				TrabajadorLicenciaAplicador.setFechaVencimientoSAG(rs.getString("fechaVencimientoSAG"));
				TrabajadorLicenciaAplicador.setNumeroLicenciaSAG(rs.getString("numeroLicenciaSAG"));

			}

		} catch (SQLException e) {
			System.out.println("TrabajadorPostulanteLicenciaAplicador:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("TrabajadorPostulanteLicenciaAplicador: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return TrabajadorLicenciaAplicador;

	}// fin metodo

	// update trabajador licencia Aplicador
	public static boolean updateTrabajadorPostulanteLicenciaAplicador(TrabajadorPostulanteLicenciaAplicador TrabajadorLicenciaAplicador)
			throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;

		try {

			int i = 1;

			sql = " SELECT idTrabajadorLicenciaAplicador FROM sw_trabajadorPostulanteLicenciaAplicador  WHERE idTrabajadorLicenciaAplicador = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, TrabajadorLicenciaAplicador.getIdTrabajadorLicenciaAplicador());
			rs = ps.executeQuery();

			if (!rs.next()) {
				i = 1;
				rs.close();
				ps.close();

				sql = " INSERT INTO sw_trabajadorPostulanteLicenciaAplicador (idTrabajadorLicenciaAplicador) VALUES (?) ";
				ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(i++, TrabajadorLicenciaAplicador.getIdTrabajadorLicenciaAplicador());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if (rs.next()) {
					key = rs.getInt(1);
				}

				TrabajadorLicenciaAplicador.setIdTrabajadorLicenciaAplicador(key);
			}

			i = 1;
			rs.close();
			ps.close();

			sql = " UPDATE sw_trabajadorPostulanteLicenciaAplicador SET  idTrabajador = ? , "
					+ " idTipoLicenciaAplicador = ? , fechaVencimientoSAG = ? , numeroLicenciaSAG = ? WHERE idTrabajadorLicenciaAplicador = ? ";

			ps = db.conn.prepareStatement(sql);

			//ps.setInt(i++, TrabajadorLicenciaAplicador.getIdTrabajadorLicenciaAplicador());
			ps.setInt(i++, TrabajadorLicenciaAplicador.getIdTrabajador());
			ps.setInt(i++, TrabajadorLicenciaAplicador.getIdTipoLicenciaAplicador());
			ps.setString(i++, convertStringToYYYYMMDD(TrabajadorLicenciaAplicador.getFechaVencimientoSAG()));
			ps.setString(i++, TrabajadorLicenciaAplicador.getNumeroLicenciaSAG());
			ps.setInt(i++, TrabajadorLicenciaAplicador.getIdTrabajadorLicenciaAplicador());

			ps.execute();

			return true;

		} catch (Exception e) {
			System.out.println("Error update Tabajador licencia aplicador: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}// fin metodo update

	public static boolean deleteTrabajadorPostulanteLicenciaAplicadorById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM sw_trabajadorPostulanteLicenciaAplicador WHERE idTrabajadorLicenciaAplicador=" + id;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			db.conn.close();
		}
	}// fin metodo eliminar

	/**
	 * Retorna String Convertido en YYYY-MM-DD Si el valor es null o vacio
	 * retorna null
	 * 
	 * @param fecha
	 * @return String
	 * @throws ParseException
	 */
	public static String convertStringToYYYYMMDD(String fecha) throws ParseException {

		if (fecha == null || fecha.isEmpty()) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = output.parse(fecha.replace("/", "-"));

		if (fecha.equals(output.format(date))) {
			return fecha;
		}

		java.util.Date data = sdf.parse(fecha.replace("/", "-"));
		String formattedDate = output.format(data);

		return formattedDate;

	}

}
