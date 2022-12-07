package lib.classSA;

public class programa_riego {
	public int id;
	public int idBloque;
	public String nombre;
	public String f_ultumoRiego;
	public float e_acumulada;
	public String f_riego;
	public int estado;
	public String campo;
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdBloque() {
		return idBloque;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setIdBloque(int idBloque) {
		this.idBloque = idBloque;
	}
	public String getF_ultumoRiego() {
		return f_ultumoRiego;
	}
	public void setF_ultumoRiego(String f_ultumoRiego) {
		this.f_ultumoRiego = f_ultumoRiego;
	}
	public float getE_acumulada() {
		return e_acumulada;
	}
	public void setE_acumulada(float e_acumulada) {
		this.e_acumulada = e_acumulada;
	}
	public String getF_riego() {
		return f_riego;
	}
	public void setF_riego(String f_riego) {
		this.f_riego = f_riego;
	}
}
