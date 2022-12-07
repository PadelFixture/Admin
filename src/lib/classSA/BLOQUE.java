package lib.classSA;

import java.util.ArrayList;

public class BLOQUE {
	
	public int codigo;
	public String campo;
	public String nombre;
	public String sector;
	public String especie;
	public String variedad;
	public ArrayList<CUARTEL_PF> cuarteles;
	public String tipo_riego;
	public String precipitacion_nominativa;
	public String aforo;
	public String reposicion;
	public String georeferencia;
	
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
	public String getGeoreferencia() {
		return georeferencia;
	}
	public void setGeoreferencia(String georeferencia) {
		this.georeferencia = georeferencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
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
	public ArrayList<CUARTEL_PF> getCuarteles() {
		return cuarteles;
	}
	public void setCuarteles(ArrayList<CUARTEL_PF> cuarteles) {
		this.cuarteles = cuarteles;
	}
	public String getTipo_riego() {
		return tipo_riego;
	}
	public void setTipo_riego(String tipo_riego) {
		this.tipo_riego = tipo_riego;
	}
	public String getPrecipitacion_nominativa() {
		return precipitacion_nominativa;
	}
	public void setPrecipitacion_nominativa(String precipitacion_nominativa) {
		this.precipitacion_nominativa = precipitacion_nominativa;
	}
	public String getAforo() {
		return aforo;
	}
	public void setAforo(String aforo) {
		this.aforo = aforo;
	}
	public String getReposicion() {
		return reposicion;
	}
	public void setReposicion(String reposicion) {
		this.reposicion = reposicion;
	}

}
