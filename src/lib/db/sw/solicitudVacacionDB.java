package lib.db.sw;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.classSW.Campo;
import lib.classSW.SVExtended;
import lib.classSW.SolicitudVacacionesProgresivas;
import lib.classSW.calculadoraVacaciones;
import lib.classSW.cva;
import lib.classSW.sociedad;
import lib.classSW.solicitudVacacion;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import lib.utils.TimeUtility;

public class solicitudVacacionDB {

	private final static Logger LOG = LoggerFactory.getLogger(solicitudVacacionDB.class);
	
	public static boolean createSolicitudVacacion(solicitudVacacion solicitud) throws SQLException {
		try{
			//Obtener Solicitud
			solicitudVacacion vacaciones = getSolicitudVacacionById(solicitud.getIdSolicitud());

			//Sino Existe Insertar
			if(vacaciones.getIdSolicitud() == null ){				
				insertVacaciones(solicitud);	
			}else{
				updateVacaciones(solicitud);
			}
				return true;
			}
			catch(Exception ex){
				return false;
			}
	}
	
	public static boolean createSolicitudVacacionProgresivas(SolicitudVacacionesProgresivas solicitud) throws SQLException {
		try{
			//Obtener Solicitud
			SolicitudVacacionesProgresivas vacaciones = getSolicitudVacacionProgresivasById(solicitud.getIdSolicitud());

			//Sino Existe Insertar
			if(vacaciones.getIdSolicitud() == null ){				
				insertVacacionesProgresivas(solicitud);	
			}else{
				updateVacacionesProgresivas(solicitud);
			}
				return true;
			}
			catch(Exception ex){
				return false;
			}
	}


