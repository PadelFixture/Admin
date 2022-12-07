package lib.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lib.classSA.log_login;
//import lib.jsonMap.purchaseIncidencia;
//import lib.jsonMap.purchaseMap;
import lib.struc.cuadrilla;
import lib.struc.laborCuadrilla;
import lib.struc.labores;
import lib.struc.loginApp;
import lib.struc.mapConection;
import lib.struc.productConection;
import lib.struc.trabajadores;

public class conSimpleAgro {
	//public static ConnectionDB  db = null;
	
	public static trabajadores consultaTrabajadores(int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		trabajadores e = new trabajadores();
		ConnectionDB db = new ConnectionDB();
		try{
			
			sql = "SELECT *FROM trabajadores where codigo="+codigo+" and id_perfil = 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				e.setId(rs.getInt("id"));
				e.setCodigo(rs.getString("codigo"));
				e.setRut(rs.getString("rut"));
				e.setNombre(rs.getString("nombre"));
				e.setfNacimineto(rs.getString("fNacimiento"));
				e.setDireccion(rs.getString("direccion"));
				e.setTelefono(rs.getString("telefono"));
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}finally{
			db.close();
		}
		return e;
	}
	public static void inCuadrilla(int capataz) throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String sql = "";
		try{
			db = new ConnectionDB();
			sql = "INSERT INTO cuadrilla (id_encargado) VALUES ("+capataz+")";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			ps.close();
			db.conn.close();
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			db.close();
		}
	}
	public static loginApp getLogin(String user, String pass){
		ConnectionDB db = new ConnectionDB();
		Statement stmt = null;
		String sql = "";
		loginApp us = null;
		try{
			//sql ="SELECT U.*, r.TIPO_RECEPTOR, r.PADRE FROM USUARIOS U LEFT JOIN RECEPTOR r ON(U.COD_RECEPTOR = r.id) where U.USUARIO= '"+user+"' AND U.PASSWORD = '"+pass+"' AND U.ESTADO = 1 ";
			sql ="SELECT u.id idUSUARIOS, usuario USUARIO, password PASSWORD, uc.id_club CLUB, '1' PERFIL, '1' ESTADO, '1' TIPO_RECEPTOR, '1' PADRE  "
					+ " FROM usuarios  u"
					+ " LEFT JOIN usuario_club uc on uc.id_usuario = u.id "
					+ " where usuario = '"+user+"' and password = '"+pass+"' ";
			stmt = db.conn.createStatement();
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				us = new loginApp();
				

//				us.setId(rs.getInt("idUSUARIOS"));
//				us.setUsuario(rs.getString("USUARIO"));
//				us.setPerfil(rs.getInt("PERFIL"));
//				us.setGrupoCompra(rs.getString("TIPO_RECEPTOR"));
////				us.setSolicitante(rs.getString("solicitante"));
//				us.setRolPrivado(rs.getInt("COD_RECEPTOR"));
				
				//System.out.println(rs.getInt("COD_RECEPTOR"));
				us.setId(rs.getInt("idUSUARIOS"));
				us.setUsuario(rs.getString("USUARIO"));
				us.setPerfilText("recotec");
				us.setPerfil(rs.getInt("PERFIL"));
				//us.setGrupoCompra(rs.getString("r.TIPO_RECEPTOR"));
				//us.setSolicitante(rs.getString("r.PADRE"));
				//us.getClub(rs.getInt("CLUB"));
				us.setClub(rs.getInt("CLUB"));
			}
//			insertLog(us.getId());
			stmt.close();
			rs.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error getLogin : "+ex.getMessage());
		}finally{
			db.close();
		}
		return us;
	}
	public static void insertLog(int id_user)throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String log = "";
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String fecha = dateFormat.format(date).toString().replaceAll("/","-");
			log = "INSERT log_login(id_user, hora_fecha, n_ingreso) VALUES ("+id_user+", '"+fecha+"', 1)";
			ps = db.conn.prepareStatement(log);
			ps.execute();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
	}
	public static void insertMiembros(int id_cuadrilla, int id_trabajador) throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String sql = "";
		try{
			sql = "INSERT INTO miembros_cuadrilla (id_cuadrilla, id_trabajador) values ("+id_cuadrilla+", "+id_trabajador+")";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
	}
 	public static trabajadores consultaTrabajadoresID(int id) throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String sql = "";
		trabajadores e = new trabajadores();
		try{
			sql = "SELECT *FROM trabajadores WHERE id = "+id+"";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				e.setId(rs.getInt("id"));
				e.setCodigo(rs.getString("codigo"));
				e.setNombre(rs.getString("nombre"));
				e.setRut(rs.getString("rut"));
				e.setDireccion(rs.getString("direccion"));
				e.setfNacimineto(rs.getString("fNacimiento"));
				e.setTelefono(rs.getString("telefono"));
			}
			
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return e;
	}
 	public static ArrayList<cuadrilla> buscarIdCuadrilla() throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<cuadrilla> data = new ArrayList<cuadrilla>();
		try{
			sql = "select max(id_cuadrilla) as id_cuadrilla from cuadrilla";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				cuadrilla e = new cuadrilla();
				e.setId_cuadrilla(rs.getInt("id_cuadrilla"));
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
 	public static ArrayList<cuadrilla> getCuadrilla() throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<cuadrilla> data = new ArrayList<cuadrilla>();
		try{
			sql = "SELECT *FROM cuadrilla";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				cuadrilla e = new cuadrilla();
				e.setId_cuadrilla(rs.getInt("id_cuadrilla"));
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
 	public static ArrayList<trabajadores> getMiemCuadrilla(int id_cuadrilla) throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<trabajadores> e = new ArrayList<trabajadores>();
		try{
			sql = "select trab.* FROM trabajadores trab inner join miembros_cuadrilla m on trab.id = m.id_trabajador where m.id_cuadrilla = "+id_cuadrilla+"";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajadores ed = new trabajadores();
				ed.setId(rs.getInt("id"));
				ed.setCodigo(rs.getString("codigo"));
				ed.setRut(rs.getString("rut"));
				ed.setNombre(rs.getString("nombre"));
				ed.setfNacimineto(rs.getString("fNacimiento"));
				ed.setDireccion(rs.getString("direccion"));
				ed.setTelefono(rs.getString("telefono"));
				e.add(ed);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error id: "+ex.getMessage());
		}finally{
			db.close();
		}
		return e;
	}
 	public static boolean insertActividad(laborCuadrilla lab) throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String sql = "";
		try{
			sql = "INSERT INTO laborCuadrilla (id_cuadrilla, actividad, fecha, estado, cantidad) VALUES(?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, lab.getId_cuadrilla());
			ps.setString(2, lab.getActividad());
			ps.setString(3, lab.getFecha());
			ps.setString(4, lab.getEstado());
			ps.setInt(5, lab.getCantidad()); 
			ps.execute();
			ps.close();
			db.conn.close();
			return true; 	
		}
		catch(SQLException e){
			System.out.println("Error: " +e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		finally{
			db.close();
		}
		return false;
	}
 	public static ArrayList<laborCuadrilla> getActCuadrilla() throws Exception{
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<laborCuadrilla> data = new ArrayList<laborCuadrilla>();
		try{
			sql = "SELECT *FROM laborCuadrilla";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				laborCuadrilla e = new laborCuadrilla();
				e.setId_cuadrilla(rs.getInt("id_cuadrilla"));
				e.setActividad(rs.getString("actividad"));
				e.setEstado(rs.getString("estado"));
				e.setFecha(rs.getString("fecha"));
				e.setCantidad(rs.getInt("cantidad"));
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
 	
// 	public static boolean jsonParse(String url) throws Exception {
//        JSONParser parser = new JSONParser();
//
//        try {         
//            URL oracle = new URL(url); // URL to Parse
//            URLConnection yc = oracle.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//            
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {               
//                JSONArray a = (JSONArray) parser.parse(inputLine);
//                
//                // Loop through each item
//                for (Object o : a) {
//                    JSONObject tutorials = (JSONObject) o;
//
//                    Long id = (Long) tutorials.get("ID");
//                    System.out.println("Post ID : " + id);
//
//                    String title = (String) tutorials.get("post_title");
//                    System.out.println("Post Title : " + title);
//
//                    System.out.println("\n");
//                }
//                return true;
//            }
//            in.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//		return false;
// 	}
 	
// 	public static ArrayList<purchaseMap> loadAllIncidencia() throws Exception{
//		ConnectionDB db = new ConnectionDB();
//		PreparedStatement ps = null;
//		String sql = "";
//		ArrayList<purchaseMap> mapa = new ArrayList<purchaseMap>();
//		try{
//			sql = "SELECT i.*, it.*, t.nombre, c.* FROM INCIDENCIA_TRABAJADOR it RIGHT JOIN INCIDENCIAS i on (it.id_incidencia = i.id_incidencia) LEFT JOIN trabajadores t on(t.id = it.id_trabajador) RIGHT JOIN test c on(c.idtest = i.id_campo) WHERE i.estado = 'Pendiente' OR i.estado = 'Persistente'";
//			ps = db.conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery(sql);
//			while(rs.next()){
//				ArrayList<purchaseIncidencia> Incidencias = new ArrayList<purchaseIncidencia>();
//				purchaseMap map = new purchaseMap();
//				purchaseIncidencia pi = new purchaseIncidencia();
//				map.setIdtest(rs.getInt("idtest"));
//				map.setCoordenada(rs.getString("coordenada"));
//				map.setValor1(rs.getString("valor1"));
//				map.setColor(rs.getString("color"));
//				map.setEspecie(rs.getString("especie"));
//				map.setVariedad(rs.getString("variedad"));
//				map.setHectarias(rs.getString("hectarias"));
//				map.setCantidad(rs.getInt("cantidad"));
//				map.setPlantacion(rs.getString("plantacion"));
//				map.setStatus(rs.getString("status"));
//				pi.setId_incidencia(rs.getInt("id_incidencia"));
//				pi.setId_campo(rs.getInt("id_campo"));
//				pi.setDescripcion(rs.getString("descripcion"));
//				pi.setCoordenadas(rs.getString("coordenadas"));
//				pi.setSector_afectado(rs.getString("sector_afectado"));
//				pi.setUrgencia(rs.getString("urgencia"));
//				pi.setTipo_incidencia(rs.getString("tipo_incidencia"));
//				pi.setObservaciones(rs.getString("observaciones"));
//				pi.setEstado(rs.getString("estado"));
//				pi.setFecha(rs.getString("fecha"));
//				pi.setEncargado(rs.getString("nombre"));
//				Incidencias.add(pi);
//				map.setPurchaseIncidencia(Incidencias);
//				mapa.add(map);
//			}
//			rs.close();
//		}catch(Exception ex){
//			System.out.println("Error: "+ex.getMessage());
//		}finally{
//			ps.close();
//			db.conn.close();
//		}
//		return mapa;
//	}
}
