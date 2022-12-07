package lib.classSW;

import java.util.ArrayList;

public class DatosLiquidacion {
	
	//DetalleLiquidacion
	
	private String DIAS_TRAJADOS;
	private String DIAS_TRAJADOS_FECHA_INICIO;
	private String DIAS_TRAJADOS_FECHA_FIN;
	private String GRATIFICACION;
	private String UTM;
	private String UF;
	private String TIPO_TRABAJADOR;
	private String SALUD;
	private String SALUD_NOMBRE;
	private String SALUD_PORCENTAJE;
	private String SALUD_UF;
	private String SALUD_PORCENTAJE_ISAPRE;
	private String TOPE_IMPONIBLE;
	private String TOPE_SEGURO;
	private String SUELDO_BASE_CONTRATO;
	private String SUELDO_BASE;
	private String BONO_PRODUCCION_AGRO;
	private String GRATIFICACION_PARA_CALCULAR;
	private String TOTAL_HAB_IMPONIBLE;
	private String TOTAL_HABERES;
	private String BASE_TRIBUTABLE;
	private String AFP;
	private String AFP_NOMBRE;
	private String AFP_PORCENTAJE;
	private String CAJA;
	private String CAJA_PORCENTAJE;
	private String TOTAL_DESCUENTOS_IMP;
	private String TOTAL_DESCUENTOS;
	private String COTIZACION_SIS;
	private String SEGURO_CESANTIA_AFC_EMPLEADOR;
	private String SEGURO_CESANTIA_AFC_TRABAJADOR;
	private String TOTAL_PAGO;
	private String TOTAL_LIQUIDO_MES;
	private String AHORRO_VOLUNTARIO_AFP;
	private String TOTAL_DESCUENTOS_NO_IMP;
	private String COTIZACION_BASICA;
	private String COTIZACION_ADICIONAL;
	private String COTIZACION_EXTRAORDINARIA;
	private String SANNA;
	private String BONOS_NO_IMPONIBLES;
	private String CARGA_FAMILIAR_SIMPLE;
	private String CARGA_FAMILIAR_SIMPLE_NOMBRE;
	private String CARGA_FAMILIAR_SIMPLE_CANTIDAD;
	private String CARGA_FAMILIAR_MATERNAL;
	private String CARGA_FAMILIAR_MATERNAL_NOMBRE;
	private String CARGA_FAMILIAR_MATERNAL_CANTIDAD;
	private String CARGA_FAMILIAR_RETROACTIVO;
	private String CARGA_FAMILIAR_RETROACTIVO_NOMBRE;
	private String CARGA_FAMILIAR_RETROACTIVO_CANTIDAD;
	private String CARGA_FAMILIAR_NOMBRE;
	private String CARGA_FAMILIAR_CANTIDAD;
	private String TOTAL_HAB_NO_IMPONIBLE;
	private String APV;
	private String APV_ADICIONAL;
	private ArrayList<String> IMPUESTO_UNICO;
	private ArrayList<String> IMPUESTO_UNICO_NOMBRE;
	private ArrayList<String> ANTICIPO;
	private ArrayList<String> ANTICIPO_NOMBRE;
	private ArrayList<String> BONO;
	private ArrayList<String> BONO_NOMBRE;
	private ArrayList<String> BONO_NO_IMPONIBLE;
	private ArrayList<String> BONO_NO_IMPONIBLE_NOMBRE;
	private ArrayList<String> HORAS_EXTRA;
	private ArrayList<String> HORAS_EXTRA_NHORAS;
	private ArrayList<String> HORAS_EXTRA_NOMBRE;
	private ArrayList<String> HORAS_EXTRA_VHORAS;
	private ArrayList<String> HORAS_FALTA;
	private ArrayList<String> HORAS_FALTA_NHORAS;
	private ArrayList<String> HORAS_FALTA_NOMBRE;
	private ArrayList<String> HORAS_FALTA_VHORAS;
	private ArrayList<String> DESCUENTOS;
	private ArrayList<String> DESCUENTOS_NOMBRE;
	private ArrayList<String> CONCEPTO_8;
	private ArrayList<String> CONCEPTO_8_NOMBRE;
	private ArrayList<String> SALDO_ANTERIOR;
	private ArrayList<String> SALDO_ANTERIOR_NOMBRE;
	private ArrayList<String> PLAN_ADICIONAL_NO_TRIBUTABLE;
	private ArrayList<String> PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE;
	private ArrayList<String> PLAN_ADICIONAL_NO_TRIBUTABLE_UF;
	private ArrayList<String> PLAN_ADICIONAL_TRIBUTABLE;
	private ArrayList<String> PLAN_ADICIONAL_TRIBUTABLE_NOMBRE;
	private ArrayList<String> CONCEPTO_MEDICO;
	private ArrayList<String> CONCEPTO_MEDICO_NOMBRE;
	private ArrayList<String> SOBREGIRO;
	private ArrayList<String> SOBREGIRO_NOMBRE;
	
	
	
