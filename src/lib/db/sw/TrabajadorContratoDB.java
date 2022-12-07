package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.ContratoTrabajador;
import lib.classSW.Contratos;
import lib.classSW.TrabajadorContrato;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class TrabajadorContratoDB {

	// Obtener Todos los Trabajadores con filtros
	public static ArrayList<TrabajadorContrato> getTrabajadorWithContratosByCodigoTrabajador(
			ArrayList<filterSql> filter) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<TrabajadorContrato> lista = new ArrayList<TrabajadorContrato>();

		try {

			// Crear sentencia en Sql
			sql = " SELECT * FROM trabajadores tr INNER JOIN contratos ct ON ( tr.codigo = ct.codigo_trabajador ) ";
			ps = db.conn.prepareStatement(sql);

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

						else

							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";

						andSql = " and ";
					}
				} // Fin While

			}

			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				TrabajadorContrato trabajadorContrato = TrabajadorContratoDB.setObjectTrabajadores(rs);

				ArrayList<Contratos> listaContratos = new ArrayList<Contratos>();
				Contratos ct = setObjectContratos(rs);
				listaContratos.add(ct);

				trabajadorContrato.setContratos(listaContratos);

				lista.add(trabajadorContrato);

			}
			rs.close();
			ps.close();
			db.conn.close();
			// Fin Try
		} catch (Exception e) {

			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getTrabajadoresWithContratos: " + e.getMessage());

		} finally {
			db.close();
		}

		// Retornar Lista de Trabajadores
		return lista;

	}// Fin getAllTrabajadors

	// Formatear el rut
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

	// Obtener Todos los Trabajador con sus ultimos contratos
	public static ArrayList<TrabajadorContrato> getAllTrabajadorWithLastContrato(ArrayList<filterSql> filter)
			throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		String sql_orderBy = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<TrabajadorContrato> lista = new ArrayList<TrabajadorContrato>();

		try {
			sql = ""+ " SELECT * FROM "
					+ " (SELECT tr.*,ct.id as 'idContrato', ct.EstadoContrato, ct.fechaInicio_actividad, ct.FechaTerminoContrato, ct.idSociedad, sc.denominacionSociedad, ct.sueldoBase, ct.horasSemanales, ct.tipoContrato, ct.idCECOContrato, ct.idHuertoContrato FROM trabajadores tr "
					+ " INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador)  "
					+ " LEFT JOIN sociedad sc ON (ct.idSociedad = sc.idSociedad) "
					//Esta Linea Solo Obtener el Ultimo Contrato si tiene varios
					+ " LEFT JOIN contratos ct2 ON (tr.codigo = ct2.codigo_trabajador AND  ct.fechaInicio_actividad  < ct2.fechaInicio_actividad ) WHERE ct2.id IS NULL "
					+ " ) as T1 " 
					
//					+ " LEFT JOIN "
//					+ " (SELECT doc.codTrabajador, doc.impreso FROM sw_r_trabajadorDocumentos doc "
//					+ " INNER JOIN sw_template temp ON (doc.idTemplate = temp.idTemplate) WHERE temp.tipoDocumento = 1) as T2 "
//					+ " ON T1.codigo = T2.codTrabajador "
					+ " WHERE tipoTrabajador <> 4 ";

			// Si contiene datos asignarlo al WHERE
			if (filter.size() > 0) {
				String andSql = "";
				andSql += " AND ";
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
						} else if (row.getValue().split(",").length >= 2) {
							sql += andSql + row.getCampo() + " in ( " + row.getValue() + ") ";
						} else if (GeneralUtility.isNumeric(row.getValue().replaceAll("\\\"", ""))){
							sql += andSql + row.getCampo() + " = '" + row.getValue().replaceAll("\\\"", "") + "'";
						}
						else if( "_orderBy".equals(row.getCampo())){
							sql_orderBy = " ORDER BY " + row.getValue() + " ASC " ;
						}
						else {
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						}

						andSql = " and ";
					}
				} // Fin While

			}

			sql += sql_orderBy;
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				TrabajadorContrato trabajadorContrato = TrabajadorContratoDB.setObjectTrabajadores(rs);

				ArrayList<Contratos> listaContratos = new ArrayList<Contratos>();
				Contratos ct = setObjectContratos(rs);
				listaContratos.add(ct);

				trabajadorContrato.setContratos(listaContratos);

				lista.add(trabajadorContrato);

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	
	// Obtener Todos los Trabajadores con filtros
		public static ArrayList<ContratoTrabajador> getAllTrabajadorContratoWithFilter(ArrayList<filterSql> filter) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			ArrayList<ContratoTrabajador> lista = new ArrayList<ContratoTrabajador>();

			try {

				// (Es necesario el distinct?) fue puesto porque muestra duplicados de trabajadores activos al permitir
				// agregar trabajadores paraFiniquitar (creo :o)
				sql = " SELECT DISTINCT tr.*, ct.* FROM trabajadores tr INNER JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador)  ";
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
							else if( "_orderBy".equals(row.getCampo())){
								sql_orderBy = " ORDER BY " + row.getValue() + " ASC " ;
							}
							else{
								sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
							}
							andSql = " and ";
						}
					} // Fin While

				}

				sql += sql_orderBy;
				
				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {

					ContratoTrabajador ct = new ContratoTrabajador();

					ct = contratoDB.setObjectContratoTrabajador(rs);

					lista.add(ct);

				}
				
				//Retornar Lista de Trabajadores
				return lista;
				
				// Fin Try
			} catch (Exception e) {
				throw new Exception("getAllTrabajadoresWithFilter: " + e.getMessage());
			} finally {
				db.close();
			}

		}// Fin getAllTrabajadors
	

	public static Contratos setObjectContratos(ResultSet rs) throws SQLException {

		Contratos ct = new Contratos();

		ct.setId(rs.getInt("idContrato"));
		// ct.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
		// ct.setId_peticion(rs.getInt("id_peticion"));
		// ct.setCodigo_peticion(rs.getInt("codigo_peticion"));
		ct.setIdSociedad(rs.getInt("idSociedad"));
		ct.setFechaInicio_actividad(rs.getString("fechaInicio_actividad"));
		// ct.setFechaContrato_emitido(rs.getString("fechaContrato_emitido"));
		ct.setFechaTerminoContrato(rs.getString("fechaTerminoContrato"));
		ct.setEstadoContrato(rs.getInt("EstadoContrato"));
		ct.setCargo(rs.getInt("cargo"));
		// ct.setPosicion(rs.getInt("posicion"));
		 ct.setTipoContrato(rs.getInt("tipoContrato"));
		// ct.setArticuloTerminoContrato(rs.getInt("articuloTerminoContrato"));
		// ct.setIncisoTerminoContrato(rs.getInt("incisoTerminoContrato"));
		// ct.setLetraTerminoContrato(rs.getInt("letraTerminoContrato"));
		// ct.setFechaPago(rs.getString("fechaPago"));
		// ct.setLugarPago(rs.getString("lugarPago"));
		// ct.setHoraPago(rs.getString("horaPago"));
		// ct.setIdTurno(rs.getInt("idTurno"));
		// ct.setColacionFija(rs.getString("colacionFija"));
		// ct.setValorFijo(rs.getInt("valorFijo"));
		   ct.setHorasSemanales(rs.getInt("horasSemanales"));
		   ct.setSueldoBase(rs.getDouble("sueldoBase"));
		// ct.setMovilizacionFija(rs.getDouble("movilizacionFija"));
		 ct.setTipoTrabajador(rs.getInt("tipoTrabajador"));
		// ct.setPartTime(rs.getInt("partTime"));
		// ct.setSupervisor(rs.getInt("supervisor"));
		ct.setIdCECOContrato(rs.getString("idCECOContrato"));
		ct.setIdHuertoContrato(rs.getString("idHuertoContrato"));

		return ct;

	}

	public static TrabajadorContrato setObjectTrabajadores(ResultSet rs) throws SQLException {

		TrabajadorContrato tr = new TrabajadorContrato();

		tr.setId(rs.getInt("id"));
		tr.setCodigo(rs.getString("codigo"));
		tr.setRut(FormatearRUT(rs.getString("rut")));
		tr.setNombre(rs.getString("nombre"));
		tr.setRutTemporal(rs.getString("rutTemporal"));
		tr.setPasaporte(rs.getString("pasaporte"));
		tr.setfNacimiento(rs.getString("fNacimiento"));
		tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
		tr.setApellidoMaterno(rs.getString("apellidoMaterno"));
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
		tr.setPensionadosCotizantes(rs.getInt("pensionadosCotizantes"));
//		tr.setImpreso(rs.getInt("impreso"));
		tr.setDenominacionSociedad(rs.getString("denominacionSociedad"));
		tr.setIdAdicionalAFP(rs.getInt("idAdicionalAFP"));
		tr.setEstadoProceso(rs.getString("estadoProceso"));

		return tr;

	}

}
