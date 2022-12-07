package lib.db.SASW;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import lib.ClassSASW.MantenedorGenerico;
import lib.ClassSASW.parametro_especie;
import lib.ClassSASW.parametros;

import lib.classSA.Sueldos_Cargo;
import lib.classSW.Parametros_campo;
import lib.db.ConnectionDB;

public class parametrosDB {
	// get
	public static ArrayList<parametros> getParametros(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros> lista = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();
		try {

			sql = "SELECT id, llave, descripcion, codPrevired, rutParametro, codSap from parametros WHERE codigo='" + codigo + "' and activo=1";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				parametros pm = new parametros();
				pm.setLlave(rs.getString("llave"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setCodPrevired(rs.getString("codPrevired"));
				pm.setRutParametro(rs.getString("rutParametro"));
				pm.setId(rs.getInt("id"));
				pm.setCodSap(rs.getString("codSap"));
				lista.add(pm);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	public static ArrayList<parametros> getParamsByCode(String codigo) throws Exception{
		PreparedStatement ps = null;
		String sql ="";
		ArrayList<parametros> lista = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * from parametros WHERE codigo='"+codigo+"' and activo=1 order by descripcion ASC";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				parametros pm = new parametros();
				pm.setLlave(rs.getString("llave"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setCodPrevired(rs.getString("codPrevired"));
				pm.setRutParametro(rs.getString("rutParametro"));
				pm.setCodSap(rs.getString("codSap"));
				pm.setId(rs.getInt("id"));
				lista.add(pm);
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
	public static parametros getParamById(int id) throws Exception{
		PreparedStatement ps = null;
		String sql ="";
		parametros par = new parametros();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * from parametros WHERE id='"+id+"' and activo=1";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				parametros pm = new parametros();
				pm.setId(rs.getInt("id"));
				pm.setLlave(rs.getString("llave"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setCodPrevired(rs.getString("codPrevired"));
				pm.setRutParametro(rs.getString("rutParametro"));
				pm.setCodSap(rs.getString("codSap"));
				pm.setId(rs.getInt("id"));
				par=pm;
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
		return par;
	}
	public static boolean compareParametros(parametros p) throws SQLException
	{
		PreparedStatement ps = null;
		String sql="";		
		ConnectionDB db = new ConnectionDB();
			
		try{
			
			sql="SELECT count(*) AS total FROM parametros WHERE codigo='"+p.getCodigo()+"' AND ((rutParametro='"+p.getRutParametro()+"' AND rutParametro!='') OR (codPrevired='"+p.getCodPrevired()+"' AND codPrevired!='')) AND descripcion!='"+p.getDescripcion()+"' AND llave!='"+p.getLlave()+"' AND activo=1";
			
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
	public static boolean compareParametrosToAdd(parametros p) throws SQLException
	{
		PreparedStatement ps = null;
		String sql="";		
		ConnectionDB db = new ConnectionDB();
			
		try{
			
			sql="SELECT count(*) AS total FROM parametros WHERE codigo='"+p.getCodigo()+"' AND ((rutParametro='"+p.getRutParametro()+"' AND rutParametro!='') OR (codPrevired='"+p.getCodPrevired()+"' AND codPrevired!='')) AND descripcion='"+p.getDescripcion()+"' and activo=1";
			
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
	public static parametros getParametroByCodigoLLave(String codigo, String llave) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		parametros pm = new parametros();
		try {

			sql = "SELECT * from parametros WHERE codigo= '" + codigo + "' and llave = '"+ llave +"' and activo=1 ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				pm.setId(rs.getInt("id"));
				pm.setLlave(rs.getString("llave"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setCodigo(rs.getString("codigo"));
				pm.setCodPrevired(rs.getString("codPrevired"));
				pm.setRutParametro(rs.getString("rutParametro"));
				pm.setCodSap(rs.getString("codSap"));
			}
			return pm;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return pm;
	}
	public static ArrayList<parametros> getParametrosByCodigoAndDescripcion(String codigo, String descripcion)
			throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros> lista = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();

		// int i = 1;

		try {

			sql = "SELECT llave, descripcion,id from parametros WHERE codigo= '" + codigo + "' and descripcion= '"
					+ descripcion + "' and activo=1 ";

			ps = db.conn.prepareStatement(sql);

			// ps.setObject(i++, codigo);
			// ps.setObject(i++, descripcion);

			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				parametros pm = new parametros();
				pm.setLlave(rs.getString("llave"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setId(rs.getInt("id"));
				lista.add(pm);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	public static parametros getParametrosByCodigoAndLlave(String codigo, String llave) throws Exception {
		PreparedStatement ps = null;
		String sql = "";

		ConnectionDB db = new ConnectionDB();
		parametros pm = new parametros();


		try {

			sql = "SELECT llave, descripcion,id from parametros WHERE codigo= '" + codigo + "' and llave = '"
					+ llave + "' and activo=1 ";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				
				pm.setLlave(rs.getString("llave"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setId(rs.getInt("id"));
				
			}
			
			
			return pm;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return pm;
	}

	// get Parametros por Array de Codigos
	public static ArrayList<parametros> getParametrosByCodigos(String[] arrayCodigos) throws Exception {

		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros> lista = new ArrayList<parametros>();
		ConnectionDB db = new ConnectionDB();

		try {

			sql = "SELECT llave, descripcion,id,codigo from parametros WHERE codigo in ( ";

			for (int i = 0; i < arrayCodigos.length; i++) {

				sql += "'" + arrayCodigos[i] + "'";
				sql += i + 1 != arrayCodigos.length ? "," : "";

			}

			sql += " ) and activo=1 ";

			ps = db.conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				parametros pm = new parametros();
				pm.setId(rs.getInt("id"));
				pm.setLlave(rs.getString("llave"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setCodigo(rs.getString("codigo"));

				lista.add(pm);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}

	// update estado
	public static boolean updateEstadoParam(parametros param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE parametros SET activo=0 where id='" + param.getId() + "'";
			
			ps = db.conn.prepareStatement(sql);
			
			
			
			
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}

	// update descripcion
	public static boolean updateDescripcionParam(parametros param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " UPDATE parametros SET descripcion='" + param.getDescripcion() + "' where id='" + param.getId()
					+ "'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {

			ps.close();
		
			db.close();
		}
		return false;// asd
	}
	public static boolean updateParam(parametros param) throws  Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {
			sql = " UPDATE parametros SET descripcion=?, codPrevired=?, rutParametro=?, codSap=? where codigo='"+param.getCodigo() +"' AND llave='"+param.getLlave()+"' AND activo=1" ;
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);			
			ps.setString(1, param.getDescripcion());
			ps.setString(2, param.getCodPrevired());
			ps.setString(3, param.getRutParametro());
			ps.setString(4, param.getCodSap());
			ps.executeUpdate();
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
	
	public static boolean createParam(parametros param, String Codigo) throws SQLException
	{
		PreparedStatement ps = null, ps2=null;
		String sql = "INSERT INTO parametros (codigo, llave, descripcion, activo, codPrevired, rutParametro, codSap) VALUES (?,?,?,?,?,?,?)";
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
			ps.setString(7, param.getCodSap());
			
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
	public static boolean deleteParamById(int id) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM parametros WHERE id ="+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static boolean deleteParById(String codigo, String llave) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE parametros set activo=0 WHERE codigo='"+codigo+"' AND llave='"+llave+"'" ;
			ps = db.conn.prepareStatement(sql);					
			ps.executeUpdate();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	
	//parametro por especie
	public static ArrayList<parametro_especie> getParametro_especie(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametro_especie> lista = new ArrayList<parametro_especie>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT id, especie ,tabla , descripcion from parametro_especie WHERE tabla='" + codigo + "'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				parametro_especie pm = new parametro_especie();
				pm.setEspecie(rs.getString("especie"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setTabla(rs.getString("tabla"));
				pm.setId(rs.getInt("id"));
				lista.add(pm);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	public static ArrayList<MantenedorGenerico> getMantenedorGenerico(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<MantenedorGenerico> lista = new ArrayList<MantenedorGenerico>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT distinct codigo FROM SAN_CLEMENTE.parametros where codigo = 'TIPO DE PLANTA' or codigo = 'LIMITANTES DE SUELO' or codigo = 'TIPO PLANTACION' or codigo = 'TIPO CONTROL HELADAS' or codigo = 'TIPO PROTECCION' or codigo = 'Motivo de Ingreso' and activo=1";

			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				MantenedorGenerico pm = new MantenedorGenerico();
				pm.setCodigo(rs.getString("codigo"));
				lista.add(pm);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	public static ArrayList<Sueldos_Cargo> getSueldos_Cargo(String codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Sueldos_Cargo> lista = new ArrayList<Sueldos_Cargo>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM sueldo_cargo WHERE campo= '" +codigo+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Sueldos_Cargo pm = new Sueldos_Cargo();
				pm.setId(rs.getInt("id"));
				pm.setCampo(rs.getString("campo"));
				pm.setCargo(rs.getString("cargo"));
				pm.setSueldo(rs.getDouble("sueldo"));
				lista.add(pm);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	public static boolean updateSueldosCargo(Sueldos_Cargo s) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new  ConnectionDB();
		try {			
			sql = " UPDATE sueldo_cargo SET sueldo='"+s.getSueldo()+"' where id='"+s.getId()+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean insertSUELDOCARGO (Sueldos_Cargo fa) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO sueldo_cargo (campo, cargo, sueldo) " + "VALUES(?, ?, ?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, fa.getCampo());
			ps.setString(2, fa.getCargo());
			ps.setDouble(3, fa.getSueldo());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			 ps.close();
			 db.close();
		}
		return false;
	}
	public static ArrayList<Parametros_campo> getParametros_campoByCampo(String campo, String tabla) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Parametros_campo> lista = new ArrayList<Parametros_campo>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM parametros_campo WHERE campo= '"+campo+"' and activo = 1 and tabla = '"+tabla+"'";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Parametros_campo pm = new Parametros_campo();
				pm.setCodigo(rs.getInt("codigo"));
				pm.setId(rs.getInt("id"));
				pm.setCampo(rs.getString("campo"));
				pm.setDescripcion(rs.getString("descripcion"));
				pm.setActivo(rs.getString("activo"));
				pm.setTabla(rs.getString("tabla"));
				lista.add(pm);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
	public static boolean updateParametros_campoByCampo(Parametros_campo s) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new  ConnectionDB();
		try {			
			sql = " UPDATE parametros_campo SET descripcion='"+s.getDescripcion()+"' where codigo="+s.getCodigo();
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean INSERT_PARAMETROS_CAMPO (Parametros_campo fa) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
		int id = 0;
		try {
			sql = "INSERT INTO parametros_campo (id, campo, descripcion, activo, tabla) " + "VALUES(?, ?, ?, '1', ?)";
			sql2 = "SELECT MAX(id) FROM parametros_campo where campo = '" + fa.getCampo() + "'";
			ps2 = db.conn.prepareStatement(sql2);
			ResultSet rs = ps2.executeQuery(sql2);
			while (rs.next()){
				id = rs.getInt("MAX(id)");
				id+=1;
			}
			rs.close();
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, fa.getCampo());
			ps.setString(3, fa.getDescripcion());
			ps.setString(4, fa.getTabla());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			 ps.close();
			 db.close();
		}
		return false;
	}
	public static boolean updateParametros_campo(Parametros_campo param) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE parametros_campo SET activo=0 where codigo='" + param.getCodigo() + "'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<Parametros_campo> getTablaParametros_campoByCampo(String campo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Parametros_campo> lista = new ArrayList<Parametros_campo>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT distinct tabla FROM parametros_campo WHERE campo = '"+campo+"'";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				Parametros_campo pm = new Parametros_campo();
				pm.setTabla(rs.getString("tabla"));
				lista.add(pm);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return lista;
	}
}