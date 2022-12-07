package lib.classSW;

import java.math.BigDecimal;
import java.sql.Date;

public class ImpuestoUnico {
	private int idImpuesto;
	private Date fecha;
	private int idMoneda;
	private BigDecimal desde;
	private BigDecimal hasta;
	private BigDecimal factor;
	private BigDecimal rebaja;
	private BigDecimal tasaMaxima;
	private String moneda;
	public int getIdImpuesto() {
		return idImpuesto;
	}
	public void setIdImpuesto(int idImpuesto) {
		this.idImpuesto = idImpuesto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getIdMoneda() {
		return idMoneda;
	}
	public void setIdMoneda(int idMoneda) {
		this.idMoneda = idMoneda;
	}
	public BigDecimal getDesde() {
		return desde;
	}
	public void setDesde(BigDecimal desde) {
		this.desde = desde;
	}
	public BigDecimal getHasta() {
		return hasta;
	}
	public void setHasta(BigDecimal hasta) {
		this.hasta = hasta;
	}
	public BigDecimal getFactor() {
		return factor;
	}
	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}
	public BigDecimal getRebaja() {
		return rebaja;
	}
	public void setRebaja(BigDecimal rebaja) {
		this.rebaja = rebaja;
	}
	public BigDecimal getTasaMaxima() {
		return tasaMaxima;
	}
	public void setTasaMaxima(BigDecimal tasaMaxima) {
		this.tasaMaxima = tasaMaxima;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
}
