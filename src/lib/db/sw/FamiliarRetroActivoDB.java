package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.FamiliarRetroActivo;
import lib.db.ConnectionDB;

public class FamiliarRetroActivoDB {

	public static boolean insertFamiliarRetroActivo(FamiliarRetroActivo retroActivo) throws Exception {
		
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB db = new ConnectionDB();
			int i = 1;

			try{
				sql = " INSERT INTO sw_familiarRetroActivo "
						+ " VALUES (?,?,?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				
				ps.setInt(i++, retroActivo.getIdFamiliarRetroActivo());
				ps.setInt(i++, retroActivo.getIdFamiliar());
				ps.setInt(i++, retroActivo.getCodTrabajador());
				ps.setDouble(i++, retroActivo.getMonto());
				ps.setInt(i++, retroActivo.getPeriodo());
				ps.setInt(i++, retroActivo.getIdContrato());
				
				ps.execute();
				return true;
			}catch(Exception e){

				System.out.println("Error insertRetroActivo:" + e.getMessage());
				e.printStackTrace();

			}finally{
				ps.close();
				db.conn.close();
			}
			return false;
		}
		

	
public static ArrayList<FamiliarRetroActivo> getFamiliarRetroActivoByCodTrabajador(int codTrabajador) throws Exception  {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();

		ArrayList<FamiliarRetroActivo> lista = new ArrayList<FamiliarRetroActivo>(); 

		try{
			sql = "SELECT * FROM sw_familiarRetroActivo WHERE codTrabajador = '" + codTrabajador +"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){
				
				FamiliarRetroActivo familiarRetroActivo = new FamiliarRetroActivo();
			
				familiarRetroActivo = FamiliarRetroActivoDB.setObjectFamiliarRetroActivo(rs);
				
				lista.add(familiarRetroActivo);

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
		
	}// fin metodo
	
public static FamiliarRetroActivo setObjectFamiliarRetroActivo(ResultSet rs) throws SQLException {

	FamiliarRetroActivo familiarRetroActivo = new FamiliarRetroActivo();

	familiarRetroActivo.setCodTrabajador(rs.getInt("codTrabajador"));
	familiarRetroActivo.setIdContrato(rs.getInt("idContrato"));
	familiarRetroActivo.setIdFamiliar(rs.getInt("idFamiliar"));
	familiarRetroActivo.setIdFamiliarRetroActivo(rs.getInt("idFamiliarRetroActivo"));
	familiarRetroActivo.setMonto(rs.getDouble("Monto"));
	familiarRetroActivo.setPeriodo(rs.getInt("Periodo"));
	
	

	return familiarRetroActivo;

}



public static boolean deleteFamiliarRetroActivoById(String idFamiliarRetroActivo) throws Exception {
	
	PreparedStatement ps = null;
	String sql = "";
	ConnectionDB db = new ConnectionDB();

	try{
		sql = "DELETE FROM sw_familiarRetroActivo WHERE idFamiliarRetroActivo = '" + idFamiliarRetroActivo +"'";
		ps = db.conn.prepareStatement(sql);
		ps.executeUpdate();
		
	}catch (Exception e) {
		return false;
	}finally {
		ps.close();
		db.close();
	}	
	return true;
}

	
	
	
}

