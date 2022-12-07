package lib.classSW;

public class EvMut {
	public int idTrabajador;
	public int idEventosMutualidad;
	public String fechaRegistro;//hoy
	/* check */public String rut;//desde trabajadores
	/* check */public String nombreTrabajador; //desde trabajadores
	/* check */public String apellidoTrabajador;//desde Trabajadores
	/* check */public int edadTrabajador; //desde trabajadores
	public String anosAntiguedad; //desde trabajador fecha actual - fechaIngresoCompania
	public String fechaHoraAccidente;  //llenado manual
	public String ubicacionAccidente; //llenado manual
	public String actividadTrabajador; //llenado manual
	public String lugarTrabajador;  //llenado manual ???
	public String consecuencia; //llenado manual
	public int tipoPeligro;//llenado desde params en un select  
	public String causaAccidente; //llenado manual
	public String accionMejora; //llenado manual
	public String ResponsableMejora; //llenado manual de que tabla?
	public String plazoMejora; //llenado manual
	public String nombreJefe; //??
	public String apellidoJefe; //??
	public int cargoJefe; //??
	
	public int division;//desde trabajadores
	public String subdivision;//desde trabajadores
	public int idCargo;//desde trabajadores
	public int idLicencia; //llenado desde licencia
	public int idUsuario; //llenado desde la session.getIdUser();
	public String oficioTrabajador;
	public String divisionstring;//desde trabajadores
	public String fecha_hasta;
	public String hora_accidente;
	public int estado;
	public int tipocontrato;
	public String numerotelefono;
	public String nacionalidad_;
	public String fechanacimiento;
	public String comuna_;
	public int sexo_;
	public String aniomesdia_;
	public int cantidadaniomesdia_;
	public int tiporeposo;
	public int subtipolicencia;
	public String pueblooriginario;
	public int etnia;
	public String horaingreso;
	public String horasalida;
	public String comuna_accidente;
	public String queestabahaciendatrab;
	public String trabajohabitual;
	public int trabajohabitualsino;
	public int clasificacionaccidente;
	public int tipoaccidentetrayecto;
	public int mediodeprueba;
	public String detallemedioprueba;
	public int codigojefe;
	public EvMut()
	{
		  this.idTrabajador=0;	
		  this.idEventosMutualidad=0;
		  this.fechaRegistro="";
		  this.rut="";
		  this.nombreTrabajador="";
		  this.apellidoTrabajador="";
		  this.edadTrabajador=0;
		  this.anosAntiguedad="";
		  this.fechaHoraAccidente="";
		  this.ubicacionAccidente="";
		  this.actividadTrabajador="";
		  this.lugarTrabajador="";
		  this.consecuencia="";
		  this.tipoPeligro=0;
		  this.causaAccidente="";
		  this.accionMejora="";
		  this.ResponsableMejora="";
		  this.plazoMejora="";
		  this.nombreJefe="";
		  this.apellidoJefe="";
		  this.cargoJefe=0;
		  this.division=0;
		  this.subdivision="";
		  this.idCargo=0;
		  this.idLicencia=0;
		  this.idUsuario=0;
		  this.oficioTrabajador ="";
		  this.divisionstring = "";
		  this.fecha_hasta = "";
		  this.hora_accidente = "";
		  this.estado = 0;
		  this.tipocontrato = 0;
		  this.numerotelefono = "";
		  this.nacionalidad_ = "";
		  this.fechanacimiento = "";
		  this.comuna_ = "";
		  this.sexo_ = 0;
		  this.aniomesdia_ = "";
		  this.cantidadaniomesdia_ = 0;
		  this.tiporeposo = 0;
		  this.subtipolicencia = 0;
		  this.pueblooriginario = "";
		  this.etnia = 0;
		  this.horaingreso = "";
		  this.horasalida = "";
		  this.comuna_accidente = "";
		  this.queestabahaciendatrab = "";
		  this.trabajohabitual = "";
		  this.trabajohabitualsino = 0;
		  this.clasificacionaccidente = 0;
		  this.tipoaccidentetrayecto = 0;
		  this.mediodeprueba = 0;
		  this.detallemedioprueba = "";
		  this.codigojefe = 0;
		  
	}
	
	
	public int getCodigojefe() {
		return codigojefe;
	}


	public void setCodigojefe(int codigojefe) {
		this.codigojefe = codigojefe;
	}


	public String getDetallemedioprueba() {
		return detallemedioprueba;
	}


