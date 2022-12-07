package lib.classSW;



public class LiquidacionPeriodo {
	
	private Integer codTrabajador;
	private Integer idContrato;
	private Integer periodo;
	private Integer idConcepto;
	private String concepto;
	private String valor;
	private Double valorDouble;
	
	@Override
	public String toString() {
		return "LiquidacionPeriodo [codTrabajador=" + codTrabajador + ", idContrato=" + idContrato + ", periodo="
				+ periodo + ", idConcepto=" + idConcepto + ", concepto=" + concepto + ", valor=" + valor
				+ ", valorDouble=" + valorDouble + "]";
	}
	public Integer getCodTrabajador() {
		return codTrabajador;
	}
	public void setCodTrabajador(Integer codTrabajador) {
		this.codTrabajador = codTrabajador;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public Integer getIdConcepto() {
		return idConcepto;
	}
	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Double getValorDouble() {
		return valorDouble;
	}
	public void setValorDouble(Double valorDouble) {
		this.valorDouble = valorDouble;
	}
}
