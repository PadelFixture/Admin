package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lib.classSW.TrabajadorLicenciaConducir;
import lib.db.ConnectionDB;

public class TrabajadorLicenciaConducirDB {

	
	
	// insertar trabajador licencia donducir
	public static boolean insertTrabajadorLicenciaConducir(TrabajadorLicenciaConducir TrabajadorLicenciaConducir) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int i = 1;

		try{
			sql = " INSERT into sw_r_trabajadorLicenciaConducir "
					+ " VALUES (?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++,TrabajadorLicenciaConducir.getIdTrabajadorLicenciaConducir());
			ps.setInt(i++,TrabajadorLicenciaConducir.getIdTrabajador());
			ps.setInt(i++,TrabajadorLicenciaConducir.getIdTipoLicenciaConducir1());
			ps.setInt(i++,TrabajadorLicenciaConducir.getIdTipoLicenciaConducir1());
			ps.setInt(i++,TrabajadorLicenciaConducir.getIdTipoLicenciaConducir1());
			ps.setString(i++,convertStringToYYYYMMDD(TrabajadorLicenciaConducir.getFechaVencimiento()));
			ps.setString(i++,TrabajadorLicenciaConducir.getNumeroLicencia());
			
		
			ps.execute();
			return true;
		}catch(Exception e){

			System.out.println("Error al ingresar Trabajador licencia conducir:" + e.getMessage());
			e.printStackTrace();

		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}// fin clase insert
	
	
  // get trabajador licencia conducir
	public static ArrayList<TrabajadorLicenciaConducir> getTrabajadorLicenciaConducir() throws Exception 
	{
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
        ArrayList<TrabajadorLicenciaConducir> lista = new ArrayList<TrabajadorLicenciaConducir>();
		
		try{
			sql = "select * from sw_r_trabajadorLicenciaConducir ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
			
				TrabajadorLicenciaConducir TrabajadorLicenciaConducir = new TrabajadorLicenciaConducir(); 
				
				TrabajadorLicenciaConducir.setIdTrabajadorLicenciaConducir(rs.getInt("idTrabajadorLicenciaConducir"));
				TrabajadorLicenciaConducir.setIdTrabajador(rs.getInt("idTrabajador"));
				TrabajadorLicenciaConducir.setIdTipoLicenciaConducir1(rs.getInt("idTipoLicenciaConducir1"));
				TrabajadorLicenciaConducir.setIdTipoLicenciaConducir2(rs.getInt("idTipoLicenciaConducir2"));
				TrabajadorLicenciaConducir.setIdTipoLicenciaConducir3(rs.getInt("idTipoLicenciaConducir3"));
				TrabajadorLicenciaConducir.setFechaVencimiento(rs.getString("fechaVencimiento"));
				TrabajadorLicenciaConducir.setNumeroLicencia(rs.getString("numeroLicencia"));
				
				lista.add(TrabajadorLicenciaConducir);

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
		return lista;
	}// fin metodo get


	public static TrabajadorLicenciaConducir getTrabajadorLicenciaConducirByIdTrabajador(int id) throws Exception  {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		TrabajadorLicenciaConducir TrabajadorLicenciaConducir = new TrabajadorLicenciaConducir(); 

		try{
			sql = "select * from sw_r_trabajadorLicenciaConducir "
			+ " where idTrabajador = '"+id+"' ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
			
				TrabajadorLicenciaConducir.setIdTrabajadorLicenciaConducir(rs.getInt("idTrabajadorLicenciaConducir"));
				TrabajadorLicenciaConducir.setIdTrabajador(rs.getInt("idTrabajador"));
				TrabajadorLicenciaConducir.setIdTipoLicenciaConducir1(rs.getInt("idTipoLicenciaConducir1"));
				TrabajadorLicenciaConducir.setIdTipoLicenciaConducir2(rs.getInt("idTipoLicenciaConducir2"));
				TrabajadorLicenciaConducir.setIdTipoLicenciaConducir3(rs.getInt("idTipoLicenciaConducir3"));
				TrabajadorLicenciaConducir.setFechaVencimiento(rs.getString("fechaVencimiento"));
				TrabajadorLicenciaConducir.setNumeroLicencia(rs.getString("numeroLicencia"));

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
		return TrabajadorLicenciaConducir;
		
	}// fin metodo
	
	//update trabajador licencia conducir
		public static boolean updateTrabajadorLicenciaConducir(TrabajadorLicenciaConducir TrabajadorLicenciaConducir) throws Exception{

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			ResultSet rs = null;

			try{

				int i = 1;

				sql = " SELECT idTrabajadorLicenciaConducir FROM sw_r_trabajadorLicenciaConducir  WHERE idTrabajadorLicenciaConducir = ? ";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(i++, TrabajadorLicenciaConducir.getIdTrabajadorLicenciaConducir());
				rs = ps.executeQuery();

				if(!rs.next()){
					i=1;
					rs.close();
					ps.close();

					sql= " INSERT INTO sw_r_trabajadorLicenciaConducir (idTrabajadorLicenciaConducir) VALUES (?) ";
					ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(i++, TrabajadorLicenciaConducir.getIdTrabajadorLicenciaConducir());
					ps.executeUpdate();
					rs = ps.getGeneratedKeys();

					int key = 0;
					if(rs.next()){
						key = rs.getInt(1);
					}

					TrabajadorLicenciaConducir.setIdTrabajadorLicenciaConducir(key);
				}

				i=1;
				rs.close();
				ps.close();

				sql = " UPDATE sw_r_trabajadorLicenciaConducir SET idTrabajador = ? , "
						+ " idTipoLicenciaConducir1 = ? , idTipoLicenciaConducir2 = ? , idTipoLicenciaConducir3 = ? , fechaVencimiento = ? , numeroLicencia = ? "
						+ " WHERE idTrabajadorLicenciaConducir = ? ";

				ps = db.conn.prepareStatement(sql);

				
				//ps.setInt(i++, TrabajadorLicenciaConducir.getIdTrabajadorLicenciaConducir());
				ps.setInt(i++, TrabajadorLicenciaConducir.getIdTrabajador());
				ps.setInt(i++, TrabajadorLicenciaConducir.getIdTipoLicenciaConducir1());
				ps.setInt(i++, TrabajadorLicenciaConducir.getIdTipoLicenciaConducir2());
				ps.setInt(i++, TrabajadorLicenciaConducir.getIdTipoLicenciaConducir3());
				ps.setString(i++, convertStringToYYYYMMDD(TrabajadorLicenciaConducir.getFechaVencimiento()));
				ps.setString(i++, TrabajadorLicenciaConducir.getNumeroLicencia());
				ps.setInt(i++, TrabajadorLicenciaConducir.getIdTrabajadorLicenciaConducir());
				
				ps.toString();
				
				ps.execute();

				return true;

			}catch (Exception e) {
				System.out.println("Error update Tabajador licencia conducir: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return false;
		}// fin metodo update
		
		
	
	
	
	public static boolean deleteTrabajadorLicenciaConducirById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_r_trabajadorLicenciaConducir WHERE idTrabajadorLicenciaConducir="+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}// fin metodo eliminar
	
	
	
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


	
	
	
}// fin clase
	
	
	
	
	
	
	
	
	
	
	
	

