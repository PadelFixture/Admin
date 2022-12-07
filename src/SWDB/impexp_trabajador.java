package SWDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lib.classSW.AnticiposIndividuales;
import lib.classSW.CargarTipodePago;
import lib.classSW.CreateLiquidacion;
import lib.classSW.GetDatosContratoTrabajador;
import lib.classSW.InsertContrato;
import lib.classSW.InsertHD;
import lib.classSW.ListaSociedad;
import lib.classSW.ListaSolicitudes;
import lib.classSW.LoadCargoPreseleccion;
import lib.classSW.LoadConceptos;
import lib.classSW.LoadContratacion;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.NominaAnticipos;
import lib.classSW.NotificacionContrato;
import lib.classSW.OrdenDePagoPREVIRED;
import lib.classSW.PreseleccionDetalle;
import lib.classSW.PreseleccionDetalleVer;
import lib.classSW.RechazoPreseleccionado;
import lib.classSW.TipoContrato;
import lib.classSW.TipoLicencia;
import lib.classSW.TodoTablaContrato;
import lib.classSW.UpdateEstadoReclutamiento;
import lib.classSW.UpdateTrabajadorHD;
import lib.classSW.auxiliar;
import lib.classSW.Cargo;
import lib.classSW.contrato_SW;
import lib.classSW.haberesDescuentos;
import lib.classSW.listaRechazo;
import lib.classSW.posiciones;
import lib.classSW.preseleccionados;
import lib.classSW.reclutamiento;
import lib.classSW.seleccionados;
import lib.classSW.sw_haberesDescuentos;
import lib.classSW.sw_huerto;
import lib.classSW.tablaPermisoLicencia;
//import lib.classSW.movimiento;
import lib.classSW.trabajador;
import lib.classSW.trabajador_pre;
import lib.classSW.trabajadores_prese;
import lib.db.ConnectionDB;
import lib.struc.trabajadores;
import wordCreator.utils;
public class impexp_trabajador {
	//trabajadores por rut
	public static ArrayList<trabajador> gettrabajador(String tra)  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<trabajador> lista = new ArrayList<trabajador>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from trabajador where rut = '"+tra+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajador tr = new trabajador();
				tr.setIdTrabajador(rs.getInt("idTrabajador"));
				tr.setIdPerfil(rs.getInt("idPerfil"));
				tr.setRut(rs.getString("rut"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setApellidoMaterno(rs.getString("apellidoMaterno"));
				tr.setNombre(rs.getString("nombre"));
				tr.setFechaNacimiento(rs.getString("fechaNacimiento"));
				tr.setEdad(rs.getString("edad"));
				tr.setIdNacionalidad(rs.getInt("idNacionalidad"));
				tr.setIdGenero(rs.getInt("idGenero"));
				tr.setIdStatus(rs.getInt("idStatus"));
				tr.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
				tr.setIdContrato(rs.getInt("idContrato"));
				lista.add(tr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	//rut de todos los trabajadores
	public static ArrayList<trabajador> getruttrab()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<trabajador> lista = new ArrayList<trabajador>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select rut, nombre from trabajador  order by nombre";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajador tr = new trabajador();
				tr.setRut(rs.getString("rut"));
				tr.setNombre(rs.getString("nombre"));
				lista.add(tr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	//trabajadores po codigo de preseleccion
	public static ArrayList<trabajadores_prese> getTrabajPrese(int pre , int entero)  throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		ArrayList<trabajadores_prese> lista = new ArrayList<trabajadores_prese>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select "
					+ "P.postulante,"
					+ "P.codigo_trabajador, "
					+ "P.codigo_peticion, "
					+ "P.id_peticion,"
					+ "P.fecha_entrevista,"
					+ "P.hora_entrevista,"
					+ "TR.nombre,"
					+ " upper((select cargos from cargos where id_cargo = PE.cargo)) as cargo,"
					+ "PE.posicion,"
					+ "PE.obra,"
					+"(SELECT " 
			                    +"CASE "
			                            +"WHEN telefono = '' THEN celular "
			                            +"ELSE telefono "
			                        +"END AS telefono "
			                +"FROM "
			                    +"trabajadores "
			                +"WHERE "
			                    +"codigo = P.codigo_trabajador) AS telefono,"
					+ "TR.email, "
					+ "P.status, P.id_rechazo,TR.id   from preseleccionados P inner join peticion_trabajador PE on PE.id_peticion = P.codigo_peticion inner join trabajadores "
					+ "TR on TR.codigo = P.codigo_trabajador where"
					+ " P.codigo_peticion = "+pre+" and P.id_peticion = "+entero+" "
					+ "and P.status = 'Preseleccionado' "
					+ "group by P.postulante,PE.cargo,P.codigo_trabajador, P.codigo_peticion, P.id_peticion, P.status,TR.nombre,PE.obra,PE.posicion,TR.telefono,TR.email, P.id_rechazo,P.fecha_entrevista, P.hora_entrevista ";	
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajadores_prese trab = new trabajadores_prese();
				
				trab.setCodigo(rs.getInt("codigo_trabajador"));
				trab.setCodigo_peticion(rs.getInt("codigo_peticion"));
				trab.setId_peticion(rs.getInt("id_peticion"));
				trab.setNombre(rs.getString("nombre"));
				trab.setEmail(rs.getString("email"));
				trab.setTelefono(rs.getString("telefono"));
				trab.setNombre_cargo(rs.getString("cargo"));
				trab.setPosicion(rs.getString("posicion"));
				trab.setId(rs.getInt("id"));
				trab.setEst_contrato(rs.getString("status"));
				trab.setId_rechazo(rs.getString("id_rechazo"));
				trab.setPostulante(rs.getInt("postulante"));
				trab.setFechaentrevista(rs.getString("fecha_entrevista"));
				trab.setHoraentrevista(rs.getString("hora_entrevista"));
				
				lista.add(trab);				
			}
			
			sql2 = "select "
					+ "P.postulante,"
					+ "P.codigo_trabajador, "
					+ "P.codigo_peticion, "
					+ "P.id_peticion,"
					+ "P.fecha_entrevista,"
					+ "P.hora_entrevista,"
					+ "TR.nombre,"
					+ "upper((select cargos from cargos where id_cargo = PE.cargo)) as cargo,"
					+ "PE.posicion,"
					+ "PE.obra,TR.telefono,"
					+ "TR.email, P.status, "
					+ "P.id_rechazo,TR.id   "
					+ "from preseleccionados P inner join peticion_trabajador PE on PE.id_peticion = P.codigo_peticion inner join sw_trabajadoresPostulante "
					+ "TR on TR.codigo = P.codigo_trabajador where"
					+ " P.codigo_peticion = "+pre+" and P.id_peticion = "+entero+" AND P.status = 'Preseleccionado' group by P.postulante,PE.cargo,P.codigo_trabajador, P.codigo_peticion, P.id_peticion, P.status,TR.nombre,PE.obra,PE.posicion,TR.telefono,TR.email, P.id_rechazo,P.fecha_entrevista, P.hora_entrevista ";	
			
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs2 = ps.executeQuery(sql2);
			while(rs2.next()){
				trabajadores_prese trab2 = new trabajadores_prese();
				
				trab2.setCodigo(rs2.getInt("codigo_trabajador"));
				trab2.setCodigo_peticion(rs2.getInt("codigo_peticion"));
				trab2.setId_peticion(rs2.getInt("id_peticion"));
				trab2.setNombre(rs2.getString("nombre"));
				trab2.setEmail(rs2.getString("email"));
				trab2.setTelefono(rs2.getString("telefono"));
				trab2.setNombre_cargo(rs2.getString("cargo"));
				trab2.setPosicion(rs2.getString("posicion"));
				trab2.setId(rs2.getInt("id"));
				trab2.setEst_contrato(rs2.getString("status"));
				trab2.setId_rechazo(rs2.getString("id_rechazo"));
				trab2.setPostulante(rs2.getInt("postulante"));
				trab2.setFechaentrevista(rs.getString("fecha_entrevista"));
				trab2.setHoraentrevista(rs.getString("hora_entrevista"));
				lista.add(trab2);				
			}
			
			
		}catch (SQLException e){
			System.out.println("Error: "+e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;		
	}
	//trabajadores supervisores
	public static ArrayList<trabajador> getsupervisores()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<trabajador> lista = new ArrayList<trabajador>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from trabajador where idPerfil = 7";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajador tr = new trabajador();
				tr.setIdTrabajador(rs.getInt("idTrabajador"));
				tr.setIdPerfil(rs.getInt("idPerfil"));
				tr.setRut(rs.getString("rut"));
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setApellidoMaterno(rs.getString("apellidoMaterno"));
				tr.setNombre(rs.getString("nombre"));
				tr.setFechaNacimiento(rs.getString("fechaNacimiento"));
				tr.setEdad(rs.getString("edad"));
				tr.setIdNacionalidad(rs.getInt("idNacionalidad"));
				tr.setIdGenero(rs.getInt("idGenero"));
				tr.setIdStatus(rs.getInt("idStatus"));
				tr.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
				tr.setIdContrato(rs.getInt("idContrato"));
				lista.add(tr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}//traer datos desde aux
	public static ArrayList<auxiliar> getAlgo() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<auxiliar> lista = new ArrayList<auxiliar>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from aux";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				auxiliar ob = new auxiliar();
				ob.setId_aux(rs.getInt("id_aux"));
				ob.setCampo(rs.getString("campo"));
				ob.setValor(rs.getInt("valor"));
				lista.add(ob);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	//todos los trabajadores
		public static ArrayList<trabajador> getalltrabaja()  throws Exception{
			PreparedStatement ps = null;
			String sql="";
			ArrayList<trabajador> lista = new ArrayList<trabajador>();
			ConnectionDB db = new ConnectionDB();
			try{
				sql = "select * from trabajador";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					trabajador tr = new trabajador();
					tr.setIdTrabajador(rs.getInt("idTrabajador"));
					tr.setIdPerfil(rs.getInt("idPerfil"));
					tr.setRut(rs.getString("rut"));
					tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
					tr.setApellidoMaterno(rs.getString("apellidoMaterno"));
					tr.setNombre(rs.getString("nombre"));
					tr.setFechaNacimiento(rs.getString("fechaNacimiento"));
					tr.setEdad(rs.getString("edad"));
					tr.setIdNacionalidad(rs.getInt("idNacionalidad"));
					tr.setIdGenero(rs.getInt("idGenero"));
					tr.setIdStatus(rs.getInt("idStatus"));
					tr.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
					tr.setIdContrato(rs.getInt("idContrato"));
					lista.add(tr);
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return lista;
		}//trabajadores por id_contrato
		public static ArrayList<trabajador> gettrabajadorPorId(String id)  throws Exception{
			PreparedStatement ps = null;
			String sql="";
			ArrayList<trabajador> lista = new ArrayList<trabajador>();
			ConnectionDB db = new ConnectionDB();
			try{
				sql = "select * from trabajador where id_contrato = '"+id+"'";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					trabajador tr = new trabajador();
					tr.setIdTrabajador(rs.getInt("idTrabajador"));
					tr.setIdPerfil(rs.getInt("idPerfil"));
					tr.setRut(rs.getString("rut"));
					tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
					tr.setApellidoMaterno(rs.getString("apellidoMaterno"));
					tr.setNombre(rs.getString("nombre"));
					tr.setFechaNacimiento(rs.getString("fechaNacimiento"));
					tr.setEdad(rs.getString("edad"));
					tr.setIdNacionalidad(rs.getInt("idNacionalidad"));
					tr.setIdGenero(rs.getInt("idGenero"));
					tr.setIdStatus(rs.getInt("idStatus"));
					tr.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
					tr.setIdContrato(rs.getInt("idContrato"));
					lista.add(tr);
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return lista;
		}
//		INSERT
		public static boolean insertTraba (trabajador trab) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try{
				sql = "INSERT INTO trabajador";
				sql+= "VALUES "
						+ "('"+trab.getIdTrabajador()+"', '"+trab.getIdPerfil()+"', '"+trab.getRut()+"', "
						+ "'"+trab.getApellidoPaterno()+"', '"+trab.getApellidoMaterno()+"', "
						+ "'"+trab.getNombre()+"', '"+trab.getFechaNacimiento()+"', "
						+ "'"+trab.getEdad()+"', '"+trab.getIdNacionalidad()+"', "
						+ "'"+trab.getIdGenero()+"', '"+trab.getIdStatus()+"', "
						+ "'"+trab.getIdEstadoCivil()+"', '"+trab.getIdContrato()+"')";
				
				ps = db.conn.prepareStatement(sql);
				ps.execute();
				return true;
			}catch (SQLException e){
				System.out.println("Error: "+ e.getMessage());
			}catch (Exception e){
				System.out.println("Error: "+ e.getMessage());
			}finally {
				ps.close();
				db.close();
			}
			return false;
		}//contratos segun id trabajador
		public static ArrayList<contrato_SW> getcontratoById(String id)  throws Exception{
			PreparedStatement ps = null;
			String sql="";
			ArrayList<contrato_SW> lista = new ArrayList<contrato_SW>();
			ConnectionDB db = new ConnectionDB();
			try{
				sql = "select * from contrato_sw where id_trabajador = '"+id+"'";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){
					contrato_SW c = new contrato_SW();
					c.setId_contrato(rs.getInt("id_contrato"));
					c.setId_trabajador(rs.getInt("id_trabajador"));
					c.setFecha_fin_contrato(rs.getString("fecha_fin_contrato"));
					c.setFecha_inicio_contrato(rs.getString("fecha_inicio_contrato"));
					c.setCargo(rs.getInt("cargo"));
					c.setPosicion(rs.getInt("posicion"));
					c.setTipo_contrato(rs.getString("tipo_contrato"));
					c.setEstado_contrato(rs.getString("estado_contrato"));
					c.setArticulo_termino_contrato(rs.getInt("articulo_termino_contrato"));
					c.setInciso_termino_contrato(rs.getInt("inciso_termino_contrato"));
					c.setLetra_termino_contrato(rs.getInt("letra_termino_contrato"));
					lista.add(c);
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return lista;
		}//update trabajador
		public static boolean updateTrabajadorPre(trabajador_pre pre) throws  Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB  db = new ConnectionDB();	
			try {
				sql = "Update trabajadores set "
					+ "nombre= '"+ pre.getNombre()+"', "
					+ "direccion='" +pre.getDireccion()+ "', email='" +pre.getEmail() + "', "
					+ "telefono='" +pre.getTelefono() + "' where codigo = '"+pre.getCodigo()+"'";  
				ps = db.conn.prepareStatement(sql);
				ps.execute();
						
				return true;
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
				e.printStackTrace();
			}catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return false;
		}
		
		//trabajadores por rut
		public static trabajador getTrabajadorById(String id)  throws Exception{
			PreparedStatement ps = null;
			String sql="";
			ConnectionDB db = new ConnectionDB();
			
			trabajador tr = new trabajador();
			
			try{
				sql = "select * from trabajador where id_trabajador = '"+id+"'";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				
				while(rs.next()){
					
					tr.setIdTrabajador(rs.getInt("idTrabajador"));
					tr.setIdPerfil(rs.getInt("idPerfil"));
					tr.setRut(rs.getString("rut"));
					tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
					tr.setApellidoMaterno(rs.getString("apellidoMaterno"));
					tr.setNombre(rs.getString("nombre"));
					tr.setFechaNacimiento(rs.getString("fechaNacimiento"));
					tr.setEdad(rs.getString("edad"));
					tr.setIdNacionalidad(rs.getInt("idNacionalidad"));
					tr.setIdGenero(rs.getInt("idGenero"));
					tr.setIdStatus(rs.getInt("idStatus"));
					tr.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
					tr.setIdContrato(rs.getInt("idContrato"));
			
				}			
			}catch (SQLException e){
				System.out.println("Error: " + e.getMessage());
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return tr;
		}
		
		
//		public static ArrayList<movimiento> getMovimientosByIdTrabajador(String id) throws Exception {
//
//			PreparedStatement ps = null;
//			String sql="";
//			ConnectionDB db = new ConnectionDB();
//			
//			ArrayList<movimiento> movimientosList = new ArrayList<movimiento>();
//			
//			try {
//			
//				sql = "select * from movimientos where id_trabajador = '"+id+"'";
//				ps = db.conn.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery(sql);
//				
//				while(rs.next()){
//					
//					movimiento mv = new movimiento();
//					
//					mv.setFecha_inicio(rs.getString("fecha_inicio"));
//					mv.setId_movimientos(rs.getInt("id_movimientos"));
//					mv.setId_tiposmovimientos(rs.getInt("id_tiposmovimientos"));
//					mv.setId_trabajador(rs.getInt("id_trabajador"));
//					
//					movimientosList.add(mv);
//			
//				}	
//				
//				
//			} catch (Exception e) {
//				System.out.println("Error: " + e.getMessage());
//			} finally {
//				ps.close();
//				db.close();
//			}
//			
//			
//			
//			return movimientosList;
//		}
		
	//get Contrato por Id
	public static contrato_SW getContratoById(String id) throws Exception {
	
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		contrato_SW contrato = new contrato_SW();
		
		
		try {
			
			sql = "select * from contrato_sw where id_contrato = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				
				contrato.setId_contrato(rs.getInt("id_contrato"));
				contrato.setId_trabajador(rs.getInt("id_trabajador"));
				contrato.setFecha_inicio_contrato(rs.getString("fecha_inicio_contrato"));
				contrato.setFecha_fin_contrato(rs.getString("fecha_fin_contrato"));
				contrato.setCargo(rs.getInt("cargo"));
				contrato.setPosicion(rs.getInt("posicion"));
				contrato.setTipo_contrato(rs.getString("tipo_contrato"));
				contrato.setEstado_contrato(rs.getString("estado_contrato"));
				contrato.setArticulo_termino_contrato(rs.getInt("articulo_termino_contrato"));
				contrato.setInciso_termino_contrato(rs.getInt("inciso_termino_contrato"));
				contrato.setLetra_termino_contrato(rs.getInt("letra_termino_contrato"));
			}
			
			
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		
		
		return contrato;
	}
	
	//Update Contrato
	public static boolean updateContrato(contrato_SW contrato) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try {
			sql = "UPDATE contrato_sw set " 
				+ " id_trabajador='"             + contrato.getId_trabajador()+"', "
				+ " fecha_inicio_contrato='"     + contrato.getFecha_inicio_contrato()+"', "
				+ " fecha_fin_contrato='"        + contrato.getFecha_fin_contrato()+"', "
				+ " cargo='"                     + contrato.getCargo()+"', "
				+ " posicion='"                  + contrato.getPosicion()+"', "
				+ " tipo_contrato='"             + contrato.getTipo_contrato()+"', "
				+ " estado_contrato='"           + contrato.getEstado_contrato()+"', "
				+ " articulo_termino_contrato='" + contrato.getArticulo_termino_contrato()+"', "
				+ " inciso_termino_contrato='"   + contrato.getInciso_termino_contrato()+"', "
				+ " letra_termino_contrato='"    + contrato.getLetra_termino_contrato()+"' "
			
				+ " where id_contrato='" 		 +contrato.getId_contrato()+ "'";
			
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;				
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		
		return false;
	}
	
	//Insertar Contrato
	public static boolean insertContrato(contrato_SW contrato) throws Exception {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql = "INSERT INTO contrato_sw (id_contrato,id_trabajador,fecha_inicio_contrato,fecha_fin_contrato,cargo,posicion,tipo_contrato,estado_contrato,articulo_termino_contrato,inciso_termino_contrato,letra_termino_contrato) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt   (1,  contrato.getId_contrato());
			ps.setInt   (2,  contrato.getId_trabajador());
			ps.setString(3,  contrato.getFecha_inicio_contrato());
			ps.setString(4,  contrato.getFecha_fin_contrato());
			ps.setInt   (5,  contrato.getCargo());
			ps.setInt   (6,  contrato.getPosicion());
			ps.setString(7,  contrato.getTipo_contrato());
			ps.setString(8,  contrato.getEstado_contrato());
			ps.setInt   (9,  contrato.getArticulo_termino_contrato());
			ps.setInt   (10, contrato.getInciso_termino_contrato());
			ps.setInt   (11, contrato.getLetra_termino_contrato());
			
			ps.execute();
			
			return true;
			
		}catch(Exception e){
			System.out.println("Error: " +e.getMessage());
			return false;
		}finally{
			
			ps.close();
			db.conn.close();
			
		}
		

	}
	
	public static String PreseleccionSimple (lib.classSW.PreseleccionSimple r) throws Exception{
		Statement ps = null;
		Statement ps2 = null;
		Statement ps3 = null;
		Statement ps4 = null;
		String sql="", sql2 = "", sql3 = "",sql4 = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql4 = "select * from preseleccionados where codigo_trabajador = "+r.getCodigoTrabajador()+" and codigo_peticion = "+r.getId_peticion()+" ";
			ps4 = db.conn.prepareStatement(sql4);
			System.out.println(sql4);
			ResultSet rs4 = ps4.executeQuery(sql4);
			
			if (!rs4.isBeforeFirst()) {
				
				sql = "INSERT INTO preseleccionados (codigo_trabajador, codigo_peticion, status, id_peticion,postulante)";
				sql+= "VALUES ('"+r.getCodigoTrabajador()+"','"+r.getId_reclutamiento()+"','Preseleccionado',"+r.getId_peticion()+","+r.getPostulante()+");";
								
				System.out.println(sql);
				
				
				ps = db.conn.prepareStatement(sql);
				ps.execute(sql);
				
				sql3 = "select * from trabajadores where codigo = "+r.getCodigoTrabajador()+" ";
				
				
				
				ps3 = db.conn.prepareStatement(sql3);
				
				ResultSet rs3 = ps3.executeQuery(sql3);
				
				if (!rs3.isBeforeFirst()) {
					sql2 = "UPDATE sw_trabajadoresPostulante SET" + " id_pet_tbl_PT='" + r.getId_peticion() + "', "
							+ " est_contrato='Preseleccionado'," + " estado_preselec = 1" + " WHERE codigo ='"
							+ r.getCodigoTrabajador() + "'";
					System.out.println(sql2);
					ps2 = db.conn.prepareStatement(sql2);
					ps2.execute(sql2);

				} else {
					sql2 = "UPDATE trabajadores SET" + " id_pet_tbl_PT='" + r.getId_peticion() + "', "
							+ " est_contrato='Preseleccionado'," + " estado_preselec = 1" + " WHERE codigo ='"
							+ r.getCodigoTrabajador() + "'";
					System.out.println(sql2);
					ps2 = db.conn.prepareStatement(sql2);
					ps2.execute(sql2);
				}  
				ps.close();
				ps2.close();
				
				return "Trabajador Preseleccionado con exito ";
				
			}else{
				return "Trabajador "+r.getApellidoNombre()+" COD: "+r.getCodigoTrabajador()+" ya Se Encuentra Asignado A Esta Petición";
			}
			
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			
			db.close();
		}
		return "no";
	}
	
	
	public static String PreseleccionSimple2 (lib.classSW.PreseleccionSimple r) throws Exception{
		Statement ps = null;
		Statement ps2 = null;
		Statement ps3 = null;
		Statement ps4 = null;
		String sql="", sql2 = "", sql3 = "",sql4 = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql4 = "select * from preseleccionados where codigo_trabajador = "+r.getCodigoTrabajador()+" and id_peticion = "+r.getId_peticion()+" and codigo_peticion = "+r.getId_reclutamiento()+" and status ='Confirmado'";
			ps4 = db.conn.prepareStatement(sql4);
			System.out.println(sql4);
			ResultSet rs4 = ps4.executeQuery(sql4);
			
			if (!rs4.isBeforeFirst()) {
				
				sql = "INSERT INTO preseleccionados (codigo_trabajador, codigo_peticion, status, id_peticion,postulante)";
				sql+= "VALUES ('"+r.getCodigoTrabajador()+"','"+r.getId_reclutamiento()+"','Confirmado',"+r.getId_peticion()+","+r.getPostulante()+");";
								
				System.out.println(sql);
				
				
				ps = db.conn.prepareStatement(sql);
				ps.execute(sql);
				
				sql3 = "select * from trabajadores where codigo = "+r.getCodigoTrabajador()+" ";
				
				
				
				ps3 = db.conn.prepareStatement(sql3);
				
				ResultSet rs3 = ps3.executeQuery(sql3);
				
				if (!rs3.isBeforeFirst()) {
					sql2 = "UPDATE sw_trabajadoresPostulante SET" + " id_pet_tbl_PT='" + r.getId_peticion() + "', "
							+ " est_contrato='Preseleccionado'," + " estado_preselec = 1" + " WHERE codigo ='"
							+ r.getCodigoTrabajador() + "'";
					System.out.println(sql2);
					ps2 = db.conn.prepareStatement(sql2);
					ps2.execute(sql2);

				} else {
					sql2 = "UPDATE trabajadores SET" + " id_pet_tbl_PT='" + r.getId_peticion() + "', "
							+ " est_contrato='Preseleccionado'," + " estado_preselec = 1" + " WHERE codigo ='"
							+ r.getCodigoTrabajador() + "'";
					System.out.println(sql2);
					ps2 = db.conn.prepareStatement(sql2);
					ps2.execute(sql2);
				}  
				ps.close();
				ps2.close();
				
				return "Trabajador Confirmado con exito ";
				
			}else{
				return "Trabajador "+r.getApellidoNombre()+" COD: "+r.getCodigoTrabajador()+" ya Se Encuentra Asignado A Esta Petición";
			}
			
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			
			db.close();
		}
		return "no";
	}
	
	public static String PreseleccionSimple3 (lib.classSW.PreseleccionSimple r) throws Exception{
		Statement ps = null;
		Statement ps2 = null;
		Statement ps3 = null;
		Statement ps4 = null;
		String sql="", sql2 = "", sql3 = "",sql4 = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql4 = "select * from preseleccionados where codigo_trabajador = "+r.getCodigoTrabajador()+" and id_peticion = "+r.getId_peticion()+" and codigo_peticion = "+r.getId_reclutamiento()+"";
			ps4 = db.conn.prepareStatement(sql4);
			System.out.println(sql4);
			ResultSet rs4 = ps4.executeQuery(sql4);
			
			if (rs4.isBeforeFirst()) {
				
				sql = "UPDATE preseleccionados SET status='Confirmado2',fecha_entrevista='"+r.getFechaEntreista()+"',hora_entrevista='"+r.getHoraEntrevista()+"' WHERE codigo_trabajador = "+r.getCodigoTrabajador()+" and codigo_peticion = "+r.getId_reclutamiento()+" and id_peticion = "+r.getId_peticion()+" ";
				ps = db.conn.prepareStatement(sql);
				ps.execute(sql);
				
				sql3 = "select * from trabajadores where codigo = "+r.getCodigoTrabajador()+" ";
				ps3 = db.conn.prepareStatement(sql3);
				
				ResultSet rs3 = ps3.executeQuery(sql3);
				
				if (!rs3.isBeforeFirst()) {
					sql2 = "UPDATE sw_trabajadoresPostulante SET" + " id_pet_tbl_PT='" + r.getId_peticion() + "', "
							+ " est_contrato='Preseleccionado'," + " estado_preselec = 1" + " WHERE codigo ='"
							+ r.getCodigoTrabajador() + "'";
					ps2 = db.conn.prepareStatement(sql2);
					ps2.execute(sql2);

				} else {
					sql2 = "UPDATE trabajadores SET" + " id_pet_tbl_PT='" + r.getId_peticion() + "', "
							+ " est_contrato='Preseleccionado'," + " estado_preselec = 1" + " WHERE codigo ='"
							+ r.getCodigoTrabajador() + "'";
					ps2 = db.conn.prepareStatement(sql2);
					ps2.execute(sql2);
				}  
				ps.close();
				ps2.close();
				
				return "Trabajador Confirmado con exito ";
				
			}else{
				return "Trabajador "+r.getApellidoNombre()+" COD: "+r.getCodigoTrabajador()+" ya Se Encuentra Asignado A Esta Petición";
			}
			
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			
			db.close();
		}
		return "no";
	}
	
	public static String PreseleccionSimple4 (lib.classSW.PreseleccionSimple r) throws Exception{
		Statement ps = null;
		Statement ps2 = null;
		Statement ps3 = null;
		Statement ps4 = null;
		String sql="", sql2 = "", sql3 = "",sql4 = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			
			sql4 = "select * from preseleccionados where codigo_trabajador = "+r.getCodigoTrabajador()+" and id_peticion = "+r.getId_peticion()+" and codigo_peticion = "+r.getId_reclutamiento()+" and status='Confirmado3'";
			ps4 = db.conn.prepareStatement(sql4);
			System.out.println(sql4);
			ResultSet rs4 = ps4.executeQuery(sql4);
			
			if (!rs4.isBeforeFirst()) {
				
				sql = "UPDATE preseleccionados SET status='Preseleccionado',fecha_entrevista='"+r.getFechaEntreista()+"',hora_entrevista='"+r.getHoraEntrevista()+"' WHERE codigo_trabajador = "+r.getCodigoTrabajador()+" and codigo_peticion = "+r.getId_reclutamiento()+" and id_peticion = "+r.getId_peticion()+" ";
				System.out.println(sql);
				
				
				ps = db.conn.prepareStatement(sql);
				ps.execute(sql);
				
				sql3 = "select * from trabajadores where codigo = "+r.getCodigoTrabajador()+" ";
				
				
				
				ps3 = db.conn.prepareStatement(sql3);
				
				ResultSet rs3 = ps3.executeQuery(sql3);
				
				if (!rs3.isBeforeFirst()) {
					sql2 = "UPDATE sw_trabajadoresPostulante SET" + " id_pet_tbl_PT='" + r.getId_peticion() + "', "
							+ " est_contrato='Preseleccionado'," + " estado_preselec = 1" + " WHERE codigo ='"
							+ r.getCodigoTrabajador() + "'";
					System.out.println(sql2);
					ps2 = db.conn.prepareStatement(sql2);
					ps2.execute(sql2);

				} else {
					sql2 = "UPDATE trabajadores SET" + " id_pet_tbl_PT='" + r.getId_peticion() + "', "
							+ " est_contrato='Preseleccionado'," + " estado_preselec = 1" + " WHERE codigo ='"
							+ r.getCodigoTrabajador() + "'";
					System.out.println(sql2);
					ps2 = db.conn.prepareStatement(sql2);
					ps2.execute(sql2);
				}  
				ps.close();
				ps2.close();
				
				return "Trabajador Preseleccionado con exito ";
				
			}else{
				return "Trabajador "+r.getApellidoNombre()+" COD: "+r.getCodigoTrabajador()+" ya Se Encuentra Asignado A Esta Petición";
			}
			
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			
			db.close();
		}
		return "no";
	}
	
	
	// rechazo Preseleccionado
	public static boolean rechazoPre (RechazoPreseleccionado r) throws Exception{
      	Statement ps = null;
		Statement ps2 = null;
		String sql = "";
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
	
		try{
	
		
			sql = "UPDATE trabajadores SET" 
					+ " estado_preselec='0'"
					+ " WHERE codigo ='"+r.getCodigo()+ "'";
			
			
			sql2 = "UPDATE preseleccionados SET status = 'Rechazado', id_rechazo = '"+r.getId_rechazo()+"', observacion = '"+r.getObservacion()+"' WHERE codigo_trabajador = "+r.getCodigo()+" AND codigo_peticion = " +r.getId_peticion()+ " and id_peticion = "+r.getCodigo_peticion()+"";

     		ps = db.conn.prepareStatement(sql);
			ps2 = db .conn.prepareStatement(sql2);

			
			
     		ps.execute(sql);
			ps2.execute(sql2);

			
			return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
			ps2.close();

			db.close();
		}
		return false;
	}
	
