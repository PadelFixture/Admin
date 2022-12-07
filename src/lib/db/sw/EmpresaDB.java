package lib.db.sw;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import lib.classSW.Empresa;
import lib.classSW.RHCalculo;
import lib.db.ConnectionDB;


public class EmpresaDB{

	public static boolean createEmpresa(Empresa em) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		try {
			sql = "INSERT INTO sociedad (sociedad, denominacionSociedad, idSociedad, rut, numeroConvenio, numeroNomina, tipoNomina, idMutual, idCajaCompensacion, tasaUno, tasaDos, tasaTres, gratificacion ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, em.getSociedad());
			ps.setString(2, em.getDenominacionSociedad());
			ps.setInt(3, em.getIdSociedad());
			ps.setString(4, em.getRut());
			ps.setString(5, em.getNumeroConvenio());
			ps.setString(6, em.getNumeroNomina());
			ps.setString(7, em.getTipoNomina());
			ps.setInt(8, em.getIdMutual());
			ps.setInt(9, em.getIdCajaCompensacion());
			ps.execute();
			return true;

		}catch (SQLException e) {
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
	
	public static ArrayList<Empresa> getEmpresasj() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<Empresa> ems=new ArrayList<Empresa>();
		try {
			sql="select * from sociedad "+
				"where sociedad.idSociedad!=-1 "
				+"ORDER BY denominacionSociedad ASC";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				Empresa m= new Empresa();
				m.setSociedad(rs.getString("sociedad"));
				m.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				m.setIdSociedad(rs.getInt("idSociedad"));
				ems.add(m);
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
		return ems;		
	}

	public static ArrayList<Empresa> getEmpresas() throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 ArrayList<Empresa> ems=new ArrayList<Empresa>();
		try {
			sql="select * from sociedad "+
				"left join (select descripcion AS mutual, llave from parametros where codigo='MUTUALES' and activo=1) as d on sociedad.idMutual=d.llave "+
				"left join (select descripcion as cajaCompensacion, llave from parametros where codigo='CAJA_COMPENSACION' and activo=1) as e on sociedad.idCajaCompensacion=e.llave "+
				"left join (select idSociedad, max(periodo) as pST from sw_rhcalculo group by idSociedad) as f on sociedad.idSociedad=f.idSociedad "+
				"left join (select idSociedad, max(periodo) as pG, valor as valorG from sw_rhcalculo WHERE idCalculo=15 group by idSociedad, valor) as g on sociedad.idSociedad=g.idSociedad "+
				"left join (select idSociedad, max(periodo) as pCB, valor as valorCB from sw_rhcalculo WHERE idCalculo=17 group by idSociedad, valor) as h on sociedad.idSociedad=h.idSociedad "+
				"left join (select idSociedad, max(periodo) as pCA, valor as valorCA from sw_rhcalculo WHERE idCalculo=18 group by idSociedad, valor) as i on sociedad.idSociedad=i.idSociedad "+
				"left join (select idSociedad, max(periodo) as pCE, valor as valorCE from sw_rhcalculo WHERE idCalculo=19 group by idSociedad, valor) as j on sociedad.idSociedad=j.idSociedad "+
				"left join (select idSociedad, max(periodo) as pSA, valor as valorSA from sw_rhcalculo WHERE idCalculo=20 group by idSociedad, valor) as k on sociedad.idSociedad=k.idSociedad "+
				"where sociedad.idSociedad!=-1 and pST=pG and pST=pCB and pST=pCA and pST=pCE and psT=pSA ORDER BY denominacionSociedad ASC";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				Empresa m= new Empresa();
				m.setSociedad(rs.getString("sociedad"));
				m.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				m.setIdSociedad(rs.getInt("idSociedad"));
				m.setRut(rs.getString("rut"));
				m.setNumeroConvenio(rs.getString("numeroConvenio"));
				m.setNumeroNomina(rs.getString("numeroNomina"));
				m.setTipoNomina(rs.getString("tipoNomina"));
				m.setIdMutual(rs.getInt("idMutual"));
				m.setIdCajaCompensacion(rs.getInt("idCajaCompensacion"));
				m.setMutual(rs.getString("mutual"));
				m.setCajaCompensacion(rs.getString("cajaCompensacion"));
				m.setValorG(rs.getDouble("valorG"));
				m.setValorCB(rs.getDouble("valorCB"));
				m.setValorCA(rs.getDouble("valorCA"));
				m.setValorCE(rs.getDouble("valorCE"));
				m.setValorSA(rs.getDouble("valorSA"));
				ems.add(m);
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
		return ems;		
	}

	public static Empresa getEmpresaBySociedad(String soc) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		 Empresa emp=new Empresa();
		try {
			sql="select * from sociedad "+
					"left join (select descripcion AS mutual, llave from parametros where codigo='MUTUALES' and activo=1) as d on sociedad.idMutual=d.llave "+
					"left join (select descripcion as cajaCompensacion, llave from parametros where codigo='CAJA_COMPENSACION' and activo=1) as e on sociedad.idCajaCompensacion=e.llave "+
					"left join (select idSociedad, max(periodo) as pST from sw_rhcalculo group by idSociedad) as f on sociedad.idSociedad=f.idSociedad "+
					"left join (select idSociedad, max(periodo) as pG, valor as valorG from sw_rhcalculo WHERE idCalculo=15 group by idSociedad, valor) as g on sociedad.idSociedad=g.idSociedad "+
					"left join (select idSociedad, max(periodo) as pCB, valor as valorCB from sw_rhcalculo WHERE idCalculo=17 group by idSociedad, valor) as h on sociedad.idSociedad=h.idSociedad "+
					"left join (select idSociedad, max(periodo) as pCA, valor as valorCA from sw_rhcalculo WHERE idCalculo=18 group by idSociedad, valor) as i on sociedad.idSociedad=i.idSociedad "+
					"left join (select idSociedad, max(periodo) as pCE, valor as valorCE from sw_rhcalculo WHERE idCalculo=19 group by idSociedad, valor) as j on sociedad.idSociedad=j.idSociedad "+
					"left join (select idSociedad, max(periodo) as pSA, valor as valorSA from sw_rhcalculo WHERE idCalculo=20 group by idSociedad, valor) as k on sociedad.idSociedad=k.idSociedad "+
					"where sociedad.sociedad='"+soc+"' and sociedad.idSociedad!=-1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				Empresa m= new Empresa();
				m.setSociedad(rs.getString("sociedad"));
				m.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				m.setIdSociedad(rs.getInt("idSociedad"));
				m.setRut(rs.getString("rut"));
				m.setNumeroConvenio(rs.getString("numeroConvenio"));
				m.setNumeroNomina(rs.getString("numeroNomina"));
				m.setTipoNomina(rs.getString("tipoNomina"));
				m.setIdMutual(rs.getInt("idMutual"));
				m.setIdCajaCompensacion(rs.getInt("idCajaCompensacion"));
				m.setMutual(rs.getString("mutual"));
				m.setCajaCompensacion(rs.getString("cajaCompensacion"));
				m.setValorG(rs.getDouble("valorG"));
				m.setValorCB(rs.getDouble("valorCB"));
				m.setValorCA(rs.getDouble("valorCA"));
				m.setValorCE(rs.getDouble("valorCE"));
				m.setValorSA(rs.getDouble("valorSA"));
				m.setpG(rs.getInt("pG"));
				m.setpCB(rs.getInt("pCB"));
				m.setpCA(rs.getInt("pCA"));
				m.setpCE(rs.getInt("pCE"));
				m.setpSA(rs.getInt("pSA"));
				emp=m;
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
		return emp;		
		
	}

	public static Empresa getEmpresaById(int id) throws SQLException {
		PreparedStatement ps = null;

		String sql = "";
		ConnectionDB  db = new ConnectionDB();	
		
		 Empresa emp=new Empresa();
		try {
			sql="select * from sociedad "+
					"left join (select descripcion AS mutual, llave from parametros where codigo='MUTUALES' and activo=1) as d on sociedad.idMutual=d.llave "+
					"left join (select descripcion as cajaCompensacion, llave from parametros where codigo='CAJA_COMPENSACION' and activo=1) as e on sociedad.idCajaCompensacion=e.llave "+
					"left join (select idSociedad, max(periodo) as pST from sw_rhcalculo group by idSociedad) as f on sociedad.idSociedad=f.idSociedad "+
					"left join (select idSociedad, max(periodo) as pG, valor as valorG from sw_rhcalculo WHERE idCalculo=15 group by idSociedad, valor) as g on sociedad.idSociedad=g.idSociedad "+
					"left join (select idSociedad, max(periodo) as pCB, valor as valorCB from sw_rhcalculo WHERE idCalculo=17 group by idSociedad, valor) as h on sociedad.idSociedad=h.idSociedad "+
					"left join (select idSociedad, max(periodo) as pCA, valor as valorCA from sw_rhcalculo WHERE idCalculo=18 group by idSociedad, valor) as i on sociedad.idSociedad=i.idSociedad "+
					"left join (select idSociedad, max(periodo) as pCE, valor as valorCE from sw_rhcalculo WHERE idCalculo=19 group by idSociedad, valor) as j on sociedad.idSociedad=j.idSociedad "+
					"left join (select idSociedad, max(periodo) as pSA, valor as valorSA from sw_rhcalculo WHERE idCalculo=20 group by idSociedad, valor) as k on sociedad.idSociedad=k.idSociedad "+
					"where sociedad.idSociedad='"+id+"' and sociedad.idSociedad!=-1 and pST=pG and pST=pCB and pST=pCA and pST=pCE and psT=pSA";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();	
			while (rs.next()) {
				Empresa m= new Empresa();
				m.setSociedad(rs.getString("sociedad"));
				m.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				m.setIdSociedad(rs.getInt("idSociedad"));
				m.setRut(rs.getString("rut"));
				m.setNumeroConvenio(rs.getString("numeroConvenio"));
				m.setNumeroNomina(rs.getString("numeroNomina"));
				m.setTipoNomina(rs.getString("tipoNomina"));
				m.setIdMutual(rs.getInt("idMutual"));
				m.setIdCajaCompensacion(rs.getInt("idCajaCompensacion"));
				m.setMutual(rs.getString("mutual"));
				m.setCajaCompensacion(rs.getString("cajaCompensacion"));
				m.setValorG(rs.getDouble("valorG"));
				m.setValorCA(rs.getDouble("valorCA"));
				m.setValorCB(rs.getDouble("valorCB"));
				m.setValorCE(rs.getDouble("valorCE"));
				m.setValorSA(rs.getDouble("valorSA"));
				m.setpG(rs.getInt("pG"));
				m.setpCB(rs.getInt("pCB"));
				m.setpCA(rs.getInt("pCA"));
				m.setpCE(rs.getInt("pCE"));
				m.setpSA(rs.getInt("pSA"));
				emp=m;
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
		return emp;	
	}

	public static boolean updateEmpresa(Empresa e, int iduser) throws SQLException {
		int pToday=0;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH);
		pToday=year*100;
		pToday=pToday+month+1;
		try {
			if(e.getpG()==pToday)
			{
				updateCalculo(15,e, iduser);
			}
			else
			{
				insertCalculo(15, e, iduser);
			}
			if(e.getpCB()==pToday)
			{
				updateCalculo(17,e, iduser);
			}
			else
			{
				insertCalculo(17, e, iduser);
			}
			if(e.getpCA()==pToday)
			{
				updateCalculo(18,e, iduser);
			}
			else
			{
				insertCalculo(18, e, iduser);
			}
			if(e.getpCE()==pToday)
			{
				updateCalculo(19,e, iduser);
			}
			else
			{
				insertCalculo(19, e, iduser);
			}
			if(e.getpSA()==pToday)
			{
				updateCalculo(20,e, iduser);
			}
			else
			{
				insertCalculo(20, e, iduser);
			}
			String sql="UPDATE sociedad SET idMutual=?,idCajaCompensacion=? WHERE idSociedad=?";
			PreparedStatement ps = null;
			ConnectionDB  db = new ConnectionDB();	
			ps=db.conn.prepareStatement(sql);
			ps.setInt(1, e.getIdMutual());
			ps.setInt(2, e.getIdCajaCompensacion());
			ps.setInt(3, e.getIdSociedad());
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
	public static boolean insertCalculo(int idCalculo, Empresa e, int idUsuario) throws SQLException
	{
		RHCalculo r=new RHCalculo();
		String sql="";
		double valor=0;
		int idusuario=idUsuario;
		int pToday=0;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH);
		pToday=year*100;
		pToday=pToday+month+1;
		Date today = new java.util.Date();
		Timestamp t=  new Timestamp(today.getTime());
		if(idCalculo==15)
		{
			r=rhcalculoDB.getConceptoById(idCalculo);
			valor=e.getValorG();
		}
		else if(idCalculo==17)
		{
			r=rhcalculoDB.getConceptoById(idCalculo);
			valor=e.getValorCB();
		}
		else if(idCalculo==18)
		{
			r=rhcalculoDB.getConceptoById(idCalculo);
			valor=e.getValorCA();
		}
		else if(idCalculo==19)
		{
			r=rhcalculoDB.getConceptoById(idCalculo);
			valor=e.getValorCE();
		}
		else if(idCalculo==20)
		{
			r=rhcalculoDB.getConceptoById(idCalculo);
			valor=e.getValorSA();
		}
		else
		{
			
		}
		sql="INSERT INTO sw_rhcalculo (idCalculo, concepto, idMoneda, valor, idSociedad, modificado, idUsuario, periodo) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		ConnectionDB  db = new ConnectionDB();	
		
				try{			
					ps = db.conn.prepareStatement(sql);
					ps.setInt(1, idCalculo);
					ps.setString(2, r.getConcepto());
					ps.setInt(3, r.getMoneda());
					ps.setDouble(4, valor);
					ps.setInt(5, e.getIdSociedad());
					ps.setTimestamp(6, t);
					ps.setInt(7, idusuario);
					ps.setInt(8, pToday);
					ps.execute();
					return true;
				}catch(Exception ex){

					System.out.println("Error insertDocumentos:" + ex.getMessage());
					ex.printStackTrace();
					return false;

				}finally{
					db.conn.close();
					ps.close();
				}
	}
	
	public static boolean updateCalculo(int idCalculo, Empresa e, int idUsuario) throws SQLException
	{
		String sql="";
		double valor=0;
		int idusuario=idUsuario;
		int pToday=0;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH);
		pToday=year*100;
		pToday=pToday+month+1;
		Date today = new java.util.Date();
		Timestamp t=  new Timestamp(today.getTime());
		if(idCalculo==15)
		{
			valor=e.getValorG();
		}
		else if(idCalculo==17)
		{
			valor=e.getValorCB();
		}
		else if(idCalculo==18)
		{
			valor=e.getValorCA();
		}
		else if(idCalculo==19)
		{
			valor=e.getValorCE();
		}
		else if(idCalculo==20)
		{
			valor=e.getValorSA();
		}
		else
		{
			
		}
		sql="UPDATE sw_rhcalculo SET valor=?, modificado=?, idUsuario=? WHERE idCalculo=? AND idSociedad=? AND periodo=?";
		PreparedStatement ps = null;
		ConnectionDB  db = new ConnectionDB();	
		try{
			ps = db.conn.prepareStatement(sql);
			ps.setDouble(1, valor);
			ps.setTimestamp(2, t);
			ps.setInt(3, idusuario);
			ps.setInt(4, idCalculo);
			ps.setInt(5, e.getIdSociedad());			
			ps.setInt(6, pToday);
			ps.executeUpdate();
			return true;
		}catch(Exception ex){

			System.out.println("Error insertDocumentos:" + ex.getMessage());
			ex.printStackTrace();
			return false;

		}finally{
			db.conn.close();
			ps.close();
		}
	}
	
/*
	public static boolean deleteEmpresaById(int id) throws SQLException {
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
	*/
/*
	public static boolean deleteEmpresaBySociedad(String soc) throws SQLException {
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
	*/
}
