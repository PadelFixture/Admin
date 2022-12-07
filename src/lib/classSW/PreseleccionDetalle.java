package lib.classSW;

public class PreseleccionDetalle {
	
	 public int id_reclutamiento;
	 public int cantidad;
	   public String obra;
	   public String fecha_inicio;
	   public String empresa;
	   public String fecha_now;
	public String getFecha_now() {
		return fecha_now;
	}
	public void setFecha_now(String fecha_now) {
		this.fecha_now = fecha_now;
	}
	public int getId_reclutamiento() {
		return id_reclutamiento;
	}
	public void setId_reclutamiento(int id_reclutamiento) {
		this.id_reclutamiento = id_reclutamiento;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getObra() {
		return obra;
	}
	public void setObra(String obra) {
		this.obra = obra;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

}
