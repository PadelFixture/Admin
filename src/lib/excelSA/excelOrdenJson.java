package lib.excelSA;

import java.util.ArrayList;

public class excelOrdenJson {
	public String nombre;
	public int codigo;
	public int idPrograma;
	public String programaAplicacion;
	public String admCampo;
	public String jefeAplicaion;
	public String campo;
	public String fechaInicio;
	public String formaAplicacion;
	public String orientacion_viento;
	public String temperatura;
	public String velocidad_viento;
	public String fecha_termino;
	public ArrayList<listaCuarteles> listaCuarteles;
	public ArrayList<listaMaq> listaMaq;
	public ArrayList<listaMateriales> listaMateriales;
	public ArrayList<listaVariedad> listaVariedad;
	public String getNombre() {
		return nombre;
	}
	public String getOrientacion_viento() {
		return orientacion_viento;
	}
	public void setOrientacion_viento(String orientacion_viento) {
		this.orientacion_viento = orientacion_viento;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	public String getVelocidad_viento() {
		return velocidad_viento;
	}
	public void setVelocidad_viento(String velocidad_viento) {
		this.velocidad_viento = velocidad_viento;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(int idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getProgramaAplicacion() {
		return programaAplicacion;
	}
	public void setProgramaAplicacion(String programaAplicacion) {
		this.programaAplicacion = programaAplicacion;
	}
	public String getAdmCampo() {
		return admCampo;
	}
	public void setAdmCampo(String admCampo) {
		this.admCampo = admCampo;
	}
	public String getJefeAplicaion() {
		return jefeAplicaion;
	}
	public void setJefeAplicaion(String jefeAplicaion) {
		this.jefeAplicaion = jefeAplicaion;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFormaAplicacion() {
		return formaAplicacion;
	}
	public void setFormaAplicacion(String formaAplicacion) {
		this.formaAplicacion = formaAplicacion;
	}
	public String getFecha_termino() {
		return fecha_termino;
	}
	public void setFecha_termino(String fecha_termino) {
		this.fecha_termino = fecha_termino;
	}
	public ArrayList<listaCuarteles> getListaCuarteles() {
		return listaCuarteles;
	}
	public void setListaCuarteles(ArrayList<listaCuarteles> listaCuarteles) {
		this.listaCuarteles = listaCuarteles;
	}
	public ArrayList<listaMaq> getListaMaq() {
		return listaMaq;
	}
	public void setListaMaq(ArrayList<listaMaq> listaMaq) {
		this.listaMaq = listaMaq;
	}
	public ArrayList<listaMateriales> getListaMateriales() {
		return listaMateriales;
	}
	public void setListaMateriales(ArrayList<listaMateriales> listaMateriales) {
		this.listaMateriales = listaMateriales;
	}
	public ArrayList<listaVariedad> getListaVariedad() {
		return listaVariedad;
	}
	public void setListaVariedad(ArrayList<listaVariedad> listaVariedad) {
		this.listaVariedad = listaVariedad;
	}
}
