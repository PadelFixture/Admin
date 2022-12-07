package SWDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSW.CuentasSap;
import lib.db.ConnectionDB;

public class sw_CuentasSapDB {
	
	public static ArrayList<CuentasSap> getCuentasSAP() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CuentasSap> lista = new ArrayList<CuentasSap>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="select * from Cuentas_SAP";
             System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CuentasSap hd = new CuentasSap();
				
				hd.setIdCuenta(rs.getInt("id"));
				hd.setNumerocuenta(rs.getString("n_cuenta"));
				hd.setNombrecuenta(rs.getString("descripcion"));
				
				

				lista.add(hd);
				
			}			
		}catch (SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	
	//-------------------- actualizar numero de cuenta SAP -----------------------------------------
	public static boolean updateCuentaSap(CuentasSap r) throws  Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {
			
			sql = "UPDATE Cuentas_SAP SET n_cuenta = "+r.getNumerocuenta()+" WHERE id = "+r.getIdCuenta()+""; 
				ps = db.conn.prepareStatement(sql);
				System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
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
