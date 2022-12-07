package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.Servicio_Externo;
import lib.db.ConnectionDB;

public class ServicioExterno {
	
	public static ArrayList<Servicio_Externo> GET_ServicioExterno_Envio()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Servicio_Externo> lista = new ArrayList<Servicio_Externo>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " SELECT s.*, c.descripcion nvcampo, m.descripcion nvmaquina from servicio_externo s "
				+ " inner join campo c on s.campo = c.campo "
				+ " inner join maquinaria m on s.maquina = m.codigo "
				+ " where s.estado=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				Servicio_Externo ob = new Servicio_Externo();
				ob.setOrden_ingreso(rs.getInt("orden_ingreso"));
				ob.setCampo(rs.getString("campo"));
				ob.setMaquina(rs.getString("maquina"));
				ob.setTipo_servicio(rs.getString("tipo_servicio"));
				ob.setHorometro(rs.getFloat("horometro"));
				ob.setFecha(rs.getString("fecha"));		
				ob.setNvcampo(rs.getString("nvcampo"));
				ob.setNvmaquina(rs.getString("nvmaquina"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	
	public static boolean ADD_ServicioExterno_Envio(Servicio_Externo s) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO servicio_externo(tipo_servicio, horometro, fecha,campo,maquina, estado, orden_envio)"
				+ " VALUES(?,?,?,?,?,'1',?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, s.getTipo_servicio());
			ps.setFloat(2,s.getHorometro());
			ps.setString(3, s.getFecha());
			ps.setString(4, s.getCampo());
			ps.setString(5, s.getMaquina());
			ps.setInt(6, s.getOrden_envio());
			if(s.getOrden_envio() != 0){
				MantecionDB.UPD_ESTADO_TALLER(s.getOrden_envio(), 2);
			}
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static boolean UP_ServicioExterno (Servicio_Externo s) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " UPDATE servicio_externo set  horometro_recepcionado='"+s.getHorometro_recepcionado()+"', "
				+ " fecha_llegada='"+s.getFecha_llegada()+"', costo_servicio='"+s.getCosto_servicio()+"', "
				+ " estado=0, orden_envio=orden_ingreso where orden_ingreso='"+s.getOrden_ingreso()+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			 ps.close();
			 db.close();
		}
		return false;
	}
}
