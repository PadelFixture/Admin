package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import lib.classSW.Grupo;
import lib.classSW.trabajadores;
import lib.db.ConnectionDB;

public class GrupoDB {
	
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
	public static Grupo getBlankGroup()  throws Exception{

		
		Grupo grupo = new Grupo();

		return grupo.createBlankGroup();
	}
	
	
/*------------------CRUD------------------------------*/	
	
	
	
/*------------------CREATE---------------------*/	
	
	public static boolean createGroup(Grupo group) throws SQLException
	{
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try{
		String sql="INSERT INTO sw_m_grupos (nombreGrupo, descripcionGrupo) VALUES (?,?)";
		ps = db.conn.prepareStatement(sql);
		
		
		ps.setString(1, group.getNombreGrupo());
		ps.setString(2, group.getDescripcionGrupo());
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
	
	public static ArrayList<Grupo> getAllGroups() throws Exception 
	{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Grupo> lista = new ArrayList<Grupo>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_m_grupos";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Grupo grupo= new Grupo();				
				grupo.setIdGrupo(rs.getInt("idGrupo"));				
				grupo.setNombreGrupo(rs.getString("nombreGrupo"));
				grupo.setDescripcionGrupo(rs.getString("descripcionGrupo"));
				lista.add(grupo);
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
	
	public static boolean updateGroup(Grupo group) throws SQLException
	{
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE sw_m_grupos SET nombreGrupo = ?, descripcionGrupo = ?, WHERE idGrupo = ?";
		
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,group.getNombreGrupo());
			ps.setString(2,group.getDescripcionGrupo());
			ps.setInt(3,group.getIdGrupo());
			
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
		
	}
	
	/*------------------DELETE---------------------*/
	
	public static boolean deleteGroupById(int idgroup) throws SQLException
	{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_m_grupos WHERE idGrupo ="+idgroup;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	/*------------------RELACIONES---------------------*/
	public static boolean insertGroupWorkers(String nombre, String[] trabajadores) throws SQLException
	{
		PreparedStatement ps = null,ps2=null;
		String sql = "", sql2="";
		int idgrup=0;
		ResultSet get;
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="INSERT INTO sw_m_grupos (nombreGrupo) VALUES (?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.execute();
			
			sql2="SELECT * FROM SAN_CLEMENTE.sw_m_grupos order by idGrupo desc limit 1";
			ps2=db.conn.prepareStatement(sql2);
			get=ps2.executeQuery();
			while(get.next()){
				idgrup=get.getInt("idGrupo");
			}
			
			System.out.println(""+idgrup);
			for(int i=0;i<trabajadores.length;i++)
			{
				System.out.println(i);
				insertarGrupoTrabajador(trabajadores[i],idgrup);
				
			}
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	private static void insertarGrupoTrabajador(String string, int idgrup) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			
			sql="INSERT INTO sw_r_grupoTrabajador (idGrupo, idTrabajador) VALUES (?,?)";
			ps=db.conn.prepareStatement(sql);
			ps.setInt(1, idgrup);
			ps.setString(2, string);
			ps.execute();
			System.out.println("Pasé");
		}catch(Exception ex){
			
		}finally{
			db.conn.close();
		}
	}
	public static ArrayList<trabajadores> getTrabajadoresByGroup(int idGroup) throws SQLException{
		PreparedStatement ps = null;
		String sql="";
		ArrayList<trabajadores> lista = new ArrayList<trabajadores>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM trabajadores join sw_r_grupoTrabajador on trabajadores.id= sw_r_grupoTrabajador.idTrabajador WHERE sw_r_grupoTrabajador.idGrupo="+idGroup;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				trabajadores tr=new trabajadores();
				tr.setId(rs.getInt("id"));
				tr.setCodigo(rs.getString("codigo"));
				tr.setRut(rs.getString("rut"));
				tr.setNombre(rs.getString("nombre"));				
				tr.setDireccion(rs.getString("direccion"));
				tr.setTelefono(rs.getString("telefono"));	
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setApellidoMaterno(rs.getString("apellidoMaterno"));
				tr.setFechaIngresoCompania(rs.getString("fechaIngresoCompania"));			
				lista.add(tr);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static ArrayList<trabajadores> getTrabajadoresByIds(String[] ids) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		ArrayList<trabajadores> lista = new ArrayList<trabajadores>();
				
		if(ids.length>0){
			sql="SELECT * FROM trabajadores where ";
			for(int i=0;i<ids.length;i++)
			{
				String aux="";
				if(i==0)
				{
					aux="id="+ids[i];
					sql=sql+aux;
				}
				else
				{
					aux=" or id="+ids[i];
					sql=sql+aux;
				}
			}
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){		
				
				trabajadores trab= new trabajadores();
				
				trab.setId(rs.getInt("id"));				
				trab.setNombre(rs.getString("nombre"));
				trab.setRut(rs.getString("rut"));
				trab.setApellidoPaterno(rs.getString("apellidoPaterno"));
				lista.add(trab);
			}	
		}
		return lista;
	}


	public static ArrayList<trabajadores> getTrabajadores() throws SQLException {

		PreparedStatement ps = null;
		String sql="SELECT * FROM trabajadores where rut!='null' and rutTemporal!='null' and pasaporte!='null'";
		ConnectionDB db = new ConnectionDB();

		ArrayList<trabajadores> lista = new ArrayList<trabajadores>();
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);	
			
		try{
			
			while(rs.next()){
				trabajadores tr=new trabajadores();
				tr.setId(rs.getInt("id"));
				tr.setCodigo(rs.getString("codigo"));
				tr.setRut(rs.getString("rut"));
				tr.setNombre(rs.getString("nombre"));				
				tr.setDireccion(rs.getString("direccion"));
				tr.setTelefono(rs.getString("telefono"));	
				tr.setApellidoPaterno(rs.getString("apellidoPaterno"));
				tr.setApellidoMaterno(rs.getString("apellidoMaterno"));
				tr.setFechaIngresoCompania(rs.getString("fechaIngresoCompania"));
				
				lista.add(tr);
			}		
			
			return lista;
		}
		catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		return lista;		
		
	}
	public static trabajadores getTrabajadorByCode(String code) throws SQLException
	{
		PreparedStatement ps = null;
		String sql="SELECT * FROM trabajadores WHERE codigo="+code+" or rut="+code;
		ConnectionDB db = new ConnectionDB();

		trabajadores trab = new trabajadores();
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		try{
			while(rs.next()){
				trabajadores tr=new trabajadores();
				tr.setId(rs.getInt("id"));
				tr.setCodigo(rs.getString("codigo"));
				tr.setRut(rs.getString("rut"));
				trab=tr;
			}		
		}	
		catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		
		return trab;
	}

	
	public static trabajadores getTrabajadoresByCodeFiniquito(int code) throws SQLException
	{
		PreparedStatement ps = null;
		String sql="select max(id) as id from contratos where codigo_trabajador = "+code+" and EstadoContrato = 1 ";
		
		System.out.println(sql);
		ConnectionDB db = new ConnectionDB();

		trabajadores trab = new trabajadores();
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		try{
			while(rs.next()){
				trabajadores tr=new trabajadores();
				tr.setId(rs.getInt("id"));
				trab=tr;
			}		
		}	
		catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		
		return trab;
	}


	
	
	
	
}
