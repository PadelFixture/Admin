package lib.classSW;

import java.math.BigDecimal;


public class IndicadorMonetario {
	private int idValor;
	private int idMoneda;
	private BigDecimal valor;
	private String fecha;
	private String nombreMoneda;
	
	
	public int getIdValor() {
		return idValor;
	}
	public void setIdValor(int idValor) {
		this.idValor = idValor;
	}
	public int getIdMoneda() {
		return idMoneda;
	}
	public void setIdMoneda(int idMoneda) {
		this.idMoneda = idMoneda;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNombreMoneda() {
		return nombreMoneda;
	}
	public void setNombreMoneda(String nombreMoneda) {
		this.nombreMoneda = nombreMoneda;
	}
	
	
	
}