	// Seleccionar trabajador
	
	public static boolean seleccionPre (seleccionados r) throws Exception{

		Statement ps2 = null;

		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
	
		try{
			sql2 = "UPDATE preseleccionados SET status = 'Seleccionado',fecha_inicio = '"+r.getFechaInicio()+"' "
					+ "WHERE codigo_trabajador = "+r.getCodigo_trabajador()+" and codigo_peticion = "+r.getId_peticion()+ " and id_peticion = "+r.getCodigo_peticion()+" ";
			
			System.out.println(sql2);
			ps2 = db .conn.prepareStatement(sql2);
			ps2.execute(sql2);
			
			return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps2.close();
			db.close();
		}
		return false;
	}
	
	
	public static boolean insertReclu2 (reclutamiento r) throws Exception{
		Statement ps2 = null;
		String sql2 = "";
		
		ConnectionDB db = new ConnectionDB();
		try{
		
		sql2 = "INSERT INTO peticion_trabajador (cargo, posicion, obra, cantidad, fecha_inicio, id_reclutamiento,cantidad_Hombre,cantidad_Mujer)";
		sql2+= "VALUES ('"+r.getCargo()+"','"+r.getPosicion()+"','"+r.getObra()+"',"+r.getCantidad()+",'"+r.getFecha_estimada()+"', (select max(id_reclutamiento) from reclutamiento_c),"+r.getCantidadh()+","+r.getCantidadm()+");";
				
		
		ps2 = db .conn.prepareStatement(sql2);
		ps2.execute(sql2);
		
	return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
		
			ps2.close();
			db.close();
		}
		return false;
	}//return cargos
	
