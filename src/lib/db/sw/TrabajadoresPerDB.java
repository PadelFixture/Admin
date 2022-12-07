package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import lib.classSW.TrabajadoresPer;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

public class TrabajadoresPerDB {

	// Insert Trabajador
		public static boolean insertTrabajadorPer(TrabajadoresPer trabajadores) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			int i = 1;

			try {

				sql = "INSERT INTO trabajadores_per ( codigo, periodo, rut, nombre, " + "rutTemporal, pasaporte, fNacimiento, "
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
						+ "razonSocial, trabajadorJoven, idAdicionalAFP )"
						+ " VALUES (?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "					 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "					 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "					 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "					 ?, ?, ?, ?,"
								+ " ? , ?,  "
								+ " ? , ?, ? ) ";

				ps = db.conn.prepareStatement(sql);

				ps.setString(i++, trabajadores.getCodigo());
				ps.setString(i++, trabajadores.getPeriodo());
				ps.setString(i++, trabajadores.getRut());
				ps.setString(i++, trabajadores.getNombre().trim());
				ps.setString(i++, trabajadores.getRutTemporal());
				ps.setString(i++, trabajadores.getPasaporte());
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadores.getfNacimiento()));
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
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadores.getFechaIngresoCompania()));
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
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadores.getFechaCreacion()));
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
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadores.getFechaAfiliacionAFP()));
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

				ps.execute();

				return true;

			} catch (Exception ex) {
				System.out.println("Error: " + ex.getMessage());
			} finally {
				db.conn.close();
			}
			return false;
		}
		
		
		// Actualizar Trabajador
		public static boolean updateTrabajadorPer(TrabajadoresPer trabajadores) throws Exception {

			PreparedStatement ps = null;
			ConnectionDB db = new ConnectionDB();

			int i = 1;

			try {

				String sql = " UPDATE trabajadores_per SET rut = ?, nombre = ?, "
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
						+ " idFaena = ?, rolPrivado = ?, razonSocial = ?, trabajadorJoven = ?, idAdicionalAFP = ? " + " WHERE (id = ? ) and (periodo = ?) ";

				ps = db.conn.prepareStatement(sql);

				//ps.setString(i++, trabajadores.getCodigo());
				ps.setString(i++, trabajadores.getRut());
				ps.setString(i++, trabajadores.getNombre());
				ps.setString(i++, trabajadores.getRutTemporal());
				ps.setString(i++, trabajadores.getPasaporte());
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadores.getfNacimiento()));
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
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadores.getFechaIngresoCompania()));
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
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadores.getFechaCreacion()));
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
				ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(trabajadores.getFechaAfiliacionAFP()));
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
				ps.setString(i++, trabajadores.getPeriodo());

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
		
		// Obtener Trabajador por Id
		public static TrabajadoresPer getTrabajadorPerWithFilter(ArrayList<filterSql> filter) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			TrabajadoresPer tr = new TrabajadoresPer();

			try {
				
				sql = " SELECT * FROM trabajadores_per ";
				
				// Si contiene datos asignarlo al WHERE
				if (filter.size() > 0) {
					String andSql = "";
					andSql += " WHERE ";
					Iterator<filterSql> f = filter.iterator();

					while (f.hasNext()) {
						filterSql row = f.next();

						if (!row.getValue().equals("")) {

							if (row.getCampo().endsWith("_from")) {

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
								sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >='"
										+ sqlDate.format(formatter.parse(row.getValue())) + "' ";
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
					tr = setObjectTrabajadoresPer(rs);
				}
				
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			} finally {
				ps.close();
				db.close();
			}
			return tr;
		}
	
		
		public static TrabajadoresPer setObjectTrabajadoresPer(ResultSet rs) throws SQLException {

			TrabajadoresPer tr = new TrabajadoresPer();

			tr.setId(rs.getInt("id"));
			tr.setCodigo(rs.getString("codigo"));
			tr.setRut(GeneralUtility.FormatearRUT(rs.getString("rut")));
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
			tr.setPeriodo(rs.getString("periodo"));

			return tr;

		}
		
		
		
	
}
