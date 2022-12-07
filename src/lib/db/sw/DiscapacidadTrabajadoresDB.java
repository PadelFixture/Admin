package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lib.classSW.DiscapacidadTrabajadores;
import lib.db.ConnectionDB;

public class DiscapacidadTrabajadoresDB {

	//Insert discapacidad
	public static boolean insertDiscapacidad(DiscapacidadTrabajadores DiscapacidadTrabajadores) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try{

			sql = "INSERT INTO sw_r_discapacidad_Trabajadores ( idDiscapacidad,idTrabajadores, nCredencial, fechaReevaluacion, "
					+ " gradoDiscapacidad, porcentajeDiscapacidad, causaDiscapacidad, movilidadReducida )"
					+ " VALUES (?,?,?,?,?,?,?,?) ";

			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, DiscapacidadTrabajadores.getIdDiscapacidad());
			ps.setInt(i++, DiscapacidadTrabajadores.getIdTrabajador());
			ps.setString(i++, DiscapacidadTrabajadores.getnCredencial());
			ps.setString(i++, convertStringToYYYYMMDD(DiscapacidadTrabajadores.getFechaReevaluacion()));
			ps.setInt(i++, DiscapacidadTrabajadores.getGradoDiscapacidad());
			ps.setDouble(i++, DiscapacidadTrabajadores.getPorcentajeDiscapacidad());
			ps.setInt(i++, DiscapacidadTrabajadores.getCausaDiscapacidad());
			ps.setInt(i++, DiscapacidadTrabajadores.getMovilidadReducida());

			ps.execute();

