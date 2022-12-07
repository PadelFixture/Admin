package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.ClassSASW.parametros;
import lib.db.ConnectionDB;
import lib.db.SASW.parametrosDB;

public class SupervisorDB extends parametrosDB{
	public static ArrayList<parametros> getSupervisores() throws Exception
	{
		return getParamsByCode("SUPERVISOR");
	}
	public static parametros getSupervisorById(String id) throws SQLException
	{
		return getParametroByCodigoLLave("SUPERVISOR", id);
	}
	public static boolean deleteSupervisorById(String id) throws SQLException
	{
		return deleteParById("SUPERVISOR", id);
	}
	public static boolean createSupervisor(parametros param, String Codigo) throws SQLException
	{
		PreparedStatement ps = null, ps2=null;
		String sql = "INSERT INTO parametros (codigo, llave, descripcion, activo, codPrevired, rutParametro) VALUES (?,?,?,?,?,?)";
		String sql2="SELECT * FROM SAN_CLEMENTE.parametros WHERE codigo='"+Codigo+"' AND activo=1";
		ConnectionDB  db = new ConnectionDB();	
		try {
			int llave=0;
			
			
			
			ps = db.conn.prepareStatement(sql);
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs = ps2.executeQuery(sql2);
			while(rs.next()){
				parametros pm = new parametros();
				pm.setLlave(rs.getString("llave"));
				if(Integer.parseInt(pm.getLlave())>llave){				
				llave=Integer.parseInt(pm.getLlave());
				}
				
			}
			
			
			
				llave=llave+1;
			ps.setString(1, param.getCodigo());
			ps.setString(2, ""+llave);
			ps.setString(3, param.getDescripcion());
			ps.setInt(4, 1);
			ps.setString(5, param.getCodPrevired());
			ps.setString(6, param.getRutParametro());
			
			return ps.execute();
		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
			return false;//end
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;//end
		}finally {


			ps.close();
			
			db.close();
		}		
	
	}
}
