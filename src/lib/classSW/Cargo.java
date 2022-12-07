package lib.classSW;
import java.math.BigDecimal;

public class Cargo {
	int id_cargo;
	String cargos;
	BigDecimal sueldoBase;
	
	String sociedad;
	String denominacionSociedad;
	
	
	
	
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getDenominacionSociedad() {
		return denominacionSociedad;
	}
	public void setDenominacionSociedad(String denominacionSociedad) {
		this.denominacionSociedad = denominacionSociedad;
	}
	
	public int getId_cargo() {
		return id_cargo;
	}
	public void setId_cargo(int id_cargo) {
		this.id_cargo = id_cargo;
	}
	public String getCargos() {
		return cargos;
	}
	public void setCargos(String cargo) {
		this.cargos = cargo;
	}
	public BigDecimal getSueldoBase() {
		return sueldoBase;
	}
	public void setSueldoBase(BigDecimal sueldoBase) {
		this.sueldoBase = sueldoBase;
	}
	
}