			return true;

		}catch(Exception e){

			System.out.println("Error insertDiscapacidad:" + e.getMessage());
			e.printStackTrace();

		}finally{
			db.conn.close();
		}

		return false;
	}


	//Actualizar DiscapacidadTrabajadores
	public static boolean updateDiscapacidad(DiscapacidadTrabajadores DiscapacidadTrabajadores) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		int i=1;

		try {
			sql = " UPDATE sw_r_discapacidad_Trabajadores SET "
					+ " idTrabajadores = ?,"
					+ " nCredencial = ?, "	
					+ " fechaReevaluacion = ?, "	
					+ " gradoDiscapacidad = ?,"		
					+ " porcentajeDiscapacidad = ?,"	
					+ " causaDiscapacidad = ?,"
					+ " movilidadReducida = ?"
					+ " WHERE idDiscapacidad = ?";

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, DiscapacidadTrabajadores.getIdTrabajador());
			ps.setString(i++, DiscapacidadTrabajadores.getnCredencial());
			ps.setString(i++, convertStringToYYYYMMDD(DiscapacidadTrabajadores.getFechaReevaluacion()));
			ps.setInt(i++, DiscapacidadTrabajadores.getGradoDiscapacidad());
			ps.setDouble(i++, DiscapacidadTrabajadores.getPorcentajeDiscapacidad());
			ps.setInt(i++, DiscapacidadTrabajadores.getCausaDiscapacidad());
			ps.setInt(i++, DiscapacidadTrabajadores.getMovilidadReducida());
			ps.setInt(i++, DiscapacidadTrabajadores.getIdDiscapacidad());
			

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



	//Obtener discapacidad por id Trabajadores
	public static DiscapacidadTrabajadores getDiscapacidadByIdTrabajadores(int id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		DiscapacidadTrabajadores DiscapacidadTrabajadores = new DiscapacidadTrabajadores();

		try{
			sql = "SELECT * FROM sw_r_discapacidad_Trabajadores WHERE idTrabajadores = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				DiscapacidadTrabajadores.setIdDiscapacidad(rs.getInt("idDiscapacidad"));
				DiscapacidadTrabajadores.setIdTrabajador(rs.getInt("idTrabajadores"));
				DiscapacidadTrabajadores.setnCredencial(rs.getString("nCredencial"));
				DiscapacidadTrabajadores.setFechaReevaluacion(rs.getString("fechaReevaluacion"));
				DiscapacidadTrabajadores.setGradoDiscapacidad(rs.getInt("gradoDiscapacidad"));
				DiscapacidadTrabajadores.setPorcentajeDiscapacidad(rs.getDouble("porcentajeDiscapacidad"));
				DiscapacidadTrabajadores.setCausaDiscapacidad(rs.getInt("causaDiscapacidad"));
				DiscapacidadTrabajadores.setMovilidadReducida(rs.getInt("movilidadReducida"));
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return DiscapacidadTrabajadores;
	}


	//Obtener Ultima discapacidad por id Trabajadores
	public static DiscapacidadTrabajadores getLastDiscapacidadByIdTrabajadores(int id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		DiscapacidadTrabajadores DiscapacidadTrabajadores = new DiscapacidadTrabajadores();

		try{
			sql = "SELECT * FROM sw_r_discapacidad_Trabajadores WHERE idTrabajadores = '"+id+"' ORDER BY idDiscapacidad DESC LIMIT 0,1 ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				DiscapacidadTrabajadores.setIdDiscapacidad(rs.getInt("idDiscapacidad"));
				DiscapacidadTrabajadores.setIdTrabajador(rs.getInt("idTrabajadores"));
				DiscapacidadTrabajadores.setnCredencial(rs.getString("nCredencial"));
				DiscapacidadTrabajadores.setFechaReevaluacion(rs.getString("fechaReevaluacion"));
				DiscapacidadTrabajadores.setGradoDiscapacidad(rs.getInt("gradoDiscapacidad"));
				DiscapacidadTrabajadores.setPorcentajeDiscapacidad(rs.getDouble("porcentajeDiscapacidad"));
				DiscapacidadTrabajadores.setCausaDiscapacidad(rs.getInt("causaDiscapacidad"));
				DiscapacidadTrabajadores.setMovilidadReducida(rs.getInt("movilidadReducida"));
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return DiscapacidadTrabajadores;
	}



	//Obtener discapacidad por id
	public static DiscapacidadTrabajadores getDiscapacidadById(int id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		DiscapacidadTrabajadores DiscapacidadTrabajadores = new DiscapacidadTrabajadores();

		try{
			sql = "SELECT * FROM sw_r_discapacidad_Trabajadores WHERE idDiscapacidad = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				DiscapacidadTrabajadores.setIdDiscapacidad(rs.getInt("idDiscapacidad"));
				DiscapacidadTrabajadores.setIdTrabajador(rs.getInt("idTrabajadores"));
				DiscapacidadTrabajadores.setnCredencial(rs.getString("nCredencial"));
				DiscapacidadTrabajadores.setFechaReevaluacion(rs.getString("fechaReevaluacion"));
				DiscapacidadTrabajadores.setGradoDiscapacidad(rs.getInt("gradoDiscapacidad"));
				DiscapacidadTrabajadores.setPorcentajeDiscapacidad(rs.getDouble("porcentajeDiscapacidad"));
				DiscapacidadTrabajadores.setCausaDiscapacidad(rs.getInt("causaDiscapacidad"));
				DiscapacidadTrabajadores.setMovilidadReducida(rs.getInt("movilidadReducida"));
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return DiscapacidadTrabajadores;
	}


	//delete discapacidad 
	public static boolean deleteDiscapacidad(int id) throws Exception{

		PreparedStatement ps = null;
		String sql ="";
		ConnectionDB db = new ConnectionDB();

		try{
			sql = "DELETE FROM sw_r_discapacidad_Trabajadores WHERE idDiscapacidad = '"+id+"'";
			ps = db.conn.prepareStatement(sql);

			return true;
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return false;
	}



	/**
	 * Retorna String Convertido en YYYY-MM-DD Si el valor es null o vacio retorna null
	 * @param fecha
	 * @return String
	 * @throws ParseException
	 */
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
