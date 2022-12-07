package lib.classSW;

public class Previred {
	//Datos para utilizar en logica
	private Integer codTrabajador;
	private int idContrato;
	
	//Datos trabajador
	private String rutTrabajador;
	private String dvTrabajador;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private String sexo;
	private String nacionalidad;
	private String tipoPago;
	private String periodoDesde;
	private String periodoHasta;
	private String regimenPrevisional;
	private String tipoTrabajador;
	private String diasTrabajados;
	private String tipoLinea;
	private String codigoMovimientoPersonal;
	private String fechaDesde;
	private String fechaHasta;
	private String tramoAsignacionFamiliar;
	private String nCargasSimples;
	private String nCargasMaternales;
	private String nCargasInvalidas;
	private String asignacionFamiliar;
	private String asignacionFamiliarRetroactiva;
	private String reintegroCargasFamiliares;
	private String solicitudTrabajadorJoven;
	private int isapreFonasa;

	//Datos AFP
	private String codigoAfp;
	private String rentaImponibleAfp;
	private String cotizacionObligatoriaAfp;
	private String sueldo_afc;
	private String cotizacionSeguroInvalidezSobrevivencia;
	private String cuentaAhorroVoluntarioAfp;
	private String rentaImponibleSustAfp;
	private String tasaPactadaSustit;
	private String aporteIndemnSustit;
	private String nPeriodosSustit;
	private String preiodoDesdeSustit;
	private String periosoHastaSustit;
	private String puestoTrabajoPesado;
	private String porcCotizacionTrabajoPesado;
	private String cotizacionTrabajoPesado;

	//Datos Ahorro previsional voluntario individual
	private String codigoInstitucionApvi;
	private String numeroContratoApvi;
	private String formaPagoApvi;
	private String cotizacionApvi;
	private String cotizacionDepositosConvenidos;

	//Datos ahorro previsional colectivo
	private String codigoIntitucionAutorizadaApvc;
	private String numeroContratoApvc;
	private String formaPagoApvc;
	private String cotizacionTrabajadorApvc;
	private String cotizacionEmpleadorApvc;

	//Datos Afiliado voluntario
	private String rutAfiliadoVoluntario;
	private String dvAfiliadoVoluntario;
	private String apellidoMaternoAfiliado;
	private String apellidoPaternoAfiliado;
	private String nombresAfiliado;
	private String codigoMovimientoPersonalAfiliado;
	private String fechaDesdeAfililado;
	private String fechaHastaAfiliado;
	private String codigoAfpAfiliado;
	private String montoCapitalizacionVoluntaria;
	private String montoAhorroVoluntarioAfiliado;
	private String numPeriodosCotizacionAfiliado;

	//Datos IPS - ISL - FONASA
	private String codigoExCajaRegimen;
	private String tasaCotizacionExCajaRegimen;
	private String rentaImponibleIPS;
	private String cotizacionObligatoriaIPS;
	private String rentaImponibleDesahucion;
	private String codigoExCajaRegimenDesahucion;
	private String tasaCotDeshaExCajaPrevision;
	private String cotizacionDesahucion;
	private String cotizacionFonasa;
	private String cotizacionAccidenteTrabajoISL;
	private String bonificacionLey15386;
	private String descuentoCargasFamiliaresISL;
	private String bonoGobierno;

	//Datos salud
	private String codigoInstitucionSalud;
	private String numeroFUN;
	private String rentaImponibleIsapre;
	private String mondedaPlanPactadoIsapre;
	private String cotizacionPactada;
	private String cotizacionObligatoriaIsapre;
	private String cotizacionAdicionalVoluntaria;
	private String montoGarantiaGES;

	//Datos caja compensacion
	private String codigoCCAF;
	private String rentaImponibleCCAF;
	private String creditosPersonalesCCAF;
	private String descuentoDentalCCAF;
	private String descuentosLeasing;
	private String descuentosSeguroVidaCCAF;
	private String otrosDescuentosCCAF;
	private String cotizacionCCAFNoAfilIsapres;
	private String descCargasFamiliaresCCAF;
	private String otrosDescuentosCCAF1;
	private String otrosDescuentosCCAF2;
	private String bonoGobiernoCCAF;
	private String codigoSucursalCCAF;

	//Datos Mutualidad
	private String codigoMutualidad;
	private String reantaImponibleMutualidad;
	private String cotizacionAccidenteTrabajo;
	private String sucursalPagoMutual;

	//Datos administradora seguro cesantia
	private String rentaImponibleSeguroCesantia;
	private String aporteTrabajadorSeguroCesantia;
	private String aporteEmpleadorSeguroCesantia;

	//Datos Pagador subsidios
	private String rutPagadoraSubsidio;
	private String dvPagadoraSubsidio;
	
	//Datos Pagador mutual en caso de licencia de mutualidad
	private String rutPagadoraSubsidioMutual;
	private String dvPagadoraSubsidioMutual;
	
