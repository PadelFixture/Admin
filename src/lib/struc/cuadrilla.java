package lib.struc;

public class cuadrilla {
	public int id_cuadrilla;
	public int id_encargado;
	public String descripcion;
	public String actividad;
	public String fecha_actividad;
	public int getId_cuadrilla() {
		return id_cuadrilla;
	}
	public void setId_cuadrilla(int id_cuadrilla) {
		this.id_cuadrilla = id_cuadrilla;
	}
	public int getId_encargado() {
		return id_encargado;
	}
	public void setId_encargado(int id_encargado) {
		this.id_encargado = id_encargado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getFecha_actividad() {
		return fecha_actividad;
	}
	public void setFecha_actividad(String fecha_actividad) {
		this.fecha_actividad = fecha_actividad;
	}
	@Override
	public String toString() {
		return "cuadrilla [id_cuadrilla=" + id_cuadrilla + ", id_encargado=" + id_encargado + ", descripcion="
				+ descripcion + ", actividad=" + actividad + ", fecha_actividad=" + fecha_actividad + "]";
	}
	
}
