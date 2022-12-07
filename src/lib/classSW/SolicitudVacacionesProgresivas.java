package lib.classSW;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudVacacionesProgresivas {

	   private String idSolicitud;
	   private String fechaSolicitud;
	   private String fechaInicio;
	   private String mesProceso;
	   private String mesesCotizados;
	   private String mesesReconocidos;
	   private String estadoSolicitud;
	   private String descripcion;
	   private String codTrabajador;
	   private String idContrato;
	   private Blob comprobante;
	   private String aprobadaPor;
	   private String diasProgresivos;
	   private String estado;
	   private String modificadoPor;
	   private String fechaModificacion;
	   private String sociedad;
	   private String formula;
	   private String mesesTranscurridos;
	   
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getMesProceso() {
		return mesProceso;
	}
	public void setMesProceso(String mesProceso) {
		this.mesProceso = mesProceso;
	}
	public String getMesesCotizados() {
		return mesesCotizados;
	}
	public void setMesesCotizados(String mesesCotizados) {
		this.mesesCotizados = mesesCotizados;
	}
	public String getMesesReconocidos() {
		return mesesReconocidos;
	}
	public void setMesesReconocidos(String mesesReconocidos) {
		this.mesesReconocidos = mesesReconocidos;
	}
	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}
	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodTrabajador() {
		return codTrabajador;
	}
	public void setCodTrabajador(String codTrabajador) {
		this.codTrabajador = codTrabajador;
	}
	public String getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}
	public String getAprobadaPor() {
		return aprobadaPor;
	}
	public void setAprobadaPor(String aprobadaPor) {
		this.aprobadaPor = aprobadaPor;
	}
	public String getDiasProgresivos() {
		return diasProgresivos;
	}
	public void setDiasProgresivos(String diasProgresivos) {
		this.diasProgresivos = diasProgresivos;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getModificadoPor() {
		return modificadoPor;
	}
	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public Blob getComprobante() {
		return comprobante;
	}
	public void setComprobante(Blob comprobante) {
		this.comprobante = comprobante;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getMesesTranscurridos() {
		return mesesTranscurridos;
	}
	public void setMesesTranscurridos(String mesesTranscurridos) {
		this.mesesTranscurridos = mesesTranscurridos;
	}
	
	   
	   
	   
	   
	   
	   
		
}
