package lib.db.sw;


import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.Iterator;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import java.nio.*;
import java.nio.charset.*;

import lib.ClassSASW.Template;
import lib.classSW.Document;
import lib.db.ConnectionDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
public class DocumentsDB {
	//get General
	 
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
	/*--------------Declaraciones para DB------------------------*/
	//Insert Documento
	
	
	public static boolean insertDocument(Document document,int iduser) throws Exception {
		
      
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
	
		Date d= new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd h:mm:ss:SSSS");
		String cadenaFecha= formato.format(d);
		try{

			sql = "INSERT INTO sw_template (documento,fechaCreacion,fechaModificacion,estado,idUsuario,tipoDocumento,idEmpresa,file,idHuerto) VALUES (?,?,?,?,?,?,?,?,?)";
			
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,document.getDocumento());
			int iu=iduser;
			ps.setString(2,cadenaFecha);
			ps.setString(3, cadenaFecha);
			ps.setString(4, document.getEstado());
			ps.setInt(5, iu);
			ps.setInt(6, document.getTipoDocumento());
			ps.setInt(7, document.getIdEmpresa());
			ps.setBlob(8, document.getFile());
			ps.setString(9, document.getIdHuerto());
			
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}

		
	}
	/*
	 * idTemplate
	 * documento
	 * file
	 * fechaCreacion
	 * fechaModificacion
	 * estado
	 * idUsuario 
	 * */
	//Actualizaci�n de todo el template ready by object
	public static boolean updateAllDocument(Document doc) throws SQLException
	{
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE sw_template SET documento = ?, file = ?, fechaCreacion = ?, fechaModificacion = ?, estado = ?, idUsuario = ?, idHuerto = ?, idEmpresa = ?  WHERE idTemplate = ?";
		
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,doc.getDocumento());
			ps.setBlob(2,doc.getFile());
			ps.setString(3, doc.getFechaCreacion());
			ps.setString(4,doc.getFechaModificacion());
			ps.setString(5,doc.getEstado());
			ps.setInt(6,doc.getIdUsuario());
			ps.setString(7,doc.getIdHuerto());
			ps.setInt(8,doc.getIdEmpresa());
			ps.setInt(9,doc.getIdTemplate());
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
//Actualizaci�n de todo el template by Campos
	public static boolean updateAllDocument(int idtemplate, String nombre, Blob file, String fechacreacion, String fechamodificacion, String estado, int idusuario) throws SQLException{
		PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE sw_template SET documento = ?, file = ?, fechaCreacion = ?, fechaModificacion = ?, estado = ?, idUsuario = ?  WHERE idTemplate = ?";
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(1,nombre);
			ps.setBlob(2,file);
			ps.setString(3,fechacreacion);
			ps.setString(4,fechamodificacion);
			ps.setString(5,estado);
			ps.setInt(6,idusuario);
			ps.setInt(7,idtemplate);
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	
	//Actualizaci�n partes del Documento
		
		//actualizaci�n del nombre ready
	public static boolean updateNombreDocument(int idtemplate, String nombre, int idusuario, int idEmpresa, String idHuerto) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		Date d= new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd h:mm:ss:SSSS");
		String cadenaFecha= formato.format(d);
		
		
		
		
		try{
			System.out.println("UPDATE sw_template SET documento = "+nombre+", fechaModificacion = "+cadenaFecha+",idUsuario = "+idusuario+", idEmpresa = "+idEmpresa+", idHuerto = "+idHuerto+"  WHERE idTemplate = "+idtemplate+" ");
			sql ="UPDATE sw_template SET documento = ?, fechaModificacion = ?,idUsuario = ?, idEmpresa = ?, idHuerto = ?  WHERE idTemplate = ?";
			ps = db.conn.prepareStatement(sql);			
			ps.setString(1,nombre);			
			ps.setString(2,cadenaFecha);
			ps.setInt(3,idusuario);
			ps.setInt(4,idEmpresa);
			ps.setString(5,idHuerto);
			ps.setInt(6,idtemplate);		
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
		//actualizaci�n del archivo ready
	public static boolean updateFileDocument(int idtemplate, Blob file,String fechamodificacion, int idusuario) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE sw_template SET file = ?, fechaModificacion = ?, idUsuario = ?  WHERE idTemplate = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setBlob(1,file);
			ps.setString(2,fechamodificacion);
			ps.setInt(3,idusuario);
			ps.setInt(4,idtemplate);		
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
		//actualizacion de fechaCreacion ready
	public static boolean updateCreationDateDocument(int idtemplate,String fechacreacion) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE sw_template SET fechaCreacion = ? WHERE idTemplate = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,fechacreacion);
			ps.setInt(2,idtemplate);		
			ps.execute();

			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
		//actualizacion de fechaModificacion ready
	public static boolean updateUpdateDateDocument(int idtemplate,String fechamodificacion, int idusuario) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE sw_template SET fechaModificacion = ?, idUsuario = ?  WHERE idTemplate = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,fechamodificacion);
			ps.setInt(2,idusuario);
			ps.setInt(3,idtemplate);		
			ps.execute();

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}

		//actualizacion de estado ready
	public static boolean updateStatusDocument(int idtemplate,String fechamodificacion, String estado, int idusuario) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE sw_template SET fechaModificacion = ?, estado = ? WHERE idTemplate = ?";
			ps = db.conn.prepareStatement(sql);
			ps.setString(1,fechamodificacion);
			ps.setString(2,estado);
			ps.setInt(3,idusuario);
			ps.setInt(4,idtemplate);			
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
		//actualizacion de Documento  ready
	public static boolean updateIdUserDocument(int idtemplate,String fechamodificacion, int idusuario) throws Exception {
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="UPDATE sw_template SET fechaModificacion = ?, idUsuario = ?  WHERE idTemplate = ?";
			ps = db.conn.prepareStatement(sql);
			
			ps.setString(1,fechamodificacion);
			ps.setInt(2,idusuario);
			ps.setInt(3,idtemplate);		
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}
	
	//Borrar Documento por Id ready
	public static boolean deleteDocumentById(int idtemplate) throws SQLException{
		PreparedStatement ps = null;
		String sql = "";
		ConnectionDB db = new ConnectionDB();
		try{
			sql ="DELETE FROM sw_template WHERE idTemplate ="+idtemplate;
			ps = db.conn.prepareStatement(sql);					
			ps.execute();
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
	}

	//Obtener Documento por Id ready
	public static Document getDocumentById(int idtemplate)  throws Exception{

		PreparedStatement ps = null;
		String sql="";
		ConnectionDB db = new ConnectionDB();
		Document dc = new Document();
		
		try{
			sql = "SELECT * FROM sw_template WHERE idTemplate ="+idtemplate;
			
			ps = db.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);

			while(rs.next()){

				dc.setIdTemplate(rs.getInt("idTemplate"));
				dc.setDocumento(rs.getString("documento"));
				
				//dc.setFile(rs.getBinaryStream("file"));
				
				dc.setFechaCreacion(rs.getString("fechaCreacion"));
				dc.setFechaModificacion(rs.getString("fechaModificacion"));
				dc.setEstado(rs.getString("estado"));
				dc.setIdUsuario(rs.getInt("idUsuario"));
				
			}			
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
			
		}finally {
			ps.close();
			db.close();
		}		
		return dc;
	}
	public static Document getBlankDocument(int Uid)  throws Exception{

		
		Document dc = new Document();

		return dc.createBlankDocument(Uid);
	}
	//Obtener Todos los Documentos ready
	public static ArrayList<Document> getDocumentos(ArrayList<filterSql> filter) throws Exception {
		
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Document> lista = new ArrayList<Document>();
		ConnectionDB db = new ConnectionDB();
			
		try{
			sql = "select * from sw_template ";
			
			if (filter.size() > 0) {

				String andSql = "";
				andSql += " WHERE ";

				Iterator<filterSql> f = filter.iterator();

				while (f.hasNext()) {

					filterSql row = f.next();

					if (!row.getValue().equals("")) {

						if (row.getCampo().endsWith("_to")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 3) + " <='"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}

						else if (row.getCampo().endsWith("_from")) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							SimpleDateFormat sqlDate = new SimpleDateFormat("yyyyMMdd");
							sql += andSql + row.getCampo().substring(0, row.getCampo().length() - 5) + " >= '"
									+ sqlDate.format(formatter.parse(row.getValue())) + "'";
						}
						else if (GeneralUtility.isNumeric(row.getValue())){
							sql += andSql + row.getCampo() + " = '" + row.getValue() + "'";
						}
						else if (GeneralUtility.isArray(row.getValue())){
							sql += andSql + row.getCampo() + " in ( " + row.getValue() + " )";
						}
						else
							sql += andSql + row.getCampo() + " like '%" + row.getValue() + "%'";
						andSql = " and ";

					} // Fin While

				}

			} // Fin if (filter size)
			
			
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				Document dc= new Document();				
				dc.setIdTemplate(rs.getInt("idTemplate"));				
				dc.setDocumento(rs.getString("Documento"));
				dc.setFechaCreacion(rs.getString("fechaCreacion"));
				dc.setFechaModificacion(rs.getString("fechaModificacion"));
				dc.setEstado(rs.getString("estado"));
				dc.setIdUsuario(rs.getInt("idUsuario"));
				dc.setTipoDocumento(rs.getInt("tipoDocumento"));
							
				lista.add(dc);
			}		

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
		
	}
