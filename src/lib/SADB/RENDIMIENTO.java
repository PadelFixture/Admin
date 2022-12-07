package lib.SADB;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import lib.classSA.BLOQUEO_LABOR;
import lib.classSA.CAMPO;
import lib.classSA.CUADRATURA_HORA;
import lib.classSA.CUADRILLA;
import lib.classSA.FILTRO_CUADRAR_HR;
import lib.classSA.LABOR;
import lib.classSA.LIBRO_CAMPO;
import lib.classSA.LIQUIDACION;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.RENDIMIENTO_GENERAL;
import lib.classSA.SESIONVAR;
import lib.classSA.Trabajador;
import lib.classSA.TrabajadoresAgro;
import lib.classSA.asiento_contable;
import lib.classSA.calificacion_rendimiento;
import lib.classSA.cierre_mensual;
import lib.classSA.detalle_rendimiento;
import lib.classSA.estado_rendimiento;
import lib.classSA.faena;
import lib.classSA.incidencia;
import lib.classSA.recorrido;
import lib.classSA.respuesta;
import lib.classSW.CentralizacionDetalleTmp;
import lib.classSW.Documentos;
import lib.db.ConnectionDB;
import lib.db.simpleagroDB;
import lib.excelSA.excelOrdenJson;
import lib.excelSA.listaCuarteles;
import lib.excelSA.listaMaq;
import lib.excelSA.listaMateriales;
import lib.excelSA.listaVariedad;
import lib.security.session;
import lib.sesionSA.SESION;
import lib.struc.trabajadores;
import wordCreator.utils;