	public static void insertVacaciones(solicitudVacacion solicitud) throws SQLException{
		
		ConnectionDB db= new ConnectionDB();
		PreparedStatement ps= null;
		int i = 1;
		
		try{
		
		String sql=" INSERT INTO sw_solicitudVacaciones VALUES ( if(? = '',(SELECT MAX(idSolicitud)+1 FROM (SELECT idSolicitud FROM sw_solicitudVacaciones ) AS T1 ),?),?,?,?,?,?,?,?,?,?,"
				 + "?,?,?,?,?,?,? )";
		
		ps = db.conn.prepareStatement(sql);

		ps.setString(i++,solicitud.getIdSolicitud());
		ps.setString(i++,solicitud.getIdSolicitud());
		ps.setString(i++,GeneralUtility.convertStringToYYYYMMDD(solicitud.getFechaSolicitud()));
		ps.setString(i++,GeneralUtility.convertStringToYYYYMMDD(solicitud.getFechaInicio()));
		ps.setString(i++,GeneralUtility.convertStringToYYYYMMDD(solicitud.getFechaTermino()));
		ps.setString(i++,solicitud.getPeriodoSolicitud());
		ps.setString(i++,solicitud.getCantidadDiasSolicitud());
		ps.setString(i++,solicitud.getEstadoSolicitud());
		ps.setString(i++,solicitud.getDescripcion());
		ps.setString(i++,solicitud.getCodTrabajador());
		ps.setString(i++,solicitud.getIdContrato());
		ps.setBlob	(i++,solicitud.getComprobante());
		ps.setString(i++,solicitud.getAprobadaPor());
		ps.setString(i++,GeneralUtility.convertStringToYYYYMMDD(solicitud.getFechaAprobacion()));
		ps.setString(i++,solicitud.getEstado());
		ps.setString(i++,solicitud.getModificadoPor());
		ps.setString(i++,GeneralUtility.convertStringToYYYYMMDD(solicitud.getFechaModificacion()));
		ps.setString(i++,solicitud.getSociedad());
		
		ps.execute();
		
		}catch(Exception ex){
			LOG.error("Error: insertVacaciones: " + ex.getMessage());
		}
		finally {
			ps.close();
			db.close();
		}
		
	}
	
public static void insertVacacionesProgresivas(SolicitudVacacionesProgresivas solicitud) throws SQLException{
		
		ConnectionDB db= new ConnectionDB();
		PreparedStatement ps= null;
		int i = 1;
		
		try{
		
		String sql=" INSERT INTO sw_solicitudVacacionesProgresivas VALUES (( if(? = '',(SELECT MAX(idSolicitud)+1 FROM (SELECT idSolicitud FROM sw_solicitudVacacionesProgresivas ) AS T1 ), ?)), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		ps = db.conn.prepareStatement(sql);

		ps.setString(i++, solicitud.getIdSolicitud());
		ps.setString(i++, solicitud.getIdSolicitud());
		ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(solicitud.getFechaSolicitud()));
		ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(solicitud.getFechaInicio()));
		ps.setString(i++, TimeUtility.convertStringToYYYYMM("01-"+solicitud.getMesProceso()));
		ps.setString(i++, solicitud.getMesesCotizados());
		ps.setString(i++, solicitud.getMesesReconocidos());
		ps.setString(i++, solicitud.getEstadoSolicitud());
		ps.setString(i++, solicitud.getDescripcion());
		ps.setString(i++, solicitud.getCodTrabajador());
		ps.setString(i++, solicitud.getIdContrato());
		ps.setBlob(i++, solicitud.getComprobante());
		ps.setString(i++, solicitud.getAprobadaPor());
		ps.setString(i++, solicitud.getDiasProgresivos());
		ps.setString(i++, solicitud.getEstado());
		ps.setString(i++, solicitud.getModificadoPor());
		ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(solicitud.getFechaModificacion()));
		ps.setString(i++, solicitud.getSociedad());
		ps.setString(i++, solicitud.getFormula());
		
		
		ps.execute();
		
		}catch(Exception ex){
			LOG.error("Error: insertVacacionesProgresivas: " + ex.getMessage());
		}
		finally {
			ps.close();
			db.close();
		}
		
	}
	
	private static void updateVacaciones(solicitudVacacion solicitud) throws SQLException {
		
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps= null;
		int i = 1;
		
		try{
		String sql= " UPDATE sw_solicitudVacaciones SET "  
						+ " fechaSolicitud = ?, "
						+ " fechaInicio = ?, "
						+ " fechaTermino = ?, "
						+ " periodoSolicitud = ?, "
						+ " cantidadDiasSolicitud = ?, "
						+ " estadoSolicitud = ?, "
						+ " descripcion = ?, "
						+ " codTrabajador = ?, "
						+ " idContrato = ?, "
						+ " comprobante = ?, "
						+ " aprobadaPor = ?, "
						+ " fechaAprobacion = ?, "
						+ " estado = ?, "
						+ " modificadoPor = ?, "
						+ " fechaModificacion = ?, "
						+ " sociedad = ?, "
						+ " formula = ? "
						+ " WHERE idSolicitud = ? ";
				
		
		ps = db.conn.prepareStatement(sql);
		
		ps.setString(i++, solicitud.getFechaSolicitud());
		ps.setString(i++, solicitud.getFechaInicio());
		ps.setString(i++, solicitud.getFechaTermino());
		ps.setString(i++, solicitud.getPeriodoSolicitud());
		ps.setString(i++, solicitud.getCantidadDiasSolicitud());
		ps.setString(i++, solicitud.getEstadoSolicitud());
		ps.setString(i++, solicitud.getDescripcion());
		ps.setString(i++, solicitud.getCodTrabajador());
		ps.setString(i++, solicitud.getIdContrato());
		ps.setBlob(i++, solicitud.getComprobante());
		ps.setString(i++, solicitud.getAprobadaPor());
		ps.setString(i++, solicitud.getFechaAprobacion());
		ps.setString(i++, solicitud.getEstado());
		ps.setString(i++, solicitud.getModificadoPor());
		ps.setString(i++, solicitud.getFechaModificacion());
		ps.setString(i++, solicitud.getSociedad());
		ps.setString(i++, solicitud.getIdSolicitud());
		
		ps.execute();
		
		}catch(Exception ex){
			LOG.error("Error: updateVacaciones: " + ex.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		
	}
	
private static void updateVacacionesProgresivas(SolicitudVacacionesProgresivas solicitud) throws SQLException {
		
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps= null;
		int i = 1;
		
		try{
		String sql= " UPDATE sw_solicitudVacacionesProgresivas SET "  
					+ " fechaSolicitud = ? "
					+ " fechaInicio = ? "
					+ " mesProceso = ? "
					+ " mesesCotizados = ? "
					+ " mesesReconocidos = ? "
					+ " estadoSolicitud = ? "
					+ " descripcion = ? "
					+ " codTrabajador = ? "
					+ " idContrato = ? "
					+ " comprobante = ? "
					+ " aprobadaPor = ? "
					+ " diasProgresivos = ? "
					+ " estado = ? "
					+ " modificadoPor = ? "
					+ " fechaModificacion = ? "
					+ " sociedad = ? "
					+ " formula = ? "
					+ " WHERE idSolicitud = ? ";
		
		ps = db.conn.prepareStatement(sql);
		
		ps.setString(i++, solicitud.getFechaSolicitud());
		ps.setString(i++, solicitud.getFechaInicio());
		ps.setString(i++, solicitud.getMesProceso());
		ps.setString(i++, solicitud.getMesesCotizados());
		ps.setString(i++, solicitud.getMesesReconocidos());
		ps.setString(i++, solicitud.getEstadoSolicitud());
		ps.setString(i++, solicitud.getDescripcion());
		ps.setString(i++, solicitud.getCodTrabajador());
		ps.setString(i++, solicitud.getIdContrato());
		ps.setBlob(i++, solicitud.getComprobante());
		ps.setString(i++, solicitud.getAprobadaPor());
		ps.setString(i++, solicitud.getDiasProgresivos());
		ps.setString(i++, solicitud.getEstado());
		ps.setString(i++, solicitud.getModificadoPor());
		ps.setString(i++, solicitud.getFechaModificacion());
		ps.setString(i++, solicitud.getSociedad());
		ps.setString(i++, solicitud.getIdSolicitud());
		
		ps.execute();
		
		}catch(Exception ex){
			LOG.error("Error: updateVacacionesProgresivas: " + ex.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		
	}
	

	public static solicitudVacacion getSolicitudVacacionById(String id) throws SQLException {
		
		PreparedStatement ps = null;
		String sql="";
		
		ConnectionDB db = new ConnectionDB();
		solicitudVacacion vacaciones = new solicitudVacacion();
			
		try{
			sql = "select * from sw_solicitudVacaciones where idSolicitud = "+ id ;
			ps = db.conn.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery(sql);
			
			
			
			while(rs.next()){				
				
				 vacaciones = setObjectVacaciones(rs);
				
			}		

		}catch (Exception e){
			System.out.println("Error getSolicitudVacacionById: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return vacaciones;		
	
		
	}
	
public static SolicitudVacacionesProgresivas getSolicitudVacacionProgresivasById(String id) throws SQLException {
		
		PreparedStatement ps = null;
		String sql="";
		
		ConnectionDB db = new ConnectionDB();
		SolicitudVacacionesProgresivas vacaciones = new SolicitudVacacionesProgresivas();
			
		try{
			sql = "select * from sw_solicitudVacacionesProgresivas where idSolicitud = "+ id ;
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){				
				 vacaciones = setObjectVacacionesProgresivas(rs);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return vacaciones;		
	
	}
	
	
	private static solicitudVacacion setObjectVacaciones(ResultSet rs) throws SQLException, IOException {
		
		solicitudVacacion vacaciones = new solicitudVacacion();
			
		vacaciones.setIdSolicitud(rs.getString("idSolicitud"));
		vacaciones.setFechaSolicitud(rs.getString("fechaSolicitud"));
		vacaciones.setFechaInicio(rs.getString("fechaInicio"));
		vacaciones.setFechaTermino(rs.getString("fechaTermino"));
		vacaciones.setPeriodoSolicitud(rs.getString("periodoSolicitud"));
		vacaciones.setCantidadDiasSolicitud(rs.getString("cantidadDiasSolicitud"));
		vacaciones.setEstadoSolicitud(rs.getString("estadoSolicitud"));
		vacaciones.setDescripcion(rs.getString("descripcion"));
		vacaciones.setCodTrabajador(rs.getString("codTrabajador"));
		vacaciones.setIdContrato(rs.getString("idContrato"));
		//vacaciones.setComprobante(blobDocument);
		vacaciones.setAprobadaPor(rs.getString("aprobadaPor"));
		vacaciones.setFechaAprobacion(rs.getString("fechaAprobacion"));
		vacaciones.setEstado(rs.getString("estado"));
		vacaciones.setModificadoPor(rs.getString("modificadoPor"));
		vacaciones.setFechaModificacion(rs.getString("fechaModificacion"));
		vacaciones.setSociedad(rs.getString("sociedad"));
		
		
		return vacaciones;
	}
	
private static SolicitudVacacionesProgresivas setObjectVacacionesProgresivas(ResultSet rs) throws SQLException, IOException {
		
		SolicitudVacacionesProgresivas vacaciones = new SolicitudVacacionesProgresivas();
			
		vacaciones.setIdSolicitud(rs.getString("idSolicitud"));
		vacaciones.setFechaSolicitud(rs.getString("fechaSolicitud"));
		vacaciones.setFechaInicio(rs.getString("fechaInicio"));
		vacaciones.setMesProceso(rs.getString("mesProceso"));
		vacaciones.setMesesCotizados(rs.getString("mesesCotizados"));
		vacaciones.setMesesReconocidos(rs.getString("mesesReconocidos"));
		vacaciones.setEstadoSolicitud(rs.getString("estadoSolicitud"));
		vacaciones.setDescripcion(rs.getString("descripcion"));
		vacaciones.setCodTrabajador(rs.getString("codTrabajador"));
		vacaciones.setIdContrato(rs.getString("idContrato"));
		//vacaciones.setComprobante(rs.getString("comprobante"));
		vacaciones.setAprobadaPor(rs.getString("aprobadaPor"));
		vacaciones.setDiasProgresivos(rs.getString("diasProgresivos"));
		vacaciones.setEstado(rs.getString("estado"));
		vacaciones.setModificadoPor(rs.getString("modificadoPor"));
		vacaciones.setFechaModificacion(rs.getString("fechaModificacion"));
		vacaciones.setSociedad(rs.getString("sociedad"));
		vacaciones.setFormula(rs.getString("formula"));
		
		return vacaciones;
	}


	public static ArrayList<solicitudVacacion> getSolicitudVacacionesWithFilter(ArrayList<filterSql> filter) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<solicitudVacacion> lista = new ArrayList<solicitudVacacion>();
		
		try {
			
			sql = " SELECT * FROM sw_solicitudVacaciones ";
			
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

			solicitudVacacion solicitudesVacaciones = solicitudVacacionDB.setObjectVacaciones(rs);
			lista.add(solicitudesVacaciones);

		}
	
		//Retornar Lista de Trabajadores
		return lista;
		
		// Fin Try
	} catch (Exception e) {
		LOG.error("getSolicitudVacacionesWithFilter: " + e.getMessage());
		LOG.error("getSolicitudVacacionesWithFilter: " + ps.toString());
		throw new Exception("getSolicitudVacacionesWithFilter: " + e.getMessage());
	} finally {
		db.close();
	}
		
		
	}
	
	
public static ArrayList<SolicitudVacacionesProgresivas> getSolicitudVacacionesProgresivasWithFilter(ArrayList<filterSql> filter) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<SolicitudVacacionesProgresivas> lista = new ArrayList<SolicitudVacacionesProgresivas>();
		
		try {
			
			sql = " SELECT * FROM sw_solicitudVacacionesProgresivas ";
			
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

			SolicitudVacacionesProgresivas solicitudesVacaciones = solicitudVacacionDB.setObjectVacacionesProgresivas(rs);
			lista.add(solicitudesVacaciones);

		}
	
		//Retornar Lista de Trabajadores
		return lista;
		
		// Fin Try
	} catch (Exception e) {
		LOG.error("getSolicitudVacacionesWithFilter: " + e.getMessage());
		LOG.error("getSolicitudVacacionesWithFilter: " + ps.toString());
		throw new Exception("getSolicitudVacacionesWithFilter: " + e.getMessage());
	} finally {
		db.close();
	}
		
		
	}

//
//	public static solicitudVacacion getLastSolicitudVacacion() throws SQLException{
//		PreparedStatement ps = null;
//		String sql="";
//		solicitudVacacion solicitud=new solicitudVacacion(); 
//		ConnectionDB db = new ConnectionDB();
//			
//		try{
//			sql = "select * from sw_m_solicitud_vacaciones order By idSolicitud DESC LIMIT 1";
//			ps = db.conn.prepareStatement(sql);
//			
//			
//			ResultSet rs = ps.executeQuery(sql);			
//			while(rs.next()){				
//				solicitudVacacion sol= new solicitudVacacion();				
//				sol.setIdSolicitud(rs.getInt("idSolicitud"));
//				sol.setFechaSolicitud(rs.getString("fechaSolicitud"));
//				sol.setFechaInicioSolicitud(rs.getString("fechaInicioSolicitud"));
//				sol.setFechaFinSolicitud(rs.getString("fechaFinSolicitud"));
//				sol.setPeriodoSolicitud(rs.getInt("periodoSolicitud"));
//				sol.setCantidadDiasSolicitud(rs.getInt("cantidadDiasSolicitud"));
//				sol.setEstadoSolicitud(rs.getString("estadoSolicitud"));
//				sol.setDescripcionSolicitud(rs.getString("descripcionSolicitud"));
//				sol.setComprobanteSolicitud(rs.getInt("comprobanteSolicitud"));
//				solicitud=sol;
//			}		
//
//		}catch (Exception e){
//			System.out.println("Error: " + e.getMessage());
//		}finally {
//			ps.close();
//			db.close();
//		}		
//		return solicitud;
//		
//		
//	}
//	public static solicitudVacacion getSolicitudVacacionTById(int id) throws SQLException {
//		PreparedStatement ps = null;
//		String sql="";
//		solicitudVacacion solicitud=new solicitudVacacion(); 
//		ConnectionDB db = new ConnectionDB();
//			
//		try{
//			sql = "select * from sw_r_solicitudvacaciones_trabajador Inner join sw_m_solicitud_vacaciones on sw_r_solicitudvacaciones_trabajador.idSolicitud=sw_m_solicitud_vacaciones.idSolicitud WHERE sw_r_solicitudvacaciones_trabajador.idSolicitud="+id;
//			ps = db.conn.prepareStatement(sql);
//			
//			
//			ResultSet rs = ps.executeQuery(sql);			
//			while(rs.next()){				
//				solicitudVacacion sol= new solicitudVacacion();				
//				sol.setIdSolicitud(id);
//				sol.setIdTrabajador(rs.getInt("id"));
//				sol.setFechaSolicitud(rs.getString("fechaSolicitud"));
//				sol.setFechaInicioSolicitud(rs.getString("fechaInicioSolicitud"));
//				sol.setFechaFinSolicitud(rs.getString("fechaFinSolicitud"));
//				sol.setPeriodoSolicitud(rs.getInt("periodoSolicitud"));
//				sol.setCantidadDiasSolicitud(rs.getInt("cantidadDiasSolicitud"));
//				sol.setEstadoSolicitud(rs.getString("estadoSolicitud"));
//				sol.setDescripcionSolicitud(rs.getString("descripcionSolicitud"));
//				sol.setComprobanteSolicitud(rs.getInt("comprobanteSolicitud"));
//				
//				
//				
//				
//				solicitud=sol;
//			}		
//
//		}catch (Exception e){
//			System.out.println("Error: " + e.getMessage());
//		}finally {
//			ps.close();
//			db.close();
//		}		
//		return solicitud;
//		
//	}
//
//	public static solicitudVacacion getBlankSolicitudVacacion() {
//		// TODO Auto-generated method stub
//		
//		solicitudVacacion s=new solicitudVacacion();
//		
//		return s.createBlankSolicitudVacacion();
//	}
//	public static ArrayList<solicitudVacacion> getAllsolicitudVacacionesT() throws SQLException {
//		PreparedStatement ps= null;
//		ConnectionDB db= new ConnectionDB();
//		String sql="";
//		ArrayList<solicitudVacacion> lista= new ArrayList<solicitudVacacion>();
//	
//		try{
//			sql = "select * from sw_r_solicitudvacaciones_trabajador Inner join sw_m_solicitud_vacaciones on sw_r_solicitudvacaciones_trabajador.idSolicitud=sw_m_solicitud_vacaciones.idSolicitud";
//			ps = db.conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery(sql);			
//			while(rs.next()){				
//				solicitudVacacion sol= new solicitudVacacion();				
//				sol.setIdSolicitud(rs.getInt("idSolicitud"));
//				sol.setIdTrabajador(rs.getInt("idTrabajador"));
//				sol.setFechaSolicitud(rs.getString("fechaSolicitud"));
//				sol.setFechaInicioSolicitud(rs.getString("fechaInicioSolicitud"));
//				sol.setFechaFinSolicitud(rs.getString("fechaFinSolicitud"));
//				sol.setPeriodoSolicitud(rs.getInt("periodoSolicitud"));
//				sol.setCantidadDiasSolicitud(rs.getInt("cantidadDiasSolicitud"));
//				sol.setEstadoSolicitud(rs.getString("estadoSolicitud"));
//				sol.setDescripcionSolicitud(rs.getString("descripcionSolicitud"));
//				sol.setComprobanteSolicitud(rs.getInt("comprobanteSolicitud"));
//				
//				lista.add(sol);
//			}		
//
//		}catch (Exception e){
//			System.out.println("Error: " + e.getMessage());
//		}finally {
//			ps.close();
//			db.close();
//		}		
//		return lista;
//		
//		
//		
//		
//	}
//
	public static boolean deleteSolicitudVacacionById(String id) throws SQLException {
		PreparedStatement ps= null;
		String sql="";
		
		ConnectionDB db= new ConnectionDB();
		try{
			
			sql = "UPDATE sw_solicitudVacaciones SET estado = 0 WHERE idSolicitud = "+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;			

		}catch (Exception e){
			return false;
		}finally {
			ps.close();
			db.close();
		}		
	
	}
	
	public static boolean deleteSolicitudVacacionProgresivasById(String id) throws SQLException {
		PreparedStatement ps= null;
		String sql="";
		
		ConnectionDB db= new ConnectionDB();
		try{
			
			sql = "UPDATE sw_solicitudVacacionesProgresivas SET estado = 0 WHERE idSolicitud = "+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;			

		}catch (Exception e){
			return false;
		}finally {
			ps.close();
			db.close();
		}		
	
	}

	public static boolean createSolicitudVacacion2(solicitudVacacion solicitud) throws SQLException {
		PreparedStatement ps= null,ps0=null;
		ConnectionDB db= new ConnectionDB();
		ResultSet rs=null;
		
		String fechaInicio=solicitud.getFechaInicioSolicitud();
		String diasDeVacaciones=solicitud.getCantidadDiasSolicitud();
		calculadoraVacaciones cv=new calculadoraVacaciones();
		cv.setFechaInicial(fechaInicio);
		cv.setCantidadDias(Integer.parseInt(diasDeVacaciones));
		try{
			//traer feriados
			
			String sql0="SELECT fechaFeriado FROM sw_m_feriados";
			ps0=db.conn.prepareStatement(sql0);
			rs=ps0.executeQuery(sql0);
			while(rs.next()){
				cv.addFeriado(rs.getString("fechaFeriado"));
			}
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
			String strFecha = solicitud.getFechaSolicitud();
			Date fecha = null;
			fecha = formatoDelTexto.parse(strFecha);
			java.sql.Date a=new java.sql.Date(fecha.getTime());
			System.out.println( a.toString());
			
					
			String sql="INSERT INTO sw_m_solicitud_vacaciones (fechaSolicitud, fechaInicioSolicitud, fechaFinSolicitud, periodoSolicitud, cantidadDiasSolicitud, estadoSolicitud, descripcionSolicitud, comprobanteSolicitud, codTrabajador, idContrato) VALUES (?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setDate(1, a);
			fecha = formatoDelTexto.parse(solicitud.getFechaInicioSolicitud());
			a=new java.sql.Date(fecha.getTime());
			System.out.println( a.toString());
			ps.setDate(2, a);
			fecha = formatoDelTexto.parse(cv.CalcularFechaFin());
			a=new java.sql.Date(fecha.getTime());
			System.out.println( a.toString());
			ps.setDate(3,a);
			ps.setString(4, solicitud.getPeriodoSolicitud());
			ps.setString(5,solicitud.getCantidadDiasSolicitud());
			ps.setString(6,"En Trámite");
			ps.setString(7,"");
			ps.setInt(8,0);
			ps.setString(9,solicitud.getCodTrabajador());
			ps.setString(10,solicitud.getIdContrato());
			ps.execute();
			return true;
			}
			catch(Exception ex){
				return false;
			}
			finally{
				db.conn.close();
			}
	}
	
	
	public static String getFechaFinal(cva cvas) throws SQLException {
		PreparedStatement ps0=null;
		ConnectionDB db= new ConnectionDB();
		
		calculadoraVacaciones cv= new calculadoraVacaciones();
		cv.setFechaInicial(cvas.getFechaInicioSolicitud());
		cv.setCantidadDias(cvas.getCantidadDiasSolicitud());
		String fechaFinSolicitud="";
		try{
			//traer feriados
			
			String sql0="SELECT fechaFeriado FROM sw_m_feriados WHERE idRegion='16' or idRegion="+cvas.getIdregion();
			ps0=db.conn.prepareStatement(sql0);
			ResultSet rs=ps0.executeQuery(sql0);
			while(rs.next()){
				String fecha=rs.getString("fechaFeriado");
				
				cv.addFeriado(fecha);
				
			}
			cv.getFeriados();
			fechaFinSolicitud= cv.CalcularFechaFin();		
			
			}
			
			catch(Exception ex){
				return "false";
			}
			finally{
				db.conn.close();
			}
		
		
		
		
		
		
		
		
		
		return fechaFinSolicitud;
	}
//	
//	
//	public static Integer getDiasSolicitudVacacionByIdContrato(int id){
//		PreparedStatement ps = null;
//		ConnectionDB db = new ConnectionDB();
//		Integer cantidad = 0;	
//		try{
//			String sql = "select sum(cantidadDiasSolicitud) as cantidad from sw_m_solicitud_vacaciones where idContrato = "+id;
//			ps = db.conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery(sql);			
//			while(rs.next()){				
//				cantidad = rs.getInt("cantidad");
//			}		
//
//		}catch (Exception e){
//			System.out.println("Error: " + e.getMessage());
//			e.printStackTrace();
//		}finally {
//			try {
//				ps.close();
//			} catch (SQLException e) {
//				System.out.println("Error: " + e.getMessage());
//				e.printStackTrace();
//			}
//			db.close();
//		}		
//		return cantidad;
//		
//	}
//
	public static ArrayList<solicitudVacacion> getSolicitudes() throws SQLException {
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		String sql="";
		ArrayList<solicitudVacacion> lista= new ArrayList<solicitudVacacion>();
	
		try{
			sql = "SELECT * FROM sw_m_solicitud_vacaciones "+
				  "inner join trabajadores on sw_m_solicitud_vacaciones.codTrabajador=trabajadores.codigo "+
				  "order by sw_m_solicitud_vacaciones.codTrabajador, sw_m_solicitud_vacaciones.fechaInicioSolicitud";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				solicitudVacacion sol= new solicitudVacacion();				
				sol.setIdSolicitud(rs.getString("idSolicitud"));
				
				sol.setCodTrabajador(rs.getString("codTrabajador"));
				sol.setApellidoPaterno(rs.getString("apellidoPaterno"));
				sol.setApellidoMaterno(rs.getString("apellidoMaterno"));
				sol.setNombre(rs.getString("nombre"));
				sol.setFechaSolicitud(rs.getString("fechaSolicitud"));
				sol.setFechaInicioSolicitud(rs.getString("fechaInicioSolicitud"));
				sol.setFechaFinSolicitud(rs.getString("fechaFinSolicitud"));
				sol.setPeriodoSolicitud(rs.getString("periodoSolicitud"));
				sol.setCantidadDiasSolicitud(rs.getString("cantidadDiasSolicitud"));
				sol.setEstadoSolicitud(rs.getString("estadoSolicitud"));
				sol.setDescripcionSolicitud(rs.getString("descripcionSolicitud"));
				sol.setComprobanteSolicitud(rs.getString("comprobanteSolicitud"));
				
				lista.add(sol);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<sociedad> getEmpresas() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<sociedad> lista = new ArrayList<sociedad>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql = "select * from sociedad order by denominacionSociedad ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				sociedad sc = new sociedad();
				sc.setIdSociedad(rs.getInt("idSociedad"));
				sc.setSociedad(rs.getString("sociedad"));
				sc.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				lista.add(sc);
			}
			

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		
		return lista;
	}
	public static ArrayList<SVExtended> getTrabajadoresFiltering(String Empresa, String Campo, String Grupo, String CECO) throws SQLException{
		PreparedStatement ps = null;
		String sql="";
		String addToSql="";
		
		System.out.println(Empresa+"-"+Campo+"-"+Grupo+"-"+CECO);
		
		
		if(Empresa.equals("0")){
			if(Campo.equals("0")){
				if(Grupo.equals("0")){
					if(CECO.equals("0")){
						// Empresa 0, Huerto 0, Grupo 0, CECO 0
						addToSql="";
					}
					else{
						// Empresa 0, Huerto 0, Grupo 0, CECO 1
						
						addToSql="WHERE idCECO='"+CECO+"'";
					}
				}
				else{
					if(CECO.equals("0")){
						// Empresa 0, Huerto 0, Grupo 1, CECO 0
						addToSql="WHERE campo.grupo='"+Grupo+"'";
					}
					else{
						// Empresa 0, Huerto 0, Grupo 1, CECO 1
						addToSql="WHERE campo.grupo='"+Grupo+"' and idCECO='"+CECO+"'";
					}
				}
			}
			else{
				
				if(Grupo.equals("0")){
					if(CECO.equals("0")){
						// Empresa 0, campo 1, Grupo 0, CECO 0
						addToSql="WHERE campo='"+Campo+"'";
					}
					else{
						// Empresa 0, Campo 1, Grupo 0, CECO 1
						addToSql="WHERE Campo='"+Campo+"' AND idCECO='"+CECO+"'";
					}
				}
				else{
					if(CECO.equals("0")){
						// Empresa 0, Campo 1, Grupo 1, CECO 0
						addToSql="WHERE Campo='"+Campo+"' AND campo.grupo='"+Grupo+"'";
					}
					else{
						// Empresa 0, Campo 1, Grupo 1, CECO 1
						addToSql="WHERE Campo='"+Campo+"' AND campo.grupo='"+Grupo+"' AND idCECO='"+CECO+"'";
					}
				}
			}
			
		}
		else{
			// Empresa 1, Huerto 0, Grupo 0, CECO 0,
			if(Campo.equals("0")){
				if(Grupo.equals("0")){
					if(CECO.equals("0")){
						// Empresa 1, Huerto 0, Grupo 0, CECO 0
						addToSql="WHERE sociedad='"+Empresa+"'";
					}
					else{
						// Empresa 1, Huerto 0, Grupo 0, CECO 1
						addToSql="WHERE sociedad='"+Empresa+"' AND idCECO='"+CECO+"'";
					}
				}
				else{
					if(CECO.equals("0")){
						// Empresa 1, Huerto 0, Grupo 1, CECO 0
						addToSql="WHERE sociedad='"+Empresa+"' AND campo.grupo='"+Grupo+"'";
					}
					else{
						// Empresa 1, Huerto 0, Grupo 1, CECO 1
						addToSql="WHERE sociedad='"+Empresa+"' AND campo.grupo='"+Grupo+"' AND idCECO='"+CECO+"'";
					}
				}
			}
			else{
				if(Grupo.equals("0")){
					if(CECO.equals("0")){
						// Empresa 1, Campo 1, Grupo 0, CECO 0
						addToSql="WHERE sociedad='"+Empresa+"' AND campo='"+Campo+"'";
						
					}
					else{
						// Empresa 1, Campo 1, Grupo 0, CECO 1
						addToSql="WHERE sociedad='"+Empresa+"' AND campo='"+Campo+"' AND idCECO='"+CECO+"'";
					}
				}
				else{
					if(CECO.equals("0")){
						// Empresa 1, Campo 1, Grupo 1, CECO 0
						addToSql="WHERE sociedad='"+Empresa+"' AND campo='"+Campo+"' AND campo.grupo='"+Grupo+"'";
					}
					else{
						// Empresa 1, Campo 1, Grupo 1, CECO 1
						addToSql="WHERE sociedad='"+Empresa+"' AND campo='"+Campo+"' AND campo.grupo='"+Grupo+"' AND idCECO='"+CECO+"'";
					}
				}
			}
		}
		ArrayList<SVExtended> lista = new ArrayList<SVExtended>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql = "SELECT trabajadores.id,trabajadores.idCECO , trabajadores.codigo, trabajadores.rut, trabajadores.nombre, trabajadores.rutTemporal, trabajadores.apellidoPaterno, "+
					"trabajadores.apellidoMaterno, campo.campo, campo.sociedad, campo.zona,idSolicitud, grupo_ceco_work, campo.grupo, "+
					"sw_m_solicitud_vacaciones.cantidadDiasSolicitud,sw_m_solicitud_vacaciones.fechaSolicitud,sw_m_solicitud_vacaciones.fechaInicioSolicitud, "+
					"sw_m_solicitud_vacaciones.fechaFinSolicitud "+ 
					"FROM trabajadores "+
					"left JOIN campo ON trabajadores.idHuerto=campo.campo "+
					"left JOIN sw_m_solicitud_vacaciones ON trabajadores.codigo=sw_m_solicitud_vacaciones.codTrabajador "+addToSql;

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				SVExtended sv = new SVExtended();
				sv.setIdSolicitud(rs.getString("id"));
				sv.setCodTrabajador(rs.getString("codigo"));
				sv.setRut(rs.getString("rut"));
				sv.setNombre(rs.getString("nombre"));
				sv.setApellidoPaterno(rs.getString("apellidoPaterno"));
				sv.setApellidoMaterno(rs.getString("apellidoMaterno"));
				sv.setCampo(rs.getString("campo"));
				sv.setSociedad(rs.getString("sociedad"));
				sv.setZona(rs.getString("zona"));
				sv.setGrupo_ceco_work(rs.getString("grupo_ceco_work"));
				sv.setGrupo(rs.getString("grupo"));			
				sv.setFechaSolicitud(rs.getString("fechaSolicitud"));
				sv.setFechaInicioSolicitud(rs.getString("fechaInicioSolicitud"));
				sv.setFechaFinSolicitud(rs.getString("fechaFinSolicitud"));
				sv.setCantidadDiasSolicitud(rs.getString("cantidadDiasSolicitud"));
				
				lista.add(sv);
			}
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}	
		return lista;
	}
	
	

	public static ArrayList<Campo> getZonas(String campo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Campo> lista = new ArrayList<Campo>();
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql = "SELECT distinct(zona) FROM campo where campo.campo='"+campo+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				Campo sc = new Campo();
				sc.setZona(rs.getString("zona"));
				lista.add(sc);
			}
			

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		
		return lista;
	}


	public static ArrayList<solicitudVacacion> getCantidadDiasVacaciones(String fechaInicio, String fechaTermino) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		ArrayList<solicitudVacacion> lista = new ArrayList<solicitudVacacion>();
		
		try {
			
			sql = " SELECT getCantidadDiasVacaciones(?, ?) as cantidadDiasSolicitud ";
			
			ps = db.conn.prepareStatement(sql);
			ps.setString(i++,GeneralUtility.convertStringToYYYYMMDD(fechaInicio));
			ps.setString(i++,GeneralUtility.convertStringToYYYYMMDD(fechaTermino));
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {

			solicitudVacacion solicitudesVacaciones = new solicitudVacacion();
			
			solicitudesVacaciones.setCantidadDiasSolicitud(rs.getString("cantidadDiasSolicitud"));
			
			lista.add(solicitudesVacaciones);

		}
	
		//Retornar Lista de Trabajadores
		return lista;
		
		// Fin Try
	} catch (Exception e) {
		LOG.error("getSolicitudVacacionesWithFilter: " + e.getMessage());
		LOG.error("getSolicitudVacacionesWithFilter: " + ps.toString());
		throw new Exception("getSolicitudVacacionesWithFilter: " + e.getMessage());
	} finally {
		db.close();
	}
		
		
	}
	
	