	//		INSERT reclutamiento
	public static boolean insertReclu (reclutamiento r) throws Exception{
		Statement ps = null;
	
		String sql="";
		ConnectionDB db = new ConnectionDB();
		
		try{
					
			sql = "INSERT INTO reclutamiento_c (usuario,empresa,cantidad,estado,fecha_now,huerto,zona,idCECOContrato,obrafaena)";
			sql += "VALUES ('"+r.getUsuario()+"',(select denominacionSociedad from sociedad where idSociedad = "+r.getEmpresa()+"),"+r.getCantidad()+",1,"
					+ "NOW(),'"+r.getHuerto()+"','"+r.getZona()+"','"+r.getCeco()+"',"+r.getObrafaena()+")";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			
			
			ps.execute(sql);
		
			
			return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
		
			db.close();
		}
		return false;
	}//return cargos
	public static ArrayList<Cargo> getCargos(int sociedad)  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Cargo> lista = new ArrayList<Cargo>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from cargos where sociedad = (select sociedad from sociedad where idSociedad = "+sociedad+")";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Cargo cr = new Cargo();
				cr.setId_cargo(rs.getInt("id_cargo"));
				cr.setCargos(rs.getString("cargos"));
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}//return posiciones
	public static ArrayList<posiciones> getPosiciones()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<posiciones> lista = new ArrayList<posiciones>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from posiciones";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				posiciones cr = new posiciones();
				cr.setId_posicion(rs.getInt("id_posicion"));
				cr.setPosicion(rs.getString("posicion"));
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	
	
	public static ArrayList<ListaSociedad> getSociedad()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<ListaSociedad> lista = new ArrayList<ListaSociedad>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from sociedad";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				ListaSociedad cr = new ListaSociedad();
				cr.setSociedad(rs.getString("sociedad"));
				cr.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				cr.setIdSociedad(rs.getInt("idSociedad"));
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	// cargar notificacion contrato
	public static ArrayList<NotificacionContrato> ListaNotificacionContrato()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<NotificacionContrato> lista = new ArrayList<NotificacionContrato>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = " select count(P.codigo_peticion) as requeridos,P.codigo_peticion,P.id_peticion,R.empresa,R.usuario from preseleccionados P left join reclutamiento_c R on  R.id_reclutamiento = P.id_peticion where status = 'Seleccionado'group by P.codigo_peticion,P.codigo_peticion,P.id_peticion,R.empresa,R.usuario order by P.codigo_peticion desc";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				NotificacionContrato cr = new NotificacionContrato();
				cr.setRequeridos(rs.getInt("requeridos"));
				cr.setId_peticion(rs.getInt("id_peticion"));
				cr.setCodigo_peticion(rs.getInt("codigo_peticion"));
				cr.setUsuario(rs.getString("usuario"));
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	public static ArrayList<listaRechazo> getRechazos()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<listaRechazo> lista = new ArrayList<listaRechazo>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from lista_rechazo";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				listaRechazo cr = new listaRechazo();
				cr.setId_lista(rs.getInt("id_lista"));
				cr.setNombre_lista(rs.getString("nombre_lista"));
				lista.add(cr);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	// cargar trabajadores seleccionados en pantalla de contratacion
	public static ArrayList<LoadContratacion> LoadSeleccContratacion(int id_peticion,int cod_peticion ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadContratacion> data = new ArrayList<LoadContratacion>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select distinct  "
					+ "P.codigo_trabajador, P.postulante,"
					// maquinista
					+"if( "
					    	+"(select maquinista from contratos where codigo_trabajador =   P.codigo_trabajador "
					        +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					        +"is null,"
					        // si
					        +"(select maquinista from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador),"
					        // no
					        +"(select maquinista from contratos where codigo_trabajador =  P.codigo_trabajador "
							+"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					    +") as maquinista," 
					    // supervisor    
					+"if( "
					        +"(select supervisor from contratos where codigo_trabajador =   P.codigo_trabajador "
					        +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					        +"is null,"
					        // si
					        +"(select supervisor from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador ),"
					        // no
					        +"(select supervisor from contratos where codigo_trabajador =  P.codigo_trabajador "
							+"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					    +") as supervisor,"
					    // partTime   
					 +"if( "
					        +"(select partTime from contratos where codigo_trabajador =   P.codigo_trabajador "
					        +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					        +"is null,"
					        // si
					        +"(select partTime from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador),"
					        // no
					        +"(select partTime from contratos where codigo_trabajador =  P.codigo_trabajador "
							+"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					      +") as partTime," 
					//nombre tipo trabajador
					+"if( "
					        +"(select tipoTrabajador from contratos where codigo_trabajador =   P.codigo_trabajador "
					        +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					        +"is null,"
					        // si 
					        +"(select descripcion from parametros where codigo = 'TIPO_TRABAJADOR' and llave = (select tipoTrabajador from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador )),"
					        // no  
					        +"(select descripcion from parametros where codigo = 'TIPO_TRABAJADOR' and llave = "
					        +"(select tipoTrabajador from contratos where codigo_trabajador =  P.codigo_trabajador "
							+"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")))"
					   +") as nombretipoTrabajador,"  
					   // id tipo trabajador     
					+"if( "
					        +"(select tipoTrabajador from contratos where codigo_trabajador =   P.codigo_trabajador "
					        +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+"))"
					        +"is null,"
					        // si
					        +"(select tipoTrabajador from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador ),"
					        // no
					       
					        +"(select tipoTrabajador from contratos where codigo_trabajador =  P.codigo_trabajador "
							+"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					    +") as tipoTrabajador, " 
					// nombre tipo contrato
					+"if( "
					    	+"(select tipoContrato from contratos where codigo_trabajador =   P.codigo_trabajador "
					        +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					        +"is null,"
					        // si
					        +"(select descripcion from parametros where codigo = 'TIPO_CONTRATO' and llave = (select tipoContrato from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador )),"
					        // no 
					        +"(select descripcion from parametros where codigo = 'TIPO_CONTRATO' and llave = "
					        +"(select tipoContrato from contratos where codigo_trabajador =  P.codigo_trabajador "
							+"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+"))) "
					    +") as nombetipo_contrato," 
					// id tipo contrato
					+"if( "
					    	+"(select tipoContrato from contratos where codigo_trabajador =   P.codigo_trabajador "
					        +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+") ) "
					        +"is null,"
					        // si
					        +"(select tipoContrato from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador ),"
					        // no
					        +"(select tipoContrato from contratos where codigo_trabajador =  P.codigo_trabajador "
							+"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					     +") as tipo_contrato,"
					  // id tipo contrato
					// horas semanales
					+"if( "
						   +"( "
						        +"select horasSemanales from contratos where codigo_trabajador =  P.codigo_trabajador "
						        +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+") "
						        +"and id = (select max(id) from contratos where codigo_trabajador = P.codigo_trabajador and  cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+") "
						    +"))"
						    +"is null, "
						    // si
						    +"(select horasSemanales from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador ),"
						    // no     
						    +"("
						    	+"select horasSemanales from contratos where codigo_trabajador =  P.codigo_trabajador "
						    	+"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+") "
						    	+" and id = (select max(id) from contratos where codigo_trabajador = P.codigo_trabajador and  cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+") "
						     +") )"
						+") as horasSemanales," 
				    // end // horas semanales
					+"if( "
					       +"(select sueldoBase from contratos where codigo_trabajador =  P.codigo_trabajador "
					         +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+") "
					         + "and id = "
					         + "(select max(id) from contratos where codigo_trabajador = P.codigo_trabajador and  cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					         + ") "
					         +"is null,"
					         +"(select sueldoBase from sw_trabajadoresPostulante where codigo =  P.codigo_trabajador ),"
					         +"(select sueldoBase from contratos where codigo_trabajador =  P.codigo_trabajador "
					         +"and cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+") "
					         + "and id = "
					         + "(select max(id) from contratos where codigo_trabajador = P.codigo_trabajador and  cargo = (SELECT cargo FROM peticion_trabajador WHERE   id_peticion = "+id_peticion+")) "
					         + ") "
					   +") as sueldoBase,"
					+"IF((SELECT "
			                +"UPPER(CONCAT(apellidoPaterno,' ',apellidoMaterno,' ', nombre))  FROM trabajadores WHERE codigo = codigo_trabajador) "
			        +"IS NULL,"                 
			        +"(SELECT UPPER(CONCAT(apellidoPaterno, ' ',  apellidoMaterno,  ' ',  nombre)) FROM sw_trabajadoresPostulante WHERE  codigo = codigo_trabajador),"
			        +"(SELECT UPPER(CONCAT(apellidoPaterno, ' ', apellidoMaterno,' ', nombre)) FROM trabajadores  WHERE codigo = codigo_trabajador)) "
			        +"AS nombre,"
					+ "(select cargos from cargos where id_cargo = (select cargo from peticion_trabajador where id_peticion = "+id_peticion+")) as cargo,"
					+ "(select posicion from peticion_trabajador where id_peticion = "+id_peticion+") as posicion,"
					+ "(select idSociedad from sociedad where denominacionSociedad = (select empresa from reclutamiento_c where id_reclutamiento = (select id_reclutamiento from peticion_trabajador where id_peticion = "+cod_peticion+"))) as idsociedad,"
					+ "(select cargo from peticion_trabajador where id_peticion = "+id_peticion+") as idcargo,"
					+ "(SELECT huerto FROM reclutamiento_c WHERE id_reclutamiento = (SELECT id_reclutamiento FROM peticion_trabajador WHERE id_peticion = "+id_peticion+")) as huerto,"
					+ "(SELECT zona FROM  reclutamiento_c WHERE id_reclutamiento = (SELECT id_reclutamiento FROM peticion_trabajador  WHERE id_peticion = "+id_peticion+")) as zona,"
					+ "(SELECT obrafaena FROM reclutamiento_c WHERE id_reclutamiento = (SELECT id_reclutamiento FROM peticion_trabajador WHERE id_peticion = "+id_peticion+")) as obrafaena,"
					+ "(SELECT  idCECOContrato FROM  reclutamiento_c WHERE id_reclutamiento = (SELECT id_reclutamiento  FROM peticion_trabajador WHERE id_peticion = "+id_peticion+")) AS idcecocontrato,"
					+ "  (select id_posicion from posiciones where posicion = (SELECT posicion  FROM peticion_trabajador WHERE id_peticion = "+id_peticion+")) as idposicion, "
					+"if( (SELECT  telefono  FROM  trabajadores WHERE   codigo = codigo_trabajador),"
					+"(SELECT   telefono FROM sw_trabajadoresPostulante WHERE codigo = codigo_trabajador),"         
					+"(SELECT  telefono FROM trabajadores WHERE  codigo = codigo_trabajador)) "
					+"AS telefono,"  
					+ "P.fecha_inicio,P.id_peticion,P.codigo_peticion, P.postulante,(select id from trabajadores where codigo = P.codigo_trabajador group by id) as id from preseleccionados P where P.codigo_peticion = "+cod_peticion+" and P.id_peticion = "+id_peticion+" and P.status = 'Seleccionado' GROUP BY P.codigo_trabajador,P.fecha_inicio,P.postulante";
			
			
			System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				LoadContratacion e = new LoadContratacion();
				e.setCodigo(rs.getInt("codigo_trabajador"));
				e.setNombre(rs.getString("nombre"));
				e.setPosicion(rs.getString("posicion"));
				e.setFecha_inicio(rs.getString("fecha_inicio"));
				e.setCargo(rs.getString("cargo"));
				e.setTelefono(rs.getString("telefono"));
				e.setId(rs.getInt("id"));
				e.setIdSociedad(rs.getInt("idsociedad"));
				e.setIdcargo(rs.getInt("idcargo"));
				e.setIdHuertoContrato(rs.getString("huerto"));
				e.setZona(rs.getString("zona"));
				e.setIdCECOContrato(rs.getString("idcecocontrato"));
				e.setIdFaenaContrato(rs.getInt("obrafaena"));
				e.setIdposicion(rs.getInt("idposicion"));
				e.setSueldo_base(rs.getInt("sueldoBase"));
				e.setHoras_semanales(rs.getInt("horasSemanales"));
				e.setId_tipocontrato(rs.getInt("tipo_contrato"));
				e.setNombretipocontrato(rs.getString("nombetipo_contrato"));
				e.setNombretipotrabajador(rs.getString("nombretipoTrabajador"));
				e.setIdtipotrabajador(rs.getInt("tipoTrabajador"));
				e.setMaquinista(rs.getInt("maquinista"));
				e.setPartTime(rs.getInt("partTime"));
				e.setSupervisor(rs.getInt("supervisor"));
				e.setPostulante(rs.getInt("postulante"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	
	
	// cargar select cargo pantalla preseleccion
	public static ArrayList<LoadCargoPreseleccion> getCargoPreseleccion(int entero ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadCargoPreseleccion> data = new ArrayList<LoadCargoPreseleccion>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select "
					+ "P.cargo as idcargo,"
					+ "(SELECT cargos FROM cargos WHERE id_cargo = P.cargo) AS cargo,"
					+ "P.posicion, P.id_peticion,R.usuario "
					+ "from reclutamiento_c R "
					+ "join peticion_trabajador P on R.id_reclutamiento = P.id_reclutamiento where P.id_reclutamiento = "+entero+" and estado_peticion = 1 group by P.cargo, P.id_peticion,P.posicion";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				LoadCargoPreseleccion e = new LoadCargoPreseleccion();
				e.setCargo(rs.getString("cargo"));
				e.setId_peticion(rs.getInt("id_peticion"));
				e.setUsuario(rs.getString("usuario"));
				e.setPosicion(rs.getString("posicion"));
				e.setIdcargo(rs.getString("idcargo"));
				
				
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	
	public static ArrayList<LoadCargoPreseleccion> getCriteriopreseleccion() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LoadCargoPreseleccion> data = new ArrayList<LoadCargoPreseleccion>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select * from criterio_preseleccion";
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				LoadCargoPreseleccion e = new LoadCargoPreseleccion();
				e.setId(rs.getInt("id"));
				e.setDescripcion(rs.getString("descripcion"));
				
				
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	
	public static ArrayList<PreseleccionDetalle> LoadNSolicitud(int entero ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<PreseleccionDetalle> data = new ArrayList<PreseleccionDetalle>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select P.id_reclutamiento, sum(P.cantidad) as cantidad, "
					+ "(select faena from faena where codigo = P.obra) as obra,"
					+ "P.fecha_inicio,R.fecha_now from peticion_trabajador P join reclutamiento_c R on P.id_reclutamiento = R.id_reclutamiento where P.id_reclutamiento = "+entero+" group by P.obra,P.id_reclutamiento,P.fecha_inicio";
			
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				PreseleccionDetalle e = new PreseleccionDetalle();
				e.setId_reclutamiento(rs.getInt("id_reclutamiento"));
				e.setCantidad(rs.getInt("cantidad"));
				e.setObra(rs.getString("obra"));
				e.setFecha_inicio(rs.getString("fecha_inicio"));
				e.setFecha_now(rs.getString("fecha_now"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	
	
	public static ArrayList<PreseleccionDetalleVer> PreseleccionDetalleVerMasLista(int entero ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<PreseleccionDetalleVer> data = new ArrayList<PreseleccionDetalleVer>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql =" select P.id_reclutamiento, sum(P.cantidad) as cantidad_total, "
					+ "P.obra as faena, P.fecha_inicio,R.usuario, "
					+ "(select count(*) from preseleccionados where codigo_peticion = P.id_reclutamiento and status = 'Seleccionado') as seleccionado, "
					+ "(select count(*) from preseleccionados where codigo_peticion = P.id_reclutamiento and status = 'Preseleccionado') as preseleccionado, (select count(*) from preseleccionados where codigo_peticion = P.id_reclutamiento and status = 'Contratado') as con, sum(P.cantidad) -"
					+ " (select sum((SELECT count(status) from preseleccionados where status = 'Contratado' and id_peticion =  "
					+ " P.id_reclutamiento and codigo_peticion = P.id_peticion))) as saldo "
					+ "from peticion_trabajador P join reclutamiento_c R on P.id_reclutamiento = R.id_reclutamiento "
					+ "where R.estado = 1 and P.id_reclutamiento = "+entero+" group by P.obra,P.id_reclutamiento,P.fecha_inicio ";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				PreseleccionDetalleVer e = new PreseleccionDetalleVer();
				e.setId_lista(rs.getInt("id_reclutamiento"));
				e.setCantidad(rs.getInt("cantidad_total"));
				e.setObra(rs.getString("faena"));
				e.setUsuario(rs.getString("usuario"));
				e.setSaldo(rs.getInt("saldo"));
				e.setSeleccionado(rs.getInt("seleccionado"));
				e.setPreseleccionado(rs.getInt("preseleccionado"));
				
				
				

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	
	// Preseleccion boton ver mas
	public static ArrayList<PreseleccionDetalleVer> PreseleccionDetalleVerMas(int entero ) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<PreseleccionDetalleVer> data = new ArrayList<PreseleccionDetalleVer>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select  P.id_peticion AS id_lista ,"
					+ "(SELECT cargos FROM cargos WHERE id_cargo = P.cargo) AS cargo,"
					+ "(select count(status) from preseleccionados  "
					+ "where status = 'Preseleccionado' and  id_peticion = id_lista ) as preseleccionado,"
					+ "( select count(status) from preseleccionados  "
					+ "where status = 'Seleccionado' and  id_peticion = id_lista ) as seleccionado, "
					+ "P.posicion,P.obra, P.cantidad,"
					+ "DATE_FORMAT(P.fecha_inicio, '%d-%m-%Y') AS fecha_inicio, "
					+ "(P.cantidad) -(select sum((SELECT count(status) "
					+ "from preseleccionados  "
					+ "where status = 'Contratado' and id_peticion =  P.id_peticion "
					+ "and codigo_peticion = "+entero+")) ) as saldo from reclutamiento_c R "
							+ "join peticion_trabajador P on R.id_reclutamiento = P.id_reclutamiento "
							+ "where P.id_reclutamiento = "+entero+" group by P.id_peticion,P.cargo, "
									+ "P.posicion,P.obra, P.cantidad,P.fecha_inicio,id_lista";
			
			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				PreseleccionDetalleVer e = new PreseleccionDetalleVer();
				e.setId_lista(rs.getInt("id_lista"));
				e.setCargo(rs.getString("cargo"));
				e.setObra(rs.getString("obra"));
				e.setCantidad(rs.getInt("cantidad"));
				e.setFecha_inicio(rs.getString("fecha_inicio"));
				e.setPosicion(rs.getString("posicion"));
				e.setSaldo(rs.getInt("saldo"));
				e.setSeleccionado(rs.getInt("seleccionado"));
				e.setPreseleccionado(rs.getInt("preseleccionado"));

				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
	public static boolean UpdateReclutamiento(UpdateEstadoReclutamiento map) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "UPDATE reclutamiento_c SET observacion = ?, estado=0 WHERE id_reclutamiento= ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, map.getObservacion());
			ps.setInt(2, map.getId_reclutamiento());
			
			
			ps.execute();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static ArrayList<trabajadores> loadPersonal(int idcargo,int idpeticion) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		
		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT "
						    +"* "
						+"FROM "
						    +"(SELECT "
						       +"id AS id2,"
						            +"codigo_trabajador AS codigo,"
						            +"(SELECT "
						                    +"EstadoContrato "
						                +"FROM "
						                    +"contratos co5 "
						                +"WHERE "
						                    +"co5.id = id2) AS estadocontrato,"
						            +"(SELECT "
						                    +"FechaTerminoContrato "
						                +"FROM "
						                    +"contratos co1 "
						                +"WHERE "
						                    +"co1.id = id2) AS f_termino,"
						            +" "+idcargo+" AS idcargo,"
						            +"(SELECT "
						                    +"cargos "
						                +"FROM "
						                    +"cargos "
						                +"WHERE "
						                    +"id_cargo = "+idcargo+") AS cargo,"
						            +"(SELECT "
						                    +"CASE "
						                            +"WHEN rut = '' THEN rutTemporal "
						                            +"ELSE rut "
						                        +"END AS rut "
						                +"FROM "
						                    +"trabajadores "
						                +"WHERE "
						                    +"codigo = codigo_trabajador) AS rut,"
						            +"(SELECT "
						                    +"UPPER(CONCAT(apellidoPaterno, ' ', apellidoMaterno, ' ', nombre)) AS nombre "
						                +"FROM "
						                    +"trabajadores "
						                +"WHERE "
						                    +"codigo = codigo_trabajador) AS nombre,"
						            +"(SELECT "
						                    +"CONCAT(calle, ' ', ndireccion, ' ', poblacion) "
						                +"FROM "
						                    +"trabajadores "
						                +"WHERE "
						                    +"codigo = codigo_trabajador) AS direccion,"
						            +"(SELECT "
						                    +"CASE "
						                            +"WHEN telefono = '' THEN celular "
						                            +"ELSE telefono "
						                        +"END AS telefono "
						                +"FROM "
						                    +"trabajadores "
						                +"WHERE "
						                    +"codigo = codigo_trabajador) AS telefono,"
						            +"(SELECT "
						                    +"email "
						                +"FROM "
						                    +"trabajadores "
						                +"WHERE "
						                    +"codigo = codigo_trabajador) AS email,"
						            +"(SELECT "
						                    +"id_status "
						                +"FROM "
						                    +"trabajadores "
						                +"WHERE "
						                    +"codigo = codigo_trabajador) AS id_status,"
						            +"(SELECT "
						                    +"estado_preselec "
						                +"FROM "
						                    +"trabajadores "
						                +"WHERE "
						                    +"codigo = codigo_trabajador) AS estado_preselec "
						    +"FROM "
						        +"(SELECT "
						        +"MAX(co.id) AS id, co.codigo_trabajador "
						    +"FROM "
						        +"contratos co "
						    +"GROUP BY co.codigo_trabajador) AS ss) AS uuu "
						+"WHERE "
						    +"id_status = 0 AND estado_preselec = 0 "
						        +"AND estadocontrato = 0 "
						        +"AND "+idcargo+" IN (SELECT " 
						            +"cargo "
						        +"FROM "
						            +"contratos "
						        +"WHERE "
						            +"codigo_trabajador = codigo)"
						            + "AND (select huerto from reclutamiento_c where id_reclutamiento = (select id_reclutamiento from peticion_trabajador where id_peticion = "+idpeticion+")) "
						            + " in (SELECT idHuertoContrato FROM contratos WHERE  codigo_trabajador = codigo) ";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				trabajadores e = new trabajadores();
				e.setCodigo(rs.getString("codigo"));
				e.setRut(rs.getString("rut"));
				e.setNombre(rs.getString("nombre"));
				e.setDireccion(rs.getString("direccion"));
				e.setTelefono(rs.getString("telefono"));
				e.setId(rs.getInt("id2"));
				e.setF_termino(rs.getString("f_termino"));
				e.setEmail(rs.getString("email"));
				e.setNombre_cargo(rs.getString("cargo"));
				e.setPostulante(0);
				data.add(e);
			}
			
			sql2 = "SELECT "
						+"tr.id,"
						    +"tr.codigo,"
						    +"CASE "
						        +"WHEN tr.rut = '' THEN tr.rutTemporal "
						        +"ELSE tr.rut "
						    +"END AS rut,"
						     +"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre,"
						       +"CONCAT(tr.calle, ' ', tr.ndireccion, ' ', tr.poblacion) as direccion,"
						        +"CASE "
						         	+"WHEN tr.telefono = '' THEN tr.celular "
						            +"ELSE tr.telefono "
						            +"END AS telefono,"
						        +"tr.email,"
						        +"(select cargos from cargos where id_cargo = ca.id_cargo) as cargo,"
						        +"1 as postulante "
						+"FROM "
						    +"sw_trabajadoresPostulante tr "
						        +"INNER JOIN "
						    +"sw_trabajadorCargo ca "
						+"WHERE "
						    +"ca.id_cargo =  "+idcargo+"";
			
			
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs2 = ps2.executeQuery(sql2);
			
			while(rs2.next()){
				trabajadores e2 = new trabajadores();
				e2.setCodigo(rs2.getString("codigo"));
				e2.setRut(rs2.getString("rut"));
				e2.setNombre(rs2.getString("nombre"));
				e2.setDireccion(rs2.getString("direccion"));
				e2.setTelefono(rs2.getString("telefono"));
				e2.setId(rs2.getInt("id"));
				e2.setF_termino("");
				e2.setEmail(rs2.getString("email"));
				e2.setNombre_cargo(rs2.getString("cargo"));
				e2.setPostulante(rs2.getInt("postulante"));
				data.add(e2);
			}
			
			
		
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			ps2.close();
			db.close();
		}	
		
		
		return data;
	}
	
	
	
	public static ArrayList<trabajadores> loadPersonal3(int idcargo,int idpeticion,int soc,String criterio_preseleccion,String genero,String edad1,String edad2,String region_,String provincia_, String comuna_, int nreclutamiento) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		
		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		ConnectionDB db = new ConnectionDB();
		try{
			
			if("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion))
			{
				sql = "SELECT "
					    +"* "
					+"FROM "
					    +"(SELECT "
					       +"id AS id2,"
					            +"codigo_trabajador AS codigo,"
					            +"(SELECT "
					                    +"EstadoContrato "
					                +"FROM "
					                    +"contratos co5 "
					                +"WHERE "
					                    +"co5.id = id2) AS estadocontrato,"
					            +"(SELECT "
					                    +"FechaTerminoContrato "
					                +"FROM "
					                    +"contratos co1 "
					                +"WHERE "
					                    +"co1.id = id2) AS f_termino,"
					            +" "+idcargo+" AS idcargo,"
					            +"(SELECT "
					                    +"cargos "
					                +"FROM "
					                    +"cargos "
					                +"WHERE "
					                    +"id_cargo = "+idcargo+") AS cargo,"
					            +"(SELECT "
					                    +"CASE "
					                            +"WHEN rut = '' THEN rutTemporal "
					                            +"ELSE rut "
					                        +"END AS rut "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS rut,"
					            +"(SELECT "
					                    +"UPPER(CONCAT(apellidoPaterno, ' ', apellidoMaterno, ' ', nombre)) AS nombre "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS nombre,"
					            +"(SELECT "
					                    +"CONCAT(calle, ' ', ndireccion, ' ', poblacion) "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS direccion,"
					            +"(SELECT "
					                    +"CASE "
					                            +"WHEN telefono = '' THEN celular "
					                            +"ELSE telefono "
					                        +"END AS telefono "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS telefono,"
					            +"(SELECT "
					                    +"email "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS email,"
					                    + "(SELECT idGenero FROM trabajadores WHERE codigo = codigo_trabajador) AS genero,"
					                    + "(SELECT fNacimiento FROM trabajadores WHERE codigo = codigo_trabajador) AS fnacimiento,"
					                    +"(SELECT " 
			                            +"idRegion "
			                        +"FROM "
			                            +"trabajadores "
			                        +"WHERE "
			                            +"codigo = codigo_trabajador) AS idRegion,"
			                    // provincia
			                            +"(SELECT " 
			                            +"idProvincia "
			                        +"FROM "
			                            +"trabajadores "
			                        +"WHERE "
			                            +"codigo = codigo_trabajador) AS idProvincia,"
			                   // comuna
			                         // provincia
			                            +"(SELECT " 
			                            +"idComuna "
			                        +"FROM "
			                            +"trabajadores "
			                        +"WHERE "
			                            +"codigo = codigo_trabajador) AS idComuna,"
					            +"(SELECT "
					                    +"id_status "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS id_status,"
					            +"(SELECT "
					                    +"estado_preselec "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS estado_preselec "
					    +"FROM "
					        +"(SELECT "
					        +"MAX(co.id) AS id, co.codigo_trabajador "
					    +"FROM "
					        +"contratos co "
					        + "left join preseleccionados pre on pre.codigo_trabajador = co.codigo_trabajador "
					        + " where  pre.status = 'Confirmado'"
					    +"GROUP BY co.codigo_trabajador) AS ss) AS uuu "
					+"WHERE "
					    +"id_status = 0 "
					    + "AND estado_preselec in (0,1) "
					        +"AND estadocontrato = 0 ";
					        if("null".equals(genero)){
					        	
					        }else{sql += " and genero  = "+genero+"";
					        }
					     // region 
					        if("null".equals(region_)){
					        	
					        }else{sql += " and idRegion  = "+region_+"";
					        }
					        // provincia 
					        if("null".equals(provincia_)){
					        	
					        }else{sql += " and idProvincia  = "+provincia_+"";
					        }
					        // comuna 
					        if("null".equals(comuna_)){
					        	
					        }else{sql += " and idComuna  = "+comuna_+"";
					        }
					        
					        if("null".equals(edad1)){
								
							}else if(!"null".equals(edad1) && "null".equals(edad2) ){
								sql += " and TIMESTAMPDIFF(YEAR,fnacimiento,CURDATE())  = "+edad1+"";}
							else if(!"null".equals(edad1) && !"null".equals(edad2)){
								sql += " and TIMESTAMPDIFF(YEAR,fnacimiento,CURDATE()) BETWEEN "+edad1+" and "+edad2+"";
							}
					        
					        
					        sql += " AND "+idcargo+" IN (SELECT " 
					            +"cargo "
					        +"FROM "
					            +"contratos "
					        +"WHERE "
					            +"codigo_trabajador = codigo)"
					            + "AND (select huerto from reclutamiento_c where id_reclutamiento = (select id_reclutamiento from peticion_trabajador where id_peticion = "+idpeticion+")) "
					            + " in (SELECT idHuertoContrato FROM contratos WHERE  codigo_trabajador = codigo) ";
		System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		
		while(rs.next()){
			trabajadores e = new trabajadores();
			e.setCodigo(rs.getString("codigo"));
			e.setRut(rs.getString("rut"));
			e.setNombre(rs.getString("nombre"));
			e.setDireccion(rs.getString("direccion"));
			e.setTelefono(rs.getString("telefono"));
			e.setId(rs.getInt("id2"));
			e.setF_termino(rs.getString("f_termino"));
			e.setEmail(rs.getString("email"));
			e.setNombre_cargo(rs.getString("cargo"));
			e.setPostulante(0);
			e.setId_peticion(idpeticion);
			e.setId_reclutamiento(nreclutamiento);
			data.add(e);
		}
			}else{
				sql += "";
			}
			
			if("null".equals(criterio_preseleccion) || "1".equals(criterio_preseleccion) ||
					   "1".equals(criterio_preseleccion) || "2".equals(criterio_preseleccion) ||
					   "3".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion) || "4".equals(criterio_preseleccion))
					{
				sql2 = "SELECT distinct "
						+"pre.codigo_peticion,"
						+"pre.id_peticion,"
						+"tr.id,"
						    +"tr.codigo,"
						    +"CASE "
						        +"WHEN tr.rut = '' THEN tr.rutTemporal "
						        +"ELSE tr.rut "
						    +"END AS rut,"
						     +"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre,"
						       +"CONCAT(tr.calle, ' ', tr.ndireccion, ' ', tr.poblacion) as direccion,"
						        +"CASE "
						         	+"WHEN tr.telefono = '' THEN tr.celular "
						            +"ELSE tr.telefono "
						            +"END AS telefono,"
						        +"tr.email,"
						        +"(select cargos from cargos where id_cargo = ca.id_cargo) as cargo,"
						        +"1 as postulante "
						+"FROM "
						    +"sw_trabajadoresPostulante tr "
						        +"INNER JOIN "
						    +"sw_trabajadorCargo ca ON tr.codigo = ca.cod_trabajador "
						    + "LEFT JOIN preseleccionados pre ON pre.codigo_trabajador = tr.codigo "
						+"WHERE "
						    +"ca.id_cargo =  "+idcargo+" and tr.idSociedad = "+soc+" "
						    		+ " and pre.status = 'Confirmado'";
			
			if("null".equals(genero)){}else{sql2 += " and tr.idGenero = "+genero+"";}
			if("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion) || "4".equals(criterio_preseleccion)){}else{sql2 += " and tr.criterio_postulacion = "+criterio_preseleccion+"";}
			//region
			if("null".equals(region_)){}else{sql2 += " and tr.idRegion = "+region_+"";}
			// provincia
			if("null".equals(provincia_)){}else{sql2 += " and tr.idProvincia = "+provincia_+"";}
			// comuna 
			if("null".equals(comuna_)){}else{sql2 += " and tr.idComuna = "+comuna_+"";}
			if("null".equals(edad1)){
				
			}else if(!"null".equals(edad1) && "null".equals(edad2) ){
				sql2 += " and TIMESTAMPDIFF(YEAR,tr.fNacimiento,CURDATE())  = "+edad1+"";}
			else if(!"null".equals(edad1) && !"null".equals(edad2)){
				sql2 += " and TIMESTAMPDIFF(YEAR,tr.fNacimiento,CURDATE()) BETWEEN "+edad1+" and "+edad2+"";
			}
		
			
			
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs2 = ps2.executeQuery(sql2);
			
			while(rs2.next()){
				trabajadores e2 = new trabajadores();
				e2.setCodigo(rs2.getString("codigo"));
				e2.setRut(rs2.getString("rut"));
				e2.setNombre(rs2.getString("nombre"));
				e2.setDireccion(rs2.getString("direccion"));
				e2.setTelefono(rs2.getString("telefono"));
				e2.setId(rs2.getInt("id"));
				e2.setF_termino("");
				e2.setEmail(rs2.getString("email"));
				e2.setNombre_cargo(rs2.getString("cargo"));
				e2.setPostulante(rs2.getInt("postulante"));
				e2.setId_peticion(rs2.getInt("id_peticion"));
				e2.setId_reclutamiento(rs2.getInt("codigo_peticion"));
				data.add(e2);
			}
					}else{
						sql2 += "";
					}
			
			
		
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		} finally {
			if ("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion)) {
				ps.close();
			}

			if ("null".equals(criterio_preseleccion) || "1".equals(criterio_preseleccion)
					|| "1".equals(criterio_preseleccion) || "2".equals(criterio_preseleccion)
					|| "3".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion)
					|| "4".equals(criterio_preseleccion)) {
				ps2.close();
			}
			db.close();
		}
		
		
		return data;
	}
	
	public static ArrayList<trabajadores> loadPersonal4(int idcargo, int idpeticion, int soc,
			String criterio_preseleccion, String genero, String edad1, String edad2, String region_, String provincia_,
			String comuna_) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";

		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		ConnectionDB db = new ConnectionDB();
		try {

			if ("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion)) {
				sql = "SELECT " + "* " + "FROM " + "(SELECT " + "id AS id2," + "codigo_trabajador AS codigo,"
						+ "(SELECT " + "EstadoContrato " + "FROM " + "contratos co5 " + "WHERE "
						+ "co5.id = id2) AS estadocontrato," + "(SELECT " + "FechaTerminoContrato " + "FROM "
						+ "contratos co1 " + "WHERE " + "co1.id = id2) AS f_termino," + " " + idcargo + " AS idcargo,"
						+ "(SELECT " + "cargos " + "FROM " + "cargos " + "WHERE " + "id_cargo = " + idcargo
						+ ") AS cargo," + "(SELECT " + "CASE " + "WHEN rut = '' THEN rutTemporal " + "ELSE rut "
						+ "END AS rut " + "FROM " + "trabajadores " + "WHERE " + "codigo = codigo_trabajador) AS rut,"
						+ "(SELECT " + "UPPER(CONCAT(apellidoPaterno, ' ', apellidoMaterno, ' ', nombre)) AS nombre "
						+ "FROM " + "trabajadores " + "WHERE " + "codigo = codigo_trabajador) AS nombre," + "(SELECT "
						+ "CONCAT(calle, ' ', ndireccion, ' ', poblacion) " + "FROM " + "trabajadores " + "WHERE "
						+ "codigo = codigo_trabajador) AS direccion," + "(SELECT " + "CASE "
						+ "WHEN telefono = '' THEN celular " + "ELSE telefono " + "END AS telefono " + "FROM "
						+ "trabajadores " + "WHERE " + "codigo = codigo_trabajador) AS telefono," + "(SELECT "
						+ "email " + "FROM " + "trabajadores " + "WHERE " + "codigo = codigo_trabajador) AS email,"
						+ "(SELECT idGenero FROM trabajadores WHERE codigo = codigo_trabajador) AS genero,"
						+ "(SELECT fNacimiento FROM trabajadores WHERE codigo = codigo_trabajador) AS fnacimiento,"
						// region
						+ "(SELECT " + "idRegion " + "FROM " + "trabajadores " + "WHERE "
						+ "codigo = codigo_trabajador) AS idRegion,"
						// provincia
						+ "(SELECT " + "idProvincia " + "FROM " + "trabajadores " + "WHERE "
						+ "codigo = codigo_trabajador) AS idProvincia,"
						// comuna
						// provincia
						+ "(SELECT " + "idComuna " + "FROM " + "trabajadores " + "WHERE "
						+ "codigo = codigo_trabajador) AS idComuna," + "(SELECT " + "id_status " + "FROM "
						+ "trabajadores " + "WHERE " + "codigo = codigo_trabajador) AS id_status," + "(SELECT "
						+ "estado_preselec " + "FROM " + "trabajadores " + "WHERE "
						+ "codigo = codigo_trabajador) AS estado_preselec, "
						+"(select  if(fecha_entrevista is null,'',fecha_entrevista) from  preseleccionados where codigo_trabajador = codigo_trabajador and id_peticion = "+idpeticion+") as fecha_entre,"
	                    +"(select  if(hora_entrevista is null,'',hora_entrevista) from  preseleccionados where codigo_trabajador = codigo_trabajador and id_peticion = "+idpeticion+") as hora_entre "
						+ "FROM " + "(SELECT "
						+ "MAX(co.id) AS id, co.codigo_trabajador " + "FROM " + "contratos co "
						+ "left join preseleccionados pre on pre.codigo_trabajador = co.codigo_trabajador "
						+ " where  pre.status = 'Confirmado2'" + "GROUP BY co.codigo_trabajador) AS ss) AS uuu "
						+ "WHERE " + "id_status = 0 " + "AND estado_preselec in (0,1) " + "AND estadocontrato = 0 ";
				if ("null".equals(genero)) {

				} else {
					sql += " and genero  = " + genero + "";
				}
				// region
				if ("null".equals(region_)) {

				} else {
					sql += " and idRegion  = " + region_ + "";
				}
				// provincia
				if ("null".equals(provincia_)) {

				} else {
					sql += " and idProvincia  = " + provincia_ + "";
				}
				// comuna
				if ("null".equals(comuna_)) {

				} else {
					sql += " and idComuna  = " + comuna_ + "";
				}
				if ("null".equals(edad1)) {

				} else if (!"null".equals(edad1) && "null".equals(edad2)) {
					sql += " and TIMESTAMPDIFF(YEAR,fnacimiento,CURDATE())  = " + edad1 + "";
				} else if (!"null".equals(edad1) && !"null".equals(edad2)) {
					sql += " and TIMESTAMPDIFF(YEAR,fnacimiento,CURDATE()) BETWEEN " + edad1 + " and " + edad2 + "";
				}

				sql += " AND " + idcargo + " IN (SELECT " + "cargo " + "FROM " + "contratos " + "WHERE "
						+ "codigo_trabajador = codigo)"
						+ "AND (select huerto from reclutamiento_c where id_reclutamiento = (select id_reclutamiento from peticion_trabajador where id_peticion = "
						+ idpeticion + ")) "
						+ " in (SELECT idHuertoContrato FROM contratos WHERE  codigo_trabajador = codigo) ";
				System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {
					trabajadores e = new trabajadores();
					e.setCodigo(rs.getString("codigo"));
					e.setRut(rs.getString("rut"));
					e.setNombre(rs.getString("nombre"));
					e.setDireccion(rs.getString("direccion"));
					e.setTelefono(rs.getString("telefono"));
					e.setId(rs.getInt("id2"));
					e.setF_termino(rs.getString("f_termino"));
					e.setEmail(rs.getString("email"));
					e.setNombre_cargo(rs.getString("cargo"));
					e.setPostulante(0);
					e.setFecha_e(rs.getString("fecha_entre"));
					e.setHora_e(rs.getString("hora_entre"));
					data.add(e);
				}
			} else {
				sql += "";
			}

			if ("null".equals(criterio_preseleccion) || "1".equals(criterio_preseleccion)
					|| "1".equals(criterio_preseleccion) || "2".equals(criterio_preseleccion)
					|| "3".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion)
					|| "4".equals(criterio_preseleccion)) {
				sql2 = "SELECT distinct " + "tr.id," + "tr.codigo," + "CASE " + "WHEN tr.rut = '' THEN tr.rutTemporal "
						+ "ELSE tr.rut " + "END AS rut,"
						+ "UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre,"
						+ "CONCAT(tr.calle, ' ', tr.ndireccion, ' ', tr.poblacion) as direccion," + "CASE "
						+ "WHEN tr.telefono = '' THEN tr.celular " + "ELSE tr.telefono " + "END AS telefono,"
						+ "tr.email," + "(select cargos from cargos where id_cargo = ca.id_cargo) as cargo,"
						+ "1 as postulante,"
						+"pre.fecha_entrevista,"
					    +"pre.hora_entrevista "
						+ "FROM " + "sw_trabajadoresPostulante tr " + "INNER JOIN "
						+ "sw_trabajadorCargo ca ON tr.codigo = ca.cod_trabajador "
						+ "LEFT JOIN preseleccionados pre ON pre.codigo_trabajador = tr.codigo " + "WHERE "
						+ "ca.id_cargo =  " + idcargo + " and tr.idSociedad = " + soc + " "
						+ " AND pre.status = 'Confirmado2'";

				if ("null".equals(genero)) {
				} else {
					sql2 += " and tr.idGenero = " + genero + "";
				}
				if ("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion)
						|| "4".equals(criterio_preseleccion)) {
				} else {
					sql2 += " and tr.criterio_postulacion = " + criterio_preseleccion + "";
				}
				// region
				if ("null".equals(region_)) {
				} else {
					sql2 += " and tr.idRegion = " + region_ + "";
				}
				// provincia
				if ("null".equals(provincia_)) {
				} else {
					sql2 += " and tr.idProvincia = " + provincia_ + "";
				}
				// comuna
				if ("null".equals(comuna_)) {
				} else {
					sql2 += " and tr.idComuna = " + comuna_ + "";
				}
				if ("null".equals(edad1)) {

				} else if (!"null".equals(edad1) && "null".equals(edad2)) {
					sql2 += " and TIMESTAMPDIFF(YEAR,tr.fNacimiento,CURDATE())  = " + edad1 + "";
				} else if (!"null".equals(edad1) && !"null".equals(edad2)) {
					sql2 += " and TIMESTAMPDIFF(YEAR,tr.fNacimiento,CURDATE()) BETWEEN " + edad1 + " and " + edad2 + "";
				}

				System.out.println(sql2);
				ps2 = db.conn.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery(sql2);

				while (rs2.next()) {
					trabajadores e2 = new trabajadores();
					e2.setCodigo(rs2.getString("codigo"));
					e2.setRut(rs2.getString("rut"));
					e2.setNombre(rs2.getString("nombre"));
					e2.setDireccion(rs2.getString("direccion"));
					e2.setTelefono(rs2.getString("telefono"));
					e2.setId(rs2.getInt("id"));
					e2.setF_termino("");
					e2.setEmail(rs2.getString("email"));
					e2.setNombre_cargo(rs2.getString("cargo"));
					e2.setPostulante(rs2.getInt("postulante"));
					e2.setFecha_e(rs2.getString("fecha_entrevista"));
					e2.setHora_e(rs2.getString("hora_entre"));
					data.add(e2);
				}
			} else {
				sql2 += "";
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			if ("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion)) {
				ps.close();
			}

			if ("null".equals(criterio_preseleccion) || "1".equals(criterio_preseleccion)
					|| "1".equals(criterio_preseleccion) || "2".equals(criterio_preseleccion)
					|| "3".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion)
					|| "4".equals(criterio_preseleccion)) {
				ps2.close();
			}
			db.close();
		}

		return data;
	}
	
	public static ArrayList<trabajadores> loadPersonal2(int idcargo,int idpeticion,int soc,String criterio_preseleccion,String genero,String edad1,String edad2,String region_,String provincia_, String comuna_) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		
		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		ConnectionDB db = new ConnectionDB();
		try{
			
			if("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion))
					{
				sql = "SELECT "
					    +"* "
					+"FROM "
					    +"(SELECT "
					       +"id AS id2,"
					            +"codigo_trabajador AS codigo,"
					            +"(SELECT "
					                    +"EstadoContrato "
					                +"FROM "
					                    +"contratos co5 "
					                +"WHERE "
					                    +"co5.id = id2) AS estadocontrato,"
					            +"(SELECT "
					                    +"FechaTerminoContrato "
					                +"FROM "
					                    +"contratos co1 "
					                +"WHERE "
					                    +"co1.id = id2) AS f_termino,"
					            +" "+idcargo+" AS idcargo,"
					            +"(SELECT "
					                    +"cargos "
					                +"FROM "
					                    +"cargos "
					                +"WHERE "
					                    +"id_cargo = "+idcargo+") AS cargo,"
					            +"(SELECT "
					                    +"CASE "
					                            +"WHEN rut = '' THEN rutTemporal "
					                            +"ELSE rut "
					                        +"END AS rut "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS rut,"
					            +"(SELECT "
					                    +"UPPER(CONCAT(apellidoPaterno, ' ', apellidoMaterno, ' ', nombre)) AS nombre "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS nombre,"
					            +"(SELECT "
					                    +"CONCAT(calle, ' ', ndireccion, ' ', poblacion) "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS direccion,"
					            +"(SELECT "
					                    +"CASE "
					                            +"WHEN telefono = '' THEN celular "
					                            +"ELSE telefono "
					                        +"END AS telefono "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS telefono,"
					            +"(SELECT "
					                    +"email "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS email,"
					                    + "(SELECT idGenero FROM trabajadores WHERE codigo = codigo_trabajador) AS genero,"
					                    + "(SELECT fNacimiento FROM trabajadores WHERE codigo = codigo_trabajador) AS fnacimiento,"
					                    // region
					                    +"(SELECT " 
					                            +"idRegion "
					                        +"FROM "
					                            +"trabajadores "
					                        +"WHERE "
					                            +"codigo = codigo_trabajador) AS idRegion,"
					                    // provincia
					                            +"(SELECT " 
					                            +"idProvincia "
					                        +"FROM "
					                            +"trabajadores "
					                        +"WHERE "
					                            +"codigo = codigo_trabajador) AS idProvincia,"
					                   // comuna
					                         // provincia
					                            +"(SELECT " 
					                            +"idComuna "
					                        +"FROM "
					                            +"trabajadores "
					                        +"WHERE "
					                            +"codigo = codigo_trabajador) AS idComuna,"
					            +"(SELECT "
					                    +"id_status "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS id_status,"
					            +"(SELECT "
					                    +"estado_preselec "
					                +"FROM "
					                    +"trabajadores "
					                +"WHERE "
					                    +"codigo = codigo_trabajador) AS estado_preselec "
					    +"FROM "
					        +"(SELECT "
					        +"MAX(co.id) AS id, co.codigo_trabajador "
					    +"FROM "
					        +"contratos co "
					    +"GROUP BY co.codigo_trabajador) AS ss) AS uuu "
					+"WHERE "
					    +"id_status = 0 "
					    + "and  rut  not in (select rutNoAutorizados from sw_m_rutNoAutorizados where rutNoAutorizados = rut) "
					    + "AND estado_preselec in (0,1) "
					        +"AND estadocontrato = 0 ";
					        if("null".equals(genero)){
					        	
					        }else{sql += " and genero  = "+genero+"";
					        }
					        
					        // region 
					        if("null".equals(region_)){
					        	
					        }else{sql += " and idRegion  = "+region_+"";
					        }
					        // provincia 
					        if("null".equals(provincia_)){
					        	
					        }else{sql += " and idProvincia  = "+provincia_+"";
					        }
					        // comuna 
					        if("null".equals(comuna_)){
					        	
					        }else{sql += " and idComuna  = "+comuna_+"";
					        }
					        
					        
					        
					        if("null".equals(edad1)){
								
							}else if(!"null".equals(edad1) && "null".equals(edad2) ){
								sql += " and TIMESTAMPDIFF(YEAR,fnacimiento,CURDATE())  = "+edad1+"";}
							else if(!"null".equals(edad1) && !"null".equals(edad2)){
								sql += " and TIMESTAMPDIFF(YEAR,fnacimiento,CURDATE()) BETWEEN "+edad1+" and "+edad2+"";
							}
					        
					        
					        sql += " AND "+idcargo+" IN (SELECT " 
					            +"cargo "
					        +"FROM "
					            +"contratos "
					        +"WHERE "
					            +"codigo_trabajador = codigo)"
					            + "AND (select huerto from reclutamiento_c where id_reclutamiento = (select id_reclutamiento from peticion_trabajador where id_peticion = "+idpeticion+")) "
					            + " in (SELECT idHuertoContrato FROM contratos WHERE  codigo_trabajador = codigo) ";
		System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		
		while(rs.next()){
			trabajadores e = new trabajadores();
			e.setCodigo(rs.getString("codigo"));
			e.setRut(rs.getString("rut"));
			e.setNombre(rs.getString("nombre"));
			e.setDireccion(rs.getString("direccion"));
			e.setTelefono(rs.getString("telefono"));
			e.setId(rs.getInt("id2"));
			e.setF_termino(rs.getString("f_termino"));
			e.setEmail(rs.getString("email"));
			e.setNombre_cargo(rs.getString("cargo"));
			e.setPostulante(0);
			data.add(e);
			
			
		}
					}else{
						sql += "";
					}
			
			
			if("null".equals(criterio_preseleccion) || "1".equals(criterio_preseleccion) ||
			   "1".equals(criterio_preseleccion) || "2".equals(criterio_preseleccion) ||
			   "3".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion) || "4".equals(criterio_preseleccion))
			{
				sql2 = "SELECT distinct "
						+"tr.id,"
						    +"tr.codigo,"
						    +"CASE "
						        +"WHEN tr.rut = '' THEN tr.rutTemporal "
						        +"ELSE tr.rut "
						    +"END AS rut,"
						     +"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre,"
						       +"CONCAT(tr.calle, ' ', tr.ndireccion, ' ', tr.poblacion) as direccion,"
						        +"CASE "
						         	+"WHEN tr.telefono = '' THEN tr.celular "
						            +"ELSE tr.telefono "
						            +"END AS telefono,"
						        +"tr.email,"
						        +"(select cargos from cargos where id_cargo = ca.id_cargo) as cargo,"
						        +"1 as postulante "
						+"FROM "
						    +"sw_trabajadoresPostulante tr "
						        +"INNER JOIN "
						    +"sw_trabajadorCargo ca on tr.codigo = ca.cod_trabajador "
						+"WHERE "
						    +"ca.id_cargo =  "+idcargo+" and tr.idSociedad = "+soc+" "
						    + "and  rut  not in (select rutNoAutorizados from sw_m_rutNoAutorizados where rutNoAutorizados = rut)";
			
			if("null".equals(genero)){}else{sql2 += " and tr.idGenero = "+genero+"";}
			if("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion) || "4".equals(criterio_preseleccion)){}else{sql2 += " and tr.criterio_postulacion = "+criterio_preseleccion+"";}
			//region
			if("null".equals(region_)){}else{sql2 += " and tr.idRegion = "+region_+"";}
			// provincia
			if("null".equals(provincia_)){}else{sql2 += " and tr.idProvincia = "+provincia_+"";}
			// comuna 
			if("null".equals(comuna_)){}else{sql2 += " and tr.idComuna = "+comuna_+"";}
			if("null".equals(edad1)){
				
			}else if(!"null".equals(edad1) && "null".equals(edad2) ){
				sql2 += " and TIMESTAMPDIFF(YEAR,tr.fNacimiento,CURDATE())  = "+edad1+"";}
			else if(!"null".equals(edad1) && !"null".equals(edad2)){
				sql2 += " and TIMESTAMPDIFF(YEAR,tr.fNacimiento,CURDATE()) BETWEEN "+edad1+" and "+edad2+"";
			}
		
			
			
			System.out.println(sql2);
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs2 = ps2.executeQuery(sql2);
			
			while(rs2.next()){
				trabajadores e2 = new trabajadores();
				e2.setCodigo(rs2.getString("codigo"));
				e2.setRut(rs2.getString("rut"));
				e2.setNombre(rs2.getString("nombre"));
				e2.setDireccion(rs2.getString("direccion"));
				e2.setTelefono(rs2.getString("telefono"));
				e2.setId(rs2.getInt("id"));
				e2.setF_termino("");
				e2.setEmail(rs2.getString("email"));
				e2.setNombre_cargo(rs2.getString("cargo"));
				e2.setPostulante(rs2.getInt("postulante"));
				data.add(e2);
			}
			
			}else{
				sql2 += "";
			}
			
			
			
		
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			if("null".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion))
			{
				ps.close();
			}
			
			if("null".equals(criterio_preseleccion) || "1".equals(criterio_preseleccion) ||
					   "1".equals(criterio_preseleccion) || "2".equals(criterio_preseleccion) ||
					   "3".equals(criterio_preseleccion) || "5".equals(criterio_preseleccion) || "4".equals(criterio_preseleccion))
					{
					ps2.close();
					}
			
			db.close();
		}	
		
		
		return data;
	}
	//cargar p haberes descuentos
	public static ArrayList<haberesDescuentos> getHaberDsc() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<haberesDescuentos> lista = new ArrayList<haberesDescuentos>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM sw_p_haberesDescuentos";
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				haberesDescuentos hd = new haberesDescuentos();
				hd.setCodigo(rs.getInt("codigo"));
				hd.setDescripcion(rs.getString("descripcion"));
				hd.setTipo(rs.getString("tipo"));
				hd.setImponible(rs.getString("imponible"));
				hd.setTributable(rs.getString("tributable"));
				hd.setGratificacion(rs.getString("gratificacion"));
				hd.setIncluirGlosa(rs.getString("incluirGlosa"));
				hd.setCaracterFija(rs.getString("caracterFija"));
				hd.setCaracterVariable(rs.getString("caracterVariable"));
				hd.setSemanaCorrida(rs.getString("semanaCorrida"));
				lista.add(hd);
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}


				// cargar conceptos haberes y descuentos
				public static ArrayList<LoadConceptos> getConceptos(String HD ) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<LoadConceptos> data = new ArrayList<LoadConceptos>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select codigo, descripcion from sw_p_haberesDescuentos where tipo = '"+HD+"'";
						
						
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							LoadConceptos e = new LoadConceptos();
							
							e.setCodigo(rs.getInt("codigo"));
							e.setConceptos(rs.getString("descripcion"));
						
							data.add(e);
						}
						rs.close();
						ps.close();
						db.conn.close();
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}
	
				
				//todos los trabajadores codigo y nombre
				public static ArrayList<trabajadores> getallTrabajaCodNom(String idSociedad,String huerto, String zona, String ceco,int rolPrivado,String estado_proceso)  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<trabajadores> lista = new ArrayList<trabajadores>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select  "
								+ "TR.codigo,"
								+ "TR.nombre,"
								+ "TR.apellidoPaterno,"
								+ "TR.apellidoMaterno "
								+ "from contratos CO "
								+ "inner join trabajadores TR on TR.codigo = CO.codigo_trabajador "
								+ "where 1 = 1 and CO.EstadoContrato = 1  ";
						System.out.println("roll privado es "+rolPrivado);
						
						if(rolPrivado == 1){
							sql += " and  TR.rolPrivado in (1,0)";
						}else{
							sql += " and  TR.rolPrivado in (0)";
						}
						
						
						if("null".equals(idSociedad)){}else{sql += " and idSociedad = "+idSociedad+"";}
						if("null".equals(huerto)){}else{sql += " and TR.idHuerto = '"+huerto+"'";}
						if("null".equals(zona)){}else{sql += " and TR.idZona = '"+zona+"'";}
						if("null".equals(ceco)){}else{sql += " and TR.idCECO = '"+ceco+"'";}
						if("null".equals(estado_proceso)){}else{sql += " and TR.estadoProceso = "+estado_proceso+" ";}
						
						
						//sql += " and CO.EstadoContrato = 1 group by CO.codigo_trabajador";
						sql += " group by CO.codigo_trabajador";
						System.out.println(sql);
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							trabajadores tr = new trabajadores();
							tr.setCodigo(rs.getString("codigo"));
							tr.setNombre(rs.getString("nombre"));
							tr.setAp_paterno(rs.getString("apellidoPaterno"));
							tr.setAp_materno(rs.getString("apellidoMaterno"));
							lista.add(tr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
				
				
				// insert haberes y descuento para trabajadores
				
				public static boolean insertarHD (InsertHD r) throws Exception{
					Statement ps = null;
				
					String sql="";
					ConnectionDB db = new ConnectionDB();
					
					try{
						sql = "INSERT INTO sw_haberesDescuentos (periodo , tipo, codigo_hd , monto , codigo_trabajador,frecuencia,cuotas,fecha_inicio,fecha_termino,idContrato,llave_moneda,proporcional)";
						sql+= "VALUES ("+r.getPeriodo()+",'"+r.getTipo()+"',"+r.getCodigo_hd()+","+r.getMonto()+","+r.getCodigo_trabajador()+","+r.getFrecuencia()+","+r.getCuotas()+","+r.getFecha_inicio()+","+r.getFecha_termino()+","+r.getId_contrato()+ ","+r.getIdmoneda()+","+r.getValorcheck()+");";
										
						System.out.println(sql);
						
						ps = db.conn.prepareStatement(sql);
						
						
						ps.execute(sql);
					
						
						return true;
					}catch (SQLException e){
						System.out.println("Error: "+ e.getMessage());
					}catch (Exception e){
						System.out.println("Error: "+ e.getMessage());
					}finally {
						ps.close();
					
						db.close();
					}
					return false;
				}
				
				public static ArrayList<sw_haberesDescuentos> sw_haberesDescuento() throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<sw_haberesDescuentos> lista = new ArrayList<sw_haberesDescuentos>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "SELECT sw.id,sw.periodo,sw.tipo,sw.codigo_hd,sw.monto,sw.fecha,sw.codigo_trabajador,"
								+ "(select descripcion from parametros where id = sw.frecuencia) as frecuencia,"
								+ "sw.frecuencia as idfrecuencia,sw.cuotas,sw.fecha_inicio,sw.fecha_termino, tr.nombre,tr.apellidoPaterno,tr.apellidoMaterno FROM sw_haberesDescuentos sw inner join trabajadores tr on sw.codigo_trabajador = tr.codigo ";
						
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							sw_haberesDescuentos hd = new sw_haberesDescuentos();
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setTipo(rs.getString("tipo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setApellidopaterno(rs.getString("apellidoPaterno"));
							hd.setApellidomaterno(rs.getString("apellidoMaterno"));
							hd.setCodigo_hd(rs.getInt("codigo_hd"));
							hd.setMonto(rs.getInt("monto"));
							hd.setFecha(rs.getDate("fecha"));
							hd.setCodigo_trabajador(rs.getString("codigo_trabajador"));
							
							hd.setNombreFrecuencia(rs.getString("frecuencia"));
							hd.setIdfrecuencia(rs.getInt("idfrecuencia"));
							hd.setCuotas(rs.getInt("cuotas"));
							hd.setFechainicio(rs.getInt("fecha_inicio"));
							hd.setFechatermino(rs.getInt("fecha_termino"));
					
							
					
							
							
							
							lista.add(hd);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
				
				public static boolean updateTHD(UpdateTrabajadorHD pre) throws  Exception{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB  db = new ConnectionDB();	
					try {

						sql = "Update sw_haberesDescuentos set "
							+ "periodo = "+ pre.getPeriodo_t()+", "
							+ "tipo ='" +pre.getTipo_t()+ "',"
							+ "codigo_hd = " +pre.getCodigo_hd_t()+ ","
							+ "monto = " +pre.getMontonew()+ ","
							+ "frecuencia = " +pre.getFrecuencia_t()+ ","
							+ "cuotas = " +pre.getCuota()+ ","
						    + "fecha_inicio = " +pre.getPeriodo_t()+ ","
						    + "fecha_termino = " +pre.getFecha_termino()+ ","
						    + "proporcional = " +pre.getProporcional()+ ","
						    + "llave_moneda = " +pre.getIdmoneda()+ " "	
							+ "where id = "+pre.getId()+""; 
				
						
						ps = db.conn.prepareStatement(sql);
						ps.execute();
								
						return true;
					} catch (SQLException e) {
						System.out.println("Error:" + e.getMessage());
						e.printStackTrace();
					}catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return false;
				}
				
			// insertar contratos
				
				public static GetDatosContratoTrabajador insertarContrato (InsertContrato r, int pet,int cod) throws Exception{
					Statement ps = null;
					Statement ps2 = null;
					Statement ps3 = null;
					Statement ps4 = null;
					Statement ps5 = null;
					Statement ps6 = null;
					Statement ps7 = null;
					Statement ps8 = null;
					Statement ps9 = null;
					Statement ps10 = null;
					String sql="";
					String sql2="";
					String sql3="";
					String sql4="";
					String sql5="";
					String sql6="";
					String sql7="";
					String sql8="";
					String sql9="";
					String sql10="";
					
					ConnectionDB db = new ConnectionDB();
					GetDatosContratoTrabajador tr = new GetDatosContratoTrabajador();
					

//					zona : id_ZONA_C[fila]
					
					
					try{
						
						sql8 = "select * from sw_trabajadoresPostulante where codigo = "+r.getCodigo_trabajador()+"";
						System.out.println(sql8);	
						
						ps8 = db.conn.prepareStatement(sql4);
						
						ResultSet rs8 = ps8.executeQuery(sql8);
						
						if (!rs8.isBeforeFirst()) {
							
							System.out.println("no inseerto en trabajadores");
							
						}else{
							System.out.println("inserto en trabajadores");
							sql9 = "INSERT INTO trabajadores (id, codigo, rut, nombre, rutTemporal, pasaporte, fNacimiento, apellidoPaterno, apellidoMaterno, direccion, telefono, celular, id_perfil, hrs_semanal, email, asign_zona_extrema, id_pet_tbl_PT, id_rechazo, id_status, fechaIngresoCompania, idRegion, idComuna, idSubDivision, idSubGrupo, idGenero, idNacionalidad, idEstadoCivil, idProvincia, pensionados, pensionadosCotizantes, sCesantia, capacidades, subsidio, mayor11Anos, recurrente, tipoTrabajador, division, grupo, cargo, nombreEmergencia, telefonoEmergencia, emailEmergencia, parentesco, estado_preselec, agro, trabajadorAgricola, valorFijo, fechaCreacion, idVacaciones, recorrido, idSector, idAFP, idMonedaAFP, valorAFP, idIsapre, idMonedaPlan, valorPlan, idMonedaAdicionalAFP, valorAdicionalAFP, fechaAfiliacionAFP, institucionAPV, idMonedaAPV, valorDepositoAPV, institucionConvenido, idMonedaConvenido, valorConvenido, nContrato, f_termino, est_contrato, tipo_contrato, idEtnia, idContratista, idTipoLicenciaConducir, idHuerto, idZona, idCECO, calle, ndireccion, depto, poblacion, idFaena, rolPrivado, razonSocial, trabajadorJoven, idAdicionalAFP, estadoProceso) "
									+ "SELECT id, codigo, rut, nombre, rutTemporal, pasaporte, fNacimiento, apellidoPaterno, apellidoMaterno, direccion, telefono, celular, id_perfil, hrs_semanal, email, asign_zona_extrema, id_pet_tbl_PT, id_rechazo, id_status, fechaIngresoCompania, idRegion, idComuna, idSubDivision, idSubGrupo, idGenero, idNacionalidad, idEstadoCivil, idProvincia, pensionados, pensionadosCotizantes, sCesantia, capacidades, subsidio, mayor11Anos, recurrente, tipoTrabajador, division, grupo, cargo, nombreEmergencia, telefonoEmergencia, emailEmergencia, parentesco, estado_preselec, agro, trabajadorAgricola, valorFijo, fechaCreacion, idVacaciones, recorrido, idSector, idAFP, idMonedaAFP, valorAFP, idIsapre, idMonedaPlan, valorPlan, idMonedaAdicionalAFP, valorAdicionalAFP, fechaAfiliacionAFP, institucionAPV, idMonedaAPV, valorDepositoAPV, institucionConvenido, idMonedaConvenido, valorConvenido, nContrato, f_termino, est_contrato, tipo_contrato, idEtnia, idContratista, idTipoLicenciaConducir, idHuerto, idZona, idCECO, calle, ndireccion, depto, poblacion, idFaena, rolPrivado, razonSocial, trabajadorJoven, idAdicionalAFP, estadoProceso"
									+ " FROM sw_trabajadoresPostulante where codigo = "+r.getCodigo_trabajador()+"";
							
							ps9 = db.conn.prepareStatement(sql9);
							ps9.execute(sql9);
							
							sql10 = "DELETE FROM sw_trabajadoresPostulante where codigo ="+r.getCodigo_trabajador()+"";
							
							System.out.println(sql10);
							ps10 = db.conn.prepareStatement(sql10);
							ps10.execute(sql10);
							
							
						}
						
						
						sql = "INSERT INTO contratos "
								+ "(codigo_trabajador,"
								+ "id_peticion,"
								+ "codigo_peticion,"
								+ "sueldoBase,"
								+ "fechaInicio_actividad,"
								+ "fechaContrato_emitido,"
								+ "EstadoContrato,"
								+ "idSociedad,"
								+ "cargo,"
								+ "posicion,"
								+ "tipoContrato,"
								+ "horasSemanales,"
								+ "tipoTrabajador,"
								+ "partTime,"
								+ "supervisor,"
								+ "maquinista,"
								+ "idHuertoContrato,"
								+ "idCECOContrato,"
								+ "idFaenaContrato)";
						
						sql+= "VALUES ("+r.getCodigo_trabajador()+","
										+r.getId_peticion()+","
										+r.getCodigo_peticion()+","
										+r.getSueldo_base()+",'"
										+r.getFechaactividad()+"',"
										+ "now(),"
										+ "1,"
										+r.getIdSociedad()+","
										+r.getCargo()+","
										+r.getPosicion()+","
										+r.getTipoContrato()+","
										+r.getHorasSemanales()+","
										+r.getTipoTrabajador()+","
										+r.getPartTime()+","
										+r.getSupervisor()+","
										+r.getMaquinista()+",'"
										+r.getIdHuertoContrato()+"','"
										+r.getIdCECOContrato()+"','"
										+r.getIdFaenaContrato()+"')";
						System.out.println(sql);				
						sql2 = "select TR.codigo as codigoTrabajador,'ciudadContrato',(select empresa from reclutamiento_c where id_reclutamiento = "+pet+") as nombreEmpresa,TR.apellidoPaterno as appPatTrabajador,TR.apellidoMaterno as appMaternoTrabajador,TR.rut as rutCompletoTrabajador,(select descripcion from parametros where codigo = 'ESTADO_CIVIL' and llave = TR.idEstadoCivil) as estadoCivil,TR.fNacimiento as fechaNacimientoTrabajador,(select pais from nacionalidad  where idnacionalidad = TR.idNacionalidad) as nacionalidadTrabajador, TR.direccion as direccionTrabajador,(select nombre from comuna  where id = TR.idComuna) as comuna, (select cargo from peticion_trabajador  where id_reclutamiento = "+pet+" group by cargo) as cargoTrabajador,(select obra from peticion_trabajador  where id_reclutamiento = "+pet+" group by obra) as faenaContratacion,'SueldoCpuntosTrabajador',(select fecha_inicio from preseleccionados where codigo_trabajador = "+r.getCodigo_trabajador()+" and codigo_peticion = "+pet+") as fechaInicio,'ciudadContrato',TR.rut as rutCompletoTrabajador from trabajadores TR where codigo = "+r.getCodigo_trabajador()+"";
						System.out.println(sql2);	
						sql3 = "UPDATE trabajadores SET id_status = 1 WHERE codigo = "+r.getCodigo_trabajador()+"";	  
						System.out.println(sql3);	
						sql4 = "UPDATE preseleccionados SET status = 'Contratado' WHERE codigo_trabajador = "+r.getCodigo_trabajador()+" and codigo_peticion = "+pet+" and id_peticion = "+cod+"";
						System.out.println(sql4);	
						
						ps = db.conn.prepareStatement(sql);
						ps2 = db.conn.prepareStatement(sql2);
						ps3 = db.conn.prepareStatement(sql3);
						ps4 = db.conn.prepareStatement(sql4);
						
						ps.execute(sql);
						ps3.execute(sql3);
						ps4.execute(sql4);
						
						ResultSet rs = ps2.executeQuery(sql2);
						
						if (!rs8.isBeforeFirst()) {
							if(r.getTipoContrato() == 1){
							sql5 = "INSERT INTO sw_movimientoPrevired"
									+ "(cod_trabajador, id_contrato, periodo, movimento_previred, id_sociedad, fecha_movimiento)"
									+ "VALUES ("+r.getCodigo_trabajador()+", (select max(id) from contratos),DATE_FORMAT('"+r.getFechaactividad()+"', '%Y%m'), '1', "+r.getIdSociedad()+", '"+r.getFechaactividad()+"')";
							System.out.println(sql5);
							ps5 = db.conn.prepareStatement(sql5);
							ps5.execute(sql5);
						}else if(r.getTipoContrato() == 2){
							sql6 = "INSERT INTO sw_movimientoPrevired"
									+ "(cod_trabajador, id_contrato, periodo, movimento_previred, id_sociedad, fecha_movimiento)"
									+ "VALUES ("+r.getCodigo_trabajador()+", (select max(id) from contratos),DATE_FORMAT('"+r.getFechaactividad()+"', '%Y%m'), '5', "+r.getIdSociedad()+", '"+r.getFechaactividad()+"')";
							System.out.println(sql6);
							ps6 = db.conn.prepareStatement(sql6);
							ps6.execute(sql6);
						}else if(r.getTipoContrato() == 3){
							sql7 = "INSERT INTO sw_movimientoPrevired"
									+ "(cod_trabajador, id_contrato, periodo, movimento_previred, id_sociedad, fecha_movimiento,fecha_termino)"
									+ "VALUES ("+r.getCodigo_trabajador()+", (select max(id) from contratos),DATE_FORMAT('"+r.getFechaactividad()+"', '%Y%m'), '7', "+r.getIdSociedad()+", '"+r.getFechaactividad()+"', '"+r.getFechaTermino()+"')";
							System.out.println(sql7);
							ps7 = db.conn.prepareStatement(sql7);
							ps7.execute(sql7);
						}
						}else{
							
						}
						
						

						 
						
						
						
						
						
						
						while(rs.next()){
							
							tr.setCodigoTrabajador(rs.getString("codigoTrabajador"));
							tr.setCiudadContrato(rs.getString("ciudadContrato"));
							tr.setNombreEmpresa(rs.getString("nombreEmpresa"));
							tr.setAppPatTrabajador(rs.getString("appPatTrabajador"));
							tr.setAppMaternoTrabajador(rs.getString("appMaternoTrabajador"));
							tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador"));
							tr.setEstadoCivil(rs.getString("estadoCivil"));
							tr.setFechaNacimientoTrabajador(rs.getString("fechaNacimientoTrabajador"));
							tr.setNacionalidadTrabajador(rs.getString("nacionalidadTrabajador"));
							tr.setDireccionTrabajador(rs.getString("direccionTrabajador"));
							tr.setComunaTrabajador(rs.getString("comuna"));
							tr.setCargoTrabajador(rs.getString("cargoTrabajador"));
							tr.setFaenaContratacion(rs.getString("faenaContratacion"));
							tr.setSueldoCPuntosTrabajador(rs.getString("SueldoCpuntosTrabajador"));
							tr.setFechaInicio(rs.getString("fechaInicio"));
							tr.setCiudadContrato(rs.getString("ciudadContrato"));
							tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador"));
						 
							
							
							
							return tr;
							
						}		
						
					}catch (SQLException e){
						System.out.println("Error: "+ e.getMessage());
					}catch (Exception e){
						System.out.println("Error: "+ e.getMessage());
					}finally {
						ps.close();
						ps2.close();
						ps3.close();
						ps4.close();
						db.close();
					}
					return null;
				}
				
				//cargar saldo de lista de peticion
				public static ArrayList<ListaSolicitudes> saldoNotificacion(int codigo_P, int id_P) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<ListaSolicitudes> lista = new ArrayList<ListaSolicitudes>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select P.id_reclutamiento,P.id_peticion,sum(P.cantidad) as cantidad_total,P.obra as faena, P.fecha_inicio,R.usuario, (select count(*)from preseleccionados where codigo_peticion = P.id_reclutamiento  and status = 'Seleccionado') as seleccionado,	(select count(*) from preseleccionados where codigo_peticion = P.id_reclutamiento	and status = 'Preseleccionado') as preseleccionado, sum(P.cantidad) - (select sum((SELECT count(status) from preseleccionados where status = 'Contratado' and id_peticion =  P.id_peticion and codigo_peticion = P.id_reclutamiento)) )as saldo from peticion_trabajador P join reclutamiento_c R on P.id_reclutamiento = R.id_reclutamiento where R.estado = 1 and R.id_reclutamiento = "+codigo_P+" and P.id_peticion = "+id_P+" group by P.obra,P.id_reclutamiento,P.fecha_inicio,P.id_peticion";
						
						 
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							ListaSolicitudes hd = new ListaSolicitudes();
							hd.setTotal_saldo(rs.getInt("saldo"));
							hd.setCantidad_total(rs.getInt("cantidad_total"));
							
							lista.add(hd);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
				//cambiar estado peticion pagina lista preseleccion
				public static boolean updateListadoP(NotificacionContrato map) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "UPDATE peticion_trabajador SET estado_peticion = 0 WHERE id_peticion = ? and id_reclutamiento = ?";
						
						ps = db.conn.prepareStatement(sql);
						ps.setInt(1, map.getId_peticion());
						ps.setInt(2, map.getCodigo_peticion());
						
						
						
						ps.execute();
						return true;
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						ps.close();
						db.conn.close();
					}
					return false;
				}
				
				// saldo por orden
				public static ArrayList<ListaSolicitudes> saldoxOrden(int codigo_P, int id_P) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<ListaSolicitudes> lista = new ArrayList<ListaSolicitudes>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select P.id_reclutamiento, sum(P.cantidad) as cantidad_total,P.obra as faena, P.fecha_inicio,R.usuario,(select count(*) from preseleccionados where codigo_peticion = P.id_reclutamiento and status = 'Seleccionado') as seleccionado, (select count(*) from preseleccionados where codigo_peticion = P.id_reclutamiento and status = 'Preseleccionado') as preseleccionado, sum(P.cantidad) - (select sum((SELECT count(status) from preseleccionados where status = 'Contratado' and id_peticion =  P.id_peticion and codigo_peticion = P.id_reclutamiento)))as saldo from peticion_trabajador P join reclutamiento_c R on P.id_reclutamiento = R.id_reclutamiento where R.estado = 1 and R.id_reclutamiento = "+codigo_P+" group by P.obra,P.id_reclutamiento,P.fecha_inicio ";
						
						 
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							ListaSolicitudes hd = new ListaSolicitudes();
							hd.setTotal_saldo(rs.getInt("saldo"));
							hd.setCantidad_total(rs.getInt("cantidad_total"));
							
							lista.add(hd);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
				//cambiar estado orden cuando se completa el total de la cantidad de personas solicitadas

				public static boolean updateListadoO(NotificacionContrato map) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB db = new ConnectionDB();
					try{
						sql = " UPDATE reclutamiento_c SET estado = 0 WHERE id_reclutamiento = ?";
						
						ps = db.conn.prepareStatement(sql);
						ps.setInt(1, map.getCodigo_peticion());
						
						
						
						ps.execute();
						return true;
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						ps.close();
						db.conn.close();
					}
					return false;
				}
				
				// devolver seleccionados a preseleccionados 
				public static ArrayList<preseleccionados> devolverSelec(int codigo_P, int id_P) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<preseleccionados> lista = new ArrayList<preseleccionados>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select distinct  P.codigo_trabajador,(select nombre from trabajadores where codigo = codigo_trabajador) as nombre,(select cargo from peticion_trabajador where id_peticion = "+id_P+") as cargo,(select posicion from peticion_trabajador where id_peticion = "+id_P+") as posicion,(select telefono from trabajadores where codigo = codigo_trabajador) as telefono,P.fecha_inicio,P.id_peticion,P.codigo_peticion from preseleccionados P	where P.codigo_peticion = "+codigo_P+" and P.id_peticion = "+id_P+" and P.status = 'Seleccionado' GROUP BY P.codigo_trabajador,P.fecha_inicio";
						
						 
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							preseleccionados hd = new preseleccionados();
							hd.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
							
							
							lista.add(hd);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
				
				// update trabajadores que no fueron contratados  por que ya se culmplio el total requerido
				// volver al estado para preseleccionarlo para otra orden de reclutamiento
				
				public static boolean UTraNoSelec(preseleccionados map) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB db = new ConnectionDB();
					try{
						sql = " UPDATE trabajadores SET estado_preselec = 0 WHERE codigo = ?";
						
						ps = db.conn.prepareStatement(sql);
						ps.setInt(1, map.getCodigo_trabajador());
						
						
						
						ps.execute();
						return true;
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						ps.close();
						db.conn.close();
					}
					return false;
				}
				// traspazar trabajador
				public static boolean traspazarTr (RechazoPreseleccionado r) throws Exception{
			      	Statement ps = null;
					Statement ps2 = null;
					String sql = "";
					String sql2 = "";
					ConnectionDB db = new ConnectionDB();
				
					try{
				
					
						sql = "UPDATE trabajadores SET" 
								+ " estado_preselec='0'"
								+ " WHERE codigo ='"+r.getCodigo()+ "'";
						
						
						sql2 = "UPDATE preseleccionados SET status = 'Traspazado', observacion = '"+r.getObservacion()+"' WHERE codigo_trabajador = "+r.getCodigo()+" AND codigo_peticion = " +r.getId_peticion()+ " and id_peticion = "+r.getCodigo_peticion()+"";

			     		ps = db.conn.prepareStatement(sql);
						ps2 = db .conn.prepareStatement(sql2);

						
						
			     		ps.execute(sql);
						ps2.execute(sql2);

						
						return true;
					}catch (SQLException e){
						System.out.println("Error: "+ e.getMessage());
					}catch (Exception e){
						System.out.println("Error: "+ e.getMessage());
					}finally {
						ps.close();
						ps2.close();

						db.close();
					}
					return false;
				}
				
				// Cargar todos los trabajadores asociados a una empresa
				public static ArrayList<LoadTrabajadorSociedad> getSociedadTrab(String idSociedad,String huerto, String zona, String ceco, int iduser,String estadoproceso,int rolPrivado) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select TR.codigo,"
								+ "TR.nombre,"
								+ "TR.apellidoPaterno,"
								+ "TR.apellidoMaterno, "
								+ "TR.division,TR.idSubDivision,"
								+ "TR.grupo,TR.idSubgrupo,"
								+ "TR.rut "
								+ "from trabajadores TR "
								+ "left join contratos  CO  on TR.codigo = CO.codigo_trabajador "
								+ "left join campo ca on ca.campo = TR.idHuerto "
								+ "where 1 = 1 " ; 
						
						
						if("null".equals(idSociedad)){}else{sql += " and idSociedad = "+idSociedad+"";}
						if("null".equals(huerto)){}else{sql += " and TR.idHuerto = '"+huerto+"'";}
						if("null".equals(zona)){}else{sql += " and TR.idZona = '"+zona+"'";}
						if("null".equals(ceco)){}else{sql += " and TR.idCECO = '"+ceco+"'";}
						if("null".equals(estadoproceso)){}else{sql += " and TR.estadoProceso = "+estadoproceso+" ";}
						
						if(rolPrivado == 1){
							sql += " and  TR.rolPrivado in (1,0) ";
						}else{
							sql += " and  TR.rolPrivado in (0) ";
						}
						
						sql += " and CO.EstadoContrato = 1 "
								+ "and ca.campo in ( select codigo_campo FROM usuario_campo where codigo_usuario = "+iduser+")"
								+ "group by CO.codigo_trabajador";
						

						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
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
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}
				
				// detalle trabajador para pantalla permiso y licencia
				public static ArrayList<LoadTrabajadorSociedad> getTrabajadorPermisolicencia(int codigo,int id ) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select TR.codigo,TR.rut,TR.nombre,TR.apellidoPaterno,TR.apellidoMaterno,TR.direccion,TR.telefono,(select denominacionSociedad from sociedad where idSociedad = "+id+" ) AS empresa,TR.division as idDivision,(select division_personal from division_personal where iddivision_personal = idDivision) as divisionName,TR.idSubDivision,(select subdivision_de_personal from subdivision_personal where idsubdivision_personal = TR.idSubDivision) as subdivisionName,TR.grupo as idGrupo,(select grupo from grupo where idgrupo = TR.grupo) as grupoName,TR.idSubGrupo,(select subgrupo from subgrupo where idsubgrupo = TR.idSubGrupo) as subgrupoName from contratos  CO inner join trabajadores TR on TR.codigo = CO.codigo_trabajador where idSociedad = "+id+" and TR.codigo = "+codigo+" and CO.EstadoContrato = 1 group by CO.codigo_trabajador;";
						
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							LoadTrabajadorSociedad e = new LoadTrabajadorSociedad();
							
							e.setCodigotrabajador(rs.getInt("codigo"));
							e.setRut(rs.getString("rut"));
							e.setNombre(rs.getString("nombre"));
							e.setApellidoPaterno(rs.getString("apellidoPaterno"));
							e.setApellidoMaterno(rs.getString("apellidoMaterno"));
							e.setDireccion(rs.getString("direccion"));
							e.setTelefono(rs.getString("telefono"));
							e.setEmpresa(rs.getString("empresa"));
							e.setIddivision(rs.getInt("idDivision"));
							e.setNombredivision(rs.getString("divisionName"));
							e.setIdsubdivision(rs.getInt("idSubDivision"));
							e.setNombresubdivision(rs.getString("subdivisionName"));
							e.setIdgrupo(rs.getInt("idGrupo"));
							e.setNombregrupo(rs.getString("grupoName"));
							e.setIdsubgrupo(rs.getInt("idSubGrupo"));
							e.setNombresubgrupo(rs.getString("subgrupoName"));

							data.add(e);
						}
						rs.close();
						ps.close();
						db.conn.close();
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}
				
				///cargar tabla pantalla permiso licencia
				public static ArrayList<tablaPermisoLicencia> getTablaPL(String codigo,int idAccion,int idEmpresa,String huerto, String zona, String ceco,int periodo ) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<tablaPermisoLicencia> data = new ArrayList<tablaPermisoLicencia>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select p.id,"
						   + "id_empresa,"
						   + "codigo_trabajador,"
						   + "(select nombre from trabajadores where codigo = codigo_trabajador)as nombretrab,"
						   + "(select apellidoPaterno from trabajadores where codigo = codigo_trabajador)as appaterno,"
						   + "(select apellidoMaterno from trabajadores where codigo = codigo_trabajador)as apmaterno,"
						   + "accion,"
						   + "(select descripcion from parametros where id = tipo_licencia) as tipo_licencia,"
						   + "(select descripcion from parametros where id = subtipo_licencia) as subtipo_licencia,"
						   + "(select descripcion from parametros where id = reposo) as nombre_reposo,"
						   + "reposo,"
						   + "incluye_feriados,"
						   + "fecha_desde,"
						   + "fecha_hasta,"
						   + "fecha_creacion,"
						   + "horas_inasistencia,"
						   + "dias_corridos,"
						   + "reposo,"
						   + "tipo_reposo,"
						   + "doctor,"
						   + "especialidad,"
						   + "tipo_licencia AS TipoLicenciaId,"
						   + "subtipo_licencia AS subTipoLicenciaId,"
						   + "ruta_archivo "
						   + "from permiso_licencia p "
						   + "INNER JOIN trabajadores tr on tr.codigo = p.codigo_trabajador where ";
						   
						   if(idAccion == 5){
							   sql += "accion in (1,2,3,4) ";
						   }else{
						   sql += "accion = "+idAccion+" ";}
						   if("null".equals(codigo)){}else{sql += "and codigo_trabajador = "+codigo+" ";}
						   if("null".equals(huerto)){}else{sql += " and tr.idHuerto = '"+huerto+"' ";}
						   if("null".equals(zona)){}else{sql += " and tr.idZona = '"+zona+"' ";}
						   if("null".equals(ceco)){}else{sql += " and tr.idCECO = '"+ceco+"' ";}
						   sql+= " and id_empresa ="+idEmpresa+" and DATE_FORMAT(fecha_desde, '%Y%m') <= "+periodo+" and  DATE_FORMAT(fecha_hasta, '%Y%m') >= "+periodo+"  ";
						   
						   if(idAccion == 5){
							   sql += "ORDER BY fecha_desde desc ";
						   }else{
							   sql +="ORDER BY p.id DESC";
						   }
						
					  System.out.println(sql);
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							tablaPermisoLicencia e = new tablaPermisoLicencia();
							
							e.setId_empresa(rs.getInt("id_empresa"));
							e.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
							e.setAccion(rs.getInt("accion"));
							e.setTipo_licencia(rs.getString("tipo_licencia"));
							e.setSubtipo_licencia(rs.getString("subtipo_licencia"));
							e.setIncluye_feriados(rs.getInt("incluye_feriados"));
							e.setFecha_desde(rs.getString("fecha_desde"));
							e.setFecha_hasta(rs.getString("fecha_hasta"));
							e.setFecha_creacion(rs.getString("fecha_creacion"));
							e.setHoras_inasistencia(rs.getInt("horas_inasistencia"));
							e.setDias_corridos(rs.getInt("dias_corridos"));
							e.setRuta_archivo(rs.getString("ruta_archivo"));
							e.setNombre_reposo(rs.getString("nombre_reposo"));
							e.setId(rs.getInt("id"));
							e.setSubtipo_licenciaid(rs.getInt("subTipoLicenciaId"));
							e.setTipo_licenciaid(rs.getInt("TipoLicenciaId"));
							e.setReposo(rs.getInt("reposo"));
							e.setDoctor(rs.getString("doctor"));
							e.setEspecialidad(rs.getString("especialidad"));
							e.setTipo_reposo(rs.getInt("tipo_reposo"));
							e.setNombrecompleto(rs.getString("appaterno")+" "+rs.getString("apmaterno")+" "+rs.getString("nombretrab"));
							data.add(e);
						}
						rs.close();
						ps.close();
						db.conn.close();
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}
				public static boolean insertarPermisoConGoce (tablaPermisoLicencia r) throws Exception{
					
					PreparedStatement ps = null;
					String sql="";
					ConnectionDB db = new ConnectionDB();
					
					try{
						sql = "INSERT INTO permiso_licencia (id_empresa, codigo_trabajador, accion, fecha_desde, fecha_hasta, fecha_creacion,dias_corridos, ruta_archivo,idContrato,permisos_legales,permisos_convencionales) VALUES (?,?,?,?,?,NOW(),?,?,?,?,?)";
						ps = db.conn.prepareStatement(sql);
						
						ps.setInt    (1,  r.getId_empresa());
						ps.setInt    (2,  r.getCodigo_trabajador());
						ps.setInt    (3,  r.getAccion());
						ps.setString (4,  r.getFecha_desde());
						ps.setString (5,  r.getFecha_hasta());	
						ps.setInt    (6, r.getDias_corridos());
						ps.setString (7, r.getRuta_archivo());
						ps.setInt    (8, r.getIdContrato());
						ps.setInt    (9, r.getPermisolegal());
						ps.setInt    (10, r.getPermisoconvencional());
						ps.execute();
						
						return true;
					}catch (SQLException e){
						System.out.println("Error: "+ e.getMessage());
					}catch (Exception e){
						System.out.println("Error: "+ e.getMessage());
					}finally {
						ps.close();
					
						db.close();
					}
					return false;
				}
				
				public static boolean insertarPermiso (tablaPermisoLicencia r) throws Exception{
					
					PreparedStatement ps = null;
					String sql="";
					ConnectionDB db = new ConnectionDB();
					
					try{
						sql = "INSERT INTO permiso_licencia (id_empresa, codigo_trabajador, accion,incluye_feriados, fecha_desde, fecha_hasta, fecha_creacion,dias_corridos, ruta_archivo,idContrato) VALUES (?,?,?,?,?,?,NOW(),?,?,?)";
						ps = db.conn.prepareStatement(sql);
						
						
						ps.setInt    (1,  r.getId_empresa());
						ps.setInt    (2,  r.getCodigo_trabajador());
						ps.setInt    (3,  r.getAccion());
						ps.setInt    (4,  r.getIncluye_feriados());
						ps.setString (5,  r.getFecha_desde());
						ps.setString (6,  r.getFecha_hasta());	
						ps.setInt    (7, r.getDias_corridos());
						ps.setString (8, r.getRuta_archivo());
						ps.setInt    (9, r.getIdContrato());
						ps.execute();
						
						return true;
					}catch (SQLException e){
						System.out.println("Error: "+ e.getMessage());
					}catch (Exception e){
						System.out.println("Error: "+ e.getMessage());
					}finally {
						ps.close();
					
						db.close();
					}
					return false;
				}
