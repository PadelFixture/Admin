package lib.classSW;


import java.math.BigDecimal;
import java.util.ArrayList;

public class LibroRemuneraciones {
 public int codTrabajador, idContrato, idConcepto, periodo,articulo;
 public String nombre, apellidoPaterno, apellidoMaterno,rut,cargo,huerto,ceco,faena,tipoCuenta,fechaPago,nombreAFP, sociedad, denominacionSociedad;
 public String nombreSalud, nombreAPV, nombreAhorro,nombreCECO, nombreTipoTrabajador;
 public BigDecimal tipoTrabajador, sueldoBase, diasTrabajados,sueldoBase2, bonosImponibles, horaExtra, gratificacion; 
 public BigDecimal totalHaberesImponibles, bonosNoImponibles, cargaFamiliarSimple, cargaFamiliarMaternal, cargaFamiliaresRetro, totalHabNoImponible;
 public BigDecimal totalHaberes, baseTributable, AFP, salud, caja, seguroCesantiaAFCTrabajador, APV,impuestoUnico, totalDescuentosImp, anticipo;
 public BigDecimal descuentos, ahoroVoluntarioAfp, totalDescuentosNoImp, totalDescuentos, cotizacionSIS, seguroSesantiaAFC, cotizacionBasica;
 public BigDecimal SANNA, totalPago, totalLiquidoMes, cotizacionAdicional, cotizacionExtraordinaria,adicionalSalud;
 public BigDecimal MontoHorafalta, HoraFalta, MontoHoraExtra, HoraExtraJ,liquidoAgro,bonoProduccionAgro,valorFeriadoProporcional;
 public String fechaInicio_actividad,FechaTerminoContrato, causal;
 public BigDecimal adicionalTribu,costoVidaCamara;
 public BigDecimal tiemposervido,afcsueldo,sobregiro_;


