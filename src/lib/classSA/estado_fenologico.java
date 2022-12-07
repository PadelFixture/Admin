package lib.classSA;

public class estado_fenologico {
	
	public int codigo;
	public String estado_fenologicos;
	public String descripcion;
	public int estado;
	public int especie;
	public String nvEspecie;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getEstado_fenologicos() {
		return estado_fenologicos;
	}
	public void setEstado_fenologicos(String estado_fenologicos) {
		this.estado_fenologicos = estado_fenologicos;
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
	public String getNvEspecie() {
		return nvEspecie;
	}
	public void setNvEspecie(String nvEspecie) {
		this.nvEspecie = nvEspecie;
	}
}
