package lib.db.sw;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.ClassSASW.parametros;
import lib.classSW.CargarTipodePago;
import lib.classSW.DatosTrabajadorFiniquito;
import lib.classSW.Finiquito;
import lib.classSW.HorasAsistencia;
import lib.classSW.InsertHD;
import lib.classSW.LiquidacionPeriodo;
import lib.classSW.ListaSociedad;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.TipoLicencia;
import lib.classSW.UpdateTrabajadorHD;
import lib.classSW.sociedad;
import lib.classSW.trabajadores;
import lib.db.ConexionBD;
import lib.db.ConnectionDB;

public class FiniquitosBD {

	private final static Logger LOG = LoggerFactory.getLogger(FiniquitosBD.class);

	public static Connection db;

	public List<sociedad> getSociedad() throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		List<sociedad> lista = new ArrayList<>();
		try {
			sql = "select * from sociedad";
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				sociedad sc = new sociedad();
				sc.setIdSociedad(rs.getInt("idSociedad"));
				sc.setSociedad(rs.getString("sociedad"));
				sc.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				lista.add(sc);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	// Obtener sociedad por Id
	public sociedad getSociedadById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";

		sociedad sc = new sociedad();

		try {
			sql = "SELECT * FROM sociedad WHERE idSociedad = '" + id + "'";
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				sc.setIdSociedad(rs.getInt("idSociedad"));
				sc.setSociedad(rs.getString("sociedad"));
				sc.setDenominacionSociedad(rs.getString("DenominacionSociedad"));

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return sc;
	}

	public List<CargarTipodePago> getTipoDivision() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		List<CargarTipodePago> lista = new ArrayList<>();
		try {
			sql = "select * from parametros where codigo = 'DIVISION_PERSONAL'  AND activo = 1  ORDER BY descripcion;";
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				CargarTipodePago cr = new CargarTipodePago();
				cr.setId(rs.getInt("id"));
				cr.setDescripcion(rs.getString("descripcion"));
				lista.add(cr);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	public List<CargarTipodePago> getTipoSubDivision() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		List<CargarTipodePago> lista = new ArrayList<>();
		try {
			sql = "select * from parametros where codigo = 'SUBDIVISION_PERSONAL'  AND activo = 1  ORDER BY descripcion;";
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				CargarTipodePago cr = new CargarTipodePago();
				cr.setId(rs.getInt("id"));
				cr.setDescripcion(rs.getString("descripcion"));
				lista.add(cr);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	public List<CargarTipodePago> getListaGrupo() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CargarTipodePago> lista = new ArrayList<>();
		try {
			sql = "select * from parametros where codigo = 'GRUPO'  AND activo = 1  ORDER BY descripcion;";
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				CargarTipodePago cr = new CargarTipodePago();
				cr.setId(rs.getInt("id"));
				cr.setDescripcion(rs.getString("descripcion"));
				lista.add(cr);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	public List<CargarTipodePago> getListaSubGrupo() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CargarTipodePago> lista = new ArrayList<>();
		try {
			sql = "select * from parametros where codigo = 'SUBGRUPO'  AND activo = 1  ORDER BY descripcion;";
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				CargarTipodePago cr = new CargarTipodePago();
				cr.setId(rs.getInt("id"));
				cr.setDescripcion(rs.getString("descripcion"));
				lista.add(cr);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	public String getIdSociedadXCodTrabajador(String codTrabajado) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		String id = "";
		try {
			sql = "SELECT cntt.idSociedad from contratos cntt "
					+ "inner join trabajadores tr on tr.codigo = cntt.codigo_trabajador " + "where tr.codigo = "
					+ codTrabajado + " and cntt.EstadoContrato = 1 ";

			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				id = rs.getString("idSociedad");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return id;
	}

	public Finiquito getFiniquitoByCodTrabajador(String codTrabajador) throws Exception {
		LOG.info("Iniciamos la busqueda de finiquito para codigo trabajador " + codTrabajador);
		PreparedStatement ps = null;
		String sql = "";
		Finiquito finiquito = new Finiquito();
		try {
			sql = "SELECT * FROM sw_finiquitos WHERE cod_trabajador = '" + codTrabajador + "' ";
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				finiquito.setCodTrabajador(rs.getInt("cod_trabajador"));
				finiquito.setRut(rs.getString("rut"));
				finiquito.setFechaTerminoContrato(rs.getString("fecha_termino_contrato"));
				finiquito.setArticuloTerminoContrato(rs.getInt("articulo_termino_contrato"));
				// finiquito.setIncisoTerminoContrato(rs.getInt("inciso_termino_contrato"));
				// finiquito.setLetraTerminoContrato(rs.getInt("letra_termino_contrato"));
				finiquito.setDescripcion(rs.getString("descripcion"));
				finiquito.setFechaPago(rs.getString("fecha_pago"));
				finiquito.setLugarPago(rs.getString("lugar_pago"));
				finiquito.setHoraPago(rs.getString("hora_pago"));
			}
			if (finiquito != null) {
				LOG.info("se encuentra " + finiquito.toString());
			}
		} catch (Exception e) {
			LOG.error("Ha ocurrido un error al actualizar finiquito del tipo ", e);
			e.printStackTrace();
		}
		return finiquito;
	}

	public ArrayList<LoadTrabajadorSociedad> getSociedaTrabAS(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select TR.codigo,TR.nombre,TR.apellidoPaterno,TR.apellidoMaterno,TR.rut, TR.division,TR.idSubDivision,TR.grupo,TR.idSubgrupo from contratos  CO inner join trabajadores TR on TR.codigo = CO.codigo_trabajador where idSociedad = "
					+ id + " and CO.EstadoContrato = 1 group by CO.codigo_trabajador ";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LoadTrabajadorSociedad e = new LoadTrabajadorSociedad();

				e.setCodigotrabajador(rs.getInt("codigo"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidoPaterno(rs.getString("apellidoPaterno"));
				e.setApellidoMaterno(rs.getString("apellidoMaterno"));
				e.setIddivision(rs.getInt("division"));
				e.setIdsubdivision(rs.getInt("idSubDivision"));
				e.setIdgrupo(rs.getInt("grupo"));
				e.setIdsubgrupo(rs.getInt("idSubgrupo"));
				e.setRut(rs.getString("rut"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}

	public static ArrayList<DatosTrabajadorFiniquito> getSociedaTrabAS2(String empr, String div, String subdiv,
			String gru, String subgru) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> data = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select distinct TR.codigo,TR.nombre,TR.apellidoPaterno,TR.apellidoMaterno,TR.rut, TR.division,TR.idSubDivision,TR.grupo,TR.idSubgrupo "
					+ "from contratos CO  join trabajadores TR on TR.codigo = CO.codigo_trabajador "
					+ "inner join sw_liquidacion swl on swl.cod_trabajador = CO.codigo_trabajador " + "where 1 = 1 ";

			if ("null".equals(empr)) {
			} else {
				sql += "and CO.idSociedad = " + empr + "";
			}
			if ("null".equals(div)) {
			} else {
				sql += " and TR.division = '" + div + "'";
			}
			if ("null".equals(subdiv)) {
			} else {
				sql += " and TR.idSubDivision = '" + subdiv + "'";
			}
			if ("null".equals(gru)) {
			} else {
				sql += " and TR.grupo = '" + gru + "'";
			}
			if ("null".equals(subgru)) {
			} else {
				sql += " and idSubgrupo = '" + subgru + "'";
			}

			sql += " and CO.EstadoContrato = 1 and CO.paraFiniquitar = 1 group by CO.codigo_trabajador;";

			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				DatosTrabajadorFiniquito e = new DatosTrabajadorFiniquito();

				e.setCodigo_trabajador(rs.getInt("codigo"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidoPaterno(rs.getString("apellidoPaterno"));
				e.setApellidoMaterno(rs.getString("apellidoMaterno"));
				e.setRut(rs.getString("rut"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}

	public List<LiquidacionPeriodo> obtenerLiquidacionPeriodo(LiquidacionPeriodo liquidacion) {
		// LOG.info("Iniciamos la busqueda de liquidacion para el trabajador {}
		// >> periodo {}",liquidacion.getCodTrabajador(),
		// liquidacion.getPeriodo());
		PreparedStatement ps = null;
		String sql = "";

		int cod = liquidacion.getCodTrabajador();
		int idcont = liquidacion.getIdContrato();
		int tipo_periodo = liquidacion.getPeriodo();

		List<LiquidacionPeriodo> lista = new ArrayList<>();
		try {

			sql = "select * from sw_liquidacionDetalle where periodo = " + tipo_periodo + " and codTrabajador = " + cod
					+ " and idContrato = " + idcont + "";

			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			LiquidacionPeriodo liq;
			while (rs.next()) {

				liq = new LiquidacionPeriodo();
				// liq.setCodTrabajador(liquidacion.getCodTrabajador());
				// liq.setIdContrato(liquidacion.getIdContrato());
				// liq.setPeriodo(liquidacion.getPeriodo());
				liq.setIdConcepto(rs.getInt("idConcepto"));
				liq.setConcepto(rs.getString("descripcion"));

				if (rs.getString("valor") == null) {
					liq.setValor("0");
				} else if (rs.getString("valor").indexOf("-") != -1) {
					liq.setValor("0");
				}

				else {

					double value = Double.parseDouble(rs.getString("valor"));
					int intu = (int) value;
					String numCadena = intu + "";
					liq.setValor(numCadena);

				}

				// if(liq.getValor() != null &&
				// !"".equalsIgnoreCase(liq.getValor())){
				// liq.setValorDouble(Double.parseDouble(liq.getValor().replace(".",
				// "").replace(",", ".")));
				// }
				lista.add(liq);
				//
			}

		} catch (Exception e) {
			LOG.error("Ha ocurrido un error al actualizar finiquito del tipo ", e);
			e.printStackTrace();
		}
		return lista;
	}

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

	public Integer obtenerTopeCalculoFiniquito(LiquidacionPeriodo liquidacion) {
		LOG.info("Iniciamos la busqueda del tope pago >> periodo {}", liquidacion.getPeriodo());
		String call = "";
		call = "select rhValor(16, ?) as tope ";
		String valor = "";
		Double tope = (double) 0;
		try (Connection db = ConexionBD.getConnection(); PreparedStatement stmt = db.prepareStatement(call)) {
			stmt.setString(1, liquidacion.getPeriodo() + "01");
			stmt.execute();

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				valor = rs.getString("tope");
				LOG.info("tope pago >> {}", valor);
				if (valor != null && !"".equalsIgnoreCase(valor)) {
					tope = Double.parseDouble(valor.replace(",", "."));
				}
			}
		} catch (Exception e) {
			LOG.error("Ha ocurrido un error al buscar tope >> del tipo ", e);
			e.printStackTrace();
		}
		LOG.info("Finalizamos la busqueda del tope pago >> tope {}", tope);
		return tope.intValue();
	}

	// obtener ultimos 3 sueldos Liquidos
	public static ArrayList<DatosTrabajadorFiniquito> getSueldoLiquido(int cod) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		PreparedStatement ps3 = null;
		String sql3 = "";
		PreparedStatement ps4 = null;
		String sql4 = "";

		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select valor, " + "(SELECT nombre FROM trabajadores WHERE codigo = " + cod + " ) AS nombre,"
					+ "(SELECT apellidoMaterno FROM trabajadores WHERE codigo = " + cod + " ) as appMaterno,"
					+ "(SELECT apellidoPaterno FROM trabajadores WHERE codigo = " + cod + " ) as appPaterno,"
					+ "(SELECT rut FROM trabajadores WHERE codigo = " + cod + " ) as rut,"
					+ "(SELECT articuloTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as articulo,"

					+ "(SELECT DATEDIFF(FechaTerminoContrato, fechaInicio_actividad) from contratos where  codigo_trabajador = "
					+ cod + ")as dias,"
					+ "(select sum(cantidadDiasSolicitud) from sw_m_solicitud_vacaciones where idContrato = "
					+ " (SELECT max(idContrato) FROM contratos where codTrabajador = " + cod
					+ ") group by idContrato) as diastomado,"
					+ "(SELECT idSociedad FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as empresa,"
					+ "(SELECT fechaPago FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as fechapago,"
					+ "(SELECT lugarPago FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as lugarpago,"
					+ "(SELECT horaPago FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as horapago,"

					+ "(SELECT incisoTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as inciso,"
					+ "(SELECT letraTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as letra,"
					+ "(SELECT aviso FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as aviso,"
					+ "(SELECT id FROM trabajadores WHERE codigo = " + cod + "  ) as id,"
					+ "(select denominacionSociedad from sociedad where idSociedad = "
					+ "(SELECT idSociedad FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + "))) as nombreSociedad,"
					+ "(SELECT descripcion FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as descripcion,"
					+ "(SELECT tipoContrato FROM SAN_CLEMENTE.contratos WHERE codigo_trabajador = " + cod + " and"
					+ "	id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as tipoContrato,"
					+ "(SELECT descripcion FROM parametros where codigo = 'TIPO_CONTRATO' and llave ="
					+ "(SELECT tipoContrato FROM SAN_CLEMENTE.contratos WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod
					+ "))) as nombretipo_contrato,"
					+ "(SELECT fechaInicio_actividad FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ " id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as fechaInicio,"
					+ " (SELECT FechaTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ " id=(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ")) as fechaTermino,"
					+ "CASE WHEN 1 = 1 THEN 'SUELDO'" + " ELSE '' "
					+ "END as nombreconcepto  from sw_liquidacionDetalle " + " where codTrabajador = " + cod + " "
					+ "and idConcepto = 1 order by periodo desc limit 3";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setValor(rs.getInt("valor"));
				cr.setNombre_concepto(rs.getString("nombreconcepto"));
				cr.setNombre(rs.getString("nombre"));
				cr.setApmaterno(rs.getString("appMaterno"));
				cr.setAppaterno(rs.getString("appPaterno"));
				cr.setRut(rs.getString("rut"));
				cr.setNombre_sociedad(rs.getString("nombreSociedad"));
				cr.setFecha_inicio(rs.getString("fechaInicio"));
				cr.setFecha_termino(rs.getString("fechaTermino"));
				cr.setId_trabajador(rs.getInt("id"));
				cr.setArticulo(rs.getInt("articulo"));
				cr.setInciso(rs.getInt("inciso"));
				cr.setLetra(rs.getInt("letra"));
				cr.setAviso(rs.getInt("aviso"));
				cr.setFechapago(rs.getString("fechapago"));
				cr.setLugarpago(rs.getString("lugarpago"));
				cr.setHorapago(rs.getString("horapago"));
				cr.setDias(rs.getString("dias"));
				cr.setDiastomado(rs.getInt("diastomado"));
				cr.setDescripcion(rs.getString("descripcion"));
				cr.setTipocontrato(rs.getInt("tipoContrato"));
				cr.setNombretipocontrato(rs.getString("nombretipo_contrato"));
				cr.setSociedad(rs.getInt("empresa"));
				lista.add(cr);
			}

			sql2 = "select sum(valor) as valor,periodo," + "CASE " + "WHEN 1 = 1 THEN 'ASIGNACIONES IMPONIBLES'"
					+ " ELSE '' " + "END as nombreconcepto  from sw_liquidacionDetalle " + "where codTrabajador = "
					+ cod + " and idConcepto  IN (4,5,6,7,8)" + "group by periodo desc limit 3 ";

			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs2 = ps2.executeQuery(sql2);

			while (rs2.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setValorasignacionesimponibles(rs2.getInt("valor"));
				cr.setNombre_concepto(rs2.getString("nombreconcepto"));

				lista.add(cr);
			}

			sql3 = "select valor, " + "CASE WHEN 1 = 1 THEN 'GRATIFICACION'" + " ELSE '' "
					+ "END as nombreconcepto  from sw_liquidacionDetalle " + " where codTrabajador = " + cod + " "
					+ "and idConcepto = 9 order by periodo desc limit 3";

			ps3 = db.conn.prepareStatement(sql3);
			ResultSet rs3 = ps3.executeQuery(sql3);

			while (rs3.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setValorgratificacion(rs3.getInt("valor"));
				cr.setNombre_concepto(rs3.getString("nombreconcepto"));

				lista.add(cr);
			}

			sql4 = "select sum(valor) as valor,periodo," + "CASE " + "WHEN 1 = 1 THEN 'NO IMPONIBLES'" + " ELSE '' "
					+ "END as nombreconcepto  from sw_liquidacionDetalle " + "where codTrabajador = " + cod
					+ " and idConcepto  IN (11,12,13,14,15,16,17,18,19)" + "group by periodo desc limit 3 ";

			ps4 = db.conn.prepareStatement(sql4);
			ResultSet rs4 = ps4.executeQuery(sql4);

			while (rs4.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setValornoimponible(rs4.getInt("valor"));
				cr.setNombre_concepto(rs4.getString("nombreconcepto"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	//////// LISTA DE ARTICULOS ///////////////////////////////
	public static ArrayList<parametros> getListadoArticulo() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros> lista = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from sw_m_articuloTerminoContrato";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {

				parametros cr = new parametros();

				cr.setId(rs.getInt("idArticuloTerminoContrato"));
				cr.setDescripcion(rs.getString("descripcion"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	//////// LISTA DE INCISO ///////////////////////////////
	public static ArrayList<parametros> getListadoInciso() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros> lista = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from sw_m_incisoTerminoContrato;";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {

				parametros cr = new parametros();

				cr.setId(rs.getInt("idIncisoTerminoContrato"));
				cr.setDescripcion(rs.getString("descripcion"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	//////// LISTA DE LETRA ///////////////////////////////
	public static ArrayList<parametros> getListadoLetra() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros> lista = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from sw_m_letraTerminoContrato;";
			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {

				parametros cr = new parametros();

				cr.setId(rs.getInt("idLetraTerminoContrato"));
				cr.setDescripcion(rs.getString("descripcion"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	//////// CALCULAR TOPE ///////////////////////////////
	public static ArrayList<DatosTrabajadorFiniquito> getTopeFecha(String fecha) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select rhValor(16,'" + fecha + "',0) as tope;";
			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {

				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setValor(rs.getInt("tope"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	public static ArrayList<DatosTrabajadorFiniquito> getFeriadosProporcionalMasivo(String fechaIEnviar, String fecha,
			String dias, int cod, int idcontrato, String periodot) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select null as id, null as periodo, null as tipo, 17 as codigo_hd, null as monto, null as codigo_trabajador, null as frecuencia, null as dias, null as fecha_inicio,"
					+ "null as fecha_termino, null as idContrato, null as llave_moneda,null as nombre_hd, null as proporcional,"
					+ "getFeriadosProporcionales(" + dias + ",'" + fecha
					+ "') as dias_proporcional,NULL AS gratificacion " + "UNION ALL SELECT " + "NULL AS id,"
					+ "NULL AS periodo," + "NULL AS tipo," + "20000 AS codigo_hd," + "NULL AS monto,"
					+ "NULL AS codigo_trabajador," + "NULL AS frecuencia," + "NULL AS dias," + "NULL AS fecha_inicio,"
					+ "NULL AS fecha_termino," + "NULL AS idContrato," + "NULL AS llave_moneda," + "NULL AS nombre_hd,"
					+ "NULL AS proporcional," + "getDifMes('" + fechaIEnviar + "', '" + fecha
					+ "') AS dias_proporcional," + "NULL AS gratificacion "

					+ "UNION ALL SELECT " + "NULL AS id," + "NULL AS periodo," + "NULL AS tipo," + "20001 AS codigo_hd,"
					+ "(select (SUM(valor) * 2.8)/3 from sw_liquidacionDetalle where codTrabajador = " + cod
					+ " and idContrato = " + idcontrato + " and idConcepto = 92 "
					+ "AND periodo <= DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 1 MONTH),'%Y%m') "
					+ "order by periodo desc) AS monto," + "NULL AS codigo_trabajador," + "NULL AS frecuencia,"
					+ "NULL AS dias," + "NULL AS fecha_inicio," + "NULL AS fecha_termino," + "NULL AS idContrato,"
					+ "NULL AS llave_moneda," + "(select " + "CASE "
					+ "WHEN sueldo_base = 1 THEN 'sueldo base + gratificacion' "
					+ "WHEN total_imponible = 1 THEN 'total imponible' " + "ELSE '' " + "END "
					+ "from tiempo_servido_empresa where fecha_desde <= now() and (fecha_hasta >= now() or fecha_hasta is null)) AS nombre_hd,"
					+ "NULL AS proporcional,"
					+ "(select dias from tiempo_servido_empresa where now() BETWEEN fecha_desde AND fecha_hasta) AS dias_proporcional, "
					+ "(select sum(valor)  from sw_liquidacionDetalle where codTrabajador = " + cod
					+ "  and periodo =  date_format(DATE_SUB(CURDATE(), INTERVAL 1 MONTH),  '%Y%m')  and idConcepto = 9) AS gratificacion "

					+ "UNION ALL SELECT " + "NULL AS id," + "NULL AS periodo," + "NULL AS tipo," + "20002 AS codigo_hd,"
					+ "CAST( " + "CAST( " + "(select " + "CASE "
					// liquidaciones = 1
					+ "WHEN " + "(SELECT " + "COUNT(valor) AS contador " + "FROM " + "sw_liquidacionDetalle " + "WHERE "
					+ "codTrabajador = " + cod + " AND idContrato = " + idcontrato + " " + "AND idConcepto = 2 "
					+ "AND periodo != DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 0 MONTH),'%Y%m')) = 1 "
					+ "THEN " + "CAST(((SELECT " + "CAST(valor AS SIGNED) " + "FROM " + "sw_liquidacionDetalle "
					+ "WHERE " + "codTrabajador = " + cod + " AND idContrato = " + idcontrato + " "
					+ "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 1 MONTH), " + "'%Y%m') "
					+ "AND idConcepto = 10)) / (SELECT " + "(SELECT " + "CAST(valor AS SIGNED) AS dias " + "FROM "
					+ "sw_liquidacionDetalle " + "WHERE " + "codTrabajador = " + cod + " AND idContrato = " + idcontrato
					+ " " + "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 1 MONTH), " + "'%Y%m') "
					+ "AND idConcepto = 2) " + ") " + "AS SIGNED) "
					// liquidaciones = 2
					+ "WHEN " + "(select count(valor) as contador  FROM sw_liquidacionDetalle WHERE codTrabajador = "
					+ cod + " " + "AND idContrato = " + idcontrato + "  and idConcepto = 2 "
					+ "AND periodo != DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 0 MONTH),'%Y%m')) " + "= 2 "
					+ "THEN " + "CAST(((SELECT " + "CAST(valor AS SIGNED) " + "FROM " + "sw_liquidacionDetalle "
					+ "WHERE " + "codTrabajador = " + cod + " " + "AND idContrato = " + idcontrato + " "
					+ "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 1 MONTH)," + "'%Y%m') "
					+ "AND idConcepto = 10) + (SELECT " + "CAST(valor AS SIGNED) " + "FROM " + "sw_liquidacionDetalle "
					+ "WHERE " + "codTrabajador = " + cod + " " + "AND idContrato = " + idcontrato + " "
					+ "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 2 MONTH)," + "'%Y%m') "
					+ "AND idConcepto = 10) ) / (SELECT " + "(SELECT " + "CAST(valor AS SIGNED) AS dias " + "FROM "
					+ "sw_liquidacionDetalle " + "WHERE " + "codTrabajador = " + cod + " " + "AND idContrato = "
					+ idcontrato + " " + "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 2 MONTH), "
					+ "'%Y%m') " + "AND idConcepto = 2)  + (SELECT  " + "CAST(valor AS SIGNED) AS dias " + "FROM "
					+ "sw_liquidacionDetalle " + "WHERE " + "codTrabajador = " + cod + " " + "AND idContrato = "
					+ idcontrato + " " + "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 1 MONTH),"
					+ "'%Y%m') " + "AND idConcepto = 2) " + ") " + "AS SIGNED) "
					// liquidaciones >= 3
					+ "WHEN " + "(select count(valor) as contador  FROM sw_liquidacionDetalle WHERE codTrabajador = "
					+ cod + " " + "AND idContrato = " + idcontrato + "  "
					+ "and idConcepto = 2 AND periodo != DATE_FORMAT(DATE_SUB('" + periodot
					+ "', INTERVAL 0 MONTH),'%Y%m')) " + ">= 3 " + "THEN " + "CAST(((SELECT " + "CAST(valor AS SIGNED) "
					+ "FROM " + "sw_liquidacionDetalle " + "WHERE " + "codTrabajador = " + cod + " AND idContrato = "
					+ idcontrato + " " + "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 1 MONTH), "
					+ "'%Y%m') " + "AND idConcepto = 10) + "

					// suma segundo mes hacia atras total haberes imponibles
					+ "(select " + "CASE " + "WHEN "
					+ "(SELECT  CAST(valor AS SIGNED) FROM sw_liquidacionDetalle  WHERE  codTrabajador = " + cod
					+ " AND idContrato = " + idcontrato + "  AND periodo = DATE_FORMAT(DATE_SUB('" + periodot
					+ "', INTERVAL 2 MONTH), '%Y%m') AND idConcepto = 10) " + "is null THEN 0 "
					+ "ELSE (SELECT  CAST(valor AS SIGNED) FROM sw_liquidacionDetalle  WHERE  codTrabajador = " + cod
					+ " AND idContrato = " + idcontrato + "  AND periodo = DATE_FORMAT(DATE_SUB('" + periodot
					+ "', INTERVAL 2 MONTH), '%Y%m') AND idConcepto = 10) " + "END ) "

					+ "+ (SELECT  " + "CAST(valor AS SIGNED) " + "FROM " + "sw_liquidacionDetalle " + "WHERE "
					+ "codTrabajador = " + cod + " and idContrato = " + idcontrato + " "
					+ "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 3 MONTH), " + "'%Y%m') "
					+ "AND idConcepto = 10)) / " + "(select (SELECT " + "CAST(valor AS SIGNED) AS dias " + "FROM "
					+ "sw_liquidacionDetalle " + "WHERE " + "codTrabajador = " + cod + " AND idContrato = " + idcontrato
					+ " " + "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 1 MONTH), " + "'%Y%m') "
					+ "AND idConcepto = 2) + "

					// suma segundo mes hacia atras dias
					+ "(select " + "CASE " + "WHEN "
					+ "(SELECT  CAST(valor AS SIGNED) FROM sw_liquidacionDetalle  WHERE  codTrabajador = " + cod
					+ " AND idContrato = " + idcontrato + "  AND periodo = DATE_FORMAT(DATE_SUB('" + periodot
					+ "', INTERVAL 2 MONTH), '%Y%m') AND idConcepto = 2) " + "is null THEN 0 "
					+ "ELSE (SELECT  CAST(valor AS SIGNED) FROM sw_liquidacionDetalle  WHERE  codTrabajador = " + cod
					+ " AND idContrato = " + idcontrato + "  AND periodo = DATE_FORMAT(DATE_SUB('" + periodot
					+ "', INTERVAL 2 MONTH), '%Y%m') AND idConcepto = 2) " + "END ) + "

					+ "(SELECT  " + "CAST(valor AS SIGNED) AS dias " + "FROM " + "sw_liquidacionDetalle " + "WHERE "
					+ "codTrabajador = " + cod + " AND idContrato = " + idcontrato + " "
					+ "AND periodo = DATE_FORMAT(DATE_SUB('" + periodot + "', INTERVAL 3 MONTH), " + "'%Y%m') "
					+ "AND idConcepto = 2)) " + "AS SIGNED) " + "ELSE '' " + "END AS monto " + ") "
					+ "AS CHAR (10000) CHARACTER SET UTF8) " + "AS SIGNED)," + "NULL AS codigo_trabajador,"
					+ "NULL AS frecuencia," + "NULL AS dias," + "NULL AS fecha_inicio," + "NULL AS fecha_termino,"
					+ "NULL AS idContrato," + "NULL AS llave_moneda," + "NULL AS nombre_hd," + "NULL AS proporcional,"
					+ "NULL AS dias_proporcional," + "NULL AS gratificacion "

					+ "UNION ALL " + "select id, periodo, tipo, codigo_hd, monto, codigo_trabajador, frecuencia, dias,"
					+ " fecha_inicio, fecha_termino, idContrato, llave_moneda,"
					+ "(select descripcion from SAN_CLEMENTE.sw_p_haberesDescuentos where codigo = codigo_hd) as nombre_hd,"
					+ " proporcional,null as dias_proporcional,NULL AS gratificacion  from sw_haberesDescuentosFiniquito "
					+ "where codigo_trabajador = " + cod + " and idContrato = (SELECT max(id) "
					+ "FROM contratos where codigo_trabajador = " + cod + ") and codigo_hd NOT IN (2006,2007,2008) "
					+ "UNION ALL " + "SELECT null as id, null as periodo, null as tipo,codigo_hd,sum(monto) as monto,"
					+ "null as codigo_trabajador, null as frecuencia,sum(dias), null as fecha_inicio,"
					+ "null as fecha_termino, null as idContrato, null as llave_moneda,null as nombre_hd,"
					+ "null as proporcional,null as dias_proporcional," + "NULL AS gratificacion "
					+ "from sw_haberesDescuentosFiniquito " + "where codigo_trabajador = " + cod + " "
					+ "and idContrato = (SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ") "
					+ "AND codigo_hd IN (2006,2007,2008) AND periodo = "
					+ "(select  date_format(FechaTerminoContrato,'%Y%m') " + "from contratos where codigo_trabajador = "
					+ cod + " and id = " + "(SELECT max(id) FROM contratos where codigo_trabajador = " + cod + "))"
					+ " group by codigo_hd "

					// SI TIENE DESCUENTO POR LENTES OPTICOS
					+ "UNION ALL SELECT " + "NULL AS id," + "NULL AS periodo," + "NULL AS tipo, "
					+ "20010 AS codigo_hd," + "(select monto - " + "(select sum(valor) from sw_liquidacionDetalle "
					+ "where codTrabajador = " + cod + "  and descripcion = 'LENTES OPTICOS' and idContrato = "
					+ idcontrato + ") "

					+ "from sw_haberesDescuentos " + "where codigo_trabajador = " + cod
					+ " and  idContrato = 668 and codigo_hd = 3024), " + "NULL AS codigo_trabajador, "
					+ "NULL AS frecuencia," + "NULL AS dias," + "NULL AS fecha_inicio," + "NULL AS fecha_termino,"
					+ "NULL AS idContrato," + "NULL AS llave_moneda," + "NULL AS nombre_hd," + "NULL AS proporcional,"
					+ "NULL AS dias_proporcional," + "NULL AS gratificacion ";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {

				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setProporcional(rs.getDouble("dias_proporcional"));
				cr.setMontoHD(rs.getDouble("monto"));
				cr.setTipoHD(rs.getString("tipo"));
				cr.setCodHD(rs.getInt("codigo_hd"));
				cr.setNombreHD(rs.getString("nombre_hd"));
				cr.setDiasint(rs.getDouble("dias"));
				cr.setExiste(0);

				lista.add(cr);
			}
			sql2 = "SELECT * FROM sw_detalleFiniquito  where codTrabajador = " + cod + " and idContrato = " + idcontrato
					+ "";

			ps2 = db.conn.prepareStatement(sql2);

			ResultSet rs2 = ps.executeQuery(sql2);
			int existeData2;
			if (!rs2.isBeforeFirst()) {
				System.out.println("No Data Found rs2"); // data not exist
				existeData2 = 0;
			} else {
				System.out.println("si Data Found rs2"); // data not exist
				existeData2 = 1;
			}
			if (existeData2 == 1) {
				while (rs2.next()) {

					DatosTrabajadorFiniquito cr2 = new DatosTrabajadorFiniquito();

					if (rs2.getInt("idConcepto") == 1) {
						lista.get(0).setFecha_termino(rs2.getString("descripcion"));
					}
					if (rs2.getInt("idConcepto") == 2) {
						lista.get(0).setFechapago(rs2.getString("descripcion"));
					}
					if (rs2.getInt("idConcepto") == 3) {
						lista.get(0).setDescripcion(rs2.getString("descripcion"));
					}
					if (rs2.getInt("idConcepto") == 4) {
						lista.get(0).setLugarpago(rs2.getString("descripcion"));
					}
					if (rs2.getInt("idConcepto") == 4) {
						lista.get(0).setLugarpago(rs2.getString("descripcion"));
					}
					if (rs2.getInt("idConcepto") == 5) {
						lista.get(0).setHorapago(rs2.getString("descripcion"));
					}
					if (rs2.getInt("idConcepto") == 6) {
						lista.get(0).setArticulo(rs2.getInt("valor"));
					}
					if (rs2.getInt("idConcepto") == 7) {
						lista.get(0).setInciso(rs2.getInt("valor"));
					}
					if (rs2.getInt("idConcepto") == 8) {
						lista.get(0).setLetra(rs2.getInt("valor"));
					}
					if (rs2.getInt("idConcepto") == 9) {
						lista.get(0).setTotal_finiquito(rs2.getInt("valor"));
					}
					if (rs2.getInt("idConcepto") == 10) {
						lista.get(0).setFeriadobasico(rs2.getDouble("valor"));
					}
					if (rs2.getInt("idConcepto") == 11) {
						lista.get(0).setFeriadoprogresivo(rs2.getDouble("valor"));
					}
					if (rs2.getInt("idConcepto") == 12) {
						lista.get(0).setFeriadoconvencional(rs2.getDouble("valor"));
					}
					if (rs2.getInt("idConcepto") == 13) {
						lista.get(0).setTotalferiadobpc(rs2.getDouble("valor"));
					}
					if (rs2.getInt("idConcepto") == 14) {
						lista.get(0).setDiastomado(rs2.getInt("valor"));
					}
					if (rs2.getInt("idConcepto") == 15) {
						lista.get(0).setSubtotal(rs2.getDouble("valor"));
					}
					if (rs2.getInt("idConcepto") == 16) {
						lista.get(0).setDiasinhabiles(rs2.getInt("valor"));
					}
					if (rs2.getInt("idConcepto") == 18) {
						lista.get(0).setAviso(rs2.getInt("valor"));
					}

					int codnew = rs2.getInt("idConcepto");
					System.out.println(codnew);
					if (codnew == 2006) {

						for (int i = 0; i < lista.size(); i++) {
							if (lista.get(i).getCodHD() == 2006) {
								lista.get(i).setMontoHD(rs2.getDouble("valor"));
								lista.get(i).setExiste(1);
								System.out.println("pase por aqui");
							}

						}
					}

					if (codnew == 2007) {

						for (int i = 0; i < lista.size(); i++) {
							if (lista.get(i).getCodHD() == 2007) {
								lista.get(i).setMontoHD(rs2.getDouble("valor"));
								lista.get(i).setExiste(1);
								System.out.println("pase por aqui");
							}

						}
					}

					if (codnew == 2008) {

						for (int i = 0; i < lista.size(); i++) {
							if (lista.get(i).getCodHD() == 2008) {
								lista.get(i).setMontoHD(rs2.getDouble("valor"));
								lista.get(i).setExiste(1);
								System.out.println("pase por aqui");
							}

						}
					}
					if (codnew == 17) {

						for (int i = 0; i < lista.size(); i++) {
							if (lista.get(i).getCodHD() == 17) {
								lista.get(i).setMontoHD(rs2.getDouble("valor"));
								lista.get(i).setExiste(1);
								System.out.println("pase por aqui");
							}

						}
					}
				}
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			ps2.close();
			db.close();
		}
		return lista;
	}

	//////// CALCULAR FERIADOS PROPORCIONALES
	//////// INDIVIDUAL///////////////////////////////
	public static ArrayList<DatosTrabajadorFiniquito> getFeriadosProporcionalIndividual(String fecha, String dias,
			int cod) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select null as id, null as periodo, null as tipo, null as codigo_hd, null as monto, null as codigo_trabajador, null as frecuencia, null as dias, null as fecha_inicio,"
					+ "null as fecha_termino, null as idContrato, null as llave_moneda,null as nombre_hd, null as proporcional,"
					+ "getFeriadosProporcionales(" + dias + ",'" + fecha + "') as dias_proporcional " + "UNION ALL "
					+ "select id, periodo, tipo, codigo_hd, monto, codigo_trabajador, frecuencia, dias,"
					+ " fecha_inicio, fecha_termino, idContrato, llave_moneda,"
					+ "(select descripcion from SAN_CLEMENTE.sw_p_haberesDescuentos where codigo = codigo_hd) as nombre_hd,"
					+ " proporcional,null as dias_proporcional  from sw_haberesDescuentosFiniquito "
					+ "where codigo_trabajador = " + cod + " and idContrato = (SELECT max(id) "
					+ "FROM contratos where codigo_trabajador = " + cod + ") and codigo_hd NOT IN (2006,2007,2008) "
					+ "UNION ALL " + "SELECT null as id, null as periodo, null as tipo,codigo_hd,sum(monto) as monto,"
					+ "null as codigo_trabajador, null as frecuencia,sum(dias), null as fecha_inicio,"
					+ "null as fecha_termino, null as idContrato, null as llave_moneda,null as nombre_hd,"
					+ "null as proporcional,null as dias_proporcional " + "from sw_haberesDescuentosFiniquito "
					+ "where codigo_trabajador = " + cod + " "
					+ "and idContrato = (SELECT max(id) FROM contratos where codigo_trabajador = " + cod + ") "
					+ "AND codigo_hd IN (2006,2007,2008) group by codigo_hd";

			ps = db.conn.prepareStatement(sql);
			System.out.println("hola jose");
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {

				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setProporcional(rs.getDouble("dias_proporcional"));
				cr.setMontoHD(rs.getDouble("monto"));
				cr.setTipoHD(rs.getString("tipo"));
				cr.setCodHD(rs.getInt("codigo_hd"));
				cr.setNombreHD(rs.getString("nombre_hd"));
				cr.setDiasint(rs.getDouble("dias"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	// INSERTAR FINIQUITOS

	public static boolean insertarFiniquito(DatosTrabajadorFiniquito r) throws Exception {

		PreparedStatement ps2 = null;
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();

		try {

			db.conn.setAutoCommit(false);

			sql2 = "UPDATE contratos SET FechaTerminoContrato = '" + r.getFecha_termino() + "', descripcion = '"
					+ r.getDescripcion() + "'," + "EstadoContrato = 0, articuloTerminoContrato = " + r.getArticulo()
					+ ", " + "incisoTerminoContrato  = " + r.getInciso() + ", letraTerminoContrato = " + r.getLetra()
					+ ", " + "fechaPago = '" + r.getFechapago() + "', lugarPago = '" + r.getLugarpago() + "',"
					+ "horaPago = '" + r.getHorapago() + "', aviso = " + r.getAviso() + "  WHERE id = "
					+ r.getId_contrato() + " ";
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);

			ps2.execute();

			db.conn.commit();

			return true;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			db.conn.rollback();
		} finally {
			ps2.close();

			db.close();
		}
		return false;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////// INSERT
	////////////////////////////////////////////////////////////////////////////////////////////////////////////// sw_detalleFiniquito///////////////////////////////////////////////////////////
	public static boolean insertFiniquitoDetalle(DatosTrabajadorFiniquito r) throws Exception {
		Statement ps2 = null;
		String sql2 = "";

		ConnectionDB db = new ConnectionDB();
		try {

			sql2 = "INSERT INTO sw_detalleFiniquito "
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta, codSap ) VALUES "
					+ "(" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", " + r.getCodHD() + ","
					+ r.getPeriodo() + " , '" + r.getNombreHD() + "' , " + r.getMontoHD()
					+ ",1, ( SELECT cuentaContable FROM sw_p_haberesDescuentos WHERE descripcion LIKE '%"
					+ r.getNombreHD() + "%' LIMIT 1 ) )";

			System.out.println(sql2);

			ps2 = db.conn.prepareStatement(sql2);
			ps2.execute(sql2);

			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			ps2.close();
			db.close();
		}
		return false;
	}

	public static ArrayList<LoadTrabajadorSociedad> getallTrabajadorTemporadaFiniquito(String empr, String div,
			String subdiv, String gru, String fecha, String tipoContra, String estado_proceso, int rolPrivado)
	
	
			throws Exception {
		String replaceString=fecha.replaceAll("s","','");
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "select distinct  TR.codigo,TR.nombre,TR.apellidoPaterno,TR.apellidoMaterno,TR.rut from contratos CO "
					+ "inner join trabajadores TR on TR.codigo = CO.codigo_trabajador  left join sw_liquidacion swl on swl.cod_trabajador = CO.codigo_trabajador"
					+ " where 1 = 1";

			if ("null".equals(empr)) {
			} else {
				sql += " and CO.idSociedad = " + empr + "";
			}
			if ("null".equals(div)) {
			} else {
				sql += " and TR.idHuerto = '" + div + "'";
			}
			if ("null".equals(subdiv)) {
			} else {
				sql += " and TR.idZona = '" + subdiv + "'";
			}
			if ("null".equals(gru)) {
			} else {
				sql += " and TR.idCECO = '" + gru + "'";
			}

			if ("null".equals(fecha)) {
			} else {
				sql += " and CO.FechaTerminoContrato in ( '" + replaceString + "')";
			}
			if ("null".equals(tipoContra)) {
			} else {
				sql += " and CO.tipoContrato = " + tipoContra + "";
			}
			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and TR.estadoProceso = " + estado_proceso + " ";
			}

			if (rolPrivado == 1) {
				sql += " and  TR.rolPrivado in (1,0) ";
			} else {
				sql += " and  TR.rolPrivado in (0) ";
			}

			sql += " and CO.EstadoContrato =  1 and paraFiniquitar = 1 AND TR.tipoTrabajador !=4 group by codigo,nombre,apellidoPaterno,apellidoMaterno";

			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LoadTrabajadorSociedad e = new LoadTrabajadorSociedad();

				e.setCodigotrabajador(rs.getInt("codigo"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidoPaterno(rs.getString("apellidoPaterno"));
				e.setApellidoMaterno(rs.getString("apellidoMaterno"));
				e.setRut(rs.getString("rut"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}

	public static ArrayList<DatosTrabajadorFiniquito> getSueldoLiquidoTemporal(int cod) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		PreparedStatement ps3 = null;
		String sql3 = "";
		PreparedStatement ps4 = null;
		String sql4 = "";
		PreparedStatement ps5 = null;
		String sql5 = "";

		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select " + "(select sueldoBase FROM contratos_per WHERE codigo_trabajador = " + cod
					+ " and periodo < (select date_format(curdate(),'%Y%m')) order by periodo desc limit 1)"
					+ " as valor, " + "(SELECT nombre FROM trabajadores WHERE codigo = " + cod + " ) AS nombre,"
					+ "(SELECT apellidoMaterno FROM trabajadores WHERE codigo = " + cod + " ) as appMaterno,"
					+ "(SELECT apellidoPaterno FROM trabajadores WHERE codigo = " + cod + " ) as appPaterno,"
					+ "(SELECT rut FROM trabajadores WHERE codigo = " + cod + " ) as rut,"
					+ "(SELECT articuloTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as articulo,"
					+ "(SELECT codigo FROM trabajadores WHERE codigo = " + cod + " ) AS codigotrab,"
					+ "(SELECT DATEDIFF(FechaTerminoContrato, fechaInicio_actividad) from contratos where  codigo_trabajador = "
					+ cod + " "
					+ "AND id = (SELECT id FROM contratos WHERE fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as dias,"
					+ "(select sum(cantidadDiasSolicitud) from sw_solicitudVacaciones where idContrato = "
					+ " (SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ") and codigo_trabajador =  " + cod + " group by idContrato) as diastomado,"
					+ "(SELECT idSociedad FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as empresa,"
					+ "(SELECT fechaPago FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as fechapago,"
					+ "(SELECT lugarPago FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as lugarpago,"
					+ "(SELECT horaPago FROM contratos  WHERE codigo_trabajador = " + cod + " and"
					+ " id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as horapago,"
					+ "(select descripcion from sw_m_articuloTerminoContrato where idArticuloTerminoContrato = "
					+ "(SELECT articuloTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + "))) as nombreCausal,"
					+ "(select letraTerminoContrato from sw_m_letraTerminoContrato where idLetraTerminoContrato = "
					+ "(SELECT letraTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + "))) as nombreLetra,"
					+ "(SELECT incisoTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as inciso,"
					+"(select nombre from sw_m_incisoTerminoContrato where idIncisoTerminoContrato = (SELECT  incisoTerminoContrato "
					+"FROM contratos WHERE codigo_trabajador = " + cod + "  AND id = "
					+"(SELECT id FROM contratos  WHERE "
                    +"fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos  WHERE "
                    +"codigo_trabajador = " + cod + ") AND codigo_trabajador = " + cod + "))  ) as incisonew,"
					+"(select nombre from sw_m_incisoTerminoContrato where idIncisoTerminoContrato = (SELECT  incisoTerminoContrato "
					+"FROM contratos WHERE codigo_trabajador = " + cod + "  AND id = "
					+"(SELECT id FROM contratos  WHERE "
                    +"fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos  WHERE "
                    +"codigo_trabajador = " + cod + ") AND codigo_trabajador = " + cod + "))  ) as incisonew,"
					+ "(SELECT letraTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as letra,"
					+ "(SELECT aviso FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as aviso,"
					+ "(SELECT id FROM trabajadores WHERE codigo = " + cod + "  ) as id,"
					+ "(select denominacionSociedad from sociedad where idSociedad = "
					+ "(SELECT idSociedad FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + "))) as nombreSociedad,"
					+ "(SELECT descripcion FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as descripcion,"
					+ "(SELECT tipoContrato FROM SAN_CLEMENTE.contratos WHERE codigo_trabajador = " + cod + " and"
					+ "	id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as tipoContrato,"
					+ "(SELECT descripcion FROM parametros where codigo = 'TIPO_CONTRATO' and llave ="
					+ "(SELECT tipoContrato FROM SAN_CLEMENTE.contratos WHERE codigo_trabajador = " + cod + " and "
					+ "id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + "))) as nombretipo_contrato,"
					+ "(SELECT fechaInicio_actividad FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ " id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as fechaInicio,"
					+ " (SELECT FechaTerminoContrato FROM contratos  WHERE codigo_trabajador = " + cod + " and "
					+ " id=(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ")) as fechaTermino, "
					+ "(SELECT id FROM contratos where fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and  codigo_trabajador = " + cod + ") as idcontratoTra,"
					+ "CASE WHEN 1 = 1 THEN 'SUELDO'" + " ELSE '' " + "END as nombreconcepto  from contratos "
					+ "WHERE codigo_trabajador = " + cod
					+ " and id = (SELECT id FROM contratos WHERE fechaInicio_actividad = (SELECT MAX(fechaInicio_actividad) FROM contratos WHERE codigo_trabajador = "
					+ cod + " ) and codigo_trabajador = " + cod + ") ";

			System.out.println("aqui");
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setValor(rs.getInt("valor"));
				cr.setNombre_concepto(rs.getString("nombreconcepto"));
				cr.setNombre(rs.getString("nombre"));
				cr.setApmaterno(rs.getString("appMaterno"));
				cr.setAppaterno(rs.getString("appPaterno"));
				cr.setRut(rs.getString("rut"));
				cr.setNombre_sociedad(rs.getString("nombreSociedad"));
				cr.setFecha_inicio(rs.getString("fechaInicio"));
				cr.setFecha_termino(rs.getString("fechaTermino"));
				cr.setId_trabajador(rs.getInt("id"));
				cr.setArticulo(rs.getInt("articulo"));
				cr.setInciso(rs.getInt("inciso"));
				cr.setLetra(rs.getInt("letra"));
				cr.setAviso(rs.getInt("aviso"));
				cr.setFechapago(rs.getString("fechapago"));
				cr.setLugarpago(rs.getString("lugarpago"));
				cr.setHorapago(rs.getString("horapago"));
				cr.setDias(rs.getString("dias"));
				cr.setDiastomado(rs.getInt("diastomado"));
				cr.setDescripcion(rs.getString("descripcion"));
				cr.setTipocontrato(rs.getInt("tipoContrato"));
				cr.setNombretipocontrato(rs.getString("nombretipo_contrato"));
				cr.setSociedad(rs.getInt("empresa"));
				cr.setId_contrato(rs.getInt("idcontratoTra"));
				cr.setNombre_letra(rs.getString("nombreLetra"));
				cr.setNombre_causal(rs.getString("nombreCausal"));
				cr.setCodigo_trabajador(rs.getInt("codigotrab"));
				cr.setIncisonew(rs.getInt("incisonew"));
				cr.setIncisonew(rs.getInt("incisonew"));
				lista.add(cr);
			}

			sql2 = "SELECT sum(valor)/3 as ASIGNACIONES_IMPONIBLES FROM " + "(select sum(valor) as valor "
					+ "from sw_liquidacionDetalle where codTrabajador = " + cod + " "
					+ " and idConcepto  IN (4,5,6,7,8) group by periodo desc limit 3 )v ";

			System.out.println("//////////////////////////////");
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs2 = ps2.executeQuery(sql2);

			while (rs2.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				lista.get(0).setValorasignacionesimponibles(rs2.getInt("ASIGNACIONES_IMPONIBLES"));
			}

			sql3 = "SELECT sum(valor)/3 as gratificacion " + "from sw_liquidacionDetalle  where codTrabajador = " + cod
					+ " " + "and idConcepto = 9 order by periodo  desc limit 3 ";

			System.out.println("//////////////////////////////");
			System.out.println(sql3);
			ps3 = db.conn.prepareStatement(sql3);
			ResultSet rs3 = ps3.executeQuery(sql3);

			while (rs3.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				lista.get(0).setValorgratificacion(rs3.getInt("gratificacion"));

			}

			sql4 = "SELECT sum(valor)/3 as NO_IMPONIBLES FROM " + "(SELECT sum(valor) as valor "
					+ "from sw_liquidacionDetalle where codTrabajador = " + cod + " "
					+ "and idConcepto  IN (11,12,13,14,15,16,17,18,19)group by periodo desc limit 3)v";

			ps4 = db.conn.prepareStatement(sql4);
			ResultSet rs4 = ps4.executeQuery(sql4);

			while (rs4.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				lista.get(0).setValornoimponible(rs4.getInt("NO_IMPONIBLES"));

			}

			/// SOBRE GRIRO

			sql5 = "select valor  from sw_liquidacionDetalle where codTrabajador = " + cod
					+ " and descripcion = 'sobregiro' "
					+ "and periodo = (select max(periodo) from sw_liquidacionDetalle where codTrabajador = " + cod
					+ " )";

			System.out.println(sql5);
			ps5 = db.conn.prepareStatement(sql5);
			ResultSet rs5 = ps5.executeQuery(sql5);

			while (rs5.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				lista.get(0).setSobregiro(rs5.getInt("valor"));

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	// FINIQUITO MASIVO INSERTAR

	public static boolean insertarFiniquitoMasivo(DatosTrabajadorFiniquito r) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps26 = null;
		PreparedStatement ps27 = null;
		Statement ps3, ps4, ps5, ps6, ps7, ps8, ps9, ps10, ps11, ps12, ps13, ps14, ps15, ps16, ps17, ps18, ps19, ps20,
				ps21, ps22, ps23, ps24, ps25, ps20000, ps20001, ps20002 = null, ps20003 = null;
		String sql = "";
		String sql2 = "";
		String sql3 = "", sql4 = "", sql5 = "", sql6 = "", sql7 = "", sql8 = "", sql9 = "", sql10 = "", sql11 = "",
				sql12 = "", sql13 = "", sql14 = "", sql15 = "", sql16 = "", sql17 = "", sql18 = "", sql19 = "",
				sql20 = "", sql21 = "", sql22 = "", sql23 = "", sql24 = "", sql25 = "", sql26 = "", sql27 = "", sql20000 = "",
				sql20001 = "", sql20002 = "", sql20003 = "";

		ConnectionDB db = new ConnectionDB();

		try {

			db.conn.setAutoCommit(false);

			String PeriodoFechaTernino = r.getFecha_termino();
			String[] fechaTSplitP = PeriodoFechaTernino.split("-");
			PeriodoFechaTernino = fechaTSplitP[0] + fechaTSplitP[1];

			if (PeriodoFechaTernino != "" + r.getPeridocentralizacion() + "") {

			} else {

				sql25 = "call sw_createLiquidacion(" + r.getCodigo_trabajador() + "," + r.getId_contrato() + ","
						+ r.getPeridocentralizacion() + ",0,1) ";

				ps25 = db.conn.prepareStatement(sql25);
				ps25.execute(sql25);
			}

			sql24 = "DELETE FROM sw_detalleFiniquito WHERE idContrato = " + r.getId_contrato() + " AND periodo = "
					+ r.getPeriodo() + " AND codTrabajador = " + r.getCodigo_trabajador() + "";

			ps24 = db.conn.prepareStatement(sql24);
			ps24.execute(sql24);

			sql3 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 1 ," + r.getPeriodo() + " , '"
					+ r.getFecha_termino() + "' , 0,0);";

			ps3 = db.conn.prepareStatement(sql3);
			ps3.execute(sql3);

			sql4 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 2 ," + r.getPeriodo() + " , '"
					+ r.getFechapago() + "' , 0,0);";
			System.out.println(sql4);
			ps4 = db.conn.prepareStatement(sql4);
			ps4.execute(sql4);

			sql5 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 3 ," + r.getPeriodo() + " , '"
					+ r.getDescripcion() + "' , 0,0);";

			ps5 = db.conn.prepareStatement(sql5);
			ps5.execute(sql5);

			sql6 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 4 ," + r.getPeriodo() + " , '"
					+ r.getLugarpago() + "' , 0,0);";

			ps6 = db.conn.prepareStatement(sql6);
			ps6.execute(sql6);

			sql7 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 5 ," + r.getPeriodo() + " , '"
					+ r.getHorapago() + "' , 0,0);";

			ps7 = db.conn.prepareStatement(sql7);
			ps7.execute(sql7);

			sql8 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 6 ," + r.getPeriodo()
					+ " , 'ARTICULO' ," + r.getArticulo() + " ,0);";

			ps8 = db.conn.prepareStatement(sql8);
			ps8.execute(sql8);

			sql9 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 7 ," + r.getPeriodo() + " , 'INCISO' ,"
					+ r.getInciso() + " ,0);";

			ps9 = db.conn.prepareStatement(sql9);
			ps9.execute(sql9);

			sql10 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 8 ," + r.getPeriodo() + " , 'LETRA' ,"
					+ r.getLetra() + " ,0);";

			ps10 = db.conn.prepareStatement(sql10);
			ps10.execute(sql10);

			sql11 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 9 ," + r.getPeriodo()
					+ " , 'TOTAL FINIQUITO' ," + r.getTotal_finiquito() + " ,0);";

			ps11 = db.conn.prepareStatement(sql11);
			ps11.execute(sql11);

			sql12 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 10 ," + r.getPeriodo()
					+ " , 'FERIADO BASICO' ," + r.getFeriadobasico() + " ,0);";

			ps12 = db.conn.prepareStatement(sql12);
			ps12.execute(sql12);

			sql13 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 11 ," + r.getPeriodo()
					+ " , 'FERIADO PROGRESIVO' ," + r.getFeriadoprogresivo() + " ,0);";

			ps13 = db.conn.prepareStatement(sql13);
			ps13.execute(sql13);

			sql14 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 12 ," + r.getPeriodo()
					+ " , 'FERIADO CONVENCIONAL' ," + r.getFeriadoconvencional() + " ,0);";

			ps14 = db.conn.prepareStatement(sql14);
			ps14.execute(sql14);

			sql15 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 13 ," + r.getPeriodo()
					+ " , 'TOTAL FERIADOS BPC' ," + r.getTotalferiadobpc() + " ,0);";

			ps15 = db.conn.prepareStatement(sql15);
			ps15.execute(sql15);

			sql16 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 14 ," + r.getPeriodo()
					+ " , 'DIAS TOMADO' ," + r.getDiastomado() + " ,0);";

			ps16 = db.conn.prepareStatement(sql16);
			ps16.execute(sql16);

			sql17 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 15 ," + r.getPeriodo()
					+ " , 'SUBTOTAL' ," + r.getSubtotal() + " ,0);";

			ps17 = db.conn.prepareStatement(sql17);
			ps17.execute(sql17);

			sql18 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 16 ," + r.getPeriodo()
					+ " , 'DIAS INHABILES' ," + r.getDiasinhabiles() + " ,0);";

			ps18 = db.conn.prepareStatement(sql18);
			ps18.execute(sql18);

			sql19 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 17 ," + r.getPeriodo()
					+ " , 'TOTAL DIAS PROPORCIONALES' ," + r.getTotaldiasproporcional() + " ,0);";

			ps19 = db.conn.prepareStatement(sql19);
			ps19.execute(sql19);

			sql20 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES " + "("
					+ r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 18 ," + r.getPeriodo() + " , 'AVISO' ,"
					+ r.getAviso() + " ,0);";

			ps20 = db.conn.prepareStatement(sql20);
			ps20.execute(sql20);

			sql21 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta, codSap) VALUES "
					+ "(" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 2008 ," + r.getPeriodo()
					+ " , 'INDEMNIZACION MES DE NO AVISO' ," + r.getMontoaviso()
					+ " ,0, ( SELECT cuentaContable FROM sw_p_haberesDescuentos WHERE descripcion LIKE '%INDEMNIZACION MES DE NO AVISO%' LIMIT 1 ) );";

			ps21 = db.conn.prepareStatement(sql21);
			ps21.execute(sql21);

			if (r.getArticulo() == 1 && r.getInciso() == 2) {

			} else {
				sql22 = "INSERT INTO sw_detalleFiniquito"
						+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta, codSap) VALUES "
						+ "(" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 2007 ," + r.getPeriodo()
						+ " , 'INDEMNIZACION AÑOS DE SERVICIO' ," + r.getMontoanio()
						+ " ,0, ( SELECT cuentaContable FROM sw_p_haberesDescuentos WHERE descripcion LIKE '%INDEMNIZACION AÑOS DE SERVICIO%' LIMIT 1 ) );";

				ps22 = db.conn.prepareStatement(sql22);
				ps22.execute(sql22);
			}

			sql23 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta, codSap) VALUES "
					+ "(" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 2006 ," + r.getPeriodo()
					+ " , 'FERIADO PROPORCIONAL' ," + r.getMontoferiado()
					+ " ,0, ( SELECT cuentaContable FROM sw_p_haberesDescuentos WHERE descripcion LIKE '%FERIADO PROPORCIONAL%' LIMIT 1 ) );";

			ps23 = db.conn.prepareStatement(sql23);
			ps23.execute(sql23);

			if (r.getSobregiro() == 0) {

			} else {
				sql20001 = "INSERT INTO sw_detalleFiniquito"
						+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES "
						+ "(" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 20001 ," + r.getPeriodo()
						+ " , 'SOBRE GIRO' ," + r.getSobregiro() + " ,0);";

				ps20001 = db.conn.prepareStatement(sql20001);
				ps20001.execute(sql20001);

				sql20002 = "INSERT INTO sw_detalleFiniquito"
						+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta) VALUES "
						+ "(" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 20002 ," + r.getPeriodo()
						+ " , 'SALDO SOBRE GIRO' ," + r.getSaldosobregiro() + " ,0);";

				ps20002 = db.conn.prepareStatement(sql20002);
				ps20002.execute(sql20002);
			}

			sql20000 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta, codSap) VALUES "
					+ "(" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 20000 ," + r.getPeriodo()
					+ " , 'INDEMNIZACION POR TIEMPO SERVIDO' ," + r.getTiemposervicio()
					+ " ,0 , ( SELECT cuentaContable FROM sw_p_haberesDescuentos WHERE descripcion LIKE '%INDEMNIZACION POR TIEMPO SERVIDO%' LIMIT 1 ) );";

			ps20000 = db.conn.prepareStatement(sql20000);
			ps20000.execute(sql20000);

			sql20003 = "INSERT INTO sw_detalleFiniquito"
					+ "(codTrabajador, idContrato, idConcepto, periodo, descripcion, valor, propuesta, codSap) VALUES "
					+ "(" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", 20003 ," + r.getPeriodo()
					+ " , 'DIAS TIEMPO SERVIDO' ," + r.getDiastiemposervido()
					+ " ,0, ( SELECT cuentaContable FROM sw_p_haberesDescuentos WHERE descripcion LIKE '%DIAS TIEMPO SERVIDO%' LIMIT 1 ) );";

			ps20003 = db.conn.prepareStatement(sql20003);
			ps20003.execute(sql20003);

			sql = "INSERT INTO sw_finiquitos (cod_trabajador, id_contrato, id_sociedad, "
					+ "rut, fecha_termino_contrato,"
					+ "articulo_termino_contrato,inciso_termino_contrato,letra_termino_contrato,"
					+ "descripcion,periodo,fecha_pago,lugar_pago,hora_pago,total_pago_finiquito"
					+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(1, r.getCodigo_trabajador());
			ps.setInt(2, r.getId_contrato());
			ps.setInt(3, r.getSociedad());
			ps.setString(4, r.getRut());

			String[] fechaTSplitu = r.getFecha_termino().split("-");

			ps.setString(5, fechaTSplitu[0] + "-" + fechaTSplitu[1] + "-" + fechaTSplitu[2]);
			ps.setInt(6, r.getArticulo());
			ps.setInt(7, r.getInciso());
			ps.setInt(8, r.getLetra());
			ps.setString(9, r.getDescripcion());
			ps.setInt(10, r.getPeridocentralizacion());
			if ("null".equals(r.getFechapago())) {
				ps.setString(11, null);
			} else {
				ps.setString(11, r.getFechapago());
			}

			ps.setString(12, r.getLugarpago());
			ps.setString(13, r.getHorapago());
			ps.setInt(14, r.getTotal_finiquito());

			ps.execute();

			String[] fechaTSplitu1 = r.getFecha_termino().split("-");

			sql2 = "UPDATE contratos SET FechaTerminoContrato = '" + fechaTSplitu1[0] + "-" + fechaTSplitu1[1] + "-"
					+ fechaTSplitu1[2] + "', descripcion = '" + r.getDescripcion() + "',"
					+ "EstadoContrato = 0, articuloTerminoContrato = " + r.getArticulo() + ", "
					+ "incisoTerminoContrato  = " + r.getInciso() + ", letraTerminoContrato = " + r.getLetra() + ", ";
			if ("null".equals(r.getFechapago())) {
				sql2 += " fechaPago = null ";
			} else {
				sql2 += " fechaPago = '" + r.getFechapago() + "'";
			}

			sql2 += ",lugarPago = '" + r.getLugarpago() + "'," + "horaPago = '" + r.getHorapago() + "', aviso = "
					+ r.getAviso() + "  WHERE id = " + r.getId_contrato() + " ";
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);
			System.out.println(sql2);
			ps2.execute();
			
			sql27="UPDATE contratos SET fechaPago ='" + r.getFechapago() + "' WHERE id=" + r.getId_contrato() + " and codigo_trabajador = " + r.getCodigo_trabajador() + ";";
			System.out.println(sql27);
			ps27 = db.conn.prepareStatement(sql27);
			System.out.println(sql27);
			ps27.execute();

			sql26 = "INSERT INTO sw_movimientoPrevired "
					+ "(cod_trabajador,id_contrato,periodo,movimento_previred,id_sociedad,fecha_movimiento,fecha_termino) "
					+ "VALUES (" + r.getCodigo_trabajador() + ", " + r.getId_contrato() + ", " + r.getPeriodo() + ", 2,"
					+ r.getSociedad() + ",(select fechaInicio_actividad from contratos where codigo_trabajador = "
					+ r.getCodigo_trabajador() + " and id = " + r.getId_contrato() + ") , '" + fechaTSplitu1[0] + "-"
					+ fechaTSplitu1[1] + "-" + fechaTSplitu1[2] + "')";

			System.out.println(sql26);
			ps26 = db.conn.prepareStatement(sql26);
			ps26.execute(sql26);

			db.conn.commit();

			return true;

		} catch (Exception e) {
			db.conn.rollback();
			System.out.println("Error: " + e.getMessage());
			System.out.println("Transaction rollback...");
			e.printStackTrace();
			return false;

		} finally {
			ps.close();

			db.close();
		}

	}

	public static DatosTrabajadorFiniquito obtenerDatosTrabajadorFiniquitos(String idtabla, int COD_TRAB_INT)
			throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		DatosTrabajadorFiniquito tr = new DatosTrabajadorFiniquito();

		Date fechaActual = new Date();
		System.out.println(fechaActual);

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		String formatoFecha = "" + formatFecha.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		;

		try {

			sql = "SELECT cod_trabajador,fecha_termino_contrato,"
					+ "(select nombre from sw_m_articuloTerminoContrato where idArticuloTerminoContrato = articulo_termino_contrato) as articulo_termino_contrato,"
					+ "inciso_termino_contrato," + "letra_termino_contrato,total_pago_finiquito,"
					+"(SELECT " 
		            +"descripcion "
		        +"FROM "
		            +"sw_m_incisoTerminoContrato "
		        +"WHERE "
		            +"nombre = inciso_termino_contrato and idArticuloTerminoContrato = (select idArticuloTerminoContrato from sw_m_articuloTerminoContrato where nombre = (SELECT "
		            +"nombre "
		        +"FROM "
		            +"sw_m_articuloTerminoContrato "
		        +"WHERE "
		            +"idArticuloTerminoContrato = articulo_termino_contrato))) AS incisoDescripcion,"
					+ "(SELECT cargos FROM cargos WHERE id_cargo = (SELECT cargo FROM contratos WHERE codigo_trabajador = sw.cod_trabajador  and id = sw.id_contrato)) as cargoTrab,"
					+ "(select denominacionSociedad from sociedad WHERE idSociedad = sw.id_sociedad ) as nombreSociedad,"
					+ "(select rut from sociedad WHERE idSociedad = sw.id_sociedad) as rutsociedad,"
					+ "(SELECT nombre FROM trabajadores WHERE codigo = sw.cod_trabajador ) as nombreTrabajador,"
					+ "(SELECT apellidoPaterno FROM trabajadores WHERE codigo = sw.cod_trabajador ) as apellidoPate,"
					+ "(SELECT apellidoMaterno FROM trabajadores WHERE codigo = sw.cod_trabajador ) as apellidoMate,"
					+ "(SELECT rut FROM trabajadores WHERE codigo = sw.cod_trabajador ) as ruttrab,"
					+ "IF((SELECT direccion_huerto FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato)) is null, '', (SELECT direccion_huerto FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato))) as direccion, "
					+ "IF((SELECT ciudad_huerto FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato)) is null, '', (SELECT ciudad_huerto FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato))) as ciudad,"
					+ "IF((SELECT comuna_huerto FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato)) is null, '', (SELECT comuna_huerto FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato))) as comuna,"
					+ "(SELECT fechaInicio_actividad FROM contratos WHERE id = sw.id_contrato) as fecha_actividad, "
					+ " IF((SELECT representante_legal_nombre FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato)) is null, '', (SELECT representante_legal_nombre FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato))) as nombre_representante, "
					+ " IF((SELECT representante_legal_apPaterno FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato)) is null,'', (SELECT representante_legal_apPaterno FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato))) as apellidoP_representante, "
					+ " IF((SELECT representante_legal_apMaterno FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato)) is null, '', (SELECT representante_legal_apMaterno FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato))) as apellidoM_representante, "
					+ " IF((SELECT representante_legal_rut FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato)) is null, '', (SELECT representante_legal_rut FROM campo WHERE campo = (select idHuertoContrato FROM contratos WHERE id = sw.id_contrato))) as rut_representante "
					+ "FROM sw_finiquitos sw where sw.id_finiquito = " + idtabla + " ";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				tr.setCodigo_trabajador(rs.getInt("cod_trabajador"));
				tr.setArticulostring(rs.getString("articulo_termino_contrato"));
				tr.setInciso(rs.getInt("inciso_termino_contrato"));
				tr.setLetra(rs.getInt("letra_termino_contrato"));
				tr.setNombre_sociedad(rs.getString("nombreSociedad").toUpperCase());
				tr.setRut_sociedad(rs.getString("rutsociedad"));
				tr.setDireccion_huerto(rs.getString("direccion").toUpperCase());
				tr.setCiudad_huerto(rs.getString("ciudad"));
				tr.setRut(rs.getString("ruttrab"));
				tr.setNombrerepresentante(rs.getString("nombre_representante").toUpperCase());
				tr.setApprepresentante(rs.getString("apellidoP_representante").toUpperCase());
				tr.setApmrepresentante(rs.getString("apellidoM_representante").toUpperCase());
				tr.setRutrepresentante(rs.getString("rut_representante"));

				tr.setTotal_finiquito(rs.getInt("total_pago_finiquito"));
				tr.setIncisoDescripcion(rs.getString("incisoDescripcion").toUpperCase());

				String fechaTerminoSplit = rs.getString("fecha_termino_contrato").toUpperCase();
				String[] fechaTSplit = fechaTerminoSplit.split("-");
				tr.setFecha_termino(fechaTSplit[2] + "-" + fechaTSplit[1] + "-" + fechaTSplit[0]);

				String fechaIncioSplit = rs.getString("fecha_actividad").toUpperCase();
				String[] fechaISplit = fechaIncioSplit.split("-");
				tr.setFecha_inicio(fechaISplit[2] + "-" + fechaISplit[1] + "-" + fechaISplit[0]);

				tr.setNombre(
						rs.getString("apellidoPate").toUpperCase() + " " + rs.getString("apellidoMate").toUpperCase()
								+ " " + rs.getString("nombreTrabajador").toUpperCase());
				tr.setCargotrabajador(rs.getString("cargoTrab"));
				tr.setComunaHurto(rs.getString("comuna"));

				return tr;
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return tr;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////// CARGAR TRABAJADORES FINIQUITOS PARA
	///////////////////////////////////////////////////////////////////////////////////////////////////// IMPRIMIR//////////////////////////

	public static ArrayList<DatosTrabajadorFiniquito> getTrabajadoresFiniquitoImprimir(int id, String estado_proceso,
			int rolPrivado) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> data = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "SELECT distinct " + "	        sw.cod_trabajador," + "		    tr.nombre,"
					+ "		    tr.apellidoPaterno," + "		    tr.apellidoMaterno," + " 	    tr.rut "
					+ "		FROM " + "		    sw_finiquitos sw " + "		        INNER JOIN "
					+ "		    trabajadores tr ON sw.cod_trabajador = tr.codigo " + "		WHERE "
					+ "		    id_sociedad = " + id + " and sw.estado_finiquito in (0,1) ";

			if (rolPrivado == 1) {
				sql += " and  tr.rolPrivado in (1,0) ";
			} else {
				sql += " and  tr.rolPrivado in (0) ";
			}

			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and tr.estadoProceso = " + estado_proceso + " ";
			}

			sql += " ORDER BY tr.apellidoPaterno ASC;";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				DatosTrabajadorFiniquito e = new DatosTrabajadorFiniquito();

				e.setCodigo_trabajador(rs.getInt("cod_trabajador"));
				e.setNombre(rs.getString("nombre"));
				e.setApellidoPaterno(rs.getString("apellidoPaterno"));
				e.setApellidoMaterno(rs.getString("apellidoMaterno"));
				e.setRut(rs.getString("rut"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}

	public static ArrayList<DatosTrabajadorFiniquito> getBuscarFiniquitosSimpleImprimir(String fec, String cod,
			String emp, String divi, String subd, String gru, String tipo_cuenta, String estado_proceso, int rolPrivado)
			throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "SELECT DISTINCT " + "sw.id_finiquito," + "sw.id_contrato," + "sw.total_pago_finiquito,"
					+ "sw.cod_trabajador," + "cnt.fechaInicio_actividad," + "cnt.FechaTerminoContrato,"
					+ "sw.id_finiquito," + "sw.fecha_pago," + "tr.apellidoPaterno," + "tr.apellidoMaterno," + "tr.rut,"
					+ "tr.nombre," + "cnt.idSociedad " + "FROM " + "sw_finiquitos sw " + "INNER JOIN "
					+ "trabajadores tr ON tr.codigo = sw.cod_trabajador " + "INNER JOIN "
					+ "contratos cnt ON cnt.id = sw.id_contrato " + "INNER JOIN cuentaBancaria ca  "
					+ "on ca.codigoTrabajador = tr.codigo " + "WHERE " + "1 = 1 ";

			if ("null".equals(fec)) {
			} else {
				sql += " and sw.fecha_pago = '" + fec + "'";
			}
			if ("null".equals(cod)) {
			} else {
				sql += " and sw.cod_trabajador = " + cod + "";
			}
			if ("null".equals(emp)) {
			} else {
				sql += " and sw.id_sociedad = " + emp + "";
			}
			if ("null".equals(divi)) {
			} else {
				sql += " and tr.idHuerto = '" + divi + "'";
			}
			if ("null".equals(subd)) {
			} else {
				sql += " and tr.idZona = '" + subd + "'";
			}
			if ("null".equals(gru)) {
			} else {
				sql += " and tr.idCECO = '" + gru + "'";
			}
			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and tr.estadoProceso = " + estado_proceso + " ";
			}

			if (rolPrivado == 1) {
				sql += " and  tr.rolPrivado in (1,0) ";
			} else {
				sql += " and  tr.rolPrivado in (0) ";
			}

			// todas las cuentas
			if ("null".equals(tipo_cuenta)) {
			} else if ("0".equals(tipo_cuenta)) {
				sql += " AND ca.idTipoCuenta  NOT IN (2,12,4,3)";
			} else {
				sql += " and ca.idTipoCuenta = " + tipo_cuenta + "";
			}

			sql += " and sw.estado_finiquito in (0,1) ";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				DatosTrabajadorFiniquito hd = new DatosTrabajadorFiniquito();

				hd.setId(rs.getInt("id_finiquito"));
				hd.setTotal_finiquito(rs.getInt("total_pago_finiquito"));
				hd.setCodigo_trabajador(rs.getInt("cod_trabajador"));
				hd.setSociedad(rs.getInt("idSociedad"));
				hd.setFechapago(rs.getString("fecha_pago"));
				hd.setFecha_inicio(rs.getString("fechaInicio_actividad"));
				hd.setFecha_termino(rs.getString("FechaTerminoContrato"));
				hd.setApellidoPaterno(rs.getString("apellidoPaterno"));
				hd.setApellidoMaterno(rs.getString("apellidoMaterno"));
				hd.setNombre(rs.getString("nombre"));
				hd.setRut(rs.getString("rut"));
				hd.setId_contrato(rs.getInt("id_contrato"));

				lista.add(hd);

			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// IMPRIMIR FINIQUITO
	/////////////////////////////////////////////////////////////////////////////////////////////////////////// MASIVO///////////////////////////////////////////////

	public static ArrayList<DatosTrabajadorFiniquito> obtenerDatosTrabajadorFiniquitosMasivos(String idtabla)
			throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();

		Date fechaActual = new Date();
		System.out.println(fechaActual);

		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		String formatoFecha = "" + formatFecha.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");
		;

		try {
			// sql ="SELECT *,"
			// + "(SELECT nombre FROM trabajadores WHERE codigo =
			// sw.cod_trabajador ) as nombreTrabajador,"
			// + "(SELECT apellidoPaterno FROM trabajadores WHERE codigo =
			// sw.cod_trabajador ) as apellidoPate,"
			// + "(SELECT apellidoMaterno FROM trabajadores WHERE codigo =
			// sw.cod_trabajador ) as apellidoMate,"
			// + "(SELECT fechaContrato_emitido FROM contratos where id =
			// sw.idContrato) as fecha_contrato,"
			// + "(select denominacionSociedad from sociedad WHERE idSociedad =
			// sw.empresa ) as nombresociedad,"
			// + "(select rut from sociedad WHERE idSociedad = sw.empresa) as
			// rutsociedad,"
			// + "(SELECT rut FROM trabajadores WHERE codigo = sw.cod_trabajador
			// ) as ruttrab,"
			// +"(select idTipoCuenta from cuentaBancaria where cuentaPrimaria =
			// 1 and codigoTrabajador = sw.cod_trabajador) as idtipocuenta,"
			// + "(select descripcion from parametros where codigo =
			// 'TIPO_DE_CUENTA' and llave = idtipocuenta) as tipocuentatrab"
			// + " FROM sw_asignacionAnticipos sw where id IN ("+idtabla+")
			// order by apellidoPate";

			sql = "SELECT * FROM sw_detalleFiniquito sw where id IN (" + idtabla + ")";

			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				DatosTrabajadorFiniquito tr = new DatosTrabajadorFiniquito();

				tr.setTotal_finiquito(rs.getInt("total_pago"));
				// tr.setCodigoTrabajador(rs.getString("cod_trabajador").toUpperCase());
				//
				//
				// String fechaAnticipoSplit =
				// rs.getString("fecha").toUpperCase();
				// String[] fechaASplit = fechaAnticipoSplit.split("-");
				// tr.setFechaPagoAnticipo(fechaASplit[2]+"-"+fechaASplit[1]+"-"+fechaASplit[0]);
				//
				//
				//
				// String text = rs.getString("monto_ingresado").toUpperCase();
				// double value = Double.parseDouble(text);
				//
				//
				// String totalf = String.format("%,.0f", value);
				//
				// tr.setMonto(totalf);
				//
				// tr.setNombreTrabajador(rs.getString("nombreTrabajador").toUpperCase());
				// tr.setAppPatTrabajador(rs.getString("apellidoPate").toUpperCase());
				// tr.setAppMaternoTrabajador(rs.getString("apellidoMate").toUpperCase());
				//// tr.setTipo("TRANSFER");
				// tr.setTipo(rs.getString("tipocuentatrab").toUpperCase());
				// tr.setFecha(formatoFecha);
				//
				// String fechaInicioSplit =
				// rs.getString("fecha_contrato").toUpperCase();
				// String[] fechaISplit = fechaInicioSplit.split("-");
				// tr.setFechaInicio(fechaISplit[2]+"-"+fechaISplit[1]+"-"+fechaISplit[0]);
				// tr.setRuttrabajador(rs.getString("ruttrab").toUpperCase());
				// tr.setNombreEmpresa(rs.getString("nombresociedad").toUpperCase());
				// tr.setRutCompletoEmpresa(rs.getString("rutsociedad").toUpperCase());
				//
				// String per = rs.getString("periodo");
				// System.out.println(per);;
				// String anio = per.substring(0,4);
				// System.out.println(anio);
				//
				// String per2 = per.substring(4);//mes
				// System.out.println(per2);
				// int meses;
				//
				// String mesPalabra = "";
				// meses=Integer.parseInt(per2);
				//
				// switch ( meses ){
				// case 1: mesPalabra = "ENERO"; break;
				// case 2: mesPalabra = "FEBRERO"; break;
				// case 3: mesPalabra = "MARZO"; break;
				// case 4: mesPalabra = "ABRIL"; break;
				// case 5: mesPalabra = "MAYO"; break;
				// case 6: mesPalabra = "JUNIO"; break;
				// case 7: mesPalabra = "JULIO"; break;
				// case 8: mesPalabra = "AGOSTO"; break;
				// case 9: mesPalabra = "SEPTIEMBRE"; break;
				// case 10: mesPalabra = "OCTUBRE"; break;
				// case 11: mesPalabra = "NOVIEMBRE"; break;
				// case 12: mesPalabra = "DICIEMBRE"; break;
				// default: mesPalabra = ""; break;
				// }
				//
				//
				// tr.setNombreperiod(mesPalabra+ " " + anio);
				//

				lista.add(tr);

			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////// INSERTAR HABERES Y DESCUENTOS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////// FINIQUITO//////////////////////////////////////////////////

	public static boolean insertarHDFiniquito(InsertHD r) throws Exception {
		Statement ps = null;

		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {
			sql = "INSERT INTO sw_haberesDescuentosFiniquito (periodo , tipo, codigo_hd , monto , codigo_trabajador,frecuencia,dias,fecha_inicio,fecha_termino,idContrato,llave_moneda,proporcional)";
			sql += "VALUES (" + r.getPeriodo() + ",'" + r.getTipo() + "'," + r.getCodigo_hd() + "," + r.getMonto() + ","
					+ r.getCodigo_trabajador() + "," + r.getFrecuencia() + "," + r.getDias() + "," + r.getFecha_inicio()
					+ "," + r.getFecha_termino() + "," + r.getId_contrato() + "," + r.getIdmoneda() + ","
					+ r.getValorcheck() + ");";

			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);

			ps.execute(sql);

			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();

			db.close();
		}
		return false;
	}

	//////// CALCULAR FERIADOS PROPORCIONALES
	//////// INDIVIDUAL///////////////////////////////
	public static ArrayList<DatosTrabajadorFiniquito> getFeriadosProporcional(String fecha, String dias)
			throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select getFeriadosProporcionales(" + dias + ",'" + fecha + "') as dias_proporcional";

			ps = db.conn.prepareStatement(sql);
			System.out.println("hola jose");
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {

				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setProporcional(rs.getDouble("dias_proporcional"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	/////////////////////// LISTADO TIPO DE
	/////////////////////// CONTRATO///////////////////////////////////
	public static ArrayList<ListaSociedad> getTipocontratoFiniquito() throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<ListaSociedad> lista = new ArrayList<ListaSociedad>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select llave,descripcion from parametros where codigo = 'TIPO_CONTRATO'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				ListaSociedad cr = new ListaSociedad();
				cr.setDenominacionSociedad(rs.getString("descripcion"));
				cr.setIdSociedad(rs.getInt("llave"));
				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	public static ArrayList<DatosTrabajadorFiniquito> obtenerHaberesFiniquitos(int COD_TRAB_INT, int idcontrato)
			throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<DatosTrabajadorFiniquito> Lista = new ArrayList<DatosTrabajadorFiniquito>();

		try {

			sql = "SELECT " + "tipo," + "codigo_hd," + "monto," + "(SELECT " + "descripcion " + "FROM "
					+ "SAN_CLEMENTE.sw_p_haberesDescuentos " + "WHERE " + "codigo = codigo_hd) AS nombre_hd,"
					+ "0 as proporcional " + "FROM " + "sw_haberesDescuentosFiniquito " + "WHERE "
					+ "codigo_trabajador = " + COD_TRAB_INT + " " + "AND idContrato  = " + idcontrato + " and "
					+ "codigo_trabajador = " + COD_TRAB_INT + " and tipo in ('h','hn') "
					+ "AND codigo_hd NOT IN (2006 , 2007, 2008) " + "UNION ALL " + "SELECT 'h' AS tipo,"
					+ "idConcepto as codigo_hd," + "valor as monto," + "descripcion as nombre_hd,"
					+ "0 as proporcional " + "from sw_detalleFiniquito  WHERE codTrabajador = " + COD_TRAB_INT
					+ " AND idConcepto = 2008 AND idContrato = " + idcontrato + " " + "UNION ALL "
					+ "SELECT 'h' AS tipo," + "idConcepto as codigo_hd," + "valor as monto,"
					+ "descripcion as nombre_hd," + "0 as proporcional "
					+ "from sw_detalleFiniquito  WHERE codTrabajador = " + COD_TRAB_INT
					+ " AND idConcepto = 2007 AND idContrato = " + idcontrato + " " + "UNION ALL "
					+ "SELECT 'h' AS tipo," + "idConcepto as codigo_hd," + "valor as monto,"
					+ "descripcion as nombre_hd," + "(SELECT valor FROM  sw_detalleFiniquito  WHERE codTrabajador = "
					+ COD_TRAB_INT + " AND idConcepto = 17  AND idContrato = " + idcontrato + ")as proporcional "
					+ "from sw_detalleFiniquito  WHERE codTrabajador = " + COD_TRAB_INT
					+ " AND idConcepto = 2006  AND idContrato = " + idcontrato + " "

					+ "UNION ALL SELECT " + "'h' AS tipo," + "idConcepto AS codigo_hd," + "if(valor < 0, 0, valor) AS monto,"
					+ "descripcion AS nombre_hd," + "(SELECT " + "valor " + "FROM " + "sw_detalleFiniquito " + "WHERE "
					+ "codTrabajador = " + COD_TRAB_INT + " AND idConcepto = 20003 " + "AND idContrato = " + idcontrato
					+ " ) AS proporcional " + "FROM " + "sw_detalleFiniquito " + "WHERE " + "codTrabajador = "
					+ COD_TRAB_INT + " AND idConcepto = 20000 " + "AND idContrato = " + idcontrato + "";

			System.out.println("/////////////////////////////obtenerHaberesFiniquitos////////////////////");
			System.out.println(sql);
			System.out.println("/////////////////////////////////////////////////");
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				if (rs.getInt("monto") != 0) {
					DatosTrabajadorFiniquito tr = new DatosTrabajadorFiniquito();
					tr.setMontoHaberes(rs.getInt("monto"));
					tr.setNombreHD(rs.getString("nombre_hd"));
					tr.setTotaldiasproporcional(rs.getDouble("proporcional"));
					Lista.add(tr);
				}
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}

	public static ArrayList<DatosTrabajadorFiniquito> obtenersobregiro(int COD_TRAB_INT, int id_cont) throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<DatosTrabajadorFiniquito> Lista = new ArrayList<DatosTrabajadorFiniquito>();

		try {

			sql = "SELECT 'h' AS tipo," + "idConcepto as codigo_hd," + "valor as monto," + "descripcion as nombre_hd,"
					+ "(SELECT valor FROM  sw_detalleFiniquito  WHERE codTrabajador = " + COD_TRAB_INT
					+ " AND idConcepto = 17  AND idContrato = " + id_cont + " )as proporcional "
					+ "from sw_detalleFiniquito  WHERE codTrabajador = " + COD_TRAB_INT
					+ " AND idConcepto = 20001  AND idContrato = " + id_cont + " "

					+ "UNION ALL " + "SELECT 'h' AS tipo," + "idConcepto as codigo_hd," + "valor as monto,"
					+ "descripcion as nombre_hd," + "(SELECT valor FROM  sw_detalleFiniquito  WHERE codTrabajador = "
					+ COD_TRAB_INT + " AND idConcepto = 17  AND idContrato = " + id_cont + ")as proporcional "
					+ "from sw_detalleFiniquito  WHERE codTrabajador = " + COD_TRAB_INT
					+ " AND idConcepto = 20002  AND idContrato = " + id_cont + " ";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				// if(rs.getInt("monto") != 0){
				DatosTrabajadorFiniquito tr = new DatosTrabajadorFiniquito();
				tr.setMontoHaberes(rs.getInt("monto"));
				tr.setNombreHD(rs.getString("nombre_hd"));
				tr.setTotaldiasproporcional(rs.getDouble("proporcional"));
				Lista.add(tr);
				// }
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}

	public static ArrayList<DatosTrabajadorFiniquito> obtenerDescuentosFiniquitos(int COD_TRAB_INT, int id_cont)
			throws Exception {
		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<DatosTrabajadorFiniquito> Lista = new ArrayList<DatosTrabajadorFiniquito>();

		try {

			sql = "SELECT " + "tipo," + "codigo_hd," + "monto," + "(SELECT " + "descripcion " + "FROM "
					+ "SAN_CLEMENTE.sw_p_haberesDescuentos " + "WHERE " + "codigo = codigo_hd) AS nombre_hd,"
					+ "0 as proporcional " + "FROM " + "sw_haberesDescuentosFiniquito " + "WHERE "
					+ "codigo_trabajador = " + COD_TRAB_INT + " " + "AND idContrato =  " + id_cont + " "
					+ " and codigo_trabajador = " + COD_TRAB_INT + " " + "and tipo = 'd' "
					+ "AND codigo_hd NOT IN (2006 , 2007, 2008) "

					+ "UNION ALL SELECT " + "'d' AS tipo," + "idConcepto AS codigo_hd," + "valor AS monto,"
					+ "descripcion AS nombre_hd," + "0 AS proporcional " + "FROM " + "sw_detalleFiniquito " + "WHERE "
					+ "codTrabajador = " + COD_TRAB_INT + " " + "AND idConcepto = 20001 " + "AND idContrato = "
					+ id_cont + " ";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				DatosTrabajadorFiniquito tr = new DatosTrabajadorFiniquito();
				tr.setMontoHaberes(rs.getInt("monto"));
				tr.setNombreHD(rs.getString("nombre_hd"));
				tr.setTotaldiasproporcional(rs.getDouble("proporcional"));
				Lista.add(tr);
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return Lista;
	}

	///////////////////////// busacar fifniquito para
	///////////////////////// eliminar///////////////////////////////////
	public static ArrayList<DatosTrabajadorFiniquito> getBuscarFiniquitoEliminar(String sociedad, String huerto,
			String zona, String ceco, String fechaTermino, String tipocontrato, String codtrab, String estado_proceso,
			int rolPrivado) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "SELECT DISTINCT " + "sw.id_finiquito," + "sw.total_pago_finiquito," + "sw.cod_trabajador,"
					+ "sw.id_contrato," + "cnt.fechaInicio_actividad," + "cnt.FechaTerminoContrato,"
					+ "tr.apellidoPaterno," + "tr.apellidoMaterno," + "tr.rut," + "tr.nombre,"
					+ "(select descripcion from sw_m_articuloTerminoContrato where idArticuloTerminoContrato = cnt.articuloTerminoContrato) as articulo,"
					+ "cnt.incisoTerminoContrato," + "cnt.letraTerminoContrato " + "FROM " + "sw_finiquitos sw "
					+ "INNER JOIN " + "trabajadores tr ON tr.codigo = sw.cod_trabajador " + "INNER JOIN "
					+ "contratos cnt ON cnt.id = sw.id_contrato "
					+ "WHERE  sw.id_contrato = (select max(id_contrato) from sw_finiquitos WHERE cod_trabajador = sw.cod_trabajador) AND "
					+ "1 = 1 ";

			if (rolPrivado == 1) {
				sql += " and  tr.rolPrivado in (1,0) ";
			} else {
				sql += " and  tr.rolPrivado in (0) ";
			}
			if ("null".equals(sociedad)) {
			} else {
				sql += " and sw.id_sociedad = " + sociedad + "";
			}
			if ("null".equals(codtrab)) {
			} else {
				sql += " and sw.cod_trabajador = " + codtrab + "";
			}
			if ("null".equals(fechaTermino)) {
			} else {
				sql += " and cnt.FechaTerminoContrato = '" + fechaTermino + "'";
			}
			if ("null".equals(huerto)) {
			} else {
				sql += " and tr.idHuerto = '" + huerto + "'";
			}
			if ("null".equals(zona)) {
			} else {
				sql += " and tr.idZona = '" + zona + "'";
			}
			if ("null".equals(ceco)) {
			} else {
				sql += " and tr.idCECO = '" + ceco + "'";
			}
			if ("null".equals(tipocontrato)) {
			} else {
				sql += " and cnt.tipoContrato = " + tipocontrato + "";
			}
			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and tr.estadoProceso = " + estado_proceso + " ";
			}

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				DatosTrabajadorFiniquito hd = new DatosTrabajadorFiniquito();

				hd.setId(rs.getInt("id_finiquito"));
				hd.setTotal_finiquito(rs.getInt("total_pago_finiquito"));
				hd.setCodigo_trabajador(rs.getInt("cod_trabajador"));
				hd.setFecha_inicio(rs.getString("fechaInicio_actividad"));
				hd.setFecha_termino(rs.getString("FechaTerminoContrato"));
				hd.setApellidoPaterno(rs.getString("apellidoPaterno"));
				hd.setApellidoMaterno(rs.getString("apellidoMaterno"));
				hd.setNombre(rs.getString("nombre"));
				hd.setRut(rs.getString("rut"));
				hd.setId_contrato(rs.getInt("id_contrato"));

				String nombre_causal = rs.getString("articulo") + "-" + rs.getInt("incisoTerminoContrato") + "-"
						+ rs.getInt("letraTerminoContrato");
				hd.setNombre_causal(nombre_causal);

				lista.add(hd);

			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	///////////////// ELIMINAR FINIQUITOS/////////////////////////
	public static boolean eliminarFiniquitos(int id, int idcontrato, int codtrab) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		PreparedStatement ps3 = null;
		String sql3 = "";

		PreparedStatement ps4 = null;
		String sql4 = "";

		ConnectionDB db = new ConnectionDB();
		try {
			db.conn.setAutoCommit(false);

			sql = "DELETE FROM sw_finiquitos WHERE id_finiquito =" + id + "";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			System.out.println(sql);
			sql2 = "DELETE FROM sw_detalleFiniquito WHERE codTrabajador = " + codtrab + " " + "AND idContrato = "
					+ idcontrato + " ";
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);
			ps2.execute();

			sql3 = "UPDATE contratos SET EstadoContrato = 1 WHERE id = " + idcontrato + "";

			System.out.println(sql3);
			ps3 = db.conn.prepareStatement(sql3);
			ps3.execute();

			sql4 = "DELETE FROM sw_movimientoPrevired WHERE cod_trabajador = " + codtrab + " AND id_contrato = "
					+ idcontrato + " and movimento_previred = 2";
			ps4 = db.conn.prepareStatement(sql4);
			ps4.execute();

			db.conn.commit();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
			db.conn.rollback();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			db.conn.rollback();
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	///////////////////////// BUSCAR TRABAJADORES HD
	///////////////////////// FINIQUITOS///////////////////////////////////
	public static ArrayList<DatosTrabajadorFiniquito> getBuscarHDFiniquitoTrabajadores(String sociedad, String huerto,
			String zona, String ceco, String tipocontrato, String codtrab, String periodo, String estado_proceso,
			int rolPrivado) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "SELECT DISTINCT " + "sw.codigo_trabajador," + "tr.apellidoPaterno," + "tr.apellidoMaterno,"
					+ "tr.rut," + "tr.nombre " + "FROM sw_haberesDescuentosFiniquito sw "
					+ "INNER JOIN trabajadores tr ON tr.codigo = sw.codigo_trabajador "
					+ "INNER JOIN contratos co ON co.id = sw.idContrato "
					+ "WHERE  sw.idContrato = (select max(idContrato) from sw_haberesDescuentosFiniquito WHERE codigo_trabajador = sw.codigo_trabajador) AND "
					+ "1 = 1 ";

			if ("null".equals(sociedad)) {
			} else {
				sql += " and co.idSociedad = " + sociedad + "";
			}
			if ("null".equals(codtrab)) {
			} else {
				sql += " and sw.codigo_trabajador = " + codtrab + "";
			}
			if ("null".equals(huerto)) {
			} else {
				sql += " and tr.idHuerto = '" + huerto + "'";
			}
			if ("null".equals(zona)) {
			} else {
				sql += " and tr.idZona = '" + zona + "'";
			}
			if ("null".equals(ceco)) {
			} else {
				sql += " and tr.idCECO = '" + ceco + "'";
			}
			if ("null".equals(tipocontrato)) {
			} else {
				sql += " and co.tipoContrato = " + tipocontrato + "";
			}
			if ("null".equals(periodo)) {
			} else {
				sql += " and sw.periodo = " + periodo + "";
			}

			if (rolPrivado == 1) {
				sql += " and  tr.rolPrivado in (1,0) ";
			} else {
				sql += " and  tr.rolPrivado in (0) ";
			}

			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and tr.estadoProceso = " + estado_proceso + " ";
			}

			sql += " group by sw.codigo_trabajador,tr.apellidoPaterno,tr.apellidoMaterno,tr.rut,tr.nombre";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				DatosTrabajadorFiniquito hd = new DatosTrabajadorFiniquito();

				hd.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
				hd.setApellidoPaterno(rs.getString("apellidoPaterno"));
				hd.setApellidoMaterno(rs.getString("apellidoMaterno"));
				hd.setNombre(rs.getString("nombre"));
				hd.setRut(rs.getString("rut"));

				lista.add(hd);

			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	///////////////////////// BUSCAR TRABAJADORES HD
	///////////////////////// FINIQUITOS///////////////////////////////////
	public static ArrayList<DatosTrabajadorFiniquito> getBuscarHDFiniquitoTrabajadoresTabla(String sociedad,
			String huerto, String zona, String ceco, String tipocontrato, String codtrab, String periodo,
			String estado_proceso, int rolPrivado) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "SELECT DISTINCT " + "sw.id," + "sw.codigo_trabajador," + "tr.nombre," + "tr.apellidoPaterno,"
					+ "tr.apellidoMaterno," + "sw.periodo," + "sw.tipo,"
					+ "(select descripcion from sw_p_haberesDescuentos WHERE codigo = sw.codigo_hd) as concepto,"
					+ "tr.rut," + "sw.codigo_hd," + "sw.monto," + "sw.dias," + "sw.idContrato "
					+ "FROM sw_haberesDescuentosFiniquito sw "
					+ "INNER JOIN trabajadores tr ON tr.codigo = sw.codigo_trabajador "
					+ "INNER JOIN contratos co ON co.id = sw.idContrato "
					+ "WHERE  sw.idContrato = (select max(idContrato) from sw_haberesDescuentosFiniquito WHERE codigo_trabajador = sw.codigo_trabajador) AND "
					+ " 1 = 1 ";

			if ("null".equals(sociedad)) {
			} else {
				sql += " and co.idSociedad = " + sociedad + "";
			}
			if ("null".equals(codtrab)) {
			} else {
				sql += " and sw.codigo_trabajador = " + codtrab + "";
			}
			if ("null".equals(huerto)) {
			} else {
				sql += " and tr.idHuerto = '" + huerto + "'";
			}
			if ("null".equals(zona)) {
			} else {
				sql += " and tr.idZona = '" + zona + "'";
			}
			if ("null".equals(ceco)) {
			} else {
				sql += " and tr.idCECO = '" + ceco + "'";
			}
			if ("null".equals(tipocontrato)) {
			} else {
				sql += " and co.tipoContrato = " + tipocontrato + "";
			}
			if ("null".equals(periodo)) {
			} else {
				sql += " and sw.periodo = " + periodo + "";
			}

			if (rolPrivado == 1) {
				sql += " and  tr.rolPrivado in (1,0) ";
			} else {
				sql += " and  tr.rolPrivado in (0) ";
			}

			if ("null".equals(estado_proceso)) {
			} else {
				sql += " and tr.estadoProceso = " + estado_proceso + " ";
			}

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				DatosTrabajadorFiniquito hd = new DatosTrabajadorFiniquito();

				hd.setId(rs.getInt("id"));
				hd.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
				hd.setApellidoPaterno(rs.getString("apellidoPaterno"));
				hd.setApellidoMaterno(rs.getString("apellidoMaterno"));
				hd.setNombre(rs.getString("nombre"));
				hd.setRut(rs.getString("rut"));
				hd.setPeriodo(rs.getInt("periodo"));
				hd.setTipoHD(rs.getString("tipo"));
				hd.setNombre_concepto(rs.getString("concepto"));
				hd.setCodHD(rs.getInt("codigo_hd"));
				hd.setMontoHaberes(rs.getInt("monto"));
				hd.setDiasint(rs.getDouble("dias"));
				hd.setId_contrato(rs.getInt("idContrato"));

				lista.add(hd);

			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	/////////////////////// GET datos trabajador HD Finiquitos para
	/////////////////////// modificar///////////////////////////////////
	public static ArrayList<DatosTrabajadorFiniquito> getModifiHDFiniquito(int id, int contrato, int codtra)
			throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT tipo,codigo_hd,monto,dias,periodo FROM sw_haberesDescuentosFiniquito " + "WHERE id = " + id
					+ " " + "AND codigo_trabajador = " + codtra + " " + "AND idContrato = " + contrato + "";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				DatosTrabajadorFiniquito cr = new DatosTrabajadorFiniquito();

				cr.setTipoHD(rs.getString("tipo"));
				cr.setCodHD(rs.getInt("codigo_hd"));
				cr.setMontoHDint(rs.getInt("monto"));
				cr.setDiasint(rs.getDouble("dias"));
				cr.setPeriodo(rs.getInt("periodo"));

				lista.add(cr);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	public static boolean updateTHDF(DatosTrabajadorFiniquito pre) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "Update sw_haberesDescuentosFiniquito set " + "periodo = " + pre.getPeriodo() + ", " + "tipo ='"
					+ pre.getTipoHD() + "'," + "codigo_hd = " + pre.getCodHD() + "," + "monto = " + pre.getMontoHD()
					+ "," + "dias = " + pre.getDiasint() + " " + "where id = " + pre.getId() + "";
			System.out.println(sql);

			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	public static boolean eliminarHDF(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM sw_haberesDescuentosFiniquito WHERE id=" + id + "";
			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	//// fechas de termino finiquitos

	public static ArrayList<DatosTrabajadorFiniquito> getAllfechasxPereiodoF(int periodo, String empresa)
			throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT " + "DATE_FORMAT(FechaTerminoContrato, '%d-%m-%Y') AS fecha " + "FROM " + "contratos "
					+ "WHERE " + "DATE_FORMAT(FechaTerminoContrato, '%Y%m') = " + periodo + " " + "AND  idSociedad  = "
					+ empresa + " and EstadoContrato = 1 and paraFiniquitar = 1 " + "GROUP BY FechaTerminoContrato;";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				DatosTrabajadorFiniquito hd = new DatosTrabajadorFiniquito();

				hd.setFecha(rs.getString("fecha"));

				lista.add(hd);
				System.out.println(lista);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	public static ArrayList<DatosTrabajadorFiniquito> getAllfechasxPereiodoFImprimir(int periodo, String empresa)
			throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<DatosTrabajadorFiniquito> lista = new ArrayList<DatosTrabajadorFiniquito>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT " + "DATE_FORMAT(fecha_pago, '%d-%m-%Y') AS fecha " + "FROM " + "sw_finiquitos " + "WHERE "
					+ "DATE_FORMAT(fecha_termino_contrato, '%Y%m') = " + periodo + " " + "AND  id_sociedad  = "
					+ empresa + " " + "GROUP BY fecha_pago;";

			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				DatosTrabajadorFiniquito hd = new DatosTrabajadorFiniquito();

				hd.setFecha(rs.getString("fecha"));

				lista.add(hd);
				System.out.println(lista);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
}
