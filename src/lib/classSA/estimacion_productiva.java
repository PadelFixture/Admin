package lib.classSA;

import java.util.ArrayList;

public class estimacion_productiva extends parametros_estimacion{
	public int codigo_e;
	public int cuartel;
	public String campo;
	public String especie;
	public float kep;
	public float ke;
	public float kxp;
	public float kx;
	public float ct;
	public float cth;
	public float valor;
	public ArrayList<estimacion_prd_data> estimacion_prd_data;
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public int getCodigo_e() {
		return codigo_e;
	}
	public void setCodigo_e(int codigo_e) {
		this.codigo_e = codigo_e;
	}
	public int getCuartel() {
		return cuartel;
	}
	public void setCuartel(int cuartel) {
		this.cuartel = cuartel;
	}
	public float getKep() {
		return kep;
	}
	public void setKep(float kep) {
		this.kep = kep;
	}
	public float getKe() {
		return ke;
	}
	public void setKe(float ke) {
		this.ke = ke;
	}
	public float getKxp() {
		return kxp;
	}
	public void setKxp(float kxp) {
		this.kxp = kxp;
	}
	public float getKx() {
		return kx;
	}
	public void setKx(float kx) {
		this.kx = kx;
	}
	public float getCt() {
		return ct;
	}
	public void setCt(float ct) {
		this.ct = ct;
	}
	public float getCth() {
		return cth;
	}
	public void setCth(float cth) {
		this.cth = cth;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public ArrayList<estimacion_prd_data> getEstimacion_prd_data() {
		return estimacion_prd_data;
	}
	public void setEstimacion_prd_data(ArrayList<estimacion_prd_data> estimacion_prd_data) {
		this.estimacion_prd_data = estimacion_prd_data;
	}
}
