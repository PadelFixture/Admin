package lib.classSA;

import java.util.ArrayList;

public class CONFIRMACION_APLICACION {
	
	public int codigo;
	public int codigo_orden;
	public String fecha_termino;
	public int mojamiento;
	public String tractor_real;
	public int bomba_real;
	public String boquilla_real;
	public int cambio_real;
	public int presion_real;
	public String velocidad_viento;
	public String orientacion_viento;
	public String hora_inicio;
	public String fecha_inicio;
	public String hora_termino;
	public ArrayList<MATERIAL_PF> lista_materiales;
	public ArrayList<CUARTEL_PF> lista_cuarteles;
	public ArrayList<MAQUINARIA_PF> lista_maquinaria;
	public String consumo;
	public String devolucion;         
	public String diferencia;
	public String temperatura;
	public double cantidad_devolucion;
	public double mojamiento_total;
	public int tipo;
	private String periodo;
	private String observacion;
	
	
	
	
	
	
	
	
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public double getMojamiento_total() {
		return mojamiento_total;
	}
	public void setMojamiento_total(double mojamiento_total) {
		this.mojamiento_total = mojamiento_total;
	}
	public double getCantidad_devolucion() {
		return cantidad_devolucion;
	}
	public void setCantidad_devolucion(double cantidad_devolucion) {
		this.cantidad_devolucion = cantidad_devolucion;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
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
	public String getDiferencia() {
		return diferencia;
	}
	public void setDiferencia(String diferencia) {
		this.diferencia = diferencia;
	}
	public int getCambio_real() {
		return cambio_real;
	}
	public void setCambio_real(int cambio_real) {
		this.cambio_real = cambio_real;
	}
	public int getPresion_real() {
		return presion_real;
	}
	public void setPresion_real(int presion_real) {
		this.presion_real = presion_real;
	}
	public String getVelocidad_viento() {
		return velocidad_viento;
	}
	public void setVelocidad_viento(String velocidad_viento) {
		this.velocidad_viento = velocidad_viento;
	}
	public String getOrientacion_viento() {
		return orientacion_viento;
	}
	public void setOrientacion_viento(String orientacion_viento) {
		this.orientacion_viento = orientacion_viento;
	}
	public String getHora_inicio() {
		return hora_inicio;
	}
	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public String getHora_termino() {
		return hora_termino;
	}
	public void setHora_termino(String hora_termino) {
		this.hora_termino = hora_termino;
	}
	
	
	
	public ArrayList<MATERIAL_PF> getLista_materiales() {
		return lista_materiales;
	}
	public void setLista_materiales(ArrayList<MATERIAL_PF> lista_materiales) {
		this.lista_materiales = lista_materiales;
	}
	public ArrayList<CUARTEL_PF> getLista_cuarteles() {
		return lista_cuarteles;
	}
	public void setLista_cuarteles(ArrayList<CUARTEL_PF> lista_cuarteles) {
		this.lista_cuarteles = lista_cuarteles;
	}
	
	
	
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodigo_orden() {
		return codigo_orden;
	}
	public void setCodigo_orden(int codigo_orden) {
		this.codigo_orden = codigo_orden;
	}
	public String getFecha_termino() {
		return fecha_termino;
	}
	public void setFecha_termino(String fecha_termino) {
		this.fecha_termino = fecha_termino;
	}
	public int getMojamiento() {
		return mojamiento;
	}
	public void setMojamiento(int mojamiento) {
		this.mojamiento = mojamiento;
	}
	public String getTractor_real() {
		return tractor_real;
	}
	public void setTractor_real(String tractor_real) {
		this.tractor_real = tractor_real;
	}
	public int getBomba_real() {
		return bomba_real;
	}
	public void setBomba_real(int bomba_real) {
		this.bomba_real = bomba_real;
	}
	public String getBoquilla_real() {
		return boquilla_real;
	}
	public void setBoquilla_real(String boquilla_real) {
		this.boquilla_real = boquilla_real;
	}
	public ArrayList<MAQUINARIA_PF> getLista_maquinaria() {
		return lista_maquinaria;
	}
	public void setLista_maquinaria(ArrayList<MAQUINARIA_PF> lista_maquinaria) {
		this.lista_maquinaria = lista_maquinaria;
	}
	
	
	
	

}
