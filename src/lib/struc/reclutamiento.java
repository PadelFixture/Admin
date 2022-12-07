package lib.struc;

import java.util.ArrayList;

public class reclutamiento {
	public int id;
	public String fecha_trabajo;
	public String cant_requerida;
	public String cant_trabajo;
	public String operacion;
	public String observacion;
	public ArrayList<trabajadores> trab;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFecha_trabajo() {
		return fecha_trabajo;
	}
	public void setFecha_trabajo(String fecha_trabajo) {
		this.fecha_trabajo = fecha_trabajo;
	}
	public String getCant_requerida() {
		return cant_requerida;
	}
	public void setCant_requerida(String cant_requerida) {
		this.cant_requerida = cant_requerida;
	}
	public String getCant_trabajo() {
		return cant_trabajo;
	}
	public void setCant_trabajo(String cant_trabajo) {
		this.cant_trabajo = cant_trabajo;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public ArrayList<trabajadores> getTrab() {
		return trab;
	}
	public void setTrab(ArrayList<trabajadores> trab) {
		this.trab = trab;
	}
	
}
