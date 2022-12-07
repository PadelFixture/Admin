package lib.classSA;

public class INGRESORIEGO {

	public int codigo;
	public String campo;
	public String caseta;
	public String equipo;
	public String motivo_ingreso;
	public String fecha;
	public String diagnostico_preliminar;
	public String nreserva;
	public String fechaCierre;
	
	
	
	public String getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public String getNreserva() {
		return nreserva;
	}
	public void setNreserva(String nreserva) {
		this.nreserva = nreserva;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDiagnostico_preliminar() {
		return diagnostico_preliminar;
	}
	public void setDiagnostico_preliminar(String diagnostico_preliminar) {
		this.diagnostico_preliminar = diagnostico_preliminar;
	}
	public String getCaseta() {
		return caseta;
	}
	public void setCaseta(String caseta) {
		this.caseta = caseta;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public String getMotivo_ingreso() {
		return motivo_ingreso;
	}
	public void setMotivo_ingreso(String motivo_ingreso) {
		this.motivo_ingreso = motivo_ingreso;
	}
}
