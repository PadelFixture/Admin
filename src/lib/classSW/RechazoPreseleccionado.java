package lib.classSW;

public class RechazoPreseleccionado {
	
	public int codigo;
	public String id_rechazo;
	public String observacion;
	public int id_peticion;
	public int codigo_peticion;
	
	public int getId_peticion() {
		return id_peticion;
	}
	public void setId_peticion(int id_peticion) {
		this.id_peticion = id_peticion;
	}
	public int getCodigo_peticion() {
		return codigo_peticion;
	}
	public void setCodigo_peticion(int codigo_peticion) {
		this.codigo_peticion = codigo_peticion;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getId_rechazo() {
		return id_rechazo;
	}
	public void setId_rechazo(String id_rechazo) {
		this.id_rechazo = id_rechazo;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
