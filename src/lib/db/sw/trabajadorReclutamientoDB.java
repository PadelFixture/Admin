package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lib.classSW.trabajadorReclutamiento;
import lib.db.ConnectionDB;

public class trabajadorReclutamientoDB {
	
	
	//INSERT trabajadorReclutamientoDB
	public static boolean insertTrabajadorReclutamiento(trabajadorReclutamiento trReclu) throws Exception{
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;
		
		try{
			
			sql = " INSERT INTO sw_m_trabajadorReclutamiento ( "
					+ "idTrabajadorReclutamiento, idNacionalidad, "
					+ "idTrabajador, codigoTrabajador, rut, "
					+ "rutTemporal, pasaporte, nombre, "
					+ "apellidoPaterno, apellidoMaterno, "
					+ "fNacimiento, idGenero, idEstadoCivil, "
					+ "telefono, celular, email, direccion, "
					+ "idRegion, idProvincia, idComuna, "
					+ "idCargo, recorrido, sector, pensionados, "
					+ "sCesantia "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, trReclu.getIdTrabajadorReclutamiento());
			ps.setInt(i++, trReclu.getIdNacionalidad());
			ps.setInt(i++, trReclu.getIdTrabajador());
			ps.setInt(i++, trReclu.getCodigoTrabajador());
			ps.setString(i++, trReclu.getRut());
			ps.setString(i++, trReclu.getRutTemporal());
			ps.setString(i++, trReclu.getPasaporte());
			ps.setString(i++, trReclu.getNombre());
			ps.setString(i++, trReclu.getApellidoPaterno());
			ps.setString(i++, trReclu.getApellidoMaterno());
			ps.setString(i++, convertStringToYYYYMMDD(trReclu.getfNacimiento()));
			ps.setInt(i++, trReclu.getIdGenero());
			ps.setInt(i++, trReclu.getIdEstadoCivil());
			ps.setString(i++, trReclu.getTelefono());
			ps.setString(i++, trReclu.getCelular());
			ps.setString(i++, trReclu.getEmail());
			ps.setString(i++, trReclu.getDireccion());
			ps.setInt(i++, trReclu.getIdRegion());
			ps.setInt(i++, trReclu.getIdProvincia());
			ps.setInt(i++, trReclu.getIdComuna());
			ps.setInt(i++, trReclu.getIdCargo());
			ps.setInt(i++, trReclu.getRecorrido());
			ps.setInt(i++, trReclu.getSector());
			ps.setInt(i++, trReclu.getPensionados());
			ps.setInt(i++, trReclu.getsCesantia());
			
			ps.execute();
			
			return true;			
		}catch(Exception e){
			System.out.println("Error insert trabajadorReclutamiento: "+e.getMessage());
			e.printStackTrace();
		}finally{
			db.conn.close();
		}
		return false;
	}
	
	//GET TRABAJADOR RECLUTAMIENTO by idTrabajador
		public static ArrayList<trabajadorReclutamiento> getTrabajadorReclutamuentoByIdTrabajador(String id) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			
			ArrayList<trabajadorReclutamiento> trRecluLista = new ArrayList<trabajadorReclutamiento>();
			
			try{
				sql = "SELECT * FROM sw_m_trabajadorReclutamiento WHERE idTrabajador = '"+id+"'";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){
					trabajadorReclutamiento trReclu = new trabajadorReclutamiento();
					
					trReclu.setIdTrabajadorReclutamiento(rs.getInt("idTrabajadorReclutamiento"));
					trReclu.setIdNacionalidad(rs.getInt("idNacionalidad"));
					trReclu.setIdTrabajador(rs.getInt("idTrabajador"));
					trReclu.setCodigoTrabajador(rs.getInt("codigoTrabajador"));
					trReclu.setRut(rs.getString("rut"));
					trReclu.setRutTemporal(rs.getString("rutTemporal"));
					trReclu.setPasaporte(rs.getString("pasaporte"));
					trReclu.setNombre(rs.getString("nombre"));
					trReclu.setApellidoPaterno(rs.getString("apellidoPaterno"));
					trReclu.setApellidoMaterno(rs.getString("apellidoMaterno"));
					trReclu.setfNacimiento(rs.getString("fNacimiento"));
					trReclu.setIdGenero(rs.getInt("idGenero"));
					trReclu.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
					trReclu.setTelefono(rs.getString("telefono"));
					trReclu.setCelular(rs.getString("celular"));
					trReclu.setEmail(rs.getString("email"));
					trReclu.setDireccion(rs.getString("direccion"));
					trReclu.setIdRegion(rs.getInt("idRegion"));
					trReclu.setIdProvincia(rs.getInt("idProvincia"));				
					trReclu.setIdComuna(rs.getInt("idComuna"));
					trReclu.setIdCargo(rs.getInt("idCargo"));
					trReclu.setRecorrido(rs.getInt("recorrido"));
					trReclu.setSector(rs.getInt("sector"));
					trReclu.setPensionados(rs.getInt("pensionados"));
					trReclu.setsCesantia(rs.getInt("sCesantia"));
					
					trRecluLista.add(trReclu);
				}
			
			
			}catch(Exception e){
				System.out.println("Error en get trabajadorReclutamientoByIdTrabajador: "+e.getMessage());
				e.printStackTrace();
			}finally{
				ps.close();
				db.close();
			}
			return trRecluLista;
		}
		
		
		//GET TRABAJADOR RECLUTAMIENTO by idTrabajadorReclutamiento
		public static trabajadorReclutamiento getTrabajadorReclutamuentoByIdTrabajadorReclutamiento(String id) throws Exception{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			
			trabajadorReclutamiento trReclu = new trabajadorReclutamiento();
			
			try{
				sql = "SELECT * FROM sw_m_trabajadorReclutamiento WHERE idTrabajadorReclutamiento = '"+id+"'";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				
				while(rs.next()){
					trReclu.setIdTrabajadorReclutamiento(rs.getInt("idTrabajadorReclutamiento"));
					trReclu.setIdNacionalidad(rs.getInt("idNacionalidad"));
					trReclu.setIdTrabajador(rs.getInt("idTrabajador"));
					trReclu.setCodigoTrabajador(rs.getInt("codigoTrabajador"));
					trReclu.setRut(rs.getString("rut"));
					trReclu.setRutTemporal(rs.getString("rutTemporal"));
					trReclu.setPasaporte(rs.getString("pasaporte"));
					trReclu.setNombre(rs.getString("nombre"));
					trReclu.setApellidoPaterno(rs.getString("apellidoPaterno"));
					trReclu.setApellidoMaterno(rs.getString("apellidoMaterno"));
					trReclu.setfNacimiento(rs.getString("fNacimiento"));
					trReclu.setIdGenero(rs.getInt("idGenero"));
					trReclu.setIdEstadoCivil(rs.getInt("idEstadoCivil"));
					trReclu.setTelefono(rs.getString("telefono"));
					trReclu.setCelular(rs.getString("celular"));
					trReclu.setEmail(rs.getString("email"));
					trReclu.setDireccion(rs.getString("direccion"));
					trReclu.setIdRegion(rs.getInt("idRegion"));
					trReclu.setIdProvincia(rs.getInt("idProvincia"));				
					trReclu.setIdComuna(rs.getInt("idComuna"));
					trReclu.setIdCargo(rs.getInt("idCargo"));
					trReclu.setRecorrido(rs.getInt("recorrido"));
					trReclu.setSector(rs.getInt("sector"));
					trReclu.setPensionados(rs.getInt("pensionados"));
					trReclu.setsCesantia(rs.getInt("sCesantia"));
				}
			}catch(Exception e){
				System.out.println("Error en get trabajadorReclutamientoByIdTrabajadorReclutamiento: "+e.getMessage());
				e.printStackTrace();
			}finally{
				ps.close();
				db.close();
			}
			return trReclu;
		}
	
	
	//UPDATE trabajadorReclutamiento
	public static boolean updateTrabajadorReclutamiento(trabajadorReclutamiento trReclu) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;
		
		try{
			sql = "UPDATE sw_m_trabajadorReclutamiento SET "
					+ "idNacionalidad = ?, "
					+ "idTrabajador = ?, "
					+ "codigoTrabajador = ?, "
					+ "rut = ?, "
					+ "rutTemporal = ?, "
					+ "pasaporte = ?, "
					+ "nombre = ?, "
					+ "apellidoPaterno = ?, "
					+ "apellidoMaterno = ?, "
					+ "fNacimiento = ?, "
					+ "idGenero = ?, "
					+ "idEstadoCivil = ?, "
					+ "telefono = ?, "
					+ "celular = ?, "
					+ "email = ?, "
					+ "direccion = ?, "
					+ "idRegion = ?, "
					+ "idProvincia = ?, "
					+ "idComuna = ?, "
					+ "idCargo = ?, "
					+ "recorrido = ?, "
					+ "sector = ?, "
					+ "pensionados = ?, "
					+ "sCesantia = ? "
					+ "WHERE idTrabajadorReclutamiento = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, trReclu.getIdNacionalidad());
			ps.setInt(i++, trReclu.getIdTrabajador());
			ps.setInt(i++, trReclu.getCodigoTrabajador());
			ps.setString(i++, trReclu.getRut());
			ps.setString(i++, trReclu.getRutTemporal());
			ps.setString(i++, trReclu.getPasaporte());
			ps.setString(i++, trReclu.getNombre());
			ps.setString(i++, trReclu.getApellidoPaterno());
			ps.setString(i++, trReclu.getApellidoMaterno());
			ps.setString(i++, convertStringToYYYYMMDD(trReclu.getfNacimiento()));
			ps.setInt(i++, trReclu.getIdGenero());
			ps.setInt(i++, trReclu.getIdEstadoCivil());
			ps.setString(i++, trReclu.getTelefono());
			ps.setString(i++, trReclu.getCelular());
			ps.setString(i++, trReclu.getEmail());
			ps.setString(i++, trReclu.getDireccion());
			ps.setInt(i++, trReclu.getIdRegion());
			ps.setInt(i++, trReclu.getIdProvincia());
			ps.setInt(i++, trReclu.getIdComuna());
			ps.setInt(i++, trReclu.getIdCargo());
			ps.setInt(i++, trReclu.getRecorrido());
			ps.setInt(i++, trReclu.getSector());
			ps.setInt(i++, trReclu.getPensionados());
			ps.setInt(i++, trReclu.getsCesantia());
			ps.setInt(i++, trReclu.getIdTrabajadorReclutamiento());
			
			ps.execute();
			return true;

		} catch (SQLException e) {
			System.out.println("Error: update trabajadorReclutamiento:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: update trabajadorReclutamiento:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}
	
	public static boolean deleteTrabajadorReclutamiento(String id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql = "DELETE FROM sw_m_trabajadorReclutamiento WHERE idTrabajadorReclutamiento = '"+id+"' ";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			
			return true;
		} catch (SQLException e) {
			System.out.println("Error: delete trabajadorReclutamiento:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: delete trabajadorReclutamiento:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}
	
	
	
	
	
	
	
	public static String convertStringToYYYYMMDD(String fecha) throws ParseException{

		if(fecha == null || fecha.isEmpty()){
			return null;
		}
		
	    
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		 SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		 java.util.Date date = output.parse(fecha.replace("/", "-"));
		 
		 if (fecha.equals(output.format(date))) {
		        return fecha;
		    }
		 
		 java.util.Date data = sdf.parse(fecha.replace("/", "-"));
		 String formattedDate = output.format(data);
		 
		 return formattedDate;

	}
	
	
}
