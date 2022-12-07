package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lib.classSW.sw_academicos;
import lib.db.ConnectionDB;

public class academicosDB {
	//insert academico
	public static boolean insertAcademicos(sw_academicos sw_academicos) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try{
			sql = " INSERT into sw_academicos "
					+ " VALUES (?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, sw_academicos.getIdAcademicos());
			ps.setInt(2, sw_academicos.getNivelEducacion());
			ps.setInt(3, sw_academicos.getNivel());
			ps.setInt(4, sw_academicos.getCarrera());
			ps.setInt(5, sw_academicos.getInstituciones());
			ps.setInt(6, sw_academicos.getNombreInstitucion());
			ps.setString(7, convertStringToYYYYMMDD(sw_academicos.getFechaDesdeInstitucion()));		
			ps.setString(8, convertStringToYYYYMMDD(sw_academicos.getFechaHastaInstitucion()));
			ps.setInt(9, sw_academicos.getIdTrabajador());

			ps.execute();
			return true;
		}catch(Exception e){

			System.out.println("Error insertAcademico:" + e.getMessage());
			e.printStackTrace();

		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}

	//update academicos
	public static boolean updateAcademicos(sw_academicos sw_academicos) throws Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;


		try{

			int i = 1;

			sql = " SELECT idAcademicos FROM sw_academicos WHERE idAcademicos = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, sw_academicos.getIdAcademicos());
			rs = ps.executeQuery();

			if(!rs.next()){
				i=1;
				rs.close();
				ps.close();

				sql= " INSERT INTO sw_academicos (idTrabajador) VALUES (?) ";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(i++, sw_academicos.getIdTrabajador());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if(rs.next()){
					key = rs.getInt(1);
				}

				sw_academicos.setIdAcademicos(key);

			}

			i=1;
			rs.close();
			ps.close();

			sql = " UPDATE sw_academicos SET nivelEducacion = ? , nivel = ? , "
					+ " carrera = ? , instituciones = ? , nombreInstitucion = ? , "
					+ " fechaDesdeInstitucion = ?, fechaHastaInstitucion = ? "
					+ " WHERE idAcademicos = ? ";

			ps = db.conn.prepareStatement(sql);

			ps.setInt (i++,  sw_academicos.getNivelEducacion());
			ps.setInt (i++,  sw_academicos.getNivel());
			ps.setInt (i++,  sw_academicos.getCarrera());
			ps.setInt (i++,  sw_academicos.getInstituciones());
			ps.setInt (i++,  sw_academicos.getNombreInstitucion());
			ps.setString(i++,  convertStringToYYYYMMDD(sw_academicos.getFechaDesdeInstitucion()));		
			ps.setString(i++,  convertStringToYYYYMMDD(sw_academicos.getFechaHastaInstitucion()));	
			ps.setInt (i++,  sw_academicos.getIdAcademicos());


			ps.execute();

			return true;

		}catch (Exception e) {
			System.out.println("Error updateAcademico: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}

	//get academicos
	public static sw_academicos getAcademicosByIdTrabajador (int idTrabajador) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		sw_academicos sw_academicos = new sw_academicos(); 

		try{
			sql = "select * from sw_academicos "
					+ " where idTrabajador = '"+idTrabajador+"' "
					+ " order by fechaDesdeInstitucion desc limit 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				sw_academicos.setIdAcademicos(rs.getInt("idAcademicos"));
				sw_academicos.setNivelEducacion(rs.getInt("nivelEducacion"));
				sw_academicos.setNivel(rs.getInt("nivel"));
				sw_academicos.setCarrera(rs.getInt("carrera"));
				sw_academicos.setInstituciones(rs.getInt("instituciones"));
				sw_academicos.setNombreInstitucion(rs.getInt("nombreInstitucion"));
				sw_academicos.setFechaDesdeInstitucion(rs.getString("fechaDesdeInstitucion"));
				sw_academicos.setFechaHastaInstitucion(rs.getString("fechaHastaInstitucion"));
				sw_academicos.setIdTrabajador(rs.getInt("idTrabajador"));

			}

		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}	
		return sw_academicos;
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
