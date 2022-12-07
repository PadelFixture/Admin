package lib.classSA;

public class LABOR {

	public int codigo;
	public int faena;
	public String labor;
	public int maquinaria;
	public int rebaja;
	public int tipo_labor;
	public String nfaena;
	public int estado;
	public String zona;
	private String clasificacion;
	private String nmaquinaria;
	private String nrebaja;
	
	
	
	
	
	
	public String getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	public String getNmaquinaria() {
		return nmaquinaria;
	}
	public void setNmaquinaria(String nmaquinaria) {
		this.nmaquinaria = nmaquinaria;
	}
	public String getNrebaja() {
		return nrebaja;
	}
	public void setNrebaja(String nrebaja) {
		this.nrebaja = nrebaja;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getFaena() {
		return faena;
	}
	public void setFaena(int faena) {
		this.faena = faena;
	}
	public String getLabor() {
		return labor;
	}
	public void setLabor(String labor) {
		this.labor = labor;
	}
	public int getMaquinaria() {
		return maquinaria;
	}
	public void setMaquinaria(int maquinaria) {
		this.maquinaria = maquinaria;
	}
	public int getRebaja() {
		return rebaja;
	}
	public void setRebaja(int rebaja) {
		this.rebaja = rebaja;
	}
	public int getTipo_labor() {
		return tipo_labor;
	}
	public void setTipo_labor(int tipo_labor) {
		this.tipo_labor = tipo_labor;
	}
	public String getNfaena() {
		return nfaena;
	}
	public void setNfaena(String nfaena) {
		this.nfaena = nfaena;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "{codigo:" + codigo + ", faena:" + faena + ", labor:'" + labor + "', maquinaria:" + maquinaria
				+ ", rebaja:" + rebaja + ", tipo_labor:" + tipo_labor + ", nfaena:'" + nfaena + "', estado:" + estado
				+ ", zona:'" + zona + "'}";
	}
}