public static ArrayList<SolicitudVacacionesProgresivas> getMesesReconocidos(String periodoSolicitud, String idContrato) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		ArrayList<SolicitudVacacionesProgresivas> lista = new ArrayList<SolicitudVacacionesProgresivas>();
		
		try {
			
			sql = " SELECT getMesesReconocidos(?, ?) as mesesReconocidos ";
			
			ps = db.conn.prepareStatement(sql);
			ps.setString(i++,periodoSolicitud);
			ps.setString(i++,idContrato);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {

			SolicitudVacacionesProgresivas solicitudesVacaciones = new SolicitudVacacionesProgresivas();
			
			solicitudesVacaciones.setMesesReconocidos((rs.getString("mesesReconocidos")));
			
			lista.add(solicitudesVacaciones);

		}
	
		//Retornar Lista de Trabajadores
		return lista;
		
		// Fin Try
	} catch (Exception e) {
		LOG.error("getMesesReconocidos: " + e.getMessage());
		LOG.error("getMesesReconocidos: " + ps.toString());
		throw new Exception("getMesesReconocidos: " + e.getMessage());
	} finally {
		db.close();
	}
		
		
	}


public static ArrayList<SolicitudVacacionesProgresivas> getDiasProgresivos(String idContrato, String mesesCotizados, String mesesReconocidos) throws Exception {
	
	PreparedStatement ps = null;
	String sql = "";
	ConnectionDB db = new ConnectionDB();
	int i = 1;

	ArrayList<SolicitudVacacionesProgresivas> lista = new ArrayList<SolicitudVacacionesProgresivas>();
	
	try {
		
		sql = " SELECT getDiasProgresivos(?, ?, ?) as mesesTotales ";
		
		ps = db.conn.prepareStatement(sql);
		ps.setString(i++,idContrato);
		ps.setString(i++,mesesCotizados);
		ps.setString(i++,mesesReconocidos);
	
	ResultSet rs = ps.executeQuery();
	
	while (rs.next()) {

		SolicitudVacacionesProgresivas solicitudesVacaciones = new SolicitudVacacionesProgresivas();
		
		solicitudesVacaciones.setFormula((rs.getString("mesesTotales")));
		
		lista.add(solicitudesVacaciones);

	}

	//Retornar Lista de Trabajadores
	return lista;
	
	// Fin Try
} catch (Exception e) {
	LOG.error("getMesesReconocidos: " + e.getMessage());
	LOG.error("getMesesReconocidos: " + ps.toString());
	throw new Exception("getMesesReconocidos: " + e.getMessage());
} finally {
	db.close();
}
	
	
}


