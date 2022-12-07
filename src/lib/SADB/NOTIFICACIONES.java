package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.CUARTEL_PF;
import lib.classSA.MATERIAL_PF;
import lib.classSA.notificacion;
import lib.db.ConnectionDB;

public class NOTIFICACIONES {
	

//--------------NOTIFICACION----------------------- 
//	SELECT
	public static ArrayList<notificacion> GETN(int user,int tipo,int estado,String campo, int id)throws Exception{
		PreparedStatement ps = null; 
		String sql= "";
		ArrayList<notificacion> lista = new ArrayList<notificacion>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT N.*,e.especie nespecie, v.variedad nvariedad, pa.descripcion nproapli, tc.control_aplicacion nombretipocontrol, "
					+ " ef.estado_fenologicos nestadofenologico,pf.id idPrograma, pf.observacion, pf.fecha_estimada, pf.mojamiento, cm.descripcion as campo "
					+ " FROM notificaciones N "
					+ " join programa_fitosanitario pf on pf.codigo = N.codigo_tarea"
					+ " left join campo cm on cm.campo = pf.campo "
					+ " left join especie e on e.codigo = pf.especie"
					+ " left join variedad v on v.codigo = pf.variedad"
					+ " left join programa_aplicacion pa on pa.codigo = pf.programa_aplicacion"
					+ " left join estado_fenologico ef on ef.codigo = pf.estado_fenologico"
					+ " left join control_aplicacion tc on tc.codigo = pf.tipo_control"
					+ " where N.fecha_alerta < NOW() AND N.usuario_receptor='" +user+"'"
					+ " and N.estado = "+ estado
					+ " and N.tipo = "+tipo
					+ " AND pf.estado_pf != 5 "
					+ " and pf.campo in (select codigo_campo from usuario_campo where codigo_usuario = "+id+")";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				notificacion ob = new notificacion();
				ob.setIdPrograma(rs.getInt("idPrograma"));
				ob.setId_codigo(rs.getInt("id_codigo"));
				ob.setTipo(rs.getInt("tipo"));
				ob.setCodigo_tarea(rs.getInt("codigo_tarea"));
				ob.setUsuario_origen(rs.getInt("usuario_origen"));
				ob.setUsuario_receptor(rs.getInt("usuario_receptor"));
				ob.setFecha_alerta(rs.getString("fecha_alerta"));
				ob.setFecha_ingreso(rs.getString("fecha_ingreso"));
				ob.setEstado(rs.getInt("estado"));
				ob.setFecha(rs.getString("fecha_estimada"));
				ob.setCampo(rs.getString("campo"));
				ob.setEspecie(rs.getString("nespecie"));
				ob.setVariedad(rs.getString("nvariedad"));
				ob.setEstado_fenologico(rs.getString("nestadofenologico"));
				ob.setPrograma_aplicacion(rs.getString("nproapli"));
				ob.setMojamiento(rs.getInt("mojamiento"));
				ob.setTipo_control(rs.getString("nombretipocontrol"));
				ob.setObservacion(rs.getString("observacion"));
				ArrayList<MATERIAL_PF> m = new ArrayList<MATERIAL_PF>();
				m = material.getMPF(rs.getInt("codigo_tarea"));
				ob.setMaterial(m);
				ArrayList<CUARTEL_PF> c = new ArrayList<CUARTEL_PF>();
				c = cuartel.getCPF(rs.getInt("codigo_tarea"));
				ob.setCuartel(c);
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e ){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
//	INSERT
	public static boolean insertN (notificacion n)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="INSERT INTO notificaciones(tipo, codigo_tarea, usuario_origen, usuario_receptor, "
				+ "fecha_alerta, fecha_ingreso, estado) VALUES(?,?,?,?,?,now(),?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, n.getTipo());
			ps.setInt(2, n.getCodigo_tarea());
			ps.setInt(3, n.getUsuario_origen());
			ps.setInt(4, n.getUsuario_receptor());
			ps.setString(5, n.getFecha_alerta());
			//ps.setString(6, n.getFecha_ingreso());
			ps.setInt(6, n.getEstado());
			//System.out.println(sql); 
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: insertN" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error: insertN" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	
	
// UPDATE ESTADO NOTIFICACIÓN
	public static boolean updateEstadoNot (int codigo, int estado)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="UPDATE notificaciones SET estado = "+estado+" where id_codigo = "+codigo;
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: insertN" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error: insertN" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
//-----------------FIN NOTIFICACION---------------
	
	
	
	
}
