package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.tiposMovimiento;

import lib.db.ConnectionDB;

public class tiposMovimientoDB {

	//Insert tiposMovimiento
		public static boolean insertTiposMovimiento(tiposMovimiento tiposMovimiento) throws Exception {

			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();

			try{

				sql = "INSERT INTO tipos_movimiento ( idTiposMovimiento, nombre, descripcion ) "
						+ " VALUES (?,?,?) ";

				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, tiposMovimiento.getIdTiposMovimiento());
				ps.setString(2, tiposMovimiento.getNombre());
				ps.setString(3, tiposMovimiento.getDescripcion());

				ps.execute();

				return true;

			}catch(Exception ex){

			}finally{
				db.conn.close();
			}

			return false;
		}

		//Actualizar tiposMovimiento

		//Borrar tiposMovimiento por Id

		//Obtener tiposMovimiento por Id
		public static tiposMovimiento getTiposMovimientoById(String id)  throws Exception{

			PreparedStatement ps = null;
			String sql="";
			ConnectionDB db = new ConnectionDB();

			tiposMovimiento sc = new tiposMovimiento();

			try{
				sql = "SELECT * FROM tipos_movimiento WHERE idTiposMovimiento = '"+id+"'";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while(rs.next()){

					sc.setIdTiposMovimiento(rs.getInt("idtiposMovimiento"));
					sc.setNombre(rs.getString("nombre"));
					sc.setDescripcion(rs.getString("descripcion"));
					
				}			
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			return sc;
		}

		//Obtener Todos las tiposMovimientoes
		public static ArrayList<tiposMovimiento> getTiposMovimiento() throws Exception {
			
			PreparedStatement ps = null;
			String sql="";
			ArrayList<tiposMovimiento> lista = new ArrayList<tiposMovimiento>();
			ConnectionDB db = new ConnectionDB();
			
			try{
				
				sql = " SELECT * FROM tipos_movimiento ";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				
				while(rs.next()){
					tiposMovimiento sc = new tiposMovimiento();
					sc.setIdTiposMovimiento(rs.getInt("idtiposMovimiento"));
					sc.setNombre(rs.getString("nombre"));
					sc.setDescripcion(rs.getString("descripcion"));
					lista.add(sc);
				}
				

			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
			}finally {
				ps.close();
				db.close();
			}		
			
			return lista;
			
		}

		

		//Obtener Todos las tiposMovimientoes con Filtros

		//Obtener Todos los tiposMovimientoes totales


}
