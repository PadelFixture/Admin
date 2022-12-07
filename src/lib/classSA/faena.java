package lib.classSA;

public class faena {
	
	public int codigo;
	public String faena;
	public String descripcion;
	public String zona;
	public String clasificacion;
	public String cuenta;
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public int estado;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getFaena() {
		return faena;
	}
	public void setFaena(String faena) {
		this.faena = faena;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "{codigo:" + codigo + ", faena:'" + faena + "', descripcion:'" + descripcion + "', zona:'" + zona
				+ "', clasificacion:'" + clasificacion + "', cuenta:'" + cuenta + "', estado:" + estado + "}";
	}
	
}