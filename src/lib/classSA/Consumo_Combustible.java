package lib.classSA;

public class Consumo_Combustible {

	public int codigo;
	public String campo;
	public String Tipo;
	public int vehiculo;
	public String fecha;
	public float litro;
	public String operador;
	public float horometro;
	public String implemento;
	public String material_document;
	public String material_eliminado;
	
	
	public String getMaterial_eliminado() {
		return material_eliminado;
	}
	public void setMaterial_eliminado(String material_eliminado) {
		this.material_eliminado = material_eliminado;
	}
	public int getCodigo() {
		return codigo;
	}
	public String getMaterial_document() {
		return material_document;
	}
	public void setMaterial_document(String material_document) {
		this.material_document = material_document;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public int getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(int vehiculo) {
		this.vehiculo = vehiculo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public float getLitro() {
		return litro;
	}
	public void setLitro(float litro) {
		this.litro = litro;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public float getHorometro() {
		return horometro;
	}
	public void setHorometro(float horometro) {
		this.horometro = horometro;
	}
	public String getImplemento() {
		return implemento;
	}
	public void setImplemento(String implemento) {
		this.implemento = implemento;
	}
}
