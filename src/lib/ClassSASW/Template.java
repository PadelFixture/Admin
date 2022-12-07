package lib.ClassSASW;

import java.io.File;
import java.io.InputStream;
import java.sql.Blob;

public class Template {
	private int idTemplate;
	private String documento; //nombre del documento
	private Blob file;
	private InputStream file2;
	private File file3;
	private String extension;
	private int idEmpresa;
	private String nombreEmpresa;
	private String fechaCreacion;
	private String fechaModificacion;
	private String estado; 
	private int idUsuario;
	private String nombreUsuario;
	private int tipoDocumento;
	private String nombreTipoDocumento;
    private String idHuerto;
	
	
	public String getIdHuerto() {
		return idHuerto;
	}
	public void setIdHuerto(String idHuerto) {
		this.idHuerto = idHuerto;
	}
	public int getIdTemplate() {
		return idTemplate;
	}
	public void setIdTemplate(int idTemplate) {
		this.idTemplate = idTemplate;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public Blob getFile() {
		return file;
	}
	public void setFile(Blob file) {
		this.file = file;
	}
	public InputStream getFile2() {
		return file2;
	}
	public void setFile2(InputStream file2) {
		this.file2 = file2;
	}
	public File getFile3() {
		return file3;
	}
	public void setFile3(File file3) {
		this.file3 = file3;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
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
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
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
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public int getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}
	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}
	
	
	
}