	private int codmov;
	private String topeImL;
	private String topeseguro;
	private String imponibleL;
	private String baseAFC;
	private String basesanna;
	private String idafp;
	private String valorsss;
	private String codigosssExcaja;
	public int pensionadocotizante;
    public int pensionado;
    public int id_mutual;
    public int id_cajamutal;
    
	
	
	public String getRutPagadoraSubsidioMutual() {
		return rutPagadoraSubsidioMutual;
	}

	public void setRutPagadoraSubsidioMutual(String rutPagadoraSubsidioMutual) {
		this.rutPagadoraSubsidioMutual = rutPagadoraSubsidioMutual;
	}

	public String getDvPagadoraSubsidioMutual() {
		return dvPagadoraSubsidioMutual;
	}

	public void setDvPagadoraSubsidioMutual(String dvPagadoraSubsidioMutual) {
		this.dvPagadoraSubsidioMutual = dvPagadoraSubsidioMutual;
	}

	public int getId_cajamutal() {
		return id_cajamutal;
	}

	public void setId_cajamutal(int id_cajamutal) {
		this.id_cajamutal = id_cajamutal;
	}

	public int getId_mutual() {
		return id_mutual;
	}

	public void setId_mutual(int id_mutual) {
		this.id_mutual = id_mutual;
	}

	public int getPensionado() {
		return pensionado;
	}

	public void setPensionado(int pensionado) {
		this.pensionado = pensionado;
	}

	public String getBasesanna() {
		return basesanna;
	}

	public void setBasesanna(String basesanna) {
		this.basesanna = basesanna;
	}

	public int getPensionadocotizante() {
		return pensionadocotizante;
	}

	public void setPensionadocotizante(int pensionadocotizante) {
		this.pensionadocotizante = pensionadocotizante;
	}

	public String getBaseAFC() {
		return baseAFC;
	}

	public void setBaseAFC(String baseAFC) {
		this.baseAFC = baseAFC;
	}

	public String getTopeseguro() {
		return topeseguro;
	}

	public void setTopeseguro(String topeseguro) {
		this.topeseguro = topeseguro;
	}

	public String getCodigosssExcaja() {
		return codigosssExcaja;
	}

	public void setCodigosssExcaja(String codigosssExcaja) {
		this.codigosssExcaja = codigosssExcaja;
	}

	public String getIdafp() {
		return idafp;
	}

	public void setIdafp(String idafp) {
		this.idafp = idafp;
	}

	public String getValorsss() {
		return valorsss;
	}

	public void setValorsss(String valorsss) {
		this.valorsss = valorsss;
	}

	public String getTopeImL() {
		return topeImL;
	}

	public void setTopeImL(String topeImL) {
		this.topeImL = topeImL;
	}

	public String getImponibleL() {
		return imponibleL;
	}

	public void setImponibleL(String imponibleL) {
		this.imponibleL = imponibleL;
	}

	public int getIsapreFonasa() {
		return isapreFonasa;
	}

	public void setIsapreFonasa(int isapreFonasa) {
		this.isapreFonasa = isapreFonasa;
	}

	public int getCodmov() {
		return codmov;
	}

	public void setCodmov(int codmov) {
		this.codmov = codmov;
	}

	//Otros Datos empresa
	private String centroCostoSucAgencia;
	
	
	

