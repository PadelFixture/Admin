package lib.classSA;

import java.util.ArrayList;
import java.util.List;

import lib.classSW.trabajador;

public class CUADRILLA {
	public int codigo;
	public String nombre_cuadrilla;
	public int supervisor;
	public String fecha_creacion;
	public int estado;
	public ArrayList<TrabajadoresAgro> trab;
	public ArrayList<RENDIMIENTO_GENERAL> rendimiento_general;
	public ArrayList<RENDIMIENTO_DIARIO> rd;
	
	public ArrayList<RENDIMIENTO_DIARIO> getRd() {
		return rd;
	}
	public void setRd(ArrayList<RENDIMIENTO_DIARIO> rd) {
		this.rd = rd;
	}
	public ArrayList<TrabajadoresAgro> getTrab() {
		return trab;
	}
	public ArrayList<RENDIMIENTO_GENERAL> getRendimiento_general() {
		return rendimiento_general;
	}
	public void setRendimiento_general(ArrayList<RENDIMIENTO_GENERAL> rendimiento_general) {
		this.rendimiento_general = rendimiento_general;
	}
	public void setTrab(ArrayList<TrabajadoresAgro> trab) {
		this.trab = trab;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre_cuadrilla() {
		return nombre_cuadrilla;
	}
	public void setNombre_cuadrilla(String nombre_cuadrilla) {
		this.nombre_cuadrilla = nombre_cuadrilla;
	}
	public int getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}
	public String getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
}
