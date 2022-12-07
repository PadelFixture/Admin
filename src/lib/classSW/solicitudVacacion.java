package lib.classSW;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class solicitudVacacion {
	
	private String idTrabajador;
	private String idSolicitud;
	private String fechaSolicitud;
	private String fechaInicio;
	private String fechaTermino;
	private String periodoSolicitud;
	private String cantidadDiasSolicitud;
	private String estadoSolicitud;
	private String descripcion;
	private String codTrabajador;
	private String idContrato;
	private String aprobadaPor;
	private String fechaAprobacion;
	private String estado;
	private String modificadoPor;
	private String fechaModificacion;
	private String sociedad;
	private Blob comprobante;
	
	
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String fechaInicioSolicitud;
	private String fechaFinSolicitud;
	private String descripcionSolicitud;
	private String comprobanteSolicitud;
	
	
	
	
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
	public String getFechaTermino() {
		return fechaTermino;
	}
	public void setFechaTermino(String fechaTermino) {
		this.fechaTermino = fechaTermino;
	}
	public String getPeriodoSolicitud() {
		return periodoSolicitud;
	}
	public void setPeriodoSolicitud(String periodoSolicitud) {
		this.periodoSolicitud = periodoSolicitud;
	}
	public String getCantidadDiasSolicitud() {
		return cantidadDiasSolicitud;
	}
	public void setCantidadDiasSolicitud(String cantidadDiasSolicitud) {
		this.cantidadDiasSolicitud = cantidadDiasSolicitud;
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
	public String getFechaAprobacion() {
		return fechaAprobacion;
	}
	public void setFechaAprobacion(String fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
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
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFechaInicioSolicitud() {
		return fechaInicioSolicitud;
	}
	public void setFechaInicioSolicitud(String fechaInicioSolicitud) {
		this.fechaInicioSolicitud = fechaInicioSolicitud;
	}
	public String getFechaFinSolicitud() {
		return fechaFinSolicitud;
	}
	public void setFechaFinSolicitud(String fechaFinSolicitud) {
		this.fechaFinSolicitud = fechaFinSolicitud;
	}
	public String getDescripcionSolicitud() {
		return descripcionSolicitud;
	}
	public void setDescripcionSolicitud(String descripcionSolicitud) {
		this.descripcionSolicitud = descripcionSolicitud;
	}
	public String getComprobanteSolicitud() {
		return comprobanteSolicitud;
	}
	public void setComprobanteSolicitud(String comprobanteSolicitud) {
		this.comprobanteSolicitud = comprobanteSolicitud;
	}
	public String getIdTrabajador() {
		return idTrabajador;
	}
	public void setIdTrabajador(String idTrabajador) {
		this.idTrabajador = idTrabajador;
	}
	
	

	
}
