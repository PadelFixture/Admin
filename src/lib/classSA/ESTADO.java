package lib.classSA;

public class ESTADO {
	public int codigo;
	public String campo;
	public int verde_hasta;
	public int amarillo_hasta;


	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getVerde_hasta() {
		return verde_hasta;
	}
	public void setVerde_hasta(int verde_hasta) {
		this.verde_hasta = verde_hasta;
	}
	public int getAmarillo_hasta() {
		return amarillo_hasta;
	}
	public void setAmarillo_hasta(int amarillo_hasta) {
		this.amarillo_hasta = amarillo_hasta;
	}
}