//---------  
				public static ArrayList<TipoLicencia> getTipoLicencia()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<TipoLicencia> lista = new ArrayList<TipoLicencia>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'TIPO_LICENCIA' and activo = 1";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							TipoLicencia cr = new TipoLicencia();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
						
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
				
//-----------------------Listado Subtipo Licencia----------------------------
				
				
				
				public static ArrayList<TipoLicencia> getSubtipoLicencia()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<TipoLicencia> lista = new ArrayList<TipoLicencia>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'Sub_Tipo_Licencia' and activo = 1";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							TipoLicencia cr = new TipoLicencia();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
						
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//---------------------------------------------------------------------------------
				public static boolean insertarLicencia (tablaPermisoLicencia r) throws Exception{
					
					PreparedStatement ps = null;
					String sql="";
					ConnectionDB db = new ConnectionDB();
					
					try{
						sql = "INSERT INTO permiso_licencia (id_empresa, codigo_trabajador, accion, tipo_licencia, subtipo_licencia, incluye_feriados, fecha_desde, fecha_hasta, fecha_creacion, dias_corridos, ruta_archivo,reposo,doctor,especialidad,tipo_reposo,idContrato,numero_folio) VALUES (?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?)";
						ps = db.conn.prepareStatement(sql);
						
						
						
						ps.setInt    (1,  r.getId_empresa());
						ps.setInt    (2,  r.getCodigo_trabajador());
						ps.setInt    (3,  r.getAccion());
						ps.setInt    (4,  r.getTipo_licenciaid());
						ps.setInt    (5,  r.getSubtipo_licenciaid());
						ps.setInt    (6,  r.getIncluye_feriados());
						ps.setString (7,  r.getFecha_desde());
						ps.setString (8,  r.getFecha_hasta());	
						ps.setInt    (9, r.getDias_corridos());
						ps.setString (10, r.getRuta_archivo());
						ps.setInt    (11, r.getReposo());
						ps.setString (12, r.getDoctor());
						ps.setString (13, r.getEspecialidad());
						ps.setInt    (14, r.getTipo_reposo());
						ps.setInt    (15, r.getIdContrato());
						ps.setString    (16, r.getNumerofolio());
						ps.execute();
						
						return true;
					}catch (SQLException e){
						System.out.println("Error: "+ e.getMessage());
					}catch (Exception e){
						System.out.println("Error: "+ e.getMessage());
					}finally {
						ps.close();
					
						db.close();
					}
					return false;
				}
