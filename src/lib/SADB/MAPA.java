package lib.SADB;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import lib.classSA.CAMPO;
import lib.classSA.CUARTEL;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.db.ConnectionDB;
import lib.sesionSA.SESION;
import lib.classSA.SECTOR;

public class MAPA {
	public static boolean addSector(SECTOR s, HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO sector (campo, sector, descripcion) VALUES (?,'-',?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, s.getCampo());
			ps.setString(2, s.getDescripcion());
			ps.execute();
			SESION mc = new SESION(httpSession);
			mc.addSector(s, s.sector);
			mc.save();
			return true;
		}
		catch(SQLException e){
			System.out.println("Error add sector: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static boolean addCuartel(CUARTEL c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "INSERT INTO cuartel (codigo_cuartel, nombre, sector, variedad, patron, ano_plantacion, superficie, plantas, distancia_largo, distancia_hancho, formacion, vivero, tipo_planta, tipo_control_heladas, tipo_proteccion, limitante_suelo, polinizante, estado, tipo_plantacion, clon, georeferencia, ceco, especie, macroco, ordenco)";
			sql += "VALUES (?,?,?,?,?,?,"+String.valueOf(c.getSuperficie())+",?,"+String.valueOf(c.getDistancia_largo())+","+String.valueOf(c.getDistancia_hancho())+",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getCodigo_cuartel());
			ps.setString(2, c.getNombre());
			ps.setString(3, c.getSector());
			ps.setInt(4, c.getVariedad());
			ps.setString(5, c.getPatron());
			ps.setString(6, c.getAno_plantacion());
			ps.setInt(7, c.getPlantas());
			ps.setString(8, c.getFormacion());
			ps.setString(9, c.getVivero());
			ps.setString(10, c.getTipo_planta());
			ps.setString(11, c.getTipo_control_heladas());
			ps.setString(12, c.getTipo_proteccion());
			ps.setString(13, c.getLimitante_suelo());
			ps.setString(14, c.getPolinizante());
			ps.setInt(15, c.getEstado());
			ps.setString(16, c.getTipo_plantacion());
			ps.setString(17, c.getClon());
			ps.setString(18, c.getGeoreferencia());
			ps.setString(19, c.getCeco());
			ps.setInt(20, c.getEspecie());
			ps.setString(21, c.getMacroco());
			ps.setString(22, c.getOrdenco());
			ps.execute();
			return true;
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<CUARTEL> GETALLCAMPO(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CUARTEL> data = new ArrayList<CUARTEL>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT c.*, cam.descripcion ncampo FROM cuartel AS c INNER JOIN sector AS s ON(c.sector = s.sector) INNER JOIN campo cam ON (cam.campo = s.campo) WHERE cam.codigo = "+id+"";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CUARTEL e = new CUARTEL();
				e.setCodigo(rs.getInt("codigo"));
				e.setCodigo_cuartel(rs.getString("codigo_cuartel"));
				e.setNombre(rs.getString("nombre"));
				e.setSector(rs.getString("sector"));
				e.setVariedad(rs.getInt("variedad"));
				e.setPatron(rs.getString("patron"));
				e.setAno_plantacion(rs.getString("ano_plantacion"));
				e.setSuperficie(rs.getFloat("superficie"));
				e.setPlantas(rs.getInt("plantas"));
				e.setDistancia_largo(rs.getFloat("distancia_largo"));
				e.setDistancia_hancho(rs.getFloat("distancia_hancho"));
				e.setFormacion(rs.getString("formacion"));
				e.setVivero(rs.getString("vivero"));
				e.setTipo_planta(rs.getString("tipo_planta"));
				e.setTipo_control_heladas(rs.getString("tipo_control_heladas"));
				e.setTipo_proteccion(rs.getString("tipo_proteccion"));
				e.setLimitante_suelo(rs.getString("limitante_suelo"));
				e.setPolinizante(rs.getString("polinizante"));
				e.setEstado(rs.getInt("estado"));
				e.setTipo_plantacion(rs.getString("tipo_plantacion"));
				e.setClon(rs.getString("clon"));
				e.setGeoreferencia(rs.getString("georeferencia"));
				e.setCeco(rs.getString("ceco"));
				e.setEspecie(rs.getInt("especie"));
				e.setCampo(rs.getString("ncampo"));
				e.setMacroco(rs.getString("macroco"));
				e.setOrdenco(rs.getString("ordenco"));
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
	public static ArrayList<CUARTEL> GET_CUARTEL_SECTOR(String id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CUARTEL> data = new ArrayList<CUARTEL>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT c.* FROM cuartel AS c INNER JOIN sector AS s ON(c.sector = s.sector)  WHERE s.sector = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CUARTEL e = new CUARTEL();
				e.setCodigo(rs.getInt("codigo"));
				e.setCodigo_cuartel(rs.getString("codigo_cuartel"));
				e.setNombre(rs.getString("nombre"));
				e.setSector(rs.getString("sector"));
				e.setVariedad(rs.getInt("variedad"));
				e.setPatron(rs.getString("patron"));
				e.setAno_plantacion(rs.getString("ano_plantacion"));
				e.setSuperficie(rs.getInt("superficie"));
				e.setPlantas(rs.getInt("plantas"));
				e.setDistancia_largo(rs.getFloat("distancia_largo"));
				e.setDistancia_hancho(rs.getFloat("distancia_hancho"));
				e.setFormacion(rs.getString("formacion"));
				e.setVivero(rs.getString("vivero"));
				e.setTipo_planta(rs.getString("tipo_planta"));
				e.setTipo_control_heladas(rs.getString("tipo_control_heladas"));
				e.setTipo_proteccion(rs.getString("tipo_proteccion"));
				e.setLimitante_suelo(rs.getString("limitante_suelo"));
				e.setPolinizante(rs.getString("polinizante"));
				e.setEstado(rs.getInt("estado"));
				e.setTipo_plantacion(rs.getString("tipo_plantacion"));
				e.setClon(rs.getString("clon"));
				e.setGeoreferencia(rs.getString("georeferencia"));
				e.setCeco(rs.getString("ceco"));
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
	public static boolean DELETE_CUARTEL(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql = "UPDATE cuartel SET estado = 3  WHERE codigo = "+id+"";
			ps = db.conn.prepareStatement(sql);
			
			ps.execute();
			ps.close();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return false;
	}
	public static boolean UPDATE_CUARTEL(CUARTEL c,HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		//System.out.println(c.getNombre());
		try{
			sql = "UPDATE cuartel SET sector = ?, variedad = ?, patron = ?, ano_plantacion = ?, superficie = "+String.valueOf(c.getSuperficie())+", plantas = ?, distancia_largo = "+String.valueOf(c.getDistancia_largo())+",";
			sql += " distancia_hancho = "+String.valueOf(c.getDistancia_hancho())+", formacion = ?, vivero = ?, tipo_planta = ?, tipo_control_heladas = ?, tipo_proteccion = ?, limitante_suelo = ?,";
			sql += " polinizante = ?, estado = ?, tipo_plantacion = ?, clon = ?, georeferencia = ?, ceco = ?, especie = ?, nombre = ?, macroco = ?, ordenco = ?, codigo_cuartel = ? WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			//ps.setString(1, c.getNombre());
			ps.setString(1, c.getSector());
			ps.setInt(2, c.getVariedad());
			ps.setString(3, c.getPatron());
			ps.setString(4, c.getAno_plantacion());
			ps.setInt(5, c.getPlantas());
			ps.setString(6, c.getFormacion());
			ps.setString(7, c.getVivero());
			ps.setString(8, c.getTipo_planta());
			ps.setString(9, c.getTipo_control_heladas());
			ps.setString(10, c.getTipo_proteccion());
			ps.setString(11, c.getLimitante_suelo());
			ps.setString(12, c.getPolinizante());
			ps.setInt(13, c.getEstado());
			ps.setString(14, c.getTipo_plantacion());
			ps.setString(15, c.getClon());
			ps.setString(16, c.getGeoreferencia());
			ps.setString(17, c.getCeco());
			ps.setInt(18, c.getEspecie());
			ps.setString(19, c.getNombre());
			ps.setString(20, c.getMacroco());
			ps.setString(21, c.getOrdenco());
			ps.setString(22, c.getCodigo_cuartel());
			ps.setInt(23, c.getCodigo());
			
			System.out.println(ps);
			ps.execute();
			SESION mc= new SESION(httpSession);
			mc.updCuartel(c, c.getCodigo());
			mc.save();
			c.setUsuario(mc.getIdUserSesion());
			INSERT_LOG_CUARTEL(c);
			return true;
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return false;
	}
	
	public static boolean INSERT_LOG_CUARTEL(CUARTEL c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		System.out.println(c.getSuperficie());
		try{
			sql = "INSERT log_cambio_cuartel (usuario, cuartel, estado, ceco, macro, ordenco, fecha) VALUES (?,?,?,?,?,?,NOW())";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, c.getUsuario());
			ps.setInt(2, c.getCodigo());
			ps.setInt(3, c.getEstado());
			ps.setString(4, c.getCeco());
			ps.setString(5, c.getMacroco());
			ps.setString(6, c.getOrdenco());
			System.out.println(ps);
			ps.execute();
			return true;
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return false;
	}
	
	
	public static boolean UPDATE_GEOREFERENCIA(CUARTEL c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		System.out.println(c.getSuperficie());
		try{
			sql = "UPDATE cuartel SET georeferencia = ? ";
			sql += "WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getGeoreferencia());
			ps.setInt(2, c.getCodigo());
			ps.execute();
			return true;
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<CAMPO> GET_CAMPO_MANTENEDOR() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CAMPO> data = new ArrayList<CAMPO>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM campo";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CAMPO e = new CAMPO();
				e.setCampo(rs.getString("campo"));
				e.setCodigo(rs.getInt("codigo"));
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
	public static ArrayList<CAMPO> GET_USUARIOS_MANTENEDOR() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CAMPO> data = new ArrayList<CAMPO>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM loginTest";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CAMPO e = new CAMPO();
				e.setCodigo(rs.getInt("id"));
				e.setDescripcion(rs.getString("usuario"));
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
	public  static boolean ADD_USUARIO_CAMPO(ConnectionDB db, ArrayList<CAMPO> campo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		try {
			for(CAMPO r: campo){
				sql = "INSERT INTO usuario_campo(codigo_usuario, codigo_campo) VALUES(?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, r.getCodigo());
				ps.setString(2, r.getDescripcion());
				System.out.println(sql);
				ps.execute();
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error  fghfgh:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ytyrtyr:" + e.getMessage());
		} finally {
			db.close();
		}
		return false;
	}
}
