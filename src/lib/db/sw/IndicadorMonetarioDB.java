package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import lib.classSW.IndicadorMonetario;
import lib.db.ConnectionDB;

public class IndicadorMonetarioDB {

	public static boolean updateIndicador(IndicadorMonetario indicador) throws SQLException {
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		try{
			String sql="UPDATE sw_rhvalorMoneda SET idMoneda=?, valor=?, fecha=? WHERE idValor="+indicador.getIdValor();
			ps = db.conn.prepareStatement(sql);
			
			
			ps.setInt(1, indicador.getIdMoneda());
			ps.setBigDecimal(2, indicador.getValor());
			ps.setString(3,indicador.getFecha());
			
			ps.executeUpdate();
			return true;
			}
			catch(Exception ex){
				return false;
			}
			finally{
				db.conn.close();
			}
	}

	public static IndicadorMonetario getIndicadorById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		IndicadorMonetario indicador=new IndicadorMonetario(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_rhvalorMoneda where idValor="+id;
			ps = db.conn.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				IndicadorMonetario ind= new IndicadorMonetario();				
				ind.setIdValor(id);
				ind.setIdMoneda(rs.getInt("idMoneda"));
				ind.setValor(rs.getBigDecimal("valor"));
				ind.setFecha(rs.getString("fecha"));
				indicador=ind;
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return indicador;
	}


	public static ArrayList<IndicadorMonetario> getIndicadores() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<IndicadorMonetario> indicadores=new ArrayList<IndicadorMonetario>(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_rhvalorMoneda inner join (select * from  sw_rhmoneda where visible=1) as rh on sw_rhvalorMoneda.idMoneda=rh.idMoneda order by fecha desc" ;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){
				if(rs.getInt("visible")==1){
				IndicadorMonetario indicador= new IndicadorMonetario();				
				indicador.setIdValor(rs.getInt("idValor"));
				indicador.setIdMoneda(rs.getInt("idMoneda"));
				indicador.setValor(rs.getBigDecimal("valor"));
				indicador.setFecha(rs.getString("fecha"));
				indicador.setNombreMoneda(rs.getString("moneda"));
				indicadores.add(indicador);
				}
				
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return indicadores;
	}
	public static ArrayList<IndicadorMonetario> getPeriodos() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<IndicadorMonetario> indicadores=new ArrayList<IndicadorMonetario>(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT distinct(fecha) FROM sw_rhvalorMoneda order by fecha ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){
				
				IndicadorMonetario indicador= new IndicadorMonetario();				
				indicador.setFecha(rs.getString("fecha"));
				indicadores.add(indicador);
				
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return indicadores;
	}

	public static boolean deleteIndicadorById(int id) throws SQLException {
		PreparedStatement ps= null;
		String sql="";
		ConnectionDB db= new ConnectionDB();
		try{
			sql = "DELETE FROM sw_rhvalorMoneda WHERE idValor="+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;			

		}catch (Exception e){
			return false;
		}finally {
			ps.close();
			db.close();
		}
	}

	public static boolean createIndicador(IndicadorMonetario indicador) throws SQLException {
		PreparedStatement ps = null;
		
		String sql="INSERT INTO sw_rhvalorMoneda (idMoneda, valor, fecha) VALUES (?,?,?)";
		ConnectionDB db= new ConnectionDB();
		try{
			ps=db.conn.prepareStatement(sql);
			ps.setString(1, ""+indicador.getIdMoneda());
			ps.setBigDecimal(2, indicador.getValor());
			ps.setString(3, indicador.getFecha());
			return ps.execute();
			
		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {

			ps.close();
			db.close();
		}
		return false;//end
	}

	public static ArrayList<IndicadorMonetario> getIndicadoresByType(int tipo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<IndicadorMonetario> indicadores=new ArrayList<IndicadorMonetario>(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM sw_rhvalorMoneda inner join (select * from  sw_rhmoneda where visible=1) as rh on sw_rhvalorMoneda.idMoneda=rh.idMoneda where sw_rhvalorMoneda.idMoneda='"+tipo+"' order by fecha desc" ;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){
				if(rs.getInt("visible")==1){
				IndicadorMonetario indicador= new IndicadorMonetario();				
				indicador.setIdValor(rs.getInt("idValor"));
				indicador.setIdMoneda(rs.getInt("idMoneda"));
				indicador.setValor(rs.getBigDecimal("valor"));
				indicador.setFecha(rs.getString("fecha"));
				indicador.setNombreMoneda(rs.getString("moneda"));
				indicadores.add(indicador);
				}
				
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return indicadores;
	}

	public static ArrayList<IndicadorMonetario> getPeriodosByTipo(int tipo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<IndicadorMonetario> indicadores=new ArrayList<IndicadorMonetario>(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT distinct(fecha) FROM sw_rhvalorMoneda where idMoneda='"+tipo+"' order by fecha ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){
				
				IndicadorMonetario indicador= new IndicadorMonetario();				
				indicador.setFecha(rs.getString("fecha"));
				indicadores.add(indicador);
				
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return indicadores;
	}
	

}
