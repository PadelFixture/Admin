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

import lib.classSW.TrabajadorPeriodo;
import lib.db.ConnectionDB;

public class TrabajadorPeriodoDB {

	// Insert TrabajadorPeriodo
	public static boolean insertTrabajadorPeriodo(TrabajadorPeriodo TrabajadorPeriodo) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try {

			sql = "INSERT INTO sw_r_trabajadorPeriodo ( idTrabajadorPeriodo, fechaBack, idTrabajador, "
					+ "idAFP, idIsapre, idMonedaPlan, " + "idMonedaAFP, idMonedaAdicionalAFP, valorPlan, "
					+ "valorAFP, valorAdicionalAFP, fechaAfiliacionAFP, " + "capacidades, subsidio, mayor11anos, "
					+ "agro, trabajadorAgricola, institucionAPV, " + "idMonedaAPV, valorDepositoAPV, valorAPV, "
					+ "nContrato, institucionConvenido, idMonedaConvenido, "
					+ "valorConvenido, periodo, periodoTrabajador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "								   ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "								   ?, ?, ?, ?, ?, ?, ?) ";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, TrabajadorPeriodo.getIdTrabajadorPeriodo());
			ps.setString(i++, convertStringToYYYYMMDD(TrabajadorPeriodo.getFechaBack()));
			ps.setInt(i++, TrabajadorPeriodo.getIdTrabajador());
			ps.setInt(i++, TrabajadorPeriodo.getIdAFP());
			ps.setInt(i++, TrabajadorPeriodo.getIdIsapre());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaPlan());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaAFP());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaAdicionalAFP());
			ps.setDouble(i++, TrabajadorPeriodo.getValorPlan());
			ps.setDouble(i++, TrabajadorPeriodo.getValorAFP());
			ps.setDouble(i++, TrabajadorPeriodo.getValorAdicionalAFP());
			ps.setString(i++, convertStringToYYYYMMDD(TrabajadorPeriodo.getFechaAfiliacionAFP()));
			ps.setInt(i++, TrabajadorPeriodo.getCapacidades());
			ps.setInt(i++, TrabajadorPeriodo.getSubsidio());
			ps.setInt(i++, TrabajadorPeriodo.getMayor11anos());
			ps.setInt(i++, TrabajadorPeriodo.getAgro());
			ps.setInt(i++, TrabajadorPeriodo.getTrabajadorAgricola());
			ps.setInt(i++, TrabajadorPeriodo.getInstitucionAPV());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaAPV());
			ps.setDouble(i++, TrabajadorPeriodo.getValorDepositoAPV());
			ps.setDouble(i++, TrabajadorPeriodo.getValorAPV());
			ps.setString(i++, TrabajadorPeriodo.getnContrato());
			ps.setInt(i++, TrabajadorPeriodo.getInstitucionConvenido());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaConvenido());
			ps.setDouble(i++, TrabajadorPeriodo.getValorConvenido());
			ps.setString(i++, TrabajadorPeriodo.getPeriodo());
			ps.setString(i++, convertStringToYYYYMMDD(TrabajadorPeriodo.getPeriodoTrabajador()));

			ps.execute();

			return true;

		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.conn.close();
		}
		return false;
	}

	// Actualizar TrabajadorPeriodo
	public static boolean updateTrabajadorPeriodo(TrabajadorPeriodo TrabajadorPeriodo) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		int i = 1;

		try {

			sql = "UPDATE sw_r_trabajadorPeriodo SET fechaBack = ?, idTrabajador = ?, idAFP = ?, "
					+ "idIsapre = ?, idMonedaPlan = ?, idMonedaAFP = ?, "
					+ "idMonedaAdicionalAFP = ?, valorPlan = ?, valorAFP = ?, "
					+ "valorAdicionalAFP = ?, fechaAfiliacionAFP = ?, capacidades = ?, "
					+ "subsidio = ?, mayor11anos = ?, agro = ?, "
					+ "trabajadorAgricola = ?, institucionAPV = ?, idMonedaAPV = ?, "
					+ "valorDepositoAPV = ?, valorAPV = ?, nContrato = ?, "
					+ "institucionConvenido = ?, idMonedaConvenido = ?, valorConvenido = ?, "
					+ "periodo = ?, periodoTrabajador = ? WHERE (idTrabajadorPeriodo = ? ) ";

			ps = db.conn.prepareStatement(sql);

			ps.setString(i++, convertStringToYYYYMMDD(TrabajadorPeriodo.getFechaBack()));
			ps.setInt(i++, TrabajadorPeriodo.getIdTrabajador());
			ps.setInt(i++, TrabajadorPeriodo.getIdAFP());
			ps.setInt(i++, TrabajadorPeriodo.getIdIsapre());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaPlan());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaAFP());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaAdicionalAFP());
			ps.setDouble(i++, TrabajadorPeriodo.getValorPlan());
			ps.setDouble(i++, TrabajadorPeriodo.getValorAFP());
			ps.setDouble(i++, TrabajadorPeriodo.getValorAdicionalAFP());
			ps.setString(i++, convertStringToYYYYMMDD(TrabajadorPeriodo.getFechaAfiliacionAFP()));
			ps.setInt(i++, TrabajadorPeriodo.getCapacidades());
			ps.setInt(i++, TrabajadorPeriodo.getSubsidio());
			ps.setInt(i++, TrabajadorPeriodo.getMayor11anos());
			ps.setInt(i++, TrabajadorPeriodo.getAgro());
			ps.setInt(i++, TrabajadorPeriodo.getTrabajadorAgricola());
			ps.setInt(i++, TrabajadorPeriodo.getInstitucionAPV());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaAPV());
			ps.setDouble(i++, TrabajadorPeriodo.getValorDepositoAPV());
			ps.setDouble(i++, TrabajadorPeriodo.getValorAPV());
			ps.setString(i++, TrabajadorPeriodo.getnContrato());
			ps.setInt(i++, TrabajadorPeriodo.getInstitucionConvenido());
			ps.setInt(i++, TrabajadorPeriodo.getIdMonedaConvenido());
			ps.setDouble(i++, TrabajadorPeriodo.getValorConvenido());
			ps.setString(i++, TrabajadorPeriodo.getPeriodo());
			ps.setString(i++, convertStringToYYYYMMDD(TrabajadorPeriodo.getPeriodoTrabajador()));
			ps.setInt(i++, TrabajadorPeriodo.getIdTrabajadorPeriodo());

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

	// Borrar TrabajadorPeriodo por Id
	public static boolean deleteTrabajadorPeriodoById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {
			sql = " DELETE FROM sw_r_trabajadorPeriodo WHERE idTrabajadorPeriodo = " + id + "' ";

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

	// Obtener TrabajadorPeriodo por Id
	public static TrabajadorPeriodo getTrabajadorPeriodoById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		TrabajadorPeriodo tr = new TrabajadorPeriodo();

		try {
			sql = "SELECT * FROM sw_r_trabajadorPeriodo WHERE idTrabajadorPeriodo = '" + id + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				tr.setIdTrabajadorPeriodo(rs.getInt("idTrabajadorPeriodo"));
				tr.setFechaBack(rs.getString("fechaBack"));
				tr.setIdTrabajador(rs.getInt("idTrabajador"));
				tr.setIdAFP(rs.getInt("idAFP"));
				tr.setIdIsapre(rs.getInt("idIsapre"));
				tr.setIdMonedaPlan(rs.getInt("idMonedaPlan"));
				tr.setIdMonedaAFP(rs.getInt("idMonedaAFP"));
				tr.setIdMonedaAdicionalAFP(rs.getInt("idMonedaAdicionalAFP"));
				tr.setValorPlan(rs.getDouble("valorPlan"));
				tr.setValorAFP(rs.getDouble("valorAFP"));
				tr.setValorAdicionalAFP(rs.getDouble("valorAdicionalAFP"));
				tr.setFechaAfiliacionAFP(rs.getString("fechaAfiliacionAFP"));
				tr.setCapacidades(rs.getInt("capacidades"));
				tr.setSubsidio(rs.getInt("subsidio"));
				tr.setMayor11anos(rs.getInt("mayor11anos"));
				tr.setAgro(rs.getInt("agro"));
				tr.setTrabajadorAgricola(rs.getInt("trabajadorAgricola"));
				tr.setInstitucionAPV(rs.getInt("institucionAPV"));
				tr.setIdMonedaAPV(rs.getInt("idMonedaAPV"));
				tr.setValorDepositoAPV(rs.getDouble("valorDepositoAPV"));
				tr.setValorAPV(rs.getDouble("valorAPV"));
				tr.setnContrato(rs.getString("nContrato"));
				tr.setInstitucionConvenido(rs.getInt("institucionConvenido"));
				tr.setIdMonedaConvenido(rs.getInt("idMonedaConvenido"));
				tr.setValorConvenido(rs.getDouble("valorConvenido"));
				tr.setPeriodo(rs.getString("periodo"));
				tr.setPeriodoTrabajador(rs.getString("periodoTrabajador"));

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return tr;
	}

	// Obtener TrabajadorPeriodo por Id
	public static ArrayList<TrabajadorPeriodo> getAllTrabajadorPeriodoByIdTrabajador(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<TrabajadorPeriodo> lista = new ArrayList<TrabajadorPeriodo>();

		try {
			sql = "SELECT * FROM sw_r_trabajadorPeriodo WHERE idTrabajadorPeriodo = '" + id + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				TrabajadorPeriodo tr = new TrabajadorPeriodo();

				tr.setIdTrabajadorPeriodo(rs.getInt("idTrabajadorPeriodo"));
				tr.setFechaBack(rs.getString("fechaBack"));
				tr.setIdTrabajador(rs.getInt("idTrabajador"));
				tr.setIdAFP(rs.getInt("idAFP"));
				tr.setIdIsapre(rs.getInt("idIsapre"));
				tr.setIdMonedaPlan(rs.getInt("idMonedaPlan"));
				tr.setIdMonedaAFP(rs.getInt("idMonedaAFP"));
				tr.setIdMonedaAdicionalAFP(rs.getInt("idMonedaAdicionalAFP"));
				tr.setValorPlan(rs.getDouble("valorPlan"));
				tr.setValorAFP(rs.getDouble("valorAFP"));
				tr.setValorAdicionalAFP(rs.getDouble("valorAdicionalAFP"));
				tr.setFechaAfiliacionAFP(rs.getString("fechaAfiliacionAFP"));
				tr.setCapacidades(rs.getInt("capacidades"));
				tr.setSubsidio(rs.getInt("subsidio"));
				tr.setMayor11anos(rs.getInt("mayor11anos"));
				tr.setAgro(rs.getInt("agro"));
				tr.setTrabajadorAgricola(rs.getInt("trabajadorAgricola"));
				tr.setInstitucionAPV(rs.getInt("institucionAPV"));
				tr.setIdMonedaAPV(rs.getInt("idMonedaAPV"));
				tr.setValorDepositoAPV(rs.getDouble("valorDepositoAPV"));
				tr.setValorAPV(rs.getDouble("valorAPV"));
				tr.setnContrato(rs.getString("nContrato"));
				tr.setInstitucionConvenido(rs.getInt("institucionConvenido"));
				tr.setIdMonedaConvenido(rs.getInt("idMonedaConvenido"));
				tr.setValorConvenido(rs.getDouble("valorConvenido"));
				tr.setPeriodo(rs.getString("periodo"));
				tr.setPeriodoTrabajador(rs.getString("periodoTrabajador"));

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

	// Obtener TrabajadorPeriodo por Id
	public static ArrayList<TrabajadorPeriodo> getTrabajadorPeriodoByIdTrabajadorAndPeriodo(String id, String periodo)
			throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<TrabajadorPeriodo> lista = new ArrayList<TrabajadorPeriodo>();

		try {
			sql = "SELECT * FROM sw_r_trabajadorPeriodo WHERE idTrabajador = '" + id + "' AND periodo = '" + periodo
					+ "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				TrabajadorPeriodo tr = new TrabajadorPeriodo();

				tr.setIdTrabajadorPeriodo(rs.getInt("idTrabajadorPeriodo"));
				tr.setFechaBack(rs.getString("fechaBack"));
				tr.setIdTrabajador(rs.getInt("idTrabajador"));
				tr.setIdAFP(rs.getInt("idAFP"));
				tr.setIdIsapre(rs.getInt("idIsapre"));
				tr.setIdMonedaPlan(rs.getInt("idMonedaPlan"));
				tr.setIdMonedaAFP(rs.getInt("idMonedaAFP"));
				tr.setIdMonedaAdicionalAFP(rs.getInt("idMonedaAdicionalAFP"));
				tr.setValorPlan(rs.getDouble("valorPlan"));
				tr.setValorAFP(rs.getDouble("valorAFP"));
				tr.setValorAdicionalAFP(rs.getDouble("valorAdicionalAFP"));
				tr.setFechaAfiliacionAFP(rs.getString("fechaAfiliacionAFP"));
				tr.setCapacidades(rs.getInt("capacidades"));
				tr.setSubsidio(rs.getInt("subsidio"));
				tr.setMayor11anos(rs.getInt("mayor11anos"));
				tr.setAgro(rs.getInt("agro"));
				tr.setTrabajadorAgricola(rs.getInt("trabajadorAgricola"));
				tr.setInstitucionAPV(rs.getInt("institucionAPV"));
				tr.setIdMonedaAPV(rs.getInt("idMonedaAPV"));
				tr.setValorDepositoAPV(rs.getDouble("valorDepositoAPV"));
				tr.setValorAPV(rs.getDouble("valorAPV"));
				tr.setnContrato(rs.getString("nContrato"));
				tr.setInstitucionConvenido(rs.getInt("institucionConvenido"));
				tr.setIdMonedaConvenido(rs.getInt("idMonedaConvenido"));
				tr.setValorConvenido(rs.getDouble("valorConvenido"));
				tr.setPeriodo(rs.getString("periodo"));
				tr.setPeriodoTrabajador(rs.getString("periodoTrabajador"));

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

	// Obtener TrabajadorPeriodo por Id
	public static ArrayList<TrabajadorPeriodo> getPreviousTrabajadorPeriodoByIdTrabajadorAndPeriodo(String id, String periodo)
			throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<TrabajadorPeriodo> lista = new ArrayList<TrabajadorPeriodo>();

		try {
			sql = "SELECT * FROM sw_r_trabajadorPeriodo WHERE idTrabajador = '" + id + "' AND periodo = '" + periodo
					+ "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				TrabajadorPeriodo tr = new TrabajadorPeriodo();

				tr.setIdTrabajadorPeriodo(rs.getInt("idTrabajadorPeriodo"));
				tr.setFechaBack(rs.getString("fechaBack"));
				tr.setIdTrabajador(rs.getInt("idTrabajador"));
				tr.setIdAFP(rs.getInt("idAFP"));
				tr.setIdIsapre(rs.getInt("idIsapre"));
				tr.setIdMonedaPlan(rs.getInt("idMonedaPlan"));
				tr.setIdMonedaAFP(rs.getInt("idMonedaAFP"));
				tr.setIdMonedaAdicionalAFP(rs.getInt("idMonedaAdicionalAFP"));
				tr.setValorPlan(rs.getDouble("valorPlan"));
				tr.setValorAFP(rs.getDouble("valorAFP"));
				tr.setValorAdicionalAFP(rs.getDouble("valorAdicionalAFP"));
				tr.setFechaAfiliacionAFP(rs.getString("fechaAfiliacionAFP"));
				tr.setCapacidades(rs.getInt("capacidades"));
				tr.setSubsidio(rs.getInt("subsidio"));
				tr.setMayor11anos(rs.getInt("mayor11anos"));
				tr.setAgro(rs.getInt("agro"));
				tr.setTrabajadorAgricola(rs.getInt("trabajadorAgricola"));
				tr.setInstitucionAPV(rs.getInt("institucionAPV"));
				tr.setIdMonedaAPV(rs.getInt("idMonedaAPV"));
				tr.setValorDepositoAPV(rs.getDouble("valorDepositoAPV"));
				tr.setValorAPV(rs.getDouble("valorAPV"));
				tr.setnContrato(rs.getString("nContrato"));
				tr.setInstitucionConvenido(rs.getInt("institucionConvenido"));
				tr.setIdMonedaConvenido(rs.getInt("idMonedaConvenido"));
				tr.setValorConvenido(rs.getDouble("valorConvenido"));
				tr.setPeriodo(rs.getString("periodo"));
				tr.setPeriodoTrabajador(rs.getString("periodoTrabajador"));

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

	public static String getTrabajadorPeriodoByIdTrabajador(String idTrabajador) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		Gson gson = new Gson();
		String json = "";

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT periodo,idTrabajadorPeriodo FROM sw_r_trabajadorPeriodo WHERE idTrabajador = " + idTrabajador
					+ " order by periodo desc ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Integer idTrabajadorPeriodo = new Integer(rs.getInt("idTrabajadorPeriodo"));
				String periodo = rs.getString("periodo");

				map.put(idTrabajadorPeriodo, periodo);

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
