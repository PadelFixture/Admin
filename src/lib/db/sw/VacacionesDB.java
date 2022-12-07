package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.classSW.DatosVacaciones;
import lib.classSW.Vacaciones;
import lib.classSW.VacacionesTrabajador;
import lib.db.ConnectionDB;
import lib.struc.filterSql;

public class VacacionesDB {
	
	private final static Logger LOG = LoggerFactory.getLogger(VacacionesDB.class);

	// Obtener Todos los Trabajadores con filtros
	public static ArrayList<VacacionesTrabajador> getAllVacacionesWithFilter(ArrayList<filterSql> filter) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			ArrayList<VacacionesTrabajador> lista = new ArrayList<VacacionesTrabajador>();

			try {

				// Crear sentencia en Sql
				sql = " SELECT * FROM sw_r_vacacionesTrabajador ";
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

					VacacionesTrabajador vacacionesTrabajador = VacacionesDB.setObjectVacacionesTrabajador(rs);
					lista.add(vacacionesTrabajador);

				}
				
				//Retornar Lista de Trabajadores
				return lista;
				
				// Fin Try
			} catch (Exception e) {
				throw new Exception("getVacationWithFilter: " + e.getMessage());
			} finally {
				db.close();
			}

		}// Fin getAllTrabajadors
		
		public static ArrayList<String[]> getVacacionesBasico(String idContrato, String fechaCalculo) throws Exception{
			
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			int i = 1;

			ArrayList<DatosVacaciones> lista = new ArrayList<DatosVacaciones>();

			try {

				// Crear sentencia en Sql
				sql = " call SAN_CLEMENTE.sw_getVacaciones( (SELECT codigo_trabajador FROM contratos WHERE id = "+idContrato+") , "+idContrato+", '"+fechaCalculo+"') ";
					
				ps = db.conn.prepareStatement(sql);
				
				ResultSet rs = ps.executeQuery(sql);

				String datos_periodo = "<span> </span>,";
//				String datos_fechaInicio = "<span></span>,";
//				String datos_fechaTermino = "<span></span>,";
//				String datos_prueba = "<span></span>,";
//				String datos_periodoGastado = "<span></span>,";
				String datos_diasGastados = "<span>Dias Usados</span>,";
				String datos_totalBasico = "<span>SALDO PERIODO: </span>,";
				String datos_feriadoBasico = "<span>Feriado Basico</span>,";
				String datos_feriadoProporcional = "<span>Feriado Progresivo</span>,";
				String datos_feriadoConvencional = "<span>Feriado Convencional</span>,";
				String datos_totalBasicoDias = "<span>TOTAL ACUMULADO: </span>,";
			
			
				while (rs.next()) {

					DatosVacaciones vt = new DatosVacaciones();
					
					vt.setFechaInicio(rs.getString("fechaInicio"));
					vt.setFechaTermino(rs.getString("fechaTermino"));
					vt.setPrueba(rs.getString("feriadoBasico"));
					vt.setPeriodoGastado(rs.getString("periodoGastado"));
					vt.setDiasGastados(rs.getString("diasGastados"));
					vt.setTotalBasico(rs.getString("totalBasico"));
					vt.setTotalBasicoDias(rs.getString("totalBasicoDias"));
					
					datos_periodo += rs.getString("periodo") + ","; //Periodo
					datos_totalBasico += rs.getString("totalBasico") + ","; //Total
					datos_diasGastados += rs.getString("diasGastados") + ","; //Dias Tomados
					datos_feriadoBasico += rs.getString("feriadoBasico") + ","; //Dias Tomados
					datos_feriadoProporcional += rs.getString("feriadoProgresivo") + ","; //Dias Tomados
					datos_feriadoConvencional += "0.0" + ","; //Dias Tomados
					datos_totalBasicoDias += rs.getString("totalBasicoDias") + ","; //Feriado Basico
					
					lista.add(vt);
		
				}
				
				String [] dataAux_periodo = datos_periodo.split(",");
				String [] dataAux_totalBasico = datos_totalBasico.split(",");
				String [] dataAux_diasGastados = datos_diasGastados.split(",");
				String [] dataAux_feriadoBasico = datos_feriadoBasico.split(",");
				String [] dataAux_feriadoProporcional = datos_feriadoProporcional.split(",");
				String [] dataAux_feriadoConvencional = datos_feriadoConvencional.split(",");
				String [] dataAux_totalBasicoDias = datos_totalBasicoDias.split(","); // Feriado Basico
				
				ArrayList<String[]> data = new ArrayList<>();
				data.add(dataAux_periodo);
				data.add(dataAux_feriadoBasico);
				data.add(dataAux_feriadoProporcional);
				data.add(dataAux_feriadoConvencional);
				data.add(dataAux_diasGastados);
				data.add(dataAux_totalBasico);
				data.add(dataAux_totalBasicoDias); //Total Acumulado
				
				//Retornar Lista de vacaciones
				
				return data;
				
				// Fin Try
			} catch (Exception e) {
				throw new Exception("getVacacionesBasico: " + e.getMessage());
			} finally {
				db.close();
			}
			
		}

		
		// Obtener Todos los Trabajadores con filtros
		public static ArrayList<Vacaciones> getVacacionesWithFilter(ArrayList<filterSql> filter) throws Exception {

					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB db = new ConnectionDB();

					ArrayList<Vacaciones> lista = new ArrayList<Vacaciones>();

					try {

						// Crear sentencia en Sql
						sql = " SELECT * FROM sw_vacaciones ";
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

							Vacaciones vacaciones = VacacionesDB.setObjectVacaciones(rs);
							lista.add(vacaciones);

						}
						// Fin Try
					} catch (Exception ex) {
						LOG.error("Error: Vacaciones: " + ex.getMessage() + " SQL: " + ps.toString());
					} finally {
						db.close();
					}
					return lista;

		}// Fin getAllTrabajadors
	
	
		public static VacacionesTrabajador setObjectVacacionesTrabajador(ResultSet rs) throws SQLException {

			VacacionesTrabajador vt = new VacacionesTrabajador();
			
			vt.setIdVacacionesTrabajador(rs.getInt("idVacacionesTrabajador"));
			vt.setCodTrabajador(rs.getInt("codTrabajador"));
			vt.setFechaSolicitud(rs.getString("fechaSolicitud"));
			vt.setFechaAprobacion(rs.getString("fechaAprobacion"));
			vt.setDias(rs.getInt("dias"));
			vt.setEstado(rs.getInt("estado"));
			vt.setIdVacacion(rs.getInt("idVacacion"));	
			vt.setIdContrato(rs.getInt("idContrato"));
			vt.setFechaInicioVacacion(rs.getString("fechaInicioVacacion"));
			vt.setFechaFinVacacion(rs.getString("fechaFinVacacion"));
			vt.setIdFeriadosLegales(rs.getInt("idFeriadosLegales"));	
			vt.setIdFeriadosProgresivos(rs.getInt("idFeriadosProgresivos"));	
			vt.setIdFeriadosConvencionales(rs.getInt("idFeriadosConvencionales"));
			vt.setDiasHabiles(rs.getInt("diasHabiles"));
			vt.setRut(rs.getString("rut"));

			return vt;

		}
		
		
		public static Vacaciones setObjectVacaciones(ResultSet rs) throws SQLException {

			Vacaciones vt = new Vacaciones();
			
			vt.setIdVacaciones(rs.getString("idVacaciones"));
			vt.setCodTrabajador(rs.getString("codTrabajador"));
			vt.setIdContrato(rs.getString("idContrato"));
			vt.setFechaAprobacion(rs.getString("fechaAprobacion"));
			vt.setSociedad(rs.getString("sociedad"));
			vt.setEstado(rs.getString("estado"));
			vt.setEstadoVacacion(rs.getString("estadoVacacion"));
			vt.setIdSolicitud(rs.getString("idSolicitud"));
			vt.setFechaInicioVacacion(rs.getString("fechaInicioVacacion"));
			vt.setFechaFinVacacion(rs.getString("fechaFinVacacion"));
			vt.setFeriadosLegales(rs.getString("feriadosLegales"));
			vt.setFeriadosProgresivos(rs.getString("feriadosProgresivos"));
			vt.setFeriadosConvencionales(rs.getString("feriadosConvencionales"));
			vt.setAprobadoPor(rs.getString("aprobadoPor"));
			vt.setCreadoPor(rs.getString("creadoPor"));
			vt.setFechaCreacion(rs.getString("fechaCreacion"));
			vt.setModificadoPor(rs.getString("modificadoPor"));
			vt.setFechaModificacion(rs.getString("fechaModificacion"));

			return vt;

		}
		
	
}