public static ArrayList<SolicitudVacacionesProgresivas> getMesesTranscurridos(String periodoInicio, String periodoFin) throws Exception {
	
	PreparedStatement ps = null;
	String sql = "";
	ConnectionDB db = new ConnectionDB();
	int i = 1;

	ArrayList<SolicitudVacacionesProgresivas> lista = new ArrayList<SolicitudVacacionesProgresivas>();
	
	try {
		
		sql = " SELECT PERIOD_DIFF(?, ?) AS mesesTranscurridos ";
		
		ps = db.conn.prepareStatement(sql);
		ps.setString(i++,TimeUtility.convertStringToYYYYMM(GeneralUtility.getCurrentDateDDMMYYYY().replaceAll("/", "-")));
		ps.setString(i++,TimeUtility.convertStringToYYYYMM(TimeUtility.convertStringToDDMMYYYY(periodoInicio)));
	
	ResultSet rs = ps.executeQuery();
	
	while (rs.next()) {

		SolicitudVacacionesProgresivas solicitudesVacaciones = new SolicitudVacacionesProgresivas();
		
		solicitudesVacaciones.setMesesTranscurridos((rs.getString("mesesTranscurridos")));
		
		lista.add(solicitudesVacaciones);

	}

	//Retornar Lista de Trabajadores
	return lista;
	
	// Fin Try
} catch (Exception e) {
	LOG.error("getMesesTranscurridos: " + e.getMessage());
	LOG.error("getMesesTranscurridos: " + ps.toString());
	throw new Exception("getMesesTranscurridos: " + e.getMessage());
} finally {
	db.close();
}
	
	
}


	
	
}
