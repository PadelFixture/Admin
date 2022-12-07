package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import lib.classSW.ContratosPer;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class ContratosPerDB {

	// Actualizar Contrato
	public static boolean updateContratosPer(ContratosPer contrato) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;

		try {

			int i = 1;

			sql = " SELECT id FROM contratos_per WHERE id = ? and periodo = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, contrato.getId());
			ps.setString(i++, contrato.getPeriodo());

			rs = ps.executeQuery();

			if (!rs.next()) {
				i = 1;
				rs.close();
				ps.close();

				sql = " INSERT INTO contratos_per (id, periodo) VALUES (?, ?) ";
				ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(i++, contrato.getId());
				ps.setString(i++, contrato.getPeriodo());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

			}

			i = 1;

			sql = "" + " UPDATE contratos_per " + " SET " + " codigo_trabajador = ?, " + " id_peticion = ?, "
					+ " codigo_peticion = ?, " + " idSociedad = ?, " + " fechaInicio_actividad = ?, "
					+ " fechaContrato_emitido = ?, " + " FechaTerminoContrato = ?, " + " EstadoContrato = ?, "
					+ " cargo = ?, " + " posicion = ?, " + " tipoContrato = ?, " + " articuloTerminoContrato = ?, "
					+ " incisoTerminoContrato = ?, " + " letraTerminoContrato = ?, " + " fechaPago = ?, "
					+ " lugarPago = ?, " + " horaPago = ?, " + " idTurno = ?, " + " colacionFija = ?,"
					+ " valorFijo = ?," + " horasSemanales = ?," + " sueldoBase = ?, " + " movilizacionFija = ?, "
					+ " tipoTrabajador = ?, " + " partTime = ?, " + " supervisor = ?, " + " maquinista = ?, "
					+ " jornada = ?, idHuertoContrato = ?, idCECOContrato = ?, fIngresoCompContrato = ?, idFaenaContrato = ? "
					+ " WHERE id = ? and periodo = ? ";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, contrato.getCodigo_trabajador());
			ps.setInt(i++, contrato.getId_peticion());
			ps.setInt(i++, contrato.getCodigo_peticion());
			ps.setInt(i++, contrato.getId_sociedad());
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(contrato.getFecha_inicio_actividad()));
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(contrato.getFecha_contrato_emitido()));
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(contrato.getFecha_termino_actividad()));
			ps.setInt(i++, contrato.getEstado_contrato());
			ps.setInt(i++, contrato.getCargo());
			ps.setInt(i++, contrato.getPosicion());
			ps.setInt(i++, contrato.getTipoContrato());
			ps.setInt(i++, contrato.getArticuloTerminoContrato());
			ps.setInt(i++, contrato.getIncisoTerminoContrato());
			ps.setInt(i++, contrato.getLetraTerminoContrato());
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(contrato.getFechaPago()));
			ps.setString(i++, contrato.getLugarPago());
			ps.setString(i++, contrato.getHoraPago());
			ps.setInt(i++, contrato.getIdTurno());
			ps.setString(i++, contrato.getColacionFija());
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
			ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(contrato.getfIngresoCompContrato()));
			ps.setInt(i++, contrato.getIdFaenaContrato());

			ps.setInt(i++, contrato.getId());
			ps.setString(i++, contrato.getPeriodo().trim());

			System.out.println(ps.toString());
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

	// Obtener Todos los Contratos By IdTrabajador con filtros
	public static ArrayList<ContratosPer> getContratosPerWithFilter(ArrayList<filterSql> filter) throws Exception {

		Statement stmt = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<ContratosPer> lista = new ArrayList<ContratosPer>();

		try {

			stmt = db.conn.createStatement();
			sql = " SELECT * FROM contratos_per ct INNER JOIN trabajadores tr ON (ct.codigo_trabajador = tr.codigo) ";

			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " WHERE ";
				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {
					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().endsWith("fecha_pago")) {

							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");
							sql += andSql + row.getCampo() + " ='" + sqlDate.format(formatter.parse(row.getValue()))
									+ "'";
						} else if (GeneralUtility.isNumeric(row.getValue())) {
							sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
						} else if (GeneralUtility.isArray(row.getValue())) {
							sql += andSql + row.getCampo() + " in ( " + row.getValue() + " )";
						} else {
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						}

						andSql = " and ";
					}
				} // Fin While

			}

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				ContratosPer ct = new ContratosPer();
				ct = setObjectContratosPer(rs);
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

	
	public static ContratosPer setObjectContratosPer(ResultSet rs) throws SQLException {

		ContratosPer ct = new ContratosPer();

		ct.setId(rs.getInt("id"));
		ct.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
		ct.setId_peticion(rs.getInt("id_peticion"));
		ct.setCodigo_peticion(rs.getInt("codigo_peticion"));
		ct.setIdSociedad(rs.getInt("idSociedad"));
		ct.setFechaInicio_actividad(rs.getString("fechaInicio_Actividad"));
		ct.setFecha_inicio_actividad(rs.getString("fechaInicio_Actividad"));
		ct.setFechaContrato_emitido(rs.getString("fechaContrato_emitido"));
		ct.setFechaTerminoContrato(rs.getString("FechaTerminoContrato"));
		ct.setFecha_termino_actividad(rs.getString("FechaTerminoContrato"));
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

		return ct;

	}
	
	
	
	
	
}