//-----------------eliminar habers y descuento----------------------------
				public static boolean eliminarHD(int id) throws  Exception{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB  db = new ConnectionDB();	
					try {
						sql = "DELETE FROM sw_haberesDescuentos WHERE id="+id+"";
						ps = db.conn.prepareStatement(sql);
						ps.execute();
								
						return true;
					} catch (SQLException e) {
						System.out.println("Error:" + e.getMessage());
						e.printStackTrace();
					}catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return false;
				}
//--------------------load Tipo de Pago--------------------------------------------------------
				public static ArrayList<CargarTipodePago> getTipoPago()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'TIPO_PAGO'  AND activo = 1  ORDER BY descripcion;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load lista de división-----------------------------------------------
				public static ArrayList<CargarTipodePago> getTipoDivision()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'DIVISION_PERSONAL'  AND activo = 1  ORDER BY descripcion;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load lista de sub-división-----------------------------------------------
				public static ArrayList<CargarTipodePago> getTipoSubDivision()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'SUBDIVISION_PERSONAL'  AND activo = 1  ORDER BY descripcion;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load lista Grupo-----------------------------------------------
				public static ArrayList<CargarTipodePago> getListaGrupo()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'GRUPO'  AND activo = 1  ORDER BY descripcion;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load lista SubGrupo-----------------------------------------------
				public static ArrayList<CargarTipodePago> getListaSubGrupo()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'SUBGRUPO'  AND activo = 1  ORDER BY descripcion;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load lista Frecuencia-----------------------------------------------
				public static ArrayList<CargarTipodePago> getListaFrecuencia()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'FRECUENCIA'  AND activo = 1  ORDER BY descripcion;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load Lista Tipo de Reposo-----------------------------------------------
				public static ArrayList<CargarTipodePago> getListaTipoReposo()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where llave = 3 and codigo = 'Sub_Tipo_Licencia' and activo = 1;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load Lista Tipo de Reposo Parcial-----------------------------------------------
				public static ArrayList<CargarTipodePago> getListaTipoReposoParcial()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where codigo = 'REPOSO_PARCIAL' and activo = 1;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load Lista Accidente Trabajo-----------------------------------------------
				public static ArrayList<CargarTipodePago> getListaAccidenteTrabajo()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where llave = 2 and codigo = 'Sub_Tipo_Licencia' and activo = 1;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//-------------------Load Lista Maternal-----------------------------------------------
				public static ArrayList<CargarTipodePago> getListaMaternal()  throws Exception{
					PreparedStatement ps = null;
					String sql="";
					ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "select * from parametros where llave = 1 and codigo = 'Sub_Tipo_Licencia' and activo = 1;";
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							CargarTipodePago cr = new CargarTipodePago();
							cr.setId(rs.getInt("id"));
							cr.setDescripcion(rs.getString("descripcion"));
		
							
							lista.add(cr);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}		
					return lista;
				}
