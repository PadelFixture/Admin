package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import lib.classSA.CAMPO;
import lib.classSA.CUARTEL;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.detalleNoIncidencia;
import lib.classSA.incidenciaClass;
import lib.classSW.Documentos;
import lib.db.ConnectionDB;
import lib.db.sw.DocumentosDB;
import lib.sesionSA.SESION;
import lib.struc.filterSql;
import lib.struc.incidencia;
import lib.utils.GeneralUtility;

public class noIncidencia {
	public static detalleNoIncidencia DETALLE_INCIDENCIA_CODIGO(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		detalleNoIncidencia e = new detalleNoIncidencia();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT i.*, t.nombre AS trabajador, c.nombre AS ncuartel FROM incidencia i ";
			sql += "LEFT JOIN trabajadores t ON(i.usuario_ingreso = t.id) ";
			sql += "INNER JOIN cuartel c ON(c.codigo = i.cuartel) ";
			sql += "WHERE i.codigo = "+id;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				e.setCodigo(rs.getInt("codigo"));
				e.setFecha_ingreso(rs.getString("fecha_ingreso"));
				e.setTipo_incidencia(rs.getString("tipo_incidencia"));
				e.setGeoreferencia(rs.getString("georeferencia"));
				e.setUsuario_ingreso(rs.getInt("usuario_ingreso"));
				e.setCuartel(rs.getInt("cuartel"));
				e.setObservacion(rs.getString("observacion"));
				e.setTrabajador(rs.getString("trabajador"));
				e.setNcuartel(rs.getString("ncuartel"));
				e.setImg(rs.getString("foto"));
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return e;
	}
	public static boolean CERRAR_INCIDENCIA(detalleNoIncidencia e,HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE incidencia SET usuario_cierre = ?, accion = ?, observacion_cierre = ?, fecha_cierre = ?, estado = 2 WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, e.getUsuario_cierre());
			ps.setString(2, e.getAccion());
			ps.setString(3, e.getObservacion_cierre());
			ps.setString(4, e.getFecha_cierre());
			ps.setInt(5, e.getCodigo());
			ps.execute();
			SESION mc= new SESION(httpSession);
			mc.delIncidencia(e.getCodigo());
			mc.save();
			return true;
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<detalleNoIncidencia> GET_ALL_INCIDENCIA() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<detalleNoIncidencia> lista = new ArrayList<detalleNoIncidencia>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT i.*, CONCAT(t.nombre,' ', t.apellidoPaterno) AS trabajador, c.nombre AS ncuartel, cam.campo, cam.descripcion ncampo "
					+ " FROM incidencia i ";
			sql += "LEFT JOIN trabajadores t ON(i.usuario_ingreso = t.id) ";
			sql += "INNER JOIN cuartel c ON(c.codigo = i.cuartel) "
					+ "  left join sector s on s.sector = c.sector"
					+ "	left join campo cam on cam.campo = s.campo"	
					+ " WHERE i.estado = 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				detalleNoIncidencia e = new detalleNoIncidencia();
				e.setCodigo(rs.getInt("codigo"));
				e.setFecha_ingreso(rs.getString("fecha_ingreso"));
				e.setTipo_incidencia(rs.getString("tipo_incidencia"));
				e.setGeoreferencia(rs.getString("georeferencia"));
				e.setUsuario_ingreso(rs.getInt("usuario_ingreso"));
				e.setCuartel(rs.getInt("cuartel"));
				e.setObservacion(rs.getString("observacion"));
				e.setTrabajador(rs.getString("trabajador"));
				e.setNcuartel(rs.getString("ncuartel"));
				e.setImg(rs.getString("foto"));
				e.setCampo(rs.getString("campo"));
				e.setNcampo(rs.getString("ncampo"));				
				lista.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	public static ArrayList<detalleNoIncidencia> GET_INCIDENCIA_CAMPO(String campo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<detalleNoIncidencia> lista = new ArrayList<detalleNoIncidencia>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT ";
			sql += 		"i.codigo, i.fecha_ingreso, tipo_incidencia, i.georeferencia, usuario_ingreso, i.cuartel, CONCAT(c.codigo_cuartel, ' ', c.nombre) as ncuartel, ";
			sql += 		"i.observacion, i.foto, i.usuario_cierre, i.accion, i.id_accion, i.observacion_cierre, i.fecha_cierre, i.estado, ";
			sql += 		"cm.campo, cm.descripcion, l.usuario ";
			sql += 	"FROM incidencia i ";
			sql += 		"LEFT JOIN cuartel c ON(c.codigo = i.cuartel) ";
			sql += 		"LEFT JOIN sector s ON(c.sector = s.sector) ";
			sql += 		"LEFT JOIN campo cm ON(cm.campo = s.campo) ";
			sql += 		"LEFT JOIN loginTest l ON(l.id = i.usuario_ingreso) ";
			sql += 	"WHERE cm.campo = '"+campo+"';";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				detalleNoIncidencia e = new detalleNoIncidencia();
				e.setCodigo(rs.getInt("i.codigo"));
				e.setFecha_ingreso(rs.getString("i.fecha_ingreso"));
				e.setTipo_incidencia(rs.getString("tipo_incidencia"));
				e.setGeoreferencia(rs.getString("georeferencia"));
				e.setUsuario_ingreso(rs.getInt("usuario_ingreso"));
				e.setCuartel(rs.getInt("i.cuartel"));
				e.setObservacion(rs.getString("observacion"));
				e.setTrabajador(rs.getString("l.usuario"));
				e.setNcuartel(rs.getString("ncuartel"));
				e.setImg(rs.getString("foto"));
				e.setCampo(rs.getString("cm.campo"));
				e.setNcampo(rs.getString("cm.descripcion"));
				e.setEstado(rs.getInt("i.estado"));;				
				lista.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	public static int INSERT_DOC(Documentos documentos) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE torneo SET image = ? WHERE id = (SELECT MAX(t1.id) FROM (select id from torneo ) as t1 )";
			ps = db.conn.prepareStatement(sql);
			ps.setBlob(1, documentos.getDocumento());
			ps.execute();
			ps.close();
			db.close();
			return documentos.getIdTrabajadorDocumentos();
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
			ps.close();
		}catch(Exception rx){
			
		}
		return 0;
	}
	public static int INSERT_DOC_FACTURA(Documentos documentos) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE liquidacion_contratista SET imgFactura = ? WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setBlob(1, documentos.getDocumento());
			ps.setInt(2, documentos.getCodTrabajador());
			System.out.println(ps.toString());
			ps.execute();
			ps.close();
			db.close();
			return documentos.getIdTrabajadorDocumentos();
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
			ps.close();
		}catch(Exception rx){
			
		}
		return 0;
	}
	// Obtener documento de la tabla sw_documentos
	public static Documentos GET_IMAGEN_INCIDENCIA(int codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Documentos documentos = new Documentos();
		try {
			sql = "SELECT image FROM incidencia WHERE codigo = "+codigo+"";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()) {
				documentos.setDocumento(rs.getBlob("image"));
			}
			return documentos;
		} catch (Exception e) {
			throw new Exception("GET_IMAGEN_INCIDENCIA: " + e.getMessage());
		} finally {
			db.close();
		}
	}
}
