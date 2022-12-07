package lib.classSW;

public class Contratos {

	private int id;
	private int codigo_trabajador;
	private int id_peticion;
	private int codigo_peticion;
	private int idSociedad;
	private String fechaInicio_actividad;
	private String fechaContrato_emitido;
	private String fechaTerminoContrato;
	private int  EstadoContrato;
	private int  cargo;
	private int  posicion;
	private int  tipoContrato; 
	private int  articuloTerminoContrato;
	private int  incisoTerminoContrato;
	private int  letraTerminoContrato; 
	private String fechaPago;
	private String lugarPago;
	private String horaPago;
	private String horaPago2;
	private int  idTurno;
	private String colacionFija;
	private int  valorFijo;
	private int  horasSemanales; /*Cambiar a double*/
	private double sueldoBase;
	private double  movilizacionFija;
	private int tipoTrabajador;
	private int partTime;
	private int supervisor;
	private String descripcion;
	private int jornada;
	private int maquinista;
	private int aviso;
	private int paraFiniquitar;
	private String fechaNotificacion;
	private int finiquitado;
	private String idHuertoContrato;
	private String idCECOContrato;
	private String fIngresoCompContrato;
	private int idFaenaContrato;
	private String periodo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCodigo_trabajador() {
		return codigo_trabajador;
	}
	public void setCodigo_trabajador(int codigo_trabajador) {
		this.codigo_trabajador = codigo_trabajador;
	}
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
	public int getIdSociedad() {
		return idSociedad;
	}
	public void setIdSociedad(int idSociedad) {
		this.idSociedad = idSociedad;
	}
	public String getFechaInicio_actividad() {
		return fechaInicio_actividad;
	}
	public void setFechaInicio_actividad(String fechaInicio_actividad) {
		this.fechaInicio_actividad = fechaInicio_actividad;
	}
	public String getFechaContrato_emitido() {
		return fechaContrato_emitido;
	}
	public void setFechaContrato_emitido(String fechaContrato_emitido) {
		this.fechaContrato_emitido = fechaContrato_emitido;
	}
	
	public String getFechaTerminoContrato() {
		return fechaTerminoContrato;
	}
	public void setFechaTerminoContrato(String fechaTerminoContrato) {
		this.fechaTerminoContrato = fechaTerminoContrato;
	}
	public int getEstadoContrato() {
		return EstadoContrato;
	}
	public void setEstadoContrato(int estadoContrato) {
		EstadoContrato = estadoContrato;
	}
	public int getCargo() {
		return cargo;
	}
	public void setCargo(int cargo) {
		this.cargo = cargo;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public int getTipoContrato() {
		return tipoContrato;
	}
	public void setTipoContrato(int tipoContrato) {
		this.tipoContrato = tipoContrato;
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
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	public String getColacionFija() {
		return colacionFija;
	}
	public void setColacionFija(String colacionFija) {
		this.colacionFija = colacionFija;
	}
	public int getValorFijo() {
		return valorFijo;
	}
	public void setValorFijo(int valorFijo) {
		this.valorFijo = valorFijo;
	}
	public int getHorasSemanales() {
		return horasSemanales;
	}
	public void setHorasSemanales(int horasSemanales) {
		this.horasSemanales = horasSemanales;
	}
	public double getSueldoBase() {
		return sueldoBase;
	}
	public void setSueldoBase(double sueldoBase) {
		this.sueldoBase = sueldoBase;
	}
	public double getMovilizacionFija() {
		return movilizacionFija;
	}
	public void setMovilizacionFija(double movilizacionFija) {
		this.movilizacionFija = movilizacionFija;
	}
	public int getTipoTrabajador() {
		return tipoTrabajador;
	}
	public void setTipoTrabajador(int tipoTrabajador) {
		this.tipoTrabajador = tipoTrabajador;
	}
	public int getPartTime() {
		return partTime;
	}
	public void setPartTime(int partTime) {
		this.partTime = partTime;
	}
	public int getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getJornada() {
		return jornada;
	}
	public void setJornada(int jornada) {
		this.jornada = jornada;
	}
	public int getMaquinista() {
		return maquinista;
	}
	public void setMaquinista(int maquinista) {
		this.maquinista = maquinista;
	}
	public int getAviso() {
		return aviso;
	}
	public void setAviso(int aviso) {
		this.aviso = aviso;
	}
	public int getParaFiniquitar() {
		return paraFiniquitar;
	}
	public void setParaFiniquitar(int paraFiniquitar) {
		this.paraFiniquitar = paraFiniquitar;
	}
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public String getHoraPago2() {
		return horaPago2;
	}
	public void setHoraPago2(String horaPago2) {
		this.horaPago2 = horaPago2;
	}
	public int getFiniquitado() {
		return finiquitado;
	}
	public void setFiniquitado(int finiquitado) {
		this.finiquitado = finiquitado;
	}
	public String getIdHuertoContrato() {
		return idHuertoContrato;
	}
	public void setIdHuertoContrato(String idHuertoContrato) {
		this.idHuertoContrato = idHuertoContrato;
	}
	public String getIdCECOContrato() {
		return idCECOContrato;
	}
	public void setIdCECOContrato(String idCECOContrato) {
		this.idCECOContrato = idCECOContrato;
	}
	public String getfIngresoCompContrato() {
		return fIngresoCompContrato;
	}
	public void setfIngresoCompContrato(String fIngresoCompContrato) {
		this.fIngresoCompContrato = fIngresoCompContrato;
	}
	public int getIdFaenaContrato() {
		return idFaenaContrato;
	}
	public void setIdFaenaContrato(int idFaenaContrato) {
		this.idFaenaContrato = idFaenaContrato;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	@Override
	public String toString() {
		return "Contratos [id=" + id + ", codigo_trabajador=" + codigo_trabajador + ", id_peticion=" + id_peticion
				+ ", codigo_peticion=" + codigo_peticion + ", idSociedad=" + idSociedad + ", fechaInicio_actividad="
				+ fechaInicio_actividad + ", fechaContrato_emitido=" + fechaContrato_emitido + ", fechaTerminoContrato="
				+ fechaTerminoContrato + ", EstadoContrato=" + EstadoContrato + ", cargo=" + cargo + ", posicion="
				+ posicion + ", tipoContrato=" + tipoContrato + ", articuloTerminoContrato=" + articuloTerminoContrato
				+ ", incisoTerminoContrato=" + incisoTerminoContrato + ", letraTerminoContrato=" + letraTerminoContrato
				+ ", fechaPago=" + fechaPago + ", lugarPago=" + lugarPago + ", horaPago=" + horaPago + ", horaPago2="
				+ horaPago2 + ", idTurno=" + idTurno + ", colacionFija=" + colacionFija + ", valorFijo=" + valorFijo
				+ ", horasSemanales=" + horasSemanales + ", sueldoBase=" + sueldoBase + ", movilizacionFija="
				+ movilizacionFija + ", tipoTrabajador=" + tipoTrabajador + ", partTime=" + partTime + ", supervisor="
				+ supervisor + ", descripcion=" + descripcion + ", jornada=" + jornada + ", maquinista=" + maquinista
				+ ", aviso=" + aviso + ", paraFiniquitar=" + paraFiniquitar + ", fechaNotificacion=" + fechaNotificacion
				+ ", finiquitado=" + finiquitado + ", idHuertoContrato=" + idHuertoContrato + ", idCECOContrato="
				+ idCECOContrato + ", fIngresoCompContrato=" + fIngresoCompContrato + ", idFaenaContrato="
				+ idFaenaContrato + ", periodo=" + periodo + "]";
	}
	
	
	
	
	
}