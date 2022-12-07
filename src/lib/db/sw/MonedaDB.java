package lib.db.sw;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import lib.classSW.Moneda;
import lib.db.ConnectionDB;

public class MonedaDB {


	//Insert sociedad TODO: Agregar los otros campos
	public static boolean insertMoneda(Moneda moneda) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		int i = 1;

		try{

			sql = "INSERT INTO sw_m_moneda ( idMoneda , sueldoMinimo  ) "
					+ " VALUES (?,?) ";

			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, moneda.getIdMoneda());
			ps.setDouble(i++, moneda.getSueldoMinimo());


			ps.execute();

			return true;

		}catch(Exception ex){

		}finally{
			db.conn.close();
		}

		return false;
	}

	//Actualizar moneda

	//Borrar moneda por Id

	//Obtener moneda por IdMoneda TODO: Obtener los demas valores
	public static Moneda getMonedaById(String id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		Moneda md = new Moneda();

		try{
			sql = "SELECT * FROM sw_m_moneda WHERE idMoneda = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				md.setIdMoneda(rs.getInt("idMoneda"));

			}			
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return md;
	}

	//Obtener Todos las sociedades
	public static ArrayList<Moneda> getMonedas() throws Exception {

		PreparedStatement ps = null;
		String sql="";
		ArrayList<Moneda> lista = new ArrayList<Moneda>();
		ConnectionDB db = new ConnectionDB();

		try{

			sql = "select * from sw_m_moneda order by fecha desc";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				Moneda md = new Moneda();
				md.setIdMoneda(rs.getInt("idMoneda"));

				lista.add(md);
			}


		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		

		return lista;

	}



	//Obtener Todos las sociedades con Filtros

	//Obtener Todos los sociedades totales


}

