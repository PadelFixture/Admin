package lib.classSA;

public class Servicio_Externo {
	
//ENVIO
	public int codigo;
	public String tipo_servicio;
	public int orden_ingreso;
	public float horometro;
	public float horometro_recepcionado;
	public String fecha;
	public int estado;
	public String campo;
	public String nvcampo;
	public String maquina;
	public String nvmaquina;
	
//REGRESO
	public int orden_envio;
	public String fecha_llegada;
	public float costo_servicio;
	
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getTipo_servicio() {
		return tipo_servicio;
	}
	public void setTipo_servicio(String tipo_servicio) {
		this.tipo_servicio = tipo_servicio;
	}
	public int getOrden_ingreso() {
		return orden_ingreso;
	}
	public void setOrden_ingreso(int orden_ingreso) {
		this.orden_ingreso = orden_ingreso;
	}
	public float getHorometro() {
		return horometro;
	}
	public void setHorometro(float horometro) {
		this.horometro = horometro;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getOrden_envio() {
		return orden_envio;
	}
	public void setOrden_envio(int orden_envio) {
		this.orden_envio = orden_envio;
	}
	public String getFecha_llegada() {
		return fecha_llegada;
	}
	public void setFecha_llegada(String fecha_llegada) {
		this.fecha_llegada = fecha_llegada;
	}
	public float getCosto_servicio() {
		return costo_servicio;
	}
	public void setCosto_servicio(float costo_servicio) {
		this.costo_servicio = costo_servicio;
	}
	public float getHorometro_recepcionado() {
		return horometro_recepcionado;
	}
	public void setHorometro_recepcionado(float horometro_recepcionado) {
		this.horometro_recepcionado = horometro_recepcionado;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getMaquina() {
		return maquina;
	}
	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}
	public String getNvcampo() {
		return nvcampo;
	}
	public void setNvcampo(String nvcampo) {
		this.nvcampo = nvcampo;
	}
	public String getNvmaquina() {
		return nvmaquina;
	}
	public void setNvmaquina(String nvmaquina) {
		this.nvmaquina = nvmaquina;
	}
}