//--------------insert Licencia Mutualidad----------------------------------------------------------------
				public static boolean insertarLicenciaMutualidad (tablaPermisoLicencia r) throws Exception{
					
					PreparedStatement ps = null;
					String sql="";
					ConnectionDB db = new ConnectionDB();
					
					try{
						sql = "INSERT INTO permiso_licencia (id_empresa, codigo_trabajador, accion,incluye_feriados, fecha_desde, fecha_hasta, fecha_creacion,dias_corridos,idContrato) VALUES (?,?,?,?,?,?,NOW(),?,?)";
						ps = db.conn.prepareStatement(sql);
						
						
						
						ps.setInt    (1,  r.getId_empresa());
						ps.setInt    (2,  r.getCodigo_trabajador());
						ps.setInt    (3,  r.getAccion());
						ps.setInt    (4,  r.getIncluye_feriados());
						ps.setString (5,  r.getFecha_desde());
						ps.setString (6,  r.getFecha_hasta());	
						ps.setInt    (7, r.getDias_corridos());
						ps.setInt    (8, r.getIdContrato());
						
						ps.execute();
						
						return true;
					}catch (SQLException e){
						System.out.println("Error: "+ e.getMessage());
					}catch (Exception e){
						System.out.println("Error: "+ e.getMessage());
					}finally {
						ps.close();
					
						db.close();
					}
					return false;
				}
//--- cargar todo de tabla contrato--------------------------------------------------------------------
				public static ArrayList<TodoTablaContrato> getIdContrato(int cod ) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<TodoTablaContrato> data = new ArrayList<TodoTablaContrato>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select  *,tr.agro, tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno from contratos co INNER JOIN trabajadores tr on tr.codigo = co.codigo_trabajador where co.codigo_trabajador = "+cod+"  and co.EstadoContrato = 1";
						
			
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							TodoTablaContrato e = new TodoTablaContrato();
							
							e.setId(rs.getInt("id"));
							e.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
							e.setId_peticion(rs.getInt("id_peticion"));
							e.setCod_peticion(rs.getInt("codigo_peticion"));
							e.setId_sociedad(rs.getInt("idSociedad"));
							e.setFecha_inicio_actividad(rs.getString("fechaInicio_actividad"));
							e.setFecha_contrato_emitido(rs.getString("fechaContrato_emitido"));
							e.setFecha_termino_contrato(rs.getString("FechaTerminoContrato"));
							e.setEstado_contrato(rs.getInt("EstadoContrato"));
							e.setAgro(rs.getInt("agro"));
							
							e.setNombre(rs.getString("nombre"));
							e.setApppaterno(rs.getString("apellidoPaterno"));
							e.setAppmaterno(rs.getString("apellidoMaterno"));
							
				
						
							data.add(e);
						}
						rs.close();
						ps.close();
						db.conn.close();
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}
//--- cargar todo id contrato para pantalla de listado de haberes y descuento--------------------------------------------------------------------
				public static ArrayList<TodoTablaContrato> getIdContratoPLHD(int cod ) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<TodoTablaContrato> data = new ArrayList<TodoTablaContrato>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select  idContrato, CO.fechaInicio_actividad from  sw_haberesDescuentos SW inner join contratos CO on SW.idContrato = CO.id where SW.codigo_trabajador = "+cod+" group by idContrato";
						
						
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							TodoTablaContrato e = new TodoTablaContrato();
							
							e.setId(rs.getInt("idContrato"));
							e.setFecha_inicio_actividad(rs.getString("fechaInicio_actividad"));
							
				
						
							data.add(e);
						}
						rs.close();
						ps.close();
						db.conn.close();
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}

//-----------------------cargar haberes y descuentos por Periodo -------------------------------------------------------------
				public static ArrayList<sw_haberesDescuentos> getswHDPeriodo(
						String periodo,
						String soci, 
						String idcontrato, 
						String codtrabajador,
						String huerto, 
						String zona, 
						String ceco, 
						String hdimput, 
						String concepto,
						String estado_proceso) throws Exception
				{
					PreparedStatement ps = null;
					String sql = "";
					
					PreparedStatement ps2 = null;
					String sql2 = "";
					
					PreparedStatement ps3 = null;
					String sql3 = "";
					
					ArrayList<sw_haberesDescuentos> lista = new ArrayList<sw_haberesDescuentos>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="SELECT distinct (select descripcion from  parametros where codigo = 'MONEDA_PLAN' and llave = sw.llave_moneda)as nombremoneda,sw.llave_moneda,sw.id,sw.periodo,"
								+ "sw.tipo,sw.codigo_hd,sw.monto,sw.idContrato,sw.proporcional as propor,"
								+ "sw.codigo_trabajador,"
								+ "sw.estadoCambio,"
								+ "(select descripcion from parametros where id = sw.frecuencia) as frecuencia,"
								+ "(select descripcion from sw_p_haberesDescuentos where codigo = sw.codigo_hd) as nombreCodigoHD,"
								+ "sw.frecuencia as idfrecuencia,sw.cuotas,sw.fecha_inicio,sw.fecha_termino,"
								+ "tr.nombre,tr.apellidoPaterno,tr.apellidoMaterno "
								+ "FROM sw_haberesDescuentos sw "
								+ "inner join  contratos CO on sw.codigo_trabajador = CO.codigo_trabajador "
								+ "inner join trabajadores tr on sw.codigo_trabajador = tr.codigo "
								+ "where 1 = 1 ";
						        
						
						
						if("null".equals(soci)){}else{sql += " and  CO.idSociedad = "+soci+"";}
						if("null".equals(idcontrato)){}else{sql += " and  sw.idContrato = "+idcontrato+"";}
						if("null".equals(codtrabajador)){}else{sql += " and  sw.codigo_trabajador = "+codtrabajador+"";}
						if("null".equals(huerto)){}else{sql += " and tr.idHuerto = '"+huerto+"'";}
						if("null".equals(zona)){}else{sql += " and tr.idZona = '"+zona+"'";}
						if("null".equals(ceco)){}else{sql += " and tr.idCECO = '"+ceco+"'";}
						if("null".equals(hdimput)){}else{sql += " and sw.tipo = '"+hdimput+"'";}
						if("null".equals(concepto)){}else{sql += " and sw.codigo_hd = "+concepto+"";}
						if("null".equals(estado_proceso)){}else{sql += " and tr.estadoProceso = "+estado_proceso+" ";}
						if("null".equals(periodo)){}else{sql += " and ("+periodo+" between  sw.fecha_inicio and sw.fecha_termino or sw.periodo = "+periodo+")";}
						
						System.out.println(sql);
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						
						if (!rs.isBeforeFirst()) {
							
							sql3 ="SELECT distinct (select descripcion from  parametros where codigo = 'MONEDA_PLAN' and llave = sw.llave_moneda)as nombremoneda,sw.llave_moneda,sw.id,sw.periodo,"
									+ "sw.tipo,sw.codigo_hd,sw.monto,sw.idContrato,sw.proporcional as propor,"
									+ "sw.codigo_trabajador,"
									+ "sw.estadoCambio,"
									+ "(select descripcion from parametros where id = sw.frecuencia) as frecuencia,"
									+ "(select descripcion from sw_p_haberesDescuentos where codigo = sw.codigo_hd) as nombreCodigoHD,"
									+ "sw.frecuencia as idfrecuencia,sw.cuotas,sw.fecha_inicio,sw.fecha_termino,"
									+ "tr.nombre,tr.apellidoPaterno,tr.apellidoMaterno "
									+ "FROM sw_haberesDescuentos sw "
									+ "inner join  contratos CO on sw.codigo_trabajador = CO.codigo_trabajador "
									+ "inner join trabajadores tr on sw.codigo_trabajador = tr.codigo "
									+ "where 1 = 1 ";
							        
							
							
							if("null".equals(soci)){}else{sql3 += " and  CO.idSociedad = "+soci+"";}
							if("null".equals(idcontrato)){}else{sql3 += " and  sw.idContrato = "+idcontrato+"";}
							if("null".equals(codtrabajador)){}else{sql3 += " and  sw.codigo_trabajador = "+codtrabajador+"";}
							if("null".equals(huerto)){}else{sql3 += " and tr.idHuerto = '"+huerto+"'";}
							if("null".equals(zona)){}else{sql3 += " and tr.idZona = '"+zona+"'";}
							if("null".equals(ceco)){}else{sql3 += " and tr.idCECO = '"+ceco+"'";}
							if("null".equals(hdimput)){}else{sql3 += " and sw.tipo = '"+hdimput+"'";}
							if("null".equals(concepto)){}else{sql3 += " and sw.codigo_hd = "+concepto+"";}
							if("null".equals(estado_proceso)){}else{sql3 += " and tr.estadoProceso = "+estado_proceso+" ";}
							
							System.out.println("sql3    "+sql3);
							ps3 = db.conn.prepareStatement(sql3);
							ResultSet rs3 = ps.executeQuery(sql3);
							
							
									System.out.println("aqui");
									while(rs3.next()){
										if(rs3.getString("frecuencia").equals("Indefinido")){
										sw_haberesDescuentos hd3 = new sw_haberesDescuentos();
										hd3.setId(rs3.getInt("id"));
										hd3.setPeriodo(rs3.getInt("periodo"));
										hd3.setTipo(rs3.getString("tipo"));
										hd3.setNombre(rs3.getString("nombre"));
										hd3.setApellidopaterno(rs3.getString("apellidoPaterno"));
										hd3.setApellidomaterno(rs3.getString("apellidoMaterno"));
										hd3.setCodigo_hd(rs3.getInt("codigo_hd"));
										hd3.setMonto2(rs3.getBigDecimal("monto"));
										hd3.setCodigo_trabajador(rs3.getString("codigo_trabajador"));
										hd3.setNombrecodigohd(rs3.getString("nombreCodigoHD"));
										hd3.setNombreFrecuencia(rs3.getString("frecuencia"));
										hd3.setIdfrecuencia(rs3.getInt("idfrecuencia"));
										hd3.setCuotas(rs3.getInt("cuotas"));
										hd3.setFechainicio(rs3.getInt("fecha_inicio"));
										hd3.setFechatermino(rs3.getInt("fecha_termino"));
										hd3.setLlavemoneda(rs3.getInt("llave_moneda"));
										hd3.setNombremoneda(rs3.getString("nombremoneda"));
										hd3.setIdcontrato(rs3.getInt("idContrato"));
										hd3.setProporcional(rs3.getInt("propor"));
										hd3.setEstadoCambio(rs3.getInt("estadoCambio"));
										
										lista.add(hd3);
										}
										
									}
						}else{
						
						while(rs.next()){
							sw_haberesDescuentos hd = new sw_haberesDescuentos();
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setTipo(rs.getString("tipo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setApellidopaterno(rs.getString("apellidoPaterno"));
							hd.setApellidomaterno(rs.getString("apellidoMaterno"));
							hd.setCodigo_hd(rs.getInt("codigo_hd"));
							hd.setMonto2(rs.getBigDecimal("monto"));
							hd.setCodigo_trabajador(rs.getString("codigo_trabajador"));
							hd.setNombrecodigohd(rs.getString("nombreCodigoHD"));
							hd.setNombreFrecuencia(rs.getString("frecuencia"));
							hd.setIdfrecuencia(rs.getInt("idfrecuencia"));
							hd.setCuotas(rs.getInt("cuotas"));
							hd.setFechainicio(rs.getInt("fecha_inicio"));
							hd.setFechatermino(rs.getInt("fecha_termino"));
							hd.setLlavemoneda(rs.getInt("llave_moneda"));
							hd.setNombremoneda(rs.getString("nombremoneda"));
							hd.setIdcontrato(rs.getInt("idContrato"));
							hd.setProporcional(rs.getInt("propor"));
							hd.setEstadoCambio(rs.getInt("estadoCambio"));
						
							lista.add(hd);
							
						}	
					}
						
						sql2 ="SELECT distinct (select descripcion from  parametros where codigo = 'MONEDA_PLAN' and llave = sw.llave_moneda)as nombremoneda,sw.llave_moneda,sw.id,sw.periodo,"
								+ "sw.tipo,sw.codigo_hd,sw.monto,sw.idContrato,sw.proporcional as propor,"
								+ "sw.codigo_trabajador,"
								+ "sw.estadoCambio,"
								+ "(select descripcion from parametros where id = sw.frecuencia) as frecuencia,"
								+ "(select descripcion from sw_p_haberesDescuentos where codigo = sw.codigo_hd) as nombreCodigoHD,"
								+ "sw.frecuencia as idfrecuencia,sw.cuotas,sw.fecha_inicio,sw.fecha_termino,"
								+ "tr.nombre,tr.apellidoPaterno,tr.apellidoMaterno "
								+ "FROM sw_haberesDescuentosHistorial sw "
								+ "inner join trabajadores tr on sw.codigo_trabajador = tr.codigo "
								+ "join contratos CO on sw.idContrato = CO.id "
								+ "where 1 = 1 ";
						        
						
						if("null".equals(periodo)){}else{sql2 += " and sw.periodo = "+periodo+"";}
						if("null".equals(soci)){}else{sql2 += " and  CO.idSociedad = "+soci+"";}
						if("null".equals(idcontrato)){}else{sql2 += " and  sw.idContrato = "+idcontrato+"";}
						if("null".equals(codtrabajador)){}else{sql2 += " and  sw.codigo_trabajador = "+codtrabajador+"";}
						if("null".equals(huerto)){}else{sql2 += " and tr.idHuerto = '"+huerto+"'";}
						if("null".equals(zona)){}else{sql2 += " and tr.idZona = '"+zona+"'";}
						if("null".equals(ceco)){}else{sql2 += " and tr.idCECO = '"+ceco+"'";}
						if("null".equals(hdimput)){}else{sql2 += " and sw.tipo = '"+hdimput+"'";}
						if("null".equals(concepto)){}else{sql2 += " and sw.codigo_hd = "+concepto+"";}
						
			
						ps2 = db.conn.prepareStatement(sql2);
						ResultSet rs2 = ps2.executeQuery(sql2);
						while(rs2.next()){
							sw_haberesDescuentos hd2 = new sw_haberesDescuentos();
							hd2.setId(rs2.getInt("id"));
							hd2.setPeriodo(rs2.getInt("periodo"));
							hd2.setTipo(rs2.getString("tipo"));
							hd2.setNombre(rs2.getString("nombre"));
							hd2.setApellidopaterno(rs2.getString("apellidoPaterno"));
							hd2.setApellidomaterno(rs2.getString("apellidoMaterno"));
							hd2.setCodigo_hd(rs2.getInt("codigo_hd"));
							hd2.setMonto2(rs2.getBigDecimal("monto"));
							hd2.setCodigo_trabajador(rs2.getString("codigo_trabajador"));
							hd2.setNombrecodigohd(rs2.getString("nombreCodigoHD"));
							hd2.setNombreFrecuencia(rs2.getString("frecuencia"));
							hd2.setIdfrecuencia(rs2.getInt("idfrecuencia"));
							hd2.setCuotas(rs2.getInt("cuotas"));
							hd2.setFechainicio(rs2.getInt("fecha_inicio"));
							hd2.setFechatermino(rs2.getInt("fecha_termino"));
							hd2.setLlavemoneda(rs2.getInt("llave_moneda"));
							hd2.setNombremoneda(rs2.getString("nombremoneda"));
							hd2.setIdcontrato(rs2.getInt("idContrato"));
							hd2.setProporcional(rs2.getInt("propor"));
							hd2.setEstadoCambio(rs2.getInt("estadoCambio"));
							
							
							
						
							lista.add(hd2);
							
						}
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						
						db.close();
					}
					return lista;
				}
//-----------------------insert Anticipos Individuales-------------------------------------------------------------

				public static boolean insertarAnticiposInd (AnticiposIndividuales r) throws Exception{
					Statement ps = null;
				
					String sql="";
					ConnectionDB db = new ConnectionDB();
					
					try{
						sql = "INSERT INTO sw_asignacionAnticipos (periodo , fecha, cod_trabajador , monto_ingresado,empresa,idContrato)";
						sql+= "VALUES ("+r.getPeriodo()+",'"+r.getFecha()+"',"+r.getCodtrabajador()+","+r.getMontoingresado()+","+r.getEmpresa()+","+r.getIdcontrato()+");";
										
						
						
						ps = db.conn.prepareStatement(sql);
						
						
						ps.execute(sql);
					
						
						return true;
					}catch (SQLException e){
						System.out.println("Error: "+ e.getMessage());
					}catch (Exception e){
						System.out.println("Error: "+ e.getMessage());
					}finally {
						ps.close();
					
						db.close();
					}
					return false;
				}
				
//-----------cargar popup pantalla asignacion simple-------------------------------------------------------------------------
				public static ArrayList<AnticiposIndividuales> getUpdateAnticiposIndividuales(int id) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select *, tr.nombre, tr.apellidoPaterno,tr.apellidoMaterno, tr.division, tr.idSubDivision as division ,tr.idSubDivision as subDivision, tr.grupo as grupo, tr.idSubGrupo as subgrupo  from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador  where sw.id = "+id+"";
			          
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							AnticiposIndividuales hd = new AnticiposIndividuales();
							
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setFecha(rs.getString("fecha"));
							hd.setCodtrabajador(rs.getInt("cod_trabajador"));
							hd.setMontoingresado(rs.getInt("monto_ingresado"));
							hd.setEmpresa(rs.getInt("empresa"));
							hd.setDivision(rs.getInt("division"));
							hd.setSubDivision(rs.getInt("subDivision"));
							hd.setGrupo(rs.getInt("grupo"));
							hd.setSubgrupo(rs.getInt("subgrupo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setAppaterno(rs.getString("apellidoPaterno"));
							hd.setAppmaterno(rs.getString("apellidoMaterno"));

							lista.add(hd);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
//------pantalla anticipos simple cargar por codigo trabajador ---------------------------------------------------------------------------------------				
				public static ArrayList<AnticiposIndividuales> getcargarXCodTrabajador(int cod) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select sw.id, sw.cod_trabajador, sw.periodo ,sw.fecha,sw.monto_ingresado,sw.empresa,tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno, tr.rut,tr.division,tr.idSubDivision as division ,tr.idSubDivision as subDivision,tr.grupo as grupo,tr.idSubGrupo as subgrupo from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador where sw.cod_trabajador = "+cod+"";
			          
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							AnticiposIndividuales hd = new AnticiposIndividuales();
							
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setFecha(rs.getString("fecha"));
							hd.setCodtrabajador(rs.getInt("cod_trabajador"));
							hd.setMontoingresado(rs.getInt("monto_ingresado"));
							hd.setEmpresa(rs.getInt("empresa"));
							hd.setDivision(rs.getInt("division"));
							hd.setSubDivision(rs.getInt("subDivision"));
							hd.setGrupo(rs.getInt("grupo"));
							hd.setSubgrupo(rs.getInt("subgrupo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setAppaterno(rs.getString("apellidoPaterno"));
							hd.setAppmaterno(rs.getString("apellidoMaterno"));
							hd.setRut(rs.getString("rut"));

							lista.add(hd);
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
//-----------Eliminar anticipo simple-----------------------------------------
				public static boolean DeleteAnticipoSimple(AnticiposIndividuales map) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "DELETE FROM sw_asignacionAnticipos WHERE id=?";
						ps = db.conn.prepareStatement(sql);
					
						ps.setInt(1, map.getId());
						
						
						ps.execute();
						return true;
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						ps.close();
						db.conn.close();
					}
					return false;
				}
//------Cargar todos los trabajadores asociados a una empresa y si tienen anticipos  ---------------------------------------------------------------------------------------				

				public static ArrayList<LoadTrabajadorSociedad> getSociedaTrabAS(String id,String div,String subdiv,String gru, String estado_proceso,int rolPrivado) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select TR.codigo,TR.nombre,TR.apellidoPaterno,"
								+ "TR.apellidoMaterno,TR.rut, TR.division,"
								+ "TR.idSubDivision,TR.grupo,TR.idSubgrupo "
								+ "from contratos  CO "
								+ "inner join trabajadores TR on TR.codigo = CO.codigo_trabajador "
								+ "join sw_asignacionAnticipos sw on TR.codigo = sw.cod_trabajador "
								+ "where 1 = 1 ";
						
						if("null".equals(id)){}else{sql += "and idSociedad = "+id+"";}
						if("null".equals(div)){}else{sql += " and TR.idHuerto = '"+div+"'";}
						if("null".equals(subdiv)){}else{sql += " and TR.idZona = '"+subdiv+"'";}
						if("null".equals(gru)){}else{sql += " and TR.idCECO = '"+gru+"'";}
						if("null".equals(estado_proceso)){}else{sql += " and TR.estadoProceso = "+estado_proceso+" ";}
						
						if(rolPrivado == 1){
							sql += " and  TR.rolPrivado in (1,0) ";
						}else{
							sql += " and  TR.rolPrivado in (0) ";
						}

						sql += " AND CO.EstadoContrato = 1 group by CO.codigo_trabajador";
						
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
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
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}
				//------Cargar todos los trabajadores asociados a una empresa y si tienen anticipos  ---------------------------------------------------------------------------------------				

				public static ArrayList<LoadTrabajadorSociedad> getSociedaTrabAS2(String empr,String huerto,String subdiv,String gru, String tipo_contrato,String fecha_pago,String estado_proceso, int rolPrivado) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
					ConnectionDB db = new ConnectionDB();
					
					huerto = huerto.replace("]", "','");
					
					try{
						
						sql = "select TR.codigo,upper(TR.nombre) as nombre,upper(TR.apellidoPaterno)as apellidoPaterno,upper(TR.apellidoMaterno) as apellidoMaterno,TR.rut, "
								+ "TR.division,TR.idSubDivision,TR.grupo,TR.idSubgrupo "
								+ "from contratos CO  "
								+ "join trabajadores TR on TR.codigo = CO.codigo_trabajador "
								+ "where 1 = 1 ";
						
						if("null".equals(empr)){}else{sql += "and CO.idSociedad = "+empr+"";}
						if("null".equals(subdiv)){}else{sql += " and TR.idZona = '"+subdiv+"'";}
						if("null".equals(gru)){}else{sql += " and TR.idCECO = '"+gru+"'";}
						if("null".equals(tipo_contrato)){}else{sql += " and CO.tipoContrato = "+tipo_contrato+"";}
						if("null".equals(fecha_pago)){}else{sql += " AND CO.fechaInicio_actividad <= '"+fecha_pago+"'  AND (CO.FechaTerminoContrato >= '"+fecha_pago+"' OR CO.FechaTerminoContrato IS NULL) ";}
						if("null".equals(estado_proceso)){}else{sql += " and TR.estadoProceso = "+estado_proceso+" ";}
						
						if(rolPrivado == 1){
							sql += " and  TR.rolPrivado in (1,0) ";
						}else{
							sql += " and  TR.rolPrivado in (0) ";
						}
						
					sql += " and CO.EstadoContrato = 1 and   CO.idHuertoContrato in ('"+huerto+"') group by CO.codigo_trabajador;";
						System.out.println(sql);
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
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
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}
//-----------cargar por tipo de pago  pantalla asignacion anticipos Simple-------------------------------------------------------------------------
				public static ArrayList<AnticiposIndividuales> getCodTrabAsignacionSimple(int p) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select *, tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador  where sw.cod_trabajador = "+p+"";
			          
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							AnticiposIndividuales hd = new AnticiposIndividuales();
							
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setFecha(rs.getString("fecha"));
							hd.setCodtrabajador(rs.getInt("cod_trabajador"));
							hd.setTipopago(rs.getInt("tipoPago"));
							hd.setMontoingresado(rs.getInt("monto_ingresado"));
							hd.setEmpresa(rs.getInt("empresa"));
							hd.setDivision(rs.getInt("division"));
							hd.setSubDivision(rs.getInt("subDivision"));
							hd.setGrupo(rs.getInt("grupo"));
							hd.setSubgrupo(rs.getInt("subgrupo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setAppaterno(rs.getString("apellidoPaterno"));
							hd.setAppmaterno(rs.getString("apellidoMaterno"));

							lista.add(hd);
							
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
//-----------cargar por fecha  pantalla asignacion anticipos Simple-------------------------------------------------------------------------
				public static ArrayList<AnticiposIndividuales> getFechaAsignacionSimple(String p) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<AnticiposIndividuales> lista = new ArrayList<AnticiposIndividuales>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select *, tr.nombre, tr.apellidoPaterno, tr.apellidoMaterno from sw_asignacionAnticipos sw inner join trabajadores tr on tr.codigo = sw.cod_trabajador  where sw.fecha = '"+p+"'";
			          
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							AnticiposIndividuales hd = new AnticiposIndividuales();
							
							hd.setId(rs.getInt("id"));
							hd.setPeriodo(rs.getInt("periodo"));
							hd.setFecha(rs.getString("fecha"));
							hd.setCodtrabajador(rs.getInt("cod_trabajador"));
							hd.setTipopago(rs.getInt("tipoPago"));
							hd.setMontoingresado(rs.getInt("monto_ingresado"));
							hd.setEmpresa(rs.getInt("empresa"));
							hd.setDivision(rs.getInt("division"));
							hd.setSubDivision(rs.getInt("subDivision"));
							hd.setGrupo(rs.getInt("grupo"));
							hd.setSubgrupo(rs.getInt("subgrupo"));
							hd.setNombre(rs.getString("nombre"));
							hd.setAppaterno(rs.getString("apellidoPaterno"));
							hd.setAppmaterno(rs.getString("apellidoMaterno"));

							lista.add(hd);
							
						}			
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return lista;
				}
//-----------Update anticipo simple-----------------------------------------
				public static boolean UpdateAnticipoSimple(AnticiposIndividuales map) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB db = new ConnectionDB();
					try{
						sql = "UPDATE sw_asignacionAnticipos SET periodo = ?, fecha = ?, monto_ingresado = ? WHERE id= ?";
						ps = db.conn.prepareStatement(sql);
	
						ps.setInt(1,map.getPeriodo());
						ps.setString(2,map.getFecha());
						ps.setInt(3,map.getMontoingresado());
						ps.setInt(4, map.getId());
						
						
						ps.execute();
						return true;
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						ps.close();
						db.conn.close();
					}
					return false;
				}
//--- get codigo trabajador x rut--------------------------------------------------------------------
				public static ArrayList<TodoTablaContrato> getcodxrut(String rut ) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					ArrayList<TodoTablaContrato> data = new ArrayList<TodoTablaContrato>();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="select codigo from trabajadores where rut = '"+rut+"'";
						
						ps = db.conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery(sql);
						while(rs.next()){
							TodoTablaContrato e = new TodoTablaContrato();
						  
							e.setCodigo_trabajador(rs.getInt("codigo"));
							data.add(e);
						}
						rs.close();
						ps.close();
						db.conn.close();
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}	
//--- crear liquidacion--------------------------------------------------------------------
				public static ArrayList<CreateLiquidacion> getCreateLiquidacion(int cod, int idcontrato,int periodo) throws Exception{

					ArrayList<CreateLiquidacion> data = new ArrayList<CreateLiquidacion>();
					ConnectionDB db = new ConnectionDB();
					try{
						CallableStatement cStmt = db.conn.prepareCall("{call SAN_CLEMENTE.sw_createLiquidacion(?, ?, ?,0,1)}");
						
						  cStmt.setInt(1, cod);
						  cStmt.setInt(2, idcontrato);
						  cStmt.setInt(3, periodo);
				          cStmt.execute();
				          ResultSet rs = cStmt.getResultSet();
						
						while(rs.next()){
							CreateLiquidacion e = new CreateLiquidacion();
						
							e.setCodTrabajador(rs.getInt("codTrabajador"));
							e.setIdContrato(rs.getInt("idContrato"));
							e.setIdConcepto(rs.getInt("idConcepto"));
							e.setDescripcion(rs.getString("descripcion"));
							e.setValor(rs.getString("valor"));
						
							data.add(e);
						}
						rs.close();
						db.conn.close();
					}catch(Exception ex){
						System.out.println("Error: "+ex.getMessage());
					}finally{
						db.close();
					}
					return data;
				}
				