	public Previred() {
		this.rutTrabajador = "0";
		this.dvTrabajador = "";
		this.apellidoPaterno = "";
		this.apellidoMaterno = "";
		this.nombres = "";
		this.sexo = "";
		this.nacionalidad = "0";
		this.tipoPago = "01";
		this.periodoDesde = "0";
		this.periodoHasta = "0";
		this.regimenPrevisional = "";
		this.tipoTrabajador = "0";
		this.diasTrabajados = "0";
		this.tipoLinea = "";
		this.codigoMovimientoPersonal = "0";
		this.fechaDesde = "";
		this.fechaHasta = "";
		this.tramoAsignacionFamiliar = "D";
		this.nCargasSimples = "0";
		this.nCargasMaternales = "0";
		this.nCargasInvalidas = "0";
		this.asignacionFamiliar = "0";
		this.asignacionFamiliarRetroactiva = "0";
		this.reintegroCargasFamiliares = "0";
		this.solicitudTrabajadorJoven = "N";
		this.codigoAfp = "00";
		this.rentaImponibleAfp = "0";
		this.cotizacionObligatoriaAfp = "0";
		this.cotizacionSeguroInvalidezSobrevivencia = "0";
		this.cuentaAhorroVoluntarioAfp = "0";
		this.rentaImponibleSustAfp = "0";
		this.tasaPactadaSustit = "0";
		this.aporteIndemnSustit = "0";
		this.nPeriodosSustit = "0";
		this.preiodoDesdeSustit = "";
		this.periosoHastaSustit = "";
		this.puestoTrabajoPesado = "";
		this.porcCotizacionTrabajoPesado = "0";
		this.cotizacionTrabajoPesado = "0";
		this.codigoInstitucionApvi = "000";
		this.numeroContratoApvi = "";
		this.formaPagoApvi = "1";
		this.cotizacionApvi = "0";
		this.cotizacionDepositosConvenidos = "0";
		this.codigoIntitucionAutorizadaApvc = "0";
		this.numeroContratoApvc = "";
		this.formaPagoApvc = "0";
		this.cotizacionTrabajadorApvc = "0";
		this.cotizacionEmpleadorApvc = "0";
		this.rutAfiliadoVoluntario = "0";
		this.dvAfiliadoVoluntario = "";
		this.apellidoMaternoAfiliado = "";
		this.apellidoPaternoAfiliado = "";
		this.nombresAfiliado = "";
		this.codigoMovimientoPersonalAfiliado = "0";
		this.fechaDesdeAfililado = "";
		this.fechaHastaAfiliado = "";
		this.codigoAfpAfiliado = "0";
		this.montoCapitalizacionVoluntaria = "0";
		this.montoAhorroVoluntarioAfiliado = "0";
		this.numPeriodosCotizacionAfiliado = "0";
		this.codigoExCajaRegimen = "0";
		this.tasaCotizacionExCajaRegimen = "0";
		this.rentaImponibleIPS = "0";
		this.cotizacionObligatoriaIPS = "0";
		this.rentaImponibleDesahucion = "0";
		this.codigoExCajaRegimenDesahucion = "0";
		this.tasaCotDeshaExCajaPrevision = "0";
		this.cotizacionDesahucion = "0";
		this.cotizacionFonasa = "0";
		this.cotizacionAccidenteTrabajoISL = "0";
		this.bonificacionLey15386 = "0";
		this.descuentoCargasFamiliaresISL = "0";
		this.bonoGobierno = "0";
		this.codigoInstitucionSalud = "00";
		this.numeroFUN = "";
		this.rentaImponibleIsapre = "0";
		this.mondedaPlanPactadoIsapre = "0";
		this.cotizacionPactada = "0";
		this.cotizacionObligatoriaIsapre = "0";
		this.cotizacionAdicionalVoluntaria = "0";
		this.montoGarantiaGES = "0";
		this.codigoCCAF = "0";
		this.rentaImponibleCCAF = "0";
		this.creditosPersonalesCCAF = "0";
		this.descuentoDentalCCAF = "0";
		this.descuentosLeasing = "0";
		this.descuentosSeguroVidaCCAF = "0";
		this.otrosDescuentosCCAF = "0";
		this.cotizacionCCAFNoAfilIsapres = "0";
		this.descCargasFamiliaresCCAF = "0";
		this.otrosDescuentosCCAF1 = "0";
		this.otrosDescuentosCCAF2 = "0";
		this.bonoGobiernoCCAF = "0";
		this.codigoSucursalCCAF = "";
		this.codigoMutualidad = "0";
		this.reantaImponibleMutualidad = "0";
		this.cotizacionAccidenteTrabajo = "0";
		this.sucursalPagoMutual = "0";
		this.rentaImponibleSeguroCesantia = "0";
		this.aporteTrabajadorSeguroCesantia = "0";
		this.aporteEmpleadorSeguroCesantia = "0";
		this.rutPagadoraSubsidio = "0";
		this.dvPagadoraSubsidio = "";
		this.centroCostoSucAgencia = "";
		this.idafp = "0";
		this.valorsss = "0";
		this.codigosssExcaja = "0";
		this.baseAFC = "0";
		this.pensionadocotizante = 0;
		this.basesanna ="0";
		this.sueldo_afc = "0";
		this.pensionado = 0;
		this.id_mutual = 0;
		this.id_cajamutal = 0;
		this.rutPagadoraSubsidioMutual = "";
		this.dvPagadoraSubsidioMutual = "";
	}

	public String getSueldo_afc() {
		return sueldo_afc;
	}

	public void setSueldo_afc(String sueldo_afc) {
		this.sueldo_afc = sueldo_afc;
	}

	public Integer getCodTrabajador() {
		return codTrabajador;
	}

	public void setCodTrabajador(Integer codTrabajador) {
		this.codTrabajador = codTrabajador;
	}