public static ArrayList<Document> getDocumentoById(int id) throws Exception {
		
		PreparedStatement ps = null;
		String sql="";
		ArrayList<Document> lista = new ArrayList<Document>();
		ConnectionDB db = new ConnectionDB();
		try{
			sql = "select * from sw_template where id=?";
			ps = db.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery(sql);
									
				Document dc= new Document();
				dc.setIdTemplate(rs.getInt("idTemplate"));
				dc.setDocumento(rs.getString("documento"));
				dc.setFile(rs.getBlob("file"));
				dc.setFechaCreacion(rs.getString("fechaCreacion"));
				dc.setFechaModificacion(rs.getString("fechaModificacion"));
				dc.setEstado(rs.getString("estado"));
				dc.setIdUsuario(rs.getInt("idUsuario"));
				lista.add(dc);
		}catch (Exception e){
			
		}finally {
			ps.close();
			db.close();
		}		
		return lista;
		
	}

public static Document getBlankDocument() throws ParseException {
	Document dc = new Document();
	return dc;
}


/**
 * @author juan.vegas
 * @return Blob file
 * @throws Exception
 */
public static Blob getFileById(String idTemplate) throws Exception {

	Statement ps = null;
	String sql = "";
	ConnectionDB db = new ConnectionDB();

	try {

		ps = db.conn.createStatement();
		sql = "SELECT file FROM sw_template WHERE idTemplate = '"+idTemplate+"' ";
		System.out.println(sql);
		ResultSet rs = ps.executeQuery(sql);
		System.out.println("Ejecute la sql");
		while (rs.next()) {
			Blob file = rs.getBlob(1);
			System.out.println("Retornando blob");
			return file;
		}
		rs.close();
		ps.close();
		db.conn.close();

	} catch (SQLException e) {
		System.out.println("Error: " + e.getMessage());
		System.out.println("sql: " + sql);
		throw new Exception("getFileById: " + e.getMessage());
	} finally {
		db.close();
	}

	return null;
}
public static ArrayList<Document> getDocuments() throws SQLException {
	PreparedStatement ps = null;
	String sql="";
	ArrayList<Document> lista = new ArrayList<Document>();
	ConnectionDB db = new ConnectionDB();
	try{
		sql = 	"SELECT * FROM sw_template "+
				"left join (select idSociedad, denominacionSociedad as nombreEmpresa From sociedad )as k on sw_template.idEmpresa=k.idSociedad "+
				"left join (select llave, descripcion from parametros where codigo='TIPO_DOCUMENTO' and activo=1) as l on sw_template.tipoDocumento=l.llave "+
				"left join (select codigo, usuario from usuario) as m on sw_template.idUsuario=m.codigo";
		ps = db.conn.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery(sql);
		while(rs.next()){						
			Document dc= new Document();
			dc.setIdTemplate(rs.getInt("idTemplate"));
			dc.setDocumento(rs.getString("documento"));
			dc.setFile(rs.getBlob("file"));
			dc.setIdEmpresa(rs.getInt("idEmpresa"));
			dc.setNombreEmpresa(rs.getString("nombreEmpresa"));
			dc.setFechaCreacion(rs.getString("fechaCreacion"));
			dc.setFechaModificacion(rs.getString("fechaModificacion"));
			dc.setEstado(rs.getString("estado"));
			dc.setTipoDocumento(rs.getInt("tipoDocumento"));
			dc.setNombreTipoDocumento(rs.getString("descripcion"));
			dc.setIdUsuario(rs.getInt("idUsuario"));
			dc.setNombreUsuario(rs.getString("usuario"));
			Gson g= new Gson();
			System.out.println(g.toJson(dc));
			lista.add(dc);
		}
			
			
	}catch (Exception e){
		
	}finally {
		ps.close();
		db.close();
	}		
	return lista;
}
public static boolean updateTemplate(Template t) throws SQLException {
	PreparedStatement ps = null;
	String sql =""; 
	ConnectionDB db = new ConnectionDB();
	try{
		//generar una estructura que me permita modificar cualquier campo siempre y cuando 
		//tenga datos, si no los tiene hay que dejarlo exactamente igual


		sql ="UPDATE sw_template SET documento = ?, fechaModificacion = ?, estado = ?, idUsuario = ?  WHERE idTemplate = ?";
	
		ps = db.conn.prepareStatement(sql);
		ps.setString(1,t.getDocumento());
	
		Date d= new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd h:mm:ss:SSSS");
		String cadenaFecha= formato.format(d);
		ps.setString(4,cadenaFecha);
		ps.setString(5,"Y");
		ps.setInt(6,t.getIdUsuario());
		ps.setInt(7,t.getIdTemplate());
		ps.execute();

		return true;

	}catch(Exception ex){
		return false;
	}finally{
		db.conn.close();
	}
}
 public static boolean uploadFile(MultipartFile file, int id) throws IOException, SQLException {
	 PreparedStatement ps = null;
		String sql =""; 
		ConnectionDB db = new ConnectionDB();
		try{
			//generar una estructura que me permita modificar cualquier campo siempre y cuando 
			//tenga datos, si no los tiene hay que dejarlo exactamente igual


			sql ="UPDATE sw_template SET  file = ?  WHERE idTemplate = "+id;
		
			ps = db.conn.prepareStatement(sql);
			ps.setBytes(1, file.getBytes());

			return true;

		}catch(Exception ex){
			return false;
		}finally{
			db.conn.close();
		}
}

}
