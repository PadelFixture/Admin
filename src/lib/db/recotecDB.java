package lib.db;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import wordCreator.utils;

public class recotecDB {

	public static boolean UPDATE_RECEPTOR (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="CALL "+row.getString("SP")+"(?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, row.getInt("_ID"));
			ps.setString(2, row.getString("_RUT"));
			ps.setString(3, row.getString("_RAZON_SOCIAL"));
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
	public static boolean UPDATE_USUARIOS (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="CALL UPDATE_USUARIOS(?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, row.getInt("_ID"));
			ps.setString(2, row.getString("USUARIO"));
			ps.setString(3, row.getString("PERFIL"));
			ps.setString(4, row.getString("PASS"));
			ps.setString(5, row.getString("RECEPTOR"));
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
	public static boolean UPDATE_CONCEPTOS (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="UPDATE CONCEPTOS SET DESCRIPCION = ? WHERE id = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, row.getString("DESCRIPCION"));
			ps.setInt(2, row.getInt("_ID"));
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
	public static boolean ADD_USUARIO (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="INSERT INTO USUARIOS( USUARIO, PASSWORD, COD_RECEPTOR, PERFIL) VALUES(?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, row.getString("USUARIO"));
			ps.setString(2, row.getString("PASS"));
			ps.setInt(3, row.getInt("RECEPTOR"));
			ps.setString(4, row.getString("PERFIL"));
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
	public static boolean ADD_CONCEPTO (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="INSERT INTO CONCEPTOS(DESCRIPCION, TIPO) VALUES(?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, row.getString("DESCRIPCION"));
			ps.setString(2, row.getString("TIPO"));
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
	public static boolean INSERT_RECEPTOR (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="INSERT INTO RECEPTOR( rut, RAZON_SOCIAL, PROPIO, TIPO_RECEPTOR) VALUES( ?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, row.getString("_RUT"));
			ps.setString(2, row.getString("_RAZON_SOCIAL"));
			ps.setInt(3, row.getInt("_PROPIO"));
			ps.setInt(4, row.getInt("_TIPO_RECEPTOR"));
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
	public static boolean DELETE_RECEPTOR (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="DELETE FROM RECEPTOR WHERE id = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, row.getInt("_ID"));
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
	public static boolean DELETE_USUARIO (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="DELETE FROM USUARIOS WHERE id = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, row.getInt("_ID"));
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
	public static boolean DELETE_CONCEPTO (String i)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject row = new JSONObject(i);
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="DELETE FROM CONCEPTOS WHERE id = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, row.getInt("_ID"));
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
	public static String INSERT_VALIDACION_SIM(ConnectionDB db, String json) throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject j = new JSONObject(json);
		try {
			sql = "CALL INSERT_ACTIVACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1, j.getString("ID_MES"));
			ps.setString(2, j.getString("ID_DIA_CORTE"));
			ps.setString(3, j.getString("TIPO_LINEA"));
			ps.setString(4, j.getString("SUBCATEGORIA"));
			ps.setString(5, j.getString("DESCR_MOVIL"));
			ps.setString(6, j.getString("ID_IMSI"));
			ps.setString(7, j.getString("MODELOEQUIPOACT"));
			ps.setString(8, j.getString("CODIGO_SIM"));
			ps.setString(9, j.getString("BODEGAORI"));
			ps.setString(10, j.getString("BODEGA_DEST"));
			ps.setString(11, j.getString("DESC_HABILITACION"));
			ps.setString(12, j.getString("ID_IMEI_ORIGINAL_PACK"));
			ps.setString(13, j.getString("PATENTE"));
			ps.setString(14, j.getString("ICCID"));
			ps.setString(15, j.getString("COD_PDV"));
			ps.setString(16, j.getString("DESCRIP"));
			ps.setString(17, j.getString("TIPO"));
			ps.setString(18, j.getString("TIPO2"));
			ps.setString(19, j.getString("BOD_SAP"));
			ps.setString(20, j.getString("BOD_ORIG_DEST"));
			ps.setString(21, j.getString("ZONA"));
			ps.setString(22, j.getString("GERENCIA"));
			ps.setString(23, j.getString("GRUPO_CANAL"));
			ps.setString(24, j.getString("CANAL"));
			ps.setString(25, j.getString("SOCIO"));
			ps.setString(26, j.getString("RUT_SOCIO"));
			ps.setString(27, j.getString("NOMBRE_PDV"));
			ps.setString(28, j.getString("KAM_OP"));
			ps.setString(29, j.getString("KAM_COM"));
			ps.setString(30, j.getString("JEFE_NEGOCIOS"));
			ps.setString(31, j.getString("CANAL_ORIGINAL"));
			ps.setString(32, j.getString("hab"));
			ps.setString(33, j.getString("rec"));
			ps.setString(34, j.getString("SUM_of_Q_RECARGAS"));
			ps.setString(35, j.getString("SUM_of_MONTO"));
			ps.setString(36, j.getString("MIN_of_FECHA"));
			ps.setString(37, j.getString("TIPO_IMPORT"));
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();

			System.out.println(j.getString("TIPO_IMPORT"));
			if(rs.next()){
//				System.out.println(rs.getString("res"));
				
			}
			return "Ok";
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} catch (Exception e) {
			System.out.println("Error Interno del sistema:" + e.getMessage());
			return e.getMessage();
		} finally {
//			ps.close();
		}
	}
	public static String INSERT_STOCK_SIM(String row) throws Exception{
		JSONObject res = new JSONObject();
		ConnectionDB db = new ConnectionDB();
		PreparedStatement ps = null;
		try {
			JSONArray arr = new JSONArray(row);
//			db.conn.setAutoCommit(false);
			Statement multiQuery = db.conn.createStatement();
//			Statement multiQuery2 = db.conn.createStatement();
//			int c = 0;
			for(int i = 0; i < arr.length(); i++){
				JSONObject e = arr.getJSONObject(i);
	        	String sql = "";
	        	sql += "CALL INSERT_STOCK_SIM('"+e.get("_FACTURA")+"', ";
	        	sql += "'"+e.get("_MATERIAL")+"', ";
	        	sql += "'"+e.get("_IMEI")+"', ";
	        	sql += "'"+e.get("_ICCID")+"', ";
	        	sql += "'"+e.get("_IMSI")+"', ";
	        	sql += "'"+e.get("_FECHA")+"', ";
	        	sql += "'"+e.get("_RECEPTOR")+"', ";
	        	sql += "'"+e.get("_TIPO_RECEPTOR")+"', ";
	        	sql += "'"+e.get("_PROVEEDOR")+"') ";
//	        	ps = db.conn.prepareStatement(sql);
//	        	ps.executeQuery(sql);
//	        	if(i > 32000){
//	        		multiQuery2.addBatch(sql);
//	        	}else{
	        		multiQuery.addBatch(sql);
//	        	}
//				c++;
			}
			multiQuery.executeBatch();
//			if(c > 32000){
//				multiQuery2.executeBatch();
//			}
//			db.conn.commit();
			res.put("error", 0);
			res.put("message", "Ok");
		} catch (SQLException e) {
			res.put("error", 1);
			res.put("message", e.getMessage());
//			db.conn.rollback();
		}catch (Exception e) {
			res.put("error", 2);
			res.put("message", e.getMessage());
			res.put("messagev1", e.getStackTrace().toString());
			res.put("messagev2", e.getSuppressed());
			res.put("messagev3", e.getCause());
			System.out.println(e);
			System.out.println(e.getStackTrace());
//			db.conn.rollback();
		} finally {
			db.close();
		}
		return res.toString();
	}
	public static String GET_USUARIOS (int id, int idUser)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONArray array = new JSONArray();
		ConnectionDB db = new ConnectionDB();
		try {
			sql +=	"SELECT us.*, c.* FROM USUARIOS us LEFT JOIN systemPerfil c ON(us.PERFIL = c.idPerfil) WHERE us.idUSUARIOS = "+idUser+" and us.ESTADO = 1 ";
			sql +=	"UNION ";
			sql +=	"SELECT us.*, c.* FROM USUARIOS us LEFT JOIN systemPerfil c ON(us.PERFIL = c.idPerfil) WHERE us.ESTADO = 1 AND us.COD_RECEPTOR IN(SELECT id FROM RECEPTOR WHERE PADRE IN(SELECT id FROM RECEPTOR WHERE PADRE = "+id+")) ";
			sql +=	"UNION ";
			sql +=	"SELECT us.*, c.* FROM USUARIOS us LEFT JOIN systemPerfil c ON(us.PERFIL = c.idPerfil) WHERE COD_RECEPTOR IN(SELECT id FROM RECEPTOR WHERE PADRE = "+id+") and us.ESTADO = 1";
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				
				JSONObject ob = new JSONObject();
				ob.put("IDUSUARIOS", rs.getInt("idUSUARIOS"));
				ob.put("USUARIO", rs.getString("USUARIO"));
				ob.put("PASSWORD", rs.getString("PASSWORD"));
				ob.put("COD_RECEPTOR", rs.getString("COD_RECEPTOR"));
				ob.put("PERFIL", rs.getString("PERFIL"));
				ob.put("IDPERFIL", rs.getString("idPerfil"));
				ob.put("DESCRIPCION", rs.getString("nombre"));
				array.put(ob);
			}
		} catch (SQLException e) {
			System.out.println("Error: insertN" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error: insertN" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return array.toString();
	}
	public static String GET_PERFIL ()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONArray array = new JSONArray();
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="SELECT *FROM systemPerfil";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("IDPERFIL", rs.getInt("idPerfil"));
				ob.put("NOMBRE", rs.getString("nombre"));
				array.put(ob);
			}
		} catch (SQLException e) {
			System.out.println("Error: insertN" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error: insertN" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return array.toString();
	}
	public static String GET_TIPO_CONCEPTO ()throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONArray array = new JSONArray();
		ConnectionDB db = new ConnectionDB();
		try {
			sql ="SELECT DISTINCT TIPO FROM CONCEPTOS";
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				JSONObject ob = new JSONObject();
				ob.put("TIPO", rs.getString("TIPO"));
				array.put(ob);
			}
		} catch (SQLException e) {
			System.out.println("Error: insertN" + e.getMessage());
		} catch (Exception e){
			System.out.println("Error: insertN" + e.getMessage());
		} finally {
			ps.close();
			db.close();
		}
		return array.toString();
	}
	public static String RECOTEC (String row)throws Exception{
		PreparedStatement ps = null;
		String sql = "";
		JSONObject object = new JSONObject(row);
		JSONArray array = new JSONArray();
		JSONObject data = new JSONObject();
		ConnectionDB db = new ConnectionDB();
		ArrayList<String> titulos = new ArrayList<>();
		try {
			String fill = "";
			if(object.optJSONArray("FILTERS") != null){
				JSONArray filters = new JSONArray(object.get("FILTERS").toString());
				int c = 0;
				for(int ix = 0; ix < filters.length(); ix++){
		        	JSONObject e = new JSONObject(filters.getJSONObject(ix).toString());
		        	c++;
		        	fill += (c != filters.length())? "'"+e.get("value")+"'," : "'"+e.get("value")+"'";
				}
			}
			sql ="CALL "+object.getString("SP")+"("+fill+")";
			System.out.println(sql);
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
					ob.put(titulos.get(i), rs.getObject(titulos.get(i)) == null ? JSONObject.NULL: rs.getObject(titulos.get(i)));
				}
				array.put(ob);
			}
			data.put("data", array);
			data.put("message", "Ok");
			data.put("error", 0);
		} catch (SQLException e) {
			data.put("data", array);
			data.put("message", e.getMessage());
			data.put("error", 1);
		} catch (Exception e){
			data.put("data", array);
			data.put("message", e.getMessage());
			data.put("error", 2);
		} finally {
			ps.close();
			db.close();
		}
		return data.toString();
	}
	public static String UPLOAD_PHOTO (HttpServletRequest request, MultipartFile multipartFile)throws Exception{
	    final long serialVersionUID = 1L;
	    final String UPLOAD_DIRECTORY = utils.Licencia();
    	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		JSONObject data = new JSONObject();
		System.out.println(isMultipart);
		System.out.println(0);
//    	if (isMultipart) {
    		System.out.println(1);
	        FileItemFactory factory = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        try {
	    		System.out.println(2);
	            List<FileItem> multiparts = upload.parseRequest(request);
	            Object[] aux = multiparts.toArray(); 
	            FileItem iaux = (FileItem)aux[1];
	            FileItem iaux2 = (FileItem)aux[2];
	            String nombre = String.valueOf(iaux2.getFieldName());
	            for (FileItem item : multiparts) {
	                if (!item.isFormField()) {
	                	dteBD e = new dteBD();
		                String[] name = new File(item.getName()).getName().split("\\.");
		                String nm = name[0]+nombre+"."+name[1];
		                item.write(new File(UPLOAD_DIRECTORY + File.separator + nm));
	                }
	            }
	            data.put("error", 0);
	            data.put("message", "Ok");
	    		System.out.println(3);
	        } 
	        catch (Exception e){
	        	System.out.println("Error: "+e.getMessage());
	            data.put("error", 1);
	            data.put("message", e.getMessage());
	        }
//	    }
    	return data.toString();
	}
	public JSONObject saveImageBase64FromWeb(String row) throws IOException{
		JSONObject res = new JSONObject();
		JSONArray data = new JSONArray(row);
		res.put("error", 0);
		res.put("message", "OK");
//		Properties config =ConfigApp.getProperties();
	    final String UPLOAD_DIRECTORY = utils.Licencia();
//		String fiePath = config.getProperty("FILE_UPLOAD_PATH") + "/img/" ;
//		String webFilePath=config.getProperty("movilidad.files.FILE_WEB_IMG_PATH");
		// create a buffered image
		try{
			JSONArray datos = new JSONArray();
			for(int i = 0; i < data.length(); i++){
				JSONObject e = data.getJSONObject(i);
				String idrequest = UUID.randomUUID().toString();
				String timeStampId = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
				String fileName=timeStampId+"_"+idrequest;
				String base64=(e.getString("base64"));
//				byte[] decodedImg = Base64.getMimeDecoder().decode(base64);
				byte[] decodedImg = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
				Path destinationFile = Paths.get(UPLOAD_DIRECTORY, fileName+".png");
				Files.write(destinationFile, decodedImg);
				JSONObject a = new JSONObject();
				a.put("url", fileName+".png");
				datos.put(a);
			}
			res.put("data",datos);
			
//			System.out.println(res.toString());
		} catch (IOException e) {
			e.printStackTrace();
			res.put("message", "" + e.getMessage() + " " + e.getLocalizedMessage() + " -> " + e.getStackTrace()[0].getLineNumber());
			res.put("error", 1);
		}catch (Exception e) {
			e.printStackTrace();
			res.put("message", "" + e.getMessage() + " " + e.getLocalizedMessage() + " -> " + e.getStackTrace()[0].getLineNumber());
			res.put("error", 2);
		}
		System.out.println(res.toString());
		return res;
	}
	public JSONObject saveFileWebRecotec(String row) throws IOException{
		JSONObject res = new JSONObject();
		JSONArray data = new JSONArray(row);
		res.put("error", 0);
		res.put("message", "OK");
//		Properties config =ConfigApp.getProperties();
	    final String UPLOAD_DIRECTORY = utils.Incidencia();
//		String fiePath = config.getProperty("FILE_UPLOAD_PATH") + "/img/" ;
//		String webFilePath=config.getProperty("movilidad.files.FILE_WEB_IMG_PATH");
		// create a buffered image
		try{
			JSONArray datos = new JSONArray();
			for(int i = 0; i < data.length(); i++){
				JSONObject e = data.getJSONObject(i);
				String idrequest = UUID.randomUUID().toString();
				String timeStampId = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
				String fileName=timeStampId+"_"+idrequest;
				String base64=(e.getString("base64"));
				String name=(e.getString("name"));
//				byte[] decodedImg = Base64.getMimeDecoder().decode(base64);
				byte[] decodedImg = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
				Path destinationFile = Paths.get(UPLOAD_DIRECTORY, fileName+name);
				Files.write(destinationFile, decodedImg);
				JSONObject a = new JSONObject();
				a.put("url", fileName+".png");
				datos.put(a);
			}
			res.put("data",datos);
			
//			System.out.println(res.toString());
		} catch (IOException e) {
			e.printStackTrace();
			res.put("message", "" + e.getMessage() + " " + e.getLocalizedMessage() + " -> " + e.getStackTrace()[0].getLineNumber());
			res.put("error", 1);
		}catch (Exception e) {
			e.printStackTrace();
			res.put("message", "" + e.getMessage() + " " + e.getLocalizedMessage() + " -> " + e.getStackTrace()[0].getLineNumber());
			res.put("error", 2);
		}
		System.out.println(res.toString());
		return res;
	}
}