	//Liquidacion
	private String id_liquidacion;
	private String cod_trabajador;
	private String id_contrato;
	private String id_sociedad;
	private String periodo;
	private String fecha_pago;
	private String total_pago;
	
	
	public String getDIAS_TRAJADOS() {
		return DIAS_TRAJADOS;
	}
	public void setDIAS_TRAJADOS(String dIAS_TRAJADOS) {
		DIAS_TRAJADOS = dIAS_TRAJADOS;
	}
	public String getDIAS_TRAJADOS_FECHA_INICIO() {
		return DIAS_TRAJADOS_FECHA_INICIO;
	}
	public void setDIAS_TRAJADOS_FECHA_INICIO(String dIAS_TRAJADOS_FECHA_INICIO) {
		DIAS_TRAJADOS_FECHA_INICIO = dIAS_TRAJADOS_FECHA_INICIO;
	}
	public String getDIAS_TRAJADOS_FECHA_FIN() {
		return DIAS_TRAJADOS_FECHA_FIN;
	}
	public void setDIAS_TRAJADOS_FECHA_FIN(String dIAS_TRAJADOS_FECHA_FIN) {
		DIAS_TRAJADOS_FECHA_FIN = dIAS_TRAJADOS_FECHA_FIN;
	}
	public String getGRATIFICACION() {
		return GRATIFICACION;
	}
	public void setGRATIFICACION(String gRATIFICACION) {
		GRATIFICACION = gRATIFICACION;
	}
	public String getUTM() {
		return UTM;
	}
	public void setUTM(String uTM) {
		UTM = uTM;
	}
	public String getUF() {
		return UF;
	}
	public void setUF(String uF) {
		UF = uF;
	}
	public String getTIPO_TRABAJADOR() {
		return TIPO_TRABAJADOR;
	}
	public void setTIPO_TRABAJADOR(String tIPO_TRABAJADOR) {
		TIPO_TRABAJADOR = tIPO_TRABAJADOR;
	}
	public String getTOPE_IMPONIBLE() {
		return TOPE_IMPONIBLE;
	}
	public void setTOPE_IMPONIBLE(String tOPE_IMPONIBLE) {
		TOPE_IMPONIBLE = tOPE_IMPONIBLE;
	}
	public String getTOPE_SEGURO() {
		return TOPE_SEGURO;
	}
	public void setTOPE_SEGURO(String tOPE_SEGURO) {
		TOPE_SEGURO = tOPE_SEGURO;
	}
	public String getSUELDO_BASE_CONTRATO() {
		return SUELDO_BASE_CONTRATO;
	}
	public void setSUELDO_BASE_CONTRATO(String sUELDO_BASE_CONTRATO) {
		SUELDO_BASE_CONTRATO = sUELDO_BASE_CONTRATO;
	}
	public String getSUELDO_BASE() {
		return SUELDO_BASE;
	}
	public void setSUELDO_BASE(String sUELDO_BASE) {
		SUELDO_BASE = sUELDO_BASE;
	}
	public String getGRATIFICACION_PARA_CALCULAR() {
		return GRATIFICACION_PARA_CALCULAR;
	}
	public void setGRATIFICACION_PARA_CALCULAR(String gRATIFICACION_PARA_CALCULAR) {
		GRATIFICACION_PARA_CALCULAR = gRATIFICACION_PARA_CALCULAR;
	}
	public String getTOTAL_HAB_IMPONIBLE() {
		return TOTAL_HAB_IMPONIBLE;
	}
	public void setTOTAL_HAB_IMPONIBLE(String tOTAL_HAB_IMPONIBLE) {
		TOTAL_HAB_IMPONIBLE = tOTAL_HAB_IMPONIBLE;
	}
	public String getTOTAL_HABERES() {
		return TOTAL_HABERES;
	}
	public void setTOTAL_HABERES(String tOTAL_HABERES) {
		TOTAL_HABERES = tOTAL_HABERES;
	}
	public String getBASE_TRIBUTABLE() {
		return BASE_TRIBUTABLE;
	}
	public void setBASE_TRIBUTABLE(String bASE_TRIBUTABLE) {
		BASE_TRIBUTABLE = bASE_TRIBUTABLE;
	}
	public String getCAJA() {
		return CAJA;
	}
	public void setCAJA(String cAJA) {
		CAJA = cAJA;
	}
	public String getTOTAL_DESCUENTOS_IMP() {
		return TOTAL_DESCUENTOS_IMP;
	}
	public void setTOTAL_DESCUENTOS_IMP(String tOTAL_DESCUENTOS_IMP) {
		TOTAL_DESCUENTOS_IMP = tOTAL_DESCUENTOS_IMP;
	}
	public String getTOTAL_DESCUENTOS() {
		return TOTAL_DESCUENTOS;
	}
	public void setTOTAL_DESCUENTOS(String tOTAL_DESCUENTOS) {
		TOTAL_DESCUENTOS = tOTAL_DESCUENTOS;
	}
	public String getCOTIZACION_SIS() {
		return COTIZACION_SIS;
	}
	public void setCOTIZACION_SIS(String cOTIZACION_SIS) {
		COTIZACION_SIS = cOTIZACION_SIS;
	}
	public String getTOTAL_PAGO() {
		return TOTAL_PAGO;
	}
	public void setTOTAL_PAGO(String tOTAL_PAGO) {
		TOTAL_PAGO = tOTAL_PAGO;
	}
	public String getTOTAL_LIQUIDO_MES() {
		return TOTAL_LIQUIDO_MES;
	}
	public void setTOTAL_LIQUIDO_MES(String tOTAL_LIQUIDO_MES) {
		TOTAL_LIQUIDO_MES = tOTAL_LIQUIDO_MES;
	}
	public String getId_liquidacion() {
		return id_liquidacion;
	}
	public void setId_liquidacion(String id_liquidacion) {
		this.id_liquidacion = id_liquidacion;
	}
	public String getCod_trabajador() {
		return cod_trabajador;
	}
	public void setCod_trabajador(String cod_trabajador) {
		this.cod_trabajador = cod_trabajador;
	}
	public String getId_contrato() {
		return id_contrato;
	}
	public void setId_contrato(String id_contrato) {
		this.id_contrato = id_contrato;
	}
	public String getId_sociedad() {
		return id_sociedad;
	}
	public void setId_sociedad(String id_sociedad) {
		this.id_sociedad = id_sociedad;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getFecha_pago() {
		return fecha_pago;
	}
	public void setFecha_pago(String fecha_pago) {
		this.fecha_pago = fecha_pago;
	}
	public String getTotal_pago() {
		return total_pago;
	}
	public void setTotal_pago(String total_pago) {
		this.total_pago = total_pago;
	}
	public String getBONO_PRODUCCION_AGRO() {
		return BONO_PRODUCCION_AGRO;
	}
	public void setBONO_PRODUCCION_AGRO(String bONO_PRODUCCION_AGRO) {
		BONO_PRODUCCION_AGRO = bONO_PRODUCCION_AGRO;
	}
	public String getAHORRO_VOLUNTARIO_AFP() {
		return AHORRO_VOLUNTARIO_AFP;
	}
	public void setAHORRO_VOLUNTARIO_AFP(String aHORRO_VOLUNTARIO_AFP) {
		AHORRO_VOLUNTARIO_AFP = aHORRO_VOLUNTARIO_AFP;
	}
	public String getTOTAL_DESCUENTOS_NO_IMP() {
		return TOTAL_DESCUENTOS_NO_IMP;
	}
	public void setTOTAL_DESCUENTOS_NO_IMP(String tOTAL_DESCUENTOS_NO_IMP) {
		TOTAL_DESCUENTOS_NO_IMP = tOTAL_DESCUENTOS_NO_IMP;
	}
	public String getCOTIZACION_BASICA() {
		return COTIZACION_BASICA;
	}
	public void setCOTIZACION_BASICA(String cOTIZACION_BASICA) {
		COTIZACION_BASICA = cOTIZACION_BASICA;
	}
	public String getCOTIZACION_ADICIONAL() {
		return COTIZACION_ADICIONAL;
	}
	public void setCOTIZACION_ADICIONAL(String cOTIZACION_ADICIONAL) {
		COTIZACION_ADICIONAL = cOTIZACION_ADICIONAL;
	}
	public String getCOTIZACION_EXTRAORDINARIA() {
		return COTIZACION_EXTRAORDINARIA;
	}
	public void setCOTIZACION_EXTRAORDINARIA(String cOTIZACION_EXTRAORDINARIA) {
		COTIZACION_EXTRAORDINARIA = cOTIZACION_EXTRAORDINARIA;
	}
	public String getSANNA() {
		return SANNA;
	}
	public void setSANNA(String sANNA) {
		SANNA = sANNA;
	}
	public String getBONOS_NO_IMPONIBLES() {
		return BONOS_NO_IMPONIBLES;
	}
	public void setBONOS_NO_IMPONIBLES(String bONOS_NO_IMPONIBLES) {
		BONOS_NO_IMPONIBLES = bONOS_NO_IMPONIBLES;
	}
	public String getCARGA_FAMILIAR_SIMPLE() {
		return CARGA_FAMILIAR_SIMPLE;
	}
	public void setCARGA_FAMILIAR_SIMPLE(String cARGA_FAMILIAR_SIMPLE) {
		CARGA_FAMILIAR_SIMPLE = cARGA_FAMILIAR_SIMPLE;
	}
	public String getCARGA_FAMILIAR_MATERNAL() {
		return CARGA_FAMILIAR_MATERNAL;
	}
	public void setCARGA_FAMILIAR_MATERNAL(String cARGA_FAMILIAR_MATERNAL) {
		CARGA_FAMILIAR_MATERNAL = cARGA_FAMILIAR_MATERNAL;
	}
	public String getCARGA_FAMILIAR_RETROACTIVO() {
		return CARGA_FAMILIAR_RETROACTIVO;
	}
	public void setCARGA_FAMILIAR_RETROACTIVO(String cARGA_FAMILIAR_RETROACTIVO) {
		CARGA_FAMILIAR_RETROACTIVO = cARGA_FAMILIAR_RETROACTIVO;
	}
	public String getTOTAL_HAB_NO_IMPONIBLE() {
		return TOTAL_HAB_NO_IMPONIBLE;
	}
	public void setTOTAL_HAB_NO_IMPONIBLE(String tOTAL_HAB_NO_IMPONIBLE) {
		TOTAL_HAB_NO_IMPONIBLE = tOTAL_HAB_NO_IMPONIBLE;
	}
	public String getSEGURO_CESANTIA_AFC_EMPLEADOR() {
		return SEGURO_CESANTIA_AFC_EMPLEADOR;
	}
	public void setSEGURO_CESANTIA_AFC_EMPLEADOR(String sEGURO_CESANTIA_AFC_EMPLEADOR) {
		SEGURO_CESANTIA_AFC_EMPLEADOR = sEGURO_CESANTIA_AFC_EMPLEADOR;
	}
	public String getSEGURO_CESANTIA_AFC_TRABAJADOR() {
		return SEGURO_CESANTIA_AFC_TRABAJADOR;
	}
	public void setSEGURO_CESANTIA_AFC_TRABAJADOR(String sEGURO_CESANTIA_AFC_TRABAJADOR) {
		SEGURO_CESANTIA_AFC_TRABAJADOR = sEGURO_CESANTIA_AFC_TRABAJADOR;
	}
	public String getAFP() {
		return AFP;
	}
	public void setAFP(String aFP) {
		AFP = aFP;
	}
	public String getAPV() {
		return APV;
	}
	public void setAPV(String aPV) {
		APV = aPV;
	}
	public String getSALUD() {
		return SALUD;
	}
	public void setSALUD(String sALUD) {
		SALUD = sALUD;
	}
	public String getSALUD_NOMBRE() {
		return SALUD_NOMBRE;
	}
	public void setSALUD_NOMBRE(String sALUD_NOMBRE) {
		SALUD_NOMBRE = sALUD_NOMBRE;
	}
	public String getSALUD_PORCENTAJE() {
		return SALUD_PORCENTAJE;
	}
	public void setSALUD_PORCENTAJE(String sALUD_PORCENTAJE) {
		SALUD_PORCENTAJE = sALUD_PORCENTAJE;
	}
	public String getCAJA_PORCENTAJE() {
		return CAJA_PORCENTAJE;
	}
	public void setCAJA_PORCENTAJE(String cAJA_PORCENTAJE) {
		CAJA_PORCENTAJE = cAJA_PORCENTAJE;
	}
	public String getAFP_NOMBRE() {
		return AFP_NOMBRE;
	}
	public void setAFP_NOMBRE(String aFP_NOMBRE) {
		AFP_NOMBRE = aFP_NOMBRE;
	}
	public String getAFP_PORCENTAJE() {
		return AFP_PORCENTAJE;
	}
	public void setAFP_PORCENTAJE(String aFP_PORCENTAJE) {
		AFP_PORCENTAJE = aFP_PORCENTAJE;
	}
	public String getCARGA_FAMILIAR_NOMBRE() {
		return CARGA_FAMILIAR_NOMBRE;
	}
	public void setCARGA_FAMILIAR_NOMBRE(String cARGA_FAMILIAR_NOMBRE) {
		CARGA_FAMILIAR_NOMBRE = cARGA_FAMILIAR_NOMBRE;
	}
	public String getCARGA_FAMILIAR_CANTIDAD() {
		return CARGA_FAMILIAR_CANTIDAD;
	}
	public void setCARGA_FAMILIAR_CANTIDAD(String cARGA_FAMILIAR_CANTIDAD) {
		CARGA_FAMILIAR_CANTIDAD = cARGA_FAMILIAR_CANTIDAD;
	}
	public ArrayList<String> getBONO() {
		return BONO;
	}
	public void setBONO(ArrayList<String> bONO) {
		BONO = bONO;
	}
	public ArrayList<String> getBONO_NOMBRE() {
		return BONO_NOMBRE;
	}
	public void setBONO_NOMBRE(ArrayList<String> bONO_NOMBRE) {
		BONO_NOMBRE = bONO_NOMBRE;
	}
	public ArrayList<String> getBONO_NO_IMPONIBLE() {
		return BONO_NO_IMPONIBLE;
	}
	public void setBONO_NO_IMPONIBLE(ArrayList<String> bONO_NO_IMPONIBLE) {
		BONO_NO_IMPONIBLE = bONO_NO_IMPONIBLE;
	}
	public ArrayList<String> getBONO_NO_IMPONIBLE_NOMBRE() {
		return BONO_NO_IMPONIBLE_NOMBRE;
	}
	public void setBONO_NO_IMPONIBLE_NOMBRE(ArrayList<String> bONO_NO_IMPONIBLE_NOMBRE) {
		BONO_NO_IMPONIBLE_NOMBRE = bONO_NO_IMPONIBLE_NOMBRE;
	}
	public ArrayList<String> getHORAS_EXTRA() {
		return HORAS_EXTRA;
	}
	public void setHORAS_EXTRA(ArrayList<String> hORAS_EXTRA) {
		HORAS_EXTRA = hORAS_EXTRA;
	}
	public ArrayList<String> getHORAS_FALTA() {
		return HORAS_FALTA;
	}
	public void setHORAS_FALTA(ArrayList<String> hORAS_FALTA) {
		HORAS_FALTA = hORAS_FALTA;
	}
	public ArrayList<String> getHORAS_EXTRA_NHORAS() {
		return HORAS_EXTRA_NHORAS;
	}
	public void setHORAS_EXTRA_NHORAS(ArrayList<String> hORAS_EXTRA_NHORAS) {
		HORAS_EXTRA_NHORAS = hORAS_EXTRA_NHORAS;
	}
	public ArrayList<String> getHORAS_FALTA_NHORAS() {
		return HORAS_FALTA_NHORAS;
	}
	public void setHORAS_FALTA_NHORAS(ArrayList<String> hORAS_FALTA_NHORAS) {
		HORAS_FALTA_NHORAS = hORAS_FALTA_NHORAS;
	}
	public ArrayList<String> getHORAS_EXTRA_NOMBRE() {
		return HORAS_EXTRA_NOMBRE;
	}
	public void setHORAS_EXTRA_NOMBRE(ArrayList<String> hORAS_EXTRA_NOMBRE) {
		HORAS_EXTRA_NOMBRE = hORAS_EXTRA_NOMBRE;
	}
	public ArrayList<String> getHORAS_FALTA_NOMBRE() {
		return HORAS_FALTA_NOMBRE;
	}
	public void setHORAS_FALTA_NOMBRE(ArrayList<String> hORAS_FALTA_NOMBRE) {
		HORAS_FALTA_NOMBRE = hORAS_FALTA_NOMBRE;
	}
	public ArrayList<String> getANTICIPO() {
		return ANTICIPO;
	}
	public void setANTICIPO(ArrayList<String> aNTICIPO) {
		ANTICIPO = aNTICIPO;
	}
	public ArrayList<String> getANTICIPO_NOMBRE() {
		return ANTICIPO_NOMBRE;
	}
	public void setANTICIPO_NOMBRE(ArrayList<String> aNTICIPO_NOMBRE) {
		ANTICIPO_NOMBRE = aNTICIPO_NOMBRE;
	}
	public ArrayList<String> getIMPUESTO_UNICO() {
		return IMPUESTO_UNICO;
	}
	public void setIMPUESTO_UNICO(ArrayList<String> iMPUESTO_UNICO) {
		IMPUESTO_UNICO = iMPUESTO_UNICO;
	}
	public ArrayList<String> getIMPUESTO_UNICO_NOMBRE() {
		return IMPUESTO_UNICO_NOMBRE;
	}
	public void setIMPUESTO_UNICO_NOMBRE(ArrayList<String> iMPUESTO_UNICO_NOMBRE) {
		IMPUESTO_UNICO_NOMBRE = iMPUESTO_UNICO_NOMBRE;
	}
	public ArrayList<String> getDESCUENTOS() {
		return DESCUENTOS;
	}
	public void setDESCUENTOS(ArrayList<String> dESCUENTOS) {
		DESCUENTOS = dESCUENTOS;
	}
	public ArrayList<String> getDESCUENTOS_NOMBRE() {
		return DESCUENTOS_NOMBRE;
	}
	public void setDESCUENTOS_NOMBRE(ArrayList<String> dESCUENTOS_NOMBRE) {
		DESCUENTOS_NOMBRE = dESCUENTOS_NOMBRE;
	}
	public ArrayList<String> getCONCEPTO_8() {
		return CONCEPTO_8;
	}
	public void setCONCEPTO_8(ArrayList<String> cONCEPTO_8) {
		CONCEPTO_8 = cONCEPTO_8;
	}
	public ArrayList<String> getCONCEPTO_8_NOMBRE() {
		return CONCEPTO_8_NOMBRE;
	}
	public void setCONCEPTO_8_NOMBRE(ArrayList<String> cONCEPTO_8_NOMBRE) {
		CONCEPTO_8_NOMBRE = cONCEPTO_8_NOMBRE;
	}
	public String getAPV_ADICIONAL() {
		return APV_ADICIONAL;
	}
	public void setAPV_ADICIONAL(String aPV_ADICIONAL) {
		APV_ADICIONAL = aPV_ADICIONAL;
	}
	public ArrayList<String> getSALDO_ANTERIOR() {
		return SALDO_ANTERIOR;
	}
	public void setSALDO_ANTERIOR(ArrayList<String> sALDO_ANTERIOR) {
		SALDO_ANTERIOR = sALDO_ANTERIOR;
	}
	public ArrayList<String> getSALDO_ANTERIOR_NOMBRE() {
		return SALDO_ANTERIOR_NOMBRE;
	}
	public void setSALDO_ANTERIOR_NOMBRE(ArrayList<String> sALDO_ANTERIOR_NOMBRE) {
		SALDO_ANTERIOR_NOMBRE = sALDO_ANTERIOR_NOMBRE;
	}
	public ArrayList<String> getPLAN_ADICIONAL_NO_TRIBUTABLE() {
		return PLAN_ADICIONAL_NO_TRIBUTABLE;
	}
	public void setPLAN_ADICIONAL_NO_TRIBUTABLE(ArrayList<String> pLAN_ADICIONAL_NO_TRIBUTABLE) {
		PLAN_ADICIONAL_NO_TRIBUTABLE = pLAN_ADICIONAL_NO_TRIBUTABLE;
	}
	public ArrayList<String> getPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE() {
		return PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE;
	}
	public void setPLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE(ArrayList<String> pLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE) {
		PLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE = pLAN_ADICIONAL_NO_TRIBUTABLE_NOMBRE;
	}
	public ArrayList<String> getPLAN_ADICIONAL_TRIBUTABLE() {
		return PLAN_ADICIONAL_TRIBUTABLE;
	}
	public void setPLAN_ADICIONAL_TRIBUTABLE(ArrayList<String> pLAN_ADICIONAL_TRIBUTABLE) {
		PLAN_ADICIONAL_TRIBUTABLE = pLAN_ADICIONAL_TRIBUTABLE;
	}
	public ArrayList<String> getPLAN_ADICIONAL_TRIBUTABLE_NOMBRE() {
		return PLAN_ADICIONAL_TRIBUTABLE_NOMBRE;
	}
	public void setPLAN_ADICIONAL_TRIBUTABLE_NOMBRE(ArrayList<String> pLAN_ADICIONAL_TRIBUTABLE_NOMBRE) {
		PLAN_ADICIONAL_TRIBUTABLE_NOMBRE = pLAN_ADICIONAL_TRIBUTABLE_NOMBRE;
	}
	public ArrayList<String> getCONCEPTO_MEDICO() {
		return CONCEPTO_MEDICO;
	}
	public void setCONCEPTO_MEDICO(ArrayList<String> cONCEPTO_MEDICO) {
		CONCEPTO_MEDICO = cONCEPTO_MEDICO;
	}
	public ArrayList<String> getCONCEPTO_MEDICO_NOMBRE() {
		return CONCEPTO_MEDICO_NOMBRE;
	}
	public void setCONCEPTO_MEDICO_NOMBRE(ArrayList<String> cONCEPTO_MEDICO_NOMBRE) {
		CONCEPTO_MEDICO_NOMBRE = cONCEPTO_MEDICO_NOMBRE;
	}
	public ArrayList<String> getSOBREGIRO() {
		return SOBREGIRO;
	}
	public void setSOBREGIRO(ArrayList<String> sOBREGIRO) {
		SOBREGIRO = sOBREGIRO;
	}
	public ArrayList<String> getSOBREGIRO_NOMBRE() {
		return SOBREGIRO_NOMBRE;
	}
	public void setSOBREGIRO_NOMBRE(ArrayList<String> sOBREGIRO_NOMBRE) {
		SOBREGIRO_NOMBRE = sOBREGIRO_NOMBRE;
	}
	public String getCARGA_FAMILIAR_SIMPLE_NOMBRE() {
		return CARGA_FAMILIAR_SIMPLE_NOMBRE;
	}
	public void setCARGA_FAMILIAR_SIMPLE_NOMBRE(String cARGA_FAMILIAR_SIMPLE_NOMBRE) {
		CARGA_FAMILIAR_SIMPLE_NOMBRE = cARGA_FAMILIAR_SIMPLE_NOMBRE;
	}
	public String getCARGA_FAMILIAR_SIMPLE_CANTIDAD() {
		return CARGA_FAMILIAR_SIMPLE_CANTIDAD;
	}
	public void setCARGA_FAMILIAR_SIMPLE_CANTIDAD(String cARGA_FAMILIAR_SIMPLE_CANTIDAD) {
		CARGA_FAMILIAR_SIMPLE_CANTIDAD = cARGA_FAMILIAR_SIMPLE_CANTIDAD;
	}
	public String getCARGA_FAMILIAR_MATERNAL_NOMBRE() {
		return CARGA_FAMILIAR_MATERNAL_NOMBRE;
	}
	public void setCARGA_FAMILIAR_MATERNAL_NOMBRE(String cARGA_FAMILIAR_MATERNAL_NOMBRE) {
		CARGA_FAMILIAR_MATERNAL_NOMBRE = cARGA_FAMILIAR_MATERNAL_NOMBRE;
	}
	public String getCARGA_FAMILIAR_MATERNAL_CANTIDAD() {
		return CARGA_FAMILIAR_MATERNAL_CANTIDAD;
	}
	public void setCARGA_FAMILIAR_MATERNAL_CANTIDAD(String cARGA_FAMILIAR_MATERNAL_CANTIDAD) {
		CARGA_FAMILIAR_MATERNAL_CANTIDAD = cARGA_FAMILIAR_MATERNAL_CANTIDAD;
	}
	public String getCARGA_FAMILIAR_RETROACTIVO_NOMBRE() {
		return CARGA_FAMILIAR_RETROACTIVO_NOMBRE;
	}
	public void setCARGA_FAMILIAR_RETROACTIVO_NOMBRE(String cARGA_FAMILIAR_RETROACTIVO_NOMBRE) {
		CARGA_FAMILIAR_RETROACTIVO_NOMBRE = cARGA_FAMILIAR_RETROACTIVO_NOMBRE;
	}
	public String getCARGA_FAMILIAR_RETROACTIVO_CANTIDAD() {
		return CARGA_FAMILIAR_RETROACTIVO_CANTIDAD;
	}
	public void setCARGA_FAMILIAR_RETROACTIVO_CANTIDAD(String cARGA_FAMILIAR_RETROACTIVO_CANTIDAD) {
		CARGA_FAMILIAR_RETROACTIVO_CANTIDAD = cARGA_FAMILIAR_RETROACTIVO_CANTIDAD;
	}
	public String getSALUD_UF() {
		return SALUD_UF;
	}
	public void setSALUD_UF(String sALUD_UF) {
		SALUD_UF = sALUD_UF;
	}
	public ArrayList<String> getPLAN_ADICIONAL_NO_TRIBUTABLE_UF() {
		return PLAN_ADICIONAL_NO_TRIBUTABLE_UF;
	}
	public void setPLAN_ADICIONAL_NO_TRIBUTABLE_UF(ArrayList<String> pLAN_ADICIONAL_NO_TRIBUTABLE_UF) {
		PLAN_ADICIONAL_NO_TRIBUTABLE_UF = pLAN_ADICIONAL_NO_TRIBUTABLE_UF;
	}
	public String getSALUD_PORCENTAJE_ISAPRE() {
		return SALUD_PORCENTAJE_ISAPRE;
	}
	public void setSALUD_PORCENTAJE_ISAPRE(String sALUD_PORCENTAJE_ISAPRE) {
		SALUD_PORCENTAJE_ISAPRE = sALUD_PORCENTAJE_ISAPRE;
	}
	public ArrayList<String> getHORAS_EXTRA_VHORAS() {
		return HORAS_EXTRA_VHORAS;
	}
	public void setHORAS_EXTRA_VHORAS(ArrayList<String> hORAS_EXTRA_VHORAS) {
		HORAS_EXTRA_VHORAS = hORAS_EXTRA_VHORAS;
	}
	public ArrayList<String> getHORAS_FALTA_VHORAS() {
		return HORAS_FALTA_VHORAS;
	}
	public void setHORAS_FALTA_VHORAS(ArrayList<String> hORAS_FALTA_VHORAS) {
		HORAS_FALTA_VHORAS = hORAS_FALTA_VHORAS;
	}
	
	
	
	

	
}
