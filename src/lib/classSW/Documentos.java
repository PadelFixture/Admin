package lib.classSW;

import java.sql.Blob;

public class Documentos {

	private int idTrabajadorDocumentos;
	private int codTrabajador;
	private int tipoDocumento;
	private Blob documento;
	private String nombreDocumento;
	
	public int getIdTrabajadorDocumentos() {
		return idTrabajadorDocumentos;
	}
	public void setIdTrabajadorDocumentos(int idTrabajadorDocumentos) {
		this.idTrabajadorDocumentos = idTrabajadorDocumentos;
	}
	public int getCodTrabajador() {
		return codTrabajador;
	}
	public void setCodTrabajador(int codTrabajador) {
		this.codTrabajador = codTrabajador;
	}
	public int getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Blob getDocumento() {
		return documento;
	}
	public void setDocumento(Blob documento) {
		this.documento = documento;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	
	@Override
	public String toString() {
		return "Documentos [idTrabajadorDocumentos=" + idTrabajadorDocumentos + ", codTrabajador=" + codTrabajador
				+ ", tipoDocumento=" + tipoDocumento + ", documento=" + documento + ", nombreDocumento="
				+ nombreDocumento + "]";
	}
	
	
	
	
}
