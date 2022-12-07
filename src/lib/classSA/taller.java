package lib.classSA;

public class taller {
	public int codigo;
	public String campo;
	public String tipo;
	public int vehiculo;
	public String motivo;
	public int causa;
	public String fecha;
	public String operador;
	public float horometro;
	public String observacion;
	public String nreserva;
	public int estado;
	public String fechaCierre;
	public float horoCierre;
	public String dgtco_dfnvo;
	public String recomendacion;
	private String consumo;
	private String devolucion;
	private float cantidad;
	public String descCampo;
	
	
	
	public String getDescCampo() {
		return descCampo;
	}
	public void setDescCampo(String descCampo) {
		this.descCampo = descCampo;
	}
	public float getCantidad() {
		return cantidad;
	}
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	public String getConsumo() {
		return consumo;
	}
	public void setConsumo(String consumo) {
		this.consumo = consumo;
	}
	public String getDevolucion() {
		return devolucion;
	}
	public void setDevolucion(String devolucion) {
		this.devolucion = devolucion;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public float getHoroCierre() {
		return horoCierre;
	}
	public void setHoroCierre(float horoCierre) {
		this.horoCierre = horoCierre;
	}
	public String getRecomendacion() {
		return recomendacion;
	}
	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}
	public String getDgtco_dfnvo() {
		return dgtco_dfnvo;
	}
	public void setDgtco_dfnvo(String dgtco_dfnvo) {
		this.dgtco_dfnvo = dgtco_dfnvo;
	}
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
		if(nreserva==null)
		this.nreserva = "";
		else
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(int vehiculo) {
		this.vehiculo = vehiculo;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public int getCausa() {
		return causa;
	}
	public void setCausa(int causa) {
		this.causa = causa;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public float getHorometro() {
		return horometro;
	}
	public void setHorometro(float horometro) {
		this.horometro = horometro;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
}
