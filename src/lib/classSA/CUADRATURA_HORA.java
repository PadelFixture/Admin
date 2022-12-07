package lib.classSA;

public class CUADRATURA_HORA {
	public int id;
	public int codigo_rd;
	public int motivo;
	public String hora_desde;
	public String hora_hasta;
	public String observacion;
	public String fecha;
	public double horas;
	public String campo;
	public String rut;
	public String nombre;
	public String supervisor;
	public String nsupervisor;
	public String cuartel;
	public float horasFalta;
	public int codigo;


	public int getCodigo_rd() {
		return codigo_rd;
	}
	public void setCodigo_rd(int codigo_rd) {
		this.codigo_rd = codigo_rd;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public float getHorasFalta() {
		return horasFalta;
	}
	public void setHorasFalta(float horasFalta) {
		this.horasFalta = horasFalta;
	}
	public int getMotivo() {
		return motivo;
	}
	public void setMotivo(int motivo) {
		this.motivo = motivo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHora_desde() {
		return hora_desde;
	}
	public void setHora_desde(String hora_desde) {
		this.hora_desde = hora_desde;
	}
	public String getHora_hasta() {
		return hora_hasta;
	}
	public void setHora_hasta(String hora_hasta) {
		this.hora_hasta = hora_hasta;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	public String getCuartel() {
		return cuartel;
	}
	public void setCuartel(String cuartel) {
		this.cuartel = cuartel;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getHoras() {
		return horas;
	}
	public void setHoras(double horas) {
		this.horas = horas;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNsupervisor() {
		return nsupervisor;
	}
	public void setNsupervisor(String nsupervisor) {
		this.nsupervisor = nsupervisor;
	}
}
