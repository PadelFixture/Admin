package lib.classSW;

public class ContratoTrabajador {

	//	public int idContrato;
	//	public int idTrabajador;
	//	public String fechaIngreso;
	//	public String fechaTermino;
	//	public int cargo;
	//	public int posicion;
	//	public int tipoContrato;
	//	public int estadoContrato;
	//	public int articuloTerminoContrato;
	//	public int incisoTerminoContrato;
	//	public int letraTerminoContrato;
	//	public String sociedad;
	//	public String horaPago;
	//	public String lugarPago;
	//	public String fechaPago;
	//	public int idTurno;

	//Merge Tabla Contratos y Contrato
	public int id; //idContrato
	public int codigo_trabajador; // idTrabajador
	public String fecha_inicio_actividad; //fechaIngreso
	public String fecha_termino_actividad; //fechaTermino
	public int estado_contrato; //estadoContrato
	public int id_sociedad; //sociedad

	//Tabla Contratos
	public int id_peticion;
	public int codigo_peticion;
	public String fecha_contrato_emitido;

	//Tabla contrato
	public int cargo;
	public int posicion;
	public int tipoContrato;
	public int articuloTerminoContrato;
	public int incisoTerminoContrato;
	public int letraTerminoContrato;
	public String fechaPago;
	public String lugarPago;
	public String horaPago;
	public int idTurno;
	public int colacionFija;
	public int valorFijo;
	public int horasSemanales;
	public double sueldoBase;
	
	public double movilizacionFija;
	public int tipoTrabajador;
	private int partTime;
	private int supervisor;
	private String descripcion;
	private int maquinista;
	private int jornada;
	private int paraFiniquitar;
	private String horaPago2;
	private String fechaNotificacion;
	private int finiquitado;
	
	//Trabajador
	public int idTrabajador;
	public int codigoTrabajador;
	public String rut;
	public String nombre;
	public String apellidoPaterno;
	public String apellidoMaterno;
	
	private String idHuertoContrato;
	private String idCECOContrato;
	private String fIngresoCompContrato;
	private int idFaenaContrato;
	private int tipoMoneda;
	private String estadoProceso;
	
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
	public int getColacionFija() {
		return colacionFija;
	}
	public void setColacionFija(int colacionFija) {
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
	public int getIdTrabajador() {
		return idTrabajador;
	}
	public void setIdTrabajador(int idTrabajador) {
		this.idTrabajador = idTrabajador;
	}
	public int getCodigoTrabajador() {
		return codigoTrabajador;
	}
	public void setCodigoTrabajador(int codigoTrabajador) {
		this.codigoTrabajador = codigoTrabajador;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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
	public int getTipoMoneda() {
		return tipoMoneda;
	}
	public void setTipoMoneda(int tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
	public String getEstadoProceso() {
		return estadoProceso;
	}
	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}
	
	
	
	
}