public static tablaPermisoLicencia getRutaTablapermisoLicencia(String idruta) throws Exception{
					PreparedStatement ps = null;
					String sql = "";
					tablaPermisoLicencia permiso = new tablaPermisoLicencia();
					ConnectionDB db = new ConnectionDB();
					try{
						sql ="SELECT ruta_archivo FROM permiso_licencia where id = "+idruta+"";
                        System.out.println(sql);
						ps = db.conn.prepareStatement(sql);
		
						ResultSet rs = ps.executeQuery(sql);
						if(rs.next()){
							
							permiso.setRuta_archivo(rs.getString("ruta_archivo"));
							
						}
						
					}catch (SQLException e){
						System.out.println("Error: " + e.getMessage());
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
					}finally {
						ps.close();
						db.close();
					}
					return permiso;
					
				}
public static ArrayList<sw_haberesDescuentos> getswHDNombreTrabajador2(int codigo) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<sw_haberesDescuentos> lista = new ArrayList<sw_haberesDescuentos>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select nombre,apellidoPaterno,apellidoMaterno from trabajadores where codigo = "+codigo+";";

		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			sw_haberesDescuentos hd = new sw_haberesDescuentos();
			
			hd.setNombre(rs.getString("nombre"));
			hd.setApellidopaterno(rs.getString("apellidoPaterno"));
			hd.setApellidomaterno(rs.getString("apellidoMaterno"));
		
			lista.add(hd);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return lista;
}

public static ArrayList<sw_haberesDescuentos> getswHDNombreTrabajador(int codigo) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<sw_haberesDescuentos> lista = new ArrayList<sw_haberesDescuentos>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select tr.nombre,tr.apellidoPaterno,tr.apellidoMaterno, co.id as idcontra from trabajadores tr inner join contratos co on codigo_trabajador = tr.codigo where tr.codigo = "+codigo+";";

		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			sw_haberesDescuentos hd = new sw_haberesDescuentos();
			
			hd.setNombre(rs.getString("nombre"));
			hd.setApellidopaterno(rs.getString("apellidoPaterno"));
			hd.setApellidomaterno(rs.getString("apellidoMaterno"));
			hd.setIdcontrato(rs.getInt("idcontra"));
			
			lista.add(hd);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return lista;
}

	public static GetDatosContratoTrabajador obtenerDatosTrabajador(String idTrabajador) throws Exception{
		Statement ps = null;		
		String sql="";
		ConnectionDB db = new ConnectionDB();
		GetDatosContratoTrabajador tr = new GetDatosContratoTrabajador();
		
		try{
			sql = "select tr.codigo as codigoTrabajador, soc.denominacionSociedad as nombreEmpresa, "
				+ "tr.apellidoPaterno as appPatTrabajador,tr.apellidoMaterno as appMaternoTrabajador, "
				+ "tr.rut as rutCompletoTrabajador,tr.nombre as nombreTrabajador, 'Soltero' as estadoCivil, "
				+ "tr.fNacimiento as fechaNacimientoTrabajador, nac.pais as nacionalidadTrabajador, "
				+ "tr.direccion as direccionTrabajador, com.nombre as comuna, "
				+ "cntt.cargo as cargoTrabajador, 'PODA' as faenaContratacion, "
				+ "'2018' as temporadaContratacion, cntt.sueldoBase as sueldoCpuntosTrabajador,  "
				+ "'Talca' as ciudadContrato, soc.rut as rutEmpresa, 'Trescientos Mil Pesos' as sueldoEnPalabrasTrabajador, "
				+ "cntt.fechaInicio_actividad as fechaInicio "
				+ "from trabajadores tr "
				+ "inner join contratos cntt on tr.codigo = cntt.codigo_trabajador and cntt.id in  "
				+ "		(select max(id) from contratos where codigo_trabajador = tr.codigo) "
				+ "inner join sociedad soc on cntt.idSociedad = soc.idSociedad "
				+ "inner join parametros nac on tr.idNacionalidad = nac.llave and nac.codigo = 'NACIONALIDAD' "
				+ "inner join comuna com on tr.idcomuna = com.id "
				+ "where tr.id = "+ idTrabajador;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){				
				tr.setCodigoTrabajador(rs.getString("codigoTrabajador"));
				tr.setCiudadContrato(rs.getString("ciudadContrato"));
				tr.setNombreEmpresa(rs.getString("nombreEmpresa"));
				tr.setAppPatTrabajador(rs.getString("appPatTrabajador"));
				tr.setAppMaternoTrabajador(rs.getString("appMaternoTrabajador"));
				tr.setNombreTrabajador(rs.getString("nombreTrabajador"));
				tr.setRutCompletoTrabajador(rs.getString("rutCompletoTrabajador"));
				tr.setEstadoCivil(rs.getString("estadoCivil"));
				tr.setFechaNacimientoTrabajador(rs.getString("fechaNacimientoTrabajador"));
				tr.setNacionalidadTrabajador(rs.getString("nacionalidadTrabajador"));
				tr.setDireccionTrabajador(rs.getString("direccionTrabajador"));
				tr.setComunaTrabajador(rs.getString("comuna"));
				tr.setCargoTrabajador(rs.getString("cargoTrabajador"));
				tr.setFaenaContratacion(rs.getString("faenaContratacion"));
				tr.setTemporadaContratacion(rs.getString("temporadaContratacion"));
				tr.setSueldoCPuntosTrabajador(rs.getString("SueldoCpuntosTrabajador"));
				tr.setSueldoEnPalabrasTrabajador(rs.getString("sueldoEnPalabrasTrabajador"));
				tr.setFechaInicio(rs.getString("fechaInicio"));
				tr.setCiudadContrato(rs.getString("ciudadContrato"));
				tr.setRutEmpresa(rs.getString("rutEmpresa"));				
				return tr;
			}
			
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return tr;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
public static NominaAnticipos getUltimoIdSW() throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	NominaAnticipos permiso = new NominaAnticipos();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select max(id_nomina)as id from sw_nomina";

		ps = db.conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery(sql);
		if(rs.next()){
			
			permiso.setIdtablaswnomina(rs.getInt("id"));
		}
		
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return permiso;
	
}
////////////update nombre Archivos sw_nomina////////////////////////////////////////////
public static void updateNOmbreArchivoSW_nomina(String ruta, int id) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	
	ConnectionDB db = new ConnectionDB();
	String rutafinal = ruta.replace("\\", "\\\\");
	
	try{
		
		sql ="UPDATE sw_nomina SET ruta_detalle='"+rutafinal+"' WHERE id_nomina = "+id+";";
        System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ps.execute();
		
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
}

public static void updateNOmbreArchivoSW_nomina_excel(String ruta, int id) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	
	ConnectionDB db = new ConnectionDB();
	String rutafinal = ruta.replace("\\", "\\\\");

	try{
		
		sql ="UPDATE sw_nomina SET ruta_excel='"+rutafinal+"' WHERE id_nomina = "+id+";";
		ps = db.conn.prepareStatement(sql);
		ps.execute();
	
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
}

public static ArrayList<trabajadores> getallTrabajaCodNomHD(int cod)  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<trabajadores> lista = new ArrayList<trabajadores>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "select  TR.codigo,TR.nombre,TR.apellidoPaterno,TR.apellidoMaterno from contratos CO "
				+ "inner join trabajadores TR on TR.codigo = CO.codigo_trabajador "
				+ " left join sw_haberesDescuentos HD on HD.codigo_trabajador = CO.codigo_trabajador	"
				+ "where idSociedad = "+cod+" group by codigo";
		
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			trabajadores tr = new trabajadores();
			tr.setCodigo(rs.getString("codigo"));
			tr.setNombre(rs.getString("nombre"));
			tr.setAp_paterno(rs.getString("apellidoPaterno"));
			tr.setAp_materno(rs.getString("apellidoMaterno"));
			lista.add(tr);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}

//-------------------Load Tipo Moneda Pantalla Permiso y Licencia-----------------------------------------------
public static ArrayList<CargarTipodePago> TipoMonedaHD()  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<CargarTipodePago> lista = new ArrayList<CargarTipodePago>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "select * from parametros where codigo = 'MONEDA_PLAN'  AND activo = 1 AND llave in (4,2)  ORDER BY descripcion;";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			CargarTipodePago cr = new CargarTipodePago();
			cr.setId(rs.getInt("llave"));
			cr.setDescripcion(rs.getString("descripcion"));
			lista.add(cr);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}

public static ArrayList<sw_haberesDescuentos> getswHDPeriodoAll(String codtrabajador) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<sw_haberesDescuentos> lista = new ArrayList<sw_haberesDescuentos>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="SELECT (select descripcion from  parametros where codigo = 'MONEDA_PLAN' and llave = sw.llave_moneda)as nombremoneda,sw.llave_moneda,sw.id,sw.periodo,"
				+ "sw.tipo,sw.codigo_hd,sw.monto,sw.estadoCambio,"
				+ "sw.codigo_trabajador,"
				+ "(select descripcion from parametros where id = sw.frecuencia) as frecuencia,"
				+ "(select descripcion from sw_p_haberesDescuentos where codigo = sw.codigo_hd) as nombreCodigoHD,"
				+ "sw.frecuencia as idfrecuencia,sw.cuotas,sw.fecha_inicio,sw.fecha_termino,"
				+ "tr.nombre,tr.apellidoPaterno,tr.apellidoMaterno "
				+ "FROM sw_haberesDescuentos sw "
				+ "inner join trabajadores tr on sw.codigo_trabajador = tr.codigo "
				+ "join contratos CO on sw.idContrato = CO.id "
				+ "where 1 = 1 ";
		
		    sql += " and  sw.codigo_trabajador in ("+codtrabajador+")";
        
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			sw_haberesDescuentos hd = new sw_haberesDescuentos();
			hd.setId(rs.getInt("id"));
			hd.setPeriodo(rs.getInt("periodo"));
			hd.setTipo(rs.getString("tipo"));
			hd.setNombre(rs.getString("nombre"));
			hd.setApellidopaterno(rs.getString("apellidoPaterno"));
			hd.setApellidomaterno(rs.getString("apellidoMaterno"));
			hd.setCodigo_hd(rs.getInt("codigo_hd"));
			hd.setMonto2(rs.getBigDecimal("monto"));
			hd.setCodigo_trabajador(rs.getString("codigo_trabajador"));
			hd.setNombrecodigohd(rs.getString("nombreCodigoHD"));
			hd.setNombreFrecuencia(rs.getString("frecuencia"));
			hd.setIdfrecuencia(rs.getInt("idfrecuencia"));
			hd.setCuotas(rs.getInt("cuotas"));
			hd.setFechainicio(rs.getInt("fecha_inicio"));
			hd.setFechatermino(rs.getInt("fecha_termino"));
			hd.setLlavemoneda(rs.getInt("llave_moneda"));
			hd.setNombremoneda(rs.getString("nombremoneda"));
			hd.setEstado_cambio(rs.getInt("estadoCambio"));
		
			lista.add(hd);
			
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return lista;
}

//-----------------eliminar habers y descuento----------------------------
public static boolean eliminarPL(int id) throws  Exception{
	PreparedStatement ps = null;
	String sql = "";
	ConnectionDB  db = new ConnectionDB();	
	try {
		sql = "DELETE FROM permiso_licencia WHERE id="+id+"";
		ps = db.conn.prepareStatement(sql);
		ps.execute();
				
		return true;
	} catch (SQLException e) {
		System.out.println("Error:" + e.getMessage());
		e.printStackTrace();
	}catch (Exception e) {
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return false;
}

//-------------------Load lista de Huerto-----------------------------------------------
public static ArrayList<sw_huerto> getHuertoLista()  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<sw_huerto> lista = new ArrayList<sw_huerto>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "select campo, descripcion from campo;";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			sw_huerto cr = new sw_huerto();
		    
			cr.setCampo(rs.getString("campo"));
			cr.setDescripcion(rs.getString("descripcion"));
			
			lista.add(cr);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}
//------Cargar todos los trabajadores asociados a una empresa y si tienen anticipos para imprimir ---------------------------------------------------------------------------------------				
public static ArrayList<LoadTrabajadorSociedad> getTrabajadoresAnticipoImprimir(int id, int rolPrivado ) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="SELECT sw.cod_trabajador, tr.nombre, "
				+ "tr.apellidoPaterno, "
				+ "tr.apellidoMaterno, tr.rut "
				+ "FROM sw_asignacionAnticipos sw "
				+ "inner join trabajadores tr  "
				+ "on  sw.cod_trabajador = tr.codigo "
				+ "WHERE estado = 1 AND empresa = "+id+" ";
				if(rolPrivado == 1){
					sql += " and  tr.rolPrivado in (1,0) ";
				}else{
					sql += " and  tr.rolPrivado in (0) ";
				}
				sql += "order by tr.apellidoPaterno asc";

		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			LoadTrabajadorSociedad e = new LoadTrabajadorSociedad();
			
			e.setCodigotrabajador(rs.getInt("cod_trabajador"));
			e.setNombre(rs.getString("nombre"));
			e.setApellidoPaterno(rs.getString("apellidoPaterno"));
			e.setApellidoMaterno(rs.getString("apellidoMaterno"));
		    e.setRut(rs.getString("rut"));
		
			data.add(e);
		}
		rs.close();
		ps.close();
		db.conn.close();
	}catch(Exception ex){
		System.out.println("Error: "+ex.getMessage());
	}finally{
		db.close();
	}
	return data;
}
// PERMISOS LEGALES
public static ArrayList<TipoLicencia> getPermisoslegales()  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<TipoLicencia> lista = new ArrayList<TipoLicencia>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "SELECT * FROM parametros WHERE codigo = 'PERMISOS_LEGALES' and activo = 1";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			TipoLicencia cr = new TipoLicencia();
			cr.setId(rs.getInt("llave"));
			cr.setDescripcion(rs.getString("descripcion"));
		
			lista.add(cr);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}

//PERMISOS CONVENCIONALES
public static ArrayList<TipoLicencia> getPermisosConvencionales()  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<TipoLicencia> lista = new ArrayList<TipoLicencia>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "SELECT * FROM parametros WHERE codigo = 'PERMISOS_CONVENCIONALES' and activo = 1";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			TipoLicencia cr = new TipoLicencia();
			cr.setId(rs.getInt("llave"));
			cr.setDescripcion(rs.getString("descripcion"));
		
			lista.add(cr);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}

//--- BUSCAR HUERTO TRABAJADOR-------------------------------------------------------------------
public static ArrayList<TodoTablaContrato> getHuertoTrab(int cod ) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<TodoTablaContrato> data = new ArrayList<TodoTablaContrato>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select idHuerto,id,agro from SAN_CLEMENTE.trabajadores idHuerto where codigo = "+cod+"";
		
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			TodoTablaContrato e = new TodoTablaContrato();
		
			e.setIdhuerto(rs.getString("idHuerto"));
		    e.setId(rs.getInt("id"));
		    e.setAgro(rs.getInt("agro"));
			data.add(e);
		}
		rs.close();
		ps.close();
		db.conn.close();
	}catch(Exception ex){
		System.out.println("Error: "+ex.getMessage());
	}finally{
		db.close();
	}
	return data;
}

public static boolean insertarPermisoRendimientoDiario (tablaPermisoLicencia r) throws Exception{
	Statement ps2 = null;
	String sql2 = "";
	
	ConnectionDB db = new ConnectionDB();
	try{
		
    if(r.getValorchecked() == 1){
    	
    	sql2 = "insert into rendimiento_diario"
    			+ "(estado, valor_liquido,fecha_i,codigo_rg,idContrato,trabajador,tipo_permiso,cargo,campo_rd,labor,cuartel,supervisor_i) "
    			+ "values(3,CAST(("+r.getTotalliquidodia()+"/getDiasHabiles(DATE_FORMAT('"+r.getFechapermiso()+"', '%Y%m')))as SIGNED),'"+r.getFechapermiso()+"',0,"
    			+ "(SELECT MAX(id) FROM contratos WHERE codigo_trabajador =  "+r.getCodigo_trabajador()+"),"+r.getIdtrabajador()+",1,"+r.getCargo()+",'"+r.getHuerto()+"',"+r.getLabor()+","+r.getCuartel()+",(select max(supervisor_i) from rendimiento_diario where trabajador = (SELECT MAX(id) FROM contratos WHERE codigo_trabajador =  "+r.getCodigo_trabajador()+") ))";

    	ps2 = db .conn.prepareStatement(sql2);
    	ps2.execute(sql2);
		System.out.println(sql2);
    }else{
    	if(r.getMacro().equals("1")){
    		
    		sql2 = "insert into rendimiento_diario"
    				+ "(estado, valor_liquido,fecha_i,codigo_rg,idContrato,trabajador,tipo_permiso,cargo,campo_rd,macroco,ceco,cuartel,rd_contratista,supervisor_i) "
    				+ "values(3,CAST(("+r.getTotalliquidodia()+"/getDiasHabiles(DATE_FORMAT('"+r.getFechapermiso()+"', '%Y%m')))as SIGNED),'"+r.getFechapermiso()+"',0,"
    				+ "(SELECT MAX(id) FROM contratos WHERE codigo_trabajador =  "+r.getCodigo_trabajador()+"),"+r.getIdtrabajador()+",1,"+r.getCargo()+",'"+r.getHuerto()+"',1,'"+r.getCeco()+"',0,'0',(select max(supervisor_i) from rendimiento_diario where trabajador = (SELECT MAX(id) FROM contratos WHERE codigo_trabajador =  "+r.getCodigo_trabajador()+") ))";
    		ps2 = db .conn.prepareStatement(sql2);
    		ps2.execute(sql2);
    		System.out.println(sql2);
    	}else{
    		
    		sql2 = "insert into rendimiento_diario"
    				+ "(estado, valor_liquido,fecha_i,codigo_rg,idContrato,trabajador,tipo_permiso,cargo,campo_rd,macroco,ordenco,cuartel,rd_contratista,supervisor_i) "
    				+ "values(3,CAST(("+r.getTotalliquidodia()+"/getDiasHabiles(DATE_FORMAT('"+r.getFechapermiso()+"', '%Y%m')))as SIGNED),'"+r.getFechapermiso()+"',0,"
    				+ "(SELECT MAX(id) FROM contratos WHERE codigo_trabajador =  "+r.getCodigo_trabajador()+"),"+r.getIdtrabajador()+",1,"+r.getCargo()+",'"+r.getHuerto()+"','"+r.getMacro()+"','"+r.getCeco()+"',0,'0',(select max(supervisor_i) from rendimiento_diario where trabajador = (SELECT MAX(id) FROM contratos WHERE codigo_trabajador =  "+r.getCodigo_trabajador()+") ))";
    		ps2 = db .conn.prepareStatement(sql2);
    		ps2.execute(sql2);
    		System.out.println(sql2);
    	}
    }
	
	
   
	
	
return true;
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
	
		ps2.close();
		db.close();
	}
	return false;
}
////////////cargar trabajadores con permisos y licencias////////////////////////////////////////////
public static ArrayList<LoadTrabajadorSociedad> getSociedadTrabListadoP(String idSociedad,String huerto, String zona, String ceco,String estado_proceso,int rolPrivado) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<LoadTrabajadorSociedad> data = new ArrayList<LoadTrabajadorSociedad>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select TR.codigo,"
				+"TR.nombre,"
				+"TR.apellidoPaterno,"
				+"TR.apellidoMaterno, "
				+"TR.rut,"
				+"CO.EstadoContrato "
				+"from permiso_licencia P "
				+"inner join trabajadores TR on TR.codigo = P.codigo_trabajador "
				+"inner join contratos CO on CO.codigo_trabajador = P.codigo_trabajador "
				+"where 1 = 1 ";
				
		
		
		if("null".equals(idSociedad)){}else{sql += " and CO.idSociedad = "+idSociedad+"";}
		if("null".equals(huerto)){}else{sql += " and TR.idHuerto = '"+huerto+"'";}
		if("null".equals(zona)){}else{sql += " and TR.idZona = '"+zona+"'";}
		if("null".equals(ceco)){}else{sql += " and TR.idCECO = '"+ceco+"'";}
		if("null".equals(estado_proceso)){}else{sql += " and TR.estadoProceso = "+estado_proceso+" ";}
		if(rolPrivado == 1){
			sql += " and  TR.rolPrivado in (1,0) ";
		}else{
			sql += " and  TR.rolPrivado in (0) ";
		}
		
		
		sql += " and CO.EstadoContrato = 1 group by CO.codigo_trabajador";
		
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
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
	}catch(Exception ex){
		System.out.println("Error: "+ex.getMessage());
	}finally{
		db.close();
	}
	return data;
}


