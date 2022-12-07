package lib.classSW;

public class Finiquito {
	
	private int idTrabajador;
	private int codTrabajador;
	private int idContrato;
	private int idSociedad;
	private String rut;
	public String fechaTerminoContrato;
	public int articuloTerminoContrato;
	public int incisoTerminoContrato;
	public int letraTerminoContrato;
	private String descripcion;
	public String fechaPago;
	public String lugarPago;
	public String horaPago;
	private int idNomina;	
	private double promediSueldo;
	private double promedioGratificacion;
	private double promedioSemanaCorrida;
	private double promedioAsignacionImp;
	private double promedioAsignacionNoImp;
	private double totalItems;
	private double pagoMesAviso;
	private int anoServicios;
	private double pagoAnoServicios;
	private int feriadosProporcionales;
	private double pagoFeriadosProporcionales;
	private double totalPagoFiniquito;
	private int estadoFiniquito;
	
	public Finiquito() {
	}

	@Override
	public String toString() {
		return "Finiquito [idTrabajador=" + idTrabajador + ", codTrabajador=" + codTrabajador + ", idContrato="
				+ idContrato + ", idSociedad=" + idSociedad + ", rut=" + rut + ", fechaTerminoContrato="
				+ fechaTerminoContrato + ", articuloTerminoContrato=" + articuloTerminoContrato
				+ ", incisoTerminoContrato=" + incisoTerminoContrato + ", letraTerminoContrato=" + letraTerminoContrato
				+ ", descripcion=" + descripcion + ", fechaPago=" + fechaPago + ", lugarPago=" + lugarPago
				+ ", horaPago=" + horaPago + ", idNomina=" + idNomina + ", promediSueldo=" + promediSueldo
				+ ", promedioGratificacion=" + promedioGratificacion + ", promedioSemanaCorrida="
				+ promedioSemanaCorrida + ", promedioAsignacionImp=" + promedioAsignacionImp
				+ ", promedioAsignacionNoImp=" + promedioAsignacionNoImp + ", totalItems=" + totalItems
				+ ", pagoMesAviso=" + pagoMesAviso + ", anoServicios=" + anoServicios + ", pagoAnoServicios="
				+ pagoAnoServicios + ", feriadosProporcionales=" + feriadosProporcionales
				+ ", pagoFeriadosProporcionales=" + pagoFeriadosProporcionales + ", totalPagoFiniquito="
				+ totalPagoFiniquito + ", estadoFiniquito=" + estadoFiniquito + "]";
	}

	public int getIdTrabajador() {
		return idTrabajador;
	}
	public void setIdTrabajador(int idTrabajador) {
		this.idTrabajador = idTrabajador;
	}
	public int getCodTrabajador() {
		return codTrabajador;
	}
	public void setCodTrabajador(int codTrabajador) {
		this.codTrabajador = codTrabajador;
	}
	public int getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}
	public int getIdSociedad() {
		return idSociedad;
	}
	public void setIdSociedad(int idSociedad) {
		this.idSociedad = idSociedad;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getFechaTerminoContrato() {
		return fechaTerminoContrato;
	}
	public void setFechaTerminoContrato(String fechaTerminoContrato) {
		this.fechaTerminoContrato = fechaTerminoContrato;
	}
	public int getArticuloTerminoContrato() {
		return articuloTerminoContrato;
	}
	public void setArticuloTerminoContrato(int articuloTerminoContrato) {
		this.articuloTerminoContrato = articuloTerminoContrato;
	}
	public int getIncisoTerminoContrato() {
		return incisoTerminoContrato;
	}
	public void setIncisoTerminoContrato(int incisoTerminoContrato) {
		this.incisoTerminoContrato = incisoTerminoContrato;
	}
	public int getLetraTerminoContrato() {
		return letraTerminoContrato;
	}
	public void setLetraTerminoContrato(int letraTerminoContrato) {
		this.letraTerminoContrato = letraTerminoContrato;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getLugarPago() {
		return lugarPago;
	}
	public void setLugarPago(String lugarPago) {
		this.lugarPago = lugarPago;
	}
	public String getHoraPago() {
		return horaPago;
	}
	public void setHoraPago(String horaPago) {
		this.horaPago = horaPago;
	}
	public int getIdNomina() {
		return idNomina;
	}
	public void setIdNomina(int idNomina) {
		this.idNomina = idNomina;
	}
	public double getPromediSueldo() {
		return promediSueldo;
	}
	public void setPromediSueldo(double promediSueldo) {
		this.promediSueldo = promediSueldo;
	}
	public double getPromedioGratificacion() {
		return promedioGratificacion;
	}
	public void setPromedioGratificacion(double promedioGratificacion) {
		this.promedioGratificacion = promedioGratificacion;
	}
	public double getPromedioSemanaCorrida() {
		return promedioSemanaCorrida;
	}
	public void setPromedioSemanaCorrida(double promedioSemanaCorrida) {
		this.promedioSemanaCorrida = promedioSemanaCorrida;
	}
	public double getPromedioAsignacionImp() {
		return promedioAsignacionImp;
	}
	public void setPromedioAsignacionImp(double promedioAsignacionImp) {
		this.promedioAsignacionImp = promedioAsignacionImp;
	}
	public double getPromedioAsignacionNoImp() {
		return promedioAsignacionNoImp;
	}
	public void setPromedioAsignacionNoImp(double promedioAsignacionNoImp) {
		this.promedioAsignacionNoImp = promedioAsignacionNoImp;
	}
	public double getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(double totalItems) {
		this.totalItems = totalItems;
	}
	public double getPagoMesAviso() {
		return pagoMesAviso;
	}
	public void setPagoMesAviso(double pagoMesAviso) {
		this.pagoMesAviso = pagoMesAviso;
	}
	public int getAnoServicios() {
		return anoServicios;
	}
	public void setAnoServicios(int anoServicios) {
		this.anoServicios = anoServicios;
	}
	public double getPagoAnoServicios() {
		return pagoAnoServicios;
	}
	public void setPagoAnoServicios(double pagoAnoServicios) {
		this.pagoAnoServicios = pagoAnoServicios;
	}
	public int getFeriadosProporcionales() {
		return feriadosProporcionales;
	}
	public void setFeriadosProporcionales(int feriadosProporcionales) {
		this.feriadosProporcionales = feriadosProporcionales;
	}
	public double getPagoFeriadosProporcionales() {
		return pagoFeriadosProporcionales;
	}
	public void setPagoFeriadosProporcionales(double pagoFeriadosProporcionales) {
		this.pagoFeriadosProporcionales = pagoFeriadosProporcionales;
	}
	public double getTotalPagoFiniquito() {
		return totalPagoFiniquito;
	}
	public void setTotalPagoFiniquito(double totalPagoFiniquito) {
		this.totalPagoFiniquito = totalPagoFiniquito;
	}
	public int getEstadoFiniquito() {
		return estadoFiniquito;
	}
	public void setEstadoFiniquito(int estadoFiniquito) {
		this.estadoFiniquito = estadoFiniquito;
	}
}
