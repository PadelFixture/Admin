package lib.db.sw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lib.classSA.Estados;
import lib.classSW.Tbjdr;
import lib.classSW.Turno;
import lib.db.ConnectionDB;

public class TurnoDB {

	public static boolean createTurno(Turno t) throws SQLException {
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try{
		String sql="INSERT INTO a_turnos ( nombreTurno,descripcionTurno, jornadaTurno, horasTurno, lunesTurno, martesTurno, miercolesTurno, juevesTurno, viernesTurno, sabadoTurno, domingoTurno, lunesAI, lunesAF, martesAI, martesAF, miercolesAI, miercolesAF, juevesAI, juevesAF, viernesAI, viernesAF, sabadoAI, sabadoAF, domingoAI, domingoAF, lunesPI, lunesPF, martesPI, martesPF, miercolesPI, miercolesPF, juevesPI, juevesPF, viernesPI, viernesPF, sabadoPI, sabadoPF, domingoPI, domingoPF, activo ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1)";
		ps = db.conn.prepareStatement(sql);
		ps.setString(1, t.getNombreTurno());
		ps.setString(2,t.getDescripcionTurno());
		ps.setInt(3, t.getJornadaTurno());
		ps.setBigDecimal(4, t.getHorasTurno());
		ps.setBigDecimal(5, t.getLunesTurno());
		ps.setBigDecimal(6, t.getMartesTurno());
		ps.setBigDecimal(7, t.getMiercolesTurno());
		ps.setBigDecimal(8, t.getJuevesTurno());
		ps.setBigDecimal(9, t.getViernesTurno());
		ps.setBigDecimal(10, t.getSabadoTurno());
		ps.setBigDecimal(11, t.getDomingoTurno());
		ps.setTime(12, t.getLunesAI());
		ps.setTime(13, t.getLunesAF());
		ps.setTime(14, t.getMartesAI());
		ps.setTime(15, t.getMartesAF());
		ps.setTime(16, t.getMiercolesAI());
		ps.setTime(17, t.getMiercolesAF());
		ps.setTime(18, t.getJuevesAI());
		ps.setTime(19, t.getJuevesAF());
		ps.setTime(20, t.getViernesAI());
		ps.setTime(21, t.getViernesAF());
		ps.setTime(22, t.getSabadoAI());
		ps.setTime(23, t.getSabadoAF());
		ps.setTime(24, t.getDomingoAI());
		ps.setTime(25, t.getDomingoAF());
		ps.setTime(26, t.getLunesPI());
		ps.setTime(27, t.getLunesPF());
		ps.setTime(28, t.getMartesPI());
		ps.setTime(29, t.getMartesPF());
		ps.setTime(30, t.getMiercolesPI());
		ps.setTime(31, t.getMiercolesPF());
		ps.setTime(32, t.getJuevesPI());
		ps.setTime(33, t.getJuevesPF());
		ps.setTime(34, t.getViernesPI());
		ps.setTime(35, t.getViernesPF());
		ps.setTime(36, t.getSabadoPI());
		ps.setTime(37, t.getSabadoPF());
		ps.setTime(38, t.getDomingoPI());
		ps.setTime(39, t.getDomingoPF());
		
		
		ps.execute();
		return true;
		}
		catch(Exception ex){
			ps.close();
			db.close();
			return false;
		}
		finally{
			db.conn.close();
		}
	}
	public static boolean createTurnoSimple(Turno t) throws SQLException {
		PreparedStatement ps= null;
		ConnectionDB db= new ConnectionDB();
		
		try{
		String sql="INSERT INTO a_turnos ( nombreTurno) VALUES (?)";
		ps = db.conn.prepareStatement(sql);
		ps.setString(1, t.getNombreTurno());
		ps.execute();
		return true;
		}
		catch(Exception ex){
			ps.close();
			db.close();
			return false;
		}
		finally{
			db.conn.close();
		}
	}
	public static boolean updateTurno(Turno t) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE a_turnos SET nombreTurno = ?, descripcionTurno = ?, jornadaTurno = ?, horasTurno=?, lunesTurno=?, martesTurno=?, miercolesTurno=?, juevesTurno=?, viernesTurno=?, sabadoTurno=?, domingoTurno=?, lunesAI=?, lunesAF=?, martesAI=?, martesAF=?, miercolesAI=?, miercolesAF=?, juevesAI=?, juevesAF=?, viernesAI=?, viernesAF=?, sabadoAI=?, sabadoAF=?, domingoAI=?, domingoAF=?, lunesPI=?, lunesPF=?, martesPI=?, martesPF=?, miercolesPI=?, miercolesPF=?, juevesPI=?, juevesPF=?, viernesPI=?, viernesPF=?, sabadoPI=?, sabadoPF=?, domingoPI=?, domingoPF=?  WHERE idTurno="+t.getIdTurno();
		
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, t.getNombreTurno());
			ps.setString(2,t.getDescripcionTurno());
			ps.setInt(3, t.getJornadaTurno());
			ps.setBigDecimal(4, t.getHorasTurno());
			ps.setBigDecimal(5, t.getLunesTurno());
			ps.setBigDecimal(6, t.getMartesTurno());
			ps.setBigDecimal(7, t.getMiercolesTurno());
			ps.setBigDecimal(8, t.getJuevesTurno());
			ps.setBigDecimal(9, t.getViernesTurno());
			ps.setBigDecimal(10, t.getSabadoTurno());
			ps.setBigDecimal(11, t.getDomingoTurno());
			ps.setTime(12, t.getLunesAI());
			ps.setTime(13, t.getLunesAF());
			ps.setTime(14, t.getMartesAI());
			ps.setTime(15, t.getMartesAF());
			ps.setTime(16, t.getMiercolesAI());
			ps.setTime(17, t.getMiercolesAF());
			ps.setTime(18, t.getJuevesAI());
			ps.setTime(19, t.getJuevesAF());
			ps.setTime(20, t.getViernesAI());
			ps.setTime(21, t.getViernesAF());
			ps.setTime(22, t.getSabadoAI());
			ps.setTime(23, t.getSabadoAF());
			ps.setTime(24, t.getDomingoAI());
			ps.setTime(25, t.getDomingoAF());
			ps.setTime(26, t.getLunesPI());
			ps.setTime(27, t.getLunesPF());
			ps.setTime(28, t.getMartesPI());
			ps.setTime(29, t.getMartesPF());
			ps.setTime(30, t.getMiercolesPI());
			ps.setTime(31, t.getMiercolesPF());
			ps.setTime(32, t.getJuevesPI());
			ps.setTime(33, t.getJuevesPF());
			ps.setTime(34, t.getViernesPI());
			ps.setTime(35, t.getViernesPF());
			ps.setTime(36, t.getSabadoPI());
			ps.setTime(37, t.getSabadoPF());
			ps.setTime(38, t.getDomingoPI());
			ps.setTime(39, t.getDomingoPF());
			
			ps.executeUpdate();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			ps.close();
			db.close();
		}
		
		
	}
	public static Turno getTurnoSimpleById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		Turno tf=new Turno();
		try{
			sql = "SELECT * FROM a_turnos "+
				 
				  "where a_turnos.activo=1 and a_turnos.idTurno="+id;
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Turno t= new Turno();				
				t.setIdTurno(rs.getInt("idTurno"));	
				t.setNombreTurno(rs.getString("nombreTurno"));
				tf=t;
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return tf;
	}
	public static Turno getTurnoById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		Turno tf=new Turno();
		try{
			sql = "SELECT * FROM a_turnos "+
				  "left join (select parametros.llave, parametros.descripcion as nombreJornada from parametros where activo=1 and codigo='JORNADA') as b on a_turnos.jornadaTurno=b.llave "+
				  "where a_turnos.activo=1 and a_turnos.idTurno="+id+ "order by nombreJornada DESC";
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Turno t= new Turno();				
				t.setIdTurno(rs.getInt("idTurno"));	
				t.setNombreTurno(rs.getString("nombreTurno"));
				t.setDescripcionTurno(rs.getString("descripcionTurno"));
				t.setJornadaTurno(rs.getInt("jornadaTurno"));
				t.setNombreJornada(rs.getString("nombreJornada"));
				t.setHorasTurno(rs.getBigDecimal("horasTurno"));
				t.setLunesTurno(rs.getBigDecimal("lunesTurno"));
				t.setMartesTurno(rs.getBigDecimal("martesTurno"));
				t.setMiercolesTurno(rs.getBigDecimal("miercolesTurno"));
				t.setJuevesTurno(rs.getBigDecimal("juevesTurno"));
				t.setViernesTurno(rs.getBigDecimal("viernesTurno"));
				t.setSabadoTurno(rs.getBigDecimal("sabadoTurno"));
				t.setDomingoTurno(rs.getBigDecimal("domingoTurno"));
				t.setLunesAI(rs.getTime("lunesAI"));
				t.setLunesAF(rs.getTime("lunesAF"));
				t.setLunesPI(rs.getTime("lunesPI"));
				t.setLunesPF(rs.getTime("lunesPF"));
				t.setMartesAI(rs.getTime("martesAI"));
				t.setMartesAF(rs.getTime("martesAF"));
				t.setMartesPI(rs.getTime("martesPI"));
				t.setMartesPF(rs.getTime("martesPF"));
				t.setMiercolesAI(rs.getTime("miercolesAI"));
				t.setMiercolesAF(rs.getTime("miercolesAF"));
				t.setMiercolesPI(rs.getTime("miercolesPI"));
				t.setMiercolesPF(rs.getTime("miercolesPF"));
				t.setJuevesAI(rs.getTime("juevesAI"));
				t.setJuevesAF(rs.getTime("juevesAF"));
				t.setJuevesPI(rs.getTime("juevesPI"));
				t.setJuevesPF(rs.getTime("juevesPF"));
				t.setViernesAI(rs.getTime("viernesAI"));
				t.setViernesAF(rs.getTime("viernesAF"));
				t.setViernesPI(rs.getTime("viernesPI"));
				t.setViernesPF(rs.getTime("viernesPF"));
				t.setSabadoAI(rs.getTime("sabadoAI"));
				t.setSabadoAF(rs.getTime("sabadoAF"));
				t.setSabadoPI(rs.getTime("sabadoPI"));
				t.setSabadoPF(rs.getTime("sabadoPF"));
				t.setDomingoAI(rs.getTime("domingoAI"));
				t.setDomingoAF(rs.getTime("domingoAF"));
				t.setDomingoPI(rs.getTime("domingoPI"));
				t.setDomingoPF(rs.getTime("domingoPF"));
				
				
				
				
				tf=t;
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return tf;
	}

	public static Turno getBlankTurno() {
		return new Turno();
	}

	public static ArrayList<Turno> getAllTurnos() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Turno> lista = new ArrayList<Turno>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM a_turnos "+
				  "left join (select parametros.llave, parametros.descripcion as nombreJornada from parametros where activo=1 and codigo='JORNADA') as b on a_turnos.jornadaTurno=b.llave "+
				  "where a_turnos.activo=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Turno t= new Turno();				
				t.setIdTurno(rs.getInt("idTurno"));	
				t.setNombreTurno(rs.getString("nombreTurno"));
				t.setDescripcionTurno(rs.getString("descripcionTurno"));
				t.setJornadaTurno(rs.getInt("jornadaTurno"));
				t.setNombreJornada(rs.getString("nombreJornada"));
				t.setHorasTurno(rs.getBigDecimal("horasTurno"));
				lista.add(t);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	
	public static ArrayList<Turno> getAllTurnosSimpleWork() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Turno> lista = new ArrayList<Turno>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT * FROM a_turnos WHERE activo=1 ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Turno t= new Turno();				
				t.setIdTurno(rs.getInt("idTurno"));	
				t.setNombreTurno(rs.getString("nombreTurno"));
				t.setDescripcionTurno(rs.getString("descripcionTurno"));
				t.setJornadaTurno(rs.getInt("jornadaTurno"));
				//t.setNombreJornada(rs.getString("nombreJornada"));
				t.setHorasTurno(rs.getBigDecimal("horasTurno"));
				lista.add(t);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	
	
	public static boolean deleteTurno(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE a_turnos SET activo = 0 WHERE idTurno="+id;
		
			ps = db.conn.prepareStatement(sql);
			
			
			ps.executeUpdate();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}

	

	public static boolean updateTurnoSimple(Turno t) {
		// TODO Auto-generated method stub
		return false;
	}

	

	public static ArrayList<Turno> getAllTurnosSimple() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Turno> lista = new ArrayList<Turno>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT idTurno, nombreTurno, sociedad, huerto, zona, ceco FROM a_turnos  where activo=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Turno t= new Turno();				
				t.setIdTurno(rs.getInt("idTurno"));	
				t.setNombreTurno(rs.getString("nombreTurno"));
				t.setSociedad(rs.getString("sociedad"));
				t.setHuerto(rs.getString("huerto"));
				t.setZona(rs.getString("zona"));
				t.setCeco(rs.getString("ceco"));
				lista.add(t);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	public static ArrayList<Turno> getAllTurnosFiltered(String Sociedad, String huerto) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Turno> lista = new ArrayList<Turno>();
		ConnectionDB db = new ConnectionDB();
			
		String filter="";
		
		if(Sociedad.equals("0")){
			if(huerto.equals("0")){
				filter="";
			}
		}
		else{
			if(huerto.equals("0")){
				filter="(sociedad='"+Sociedad+"' or sociedad is null) and";
			}
			else{
				filter="(sociedad='"+Sociedad+"' or sociedad is null) and (huerto='"+huerto+"' or huerto is null) and";
			}
		}
		
		
		try{
			sql = "SELECT idTurno, nombreTurno, sociedad, huerto, zona, ceco, activo FROM a_turnos "+  
				  "where "+filter+" activo=1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Turno t= new Turno();				
				t.setIdTurno(rs.getInt("idTurno"));	
				t.setNombreTurno(rs.getString("nombreTurno"));
				t.setSociedad(rs.getString("sociedad"));
				t.setHuerto(rs.getString("huerto"));
				t.setZona(rs.getString("zona"));
				t.setCeco(rs.getString("ceco"));
				lista.add(t);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;	
	}
	
	
	
	
	public static boolean updateTurnoTrabajadores(ArrayList<Estados> a) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			
			for(Estados e: a){
				sql ="UPDATE contratos SET idTurno='"+e.getIdTurno()+"' WHERE id='"+e.getId()+"'";
				ps = db.conn.prepareStatement(sql);
				ps.executeUpdate();
			}

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			ps.close();
			db.close();
		}
	}
	public static ArrayList<Tbjdr> getTrabajadoresByTurno(int idTurno) throws SQLException {

		
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Tbjdr> lista = new ArrayList<Tbjdr>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT contratos.id, contratos.codigo_trabajador,trabajadores.rut, trabajadores.apellidoPaterno, trabajadores.apellidoMaterno, "+
					"trabajadores.nombre ,sociedad.sociedad, sociedad.denominacionSociedad,trabajadores.idHuerto, "+
					"campo.descripcion as nombreHuerto,campo.zona, "+
					"trabajadores.idCECO, contratos.idTurno ,contratos.EstadoContrato FROM SAN_CLEMENTE.contratos "+
					"inner join trabajadores on trabajadores.codigo=contratos.codigo_trabajador "+
					"inner join sociedad on contratos.idSociedad=sociedad.idSociedad "+
					"inner join campo on trabajadores.idHuerto=campo.campo "+
					"WHERE trabajadores.idCECO!='null'  and trabajadores.idCECO!='' and idTurno='"+idTurno+"' and "+
					"contratos.EstadoContrato=1 order by sociedad.sociedad,trabajadores.idHuerto, trabajadores.apellidoPaterno, trabajadores.apellidoMaterno, nombre ASC";
			
				//sociedad.sociedad='AS01' and idHuerto='AS06' and idZona like '%AS01MQ%' and idCECO like '%AS01MCO001%' and 
			
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Tbjdr t= new Tbjdr();
				t.setId(rs.getInt("id"));
				t.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
				t.setRut(rs.getString("rut"));
				t.setApellidoPaterno(rs.getString("apellidoPaterno"));
				t.setApellidoMaterno(rs.getString("apellidoMaterno"));
				t.setNombre(rs.getString("nombre"));
				t.setSociedad(rs.getString("sociedad"));
				t.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				t.setIdHuerto(rs.getString("idHuerto"));
				t.setNombreHuerto(rs.getString("nombreHuerto"));
				t.setZona(rs.getString("zona"));
				t.setIdCECO(rs.getString("idCECO"));
				t.setIdTurno(rs.getInt("idTurno"));
				t.setEstadoContrato(rs.getInt("EstadoContrato"));
				
				lista.add(t);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	
	
	public static ArrayList<Tbjdr> getTrabajadoresWithoutFilter() throws SQLException{
		
							
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Tbjdr> lista = new ArrayList<Tbjdr>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "SELECT contratos.id, contratos.codigo_trabajador,trabajadores.rut, trabajadores.apellidoPaterno, trabajadores.apellidoMaterno, "+
					"trabajadores.nombre ,sociedad.sociedad, sociedad.denominacionSociedad,trabajadores.idHuerto, "+
					"campo.descripcion as nombreHuerto,campo.zona, "+
					"trabajadores.idCECO, contratos.idTurno ,contratos.EstadoContrato FROM SAN_CLEMENTE.contratos "+
					"inner join trabajadores on trabajadores.codigo=contratos.codigo_trabajador "+
					"inner join sociedad on contratos.idSociedad=sociedad.idSociedad "+
					"inner join campo on trabajadores.idHuerto=campo.campo "+
					"WHERE trabajadores.idCECO!='null'  and trabajadores.idCECO!='' and "+
					"contratos.EstadoContrato=1 order by sociedad.sociedad,trabajadores.idHuerto, trabajadores.apellidoPaterno, trabajadores.apellidoMaterno, nombre ASC";
			
				//sociedad.sociedad='AS01' and idHuerto='AS06' and idZona like '%AS01MQ%' and idCECO like '%AS01MCO001%' and 
			
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Tbjdr t= new Tbjdr();
				t.setId(rs.getInt("id"));
				t.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
				t.setRut(rs.getString("rut"));
				t.setApellidoPaterno(rs.getString("apellidoPaterno"));
				t.setApellidoMaterno(rs.getString("apellidoMaterno"));
				t.setNombre(rs.getString("nombre"));
				t.setSociedad(rs.getString("sociedad"));
				t.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				t.setIdHuerto(rs.getString("idHuerto"));
				t.setNombreHuerto(rs.getString("nombreHuerto"));
				t.setZona(rs.getString("zona"));
				t.setIdCECO(rs.getString("idCECO"));
				t.setIdTurno(rs.getInt("idTurno"));
				t.setEstadoContrato(rs.getInt("EstadoContrato"));
				
				lista.add(t);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
		
		
		
	}
	public static ArrayList<Tbjdr> getTrabajadoresBy(String sociedad, String huerto) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Tbjdr> lista = new ArrayList<Tbjdr>();
		ConnectionDB db = new ConnectionDB();
		
		String filter="";
		
		if(sociedad.equals("0")){
			filter="";
		}
		else{
			if(huerto.equals("0")){
				filter="sociedad.sociedad='"+sociedad+"' and ";
			}
			else{
				filter="sociedad.sociedad='"+sociedad+"' and campo.campo='"+huerto+"' and";
			}
		}
		
		
		
		
			
		try{
			sql = "SELECT contratos.id, contratos.codigo_trabajador,trabajadores.rut, trabajadores.apellidoPaterno, trabajadores.apellidoMaterno, "+
					"trabajadores.nombre ,sociedad.sociedad, sociedad.denominacionSociedad,trabajadores.idHuerto, "+
					"campo.descripcion as nombreHuerto,campo.zona, "+
					"trabajadores.idCECO, contratos.idTurno ,contratos.EstadoContrato FROM SAN_CLEMENTE.contratos "+
					"inner join trabajadores on trabajadores.codigo=contratos.codigo_trabajador "+
					"inner join sociedad on contratos.idSociedad=sociedad.idSociedad "+
					"inner join campo on trabajadores.idHuerto=campo.campo "+
					"WHERE "+filter+" trabajadores.idCECO!='null'  and trabajadores.idCECO!='' and "+
					"contratos.EstadoContrato=1 order by sociedad.sociedad,trabajadores.idHuerto, trabajadores.apellidoPaterno, trabajadores.apellidoMaterno, nombre ASC";
			
				//sociedad.sociedad='AS01' and idHuerto='AS06' and idZona like '%AS01MQ%' and idCECO like '%AS01MCO001%' and 
			
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Tbjdr t= new Tbjdr();
				t.setId(rs.getInt("id"));
				t.setCodigo_trabajador(rs.getInt("codigo_trabajador"));
				t.setRut(rs.getString("rut"));
				t.setApellidoPaterno(rs.getString("apellidoPaterno"));
				t.setApellidoMaterno(rs.getString("apellidoMaterno"));
				t.setNombre(rs.getString("nombre"));
				t.setSociedad(rs.getString("sociedad"));
				t.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				t.setIdHuerto(rs.getString("idHuerto"));
				t.setNombreHuerto(rs.getString("nombreHuerto"));
				t.setZona(rs.getString("zona"));
				t.setIdCECO(rs.getString("idCECO"));
				t.setIdTurno(rs.getInt("idTurno"));
				t.setEstadoContrato(rs.getInt("EstadoContrato"));
				lista.add(t);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
}
