package lib.struc;

public class incidencia {
	public int id_incidencia;
	public int id_campo;
	public String coordenadas;
	public String descripcion;
	public String sector_afectado;
	public String urgencia;
	public String tipo_incidencia;
	public String observaciones;
	public String estado;
	public String fecha;
	public int getId_incidencia() {
		return id_incidencia;
	}
	public void setId_incidencia(int id_incidencia) {
		this.id_incidencia = id_incidencia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getId_campo() {
		return id_campo;
	}
	public void setId_campo(int id_campo) {
		this.id_campo = id_campo;
	}
	public String getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}
	public String getSector_afectado() {
		return sector_afectado;
	}
	public void setSector_afectado(String sector_afectado) {
		this.sector_afectado = sector_afectado;
	}
	public String getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	public String getTipo_incidencia() {
		return tipo_incidencia;
	}
	public void setTipo_incidencia(String tipo_incidencia) {
		this.tipo_incidencia = tipo_incidencia;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
