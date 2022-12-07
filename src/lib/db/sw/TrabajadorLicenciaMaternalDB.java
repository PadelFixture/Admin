package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.TrabajadorLicenciaMaternal;
import lib.db.ConnectionDB;
import lib.utils.GeneralUtility;

public class TrabajadorLicenciaMaternalDB {

	
	// insertar trabajador licencia maternal
	public static boolean insertTrabajadorLicenciaMaternal(TrabajadorLicenciaMaternal TrabajadorLicenciaMaternal) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		try{
			sql = " INSERT into sw_r_trabajadorLicenciaConducir "
					+ " VALUES (?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			
			ps.setInt(1, TrabajadorLicenciaMaternal.getIdTrabajadorLicenciaMaternal());
			ps.setString(2, GeneralUtility.convertStringToYYYYMMDD(TrabajadorLicenciaMaternal.getFechaTerminoContrato()));
			ps.setString(3, GeneralUtility.convertStringToYYYYMMDD(TrabajadorLicenciaMaternal.getFechaConcepcion()));
			ps.setInt(4, TrabajadorLicenciaMaternal.getIdLicenciaMaternal());
			ps.setInt(5, TrabajadorLicenciaMaternal.getIdTrabajador());
			
			ps.execute();
			return true;
		}catch(Exception e){

			System.out.println("Error al ingresar Trabajador licencia maternal:" + e.getMessage());
			e.printStackTrace();

		}finally{
			ps.close();
			db.conn.close();
		}
		return false;
	}// fin clase insert
	
	  // get trabajador licencia Maternal
		public static ArrayList<TrabajadorLicenciaMaternal> getTrabajadorLicenciaMaternal() throws Exception 
		{
			
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
	        ArrayList<TrabajadorLicenciaMaternal> lista = new ArrayList<TrabajadorLicenciaMaternal>();
			
			try{
				sql = "select * from sw_r_trabajadorLicenciaMaternal ";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){
				
					TrabajadorLicenciaMaternal TrabajadorLicenciaMaternal = new TrabajadorLicenciaMaternal(); 
					
					TrabajadorLicenciaMaternal.setIdTrabajadorLicenciaMaternal(rs.getInt("idTrabajadorLicenciaMaternal"));
					TrabajadorLicenciaMaternal.setFechaTerminoContrato(rs.getString("fechaTerminoContrato"));
					TrabajadorLicenciaMaternal.setFechaConcepcion(rs.getString("fechaConcepcion"));
					TrabajadorLicenciaMaternal.setIdLicenciaMaternal(rs.getInt("idLicenciaMaternal"));
					TrabajadorLicenciaMaternal.setIdTrabajador(rs.getInt("idTrabajador"));

					lista.add(TrabajadorLicenciaMaternal);

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
	
		public static TrabajadorLicenciaMaternal getTrabajadorLicenciaMaternalByIdTrabajador(int id) throws Exception  {
			
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			TrabajadorLicenciaMaternal TrabajadorLicenciaMaternal = new TrabajadorLicenciaMaternal(); 

			try{
				sql = "select * from sw_r_trabajadorLicenciaMaternal "
				+ " where idTrabajador = '"+id+"' ";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){
				
					TrabajadorLicenciaMaternal.setIdTrabajadorLicenciaMaternal(rs.getInt("idTrabajadorLicenciaMaternal"));
					TrabajadorLicenciaMaternal.setFechaTerminoContrato(rs.getString("fechaTerminoContrato"));
					TrabajadorLicenciaMaternal.setFechaConcepcion(rs.getString("fechaConcepcion"));
					TrabajadorLicenciaMaternal.setIdLicenciaMaternal(rs.getInt("idLicenciaMaternal"));
					TrabajadorLicenciaMaternal.setIdTrabajador(rs.getInt("idTrabajador"));

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
			return TrabajadorLicenciaMaternal;
			
		}// fin metodo
	
		//update trabajador licencia conducir
			public static boolean updateTrabajadorLicenciaMaternal(TrabajadorLicenciaMaternal TrabajadorLicenciaMaternal) throws Exception{

				PreparedStatement ps = null;
				String sql = "";
				ConnectionDB db = new ConnectionDB();
				ResultSet rs = null;


				try{

					int i = 1;

					sql = " SELECT idTrabajadorLicenciaMaternal FROM sw_r_trabajadorLicenciaMaternal  WHERE idTrabajadorLicenciaMaternal = ? ";
					ps = db.conn.prepareStatement(sql);
					ps.setInt(i++, TrabajadorLicenciaMaternal.getIdTrabajadorLicenciaMaternal());
					rs = ps.executeQuery();

					if(!rs.next()){
						i=1;
						rs.close();
						ps.close();

						sql= " INSERT INTO sw_r_trabajadorLicenciaMaternal (idTrabajadorLicenciaMaternal) VALUES (?) ";
						ps = db.conn.prepareStatement(sql);
						ps.setInt(i++, TrabajadorLicenciaMaternal.getIdTrabajadorLicenciaMaternal());
						ps.executeUpdate();
						rs = ps.getGeneratedKeys();

						int key = 0;
						if(rs.next()){
							key = rs.getInt(1);
						}

						TrabajadorLicenciaMaternal.setIdTrabajadorLicenciaMaternal(key);
					}

					i=1;
					rs.close();
					ps.close();

					sql = " UPDATE sw_r_trabajadorLicenciaMaternal SET idTrabajadorLicenciaMaternal = ? ,  "
							+ " fechaTerminoContrato = ? , fechaConcepcion = ? , idLicenciaMaternal = ? WHERE idTrabajadorLicenciaMaternal = ?  ";

					ps = db.conn.prepareStatement(sql);

					
					ps.setInt(i++, TrabajadorLicenciaMaternal.getIdTrabajadorLicenciaMaternal());
					ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(TrabajadorLicenciaMaternal.getFechaTerminoContrato()));
					ps.setString(i++, GeneralUtility.convertStringToYYYYMMDD(TrabajadorLicenciaMaternal.getFechaConcepcion()));
				    ps.setInt(i++, TrabajadorLicenciaMaternal.getIdLicenciaMaternal());
				    ps.setInt(i++, TrabajadorLicenciaMaternal.getIdTrabajadorLicenciaMaternal());
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
		
		
	
		
		public static boolean deleteTrabajadorLicenciaMaternalById(int id) throws SQLException {
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			try{
				sql ="DELETE FROM sw_r_trabajadorLicenciaMaternal WHERE idTrabajadorLicenciaMaternal="+id;
				ps = db.conn.prepareStatement(sql);					
				ps.execute();
				return true;
			}catch(Exception ex){
				return false;
			}finally{
				db.conn.close();
			}
		}
		
		
		
		
		
}// fin clase
