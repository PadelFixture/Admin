package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.classSW.Colaboradores;
import lib.classSW.Tsimple;
import lib.classSW.trabajadores;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import lib.utils.TimeUtility;

public class trabajadoresDB {

	private final static Logger LOG = LoggerFactory.getLogger(trabajadoresDB.class);
	
	// Insert Trabajador
	public static boolean insertTrabajador(trabajadores trabajadores) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try {

			sql = "INSERT INTO trabajadores ( codigo, rut, nombre, " + "rutTemporal, pasaporte, fNacimiento, "
					+ "apellidoPaterno, apellidoMaterno, direccion, " + "telefono, celular, id_perfil, "
					+ "hrs_semanal, email, asign_zona_extrema, " + "id_pet_tbl_PT, id_rechazo, id_status, "
					+ "fechaIngresoCompania, idRegion, idComuna, " + "idSubDivision, idSubGrupo, idGenero, "
					+ "idNacionalidad, idEstadoCivil, idProvincia, " + "pensionados, sCesantia, capacidades, "
					+ "subsidio, mayor11Anos, recurrente, " + "tipoTrabajador, division, grupo, "
					+ "nombreEmergencia, telefonoEmergencia, emailEmergencia, " + "parentesco, estado_preselec, agro, "
					+ "trabajadorAgricola, valorFijo, fechaCreacion, " + "idVacaciones, recorrido, idSector, "
					+ "idAFP, idMonedaAFP, valorAFP, " + "idIsapre, idMonedaPlan, valorPlan, "
					+ "idMonedaAdicionalAFP, valorAdicionalAFP, fechaAfiliacionAFP, "
					+ "institucionAPV, idMonedaAPV, valorDepositoAPV, "
					+ "institucionConvenido, idMonedaConvenido, valorConvenido, "
					+ "nContrato, idEtnia, idContratista, idTipoLicenciaConducir,"
					+ "pensionadosCotizantes, idHuerto, idZona, idCECO, " + "calle, ndireccion, depto, poblacion, "
					+ "idFaena, rolPrivado, "
					+ "razonSocial, trabajadorJoven, idAdicionalAFP, estadoProceso )"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "					 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "					 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "					 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " 
					+ "					 ?, ?, ?, ?,"
					+ " ? , ?,  "
					+ " ? , ?, ?, ? ) ";

			ps = db.conn.prepareStatement(sql);

			ps.setString(i++, trabajadores.getCodigo());
			ps.setString(i++, trabajadores.getRut());
			ps.setString(i++, trabajadores.getNombre().trim());
			ps.setString(i++, trabajadores.getRutTemporal());
			ps.setString(i++, trabajadores.getPasaporte());
			ps.setString(i++, convertStringToYYYYMMDD(trabajadores.getfNacimiento()));
			ps.setString(i++, trabajadores.getApellidoPaterno().trim());
			ps.setString(i++, trabajadores.getApellidoMaterno().trim());
			ps.setString(i++, trabajadores.getDireccion());
			ps.setString(i++, trabajadores.getTelefono());
			ps.setString(i++, trabajadores.getCelular());
			ps.setInt(i++, trabajadores.getId_perfil());
			ps.setInt(i++, trabajadores.getHrs_semanal());
			ps.setString(i++, trabajadores.getEmail());
			ps.setInt(i++, trabajadores.getAsign_zona_extrema());
			ps.setInt(i++, trabajadores.getId_pet_tbl_PT());
			ps.setString(i++, trabajadores.getId_rechazo());
			ps.setInt(i++, trabajadores.getId_status());
			ps.setString(i++, convertStringToYYYYMMDD(null));
			ps.setInt(i++, trabajadores.getIdRegion());
			ps.setInt(i++, trabajadores.getIdComuna());
			ps.setInt(i++, trabajadores.getIdSubDivision());
			ps.setInt(i++, trabajadores.getIdSubGrupo());
			ps.setInt(i++, trabajadores.getIdGenero());
			ps.setInt(i++, trabajadores.getIdNacionalidad());
			ps.setInt(i++, trabajadores.getIdEstadoCivil());
			ps.setInt(i++, trabajadores.getIdProvincia());
			ps.setInt(i++, trabajadores.getPensionados());
			ps.setInt(i++, trabajadores.getsCesantia());
			ps.setInt(i++, trabajadores.getCapacidades());
			ps.setInt(i++, trabajadores.getSubsidio());
			ps.setInt(i++, trabajadores.getMayor11Anos());
			ps.setInt(i++, trabajadores.getRecurrente());
			ps.setInt(i++, trabajadores.getTipoTrabajador());
			ps.setInt(i++, trabajadores.getDivision());
			ps.setInt(i++, trabajadores.getGrupo());
			ps.setString(i++, trabajadores.getNombreEmergencia());
			ps.setString(i++, trabajadores.getTelefonoEmergencia());
			ps.setString(i++, trabajadores.getEmailEmergencia());
			ps.setString(i++, trabajadores.getParentesco());
			ps.setInt(i++, trabajadores.getEstado_preselec());
			ps.setInt(i++, trabajadores.getAgro());
			ps.setInt(i++, trabajadores.getTrabajadorAgricola());
			ps.setString(i++, trabajadores.getValorFijo());
			ps.setString(i++, convertStringToYYYYMMDD(trabajadores.getFechaCreacion()));
			ps.setInt(i++, trabajadores.getIdVacaciones());
			ps.setInt(i++, trabajadores.getRecorrido());
			ps.setInt(i++, trabajadores.getIdSector());
			ps.setInt(i++, trabajadores.getIdAFP());
			ps.setInt(i++, trabajadores.getIdMonedaAFP());
			ps.setDouble(i++, trabajadores.getValorAFP());
			ps.setInt(i++, trabajadores.getIdIsapre());
			ps.setInt(i++, trabajadores.getIdMonedaPlan());
			ps.setDouble(i++, trabajadores.getValorPlan());
			ps.setInt(i++, trabajadores.getIdMonedaAdicionalAFP());
			ps.setDouble(i++, trabajadores.getValorAdicionalAFP());
			ps.setString(i++, convertStringToYYYYMMDD(trabajadores.getFechaAfiliacionAFP()));
			ps.setInt(i++, trabajadores.getInstitucionAPV() == 0 ? 1: trabajadores.getInstitucionAPV());
			ps.setInt(i++, trabajadores.getIdMonedaAPV());
			ps.setDouble(i++, trabajadores.getValorDepositoAPV());
			ps.setInt(i++, trabajadores.getInstitucionConvenido());
			ps.setInt(i++, trabajadores.getIdMonedaConvenido());
			ps.setDouble(i++, trabajadores.getValorConvenido());
			ps.setString(i++, trabajadores.getnContrato());
			ps.setInt(i++, trabajadores.getIdEtnia());
			ps.setString(i++, trabajadores.getIdContratista());
			ps.setInt(i++, trabajadores.getIdTipoLicenciaConducir());
			ps.setInt(i++, trabajadores.getPensionadosCotizantes());
			ps.setString(i++, trabajadores.getIdHuerto());
			ps.setString(i++, trabajadores.getIdZona());
			ps.setString(i++, trabajadores.getIdCECO());
			ps.setString(i++, trabajadores.getCalle());
			ps.setString(i++, trabajadores.getNdireccion());
			ps.setString(i++, trabajadores.getDepto());
			ps.setString(i++, trabajadores.getPoblacion());
			ps.setInt(i++, trabajadores.getIdFaena());
			ps.setInt(i++, trabajadores.getRolPrivado());
			ps.setInt(i++, trabajadores.getRazonSocial());
			ps.setInt(i++, trabajadores.getTrabajadorJoven());
			ps.setInt(i++, trabajadores.getIdAdicionalAFP());
			ps.setString(i++, "0");

			ps.execute();

			return true;

		} catch (Exception ex) {
			LOG.error(ps.toString());
			LOG.error("Error: insertTrabajador: " + ex.getMessage());
		} finally {
			db.conn.close();
		}
		return false;
	}

	public static int getUltimoCodigoTrabajador() throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int total = 0;

		try {

			ps = db.conn.createStatement();
			//sql = " SELECT MAX(codigo) FROM trabajadores ";
			sql = " SELECT MAX(T1.codigo) as codigo FROM ( " +
					" SELECT codigo FROM trabajadores " +
					" UNION ALL " +
					" SELECT codigo FROM sw_trabajadoresPostulante " + 
				" ) T1 ";
			
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			LOG.error("Error: getUltimoCodigoTrabajador " + e.getMessage());
			LOG.error("sql: " + ps.toString());
			throw new Exception("getTrabajadorAll: getUltimoCodigoTrabajador " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	public static int getIdTrabajadorByCodigo(String codigo) throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int id = 0;

		try {

			ps = db.conn.createStatement();
			sql = " SELECT id FROM trabajadores where codigo = " + codigo;
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			LOG.error("Error: getIdTrabajadorByCodigo " + e.getMessage());
			LOG.error("sql: " + ps.toString());
			throw new Exception("getTrabajadorAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return id;
	}

	public static int getCodigoByIdTrabajador(String idTrabajador) throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int codigo = 0;

		try {

			ps = db.conn.createStatement();
			sql = " SELECT codigo FROM trabajadores where id = " + idTrabajador;
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				codigo = rs.getInt(1);
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			LOG.error("Error: getCodigoByIdTrabajador: " + e.getMessage());
			LOG.error("sql: " + sql);
			throw new Exception("getTrabajadorAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return codigo;
	}

	// Actualizar Trabajador
	public static boolean updateTrabajador(trabajadores trabajadores) throws Exception {

		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();

		int i = 1;

		try {

			String sql = "UPDATE trabajadores SET rut = ?, nombre = ?, "
					+ "rutTemporal = ?, pasaporte = ?, fNacimiento = ?, "
					+ "apellidoPaterno = ?, apellidoMaterno = ?, direccion = ?, "
					+ "telefono = ?, celular = ?, id_perfil = ?, "
					+ "hrs_semanal = ?, email = ?, asign_zona_extrema = ?, "
					+ "id_pet_tbl_PT = ?, id_rechazo = ?, id_status = ?, "
					+ "fechaIngresoCompania = ?, idRegion = ?, idComuna = ?, "
					+ "idSubDivision = ?, idSubGrupo = ?, idGenero = ?, "
					+ "idNacionalidad = ?, idEstadoCivil = ?, idProvincia = ?, "
					+ "pensionados = ?, sCesantia = ?, capacidades = ?, "
					+ "subsidio = ?, mayor11Anos = ?, recurrente = ?, "
					+ "tipoTrabajador = ?, division = ?, grupo = ?, "
					+ "nombreEmergencia = ?, telefonoEmergencia = ?, emailEmergencia = ?, "
					+ "parentesco = ?, estado_preselec = ?, agro = ?, "
					+ "trabajadorAgricola = ?, valorFijo = ?, fechaCreacion = ?, "
					+ "idVacaciones = ?, recorrido = ?, idSector = ?, " + "idAFP = ?, idMonedaAFP = ?, valorAFP = ?, "
					+ "idIsapre = ?, idMonedaPlan = ?, valorPlan = ?, "
					+ "idMonedaAdicionalAFP = ?, valorAdicionalAFP = ?, fechaAfiliacionAFP = ?, "
					+ "institucionAPV = ?, idMonedaAPV = ?, valorDepositoAPV = ?, "
					+ "institucionConvenido = ?, idMonedaConvenido = ?, valorConvenido = ?, "
					+ "nContrato = ?, idEtnia = ?, idContratista = ?, idTipoLicenciaConducir = ?,"
					+ " pensionadosCotizantes = ?, idHuerto = ?, idZona = ?, idCECO = ?, "
					+ " calle = ?, ndireccion = ?, depto = ?, poblacion = ?,"
					+ " idFaena = ?, rolPrivado = ?, razonSocial = ?, trabajadorJoven = ?, idAdicionalAFP = ? " + " WHERE (id = ? ) ";

			ps = db.conn.prepareStatement(sql);

			//ps.setString(i++, trabajadores.getCodigo());
			ps.setString(i++, trabajadores.getRut());
			ps.setString(i++, trabajadores.getNombre());
			ps.setString(i++, trabajadores.getRutTemporal());
			ps.setString(i++, trabajadores.getPasaporte());
			ps.setString(i++, convertStringToYYYYMMDD(trabajadores.getfNacimiento()));
			ps.setString(i++, trabajadores.getApellidoPaterno());
			ps.setString(i++, trabajadores.getApellidoMaterno());
			ps.setString(i++, trabajadores.getDireccion());
			ps.setString(i++, trabajadores.getTelefono());
			ps.setString(i++, trabajadores.getCelular());
			ps.setInt(i++, trabajadores.getId_perfil());
			ps.setInt(i++, trabajadores.getHrs_semanal());
			ps.setString(i++, trabajadores.getEmail());
			ps.setInt(i++, trabajadores.getAsign_zona_extrema());
			ps.setInt(i++, trabajadores.getId_pet_tbl_PT());
			ps.setString(i++, trabajadores.getId_rechazo());
			ps.setInt(i++, trabajadores.getId_status());
			ps.setString(i++, convertStringToYYYYMMDD(trabajadores.getFechaIngresoCompania()));
			ps.setInt(i++, trabajadores.getIdRegion());
			ps.setInt(i++, trabajadores.getIdComuna());
			ps.setInt(i++, trabajadores.getIdSubDivision());
			ps.setInt(i++, trabajadores.getIdSubGrupo());
			ps.setInt(i++, trabajadores.getIdGenero());
			ps.setInt(i++, trabajadores.getIdNacionalidad());
			ps.setInt(i++, trabajadores.getIdEstadoCivil());
			ps.setInt(i++, trabajadores.getIdProvincia());
			ps.setInt(i++, trabajadores.getPensionados());
			ps.setInt(i++, trabajadores.getsCesantia());
			ps.setInt(i++, trabajadores.getCapacidades());
			ps.setInt(i++, trabajadores.getSubsidio());
			ps.setInt(i++, trabajadores.getMayor11Anos());
			ps.setInt(i++, trabajadores.getRecurrente());
			ps.setInt(i++, trabajadores.getTipoTrabajador());
			ps.setInt(i++, trabajadores.getDivision());
			ps.setInt(i++, trabajadores.getGrupo());
			ps.setString(i++, trabajadores.getNombreEmergencia());
			ps.setString(i++, trabajadores.getTelefonoEmergencia());
			ps.setString(i++, trabajadores.getEmailEmergencia());
			ps.setString(i++, trabajadores.getParentesco());
			ps.setInt(i++, trabajadores.getEstado_preselec());
			ps.setInt(i++, trabajadores.getAgro());
			ps.setInt(i++, trabajadores.getTrabajadorAgricola());
			ps.setString(i++, trabajadores.getValorFijo());
			ps.setString(i++, convertStringToYYYYMMDD(trabajadores.getFechaCreacion()));
			ps.setInt(i++, trabajadores.getIdVacaciones());
			ps.setInt(i++, trabajadores.getRecorrido());
			ps.setInt(i++, trabajadores.getIdSector());
			ps.setInt(i++, trabajadores.getIdAFP());
			ps.setInt(i++, trabajadores.getIdMonedaAFP());
			ps.setDouble(i++, trabajadores.getValorAFP());
			ps.setInt(i++, trabajadores.getIdIsapre());
			ps.setInt(i++, trabajadores.getIdMonedaPlan());
			ps.setDouble(i++, trabajadores.getValorPlan());
			ps.setInt(i++, trabajadores.getIdMonedaAdicionalAFP());
			ps.setDouble(i++, trabajadores.getValorAdicionalAFP());
			ps.setString(i++, convertStringToYYYYMMDD(trabajadores.getFechaAfiliacionAFP()));
			ps.setInt(i++, trabajadores.getInstitucionAPV());
			ps.setInt(i++, trabajadores.getIdMonedaAPV());
			ps.setDouble(i++, trabajadores.getValorDepositoAPV());
			ps.setInt(i++, trabajadores.getInstitucionConvenido());
			ps.setInt(i++, trabajadores.getIdMonedaConvenido());
			ps.setDouble(i++, trabajadores.getValorConvenido());
			ps.setString(i++, trabajadores.getnContrato());
			ps.setInt(i++, trabajadores.getIdEtnia());
			ps.setString(i++, trabajadores.getIdContratista());
			ps.setInt(i++, trabajadores.getIdTipoLicenciaConducir());
			ps.setInt(i++, trabajadores.getPensionadosCotizantes());
			ps.setString(i++, trabajadores.getIdHuerto());
			ps.setString(i++, trabajadores.getIdZona());
			ps.setString(i++, trabajadores.getIdCECO());
			ps.setString(i++, trabajadores.getCalle());
			ps.setString(i++, trabajadores.getNdireccion());
			ps.setString(i++, trabajadores.getDepto());
			ps.setString(i++, trabajadores.getPoblacion());
			ps.setInt(i++, trabajadores.getIdFaena());
			ps.setInt(i++, trabajadores.getRolPrivado());
			ps.setInt(i++, trabajadores.getRazonSocial());
			ps.setInt(i++, trabajadores.getTrabajadorJoven());
			ps.setInt(i++, trabajadores.getIdAdicionalAFP());
			ps.setInt(i++, trabajadores.getId());

			ps.execute();

			return true;

		} catch (SQLException e) {
			LOG.error("Error:" + e.getMessage());
			LOG.error("updateTrabajador : " + ps.toString());
		} catch (Exception e) {
			LOG.error("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	
	/// Actualizar Trabajador CECO HUERTO FAENA
		public static boolean updateTrabajadorCECO(trabajadores trabajadores) throws Exception {

			PreparedStatement ps = null;
			ConnectionDB db = new ConnectionDB();

			int i = 1;

			try {

				String sql = " UPDATE trabajadores SET idHuerto = ?, idCECO = ?, idFaena = ? " 
				+ " WHERE (codigo = ? ) ";

				ps = db.conn.prepareStatement(sql);

				ps.setString(i++, trabajadores.getIdHuerto());
				ps.setString(i++, trabajadores.getIdCECO());
				ps.setInt(i++, trabajadores.getIdFaena());
				ps.setString(i++, trabajadores.getCodigo());

				ps.execute();

				return true;

			} catch (SQLException e) {
				LOG.error("Error: " + e.getMessage());
				LOG.error("Error: updateTrabajadorCECO " + ps.toString());
			} catch (Exception e) {
				LOG.error("Error: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return false;
		}
	
		
	/// Actualizar Trabajador CECO HUERTO FAENA
	public static boolean updateTrabajadorEstadoProceso(trabajadores trabajadores) throws Exception {

		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();

		int i = 1;

		try {

			String sql = " UPDATE trabajadores SET estadoProceso = ? " 
			+ " WHERE (codigo = ? ) ";

			ps = db.conn.prepareStatement(sql);

			ps.setString(i++, trabajadores.getEstadoProceso());
			ps.setString(i++, trabajadores.getCodigo());

			ps.execute();

			return true;

		} catch (SQLException e) {
			LOG.error("Error: updateTrabajadorEstadoProceso " + e.getMessage());
			LOG.error("Error: updateTrabajadorEstadoProceso " + ps.toString());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
			return false;
	}
				
	
	// Borrar Trabajador por Id
	public static boolean deleteTrabajadorById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {
			sql = " DELETE FROM trabajadores WHERE id = " + id + "' ";

			ps = db.conn.prepareStatement(sql);
			ps.execute();

			return true;

		} catch (Exception e) {
			LOG.error("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	// Obtener Trabajador por Id
	public static trabajadores getTrabajadorById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		trabajadores tr = new trabajadores();

		try {
			sql = "SELECT * FROM trabajadores WHERE id = '" + id + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				tr = setObjectTrabajadores(rs);

			}
		} catch (Exception e) {
			LOG.error("Error: " + e.getMessage());
			LOG.error("Error: getTrabajadorById " + ps.toString());
		} finally {
			ps.close();
			db.close();
		}
		return tr;
	}

	// Obtener Todos los Trabajadores
	public static ArrayList<trabajadores> getAllTrabajador() throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<trabajadores> lista = new ArrayList<trabajadores>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM trabajadores WHERE tipoTrabajador in (1,2,3) ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				trabajadores tr = new trabajadores();

				tr = setObjectTrabajadores(rs);

				lista.add(tr);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (SQLException e) {

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getTrabajadores: " + e.getMessage());

		} finally {
			db.close();
		}

		// Retornar Lista de Trabajadores
		return lista;

	}// Fin getAllTrabajadors

	// Obtener trabajador por rut
	public static trabajadores getTrabajadorByRut(String rut) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		trabajadores tr = new trabajadores();

		try {
			sql = "SELECT * FROM trabajadores tr "
					+ " INNER JOIN contratos cntt ON (tr.codigo = cntt.codigo_trabajador)"
					+ " WHERE tr.rut = '" + rut + "' AND cntt.partTime <> '1' "
					+ " AND tr.rut NOT IN ( SELECT rutAutorizados FROM sw_m_rutAutorizados ) ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				tr = setObjectTrabajadores(rs);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return tr;
	}
	
	
	// Obtener trabajador por rut
//		public static trabajadores getPostulantesByRut(String rut) throws Exception {
//
//			PreparedStatement ps = null;
//			String sql = "";
//			ConnectionDB db = new ConnectionDB();
//
//			trabajadores tr = new trabajadores();
//
//			try {
//				sql = "SELECT * FROM sw_trabajadoresPostulante tr "
//						+ " WHERE tr.rut = '" + rut + "' ";
//				ps = db.conn.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery(sql);
//
//				while (rs.next()) {
//
//					tr = setObjectTrabajadores(rs);
//				}
//			} catch (Exception e) {
//				System.out.println("Error: getPostulantesByRut " + e.getMessage());
//				LOG.info("Error: getPostulantesByRut " + e.getMessage());
//				LOG.info("Error: getPostulantesByRut " + ps.toString());
//			} finally {
//				ps.close();
//				db.close();
//			}
//			return tr;
//		}
	
		// Obtener trabajador por rut
		public static trabajadores getTrabajadorByRutTemporal(String rut) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			trabajadores tr = new trabajadores();

			try {
				sql = "SELECT * FROM trabajadores tr "
						+ " INNER JOIN contratos cntt ON (tr.codigo = cntt.codigo_trabajador)"
						+ " WHERE tr.rutTemporal = '" + rut + "' AND cntt.partTime <> '1' "
						+ " AND tr.rut NOT IN ( SELECT rutAutorizados FROM sw_m_rutAutorizados ) ";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {

					tr = setObjectTrabajadores(rs);
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return tr;
		}

	// Obtener trabajador por rut
	public static ArrayList<trabajadores> getTrabajadorByColumnAndValue(String column, String value) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<trabajadores> lista = new ArrayList<trabajadores>();

		try {
			sql = "SELECT * FROM trabajadores WHERE " + column + " = " + value + "";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				trabajadores tr = new trabajadores();

				tr = setObjectTrabajadores(rs);

				lista.add(tr);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	// Verificar si ese rut temporal ya fue Ingresado
	public static boolean existTrabajadorByRutTemporal(String rut) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "SELECT * FROM trabajadores WHERE rutTemporal = '" + rut + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				return true;
			}

			return false;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;

	}

	// Obtener todos los trabajadores totales
	public static int getTrabajadorAll(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM trabajadores ";

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
						} else if (row.getCampo().endsWith("_from")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else
							sql += andSql + row.getCampo() + " like'%" + row.getValue() + "%'";
						andSql = " and ";
					}
				}

			}

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			db.conn.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getTrabajadorAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	// Obtener Todos los Trabajadores Contratistas
	public static ArrayList<trabajadores> getAllTrabajadorWithContratoActivo() throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<trabajadores> lista = new ArrayList<trabajadores>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM trabajadores tr " + " INNER JOIN contratos c ON (tr.codigo = c.codigo_trabajador) "
					+ " WHERE EstadoContrato = 1 ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				trabajadores tr = new trabajadores();

				tr = setObjectTrabajadores(rs);

				lista.add(tr);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (SQLException e) {

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getTrabajadores: " + e.getMessage());

		} finally {
			db.close();
		}

		// Retornar Lista de Trabajadores
		return lista;

	}// Fin getAllTrabajadors

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

	/**
	 * Retorna String Convertido en null Si es Vacio
	 * 
	 * @param string
	 * @return String
	 * @throws ParseException
	 */
	public static String convertStringToNULL(String string) throws ParseException {

		if (string == null || string.isEmpty()) {
			return null;
		}

		return string;

	}

	public static String FormatearRUT(String rut) {

		if (rut == null || rut.trim().isEmpty()) {
			return "";
		}

		int cont = 0;
		String format;
		rut = rut.replace(".", "");
		rut = rut.replace("-", "");
		format = "-" + rut.substring(rut.length() - 1);
		for (int i = rut.length() - 2; i >= 0; i--) {
			format = rut.substring(i, i + 1) + format;
			cont++;
			if (cont == 3 && i != 0) {
				format = "." + format;
				cont = 0;
			}
		}
		return format;
	}

	public static trabajadores setObjectTrabajadores(ResultSet rs) throws SQLException {

		trabajadores tr = new trabajadores();

		tr.setId(rs.getInt("id"));
		tr.setCodigo(rs.getString("codigo"));
		tr.setRut(FormatearRUT(rs.getString("rut")));
		tr.setNombre(rs.getString("nombre").toUpperCase());
		tr.setRutTemporal(rs.getString("rutTemporal"));
		tr.setPasaporte(rs.getString("pasaporte"));
		tr.setfNacimiento(rs.getString("fNacimiento"));
		tr.setApellidoPaterno(rs.getString("apellidoPaterno").toUpperCase());
		tr.setApellidoMaterno(rs.getString("apellidoMaterno").toUpperCase());
		tr.setDireccion(rs.getString("direccion"));
		tr.setTelefono(rs.getString("telefono"));
		tr.setCelular(rs.getString("celular"));
		tr.setId_perfil(rs.getInt("id_perfil"));
		tr.setHrs_semanal(rs.getInt("hrs_semanal"));
		tr.setEmail(rs.getString("email"));
		tr.setAsign_zona_extrema(rs.getInt("asign_zona_extrema"));
		tr.setId_pet_tbl_PT(rs.getInt("id_pet_tbl_PT"));
		tr.setId_rechazo(rs.getString("id_rechazo"));
		tr.setId_status(rs.getInt("id_status"));
		tr.setFechaIngresoCompania(rs.getString("fechaIngresoCompania"));
		tr.setIdRegion(rs.getInt("idRegion"));
		tr.setIdComuna(rs.getInt("idComuna"));
		tr.setIdSubDivision(rs.getInt("idSubDivision"));
		tr.setIdSubGrupo(rs.getInt("idSubGrupo"));
		tr.setIdGenero(rs.getInt("idGenero"));
		tr.setIdNacionalidad(rs.getInt("idNacionalidad"));
		tr.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
		tr.setIdProvincia(rs.getInt("idProvincia"));
		tr.setPensionados(rs.getInt("pensionados"));
		tr.setsCesantia(rs.getInt("sCesantia"));
		tr.setCapacidades(rs.getInt("capacidades"));
		tr.setSubsidio(rs.getInt("subsidio"));
		tr.setMayor11Anos(rs.getInt("mayor11Anos"));
		tr.setRecurrente(rs.getInt("recurrente"));
		tr.setTipoTrabajador(rs.getInt("tipoTrabajador"));
		tr.setDivision(rs.getInt("division"));
		tr.setGrupo(rs.getInt("grupo"));
		tr.setNombreEmergencia(rs.getString("nombreEmergencia"));
		tr.setTelefonoEmergencia(rs.getString("telefonoEmergencia"));
		tr.setEmailEmergencia(rs.getString("emailEmergencia"));
		tr.setParentesco(rs.getString("parentesco"));
		tr.setEstado_preselec(rs.getInt("estado_preselec"));
		tr.setAgro(rs.getInt("agro"));
		tr.setTrabajadorAgricola(rs.getInt("trabajadorAgricola"));
		tr.setValorFijo(rs.getString("valorFijo"));
		tr.setFechaCreacion(rs.getString("fechaCreacion"));
		tr.setIdVacaciones(rs.getInt("idVacaciones"));
		tr.setRecorrido(rs.getInt("recorrido"));
		tr.setIdSector(rs.getInt("idSector"));
		tr.setIdAFP(rs.getInt("idAFP"));
		tr.setIdMonedaAFP(rs.getInt("idMonedaAFP"));
		tr.setValorAFP(rs.getDouble("valorAFP"));
		tr.setIdIsapre(rs.getInt("idIsapre"));
		tr.setIdMonedaPlan(rs.getInt("idMonedaPlan"));
		tr.setValorPlan(rs.getDouble("valorPlan"));
		tr.setIdMonedaAdicionalAFP(rs.getInt("idMonedaAdicionalAFP"));
		tr.setValorAdicionalAFP(rs.getDouble("valorAdicionalAFP"));
		tr.setFechaAfiliacionAFP(rs.getString("fechaAfiliacionAFP"));
		tr.setInstitucionAPV(rs.getInt("institucionAPV"));
		tr.setIdMonedaAPV(rs.getInt("idMonedaAPV"));
		tr.setValorDepositoAPV(rs.getDouble("valorDepositoAPV"));
		tr.setInstitucionConvenido(rs.getInt("institucionConvenido"));
		tr.setIdMonedaConvenido(rs.getInt("idMonedaConvenido"));
		tr.setValorConvenido(rs.getDouble("valorConvenido"));
		tr.setnContrato(rs.getString("nContrato"));
		tr.setIdEtnia(rs.getInt("idEtnia"));
		tr.setIdContratista(rs.getString("idContratista"));
		tr.setIdTipoLicenciaConducir(rs.getInt("idTipoLicenciaConducir"));
		tr.setPensionadosCotizantes(rs.getInt("pensionadosCotizantes"));
		tr.setIdHuerto(rs.getString("idHuerto"));
		tr.setIdZona(rs.getString("idZona"));
		tr.setIdCECO(rs.getString("idCECO"));
		tr.setCalle(rs.getString("calle"));
		tr.setNdireccion(rs.getString("ndireccion"));
		tr.setDepto(rs.getString("depto"));
		tr.setPoblacion(rs.getString("poblacion"));
		tr.setIdFaena(rs.getInt("idFaena"));
		tr.setRolPrivado(rs.getInt("rolPrivado"));
		tr.setRazonSocial(rs.getInt("razonSocial"));
		tr.setTrabajadorJoven(rs.getInt("trabajadorJoven"));
		tr.setIdAdicionalAFP(rs.getInt("idAdicionalAFP"));
		tr.setEstadoProceso(rs.getString("estadoProceso"));

		return tr;

	}
	
	public static Colaboradores setObjectColaboradores(ResultSet rs) throws SQLException {

		Colaboradores tr = new Colaboradores();

		tr.setId(rs.getInt("id"));
		tr.setCodigo(rs.getString("codigo"));
		tr.setRut(FormatearRUT(rs.getString("rut")));
		tr.setNombre(rs.getString("nombre").toUpperCase());
		tr.setRutTemporal(rs.getString("rutTemporal"));
		tr.setPasaporte(rs.getString("pasaporte"));
		tr.setfNacimiento(rs.getString("fNacimiento"));
		tr.setApellidoPaterno(rs.getString("apellidoPaterno").toUpperCase());
		tr.setApellidoMaterno(rs.getString("apellidoMaterno").toUpperCase());
		tr.setDireccion(rs.getString("direccion"));
		tr.setTelefono(rs.getString("telefono"));
		tr.setCelular(rs.getString("celular"));
		tr.setId_perfil(rs.getInt("id_perfil"));
		tr.setHrs_semanal(rs.getInt("hrs_semanal"));
		tr.setEmail(rs.getString("email"));
		tr.setAsign_zona_extrema(rs.getInt("asign_zona_extrema"));
		tr.setId_pet_tbl_PT(rs.getInt("id_pet_tbl_PT"));
		tr.setId_rechazo(rs.getString("id_rechazo"));
		tr.setId_status(rs.getInt("id_status"));
		tr.setFechaIngresoCompania(rs.getString("fechaIngresoCompania"));
		tr.setIdRegion(rs.getInt("idRegion"));
		tr.setIdComuna(rs.getInt("idComuna"));
		tr.setIdSubDivision(rs.getInt("idSubDivision"));
		tr.setIdSubGrupo(rs.getInt("idSubGrupo"));
		tr.setIdGenero(rs.getInt("idGenero"));
		tr.setIdNacionalidad(rs.getInt("idNacionalidad"));
		tr.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
		tr.setIdProvincia(rs.getInt("idProvincia"));
		tr.setPensionados(rs.getInt("pensionados"));
		tr.setsCesantia(rs.getInt("sCesantia"));
		tr.setCapacidades(rs.getInt("capacidades"));
		tr.setSubsidio(rs.getInt("subsidio"));
		tr.setMayor11Anos(rs.getInt("mayor11Anos"));
		tr.setRecurrente(rs.getInt("recurrente"));
		tr.setTipoTrabajador(rs.getInt("tipoTrabajador"));
		tr.setDivision(rs.getInt("division"));
		tr.setGrupo(rs.getInt("grupo"));
		tr.setNombreEmergencia(rs.getString("nombreEmergencia"));
		tr.setTelefonoEmergencia(rs.getString("telefonoEmergencia"));
		tr.setEmailEmergencia(rs.getString("emailEmergencia"));
		tr.setParentesco(rs.getString("parentesco"));
		tr.setEstado_preselec(rs.getInt("estado_preselec"));
		tr.setAgro(rs.getInt("agro"));
		tr.setTrabajadorAgricola(rs.getInt("trabajadorAgricola"));
		tr.setValorFijo(rs.getString("valorFijo"));
		tr.setFechaCreacion(rs.getString("fechaCreacion"));
		tr.setIdVacaciones(rs.getInt("idVacaciones"));
		tr.setRecorrido(rs.getInt("recorrido"));
		tr.setIdSector(rs.getInt("idSector"));
		tr.setIdAFP(rs.getInt("idAFP"));
		tr.setIdMonedaAFP(rs.getInt("idMonedaAFP"));
		tr.setValorAFP(rs.getDouble("valorAFP"));
		tr.setIdIsapre(rs.getInt("idIsapre"));
		tr.setIdMonedaPlan(rs.getInt("idMonedaPlan"));
		tr.setValorPlan(rs.getDouble("valorPlan"));
		tr.setIdMonedaAdicionalAFP(rs.getInt("idMonedaAdicionalAFP"));
		tr.setValorAdicionalAFP(rs.getDouble("valorAdicionalAFP"));
		tr.setFechaAfiliacionAFP(rs.getString("fechaAfiliacionAFP"));
		tr.setInstitucionAPV(rs.getInt("institucionAPV"));
		tr.setIdMonedaAPV(rs.getInt("idMonedaAPV"));
		tr.setValorDepositoAPV(rs.getDouble("valorDepositoAPV"));
		tr.setInstitucionConvenido(rs.getInt("institucionConvenido"));
		tr.setIdMonedaConvenido(rs.getInt("idMonedaConvenido"));
		tr.setValorConvenido(rs.getDouble("valorConvenido"));
		tr.setnContrato(rs.getString("nContrato"));
		tr.setIdEtnia(rs.getInt("idEtnia"));
		tr.setIdContratista(rs.getString("idContratista"));
		tr.setIdTipoLicenciaConducir(rs.getInt("idTipoLicenciaConducir"));
		tr.setPensionadosCotizantes(rs.getInt("pensionadosCotizantes"));
		tr.setIdHuerto(rs.getString("idHuerto"));
		tr.setIdZona(rs.getString("idZona"));
		tr.setIdCECO(rs.getString("idCECO"));
		tr.setCalle(rs.getString("calle"));
		tr.setNdireccion(rs.getString("ndireccion"));
		tr.setDepto(rs.getString("depto"));
		tr.setPoblacion(rs.getString("poblacion"));
		tr.setIdFaena(rs.getInt("idFaena"));
		tr.setRolPrivado(rs.getInt("rolPrivado"));
		tr.setRazonSocial(rs.getInt("razonSocial"));
		tr.setTrabajadorJoven(rs.getInt("trabajadorJoven"));
		tr.setIdAdicionalAFP(rs.getInt("idAdicionalAFP"));
		tr.setEstadoProceso(rs.getString("estadoProceso"));
		tr.setEstadoContrato(rs.getString("estadoContrato"));
		tr.setSociedad(rs.getString("sociedad"));
		tr.setFechaInicioContrato(rs.getString("fechaInicioContrato"));

		return tr;

	}

	// Obtener Todos los Trabajadores con filtros
	public static ArrayList<trabajadores> getAllTrabajadorWithFilter(ArrayList<filterSql> filter) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<trabajadores> lista = new ArrayList<trabajadores>();

		try {

			// (Es necesario el distinct?) fue puesto porque muestra duplicados de trabajadores activos al permitir
			// agregar trabajadores paraFiniquitar (creo :o)
			sql = " SELECT DISTINCT tr.*, ct.idSociedad as idSociedad FROM trabajadores tr INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador)  ";
			ps = db.conn.prepareStatement(sql);
			
			String sql_orderBy = "";

			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().equals(("periodo"))) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + " DATE_FORMAT(fechaInicio_actividad, '%Y-%m') " + " <= DATE_FORMAT('"
									+ sqlDate.format(formatter.parse("01-"+row.getValue())) + "','%Y-%m') ";
							sql += " AND ( DATE_FORMAT(FechaTerminoContrato, '%Y-%m') >= DATE_FORMAT('"+ sqlDate.format(formatter.parse("01-"+row.getValue())) + "','%Y-%m') OR FechaTerminoContrato IS NULL) ";
						}
						else if (row.getCampo().equals(("periodo_contrato"))) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + " DATE_FORMAT(fechaInicio_actividad, '%Y-%m') " + " = DATE_FORMAT('"
									+ sqlDate.format(formatter.parse("01-"+row.getValue())) + "','%Y-%m') ";
						}
						else if (row.getCampo().endsWith("fechaIngreso")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + " DATE_FORMAT(fechaInicio_actividad, '%Y-%m-%d') " + " = DATE_FORMAT('"
									+ sqlDate.format(formatter.parse(row.getValue())) + "','%Y-%m-%d') ";
						}
						else if (row.getCampo().endsWith("fechaTermino")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + " DATE_FORMAT(FechaTerminoContrato, '%Y-%m-%d') " + " = DATE_FORMAT('"
									+ sqlDate.format(formatter.parse(row.getValue())) + "','%Y-%m-%d') ";
						}
						else if (GeneralUtility.isArray(row.getValue())){
							sql += andSql + row.getCampo() + " in ( "+GeneralUtility.convertJSONArrayToArray(row.getValue())+" ) ";
						}
						else if (GeneralUtility.isNumeric(row.getValue())){
							sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
						}
						else if( "_sqlInjection".equals(row.getCampo()) ){
							sql += andSql + row.getValue();
						}
						else if( "_lastContrato".equals(row.getCampo()) ){
							if("true".equals(row.getValue())){
								sql += andSql + " ct.fechaInicio_actividad >= (SELECT MAX(ct2.fechaInicio_actividad) FROM contratos ct2 WHERE ct2.codigo_trabajador = tr.codigo ) ";
							}else if("false".equals(row.getValue())){
								sql += andSql + " ct.fechaInicio_actividad <= (SELECT MIN(ct2.fechaInicio_actividad) FROM contratos ct2 WHERE ct2.codigo_trabajador = tr.codigo ) ";
							}else{
								sql += andSql + " 1=1 ";
								sql.replace("DISTINCT", " ");
							}
						}
						else if( "_byNombreCompleto".equals(row.getCampo())){
							sql += andSql + " concat(tr.nombre,' ',tr.apellidoPaterno,' ',tr.apellidoMaterno) " + " like '%" + row.getValue() + "%'";
						}
						else if( "_excluirNomina".equals(row.getCampo()) ){
							//SUBQUERY PARA EXCLUIR a los Trabajadores de Nomina
							sql += andSql + " ct.id NOT IN ( SELECT l.id_contrato FROM sw_liquidacion l INNER JOIN sw_nomina n ON (l.id_nomina = n.id_nomina) WHERE l.id_nomina is not null AND n.estado = 1 AND n.periodo = "+ TimeUtility.convertStringToYYYYMM(row.getValue()) +" ) ";  
						}
						else if( "_orderBy".equals(row.getCampo())){
							sql_orderBy = " ORDER BY " + row.getValue() + " ASC " ;
						}
						else if (row.getCampo().endsWith("_date")) {
							sql += andSql + " DATE_FORMAT("+row.getCampo().split("_")[0]+", '%Y-%m-%d') " + " = DATE_FORMAT('"
									+ row.getValue() + "','%Y-%m-%d') ";
						}
						else{
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "'";
						}
						andSql = " and ";
					}
				} // Fin While

			}

			sql += sql_orderBy;
			
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				trabajadores trabajadores = trabajadoresDB.setObjectTrabajadores(rs);
				lista.add(trabajadores);

			}
			
			//Retornar Lista de Trabajadores
			return lista;
			
			// Fin Try
		} catch (Exception e) {
			LOG.error("getAllTrabajadoresWithFilter: " + e.getMessage());
			LOG.error("getAllTrabajadoresWithFilter: " + ps.toString());
			throw new Exception("getAllTrabajadoresWithFilter: " + e.getMessage());
		} finally {
			db.close();
		}

	}// Fin getAllTrabajadors
	
	
	public static ArrayList<Colaboradores> getTrabajadorWithFilter(ArrayList<filterSql> filter) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<Colaboradores> lista = new ArrayList<Colaboradores>();

		try {

			// (Es necesario el distinct?) fue puesto porque muestra duplicados de trabajadores activos al permitir
			// agregar trabajadores paraFiniquitar (creo :o)
			sql = " SELECT DISTINCT tr.*, ct.idSociedad as idSociedad, ct.fechaInicio_actividad as fechaInicioContrato , ct.EstadoContrato as estadoContrato, sc.denominacionSociedad as sociedad FROM trabajadores tr "
					+ " INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador) "
					+ " INNER JOIN sociedad sc ON (ct.idSociedad = sc.idSociedad ) ";
			ps = db.conn.prepareStatement(sql);
			
			String sql_orderBy = "";

			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().equals(("periodo"))) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + " DATE_FORMAT(fechaInicio_actividad, '%Y-%m') " + " <= DATE_FORMAT('"
									+ sqlDate.format(formatter.parse("01-"+row.getValue())) + "','%Y-%m') ";
							sql += " AND ( DATE_FORMAT(FechaTerminoContrato, '%Y-%m') >= DATE_FORMAT('"+ sqlDate.format(formatter.parse("01-"+row.getValue())) + "','%Y-%m') OR FechaTerminoContrato IS NULL) ";
						}
						else if (row.getCampo().equals(("periodo_contrato"))) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + " DATE_FORMAT(fechaInicio_actividad, '%Y-%m') " + " = DATE_FORMAT('"
									+ sqlDate.format(formatter.parse("01-"+row.getValue())) + "','%Y-%m') ";
						}
						else if (row.getCampo().endsWith("fechaIngreso")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + " DATE_FORMAT(fechaInicio_actividad, '%Y-%m-%d') " + " = DATE_FORMAT('"
									+ sqlDate.format(formatter.parse(row.getValue())) + "','%Y-%m-%d') ";
						}
						else if (row.getCampo().endsWith("fechaTermino")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + " DATE_FORMAT(FechaTerminoContrato, '%Y-%m-%d') " + " = DATE_FORMAT('"
									+ sqlDate.format(formatter.parse(row.getValue())) + "','%Y-%m-%d') ";
						}
						else if (GeneralUtility.isArray(row.getValue())){
							sql += andSql + row.getCampo() + " in ( "+GeneralUtility.convertJSONArrayToArray(row.getValue())+" ) ";
						}
						else if (GeneralUtility.isNumeric(row.getValue())){
							sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
						}
						else if( "_sqlInjection".equals(row.getCampo()) ){
							sql += andSql + row.getValue();
						}
						else if( "_lastContrato".equals(row.getCampo()) ){
							if("true".equals(row.getValue())){
								sql += andSql + " ct.fechaInicio_actividad >= (SELECT MAX(ct2.fechaInicio_actividad) FROM contratos ct2 WHERE ct2.codigo_trabajador = tr.codigo ) ";
							}else if("false".equals(row.getValue())){
								sql += andSql + " ct.fechaInicio_actividad <= (SELECT MIN(ct2.fechaInicio_actividad) FROM contratos ct2 WHERE ct2.codigo_trabajador = tr.codigo ) ";
							}else{
								sql += andSql + " 1=1 ";
								sql.replace("DISTINCT", " ");
							}
						}
						else if( "_byNombreCompleto".equals(row.getCampo())){
							sql += andSql + " concat(tr.nombre,' ',tr.apellidoPaterno,' ',tr.apellidoMaterno) " + " like '%" + row.getValue() + "%'";
						}
						else if( "_excluirNomina".equals(row.getCampo()) ){
							//SUBQUERY PARA EXCLUIR a los Trabajadores de Nomina
							sql += andSql + " ct.id NOT IN ( SELECT l.id_contrato FROM sw_liquidacion l INNER JOIN sw_nomina n ON (l.id_nomina = n.id_nomina) WHERE l.id_nomina is not null AND n.estado = 1 AND n.periodo = "+ TimeUtility.convertStringToYYYYMM(row.getValue()) +" ) ";  
						}
						else if( "_orderBy".equals(row.getCampo())){
							sql_orderBy = " ORDER BY " + row.getValue() + " ASC " ;
						}
						else if (row.getCampo().endsWith("_date")) {
							sql += andSql + " DATE_FORMAT("+row.getCampo().split("_")[0]+", '%Y-%m-%d') " + " = DATE_FORMAT('"
									+ row.getValue() + "','%Y-%m-%d') ";
						}
						else{
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "'";
						}
						andSql = " and ";
					}
				} // Fin While

			}

			sql += sql_orderBy;
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				Colaboradores trabajadores = trabajadoresDB.setObjectColaboradores(rs);
				lista.add(trabajadores);

			}
			
			//Retornar Lista de Trabajadores
			return lista;
			
			// Fin Try
		} catch (Exception e) {
			LOG.error("getAllTrabajadoresWithFilter: " + e.getMessage());
			LOG.error("getAllTrabajadoresWithFilter: " + ps.toString());
			throw new Exception("getAllTrabajadoresWithFilter: " + e.getMessage());
		} finally {
			db.close();
		}

	}// Fin getAllTrabajadors
	
	
	
	
	// Obtener Todos los Trabajadores con filtros
	public static ArrayList<trabajadores> getEstadoProceso() throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			ArrayList<trabajadores> lista = new ArrayList<trabajadores>();

			try {

				sql = " SELECT DISTINCT estadoProceso FROM trabajadores ORDER BY estadoProceso ASC ";
				ps = db.conn.prepareStatement(sql);
				
				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {
					trabajadores trabajadores = new trabajadores();
					trabajadores.setEstadoProceso(rs.getString("estadoProceso"));
					lista.add(trabajadores);
				}
				
				//Retornar Lista de Trabajadores
				return lista;
				
				// Fin Try
			} catch (Exception e) {
				LOG.error("getEstadoProceso: " + e.getMessage());
				LOG.error("getEstadoProceso: " + ps.toString());
				throw new Exception("getEstadoProceso: " + e.getMessage());
			} finally {
				db.close();
			}

		}// Fin getEstadoProceso
	

	public static Tsimple getTrabajadorById2(String id) {
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Tsimple tr = new Tsimple();
		

		try {

			stmt = db.conn.createStatement();
			sql = "select trabajadores.id, trabajadores.codigo, contratos.id as idContrato from trabajadores inner join contratos on trabajadores.codigo=contratos.codigo_trabajador "+
				  "where trabajadores.id='"+id+"'";

			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				tr.setId(rs.getInt("id"));
				tr.setCodigo(rs.getInt("codigo"));
				tr.setIdContrato(rs.getInt("idContrato"));

				

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (SQLException e) {

			LOG.error("Error: " + e.getMessage());
			LOG.error("sql: getTrabajadorById2 " + sql);
			LOG.error("sql: getTrabajadorById2 " + stmt.toString());
		} finally {
			db.close();
		}
		
		// Retornar Lista de Trabajadores
		return tr;
	}

}
