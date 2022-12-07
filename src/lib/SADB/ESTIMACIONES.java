package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import org.junit.experimental.categories.Categories;

import lib.classSA.CALIBRE;
import lib.classSA.CATEGORIA;
import lib.classSA.CUARTEL_PF;
import lib.classSA.Consumo_Combustible;
import lib.classSA.EstimacionIngreso;
import lib.classSA.LISTA_APLICACIONES_OBJ;
import lib.classSA.RENDIMIENTO_GENERAL;
import lib.classSA.SEMANA;
import lib.classSA.VERSION;
import lib.classSA.estado_fenologico;
import lib.classSA.estimacionAnual;
import lib.classSA.estimacion_calibre_categoria;
import lib.classSA.estimacion_data_prd;
import lib.classSA.estimacion_prd_dias;
import lib.classSA.estimacion_productiva;
import lib.classSA.estimacion_semana;
import lib.classSA.estimacion_semanal_21;
import lib.classSA.parametros_estimacion;
import lib.db.ConnectionDB;

public class ESTIMACIONES {

	public static SEMANA getSemanas(String especie) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 SEMANA ob = new SEMANA();
		 try {
			 sql = "SELECT * FROM semanas_especie where especie = '"+especie+"'";
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 ob.setSemana_inicio(rs.getInt("semana_inicio"));
				 ob.setSemana_fin(rs.getInt("semana_termino"));
			 }
			 
