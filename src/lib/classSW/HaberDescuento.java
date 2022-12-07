package lib.classSW;

import java.math.BigDecimal;

public class HaberDescuento {
	int codigo;
	String descripcion;
	String tipo;
	String imponible;
	String tributable;
	String rutEmpresa;
	String codSap;
	String centroCosto;
	BigDecimal valor;
	String cuentaContable;
	
	
	public String getCentroCosto() {
		return centroCosto;
	}
	public void setCentroCosto(String centroCosto) {
		this.centroCosto = centroCosto;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getImponible() {
		return imponible;
	}
	public void setImponible(String imponible) {
		this.imponible = imponible;
	}
	public String getTributable() {
		return tributable;
	}
	public void setTributable(String tributable) {
		this.tributable = tributable;
	}
	public String getRutEmpresa() {
		return rutEmpresa;
	}
	public void setRutEmpresa(String rutEmpresa) {
		this.rutEmpresa = rutEmpresa;
	}
	public String getCodSap() {
		return codSap;
	}
	public void setCodSap(String codSap) {
		this.codSap = codSap;
	}
	public String getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	@Override
	public String toString() {
		return "HaberDescuento [codigo=" + codigo + ", descripcion=" + descripcion + ", tipo=" + tipo + ", imponible="
				+ imponible + ", tributable=" + tributable + ", rutEmpresa=" + rutEmpresa + ", codSap=" + codSap
				+ ", centroCosto=" + centroCosto + ", valor=" + valor + ", cuentaContable=" + cuentaContable + "]";
	}
	
	
	
	
}
