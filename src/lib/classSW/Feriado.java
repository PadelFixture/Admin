package lib.classSW;

import java.sql.Date;

public class Feriado {
	private int idFeriado;
	private String nombreFeriado;
	private Date fechaFeriado;
	private String descripcionFeriado;
	private int idRegion;
	private String nombreRegion;
	public String getNombreRegion() {
		return nombreRegion;
	}
	public void setNombreRegion(String nombreRegion) {
		this.nombreRegion = nombreRegion;
	}
	public int getIdFeriado() {
		return idFeriado;
	}
	public void setIdFeriado(int idFeriado) {
		this.idFeriado = idFeriado;
	}
	public String getNombreFeriado() {
		return nombreFeriado;
	}
	public void setNombreFeriado(String nombreFeriado) {
		this.nombreFeriado = nombreFeriado;
	}
	public Date getFechaFeriado() {
		return fechaFeriado;
	}
	public void setFechaFeriado(Date fechaFeriado) {
		this.fechaFeriado = fechaFeriado;
	}
	public String getDescripcionFeriado() {
		return descripcionFeriado;
	}
	public void setDescripcionFeriado(String descripcionFeriado) {
		this.descripcionFeriado = descripcionFeriado;
	}
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	public Feriado createBlankHoliday() {
		this.idFeriado=0;
		this.nombreFeriado="";
		this.fechaFeriado=new Date(0);
		this.descripcionFeriado="";
		this.idRegion=16;
		return this;
	}
	
	
}
