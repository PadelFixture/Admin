package lib.db.sw;

import java.sql.Blob;
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

import lib.classSW.ContratoTrabajador;
import lib.classSW.Contratos;
import lib.classSW.contrato;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class contratoDB {

	private final static Logger LOG = LoggerFactory.getLogger(contratoDB.class);

	// Insert Contrato
	public static boolean insertContrato(contrato contrato) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try {

			sql = "INSERT INTO contratos " + " ( " + " codigo_trabajador, " + " id_peticion, " + " codigo_peticion, "
					+ " idSociedad, " + " fechaInicio_actividad, " + " fechaContrato_emitido, "
					+ " FechaTerminoContrato, " + " EstadoContrato, " + " cargo, " + " posicion, " + " tipoContrato, "
					+ " articuloTerminoContrato, " + " incisoTerminoContrato, " + " letraTerminoContrato, "
					+ " fechaPago, " + " lugarPago, " + " horaPago, " + " idTurno, " + " colacionFija, "
					+ " valorFijo, " + " horasSemanales, " + " sueldoBase, " + " movilizacionFija, "
					+ " tipoTrabajador, " + " partTime, " + " supervisor, " + " maquinista, " + " jornada, "
					+ " idHuertoContrato, idCECOContrato, fIngresoCompContrato, idFaenaContrato, tipoMoneda " + "  ) "
					+ " VALUES " + " (?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, contrato.getCodigo_trabajador());
			ps.setInt(i++, contrato.getId_peticion());
			ps.setInt(i++, contrato.getCodigo_peticion());
			ps.setInt(i++, contrato.getId_sociedad());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFecha_inicio_actividad()));
			// ps.setString(i++,
			// convertStringToYYYYMMDD(contrato.getFecha_contrato_emitido()));
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFecha_termino_actividad()));
			ps.setInt(i++, contrato.getEstado_contrato());
			ps.setInt(i++, contrato.getCargo());
			ps.setInt(i++, contrato.getPosicion());
			ps.setInt(i++, contrato.getTipoContrato());
			ps.setInt(i++, contrato.getArticuloTerminoContrato());
			ps.setInt(i++, contrato.getIncisoTerminoContrato());
			ps.setInt(i++, contrato.getLetraTerminoContrato());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFechaPago()));
			ps.setString(i++, contrato.getLugarPago());
			ps.setString(i++, contrato.getHoraPago());
			ps.setInt(i++, contrato.getIdTurno());
			ps.setInt(i++, contrato.getColacionFija());
			ps.setInt(i++, contrato.getValorFijo());
			ps.setInt(i++, contrato.getHorasSemanales());
			ps.setDouble(i++, contrato.getSueldoBase());
			ps.setDouble(i++, contrato.getMovilizacionFija());
			ps.setInt(i++, contrato.getTipoTrabajador());
			ps.setInt(i++, contrato.getPartTime());
			ps.setInt(i++, contrato.getSupervisor());
			ps.setInt(i++, contrato.getMaquinista());
			ps.setInt(i++, contrato.getJornada());
			ps.setString(i++, contrato.getIdHuertoContrato());
			ps.setString(i++, contrato.getIdCECOContrato());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getfIngresoCompContrato()));
			ps.setInt(i++, contrato.getIdFaenaContrato());
			ps.setInt(i++, contrato.getTipoMoneda());
			
			ps.execute();

			return true;

		} catch (Exception e) {
			LOG.info("Error insertContrato QUERY:" + ps.toString());
			System.out.println("Error insertContrato: QUERY" + ps.toString());
			LOG.info("Error insertContrato:" + e.getMessage());
			System.out.println("Error insertContrato:" + e.getMessage());
			e.printStackTrace();

		} finally {
			db.conn.close();
		}

		return false;
	}
	
	public static String getCambioEmpresa(String idContrato, String fechaInicioCambio, String aprobadoPor, String nuevaSociedad) throws Exception{
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;
		
		
		try {

			sql = "  "
			+ " INSERT INTO sw_cambioEmpresa "
			+ " ( fechaSolicitud,  " //1
			+ "   fechaInicioContrato,   " //2
			+ "   fechaFinContrato,  " //3
			+ "   sociedad,  " //4
			+ "   sociedad2,  " //5
			+ "   fechaInicioContrato2,  " //6
			+ "   fechaTerminoContrato2,  " //7
			+ "   aprobadoPor,  " //8
			+ "   idContrato )   " // 9
 			+ " VALUES ( NOW(),  " //1
			+ "   (SELECT fechaInicio_actividad FROM contratos WHERE id = ?),  " //2
			+ "   DATE_ADD( ? , INTERVAL -1 DAY),  " //3
			+ "   (SELECT s.sociedad FROM contratos ct INNER JOIN sociedad s ON (ct.idSociedad = s.idSociedad)  WHERE id = ?) ,  " //4
			+ "   (SELECT sociedad FROM sociedad WHERE idSociedad = ? )  ,  " //5
			+ "   (SELECT fechaInicio_actividad FROM contratos WHERE id = ?) ,  " //6
			+ "   NULL,  " //7
			+ "   ?,   " //8
			+ "   ? );   "; //9
			
			
			sql += " "
			+ " INSERT INTO contratos ( codigo_trabajador,id_peticion,codigo_peticion,idSociedad,fechaInicio_actividad,fechaContrato_emitido,FechaTerminoContrato,EstadoContrato,cargo,posicion,tipoContrato,articuloTerminoContrato,incisoTerminoContrato,letraTerminoContrato,descripcion,fechaPago,lugarPago,horaPago,idTurno,colacionFija,valorFijo,horasSemanales,sueldoBase,movilizacionFija,tipoTrabajador,partTime,supervisor,maquinista,jornada,aviso,paraFiniquitar,fechaNotificacion,horaPago2,finiquitado,idHuertoContrato,idCECOContrato,fIngresoCompContrato,idFaenaContrato,tipoMoneda ) "
			+ " SELECT codigo_trabajador,id_peticion,codigo_peticion,?,fechaInicio_actividad,fechaContrato_emitido,FechaTerminoContrato,EstadoContrato,cargo,posicion,tipoContrato,articuloTerminoContrato,incisoTerminoContrato,letraTerminoContrato,descripcion,fechaPago,lugarPago,horaPago,idTurno,colacionFija,valorFijo,horasSemanales,sueldoBase,movilizacionFija,tipoTrabajador,partTime,supervisor,maquinista,jornada,aviso,paraFiniquitar,fechaNotificacion,horaPago2,finiquitado,idHuertoContrato,idCECOContrato,fIngresoCompContrato,idFaenaContrato,tipoMoneda "
			+ " FROM contratos "
			+ " WHERE id = ?; ";
			
			sql += " UPDATE contratos SET EstadoContrato = '0', descripcion = 'CAMBIO DE CONTRATO GENERADO POR SISTEMA', finiquitado = '1', FechaTerminoContrato = ?  WHERE id = ? ;  ";
			
			sql += " UPDATE sw_vacaciones SET sociedad = ( SELECT s.sociedad FROM contratos ct INNER JOIN sociedad s ON (ct.idSociedad = s.idSociedad) WHERE id = ? )  WHERE idContrato = ?; ";

			
			ps = db.conn.prepareStatement(sql);
			ps.setString(i++, idContrato);
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaInicioCambio));
			ps.setString(i++, idContrato);
			ps.setString(i++, nuevaSociedad);
			ps.setString(i++, idContrato);
			ps.setString(i++, aprobadoPor);
			ps.setString(i++, idContrato);
			
			//SEGUNDA QUERY
			ps.setString(i++, nuevaSociedad);
			ps.setString(i++, idContrato);
			
			//TERCERA QUERY
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(fechaInicioCambio));
			ps.setString(i++, idContrato);
			
			//CUARTA QUERY
			ps.setString(i++, idContrato);
			ps.setString(i++, idContrato);
		
			ps.execute();


		} catch (Exception e) {

			System.out.println("Error getCambioEmpresa:" + e.getMessage());
			LOG.info("Error getCambioEmpresa:" + e.getMessage());

		} finally {
			db.conn.close();
		}
		
		return "";
		
	}
	
	

	// Actualizar Contrato
	public static boolean updateContrato(contrato contrato) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try {

			sql = "" + " UPDATE contratos " + " SET " + " codigo_trabajador = ?, " + " id_peticion = ?, "
					+ " codigo_peticion = ?, " + " idSociedad = ?, " + " fechaInicio_actividad = ?, "
					+ " fechaContrato_emitido = ?, " + " FechaTerminoContrato = ?, " + " EstadoContrato = ?, "
					+ " cargo = ?, " + " posicion = ?, " + " tipoContrato = ?, " + " articuloTerminoContrato = ?, "
					+ " incisoTerminoContrato = ?, " + " letraTerminoContrato = ?, " + " fechaPago = ?, "
					+ " lugarPago = ?, " + " horaPago = ?, " + " idTurno = ?, " + " colacionFija = ?,"
					+ " valorFijo = ?," + " horasSemanales = ?," + " sueldoBase = ?, " + " movilizacionFija = ?, "
					+ " tipoTrabajador = ?, " + " partTime = ?, " + " supervisor = ?, " + " maquinista = ?, "
					+ " jornada = ?, idHuertoContrato = ?, idCECOContrato = ?, fIngresoCompContrato = ?, idFaenaContrato = ?, tipoMoneda = ? " + " WHERE id = ?";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, contrato.getCodigo_trabajador());
			ps.setInt(i++, contrato.getId_peticion());
			ps.setInt(i++, contrato.getCodigo_peticion());
			ps.setInt(i++, contrato.getId_sociedad());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFecha_inicio_actividad()));
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFecha_contrato_emitido()));
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFecha_termino_actividad()));
			ps.setInt(i++, contrato.getEstado_contrato());
			ps.setInt(i++, contrato.getCargo());
			ps.setInt(i++, contrato.getPosicion());
			ps.setInt(i++, contrato.getTipoContrato());
			ps.setInt(i++, contrato.getArticuloTerminoContrato());
			ps.setInt(i++, contrato.getIncisoTerminoContrato());
			ps.setInt(i++, contrato.getLetraTerminoContrato());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFechaPago()));
			ps.setString(i++, contrato.getLugarPago());
			ps.setString(i++, contrato.getHoraPago());
			ps.setInt(i++, contrato.getIdTurno());
			ps.setInt(i++, contrato.getColacionFija());
			ps.setInt(i++, contrato.getValorFijo());
			ps.setInt(i++, contrato.getHorasSemanales());
			ps.setDouble(i++, contrato.getSueldoBase());
			ps.setDouble(i++, contrato.getMovilizacionFija());
			ps.setInt(i++, contrato.getTipoTrabajador());
			ps.setInt(i++, contrato.getPartTime());
			ps.setInt(i++, contrato.getSupervisor());
			ps.setInt(i++, contrato.getMaquinista());
			ps.setInt(i++, contrato.getJornada());
			ps.setString(i++, contrato.getIdHuertoContrato());
			ps.setString(i++, contrato.getIdCECOContrato());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getfIngresoCompContrato()));
			ps.setInt(i++, contrato.getIdFaenaContrato());
			ps.setInt(i++, contrato.getTipoMoneda());
			
			ps.setInt(i++, contrato.getId());

			ps.execute();

			return true;

		} catch (SQLException e) {
			LOG.info("Error updateContrato:" + e.getMessage());
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			LOG.info("Error updateContrato:" + e.getMessage());
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	// Actualizar Contrato (Mejorado con PreparedStament)
	public static boolean updateContratoTrabajador(contrato contrato) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try {

			sql = "" + " UPDATE contratos " + " SET " + " codigo_trabajador = ?, " + " id_peticion = ?, "
					+ " codigo_peticion = ?, " + " idSociedad = ?, " + " fechaInicio_actividad = ?, "
					+ " fechaContrato_emitido = ?, " + " FechaTerminoContrato = ?, " + " EstadoContrato = ?, "
					+ " cargo = ?, " + " posicion = ?, " + " tipoContrato = ?, " + " articuloTerminoContrato = ?, "
					+ " incisoTerminoContrato = ?, " + " letraTerminoContrato = ?, " + " fechaPago = ?, "
					+ " lugarPago = ?, " + " horaPago = ?, " + " idTurno = ?, " + " colacionFija = ?, "
					+ " valorFijo = ?," + " horasSemanales = ?," + " sueldoBase = ?, " + " movilizacionFija = ?, "
					+ " tipoTrabajador = ?, " + " partTime = ?, " + " supervisor = ?, " + " maquinista = ?, "
					+ " jornada = ?, " + " paraFiniquitar = ?, " + " finiquitado = ?, " + " fechaNotificacion = ?, "
					+ " horaPago2 = ?, idHuertoContrato = ?, idCECOContrato = ?, fIngresoCompContrato = ?, idFaenaContrato = ?, tipoMoneda = ?, "
					+ " descripcion = ? "

					+ " WHERE id = ?";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, contrato.getCodigo_trabajador());
			ps.setInt(i++, contrato.getId_peticion());
			ps.setInt(i++, contrato.getCodigo_peticion());
			ps.setInt(i++, contrato.getId_sociedad());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFecha_inicio_actividad()));
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFecha_contrato_emitido()));
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFecha_termino_actividad()));
			ps.setInt(i++, contrato.getFiniquitado() == 1 ? 0 : contrato.getEstado_contrato());
			ps.setInt(i++, contrato.getCargo());
			ps.setInt(i++, contrato.getPosicion());
			ps.setInt(i++, contrato.getTipoContrato());
			ps.setInt(i++, contrato.getArticuloTerminoContrato());
			ps.setInt(i++, contrato.getIncisoTerminoContrato());
			ps.setInt(i++, contrato.getLetraTerminoContrato());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFechaPago()));
			ps.setString(i++, contrato.getLugarPago());
			ps.setString(i++, contrato.getHoraPago());
			ps.setInt(i++, contrato.getIdTurno());
			ps.setInt(i++, contrato.getColacionFija());
			ps.setInt(i++, contrato.getValorFijo());
			ps.setInt(i++, contrato.getHorasSemanales());
			ps.setDouble(i++, contrato.getSueldoBase());
			ps.setDouble(i++, contrato.getMovilizacionFija());
			ps.setInt(i++, contrato.getTipoTrabajador());
			ps.setInt(i++, contrato.getPartTime());
			ps.setInt(i++, contrato.getSupervisor());
			ps.setInt(i++, contrato.getMaquinista());
			ps.setInt(i++, contrato.getJornada());
			ps.setInt(i++, contrato.getParaFiniquitar());
			ps.setInt(i++, contrato.getFiniquitado());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getFechaNotificacion()));
			ps.setString(i++, contrato.getHoraPago2());
			ps.setString(i++, contrato.getIdHuertoContrato());
			ps.setString(i++, contrato.getIdCECOContrato());
			ps.setString(i++, convertStringToYYYYMMDD(contrato.getfIngresoCompContrato()));
			ps.setInt(i++, contrato.getIdFaenaContrato());
			ps.setInt(i++, contrato.getTipoMoneda());
			ps.setString(i++, contrato.getDescripcion());
			
			ps.setInt(i++, contrato.getId());

			ps.execute();

			return true;

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			LOG.info("Error updateContratoTrabajador:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			LOG.info("Error updateContratoTrabajador:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	
	
	// Actualizar Contrato (Mejorado con PreparedStament)
		public static boolean activarDesactivarContrato(contrato contrato) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			int i = 1;

			try {

				sql = "" + " UPDATE contratos " + " SET  EstadoContrato = ? "
						+ " WHERE id = ?";

				ps = db.conn.prepareStatement(sql);

				ps.setInt(i++, contrato.getEstado_contrato());
				ps.setInt(i++, contrato.getId());

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
	
	
	
	
	
	// Obtener Contrato por Id

	// Obtener Todos los contratos

	// Obtener Contratos por Id Trabajador

	// Obtener Todos los Contratos con filtros
	public static ArrayList<contrato> getAllContratoWithFilter(ArrayList<filterSql> filter) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<contrato> lista = new ArrayList<contrato>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM contratos ";

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
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >= '"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						} else if (GeneralUtility.isNumeric(row.getValue())) {
							sql += andSql + row.getCampo() + " like '" + row.getValue() + "'";
						} else
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						andSql = " and ";

					} // Fin While

				}

			} // Fin if (filter size)

			sql += " order by fechaInicio_actividad desc ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				contrato ct = new contrato();

				ct = setObjectContrato(rs);

				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (Exception e) {
			LOG.info("getAllContratoWithFilter: " + e.getMessage());

		} finally {
			db.close();
		}

		return lista;
	}

	// Obtener Todos los Contratos con filtros
	public static ArrayList<contrato> getAllContratoTrabajadorWithFilter(ArrayList<filterSql> filter) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<contrato> lista = new ArrayList<contrato>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM contratos ct INNER JOIN trabajadores tr ON (ct.codigo_trabajador = tr.codigo) ";

			if (filter.size() > 0) {

				String andSql = "";
				andSql += " WHERE ";

				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {

					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().endsWith("_date")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " ='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}
						else if (GeneralUtility.isNumeric(row.getValue())) {
							sql += andSql + row.getCampo() + " like '" + row.getValue() + "'";
						}
						else if (GeneralUtility.isArray(row.getValue())){
							sql += andSql + row.getCampo() + " in ( " + row.getValue() + " )";
						}
						else if (row.getCampo().equals("_periodo")) {
							
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							String _FECHA = sqlDate.format(formatter.parse(row.getValue()));
							
							sql += andSql +	" ct.fechaInicio_actividad <= "+_FECHA+" " 
										  +	" AND (ct.FechaTerminoContrato >= "+_FECHA+" " 
										  +	" OR ct.FechaTerminoContrato IS NULL) ";
						}else if(row.getCampo().equals("_historial")){
							sql = sql.replace("* FROM contratos", "* FROM contratos_per");
						}				
						else
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						andSql = " and ";

					} // Fin While

				}

			} // Fin if (filter size)

			sql += " order by fechaInicio_actividad desc ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				contrato ct = new contrato();

				ct = setObjectContrato(rs);

				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (Exception e) {
			LOG.info("getAllContratoWithFilter: " + e.getMessage());

		} finally {
			db.close();
		}

		return lista;
	}

	
	// Obtener Todos los Contratos con filtros
	public static ArrayList<ContratoTrabajador> getAllContratoTrabajadorWithFilter2(ArrayList<filterSql> filter) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<ContratoTrabajador> lista = new ArrayList<ContratoTrabajador>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM contratos ct INNER JOIN trabajadores tr ON (ct.codigo_trabajador = tr.codigo) ";

			if (filter.size() > 0) {

				String andSql = "";
				andSql += " WHERE ";

				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {

					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().endsWith("_date")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " ='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}
						else if (GeneralUtility.isNumeric(row.getValue())) {
							sql += andSql + row.getCampo() + " like '" + row.getValue() + "'";
						} 
						else if (GeneralUtility.isArray(row.getValue())){
							sql += andSql + row.getCampo() + " in ( " + row.getValue() + " )";
						}
						else
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						andSql = " and ";

					} // Fin While

				}

			} // Fin if (filter size)

			sql += " order by fechaInicio_actividad desc ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				ContratoTrabajador ct = new ContratoTrabajador();

				ct = setObjectContratoTrabajador(rs);

				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (Exception e) {
			LOG.info("getAllContratoWithFilter: " + e.getMessage());

		} finally {
			db.close();
		}

		return lista;
	}
	
	
	
	
	
	// Obtener todos los Contratos totales
	public static int getAllContrato(ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM contrato ";

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

	// Obtener Todos los Contratos By IdTrabajador con filtros
	public static ArrayList<Contratos> getContratoByIdTrabajador(String id) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<Contratos> lista = new ArrayList<Contratos>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM contratos WHERE codigo_trabajador = " + id + " ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Contratos ct = new Contratos();
				ct = setObjectContratos(rs);
				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (Exception e) {

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getContratoByIdTrabajador: " + e.getMessage());

		} finally {
			db.close();
		}

		return lista;
	}
	
	
	// Obtener Todos los Contratos By IdTrabajador con filtros
		public static ArrayList<Contratos> getContratoWithFilter(ArrayList<filterSql> filter) throws Exception {

			Statement stmt = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			ArrayList<Contratos> lista = new ArrayList<Contratos>();

			try {

				stmt = db.conn.createStatement();
				sql = " SELECT * FROM contratos ";
				
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

							else if (row.getCampo().endsWith("fecha_pago")) {

								SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
								sql += andSql + row.getCampo() + " ='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							}
							else if (row.getCampo().endsWith("_date")) {

								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
								sql += andSql + row.getCampo().replace("_date", "") + " ='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							}
							else if (GeneralUtility.isNumeric(row.getValue())){
								sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
							}
							else if (GeneralUtility.isArray(row.getValue())){
								sql += andSql + row.getCampo() + " in ( " + row.getValue() + " )";
							}
							else if( "_excluirNomina".equals(row.getCampo()) ){
								//SUBQUERY PARA EXCLUIR a los Trabajadores de Nomina
								//sql += andSql + " tr.codigo NOT IN ( SELECT l.cod_trabajador FROM sw_liquidacion l INNER JOIN sw_nomina n ON (l.id_nomina = n.id_nomina) WHERE l.id_nomina is not null AND n.estado = 1 AND n.periodo = "+ TimeUtility.convertStringToYYYYMM(row.getValue()) +" ) ";
								sql += andSql + " 1 = 1 ";
							}
							else{
								sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
							}

							andSql = " and ";
						}
					} // Fin While

				}

				ResultSet rs = stmt.executeQuery(sql);

				while (rs.next()) {
					Contratos ct = new Contratos();
					ct = setObjectContratos(rs);
					lista.add(ct);

				}
				rs.close();
				stmt.close();
				db.conn.close();
				// Fin Try
			} catch (Exception e) {

				System.out.println("Error: " + e.getMessage());
				System.out.println("sql: " + sql);
				throw new Exception("getContratoWithFilter: " + e.getMessage());

			} finally {
				db.close();
			}

			return lista;
		}
	
	

	// Obtener Todos los Contratos By IdTrabajador para Cambio Empresa
	public static ArrayList<contrato> getContratoByIdTrabajadorToCambioEmpresa(String id, ArrayList<filterSql> filter,
			String order, int start, int length) throws Exception {

		PreparedStatement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<contrato> lista = new ArrayList<contrato>();

		try {

			sql = " SELECT * FROM contratos WHERE codigo_trabajador = " + id + " ";
			sql += " ORDER BY fechaInicio_Actividad DESC";
			sql += " LIMIT " + 0 + "," + 1 + " ";
			stmt = db.conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				contrato ct = new contrato();

				ct = setObjectContrato(rs);

				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (SQLException e) {
			System.out.println("Error getContratoByIdTrabajadorToCambioEmpresa: " + e.getMessage());

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getContratoByIdTrabajadorToCambioEmpresa: " + e.getMessage());
		} finally {
			db.close();
		}

		return lista;
	}

	// Obtener Todos los Contratos By IdTrabajador para Separacion
	public static ArrayList<contrato> getContratoByIdTrabajadorToSeparacion(String id, ArrayList<filterSql> filter,
			String order, int start, int length) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<contrato> lista = new ArrayList<contrato>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM contratos WHERE codigo_trabajador = " + id + " " + " " + " ";

			sql += " ORDER BY fechaInicio_Actividad DESC";

			sql += " LIMIT " + 0 + "," + 1 + " ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				contrato ct = new contrato();

				ct = setObjectContrato(rs);

				lista.add(ct);

			}
			rs.close();
			stmt.close();
			db.conn.close();
			// Fin Try
		} catch (Exception e) {

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getContratoByIdTrabajadorSeparacion: " + e.getMessage());

		} finally {
			db.close();
		}

		return lista;
	}

	// Obtener los contratos totales By trabajador con Filtros
	public static int getContratoByIdTrabajador(String id, ArrayList<filterSql> filter) throws Exception {

		int total = 0;
		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {

			stmt = db.conn.createStatement();

			sql = "SELECT count(1) FROM contratos ";

			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE codigo_trabajador = " + id + " ";
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
			throw new Exception("getContratoByIdTrabajador: " + e.getMessage());
		} finally {
			db.close();
		}

		return total;
	}

	// Obtener Contrato por Id
	public static contrato getContratoById(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		contrato ct = new contrato();

		try {
			sql = "SELECT * FROM contratos WHERE id = '" + id + "'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				ct = setObjectContrato(rs);

			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return ct;
	}

	// Obtener Ultimo contrato activo por Id del Trabajador
	public static contrato getUltimoContratoActivoByIdTrabajador(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		contrato ct = new contrato();

		try {

			sql = " SELECT * FROM contratos WHERE codigo_trabajador = " + id + " AND EstadoContrato = 1 ";
			sql += " ORDER BY fechaInicio_Actividad DESC";
			sql += " LIMIT " + 0 + "," + 1 + " ";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				ct = setObjectContrato(rs);

			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return ct;
	}
	
	// Obtener Ultimo contrato activo por Id del Trabajador
		public static contrato getUltimoContrato(ArrayList<filterSql> filter) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			contrato ct = new contrato();
			
			sql = " SELECT * FROM contratos ";

			try {
				
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
								sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >= '"
										+ sqlDate.format(formatter.parse(row.getValue())) + "'";
							} else if (GeneralUtility.isNumeric(row.getValue())) {
								sql += andSql + row.getCampo() + " like '" + row.getValue() + "'";
							} else
								sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
							andSql = " and ";

						} // Fin While

					}

				} // Fin if (filter size)

				sql += " ORDER BY fechaInicio_Actividad DESC";
				sql += " LIMIT " + 0 + "," + 1 + " ";

				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {

					ct = setObjectContrato(rs);

				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return ct;
		}

	// Obtener todos los contratos Activos del trabajor
	public static ArrayList<contrato> getAllContratoActivoByIdTrabajador(String id) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<contrato> lista = new ArrayList<contrato>();

		try {

			sql = " SELECT * FROM trabajadores tr " + " INNER JOIN contratos c ON (tr.codigo = c.codigo_trabajador) "
					+ " WHERE EstadoContrato = 1 AND tr.id = '" + id + "' ";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				contrato ct = new contrato();

				ct = setObjectContrato(rs);

				lista.add(ct);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	// Obtener todas las sociedades dado el codigo de trabajador
	public static ArrayList<Integer> getAllSociedadesByCodigoTrabajador(String codigo_trabajador) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<Integer> idSociedadList = new ArrayList<>();

		try {

			sql = " SELECT DISTINCT idSociedad FROM contratos WHERE codigo_trabajador = " + codigo_trabajador + " ";
		    //sql += " ORDER BY fechaInicio_Actividad DESC ";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				idSociedadList.add(rs.getInt("idSociedad"));

			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return idSociedadList;
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

	public static contrato setObjectContrato(ResultSet rs) throws SQLException {

		contrato ct = new contrato();

		ct.setId(rs.getInt("id"));
		ct.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
		ct.setId_peticion(rs.getInt("id_peticion"));
		ct.setCodigo_peticion(rs.getInt("codigo_peticion"));
		ct.setId_sociedad(rs.getInt("idSociedad"));
		ct.setFecha_inicio_actividad(rs.getString("fechaInicio_Actividad"));
		ct.setFecha_contrato_emitido(rs.getString("fechaContrato_emitido"));
		ct.setFecha_termino_actividad(rs.getString("FechaTerminoContrato"));
		ct.setEstado_contrato(rs.getInt("EstadoContrato"));
		ct.setCargo(rs.getInt("cargo"));
		ct.setPosicion(rs.getInt("posicion"));
		ct.setTipoContrato(rs.getInt("tipoContrato"));
		ct.setArticuloTerminoContrato(rs.getInt("articuloTerminoContrato"));
		ct.setIncisoTerminoContrato(rs.getInt("incisoTerminoContrato"));
		ct.setLetraTerminoContrato(rs.getInt("letraTerminoContrato"));
		ct.setFechaPago(rs.getString("fechaPago"));
		ct.setLugarPago(rs.getString("lugarPago"));
		ct.setHoraPago(rs.getString("horaPago"));
		ct.setIdTurno(rs.getInt("idTurno"));
		ct.setColacionFija(rs.getInt("colacionFija"));
		ct.setValorFijo(rs.getInt("valorFijo"));
		ct.setHorasSemanales(rs.getInt("horasSemanales"));
		ct.setSueldoBase(rs.getDouble("sueldoBase"));
		ct.setMovilizacionFija(rs.getDouble("movilizacionFija"));
		ct.setTipoTrabajador(rs.getInt("tipoTrabajador"));
		ct.setPartTime(rs.getInt("partTime"));
		ct.setSupervisor(rs.getInt("supervisor"));
		ct.setMaquinista(rs.getInt("maquinista"));
		ct.setJornada(rs.getInt("jornada"));
		ct.setParaFiniquitar(rs.getInt("paraFiniquitar"));
		ct.setFechaNotificacion(rs.getString("fechaNotificacion"));
		ct.setHoraPago2(rs.getString("horaPago2"));
		ct.setFiniquitado(rs.getInt("finiquitado"));
		ct.setDescripcion(rs.getString("descripcion"));
		ct.setIdHuertoContrato(rs.getString("idHuertoContrato"));
		ct.setIdCECOContrato(rs.getString("idCECOContrato"));
		ct.setfIngresoCompContrato(rs.getString("fIngresoCompContrato"));
		ct.setIdFaenaContrato(rs.getInt("idFaenaContrato"));
		ct.setTipoMoneda(rs.getInt("tipoMoneda"));
		
		try{ ct.setPeriodo(rs.getString("periodo")); }catch(Exception e){ System.out.println("NO ES HISTORIAL"); }
		

		return ct;

	}

	public static Contratos setObjectContratos(ResultSet rs) throws SQLException {

		Contratos ct = new Contratos();

		ct.setId(rs.getInt("id"));
		ct.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
		ct.setId_peticion(rs.getInt("id_peticion"));
		ct.setCodigo_peticion(rs.getInt("codigo_peticion"));
		ct.setIdSociedad(rs.getInt("idSociedad"));
		ct.setFechaInicio_actividad(rs.getString("fechaInicio_Actividad"));
		ct.setFechaContrato_emitido(rs.getString("fechaContrato_emitido"));
		ct.setFechaTerminoContrato(rs.getString("FechaTerminoContrato"));
		ct.setEstadoContrato(rs.getInt("EstadoContrato"));
		ct.setCargo(rs.getInt("cargo"));
		ct.setPosicion(rs.getInt("posicion"));
		ct.setTipoContrato(rs.getInt("tipoContrato"));
		ct.setArticuloTerminoContrato(rs.getInt("articuloTerminoContrato"));
		ct.setIncisoTerminoContrato(rs.getInt("incisoTerminoContrato"));
		ct.setLetraTerminoContrato(rs.getInt("letraTerminoContrato"));
		ct.setFechaPago(rs.getString("fechaPago"));
		ct.setLugarPago(rs.getString("lugarPago"));
		ct.setHoraPago(rs.getString("horaPago"));
		ct.setIdTurno(rs.getInt("idTurno"));
		ct.setColacionFija(rs.getString("colacionFija"));
		ct.setValorFijo(rs.getInt("valorFijo"));
		ct.setHorasSemanales(rs.getInt("horasSemanales"));
		ct.setSueldoBase(rs.getDouble("sueldoBase"));
		ct.setMovilizacionFija(rs.getDouble("movilizacionFija"));
		ct.setTipoTrabajador(rs.getInt("tipoTrabajador"));
		ct.setPartTime(rs.getInt("partTime"));
		ct.setSupervisor(rs.getInt("supervisor"));
		ct.setMaquinista(rs.getInt("maquinista"));
		ct.setJornada(rs.getInt("jornada"));
		ct.setParaFiniquitar(rs.getInt("paraFiniquitar"));
		ct.setFiniquitado(rs.getInt("finiquitado"));
		ct.setHoraPago2(rs.getString("horaPago2"));
		ct.setFechaNotificacion(rs.getString("fechaNotificacion"));
		ct.setDescripcion(rs.getString("descripcion"));
		ct.setIdHuertoContrato(rs.getString("idHuertoContrato"));
		ct.setIdCECOContrato(rs.getString("idCECOContrato"));
		ct.setfIngresoCompContrato(rs.getString("fIngresoCompContrato"));
		ct.setIdFaenaContrato(rs.getInt("idFaenaContrato"));
		ct.setTipoTrabajador(rs.getInt("tipoMoneda"));

		return ct;

	}
	
	
	public static ContratoTrabajador setObjectContratoTrabajador(ResultSet rs) throws SQLException {

		ContratoTrabajador ct = new ContratoTrabajador();

		ct.setId(rs.getInt("id"));
		ct.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
		ct.setId_peticion(rs.getInt("id_peticion"));
		ct.setCodigo_peticion(rs.getInt("codigo_peticion"));
		ct.setId_sociedad(rs.getInt("idSociedad"));
		ct.setFecha_inicio_actividad(rs.getString("fechaInicio_Actividad"));
		ct.setFecha_contrato_emitido(rs.getString("fechaContrato_emitido"));
		ct.setFecha_termino_actividad(rs.getString("FechaTerminoContrato"));
		ct.setEstado_contrato(rs.getInt("EstadoContrato"));
		ct.setCargo(rs.getInt("cargo"));
		ct.setPosicion(rs.getInt("posicion"));
		ct.setTipoContrato(rs.getInt("tipoContrato"));
		ct.setArticuloTerminoContrato(rs.getInt("articuloTerminoContrato"));
		ct.setIncisoTerminoContrato(rs.getInt("incisoTerminoContrato"));
		ct.setLetraTerminoContrato(rs.getInt("letraTerminoContrato"));
		ct.setFechaPago(rs.getString("fechaPago"));
		ct.setLugarPago(rs.getString("lugarPago"));
		ct.setHoraPago(rs.getString("horaPago"));
		ct.setIdTurno(rs.getInt("idTurno"));
		ct.setColacionFija(rs.getInt("colacionFija"));
		ct.setValorFijo(rs.getInt("valorFijo"));
		ct.setHorasSemanales(rs.getInt("horasSemanales"));
		ct.setSueldoBase(rs.getDouble("sueldoBase"));
		ct.setMovilizacionFija(rs.getDouble("movilizacionFija"));
		ct.setTipoTrabajador(rs.getInt("tipoTrabajador"));
		ct.setPartTime(rs.getInt("partTime"));
		ct.setSupervisor(rs.getInt("supervisor"));
		ct.setMaquinista(rs.getInt("maquinista"));
		ct.setJornada(rs.getInt("jornada"));
		ct.setParaFiniquitar(rs.getInt("paraFiniquitar"));
		ct.setFechaNotificacion(rs.getString("fechaNotificacion"));
		ct.setHoraPago2(rs.getString("horaPago2"));
		ct.setFiniquitado(rs.getInt("finiquitado"));
		ct.setDescripcion(rs.getString("descripcion"));
		ct.setApellidoPaterno(rs.getString("apellidoPaterno"));
		ct.setApellidoMaterno(rs.getString("apellidoMaterno"));
		ct.setNombre(rs.getString("nombre"));
		ct.setRut(rs.getString("rut"));
		ct.setIdHuertoContrato(rs.getString("idHuertoContrato"));
		ct.setIdCECOContrato(rs.getString("idCECOContrato"));
		ct.setfIngresoCompContrato(rs.getString("fIngresoCompContrato"));
		ct.setIdFaenaContrato(rs.getInt("idFaenaContrato"));
		ct.setTipoMoneda(rs.getInt("tipoMoneda"));
		ct.setEstadoProceso(rs.getString("estadoProceso"));

		return ct;

	}
	
	
	
	

	public static Blob getContrato() throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try {

			ps = db.conn.createStatement();
			sql = "SELECT file FROM sw_template WHERE documento = 'Contrato' ";
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Blob cartaTermino = rs.getBlob(1);
				return cartaTermino;
			}
			rs.close();
			ps.close();
			db.conn.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getTrabajadorAll: " + e.getMessage());
		} finally {
			db.close();
		}

		return null;
	}

}
