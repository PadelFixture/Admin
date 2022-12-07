package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

import lib.classSW.AnexoContrato;
import lib.db.ConnectionDB;

public class AnexoContratoDB {

	// Insert AnexoContrato
	public static boolean insertAnexoContrato(AnexoContrato AnexoContrato) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try {

			sql = "INSERT INTO sw_r_anexoContrato ( idAnexoContrato, idContrato, sueldoBase, "
					+ "horasSemanales, tipoContrato, EstadoContrato, "
					+ "fechaInicio_actividad, FechaTerminoContrato ,fechaCreacion, "
					+ "periodo, idTrabajador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, AnexoContrato.getIdAnexoContrato());
			ps.setInt(i++, AnexoContrato.getId());
			ps.setDouble(i++, AnexoContrato.getSueldoBase());
			ps.setInt(i++, AnexoContrato.getHorasSemanales());
			ps.setInt(i++, AnexoContrato.getTipoContrato());
			ps.setInt(i++, AnexoContrato.getEstado_contrato());
			ps.setString(i++, convertStringToYYYYMMDD(AnexoContrato.getFecha_inicio_actividad()));
			ps.setString(i++, convertStringToYYYYMMDD(AnexoContrato.getFecha_termino_actividad()));
			ps.setString(i++, convertStringToYYYYMMDD(AnexoContrato.getFechaCreacion()));
			ps.setString(i++, convertStringToYYYYMMDD(AnexoContrato.getPeriodo()));
			ps.setInt(i++, AnexoContrato.getIdTrabajador());

			

			ps.execute();

			return true;

		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.conn.close();
		}
		return false;
	}

	// Actualizar AnexoContrato
	public static boolean updateAnexoContrato(AnexoContrato AnexoContrato) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		int i = 1;

		try {

			sql = "UPDATE sw_r_anexoContrato SET idContrato = ?, sueldoBase = ?, horasSemanales = ?, "
					+ "tipoContrato = ?, EstadoContrato = ?, fechaInicio_actividad = ?, "
					+ "FechaTerminoContrato = ?, fechaCreacion = ?, periodo = ?, "
					+ "idTrabajador = ? WHERE (idAnexoContrato = ? ) ";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, AnexoContrato.getId());
			ps.setDouble(i++, AnexoContrato.getSueldoBase());
			ps.setInt(i++, AnexoContrato.getHorasSemanales());
			ps.setInt(i++, AnexoContrato.getTipoContrato());
			ps.setInt(i++, AnexoContrato.getEstado_contrato());
			ps.setString(i++, convertStringToYYYYMMDD(AnexoContrato.getFecha_inicio_actividad()));
			ps.setString(i++, convertStringToYYYYMMDD(AnexoContrato.getFecha_termino_actividad()));
			ps.setString(i++, convertStringToYYYYMMDD(AnexoContrato.getFechaCreacion()));
			ps.setString(i++, convertStringToYYYYMMDD(AnexoContrato.getPeriodo()));
			ps.setInt(i++, AnexoContrato.getIdTrabajador());

			ps.setInt(i++, AnexoContrato.getIdAnexoContrato());

			ps.execute();

			return true;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	// Borrar AnexoContrato por Id
	public static boolean deleteAnexoContratoById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {
			sql = " DELETE FROM sw_r_anexoContrato WHERE idAnexoContrato = " + id + "' ";

			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	// Obtener AnexoContrato por Id
	public static AnexoContrato getAnexoContratoById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		AnexoContrato tr = new AnexoContrato();

		try {
			sql = "SELECT * FROM sw_r_anexoContrato WHERE idAnexoContrato = '" + id + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				tr.setIdAnexoContrato(rs.getInt("idAnexoContrato"));
				tr.setId(rs.getInt("idContrato"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setHorasSemanales(rs.getInt("horasSemanales"));
				tr.setTipoContrato(rs.getInt("tipoContrato"));
				tr.setEstado_contrato(rs.getInt("EstadoContrato"));
				tr.setFecha_inicio_actividad(rs.getString("fechaInicio_actividad"));
				tr.setFecha_termino_actividad(rs.getString("FechaTerminoContrato"));
				tr.setFechaCreacion(rs.getString("fechaCreacion"));
				tr.setPeriodo(rs.getString("periodo"));
				tr.setIdTrabajador(rs.getInt("idTrabajador"));

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return tr;
	}

	// Obtener AnexoContrato por Id
	public static ArrayList<AnexoContrato> getAllAnexoContratoByIdTrabajador(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<AnexoContrato> lista = new ArrayList<AnexoContrato>();

		try {
			sql = "SELECT * FROM sw_r_anexoContrato WHERE idAnexoContrato = '" + id + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				AnexoContrato tr = new AnexoContrato();

				tr.setIdAnexoContrato(rs.getInt("idAnexoContrato"));
				tr.setId(rs.getInt("idContrato"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setHorasSemanales(rs.getInt("horasSemanales"));
				tr.setTipoContrato(rs.getInt("tipoContrato"));
				tr.setEstado_contrato(rs.getInt("EstadoContrato"));
				tr.setFecha_inicio_actividad(rs.getString("fechaInicio_actividad"));
				tr.setFecha_termino_actividad(rs.getString("FechaTerminoContrato"));
				tr.setFechaCreacion(rs.getString("fechaCreacion"));
				tr.setPeriodo(rs.getString("periodo"));
				tr.setIdTrabajador(rs.getInt("idTrabajador"));

				lista.add(tr);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	// Obtener AnexoContrato por Id
	public static ArrayList<AnexoContrato> getAnexoContratoByIdTrabajadorAndPeriodo(String id, String periodo)
			throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<AnexoContrato> lista = new ArrayList<AnexoContrato>();

		try {
			sql = "SELECT * FROM sw_r_anexoContrato WHERE idTrabajador = '" + id + "' AND periodo = '" + periodo + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				AnexoContrato tr = new AnexoContrato();

				tr.setIdAnexoContrato(rs.getInt("idAnexoContrato"));
				tr.setId(rs.getInt("idContrato"));
				tr.setSueldoBase(rs.getInt("sueldoBase"));
				tr.setHorasSemanales(rs.getInt("horasSemanales"));
				tr.setTipoContrato(rs.getInt("tipoContrato"));
				tr.setEstado_contrato(rs.getInt("EstadoContrato"));
				tr.setFecha_inicio_actividad(rs.getString("fechaInicio_actividad"));
				tr.setFecha_termino_actividad(rs.getString("FechaTerminoContrato"));
				tr.setFechaCreacion(rs.getString("fechaCreacion"));
				tr.setPeriodo(rs.getString("periodo"));
				tr.setIdTrabajador(rs.getInt("idTrabajador"));

				lista.add(tr);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	public static String getAnexoContratoByIdTrabajador(String idTrabajador) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		Gson gson = new Gson();
		String json = "";

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT periodo,idAnexoContrato FROM sw_r_anexoContrato WHERE idTrabajador = " + idTrabajador
					+ " order by periodo desc ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Integer idAnexoContrato = new Integer(rs.getInt("idAnexoContrato"));
				String periodo = rs.getString("periodo");

				map.put(idAnexoContrato, periodo);

			}

			json = gson.toJson(map);
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (Exception e) {

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getFechaBackByIdTrabajador: " + e.getMessage());

		} finally {
			db.close();
		}

		return json;

	}

	/**
	 * Retorna String Convertido en YYYY-MM-DD Si el valor es null o vacio retorna
	 * null
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