 public BigDecimal getSobregiro_() {
	return sobregiro_;
}

public void setSobregiro_(BigDecimal sobregiro_) {
	this.sobregiro_ = sobregiro_;
}

public BigDecimal getAfcsueldo() {
	return afcsueldo;
}

public void setAfcsueldo(BigDecimal afcsueldo) {
	this.afcsueldo = afcsueldo;
}

public BigDecimal getTiemposervido() {
	return tiemposervido;
}

public void setTiemposervido(BigDecimal tiemposervido) {
	this.tiemposervido = tiemposervido;
}

public BigDecimal getCostoVidaCamara() {
	return costoVidaCamara;
}

public void setCostoVidaCamara(BigDecimal costoVidaCamara) {
	this.costoVidaCamara = costoVidaCamara;
}

public BigDecimal getValorFeriadoProporcional() {
	return valorFeriadoProporcional;
}

public void setValorFeriadoProporcional(BigDecimal valorFeriadoProporcional) {
	this.valorFeriadoProporcional = valorFeriadoProporcional;
}

public BigDecimal getLiquidoAgro() {
	return liquidoAgro;
}

public void setLiquidoAgro(BigDecimal liquidoAgro) {
	this.liquidoAgro = liquidoAgro;
}

public BigDecimal getBonoProduccionAgro() {
	return bonoProduccionAgro;
}

public void setBonoProduccionAgro(BigDecimal bonoProduccionAgro) {
	this.bonoProduccionAgro = bonoProduccionAgro;
}

public BigDecimal getAdicionalTribu() {
	return adicionalTribu;
}

public void setAdicionalTribu(BigDecimal adicionalTribu) {
	this.adicionalTribu = adicionalTribu;
}

public int getArticulo() {
	return articulo;
}

public void setArticulo(int articulo) {
	this.articulo = articulo;
}

public String getCausal() {
	return causal;
}

public void setCausal(String causal) {
	this.causal = causal;
}

public String getFechaInicio_actividad() {
	return fechaInicio_actividad;
}

public void setFechaInicio_actividad(String fechaInicio_actividad) {
	this.fechaInicio_actividad = fechaInicio_actividad;
}

public String getFechaTerminoContrato() {
	return FechaTerminoContrato;
}

public void setFechaTerminoContrato(String fechaTerminoContrato) {
	FechaTerminoContrato = fechaTerminoContrato;
}
public String numeroHorafalta;
 
 
 public String getNumeroHorafalta() {
	return numeroHorafalta;
}

public void setNumeroHorafalta(String numeroHorafalta) {
	this.numeroHorafalta = numeroHorafalta;
}

public BigDecimal getMontoHoraExtra() {
	return MontoHoraExtra;
}

public void setMontoHoraExtra(BigDecimal montoHoraExtra) {
	MontoHoraExtra = montoHoraExtra;
}

public BigDecimal getHoraExtraJ() {
	return HoraExtraJ;
}

public void setHoraExtraJ(BigDecimal horaExtraJ) {
	HoraExtraJ = horaExtraJ;
}

public BigDecimal getMontoHorafalta() {
	return MontoHorafalta;
}

public void setMontoHorafalta(BigDecimal montoHorafalta) {
	MontoHorafalta = montoHorafalta;
}

public BigDecimal getHoraFalta() {
	return HoraFalta;
}

public void setHoraFalta(BigDecimal horaFalta) {
	HoraFalta = horaFalta;
}

public BigDecimal getAdicionalSalud() {
	return adicionalSalud;
}

public void setAdicionalSalud(BigDecimal adicionalSalud) {
	this.adicionalSalud = adicionalSalud;
}
ArrayList<HaberDescuento> haberesImponibles;
ArrayList<HaberDescuento> haberesNoImponibles2;
ArrayList<HaberDescuento> Descuento;
ArrayList<HaberDescuento> costoempresa;

ArrayList<HaberDescuento> Descuento2;

// finiquito
ArrayList<HaberDescuento> haberesImponiblesF;
ArrayList<HaberDescuento> haberesNoImponiblesF;
ArrayList<HaberDescuento> DescuentoF;
ArrayList<HaberDescuento> costoempresaF;
 
 
 
 
 public ArrayList<HaberDescuento> getDescuento2() {
	return Descuento2;
}

public void setDescuento2(ArrayList<HaberDescuento> descuento2) {
	Descuento2 = descuento2;
}

public ArrayList<HaberDescuento> getHaberesImponiblesF() {
	return haberesImponiblesF;
}

public void setHaberesImponiblesF(ArrayList<HaberDescuento> haberesImponiblesF) {
	this.haberesImponiblesF = haberesImponiblesF;
}

public ArrayList<HaberDescuento> getHaberesNoImponiblesF() {
	return haberesNoImponiblesF;
}

public void setHaberesNoImponiblesF(ArrayList<HaberDescuento> haberesNoImponiblesF) {
	this.haberesNoImponiblesF = haberesNoImponiblesF;
}

public ArrayList<HaberDescuento> getDescuentoF() {
	return DescuentoF;
}

public void setDescuentoF(ArrayList<HaberDescuento> descuentoF) {
	DescuentoF = descuentoF;
}

public ArrayList<HaberDescuento> getCostoempresaF() {
	return costoempresaF;
}

public void setCostoempresaF(ArrayList<HaberDescuento> costoempresaF) {
	this.costoempresaF = costoempresaF;
}

public ArrayList<HaberDescuento> getCostoempresa() {
	return costoempresa;
}

public void setCostoempresa(ArrayList<HaberDescuento> costoempresa) {
	this.costoempresa = costoempresa;
}

public String getNombreTipoTrabajador() {
	return nombreTipoTrabajador;
}

public void setNombreTipoTrabajador(String nombreTipoTrabajador) {
	this.nombreTipoTrabajador = nombreTipoTrabajador;
}

public String getNombreAPV() {
	return nombreAPV;
}

public void setNombreAPV(String nombreAPV) {
	this.nombreAPV = nombreAPV;
}

public String getNombreAhorro() {
	return nombreAhorro;
}

public void setNombreAhorro(String nombreAhorro) {
	this.nombreAhorro = nombreAhorro;
}

public String getNombreCECO() {
	return nombreCECO;
}

public void setNombreCECO(String nombreCECO) {
	this.nombreCECO = nombreCECO;
}

public String getNombreSalud() {
	return nombreSalud;
}

public void setNombreSalud(String nombreSalud) {
	this.nombreSalud = nombreSalud;
}

public String getNombreAFP() {
	return nombreAFP;
}

public void setNombreAFP(String nombreAFP) {
	this.nombreAFP = nombreAFP;
}

public String getTipoCuenta() {
	return tipoCuenta;
}

public void setTipoCuenta(String tipoCuenta) {
	this.tipoCuenta = tipoCuenta;
}

public String getFechaPago() {
	return fechaPago;
}

public void setFechaPago(String fechaPago) {
	this.fechaPago = fechaPago;
}

public String getCargo() {
	return cargo;
}

public void setCargo(String cargo) {
	this.cargo = cargo;
}

public String getHuerto() {
	return huerto;
}

public void setHuerto(String huerto) {
	this.huerto = huerto;
}

public String getCeco() {
	return ceco;
}

public void setCeco(String ceco) {
	this.ceco = ceco;
}

public String getFaena() {
	return faena;
}

public void setFaena(String faena) {
	this.faena = faena;
}

public String getRut() {
	return rut;
}

public void setRut(String rut) {
	this.rut = rut;
}

public ArrayList<HaberDescuento> getDescuento() {
	return Descuento;
}

public void setDescuento(ArrayList<HaberDescuento> descuento) {
	Descuento = descuento;
}

public ArrayList<HaberDescuento> getHaberesImponibles() {
	return this.haberesImponibles;
}

public void setHaberesImponibles(ArrayList<HaberDescuento> haberesImponibles) {
	this.haberesImponibles = haberesImponibles;
}

public ArrayList<HaberDescuento> getHaberesNoImponibles2() {
	return this.haberesNoImponibles2;
}

public void setHaberesNoImponibles2(ArrayList<HaberDescuento> haberesNoImponibles) {
	this.haberesNoImponibles2 = haberesNoImponibles;
}

public void setDescuentos(ArrayList<HaberDescuento> descuentos) {
	Descuento = descuentos;
}

public String[] getDatos(){
	 String[] Datos=new String[LibroRemuneraciones.class.getFields().length];
	 
	 return Datos;
 }
 
public String getSociedad() {
	return sociedad;
}
public void setSociedad(String sociedad) {
	this.sociedad = sociedad;
}
public String getDenominacionSociedad() {
	return denominacionSociedad;
}
public void setDenominacionSociedad(String denominacionSociedad) {
	this.denominacionSociedad = denominacionSociedad;
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
public int getIdConcepto() {
	return idConcepto;
}
public void setIdConcepto(int idConcepto) {
	this.idConcepto = idConcepto;
}
public int getPeriodo() {
	return periodo;
}
public void setPeriodo(int periodo) {
	this.periodo = periodo;
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
public BigDecimal getTipoTrabajador() {
	return tipoTrabajador;
}
public void setTipoTrabajador(BigDecimal tipoTrabajador) {
	this.tipoTrabajador = tipoTrabajador;
}
public BigDecimal getSueldoBase() {
	return sueldoBase;
}
public void setSueldoBase(BigDecimal sueldoBase) {
	this.sueldoBase = sueldoBase;
}
public BigDecimal getDiasTrabajados() {
	return diasTrabajados;
}
public void setDiasTrabajados(BigDecimal diasTrabajados) {
	this.diasTrabajados = diasTrabajados;
}
public BigDecimal getSueldoBase2() {
	return sueldoBase2;
}
public void setSueldoBase2(BigDecimal sueldoBase2) {
	this.sueldoBase2 = sueldoBase2;
}
public BigDecimal getBonosImponibles() {
	return bonosImponibles;
}
public void setBonosImponibles(BigDecimal bonosImponibles) {
	this.bonosImponibles = bonosImponibles;
}
public BigDecimal getHoraExtra() {
	return horaExtra;
}
public void setHoraExtra(BigDecimal horaExtra) {
	this.horaExtra = horaExtra;
}
public BigDecimal getGratificacion() {
	return gratificacion;
}
public void setGratificacion(BigDecimal gratificacion) {
	this.gratificacion = gratificacion;
}
public BigDecimal getTotalHaberesImponibles() {
	return totalHaberesImponibles;
}
public void setTotalHaberesImponibles(BigDecimal totalHaberesImponibles) {
	this.totalHaberesImponibles = totalHaberesImponibles;
}
public BigDecimal getBonosNoImponibles() {
	return bonosNoImponibles;
}
public void setBonosNoImponibles(BigDecimal bonosNoImponibles) {
	this.bonosNoImponibles = bonosNoImponibles;
}
public BigDecimal getCargaFamiliarSimple() {
	return cargaFamiliarSimple;
}
public void setCargaFamiliarSimple(BigDecimal cargaFamiliarSimple) {
	this.cargaFamiliarSimple = cargaFamiliarSimple;
}
public BigDecimal getCargaFamiliarMaternal() {
	return cargaFamiliarMaternal;
}
public void setCargaFamiliarMaternal(BigDecimal cargaFamiliarMaternal) {
	this.cargaFamiliarMaternal = cargaFamiliarMaternal;
}
public BigDecimal getCargaFamiliaresRetro() {
	return cargaFamiliaresRetro;
}
public void setCargaFamiliaresRetro(BigDecimal cargaFamiliaresRetro) {
	this.cargaFamiliaresRetro = cargaFamiliaresRetro;
}
public BigDecimal getTotalHabNoImponible() {
	return totalHabNoImponible;
}
public void setTotalHabNoImponible(BigDecimal totalHabNoImponible) {
	this.totalHabNoImponible = totalHabNoImponible;
}
public BigDecimal getTotalHaberes() {
	return totalHaberes;
}
public void setTotalHaberes(BigDecimal totalHaberes) {
	this.totalHaberes = totalHaberes;
}
public BigDecimal getBaseTributable() {
	return baseTributable;
}
public void setBaseTributable(BigDecimal baseTributable) {
	this.baseTributable = baseTributable;
}
public BigDecimal getAFP() {
	return AFP;
}
public void setAFP(BigDecimal aFP) {
	AFP = aFP;
}
public BigDecimal getSalud() {
	return salud;
}
public void setSalud(BigDecimal salud) {
	this.salud = salud;
}
public BigDecimal getCaja() {
	return caja;
}
public void setCaja(BigDecimal caja) {
	this.caja = caja;
}
public BigDecimal getSeguroCesantiaAFCTrabajador() {
	return seguroCesantiaAFCTrabajador;
}
public void setSeguroCesantiaAFCTrabajador(BigDecimal seguroCesantiaAFCTrabajador) {
	this.seguroCesantiaAFCTrabajador = seguroCesantiaAFCTrabajador;
}
public BigDecimal getAPV() {
	return APV;
}
public void setAPV(BigDecimal aPV) {
	APV = aPV;
}
public BigDecimal getImpuestoUnico() {
	return impuestoUnico;
}
public void setImpuestoUnico(BigDecimal impuestoUnico) {
	this.impuestoUnico = impuestoUnico;
}
public BigDecimal getTotalDescuentosImp() {
	return totalDescuentosImp;
}
public void setTotalDescuentosImp(BigDecimal totalDescuentosImp) {
	this.totalDescuentosImp = totalDescuentosImp;
}
public BigDecimal getAnticipo() {
	return anticipo;
}
public void setAnticipo(BigDecimal anticipo) {
	this.anticipo = anticipo;
}
public BigDecimal getDescuentos() {
	return descuentos;
}
public void setDescuentos(BigDecimal descuentos) {
	this.descuentos = descuentos;
}
public BigDecimal getAhoroVoluntarioAfp() {
	return ahoroVoluntarioAfp;
}
public void setAhoroVoluntarioAfp(BigDecimal ahoroVoluntarioAfp) {
	this.ahoroVoluntarioAfp = ahoroVoluntarioAfp;
}
public BigDecimal getTotalDescuentosNoImp() {
	return totalDescuentosNoImp;
}
public void setTotalDescuentosNoImp(BigDecimal totalDescuentosNoImp) {
	this.totalDescuentosNoImp = totalDescuentosNoImp;
}
public BigDecimal getTotalDescuentos() {
	return totalDescuentos;
}
public void setTotalDescuentos(BigDecimal totalDescuentos) {
	this.totalDescuentos = totalDescuentos;
}
public BigDecimal getCotizacionSIS() {
	return cotizacionSIS;
}
public void setCotizacionSIS(BigDecimal cotizacionSIS) {
	this.cotizacionSIS = cotizacionSIS;
}
public BigDecimal getSeguroSesantiaAFC() {
	return seguroSesantiaAFC;
}
public void setSeguroSesantiaAFC(BigDecimal seguroSesantiaAFC) {
	this.seguroSesantiaAFC = seguroSesantiaAFC;
}
public BigDecimal getCotizacionBasica() {
	return cotizacionBasica;
}
public void setCotizacionBasica(BigDecimal cotizacionBasica) {
	this.cotizacionBasica = cotizacionBasica;
}
public BigDecimal getCotizacionAdicional() {
	return cotizacionAdicional;
}
public void setCotizacionAdicional(BigDecimal cotizacionAdicional) {
	this.cotizacionAdicional = cotizacionAdicional;
}
public BigDecimal getCotizacionExtraordinaria() {
	return cotizacionExtraordinaria;
}
public void setCotizacionExtraordinaria(BigDecimal cotizacionExtraordinaria) {
	this.cotizacionExtraordinaria = cotizacionExtraordinaria;
}
public BigDecimal getSANNA() {
	return SANNA;
}
public void setSANNA(BigDecimal sANNA) {
	SANNA = sANNA;
}
public BigDecimal getTotalPago() {
	return totalPago;
}
public void setTotalPago(BigDecimal totalPago) {
	this.totalPago = totalPago;
}
public BigDecimal getTotalLiquidoMes() {
	return totalLiquidoMes;
}
public void setTotalLiquidoMes(BigDecimal totalLiquidoMes) {
	this.totalLiquidoMes = totalLiquidoMes;
}
 
}
