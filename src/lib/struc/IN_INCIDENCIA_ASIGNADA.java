package lib.struc;

public class IN_INCIDENCIA_ASIGNADA {
	public int id_trabajador;
	public int id_incidencia;
	public String fecha;
	public String descripcion;
	public int id_material;
	public float cantidad;
	public String unidad;
	public String upd;
	
	public String getUpd() {
		return upd;
	}
	public void setUpd(String upd) {
		this.upd = upd;
	}
	public int getId_trabajador() {
		return id_trabajador;
	}
	public void setId_trabajador(int id_trabajador) {
		this.id_trabajador = id_trabajador;
	}
	public int getId_incidencia() {
		return id_incidencia;
	}
	public void setId_incidencia(int id_incidencia) {
		this.id_incidencia = id_incidencia;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getId_material() {
		return id_material;
	}
	public void setId_material(int id_material) {
		this.id_material = id_material;
	}
	public float getCantidad() {
		return cantidad;
	}
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
}
