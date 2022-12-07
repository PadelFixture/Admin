package lib.classSW;

import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Document {
	private int idTemplate;
	private String documento;
	private InputStream f;
	private Blob file;
	private File file2;
	private String idHuerto;
	
	
	public String getIdHuerto() {
		return idHuerto;
	}
	public void setIdHuerto(String idHuerto) {
		this.idHuerto = idHuerto;
	}
	public Blob getFile() {
		return file;
	}
	public InputStream getF() {
		return f;
	}
	public void setF(InputStream f) {
		this.f = f;
	}
	public int getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public void setFile(Blob file) {
		this.file = file;
	}
	private int idEmpresa;
	private String nombreEmpresa;
	private String fechaCreacion;
	private String fechaModificacion;
	private String estado; 
	private int idUsuario;
	private int tipoDocumento;
	private String nombreTipoDocumento;
	private String nombreUsuario;
	
	
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}
	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public Document(int uid) throws ParseException{
		this.idTemplate=0;
		this.documento="";
		this.f=null;
		String date="yyyy-MM-dd hh:mm:ss.S";
		SimpleDateFormat sdf = new SimpleDateFormat(date);
		
		String dateAsString = sdf.format(date); //"08.01.2013"
		String dateFromString =  sdf.parse(dateAsString).toString();
		this.fechaCreacion=dateFromString;
		this.fechaModificacion=dateFromString;
		this.estado="Y";
		this.idUsuario=uid;
		this.file = null;
		this.idHuerto="";
	}
	public Document() throws ParseException{
		
		this.idTemplate=0;
		this.documento="";
		this.f=null;
		String date="yyyy-MM-dd hh:mm:ss.S";
		
		
		
		
		
		this.fechaCreacion=date;
		this.fechaModificacion=date;
		this.estado="Y";	
		this.file = null;
		this.idHuerto="";
		
	}
	
	public Document createBlankDocument(int uid) throws ParseException{
		
		this.idTemplate=0;
		this.documento="";
		this.f=null;
		String date="yyyy-MM-dd hh:mm:ss.S";
		SimpleDateFormat sdf = new SimpleDateFormat(date);
		String dateAsString = sdf.format(date); //"08.01.2013"
		
		this.fechaCreacion=dateAsString;
		this.fechaModificacion=dateAsString;
		this.estado="Y";
		this.idUsuario=uid;
		this.file = null;
		this.idHuerto="";
		
		return this;	
	}
	
	
	public int getIdTemplate() {
		return idTemplate;
	}
	public void setIdTemplate(int id) {
		this.idTemplate = id;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String string) {
		
		this.fechaCreacion = string;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String string) {
		this.fechaModificacion = string;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public File getFile2() {
		return file2;
	}
	public void setFile2(File file2) {
		this.file2 = file2;
	}
}