public class RENDIMIENTO {
	public static ArrayList<RENDIMIENTO_DIARIO> GET_RENDIMIENTO_DIARIO_TRABAJADOR(int id) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_DIARIO> lista = new ArrayList<RENDIMIENTO_DIARIO>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM rendimiento_diario WHERE ";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				RENDIMIENTO_DIARIO ob = new RENDIMIENTO_DIARIO();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setTrabajador(rs.getInt("trabajador"));
				ob.setBase_piso_hora(rs.getInt("base_piso_hora"));
				ob.setSubsidio(rs.getInt("subsidio"));
				ob.setLabor(rs.getInt("labor"));
				ob.setValor(rs.getInt("valor"));
				ob.setTipo_trato(rs.getInt("tipo_trato"));
				ob.setRendimiento(rs.getInt("rendimiento"));
				ob.setHoras_trabajadas(rs.getInt("horas_trabajadas"));
				ob.setHoras_extras(rs.getInt("horas_extras"));
				ob.setBono1(rs.getInt("bono1"));
				ob.setBono2(rs.getInt("bono2"));
				ob.setValor_liquido(rs.getInt("valor_liquido"));
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setImplemento(rs.getInt("implemento"));
				ob.setEstado(rs.getInt("estado"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	public static boolean INSERT_CUADRILLA_UPDATE(int rut, int codigo_rg) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		String sql = "";
		try {
			sql = "INSERT INTO cuadrilla_trabajador (codigo_cuadrilla, rut_trabajador, asistencia)";
			sql += "SELECT *FROM (SELECT (SELECT c.codigo FROM cuadrilla c INNER JOIN rendimiento_general rg ON(c.codigo = rg.codigo_cuadrilla) WHERE rg.codigo_rg = "+codigo_rg+"), )";
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
	public static respuesta DEL_RENDIMIENTO_DIARIO(ConnectionDB db, RENDIMIENTO_DIARIO r) throws Exception{
		respuesta res = new respuesta();
		res.setEstado(false);
		res.setObjeto(r);
		try{
			CallableStatement cStmt = db.conn.prepareCall("{call sa_deleteRendimiento(?, ?, ?)}");
			cStmt.setString(1, r.getRut());
			cStmt.setInt(2, r.getCodigo_rg());
			cStmt.setInt(3, r.getCodigo());
			cStmt.execute();
			db.conn.close();
			res.setEstado(true);
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return res;
	}
	public static boolean ADD_NOT_EXIST(ArrayList<RENDIMIENTO_DIARIO> rd) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		String sql = "";
		try {
			for(RENDIMIENTO_DIARIO r: rd){
				sql = "INSERT INTO cuadrilla_trabajador (codigo_cuadrilla, rut_trabajador, asistencia) ";
				sql += "SELECT * FROM (SELECT ";
				sql += "(SELECT codigo_cuadrilla FROM rendimiento_general WHERE codigo_rg = "+r.getCodigo_rg()+"), ";
				sql += "(SELECT rut FROM trabajadores WHERE id = "+r.getTrabajador()+"), 5) AS tmp ";
				sql += "WHERE NOT EXISTS (";
				sql += "SELECT rut_trabajador FROM cuadrilla_trabajador WHERE codigo_cuadrilla = (SELECT codigo_cuadrilla FROM rendimiento_general WHERE codigo_rg = "+r.getCodigo_rg()+") AND rut_trabajador = (SELECT rut FROM trabajadores WHERE id = "+r.getTrabajador()+") ";
				sql += ") LIMIT 1;";
				ps = db.conn.prepareStatement(sql);
				ps.execute();
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Error  ADD_NOT:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ADD:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean ADD_RENDIMIENTO_INDIVIDUAL(ConnectionDB db, RENDIMIENTO_GENERAL rg) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		try {
			sql = "INSERT INTO rendimiento_general (fecha, especie, variedad, cuartel, faena, labor, horas, tipo_pago, codigo_cuadrilla, codigo_supervisor,estado)";
			sql += "VALUES (?,?,?,?,?,?,"+String.valueOf(rg.getHoras())+",?,0,?,3)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, rg.getFecha());
			ps.setInt(2, rg.getEspecie());
			ps.setInt(3, rg.getVariedad());
			ps.setInt(4, rg.getCuartel());
			ps.setInt(5, rg.getFaena());
			ps.setInt(6, rg.getLabor());
			ps.setInt(7, rg.getTipo_pago());
			ps.setInt(8, rg.getCodigo_supervisor());
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
	public static boolean ADD_RENDIMIENTO_DIARIO_INDIVIDUAL(ConnectionDB db, RENDIMIENTO_DIARIO rd) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		try {
			sql = "INSERT INTO rendimiento_diario (trabajador, base_piso_hora, subsidio, labor, valor, tipo_trato, rendimiento, horas_trabajadas, horas_extras, bono1, bono2, valor_liquido, maquinaria, implemento, estado, codigo_rg) ";
			sql += "VALUES ("+rd.trabajador+",";
			sql += "'"+rd.base_piso_hora+"', ";
			sql += ""+rd.subsidio+", ";
			sql += ""+rd.labor+", ";
			sql += ""+rd.valor+", ";
			sql += ""+rd.tipo_trato+", ";
			sql += ""+rd.rendimiento+", ";
			sql += "'"+rd.horas_trabajadas+"', ";
			sql += "'"+rd.horas_extras+"', ";
			sql += ""+rd.bono1+", ";
			sql += ""+rd.bono2+", ";
			sql += ""+rd.valor_liquido+", ";
			sql += ""+rd.maquinaria+", ";
			sql += ""+rd.implemento+", ";
			sql += ""+rd.estado+", ";
			sql += "(SELECT MAX(codigo_rg) FROM rendimiento_general)); ";
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
//	UPDATE
	public static boolean UPDATE_RENDIMIENTO_DIARIO(RENDIMIENTO_DIARIO rd) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE rendimiento_diario set codigo='" +rd.getCodigo()+ "', trabajador='" +rd.getTrabajador()+ "', base_piso_hora='" +rd.getBase_piso_hora()
				+ "', subsidio='" +rd.getSubsidio()+ "', labor='" +rd.getLabor()+ "', valor='" +rd.getValor()+ "', tipo_trato='" +rd.getTipo_trato()
				+ "', rendimiento='" +rd.getRendimiento()+ "', horas_trabajadas='" +rd.getHoras_trabajadas()+ "', horas_extras='" +rd.getHoras_extras()
				+ "', bono1='" +rd.getBono1()+"', bono2='"+rd.getBono2()+"', valor_liquido='" +rd.getValor_liquido()+ "', maquinaria='" +rd.getMaquinaria()
				+ "', implemento='" +rd.getImplemento()+ "', estado= '" +rd.getEstado()+ "', codigo_rg='" +rd.getCodigo_rg()+ "'";
			ps = db.conn.prepareStatement(sql);
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
	public static ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTO_GENERAL_FECHA(String fecha) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_GENERAL> lista = new ArrayList<RENDIMIENTO_GENERAL>();
		ConnectionDB db = new ConnectionDB();
		try{
		sql = "SELECT rg.* FROM cuadrilla c RIGHT JOIN rendimiento_general rg ON(c.codigo = rg.codigo_cuadrilla) WHERE c.codigo = "+fecha;
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while (rs.next()) {
			RENDIMIENTO_GENERAL ob = new RENDIMIENTO_GENERAL();
			ob.setCodigo(rs.getInt("codigo"));
			ob.setFecha(rs.getString("fecha"));
			ob.setEspecie(rs.getInt("especie"));
			ob.setVariedad(rs.getInt("variedad"));
			ob.setCuartel(rs.getInt("cuartel"));
			ob.setFaena(rs.getInt("faena"));
			ob.setLabor(rs.getInt("labor"));
			ob.setHoras(rs.getInt("horas"));
			ob.setTipo_pago(rs.getInt("tipo_pago"));
			ob.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla_trabajador"));
			ob.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
			lista.add(ob);
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
		return lista;
	}
	public static ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTO_GENERAL_CUADRILLA(	int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_GENERAL> lista = new ArrayList<RENDIMIENTO_GENERAL>();
		ConnectionDB db = new ConnectionDB();
		try{
		sql = "SELECT rg.* FROM cuadrilla c RIGHT JOIN rendimiento_general rg ON(c.codigo = rg.codigo_cuadrilla) WHERE c.codigo = "+codigo;
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while (rs.next()) {
			RENDIMIENTO_GENERAL ob = new RENDIMIENTO_GENERAL();
			ob.setCodigo(rs.getInt("codigo"));
			ob.setFecha(rs.getString("fecha"));
			ob.setEspecie(rs.getInt("especie"));
			ob.setVariedad(rs.getInt("variedad"));
			ob.setCuartel(rs.getInt("cuartel"));
			ob.setFaena(rs.getInt("faena"));
			ob.setLabor(rs.getInt("labor"));
			ob.setHoras(rs.getInt("horas"));
			ob.setTipo_pago(rs.getInt("tipo_pago"));
			ob.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla_trabajador"));
			ob.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
			lista.add(ob);
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
		return lista;
	}
	public static ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTO_GENERAL(String fecha, int id, String cuartel, String tipo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_GENERAL> lista = new ArrayList<RENDIMIENTO_GENERAL>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM rendimiento_general WHERE fecha = '"+fecha+"' AND codigo_supervisor = "+id+" AND estado != 7 ";
			sql += "AND (cuartel = '"+cuartel+"' OR ceco = '"+cuartel+"' OR ordenco = '"+cuartel+"')";
			if(tipo.equals("PLANTA")){
				sql += " AND contratista IS NULL";
			}else{
				sql += " AND contratista IS NOT NULL";
			}
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				RENDIMIENTO_GENERAL ob = new RENDIMIENTO_GENERAL();
				ob.setCodigo(rs.getInt("codigo_rg"));
				ob.setFecha(rs.getString("fecha"));
				ob.setEspecie(rs.getInt("especie"));
				ob.setVariedad(rs.getInt("variedad"));
				ob.setCuartel(rs.getInt("cuartel"));
				ob.setFaena(rs.getInt("faena"));
				ob.setLabor(rs.getInt("labor"));
				ob.setHoras(rs.getInt("horas"));
				ob.setTipo_pago(rs.getInt("tipo_pago"));
				ob.setValor(rs.getInt("valor"));
				ob.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
				ob.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
				ob.setContratista(rs.getString("contratista"));
				ob.setMacro(rs.getString("macro"));
				ob.setCeco(rs.getString("ceco"));
				ob.setOrdenco(rs.getString("ordenco"));
				lista.add(ob);
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
		return lista;
	}
	public static int r_folio(String sql) throws Exception{
		int id = 0;
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		try {
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				id = rs.getInt("folio");
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return id;
	}
	public static boolean ADD_RENDIMIENTO_GENERAL(RENDIMIENTO_GENERAL rg) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			int folio = r_folio("SELECT MAX(FOLIO+1) as folio FROM rendimiento_general WHERE campo = '"+rg.getCampo()+"'");
			if(folio == 0){
				folio = 1;
			}
			System.out.println(rg.getHoras());
			sql = "INSERT INTO rendimiento_general (fecha, campo, especie, variedad, cuartel, faena, labor, horas, tipo_pago, valor, base_piso_dia, codigo_supervisor, estado, folio,contratista,ncontratista, ";
			sql += "macro, ceco, ordenco)";
			sql += "VALUES (?,?,?,?,?,?,?,"+String.valueOf(rg.getHoras())+",?,?,?,?,1,"+folio+",?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, rg.getFecha());
			ps.setString(2, rg.getCampo());
			ps.setInt(3, rg.getEspecie());
			ps.setInt(4, rg.getVariedad());
			ps.setInt(5, rg.getCuartel());
			ps.setInt(6, rg.getFaena());
			ps.setInt(7, rg.getLabor());
			ps.setInt(8, rg.getTipo_pago());
			ps.setInt(9, rg.getValor());
			ps.setInt(10, rg.getBase_piso_dia());
			ps.setInt(11, rg.getCodigo_supervisor());
			ps.setString(12, rg.getContratista());
			ps.setString(13, rg.getNcontratista());
			ps.setString(14, rg.getMacro());
			ps.setString(15, rg.getCeco());
			ps.setString(16, rg.getOrdenco());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
//	UPDATE
	public static boolean UPDATE_RENDIMIENTO_GENERAL (RENDIMIENTO_GENERAL rg)throws Exception{
		PreparedStatement ps  = null;
		 String sql = "";
		 ConnectionDB db = new ConnectionDB();
		 try {
			sql ="UPDATE rendimiento_general set fecha = ?, especie = ?, variedad = ?, cuartel = ?, faena = ?, labor = ?, horas = "+String.valueOf(rg.getHoras())+", tipo_pago = ?, codigo_cuadrilla = ?, codigo_supervisor = ?, where codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, rg.getFecha());
			ps.setInt(2, rg.getEspecie());
			ps.setInt(3, rg.getVariedad());
			ps.setInt(4, rg.getCuartel());
			ps.setInt(5, rg.getFaena());
			ps.setInt(6, rg.getLabor());
			ps.setInt(7, rg.getTipo_pago());
			ps.setInt(8, rg.getCodigo_cuadrilla());
			ps.setInt(9, rg.getCodigo_supervisor());
			ps.setInt(10, rg.getCodigo());
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
	public static ArrayList<estado_rendimiento> GETER () throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<estado_rendimiento> lista  = new ArrayList<estado_rendimiento>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM estado_rendimiento";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				estado_rendimiento ob = new estado_rendimiento();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setDescripcion(rs.getString("descripcion"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
//-------------FIN ESTADO_RENDIMIENTO------------------
			
			
			
			
			
//--------------CALIFICAR_RENDIMIENTO----------------
//			SELECT
	public static ArrayList<calificacion_rendimiento> GET_CALIFICAR_RENDIMIENTO ()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<calificacion_rendimiento> lista = new ArrayList<calificacion_rendimiento>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM calificacion_rendimiento";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
			calificacion_rendimiento ob = new calificacion_rendimiento();
			ob.setCodigo(rs.getInt("codigo"));
			ob.setTrabajado(rs.getInt("trabajado"));
			ob.setRendimiento(rs.getInt("rendimiento"));
			ob.setPromedio_cuadrilla(rs.getInt("promedio_cuadrilla"));
			ob.setCalificacion(rs.getInt("calificacion"));
			ob.setCaudrilla(rs.getInt("cuadrilla"));
			ob.setLabor(rs.getInt("labor"));
			lista.add(ob);
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
		return lista;
	}
//			INSERT
	public static boolean ADD_CALIFICAR_RENDIMIENTO (calificacion_rendimiento c)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO calificacion_rendimiento (trabajado, rendimiento, promedio_cuadrilla,"
					+ " calificacion, cuadrilla, labor)";
			sql += "VALUES (?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, c.getTrabajado());
			ps.setInt(2, c.getRendimiento());
			ps.setInt(3, c.getPromedio_cuadrilla());
			ps.setInt(4, c.getCalificacion());
			ps.setInt(5, c.getCaudrilla());
			ps.setInt(6, c.getLabor());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	
	public static ArrayList<RENDIMIENTO_DIARIO> GET_Listado(String fecha_desde, String fecha_hasta, 
			String campo, String especie, String variedad, String faena, 
			String labor, String trabajador, String tipo_trabajador,String contratista,String cuartel, int estado, int valor, HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_DIARIO> lista = new ArrayList<RENDIMIENTO_DIARIO>();
		ConnectionDB db = new ConnectionDB();
		try {
			System.out.println(campo);
			sql += 	"SELECT DISTINCT ";
			sql += 		"rd.*,t.rut,c.especie,e.especie AS nespecie,l.faena, ";
			sql += 		"CASE ";
			sql += 			"WHEN (rd.rd_contratista IS NULL) THEN 0 ";
			sql += 			"ELSE rd.rd_contratista ";
			sql += 		"END AS contratista, ";
			sql += 		"UPPER(CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre)) AS nTrabajador, ";
			sql += 		"UPPER(CONCAT(trs.apellidoPaterno, ' ', trs.apellidoMaterno, ' ', trs.nombre)) AS supervisor, ";
			sql += 		"CASE ";
			sql += 			"WHEN (rg.fecha IS NULL) THEN rd.fecha_i ";
			sql += 			"ELSE rg.fecha ";
			sql += 		"END fecha_rendimiento, ";
			sql += 		"UPPER(CONCAT(c.codigo_cuartel, ' ', c.nombre)) AS nvnombre, ";
			sql += 		"cam.descripcion AS campo, c.variedad, v.variedad AS nVariedad, f.faena AS nFaena,l.labor AS nLabor, er.descripcion AS nestado, ";
			sql += 		"CAST((rd.valor_rendimietno/rd.rendimiento) as signed) as valor_trato, ";
			sql += 		"et.descripcion AS etnia, ";
			sql += 		"vla.costo_empresa AS costo_empresa, ";
			sql += 		"IF(c.estado IS NULL, 2, c.estado) AS c_estado ";
			sql += 	"FROM rendimiento_diario rd ";
			sql += 		"LEFT JOIN rendimiento_general rg ON(rd.codigo_rg = rg.codigo_rg) ";
			sql += 		"LEFT JOIN trabajadores t ON(t.id = rd.trabajador) ";
			sql += 		"LEFT JOIN trabajadores trs ON(trs.id = rg.codigo_supervisor OR trs.id = rd.supervisor_i) ";
			sql += 		"LEFT JOIN (SELECT *FROM parametros WHERE codigo = 'ETNIA') et ON(et.llave = t.idEtnia) ";
			sql += 		"LEFT JOIN (SELECT *FROM vw_costoEmpresaAgro WHERE periodo >= DATE_FORMAT('"+fecha_desde+"', '%Y%m') and periodo <=  DATE_FORMAT('"+fecha_hasta+"', '%Y%m')) vla ON(rd.codigo = vla.codigo) ";
			sql += 		"LEFT JOIN estado_rendimiento er ON(er.codigo = rd.estado) ";
			sql += 		"LEFT JOIN cuartel c ON(c.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN especie e ON(e.codigo = c.especie) ";
			sql += 		"LEFT JOIN variedad v ON(v.codigo = c.variedad) ";
			sql += 		"LEFT JOIN campo cam ON(cam.campo = rd.campo_rd OR cam.campo = rg.campo) ";
			sql += 		"LEFT JOIN sector s ON (s.sector = c.sector) ";
			sql += 		"LEFT JOIN labor l ON(l.codigo = rd.labor) ";
			sql += 		"LEFT JOIN faena f ON(f.codigo = l.faena) ";
			sql += 	"WHERE ";
			sql += 		"(rg.fecha BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"' OR rd.fecha_i BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"')";
			sql += 		"AND (cam.campo IN ("+campo+")) ";
			sql += 		"AND (rg.especie IN ('"+especie+"') or '"+especie+"' = 0) ";
			sql += 		"AND (v.codigo IN ('"+variedad+"') or '"+variedad+"' = 0) ";
			sql += 		"AND (f.codigo IN ('"+faena+"') or '"+faena+"' = 0) ";
			sql += 		"AND (rd.labor IN ('"+labor+"') or '"+labor+"' = 0) ";
			sql += 		"AND (rd.trabajador IN ('"+trabajador+"') or '"+trabajador+"' = 0) ";
			sql += 		"AND (rd.cuartel IN ('"+cuartel+"') or '"+cuartel+"' = 0) ";
			sql += 		"AND (rd.rd_contratista IN ('"+contratista+"') or '"+contratista+"' = 0) ";
			sql += 		"AND (rd.estado IN ('"+estado+"') or '"+estado+"' = 0) ";
			if(tipo_trabajador.equals("1")) {
				sql += " AND (rd.rd_contratista = 0 or rd.rd_contratista = '' or rd.rd_contratista is null)";
			}
			if(tipo_trabajador.equals("2")) {
				sql += " AND (rd.rd_contratista != 0 and rd.rd_contratista != '' and rd.rd_contratista is not null)";
			}
			sql += " AND rd.estado != 7";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				RENDIMIENTO_DIARIO ob = new RENDIMIENTO_DIARIO();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setNombre(rs.getString("nTrabajador"));
				ob.setRut(rs.getString("rut"));
				ob.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				ob.setHoras_extras(rs.getFloat("horas_extras"));
				ob.setEspecie(rs.getInt("especie"));
				ob.setVariedad(rs.getInt("variedad"));
				ob.setFaena(rs.getInt("faena"));
				ob.setLabor(rs.getInt("labor"));
				ob.setValor(rs.getInt("valor"));
				ob.setValor_rendimiento(rs.getFloat("valor_rendimietno"));
				ob.setRendimiento(rs.getFloat("rendimiento"));
				ob.setTipo_pago(rs.getInt("tipo_trato"));
				ob.setValor_liquido(rs.getInt("valor_liquido"));
				ob.setBono1(rs.getInt("bono1"));
				ob.setBono2(rs.getInt("bono2"));
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setImplemento(rs.getInt("implemento"));
				ob.setFecha(rs.getString("fecha_rendimiento")); 
				ob.setNvnombre(rs.getString("nvnombre"));
				ob.setDescripcion(rs.getString("campo"));
				ob.setnVariedad(rs.getString("nVariedad"));
				ob.setnEspecie(rs.getString("nEspecie"));
				ob.setnFaena(rs.getString("nFaena"));
				ob.setnLabor(rs.getString("nLabor"));
				ob.setSupervisor(rs.getString("supervisor"));
				ob.setBase_piso_hora(rs.getInt("base_piso_hora"));
				ob.setIdContratista(rs.getString("contratista"));
				ob.setNestado(rs.getString("nestado"));
				ob.setValor_hx(rs.getFloat("valor_hx"));
				ob.setMonto_hx(rs.getFloat("monto_hx"));
				ob.setHx_dos(rs.getFloat("hx_dos"));
				ob.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				ob.setMacroco(rs.getString("macroco"));
				ob.setCeco(rs.getString("ceco"));
				ob.setOrdenco(rs.getString("ordenco"));
				ob.setRes_hx(rs.getInt("res_hx"));
				ob.setSubsidio(rs.getInt("subsidio"));
				ob.setCuartel(rs.getInt("rd.cuartel"));
				ob.setValor_trato(rs.getFloat("valor_trato"));
				ob.setCosto_empresa(rs.getInt("costo_empresa"));
				ob.setEtnia(rs.getString("etnia"));
				ob.setEstado(rs.getInt("c_estado"));
				ob.setN_personas(rs.getInt("rd.n_personas"));
				lista.add(ob);
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
		return lista;
	}
	
	public static ArrayList<Trabajador> GETTRABAJADOR() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<Trabajador> lista = new ArrayList<Trabajador>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = " SELECT idTrabajador,nombre, rut, apellidoPaterno, apellidoMaterno from trabajador";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Trabajador ob = new Trabajador();
				ob.setIdTrabajador(rs.getInt("idTrabajador"));
				ob.setNombre(rs.getString("nombre"));
				ob.setApellidoPaterno(rs.getString("apellidoPaterno"));
				ob.setApellidoMaterno(rs.getString("apellidoMaterno"));
				ob.setRut(rs.getString("rut"));
				lista.add(ob);
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
		return lista;
	}
	public static String GET_CUADRATURA_HORAS (String campo, String[] fechas, double horas)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONArray array = new JSONArray();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT DISTINCT ";
			sql += 		"tr.id,tr.codigo,tr.rut AS rut, ";
			sql += 		"UPPER(CONCAT(tr.apellidoPaterno,' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre, ";
			sql += 		"cm.descripcion AS campo, ";
			sql +=		"CASE(WEEKDAY(f.fecha)) ";
			sql +=			"WHEN(0)THEN 'Lunes' ";
			sql +=			"WHEN(1)THEN 'Martes' ";
			sql +=			"WHEN(2)THEN 'Miercoles' ";
			sql +=			"WHEN(3)THEN 'Jueves' ";
			sql +=			"WHEN(4)THEN 'Viernes' ";
			sql +=			"WHEN(5)THEN 'Sabado' ";
			sql +=			"WHEN(6)THEN 'Domingo' ";
			sql +=		"END AS dia, ";
			sql += 		"f.fecha, WEEKDAY(f.fecha) as n_dia, rd.horas, ha.horasFalta, TIME_FORMAT(rd.hora_desde, '%H:%i') AS hora_desde, TIME_FORMAT(rd.hora_hasta, '%H:%i') AS hora_hasta ";
			sql += 	"FROM ";
			sql += 		"trabajadores tr ";
			sql += 		"LEFT JOIN contratos ct ON (ct.codigo_trabajador = tr.codigo) ";
			sql += 		"LEFT JOIN campo cm ON (cm.campo = tr.idHuerto) ";
			sql += 		"LEFT JOIN (SELECT ";
			sql += 				"SUM(horas_trabajadas) horas, trabajador, ";
			sql += 				"CASE WHEN(rd.fecha_i IS NULL)THEN rg.fecha ELSE rd.fecha_i END AS fecha, rd.hora_desde, hora_hasta ";
			sql += 			"FROM ";
			sql += 				"rendimiento_diario rd ";
			sql += 				"LEFT JOIN rendimiento_general rg ON (rg.codigo_rg = rd.codigo_rg) ";
			sql += 			"WHERE ";
			sql += 				"(rd.fecha_i BETWEEN '"+fechas[0]+"' AND '"+fechas[fechas.length-1]+"' OR rg.fecha BETWEEN '"+fechas[0]+"' AND '"+fechas[fechas.length-1]+"') ";
			sql += 				"AND (rg.campo = '"+campo+"' OR rd.campo_rd = '"+campo+"') ";
			sql += 				"AND rd.estado != 7 ";
			sql += 			"GROUP BY trabajador, 3,4,5) rd ON (rd.trabajador = tr.id) ";
			sql += 		"LEFT JOIN (SELECT ";
			sql += 				"SUM(nHoras) AS horasFalta, codTrabajador, fecha ";
			sql += 			"FROM ";
			sql += 				"sw_horasAsistencia ";
			sql += 			"WHERE ";
			sql += 				"fecha BETWEEN '"+fechas[0]+"' AND '"+fechas[fechas.length-1]+"' ";
			sql += 			"GROUP BY 2,3) ha ON (tr.codigo = ha.codTrabajador) ";
			sql += 		"LEFT JOIN sw_fechas f ON(rd.fecha = f.fecha) ";
			sql += 	"WHERE ";
			sql += 		"tr.agro = 1 ";
			sql += 		"AND cm.campo = '"+campo+"' ";
			sql += 		"AND (rd.horas IS NULL OR rd.horas != "+horas+") ";
			sql += 		"AND WEEKDAY(f.fecha) IN(SELECT dia FROM horario h LEFT JOIN horario_campo hc ON(h.idHc = hc.id) WHERE hc.campo = '"+campo+"')  ";
			sql += 		"AND f.fecha NOT IN (SELECT fechaFeriado FROM sw_m_feriados WHERE DATE_FORMAT(fechaFeriado, '%Y%m') = DATE_FORMAT(f.fecha, '%Y%m')) ";
			sql += 		"AND ct.fechaInicio_actividad <= f.fecha ";
			sql += 		"AND (ct.FechaTerminoContrato >= f.fecha OR ct.FechaTerminoContrato IS NULL) ";
			sql +=	"ORDER BY 4, 7;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql); 
			while(rs.next()){
				JSONObject ob = new JSONObject();
				ob.put("id", rs.getString("id"));
				ob.put("codigo", rs.getInt("codigo"));
				ob.put("campo", rs.getString("campo"));
				ob.put("dia", rs.getString("dia"));
				ob.put("fecha", rs.getString("fecha"));
				ob.put("rut", rs.getString("rut"));
				ob.put("nombre", rs.getString("nombre"));
				ob.put("horas", rs.getDouble("horas"));
				ob.put("horasFalta", rs.getFloat("horasFalta"));
				ob.put("hora_desde", rs.getString("hora_desde"));
				ob.put("hora_hasta", rs.getString("hora_hasta"));
				ob.put("n_dia", rs.getInt("n_dia"));
				array.put(ob);
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
		return array.toString();
	}			
			

	public static ArrayList<CUADRATURA_HORA> GET_DETALLE_RENDIMIENTO_RUT (FILTRO_CUADRAR_HR t)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CUADRATURA_HORA> list = new ArrayList<CUADRATURA_HORA>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT ";
			sql += 		"rd.codigo,rd.trabajador id, tr.rut rut_trabajador, cam.descripcion AS campo, rd.horas_trabajadas AS horas, ";
			sql += 		"CONCAT(tr.apellidoPaterno,' ', tr.apellidoMaterno, ' ', tr.nombre) AS nombreTrabajador, ";
			sql += 		"CASE ";
			sql += 			"WHEN(rg.fecha IS NULL) THEN  ";
			sql += 				"rd.fecha_i ";
			sql += 			"ELSE rg.fecha ";
			sql += 		"END AS fecha, ";
			sql += 		"CASE ";
			sql += 			"WHEN(rd.cuartel IS NULL) THEN ";
			sql += 				"CASE ";
			sql += 					"WHEN(rd.ceco IS NULL)THEN ";
			sql += 						"rd.ordenco ";
			sql += 					"ELSE rd.ceco ";
			sql += 				"END ";
			sql += 			"ELSE c.nombre ";
			sql += 		"END AS cuartel, ";
			sql += 		"CONCAT(trS.apellidoPaterno,' ', trS.apellidoMaterno, ' ', trS.nombre) AS supervisor ";
			sql += 	"FROM rendimiento_diario rd ";
			sql += 		"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 		"LEFT JOIN trabajadores tr ON (tr.id = rd.trabajador) ";
			sql += 		"LEFT JOIN trabajadores trS ON (trS.id = rg.codigo_supervisor OR trS.id = rd.supervisor_i) ";
			sql += 		"LEFT JOIN cuartel c ON (c.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN campo cam ON (cam.campo = rg.campo OR rd.campo_rd = cam.campo) ";
			sql += 	"WHERE ";
			sql += 		"(rg.fecha = '"+t.getFecha()+"' or rd.fecha_i = '"+t.getFecha()+"') ";
			sql += 		"AND (rg.campo = '"+t.getCampo()+"' OR rd.campo_rd = '"+t.getCampo()+"')";
			sql += 		"AND tr.rut = '"+t.getRut()+"' AND rd.estado != 7;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				CUADRATURA_HORA ob = new CUADRATURA_HORA();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setCampo(rs.getString("campo"));
				ob.setRut(rs.getString("rut_trabajador"));
				ob.setNombre(rs.getString("nombreTrabajador"));
				ob.setHoras(rs.getInt("horas"));
				ob.setSupervisor(rs.getString("id"));
				ob.setCuartel(rs.getString("cuartel"));
				ob.setFecha(rs.getString("fecha"));
				ob.setSupervisor(rs.getString("supervisor"));
				list.add(ob);
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
		return list;
	}	
//-------------FIN CUADRATURA HORAS-------------
			
//-------------FIN VALIDAR RENDIMIENTO-------------
	public static ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTOS_FECHA (String campo, String fecha_desde, String fecha_hasta, String tipo, int estado)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_GENERAL> lista = new ArrayList<RENDIMIENTO_GENERAL>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql +=	"SELECT ";
			sql +=		"rg.codigo_rg, rg.fecha, cam.campo AS codigoCampo,cam.descripcion AS campo, rg.ceco, rg.ordenco, ";
			sql +=		"CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre) AS supervisor, ";
			sql +=		"fa.faena, la.labor, rg.horas, rg.tipo_pago, rg.folio, rg.contratista, rg.ncontratista, e.especie, v.variedad, c.nombre ";
			sql +=	"FROM rendimiento_general rg ";
			sql +=		"LEFT JOIN campo cam ON(cam.campo = rg.campo) ";
			sql +=		"LEFT JOIN faena fa ON(fa.codigo = rg.faena) ";
			sql +=		"LEFT JOIN labor la ON(la.codigo = rg.labor) ";
			sql +=		"LEFT JOIN cuartel c ON(c.codigo = rg.cuartel) ";
			sql +=		"LEFT JOIN variedad v ON(v.codigo = rg.variedad) ";
			sql +=		"LEFT JOIN especie e ON(e.codigo = rg.especie) ";
			sql +=		"LEFT JOIN trabajadores t ON(t.id = rg.codigo_supervisor) ";
			sql +=	"WHERE ";
			sql +=		"rg.fecha BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"' ";
			sql +=		"AND cam.campo = '"+campo+"' ";
			sql +=		"AND rg.estado = "+estado+" ";
			if(tipo.equals("PLANTA")){
				sql += 	"AND rg.contratista IS NULL ";
			}else{
				sql += 	"AND rg.contratista IS NOT NULL ";
			}
			sql	+= "ORDER BY 2, 4;";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				RENDIMIENTO_GENERAL ob = new RENDIMIENTO_GENERAL();
				ob.setCodigo(rs.getInt("codigo_rg"));
				ob.setFecha(rs.getString("fecha"));
				ob.setCampo(rs.getString("campo"));
				ob.setNsupervisor(rs.getString("supervisor"));
				ob.setNlabor(rs.getString("labor"));
				ob.setHoras(rs.getInt("horas"));
				ob.setTipo_pago(rs.getInt("tipo_pago"));
				ob.setNespecie(rs.getString("especie"));
				ob.setNvariedad(rs.getString("variedad"));
				ob.setNfaena(rs.getString("faena"));
				ob.setNcuartel(rs.getString("nombre"));
				ob.setFolio(rs.getInt("folio"));
				ob.setContratista(rs.getString("contratista"));
				ob.setOrdenco(rs.getString("ordenco"));
				ob.setCeco(rs.getString("ceco"));
				ob.setNcontratista(rs.getString("ncontratista"));
				lista.add(ob);
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
		return lista;
	}
	public static ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTOS_FECHA_INDIVIDUAL (String campo, String fecha_desde, String fecha_hasta, String tipo, int estado)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_GENERAL> lista = new ArrayList<RENDIMIENTO_GENERAL>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT ";
			sql += 		"rd.codigo AS codigo_rg, rd.fecha_i, cam.descripcion, ";
			sql += 		"CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre) AS supervisor, ";
			sql += 		"CONCAT(trs.apellidoPaterno, ' ', trs.apellidoMaterno, ' ', trs.nombre) AS trabajador, ";
			sql += 		"f.faena, l.labor, rd.horas_trabajadas, rd.tipo_trato, e.especie, v.variedad, c.nombre, rd.macroco, rd.ceco, rd.ordenco, rd.rd_contratista, rd.tipo_permiso ";
			sql += 	"FROM rendimiento_diario rd ";
			sql += 		"LEFT JOIN labor l ON(l.codigo = rd.labor) ";
			sql += 		"LEFT JOIN faena f ON(l.faena = f.codigo) ";
			sql += 		"LEFT JOIN trabajadores t ON(t.id = rd.supervisor_i) ";
			sql += 		"LEFT JOIN trabajadores trs ON(trs.id = rd.trabajador) ";
			sql += 		"LEFT JOIN campo cam ON(cam.campo = rd.campo_rd) ";
			sql += 		"LEFT JOIN cuartel c ON(c.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN especie e ON(e.codigo = c.especie) ";
			sql += 		"LEFT JOIN variedad v ON(v.codigo = c.variedad) ";
			sql += 	"WHERE ";
			sql += 		"rd.fecha_i BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"' ";
			sql += 		"AND rd.codigo_rg = 0 ";
			sql += 		"AND (cam.campo = '"+campo+"' OR rd.campo_rd = '"+campo+"') ";
			sql += 		"AND rd.estado = "+estado+" ";
			if(tipo.equals("PLANTA")){
				sql += "AND rd.rd_contratista = 0 ";
			}else{
				sql += "AND rd.rd_contratista != 0 ";
			}
			sql	+= "ORDER BY 2, 4;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				RENDIMIENTO_GENERAL ob = new RENDIMIENTO_GENERAL();
				ob.setCodigo(rs.getInt("codigo_rg"));
				ob.setFecha(rs.getString("fecha_i"));
				ob.setCampo(rs.getString("descripcion"));
				ob.setNsupervisor(rs.getString("supervisor"));
				ob.setTrabajador(rs.getString("trabajador"));
				ob.setNfaena(rs.getString("faena"));
				ob.setNlabor(rs.getString("labor"));
				ob.setHoras(rs.getFloat("horas_trabajadas"));
				ob.setTipo_pago(rs.getInt("tipo_trato"));
				ob.setNespecie(rs.getString("especie"));
				ob.setNvariedad(rs.getString("variedad"));
				ob.setNcuartel(rs.getString("nombre"));
				ob.setMacro(rs.getString("macroco"));
				ob.setCeco(rs.getString("ceco"));
				ob.setOrdenco(rs.getString("ordenco"));
				ob.setContratista(rs.getString("rd_contratista"));
				ob.setTipo_permiso(rs.getInt("tipo_permiso"));
				lista.add(ob);
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
		return lista;
	}	
			
	public static ArrayList<detalle_rendimiento> GET_DETALLE_RENDIMIENTO_DIARIO (String tipo, String codigorg)throws Exception{
		int codigo = Integer.parseInt(codigorg);
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<detalle_rendimiento> lista = new ArrayList<detalle_rendimiento>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT rd.*, CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ',t.nombre) AS nombre_trabajador, c.nombre AS nombre_cuartel, fa.faena AS des_faena, la.labor AS des_labor, m.descripcion AS maquinaria_desc, i.descripcion AS implemento_desc ";
			sql	+= "FROM  rendimiento_diario rd ";
			sql	+= "LEFT JOIN trabajadores t ON(t.id = rd.trabajador) ";
			sql	+= "LEFT JOIN cuartel c ON(c.codigo = rd.cuartel) ";
			sql	+= "JOIN labor la ON(la.codigo = rd.labor) ";
			sql	+= "JOIN faena fa ON(fa.codigo = la.faena) ";
			sql	+= "LEFT JOIN implemento i ON(i.codigo = rd.implemento) ";
			sql	+= "LEFT JOIN maquinaria m ON(m.codigo = rd.maquinaria) ";
			if(tipo.equals("MASIVO")){
				sql	+= "WHERE rd.codigo_rg = "+codigo+"";
			}else{
				sql	+= "WHERE rd.codigo = "+codigo+"";
			}
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				detalle_rendimiento e = new detalle_rendimiento();
				e.setCodigo(rs.getInt("codigo"));
				e.setTrabajador(rs.getInt("rd.trabajador"));
				e.setNombre_trabajador(rs.getString("nombre_trabajador"));
				e.setBase_piso_hora(rs.getFloat("base_piso_hora"));
				e.setSubsidio(rs.getInt("subsidio"));
				e.setNombre_cuartel(rs.getString("nombre_cuartel"));
				e.setDes_faena(rs.getString("des_faena"));
				e.setDes_labor(rs.getString("des_labor"));
				e.setValor(rs.getInt("valor"));
				e.setTipo_trato(rs.getInt("tipo_trato"));
				e.setRendimiento(rs.getFloat("rendimiento"));
				e.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				e.setHoras_extras(rs.getFloat("horas_extras"));
				e.setBono1(rs.getInt("bono1"));
				e.setBono2(rs.getInt("bono2"));
				e.setValor_liquido(rs.getFloat("valor_liquido"));
				e.setMaquinaria_desc(rs.getString("maquinaria_desc"));
				e.setImplemento_desc(rs.getString("implemento_desc"));
				e.setMaquinaria(rs.getInt("maquinaria"));
				e.setImplemento(rs.getInt("implemento"));
				e.setBus(rs.getInt("bus"));
				e.setIdContratista(rs.getString("rd_contratista"));
				e.setCeco(rs.getString("ceco"));
				e.setCampo(rs.getString("campo_rd"));
				e.setValor_hx(rs.getFloat("valor_hx"));
				e.setMonto_hx(rs.getFloat("monto_hx"));
				e.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				e.setHx_dos(rs.getFloat("hx_dos"));
				lista.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GET_DETALLE_RENDIMIENTO_DIARIO:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error GET_DETALLE_RENDIMIENTO_DIARIO:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	public static ArrayList<detalle_rendimiento> GET_DETALLE_RENDIMIENTO_GENERAL (String tipo, String codigorg)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<detalle_rendimiento> lista = new ArrayList<detalle_rendimiento>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT ";
			sql += 		"rd.*, ";
			sql += 		"CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre) AS nombre_trabajador, ";
			sql += 		"cl.nombre AS nombre_cuartel, fa.faena AS des_faena, la.labor AS des_labor, m.descripcion AS maquinaria_desc, i.descripcion AS implemento_desc ";
			sql += 	"FROM ";
			sql += 		"cuadrilla c ";
			sql += 		"LEFT JOIN cuadrilla_trabajador ct ON (c.codigo = ct.codigo_cuadrilla) ";
			sql += 		"RIGHT JOIN trabajadores t ON (ct.rut_trabajador = t.rut OR ct.rut_trabajador = t.rutTemporal) ";
			sql += 		"INNER JOIN rendimiento_general rg ON (c.codigo = rg.codigo_cuadrilla) ";
			sql += 		"LEFT JOIN rendimiento_diario rd ON (rg.codigo_rg = rd.codigo_rg AND rd.trabajador = t.id) ";
			sql += 		"LEFT JOIN trabajadores tr ON (tr.id = rg.codigo_supervisor) ";
			sql += 		"LEFT JOIN cuartel cl ON (cl.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN especie e ON (e.codigo = cl.especie) ";
			sql += 		"LEFT JOIN variedad v ON (v.codigo = cl.variedad) ";
			sql += 		"LEFT JOIN campo cam ON (rg.campo = cam.campo) ";
			sql += 		"LEFT JOIN labor la ON (la.codigo = rd.labor) ";
			sql += 		"JOIN faena fa ON (fa.codigo = la.faena) ";
			sql += 		"LEFT JOIN implemento i ON (i.codigo = rd.implemento) ";
			sql += 		"LEFT JOIN maquinaria m ON (m.codigo = rd.maquinaria) ";
			sql += 	"WHERE ";
			sql += 		"rg.codigo_rg = "+codigorg;
			sql += 		" AND ct.asistencia IN (1 , 5)";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				detalle_rendimiento e = new detalle_rendimiento();
				e.setCodigo(rs.getInt("codigo"));
				e.setTrabajador(rs.getInt("rd.trabajador"));
				e.setNombre_trabajador(rs.getString("nombre_trabajador"));
				e.setBase_piso_hora(rs.getFloat("base_piso_hora"));
				e.setSubsidio(rs.getInt("subsidio"));
				e.setNombre_cuartel(rs.getString("nombre_cuartel"));
				e.setDes_faena(rs.getString("des_faena"));
				e.setDes_labor(rs.getString("des_labor"));
				e.setValor(rs.getInt("valor"));
				e.setTipo_trato(rs.getInt("tipo_trato"));
				e.setRendimiento(rs.getFloat("rendimiento"));
				e.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				e.setHoras_extras(rs.getFloat("horas_extras"));
				e.setBono1(rs.getInt("bono1"));
				e.setBono2(rs.getInt("bono2"));
				e.setValor_liquido(rs.getFloat("valor_liquido"));
				e.setMaquinaria_desc(rs.getString("maquinaria_desc"));
				e.setImplemento_desc(rs.getString("implemento_desc"));
				e.setMaquinaria(rs.getInt("maquinaria"));
				e.setImplemento(rs.getInt("implemento"));
				e.setBus(rs.getInt("bus"));
				e.setIdContratista(rs.getString("rd_contratista"));
				e.setCeco(rs.getString("ceco"));
				e.setCampo(rs.getString("campo_rd"));
				e.setValor_hx(rs.getFloat("valor_hx"));
				e.setMonto_hx(rs.getFloat("monto_hx"));
				e.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				e.setHx_dos(rs.getFloat("hx_dos"));
				lista.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error GET_DETALLE_RENDIMIENTO_DIARIO:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error GET_DETALLE_RENDIMIENTO_DIARIO:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
			
	public static int ESTADO_PERFIL (int perfil)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		int estado = 0;
		try {
			sql = "SELECT codigo FROM SAN_CLEMENTE.estado_rendimiento where perfil = " + perfil;
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				estado = rs.getInt("codigo");
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
		return estado;
	}
			
//-------------FIN VALIDAR RENDIMIENTO-------------
	public static ArrayList<BLOQUEO_LABOR> GET_LABOR_BLOQUEO (String campo, String mes, String especie)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<BLOQUEO_LABOR> lista = new ArrayList<BLOQUEO_LABOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT * FROM bloqueo_labor WHERE id_campo = '"+campo+"' AND "+mes+" = 0 AND id_especie = '"+especie+"';";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				BLOQUEO_LABOR e = new BLOQUEO_LABOR();
				e.setId_campo(rs.getString("id_campo"));
				e.setId_labor(rs.getInt("id_faena"));
				e.setEnero(rs.getInt("enero"));
				e.setFebrero(rs.getInt("febrero"));
				e.setMarzo(rs.getInt("marzo"));
				e.setAbril(rs.getInt("abril"));
				e.setMayo(rs.getInt("mayo"));
				e.setJunio(rs.getInt("junio"));
				e.setJulio(rs.getInt("julio"));
				e.setAgosto(rs.getInt("agosto"));
				e.setSeptiembre(rs.getInt("septiembre"));
				e.setOctubre(rs.getInt("octubre"));
				e.setNoviembre(rs.getInt("noviembre"));
				e.setDiciembre(rs.getInt("diciembre"));
				lista.add(e);
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
		return lista;
	}	
	public static ArrayList<LABOR> GET_LABOR_ALL()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LABOR> lista = new ArrayList<LABOR>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "select * from labor where estado = 1";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()){
				LABOR ob = new LABOR();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setFaena(rs.getInt("faena"));
				ob.setLabor(rs.getString("labor"));
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setRebaja(rs.getInt("rebaja"));
				ob.setTipo_labor(rs.getInt("tipo_labor"));
				ob.setEstado(rs.getInt("estado"));
				lista.add(ob);
			}
			rs.close();
			ps.close();
			 db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return lista;
	}
	public static boolean UPDATE_RENDIMIENTO (String tipo, int estado, int codigo_rg)throws Exception{
		System.out.println(estado);
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			if(tipo.equals("INDIVIDUAL")){
				sql = "UPDATE rendimiento_diario SET estado = "+estado+" WHERE codigo = "+codigo_rg;
				System.out.println(estado);
			}else{
				sql = "UPDATE rendimiento_diario SET estado = "+estado+" WHERE codigo_rg = "+codigo_rg;
			}
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			UPDATE_ESTADO_RENDIMIENTO_GENERAL(estado, codigo_rg);
			return true;
		} catch (SQLException e) {
			System.out.println("Error dfsdfsd:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean UPDATE_ESTADO_RENDIMIENTO_GENERAL (int estado, int codigo_rg)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE rendimiento_general SET estado = "+estado+" WHERE codigo_rg = "+codigo_rg;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error tgyhuj:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
			
	public static boolean DELETE_CALIFICACION_RD (int codigo_rg)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM calificacion_rendimiento WHERE codigo_rg = "+codigo_rg;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
			
	public static boolean calificacion_cuadrilla (int codigo_rg)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			DELETE_CALIFICACION_RD(codigo_rg);
			sql += 	"INSERT INTO calificacion_rendimiento ";
			sql += 	"SELECT ";
			sql += 		"0 AS  codigo, rd.trabajador, rg.codigo_cuadrilla, rd.codigo_rg, rd.labor, s.campo, ";
			sql += 		"SUM(rd.horas_trabajadas + rd.horas_extras) horas, ";
			sql += 		"SUM(rd.rendimiento) AS rendimiento, ";
			sql += 		"SUM(rd.rendimiento) / SUM(rd.horas_trabajadas + rd.horas_extras) AS promedio, ";
			sql += 		"(SELECT AVG(horas_trabajadas + horas_extras) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") AS promedio_de_horas, ";
			sql += 		"(SELECT AVG(rendimiento) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") /  ";
			sql += 		"(SELECT AVG(horas_trabajadas + horas_extras) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") AS promedio_cuadrilla, ";
			sql += 		"CASE WHEN (";
			sql += 				"SELECT (SELECT AVG(rendimiento) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") / ";
			sql += 				"(SELECT AVG(horas_trabajadas + horas_extras) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") * ";
			sql += 				"((100 + cc.bajo_max) / 100)) > (SUM(rd.rendimiento) / SUM(rd.horas_trabajadas + rd.horas_extras)) ";
			sql += 			"THEN 1 ";
			sql += 			"WHEN (";
			sql += 				"(SELECT AVG(rendimiento) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") / ";
			sql += 				"(SELECT AVG(horas_trabajadas + horas_extras) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") * ";
			sql += 				"((100 + cc.bajo_max) / 100)) <= (SUM(rd.rendimiento) / SUM(rd.horas_trabajadas + rd.horas_extras)) ";
			sql += 				"AND (";
			sql += 				"(SELECT AVG(rendimiento) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") / ";
			sql += 				"(SELECT AVG(horas_trabajadas + horas_extras) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") * ";
			sql += 				"((100 + cc.promedio_max) / 100)) > (SUM(rd.rendimiento) / SUM(rd.horas_trabajadas + rd.horas_extras)) ";
			sql += 			"THEN 2 ";
			sql += 			"WHEN (";
			sql += 				"(SELECT AVG(rendimiento) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") / ";
			sql += 				"(SELECT AVG(horas_trabajadas + horas_extras) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") * ";
			sql += 				"((100 + cc.promedio_max) / 100)) <= (SUM(rd.rendimiento) / SUM(rd.horas_trabajadas + rd.horas_extras)) ";
			sql += 				"AND (";
			sql += 				"(SELECT AVG(rendimiento) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") / ";
			sql += 				"(SELECT AVG(horas_trabajadas + horas_extras) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") * ";
			sql += 				"((100 + cc.bueno_max) / 100)) > (SUM(rd.rendimiento) / SUM(rd.horas_trabajadas + rd.horas_extras)) ";
			sql += 			"THEN 3 ";
			sql += 				"WHEN (";
			sql += 				"(SELECT AVG(rendimiento) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") / ";
			sql += 				"(SELECT AVG(horas_trabajadas + horas_extras) FROM rendimiento_diario WHERE codigo_rg = "+codigo_rg+") * ";
			sql += 				"((100 + cc.bueno_max) / 100)) <= (SUM(rd.rendimiento) / SUM(rd.horas_trabajadas + rd.horas_extras)) ";
			sql += 			"THEN 4 ";
			sql += 		"END nota ";
			sql += 	"FROM ";
			sql += 		"rendimiento_diario rd ";
			sql += 		"LEFT JOIN rendimiento_general rg ON (rg.codigo_rg = rd.codigo_rg) ";
			sql += 		"LEFT JOIN cuartel c ON (c.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN sector s ON (s.sector = c.sector) ";
			sql += 		"LEFT JOIN calificacion_campo cc ON (cc.campo = s.campo AND cc.labor = rd.labor) ";
			sql += 	"WHERE ";
			sql += 		"rd.codigo_rg = "+codigo_rg+" ";
			sql += 		"AND rd.labor IN (SELECT labor FROM calificacion_campo WHERE campo = s.campo) ";
			sql += 	"GROUP BY trabajador , rd.labor , rg.codigo_cuadrilla , s.campo , cc.bajo_max , cc.promedio_max , cc.bueno_max;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
			
	public static faena GET_FAENA_LABOR(int codigo, String zona) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		faena e = new faena();
		ConnectionDB db = new ConnectionDB();
		try{
		sql = "SELECT *FROM faena WHERE codigo = (SELECT faena FROM labor WHERE codigo = "+codigo+") AND zona = '"+zona+"'";
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		while (rs.next()) {
			e.setCodigo(rs.getInt("codigo"));
			e.setFaena(rs.getString("faena"));
		}
		rs.close();
		ps.close();
		db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return e;
	}
	public static int GET_HORAS_MES(int mes) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		int horas = 0;
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT getDiasHabiles("+mes+")*9 as horas FROM dual";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {
				horas = rs.getInt("horas");
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return horas;
	}
	public static RENDIMIENTO_GENERAL GET_RENDIMIENTOS_CODIGO(int codigo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		RENDIMIENTO_GENERAL e = new RENDIMIENTO_GENERAL();
		ConnectionDB db = new ConnectionDB();
		try{
		sql = "SELECT fecha, codigo_supervisor, cuartel, contratista FROM rendimiento_general WHERE codigo_rg = "+codigo;
		ps = db.conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		if(rs.next()) {
			e.setFecha(rs.getString("fecha"));
			e.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
			e.setCuartel(rs.getInt("cuartel"));
			e.setContratista(rs.getString("contratista"));
		}
		rs.close();
		ps.close();
		db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return e;
	}
	public static ArrayList<recorrido> GET_RECORRIDO_CAMPO(String campo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<recorrido> data = new ArrayList<recorrido>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT *FROM recorrido WHERE campo = '"+campo+"'";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				recorrido e = new recorrido();
				e.setId_recorrido(rs.getInt("id_recorrido"));
				e.setCampo(rs.getString("campo"));
				e.setDetalle(rs.getString("detalle"));
				e.setChofer(rs.getString("chofer"));
				e.setTipo_vehiculo(rs.getString("tipo_vehiculo"));
				e.setPatente(rs.getString("patente"));
				e.setOrigen(rs.getString("origen"));
				e.setDestino(rs.getString("destino"));
				e.setResponsable(rs.getString("responsable"));
				e.setCantidad_persona(rs.getInt("cantidad_persona"));
				e.setHorario_salida(rs.getString("horario_salida"));
				e.setHorario_llegada(rs.getString("horario_llegada"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	
	public static ArrayList<RENDIMIENTO_DIARIO> GET_ListRendContratista(String CAMPO, String CONTRATISTA, String DESDE, String HASTA) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_DIARIO> data = new ArrayList<RENDIMIENTO_DIARIO>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql += 	"SELECT ";
			sql += 		"rd.codigo, cam.descripcion AS campo, rg.contratista, ";
			sql += 		"CASE ";
			sql += 			"WHEN rg.fecha IS NULL THEN rd.fecha_i ";
			sql += 			"ELSE rg.fecha ";
			sql += 		"END fecha_rendimiento, ";
			sql += 		"CONCAT(trS.nombre, ' ', trS.apellidoPaterno) AS supervisor, ";
			sql += 		"CONCAT(tr.nombre, ' ', tr.apellidoPaterno) AS trabajador, ";
			sql += 		"CONCAT(c.codigo_cuartel, ' ', c.nombre) AS cuartel,";
			sql += 		"rd.ceco, rd.ordenco, rd.macroco, tr.rut AS rut_trabajador, rd.rendimiento,";
			sql += 		"rd.valor_liquido, es.especie nespecie, var.variedad nvariedad, fa.faena nfaena, ";
			sql += 		"lab.labor nlabor, rd.rd_contratista, rd.bono1, rd.valor_rendimietno, rd.tipo_trato ";
			sql += 	"FROM ";
			sql += 		"rendimiento_diario rd ";
			sql += 		"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 		"LEFT JOIN trabajadores tr ON (tr.id = rd.trabajador) ";
			sql += 		"LEFT JOIN trabajadores trS ON (trS.id = rg.codigo_supervisor OR trS.id = rd.supervisor_i)";
			sql += 		"LEFT JOIN cuartel c ON (c.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN sector s ON (s.sector = c.sector) ";
			sql += 		"LEFT JOIN campo cam ON (cam.campo = rd.campo_rd OR cam.campo = rg.campo) ";
			sql += 		"LEFT JOIN especie es ON (es.codigo = c.especie) ";
			sql += 		"LEFT JOIN variedad var ON (var.codigo = c.variedad) ";
			sql += 		"LEFT JOIN labor lab ON (lab.codigo = rd.labor) ";
			sql += 		"LEFT JOIN faena fa ON (fa.codigo = lab.faena) ";
			sql += 	"WHERE ";
			sql += 		"(rg.fecha BETWEEN '"+DESDE+"' AND '"+HASTA+"' OR (rd.fecha_i BETWEEN '"+DESDE+"' AND '"+HASTA+"')) ";
			sql += 		"AND (rd.campo_rd = '"+CAMPO+"' OR rg.campo = '"+CAMPO+"') ";
			sql += 		"AND rd.rd_contratista = '"+CONTRATISTA+"' ";
			sql += 		"AND rd.estado = 3 ";
			sql += 		"AND rd.codigo NOT IN (SELECT  lr.codigo_rd ";
			sql += 			"FROM liquidacion_contratista lc ";
			sql += 				"JOIN ";
			sql += 				"liquidacion_rendimiento lr ON (lr.codigo_liq = lc.codigo) ";
			sql += 			"WHERE ";
			sql += 				"lc.contratista = '"+CONTRATISTA+"') ";
			sql += 	"ORDER BY 4;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				RENDIMIENTO_DIARIO e = new RENDIMIENTO_DIARIO();
				e.setCodigo(rs.getInt("codigo"));
				//e.setCampo(rs.getString("campo"));
				e.setNvnombre(rs.getString("cuartel"));
				e.setFecha(rs.getString("fecha_rendimiento"));
				e.setIdContratista(rs.getString("rd_contratista"));
				e.setSupervisor(rs.getString("supervisor"));		
				e.setNombre(rs.getString("trabajador"));
				e.setRut(rs.getString("rut_trabajador"));
				//e.setValor(rs.getInt("valor"));
				e.setValor_rendimiento(rs.getFloat("valor_rendimietno"));
				e.setRendimiento(rs.getFloat("rendimiento"));
				e.setTipo_pago(rs.getInt("tipo_trato"));
				e.setValor_liquido(rs.getFloat("valor_liquido"));
				e.setBono1(rs.getInt("bono1"));
				e.setnVariedad(rs.getString("nvariedad"));
				e.setnEspecie(rs.getString("nespecie"));
				e.setnFaena(rs.getString("nfaena"));
				e.setnLabor(rs.getString("nlabor"));
				e.setCeco(rs.getString("ceco"));
				e.setOrdenco(rs.getString("ordenco"));
				e.setMacroco(rs.getString("macroco"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	
	public static int GENERAR_LIQUIDACION (LIQUIDACION c)throws Exception{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO liquidacion_contratista ( contratista, fecha,"
					+ " usuario, campo, estado,fecha_desde,fecha_hasta, estado_liquidacion, semanas)";
			sql += "VALUES (?,NOW(),?,?,1,?,?, 1, (SELECT ";
			sql +=	"IF(WEEK('"+c.getFecha_desde()+"', 3) = WEEK('"+c.getFecha_hasta()+"', 3), ";
			sql +=	"CONCAT('Semana ', WEEK('"+c.getFecha_desde()+"', 3)), ";
			sql +=	"CONCAT('Semanas ',WEEK('"+c.getFecha_desde()+"', 3),' Hasta ', WEEK('"+c.getFecha_hasta()+"', 3)))))";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getContratista());
			ps.setString(2, c.getUsuario());
			ps.setString(3, c.getCampo());
			ps.setString(4, c.getFecha_desde());
			ps.setString(5, c.getFecha_hasta());
			ps.execute();
			
			String sql2 = "";
			sql2 = "SELECT MAX(codigo) as codigo from liquidacion_contratista";
			ResultSet idNew = ps.executeQuery(sql2);
			int codCont = 0;
			while (idNew.next()) { 
				codCont = idNew.getInt("codigo"); 
			}
			String sql3 = "";
			for(String id: c.getRendimientos()){
				sql3 = "INSERT INTO liquidacion_rendimiento (codigo_rd,codigo_liq) values ('"+id+"','"+codCont+"')";
				ps = db.conn.prepareStatement(sql3);
				ps.execute();
			}
			String sql4 = "";
			sql4 += "UPDATE liquidacion_contratista SET semanas = ";
			sql4 += 	"(SELECT ";
			sql4 += 		"IF(WEEK(t.fechamin, 3) = WEEK(t.fechamax, 3), ";
			sql4 += 		"CONCAT('Semana ', WEEK(t.fechamin, 3)), ";
			sql4 += 		"CONCAT('Semanas ',WEEK(t.fechamin, 3),' Hasta ', WEEK(t.fechamax, 3))) AS semanas ";
			sql4 += 	"FROM ";
			sql4 += 		"(SELECT ";
			sql4 += 			"MIN(IF(rd.fecha_i IS NULL, rg.fecha, rd.fecha_i)) AS fechamin, ";
			sql4 += 			"MAX(IF(rd.fecha_i IS NULL, rg.fecha, rd.fecha_i)) AS fechamax ";
			sql4 += 		"FROM ";
			sql4 += 			"rendimiento_diario rd ";
			sql4 += 			"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql4 += 		"WHERE ";
			sql4 += 			"rd.codigo IN (SELECT codigo_rd FROM liquidacion_rendimiento WHERE codigo_liq = "+codCont+")) t) ";
			sql4 += "WHERE codigo = "+codCont;
			ps2 = db.conn.prepareStatement(sql4);
			ps2.execute();
			return codCont;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return 0;
	}
	
	public static ArrayList<LIQUIDACION> GET_LIQUIDACION(LIQUIDACION liq) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<LIQUIDACION> data = new ArrayList<LIQUIDACION>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql += 	"SELECT ";
			sql += 		"lc.*, cam.descripcion ncampo, le.descripcion nestado, ";
			sql += 		"SUM(rd.valor_liquido) AS valor_liquido, ";
			sql += 		"CAST((SUM(rd.valor_liquido)*0.19) AS SIGNED) AS iva, ";
			sql += 		"(SUM(rd.valor_liquido)+CAST((SUM(rd.valor_liquido)*0.19) AS SIGNED)) as total, ";
			sql += 		"lc.anticipo,cam.sociedad, lc.orden_pago ";
			sql += 	"FROM ";
			sql += 		"liquidacion_contratista lc ";
			sql += 		"LEFT JOIN liquidacion_rendimiento lr ON lr.codigo_liq = lc.codigo ";
			sql += 		"LEFT JOIN rendimiento_diario rd ON rd.codigo = lr.codigo_rd ";
			sql += 		"LEFT JOIN campo cam ON cam.campo = lc.campo ";
			sql += 		"LEFT JOIN (SELECT * FROM SAN_CLEMENTE.parametros WHERE codigo = 'ESTADO_LIQUIDACION') le ON le.llave = lc.estado ";
			sql += 	"WHERE ";
			sql += 		"fecha BETWEEN '"+liq.getFecha_desde()+"' AND '"+liq.getFecha_hasta()+"' ";
			sql += 		"AND lc.campo = '"+liq.getCampo()+"' ";
			sql += 		"AND lc.estado_liquidacion = "+liq.getCodigo()+" ";
			if(!liq.getContratista().equals("0")){
				sql += 	"AND contratista = '"+liq.getContratista()+"' ";
			}
			sql += 	"GROUP BY lc.codigo , cam.descripcion , le.descripcion , cam.sociedad , lc.orden_pago;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				LIQUIDACION e = new LIQUIDACION();
				e.setCodigo(rs.getInt("codigo"));
				e.setFecha(rs.getString("fecha"));
				e.setContratista(rs.getString("contratista"));
				e.setValor_liquido(rs.getInt("valor_liquido"));
				System.out.println(rs.getString("nestado"));
				e.setEstado(rs.getString("nestado"));
				e.setCampo(rs.getString("ncampo"));
				e.setSociedad(rs.getString("sociedad"));
				e.setOrden(rs.getString("orden_pago"));
				e.setN_factura(rs.getString("n_factura"));
				e.setSemanas(rs.getString("semanas"));
				e.setValor_retencion(rs.getInt("valor_retencion"));
				e.setAsiento_contable(rs.getString("asiento"));
				e.setIva(rs.getInt("iva"));
				e.setTotal(rs.getInt("total"));
				e.setAnticipo(rs.getInt("anticipo"));
				e.setPdf(rs.getString("pdf"));
				if(rs.getString("imgFactura") != null){
					e.setImgFactura("foto");
				}
				
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	public static RENDIMIENTO_DIARIO GET_RD_INDIVIDUAL (int codigo_rd)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		RENDIMIENTO_DIARIO e = new RENDIMIENTO_DIARIO();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = 	"SELECT rd.*, ";
			sql += 	"CASE ";
			sql += 		"WHEN(rd.ceco IS NULL OR rd.ceco = '') THEN rd.ordenco ";
			sql += 		"ELSE rd.ceco ";
			sql += 	"END AS rdceco, ";
			sql += 	"f.codigo AS faena, cam.campo, c.especie, c.variedad, CONCAT(t.codigo, ' | ', t.rut, ' | ', t.nombre, ' ',t.apellidoPaterno) AS nombreTrabajador ";
			sql	+= 	"FROM rendimiento_diario rd ";
			sql	+= 	"LEFT JOIN trabajadores t ON (t.id = rd.trabajador) ";
			sql	+= 	"LEFT JOIN cuartel c ON(c.codigo = rd.cuartel) ";
			sql	+= 	"LEFT JOIN sector s ON (c.sector = s.sector) ";
			sql	+= 	"LEFT JOIN campo cam ON (cam.campo = s.campo OR cam.campo = rd.campo_rd) ";
			sql	+= 	"LEFT JOIN variedad v ON (v.codigo = c.variedad) ";
			sql	+= 	"LEFT JOIN especie e ON (e.codigo = c.especie) ";
			sql	+= 	"LEFT JOIN labor l ON (l.codigo = rd.labor) ";
			sql	+= 	"JOIN faena f ON (f.codigo = l.faena) ";
			sql	+= 	"WHERE rd.codigo = "+codigo_rd;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				e.setCodigo(rs.getInt("codigo"));
				e.setTrabajador(rs.getInt("trabajador"));
				e.setBase_piso_hora(rs.getInt("base_piso_hora"));
				e.setCuartel(rs.getInt("cuartel"));
				e.setLabor(rs.getInt("labor"));
				e.setValor(rs.getInt("valor"));
				e.setTipo_trato(rs.getInt("tipo_trato"));
				e.setRendimiento(rs.getFloat("rendimiento"));
				e.setValor_rendimiento(rs.getFloat("valor_rendimietno"));
				e.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				e.setHoras_extras(rs.getFloat("horas_extras"));
				e.setBono1(rs.getInt("bono1"));
				e.setBono2(rs.getInt("bono2"));
				e.setValor_liquido(rs.getFloat("valor_liquido"));
				e.setMaquinaria(rs.getInt("maquinaria"));
				e.setImplemento(rs.getInt("implemento"));
				e.setBus(rs.getInt("bus"));
				e.setFecha(rs.getString("fecha_i"));
				e.setSupervisor_i(rs.getInt("supervisor_i"));
				e.setFaena(rs.getInt("faena"));
				e.setNombre(rs.getString("campo"));
				e.setEspecie(rs.getInt("especie"));
				e.setVariedad(rs.getInt("variedad"));
				e.setNvnombre(rs.getString("nombreTrabajador").toUpperCase());
				e.setBaseCargo(rs.getString("base_cargo"));
				e.setIdContratista(rs.getString("rd_contratista"));
				e.setCeco(rs.getString("rdceco"));
				e.setN_personas(rs.getInt("n_personas"));
				e.setValor_hx(rs.getFloat("valor_hx"));
				e.setMonto_hx(rs.getFloat("monto_hx"));
				e.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				e.setHx_dos(rs.getFloat("hx_dos"));
				e.setEstado(rs.getInt("estado"));
				e.setMacroco(rs.getString("macroco"));
				e.setHoras_totales(rs.getFloat("horas_totales"));
				e.setRes_hx(rs.getInt("res_hx"));
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		} finally {
			db.close();
		}
		return e;
	}
	public static RENDIMIENTO_DIARIO GET_RD_INDIVIDUAL_RG (int codigo_rd, int codigo_rg)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		RENDIMIENTO_DIARIO e = new RENDIMIENTO_DIARIO();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT ";
			sql += 		"rd.*, f.codigo AS faena, cam.campo, c.especie, c.variedad, rg.fecha, rg.codigo_supervisor, ";
			sql += 		"CONCAT(trs.codigo, ' | ', trs.rut, ' | ', trs.nombre, ' ', trs.apellidoPaterno) AS nombreTrabajador ";
			sql	+= 	"FROM rendimiento_diario rd ";
			sql	+= 		"LEFT JOIN rendimiento_general rg ON(rd.codigo_rg = rg.codigo_rg) ";
			sql	+= 		"LEFT JOIN labor l ON(l.codigo = rd.labor) ";
			sql	+= 		"LEFT JOIN faena f ON(l.faena = f.codigo) ";
			sql	+= 		"LEFT JOIN trabajadores t ON(t.id = rd.supervisor_i) ";
			sql	+= 		"LEFT JOIN trabajadores trs ON(trs.id = rd.trabajador) ";
			sql	+= 		"LEFT JOIN campo cam ON(cam.campo = rd.campo_rd OR cam.campo = rg.campo) ";
			sql	+= 		"LEFT JOIN cuartel c ON(c.codigo = rd.cuartel) ";
			sql	+= 		"LEFT JOIN especie e ON(e.codigo = c.especie) ";
			sql	+= 		"LEFT JOIN variedad v ON(v.codigo = c.variedad) ";
			sql	+= 	"WHERE rd.codigo = "+codigo_rd+" AND rg.codigo_rg = "+codigo_rg;
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				e.setCodigo(rs.getInt("codigo"));
				e.setTrabajador(rs.getInt("trabajador"));
				e.setBase_piso_hora(rs.getInt("base_piso_hora"));
				e.setCuartel(rs.getInt("cuartel"));
				e.setLabor(rs.getInt("labor"));
				e.setValor(rs.getInt("valor"));
				e.setTipo_trato(rs.getInt("tipo_trato"));
				e.setRendimiento(rs.getFloat("rendimiento"));
				e.setValor_rendimiento(rs.getFloat("valor_rendimietno"));
				e.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				e.setHoras_extras(rs.getFloat("horas_extras"));
				e.setBono1(rs.getInt("bono1"));
				e.setBono2(rs.getInt("bono2"));
				e.setValor_liquido(rs.getFloat("valor_liquido"));
				e.setMaquinaria(rs.getInt("maquinaria"));
				e.setImplemento(rs.getInt("implemento"));
				e.setBus(rs.getInt("bus"));
				e.setFecha(rs.getString("fecha"));
				e.setSupervisor_i(rs.getInt("supervisor_i"));
				e.setFaena(rs.getInt("faena"));
				e.setNombre(rs.getString("campo"));
				e.setEspecie(rs.getInt("especie"));
				e.setVariedad(rs.getInt("variedad"));
				e.setNvnombre(rs.getString("nombreTrabajador").toUpperCase());
				e.setBaseCargo(rs.getString("base_cargo"));
				e.setIdContratista(rs.getString("rd_contratista"));
				e.setValor(rs.getInt("valor"));
				e.setSupervisor(rs.getString("codigo_supervisor"));
				e.setN_personas(rs.getInt("n_personas"));
				e.setValor_hx(rs.getFloat("valor_hx"));
				e.setMonto_hx(rs.getFloat("monto_hx"));
				e.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				e.setHx_dos(rs.getFloat("hx_dos"));
				e.setMacroco(rs.getString("macroco"));
				e.setCeco(rs.getString("ceco"));
				e.setOrdenco(rs.getString("ordenco"));
				e.setEstado(rs.getInt("estado"));
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		} finally {
			db.close();
		}
		return e;
	}
	public static CUADRILLA GET_RENDIMIENTOS_GENERALES (int codigo_rg)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		CUADRILLA CU = new CUADRILLA();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT ";
			sql += 		"rg.*, e.especie AS n_especie, v.variedad AS n_variedad, c.nombre AS nombreCuartel, f.faena AS nombreFaena, l.labor AS nombreLabor, tr.id, 	";
			sql += 		"CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre) AS trabajador, ";
			sql += 		"tr.rut, tr.codigo ";
			sql += 	"FROM rendimiento_general rg ";
			sql += 		"LEFT JOIN cuadrilla_trabajador ct ON (rg.codigo_cuadrilla = ct.codigo_cuadrilla) ";
			sql += 		"LEFT JOIN trabajadores tr ON (tr.rut = ct.rut_trabajador OR tr.rutTemporal = ct.rut_trabajador) ";
			sql += 		"LEFT JOIN cuartel c ON (c.codigo = rg.cuartel) ";
			sql += 		"LEFT JOIN especie e ON (e.codigo = c.especie) ";
			sql += 		"LEFT JOIN variedad v ON (v.codigo = c.variedad) ";
			sql += 		"LEFT JOIN labor l ON (l.codigo = rg.labor) ";
			sql += 		"LEFT JOIN faena f ON (f.codigo = l.faena) ";
			sql += 	"WHERE ";
			sql += 		"rg.codigo_rg = "+codigo_rg+" ";
			sql += 		"AND ct.asistencia IN (1 , 4) ";
			sql += 		"AND contratista IS NULL;";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			ArrayList<TrabajadoresAgro> trabajador = new ArrayList<TrabajadoresAgro>();
			ArrayList<RENDIMIENTO_GENERAL> rendimiento_general = new ArrayList<RENDIMIENTO_GENERAL>();
			int count = 1;
			while(rs.next()){
				TrabajadoresAgro tr = new TrabajadoresAgro();
				RENDIMIENTO_GENERAL rg = new RENDIMIENTO_GENERAL();
				if (count == 1){
					CU.setCodigo(rs.getInt("codigo_rg"));	
//					CU.setNombre_cuadrilla(rs.getString("nombre_cuadrilla"));
//					CU.setSupervisor(rs.getInt("supervisor"));
//					CU.setFecha_creacion(rs.getString("fecha_creacion"));
//					CU.setEstado(rs.getInt("estado"));
					rg.setCodigo(rs.getInt("codigo_rg"));
					rg.setFecha(rs.getString("fecha"));
					rg.setEspecie(rs.getInt("especie"));
					rg.setVariedad(rs.getInt("variedad"));
					rg.setCuartel(rs.getInt("cuartel"));
					rg.setFaena(rs.getInt("faena"));
					rg.setLabor(rs.getInt("labor"));
					rg.setHoras(rs.getFloat("horas"));
					rg.setTipo_pago(rs.getInt("tipo_pago"));
					rg.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
					rg.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
					rg.setNespecie(rs.getString("n_especie"));
					rg.setNvariedad(rs.getString("n_variedad"));
					rg.setNcuartel(rs.getString("nombreCuartel"));
					rg.setNfaena(rs.getString("nombreFaena"));
					rg.setNlabor(rs.getString("nombreLabor"));
					rg.setCampo(rs.getString("campo"));
					rg.setValor(rs.getInt("valor"));
					rg.setBase_piso_dia(rs.getInt("base_piso_dia"));
					rg.setMacro(rs.getString("macro"));
					rg.setCeco(rs.getString("ceco"));
					rg.setOrdenco(rs.getString("ordenco"));
					rendimiento_general.add(rg);
				}
				tr.setCodigo(rs.getString("codigo"));
				tr.setIdTrabajador(rs.getInt("id"));
				tr.setNombre(rs.getString("trabajador"));
				tr.setRut(rs.getString("rut"));
				trabajador.add(tr);
				count ++;	
			}
			CU.setTrab(trabajador);
			CU.setRendimiento_general(rendimiento_general);
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException e) {
			System.out.println("Error    :" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return CU;
	}
	public static boolean ORDEN_PAGO (String codigo, String orden, String n_factura, int valor_retencion)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql  = 	"UPDATE liquidacion_contratista ";
			sql += 	"SET orden_pago = "+orden+", estado = 3, estado_liquidacion = 2, n_factura = '"+n_factura+"', valor_retencion = "+valor_retencion+" ";
			sql += 	"WHERE codigo ="+codigo;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	public static boolean RECHAZAR (String codigo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE liquidacion_contratista SET estado = 2 where codigo ="+codigo;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			DELETE_LIQUIDACION_RENDIMIENTO(codigo);
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	
	public static boolean DELETE_LIQUIDACION_RENDIMIENTO (String codigo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE from liquidacion_rendimiento where codigo_liq = "+codigo;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static ArrayList<RENDIMIENTO_DIARIO> GREN_CON_BY_ID(int codigo_rd) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_DIARIO> data = new ArrayList<RENDIMIENTO_DIARIO>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = 	"SELECT rd.codigo, cam.descripcion campo, ";
			sql += 		"CASE ";
			sql += 			"WHEN rg.fecha IS NULL THEN rd.fecha_i ";
			sql += 			"ELSE rg.fecha ";
			sql += 		"END fecha_rendimiento, ";
			sql += 		"rg.contratista, CONCAT(trS.nombre, ' ', trS.apellidoPaterno) supervisor, CONCAT(tr.nombre, ' ', tr.apellidoPaterno) trabajador, ";
			sql += 		"tr.rut rut_trabajador, rd.rendimiento, rd.valor_liquido, es.especie nespecie, var.variedad nvariedad, ";
			sql += 		"IF(rd.cuartel = 0, ";
			sql += 			"CASE ";
			sql += 				"WHEN (rd.ceco = '' OR rd.ceco IS NULL) THEN rd.ordenco ";
			sql += 				"ELSE rd.ceco ";
			sql += 			"END, ";
			sql += 			"(SELECT CONCAT(nombre, ' ', codigo_cuartel) ";
			sql += 				"FROM cuartel ";
			sql += 			"WHERE codigo = rd.cuartel)) AS ceco, ";
			sql += 		"fa.faena nfaena, lab.labor nlabor, rd.rd_contratista, rd.bono1, rd.valor_rendimietno, rd.tipo_trato, ";
			sql += 		"CAST((rd.valor_rendimietno/rd.rendimiento)AS DECIMAL(18,3)) AS valor_x_rend, ";
			sql += 		"getValorLiquidacion("+codigo_rd+") AS totalLiquidacion, ";
			sql += 		"CAST((getValorLiquidacion("+codigo_rd+") * (0.19))AS SIGNED) AS iva, ";
			sql += 		"CAST((getValorLiquidacion("+codigo_rd+") * (0.19))+getValorLiquidacion("+codigo_rd+") AS SIGNED) AS total_liquido, ";
			sql += 		"lc.asiento, lc.semanas ";
			sql += 	"FROM rendimiento_diario rd ";
			sql += 		"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 		"LEFT JOIN trabajadores tr ON (tr.id = rd.trabajador)";
			sql += 		"LEFT JOIN trabajadores trS ON (trS.id = rg.codigo_supervisor OR trS.id = rd.supervisor_i) ";
			sql += 		"LEFT JOIN cuartel c ON (c.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN sector s ON (s.sector = c.sector) ";
			sql += 		"LEFT JOIN campo cam ON (cam.campo = s.campo OR cam.campo = rd.campo_rd OR cam.campo = rg.campo) ";
			sql += 		"LEFT JOIN especie es ON (es.codigo = c.especie) ";
			sql += 		"LEFT JOIN variedad var ON (var.codigo = c.variedad) ";
			sql += 		"LEFT JOIN labor lab ON (lab.codigo = rd.labor) ";
			sql += 		"LEFT JOIN faena fa ON (fa.codigo = lab.faena) ";
			sql += 		"LEFT JOIN liquidacion_contratista lc ON (lc.codigo = "+codigo_rd+") ";
			sql += 	"WHERE ";
			sql += 		"rd.codigo IN (SELECT codigo_rd FROM liquidacion_rendimiento WHERE codigo_liq = "+codigo_rd+") ";
			sql += 		"AND rd.estado = 3 ";
			sql += 	"ORDER BY rg.fecha ASC;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				RENDIMIENTO_DIARIO e = new RENDIMIENTO_DIARIO();
				e.setCodigo(rs.getInt("codigo"));
				e.setCampo(rs.getString("campo"));
				e.setFecha(rs.getString("fecha_rendimiento"));
				e.setIdContratista(rs.getString("rd_contratista"));
				e.setSupervisor(rs.getString("supervisor"));		
				e.setNombre(rs.getString("trabajador"));
				e.setnCeco(rs.getString("ceco"));
				e.setRut(rs.getString("rut_trabajador"));
				e.setValor_rendimiento(rs.getFloat("valor_rendimietno"));
				e.setRendimiento(rs.getFloat("rendimiento"));
				e.setTipo_pago(rs.getInt("tipo_trato"));
				e.setValor_liquido(rs.getFloat("valor_liquido"));
				e.setBono1(rs.getInt("bono1"));
				e.setnVariedad(rs.getString("nvariedad"));
				e.setnEspecie(rs.getString("nespecie"));
				e.setnFaena(rs.getString("nfaena"));
				e.setnLabor(rs.getString("nlabor"));
				e.setValor_trato(rs.getFloat("valor_x_rend"));
				e.setTotalLiquidacion(rs.getFloat("totalLiquidacion"));
				e.setIva(rs.getInt("iva"));
				e.setTotal_liquido(rs.getInt("total_liquido"));
				e.setDescripcion(rs.getString("asiento"));
				e.setSemanas(rs.getString("semanas"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}

	public static boolean DELETE_RENDIMIENTO_LIQUIDACION (int codigo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE from liquidacion_rendimiento where codigo_rd = "+codigo;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static boolean UPD_RENDIMIENTO_LIQUIDACION (int codigo, String n_factura)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "UPDATE liquidacion_contratista SET n_factura = '"+n_factura+"' where codigo ="+codigo;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static ArrayList<RENDIMIENTO_DIARIO> GET_DETALLE_HX_SEMANA (int trabajador, String fecha)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_DIARIO> data = new ArrayList<RENDIMIENTO_DIARIO>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = 	"SELECT SUM(rd.horas_extras)AS hx_dia, ";
			sql	+= 		"CASE ";
			sql	+= 			"WHEN (rd.fecha_i IS NULL) THEN rg.fecha ";
			sql	+= 			"ELSE rd.fecha_i ";
			sql	+= 		"END AS fecha, ";
			sql	+= 		"CASE ";
			sql	+= 			"WHEN (rd.fecha_i IS NULL) THEN DAYOFWEEK(rg.fecha) -1 ";
			sql	+= 			"ELSE DAYOFWEEK(rd.fecha_i) -1 ";
			sql	+= 		"END AS dia ";
			sql	+= 	"FROM rendimiento_diario rd ";
			sql	+= 	"LEFT JOIN rendimiento_general rg ON(rg.codigo_rg = rd.codigo_rg) ";
			sql	+= 	"WHERE (YEARWEEK(rd.fecha_i,1) = YEARWEEK('"+fecha+"',1) OR YEARWEEK(rg.fecha,1) = YEARWEEK('"+fecha+"',1)) ";
			sql	+= 	"AND rd.trabajador = "+trabajador+" ";
			sql	+= 	"GROUP BY 2, 3 ORDER BY 2";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			for(int i = 1; i < 8; i++){
				RENDIMIENTO_DIARIO e = new RENDIMIENTO_DIARIO();
				e.setCodigo(i);
				data.add(e);
			}
			while(rs.next()){
				data.get(rs.getInt("dia")-1).setCodigo(rs.getInt("dia"));
				data.get(rs.getInt("dia")-1).setFecha(rs.getString("fecha"));
				data.get(rs.getInt("dia")-1).setHoras_extras(rs.getFloat("hx_dia"));
			}
			rs.close();
			ps.close();
			db.conn.close();
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		} finally {
			db.close();
		}
		return data;
	}
	public static ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTOS_VALIDADOS (String campo, String fecha_desde, String fecha_hasta, String tipo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_GENERAL> lista = new ArrayList<RENDIMIENTO_GENERAL>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT rg.codigo_rg, rg.fecha, cam.campo as codigoCampo, cam.descripcion AS campo, t.nombre AS supervisor, cd.nombre_cuadrilla, fa.faena, la.labor, rg.horas, rg.tipo_pago, rg.folio, rg.contratista, e.especie, v.variedad, c.nombre ";
			sql	+= "FROM rendimiento_general rg ";
			sql	+= "LEFT JOIN cuartel c ON(c.codigo = rg.cuartel) ";
			sql	+= "RIGHT JOIN sector s ON(s.sector = c.sector) ";
			sql	+= "RIGHT JOIN campo cam ON(cam.campo = s.campo) ";
			sql	+= "JOIN trabajadores t ON(t.id = rg.codigo_supervisor) ";
			sql	+= "JOIN cuadrilla cd ON(cd.codigo = rg.codigo_cuadrilla) ";
			sql	+= "JOIN faena fa ON(fa.codigo = rg.faena) ";
			sql	+= "JOIN labor la ON(la.codigo = rg.labor) ";
			sql	+= "JOIN especie e ON(e.codigo = rg.especie) ";
			sql	+= "JOIN variedad v ON(v.codigo = rg.variedad) ";
			// " left join rendimiento_diario rd on rd.codigo_rg = rg.codigo_rg " +
			sql	+= "WHERE rg.fecha BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"' AND cam.campo = '"+campo+"' AND rg.estado = 3 ";
			if(tipo.equals("PLANTA")){
				sql += "AND rg.contratista IS NULL";
			}else{
				sql += "AND rg.contratista IS NOT NULL";
			}
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				RENDIMIENTO_GENERAL ob = new RENDIMIENTO_GENERAL();
				ob.setCodigo(rs.getInt("codigo_rg"));
				ob.setFecha(rs.getString("fecha"));
				ob.setCampo(rs.getString("campo"));
				ob.setNsupervisor(rs.getString("supervisor"));
				ob.setNcuadrilla(rs.getString("nombre_cuadrilla"));
				ob.setNlabor(rs.getString("labor"));
				ob.setHoras(rs.getInt("horas"));
				ob.setTipo_pago(rs.getInt("tipo_pago"));
				ob.setNespecie(rs.getString("especie"));
				ob.setNvariedad(rs.getString("variedad"));
				ob.setNfaena(rs.getString("faena"));
				ob.setNcuartel(rs.getString("nombre"));
				ob.setFolio(rs.getInt("folio"));
				ob.setContratista(rs.getString("contratista"));
				lista.add(ob);
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
		return lista;
	}
	public static ArrayList<RENDIMIENTO_GENERAL> GET_RENDIMIENTOS_VALIDADOS_INDIVIDUAL (String campo, String fecha_desde, String fecha_hasta, String tipo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_GENERAL> lista = new ArrayList<RENDIMIENTO_GENERAL>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "SELECT rd.codigo AS codigo_rg, rd.fecha_i, cam.descripcion, CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre) AS supervisor, CONCAT(trs.apellidoPaterno,' ',trs.apellidoMaterno,' ',trs.nombre) AS trabajador, f.faena, l.labor, rd.horas_trabajadas, "
					+ "rd.tipo_trato, e.especie, v.variedad, c.nombre, rd.ceco, CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre) nombreTrabajador ";
			sql	+= "FROM rendimiento_diario rd ";
			sql	+= "LEFT JOIN trabajadores t ON (t.id = rd.supervisor_i) ";
			sql	+= "JOIN trabajadores trs ON (trs.id = rd.trabajador) ";
			sql	+= "JOIN labor l ON (l.codigo = rd.labor) ";
			sql	+= "LEFT JOIN faena f ON (f.codigo = l.faena) ";
			sql	+= "LEFT JOIN cuartel c ON (c.codigo = rd.cuartel) ";
			sql	+= "LEFT JOIN variedad v ON (v.codigo = c.variedad)";
			sql	+= "LEFT JOIN especie e ON (e.codigo = v.especie) ";
			sql	+= "LEFT JOIN  sector s ON (s.sector = c.sector) ";
			sql	+= "LEFT JOIN  campo cam ON (cam.campo = s.campo or rd.campo_rd = cam.campo) ";
			sql	+= "WHERE rd.fecha_i BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"' AND rd.codigo_rg = 0 AND (cam.campo = '"+campo+"' or rd.campo_rd = '"+campo+"') AND rd.estado = 8 ";
			if(tipo.equals("PLANTA")){
				sql += "AND rd.rd_contratista = 0";
			}else{
				sql += "AND rd.rd_contratista != 0";
			}
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				RENDIMIENTO_GENERAL ob = new RENDIMIENTO_GENERAL();
				ob.setCodigo(rs.getInt("codigo_rg"));
				ob.setFecha(rs.getString("fecha_i"));
				ob.setCampo(rs.getString("descripcion"));
				ob.setNsupervisor(rs.getString("supervisor"));
				ob.setNcuadrilla(rs.getString("trabajador"));
				ob.setNlabor(rs.getString("labor"));
				ob.setHoras(rs.getFloat("horas_trabajadas"));
				ob.setTipo_pago(rs.getInt("tipo_trato"));
				ob.setNespecie(rs.getString("especie"));
				ob.setNvariedad(rs.getString("variedad"));
				ob.setNfaena(rs.getString("faena"));
				ob.setNcuartel(rs.getString("nombre"));
				ob.setNcontratista(rs.getString("ceco"));
				ob.trabajador = rs.getString("nombreTrabajador");
				lista.add(ob);
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
		return lista;
	}
	public static respuesta ADD_RENDIMIENTO_DIARIO(ConnectionDB db, RENDIMIENTO_DIARIO r, HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		respuesta res = new respuesta();
		res.setEstado(false);
		res.setObjeto(r);
		String sql = "";
		try {
			if(r.getOrdenco() == null){
				r.setOrdenco("");
			}
			if(r.getCeco() == null){
				r.setCeco("");
			}
			sql = "CALL sa_insertRendimientoDiario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, r.getTrabajador());
			ps.setFloat(2, r.getBase_piso_hora());
			ps.setInt(3, r.getSubsidio());
			ps.setInt(4, r.getCuartel());
			ps.setInt(5, r.getLabor());
			ps.setInt(6, r.getValor());
			ps.setInt(7, r.getTipo_trato());
			ps.setFloat(8, r.getRendimiento());
			ps.setFloat(9, r.getValor_rendimiento());
			ps.setFloat(10, r.getHoras_trabajadas());
			ps.setFloat(11, r.getHoras_extras());
			ps.setInt(12, r.getBono1());
			ps.setInt(13, r.getBono2());
			ps.setFloat(14, r.getValor_liquido());
			ps.setInt(15, r.getMaquinaria());
			ps.setInt(16, r.getImplemento());
			ps.setInt(17, r.getBus());
			ps.setInt(18, r.getEstado());
			ps.setInt(19, r.getCodigo_rg());
			ps.setInt(20, r.getCargo());
			ps.setString(21, r.getIdContratista());
			ps.setString(22, r.getBonoCargo());
			ps.setString(23, r.getBonoProduccion());
			ps.setString(24, r.getBaseFicha());
			ps.setString(25, r.getBaseCargo());
			ps.setFloat(26, r.getValor_hx());
			ps.setFloat(27, r.getMonto_hx());
			ps.setFloat(28, r.getHx_dos());
			ps.setFloat(29, r.getValor_hx_dos());
			ps.setString(30, r.getFecha());
			ps.setInt(31, r.getSupervisor_i());
			ps.setString(32, r.getMacroco());
			ps.setString(33, r.getCampo());
			ps.setInt(34, r.getN_personas());
			ps.setFloat(35, r.getHoras_totales());
			ps.setInt(36, r.getRes_hx());
			ps.setString(37, r.getCeco());
			ps.setString(38, r.getOrdenco());
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();

			
			if(rs.next()){
				System.out.println(rs.getString("res"));
				res.setMensaje(rs.getString("res"));
				if(rs.getString("res").equals("Ok")){
					res.setEstado(true);
				}
			}
			res.setObjeto(r);
			return res;
		} catch (SQLException e) {
			res.setObjeto(r);
			res.setMensaje("Error en la ejecucion de la cnsulta a la base de datos");
			System.out.println(e.getMessage());
			return res;
		} catch (Exception e) {
			res.setObjeto(r);
			res.setMensaje("Error en la ejecucion de la cnsulta a la base de datos:" + e.getMessage());
			System.out.println("Error Interno del sistema:" + e.getMessage());
			return res;
		} finally {
//			ps.close();
			db.close();
		}
	}
	public static boolean UPD_ORDEN_PAGO(LIQUIDACION e) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		String sql = "";
		try {
			sql += 	"UPDATE liquidacion_contratista ";
			sql += 	"SET orden_pago = ? ";
			sql += 	"WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, e.getOrden());
			ps.setInt(2, e.getCodigo());
			ps.execute();
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
	public static boolean UPD_ORDEN_PAGO_ASIENTO(LIQUIDACION e) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		String sql = "";
		try {
			sql += 	"UPDATE liquidacion_contratista ";
			sql += 	"SET asiento = ?, ";
			sql += 	"estado = 3, ";
			sql += 	"estado_liquidacion = 2, ";
			sql += 	"n_factura = ?, ";
			sql += 	"valor_retencion = ?, ";
			sql += 	"asiento = ?, ";
			sql += 	"anticipo = ?, ";
			sql += 	"fecha_pago = ? ";
			sql += 	"WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, e.getAsiento_contable());
			ps.setString(2, e.getN_factura());
			ps.setInt(3, e.getValor_retencion());
			ps.setString(4, e.getAsiento_contable());
			ps.setInt(5, e.getAnticipo());
			ps.setString(6, e.getFecha_pago());
			ps.setInt(7, e.getCodigo());
			ps.execute();
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
	public static ArrayList<asiento_contable> GEN_ASIENTO_CONTABLE(int codigo_rd) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<asiento_contable> data = new ArrayList<asiento_contable>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql += 	"SELECT ";
			sql += 		"IF(t.ordenco != '', '6101021053', t.cuenta) as cuenta, ";
			sql += 		"t.ceco, t.ordenco, t.valor ";
			sql += 	"FROM ";
			sql += 		"(SELECT ";
			sql += 			"f.cuenta_prd as cuenta, ";
			sql += 			"IF(rd.ceco is null or rd.ceco = '', '', rd.ceco) AS ceco, ";
			sql += 			"IF(rd.ordenco is null or rd.ordenco = '', '', rd.ordenco) AS ordenco,";
			sql += 			"CAST(SUM(rd.valor_liquido)AS SIGNED) AS valor ";
			sql += 		"FROM faena f join labor l ON(l.faena = f.codigo) ";
			sql += 			"JOIN rendimiento_diario rd ON(rd.labor = l.codigo) ";
			sql += 			"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 			"LEFT JOIN cuartel c on(c.codigo = rd.cuartel) ";
			sql += 		"WHERE rd.codigo IN (SELECT codigo_rd ";
			sql += 				"FROM liquidacion_rendimiento ";
			sql += 				"WHERE codigo_liq = "+codigo_rd+") ";
			sql += 			"AND rd.estado = 3 ";
			sql += 		"GROUP BY 2 , 3 , 1) t";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				asiento_contable e = new asiento_contable();
				e.setCuenta(rs.getString("cuenta"));
				e.setCeco(rs.getString("ceco"));
				e.setOrdenco(rs.getString("ordenco"));
				e.setValor(rs.getFloat("valor"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	public static RENDIMIENTO_GENERAL GET_REND_GNRAL(int codigo_rg) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		RENDIMIENTO_GENERAL e = new RENDIMIENTO_GENERAL();
		ConnectionDB db = new ConnectionDB();
		try{
			sql += 	"SELECT rg.*, COUNT(ct.codigo) AS n_trab ";
			sql +=	"FROM rendimiento_general rg ";
			sql +=	"LEFT JOIN cuadrilla_trabajador ct ON(rg.codigo_cuadrilla = ct.codigo_cuadrilla) ";
			sql +=	"WHERE rg.codigo_rg = "+codigo_rg;
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()) {
				e.setCodigo(rs.getInt("codigo_rg"));
				e.setFecha(rs.getString("fecha"));
				e.setEspecie(rs.getInt("especie"));
				e.setVariedad(rs.getInt("variedad"));
				e.setCuartel(rs.getInt("cuartel"));
				e.setFaena(rs.getInt("faena"));
				e.setLabor(rs.getInt("labor"));
				e.setHoras(rs.getInt("horas"));
				e.setTipo_pago(rs.getInt("tipo_pago"));
				e.setCodigo_cuadrilla(rs.getInt("codigo_cuadrilla"));
				e.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
				e.setMacro(rs.getString("macro"));
				e.setCeco(rs.getString("ceco"));
				e.setOrdenco(rs.getString("ordenco"));
				e.setN_trab(rs.getInt("n_trab"));
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return e;
	}
	public static respuesta UPD_RENDIMIENTO_DIARIO(ConnectionDB db, RENDIMIENTO_DIARIO r) throws Exception{
		PreparedStatement ps = null;
		respuesta res = new respuesta();
		res.setEstado(false);
		res.setObjeto(r);
		String sql = "";
		try {
			if(r.getOrdenco() == null){
				r.setOrdenco("");
			}
			if(r.getCeco() == null){
				r.setCeco("");
			}
			sql = "UPDATE rendimiento_diario SET ";
			sql += "trabajador = ?, base_piso_hora = ?, subsidio = ?, ";
			sql += "cuartel = ?, labor = ?, valor = ?, ";
			sql += "tipo_trato = ?, rendimiento = ?, valor_rendimietno = ?, ";
			sql += "horas_trabajadas = ?, horas_extras = ?, bono1 = ?, ";
			sql += "bono2 = ?, valor_liquido = ?, maquinaria = ?, ";
			sql += "implemento = ?, bus = ?, estado = ?, ";
			sql += "cargo = ?, rd_contratista = ?, ";
			sql += "bono_cargo = ? , bono_produccion = ?, base_ficha = ?, ";
			sql += "base_cargo = ? , valor_hx = ?, monto_hx = ?, ";
			sql += "hx_dos = ? , valor_hx_dos = ?, fecha_i = ?, ";
			sql += "supervisor_i = ? , macroco = ?, ceco = (SELECT CASE ('"+r.getCeco()+"') WHEN('')THEN MAX(IF(estado = 1, ceco, NULL)) ELSE '"+r.getCeco()+"' END AS ceco FROM cuartel WHERE codigo = "+r.getCuartel()+"), ";
			sql += "campo_rd = ? , n_personas = ?, horas_totales = ?, ";
			sql += "ordenco = (SELECT CASE ('"+r.getOrdenco()+"') WHEN('')THEN MAX(IF(estado = 2, ordenco, NULL)) ELSE '"+r.getOrdenco()+"' END AS ordenco FROM cuartel WHERE codigo = "+r.getCuartel()+"), res_hx = ? ";
			sql += "WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, r.getTrabajador());
			ps.setFloat(2, r.getBase_piso_hora());
			ps.setInt(3, r.getSubsidio());
			ps.setInt(4, r.getCuartel());
			ps.setInt(5, r.getLabor());
			ps.setInt(6, r.getValor());
			ps.setInt(7, r.getTipo_trato());
			ps.setFloat(8, r.getRendimiento());
			ps.setFloat(9, r.getValor_rendimiento());
			ps.setFloat(10, r.getHoras_trabajadas());
			ps.setFloat(11, r.getHoras_extras());
			ps.setInt(12, r.getBono1());
			ps.setInt(13, r.getBono2());
			ps.setFloat(14, r.getValor_liquido());
			ps.setInt(15, r.getMaquinaria());
			ps.setInt(16, r.getImplemento());
			ps.setInt(17, r.getBus());
			ps.setInt(18, r.getEstado());
			ps.setInt(19, r.getCargo());
			ps.setString(20, r.getIdContratista());
			ps.setString(21, r.getBonoCargo());
			ps.setString(22, r.getBonoProduccion());
			ps.setString(23, r.getBaseFicha());
			ps.setString(24, r.getBaseCargo());
			ps.setFloat(25, r.getValor_hx());
			ps.setFloat(26, r.getMonto_hx());
			ps.setFloat(27, r.getHx_dos());
			ps.setFloat(28, r.getValor_hx_dos());
			ps.setString(29, r.getFecha());
			ps.setInt(30, r.getSupervisor_i());
			ps.setString(31, r.getMacroco());
			ps.setString(32, r.getCampo());
			ps.setInt(33, r.getN_personas());
			ps.setFloat(34, r.getHoras_totales());
			ps.setFloat(35, r.getRes_hx());
			ps.setInt(36, r.getCodigo());
			ps.execute();
			System.out.println(ps.toString());
			res.setEstado(true);
			return res;
		} catch (SQLException e) {
			System.out.println("Error error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error error 2:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
			
		}
		return res;
	}
	public static  ArrayList<cierre_mensual> GET_CIERRE_MENSUAL(String campo, String periodo) throws Exception{
		CallableStatement cStmt = null;
		String sql = "";
		ArrayList<cierre_mensual> data = new ArrayList<cierre_mensual>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "call sa_getCierrePeriodo('"+periodo+"-01', '"+campo+"')";
			System.out.println(sql);
			cStmt = db.conn.prepareCall(sql);
			ResultSet rs = cStmt.executeQuery(sql);
			while(rs.next()){
				cierre_mensual e = new cierre_mensual();
				e.setId(rs.getInt("id"));
				e.setCodigo(rs.getInt("tr.codigo"));
				e.setTrabajador(rs.getString("trabajador"));
				e.setCuenta(rs.getString("cuenta"));
				e.setCeco(rs.getString("ceco"));
				e.setOrdenco(rs.getString("ordenco"));
				e.setValor(rs.getFloat("valor"));
				e.setPercent(rs.getFloat("percent"));
				e.setCosto_empresa(rs.getInt("costo_empresa"));
				e.setSociedadCentralizacion(rs.getString("sociedadCentralizacion"));
				e.setSociedadImputacion(rs.getString("sociedadImputacion"));
				e.setPeriodo(rs.getString("periodo"));
				e.setP_hx(rs.getFloat("p_hx"));
				e.setP_bono(rs.getFloat("p_bono"));
				e.setP_bono_dos(rs.getFloat("p_bono_dos"));
				e.setP_valor_rendimiento(rs.getFloat("p_valor_rendimiento"));
				e.setP_base_dia(rs.getFloat("p_base_dia"));
				data.add(e);
			}
			db.conn.close();
		}catch(Exception ex){
			System.out.println("Error: "+ex.getMessage());
		}finally{
			db.close();
		}
		return data;
	}
//	public static ArrayList<cierre_mensual> GET_CIERRE_MENSUAL(String campo, String periodo) throws Exception{
//		PreparedStatement ps = null;
//		String sql = "";
//		ArrayList<cierre_mensual> data = new ArrayList<cierre_mensual>();
//		ConnectionDB db = new ConnectionDB();
//		try{
//			sql = 	"SELECT tr.id, ";
//			sql += 	"CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ',tr.nombre) AS trabajador, ";
//			sql += 	"f.cuenta_prd AS cuenta, tr.codigo, ";
//			sql += 	"CASE ";
//			sql += 		"WHEN ((SELECT sociedad from campo where campo = cm.campo) != '"+campo+"') THEN ";
//			sql += 			"(SELECT ceco from camos_ceco where origen = '"+campo+"' and destino = (SELECT sociedad from campo where campo = cm.campo)) ";
//			sql += 		"ELSE ";
//			sql += 			"CASE WHEN (rd.cuartel = 0) THEN rd.ceco ";
//			sql += 				"ELSE CASE ";
//			sql += 					"WHEN (c.estado = 1) THEN c.ceco ";
//			sql += 					"ELSE '' ";
//			sql += 				"END ";
//			sql += 			"END ";
//			sql += 	"END AS ceco, ";
//			sql += 	"CASE ";
//			sql += 		"WHEN(rd.cuartel = 0)THEN ";
//			sql += 				"rd.ordenco ";
//			sql += 		"ELSE ";
//			sql += 			"CASE ";
//			sql += 				"WHEN(c.estado = 2) THEN c.ordenco ";
//			sql += 				"ELSE '' ";
//			sql += 			"END ";
//			sql += 	"END AS ordenco, ";
//			sql +=  "vla.liquido * GETPORCENTAJEVALOR(tr.id, DATE_FORMAT('"+periodo+"-01', '%Y%m'), SUM(rd.valor_liquido), '"+campo+"') / 100 costo_empresa, ";
//			sql += 	"SUM(rd.valor_liquido) AS valor, ";
//			sql += 	"getPorcentajeValor(tr.id, DATE_FORMAT('"+periodo+"-01', '%Y%m'), SUM(rd.valor_liquido), '"+campo+"') AS percent, ";
//			sql +=  "s.sociedad sociedadCentralizacion, cm.sociedad sociedadImputacion, DATE_FORMAT('2018-09-01', '%Y%m') periodo ";
//			sql += 	"FROM faena f join labor l ON(l.faena = f.codigo) ";
//			sql += 	"JOIN rendimiento_diario rd ON(rd.labor = l.codigo) ";
//			sql += 	"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
//			sql += 	"LEFT JOIN cuartel c on(c.codigo = rd.cuartel) ";
//			sql += 	"LEFT JOIN trabajadores tr ON(rd.trabajador = tr.id) ";
//			sql +=  "left join vw_liquidoAgro vla ON vla.codTrabajador = tr.codigo and vla.periodo = DATE_FORMAT('"+periodo+"-01', '%Y%m')";
//			sql +=  "LEFT JOIN contratos ct on ct.id = rd.idContrato ";
//			sql +=  "left join sociedad s on s.idSociedad = ct.idSociedad "
//					+ "left join campo cm on (cm.campo = rd.campo_rd or cm.campo = rg.campo) ";
//			sql += 	"WHERE (DATE_FORMAT(rd.fecha_i, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m') ";
//			sql += 		"OR DATE_FORMAT(rg.fecha, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m')) ";
//			sql += 		"AND rd.estado = 3 ";
//			sql += " and s.sociedad = '"+campo+"' and rd.rd_contratista = 0 ";
//			//sql += 		"AND (rd.campo_rd in (select campo from campo where sociedad = '"+campo+"')  "
//			//		+ " OR rg.campo in (select campo from campo where sociedad = '"+campo+"') ) "
//			//				+ " AND rd.rd_contratista = 0";
//			sql += 	"GROUP BY 1 , 3  , 5 ,6,s.sociedad ,cm.sociedad, vla.liquido;";
//			System.out.println(sql);
//			ps = db.conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery(sql);
//			while (rs.next()) {
//				cierre_mensual e = new cierre_mensual();
//				e.setId(rs.getInt("id"));
//				e.setCodigo(rs.getInt("tr.codigo"));
//				e.setTrabajador(rs.getString("trabajador"));
//				e.setCuenta(rs.getString("cuenta"));
//				e.setCeco(rs.getString("ceco"));
//				e.setOrdenco(rs.getString("ordenco"));
//				e.setValor(rs.getFloat("valor"));
//				e.setPercent(rs.getFloat("percent"));
//				e.setCosto_empresa(rs.getInt("costo_empresa"));
//				e.setSociedadCentralizacion(rs.getString("sociedadCentralizacion"));
//				e.setSociedadImputacion(rs.getString("sociedadImputacion"));
//				e.setPeriodo(rs.getString("periodo"));
//				data.add(e);
//			}
//			rs.close();
//			ps.close();
//			db.conn.close();
//		}catch (SQLException ex){
//			System.out.println("Erro:" + ex.getMessage());
//		}catch (Exception ex){
//			System.out.println("Error:" + ex.getMessage());
//		}finally {
//			db.close();
//		}
//		return data;
//	}
	public static boolean ADD_CIERRE_MENSUAL(ConnectionDB db, String periodo, String sociedad) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		try {
			sql +=	"CALL sa_insertCierrreMensual('"+periodo+"-01', '"+sociedad+"')";			
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error  fghfgh:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ytyrtyr:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean DELETE_CIERRE_MENSUAL (ConnectionDB db, String periodo, String sociedad)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		try {
			sql = "DELETE FROM cierre_periodo WHERE periodo = DATE_FORMAT('"+periodo+"-01', '%Y%m') and sociedad_centralizacion = '"+sociedad+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
//			db.conn.close();
		}
		return false;
	}
	public static ArrayList<trabajadores>  GET_ALL_TRABAJADORES(int id) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		try {
			String sql = "";
			sql += 	"SELECT DISTINCT ";
			sql += 		"tr.id, tr.idHuerto, tr.rut, CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre) AS nombre ";
			sql += 	"FROM trabajadores tr ";
			sql += 		"LEFT JOIN contratos ct ON(tr.codigo = ct.codigo_trabajador) ";
			sql += 	"WHERE ";
			sql += 		"idHuerto IN (SELECT codigo_campo FROM usuario_campo WHERE codigo_usuario = "+id+") ";
			sql += 		"AND tr.id IN (SELECT DISTINCT trabajador FROM rendimiento_diario);";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajadores e = new trabajadores();
				e.setId(rs.getInt("id"));
				e.setRut(rs.getString("rut"));
				e.setNombre(rs.getString("nombre"));
				e.setD_descripcion(rs.getString("idHuerto"));
				data.add(e);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return data;
	}
	public static ArrayList<RENDIMIENTO_DIARIO> GETLISTADO_CODIFICADO(String fecha_desde, String fecha_hasta, 
			String campo, String especie, String variedad, String faena, 
			String labor, String trabajador, String tipo_trabajador,String contratista,String cuartel, int estado) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_DIARIO> lista = new ArrayList<RENDIMIENTO_DIARIO>();
		ConnectionDB db = new ConnectionDB();
		try {
			System.out.println(campo);
			sql += 	"SELECT ";
			sql += 		"rd.*, t.rut, t.codigo,c.especie, e.especie AS nespecie, l.faena, ";
			sql += 		"UPPER(CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre)) AS nTrabajador, ";
			sql += 		"UPPER(CONCAT(trs.apellidoPaterno, ' ', trs.apellidoMaterno, ' ', trs.nombre)) AS supervisor, ";
			sql += 		"CASE ";
			sql += 			"WHEN (rg.fecha IS NULL) THEN rd.fecha_i ";
			sql += 			"ELSE rg.fecha ";
			sql += 		"END fecha_rendimiento, ";
			sql += 		"c.nombre AS nvnombre, cam.descripcion, c.variedad, v.variedad AS nVariedad, f.faena AS nFaena, l.labor AS nLabor, er.descripcion AS nestado, ";
			sql += 		"CASE ";
			sql += 			"WHEN (rd.cuartel != 0) THEN c.ceco ";
			sql += 			"ELSE rd.ceco ";
			sql += 		"END AS cecoPulento, ";
			sql += 		"CAST((rd.valor_rendimietno / rd.rendimiento) AS DECIMAL(10,2)) AS valor_trato, ";
			sql += 		"et.descripcion AS etnia, ";
			sql += 		"vla.costo_empresa AS costo_empresa, ";
			sql += 		"IF(c.estado IS NULL, 2, c.estado) AS c_estado ";
			sql += 	"FROM rendimiento_diario rd ";
			sql += 		"LEFT JOIN rendimiento_general rg ON(rd.codigo_rg = rg.codigo_rg) ";
			sql += 		"LEFT JOIN trabajadores t ON(t.id = rd.trabajador) ";
			sql += 		"LEFT JOIN trabajadores trs ON(trs.id = rg.codigo_supervisor OR trs.id = rd.supervisor_i) ";
			sql += 		"LEFT JOIN (SELECT *FROM parametros WHERE codigo = 'ETNIA') et ON(et.llave = t.idEtnia) ";
			sql += 		"LEFT JOIN (SELECT *FROM vw_costoEmpresaAgro WHERE periodo >= DATE_FORMAT('"+fecha_desde+"', '%Y%m') and periodo <=  DATE_FORMAT('"+fecha_hasta+"', '%Y%m')) vla ON(rd.codigo = vla.codigo)";
			sql += 		"LEFT JOIN estado_rendimiento er ON(er.codigo = rd.estado) ";
			sql += 		"LEFT JOIN cuartel c ON(c.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN especie e ON(e.codigo = c.especie) ";
			sql += 		"LEFT JOIN variedad v ON(v.codigo = c.variedad) ";
			sql += 		"LEFT JOIN campo cam ON(cam.campo = rd.campo_rd OR cam.campo = rg.campo) ";
			sql += 		"LEFT JOIN sector s ON (s.sector = c.sector) ";
			sql += 		"LEFT JOIN labor l ON(l.codigo = rd.labor) ";
			sql += 		"LEFT JOIN faena f ON(f.codigo = l.faena) ";
			sql += 	"WHERE ";
			sql += 		"(rg.fecha BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"' OR rd.fecha_i BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"')";
			sql += 		"AND (cam.campo IN ("+campo+")) ";
			sql += 		"AND (rg.especie IN ('"+especie+"') or '"+especie+"' = 0) ";
			sql += 		"AND (v.codigo IN ('"+variedad+"') or '"+variedad+"' = 0) ";
			sql += 		"AND (f.codigo IN ('"+faena+"') or '"+faena+"' = 0) ";
			sql += 		"AND (rd.labor IN ('"+labor+"') or '"+labor+"' = 0) ";
			sql += 		"AND (rd.trabajador IN ('"+trabajador+"') or '"+trabajador+"' = 0) ";
			sql += 		"AND (rd.cuartel IN ('"+cuartel+"') or '"+cuartel+"' = 0) ";
			sql += 		"AND (rd.rd_contratista IN ('"+contratista+"') or '"+contratista+"' = 0) ";
			sql += 		"AND (rd.estado IN ('"+estado+"') or '"+estado+"' = 0) ";
			if(tipo_trabajador.equals("1")) {
				sql += " AND (rd.rd_contratista = 0 or rd.rd_contratista = '' or rd.rd_contratista is null)";
			}
			if(tipo_trabajador.equals("2")) {
				sql += " AND (rd.rd_contratista != 0 and rd.rd_contratista != '' and rd.rd_contratista is not null)";
			}
			sql += " AND rd.estado != 7";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				RENDIMIENTO_DIARIO ob = new RENDIMIENTO_DIARIO();
				ob.setCodigo(rs.getInt("rd.codigo"));
				ob.setNombre(rs.getString("nTrabajador"));
				ob.setRut(rs.getString("rut"));
				ob.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				ob.setHoras_extras(rs.getFloat("horas_extras"));
				ob.setEspecie(rs.getInt("especie"));
				ob.setVariedad(rs.getInt("variedad"));
				ob.setFaena(rs.getInt("faena"));
				ob.setLabor(rs.getInt("labor"));
				ob.setValor(rs.getInt("valor"));
				ob.setValor_rendimiento(rs.getFloat("valor_rendimietno"));
				ob.setRendimiento(rs.getFloat("rendimiento"));
				ob.setTipo_pago(rs.getInt("tipo_trato"));
				ob.setValor_liquido(rs.getInt("valor_liquido"));
				ob.setBono1(rs.getInt("bono1"));
				ob.setBono2(rs.getInt("bono2"));
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setImplemento(rs.getInt("implemento"));
				ob.setFecha(rs.getString("fecha_rendimiento")); 
				ob.setNvnombre(rs.getString("nvnombre"));
				ob.setDescripcion(rs.getString("descripcion"));
				ob.setnVariedad(rs.getString("nVariedad"));
				ob.setnEspecie(rs.getString("nEspecie"));
//				ob.setnMaquinaria(rs.getString("nMaquinaria"));
//				ob.setnImplemento(rs.getString("nImplemento"));
				ob.setnFaena(rs.getString("nFaena"));
				ob.setnLabor(rs.getString("nLabor"));
				ob.setSupervisor(rs.getString("supervisor"));
				ob.setBase_piso_hora(rs.getInt("base_piso_hora"));
				ob.setIdContratista(rs.getString("rd_contratista"));
				ob.setNestado(rs.getString("nestado"));
				ob.setValor_hx(rs.getFloat("valor_hx"));
				ob.setMonto_hx(rs.getFloat("monto_hx"));
				ob.setHx_dos(rs.getFloat("hx_dos"));
				ob.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				ob.setMacroco(rs.getString("macroco"));
				ob.setCeco(rs.getString("ceco"));
				ob.setOrdenco(rs.getString("ordenco"));
				ob.setRes_hx(rs.getInt("res_hx"));
				ob.setSubsidio(rs.getInt("subsidio"));
				ob.setValor_trato(rs.getFloat("valor_trato"));
				ob.setTrabajador(rs.getInt("t.codigo"));
				ob.setCuartel(rs.getInt("rd.cuartel"));
				ob.setCosto_empresa(rs.getInt("costo_empresa"));
				ob.setEtnia(rs.getString("etnia"));
				ob.setEstado(rs.getInt("c_estado"));
				ob.setN_personas(rs.getInt("rd.n_personas"));
				lista.add(ob);
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
		return lista;
	}
	public static ArrayList<cierre_mensual> GET_CIERRE_TERCEROS(String campo, String periodo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<cierre_mensual> data = new ArrayList<cierre_mensual>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = 	"CALL sa_getCierreTerceros('"+periodo+"-01', '"+campo+"');";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				cierre_mensual e = new cierre_mensual();
				e.setId(rs.getInt("id"));
				e.setCodigo(rs.getInt("tr.codigo"));
				e.setTrabajador(rs.getString("trabajador"));
				e.setCuenta(rs.getString("cuenta"));
				e.setCeco(rs.getString("ceco"));
				e.setOrdenco(rs.getString("ordenco"));
				e.setValor(rs.getFloat("valor"));
				e.setPercent(rs.getFloat("percent"));
				e.setCosto_empresa(rs.getInt("costo_empresa"));
				e.setSociedadCentralizacion(rs.getString("sociedadCentralizacion"));
				e.setSociedadImputacion(rs.getString("sociedadImputacion"));
				e.setPeriodo(rs.getString("periodo"));
				e.setNombreCen(rs.getString("nombreCen"));
				e.setNombreIm(rs.getString("nombreIm"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	public static boolean ADD_CIERRE_TERCEROS(ConnectionDB db, String periodo, String campo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		try {
			sql +=	"CALL sa_insertCierreTerceros('"+periodo+"-01', '"+campo+"')";		
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error  fghfgh:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error ytyrtyr:" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static boolean DELETE_CIERRE_TERCEROS (ConnectionDB db, String periodo, String sociedad)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		try {
			sql = "DELETE FROM cierre_terceros WHERE periodo = DATE_FORMAT('"+periodo+"-01', '%Y%m') and sociedad_imputacion = '"+sociedad+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
//			db.conn.close();
		}
		return false;
	}
	public static ArrayList<String[]> GET_REVISION_ASISTENCIA (String campo, String[] fechas, int estado)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		ArrayList<String[]> data = new ArrayList<>();
		try {
			sql += 	"SELECT ";
			sql += 		"t.codigo, t.nombre, t.tipo_contrato, DATE_FORMAT(t.fechaInicio_actividad, '%d-%m-%Y') AS fechaInicio, ";
			sql += 		"CASE ";
			sql += 			"WHEN (DATE_FORMAT(t.FechaTerminoContrato, '%Y%m') > DATE_FORMAT('"+fechas[0]+"', '%Y%m')) THEN '' ";
			sql += 			"ELSE DATE_FORMAT(t.FechaTerminoContrato, '%d-%m-%Y') ";
			sql += 		"END AS fechaTermino, ";
			sql += 		"t.dias_mes, ";
			sql += 		"MAX(IF(t.faltas IS NULL, 0, t.faltas)) AS fallas, ";
			sql += 		"IF(t.faltas IS NULL,t.dias_mes,t.dias_mes - t.faltas) AS dias_trabajados ";
			for(int i = 0; i < fechas.length; i++){
				sql += 	", MAX(IF(t.fecha = '"+fechas[i]+"', t.horas, ";
				sql += 		"(SELECT pld.descripcion FROM permisos_licencias_desc pld ";
				sql += 			"LEFT JOIN permiso_licencia pl ON (pl.accion = pld.id) ";
				sql += 		"WHERE pl.codigo_trabajador = t.codigo AND pl.fecha_desde <= '"+fechas[i]+"' AND pl.fecha_hasta >= '"+fechas[i]+"' AND pl.idContrato = t.id ";
				sql += 		"LIMIT 1))) AS '"+fechas[i]+"' ";
			}
			sql += 	"FROM ";
			sql += 		"(SELECT ";
			sql += 			"tr.codigo, dt.dias_mes, df.faltas, ct.id, ";
			sql += 			"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre, ";
			sql += 			"p.descripcion AS tipo_contrato, ";
			sql += 			"ct.fechaInicio_actividad, ";
			sql += 			"CASE WHEN (ct.FechaTerminoContrato IS NULL)THEN '' ELSE ct.FechaTerminoContrato END AS FechaTerminoContrato, ";
			sql += 			"rd.fecha, ";
			sql += 			"rd.horas AS horas ";
			sql += 		"FROM ";
			sql += 			"sw_fechas f ";
			sql += 			"LEFT JOIN contratos ct ON (ct.fechaInicio_actividad <= f.fecha) ";
			sql += 				"AND (ct.FechaTerminoContrato >= f.fecha OR ct.FechaTerminoContrato IS NULL) ";
			sql += 			"LEFT JOIN trabajadores tr ON tr.codigo = ct.codigo_trabajador ";
			sql += 			"LEFT JOIN (SELECT * FROM parametros WHERE codigo = 'TIPO_CONTRATO') p ON (ct.tipoContrato = p.llave) ";
			sql += 			"LEFT JOIN(SELECT ";
			sql += 					"SUM(rd.horas_trabajadas) AS horas, rd.idContrato, rd.trabajador, rd.estado, ";
			sql += 					"CASE WHEN(rd.fecha_i IS NULL) THEN rg.fecha ELSE rd.fecha_i END AS fecha ";
			sql += 				"FROM ";
			sql += 					"rendimiento_diario rd ";
			sql += 					"LEFT JOIN rendimiento_general rg ON(rd.codigo_rg = rg.codigo_rg) ";
			sql += 				"WHERE ";
			sql += 					"(rd.rd_contratista = 0 OR rd.rd_contratista IS NULL) ";
			sql += 				"GROUP BY 2,3,4,5)rd ON(rd.idContrato = ct.id AND DATE_FORMAT(rd.fecha, '%Y%m') = DATE_FORMAT('"+fechas[0]+"', '%Y%m')) ";
			sql += 			"LEFT JOIN (SELECT ";
			sql += 					"IF(COUNT(f.fecha) > 30, 30, CASE ";
			sql += 						"WHEN (DATE_FORMAT('"+fechas[0]+"', '%m') = 02) THEN IF(COUNT(f.fecha) = 28, 30, COUNT(f.fecha)) ";
			sql += 						"ELSE COUNT(f.fecha) ";
			sql += 					"END) AS dias_mes, ";
			sql += 					"c.id ";
			sql += 				"FROM ";
			sql += 					"contratos c ";
			sql += 					"LEFT JOIN sw_fechas f ON (DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+fechas[0]+"', '%Y%m')) ";
			sql += 				"WHERE ";
			sql += 					"c.fechaInicio_actividad <= f.fecha ";
			sql += 					"AND (c.FechaTerminoContrato >= f.fecha OR c.FechaTerminoContrato IS NULL) ";
			sql += 					"AND DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+fechas[0]+"', '%Y%m') ";
			sql += 				"GROUP BY 2) dt ON (dt.id = ct.id) ";
			sql += 			"LEFT JOIN (SELECT ";
			sql += 					"COUNT(f.fecha) AS faltas, c.id ";
			sql += 				"FROM ";
			sql += 					"sw_fechas f ";
			sql += 					"LEFT JOIN contratos c ON (c.fechaInicio_actividad <= f.fecha AND (c.FechaTerminoContrato >= f.fecha OR c.FechaTerminoContrato IS NULL)) ";
			sql += 				"WHERE ";
			sql += 					"c.fechaInicio_actividad <= f.fecha ";
			sql += 					"AND (c.FechaTerminoContrato >= f.fecha OR c.FechaTerminoContrato IS NULL) ";
			sql += 					"AND DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+fechas[0]+"', '%Y%m') ";
			sql += 					"AND WEEKDAY(f.fecha) <= 4 ";
			sql += 					"AND f.fecha NOT IN (SELECT ";
			sql += 							"CASE ";
			sql += 								"WHEN (rd.fecha_i IS NULL) THEN rg.fecha ";
			sql += 								"ELSE rd.fecha_i ";
			sql += 							"END AS fecha ";
			sql += 						"FROM ";
			sql += 							"rendimiento_diario rd ";
			sql += 							"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 						"WHERE ";
			sql += 							"rd.idContrato = c.id) ";
			sql += 					"AND f.fecha NOT IN (SELECT fechaFeriado FROM sw_m_feriados WHERE DATE_FORMAT(fechaFeriado, '%Y%m') = DATE_FORMAT('"+fechas[0]+"', '%Y%m')) ";
			sql += 				"GROUP BY 2) df ON (df.id = ct.id) ";
			sql += 		"WHERE ";
			sql += 			"(DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+fechas[0]+"', '%Y%m')) ";
			sql += 			"AND tr.agro = 1 ";
			if(estado != 0){
				sql += 		"AND (rd.estado = "+estado+" ";
			}else{
				sql += 		"AND (rd.estado != 7 ";
			}
			sql += 			"OR rd.estado IS NULL) ";
			sql += 			"AND (ct.idHuertoContrato = '"+campo+"')) AS t ";
			sql += 	"GROUP BY t.codigo , t.nombre , 3 , 4 , 5 , 6 , 8 ";
			sql +=	"ORDER BY t.nombre, 3;";
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
					datos += horas+",";
				}
				String [] dataAux = datos.split(",");
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
	public static boolean DELETE_RENDIMIENTO_DUPLICADO (int codigo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE from rendimiento_diario where codigo = "+codigo;
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static ArrayList<String[]> GET_TR_SIN_RENDIMIENTO (String campo, String periodo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<String[]> data = new ArrayList<>();
		ArrayList<String> titulos = new ArrayList<>();
		ConnectionDB db = new ConnectionDB();
		try {
			sql += 	"SELECT ";
			sql += 		"DATE_FORMAT(f.fecha, '%d-%m-%Y') AS fecha, tr.codigo, UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre ";
			sql += 	"FROM contratos ct ";
			sql += 		"LEFT JOIN trabajadores tr ON(tr.codigo = ct.codigo_trabajador) ";
			sql += 		"LEFT JOIN sw_fechas f ON (DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m')) ";
			sql += 		"LEFT JOIN sw_m_feriados mf ON (DATE_FORMAT(mf.fechaFeriado, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m')) ";
			sql += 	"WHERE ";
			sql += 		"ct.fechaInicio_actividad <= f.fecha ";
			sql += 		"AND (ct.FechaTerminoContrato >= f.fecha OR ct.FechaTerminoContrato IS NULL) ";
			sql += 		"AND DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m') ";
			sql += 		"AND WEEKDAY(f.fecha) <= 4 ";
			sql += 		"AND f.fecha NOT IN (SELECT ";
			sql += 			"CASE ";
			sql += 				"WHEN (rd.fecha_i IS NULL) THEN rg.fecha ";
			sql += 				"ELSE rd.fecha_i ";
			sql += 			"END AS fecha ";
			sql += 		"FROM rendimiento_diario rd ";
			sql += 			"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 		"WHERE ";
			sql += 			"rd.idContrato = ct.id) ";
			sql += 		"AND f.fecha <= CURDATE() ";
			sql += 		"AND tr.agro = 1 ";
			sql += 		"AND tr.codigo NOT IN (SELECT codigo_trabajador FROM permiso_licencia WHERE ";
			sql += 			"fecha_desde <= f.fecha ";
			sql += 			"AND (fecha_hasta >= f.fecha OR fecha_hasta IS NULL)) ";
			sql += 		"AND tr.idHuerto = '"+campo+"' ";
			sql += 		"AND f.fecha <= CURDATE() ";
			sql += 		"AND f.fecha NOT IN (SELECT fechaFeriado FROM sw_m_feriados WHERE DATE_FORMAT(mf.fechaFeriado, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m')) ";
			sql +=	"ORDER BY 1;";
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
					datos += horas+",";
				}
				String [] dataAux = datos.split(",");
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
	public static ArrayList<String[]> GET_RESUMEN_DIGITACION (String sociedad, String campo, String periodo, int estado)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		ArrayList<String[]> data = new ArrayList<>();
		try {
			sql += 	"SELECT ";
			sql += 		"tr.codigo, ";
			sql += 		"CASE ";
			sql += 			"WHEN (tr.rut = '') THEN tr.rutTemporal ";
			sql += 			"ELSE tr.rut ";
			sql += 		"END AS rut, ";
			sql += 		"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre, ";
			sql += 		"p.descripcion, ";
			sql += 		"DATE_FORMAT(ct.fechaInicio_actividad, '%d-%m-%Y') AS fechaInicio_actividad, ";
			sql += 		"CASE ";
			sql += 			"WHEN (ct.FechaTerminoContrato IS NULL) THEN '' ";
			sql += 			"ELSE DATE_FORMAT(ct.FechaTerminoContrato, '%d-%m-%Y') ";
			sql += 		"END AS FechaTerminoContrato, DATE_FORMAT(l.fecha_pago, '%d-%m-%Y') AS fecha_pago, ";
			sql += 		"dt.dias_mes, ";
			sql += 		"IF(df.faltas IS NULL, 0, df.faltas) AS fallas, ";
			sql += 		"IF(df.faltas IS NULL,dt.dias_mes,dt.dias_mes - df.faltas) AS dias_trabajados, ";
			sql += 		"t.horas_trabajadas,t.horas_extras,t.m_hx,t.m_b,t.hx_dos,t.m_b2,t.valor_rend,t.base_dia,t.valor_liquido ";
			sql += 	"FROM ";
			sql += 		"(SELECT  ";
			sql += 			"rd.idContrato, ";
			sql += 			"COUNT(DISTINCT CASE ";
			sql += 				"WHEN (rd.fecha_i IS NULL) THEN rg.fecha ";
			sql += 				"ELSE rd.fecha_i ";
			sql += 			"END) AS dias_totales, ";
			sql += 			"SUM(rd.monto_hx) AS m_hx, ";
			sql += 			"SUM(rd.bono1) AS m_b, ";
			sql += 			"SUM(rd.bono2) AS m_b2, ";
			sql += 			"SUM(rd.horas_trabajadas) AS horas_trabajadas, ";
			sql += 			"SUM(rd.horas_extras) AS horas_extras, ";
			sql += 			"SUM(rd.hx_dos) AS hx_dos, ";
			sql += 			"SUM(CASE ";
			sql += 				"WHEN ";
			sql += 					"(rd.tipo_trato = 2) ";
			sql += 				"THEN ";
			sql += 					"CASE ";
			sql += 						"WHEN (rd.base_piso_hora = 2) THEN rd.valor_rendimietno ";
			sql += 						"ELSE CASE ";
			sql += 							"WHEN (rd.valor > rd.valor_rendimietno) THEN 0 ";
			sql += 							"ELSE rd.valor_rendimietno ";
			sql += 						"END ";
			sql += 					"END ";
			sql += 				"ELSE CASE ";
			sql += 					"WHEN (rd.tipo_trato = 3) THEN rd.valor_rendimietno ";
			sql += 					"ELSE 0 ";
			sql += 				"END ";
			sql += 			"END) AS valor_rend, ";
			sql += 			"SUM(CASE ";
			sql += 				"WHEN ";
			sql += 					"(rd.tipo_trato = 2) ";
			sql += 				"THEN ";
			sql += 					"CASE ";
			sql += 						"WHEN (rd.base_piso_hora = 2) THEN 0 ";
			sql += 						"ELSE CASE ";
			sql += 							"WHEN (rd.valor > rd.valor_rendimietno) THEN rd.valor ";
			sql += 							"ELSE 0 ";
			sql += 						"END ";
			sql += 					"END ";
			sql += 				"ELSE rd.valor ";
			sql += 			"END) AS base_dia, ";
			sql += 			"SUM(rd.valor_liquido) AS valor_liquido ";
			sql += 		"FROM ";
			sql += 			"rendimiento_diario rd ";
			sql += 		"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 		"WHERE ";
			sql += 			"rd.trabajador != 0 ";
			sql += 			"AND (DATE_FORMAT(rd.fecha_i, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m') ";
			sql += 			"OR DATE_FORMAT(rg.fecha, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m')) ";
			if(estado != 0){
				sql += 		"AND rd.estado = "+estado+" ";
			}else{
				sql += 		"AND rd.estado != 7 ";
			}
			sql += 			"AND (rd.rd_contratista = 0 OR rd.rd_contratista IS NULL) ";
			sql += 		"GROUP BY 1) AS t ";
			sql += 		"RIGHT JOIN contratos ct ON (ct.id = t.idContrato) ";
			sql += 		"LEFT JOIN trabajadores tr ON (ct.codigo_trabajador = tr.codigo) ";
			sql += 		"LEFT JOIN (SELECT *FROM parametros WHERE codigo = 'TIPO_CONTRATO') p ON(ct.tipoContrato = p.llave) ";
			sql += 		"LEFT JOIN(SELECT IF(COUNT(f.fecha) > 30, 30, ";
			sql += 				"CASE WHEN(DATE_FORMAT('"+periodo+"-01', '%m') = 02) ";
			sql += 					"THEN IF(COUNT(f.fecha) = 28, 30, COUNT(f.fecha)) ";
			sql += 					"ELSE COUNT(f.fecha) ";
			sql += 				"END) AS dias_mes, c.id ";
			sql += 			"FROM ";
			sql += 				"contratos c ";
			sql += 				"LEFT JOIN sw_fechas f ON (DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m')) ";
			sql += 			"WHERE ";
			sql += 				"c.fechaInicio_actividad <= f.fecha ";
			sql += 				"AND (c.FechaTerminoContrato >= f.fecha OR c.FechaTerminoContrato IS NULL) ";
			sql += 				"AND DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m') group by 2) dt ON(dt.id = ct.id) ";
			sql += 		"LEFT JOIN (SELECT  COUNT(f.fecha) as faltas, c.id ";
			sql += 			"FROM contratos c ";
			sql += 				"LEFT JOIN sw_fechas f ON (DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m')) ";
			sql += 			"WHERE ";
			sql += 				"c.fechaInicio_actividad <= f.fecha ";
			sql += 				"AND (c.FechaTerminoContrato >= f.fecha OR c.FechaTerminoContrato IS NULL) ";
			sql += 				"AND DATE_FORMAT(f.fecha, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m') ";
			sql += 				"AND WEEKDAY(f.fecha) <= 4 ";
			sql += 				"AND f.fecha NOT IN (SELECT ";
			sql += 					"CASE ";
			sql += 						"WHEN (rd.fecha_i IS NULL) THEN rg.fecha ";
			sql += 						"ELSE rd.fecha_i ";
			sql += 					"END AS fecha ";
			sql += 				"FROM rendimiento_diario rd ";
			sql += 					"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 				"WHERE ";
			sql += 					"rd.idContrato = c.id) ";
			sql += 				"AND f.fecha NOT IN (SELECT fechaFeriado FROM sw_m_feriados WHERE DATE_FORMAT(fechaFeriado, '%Y%m') = DATE_FORMAT('"+periodo+"-01', '%Y%m')) GROUP BY 2) df on(df.id = ct.id) ";
			sql += 		"LEFT JOIN (SELECT id_contrato, fecha_pago FROM sw_liquidacion WHERE periodo =  DATE_FORMAT('"+periodo+"-01', '%Y%m')) l ON(l.id_contrato = t.idContrato)";
			sql += 	"WHERE ";
			sql += 		"DATE_FORMAT(ct.fechaInicio_actividad, '%Y%m') <= DATE_FORMAT('"+periodo+"-01', '%Y%m') ";
			sql += 		"AND (DATE_FORMAT(ct.FechaTerminoContrato, '%Y%m') >= DATE_FORMAT('"+periodo+"-01', '%Y%m') ";
			sql += 		"OR ct.FechaTerminoContrato IS NULL) ";
			sql += 		"AND tr.agro = 1 ";
			sql += 		"AND ct.idSociedad = (SELECT idSociedad FROM sociedad WHERE sociedad = '"+sociedad+"') ";
			sql += 		"AND tr.idHuerto = '"+campo+"' ";
			sql += 	"ORDER BY 3;";
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
					datos += horas+",";
				}
				String [] dataAux = datos.split(",");
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
	public static boolean ADD_TR_SIN_DIGITACION (String[] fechas)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			int count = 0;
			sql +=	"INSERT INTO tr_sin_digitacion ";
			for(int i = 0; i < fechas.length; i++){
				if(count == 0){
					sql += 	"SELECT ";
					sql += 		"tr.id, tr.codigo as codigo, ct.id as idContrato, '"+fechas[i]+"' as fecha ";
					sql += 	"FROM ";
					sql += 		"trabajadores tr ";
					sql += 		"LEFT JOIN contratos ct ON (ct.codigo_trabajador = tr.codigo) ";
					sql += 		"LEFT JOIN(SELECT ";
					sql += 						"SUM(horas_trabajadas) AS horas, trabajador ";
					sql += 					"FROM ";
					sql += 						"rendimiento_diario rd ";
					sql += 						"LEFT JOIN rendimiento_general rg ON (rg.codigo_rg = rd.codigo_rg) ";
					sql += 					"WHERE ";
					sql += 						"(rd.fecha_i = '"+fechas[i]+"' OR rg.fecha = '"+fechas[i]+"') ";
					sql += 						"AND rd.estado != 7 ";
					sql += 						"AND (rd.rd_contratista = 0 OR rd.rd_contratista IS NULL) ";
					sql += 					"GROUP BY trabajador) rd ON rd.trabajador = tr.id ";
					sql += 	"WHERE ";
					sql += 		"tr.agro = 1 ";
					sql += 		"AND (rd.horas IS NULL OR rd.horas = 0) ";
					sql += 		"AND tr.idContratista IS NULL ";
					sql += 		"AND (ct.fechaInicio_actividad <= '"+fechas[i]+"' ";
					sql += 		"AND (ct.FechaTerminoContrato >= '"+fechas[i]+"' OR ct.FechaTerminoContrato IS NULL)) ";
					sql += 		"AND (DAYOFWEEK('"+fechas[i]+"') != 7 OR DAYOFWEEK('"+fechas[i]+"') != 1)";
				}else{
					sql += 	"UNION ALL SELECT ";
					sql += 		"tr.id, tr.codigo as codigo, ct.id as idContrato, '"+fechas[i]+"' as fecha ";
					sql += 	"FROM ";
					sql += 		"trabajadores tr ";
					sql += 		"LEFT JOIN contratos ct ON (ct.codigo_trabajador = tr.codigo) ";
					sql += 		"LEFT JOIN(SELECT ";
					sql += 						"SUM(horas_trabajadas) AS horas, trabajador ";
					sql += 					"FROM ";
					sql += 						"rendimiento_diario rd ";
					sql += 						"LEFT JOIN rendimiento_general rg ON (rg.codigo_rg = rd.codigo_rg) ";
					sql += 					"WHERE ";
					sql += 						"(rd.fecha_i = '"+fechas[i]+"' OR rg.fecha = '"+fechas[i]+"') ";
					sql += 						"AND rd.estado != 7 ";
					sql += 						"AND (rd.rd_contratista = 0 OR rd.rd_contratista IS NULL) ";
					sql += 					"GROUP BY trabajador) rd ON rd.trabajador = tr.id ";
					sql += 	"WHERE ";
					sql += 		"tr.agro = 1 ";
					sql += 		"AND (rd.horas IS NULL OR rd.horas = 0) ";
					sql += 		"AND tr.idContratista IS NULL ";
					sql += 		"AND (ct.fechaInicio_actividad <= '"+fechas[i]+"' ";
					sql += 		"AND (ct.FechaTerminoContrato >= '"+fechas[i]+"' OR ct.FechaTerminoContrato IS NULL)) ";
					sql += 		"AND (DAYOFWEEK('"+fechas[i]+"') != 7 OR DAYOFWEEK('"+fechas[i]+"') != 1)";
				}
				count++;
			}
			sql +=	"ORDER BY 1;";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			ps.close();
			db.conn.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return false;
	}
	public static boolean DELETE_TR_SIN (String fecha)throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		String sql = "";
		try {
			sql = "DELETE FROM tr_sin_digitacion WHERE DATE_FORMAT(fecha, '%Y%m') = DATE_FORMAT('"+fecha+"', '%Y%m');";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
//			db.conn.close();
		}
		return false;
	}
	public static ArrayList<RENDIMIENTO_DIARIO> GETLISTADO_TR_HUERTO(String fecha_desde, String fecha_hasta, 
			String campo, String especie, String variedad, String faena, 
			String labor, String trabajador, String tipo_trabajador,String contratista,String cuartel, int estado, int valor, HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<RENDIMIENTO_DIARIO> lista = new ArrayList<RENDIMIENTO_DIARIO>();
		ConnectionDB db = new ConnectionDB();
		try {
			System.out.println(campo);
			sql += 	"SELECT DISTINCT ";
			sql += 		"rd.*, t.rut,c.especie, e.especie AS nespecie, l.faena, ";
			sql += 		"case when(rd.rd_contratista is null) then 0 else rd.rd_contratista end as contratista, ";
			sql += 		"UPPER(CONCAT(t.apellidoPaterno, ' ', t.apellidoMaterno, ' ', t.nombre)) AS nTrabajador, ";
			sql += 		"UPPER(CONCAT(trs.apellidoPaterno, ' ', trs.apellidoMaterno, ' ', trs.nombre)) AS supervisor, ";
			sql += 		"CASE ";
			sql += 			"WHEN (rg.fecha IS NULL) THEN rd.fecha_i ";
			sql += 			"ELSE rg.fecha ";
			sql += 		"END fecha_rendimiento, ";
			sql += 		"UPPER(CONCAT(c.codigo_cuartel, ' ', c.nombre)) AS nvnombre, cam.descripcion AS campo, c.variedad, v.variedad AS nVariedad, f.faena AS nFaena, l.labor AS nLabor, er.descripcion AS nestado ";
			if(valor == 0){
				sql += 		", CAST(vla.liquido * GETPORCENTAJEVALOR(rd.trabajador, ";
				sql += 				"DATE_FORMAT((SELECT ";
				sql += 								"CASE ";
				sql += 									"WHEN (rdr.fecha_i = '') THEN rgr.fecha ";
				sql += 									"ELSE rdr.fecha_i ";
				sql += 								"END AS fecha ";
				sql += 							"FROM ";
				sql += 								"rendimiento_diario rdr ";
				sql += 								"LEFT JOIN rendimiento_general rgr ON (rdr.codigo_rg = rgr.codigo_rg) ";
				sql += 							"WHERE ";
				sql += 								"rdr.codigo = rd.codigo), '%Y%m'), ";
				sql += 				"rd.valor_liquido, ";
				sql += 				"(SELECT sociedad FROM campo WHERE campo = cam.campo)) / 100 AS SIGNED)AS costo_empresa ";
			}
			sql += 		",CAST((rd.valor_rendimietno/rd.rendimiento) as signed) as valor_trato ";
			sql += 	"FROM rendimiento_diario rd ";
			sql += 		"LEFT JOIN rendimiento_general rg ON(rd.codigo_rg = rg.codigo_rg) ";
			sql += 		"LEFT JOIN trabajadores t ON(t.id = rd.trabajador) ";
			sql += 		"LEFT JOIN trabajadores trs ON(trs.id = rg.codigo_supervisor OR trs.id = rd.supervisor_i) ";
			if(valor == 0){
				sql += 		"LEFT JOIN vw_liquidoAgro vla ON (vla.codTrabajador = t.codigo AND vla.sociedad = (SELECT sociedad FROM campo WHERE campo IN ("+campo+")) AND vla.periodo = DATE_FORMAT('"+fecha_hasta+"', '%Y%m'))";
			}
			sql += 		"LEFT JOIN estado_rendimiento er ON(er.codigo = rd.estado) ";
			sql += 		"LEFT JOIN cuartel c ON(c.codigo = rd.cuartel) ";
			sql += 		"LEFT JOIN especie e ON(e.codigo = c.especie) ";
			sql += 		"LEFT JOIN variedad v ON(v.codigo = c.variedad) ";
			sql += 		"LEFT JOIN campo cam ON(cam.campo = t.idHuerto) ";
			sql += 		"LEFT JOIN sector s ON (s.sector = c.sector) ";
			sql += 		"LEFT JOIN labor l ON(l.codigo = rd.labor) ";
			sql += 		"LEFT JOIN faena f ON(f.codigo = l.faena) ";
			sql += 	"WHERE ";
			sql += 		"(rg.fecha BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"' OR rd.fecha_i BETWEEN '"+fecha_desde+"' AND '"+fecha_hasta+"')";
			sql += 		"AND (cam.campo IN ("+campo+")) ";
			sql += 		"AND (rg.especie IN ('"+especie+"') or '"+especie+"' = 0) ";
			sql += 		"AND (v.codigo IN ('"+variedad+"') or '"+variedad+"' = 0) ";
			sql += 		"AND (f.codigo IN ('"+faena+"') or '"+faena+"' = 0) ";
			sql += 		"AND (rd.labor IN ('"+labor+"') or '"+labor+"' = 0) ";
			sql += 		"AND (rd.trabajador IN ('"+trabajador+"') or '"+trabajador+"' = 0) ";
			sql += 		"AND (rd.cuartel IN ('"+cuartel+"') or '"+cuartel+"' = 0) ";
			sql += 		"AND (rd.rd_contratista IN ('"+contratista+"') or '"+contratista+"' = 0) ";
			sql += 		"AND (rd.estado IN ('"+estado+"') or '"+estado+"' = 0) ";
			if(tipo_trabajador.equals("1")) {
				sql += " AND (rd.rd_contratista = 0 or rd.rd_contratista = '' or rd.rd_contratista is null)";
			}
			if(tipo_trabajador.equals("2")) {
				sql += " AND (rd.rd_contratista != 0 and rd.rd_contratista != '' and rd.rd_contratista is not null)";
			}
			sql += " AND rd.estado != 7";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				RENDIMIENTO_DIARIO ob = new RENDIMIENTO_DIARIO();
				ob.setCodigo(rs.getInt("codigo"));
				ob.setNombre(rs.getString("nTrabajador"));
				ob.setRut(rs.getString("rut"));
				ob.setHoras_trabajadas(rs.getFloat("horas_trabajadas"));
				ob.setHoras_extras(rs.getFloat("horas_extras"));
				ob.setEspecie(rs.getInt("especie"));
				ob.setVariedad(rs.getInt("variedad"));
				ob.setFaena(rs.getInt("faena"));
				ob.setLabor(rs.getInt("labor"));
				ob.setValor(rs.getInt("valor"));
				ob.setValor_rendimiento(rs.getFloat("valor_rendimietno"));
				ob.setRendimiento(rs.getFloat("rendimiento"));
				ob.setTipo_pago(rs.getInt("tipo_trato"));
				ob.setValor_liquido(rs.getInt("valor_liquido"));
				ob.setBono1(rs.getInt("bono1"));
				ob.setBono2(rs.getInt("bono2"));
				ob.setMaquinaria(rs.getInt("maquinaria"));
				ob.setImplemento(rs.getInt("implemento"));
				ob.setFecha(rs.getString("fecha_rendimiento")); 
				ob.setNvnombre(rs.getString("nvnombre"));
				ob.setDescripcion(rs.getString("campo"));
				ob.setnVariedad(rs.getString("nVariedad"));
				ob.setnEspecie(rs.getString("nEspecie"));
//				ob.setnMaquinaria(rs.getString("nMaquinaria"));
//				ob.setnImplemento(rs.getString("nImplemento"));
				ob.setnFaena(rs.getString("nFaena"));
				ob.setnLabor(rs.getString("nLabor"));
				ob.setSupervisor(rs.getString("supervisor"));
				ob.setBase_piso_hora(rs.getInt("base_piso_hora"));
				ob.setIdContratista(rs.getString("contratista"));
				ob.setNestado(rs.getString("nestado"));
				ob.setValor_hx(rs.getFloat("valor_hx"));
				ob.setMonto_hx(rs.getFloat("monto_hx"));
				ob.setHx_dos(rs.getFloat("hx_dos"));
				ob.setValor_hx_dos(rs.getFloat("valor_hx_dos"));
				ob.setMacroco(rs.getString("macroco"));
				ob.setCeco(rs.getString("ceco"));
				ob.setOrdenco(rs.getString("ordenco"));
				ob.setRes_hx(rs.getInt("res_hx"));
				ob.setSubsidio(rs.getInt("subsidio"));
				ob.setCuartel(rs.getInt("rd.cuartel"));
				ob.setValor_trato(rs.getFloat("valor_trato"));
				if(valor == 0){
					ob.setCosto_empresa(rs.getInt("costo_empresa"));
				}
				lista.add(ob);
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
		return lista;
	}
	public static String GET_ASISTENCIA_TRABAJADOR (int periodo, int trabajador)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONArray data = new JSONArray();
		try {
			sql += 	"SELECT ";
			sql += 		"CASE(WEEKDAY(f.fecha))";
			sql += 			"WHEN (0) THEN 'Lunes' ";
			sql += 			"WHEN (1) THEN 'Martes' ";
			sql += 			"WHEN (2) THEN 'Miercoles' ";
			sql += 			"WHEN (3) THEN 'Jueves' ";
			sql += 			"WHEN (4) THEN 'Viernes' ";
			sql += 			"WHEN (5) THEN 'Sabado' ";
			sql += 			"WHEN (6) THEN 'Domingo' ";
			sql += 		"END AS dia, ";
			sql += 		"DATE_FORMAT(f.fecha, '%d-%m-%Y') AS fecha, ";
			sql += 		"IF(t.hora_desde IS NULL, '--:--', TIME_FORMAT(t.hora_desde, '%H:%i')) AS hora_desde, ";
			sql += 		"IF(t.inicio_colacion IS NULL, '--:--', TIME_FORMAT(t.inicio_colacion, '%H:%i')) AS inicio_colacion, ";
			sql += 		"IF(t.termino_colacion IS NULL, '--:--', TIME_FORMAT(t.termino_colacion, '%H:%i')) AS termino_colacion, ";
			sql += 		"IF(t.hora_hasta IS NULL, '--:--', TIME_FORMAT(t.hora_hasta, '%H:%i')) AS hora_hasta, ";
			sql += 		"IF(t.horas IS NULL, 0, t.horas) AS horas, ";
			sql += 		"IF(t.horas IS NULL, 9, 9 - t.horas) AS falta, ";
			sql += 		"t.hx ";
			sql += 	"FROM ";
			sql += 		"sw_fechas f ";
			sql += 		"LEFT JOIN contratos ct ON ct.fechaInicio_actividad <= f.fecha AND (ct.FechaTerminoContrato >= f.fecha OR ct.FechaTerminoContrato IS NULL) ";
			sql += 		"LEFT JOIN trabajadores tr ON tr.codigo = ct.codigo_trabajador ";
			sql += 		"LEFT JOIN (SELECT ";
			sql += 				"SUM(rd.horas_trabajadas) AS horas, ";
			sql += 				"rd.trabajador, ";
			sql += 				"IF(rd.fecha_i IS NULL, rg.fecha, rd.fecha_i) AS fecha, ";
			sql += 				"SUM(rd.horas_extras) AS hx, rd.inicio_colacion, rd.termino_colacion, rd.hora_desde, rd.hora_hasta ";
			sql += 			"FROM ";
			sql += 				"rendimiento_diario rd ";
			sql += 				"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 			"WHERE ";
			sql += 				"rd.estado = 3 ";
			sql += 				"AND rd.trabajador = "+trabajador+" ";
			sql += 				"AND (DATE_FORMAT(rd.fecha_i, '%Y%m') = "+periodo+" OR DATE_FORMAT(rg.fecha, '%Y%m') = "+periodo+") ";
			sql += 			"GROUP BY 2 , 3,5,6,7,8) t ON (t.trabajador = tr.id AND t.fecha = f.fecha) ";
			sql += 	"WHERE ";
			sql += 		"DATE_FORMAT(f.fecha, '%Y%m') = "+periodo+" ";
			sql += 		"AND WEEKDAY(f.fecha) IN(SELECT dia FROM horario h LEFT JOIN horario_campo hc ON(h.idHc = hc.id) WHERE hc.campo = tr.idHuerto)  ";
			sql += 		"AND f.fecha NOT IN (SELECT fechaFeriado FROM sw_m_feriados WHERE DATE_FORMAT(fechaFeriado, '%Y%m') = "+periodo+") ";
			sql += 		"AND tr.id = "+trabajador+" ";
			sql += 	"ORDER BY f.fecha";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql); 
			while(rs.next()){
				JSONObject e = new JSONObject();
				e.put("dia", rs.getString("dia"));
				e.put("fecha", rs.getString("fecha"));
				e.put("hora_desde", rs.getString("hora_desde"));
				e.put("inicio_colacion", rs.getString("inicio_colacion"));
				e.put("termino_colacion", rs.getString("termino_colacion"));
				e.put("hora_hasta", rs.getString("hora_hasta"));
				e.put("horas", rs.getFloat("horas"));
				e.put("falta", rs.getFloat("falta"));
				e.put("hx", rs.getFloat("hx"));
				data.put(e);
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
		return data.toString();
	}	
	public String GENERAR_WORD_ASISTENCIA(int id, String periodo, String sociedad, String campo,HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		PreparedStatement ps2 = null;
		String sql2 = "";
		ConnectionDB db = new ConnectionDB();
		
		PreparedStatement psp = null;
		String sqlsp = "";
		sqlsp = 	"CALL sa_getFolioAsistencia('"+periodo+"-01','"+sociedad+"', '"+campo+"')";
		System.out.println(sqlsp);
		psp = db.conn.prepareStatement(sqlsp);
		psp.execute();
		
		String mes = periodo.split("-")[1];
		String mes2 = periodo.split("-")[1];
		if(mes.equals("01")){mes = "ENERO";}
		if(mes.equals("02")){mes = "FEBRERO";}
		if(mes.equals("03")){mes = "MARZO";}
		if(mes.equals("04")){mes = "ABRIL";}
		if(mes.equals("05")){mes = "MAYO";}
		if(mes.equals("06")){mes = "JUNIO";}
		if(mes.equals("07")){mes = "JULIO";}
		if(mes.equals("08")){mes = "AGOSTO";}
		if(mes.equals("09")){mes = "SAPTIEMBRE";}
		if(mes.equals("10")){mes = "OCTUBRE";}
		if(mes.equals("11")){mes = "NOVIEMBRE";}
		if(mes.equals("12")){mes = "DICIEMBRE";}
		String urlDocGenerado = utils.reportesExcel();
		urlDocGenerado=urlDocGenerado+"Asistencia"+".docx";
		File archivo = new File(urlDocGenerado);
		String tituloDocumento = "PLANILLA DE ASISTENCIA "+mes+" "+periodo.split("-")[0];
		XWPFDocument documento = new XWPFDocument();
		PreparedStatement ps1 = null;
		String sql1 = "";
		try {
			sql1 += "SELECT ";
			sql1 += 	"ct.id,tr.codigo, UPPER(CONCAT(apellidoPaterno, ' ', apellidoMaterno,' ', nombre)) AS nombre, ";
			sql1 += 	"IF(rutTemporal = '' OR rutTemporal IS NULL, tr.rut, rutTemporal) AS rut, ";
			sql1 += 	"fo.folio,t.horas, ";
			sql1 += 	"IF(t.horas IS NULL,(t2.n * 9), (t2.n * 9) - t.horas) AS faltas, ";
			sql1 += 	"t.hx, ";
			sql1 += 	"DATE_FORMAT(ct.fechaInicio_actividad, '%d-%m-%Y') AS fechaInicio, ";
			sql1 += 	"IF(ct.FechaTerminoContrato IS NULL, '--------', DATE_FORMAT(ct.FechaTerminoContrato, '%d-%m-%Y')) AS fechaFin, ";
			sql1 += 	"UPPER(s.denominacionSociedad) AS empresa ";
			sql1 += "FROM ";
			sql1 += 	"folio_asistencia fo ";
			sql1 += 	"LEFT JOIN trabajadores tr ON (fo.idTrabajador = tr.id AND fo.periodo = "+periodo.split("-")[0]+mes2+" )";
			sql1 += 	"LEFT JOIN contratos ct ON (ct.codigo_trabajador = tr.codigo) ";
			sql1 += 	"LEFT JOIN sociedad s ON(s.idSociedad = ct.idSociedad) ";
			sql1 += 	"LEFT JOIN (SELECT ";
			sql1 += 			"SUM(rd.horas_trabajadas) AS horas, ";
			sql1 += 			"SUM(rd.horas_extras) AS hx, ";
			sql1 += 			"rd.trabajador ";
			sql1 += 		"FROM ";
			sql1 += 			"rendimiento_diario rd ";
			sql1 += 			"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql1 += 		"WHERE ";
			sql1 += 			"rd.trabajador = "+id+" ";
			sql1 += 			"AND rd.estado != 7 ";
			sql1 += 			"AND (DATE_FORMAT(rd.fecha_i, '%Y%m') = "+periodo.split("-")[0]+mes2+" OR DATE_FORMAT(rg.fecha, '%Y%m') = "+periodo.split("-")[0]+mes2+") ";
			sql1 += 		"GROUP BY 3) t ON (t.trabajador = tr.id) ";
			sql1 += 	"LEFT JOIN (SELECT ";
			sql1 += 			"cts.id, COUNT(f.fecha) AS n ";
			sql1 += 		"FROM ";
			sql1 += 			"sw_fechas f ";
			sql1 += 			"LEFT JOIN contratos cts ON (cts.fechaInicio_actividad <= f.fecha AND (cts.FechaTerminoContrato >= f.fecha OR cts.FechaTerminoContrato IS NULL)) ";
			sql1 += 		"WHERE ";
			sql1 += 			"DATE_FORMAT(f.fecha, '%Y%m') = "+periodo.split("-")[0]+mes2+" ";
			sql1 += 			"AND WEEKDAY(f.fecha) IN(SELECT dia FROM horario h LEFT JOIN horario_campo hc ON (hc.id = h.idHc) WHERE campo = 'AS01' AND weekday(f.fecha)) ";
			sql1 += 			"AND f.fecha NOT IN (SELECT fechaFeriado FROM sw_m_feriados WHERE DATE_FORMAT(fechaFeriado, '%Y%m') = "+periodo.split("-")[0]+mes2+") ";
			sql1 += 		"GROUP BY 1) t2 ON (t2.id = ct.id) ";
			sql1 += "WHERE ";
			sql1 += 	"DATE_FORMAT(ct.fechaInicio_actividad, '%Y%m') <= "+periodo.split("-")[0]+mes2+" ";
			sql1 += 	"AND (DATE_FORMAT(ct.FechaTerminoContrato, '%Y%m') >= "+periodo.split("-")[0]+mes2+" OR ct.FechaTerminoContrato IS NULL) ";
			sql1 += 	"AND tr.id = "+id+"";
			System.out.println(sql1);
			ps1 = db.conn.prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery(sql1);
			while(rs1.next()){
				JSONObject e = new JSONObject();
				e.put("id", rs1.getInt("id"));
				e.put("codigo", rs1.getInt("codigo"));
				e.put("nombre", rs1.getString("nombre"));
				e.put("rut", rs1.getString("rut"));
				e.put("folio", rs1.getInt("folio"));
				e.put("horas", rs1.getFloat("horas"));
				e.put("faltas", rs1.getFloat("faltas"));
				e.put("hx", rs1.getFloat("hx"));
				e.put("fechaInicio", rs1.getString("fechaInicio"));
				e.put("fechaFin", rs1.getString("fechaFin"));
				e.put("empresa", rs1.getString("empresa"));
				ArrayList<String> titulos = new ArrayList<>();
				ArrayList<String[]> data = new ArrayList<>();
				float total_horas = 0;
				float faltas = 0;
				float horas_extras = 0;
//				String contenidoParrafo = "RUT: "+e.getString("rut")+"                       NOMBRE: "+e.getString("nombre");
				sql = 	"SELECT ";
				sql += 		"CASE WHEN(WEEKDAY(f.fecha) = 0) THEN 'Lunes' ";
				sql += 		"WHEN(WEEKDAY(f.fecha) = 1) THEN 'Martes' ";
				sql += 		"WHEN(WEEKDAY(f.fecha) = 2) THEN 'Miércoles' ";
				sql += 		"WHEN(WEEKDAY(f.fecha) = 3) THEN 'Jueves' ";
				sql += 		"WHEN(WEEKDAY(f.fecha) = 4) THEN 'Viernes' END AS dia, ";
				sql += 		"DATE_FORMAT(f.fecha, '%d-%m-%Y')as fecha, IFNULL(t.hora_desde, '--:--') AS hora_desde, IFNULL(t.hora_hasta, '--:--') AS hora_hasta, IF(t.horas IS NULL, 0.000, t.horas) AS horas, IF(t.horas IS NULL, 9.000, 9-t.horas) AS falta, IFNULL(t.hx, 0.000) AS hx ";
				sql += 	"FROM ";
				sql += 		"sw_fechas f ";
				sql += 		"LEFT JOIN contratos ct ON ct.fechaInicio_actividad <= f.fecha AND (ct.FechaTerminoContrato >= f.fecha OR ct.FechaTerminoContrato IS NULL) ";
				sql += 		"LEFT JOIN trabajadores tr ON tr.codigo = ct.codigo_trabajador ";
				sql += 		"LEFT JOIN (SELECT ";
				sql += 				"SUM(rd.horas_trabajadas) AS horas, ";
				sql += 				"rd.trabajador, ";
				sql += 				"IF(rd.fecha_i IS NULL, rg.fecha, rd.fecha_i) AS fecha, ";
				sql += 				"SUM(rd.horas_extras) AS hx, ";
				sql += 				"rd.hora_desde, rd.hora_hasta ";
				sql += 			"FROM ";
				sql += 				"rendimiento_diario rd ";
				sql += 				"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
				sql += 			"WHERE ";
				sql += 				"rd.estado = 3 ";
				sql += 				"AND rd.idContrato = "+e.getInt("id")+" ";
				sql += 				"AND (DATE_FORMAT(rd.fecha_i, '%Y%m') = "+periodo.split("-")[0]+mes2+" OR DATE_FORMAT(rg.fecha, '%Y%m') = "+periodo.split("-")[0]+mes2+") ";
				sql += 			"GROUP BY 2 , 3, 5,6) t ON (t.trabajador = tr.id AND t.fecha = f.fecha) ";
				sql += 	"WHERE ";
				sql += 		"DATE_FORMAT(f.fecha, '%Y%m') = "+periodo.split("-")[0]+mes2+" ";
				sql += 		"AND WEEKDAY(f.fecha) IN(SELECT h.dia FROM horario h LEFT JOIN horario_campo hc ON (hc.id = h.idHc) WHERE campo = ct.idHuertoContrato) ";
				sql += 		"AND f.fecha NOT IN (SELECT fechaFeriado FROM sw_m_feriados WHERE DATE_FORMAT(fechaFeriado, '%Y%m') = "+periodo.split("-")[0]+mes2+") ";
				sql += 		"AND ct.id = "+rs1.getInt("id")+" ";
				ps = db.conn.prepareStatement(sql);
				System.out.println(sql);
				ResultSet rs = ps.executeQuery(sql); 
				ResultSetMetaData md = rs.getMetaData();
				int count = md.getColumnCount();
				for (int i = 1; i <= count; i++) {
					titulos.add(md.getColumnName(i));
				}
				faltas = 0;
				total_horas = 0;
				while(rs.next()){
					String datos = "";
					faltas+=rs.getFloat("falta");
					total_horas += rs.getFloat("horas");
					for(String t: titulos){
						String horas = rs.getString(t);
						if(horas == null){
							horas = " ";
						}
						datos += horas+",";
					}
					String [] dataAux = datos.split(",");
					data.add(dataAux);
				}
				//Declaramos el titulo y le asignamos algunas propiedades
				XWPFParagraph titulo_doc = documento.createParagraph();
				titulo_doc.setAlignment(ParagraphAlignment.CENTER);
				titulo_doc.setVerticalAlignment(TextAlignment.TOP);
	        
				//Declaramos el titulo y le asignamos algunas propiedades
				String contenidoParrafo1 = "RUT: "+rs1.getString("rut");
				XWPFParagraph parrafo = documento.createParagraph();
				parrafo.setAlignment(ParagraphAlignment.BOTH);
				XWPFRun r1 = titulo_doc.createRun();
				r1.setBold(true);
				r1.setText(tituloDocumento);
				r1.setFontFamily("Arial");
				r1.setFontSize(12);
				r1.setTextPosition(10);
				r1.setUnderline(UnderlinePatterns.SINGLE);
				XWPFRun r2 = parrafo.createRun();
				r2.setText(contenidoParrafo1);
				r2.addTab();
				r2.addTab();
				String contenidoParrafo2 = "NOMBRE: "+e.getString("nombre");
				r2.setText(contenidoParrafo2);
				r2.addTab();
				String folioText = "FOLIO: "+e.getInt("folio");
				r2.setText(folioText);
				r2.setFontSize(10);
				r2.addCarriageReturn();
				
				String fechas = "FECHA INICIO: "+e.getString("fechaInicio");
				XWPFRun r3 = parrafo.createRun();
				r3.setText(fechas);
				r3.addTab();
				String fechas2 = "FECHA TERMINO: "+e.getString("fechaFin");
				r3.setText(fechas2);
				r3.addTab();
				String empresa = e.getString("empresa");
				r3.setText(empresa);
				r3.setFontSize(10);
				r3.addCarriageReturn();
				
				XWPFTable tableTwo = documento.createTable();
				tableTwo.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "1C7331");
				tableTwo.setWidth(3000);
				XWPFTableRow tableTwoRowOne = tableTwo.getRow(0);
				tableTwoRowOne.getCell(0).setText("");
				tableTwoRowOne.createCell().setText("Fecha");
				tableTwoRowOne.createCell().setText("Entrada");
				tableTwoRowOne.createCell().setText("Salida");
				tableTwoRowOne.createCell().setText("Trabajadas");
				tableTwoRowOne.createCell().setText("Faltas");
				tableTwoRowOne.createCell().setText("Horas Extras");
				tableTwoRowOne.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(4).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(5).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(6).setVerticalAlignment(XWPFVertAlign.CENTER);
				for (int i = 0; i < data.size(); i++) {
				    String node = "node";
				    XWPFTableRow tr = null;
				    node = node + (i + 1);
				    if (tr == null) {
				        tr = tableTwo.createRow();
				        tr.getCell(0).removeParagraph(0);
				        tr.setHeight(4);
				        XWPFParagraph paragraph = tr.getCell(0).addParagraph();
				        setRun(paragraph.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[0]) , false);

				        tr.getCell(1).removeParagraph(0);
				        XWPFParagraph paragraph1 = tr.getCell(1).addParagraph();
				        setRun(paragraph1.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[1]) , false);

				        tr.getCell(2).removeParagraph(0);
				        XWPFParagraph paragraph2 = tr.getCell(2).addParagraph();
				        setRun(paragraph2.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[2]) , false);

				        tr.getCell(3).removeParagraph(0);
				        XWPFParagraph paragraph3 = tr.getCell(3).addParagraph();
				        setRun(paragraph3.createRun() , "Arial" ,9 , String.valueOf(data.get(i)[3]) , false);

				        tr.getCell(4).removeParagraph(0);
				        XWPFParagraph paragraph4 = tr.getCell(4).addParagraph();
				        setRun(paragraph4.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[4]) , false);

				        tr.getCell(5).removeParagraph(0);
				        XWPFParagraph paragraph5 = tr.getCell(5).addParagraph();
				        setRun(paragraph5.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[5]) , false);

				        tr.getCell(6).removeParagraph(0);
				        XWPFParagraph paragraph6 = tr.getCell(6).addParagraph();
				        setRun(paragraph6.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[6]) , false);
				    }
				}
				XWPFTableRow trF = tableTwo.createRow();
				trF.getCell(0).setText("TOTAL");
				trF.getCell(1).setText("");
				trF.getCell(2).setText("");
				trF.getCell(3).setText("");
				trF.getCell(4).setText(String.valueOf(total_horas));
				trF.getCell(5).setText(String.valueOf(faltas));
				trF.getCell(6).setText(String.valueOf(horas_extras));
				for (int i = 0; i < tableTwo.getNumberOfRows(); i++) {
			        XWPFTableRow row = tableTwo.getRow(i);
			        int numCells = row.getTableCells().size();
			        for (int j = 0; j < numCells; j++) {
			            XWPFTableCell cell = row.getCell(j);
			            CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
			            CTTcPr pr = cell.getCTTc().addNewTcPr();
			            pr.addNewNoWrap();
			            cellWidth.setW(BigInteger.valueOf(2880));
			        }
			    }
				String firma = "FIRMA: ________________" ;
				XWPFParagraph salto = documento.createParagraph();
				XWPFRun r5 = salto.createRun();
				r5.addCarriageReturn();
				XWPFParagraph firma_doc = documento.createParagraph();
				firma_doc.setAlignment(ParagraphAlignment.LEFT);
				firma_doc.setVerticalAlignment(TextAlignment.BOTTOM);
				XWPFRun r4 = firma_doc.createRun();
				r4.setText(firma);
				r4.setFontSize(12);
				r4.setBold(true);
				r4.setUnderline(UnderlinePatterns.SINGLE);
//				r4.addCarriageReturn();
				r4.addBreak(BreakType.PAGE);
			}
			rs1.close();
			ps.close();
			db.conn.close();
			
			
			FileOutputStream word = new FileOutputStream(archivo);
		    documento.write(word);
		    documento.close();
		    word.close();
//		    Desktop.getDesktop().open(tituloDocumento+".docx");
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public String OA_CORFIRM_PDF(excelOrdenJson row) throws Exception{
		String urlDocGenerado = utils.reportesExcel();
		try {
			Document document = new Document(PageSize.A4.rotate());
			OutputStream out = new FileOutputStream(urlDocGenerado+row.getNombre()+".pdf");
			PdfWriter writer = PdfWriter.getInstance(document, out);
			
//			PdfWriter writer = new PdfWriter(urlDocGenerado+row.getNombre()+".pdf");
			document.open();
	        // calculate diamond shaped hole
	        float diamondHeight = 300;
	        float diamondWidth = 300;
	        float gutter = 10;
	        float bodyHeight = document.top() - document.bottom();
	        float colMaxWidth = (document.right() - document.left() - (gutter * 3)) / 3f;
	        float diamondTop = document.top() - ((bodyHeight - diamondHeight) / 3f);
	        float diamondInset = colMaxWidth - (diamondWidth / 3f);
	        float centerX = (document.right() - document.left()) / 3 + document.left();
	        // draw stuff
	        PdfContentByte cb = writer.getDirectContentUnder();
	        MultiColumnText mct = new MultiColumnText(document.top() - document.bottom());
	        // setup column 1
	        float[] left = { 
	        	document.left(), document.top(), document.left(), document.bottom() 
	        };
	        float[] right = { 
	        	document.left() + colMaxWidth, 
	        	document.top(), 
	        	document.left() + colMaxWidth, 
	        	diamondTop, 
	        	document.left() + diamondInset, 
	        	diamondTop - diamondHeight / 3, 
	        	document.left() + colMaxWidth, 
	        	diamondTop - diamondHeight, 
	        	document.left() + colMaxWidth, document.bottom() 
	        };
	        mct.addColumn(left, right);
	        // setup column 2
	        left = new float[] { document.right() - colMaxWidth, document.top(), document.right() - colMaxWidth, diamondTop, document.right() - diamondInset, diamondTop - diamondHeight / 2, document.right() - colMaxWidth, diamondTop - diamondHeight, document.right() - colMaxWidth, document.bottom() };
	        right = new float[] { document.right(), document.top(), document.right(), document.bottom() };
	        mct.addColumn(left, right);
	        // add text
	        ArrayList<PdfPCell> cells = new ArrayList<PdfPCell>();
	        PdfPTable tableT = new PdfPTable(1);
	        PdfPCell cellTitulo = new PdfPCell();
	        Paragraph pgTitulo = new Paragraph("AGRICOLA SAN CLEMENTE", FontFactory.getFont(FontFactory.HELVETICA, 13));
	        cellTitulo.addElement(pgTitulo);
	        cells.add(cellTitulo);
	        for(int i = 0; i < cells.size(); i++){
	        	cells.get(i).setBorder(Rectangle.NO_BORDER);
	        }
	        tableT.addCell(cellTitulo);
	        tableT.setHorizontalAlignment(Element.ALIGN_LEFT);
	        tableT.setWidthPercentage(100);

	        PdfPTable table = new PdfPTable(5);
	        
	        table.setHorizontalAlignment(Element.ALIGN_LEFT);
	        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        table.setWidthPercentage(100);
	        table.setWidths(new float[] { 2, 4, 2, 1, 1 });
	        table.addCell(new Phrase("TEMP 2019/2020 ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase("ORDEN APLICACIÓN PRODUCTO AGROQUÍMICOS Y FERLTILIZANTE ", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell("");
	        table.addCell(new Phrase("NRO ORDEN:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase(String.valueOf(row.getCodigo()), FontFactory.getFont(FontFactory.HELVETICA, 11)));

	        table.addCell(new Phrase("DE:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase(row.getAdmCampo(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase("TIPO PROGRAMA", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase(row.getIdPrograma()+" "+ row.getProgramaAplicacion(), FontFactory.getFont(FontFactory.HELVETICA, 11)));

	        table.addCell(new Phrase("PARA:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase(row.getJefeAplicaion(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");

	        table.addCell(new Phrase("PREDIO:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase(row.getCampo(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");

	        table.addCell(new Phrase("FECHA RECOMENDACION:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase(row.getFechaInicio(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");

	        table.addCell(new Phrase("FECHA INICIO APLICACION:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase(row.getFechaInicio(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");
	        System.out.println(row.getFormaAplicacion());
	        table.addCell(new Phrase("FORMA APLICACION:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(new Phrase(row.getFormaAplicacion(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");

	        table.addCell(new Phrase("VEL. VIENTO:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");

	        table.addCell(new Phrase("ORIEN. VIENTO:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");

	        table.addCell(new Phrase("TEMPERATURA:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");
	        table.addCell(" ");
	        Color color = null;
	        Font font = null;
	        
	        PdfPTable tableMateriales = new PdfPTable(10);
	        tableMateriales.setWidthPercentage(100);
	        tableMateriales.setWidths(new float[] { 2, 2, 2, 2,1,2,2,2,2,2 });
	        tableMateriales.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        String[] headers1 = new String[]{
                "NOMBRE COMERCIAL",
                "INGREDIENTE ACTIVO",
                "DOSIS (100)",
                "DOSIS POR HA",
                "UM",
                "DOSIS POR BOMBADA",
                "CAPACIDAD MAQUINA",
                "CONTROL",
                "APLICACION NRO",
                "A RETIRAR A BODEGA",
	        };
	        for (int i = 0; i < headers1.length; ++i) {
	        	PdfPCell cellMat = new PdfPCell();
		        Paragraph headMat = new Paragraph(headers1[i], FontFactory.getFont(FontFactory.HELVETICA, 11));
		        cellMat.addElement(headMat);
		        cellMat.setBackgroundColor(color.LIGHT_GRAY);
		        cellMat.setBorder(Rectangle.NO_BORDER);
	            tableMateriales.addCell(cellMat);
	        }
	        for(listaMateriales lm: row.getListaMateriales()){
	        	tableMateriales.addCell(new Phrase(lm.getNombreComercial(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase(lm.getIngActivo(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase(String.valueOf(lm.getDosisCien()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase(String.valueOf(lm.getDosisHas()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase(String.valueOf(lm.getMeins()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase(String.valueOf(lm.getDosisBombada()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase(String.valueOf(lm.getCapMaquina()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase(lm.getTipoControl(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase("0", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMateriales.addCell(new Phrase(String.valueOf(lm.getCantidad()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        }
	        PdfPTable tableCuartel = new PdfPTable(5);
	        tableCuartel.setWidthPercentage(80);
	        tableCuartel.setHorizontalAlignment(Element.ALIGN_LEFT);
	        tableCuartel.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        tableCuartel.setWidths(new float[] { 4, 1, 2, 3, 3 });

	        String[] headers2 = new String[]{
                "CUARTEL",
                "HAS",
                "VARIEDAD",
                "E. FENOLOGICO",
                "MOJAMIENTO X HA",
	        };
	        for (int i = 0; i < headers2.length; ++i) {
	        	PdfPCell cellMat = new PdfPCell();
		        Paragraph headMat = new Paragraph(headers2[i], FontFactory.getFont(FontFactory.HELVETICA, 11));
		        cellMat.addElement(headMat);
		        cellMat.setBackgroundColor(color.LIGHT_GRAY);
		        cellMat.setBorder(Rectangle.NO_BORDER);
		        tableCuartel.addCell(cellMat);
	        }
	        float totalHas = 0;
	        for(listaCuarteles lc: row.getListaCuarteles()){
	        	tableCuartel.addCell(new Phrase(lc.getNombreCuartel(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableCuartel.addCell(new Phrase(String.valueOf(lc.getHas()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableCuartel.addCell(new Phrase(lc.getNombreVariedad(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableCuartel.addCell(new Phrase(lc.getEstFenologico(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableCuartel.addCell(new Phrase(String.valueOf(lc.getMajamiento()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	totalHas = totalHas + lc.getHas();
	        }
	        BigDecimal numberBigDecimal = new BigDecimal(totalHas);
	        numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
	        tableCuartel.addCell(new Phrase("Total HAS:", FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        tableCuartel.addCell(new Phrase(String.valueOf(numberBigDecimal), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        tableCuartel.addCell("");
	        tableCuartel.addCell("");
	        tableCuartel.addCell("");
	        
	        PdfPTable tableMaq = new PdfPTable(7);
	        tableMaq.setWidthPercentage(100);
	        tableMaq.setWidths(new float[] { 3, 3, 3, 3, 2,2,2 });
	        tableMaq.setHorizontalAlignment(Element.ALIGN_LEFT);
	        tableMaq.getDefaultCell().setBorder(Rectangle.NO_BORDER);

	        String[] headers3 = new String[]{
                "NOMBRE APLICADOR",
                "TRACTOR",
                "IMPLEMENTO",
                "CAMBIO TRACTOR",
                "MARCHA TRACTOR",
                "PRESION BOMBA",
                "VELOCIDAD",	
	        };
	        for (int i = 0; i < headers3.length; ++i) {
	        	PdfPCell cellMaq = new PdfPCell();
		        Paragraph headMat = new Paragraph(headers3[i], FontFactory.getFont(FontFactory.HELVETICA, 11));
		        cellMaq.addElement(headMat);
		        cellMaq.setBackgroundColor(color.LIGHT_GRAY);
		        cellMaq.setBorder(Rectangle.NO_BORDER);
		        tableMaq.addCell(cellMaq);
	        }

	        for(listaMaq lq: row.getListaMaq()){
	        	tableMaq.addCell(new Phrase(lq.getResponsable(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMaq.addCell(new Phrase(lq.getMaquinaria(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMaq.addCell(new Phrase(lq.getImplemento(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMaq.addCell(new Phrase(lq.getCambio(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMaq.addCell(new Phrase(lq.getMarcha(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMaq.addCell(new Phrase(lq.getPresion(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableMaq.addCell(new Phrase(lq.getVelocidad(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        }
	        
	        PdfPTable tableVar = new PdfPTable(7);
	        tableVar.setWidthPercentage(75);
	        tableVar.setHorizontalAlignment(Element.ALIGN_LEFT);
	        tableVar.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        String[] headers4 = new String[]{
                "VARIEDAD",
                "FECHA ESTIMADA",
                "FECHA VIABLE",
                "DIAS A COSECHA",
                "DIAS CARENCIA",
                "MERCADO CONTROL AP",
                "HORAS REINGRESO",	
	        };
	        for (int i = 0; i < headers4.length; ++i) {
	        	PdfPCell cellVar = new PdfPCell();
		        Paragraph headMat = new Paragraph(headers4[i], FontFactory.getFont(FontFactory.HELVETICA, 11));
		        cellVar.addElement(headMat);
		        cellVar.setBackgroundColor(color.LIGHT_GRAY);
		        cellVar.setBorder(Rectangle.NO_BORDER);
		        tableVar.addCell(cellVar);
	        }
	        for(listaVariedad lv: row.getListaVariedad()){
	        	tableVar.addCell(new Phrase(lv.getNombreVariedad(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableVar.addCell(new Phrase(lv.getFechaEstCosecha(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableVar.addCell(new Phrase(lv.getFechaViable(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableVar.addCell(new Phrase(lv.getDiferencia(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableVar.addCell(new Phrase(String.valueOf(lv.getMaxCerencia()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableVar.addCell(new Phrase(lv.getMercado(), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        	tableVar.addCell(new Phrase(String.valueOf(lv.getMaxHr()), FontFactory.getFont(FontFactory.HELVETICA, 11)));
	        }
	        PdfPTable tableFoot = new PdfPTable(3);
	        tableFoot.setWidthPercentage(100);
	        tableFoot.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
	        tableFoot.setHorizontalAlignment(Element.ALIGN_CENTER);
	        tableFoot.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        String[] headers5 = new String[]{
                "V° B° RESPONSABLE ORDEN APLICACIÓN",
                "V° B° RESPONSABLE ORDEN APLICACIÓN",
                "V° B° RESPONSABLE ORDEN APLICACIÓN",
                "ADMINISTRADOR",	
                "JEFE HUERTO O ENCARGADO APLICACIONES",	
                "BODEGA",		
	        };
	        for (int i = 0; i < headers5.length; ++i) {
	        	PdfPCell cellFoot = new PdfPCell();
		        Paragraph headMat = new Paragraph(headers5[i], FontFactory.getFont(FontFactory.HELVETICA, 11));
		        cellFoot.addElement(headMat);
		        cellFoot.setBorder(Rectangle.NO_BORDER);
		        tableFoot.addCell(cellFoot);
	        }
	        Paragraph obs = new Paragraph("OBSERVACIONES:", FontFactory.getFont(FontFactory.HELVETICA, 12));
	        Paragraph p = new Paragraph();
//	        addEmptyLine(p, 2);
	        document.add(tableT);
	        document.add(table);
	        document.add(tableMateriales);
	        document.add(tableCuartel);
	        document.add(tableMaq);
	        document.add(tableVar);
	        document.add(p);
	        document.add(obs);
	        document.add(p);
	        document.add(tableFoot);
	        document.close();
	        return row.getNombre()+".pdf";
//	        return "hola";
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public static String GET_CAMPO_CECO (String origen, String destino)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		String ceco = "";
		try {
			sql += 	"SELECT ceco FROM camos_ceco WHERE origen = '"+origen+"' AND destino = '"+destino+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()){
				ceco = rs.getString("ceco");
			}
			System.out.println(ceco);
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
		return ceco;
	}	
	public static String ADD_CAMPO_CECO (String origen, String destino, String ceco)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		String res = "";
		try {
			sql += 	"CALL addCampoCeco('"+origen+"','"+destino+"','"+ceco+"')";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		} finally {
			db.close();
		}
		return res;
	}	
	public String GENERAR_WORD_MASIVO(String sociedad, String periodo, String campo,HttpSession httpSession) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		
		PreparedStatement psp = null;
		String sqlsp = "";
		
		String mes = periodo.split("-")[1];
		String mes2 = periodo.split("-")[1];
		if(mes.equals("01")){mes = "ENERO";}
		if(mes.equals("02")){mes = "FEBRERO";}
		if(mes.equals("03")){mes = "MARZO";}
		if(mes.equals("04")){mes = "ABRIL";}
		if(mes.equals("05")){mes = "MAYO";}
		if(mes.equals("06")){mes = "JUNIO";}
		if(mes.equals("07")){mes = "JULIO";}
		if(mes.equals("08")){mes = "AGOSTO";}
		if(mes.equals("09")){mes = "SAPTIEMBRE";}
		if(mes.equals("10")){mes = "OCTUBRE";}
		if(mes.equals("11")){mes = "NOVIEMBRE";}
		if(mes.equals("12")){mes = "DICIEMBRE";}
		String urlDocGenerado = utils.reportesExcel();
		urlDocGenerado=urlDocGenerado+"Asiatencia"+".docx";
		File archivo = new File(urlDocGenerado);
		String tituloDocumento = "PLANILLA DE ASISTENCIA "+mes+" "+periodo.split("-")[0];
		XWPFDocument documento = new XWPFDocument();
		PreparedStatement ps1 = null;
		try {
			sqlsp = 	"CALL sa_getFolioAsistencia('"+periodo+"-01','"+sociedad+"', '"+campo+"')";
			System.out.println(sqlsp);
			psp = db.conn.prepareStatement(sqlsp);
			ResultSet rsSP = psp.executeQuery(sqlsp);
			while(rsSP.next()){
				JSONObject e = new JSONObject();
				e.put("id", rsSP.getInt("id"));
				e.put("codigo", rsSP.getInt("codigo"));
				e.put("nombre", rsSP.getString("nombre"));
				e.put("rut", rsSP.getString("rut"));
				e.put("folio", rsSP.getInt("folio"));
				e.put("horas", rsSP.getFloat("horas"));
				e.put("faltas", rsSP.getFloat("faltas"));
				e.put("hx", rsSP.getFloat("hx"));
				e.put("fechaInicio", rsSP.getString("fechaInicio"));
				e.put("fechaFin", rsSP.getString("fechaFin"));
				e.put("empresa", rsSP.getString("empresa"));
				ArrayList<String> titulos = new ArrayList<>();
				ArrayList<String[]> data = new ArrayList<>();
				float total_horas = 0;
				float faltas = 0;
				float horas_extras = 0;
				sql = 	"SELECT ";
				sql += 		"CASE WHEN(WEEKDAY(f.fecha) = 0) THEN 'Lunes' ";
				sql += 		"WHEN(WEEKDAY(f.fecha) = 1) THEN 'Martes' ";
				sql += 		"WHEN(WEEKDAY(f.fecha) = 2) THEN 'Miércoles' ";
				sql += 		"WHEN(WEEKDAY(f.fecha) = 3) THEN 'Jueves' ";
				sql += 		"WHEN(WEEKDAY(f.fecha) = 4) THEN 'Viernes' END AS dia, ";
				sql += 		"DATE_FORMAT(f.fecha, '%d-%m-%Y')as fecha, IFNULL(t.hora_desde, '--:--') AS hora_desde, IFNULL(t.hora_hasta, '--:--') AS hora_hasta, IF(t.horas IS NULL, 0.000, t.horas) AS horas, IF(t.horas IS NULL, 9.000, 9-t.horas) AS falta, IFNULL(t.hx, 0.000) AS hx ";
				sql += 	"FROM ";
				sql += 		"sw_fechas f ";
				sql += 		"LEFT JOIN contratos ct ON ct.fechaInicio_actividad <= f.fecha AND (ct.FechaTerminoContrato >= f.fecha OR ct.FechaTerminoContrato IS NULL) ";
				sql += 		"LEFT JOIN trabajadores tr ON tr.codigo = ct.codigo_trabajador ";
				sql += 		"LEFT JOIN (SELECT ";
				sql += 				"SUM(rd.horas_trabajadas) AS horas, ";
				sql += 				"rd.trabajador, ";
				sql += 				"IF(rd.fecha_i IS NULL, rg.fecha, rd.fecha_i) AS fecha, ";
				sql += 				"SUM(rd.horas_extras) AS hx, ";
				sql += 				"rd.hora_desde, rd.hora_hasta ";
				sql += 			"FROM ";
				sql += 				"rendimiento_diario rd ";
				sql += 				"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
				sql += 			"WHERE ";
				sql += 				"rd.estado = 3 ";
				sql += 				"AND rd.idContrato = "+e.getInt("id")+" ";
				sql += 				"AND (DATE_FORMAT(rd.fecha_i, '%Y%m') = "+periodo.split("-")[0]+mes2+" OR DATE_FORMAT(rg.fecha, '%Y%m') = "+periodo.split("-")[0]+mes2+") ";
				sql += 			"GROUP BY 2 , 3, 5,6) t ON (t.trabajador = tr.id AND t.fecha = f.fecha) ";
				sql += 	"WHERE ";
				sql += 		"DATE_FORMAT(f.fecha, '%Y%m') = "+periodo.split("-")[0]+mes2+" ";
				sql += 		"AND WEEKDAY(f.fecha) IN(SELECT h.dia FROM horario h LEFT JOIN horario_campo hc ON (hc.id = h.idHc) WHERE campo = ct.idHuertoContrato) ";
				sql += 		"AND f.fecha NOT IN (SELECT fechaFeriado FROM sw_m_feriados WHERE DATE_FORMAT(fechaFeriado, '%Y%m') = "+periodo.split("-")[0]+mes2+") ";
				sql += 		"AND ct.id = "+rsSP.getInt("id")+" ";
				ps = db.conn.prepareStatement(sql);
				System.out.println(sql);
				ResultSet rs = ps.executeQuery(sql); 
				ResultSetMetaData md = rs.getMetaData();
				int count = md.getColumnCount();
				for (int i = 1; i <= count; i++) {
					titulos.add(md.getColumnName(i));
				}
				faltas = 0;
				total_horas = 0;
				while(rs.next()){
					String datos = "";
					faltas+=rs.getFloat("falta");
					total_horas += rs.getFloat("horas");
					for(String t: titulos){
						String horas = rs.getString(t);
						if(horas == null){
							horas = " ";
						}
						datos += horas+",";
					}
					String [] dataAux = datos.split(",");
					data.add(dataAux);
				}
	        
				//Declaramos el titulo y le asignamos algunas propiedades
				XWPFParagraph titulo_doc = documento.createParagraph();
				titulo_doc.setAlignment(ParagraphAlignment.CENTER);
				titulo_doc.setVerticalAlignment(TextAlignment.TOP);
				        
				//Declaramos el parrafo y le asignamos algunas propiedades
				String contenidoParrafo1 = "RUT: "+e.getString("rut");
				XWPFParagraph parrafo = documento.createParagraph();
				parrafo.setAlignment(ParagraphAlignment.BOTH);
				XWPFRun r1 = titulo_doc.createRun();
				r1.setBold(true);
				r1.setText(tituloDocumento);
				r1.setFontFamily("Arial");
				r1.setFontSize(12);
				r1.setTextPosition(10);
				r1.setUnderline(UnderlinePatterns.SINGLE);
				XWPFRun r2 = parrafo.createRun();
				r2.setText(contenidoParrafo1);
				r2.addTab();
				r2.addTab();
				String contenidoParrafo2 = "NOMBRE: "+e.getString("nombre");
				r2.setText(contenidoParrafo2);
				r2.addTab();
				String folioText = "FOLIO: "+e.getInt("folio");
				r2.setText(folioText);
				r2.setFontSize(10);
				r2.addCarriageReturn();
				
				String fechas = "FECHA INICIO: "+e.getString("fechaInicio");
				XWPFRun r3 = parrafo.createRun();
				r3.setText(fechas);
				r3.addTab();
				String fechas2 = "FECHA TERMINO: "+e.getString("fechaFin");
				r3.setText(fechas2);
				r3.addTab();
				String empresa = e.getString("empresa");
				r3.setText(empresa);
				r3.setFontSize(10);
				r3.addCarriageReturn();
				
				XWPFTable tableTwo = documento.createTable();
				tableTwo.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "1C7331");
				tableTwo.setWidth(3000);
				XWPFTableRow tableTwoRowOne = tableTwo.getRow(0);
				tableTwoRowOne.getCell(0).setText("");
				tableTwoRowOne.createCell().setText("Fecha");
				tableTwoRowOne.createCell().setText("Entrada");
				tableTwoRowOne.createCell().setText("Salida");
				tableTwoRowOne.createCell().setText("Trabajadas");
				tableTwoRowOne.createCell().setText("Faltas");
				tableTwoRowOne.createCell().setText("Horas Extras");
				tableTwoRowOne.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(4).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(5).setVerticalAlignment(XWPFVertAlign.CENTER);
				tableTwoRowOne.getCell(6).setVerticalAlignment(XWPFVertAlign.CENTER);
				for (int i = 0; i < data.size(); i++) {
				    String node = "node";
				    XWPFTableRow tr = null;
				    node = node + (i + 1);
				    if (tr == null) {
				        tr = tableTwo.createRow();
				        tr.getCell(0).removeParagraph(0);
				        tr.setHeight(4);
				        XWPFParagraph paragraph = tr.getCell(0).addParagraph();
				        setRun(paragraph.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[0]) , false);

				        tr.getCell(1).removeParagraph(0);
				        XWPFParagraph paragraph1 = tr.getCell(1).addParagraph();
				        setRun(paragraph1.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[1]) , false);

				        tr.getCell(2).removeParagraph(0);
				        XWPFParagraph paragraph2 = tr.getCell(2).addParagraph();
				        setRun(paragraph2.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[2]) , false);

				        tr.getCell(3).removeParagraph(0);
				        XWPFParagraph paragraph3 = tr.getCell(3).addParagraph();
				        setRun(paragraph3.createRun() , "Arial" ,9 , String.valueOf(data.get(i)[3]) , false);

				        tr.getCell(4).removeParagraph(0);
				        XWPFParagraph paragraph4 = tr.getCell(4).addParagraph();
				        setRun(paragraph4.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[4]) , false);

				        tr.getCell(5).removeParagraph(0);
				        XWPFParagraph paragraph5 = tr.getCell(5).addParagraph();
				        setRun(paragraph5.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[5]) , false);

				        tr.getCell(6).removeParagraph(0);
				        XWPFParagraph paragraph6 = tr.getCell(6).addParagraph();
				        setRun(paragraph6.createRun() , "Arial" , 9 , String.valueOf(data.get(i)[6]) , false);
				    }
				}
				XWPFTableRow trF = tableTwo.createRow();
				trF.getCell(0).setText("TOTAL");
				trF.getCell(1).setText("");
				trF.getCell(2).setText("");
				trF.getCell(3).setText("");
				trF.getCell(4).setText(String.valueOf(total_horas));
				trF.getCell(5).setText(String.valueOf(faltas));
				trF.getCell(6).setText(String.valueOf(horas_extras));
				for (int i = 0; i < tableTwo.getNumberOfRows(); i++) {
			        XWPFTableRow row = tableTwo.getRow(i);
			        int numCells = row.getTableCells().size();
			        for (int j = 0; j < numCells; j++) {
			            XWPFTableCell cell = row.getCell(j);
			            CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
			            CTTcPr pr = cell.getCTTc().addNewTcPr();
			            pr.addNewNoWrap();
			            cellWidth.setW(BigInteger.valueOf(2880));
			        }
			    }
				String firma = "FIRMA: ________________" ;
				XWPFParagraph salto = documento.createParagraph();
				XWPFRun r5 = salto.createRun();
				r5.addCarriageReturn();
				XWPFParagraph firma_doc = documento.createParagraph();
				firma_doc.setAlignment(ParagraphAlignment.LEFT);
				firma_doc.setVerticalAlignment(TextAlignment.BOTTOM);
				XWPFRun r4 = firma_doc.createRun();
				r4.setText(firma);
				r4.setFontSize(12);
				r4.setBold(true);
				r4.setUnderline(UnderlinePatterns.SINGLE);
//				r4.addCarriageReturn();
				r4.addBreak(BreakType.PAGE);
			}
			rsSP.close();
//			ps.close();
			db.conn.close();
			FileOutputStream word = new FileOutputStream(archivo);
		    documento.write(word);
		    documento.close();
		    word.close();
	        return archivo.getName();
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
	}
	private static void setRun (XWPFRun run , String fontFamily , int fontSize , String text , boolean bold ) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setText(text);
        run.setBold(bold);
    }
	public static ArrayList<cierre_mensual> GET_REPORTE_CIERRE(String campo, String periodo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<cierre_mensual> data = new ArrayList<cierre_mensual>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql += 	"SELECT c.*, s.denominacionSociedad as nombreCen, so.denominacionSociedad as nombreIm, ";
			sql += 		"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ',tr.nombre)) as trabajador ";
			sql += 	"FROM ";
			sql += 		"cierre_terceros c ";
			sql += 		"LEFT JOIN trabajadores tr ON(tr.id = c.idTrabajador) ";
			sql += 		"LEFT JOIN sociedad s ON(s.sociedad = c.sociedad_centralizacion) ";
			sql += 		"LEFT JOIN sociedad so ON(so.sociedad = c.sociedad_imputacion) ";
			sql += 	"WHERE ";
			sql += 		"periodo = DATE_FORMAT('"+periodo+"-01', '%Y%m') ";
			sql += 		"AND sociedad_centralizacion = '"+campo+"' ";
			sql += 	"ORDER BY trabajador";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				cierre_mensual e = new cierre_mensual();
				e.setId(rs.getInt("id"));
				e.setTrabajador(rs.getString("trabajador"));
				e.setCuenta(rs.getString("cuenta"));
				e.setCeco(rs.getString("ceco"));
				e.setOrdenco(rs.getString("ordenco"));
				e.setValor(rs.getFloat("valor"));
				e.setPercent(rs.getFloat("porcentaje"));
				e.setPeriodo(rs.getString("periodo"));
				e.setCosto_empresa(rs.getInt("costo_empresa"));
				e.setSociedadCentralizacion(rs.getString("sociedad_centralizacion"));
				e.setSociedadImputacion(rs.getString("sociedad_imputacion"));
				e.setNombreCen(rs.getString("nombreCen"));
				e.setNombreIm(rs.getString("nombreIm"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	public static ArrayList<trabajadores>  GET_CONTRATISTAS_X(int id) throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		ArrayList<trabajadores> data = new ArrayList<trabajadores>();
		try {
			String sql = "";
			sql += 	"SELECT DISTINCT idContratista ";
			sql += 	"FROM ";
			sql += 		"trabajadores t ";
			sql += 		"LEFT JOIN contratos c ON (c.codigo_trabajador = t.codigo) ";
			sql += 	"WHERE ";
			sql += 		"idContratista IS NOT NULL ";
			ps = db.conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				trabajadores e = new trabajadores();
				e.setId(rs.getInt("idContratista"));
				data.add(e);
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return data;
	}
	public static ArrayList<CentralizacionDetalleTmp> GET_GASTOS_CECO(String sociedad, String campo, int periodo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CentralizacionDetalleTmp> data = new ArrayList<CentralizacionDetalleTmp>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = 	"CALL sa_getGastosCeco("+periodo+", '"+sociedad+"', '"+campo+"', '');";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				CentralizacionDetalleTmp e = new CentralizacionDetalleTmp();
//				e.setCodTrabajador(rs.getString("codTrabajador"));
				e.setConcepto(rs.getString("concepto"));
				e.setCuenta(rs.getString("cuenta"));
				e.setDescripcion(rs.getString("descripcion"));
//				e.setFecha_proceso(rs.getString("fecha_proceso"));
				e.setId_sociedad(rs.getString("sociedad"));
				e.setIdCECO(rs.getString("idCECO"));
				e.setPeriodo(rs.getInt("periodo"));
				e.setProveedor(rs.getString("proveedor"));
				e.setValor(rs.getString("valor"));
				e.setOrdenCO(rs.getString("ordenco"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	public static ArrayList<CentralizacionDetalleTmp> GET_DETALLE_GASTOS_CECO(String cuenta, String ceco, String ordenco) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ArrayList<CentralizacionDetalleTmp> data = new ArrayList<CentralizacionDetalleTmp>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql +=	"SELECT ";
			sql +=		"g.codTrabajador, ";
			sql +=		"UPPER(CONCAT(tr.apellidoPaterno, ' ', tr.apellidoMaterno, ' ', tr.nombre)) as nombre, ";
			sql +=		"FORMAT(ROUND(valor), 0, 'de_DE') AS valor ";
			sql +=	"FROM ";
			sql +=		"gastos_ceco g ";
			sql +=		"LEFT JOIN trabajadores tr ON(tr.codigo = g.codTrabajador) ";
			sql +=	"WHERE ";
			sql +=		"cuenta = '"+cuenta+"' ";
			sql +=		"AND ((g.idCECO = '"+ceco+"' OR g.idCECO IS NULL) ";
			sql +=		"AND (g.ordenco = '"+ordenco+"' OR g.ordenco IS NULL));";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				CentralizacionDetalleTmp e = new CentralizacionDetalleTmp();
				e.setCodTrabajador(rs.getString("codTrabajador"));
				e.setDescripcion(rs.getString("nombre"));
				e.setValor(rs.getString("valor"));
				data.add(e);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return data;
	}
	public static respuesta UPD_HORAS_TRABAJADAS(String r) throws Exception{
		PreparedStatement ps = null;
		respuesta res = new respuesta();
		res.setEstado(false);
		res.setObjeto(r);
		JSONObject c = new JSONObject(r);
		ConnectionDB db = new ConnectionDB();
		String sql = "";
		try {
			sql += 	"UPDATE rendimiento_diario rd2 ";
			sql += 	"SET ";
			sql += 		"rd2.hora_desde = ?, ";
			sql += 		"rd2.hora_hasta = ?, ";
			sql += 		"rd2.inicio_colacion = ?, ";
			sql += 		"rd2.termino_colacion = ? ";
			sql += 	"WHERE ";
			sql += 		"rd2.codigo IN (SELECT ";
			sql += 				"codigo ";
			sql += 			"FROM ";
			sql += 				"(SELECT * FROM rendimiento_diario) rd ";
			sql += 				"LEFT JOIN rendimiento_general rg ON (rd.codigo_rg = rg.codigo_rg) ";
			sql += 			"WHERE ";
			sql += 				"(rd.fecha_i = ? OR rg.fecha = ?) ";
			sql += 				"AND rd.trabajador = (SELECT id FROM trabajadores WHERE codigo = ?)) ";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, c.getString("hora_desde"));
			ps.setString(2, c.getString("hora_hasta"));
			ps.setString(3, c.getString("inicio_colacion"));
			ps.setString(4, c.getString("termino_colacion"));
			ps.setString(5, c.getString("fecha"));
			ps.setString(6, c.getString("fecha"));
			ps.setInt(7, c.getInt("codigo"));
			System.out.println(ps.toString());
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
	public static String GET_HORARIO_CAMPO(String campo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		try{
			sql +=	"SELECT ";
			sql +=		"hc.id, hc.campo, hc.hrs_semanal, h.dia as n_dia, ";
			sql +=		"CASE(h.dia)";
			sql +=			"WHEN(0)THEN 'Lunes' ";
			sql +=			"WHEN(1)THEN 'Martes' ";
			sql +=			"WHEN(2)THEN 'Miercoles' ";
			sql +=			"WHEN(3)THEN 'Jueves' ";
			sql +=			"WHEN(4)THEN 'Viernes' ";
			sql +=			"WHEN(5)THEN 'Sabado' ";
			sql +=			"WHEN(6)THEN 'Domingo'";
			sql +=		"END AS dia, ";
			sql +=		"h.hr_desde, h.hr_hasta, h.hrs, h.inicio_colacion, h.termino_colacion, h.colacionOnOff ";
			sql +=	"FROM ";
			sql +=		"horario_campo hc ";
			sql +=		"LEFT JOIN horario h ON(hc.id = h.idHc) ";
			sql +=	"WHERE ";
			sql +=		"hc.campo = '"+campo+"' ";
			sql +=	"ORDER BY h.dia";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("id", rs.getInt("id"));
				ob.put("campo", rs.getString("campo"));
				ob.put("hrs_semanal", rs.getString("hrs_semanal"));
				ob.put("n_dia", rs.getInt("n_dia"));
				ob.put("dia", rs.getString("dia"));
				ob.put("hr_desde", rs.getString("hr_desde"));
				ob.put("hr_hasta", rs.getString("hr_hasta"));
				ob.put("inicio_colacion", rs.getString("inicio_colacion"));
				ob.put("termino_colacion", rs.getString("termino_colacion"));
				ob.put("hrs", rs.getString("hrs"));
				ob.put("colacionOnOff", rs.getInt("colacionOnOff"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
			result.put("data", array);
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	public static String GET_HORARIOS(HttpSession httpSession) throws Exception{
		
		session ses = new session(httpSession);
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		try{
			sql +=	"SELECT ";
			sql +=		"hc.id, hc.campo, hc.hrs_semanal, h.dia as n_dia, ";
			sql +=		"CASE(h.dia)";
			sql +=			"WHEN(0)THEN 'Lunes' ";
			sql +=			"WHEN(1)THEN 'Martes' ";
			sql +=			"WHEN(2)THEN 'Miercoles' ";
			sql +=			"WHEN(3)THEN 'Jueves' ";
			sql +=			"WHEN(4)THEN 'Viernes' ";
			sql +=			"WHEN(5)THEN 'Sabado' ";
			sql +=			"WHEN(6)THEN 'Domingo'";
			sql +=		"END AS dia, ";
			sql +=		"h.hrs, ";
			sql +=		"TIME_FORMAT(h.hr_desde, '%H:%i') hr_desde, ";
			sql +=		"TIME_FORMAT(h.hr_hasta, '%H:%i') hr_hasta, ";
			sql +=		"TIME_FORMAT(h.inicio_colacion, '%H:%i') inicio_colacion, ";
			sql +=		"TIME_FORMAT(h.termino_colacion, '%H:%i') termino_colacion, ";
			sql +=		"colacionOnOff ";
			sql +=	"FROM ";
			sql +=		"horario_campo hc ";
			sql +=		"LEFT JOIN horario h ON(hc.id = h.idHc) ";
			sql +=	"WHERE ";
			sql +=		"hc.campo IN(SELECT codigo_campo FROM usuario_campo WHERE codigo_usuario = "+ses.getIdUser()+")";
			sql +=	"ORDER BY campo, n_dia";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("id", rs.getInt("id"));
				ob.put("campo", rs.getString("campo"));
				ob.put("hrs_semanal", rs.getString("hrs_semanal"));
				ob.put("n_dia", rs.getInt("n_dia"));
				ob.put("dia", rs.getString("dia"));
				ob.put("hr_desde", rs.getString("hr_desde"));
				ob.put("hr_hasta", rs.getString("hr_hasta"));
				ob.put("inicio_colacion", rs.getString("inicio_colacion"));
				ob.put("termino_colacion", rs.getString("termino_colacion"));
				ob.put("hrs", rs.getFloat("hrs"));
				ob.put("colacionOnOff", rs.getInt("colacionOnOff"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
			result.put("data", array);
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	public static boolean ADD_HORARIO_CAMPO (String c)throws Exception{
		JSONObject r = new JSONObject(c);
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		System.out.println(c);
		try {
			sql = "INSERT INTO horario_campo ( campo, hrs_semanal) VALUES(?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, r.getString("campo"));
			ps.setDouble(2, r.getDouble("hrs_semanal"));
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static boolean ADD_HORARIO (String c)throws Exception{
		JSONObject r = new JSONObject(c);
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		System.out.println(c);
		try {
			sql = "INSERT INTO horario (idHc, dia, hr_desde, hr_hasta, hrs, inicio_colacion, termino_colacion, colacionOnOff) VALUES((SELECT MAX(id)FROM horario_campo),?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, r.getString("dia"));
			ps.setString(2, r.getString("hr_desde"));
			ps.setString(3, r.getString("hr_hasta"));
			ps.setDouble(4, r.getDouble("hrs"));
			ps.setString(5, r.getString("inicio_colacion"));
			ps.setString(6, r.getString("termino_colacion"));
			ps.setInt(7, r.getInt("colacionOnOff"));
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static boolean UPD_HORARIO_CAMPO (String c)throws Exception{
		JSONObject r = new JSONObject(c);
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		System.out.println(c);
		try {
			sql = "UPDATE horario_campo SET hrs_semanal = ? WHERE campo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setDouble(1, r.getDouble("hrs_semanal"));
			ps.setString(2, r.getString("campo"));
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static String DEL_HORARIO (String campo)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "DELETE FROM horario WHERE idHc = (SELECT id FROM horario_campo WHERE campo = ?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, campo);
			ps.execute();
			return "Ok";
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return "Ha ocurido un error";
	}
	public static boolean UPD_HORARIO (String c)throws Exception{
		System.out.println(c);
		JSONObject r = new JSONObject(c);
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try {
			sql = "INSERT INTO horario (idHc, dia, hr_desde, hr_hasta, hrs, inicio_colacion, termino_colacion, colacionOnOff) VALUES(?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, r.getInt("id"));
			ps.setString(2, r.getString("dia"));
			ps.setString(3, r.getString("hr_desde"));
			ps.setString(4, r.getString("hr_hasta"));
			ps.setDouble(5, r.getDouble("hrs"));
			ps.setString(6, r.getString("inicio_colacion"));
			ps.setString(7, r.getString("termino_colacion"));
			ps.setInt(8, r.getInt("colacionOnOff"));
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		} catch  (Exception e){	
			System.out.println("Error:" + e.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static boolean UPD_CECOS_CAMPO (String row, HttpSession httpSession)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONObject e = new JSONObject(row);
		session ses = new session(httpSession);
		try {
			sql = "UPDATE campo SET cecos = '"+e.getString("cecos")+"' WHERE campo = '"+e.getString("campo")+"'";
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			SESION mc= new SESION(httpSession);
			simpleagroDB.getCAMPO(httpSession, ses.getIdUser());
			mc.save();
			return true;
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
		} catch  (Exception ex){	
			System.out.println("Error:" + ex.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static boolean UPD_DATOS_CAMPO (String row, HttpSession httpSession)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONObject e = new JSONObject(row);
		session ses = new session(httpSession);
		try {
			sql += "UPDATE campo SET gerente_zonal = '"+e.getString("adm_campo")+"', ";
			sql += "grupo_maquinaria = '"+e.getString("cecoMaq")+"', ";
			sql += "grupo_repuesto = '"+e.getString("grupo_repuesto")+"', ";
			sql += "grupo_co = '"+e.getString("grupo_co")+"', ";
			sql += "grupo = '"+e.getString("grupo")+"', ";
			sql += "tipo_maq = '"+e.getInt("tipo_maq")+"' ";
			sql += "WHERE campo = '"+e.getString("campo")+"'";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			SESION mc= new SESION(httpSession);
			simpleagroDB.getCAMPO(httpSession, ses.getIdUser());
			mc.save();
			return true;
		} catch (SQLException ex) {
			System.out.println("Error:" + ex.getMessage());
		} catch  (Exception ex){	
			System.out.println("Error:" + ex.getMessage());
		} finally {
			ps.close();
			db.conn.close();
		}
		return false;
	}
	public static String LIQ_CONTRATISTA_PDF(int id, String row) throws Exception{
		JSONObject json = new JSONObject(row);
		System.out.println(json);
		JSONArray data = new JSONArray(json.get("data").toString());
		String urlDocGenerado = utils.reportesExcel();
		try {
			Document document = new Document(PageSize.A3.rotate());
			OutputStream out = new FileOutputStream(urlDocGenerado+"Liquidacion"+id+".pdf");
			PdfWriter writer = PdfWriter.getInstance(document, out);
			
//			PdfWriter writer = new PdfWriter(urlDocGenerado+row.getNombre()+".pdf");
			document.open();
	        // calculate diamond shaped hole
	        ArrayList<PdfPCell> cells = new ArrayList<PdfPCell>();
	        PdfPTable tableT = new PdfPTable(3);
	        tableT.setWidthPercentage(100);
	        tableT.setWidths(new float[] { 2, 2, 6});
	        tableT.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        PdfPCell cellTitulo = new PdfPCell();
	        
	        PdfPCell pgCellWhite = new PdfPCell();
	        pgCellWhite.addElement(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6)));
	        pgCellWhite.setBorder(Rectangle.NO_BORDER);
	        
	        PdfPCell pgTitulo = new PdfPCell();
	        pgTitulo.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph("Contratista", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgTitulo);

	        PdfPCell pgContratistaName = new PdfPCell();
	        pgContratistaName.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph(json.getString("nombreContratista"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgContratistaName);
	        tableT.addCell(pgCellWhite);
	        cells.add(pgCellWhite);

	        PdfPCell pgTitulo2 = new PdfPCell();
	        pgTitulo2.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph("Rut", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgTitulo2);

	        PdfPCell pgRut = new PdfPCell();
	        pgRut.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph(json.getString("rut"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgRut);
	        tableT.addCell(pgCellWhite);
	        cells.add(pgCellWhite);

	        PdfPCell pgTitulo3 = new PdfPCell();
	        pgTitulo3.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph("Codigo:", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgTitulo3);

	        PdfPCell pgCodigo = new PdfPCell();
	        pgCodigo.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph(json.getString("contratista"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgCodigo);
	        tableT.addCell(pgCellWhite);
	        cells.add(pgCellWhite);

	        PdfPCell pgTitulo4 = new PdfPCell();
	        pgTitulo4.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph("N° Liquidacion", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgTitulo4);

	        PdfPCell pgNliquidacion = new PdfPCell();
	        pgNliquidacion.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph(json.getString("codigo"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgNliquidacion);
	        tableT.addCell(pgCellWhite);
	        cells.add(pgCellWhite);

	        PdfPCell pgTitulo5 = new PdfPCell();
	        pgTitulo5.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph("N° Comprobante", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgTitulo5);

	        PdfPCell pgNcomprobante = new PdfPCell();
	        pgNcomprobante.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph(json.getString("asiento_contable"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgNcomprobante);
	        tableT.addCell(pgCellWhite);
	        cells.add(pgCellWhite);

	        PdfPCell pgTitulo6 = new PdfPCell();
	        pgTitulo6.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph("N° factura", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgTitulo6);

	        PdfPCell pgNfactura = new PdfPCell();
	        pgNfactura.setBorder(Rectangle.NO_BORDER);
	        tableT.addCell(new Paragraph(json.getString("n_factura"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        cells.add(pgNfactura);
	        tableT.addCell(pgCellWhite);
	        cells.add(pgCellWhite);
	        tableT.setHorizontalAlignment(Element.ALIGN_LEFT);

	        Color color = null;
	        Font font = null;
	        
	        PdfPTable t = new PdfPTable(10);
	        t.setWidthPercentage(100);
	        t.setWidths(new float[] { 1, 4, 3, 3,2,2,2,2,1,2});
	        t.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        String[] headers1 = new String[]{
                "Fecha",
                "Cuartel/Ceco",
                "Faena",
                "Labor",
                "Tipo Pago",
                "Valor Trato",
                "Rendimiento",
                "Valor Rendimiento",
                "Bono",
                "Valor Liquido"
	        };
	        for (int i = 0; i < headers1.length; ++i) {
	        	PdfPCell cellMat = new PdfPCell();
		        Paragraph headMat = new Paragraph(headers1[i], FontFactory.getFont(FontFactory.HELVETICA, 10));
		        cellMat.addElement(headMat);
		        cellMat.setBackgroundColor(color.LIGHT_GRAY);
		        cellMat.setBorder(Rectangle.NO_BORDER);
	            t.addCell(cellMat);
	        }
	        for(int ix = 0; ix < data.length(); ix++){
	        	JSONObject e = new JSONObject(data.getJSONObject(ix).toString());
	        	t.addCell(new Phrase(e.getString("fecha"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("nCeco"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("nFaena"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("nLabor"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("tipo_pago"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("valor_trato"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("rendimiento"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("valor_rendimiento"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("bono1"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	t.addCell(new Phrase(e.getString("valor_liquido"), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        	
			}
	        PdfPTable f = new PdfPTable(10);
	        f.setWidthPercentage(100);
	        f.setWidths(new float[] { 1, 4, 3, 3,2,2,2,1,2,2});
	        f.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	        
	        for(int i = 0; i <= 7; i++){
	        	f.addCell(pgCellWhite);
	        }
	        PdfPCell cNeton = new PdfPCell();
	        cNeton.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase("Neto: ", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        
	        PdfPCell cNeto = new PdfPCell();
	        cNeto.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase(String.valueOf(json.getInt("valor_liquido")), FontFactory.getFont(FontFactory.HELVETICA, 9)));

	        for(int i = 0; i <= 7; i++){
	        	f.addCell(pgCellWhite);
	        }
	        PdfPCell cIvan = new PdfPCell();
	        cIvan.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase("Iva: ", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        
	        PdfPCell cIva = new PdfPCell();
	        cIva.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase(String.valueOf(json.getInt("iva")), FontFactory.getFont(FontFactory.HELVETICA, 9)));

	        for(int i = 0; i <= 7; i++){
	        	f.addCell(pgCellWhite);
	        }
	        PdfPCell cTotaln = new PdfPCell();
	        cTotaln.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase("Total Liquido: ", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        
	        PdfPCell cTotal = new PdfPCell();
	        cTotal.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase(String.valueOf(json.getInt("total")), FontFactory.getFont(FontFactory.HELVETICA, 9)));

	        for(int i = 0; i <= 9; i++){
	        	f.addCell(pgCellWhite);
	        }

	        for(int i = 0; i <= 7; i++){
	        	f.addCell(pgCellWhite);
	        }
	        PdfPCell cNeto2n = new PdfPCell();
	        cNeto2n.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase("Neto: ", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        
	        PdfPCell cNeto2 = new PdfPCell();
	        cNeto2.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase(String.valueOf(json.getInt("valor_liquido")), FontFactory.getFont(FontFactory.HELVETICA, 9)));

	        for(int i = 0; i <= 7; i++){
	        	f.addCell(pgCellWhite);
	        }
	        PdfPCell cAnticipon = new PdfPCell();
	        cAnticipon.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase("(-) Anticipo:", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        
	        PdfPCell cAnticipo = new PdfPCell();
	        cAnticipo.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase(String.valueOf(json.getInt("anticipo")), FontFactory.getFont(FontFactory.HELVETICA, 9)));

	        for(int i = 0; i <= 7; i++){
	        	f.addCell(pgCellWhite);
	        }
	        PdfPCell cRetencionn = new PdfPCell();
	        cRetencionn.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase("(-) Retencion:", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        
	        PdfPCell cRetencion = new PdfPCell();
	        cRetencion.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase(String.valueOf(json.getInt("valor_retencion")), FontFactory.getFont(FontFactory.HELVETICA, 9)));

	        for(int i = 0; i <= 7; i++){
	        	f.addCell(pgCellWhite);
	        }
	        PdfPCell cTotalApagarn = new PdfPCell();
	        cTotalApagarn.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase("Liquido a Pagar:", FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        
	        PdfPCell cTotalApagar = new PdfPCell();
	        cTotalApagar.setBorder(Rectangle.NO_BORDER);
	        f.addCell(new Phrase(String.valueOf(json.getInt("valor_liquido") - json.getInt("valor_retencion") - json.getInt("anticipo")), FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        
	        
	        
	        document.add(tableT);
	        document.add(t);
	        document.add(f);
	        document.close();
	        return "Liquidacion"+id+".pdf";
		}catch (FileNotFoundException ex) {
			System.out.println("Archivo no localizable en sistema de archivos");
		    return "0";
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
	        return "0";
		}
		//return false;
		//return "0";
	}
	public static boolean INSERT_DOC_LIQ (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="UPDATE liquidacion_contratista SET pdf = ? WHERE codigo = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, row.getString("nombre"));
			ps.setInt(2, row.getInt("id"));
			System.out.println(ps.toString());
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: insertN" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error: insertN" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return false;
	}
	public static String GET_GASTOS_TRABAJADOR(int periodo, int sociedad) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		
//		URL url = new URL("http://200.55.206.140/SCLEM/JSON_ZMOV_10016.aspx?CARACTERISTICA=ZMM05&USPAS=soporteX*XWultu@208");
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("GET");
//		conn.setRequestProperty("Accept", "application/json");
//		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//
//		String output = "";
//		
//		if ((output = br.readLine()) != null) {
//			System.out.println(output);
//			if(!output.equals("Object reference not set to an instance of an object.")){
//				
//			}
//			
//		}
		
		try{
			sql +=	"SELECT ";
			sql +=		"h.id, ";
			sql +=		"UPPER(CONCAT(tr.apellidoPaterno,' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre, ";
			sql +=		"h.periodo, ph.descripcion, FORMAT(h.monto, 4, 'de_DE') AS monto, ";
			sql +=		"CASE (h.frecuencia)";
			sql +=			"WHEN (180) THEN '1 Vez' ";
			sql +=			"WHEN (181) THEN 'Cuotas' ";
			sql +=			"WHEN (182) THEN 'Indefinido' ";
			sql +=		"END AS frecuencia, ";
			sql +=		"h.cuotas, ";
			sql +=		"CASE (h.llave_moneda) ";
			sql +=			"WHEN (4) THEN 'Pesos' ";
			sql +=			"WHEN (2) THEN 'U.F.' ";
			sql +=		"END AS moneda ";
			sql +=	"FROM ";
			sql +=		"sw_haberesDescuentos h ";
			sql +=		"LEFT JOIN trabajadores tr ON (tr.codigo = h.codigo_trabajador) ";
			sql +=		"LEFT JOIN sw_p_haberesDescuentos ph ON (ph.codigo = h.codigo_hd) ";
			sql +=	"WHERE ";
			sql +=		"periodo = "+periodo+" ";
			sql +=		"AND h.codigo_trabajador IN (SELECT codigo_trabajador FROM contratos WHERE idSociedad = "+sociedad+") ";
			sql +=		"AND h.codigo_hd != 0 ";
			sql +=	"ORDER BY 2";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("id", rs.getInt("id"));
				ob.put("nombre", rs.getString("nombre"));
				ob.put("periodo", rs.getInt("periodo"));
				ob.put("descripcion", rs.getString("descripcion"));
				ob.put("monto", rs.getString("monto"));
				ob.put("frecuencia", rs.getString("frecuencia"));
				ob.put("cuotas", rs.getInt("cuotas"));
				ob.put("moneda", rs.getString("moneda"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
			result.put("data", array);
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	public static String GET_TRABAJADORES_PERIODO(String periodo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONArray array = new JSONArray();
		try{
			sql +=	"SELECT DISTINCT ";
			sql +=		"UPPER(CONCAT(tr.apellidoPaterno,' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre, ";
			sql +=		"ct.idHuertoContrato AS campo, tr.id ";
			sql +=	"FROM ";
			sql +=		"trabajadores tr ";
			sql +=		"LEFT JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador) ";
			sql +=	"WHERE ";
			sql +=		"tr.id IN (SELECT DISTINCT trabajador FROM rendimiento_diario WHERE DATE_FORMAT(fecha_i, '%Y-%m') = '"+periodo+"' AND rd_contratista = 0) ";
			sql +=	"ORDER BY 1";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("id", rs.getInt("id"));
				ob.put("nombre", rs.getString("nombre"));
				ob.put("campo", rs.getString("campo"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	public static String GET_TRABAJADORES_SOCIEDAD(int sociedad, String periodo, int haber) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONArray array = new JSONArray();
		try{
			sql +=	"SELECT DISTINCT ";
			sql +=		"UPPER(CONCAT(tr.apellidoPaterno,' ', tr.apellidoMaterno, ' ', tr.nombre)) AS nombre, ";
			sql +=		"ct.idHuertoContrato AS campo, tr.id, tr.codigo, tr.rut ";
			sql +=	"FROM ";
			sql +=		"trabajadores tr ";
			sql +=		"LEFT JOIN contratos ct ON (tr.codigo = ct.codigo_trabajador) ";
			sql +=	"WHERE ";
			sql +=		"tr.id IN (SELECT DISTINCT trabajador FROM rendimiento_diario WHERE DATE_FORMAT(fecha_i, '%Y-%m') = '"+periodo+"' AND rd_contratista = 0) ";
			sql +=		"AND tr.codigo IN (SELECT DISTINCT codigo_trabajador FROM sw_haberesDescuentos WHERE periodo = "+periodo.split("-")[0]+periodo.split("-")[1]+" AND tipo = 'h' AND codigo_hd = "+haber+") ";
			sql +=		"AND ct.idSociedad = "+sociedad+" ";
			sql +=	"ORDER BY 1";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("id", rs.getInt("id"));
				ob.put("codigo", rs.getString("codigo"));
				ob.put("nombre", rs.getString("nombre"));
				ob.put("campo", rs.getString("campo"));
				ob.put("rut", rs.getString("rut"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	public static String GET_HABERES_CECO(int trabajador, int periodo, int haber, int sociedad) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONArray array = new JSONArray();
		try{
			sql +=	"SELECT *FROM (SELECT ";
			sql +=		"'Agro' AS tipo, ";
			sql +=		"rd.fecha_i AS fecha, CONCAT(tr.apellidoPaterno,' ',tr.apellidoMaterno, ' ', tr.nombre) AS nombre, ";
			sql +=		"IFNULL(rd.ceco, rd.ordenco) AS ceco, ";
			sql +=		"f.cuenta_prd as cuenta, ";
			sql +=		"hdp.descripcion AS haber, ";
			sql +=		"v.codigo,v.costo_empresa,hd.monto,t.suma, ";
			sql +=		"CAST(v.costo_empresa*((hd.monto/t.suma)) AS SIGNED) AS valor ";
			sql +=	"FROM ";
			sql +=		"vw_costoEmpresaAgro v ";
			sql +=		"LEFT JOIN rendimiento_diario rd ON (rd.codigo = v.codigo) ";
			sql +=		"LEFT JOIN trabajadores tr ON (tr.id = rd.trabajador) ";
			sql +=		"LEFT JOIN contratos ct ON (ct.codigo_trabajador = tr.codigo) ";
			sql +=		"LEFT JOIN sw_haberesDescuentos hd ON (hd.codigo_trabajador = tr.codigo AND v.periodo = hd.periodo AND hd.idContrato = ct.id) ";
			sql +=		"LEFT JOIN sw_p_haberesDescuentos hdp ON (hdp.codigo = hd.codigo_hd) ";
			sql +=		"LEFT JOIN labor l ON (l.codigo = rd.labor) ";
			sql +=		"LEFT JOIN faena f ON (l.faena = f.codigo) ";
			sql +=		"LEFT JOIN(SELECT ";
			sql +=				"SUM(vw.costo_empresa) AS suma, trs.codigo ";
			sql +=			"FROM ";
			sql +=				"vw_costoEmpresaAgro vw ";
			sql +=				"LEFT JOIN rendimiento_diario rds ON (rds.codigo = vw.codigo) ";
			sql +=				"LEFT JOIN trabajadores trs ON (trs.id = rds.trabajador) ";
			sql +=			"WHERE ";
			sql +=				"vw.periodo = "+periodo+" ";
			sql +=			"GROUP BY 2) t ON (t.codigo = tr.codigo) ";
			sql +=	"WHERE ";
			sql +=		"v.periodo = "+periodo+" ";
			sql +=		"AND ct.idSociedad = "+sociedad+" ";
			sql +=		"AND hd.codigo_hd = "+haber+" ";
			if(trabajador != 0){
				sql +=	"AND tr.codigo = "+trabajador+" ";
			}
			sql +=	"UNION ALL ";
			sql +=	"SELECT DISTINCT ";
			sql +=		"'Work' AS tipo, ";
			sql +=		"hd.fecha_inicio AS fecha, ";
			sql +=		"CONCAT(tr.apellidoPaterno,' ',tr.apellidoMaterno, ' ', tr.nombre) AS nombre, ";
			sql +=		"IF(cct.idCECO IS NULL OR cct.ordenCO IS NULL, ct.idCECOContrato, IF(cct.idCECO = '',cct.ordenCO, cct.idCECO)) AS ceco, ";
			sql +=		"CASE ";
			sql +=			"WHEN (cct.cuenta != '' AND cct.cuenta IS NOT NULL) THEN cct.cuenta ";
			sql +=			"ELSE ";
			sql +=				"CASE ";
			sql +=					"WHEN (hdp.cuentaContable IS NOT NULL) THEN hdp.cuentaContable ";
			sql +=					"ELSE ";
			sql +=					"CASE ";
			sql +=						"WHEN ((IF(cct.idCECO IS NULL OR cct.ordenCO IS NULL, ct.idCECOContrato, IF(cct.idCECO = '',cct.ordenCO, cct.idCECO)) = cp.ceco)) THEN cp.cuenta ";
			sql +=					"END ";
			sql +=				"END ";
			sql +=		"END AS cuenta, ";
			sql +=		"hdp.descripcion AS haber, ";
			sql +=		"tr.codigo, hd.monto AS costo_empresa, hd.monto, hd.monto AS suma, ";
			sql +=		"IF(cct.idCECO IS NULL, hd.monto, CAST((hd.monto*cct.porcentaje)/100 AS SIGNED)) as valor ";
			sql +=	"FROM ";
			sql +=		"sw_haberesDescuentos hd ";
			sql +=		"LEFT JOIN trabajadores tr ON (hd.codigo_trabajador = tr.codigo) ";
			sql +=		"LEFT JOIN contratos ct ON (ct.codigo_trabajador = tr.codigo AND hd.idContrato = ct.id) ";
			sql +=		"LEFT JOIN sw_r_centroCostoTrabajador cct ON (cct.codTrabajador = tr.codigo) ";
			sql +=		"LEFT JOIN sw_p_haberesDescuentos hdp ON (hdp.codigo = hd.codigo_hd) ";
			sql +=		"LEFT JOIN sw_centralizacionCuentaPeriodo cp ON (cp.ceco = ct.idCECOContrato or cp.ceco = cct.idCECO) ";
			sql +=	"WHERE ";
			sql +=		"hd.periodo = "+periodo+" AND tr.id NOT IN(SELECT trabajador FROM rendimiento_diario WHERE date_format(fecha_i, '%Y%m') = "+periodo+") ";
			sql +=		"AND ct.idSociedad = "+sociedad+" ";
			sql +=		"AND hd.codigo_hd = "+haber+" ";
			if(trabajador != 0){
				sql +=	"AND tr.codigo = "+trabajador+" ";
			}
			sql +=		")t ";
			sql +=	"WHERE cuenta IS NOT NULL ";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("tipo", rs.getString("tipo"));
				ob.put("fecha", rs.getString("fecha"));
				ob.put("nombre", rs.getString("nombre"));
				ob.put("ceco", rs.getString("ceco"));
				ob.put("cuenta", rs.getString("cuenta"));
				ob.put("haber", rs.getString("haber"));
				ob.put("codigo", rs.getInt("codigo"));
				ob.put("costo_empresa", rs.getInt("costo_empresa"));
				ob.put("monto", rs.getInt("monto"));
				ob.put("suma", rs.getInt("suma"));
				ob.put("valor", rs.getInt("valor"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	public static String GET_HABERES_TRABAJADOR_MES(int trabajador, int periodo) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONArray array = new JSONArray();
		try{
			sql +=	"SELECT DISTINCT ";
			sql +=		"ph.descripcion, ph.codigo ";
			sql +=	"FROM ";
			sql +=		"sw_haberesDescuentos h ";
			sql +=		"LEFT JOIN sw_p_haberesDescuentos ph ON (ph.codigo = h.codigo_hd) ";
			sql +=	"WHERE ";
			sql +=		"periodo = "+periodo+" ";
			sql +=		"AND h.codigo_trabajador = "+trabajador+" ";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("codigo", rs.getInt("codigo"));
				ob.put("descripcion", rs.getString("descripcion"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	public static String GET_HABERES_AGRO() throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		JSONArray array = new JSONArray();
		try{
			sql +=	"SELECT DISTINCT ";
			sql +=		"hdp.* ";
			sql +=	"FROM ";
			sql +=		"sw_haberesDescuentos hd ";
			sql +=		"LEFT JOIN sw_p_haberesDescuentos hdp ON (hd.codigo_hd = hdp.codigo) ";
			sql +=	"WHERE ";
			sql +=		"hd.tipo = 'h' AND hdp.codigo IS NOT NULL";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("codigo", rs.getInt("codigo"));
				ob.put("descripcion", rs.getString("descripcion"));
				ob.put("tipo", rs.getString("tipo"));
				array.put(ob);
			}
			rs.close();
			ps.close();
			db.conn.close();
		}catch (SQLException ex){
			System.out.println("Erro:" + ex.getMessage());
		}catch (Exception ex){
			System.out.println("Error:" + ex.getMessage());
		}finally {
			db.close();
		}
		return array.toString();
	}
	// Obtener documento de la tabla sw_documentos
	public static Documentos GET_IMAGEN_LIQUIDACION(int codigo) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Documentos documentos = new Documentos();
		try {
			sql = "SELECT imgFactura FROM liquidacion_contratista WHERE codigo = "+codigo+"";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()) {
				documentos.setDocumento(rs.getBlob("imgFactura"));
			}
			return documentos;
		} catch (Exception e) {
			throw new Exception("GET_IMAGEN_LIQUIDACION: " + e.getMessage());
		} finally {
			db.close();
		}
	}
	public static String GET_RECOTEC (String sql)throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			for (int i = 1; i <= count; i++) {
				titulos.add(md.getColumnLabel(i));
			}
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				for(int i = 0; i < titulos.size(); i++){
					ob.put(titulos.get(i).toUpperCase(), rs.getObject(titulos.get(i)) == null ? JSONObject.NULL: rs.getObject(titulos.get(i)));
				}
				array.put(ob);
			}
			json.put("data", array);
			json.put("error", 0);
			json.put("mensaje", "Ok");

		}catch (SQLException ex){
			json.put("data", array);
			json.put("error", "SQL");
			json.put("mensaje", ex.getMessage());
		}catch (Exception ex){
			json.put("data", array);
			json.put("error", "Servidor");
			json.put("mensaje", ex.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return json.toString();
	}
	public static String GET_FOTO_COMERCIO(String codigo, String fecha) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Documentos documentos = new Documentos();
		String foto = "";
		try {
			sql = "SELECT CAST(FOTO AS CHAR(1000000) CHARACTER SET utf8) FOTO FROM VISITA_COMERCIO WHERE date_format(fecha, '%Y-%m-%d') = '"+fecha+"' AND ID_COMERCIO = "+codigo+" LIMIT 1";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()) {
				System.out.println(rs.getString("FOTO"));
				foto = rs.getString("FOTO");
			}
			return foto;
		} catch (Exception e) {
			throw new Exception("Error: " + e.getMessage());
		} finally {
			db.close();
		}
	}
	public static String DELETE (String sql)throws Exception{
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			ps = db.conn.prepareStatement(sql);
			ps.execute();
			json.put("data", array);
			json.put("error", 0);
			json.put("mensaje", "Ok");

		}catch (SQLException ex){
			json.put("data", array);
			json.put("error", "SQL");
			json.put("mensaje", ex.getMessage());
		}catch (Exception ex){
			json.put("data", array);
			json.put("error", "Servidor");
			json.put("mensaje", ex.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return json.toString();
	}
}