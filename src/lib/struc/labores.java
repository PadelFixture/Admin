package lib.struc;

public class labores {
	public int id_labores;
	public int id_campo;
	public int id_trabajador;
	public String actividad;
	public int cantidad;
	public String fecha;
	public String unidad;
	public String observaciones;
	public String trabajador;
	public String getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public int getId_labores() {
		return id_labores;
	}
	public void setId_labores(int id_labores) {
		this.id_labores = id_labores;
	}
	public int getId_campo() {
		return id_campo;
	}
	public void setId_campo(int id_campo) {
		this.id_campo = id_campo;
	}
	public int getId_trabajador() {
		return id_trabajador;
	}
	public void setId_trabajador(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
