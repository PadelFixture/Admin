package lib.SADB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

//import org.apache.commons.codec.binary.StringUtils;

//import org.junit.experimental.categories.Categories;

import lib.classSA.CALIBRE;
import lib.classSA.CATEGORIA;
import lib.classSA.LIQUIDACION;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.RENDIMIENTO_GENERAL;
import lib.classSA.calibre_prd;
import lib.classSA.estado_fenologico;
import lib.classSA.estimacion_data_prd;
import lib.classSA.estimacion_prd_data;
import lib.classSA.estimacion_productiva;
import lib.classSA.parametros_estimacion;
import lib.classSA.respuesta;
import lib.db.ConnectionDB;
import lib.security.session;
import lib.struc.trabajadores;

public class Categoria {
	public static boolean ADD_Categoria(CATEGORIA c) throws Exception{
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			 sql = "INSERT INTO mantenedor_sa(categoria, descripcion, cod_campo, cod_especie, estado) VALUES('CATEGORIA',?,'1',?,'1')";
			 System.out.println(sql);
			 ps = db.conn.prepareStatement(sql);
			 ps.setString(1, c.getDescripcion());
			 ps.setInt(2, c.getCod_especie());
			 ps.execute();
			 return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());;
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}
		 return false;
	}
	public static boolean UPDATE_Categoria (CATEGORIA c) throws Exception {
		PreparedStatement ps = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql = " UPDATE mantenedor_sa set descripcion='" +c.getDescripcion()+ "', "
				+ " cod_especie='"+c.getCod_especie()+"' where codigo='" +c.getCodigo()+ "'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
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
	public static boolean UP_EstadoFenologico_Estado(estado_fenologico es) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE estado_fenologico set estado=0 where codigo='"+es.getCodigo()+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			System.out.println(ps);
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
	public static boolean UP_Categoria_Estado(CATEGORIA es) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE mantenedor_sa set estado=0 where codigo='"+es.getCodigo()+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
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
	public static boolean ADD_PARAMETRO_ESTIMACION(parametros_estimacion c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			if (c.getCodigo() == 0) {
				sql = "INSERT INTO parametros_estimacion(campo, especie, id, tipo, descripcion, formula) VALUES(?,?,?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, c.getCampo());
				ps.setString(2, c.getEspecie());
				ps.setString(3, c.getId());
				ps.setInt(4, c.getTipo());
				ps.setString(5, c.getDescripcion());
				ps.setString(6, c.getFormula());
			}else{
				sql = "UPDATE parametros_estimacion SET id = ?, tipo = ?, descripcion = ?, formula = ? WHERE codigo = ?";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, c.getId());
				ps.setInt(2, c.getTipo());
				ps.setString(3, c.getDescripcion());
				ps.setString(4, c.getFormula());
				ps.setInt(5, c.getCodigo());
			}
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			;
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<parametros_estimacion> GET_PARAMETRO_ESTIMACION(String campo, int especie) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<parametros_estimacion> data = new ArrayList<parametros_estimacion>();
		ConnectionDB db = new ConnectionDB();
		try{
		sql = "SELECT *FROM parametros_estimacion WHERE campo = '"+campo+"' AND especie = '"+especie+"'";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while (rs.next()) {
			parametros_estimacion e = new parametros_estimacion();
			e.setCodigo(rs.getInt("codigo"));
			e.setCampo(rs.getString("campo"));
			e.setEspecie(rs.getString("especie"));
			e.setId(rs.getString("id"));
			e.setTipo(rs.getInt("tipo"));
			e.setDescripcion(rs.getString("descripcion"));
			e.setFormula(rs.getString("formula"));
			data.add(e);
		}
		rs.close();
		ps.close();
		db.conn.close();
		}catch (SQLException e){
			System.out.println("Erro:" + e.getMessage());
		}catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	public static boolean DELETE_PARAMETRO_ESTIMACION(int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		try{
			sql = "DELETE FROM parametros_estimacion WHERE codigo = "+codigo+"";
			ps = db.conn.prepareStatement(sql);
			
			ps.execute();
			ps.close();
			return true;
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return false;
	}
	public static boolean ADD_ESTIMACION_PRODUCTIVA(estimacion_productiva c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			if (c.getCodigo_e() == 0) {
				sql = "INSERT INTO estimacion_productiva(campo, especie, cuartel, kep, ke, kxp, kx, ct, cth) VALUES(?,?,?,?,?,?,?,?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, c.getCampo());
				ps.setString(2, c.getEspecie());
				ps.setInt(3, c.getCuartel());
				ps.setFloat(4, c.getKep());
				ps.setFloat(5, c.getKe());
				ps.setFloat(6, c.getKxp());
				ps.setFloat(7, c.getKx());
				ps.setFloat(8, c.getCt());
				ps.setFloat(9, c.getCth());
				ps.execute();
				ADD_ESTIMACION_PRD_DATA(c.getEstimacion_prd_data());
			}else{
				sql = "UPDATE estimacion_productiva SET kep = ?, ke = ?, kxp = ?, kx = ?, ct = ?, cth = ? WHERE codigo = ?";
				ps = db.conn.prepareStatement(sql);
				ps.setFloat(1, c.getKep());
				ps.setFloat(2, c.getKe());
				ps.setFloat(3, c.getKxp());
				ps.setFloat(4, c.getKx());
				ps.setFloat(5, c.getCt());
				ps.setFloat(6, c.getCth());
				ps.setInt(7, c.getCodigo_e());
				ps.execute();
				UPD_ESTIMACION_PRD_DATA(c.getEstimacion_prd_data());
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			;
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<estimacion_productiva> GET_ESTIMACION_PRODUCTIVA(String campo, int especie, int cuartel) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<estimacion_productiva> data = new ArrayList<estimacion_productiva>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT pe.*, e.*, e.codigo AS codigo_e, ep.valor FROM parametros_estimacion pe ";
			sql += "left join estimacion_productiva e on (pe.campo = e.campo AND pe.especie = e.especie) ";
			sql += "RIGHT JOIN estimacion_prd_data ep ON(ep.idEstimacion = e.codigo AND pe.id = ep.parametro) ";
			sql += "WHERE pe.campo = '"+campo+"' AND pe.especie = '"+especie+"' AND e.cuartel = "+cuartel;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				estimacion_productiva e = new estimacion_productiva();
				e.setCodigo(rs.getInt("e.codigo"));
				e.setCampo(rs.getString("campo"));
				e.setEspecie(rs.getString("especie"));
				e.setId(rs.getString("id"));
				e.setTipo(rs.getInt("tipo"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setFormula(rs.getString("formula"));
				
				e.setCodigo_e(rs.getInt("codigo_e"));
				e.setCuartel(rs.getInt("cuartel"));
				e.setKep(rs.getFloat("kep"));
				e.setKe(rs.getFloat("ke"));
				e.setKxp(rs.getFloat("kxp"));
				e.setCt(rs.getFloat("ct"));
				e.setCth(rs.getFloat("cth"));
				e.setValor(rs.getFloat("valor"));
				data.add(e);
			}
			System.out.println(sql);
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException e){
			System.out.println("Error dsfs:" + e.getMessage());
		}catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	public static boolean ADD_ESTIMACION_PRD_DATA(ArrayList<estimacion_prd_data> c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			for(estimacion_prd_data e: c){
				sql = "INSERT INTO estimacion_prd_data(idEstimacion, parametro, valor) VALUES((SELECT if(MAX(codigo) is null, 0, MAX(codigo)) FROM estimacion_productiva),?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, e.getParametro());
				ps.setFloat(2, e.getValor());
				ps.execute();
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			;
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean UPD_ESTIMACION_PRD_DATA(ArrayList<estimacion_prd_data> c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			for(estimacion_prd_data e: c){
				System.out.println(e.getParametro());
				sql = "UPDATE estimacion_prd_data SET valor = ? WHERE idEstimacion = ? AND parametro = ?";
				ps = db.conn.prepareStatement(sql);
				ps.setFloat(1, e.getValor());
				ps.setInt(2, e.getIdEstimacion());
				ps.setString(3, e.getParametro());
				ps.execute();
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			;
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<String> GET_SEMANAS_21 ()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			sql += 	"SELECT ";
			sql += 		"CONCAT('Semana ',WEEK(CURDATE(), 3)) AS semana_1, ";
			sql += 		"CONCAT('Semana ',WEEK(ADDDATE(CURDATE(), INTERVAL 7 DAY),3)) AS semana_2, ";
			sql += 		"CONCAT('Semana ',WEEK(ADDDATE(CURDATE(), INTERVAL 14 DAY), 3)) AS semana_3;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql); 
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			for (int i = 1; i <= count; i++) {
				titulos.add(md.getColumnName(i));
			}
			while(rs.next()){
				for(String t: titulos){
					String horas = rs.getString(t);
					if(horas == null){
						horas = " ";
					}
					data.add(horas);
				}
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
	public static respuesta ADD_ESTIMACION_21PRD(ConnectionDB db, estimacion_data_prd r, HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		respuesta res = new respuesta();
		res.setEstado(false);
		res.setObjeto(r);
		session ses = new session(httpSession);
		String sql = "";
		try {
			sql += 	"CALL insertEstimacion21(?,?,?,?,?,?,?,?,?,?,?,?,?);";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, r.getCampo());
			ps.setInt(2, r.getEspecie());
			ps.setInt(3, r.getVariedad());
			ps.setInt(4, r.getSemana());
			ps.setFloat(5, r.getLunes());
			ps.setFloat(6, r.getMartes());
			ps.setFloat(7, r.getMiercoles());
			ps.setFloat(8, r.getJueves());
			ps.setFloat(9, r.getViernes());
			ps.setFloat(10, r.getSabado());
			ps.setFloat(11, r.getDomingo());
			ps.setFloat(12, r.getTotal());
			ps.setInt(13, ses.getIdUser());
			ps.execute();
			res.setEstado(true);
			res.setObjeto(r);
			return res;
		} catch (SQLException e) {
			System.out.println("Error  fghfgh:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ytyrtyr:" + e.getMessage());
		} finally {
//			ps.close();
			db.close();
		}
		return res;
	}
	public static ArrayList<String[]> GET_ESTIMACION_PRD21 (String campo, int especie, int variedad)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		ArrayList<String[]> data = new ArrayList<>();
		try {
			sql += 	"SELECT ";
			sql += 		"lunes,martes,miercoles,jueves,viernes,sabado,domingo, total ";
			sql += 	"FROM ";
			sql += 		"estimacion_prd_21 ";
			sql += 	"WHERE ";
			sql += 		"semana IN (WEEK(CURDATE(), 3) , ";
			sql += 			"WEEK(ADDDATE(CURDATE(), INTERVAL 7 DAY), 3), ";
			sql += 			"WEEK(ADDDATE(CURDATE(), INTERVAL 14 DAY),3)) ";
			sql += 		"AND campo = '"+campo+"' ";
			sql += 		"AND especie = "+especie+" ";
			sql += 		"AND variedad = "+variedad+" ";
			sql += 		"AND ano = DATE_FORMAT(CURDATE(), '%Y') ";
			sql += 	"ORDER BY semana ASC;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql); 
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			for (int i = 1; i <= count; i++) {
				titulos.add(md.getColumnName(i));
			}
			String datos = "";
			int cFormula = 0;
			int countRow = 0;
			while(rs.next()){
				for(String t: titulos){
					cFormula++;
					String horas = rs.getString(t);
					if(horas == null){
						horas = " ";
					}
//					if(cFormula == 8){horas = "=SUM(A1+B1+C1+D1+E1+F1+G1)";}
//					if(cFormula == 16){horas = "=SUM(I1+J1+K1+L1+M1+N1+O1)";}
//					if(cFormula == 24){horas = "=SUM(Q1+R1+S1+T1+U1+V1+W1)";}
					datos += horas+",";
				}
				countRow++;
			}
			if(countRow < 3){
				for(int x = countRow+1; x <= 3;x++){
					for(int i = 0; i <= 7;i++){
						cFormula++;
						String valor = "0";
//						if(cFormula == 8){valor = "=SUM(A1+B1+C1+D1+E1+F1+G1)";}
//						if(cFormula == 16){valor = "=SUM(I1+J1+K1+L1+M1+N1+O1)";}
//						if(cFormula == 24){valor = "=SUM(Q1+R1+S1+T1+U1+V1+W1)";}
						datos += valor+",";
					}
				}
			}
			String [] dataAux = datos.split(",");
			data.add(dataAux);
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return data;
	}	
	public static ArrayList<calibre_prd>  GET_CALIBRE_PRD21(int especie) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		ArrayList<calibre_prd> data = new ArrayList<calibre_prd>();
		try {
			String sql = "";
			sql += 	"SELECT ";
			sql += 		"c.*, cs.id, DATE_FORMAT(CURDATE(), '%Y') as ano, WEEK(CURDATE(), 3) as semana,c.id AS idCalibre, cs.valor, n.n ";
			sql += 	"FROM ";
			sql += 		"calibre_prd c ";
			sql += 		"LEFT JOIN (SELECT *FROM calibre_semanaprd WHERE ";
			sql += 			"semana IN (WEEK(CURDATE(), 3))";
			sql += 		"AND ano IN (DATE_FORMAT(CURDATE(), '%Y') , ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 7 DAY), '%Y'), ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 14 DAY),'%Y'))) cs ON(cs.idCalibre = c.id) ";
			sql += 		"LEFT JOIN(SELECT COUNT(*) AS n, especie FROM calibre_prd WHERE especie = "+especie+") n ON(n.especie = c.especie)";
			sql += 	"WHERE ";
			sql += 		"c.especie = "+especie+" ";
			sql += 	"UNION ALL ";
			sql += 	"SELECT ";
			sql += 		"c.*, cs.id, DATE_FORMAT(CURDATE(), '%Y') as ano, WEEK(ADDDATE(CURDATE(), INTERVAL 7 DAY),3) as semana,c.id AS idCalibre, cs.valor, n.n ";
			sql += 	"FROM ";
			sql += 		"calibre_prd c ";
			sql += 		"LEFT JOIN (SELECT *FROM calibre_semanaprd WHERE ";
			sql += 			"semana IN (WEEK(ADDDATE(CURDATE(), INTERVAL 7 DAY),3)) ";
			sql += 		"AND ano IN (DATE_FORMAT(CURDATE(), '%Y') , ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 7 DAY), '%Y'), ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 14 DAY),'%Y'))) cs ON(cs.idCalibre = c.id) ";
			sql += 		"LEFT JOIN(SELECT COUNT(*) AS n, especie FROM calibre_prd WHERE especie = "+especie+") n ON(n.especie = c.especie)";
			sql += 	"WHERE ";
			sql += 		"c.especie = "+especie+" ";
			sql += 	"UNION ALL ";
			sql += 	"SELECT ";
			sql += 		"c.*, cs.id, DATE_FORMAT(CURDATE(), '%Y') as ano, WEEK(ADDDATE(CURDATE(), INTERVAL 14 DAY),3) as semana,c.id AS idCalibre, cs.valor, n.n ";
			sql += 	"FROM ";
			sql += 		"calibre_prd c ";
			sql += 		"LEFT JOIN (SELECT *FROM calibre_semanaprd WHERE ";
			sql += 			"semana IN (WEEK(ADDDATE(CURDATE(), INTERVAL 14 DAY),3)) ";
			sql += 		"AND ano IN (DATE_FORMAT(CURDATE(), '%Y') , ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 7 DAY), '%Y'), ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 14 DAY),'%Y'))) cs ON(cs.idCalibre = c.id) ";
			sql += 		"LEFT JOIN(SELECT COUNT(*) AS n, especie FROM calibre_prd WHERE especie = "+especie+") n ON(n.especie = c.especie)";
			sql += 	"WHERE ";
			sql += 		"c.especie = "+especie+" ";
			sql += 	"ORDER BY 6,1;";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			int countRow = 0;
			int semana = 0;
			int countF = 0;
			int n = 0;
			ArrayList<String> desc = new ArrayList<String>();
			ArrayList<String> idC = new ArrayList<String>();
			ArrayList<String> esp = new ArrayList<String>();
			int ano = 0;
			while(rs.next()){
				calibre_prd e = new calibre_prd();
				int idCal = rs.getInt("idCalibre");
				e.setId(rs.getInt("id"));
				e.setEspecie(rs.getInt("especie"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setIdCs(rs.getInt("id"));
				e.setIdCalibre(rs.getInt("idCalibre"));
				e.setValor(rs.getFloat("valor"));
				desc.add(rs.getString("descripcion"));
				esp.add(rs.getString("especie"));
				semana = rs.getInt("semana");
				ano = rs.getInt("ano");
				if(idCal == 0){
					idCal = rs.getInt("id");
					e.setIdCalibre(rs.getInt("id"));
				}
				idC.add(String.valueOf(idCal));
				if(semana == 0){
					semana = rs.getInt("s");
				}
				if(ano == 0){
					ano = rs.getInt("a");
				}
				e.setAno(ano);
				e.setSemana(semana);
				countF++;
				n = rs.getInt("n");
				if(countF == rs.getInt("n")){
					countRow++;
					countF = 0;
				}
				data.add(e);
				
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return data;
	}
	public static respuesta ADD_CALIBREEST_PRD21(ConnectionDB db, calibre_prd r, HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		respuesta res = new respuesta();
		res.setEstado(false);
		res.setObjeto(r);
		session ses = new session(httpSession);
		String sql = "";
		try {
			sql += 	"CALL sa_insertCalibreEst_prd21(?,?,?,?);";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, r.getAno());
			ps.setInt(2, r.getSemana());
			ps.setInt(3, r.getIdCalibre());
			ps.setFloat(4, r.getValor());
			ps.execute();
			res.setEstado(true);
			res.setObjeto(r);
			return res;
		} catch (SQLException e) {
			System.out.println("Error  fghfgh:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ytyrtyr:" + e.getMessage());
		} finally {
//			ps.close();
			db.close();
		}
		return res;
	}
	public static ArrayList<calibre_prd>  GET_CATEGORIA_PRD21(int especie) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		ArrayList<calibre_prd> data = new ArrayList<calibre_prd>();
		try {
			String sql = "";
			sql += 	"SELECT ";
			sql += 		"c.*, cs.id, DATE_FORMAT(CURDATE(), '%Y') as ano, WEEK(CURDATE(), 3) as semana,c.id AS idCategoria, cs.valor, n.n ";
			sql += 	"FROM ";
			sql += 		"categoria_prd c ";
			sql += 		"LEFT JOIN (SELECT *FROM categoria_semanaprd WHERE ";
			sql += 			"semana IN (WEEK(CURDATE(), 3))";
			sql += 		"AND ano IN (DATE_FORMAT(CURDATE(), '%Y') , ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 7 DAY), '%Y'), ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 14 DAY),'%Y'))) cs ON(cs.idCategoria = c.id) ";
			sql += 		"LEFT JOIN(SELECT COUNT(*) AS n, especie FROM categoria_prd WHERE especie = "+especie+") n ON(n.especie = c.especie)";
			sql += 	"WHERE ";
			sql += 		"c.especie = "+especie+" ";
			sql += 	"UNION ALL ";
			sql += 	"SELECT ";
			sql += 		"c.*, cs.id, DATE_FORMAT(CURDATE(), '%Y') as ano, WEEK(ADDDATE(CURDATE(), INTERVAL 7 DAY),3) as semana,c.id AS idCategoria, cs.valor, n.n ";
			sql += 	"FROM ";
			sql += 		"categoria_prd c ";
			sql += 		"LEFT JOIN (SELECT *FROM categoria_semanaprd WHERE ";
			sql += 			"semana IN (WEEK(ADDDATE(CURDATE(), INTERVAL 7 DAY),3)) ";
			sql += 		"AND ano IN (DATE_FORMAT(CURDATE(), '%Y') , ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 7 DAY), '%Y'), ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 14 DAY),'%Y'))) cs ON(cs.idCategoria = c.id) ";
			sql += 		"LEFT JOIN(SELECT COUNT(*) AS n, especie FROM categoria_prd WHERE especie = "+especie+") n ON(n.especie = c.especie)";
			sql += 	"WHERE ";
			sql += 		"c.especie = "+especie+" ";
			sql += 	"UNION ALL ";
			sql += 	"SELECT ";
			sql += 		"c.*, cs.id, DATE_FORMAT(CURDATE(), '%Y') as ano, WEEK(ADDDATE(CURDATE(), INTERVAL 14 DAY),3) as semana,c.id AS idCategoria, cs.valor, n.n ";
			sql += 	"FROM ";
			sql += 		"categoria_prd c ";
			sql += 		"LEFT JOIN (SELECT *FROM categoria_semanaprd WHERE ";
			sql += 			"semana IN (WEEK(ADDDATE(CURDATE(), INTERVAL 14 DAY),3)) ";
			sql += 		"AND ano IN (DATE_FORMAT(CURDATE(), '%Y') , ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 7 DAY), '%Y'), ";
			sql += 			"DATE_FORMAT(ADDDATE(CURDATE(), INTERVAL 14 DAY),'%Y'))) cs ON(cs.idCategoria = c.id) ";
			sql += 		"LEFT JOIN(SELECT COUNT(*) AS n, especie FROM categoria_prd WHERE especie = "+especie+") n ON(n.especie = c.especie)";
			sql += 	"WHERE ";
			sql += 		"c.especie = "+especie+" ";
			sql += 	"ORDER BY 6,1;";
//			sql += 	"ORDER BY cs.semana,1;";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			int countRow = 0;
			int semana = 0;
			int countF = 0;
			ArrayList<String> desc = new ArrayList<String>();
			ArrayList<String> idC = new ArrayList<String>();
			ArrayList<String> esp = new ArrayList<String>();
			int ano = 0;
			while(rs.next()){
				calibre_prd e = new calibre_prd();
				int idCal = rs.getInt("idCategoria");
				e.setId(rs.getInt("id"));
				e.setEspecie(rs.getInt("especie"));
				e.setDescripcion(rs.getString("descripcion"));
				e.setIdCs(rs.getInt("id"));
				e.setIdCalibre(rs.getInt("idCategoria"));
				e.setValor(rs.getFloat("valor"));
				desc.add(rs.getString("descripcion"));
				esp.add(rs.getString("especie"));
				semana = rs.getInt("semana");
				ano = rs.getInt("ano");
				if(idCal == 0){
					idCal = rs.getInt("id");
					e.setIdCalibre(rs.getInt("id"));
				}
				idC.add(String.valueOf(idCal));
				if(semana == 0){
					semana = rs.getInt("s");
				}
				if(ano == 0){
					ano = rs.getInt("a");
				}
				e.setAno(ano);
				e.setSemana(semana);
				countF++;
				if(countF == rs.getInt("n")){
					countRow++;
					countF = 0;
				}
				data.add(e);
				
			}
		} catch (SQLException e) {
			System.out.println("Error: GET_CATEGORIA_PRD21 SQL" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error: GET_CATEGORIA_PRD21" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return data;
	}
	public static respuesta ADD_CATEGORIAEST_PRD21(ConnectionDB db, calibre_prd r, HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		respuesta res = new respuesta();
		res.setEstado(false);
		res.setObjeto(r);
		session ses = new session(httpSession);
		String sql = "";
		try {
			sql += 	"CALL sa_insertCategoria_prd21(?,?,?,?);";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, r.getAno());
			ps.setInt(2, r.getSemana());
			ps.setInt(3, r.getIdCalibre());
			ps.setFloat(4, r.getValor());
			ps.execute();
			res.setEstado(true);
			res.setObjeto(r);
			return res;
		} catch (SQLException e) {
			System.out.println("Error  fghfgh:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ytyrtyr:" + e.getMessage());
		} finally {
//			ps.close();
			db.close();
		}
		return res;
	}
	public static ArrayList<calibre_prd>  GET_CALIBRE_ESPECIE(int especie) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		ArrayList<calibre_prd> data = new ArrayList<calibre_prd>();
		try {
			String sql = "";
			sql += 	"SELECT ";
			sql += 		"* ";
			sql += 	"FROM ";
			sql += 		"calibre_prd ";
			sql += 	"WHERE ";
			sql += 		"especie = "+especie+" ";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			System.out.println(1);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				calibre_prd e = new calibre_prd();
				e.setId(rs.getInt("id"));
				e.setEspecie(rs.getInt("especie"));
				e.setDescripcion(rs.getString("descripcion"));
				data.add(e);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return data;
	}
	public static boolean ADD_CALIBRE_CATEGORIA(calibre_prd c) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			if (c.getId() == 1) {
				sql = "INSERT INTO calibre_prd(especie, descripcion) VALUES(?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, c.getEspecie());
				ps.setString(2, c.getDescripcion());
				ps.execute();
			}else{
				sql = "INSERT INTO categoria_prd(especie, descripcion) VALUES(?,?)";
				ps = db.conn.prepareStatement(sql);
				ps.setInt(1, c.getEspecie());
				ps.setString(2, c.getDescripcion());
				ps.execute();
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			;
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<calibre_prd>  GET_CATEGORIA_ESPECIE(int especie) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		ArrayList<calibre_prd> data = new ArrayList<calibre_prd>();
		try {
			String sql = "";
			sql += 	"SELECT ";
			sql += 		"* ";
			sql += 	"FROM ";
			sql += 		"categoria_prd ";
			sql += 	"WHERE ";
			sql += 		"especie = "+especie+" ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				calibre_prd e = new calibre_prd();
				e.setId(rs.getInt("id"));
				e.setEspecie(rs.getInt("especie"));
				e.setDescripcion(rs.getString("descripcion"));
				data.add(e);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return data;
	}
	public static boolean UPD_CALIBRE_CATEGORIA(calibre_prd c) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		String sql = "";
		try {
			if (c.getIdCs() == 1) {
				sql = "UPDATE calibre_prd SET descripcion = ? WHERE id = ?";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, c.getDescripcion());
				ps.setInt(2, c.getId());
				ps.execute();
			}else{
				sql = "UPDATE categoria_prd SET descripcion = ? WHERE id = ?";
				ps = db.conn.prepareStatement(sql);
				ps.setString(1, c.getDescripcion());
				ps.setInt(2, c.getId());
				ps.execute();
			}
			return true;
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static ArrayList<String[]> GET_REPORTE_ESTIMACIONPRD (String campo, int variedad)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		ArrayList<String[]> data = new ArrayList<>();
		try {
			sql += 	"SELECT ";
			sql += 		"e.especie, v.variedad, CONCAT(c.codigo_cuartel, ' ', c.nombre) AS cuartel, ";
			sql += 		"t.KGHAS AS KGHAS,t.kep, t.ke, t.kxp, t.kx, t.ct, t.cth ";
			sql += 	"FROM ";
			sql += 		"cuartel c ";
			sql += 		"LEFT JOIN variedad v ON (v.codigo = c.variedad) ";
			sql += 		"LEFT JOIN especie e ON (e.codigo = v.especie) ";
			sql += 		"LEFT JOIN(SELECT ";
			sql += 				"d.valor AS KGHAS,e.kep, e.ke, e.kxp, e.kx, e.ct, e.cth,e.cuartel ";
			sql += 			"FROM ";
			sql += 				"estimacion_productiva e ";
			sql += 				"LEFT JOIN campo cam ON (cam.campo = e.campo) ";
			sql += 				"LEFT JOIN estimacion_prd_data d ON (e.codigo = d.idEstimacion AND d.parametro = 'KGHAS') ";
			sql += 			"WHERE ";
			sql += 				"e.cuartel IN (SELECT codigo FROM cuartel WHERE variedad = "+variedad+") ";
			sql += 				"AND cam.campo = '"+campo+"') t ON (t.cuartel = c.codigo)";
			sql += 	"WHERE ";
			sql += 		"c.variedad = "+variedad+" ";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql); 
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			for (int i = 1; i <= count; i++) {
				titulos.add(md.getColumnName(i));
			}
			while(rs.next()){
				String datos = "";
				for(String t: titulos){
					String horas = rs.getString(t);
					if(horas == null){
						horas = " ";
					}
					datos += horas+";";
				}
				String [] dataAux = datos.split(";");
				data.add(dataAux);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return data;
	}	
}
