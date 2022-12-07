package lib.classSA;

import java.util.ArrayList;

public class PROGRA_FITOSANITARIO {

	public int codigo;
	public int id;
	public String fecha_estimada;
	public String fecha_alerta;
	public int tipo_control;
	public String nombre_tipo_control;
	public int estado_pf;
	public int programa_aplicacion;
	public String nombre_programa_aplicacion;
	public int usuario;
	public int usuario_ja;
	public String nombre_usuario_ja;
	public String observacion;
	public int temporada;
	public String campo;
	public String nombre_especie;
	public int especie;
	public int variedad;
	public String nombre_variedad;
	public int estado_fenologico;
	public String nombre_estado_fenologico;
	public int mojamiento;
	public ArrayList<CUARTEL_PF> cuart_PF;
	public ArrayList<MATERIAL_PF> mater_PF;
	public int tipo_programa;
	public String orden;
	public String reserva;
	public String solped;
	public String libro_campo;
	
	
	
	
	public String getLibro_campo() {
		return libro_campo;
	}
	public void setLibro_campo(String libro_campo) {
		this.libro_campo = libro_campo;
	}
	public String getSolped() {
		return solped;
	}
	public void setSolped(String solped) {
		this.solped = solped;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReserva() {
		return reserva;
	}
	public void setReserva(String reserva) {
		this.reserva = reserva;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public int getTipo_programa() {
		return tipo_programa;
	}
	public void setTipo_programa(int tipo_programa) {
		this.tipo_programa = tipo_programa;
	}
	//	cla
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getFecha_estimada() {
		return fecha_estimada;
	}
	public void setFecha_estimada(String fecha_estimada) {
		this.fecha_estimada = fecha_estimada;
	}
	public String getFecha_alerta() {
		return fecha_alerta;
	}
	public void setFecha_alerta(String fecha_alerta) {
		this.fecha_alerta = fecha_alerta;
	}
	public int getTipo_control() {
		return tipo_control;
	}
	public void setTipo_control(int tipo_control) {
		this.tipo_control = tipo_control;
	}
	public int getEstado_pf() {
		return estado_pf;
	}
	public void setEstado_pf(int estado_pf) {
		this.estado_pf = estado_pf;
	}
	public int getPrograma_aplicacion() {
		return programa_aplicacion;
	}
	public void setPrograma_aplicacion(int programa_aplicacion) {
		this.programa_aplicacion = programa_aplicacion;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	public int getUsuario_ja() {
		return usuario_ja;
	}
	public void setUsuario_ja(int usuario_ja) {
		this.usuario_ja = usuario_ja;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public int getTemporada() {
		return temporada;
	}
	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public int getEspecie() {
		return especie;
	}
	public void setEspecie(int especie) {
		this.especie = especie;
	}
	public int getVariedad() {
		return variedad;
	}
	public void setVariedad(int variedad) {
		this.variedad = variedad;
	}
	public int getEstado_fenologico() {
		return estado_fenologico;
	}
	public void setEstado_fenologico(int estado_fenologico) {
		this.estado_fenologico = estado_fenologico;
	}
	public int getMojamiento() {
		return mojamiento;
	}
	public void setMojamiento(int mojamiento) {
		this.mojamiento = mojamiento;
	}
	

	public String getNombre_tipo_control() {
		return nombre_tipo_control;
	}
	public void setNombre_tipo_control(String nombre_tipo_control) {
		this.nombre_tipo_control = nombre_tipo_control;
	}
	public String getNombre_programa_aplicacion() {
		return nombre_programa_aplicacion;
	}
	public void setNombre_programa_aplicacion(String nombre_programa_aplicacion) {
		this.nombre_programa_aplicacion = nombre_programa_aplicacion;
	}
	public String getNombre_usuario_ja() {
		return nombre_usuario_ja;
	}
	public void setNombre_usuario_ja(String nombre_usuario_ja) {
		this.nombre_usuario_ja = nombre_usuario_ja;
	}
	public String getNombre_especie() {
		return nombre_especie;
	}
	public void setNombre_especie(String nombre_especie) {
		this.nombre_especie = nombre_especie;
	}
	public String getNombre_variedad() {
		return nombre_variedad;
	}
	public void setNombre_variedad(String nombre_variedad) {
		this.nombre_variedad = nombre_variedad;
	}
	public String getNombre_estado_fenologico() {
		return nombre_estado_fenologico;
	}
	public void setNombre_estado_fenologico(String nombre_estado_fenologico) {
		this.nombre_estado_fenologico = nombre_estado_fenologico;
	}
	public ArrayList<CUARTEL_PF> getCuart_PF() {
		return cuart_PF;
	}
	public void setCuart_PF(ArrayList<CUARTEL_PF> cuart_PF) {
		this.cuart_PF = cuart_PF;
	}
	public ArrayList<MATERIAL_PF> getMater_PF() {
		return mater_PF;
	}
	public void setMater_PF(ArrayList<MATERIAL_PF> mater_PF) {
		this.mater_PF = mater_PF;
	}
	
	
}