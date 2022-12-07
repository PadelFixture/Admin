package lib.classSA;

import java.util.ArrayList;

public class notificacion {
	
	public int id_codigo;
	public int tipo;
	public int codigo_tarea;
	public int usuario_origen;
	public int usuario_receptor;
	public String campo;
	public int idPrograma;
	public String fecha_alerta;
	public String fecha_ingreso;
	public int estado;
	public String fecha;
	public String especie;
	public String variedad;
	public String estado_fenologico;
	public String programa_aplicacion;
	public String tipo_control;
	public String control;
	public String observacion;
	public ArrayList<MATERIAL_PF> material;
	public ArrayList<CUARTEL_PF> cuartel;
	
	
	
	public int getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(int idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	
	
	public String getTipo_control() {
		return tipo_control;
	}
	public void setTipo_control(String tipo_control) {
		this.tipo_control = tipo_control;
	}
	public int mojamiento;
	
	public int getMojamiento() {
		return mojamiento;
	}
	public void setMojamiento(int mojamiento) {
		this.mojamiento = mojamiento;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getVariedad() {
		return variedad;
	}
	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}
	public String getEstado_fenologico() {
		return estado_fenologico;
	}
	public void setEstado_fenologico(String estado_fenologico) {
		this.estado_fenologico = estado_fenologico;
	}
	public String getPrograma_aplicacion() {
		return programa_aplicacion;
	}
	public void setPrograma_aplicacion(String programa_aplicacion) {
		this.programa_aplicacion = programa_aplicacion;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public ArrayList<MATERIAL_PF> getMaterial() {
		return material;
	}
	public void setMaterial(ArrayList<MATERIAL_PF> material) {
		this.material = material;
	}
	public ArrayList<CUARTEL_PF> getCuartel() {
		return cuartel;
	}
	public void setCuartel(ArrayList<CUARTEL_PF> cuartel) {
		this.cuartel = cuartel;
	}
	public int getId_codigo() {
		return id_codigo;
	}
	public void setId_codigo(int id_codigo) {
		this.id_codigo = id_codigo;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getCodigo_tarea() {
		return codigo_tarea;
	}
	public void setCodigo_tarea(int codigo_tarea) {
		this.codigo_tarea = codigo_tarea;
	}
	public int getUsuario_origen() {
		return usuario_origen;
	}
	public void setUsuario_origen(int usuario_origen) {
		this.usuario_origen = usuario_origen;
	}
	public int getUsuario_receptor() {
		return usuario_receptor;
	}
	public void setUsuario_receptor(int usuario_receptor) {
		this.usuario_receptor = usuario_receptor;
	}
	public String getFecha_alerta() {
		return fecha_alerta;
	}
	public void setFecha_alerta(String fecha_alerta) {
		this.fecha_alerta = fecha_alerta;
	}
	public String getFecha_ingreso() {
		return fecha_ingreso;
	}
	public void setFecha_ingreso(String fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	

}
