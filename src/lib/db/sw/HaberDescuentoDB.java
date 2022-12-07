package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import lib.classSW.HaberDescuento;
import lib.classSW.HaberYDescuento;
import lib.db.ConnectionDB;

public class HaberDescuentoDB {

	public static boolean createHaberDescuento(HaberDescuento hd) throws SQLException {
		
//		System.out.println("codigo: "+hd.getCodigo());
//		System.out.println("descripcion: "+hd.getDescripcion());
//		System.out.println("tipo: "+hd.getTipo());
//		System.out.println("imponible: "+hd.getImponible());
//		System.out.println("tributable: "+hd.getTributable());
//		System.out.println("rutEmpresa: "+hd.getRutEmpresa());
//		System.out.println("codigoSap: "+hd.getCodSap());
//		System.out.println("centroCosto: "+hd.getCentroCosto());
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {
			sql = "INSERT INTO sw_p_haberesDescuentos (codigo, descripcion, tipo, imponible, tributable, rutEmpresa, codSap, centroCosto, cuentaContable) VALUES (?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, hd.getCodigo());
			ps.setString(2, hd.getDescripcion());
			ps.setString(3, hd.getTipo());
			ps.setString(4, hd.getImponible());
			ps.setString(5, hd.getTributable());
			ps.setString(6, hd.getRutEmpresa());
			ps.setString(7, hd.getCodSap().equals("0") ? "" : hd.getCodSap() );
//			String centroCosto=hd.getCentroCosto();
//			if(centroCosto.length()>8){
//				centroCosto=centroCosto.substring(0, 9);
//			}
			ps.setString(8, "");
			ps.setString(9, hd.getCuentaContable());
			ps.execute();
			return true;

		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}finally {
			ps.close();
			db.close();
		}		
		
	}
	public static boolean existCodigo(int codigo) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		int qtty=0;
		 