public static boolean closeAndUpdateHD(UpdateTrabajadorHD r) throws  Exception{
	PreparedStatement ps = null;
	String sql = "";
	
	Statement ps2 = null;
	String sql2="";
	
	Statement ps3 = null;
	String sql3="";
	
	Statement ps4 = null;
	String sql4="";
	
	ConnectionDB  db = new ConnectionDB();	
	try {

		sql = "Update sw_haberesDescuentos set "
		    + "estadoCambio = 1 "	
			+ "where id = "+r.getId()+""; 
		
		ps = db.conn.prepareStatement(sql);
		ps.execute();
		
		sql2 = "INSERT INTO sw_haberesDescuentos (periodo , tipo, codigo_hd , monto , codigo_trabajador,frecuencia,cuotas,fecha_inicio,fecha_termino,idContrato,llave_moneda,proporcional)";
		sql2+= "VALUES ("+r.getPeriodo_t()+",'"+r.getTipo_t()+"',"+r.getCodigo_hd_t()+","+r.getMontonew()+","+r.getCod_t()+","+r.getFrecuencia_t()+","+r.getCuota()+","+r.getFecha_inicio()+","+r.getFecha_termino()+","+r.getId_contrato()+ ","+r.getIdmoneda()+","+r.getProporcional()+");";
		ps2 = db.conn.prepareStatement(sql2);
		ps2.execute(sql2);
		
		sql3 = "INSERT INTO sw_haberesDescuentosHistorial SELECT *FROM sw_haberesDescuentos WHERE id = "+r.getId()+""; 
			
			ps3 = db.conn.prepareStatement(sql3);
			ps3.execute(sql3);
			
		sql4 = "DELETE FROM sw_haberesDescuentos WHERE id = "+r.getId()+""; 
			
			ps4 = db.conn.prepareStatement(sql4);
			ps4.execute(sql4);
			
			
			

				
		return true;
	} catch (SQLException e) {
		System.out.println("Error:" + e.getMessage());
		e.printStackTrace();
	}catch (Exception e) {
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		ps2.close();
		db.close();
	}		
	return false;
}

//-----------------------cargar haberes y descuentos RETENCIONES -------------------------------------------------------------
public static ArrayList<sw_haberesDescuentos> getswHDRetenciones(String periodo,String soci, String hdimput, String concepto, int rolPrivado) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<sw_haberesDescuentos> lista = new ArrayList<sw_haberesDescuentos>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="SELECT "
					+"sw.codTrabajador,"
					+ "sw.periodo,"
				    +"UPPER(tr.nombre) as nombre,"
				    +"UPPER(tr.apellidoPaterno) as apellidoPaterno,"
				    +"UPPER(tr.apellidoMaterno) as apellidoMaterno,"
				    + "UPPER(sw.descripcion) AS descripcion,"
				    +"sw.valor + if((select sum(monto) from sw_haberesDescuentosFiniquito where codigo_trabajador = sw.codTrabajador and periodo = "+periodo+" and codigo_hd = 3019) is null,0,(select sum(monto) from sw_haberesDescuentosFiniquito where codigo_trabajador = sw.codTrabajador and periodo = "+periodo+" and codigo_hd = "+concepto+")) as valor,"
				    +"CASE "
				        +"WHEN tr.rut = '' THEN tr.rutTemporal "
				        +"ELSE tr.rut "
				    +"END AS rut,"
				    + "(select upper(denominacionSociedad) from sociedad where idSociedad = CO.idSociedad ) as nombresociedad "
				+"FROM "
				    +"sw_liquidacionDetalle sw "
				        +"INNER JOIN "
				    +"trabajadores tr ON sw.codTrabajador = tr.codigo "
				        +"JOIN "
				    +"contratos CO ON sw.idContrato = CO.id "
				+"WHERE "
				    +"1 = 1 " ;
				       
		if(rolPrivado == 1){
			sql += " and  tr.rolPrivado in (1,0) ";
		}else{
			sql += " and  tr.rolPrivado in (0) ";
		}		        
		        
		
		if("null".equals(periodo)){}else{sql += " and sw.periodo = "+periodo+"";}
		if("null".equals(soci)){}else{sql += " and  CO.idSociedad = "+soci+"";}
		if("null".equals(concepto)){}else{sql += " and sw.descripcion = (SELECT descripcion FROM sw_p_haberesDescuentos WHERE codigo = "+concepto+")";}
		System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			sw_haberesDescuentos hd = new sw_haberesDescuentos();
			
			hd.setCodigo_trabajador(rs.getString("codTrabajador"));
			hd.setRut(rs.getString("rut"));
			hd.setNombre(rs.getString("nombre"));
			hd.setApellidopaterno(rs.getString("apellidoPaterno"));
			hd.setApellidomaterno(rs.getString("apellidoMaterno"));
			hd.setPeriodo(rs.getInt("periodo"));
			hd.setNombrecodigohd(rs.getString("descripcion"));
			hd.setMonto2(rs.getBigDecimal("valor"));
			hd.setEmpresa(rs.getString("nombresociedad"));
			lista.add(hd);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}
	return lista;
}

public static String GenerarExcelHaberesdescuento(ArrayList<sw_haberesDescuentos> datos) throws Exception {

	String RutaArchivo = "";
	
	String ruta =  utils.FiniquitoTxt();


	try {

		////////////////////// EXCEL///////////////////////////

		// Creamos el archivo donde almacenaremos la hoja
		// de calculo, recuerde usar la extension correcta,
		// en este caso .xlsx
		
		
		String Nombrearchivo = "HaberesDescuento.xlsx";
		File archivo = new File("HaberesDescuento.xlsx");

		// Creamos el libro de trabajo de Excel formato OOXML
		Workbook workbook = new XSSFWorkbook();
        
		CellStyle style2 = workbook.createCellStyle();
		 DataFormat format=workbook.createDataFormat();
		style2.setDataFormat(format.getFormat("#,##0"));
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		//style2.setAlignment(CellStyle.ALIGN_RIGHT);
       
		
		// La hoja donde pondremos los datos
		Sheet pagina = workbook.createSheet("Hoja1");
		pagina.getPrintSetup().setLandscape(true);
		pagina.setFitToPage(true);
		PrintSetup ps = pagina.getPrintSetup();
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 0);

		Row fila = pagina.createRow(0);

			int numeroFor = 0;
		   	
		    String  periodo_;
		    String  cod_;
			String 	nombrecompleto = "";
			String 	tipo_ = "";
			String  nombrecodigohd_;
			
			String 	nombremoneda_ = "";
			String 	montostring_ = "";
			String 	nombreFrecuencia_ = "";
			String 	nombreCuotas_ = "";
			String 	fechaistring_ = "";
			String 	fechatstring_ = "";
			
			
		
			for (sw_haberesDescuentos datos2 : datos) {
				
				periodo_ = datos2.getPeriodoS();
				cod_= datos2.getCodigo_trabajador();
				nombrecompleto = datos2.getNombre();
				tipo_ = datos2.getTipo();
				nombrecodigohd_ = datos2.getNombrecodigohd();
				nombremoneda_ = datos2.getNombremoneda();
				montostring_ = datos2.getMontostring();
				nombreFrecuencia_ = datos2.getNombreFrecuencia();
				nombreCuotas_ = datos2.getNombreCuotas();
				fechaistring_ = datos2.getFechaistring();
				fechatstring_ = datos2.getFechatstring();
				
				String[] titulo7 = { periodo_ , cod_ , nombrecompleto,tipo_,nombrecodigohd_, nombremoneda_, montostring_ ,nombreFrecuencia_,
						nombreCuotas_ , fechaistring_, fechatstring_};
				
				
				Cell celda = fila.createCell(0);
				celda.setCellValue(titulo7[0]);
				
				Cell celda1 = fila.createCell(1);
				celda1.setCellValue(titulo7[1]);
				
				Cell celda2 = fila.createCell(2);
				celda2.setCellValue(titulo7[2]);
				
				Cell celda3 = fila.createCell(3);
				celda3.setCellValue(titulo7[3]);
			
				Cell celda4 = fila.createCell(4);
				celda4.setCellValue(titulo7[4]);
			
				Cell celda5 = fila.createCell(5);
				celda5.setCellValue(titulo7[5]);
			
				
				// monto
				if(numeroFor == 0){
				Cell celda6 = fila.createCell(6);
				celda6.setCellValue(titulo7[6]);
				}else{
					
					if(titulo7[5].equals("Pesos")){
						Cell celda6 = fila.createCell(6);
						celda6.setCellValue(Integer.parseInt(titulo7[6]));
						celda6.setCellStyle(style2);
					}else{
						Cell celda6 = fila.createCell(6);
						celda6.setCellValue(titulo7[6]);
					}
					
				}

				
				Cell celda7 = fila.createCell(7);
				celda7.setCellValue(titulo7[7]);
				
				Cell celda8 = fila.createCell(8);
				celda8.setCellValue(titulo7[8]);
			
				Cell celda9 = fila.createCell(9);
				celda9.setCellValue(titulo7[9]);
			
				Cell celda10 = fila.createCell(10);
				celda10.setCellValue(titulo7[10]);
				
				
				
				
				numeroFor = numeroFor + 1;
				fila = pagina.createRow(numeroFor);
			}
		
		

		pagina.autoSizeColumn(0);
		pagina.autoSizeColumn(1);
		pagina.autoSizeColumn(2);
		pagina.autoSizeColumn(3);
		pagina.autoSizeColumn(4);
		pagina.autoSizeColumn(5);
		pagina.autoSizeColumn(6);
		pagina.autoSizeColumn(7);
		pagina.autoSizeColumn(8);
		pagina.autoSizeColumn(9);
		pagina.autoSizeColumn(10);
		
	

		FileOutputStream salida = new FileOutputStream(ruta + archivo);
		RutaArchivo = ruta + Nombrearchivo;

		// Almacenamos el libro de
		// Excel via ese
		// flujo de datos
		workbook.write(salida);

		// Cerramos el libro para concluir operaciones
		workbook.close();

		// LOGGER.log(Level.INFO, "Archivo creado existosamente en {0}",
		// archivo.getAbsolutePath());
	} catch (FileNotFoundException ex) {
		// LOGGER.log(Level.SEVERE, "Archivo no localizable en sistema de
		// archivos");
	} catch (IOException e) {
		e.printStackTrace();
		// LOGGER.log(Level.SEVERE, "Error de entrada/salida");
		return "";
	}

	return RutaArchivo;
	
}
///cargar tabla pantalla permiso licencia
public static ArrayList<tablaPermisoLicencia> getTablaPL1(String codigo,int idAccion,int idEmpresa,String huerto, String zona, String ceco,int periodo,String estado_proceso ) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<tablaPermisoLicencia> data = new ArrayList<tablaPermisoLicencia>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select p.id,p.numero_folio,"
		   + "id_empresa,"
		   + "codigo_trabajador,"
		   + "(select nombre from trabajadores where codigo = codigo_trabajador)as nombretrab,"
		   + "(select apellidoPaterno from trabajadores where codigo = codigo_trabajador)as appaterno,"
		   + "(select apellidoMaterno from trabajadores where codigo = codigo_trabajador)as apmaterno,"
		   + "accion,"
		   + "(select descripcion from parametros where id = tipo_licencia) as tipo_licencia,"
		   + "(select descripcion from parametros where id = subtipo_licencia) as subtipo_licencia,"
		   + "(select descripcion from parametros where id = reposo) as nombre_reposo,"
		   + "reposo,"
		   + "incluye_feriados,"
		   + "fecha_desde,"
		   + "fecha_hasta,"
		   + "fecha_creacion,"
		   + "horas_inasistencia,"
		   + "dias_corridos,"
		   + "reposo,"
		   + "tipo_reposo,"
		   + "doctor,"
		   + "especialidad,"
		   + "tipo_licencia AS TipoLicenciaId,"
		   + "subtipo_licencia AS subTipoLicenciaId,"
		   + "ruta_archivo "
		   + "from permiso_licencia p "
		   + "INNER JOIN trabajadores tr on tr.codigo = p.codigo_trabajador where ";
		   
		   if(idAccion == 5){
			   sql += "accion in (1,2,3,4) ";
		   }else{
		   sql += "accion = "+idAccion+" ";}
		   if("null".equals(codigo)){}else{sql += "and codigo_trabajador = "+codigo+" ";}
		   if("null".equals(huerto)){}else{sql += " and tr.idHuerto = '"+huerto+"' ";}
		   if("null".equals(zona)){}else{sql += " and tr.idZona = '"+zona+"' ";}
		   if("null".equals(ceco)){}else{sql += " and tr.idCECO = '"+ceco+"' ";}
		   if("null".equals(estado_proceso)){}else{sql += " and tr.estadoProceso = "+estado_proceso+" ";}
		   sql+= " and id_empresa ="+idEmpresa+" and DATE_FORMAT(fecha_desde, '%Y%m') <= "+periodo+" and  DATE_FORMAT(fecha_hasta, '%Y%m') >= "+periodo+"  ";
		   
		   if(idAccion == 5){
			   sql += "ORDER BY fecha_desde desc ";
		   }else{
			   sql +="ORDER BY p.id DESC";
		   }
		System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			tablaPermisoLicencia e = new tablaPermisoLicencia();
			
			e.setId_empresa(rs.getInt("id_empresa"));
			e.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
			e.setAccion(rs.getInt("accion"));
			e.setTipo_licencia(rs.getString("tipo_licencia"));
			e.setSubtipo_licencia(rs.getString("subtipo_licencia"));
			e.setIncluye_feriados(rs.getInt("incluye_feriados"));
			e.setFecha_desde(rs.getString("fecha_desde"));
			e.setFecha_hasta(rs.getString("fecha_hasta"));
			e.setFecha_creacion(rs.getString("fecha_creacion"));
			e.setHoras_inasistencia(rs.getInt("horas_inasistencia"));
			e.setDias_corridos(rs.getInt("dias_corridos"));
			e.setRuta_archivo(rs.getString("ruta_archivo"));
			e.setNombre_reposo(rs.getString("nombre_reposo"));
			e.setId(rs.getInt("id"));
			e.setSubtipo_licenciaid(rs.getInt("subTipoLicenciaId"));
			e.setTipo_licenciaid(rs.getInt("TipoLicenciaId"));
			e.setReposo(rs.getInt("reposo"));
			e.setDoctor(rs.getString("doctor"));
			e.setEspecialidad(rs.getString("especialidad"));
			e.setTipo_reposo(rs.getInt("tipo_reposo"));
			e.setNombrecompleto(rs.getString("appaterno")+" "+rs.getString("apmaterno")+" "+rs.getString("nombretrab"));
			e.setNumerofolio(rs.getString("numero_folio"));
			
			data.add(e);
		}
		rs.close();
		ps.close();
		db.conn.close();
	}catch(Exception ex){
		System.out.println("Error: "+ex.getMessage());
	}finally{
		db.close();
	}
	return data;
}

public static ArrayList<trabajadores> getallTrabajaCodNomHD2(int cod, String estado_proceso,int rolPrivado)  throws Exception{
	PreparedStatement ps = null;
	String sql="";
	ArrayList<trabajadores> lista = new ArrayList<trabajadores>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = "select  TR.codigo,TR.nombre,TR.apellidoPaterno,TR.apellidoMaterno from contratos CO "
				+ "inner join trabajadores TR on TR.codigo = CO.codigo_trabajador "
				+ " left join sw_haberesDescuentos HD on HD.codigo_trabajador = CO.codigo_trabajador	"
				+ "where idSociedad = "+cod+" ";
				if(rolPrivado == 1){
					sql += " and  TR.rolPrivado in (1,0) ";
				}else{
					sql += " and  TR.rolPrivado in (0) ";
				}
				if("null".equals(estado_proceso)){}else{sql += " and TR.estadoProceso = "+estado_proceso+" ";}
				sql += "group by codigo";
				
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			trabajadores tr = new trabajadores();
			tr.setCodigo(rs.getString("codigo"));
			tr.setNombre(rs.getString("nombre"));
			tr.setAp_paterno(rs.getString("apellidoPaterno"));
			tr.setAp_materno(rs.getString("apellidoMaterno"));
			lista.add(tr);
		}			
	}catch (SQLException e){
		System.out.println("Error: " + e.getMessage());
	}catch (Exception e){
		System.out.println("Error: " + e.getMessage());
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}

public static ArrayList<TipoContrato> getTipoTrabajadorReclutamiento()  throws Exception{
PreparedStatement ps = null;
String sql="";
ArrayList<TipoContrato> lista = new ArrayList<TipoContrato>();
ConnectionDB db = new ConnectionDB();
try{
	sql = "SELECT * FROM parametros where codigo = 'TIPO_TRABAJADOR' and activo = 1 order by descripcion asc ";
	ps = db.conn.prepareStatement(sql);
	ResultSet rs = ps.executeQuery(sql);
	while(rs.next()){
		TipoContrato cr = new TipoContrato();
		cr.setDescripcion(rs.getString("descripcion"));
		cr.setLlave(rs.getInt("llave"));
		lista.add(cr);
	}			
}catch (SQLException e){
	System.out.println("Error: " + e.getMessage());
}catch (Exception e){
	System.out.println("Error: " + e.getMessage());
}finally {
	ps.close();
	db.close();
}		
return lista;
}
public static ArrayList<LoadCargoPreseleccion> getListadoReclu2(int soc) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<LoadCargoPreseleccion> data = new ArrayList<LoadCargoPreseleccion>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select * from reclutamiento_c where estado = 1 and empresa = (select denominacionSociedad from sociedad where idSociedad = "+soc+")";
		
		System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			LoadCargoPreseleccion e = new LoadCargoPreseleccion();
			e.setIdreclutamineto(rs.getInt("id_reclutamiento"));
			e.setEmpresa(rs.getString("empresa"));
			e.setFechacreacion(rs.getString("fecha_now"));
			
			data.add(e);
		}
		rs.close();
		ps.close();
		db.conn.close();
	}catch(Exception ex){
		System.out.println("Error: "+ex.getMessage());
	}finally{
		db.close();
	}
	return data;
}
public static ArrayList<LoadCargoPreseleccion> getListadoReclu() throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<LoadCargoPreseleccion> data = new ArrayList<LoadCargoPreseleccion>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select * from reclutamiento_c where estado = 1";
		
		System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			LoadCargoPreseleccion e = new LoadCargoPreseleccion();
			e.setIdreclutamineto(rs.getInt("id_reclutamiento"));
			e.setEmpresa(rs.getString("empresa"));
			e.setFechacreacion(rs.getString("fecha_now"));
			
			data.add(e);
		}
		rs.close();
		ps.close();
		db.conn.close();
	}catch(Exception ex){
		System.out.println("Error: "+ex.getMessage());
	}finally{
		db.close();
	}
	return data;
}

public static ArrayList<LoadCargoPreseleccion> getCargoPreseleccion2(int entero ) throws Exception{
	PreparedStatement ps = null;
	String sql = "";
	ArrayList<LoadCargoPreseleccion> data = new ArrayList<LoadCargoPreseleccion>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql ="select "
				+ "P.cargo as idcargo,"
				+ "(SELECT cargos FROM cargos WHERE id_cargo = P.cargo) AS cargo,"
				+ "P.posicion, P.id_peticion,R.usuario "
				+ "from reclutamiento_c R "
				+ "join peticion_trabajador P on R.id_reclutamiento = P.id_reclutamiento "
				+ "where P.id_reclutamiento = "+entero+" and estado_peticion = 1 "
				+ "group by P.cargo, P.id_peticion,P.posicion";
		
	
		
		System.out.println(sql);
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){
			LoadCargoPreseleccion e = new LoadCargoPreseleccion();
			e.setCargo(rs.getString("cargo"));
			e.setId_peticion(rs.getInt("id_peticion"));
			e.setUsuario(rs.getString("usuario"));
			e.setPosicion(rs.getString("posicion"));
			e.setIdcargo(rs.getString("idcargo"));
			
			
			data.add(e);
		}
		rs.close();
		ps.close();
		db.conn.close();
	}catch(Exception ex){
		System.out.println("Error: "+ex.getMessage());
	}finally{
		db.close();
	}
	return data;
}
//rechazo Preseleccionado
	public static boolean descartadoConfi (RechazoPreseleccionado r) throws Exception{
   	Statement ps = null;
		Statement ps2 = null;
		String sql = "";
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
	
		try{
			
			sql2 = "UPDATE preseleccionados SET status = 'Descartado', observacion = '"+r.getObservacion()+"' WHERE codigo_trabajador = "+r.getCodigo()+" AND codigo_peticion = " +r.getCodigo_peticion()+ " and id_peticion = "+r.getId_peticion()+"";

			ps2 = db .conn.prepareStatement(sql2);
			ps2.execute(sql2);

			
			return true;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
			
			ps2.close();

			db.close();
		}
		return false;
	}


public static String actualizarFechaEntrevista (lib.classSW.PreseleccionSimple r) throws Exception{
	Statement ps = null;
	String sql="";
	ConnectionDB db = new ConnectionDB();
	
	try{
			sql = "UPDATE preseleccionados SET fecha_entrevista='"+r.getFechaEntreista()+"',hora_entrevista='"+r.getHoraEntrevista()+"' WHERE codigo_trabajador = "+r.getCodigoTrabajador()+" and codigo_peticion = "+r.getId_reclutamiento()+" and id_peticion = "+r.getId_peticion()+" ";
			System.out.println(sql);
			
			ps = db.conn.prepareStatement(sql);
			ps.execute(sql);
			
			return "Actualizado con exito ";
		
		
	}catch (SQLException e){
		System.out.println("Error: "+ e.getMessage());
	}catch (Exception e){
		System.out.println("Error: "+ e.getMessage());
	}finally {
		
		db.close();
	}
	return "no";
}
public static String eviarDocumetoSapPREVIRED(OrdenDePagoPREVIRED r) throws Exception {

	PreparedStatement ps = null;
	String sql = "";
	PreparedStatement ps2 = null;
	String sql2 = "";
	String respuesta = "";
	ConnectionDB db = new ConnectionDB();
	
	try {


				sql = "UPDATE Previred_txt_periodo SET centralizado =1, documento_sap = '"+r.getNumerodocumento()+"', fecha_centralizacion = now() WHERE sociedad = "+r.getSociedad()+" and periodo = "+r.getPeriodo()+"" ;
				ps = db.conn.prepareStatement(sql);
					
				
				System.out.println(sql);
				ps.execute(sql);
				
				sql2 = "UPDATE sw_previred_file SET centralizado=1, fecha_centralizacion= now() WHERE sociedad = "+r.getSociedad()+" and periodo = "+r.getPeriodo()+"";
				ps2 = db.conn.prepareStatement(sql2);
				
				ps2.execute(sql2);
				
				respuesta = "Numero de Documento "+r.getNumerodocumento()+" Centralizado y Guardado Con Exito";
				
			
			
			
			return respuesta;
		}catch (SQLException e){
			System.out.println("Error: "+ e.getMessage());
		}catch (Exception e){
			System.out.println("Error: "+ e.getMessage());
		}finally {
		
		
			db.close();
		}
		
	return "Error al Guardar "+r.getNumerodocumento()+" en Base de Datos";
}
}