	public int getIdContrato() {
		return idContrato;
	}


	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}


	public String getRutTrabajador() {
		return rutTrabajador;
	}

	public void setRutTrabajador(String rutTrabajador) {
		this.rutTrabajador = rutTrabajador;
	}

	public String getDvTrabajador() {
		return dvTrabajador;
	}

	public void setDvTrabajador(String dvTrabajador) {
		this.dvTrabajador = dvTrabajador;
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

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public String getPeriodoDesde() {
		return periodoDesde;
	}

	public void setPeriodoDesde(String periodoDesde) {
		this.periodoDesde = periodoDesde;
	}

	public String getPeriodoHasta() {
		return periodoHasta;
	}

	public void setPeriodoHasta(String periodoHasta) {
		this.periodoHasta = periodoHasta;
	}

	public String getRegimenPrevisional() {
		return regimenPrevisional;
	}

	public void setRegimenPrevisional(String regimenPrevisional) {
		this.regimenPrevisional = regimenPrevisional;
	}

	public String getTipoTrabajador() {
		return tipoTrabajador;
	}

	public void setTipoTrabajador(String tipoTrabajador) {
		this.tipoTrabajador = tipoTrabajador;
	}

	public String getDiasTrabajados() {
		return diasTrabajados;
	}

	public void setDiasTrabajados(String diasTrabajados) {
		this.diasTrabajados = diasTrabajados;
	}

	public String getTipoLinea() {
		return tipoLinea;
	}

	public void setTipoLinea(String tipoLinea) {
		this.tipoLinea = tipoLinea;
	}

	public String getCodigoMovimientoPersonal() {
		return codigoMovimientoPersonal;
	}

	public void setCodigoMovimientoPersonal(String codigoMovimientoPersonal) {
		this.codigoMovimientoPersonal = codigoMovimientoPersonal;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getTramoAsignacionFamiliar() {
		return tramoAsignacionFamiliar;
	}

	public void setTramoAsignacionFamiliar(String tramoAsignacionFamiliar) {
		this.tramoAsignacionFamiliar = tramoAsignacionFamiliar;
	}

	public String getnCargasSimples() {
		return nCargasSimples;
	}

	public void setnCargasSimples(String nCargasSimples) {
		this.nCargasSimples = nCargasSimples;
	}

	public String getnCargasMaternales() {
		return nCargasMaternales;
	}

	public void setnCargasMaternales(String nCargasMaternales) {
		this.nCargasMaternales = nCargasMaternales;
	}

	public String getnCargasInvalidas() {
		return nCargasInvalidas;
	}

	public void setnCargasInvalidas(String nCargasInvalidas) {
		this.nCargasInvalidas = nCargasInvalidas;
	}

	public String getAsignacionFamiliar() {
		return asignacionFamiliar;
	}

	public void setAsignacionFamiliar(String asignacionFamiliar) {
		this.asignacionFamiliar = asignacionFamiliar;
	}

	public String getAsignacionFamiliarRetroactiva() {
		return asignacionFamiliarRetroactiva;
	}

	public void setAsignacionFamiliarRetroactiva(String asignacionFamiliarRetroactiva) {
		this.asignacionFamiliarRetroactiva = asignacionFamiliarRetroactiva;
	}

	public String getReintegroCargasFamiliares() {
		return reintegroCargasFamiliares;
	}

	public void setReintegroCargasFamiliares(String reintegroCargasFamiliares) {
		this.reintegroCargasFamiliares = reintegroCargasFamiliares;
	}

	public String getSolicitudTrabajadorJoven() {
		return solicitudTrabajadorJoven;
	}

	public void setSolicitudTrabajadorJoven(String solicitudTrabajadorJoven) {
		this.solicitudTrabajadorJoven = solicitudTrabajadorJoven;
	}

	public String getCodigoAfp() {
		return codigoAfp;
	}

	public void setCodigoAfp(String codigoAfp) {
		this.codigoAfp = codigoAfp;
	}

	public String getRentaImponibleAfp() {
		return rentaImponibleAfp;
	}

	public void setRentaImponibleAfp(String rentaImponibleAfp) {
		this.rentaImponibleAfp = rentaImponibleAfp;
	}

	public String getCotizacionObligatoriaAfp() {
		return cotizacionObligatoriaAfp;
	}

	public void setCotizacionObligatoriaAfp(String cotizacionObligatoriaAfp) {
		this.cotizacionObligatoriaAfp = cotizacionObligatoriaAfp;
	}

	public String getCotizacionSeguroInvalidezSobrevivencia() {
		return cotizacionSeguroInvalidezSobrevivencia;
	}

	public void setCotizacionSeguroInvalidezSobrevivencia(String cotizacionSeguroInvalidezSobrevivencia) {
		this.cotizacionSeguroInvalidezSobrevivencia = cotizacionSeguroInvalidezSobrevivencia;
	}

	public String getCuentaAhorroVoluntarioAfp() {
		return cuentaAhorroVoluntarioAfp;
	}

	public void setCuentaAhorroVoluntarioAfp(String cuentaAhorroVoluntarioAfp) {
		this.cuentaAhorroVoluntarioAfp = cuentaAhorroVoluntarioAfp;
	}

	public String getRentaImponibleSustAfp() {
		return rentaImponibleSustAfp;
	}

	public void setRentaImponibleSustAfp(String rentaImponibleSustAfp) {
		this.rentaImponibleSustAfp = rentaImponibleSustAfp;
	}

	public String getTasaPactadaSustit() {
		return tasaPactadaSustit;
	}

	public void setTasaPactadaSustit(String tasaPactadaSustit) {
		this.tasaPactadaSustit = tasaPactadaSustit;
	}

	public String getAporteIndemnSustit() {
		return aporteIndemnSustit;
	}

	public void setAporteIndemnSustit(String aporteIndemnSustit) {
		this.aporteIndemnSustit = aporteIndemnSustit;
	}

	public String getnPeriodosSustit() {
		return nPeriodosSustit;
	}

	public void setnPeriodosSustit(String nPeriodosSustit) {
		this.nPeriodosSustit = nPeriodosSustit;
	}

	public String getPreiodoDesdeSustit() {
		return preiodoDesdeSustit;
	}

	public void setPreiodoDesdeSustit(String preiodoDesdeSustit) {
		this.preiodoDesdeSustit = preiodoDesdeSustit;
	}

	public String getPeriosoHastaSustit() {
		return periosoHastaSustit;
	}

	public void setPeriosoHastaSustit(String periosoHastaSustit) {
		this.periosoHastaSustit = periosoHastaSustit;
	}

	public String getPuestoTrabajoPesado() {
		return puestoTrabajoPesado;
	}

	public void setPuestoTrabajoPesado(String puestoTrabajoPesado) {
		this.puestoTrabajoPesado = puestoTrabajoPesado;
	}

	public String getPorcCotizacionTrabajoPesado() {
		return porcCotizacionTrabajoPesado;
	}

	public void setPorcCotizacionTrabajoPesado(String porcCotizacionTrabajoPesado) {
		this.porcCotizacionTrabajoPesado = porcCotizacionTrabajoPesado;
	}

	public String getCotizacionTrabajoPesado() {
		return cotizacionTrabajoPesado;
	}

	public void setCotizacionTrabajoPesado(String cotizacionTrabajoPesado) {
		this.cotizacionTrabajoPesado = cotizacionTrabajoPesado;
	}

	public String getCodigoInstitucionApvi() {
		return codigoInstitucionApvi;
	}

	public void setCodigoInstitucionApvi(String codigoInstitucionApvi) {
		this.codigoInstitucionApvi = codigoInstitucionApvi;
	}

	public String getNumeroContratoApvi() {
		return numeroContratoApvi;
	}

	public void setNumeroContratoApvi(String numeroContratoApvi) {
		this.numeroContratoApvi = numeroContratoApvi;
	}

	public String getFormaPagoApvi() {
		return formaPagoApvi;
	}

	public void setFormaPagoApvi(String formaPagoApvi) {
		this.formaPagoApvi = formaPagoApvi;
	}

	public String getCotizacionApvi() {
		return cotizacionApvi;
	}

	public void setCotizacionApvi(String cotizacionApvi) {
		this.cotizacionApvi = cotizacionApvi;
	}

	public String getCotizacionDepositosConvenidos() {
		return cotizacionDepositosConvenidos;
	}

	public void setCotizacionDepositosConvenidos(String cotizacionDepositosConvenidos) {
		this.cotizacionDepositosConvenidos = cotizacionDepositosConvenidos;
	}

	public String getCodigoIntitucionAutorizadaApvc() {
		return codigoIntitucionAutorizadaApvc;
	}

	public void setCodigoIntitucionAutorizadaApvc(String codigoIntitucionAutorizadaApvc) {
		this.codigoIntitucionAutorizadaApvc = codigoIntitucionAutorizadaApvc;
	}

	public String getNumeroContratoApvc() {
		return numeroContratoApvc;
	}

	public void setNumeroContratoApvc(String numeroContratoApvc) {
		this.numeroContratoApvc = numeroContratoApvc;
	}

	public String getFormaPagoApvc() {
		return formaPagoApvc;
	}

	public void setFormaPagoApvc(String formaPagoApvc) {
		this.formaPagoApvc = formaPagoApvc;
	}

	public String getCotizacionTrabajadorApvc() {
		return cotizacionTrabajadorApvc;
	}

	public void setCotizacionTrabajadorApvc(String cotizacionTrabajadorApvc) {
		this.cotizacionTrabajadorApvc = cotizacionTrabajadorApvc;
	}

	public String getCotizacionEmpleadorApvc() {
		return cotizacionEmpleadorApvc;
	}

	public void setCotizacionEmpleadorApvc(String cotizacionEmpleadorApvc) {
		this.cotizacionEmpleadorApvc = cotizacionEmpleadorApvc;
	}

	public String getRutAfiliadoVoluntario() {
		return rutAfiliadoVoluntario;
	}

	public void setRutAfiliadoVoluntario(String rutAfiliadoVoluntario) {
		this.rutAfiliadoVoluntario = rutAfiliadoVoluntario;
	}

	public String getDvAfiliadoVoluntario() {
		return dvAfiliadoVoluntario;
	}

	public void setDvAfiliadoVoluntario(String dvAfiliadoVoluntario) {
		this.dvAfiliadoVoluntario = dvAfiliadoVoluntario;
	}

	public String getApellidoMaternoAfiliado() {
		return apellidoMaternoAfiliado;
	}

	public void setApellidoMaternoAfiliado(String apellidoMaternoAfiliado) {
		this.apellidoMaternoAfiliado = apellidoMaternoAfiliado;
	}

	public String getApellidoPaternoAfiliado() {
		return apellidoPaternoAfiliado;
	}

	public void setApellidoPaternoAfiliado(String apellidoPaternoAfiliado) {
		this.apellidoPaternoAfiliado = apellidoPaternoAfiliado;
	}

	public String getNombresAfiliado() {
		return nombresAfiliado;
	}

	public void setNombresAfiliado(String nombresAfiliado) {
		this.nombresAfiliado = nombresAfiliado;
	}

	public String getCodigoMovimientoPersonalAfiliado() {
		return codigoMovimientoPersonalAfiliado;
	}

	public void setCodigoMovimientoPersonalAfiliado(String codigoMovimientoPersonalAfiliado) {
		this.codigoMovimientoPersonalAfiliado = codigoMovimientoPersonalAfiliado;
	}

	public String getFechaDesdeAfililado() {
		return fechaDesdeAfililado;
	}

	public void setFechaDesdeAfililado(String fechaDesdeAfililado) {
		this.fechaDesdeAfililado = fechaDesdeAfililado;
	}

	public String getFechaHastaAfiliado() {
		return fechaHastaAfiliado;
	}

	public void setFechaHastaAfiliado(String fechaHastaAfiliado) {
		this.fechaHastaAfiliado = fechaHastaAfiliado;
	}

	public String getCodigoAfpAfiliado() {
		return codigoAfpAfiliado;
	}

	public void setCodigoAfpAfiliado(String codigoAfpAfiliado) {
		this.codigoAfpAfiliado = codigoAfpAfiliado;
	}

	public String getMontoCapitalizacionVoluntaria() {
		return montoCapitalizacionVoluntaria;
	}

	public void setMontoCapitalizacionVoluntaria(String montoCapitalizacionVoluntaria) {
		this.montoCapitalizacionVoluntaria = montoCapitalizacionVoluntaria;
	}

	public String getMontoAhorroVoluntarioAfiliado() {
		return montoAhorroVoluntarioAfiliado;
	}

	public void setMontoAhorroVoluntarioAfiliado(String montoAhorroVoluntarioAfiliado) {
		this.montoAhorroVoluntarioAfiliado = montoAhorroVoluntarioAfiliado;
	}

	public String getNumPeriodosCotizacionAfiliado() {
		return numPeriodosCotizacionAfiliado;
	}

	public void setNumPeriodosCotizacionAfiliado(String numPeriodosCotizacionAfiliado) {
		this.numPeriodosCotizacionAfiliado = numPeriodosCotizacionAfiliado;
	}

	public String getCodigoExCajaRegimen() {
		return codigoExCajaRegimen;
	}

	public void setCodigoExCajaRegimen(String codigoExCajaRegimen) {
		this.codigoExCajaRegimen = codigoExCajaRegimen;
	}

	public String getTasaCotizacionExCajaRegimen() {
		return tasaCotizacionExCajaRegimen;
	}

	public void setTasaCotizacionExCajaRegimen(String tasaCotizacionExCajaRegimen) {
		this.tasaCotizacionExCajaRegimen = tasaCotizacionExCajaRegimen;
	}

	public String getRentaImponibleIPS() {
		return rentaImponibleIPS;
	}

	public void setRentaImponibleIPS(String rentaImponibleIPS) {
		this.rentaImponibleIPS = rentaImponibleIPS;
	}

	public String getCotizacionObligatoriaIPS() {
		return cotizacionObligatoriaIPS;
	}

	public void setCotizacionObligatoriaIPS(String cotizacionObligatoriaIPS) {
		this.cotizacionObligatoriaIPS = cotizacionObligatoriaIPS;
	}

	public String getRentaImponibleDesahucion() {
		return rentaImponibleDesahucion;
	}

	public void setRentaImponibleDesahucion(String rentaImponibleDesahucion) {
		this.rentaImponibleDesahucion = rentaImponibleDesahucion;
	}

	public String getCodigoExCajaRegimenDesahucion() {
		return codigoExCajaRegimenDesahucion;
	}

	public void setCodigoExCajaRegimenDesahucion(String codigoExCajaRegimenDesahucion) {
		this.codigoExCajaRegimenDesahucion = codigoExCajaRegimenDesahucion;
	}

	public String getTasaCotDeshaExCajaPrevision() {
		return tasaCotDeshaExCajaPrevision;
	}

	public void setTasaCotDeshaExCajaPrevision(String tasaCotDeshaExCajaPrevision) {
		this.tasaCotDeshaExCajaPrevision = tasaCotDeshaExCajaPrevision;
	}

	public String getCotizacionDesahucion() {
		return cotizacionDesahucion;
	}

	public void setCotizacionDesahucion(String cotizacionDesahucion) {
		this.cotizacionDesahucion = cotizacionDesahucion;
	}

	public String getCotizacionFonasa() {
		return cotizacionFonasa;
	}

	public void setCotizacionFonasa(String cotizacionFonasa) {
		this.cotizacionFonasa = cotizacionFonasa;
	}

	public String getCotizacionAccidenteTrabajoISL() {
		return cotizacionAccidenteTrabajoISL;
	}

	public void setCotizacionAccidenteTrabajoISL(String cotizacionAccidenteTrabajoISL) {
		this.cotizacionAccidenteTrabajoISL = cotizacionAccidenteTrabajoISL;
	}

	public String getBonificacionLey15386() {
		return bonificacionLey15386;
	}

	public void setBonificacionLey15386(String bonificacionLey15386) {
		this.bonificacionLey15386 = bonificacionLey15386;
	}

	public String getDescuentoCargasFamiliaresISL() {
		return descuentoCargasFamiliaresISL;
	}

	public void setDescuentoCargasFamiliaresISL(String descuentoCargasFamiliaresISL) {
		this.descuentoCargasFamiliaresISL = descuentoCargasFamiliaresISL;
	}

	public String getBonoGobierno() {
		return bonoGobierno;
	}

	public void setBonoGobierno(String bonoGobierno) {
		this.bonoGobierno = bonoGobierno;
	}

	public String getCodigoInstitucionSalud() {
		return codigoInstitucionSalud;
	}

	public void setCodigoInstitucionSalud(String codigoInstitucionSalud) {
		this.codigoInstitucionSalud = codigoInstitucionSalud;
	}

	public String getNumeroFUN() {
		return numeroFUN;
	}

	public void setNumeroFUN(String numeroFUN) {
		this.numeroFUN = numeroFUN;
	}

	public String getRentaImponibleIsapre() {
		return rentaImponibleIsapre;
	}

	public void setRentaImponibleIsapre(String rentaImponibleIsapre) {
		this.rentaImponibleIsapre = rentaImponibleIsapre;
	}

	public String getMondedaPlanPactadoIsapre() {
		return mondedaPlanPactadoIsapre;
	}

	public void setMondedaPlanPactadoIsapre(String mondedaPlanPactadoIsapre) {
		this.mondedaPlanPactadoIsapre = mondedaPlanPactadoIsapre;
	}

	public String getCotizacionPactada() {
		return cotizacionPactada;
	}

	public void setCotizacionPactada(String cotizacionPactada) {
		this.cotizacionPactada = cotizacionPactada;
	}

	public String getCotizacionObligatoriaIsapre() {
		return cotizacionObligatoriaIsapre;
	}

	public void setCotizacionObligatoriaIsapre(String cotizacionObligatoriaIsapre) {
		this.cotizacionObligatoriaIsapre = cotizacionObligatoriaIsapre;
	}

	public String getCotizacionAdicionalVoluntaria() {
		return cotizacionAdicionalVoluntaria;
	}

	public void setCotizacionAdicionalVoluntaria(String cotizacionAdicionalVoluntaria) {
		this.cotizacionAdicionalVoluntaria = cotizacionAdicionalVoluntaria;
	}

	public String getMontoGarantiaGES() {
		return montoGarantiaGES;
	}

	public void setMontoGarantiaGES(String montoGarantiaGES) {
		this.montoGarantiaGES = montoGarantiaGES;
	}

	public String getCodigoCCAF() {
		return codigoCCAF;
	}

	public void setCodigoCCAF(String codigoCCAF) {
		this.codigoCCAF = codigoCCAF;
	}

	public String getRentaImponibleCCAF() {
		return rentaImponibleCCAF;
	}

	public void setRentaImponibleCCAF(String rentaImponibleCCAF) {
		this.rentaImponibleCCAF = rentaImponibleCCAF;
	}

	public String getCreditosPersonalesCCAF() {
		return creditosPersonalesCCAF;
	}

	public void setCreditosPersonalesCCAF(String creditosPersonalesCCAF) {
		this.creditosPersonalesCCAF = creditosPersonalesCCAF;
	}

	public String getDescuentoDentalCCAF() {
		return descuentoDentalCCAF;
	}

	public void setDescuentoDentalCCAF(String descuentoDentalCCAF) {
		this.descuentoDentalCCAF = descuentoDentalCCAF;
	}

	public String getDescuentosLeasing() {
		return descuentosLeasing;
	}

	public void setDescuentosLeasing(String descuentosLeasing) {
		this.descuentosLeasing = descuentosLeasing;
	}

	public String getDescuentosSeguroVidaCCAF() {
		return descuentosSeguroVidaCCAF;
	}

	public void setDescuentosSeguroVidaCCAF(String descuentosSeguroVidaCCAF) {
		this.descuentosSeguroVidaCCAF = descuentosSeguroVidaCCAF;
	}

	public String getOtrosDescuentosCCAF() {
		return otrosDescuentosCCAF;
	}

	public void setOtrosDescuentosCCAF(String otrosDescuentosCCAF) {
		this.otrosDescuentosCCAF = otrosDescuentosCCAF;
	}

	public String getCotizacionCCAFNoAfilIsapres() {
		return cotizacionCCAFNoAfilIsapres;
	}

	public void setCotizacionCCAFNoAfilIsapres(String cotizacionCCAFNoAfilIsapres) {
		this.cotizacionCCAFNoAfilIsapres = cotizacionCCAFNoAfilIsapres;
	}

	public String getDescCargasFamiliaresCCAF() {
		return descCargasFamiliaresCCAF;
	}

	public void setDescCargasFamiliaresCCAF(String descCargasFamiliaresCCAF) {
		this.descCargasFamiliaresCCAF = descCargasFamiliaresCCAF;
	}

	public String getOtrosDescuentosCCAF1() {
		return otrosDescuentosCCAF1;
	}

	public void setOtrosDescuentosCCAF1(String otrosDescuentosCCAF1) {
		this.otrosDescuentosCCAF1 = otrosDescuentosCCAF1;
	}

	public String getOtrosDescuentosCCAF2() {
		return otrosDescuentosCCAF2;
	}

	public void setOtrosDescuentosCCAF2(String otrosDescuentosCCAF2) {
		this.otrosDescuentosCCAF2 = otrosDescuentosCCAF2;
	}

	public String getBonoGobiernoCCAF() {
		return bonoGobiernoCCAF;
	}

	public void setBonoGobiernoCCAF(String bonoGobiernoCCAF) {
		this.bonoGobiernoCCAF = bonoGobiernoCCAF;
	}

	public String getCodigoSucursalCCAF() {
		return codigoSucursalCCAF;
	}

	public void setCodigoSucursalCCAF(String codigoSucursalCCAF) {
		this.codigoSucursalCCAF = codigoSucursalCCAF;
	}

	public String getCodigoMutualidad() {
		return codigoMutualidad;
	}

	public void setCodigoMutualidad(String codigoMutualidad) {
		this.codigoMutualidad = codigoMutualidad;
	}

	public String getReantaImponibleMutualidad() {
		return reantaImponibleMutualidad;
	}

	public void setReantaImponibleMutualidad(String reantaImponibleMutualidad) {
		this.reantaImponibleMutualidad = reantaImponibleMutualidad;
	}

	public String getCotizacionAccidenteTrabajo() {
		return cotizacionAccidenteTrabajo;
	}

	public void setCotizacionAccidenteTrabajo(String cotizacionAccidenteTrabajo) {
		this.cotizacionAccidenteTrabajo = cotizacionAccidenteTrabajo;
	}

	public String getSucursalPagoMutual() {
		return sucursalPagoMutual;
	}

	public void setSucursalPagoMutual(String sucursalPagoMutual) {
		this.sucursalPagoMutual = sucursalPagoMutual;
	}

	public String getRentaImponibleSeguroCesantia() {
		return rentaImponibleSeguroCesantia;
	}

	public void setRentaImponibleSeguroCesantia(String rentaImponibleSeguroCesantia) {
		this.rentaImponibleSeguroCesantia = rentaImponibleSeguroCesantia;
	}

	public String getAporteTrabajadorSeguroCesantia() {
		return aporteTrabajadorSeguroCesantia;
	}

	public void setAporteTrabajadorSeguroCesantia(String aporteTrabajadorSeguroCesantia) {
		this.aporteTrabajadorSeguroCesantia = aporteTrabajadorSeguroCesantia;
	}

	public String getAporteEmpleadorSeguroCesantia() {
		return aporteEmpleadorSeguroCesantia;
	}

	public void setAporteEmpleadorSeguroCesantia(String aporteEmpleadorSeguroCesantia) {
		this.aporteEmpleadorSeguroCesantia = aporteEmpleadorSeguroCesantia;
	}

	public String getRutPagadoraSubsidio() {
		return rutPagadoraSubsidio;
	}

	public void setRutPagadoraSubsidio(String rutPagadoraSubsidio) {
		this.rutPagadoraSubsidio = rutPagadoraSubsidio;
	}

	public String getDvPagadoraSubsidio() {
		return dvPagadoraSubsidio;
	}

	public void setDvPagadoraSubsidio(String dvPagadoraSubsidio) {
		this.dvPagadoraSubsidio = dvPagadoraSubsidio;
	}

	public String getCentroCostoSucAgencia() {
		return centroCostoSucAgencia;
	}

	public void setCentroCostoSucAgencia(String centroCostoSucAgencia) {
		this.centroCostoSucAgencia = centroCostoSucAgencia;
	}	

}
