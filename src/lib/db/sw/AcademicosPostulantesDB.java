package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lib.classSW.AcademicosPostulantes;
import lib.db.ConnectionDB;

public class AcademicosPostulantesDB {
	//insert academico
	public static boolean insertAcademicosPostulantes(AcademicosPostulantes AcademicosPostulantes) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try{
			sql = " INSERT INTO sw_academicosPostulantes "
					+ " VALUES (?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, AcademicosPostulantes.getIdAcademicos());
			ps.setInt(2, AcademicosPostulantes.getNivelEducacion());
			ps.setInt(3, AcademicosPostulantes.getNivel());
			ps.setInt(4, AcademicosPostulantes.getCarrera());
			ps.setInt(5, AcademicosPostulantes.getInstituciones());
			ps.setInt(6, AcademicosPostulantes.getNombreInstitucion());
			ps.setString(7, convertStringToYYYYMMDD(AcademicosPostulantes.getFechaDesdeInstitucion()));		
			ps.setString(8, convertStringToYYYYMMDD(AcademicosPostulantes.getFechaHastaInstitucion()));
			ps.setInt(9, AcademicosPostulantes.getIdTrabajador());

			ps.execute();
			return true;
		}catch(Exception e){

			System.out.println("Error insertAcademicoPostulantes:" + e.getMessage());
			e.printStackTrace();

		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}

	//update academicos
	public static boolean updateAcademicosPostulantes(AcademicosPostulantes AcademicosPostulantes) throws Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;


		try{

			int i = 1;

			sql = " SELECT idAcademicos FROM sw_academicosPostulantes WHERE idAcademicos = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, AcademicosPostulantes.getIdAcademicos());
			rs = ps.executeQuery();

			if(!rs.next()){
				i=1;
				rs.close();
				ps.close();

				sql= " INSERT INTO sw_academicosPostulantes (idTrabajador) VALUES (?) ";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(i++, AcademicosPostulantes.getIdTrabajador());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if(rs.next()){
					key = rs.getInt(1);
				}

				AcademicosPostulantes.setIdAcademicos(key);

			}

			i=1;
			rs.close();
			ps.close();

			sql = " UPDATE sw_academicosPostulantes SET nivelEducacion = ? , nivel = ? , "
					+ " carrera = ? , instituciones = ? , nombreInstitucion = ? , "
					+ " fechaDesdeInstitucion = ?, fechaHastaInstitucion = ? "
					+ " WHERE idAcademicos = ? ";

			ps = db.conn.prepareStatement(sql);

			ps.setInt (i++,  AcademicosPostulantes.getNivelEducacion());
			ps.setInt (i++,  AcademicosPostulantes.getNivel());
			ps.setInt (i++,  AcademicosPostulantes.getCarrera());
			ps.setInt (i++,  AcademicosPostulantes.getInstituciones());
			ps.setInt (i++,  AcademicosPostulantes.getNombreInstitucion());
			ps.setString(i++,  convertStringToYYYYMMDD(AcademicosPostulantes.getFechaDesdeInstitucion()));		
			ps.setString(i++,  convertStringToYYYYMMDD(AcademicosPostulantes.getFechaHastaInstitucion()));	
			ps.setInt (i++,  AcademicosPostulantes.getIdAcademicos());


			ps.execute();

			return true;

		}catch (Exception e) {
			System.out.println("Error updateAcademicoPostulantes: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return false;
	}

	//get academicos
	public static AcademicosPostulantes getAcademicosPostulantesByIdTrabajador (int idTrabajador) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		AcademicosPostulantes AcademicosPostulantes = new AcademicosPostulantes(); 

		try{
			sql = "select * from sw_academicosPostulantes "
					+ " where idTrabajador = '"+idTrabajador+"' "
					+ " order by fechaDesdeInstitucion desc limit 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				AcademicosPostulantes.setIdAcademicos(rs.getInt("idAcademicos"));
				AcademicosPostulantes.setNivelEducacion(rs.getInt("nivelEducacion"));
				AcademicosPostulantes.setNivel(rs.getInt("nivel"));
				AcademicosPostulantes.setCarrera(rs.getInt("carrera"));
				AcademicosPostulantes.setInstituciones(rs.getInt("instituciones"));
				AcademicosPostulantes.setNombreInstitucion(rs.getInt("nombreInstitucion"));
				AcademicosPostulantes.setFechaDesdeInstitucion(rs.getString("fechaDesdeInstitucion"));
				AcademicosPostulantes.setFechaHastaInstitucion(rs.getString("fechaHastaInstitucion"));
				AcademicosPostulantes.setIdTrabajador(rs.getInt("idTrabajador"));

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
		return AcademicosPostulantes;
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
