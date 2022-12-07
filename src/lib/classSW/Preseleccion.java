package lib.classSW;

import java.util.ArrayList;

public class Preseleccion {
	
	public ArrayList<trabajadores> codigo_trabajador;
	public int codigo_peticion;
	public String fecha_entrevista;
	
	
	public String getFecha_entrevista() {
		return fecha_entrevista;
	}
	public void setFecha_entrevista(String fecha_entrevista) {
		this.fecha_entrevista = fecha_entrevista;
	}
	public ArrayList<trabajadores> getCodigo_trabajador() {
		return codigo_trabajador;
	}
	public void setCodigo_trabajador(ArrayList<trabajadores> codigo_trabajador) {
		this.codigo_trabajador = codigo_trabajador;
	}
	public int getCodigo_peticion() {
		return codigo_peticion;
	}
	public void setCodigo_peticion(int codigo_peticion) {
		this.codigo_peticion = codigo_peticion;
	}

}
