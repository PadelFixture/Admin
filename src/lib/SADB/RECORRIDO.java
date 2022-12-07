package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.BLOQUEO_LABOR;
import lib.classSA.recorrido;
import lib.db.ConnectionDB;

public class RECORRIDO {
	
	public static ArrayList<recorrido> GET_RECORRIDO()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<recorrido> lista = new ArrayList<recorrido>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " SELECT r.*, c.descripcion from recorrido r left join campo c on r.campo = c.descripcion";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				recorrido ob = new recorrido();
				ob.setId_recorrido(rs.getInt("id_recorrido"));
				ob.setCampo(rs.getString("campo"));
				ob.setDescripcion(rs.getString("descripcion"));
				ob.setDetalle(rs.getString("detalle"));
				ob.setChofer(rs.getString("chofer"));
				ob.setTipo_vehiculo(rs.getString("tipo_vehiculo"));
				ob.setPatente(rs.getString("patente"));
				ob.setOrigen(rs.getString("origen"));
				ob.setDestino(rs.getString("destino"));
				ob.setResponsable(rs.getString("responsable"));
				ob.setCantidad_persona(rs.getInt("cantidad_persona"));
				ob.setHorario_salida(rs.getString("horario_salida"));
				ob.setHorario_llegada(rs.getString("horario_llegada"));
				ob.setTipo_pago(rs.getInt("tipo_pago"));
				ob.setTarifa(rs.getFloat("tarifa"));
				ob.setEstado(rs.getInt("estado"));
				ob.setPorcentaje(rs.getInt("porcentaje"));
				ob.setVigencia_desde(rs.getString("vigencia_desde"));
				ob.setVigencia_hasta(rs.getString("vigencia_hasta"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}	
	
	public static boolean insertRECORRIDO(recorrido r) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO recorrido"
					+ "(campo, detalle, chofer, tipo_vehiculo, patente, origen,"
					+ " destino, responsable, cantidad_persona, horario_salida, horario_llegada, "
					+ "	tipo_pago, tarifa, estado, porcentaje, vigencia_desde, vigencia_hasta)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);	
			ps.setString(1, r.getCampo());
			ps.setString(2, r.getDetalle());
			ps.setString(3, r.getChofer());
			ps.setString(4, r.getTipo_vehiculo());
			ps.setString(5, r.getPatente());
			ps.setString(6, r.getOrigen());
			ps.setString(7, r.getDestino());
			ps.setString(8, r.getResponsable());
			ps.setInt(9, r.getCantidad_persona());
			ps.setString(10, r.getHorario_salida());
			ps.setString(11, r.getHorario_llegada());
			ps.setInt(12, r.getTipo_pago());
			ps.setFloat(13, r.getTarifa());
			ps.setInt(14, r.getEstado());
			ps.setInt(15, r.getPorcentaje());
			ps.setString(16, r.getVigencia_desde());
			ps.setString(17, r.getVigencia_hasta());
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
	
	public static boolean updateRECORRIDO(recorrido r) throws Exception{
		PreparedStatement ps = null;
		String sql= "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE recorrido set"
				+ " campo='"+r.getCampo()+"', detalle='"+r.getDetalle()+"', chofer='"+r.getChofer()+"'"
				+ ", tipo_vehiculo='"+r.getTipo_vehiculo()+"', patente='"+r.getPatente()+"'"
				+ ", origen='"+r.getOrigen()+"', destino='"+r.getDestino()+"'"
				+ ", responsable='"+r.getResponsable()+"', cantidad_persona='"+r.getCantidad_persona()+"'"
				+ ", horario_salida='"+r.getHorario_salida()+"', horario_llegada='"+r.getHorario_llegada()+"'"
				+ ", tipo_pago='"+r.getTipo_pago()+"', tarifa='"+r.getTarifa()+"', estado='"+r.getEstado()+"'"
				+ ", porcentaje='"+r.getPorcentaje()+"', vigencia_desde='"+r.getVigencia_desde()+"'"
				+ ", vigencia_hasta='"+r.getVigencia_hasta()+"' where id_recorrido='"+r.getId_recorrido()+"'";
			ps=db.conn.prepareStatement(sql);
			ps.execute();
			return true;			
		}catch(SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public static ArrayList<recorrido> GET_HRECORRIDO(int codigo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<recorrido> lista = new ArrayList<recorrido>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT h.*, c.descripcion from historial_recorrido h left join campo c on h.campo = c.campo where h.codigo='"+codigo+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				recorrido ob = new recorrido();
				ob.setId_recorrido(rs.getInt("id_recorrido"));
				ob.setCampo(rs.getString("campo"));
				ob.setDescripcion(rs.getString("descripcion"));
				ob.setDetalle(rs.getString("detalle"));
				ob.setChofer(rs.getString("chofer"));
				ob.setTipo_vehiculo(rs.getString("tipo_vehiculo"));
				ob.setPatente(rs.getString("patente"));
				ob.setOrigen(rs.getString("origen"));
				ob.setDestino(rs.getString("destino"));
				ob.setResponsable(rs.getString("responsable"));
				ob.setCantidad_persona(rs.getInt("cantidad_persona"));
				ob.setHorario_salida(rs.getString("horario_salida"));
				ob.setHorario_llegada(rs.getString("horario_llegada"));
				ob.setTipo_pago(rs.getInt("tipo_pago"));
				ob.setTarifa(rs.getFloat("tarifa"));
				ob.setEstado(rs.getInt("estado"));
				ob.setPorcentaje(rs.getInt("porcentaje"));
				ob.setVigencia_desde(rs.getString("vigencia_desde"));
				ob.setVigencia_hasta(rs.getString("vigencia_hasta"));
				ob.setCodigo(rs.getInt("codigo"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}	
	
}
