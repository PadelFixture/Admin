package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import lib.classSW.AFP;
import lib.classSW.AFPdet;
import lib.classSW.TramoCarga;
import lib.classSW.periodoafp;

import lib.db.ConnectionDB;

public class AFPDB {
	/*------------------Debug For SQL SENTENCES------------------------------*/
	public static ResultSet getQuery(String query) throws SQLException
	{
		PreparedStatement ps = null;
		String sql=query;
		ConnectionDB db = new ConnectionDB();
		ResultSet rs = null;
		
		try{
			ps = db.conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);

			return rs;		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			
		}finally {
			ps.close();
			db.close();
		}		
		return rs;
	}
	/*------------------blanks------------------------------*/	
	public static AFP getBlankAFP()  throws Exception{

		new ConnectionDB();
		AFP afp = new AFP();

		return afp.createBlankAFP();
	}	
/*------------------CRUD------------------------------*/	
	
	
	
	/*------------------CREATE---------------------*/	
	
	public static boolean createAFP(AFP afp) throws SQLException
	{
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try{
		String sql="INSERT INTO sw_m_afps ( tasaAFP,sisAFP, tasaTotalAFP, periodoAFP, idParametro) VALUES (?,?,?,?,?)";
		ps = db.conn.prepareStatement(sql);
		
		
		
		ps.setBigDecimal(1, afp.getTasaAFP());
		ps.setBigDecimal(2,afp.getSisAFP());
		ps.setBigDecimal(3, afp.getTasaTotalAFP());
		ps.setString(4, afp.getPeriodoAFP());
		ps.setInt(5, afp.getIdParametro());
		ps.execute();
		return true;
		}
		catch(Exception ex){
			return false;
		}
		finally{
			db.conn.close();
		}
		
			
	} 
	
	/*------------------READ---------------------*/	
	
	public static ArrayList<AFP> getAllAFPs() throws Exception 
	{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<AFP> lista = new ArrayList<AFP>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT sw_m_afps.idAFP, sw_m_afps.nombreAFP, sw_m_afps.tasaAFP, sw_m_afps.sisAFP, sw_m_afps.tasaAFP, sw_m_afps.tasaTotalAFP, sw_m_afps.periodoAFP, sw_m_afps.idParametro, parametros.codigo, parametros.descripcion, parametros.codPrevired, parametros.llave, parametros.activo FROM SAN_CLEMENTE.sw_m_afps join parametros on sw_m_afps.idParametro=parametros.llave WHERE codigo='AFP' AND parametros.activo=1 order by nombreAFP DESC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				AFP afp= new AFP();				
				afp.setIdAFP(rs.getInt("idAFP"));	
				afp.setNombreAFP(rs.getString("nombreAFP"));
				afp.setTasaAFP(rs.getBigDecimal("tasaAFP"));
				afp.setSisAFP(rs.getBigDecimal("sisAFP"));
				afp.setTasaTotalAFP(rs.getBigDecimal("tasaTotalAFP"));
				afp.setPeriodoAFP(rs.getString("periodoAFP"));
				afp.setIdParametro(rs.getInt("idParametro"));
				afp.setCodPrevired(rs.getString("codPrevired"));
				afp.setCodigo(rs.getString("codigo"));
				afp.setDescripcion(rs.getString("descripcion"));
				afp.setActivo(rs.getInt("activo"));
				lista.add(afp);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	
	/*------------------UPDATE---------------------*/
	
	public static boolean updateAFP(AFP afp) throws SQLException
	{
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual

			
			sql ="UPDATE sw_m_afps SET tasaAFP = ?, sisAFP = ?, tasaTotalAFP = ?, periodoAFP=?, idParametro=?  WHERE idAFP="+afp.getIdafp();
		
			ps = db.conn.prepareStatement(sql);
			
			ps.setBigDecimal(1, afp.getTasaAFP());
			ps.setBigDecimal(2,afp.getSisAFP());
			ps.setBigDecimal(3,afp.getTasaTotalAFP());
			ps.setString(4, afp.getPeriodoAFP());
			ps.setInt(5, afp.getIdParametro());
			
			ps.executeUpdate();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
		
	}
	public static boolean updateAFPMas(ArrayList<AFP> afps, String periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual

			for(AFP afp :afps ){
			sql ="UPDATE sw_m_afps SET tasaAFP = ?, sisAFP = ?, tasaTotalAFP = ?  WHERE idAFP="+afp.getIdafp();
		
			ps = db.conn.prepareStatement(sql);
			
			ps.setBigDecimal(1, afp.getTasaAFP());
			ps.setBigDecimal(2,afp.getSisAFP());
			ps.setBigDecimal(3,afp.getTasaTotalAFP());
			ps.setString(4, afp.getPeriodoAFP());
			ps.setInt(5, afp.getIdParametro());
			
			ps.executeUpdate();
			}
			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	
	/*------------------DELETE---------------------*/
	
	public static boolean deleteAFPById(int idafp) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_m_afps WHERE idAFP ="+idafp;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static AFP getAFPById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		AFP afps=new AFP(); 
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_m_afps WHERE idAFP="+id;
			ps = db.conn.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				AFP afp= new AFP();				
				afp.setIdAFP(rs.getInt("idAFP"));				
				afp.setNombreAFP(rs.getString("nombreAFP"));
				afp.setTasaAFP(rs.getBigDecimal("tasaAFP"));
				afp.setSisAFP(rs.getBigDecimal("sisAFP"));
				afp.setTasaTotalAFP(rs.getBigDecimal("tasaTotalAFP"));
				afp.setPeriodoAFP(rs.getString("periodoAFP"));
				afp.setIdParametro(rs.getInt("idParametro"));
				afps=afp;
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return afps;	
	}
	public static ArrayList<periodoafp> getAllPeriodos() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<periodoafp> lista = new ArrayList<periodoafp>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT periodoAFP FROM SAN_CLEMENTE.sw_m_afps GROUP BY periodoAFP ORDER BY periodoAFP DESC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				periodoafp periodo= new periodoafp();				
				periodo.setPeriodoAFP(rs.getString("periodoAFP"));				
				
				lista.add(periodo);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	/*------------------ESTATS---------------------*/
	public static ArrayList<AFPdet> getStatsFromPeriodos() throws SQLException
	{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<AFPdet> lista = new ArrayList<AFPdet>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT periodoAFP, count(periodoAFP) AS sumPeriodo FROM sw_m_afps GROUP BY periodoAFP order by periodoAFP ASC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				AFPdet stats= new AFPdet();				
				stats.setPeriodoAFP(rs.getString("periodoAFP"));				
				stats.setSumPeriodos(rs.getInt("sumPeriodo"));
				lista.add(stats);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<AFPdet> getStatsFromLastPeriodAFPS() throws SQLException
	{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<AFPdet> lista = new ArrayList<AFPdet>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT nombreAFP, count(nombreAFP) AS qttyAFP, periodoAFP FROM sw_m_afps WHERE periodoAFP=(SELECT periodoAFP From sw_m_afps order by periodoAFP desc limit 1) group by nombreAFP, periodoAFP order by periodoAFP DESC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				AFPdet stats= new AFPdet();				
				stats.setNombreAFP(rs.getString("nombreAFP"));				
				stats.setQttyAFP(rs.getInt("qttyAFP"));
				stats.setPeriodoAFP(rs.getString("periodoAFP"));
				lista.add(stats);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<AFPdet> getStatsFromAFPS() throws SQLException
	{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<AFPdet> lista = new ArrayList<AFPdet>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT nombreAFP, count(nombreAFP) AS qttyAFP, periodoAFP FROM sw_m_afps group by nombreAFP, periodoAFP order by periodoAFP DESC";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				AFPdet stats= new AFPdet();				
				stats.setNombreAFP(rs.getString("nombreAFP"));				
				stats.setQttyAFP(rs.getInt("qttyAFP"));
				stats.setPeriodoAFP(rs.getString("periodoAFP"));
				lista.add(stats);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static boolean compareAFP(AFP afp) throws SQLException
	{
		PreparedStatement ps = null;
		String sql="";		
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT count(*) AS total FROM sw_m_afps WHERE idParametro='"+afp.getIdParametro()+"' AND periodoAFP='"+afp.getPeriodoAFP()+"' AND idAFP!="+afp.getIdafp();
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			int total=0;
			while(rs.next()){				
				total=rs.getInt("total");
				
			}
			if(total>=1){
			return false;
			}
			else{
				return true;
			}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			return false;
		}finally {
			ps.close();
			db.close();
		}		
		
	}
	public static boolean compareAddAFP(AFP afp) throws SQLException
	{
		PreparedStatement ps = null;
		String sql="";		
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT count(*) AS total FROM sw_m_afps WHERE idParametro='"+afp.getIdParametro()+"' AND periodoAFP='"+afp.getPeriodoAFP()+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			int total=0;
			while(rs.next()){				
				total=rs.getInt("total");
			}
			if(total>=1){
			return false;
			}
			else{
				return true;
			}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			return false;
		}finally {
			ps.close();
			db.close();
		}		
		
	}
	public static ArrayList<AFP> getAFPsByLastPeriod() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<AFP> lista = new ArrayList<AFP>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_m_afps "+
					"inner join (select max(periodoAFP) as pAFP from sw_m_afps) as p on sw_m_afps.periodoAFP=p.pAFP "+
					"left join (select parametros.llave, parametros.descripcion as nombreAFP2 from parametros where parametros.codigo='AFP' and parametros.activo=1) as par on sw_m_afps.idParametro=par.llave "; 

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				AFP afp= new AFP();				
				afp.setIdAFP(rs.getInt("idAFP"));	
				afp.setNombreAFP(rs.getString("nombreAFP2"));
				afp.setTasaAFP(rs.getBigDecimal("tasaAFP"));
				afp.setSisAFP(rs.getBigDecimal("sisAFP"));
				afp.setTasaTotalAFP(rs.getBigDecimal("tasaTotalAFP"));
				afp.setPeriodoAFP(rs.getString("periodoAFP"));
				afp.setIdParametro(rs.getInt("idParametro"));
				
				lista.add(afp);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	public static ArrayList<AFP> getAFPsByPeriod(String periodo) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<AFP> lista = new ArrayList<AFP>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select *,if(codPrevired is null, 00, codPrevired) as codPrevired  from sw_m_afps "+
					"inner join (select distinct(periodoAFP) as pAFP from sw_m_afps where periodoAFP='"+periodo+"') as p on sw_m_afps.periodoAFP=p.pAFP "+
					"inner join (select parametros.llave, parametros.descripcion as nombreAFP2 from parametros where parametros.codigo='AFP' and parametros.activo=1) as par on sw_m_afps.idParametro=par.llave";  
 

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				AFP afp= new AFP();				
				afp.setIdAFP(rs.getInt("idAFP"));	
				afp.setNombreAFP(rs.getString("nombreAFP2"));
				afp.setTasaAFP(rs.getBigDecimal("tasaAFP"));
				afp.setSisAFP(rs.getBigDecimal("sisAFP"));
				afp.setTasaTotalAFP(rs.getBigDecimal("tasaTotalAFP"));
				afp.setPeriodoAFP(rs.getString("periodoAFP"));
				afp.setIdParametro(rs.getInt("idParametro"));
				afp.setIdCodPrevired(rs.getInt("codPrevired"));
				
				
				lista.add(afp);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	public static boolean updateAFPsByPeriodo(ArrayList<AFP> afps) throws SQLException {
		PreparedStatement ps = null;
		String sql ="";
		PreparedStatement ps2 = null;
		String sql2 ="";
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual

		for(AFP afp :afps ){
			
			if(afp.getIdParametro() == 7){
				sql ="UPDATE sw_m_afps SET tasaAFP = ?, sisAFP = ?, tasaTotalAFP = ?,codPrevired = ?  WHERE idAFP="+afp.getIdafp();
				System.out.println(afp.getIdCodPrevired());
				ps = db.conn.prepareStatement(sql);
				ps.setBigDecimal(1, afp.getTasaAFP());
				ps.setBigDecimal(2,afp.getSisAFP());
				ps.setBigDecimal(3,afp.getTasaTotalAFP());
				ps.setInt(4,afp.getIdCodPrevired());
				ps.executeUpdate();
			}else{
			sql ="UPDATE sw_m_afps SET tasaAFP = ?, sisAFP = ?, tasaTotalAFP = ?  WHERE idAFP="+afp.getIdafp();
			System.out.println(afp.getIdCodPrevired());
			ps = db.conn.prepareStatement(sql);
			ps.setBigDecimal(1, afp.getTasaAFP());
			ps.setBigDecimal(2,afp.getSisAFP());
			ps.setBigDecimal(3,afp.getTasaTotalAFP());
			
			
			ps.executeUpdate();
			
			}
			
			
		}
		
		
		
		
			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	
	}
	
	
	
	
	public static ArrayList<AFP> getAllAFPantalla()  throws Exception{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<AFP> lista = new ArrayList<AFP>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select codPrevired,descripcion from parametros where codigo = 'AFP' and llave not in (0,7)";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				AFP cr = new AFP();
				
				cr.setCodPrevired(rs.getString("codPrevired"));
				cr.setDescripcion(rs.getString("descripcion"));
			
				lista.add(cr);
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
	
	
	
	
	
	
	
	
	
	
}