			return ob;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return ob;
	}
	
	public static ArrayList<CATEGORIA> getCategoria(String especie) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<CATEGORIA> list = new ArrayList<CATEGORIA>();
		 try {
			 sql = "SELECT * FROM categoria where especie = '"+especie+"'";
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 CATEGORIA ob = new CATEGORIA();
				 ob.setDescripcion(rs.getString("descripcion"));
				 //ob.setSemana_fin(rs.getInt("semana_termino"));
				 list.add(ob);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static ArrayList<CATEGORIA> getCalibre(String especie) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<CATEGORIA> list = new ArrayList<CATEGORIA>();
		 try {
			 sql = "SELECT * FROM calibre where especie = '"+especie+"'";
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 CATEGORIA ob = new CATEGORIA();
				 ob.setDescripcion(rs.getString("tipo"));
				 //ob.setSemana_fin(rs.getInt("semana_termino"));
				 list.add(ob);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static boolean insertEstimacion (estimacionAnual ea)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="INSERT INTO estimacion_anual (productor, especie, variedad, nvariedad, version, temporada, fecha, usuario, nproductor) "
					+ " values (?,?,?,?,?,?,NOW(),?,?) ";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, ea.getProductor());
			ps.setString(2, ea.getEspecie());
			ps.setInt(3, ea.getVariedad());
			ps.setString(4, ea.getNvariedad());
			ps.setInt(5, ea.getVersion());
			ps.setInt(6, ea.getTemporada());
			ps.setInt(7, ea.getUsuario());
			ps.setString(8, ea.getNproductor());
			System.out.println(ps);
			ps.execute();
			
			String sql2 = "";
			sql2 = "SELECT MAX(codigo) as codigo from estimacion_anual";
			ResultSet idNew = ps.executeQuery(sql2);
			int codE = 0;
			while (idNew.next()) { 
				codE = idNew.getInt("codigo"); 
			}
			for(estimacion_semana es: ea.getEstimacion_semana()){
				es.setCodigo_anual(codE);
				insertValorSemana(es);
			}
			for(EstimacionIngreso ei: ea.getEstimacion_ingreso()){
				ei.setCodigo_anual(codE);
				insertIngreso(ei);
			}
			for(estimacion_calibre_categoria ei: ea.getEstimacion_calibre_categoria()){
				ei.setCodigo_anual(codE);
				insertCalibreCategoria(ei);
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static boolean updateEstimacion (estimacionAnual ea)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="UPDATE estimacion_anual SET productor = ?, especie = ?, variedad = ? , nvariedad = ?, version = ? , temporada = ? , fecha = NOW(), usuario = ? where codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, ea.getProductor());
			ps.setString(2, ea.getEspecie());
			ps.setInt(3, ea.getVariedad());
			ps.setString(4, ea.getNvariedad());
			ps.setInt(5, ea.getVersion());
			ps.setInt(6, ea.getTemporada());
			ps.setInt(7, ea.getUsuario());
			ps.setInt(8, ea.getCodigo());
			System.out.println(ps);
			ps.execute();
			
			for(estimacion_semana es: ea.getEstimacion_semana()){
				es.setCodigo_anual(ea.getCodigo());
				updateValorSemana(es);
			}
			for(EstimacionIngreso ei: ea.getEstimacion_ingreso()){
				ei.setCodigo_anual(ea.getCodigo());
				updateIngreso(ei);
			}
			for(estimacion_calibre_categoria ei: ea.getEstimacion_calibre_categoria()){
				ei.setCodigo_anual(ea.getCodigo());
				updateCalibreCategoria(ei);
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static boolean insertValorSemana (estimacion_semana es)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="insert into estmacion_anual_semanas (codigo_anual, semana, valor) values (?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, es.getCodigo_anual());
			ps.setString(2, es.getSemana());
			ps.setInt(3, es.getValor());
			System.out.println(ps);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static boolean updateValorSemana (estimacion_semana es)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="update estmacion_anual_semanas set valor = ? where semana = ? and codigo_anual = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(3, es.getCodigo_anual());
			ps.setString(2, es.getSemana());
			ps.setInt(1, es.getValor());
			System.out.println(ps);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static boolean insertIngreso (EstimacionIngreso ei)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="insert into estimacion_ingreso (exportacion, merma, comercial, codigo_anual) values (?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, ei.getExportacion());
			ps.setInt(2, ei.getMerma());
			ps.setInt(3, ei.getComercial());
			ps.setInt(4, ei.getCodigo_anual());
			System.out.println(ps);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static boolean updateIngreso (EstimacionIngreso ei)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="update estimacion_ingreso set exportacion = ?, merma = ?, comercial = ? where codigo_anual = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, ei.getExportacion());
			ps.setInt(2, ei.getMerma());
			ps.setInt(3, ei.getComercial());
			ps.setInt(4, ei.getCodigo_anual());
			System.out.println(ps);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static boolean insertCalibreCategoria (estimacion_calibre_categoria ec)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="insert into calibre_categoria (codigo_anual, categoria, calibre, anual, semana, editado, valor, usuario, fecha )"
					+ " values(?,?,?,?,?,?,?,?,NOW() ) ";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, ec.getCodigo_anual());
			ps.setString(2, ec.getCategoria());
			ps.setString(3, ec.getCalibre());
			ps.setInt(4, ec.getAnual());
			ps.setInt(5, ec.getSemana());
			ps.setInt(6,    ec.getEditado());
			ps.setInt(7, ec.getValor());
			ps.setInt(8, ec.getUsuario());
			//ps.setString(9, ec.getFecha());
			System.out.println(ps);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static boolean updateCalibreCategoria (estimacion_calibre_categoria ec)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="update calibre_categoria set valor = ?, usuario = ?, fecha = NOW() where codigo_anual = ? and categoria = ? and calibre = ? and anual = ? and semana = ? and editado = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, ec.getValor());
			ps.setInt(2, ec.getUsuario());
			ps.setInt(3, ec.getCodigo_anual());
			ps.setString(4, ec.getCategoria());
			ps.setString(5, ec.getCalibre());
			ps.setInt(6, ec.getAnual());
			ps.setInt(7, ec.getSemana());
			ps.setInt(8,    ec.getEditado());
			
			
			System.out.println(ps);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static estimacionAnual getEstimacionAnual(estimacionAnual ea) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 estimacionAnual list = new estimacionAnual();
		 try {
			 sql = "SELECT * FROM estimacion_anual where productor = '"+ea.getProductor()+"'  "
		+ " and especie = '"+ea.getEspecie()+"'  and variedad = '"+ea.getVariedad()+"'  and version = "+ea.getVersion();
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 list.setCodigo(rs.getInt("codigo"));
				 list.setEspecie(rs.getString("especie"));
				 list.setFecha(rs.getString("fecha"));
				 list.setNvariedad(rs.getString("nvariedad"));
				 list.setProductor(rs.getString("productor"));
				 list.setTemporada(rs.getInt("temporada"));
				 list.setUsuario(rs.getInt("usuario"));
				 list.setVariedad(rs.getInt("variedad"));
				 list.setVersion(rs.getInt("version"));
				 ArrayList<estimacion_semana> es = new ArrayList<estimacion_semana>();
				 ArrayList<EstimacionIngreso> ei = new ArrayList<EstimacionIngreso>();
				 ArrayList<estimacion_calibre_categoria> ecc = new ArrayList<estimacion_calibre_categoria>();
				 es  = getDataAnualSemana(rs.getInt("codigo"));
				 ecc = getDataCalibreCategoria(rs.getInt("codigo"),0, 0,0);
				 ei  = getDataIngreso(rs.getInt("codigo"));
				 list.setEstimacion_calibre_categoria(ecc);
				 list.setEstimacion_ingreso(ei);
				 list.setEstimacion_semana(es);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static estimacionAnual getEstimacionSemanal(estimacionAnual ea) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 estimacionAnual list = new estimacionAnual();
		 try {
			 sql = "SELECT * FROM estimacion_anual where productor = '"+ea.getProductor()+"'  "
		+ " and especie = '"+ea.getEspecie()+"'  and variedad = '"+ea.getVariedad()+"'  and version = "+ea.getVersion();
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 list.setCodigo(rs.getInt("codigo"));
				 list.setEspecie(rs.getString("especie"));
				 list.setFecha(rs.getString("fecha"));
				 list.setNvariedad(rs.getString("nvariedad"));
				 list.setProductor(rs.getString("productor"));
				 list.setTemporada(rs.getInt("temporada"));
				 list.setUsuario(rs.getInt("usuario"));
				 list.setVariedad(rs.getInt("variedad"));
				 list.setVersion(rs.getInt("version"));
				 ArrayList<estimacion_semana> es = new ArrayList<estimacion_semana>();
				 ArrayList<EstimacionIngreso> ei = new ArrayList<EstimacionIngreso>();
				 ArrayList<estimacion_calibre_categoria> ecc = new ArrayList<estimacion_calibre_categoria>();
				 es  = getDataAnualSemana(rs.getInt("codigo"));
				 ecc = getDataCalibreCategoria(rs.getInt("codigo"),0, 0,0);
				 ei  = getDataIngreso(rs.getInt("codigo"));
				 list.setEstimacion_calibre_categoria(ecc);
				 list.setEstimacion_ingreso(ei);
				 list.setEstimacion_semana(es);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static ArrayList<EstimacionIngreso> getDataIngreso(int codigo) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<EstimacionIngreso> list = new ArrayList<EstimacionIngreso>();
		 try {
			 sql = "SELECT * FROM estimacion_ingreso where codigo_anual ="+codigo;
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 EstimacionIngreso ob = new EstimacionIngreso();
				 ob.setCodigo(rs.getInt("codigo"));
				 ob.setComercial(rs.getInt("comercial"));
				 ob.setExportacion(rs.getInt("exportacion"));
				 ob.setMerma(rs.getInt("merma"));
				 list.add(ob);
				
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static ArrayList<estimacion_calibre_categoria> getDataCalibreCategoria(int codigo, int semana, int editado, int count) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<estimacion_calibre_categoria> list = new ArrayList<estimacion_calibre_categoria>();
		 try {
			 sql = "SELECT * FROM calibre_categoria where codigo_anual ="+codigo+" and semana = "+semana+" and editado = "+editado;
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 estimacion_calibre_categoria ob = new estimacion_calibre_categoria();
				 ob.setCodigo(rs.getInt("codigo"));
				 ob.setCategoria(rs.getString("categoria"));
				 ob.setCalibre(rs.getString("calibre"));
				 ob.setValor(rs.getInt("valor"));
				 list.add(ob);
				
			 }
			 System.out.println(list.size());
			 if(semana > 0 && list.size() == 0 && count < 3){
				 count++;
				 if(editado > 1){
					 editado--;
				 } else {
					 editado = 52;
				 }
				 
				 list = getDataCalibreCategoria(codigo, semana, editado, count);
			 }
			 if(semana > 0 && list.size() == 0 && count == 3){
				 list = getDataCalibreCategoria(codigo, 0, 0, count);
			 }
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static ArrayList<estimacion_semana> getDataAnualSemana(int codigo) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<estimacion_semana> list = new ArrayList<estimacion_semana>();
		 try {
			 sql = "SELECT * FROM estmacion_anual_semanas where codigo_anual ="+codigo;
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 estimacion_semana ob = new estimacion_semana();
				 ob.setCodigo(rs.getInt("codigo"));
				 ob.setSemana(rs.getString("semana"));
				 ob.setValor(rs.getInt("valor"));
				 list.add(ob);
				
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static ArrayList<VERSION> getVersion() throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<VERSION> list = new ArrayList<VERSION>();
		 try {
			 sql = "SELECT * FROM estimacion_version";
			 //System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 VERSION ob = new VERSION();
				 ob.setVersion(rs.getInt("version"));
				 ob.setEstado(rs.getInt("estado"));
				 list.add(ob);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static ArrayList<VERSION> getTemporada() throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<VERSION> list = new ArrayList<VERSION>();
		 try {
			 sql = "SELECT * FROM temporada";
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 VERSION ob = new VERSION();
				 ob.setVersion(rs.getInt("temporada"));
				 ob.setEstado(rs.getInt("estado"));
				 list.add(ob);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static ArrayList<estimacion_data_prd> getData21Dias(estimacionAnual ea, boolean valida) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<estimacion_data_prd> list = new ArrayList<estimacion_data_prd>();
		 try {
			 sql = "SELECT ea.*,eas.codigo codigo_semana,s21.codigo codigo21,     eas.semana, eas.valor, s21.lunes, s21.martes, s21.miercoles, s21.jueves, s21.viernes, s21.sabado,s21.domingo, s21.total "
			 		+ " FROM estimacion_anual ea "
			 		+ " left join estmacion_anual_semanas eas on eas.codigo_anual = ea.codigo "
			 		+ " left join (select * from estmacion_21_dias where editado = "+ea.getSe()+") s21 on s21.codigo_anual_semana = eas.codigo "
			 		+ " where eas.semana in ("+ea.getS1()+","+ea.getS2()+","+ea.getS3()+") "
			 		+ " and ea.productor = '"+ea.getProductor()+"' "
			 		+ " and ea.especie = '"+ea.getEspecie()+"' "
			 		+ " and ea.variedad =  "+ea.getVariedad()
			 		+ " and ea.version =  "+ea.getVersion()
			 		+ " and ea.temporada = "+ ea.getTemporada();
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 int count = 0;
			 while(rs.next()){
				 estimacion_data_prd ob = new estimacion_data_prd();
				 ob.setCodigo_semana(rs.getInt("codigo"));
				 ob.setCodigo_21(rs.getInt("codigo21"));
				 if(valida){
					 ob.setCodigo_21(0);
				 }
				 ob.setId(rs.getInt("codigo_semana"));
				 ob.setLunes(rs.getFloat("lunes"));
				 ob.setMartes(rs.getFloat("martes"));
				 ob.setMiercoles(rs.getFloat("miercoles"));
				 ob.setJueves(rs.getFloat("jueves"));
				 ob.setViernes(rs.getFloat("viernes"));
				 ob.setSabado(rs.getFloat("sabado"));
				 ob.setDomingo(rs.getFloat("domingo"));
				 ob.setTotal(rs.getFloat("total"));
				 ob.setSemana(rs.getInt("semana"));
				 list.add(ob);
				 if(rs.getString("total") != null){
					 count++;
				 }
			 }
			 if(count == 0){
				 if(ea.getSe() > 1){
					 ea.setSe(ea.getSe()-1);
				 } else {
					 ea.setSe(52);
				 }
				 
				 list = getData21Dias(ea, true);
			 }
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static boolean insertEstimacionSemanal (estimacion_semanal_21 aes)throws Exception{
	    try {
			 for(estimacion_data_prd es: aes.getEstimacion_data_prd()){
				insertEstimacion21(es);
			 }
			 for(estimacion_calibre_categoria ecc: aes.getEstimacion_calibre_categoria()){
				 insertCalibreCategoria(ecc);
			 }
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} 
		return false;
	}
	
	public static boolean updateEstimacionSemanal (estimacion_semanal_21 aes)throws Exception{
	    try {
			 for(estimacion_data_prd es: aes.getEstimacion_data_prd()){
				updateEstimacion21(es);
			 }
			 for(estimacion_calibre_categoria ecc: aes.getEstimacion_calibre_categoria()){
				 updateCalibreCategoria(ecc);
			 }
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} 
		return false;
	}
	
	public static boolean insertEstimacion21 (estimacion_data_prd es)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="INSERT INTO estmacion_21_dias (codigo_anual_semana, lunes, martes, miercoles, jueves, viernes, sabado, domingo, total, "
					+ " editado, usuario) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, es.getId());
			ps.setFloat(2, es.getLunes());
			ps.setFloat(3, es.getMartes());
			ps.setFloat(4, es.getMiercoles());
			ps.setFloat(5, es.getJueves());
			ps.setFloat(6, es.getViernes());
			ps.setFloat(7, es.getSabado());
			ps.setFloat(8, es.getDomingo());
			ps.setFloat(9, es.getTotal());
			ps.setInt(10, es.getSemana());
			ps.setInt(11, es.getUsuario());
			System.out.println(ps);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static boolean updateEstimacion21 (estimacion_data_prd es)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="UPDATE estmacion_21_dias set lunes = ?, martes = ?, miercoles = ?, jueves = ?, viernes = ?, sabado = ?, domingo = ?, total = ? ," 
					+ "  usuario = ? where codigo = ?;";
			ps = db.conn.prepareStatement(sql);
			ps.setFloat(1, es.getLunes());
			ps.setFloat(2, es.getMartes());
			ps.setFloat(3, es.getMiercoles());
			ps.setFloat(4, es.getJueves());
			ps.setFloat(5, es.getViernes());
			ps.setFloat(6, es.getSabado());
			ps.setFloat(7, es.getDomingo());
			ps.setFloat(8, es.getTotal());
			//ps.setInt(9, es.getSemana());
			ps.setInt(9, es.getUsuario());
			ps.setInt(10, es.getCodigo_21());
			System.out.println(ps);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		 return false;
	}
	
	public static ArrayList<estimacionAnual> getVariedadesEstimadas(estimacionAnual ea) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<estimacionAnual> list = new ArrayList<estimacionAnual>();
		 try {
			 sql = "SELECT distinct variedad FROM SAN_CLEMENTE.estimacion_anual where productor = '"+ea.getProductor()+"' and especie = '"+ea.getEspecie()+"'";
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 estimacionAnual ob = new estimacionAnual();
				 ob.setVariedad(rs.getInt("variedad"));
				 list.add(ob);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	public static ArrayList<estimacionAnual> getReporteEstimacionAnual(estimacionAnual ea) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<estimacionAnual> list = new ArrayList<estimacionAnual>();
		 try {
			 sql = "SELECT ea.*, eas.semana, eas.valor, cc.calibre, cc.categoria, cc.valor, ei.exportacion, "
			 		+ " ((eas.valor * cc.valor) /100 * ei.exportacion) /100 as valorFinal "
			 		+ " FROM estimacion_anual ea "
			 		+ " LEFT JOIN estmacion_anual_semanas eas on eas.codigo_anual = ea.codigo "
			 		+ " LEFT JOIN (select * from calibre_categoria where semana = 0) cc on cc.codigo_anual = ea.codigo "
			 		+ " LEFT JOIN estimacion_ingreso ei on ei.codigo_anual = ea.codigo "
			 		+ " where eas.valor  > 0 "
			 		+ " and cc.valor > 0";
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 estimacionAnual ob = new estimacionAnual();
				 ob.setTemporada(rs.getInt("temporada"));
				 ob.setVersion(rs.getInt("version"));
				 ob.setProductor(rs.getString("productor"));
				 ob.setNproductor(rs.getString("nproductor"));
				 ob.setEspecie(rs.getString("especie"));
				 ob.setVariedad(rs.getInt("variedad"));
				 ob.setNvariedad(rs.getString("nvariedad"));
				 ob.setSemana(rs.getInt("semana"));
				 ob.setExportacion(rs.getFloat("exportacion"));
				 ob.setCalibre(rs.getString("calibre"));
				 ob.setCategoria(rs.getString("categoria"));
				 ob.setValor(rs.getFloat("valorFinal"));
				 list.add(ob);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
	
	public static ArrayList<estimacionAnual> getReporteEstimacionSemana(estimacionAnual ea) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 ArrayList<estimacionAnual> list = new ArrayList<estimacionAnual>();
		 try {
			 sql = "SELECT ea.*, eas.semana, eas.valor,cc.calibre, cc.categoria, "
			 		+ "(e21.lunes * cc.valor / 100) lunes, "
			 		+ "(e21.martes * cc.valor / 100) martes, "
			 		+ "(e21.miercoles * cc.valor / 100) miercoles, "
			 		+ "(e21.jueves * cc.valor / 100) jueves, "
			 		+ "(e21.viernes * cc.valor / 100) viernes, "
			 		+ "(e21.sabado * cc.valor / 100) sabado, "
			 		+ "(e21.domingo * cc.valor / 100) domingo, "
			 		+ "(e21.total * cc.valor / 100) total, "
			 		+ "cc.valor "
			 		+ "FROM estimacion_anual ea "
			 		+ "LEFT JOIN estmacion_anual_semanas eas ON eas.codigo_anual = ea.codigo "
			 		+ "LEFT JOIN estmacion_21_dias e21 ON e21.codigo_anual_semana = eas.codigo "
			 		+ "LEFT JOIN estimacion_ingreso ei ON ei.codigo_anual = ea.codigo "
			 		+ "LEFT JOIN calibre_categoria cc ON cc.codigo_anual = ea.codigo and cc.semana = eas.semana "
			 		+ " WHERE "
			 		+ "    eas.valor > 0 and e21.editado =  "+ ea.getS1()
			 		+ "    and cc.editado = "+ea.getS1()+" and cc.anual = 0 "
			 		+ "    and cc.valor > 0 and e21.total > 0 order by eas.semana";
			 
			 if(!ea.getProductor().equals("0")){
				 sql += " and ea.productor = '"+ea.getProductor()+"' ";
			 }
			 if(!ea.getEspecie().equals("0")){
				 sql += " and ea.especie   = '"+ea.getEspecie()+"' ";
			 }
			 if(ea.getVariedad() != 0){
				 sql += " and ea.variedad  = '"+ea.getVariedad()+"' ";
			 }
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery(sql);
			 
			 while(rs.next()){
				 estimacionAnual ob = new estimacionAnual();
				 ob.setTemporada(rs.getInt("temporada"));
				 ob.setVersion(rs.getInt("version"));
				 ob.setProductor(rs.getString("productor"));
				 ob.setNproductor(rs.getString("nproductor"));
				 ob.setEspecie(rs.getString("especie"));
				 ob.setVariedad(rs.getInt("variedad"));
				 ob.setNvariedad(rs.getString("nvariedad"));
				 ob.setSemana(rs.getInt("semana"));
				 //ob.setExportacion(rs.getFloat("exportacion"));
				 ob.setCalibre(rs.getString("calibre"));
				 ob.setCategoria(rs.getString("categoria"));
				 ob.setLunes(rs.getFloat("lunes"));
				 ob.setMartes(rs.getFloat("martes"));
				 ob.setMiercoles(rs.getFloat("miercoles"));
				 ob.setJueves(rs.getFloat("jueves"));
				 ob.setViernes(rs.getFloat("viernes"));
				 ob.setSabado(rs.getFloat("sabado"));
				 ob.setDomingo(rs.getFloat("domingo"));
				 ob.setTotal(rs.getFloat("total"));
				 //ob.setValor(rs.getFloat("valorFinal"));
				 
				 //list.add(ob);
				 list.add(ob);
			 }
			 
			return list;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return list;
	}
	
}
