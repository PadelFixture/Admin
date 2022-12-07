package lib.classSW;

public class ContratosPer {

	//Merge Tabla Contratos y Contrato
	private int id; //idContrato
	private int codigo_trabajador; // idTrabajador
	private String fecha_inicio_actividad; //fechaIngreso
	private String fecha_termino_actividad; //fechaTermino
	private int estado_contrato; //estadoContrato
	private int id_sociedad; //sociedad

	//Tabla Contratos
	private int id_peticion;
	private int codigo_peticion;
	private String fecha_contrato_emitido;

	//Tabla contrato
	private int cargo;
	private int posicion;
	private int tipoContrato;
	private int articuloTerminoContrato;
	private int incisoTerminoContrato;
	private int letraTerminoContrato;
	private String fechaPago;
	private String lugarPago;
	private String horaPago;
	private int idTurno;
	private String colacionFija;
	private int valorFijo;
	private int horasSemanales;
	private double sueldoBase;
	
	private double movilizacionFija;
	private int tipoTrabajador;
	private int partTime;
	private int supervisor;
	private String descripcion;
	private int maquinista;
	private int jornada;
	private int paraFiniquitar;
	private String horaPago2;
	private String fechaNotificacion;
	private int finiquitado;
	private String idHuertoContrato;
	private String idCECOContrato;
	private String fIngresoCompContrato;
	private int idFaenaContrato;
	private int idSociedad;
	private String fechaInicio_actividad;
	private String fechaContrato_emitido;
	private String fechaTerminoContrato;
	private int  EstadoContrato;
	private int aviso;
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
	public String getFecha_inicio_actividad() {
		return fecha_inicio_actividad;
	}
	public void setFecha_inicio_actividad(String fecha_inicio_actividad) {
		this.fecha_inicio_actividad = fecha_inicio_actividad;
	}
	public String getFecha_termino_actividad() {
		return fecha_termino_actividad;
	}
	public void setFecha_termino_actividad(String fecha_termino_actividad) {
		this.fecha_termino_actividad = fecha_termino_actividad;
	}
	public int getEstado_contrato() {
		return estado_contrato;
	}
	public void setEstado_contrato(int estado_contrato) {
		this.estado_contrato = estado_contrato;
	}
	public int getId_sociedad() {
		return id_sociedad;
	}
	public void setId_sociedad(int id_sociedad) {
		this.id_sociedad = id_sociedad;
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
	public String getFecha_contrato_emitido() {
		return fecha_contrato_emitido;
	}
	public void setFecha_contrato_emitido(String fecha_contrato_emitido) {
		this.fecha_contrato_emitido = fecha_contrato_emitido;
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
	public int getMaquinista() {
		return maquinista;
	}
	public void setMaquinista(int maquinista) {
		this.maquinista = maquinista;
	}
	public int getJornada() {
		return jornada;
	}
	public void setJornada(int jornada) {
		this.jornada = jornada;
	}
	public int getParaFiniquitar() {
		return paraFiniquitar;
	}
	public void setParaFiniquitar(int paraFiniquitar) {
		this.paraFiniquitar = paraFiniquitar;
	}
	public String getHoraPago2() {
		return horaPago2;
	}
	public void setHoraPago2(String horaPago2) {
		this.horaPago2 = horaPago2;
	}
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
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
	public int getAviso() {
		return aviso;
	}
	public void setAviso(int aviso) {
		this.aviso = aviso;
	}
	public String getColacionFija() {
		return colacionFija;
	}
	public void setColacionFija(String colacionFija) {
		this.colacionFija = colacionFija;
	}
	
	
	
	
	
	
}
