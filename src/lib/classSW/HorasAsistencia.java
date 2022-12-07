package lib.classSW;

public class HorasAsistencia {
	public int empresa;
	public int cod_trabajador;
	public int id_contrato;
	public int concepto;
	public int periodo;
	public String fecha;
	public double hora;
	public int id_horasasistencia;
	public String ruta;
	
	
	
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public int getId_horasasistencia() {
		return id_horasasistencia;
	}
	public void setId_horasasistencia(int id_horasasistencia) {
		this.id_horasasistencia = id_horasasistencia;
	}
	public int getEmpresa() {
		return empresa;
	}
	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}
	public int getCod_trabajador() {
		return cod_trabajador;
	}
	public void setCod_trabajador(int cod_trabajador) {
		this.cod_trabajador = cod_trabajador;
	}
	public int getId_contrato() {
		return id_contrato;
	}
	public void setId_contrato(int id_contrato) {
		this.id_contrato = id_contrato;
	}
	public int getConcepto() {
		return concepto;
	}
	public void setConcepto(int concepto) {
		this.concepto = concepto;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getHora() {
		return hora;
	}
	public void setHora(double hora) {
		this.hora = hora;
	}
	
	

}
