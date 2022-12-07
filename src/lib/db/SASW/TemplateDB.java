package lib.db.SASW;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import lib.ClassSASW.Template;

import lib.db.ConnectionDB;

public class TemplateDB {
	public static byte[] convertBlob(Blob blob) {
		if(blob==null)return null;
		try {
		    InputStream in = blob.getBinaryStream();
		    int len = (int) blob.length(); //read as long
		    long pos = 1; //indexing starts from 1
		    byte[] bytes = blob.getBytes(pos, len); 		    
		    in.close();
		    return bytes;	    
	         } catch (Exception e) {
		        System.out.println(e.getMessage());
		 }
		 return null;
	}
	public String decodeCharByteArray(byte[] inputArray, String charSet) { 
		//Ex charSet="US-ASCII"
	  	Charset theCharset = Charset.forName(charSet);
		CharsetDecoder decoder = theCharset.newDecoder();
		ByteBuffer theBytes = ByteBuffer.wrap(inputArray);
		CharBuffer inputArrayChars = null;
		try {
			inputArrayChars = decoder.decode(theBytes);
		} catch (CharacterCodingException e) {
			System.err.println("Error decoding");
		}
		return inputArrayChars.toString();
	}
	public static boolean createTemplate(Template t) throws SQLException {

		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Date d= new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd h:mm:ss:SSSS");
		String cadenaFecha= formato.format(d);
		try{
			sql = "INSERT INTO sw_template (documento, extension, idEmpresa, fechaCreacion, fechaModificacion,estado,idUsuario, tipoDocumento) VALUES (?,?,?,?,?,?,?,?)";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,t.getDocumento());
			//ps.setBinaryStream(2, t.getFile().getBinaryStream());
			ps.setString(2, t.getExtension());
			ps.setInt(3, t.getIdEmpresa());
			ps.setString(4,cadenaFecha);
			ps.setString(5, cadenaFecha);
			ps.setString(6, t.getEstado());
			ps.setInt(7, t.getIdUsuario());
			ps.setInt(8, t.getTipoDocumento());
			
			ps.execute();
			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}

	

	public static Template getTemplateById(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		Template lista = new Template();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM sw_template "+
					"left join (select idSociedad, denominacionSociedad as nombreEmpresa From sociedad )as k on sw_template.idEmpresa=k.idSociedad "+
					"left join (select llave, descripcion from parametros where codigo='TIPO_DOCUMENTO' and activo=1) as l on sw_template.tipoDocumento=l.llave "+
					"left join (select codigo, usuario from usuario) as m on sw_template.idUsuario=m.codigo "+
					"where idTemplate="+id;
			System.out.println(sql);
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){					
				Template t= new Template();
				t.setIdTemplate(rs.getInt("idTemplate"));
				t.setDocumento(rs.getString("documento"));
				//t.setFile(rs.getBlob("file"));
				//t.setFile2(rs.getBlob("file").getBinaryStream());
				t.setIdEmpresa(rs.getInt("idSociedad"));
				t.setNombreEmpresa("nombreEmpresa");
				t.setTipoDocumento(rs.getInt("llave"));
				t.setFechaCreacion(rs.getString("fechaCreacion"));
				t.setFechaModificacion(rs.getString("fechaModificacion"));
				t.setEstado(rs.getString("estado"));
				t.setIdUsuario(rs.getInt("idUsuario"));
				t.setIdHuerto(rs.getString("idHuerto"));
				Gson g=new Gson();
				System.out.println(g.toJson(t));
				lista=t;
				}
		}catch (Exception e){
			
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
		
	}

	public static ArrayList<Template> getTemplates() throws SQLException {
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Template> lista = new ArrayList<Template>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "SELECT * FROM sw_template "+
					"left join (select idSociedad, denominacionSociedad as nombreEmpresa From sociedad )as k on sw_template.idEmpresa=k.idSociedad "+
					"left join (select llave, descripcion from parametros where codigo='TIPO_DOCUMENTO' and activo=1) as l on sw_template.tipoDocumento=l.llave "+
					"left join (select codigo, usuario from usuario) as m on sw_template.idUsuario=m.codigo";
				
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
				while(rs.next()){					
				Template t= new Template();
				t.setIdTemplate(rs.getInt("idTemplate"));
				t.setDocumento(rs.getString("documento"));
				//t.setFile2(rs.getBlob("file").getBinaryStream());
				//t.setFile(rs.getBlob("file"));
				t.setExtension(rs.getString("extension"));
				t.setIdEmpresa(rs.getInt("idEmpresa"));
				t.setNombreEmpresa(rs.getString("nombreEmpresa"));
				t.setFechaCreacion(rs.getString("fechaCreacion"));
				t.setFechaModificacion(rs.getString("fechaModificacion"));
				t.setEstado(rs.getString("estado"));
				t.setIdUsuario(rs.getInt("idUsuario"));
				t.setNombreUsuario(rs.getString("usuario"));
				t.setTipoDocumento(rs.getInt("tipoDocumento"));
				t.setNombreTipoDocumento(rs.getString("descripcion"));
				
				
				lista.add(t);
				}
		}catch (Exception e){
			
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
	}
	public static Template getFileById(int idTemplate) throws Exception {

		Statement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
	
		try {

			ps = db.conn.createStatement();
			sql = "SELECT file FROM sw_template WHERE idTemplate = '"+idTemplate+"' ";
			ResultSet rs = ps.executeQuery(sql);
			Template t= new Template();
			while (rs.next()) {
				t.setFile(rs.getBlob(1));
				// t.setFile2(rs.getBlob(1).getBinaryStream());
				
			}
			rs.close();
			ps.close();
			db.conn.close();
			return t;

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("sql: " + sql);
			throw new Exception("getFileById: " + e.getMessage());
			
		} finally {
			db.close();
		}

		
	}
	public static boolean deleteTemplate(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_template WHERE idTemplate ="+id;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	public static boolean updateTemplate(Template t) throws SQLException {
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE sw_template SET documento = ?,idEmpresa=?, fechaModificacion = ?, estado = ?, idUsuario = ?  WHERE idTemplate = ?";
		
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,t.getDocumento());
		
			Date d= new Date();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd h:mm:ss:SSSS");
			String cadenaFecha= formato.format(d);
			ps.setString(2,cadenaFecha);
			ps.setString(3,"Y");
			ps.setInt(4,t.getIdUsuario());
			ps.setInt(5,t.getIdTemplate());
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}

}
