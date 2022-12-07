package lib.classSA;

import java.util.ArrayList;

public class ORDEN_APLICACION {

	public int codigo;
	public int codigo_pf;
	public String aplicador;
	public String fecha_programa;
	public String estado_fenologico;
	public String fecha_estimada_cosecha;
	public String mercado;
	public int codigo_fa;
	public String fecha_inicio;
	public String jefe_aplicacion;
	public double dosis_bombada;
	public String cambio_tractor;
	public int presion_bomba;
	public String marcha_tractor;
	public int dias_cosecha;
	public String fecha_viable_cosecha;
	public ArrayList<MAQUINARIA_PF> maquinaria_pf;
	public String campo;
	public String temporada;
	public String um;
	public double capacidad_maquina;
	public int dosificador;
	
	
	
	
	
	
	public int getDosificador() {
		return dosificador;
	}
	public void setDosificador(int dosificador) {
		this.dosificador = dosificador;
	}
	public double getCapacidad_maquina() {
		return capacidad_maquina;
	}
	public void setCapacidad_maquina(double capacidad_maquina) {
		this.capacidad_maquina = capacidad_maquina;
	}
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public String getTemporada() {
		return temporada;
	}
	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public ArrayList<MAQUINARIA_PF> getMaquinaria_pf() {
		return maquinaria_pf;
	}
	public void setMaquinaria_pf(ArrayList<MAQUINARIA_PF> maquinaria_pf) {
		this.maquinaria_pf = maquinaria_pf;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodigo_pf() {
		return codigo_pf;
	}
	public void setCodigo_pf(int codigo_pf) {
		this.codigo_pf = codigo_pf;
	}
	public String getAplicador() {
		return aplicador;
	}
	public void setAplicador(String aplicador) {
		this.aplicador = aplicador;
	}
	public String getFecha_programa() {
		return fecha_programa;
	}
	public void setFecha_programa(String fecha_programa) {
		this.fecha_programa = fecha_programa;
	}
	public String getEstado_fenologico() {
		return estado_fenologico;
	}
	public void setEstado_fenologico(String estado_fenologico) {
		this.estado_fenologico = estado_fenologico;
	}
	public String getFecha_estimada_cosecha() {
		return fecha_estimada_cosecha;
	}
	public void setFecha_estimada_cosecha(String fecha_estimada_cosecha) {
		this.fecha_estimada_cosecha = fecha_estimada_cosecha;
	}
	public String getMercado() {
		return mercado;
	}
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}
	public int getCodigo_fa() {
		return codigo_fa;
	}
	public void setCodigo_fa(int codigo_fa) {
		this.codigo_fa = codigo_fa;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public String getJefe_aplicacion() {
		return jefe_aplicacion;
	}
	public void setJefe_aplicacion(String jefe_aplicacion) {
		this.jefe_aplicacion = jefe_aplicacion;
	}
	public double getDosis_bombada() {
		return dosis_bombada;
	}
	public void setDosis_bombada(double dosis_bombada) {
		this.dosis_bombada = dosis_bombada;
	}

	public String getCambio_tractor() {
		return cambio_tractor;
	}
	public void setCambio_tractor(String cambio_tractor) {
		this.cambio_tractor = cambio_tractor;
	}
	public int getPresion_bomba() {
		return presion_bomba;
	}
	public void setPresion_bomba(int presion_bomba) {
		this.presion_bomba = presion_bomba;
	}
	public String getMarcha_tractor() {
		return marcha_tractor;
	}
	public void setMarcha_tractor(String marcha_tractor) {
		this.marcha_tractor = marcha_tractor;
	}
	public int getDias_cosecha() {
		return dias_cosecha;
	}
	public void setDias_cosecha(int dias_cosecha) {
		this.dias_cosecha = dias_cosecha;
	}
	public String getFecha_viable_cosecha() {
		return fecha_viable_cosecha;
	}
	public void setFecha_viable_cosecha(String fecha_viable_cosecha) {
		this.fecha_viable_cosecha = fecha_viable_cosecha;
	}


	
	
	
	
}