		try {
			sql="SELECT count(*) as qtty from sw_p_haberesDescuentos WHERE codigo="+codigo;
				
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
			qtty=rs.getInt("qtty");	
			}
			if(qtty>=1)
			{
				return true;
			}
			else{
				return false;
			}
			
			
			
			
		}catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}finally {
			ps.close();
			db.close();
		}
		
		
	}
	
	
	public static ArrayList<HaberDescuento> getCodigos() throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
		try {
			sql="SELECT distinct(codigo), descripcion FROM sw_p_haberesDescuentos";
				
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				HaberDescuento hd= new HaberDescuento();
				hd.setCodigo(rs.getInt("codigo"));
				hd.setDescripcion(rs.getString("descripcion"));
				
				
				
				lista.add(hd);
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
	}
	

	public static ArrayList<HaberDescuento> getHaberesDescuentos() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
		try {
			sql="SELECT * FROM sw_p_haberesDescuentos ORDER BY descripcion ASC";
				
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				HaberDescuento hd= new HaberDescuento();
				hd.setCodigo(rs.getInt("codigo"));
				hd.setDescripcion(rs.getString("descripcion"));
				hd.setTipo(rs.getString("tipo"));
				hd.setImponible(rs.getString("imponible"));
				hd.setTributable(rs.getString("tributable"));
				hd.setRutEmpresa(rs.getString("rutEmpresa"));
				hd.setCodSap(rs.getString("codSap"));
				hd.setCentroCosto(rs.getString("centroCosto"));
				hd.setCuentaContable(rs.getString("cuentaContable"));
				
				lista.add(hd);
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
	}
	public static ArrayList<HaberYDescuento> getHaberesYDescuentosByCTCCPClass(int codTrabajador, int codContrato, int periodo, int classe) throws SQLException{
		ArrayList<HaberYDescuento> Lista= new ArrayList<HaberYDescuento>();
		PreparedStatement ps = null;
		String sql = "SELECT distinct(sw_p_haberesDescuentos.codigo),sw_p_haberesDescuentos.descripcion, "+
				"round(sw_liquidacionDetalle.valor) as valor, "+
				"sw_liquidacionDetalle.codTrabajador,sw_liquidacionDetalle.idContrato, "+
				"sw_liquidacionDetalle.idConcepto,sw_liquidacionDetalle.periodo FROM sw_p_haberesDescuentos "+ 
				"inner join sw_liquidacionDetalle "+
				"on sw_p_haberesDescuentos.descripcion=sw_liquidacionDetalle.descripcion ";
		String sql2="";
		ConnectionDB  db = new ConnectionDB();	
		 
		if(classe==1){
			sql2="WHERE idConcepto='11' and  codTrabajador='"+codTrabajador+"' and idContrato='"+codContrato+"' and codigo between 1000 and 1999 "+
					"and periodo='"+periodo+"'";
			
		}
		else if(classe==2){
			//trae los Haberes No Imponibles
			sql2="WHERE idConcepto='11' and  codTrabajador='"+codTrabajador+"' and idContrato='"+codContrato+"' and codigo between 2000 and 2999 "+
					"and periodo='"+periodo+"'";
			
		}
		else if(classe==2){
			//trae los Descuentos
			sql2="WHERE idConcepto='11' and  codTrabajador='"+codTrabajador+"' and idContrato='"+codContrato+"' and codigo between 3000 and 3999 "+
					"and periodo='"+periodo+"'";
		}
		else{
			//los trae todos
			sql2="WHERE "+
					"periodo='"+periodo+"'";
		}
		
		try {
			sql=sql+sql2;
			
			System.out.println("///////////aqui");
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				HaberYDescuento hd= new HaberYDescuento();
				hd.setCodigo(rs.getInt("codigo"));
				hd.setDescripcion(rs.getString("descripcion"));
				hd.setValor(rs.getInt("valor"));
				hd.setCodTrabajador(rs.getInt("codTrabajador"));
				hd.setIdContrato(rs.getInt("idContrato"));
				hd.setIdConcepto(rs.getInt("idConcepto"));
				hd.setPeriodo(rs.getInt("periodo"));
				
				Lista.add(hd);
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
		return Lista;		
		
		
		
		
		
	}
	
	
	

	public static HaberDescuento getHaberDescuentoById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 HaberDescuento lista=new HaberDescuento();
		try {
			sql="SELECT * FROM sw_p_haberesDescuentos "+
				"WHERE sw_p_haberesDescuentos.codigo='"+id+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				HaberDescuento hd= new HaberDescuento();
				hd.setCodigo(rs.getInt("codigo"));
				hd.setDescripcion(rs.getString("descripcion"));
				hd.setTipo(rs.getString("tipo"));
				hd.setImponible(rs.getString("imponible"));
				hd.setTributable(rs.getString("tributable"));
				hd.setRutEmpresa(rs.getString("rutEmpresa"));
				hd.setCodSap(rs.getString("codSap"));
				hd.setCentroCosto(rs.getString("centroCosto"));
				hd.setCuentaContable(rs.getString("cuentaContable"));
				
				lista=hd;
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
	}
	public static ArrayList<HaberDescuento> getHaberImponibleUtilizado(int periodo) throws SQLException{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
		try {
//			sql="SELECT * FROM sw_p_haberesDescuentos "+
//				"inner join "+
//				"( select distinct(codigo_hd) "+
//				"from sw_haberesDescuentos "+
//				"where codigo_hd between 1000 and 1999 and periodo='"+periodo+"' order by codigo_hd ASC) as hd "+
//				"on sw_p_haberesDescuentos.codigo=hd.codigo_hd ";
			
			sql ="SELECT codigo,REPLACE(descripcion,'%',' ') as descripcion,tipo,imponible,tributable,rutEmpresa,codSap,centroCosto, codigo as codigo_hd FROM sw_p_haberesDescuentos where codigo between 1000 and 1999 order by codigo_hd ASC";
		
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				HaberDescuento hd= new HaberDescuento();
				hd.setCodigo(rs.getInt("codigo_hd"));
				hd.setDescripcion(rs.getString("descripcion"));
				hd.setTipo(rs.getString("tipo"));
				hd.setImponible(rs.getString("imponible"));
				hd.setTributable(rs.getString("tributable"));
				
				lista.add(hd);
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
	}
	public static ArrayList<HaberDescuento> getHaberNoImponibleUtilizado(int periodo) throws SQLException{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
		try {
//			sql="SELECT * FROM sw_p_haberesDescuentos "+
//					"inner join "+
//					"( select distinct(codigo_hd) "+
//					"from sw_haberesDescuentos "+
//					"where codigo_hd between 2000 and 2999 and periodo='"+periodo+"' order by codigo_hd ASC) as hd "+
//					"on sw_p_haberesDescuentos.codigo=hd.codigo_hd ";
			
			sql ="SELECT codigo,REPLACE(descripcion,'%',' ') as descripcion,tipo,imponible,tributable,rutEmpresa,codSap,centroCosto, codigo as codigo_hd FROM sw_p_haberesDescuentos where codigo between 2000 and 2999 order by codigo_hd ASC";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs= ps.executeQuery();	
				while (rs.next()) {
					HaberDescuento hd= new HaberDescuento();
					hd.setCodigo(rs.getInt("codigo_hd"));
					hd.setDescripcion(rs.getString("descripcion"));
					hd.setTipo(rs.getString("tipo"));
					hd.setImponible(rs.getString("imponible"));
					hd.setTributable(rs.getString("tributable"));
					
					lista.add(hd);
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
	}
	public static ArrayList<HaberDescuento> getDescuentosUtilizados(int periodo) throws SQLException{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
		try {
//			sql="SELECT * FROM sw_p_haberesDescuentos "+
//					"inner join "+
//					"( select distinct(codigo_hd) "+
//					"from sw_haberesDescuentos "+
//					"where codigo_hd between 3000 and 3999 and periodo='"+periodo+"' order by codigo_hd ASC) as hd "+
//					"on sw_p_haberesDescuentos.codigo=hd.codigo_hd ";
			
//			sql="select distinct descripcion from sw_liquidacionDetalle where idConcepto = 45 and periodo = '"+periodo+"'";
			sql ="SELECT codigo,REPLACE(descripcion,'%',' ') as descripcion,tipo,imponible,tributable,rutEmpresa,codSap,centroCosto, codigo as codigo_hd FROM sw_p_haberesDescuentos where codigo between 3000 and 3999 order by codigo_hd ASC";	
		   System.out.println("/////////////// descuentos utilizados");
		   System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs= ps.executeQuery();	
				while (rs.next()) {
					HaberDescuento hd= new HaberDescuento();
					hd.setCodigo(rs.getInt("codigo_hd"));
					hd.setDescripcion(rs.getString("descripcion"));
//					hd.setTipo(rs.getString("tipo"));
//					hd.setImponible(rs.getString("imponible"));
//					hd.setTributable(rs.getString("tributable"));
					
					lista.add(hd);
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
	}
	
	public static ArrayList<HaberDescuento> getCostoEmpresaUtilizados(int periodo) throws SQLException{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
		try {
			sql="SELECT * FROM sw_p_haberesDescuentos "+
					"inner join "+
					"( select distinct(codigo_hd) "+
					"from sw_haberesDescuentos "+
					"where codigo_hd between 4000 and 4999 and periodo='"+periodo+"' order by codigo_hd ASC) as hd "+
					"on sw_p_haberesDescuentos.codigo=hd.codigo_hd ";
				
			System.out.println("///////////////////////// query getDescuentosUtilizados //////////////");
			System.out.println(sql);
				ps = db.conn.prepareStatement(sql);
				ResultSet rs= ps.executeQuery();	
				while (rs.next()) {
					HaberDescuento hd= new HaberDescuento();
					hd.setCodigo(rs.getInt("codigo_hd"));
					hd.setDescripcion(rs.getString("descripcion"));
					hd.setTipo(rs.getString("tipo"));
					hd.setImponible(rs.getString("imponible"));
					hd.setTributable(rs.getString("tributable"));
					
					lista.add(hd);
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
	}
	
	
	public static ArrayList<HaberDescuento> getHaberDescuentoBySociedad(String soc) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
		try {
			sql="SELECT * FROM sw_p_haberesDescuentos "+
				"WHERE sw_p_haberesDescuentos.rutEmpresa='"+soc+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				HaberDescuento hd= new HaberDescuento();
				hd.setCodigo(rs.getInt("codigo"));
				hd.setDescripcion(rs.getString("descripcion"));
				hd.setTipo(rs.getString("tipo"));
				hd.setImponible(rs.getString("imponible"));
				hd.setTributable(rs.getString("tributable"));
				hd.setRutEmpresa(rs.getString("rutEmpresa"));
				hd.setCodSap(rs.getString("codSap"));
				hd.setCentroCosto(rs.getString("centroCosto"));
				hd.setCuentaContable(rs.getString("cuentaContable"));
				
				
				lista.add(hd);
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
	}

	public static boolean updateHaberDescuento(HaberDescuento hd) {
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try {
			String sql="UPDATE sw_p_haberesDescuentos set descripcion=?, rutEmpresa=?, codSap=?, centroCosto=?, cuentaContable=? WHERE codigo="+hd.getCodigo();
			ps = db.conn.prepareStatement(sql);
			
			
			ps.setString(1, hd.getDescripcion());
			ps.setString(2, hd.getRutEmpresa());
			ps.setString(3, hd.getCodSap().equals("0") ? "" : hd.getCodSap() );
//			String centroCosto=hd.getCentroCosto();
//			if(centroCosto.length()>8){
//				centroCosto=centroCosto.substring(0,9);
//			}
			
			ps.setString(4, "");
			ps.setString(5, hd.getCuentaContable());
			
			ps.executeUpdate();
			return true;
			
		}catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			return false;
		}finally {
		}		
	}

	public static boolean deleteHaberDescuentoById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql= "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "DELETE FROM sociedad WHERE idSociedad = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			return true;
		}catch(Exception e){
			System.out.println("Error insertDocumentos:" + e.getMessage());
			e.printStackTrace();
		}finally{
			db.conn.close();
			ps.close();
		}
		return false;
	}

	public static boolean deleteHaberDescuentoBySociedad(String soc) throws SQLException {
		PreparedStatement ps = null;
		String sql= "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "DELETE FROM sociedad WHERE sociedad = ?";
			
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, soc);
			ps.execute();
			return true;
		}catch(Exception e){

			System.out.println("Error sociedad" + e.getMessage());
			e.printStackTrace();

		}finally{
			db.conn.close();
			ps.close();
		}
		return false;
	}
	public static int[] getMaxCodes() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 int[] lista=new int[4];
		try {
			sql="SELECT max(codigo) as valor, tipo, imponible FROM sw_p_haberesDescuentos group by tipo, imponible order by valor, tipo, imponible asc ";
				
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			int aux=0;
			while (rs.next()) {
				lista[aux]=rs.getInt("valor");
				
				aux=aux+1;
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
	}
	
	// haberes no imponibles finiquito
	public static ArrayList<HaberDescuento> getHaberesyDescuentoNoImponibleFiniquito(int periodo) throws SQLException{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
		try {
//			sql="SELECT * FROM sw_p_haberesDescuentos "+
//					"inner join "+
//					"( select distinct(codigo_hd) "+
//					"from sw_haberesDescuentosFiniquito "+
//					"where codigo_hd between 2000 and 2999 and periodo='"+periodo+"' order by codigo_hd ASC) as hd "+
//					"on sw_p_haberesDescuentos.codigo=hd.codigo_hd ";
				
			sql ="SELECT codigo,REPLACE(descripcion,'%',' ') as descripcion,tipo,imponible,tributable,rutEmpresa,codSap,centroCosto, codigo as codigo_hd FROM sw_p_haberesDescuentos where codigo between 2000 and 2999 order by codigo_hd ASC";
				ps = db.conn.prepareStatement(sql);
				ResultSet rs= ps.executeQuery();	
				while (rs.next()) {
					HaberDescuento hd= new HaberDescuento();
					hd.setCodigo(rs.getInt("codigo_hd"));
					hd.setDescripcion(rs.getString("descripcion"));
					hd.setTipo(rs.getString("tipo"));
					hd.setImponible(rs.getString("imponible"));
					hd.setTributable(rs.getString("tributable"));
					
					lista.add(hd);
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
	}
	
	// haberes  finiquito
		public static ArrayList<HaberDescuento> getHaberesydescuentoFiniquito(int periodo) throws SQLException{
			PreparedStatement ps = null;
			String sql = "";
			ConnectionDB  db = new ConnectionDB();	
			 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
			try {
//				sql="SELECT * FROM sw_p_haberesDescuentos "+
//						"inner join "+
//						"( select distinct(codigo_hd) "+
//						"from sw_haberesDescuentosFiniquito "+
//						"where codigo_hd between 1000 and 1999 and periodo='"+periodo+"' order by codigo_hd ASC) as hd "+
//						"on sw_p_haberesDescuentos.codigo=hd.codigo_hd ";
					
				sql ="SELECT codigo,REPLACE(descripcion,'%',' ') as descripcion,tipo,imponible,tributable,rutEmpresa,codSap,centroCosto, codigo as codigo_hd FROM sw_p_haberesDescuentos where codigo between 1000 and 1999 order by codigo_hd ASC";
					ps = db.conn.prepareStatement(sql);
					ResultSet rs= ps.executeQuery();	
					while (rs.next()) {
						HaberDescuento hd= new HaberDescuento();
						hd.setCodigo(rs.getInt("codigo_hd"));
						hd.setDescripcion(rs.getString("descripcion"));
						hd.setTipo(rs.getString("tipo"));
						hd.setImponible(rs.getString("imponible"));
						hd.setTributable(rs.getString("tributable"));
						
						lista.add(hd);
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
		}
		
		// descuentos finiquito
				public static ArrayList<HaberDescuento> getDescuentosUtilizadosFiniquito(int periodo) throws SQLException{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB  db = new ConnectionDB();	
					 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
					try {
//						sql="SELECT * FROM sw_p_haberesDescuentos "+
//								"inner join "+
//								"( select distinct(codigo_hd) "+
//								"from sw_haberesDescuentosFiniquito "+
//								"where codigo_hd between 3000 and 3999 and periodo='"+periodo+"' order by codigo_hd ASC) as hd "+
//								"on sw_p_haberesDescuentos.codigo=hd.codigo_hd ";
							
						sql =" SELECT codigo,REPLACE(descripcion,'%',' ') as descripcion,tipo,imponible,tributable,rutEmpresa,codSap,centroCosto, codigo as codigo_hd FROM sw_p_haberesDescuentos where codigo between 3000 and 3999 order by codigo_hd ASC";
							ps = db.conn.prepareStatement(sql);
							ResultSet rs= ps.executeQuery();	
							while (rs.next()) {
								HaberDescuento hd= new HaberDescuento();
								hd.setCodigo(rs.getInt("codigo_hd"));
								hd.setDescripcion(rs.getString("descripcion"));
								hd.setTipo(rs.getString("tipo"));
								hd.setImponible(rs.getString("imponible"));
								hd.setTributable(rs.getString("tributable"));
								
								lista.add(hd);
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
				}
				
				// costo empresa finiquito
				public static ArrayList<HaberDescuento> getCostoEmpresaUtilizadosFiniquito(int periodo) throws SQLException{
					PreparedStatement ps = null;
					String sql = "";
					ConnectionDB  db = new ConnectionDB();	
					 ArrayList<HaberDescuento> lista=new ArrayList<HaberDescuento>();
					try {
						sql="SELECT * FROM sw_p_haberesDescuentos "+
								"inner join "+
								"( select distinct(codigo_hd) "+
								"from sw_haberesDescuentosFiniquito "+
								"where codigo_hd between 4000 and 4999 and periodo='"+periodo+"' order by codigo_hd ASC) as hd "+
								"on sw_p_haberesDescuentos.codigo=hd.codigo_hd ";
							
						
							ps = db.conn.prepareStatement(sql);
							ResultSet rs= ps.executeQuery();	
							while (rs.next()) {
								HaberDescuento hd= new HaberDescuento();
								hd.setCodigo(rs.getInt("codigo_hd"));
								hd.setDescripcion(rs.getString("descripcion"));
								hd.setTipo(rs.getString("tipo"));
								hd.setImponible(rs.getString("imponible"));
								hd.setTributable(rs.getString("tributable"));
								
								lista.add(hd);
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
				}
				
		
}