	public void setDetallemedioprueba(String detallemedioprueba) {
		this.detallemedioprueba = detallemedioprueba;
	}


	public int getMediodeprueba() {
		return mediodeprueba;
	}


	public void setMediodeprueba(int mediodeprueba) {
		this.mediodeprueba = mediodeprueba;
	}


	public int getTipoaccidentetrayecto() {
		return tipoaccidentetrayecto;
	}


	public void setTipoaccidentetrayecto(int tipoaccidentetrayecto) {
		this.tipoaccidentetrayecto = tipoaccidentetrayecto;
	}


	public int getClasificacionaccidente() {
		return clasificacionaccidente;
	}


	public void setClasificacionaccidente(int clasificacionaccidente) {
		this.clasificacionaccidente = clasificacionaccidente;
	}


	public int getTrabajohabitualsino() {
		return trabajohabitualsino;
	}


	public void setTrabajohabitualsino(int trabajohabitualsino) {
		this.trabajohabitualsino = trabajohabitualsino;
	}


	public String getTrabajohabitual() {
		return trabajohabitual;
	}


	public void setTrabajohabitual(String trabajohabitual) {
		this.trabajohabitual = trabajohabitual;
	}


	public String getComuna_accidente() {
		return comuna_accidente;
	}


	public void setComuna_accidente(String comuna_accidente) {
		this.comuna_accidente = comuna_accidente;
	}


	public String getQueestabahaciendatrab() {
		return queestabahaciendatrab;
	}


	public void setQueestabahaciendatrab(String queestabahaciendatrab) {
		this.queestabahaciendatrab = queestabahaciendatrab;
	}




	public String getHorasalida() {
		return horasalida;
	}


	public void setHorasalida(String horasalida) {
		this.horasalida = horasalida;
	}


	public String getHoraingreso() {
		return horaingreso;
	}


	public void setHoraingreso(String horaingreso) {
		this.horaingreso = horaingreso;
	}


	public int getEtnia() {
		return etnia;
	}


	public void setEtnia(int etnia) {
		this.etnia = etnia;
	}


	public String getPueblooriginario() {
		return pueblooriginario;
	}


	public void setPueblooriginario(String pueblooriginario) {
		this.pueblooriginario = pueblooriginario;
	}


	public int getSubtipolicencia() {
		return subtipolicencia;
	}


	public void setSubtipolicencia(int subtipolicencia) {
		this.subtipolicencia = subtipolicencia;
	}


	public int getTiporeposo() {
		return tiporeposo;
	}


	public void setTiporeposo(int tiporeposo) {
		this.tiporeposo = tiporeposo;
	}


	public String getAniomesdia_() {
		return aniomesdia_;
	}


	public void setAniomesdia_(String aniomesdia_) {
		this.aniomesdia_ = aniomesdia_;
	}


	public int getCantidadaniomesdia_() {
		return cantidadaniomesdia_;
	}


	public void setCantidadaniomesdia_(int cantidadaniomesdia_) {
		this.cantidadaniomesdia_ = cantidadaniomesdia_;
	}


	public int getSexo_() {
		return sexo_;
	}


	public void setSexo_(int sexo_) {
		this.sexo_ = sexo_;
	}


	public String getComuna_() {
		return comuna_;
	}


	public void setComuna_(String comuna_) {
		this.comuna_ = comuna_;
	}


	public String getFechanacimiento() {
		return fechanacimiento;
	}


	public void setFechanacimiento(String fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}


	public String getNacionalidad_() {
		return nacionalidad_;
	}


	public void setNacionalidad_(String nacionalidad_) {
		this.nacionalidad_ = nacionalidad_;
	}


	public String getNumerotelefono() {
		return numerotelefono;
	}


	public void setNumerotelefono(String numerotelefono) {
		this.numerotelefono = numerotelefono;
	}


	public int getTipocontrato() {
		return tipocontrato;
	}


	public void setTipocontrato(int tipocontrato) {
		this.tipocontrato = tipocontrato;
	}


	public int getEstado() {
		return estado;
	}


	public void setEstado(int estado) {
		this.estado = estado;
	}


	public String getHora_accidente() {
		return hora_accidente;
	}


	public void setHora_accidente(String hora_accidente) {
		this.hora_accidente = hora_accidente;
	}


	public String getFecha_hasta() {
		return fecha_hasta;
	}


