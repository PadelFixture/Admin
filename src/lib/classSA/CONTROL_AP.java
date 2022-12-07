package lib.classSA;

public class CONTROL_AP {
	
	
	public int codigo;
	public String control_aplicacion;
	public String descripcion;
	public int estado;
	public int especie;
	public String nVespecie;
	
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getControl_aplicacion() {
		return control_aplicacion;
	}
	public void setControl_aplicacion(String control_aplicacion) {
		this.control_aplicacion = control_aplicacion;
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
	public int getEspecie() {
		return especie;
	}
	public void setEspecie(int especie) {
		this.especie = especie;
	}
	public String getnVespecie() {
		return nVespecie;
	}
	public void setnVespecie(String nVespecie) {
		this.nVespecie = nVespecie;
	}
}
