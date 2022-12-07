package lib.classSW;

public class TramoCarga {
	public int idTramoCarga;
	public String descripcionTramoCarga;
	public int inicioTramoCarga;
	public int finTramoCarga;
	public int montoTramoCarga;
	public int periodoTramoCarga;
	public String letraTramoCarga;
	
	
	
	
	
	public String getLetraTramoCarga() {
		return letraTramoCarga;
	}
	public void setLetraTramoCarga(String letra) {
		this.letraTramoCarga = letra;
	}
	public int getMontoTramoCarga() {
		return montoTramoCarga;
	}
	public void setMontoTramoCarga(int monto) {
		this.montoTramoCarga = monto;
	}
	public int getPeriodoTramoCarga() {
		return periodoTramoCarga;
	}
	public void setPeriodoTramoCarga(int periodo) {
		this.periodoTramoCarga = periodo;
	}
	public int getFinTramoCarga() {
		return finTramoCarga;
	}
	public void setFinTramoCarga(int finTramoCarga) {
		this.finTramoCarga = finTramoCarga;
	}
	public int getIdTramoCarga() {
		return idTramoCarga;
	}
	public void setIdTramoCarga(int idTramoCarga) {
		this.idTramoCarga = idTramoCarga;
	}
	public String getDescripcionTramoCarga() {
		return descripcionTramoCarga;
	}
	public void setDescripcionTramoCarga(String descripcionTramoCarga) {
		this.descripcionTramoCarga = descripcionTramoCarga;
	}

	public int getInicioTramoCarga() {
		return inicioTramoCarga;
	}
	public void setInicioTramoCarga(int inicioTramoCarga) {
		this.inicioTramoCarga = inicioTramoCarga;
	}
	public TramoCarga createBlankTramo() {
		this.idTramoCarga=0;
		this.descripcionTramoCarga="";
		this.inicioTramoCarga=0;
		this.finTramoCarga=0;
		return this;
	}
	
	
}
