package lib.classSW;

import java.math.BigDecimal;

public class CentraRow {
	String sociedad;
	String concepto;
	String descripcion; 
	String proveedor;
	BigDecimal monto;
	int codTrabajador;
	String idCECO;
	String cuenta;
	String ordenco;
	
	
	public int getCodTrabajador() {
		return codTrabajador;
	}
	public void setCodTrabajador(int codTrabajador) {
		this.codTrabajador = codTrabajador;
	}
	public String getIdCECO() {
		return idCECO;
	}
	public void setIdCECO(String idCECO) {
		this.idCECO = idCECO;
	}
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getOrdenco() {
		return ordenco;
	}
	public void setOrdenco(String ordenco) {
		this.ordenco = ordenco;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	
	
	
	
}
