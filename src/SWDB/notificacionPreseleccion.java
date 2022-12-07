package SWDB;

public class notificacionPreseleccion {
	public int id_peticion;
	public String faena;
	public int cantidad;
	public String fecha_inicio;
	public String observacion;
	public String usuario;
	public int estado_peticion;
	public int getId_peticion() {
		return id_peticion;
	}
	
	public void setId_peticion(int id_peticion) {
		this.id_peticion = id_peticion;
	}
	public String getFaena() {
		return faena;
	}
	public void setFaena(String faena) {
		this.faena = faena;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getEstado_peticion() {
		return estado_peticion;
	}
	public void setEstado_peticion(int estado_peticion) {
		this.estado_peticion = estado_peticion;
	}
	
	

}
