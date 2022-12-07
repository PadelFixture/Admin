package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import lib.classSW.CentroCostoTrabajador;
import lib.db.ConnectionDB;

public class CentroCostoTrabajadorDB {

	//Insert CentroCostoTrabajador
	public static CentroCostoTrabajador insertCentroCostoTrabajador(CentroCostoTrabajador CentroCostoTrabajador) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;
		int i = 1;

		try{
			
			sql = " SELECT idCentroCosto FROM sw_r_centroCostoTrabajador  WHERE idCentroCosto = ? ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(i++, CentroCostoTrabajador.getIdCentroCosto());
			rs = ps.executeQuery();
			

			if(!rs.next()){
				i=1;
				rs.close();
				ps.close();
				
				sql = "INSERT INTO sw_r_centroCostoTrabajador (idCentroCosto) VALUES (?) ";
				ps = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(i++, CentroCostoTrabajador.getIdCentroCosto());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();

				int key = 0;
				if(rs.next()){
					key = rs.getInt(1);
				}

				CentroCostoTrabajador.setIdCentroCosto(key);

			}
			
			i=1;
			rs.close();
			ps.close();
			
			sql = "UPDATE sw_r_centroCostoTrabajador SET "
					+ "  "
					+ " codTrabajador = ? , " //1
					+ " sociedad = ?, " //2
					+ " idHuerto = ?, " //3
					+ " idCECO = ?, " //4
					+ " ordenCO = ?, " //5
					+ " cuenta = ?, " //6
					+ " pesos = ?, " //7
					+ " porcentaje = ? " //8
					+ " WHERE idCentroCosto = ? "; //9

			ps = db.conn.prepareStatement(sql);

			ps.setInt(i++, CentroCostoTrabajador.getCodTrabajador()); //1
			ps.setString(i++, CentroCostoTrabajador.getSociedad()); //2
			ps.setString(i++, CentroCostoTrabajador.getIdHuerto()); //3
			ps.setString(i++, CentroCostoTrabajador.getIdCECO()); //4
			ps.setString(i++, CentroCostoTrabajador.getOrdenCO()); //5
			ps.setString(i++, CentroCostoTrabajador.getCuenta()); //6
			ps.setDouble(i++, CentroCostoTrabajador.getPesos()); //7
			ps.setDouble(i++, CentroCostoTrabajador.getPorcentaje()); //8
			ps.setInt(i++, CentroCostoTrabajador.getIdCentroCosto()); //9
			

			ps.execute();

			

		}catch(Exception e){
			System.out.println("Error insertCentroCostoTrabajador:" + e.getMessage());
		}finally{
			db.conn.close();
		}
		
		return CentroCostoTrabajador;


	}


	//Obtener Todos las CentroCostoTrabajadores
	public static ArrayList<CentroCostoTrabajador> getCentroCostoTrabajador() throws Exception {

		PreparedStatement ps = null;
		String sql="";
		ArrayList<CentroCostoTrabajador> lista = new ArrayList<CentroCostoTrabajador>();
		ConnectionDB db = new ConnectionDB();

		try{

			sql = "select * from sw_r_centroCostoTrabajador";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				CentroCostoTrabajador sc = new CentroCostoTrabajador();

//				sc.setIdCentroCostoTrabajador(rs.getInt("idCentroCostoTrabajador"));
//				sc.setIdTrabajador(rs.getInt("idTrabajador"));
//				sc.setIdDivision(rs.getInt("idDivision"));
//				sc.setIdSubDivision(rs.getInt("idSubDivision"));
				sc.setPorcentaje(rs.getInt("porcentaje"));

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


	//Obtener CentroCostoTrabajador por Id
	public static ArrayList<CentroCostoTrabajador> getCentroCostoTrabajadorById(String id)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();

		ArrayList<CentroCostoTrabajador> lista = new ArrayList<CentroCostoTrabajador>();

		try{
			sql = "SELECT * FROM sw_r_centroCostoTrabajador WHERE codTrabajador = '"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				CentroCostoTrabajador sc = new CentroCostoTrabajador();
				sc.setIdCentroCosto(rs.getInt("idCentroCosto"));
				sc.setCodTrabajador(rs.getInt("codTrabajador"));
				sc.setSociedad(rs.getString("sociedad"));
				sc.setIdHuerto(rs.getString("idHuerto"));
				sc.setIdCECO(rs.getString("idCECO"));
				sc.setOrdenCO(rs.getString("ordenCO"));
				sc.setCuenta(rs.getString("cuenta"));
				sc.setPesos(rs.getDouble("pesos"));
				sc.setPorcentaje(rs.getDouble("porcentaje"));
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

	
	//Obtener CentroCostoTrabajador por Id
	public static boolean deleteCentroCostoTrabajadorById(String id)  throws Exception{

			PreparedStatement ps = null;
			String sql="";
			ConnectionDB db = new ConnectionDB();

			CentroCostoTrabajador sc = new CentroCostoTrabajador();

			try{
				sql = "DELETE FROM sw_r_centroCostoTrabajador WHERE idCentroCosto = '"+id+"'";
				ps = db.conn.prepareStatement(sql);
				ps.execute();
				
			}catch (Exception e){
				System.out.println("Error: " + e.getMessage());
				return false;
			}finally {
				ps.close();
				db.close();
			}		
			return true;
		}
	


	//Actualizar CentroCostoTrabajador
	public static boolean updateCentroCostoTrabajador(CentroCostoTrabajador CentroCostoTrabajador) throws  Exception{

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		int i = 1;

		try {

			sql = ""
					+ " UPDATE sw_r_centroCostoTrabajador "
					+ " SET "
					+ " idTrabajador = ?, "
					+ " idDivision = ?, "
					+ " idSubDivision = ?, "
					+ " porcentaje = ? "
					+ " WHERE idCentroCostoTrabajador = ?";

			ps = db.conn.prepareStatement(sql);

//			ps.setInt(i++, CentroCostoTrabajador.getIdTrabajador());
//			ps.setInt(i++, CentroCostoTrabajador.getIdDivision());
//			ps.setInt(i++, CentroCostoTrabajador.getIdSubDivision());
//			ps.setDouble(i++, CentroCostoTrabajador.getPorcentaje());
//			ps.setInt(i++, CentroCostoTrabajador.getIdCentroCostoTrabajador());

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


}