	public void setFecha_hasta(String fecha_hasta) {
		this.fecha_hasta = fecha_hasta;
	}


	public String getDivisionstring() {
		return divisionstring;
	}


	public void setDivisionstring(String divisionstring) {
		this.divisionstring = divisionstring;
	}


	public String getOficioTrabajador() {
		return oficioTrabajador;
	}


	public void setOficioTrabajador(String oficioTrabajador) {
		this.oficioTrabajador = oficioTrabajador;
	}


	public int getIdEventosMutualidad() {
		return idEventosMutualidad;
	}
	public void setIdEventosMutualidad(int idEventosMutualidad) {
		this.idEventosMutualidad = idEventosMutualidad;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getNombreTrabajador() {
		return nombreTrabajador;
	}
	public void setNombreTrabajador(String nombreTrabajador) {
		this.nombreTrabajador = nombreTrabajador;
	}
	public String getApellidoTrabajador() {
		return apellidoTrabajador;
	}
	public void setApellidoTrabajador(String apellidoTrabajador) {
		this.apellidoTrabajador = apellidoTrabajador;
	}
	public int getEdadTrabajador() {
		return edadTrabajador;
	}
	public void setEdadTrabajador(int edadTrabajador) {
		this.edadTrabajador = edadTrabajador;
	}
	public String getAnosAntiguedad() {
		return anosAntiguedad;
	}
	public void setAnosAntiguedad(String anosAntiguedad) {
		this.anosAntiguedad = anosAntiguedad;
	}
	public String getFechaHoraAccidente() {
		return fechaHoraAccidente;
	}
	public void setFechaHoraAccidente(String fechaHoraAccidente) {
		this.fechaHoraAccidente = fechaHoraAccidente;
	}
	public String getUbicacionAccidente() {
		return ubicacionAccidente;
	}
	public void setUbicacionAccidente(String ubicacionAccidente) {
		this.ubicacionAccidente = ubicacionAccidente;
	}
	public String getActividadTrabajador() {
		return actividadTrabajador;
	}
	public void setActividadTrabajador(String actividadTrabajador) {
		this.actividadTrabajador = actividadTrabajador;
	}
	public String getLugarTrabajador() {
		return lugarTrabajador;
	}
	public void setLugarTrabajador(String lugarTrabajador) {
		this.lugarTrabajador = lugarTrabajador;
	}
	public String getConsecuencia() {
		return consecuencia;
	}
	public void setConsecuencia(String consecuencia) {
		this.consecuencia = consecuencia;
	}
	public int getTipoPeligro() {
		return tipoPeligro;
	}
	public void setTipoPeligro(int tipoPeligro) {
		this.tipoPeligro = tipoPeligro;
	}
	public String getCausaAccidente() {
		return causaAccidente;
	}
	public void setCausaAccidente(String causaAccidente) {
		this.causaAccidente = causaAccidente;
	}
	public String getAccionMejora() {
		return accionMejora;
	}
	public void setAccionMejora(String accionMejora) {
		this.accionMejora = accionMejora;
	}
	public String getResponsableMejora() {
		return ResponsableMejora;
	}
	public void setResponsableMejora(String responsableMejora) {
		ResponsableMejora = responsableMejora;
	}
	public String getPlazoMejora() {
		return plazoMejora;
	}
	public void setPlazoMejora(String plazoMejora) {
		this.plazoMejora = plazoMejora;
	}
	public String getNombreJefe() {
		return nombreJefe;
	}
	public void setNombreJefe(String nombreJefe) {
		this.nombreJefe = nombreJefe;
	}
	public int getCargoJefe() {
		return cargoJefe;
	}
	public void setCargoJefe(int cargoJefe) {
		this.cargoJefe = cargoJefe;
	}
	public int getDivision() {
		return division;
	}
	public void setDivision(int division) {
		this.division = division;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public int getIdCargo() {
		return idCargo;
	}
	public void setIdCargo(int idCargo) {
		this.idCargo = idCargo;
	}
	public int getIdLicencia() {
		return idLicencia;
	}
	public void setIdLicencia(int idLicencia) {
		this.idLicencia = idLicencia;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getApellidoJefe() {
		return apellidoJefe;
	}
	public void setApellidoJefe(String apellidoJefe) {
		this.apellidoJefe = apellidoJefe;
	}
	public EvMut createBlankEvMut() {
		return this;
	}

	public int getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(int idTrabajador) {
		this.idTrabajador = idTrabajador;
	}
	
	

}
