package lib.db.sw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lib.classSW.LiquidacionPeriodo;
import lib.classSW.Previred;
import lib.db.ConexionBD;

public class PreviredDB {
	
private final static Logger LOG = LoggerFactory.getLogger(PreviredDB.class);
	
	public static Connection db;
	
	public List<Previred> getDatosPreviredContratados(String periodo, String empresa,String codTrab, String idContrato, int repetido) throws Exception {
		
		String anio = periodo.substring(0,4);
		String mes = periodo.substring(4,6);
		String dia = "01";
	    String periodoFinal = anio+"-"+mes+"-"+dia;
	    String periodoFinHasta = mes+anio;
	    
		PreparedStatement ps = null;
		String sql="";
		List<Previred> lista = new ArrayList<>();
		try{
			
			String s1= periodo;    
	        String substr2 = s1.substring(0,4); 
	        String substr3 = s1.substring(4); 
	        String periodoFormat = substr2+"-"+substr3;
	        
	         
	        
				sql = "select t.codigo as codTrabajador, cntt.id as idContrato, "
					     /*01-02*/+ "case when t.rut = '' then t.rutTemporal else t.rut end  as rutTrabajador,"
					    	/*03*/+ "t.apellidoPaterno as apellidoPaterno,"
					    	/*04*/+ "t.apellidoMaterno as apellidoMaterno,"
					    	/*05*/+ "t.nombre as nombres,"
					    	/*06*/+ "sexo.codPrevired as sexo,"
					    	/*07*/+ "nac.codPrevired as nacionalidad,"
					    	+ "(select idCajaCompensacion from sociedad where idSociedad = "+empresa+") as idcajamutal,"
					    	+ "(select codPrevired from parametros where codigo = 'MUTUALES' and llave = (select idMutual from sociedad WHERE idSociedad = "+empresa+")) as codigomutual,"
					    	/*08*//*tipo de pago*/  /*------"01"-------------por defecto----------------------*/
					    	/*09*//*periodo desde*/ /*periodo que recibo por la funcion*/
							/*10*//*periodo hasta*/ /*periodo que recibo por la funcion*/
					    	/*11*/+ "CASE WHEN t.idAFP = 7 THEN 'INP' WHEN t.idAFP = 0 THEN 'SIP' ELSE 'AFP' END regimenPrevisional,"
					    	/*12*/+ "t.pensionados,t.pensionadosCotizantes," /* con estos 2 saco el tipo de trabajador */
					       	/*13*//* dias trabajados      */  /* dato obtenido de call sw_createLiquidacion()
					     	/*14*//* tipo de linea  	  */ /*------"00"-------------por defecto----------------------*/
					    	/*15*//* codigo movimiento  	*/ /*-------"00----------------------*/
					    	/*16*//* fecha desde  	
					    	/*17*//* fecha hasta  		*/
					    	/*18*//* tramo familiar 		*/
					        /*19*//* cargas simples  	    */  /* tabla sw_familiar  valor 2
					    	/*20*//* cargas maternales    */  /* tabla sw_familiar  valor 4
					    	/*21*//* cargas Invalidas     */  /* tabla sw_familiar  valor 3
					    	/*22*//* asig. familiar       */  /* monto en asignacion familiar calculo */
					    	/*23*/+"(SELECT sum(monto) as monto from sw_familiarRetroActivo where codTrabajador = "+codTrab+" and periodo = "+periodo+") as montoRetroactivo," /* preguntar a junan carlos en que tabla lo guarda"
					    	/*24*/ +"(select sum(CAST(monto AS SIGNED)) as monto from sw_haberesDescuentos where codigo_hd = 3029 and codigo_trabajador = "+codTrab+" and periodo = "+periodo+" group by codigo_trabajador,periodo) as montoReintegro ,"  
					    	/*25*/ + "t.trabajadorJoven,"
					    	+" (select codPrevired from sw_m_afps  where idParametro = 7 and idAFP = (SELECT MAX(idAFP) FROM sw_m_afps where idParametro = 7)) as ssss,t.idAFP, "
					        /*26*/+"afp.codPrevired as codigoAfp, t.valorPlan as cotizacionPactada,"
					        /*27-36*/
					        /*37*//*NO APLICA*/
					        /*38*//*NO APLICA*/
					        /*39*//*NO APLICA*/
					        /*40*//*codigo de la institucion APV*/
					        /*41*/
					        /*42*//*Forma de pago APVI*//*quedara por defecto en valor "1"*/
					     /*43-44*/
					     /*45 ya esta por el lado de liquidacion 
					      *46 -61*//*NO APLICA*/
					     /*62*/+"(select codPrevired from parametros where codigo = 'AFP' and llave = 7) as codigosssExcaja, "
					     /* -68*/
			     	        /*69*//*NO APLICA*/
					     /*70-71*/
					        /*72*//*NO APLICA*/
					        /*73*/
					        /*74*//*NO APLICA*/
					        /*75*/
					        /*76*/ //+"t.numeroContrato "
					     /*77-81*/
					        /*82*//*NO APLICA*/
					    /*83*/ +"(select codPrevired from parametros where codigo  = 'CAJA_COMPENSACION' and llave = (select idCajaCompensacion from sociedad where idSociedad = "+empresa+"))as codigoCCAF, "
                        /*85*/ +"(select sum(CAST(monto AS SIGNED)) as monto from sw_haberesDescuentos where codigo_hd = 3032 and codigo_trabajador = "+codTrab+" and periodo = "+periodo+" and idContrato = "+idContrato+" group by codigo_trabajador,periodo) as creditosCCAF, "
					    /*103*/
                        
                        
                        
                        
                        +"if(t.idIsapre = 13,(select  substring_index(REPLACE(rutParametro, '.', ''), '-', 1) as rut from parametros where codigo = 'CAJA_COMPENSACION' and llave = 2 )  , "
                        +"(select  substring_index(REPLACE(rutParametro, '.', ''), '-', 1) as rut from parametros where codigo = 'ISAPRE' and llave = t.idIsapre  ) )"
                        +" as rutPagadora,"
                        
                        +"if(t.idIsapre = 13 ,(select  substring_index(REPLACE(rutParametro, '.', ''), '-', -1) as rut from parametros where codigo = 'CAJA_COMPENSACION' and llave = 2 ),"
                        +"(select  substring_index(REPLACE(rutParametro, '.', ''), '-', -1) as rut from parametros where codigo = 'ISAPRE' and llave = t.idIsapre ) ) "   
                        		
					    
					    /*104*/+" as dvPagadora,"
					    
					    +"(select  substring_index(REPLACE(rutParametro, '.', ''), '-', 1) as rut from parametros where codigo = 'MUTUALES' and llave = (select idMutual from sociedad where idSociedad = "+empresa+")) as rutmutual, " 
					    +"(select  substring_index(REPLACE(rutParametro, '.', ''), '-', -1) as rut from parametros where codigo = 'MUTUALES' and llave = (select idMutual from sociedad where idSociedad = "+empresa+")) as dvrutmutual, " 

					    
                               +"(select ca.centro_costo_previred from campo ca inner join contratos co on ca.campo = co.idHuertoContrato where co.codigo_trabajador = "+codTrab+" and co.id = "+idContrato+") as centro_costo_previred,"
					    
					    /* -105*/ /*aplican todos*/
                        +"t.idIsapre,isapre.codPrevired as codigoInstitucionSalud "
					               +"from contratos cntt "
					               + "inner join trabajadores t on cntt.codigo_trabajador = t.codigo "
					               + "inner join parametros afp on afp.codigo = 'AFP' and afp.llave = t.idAFP "
					               + "inner join parametros sexo on sexo.codigo = 'SEXO' and sexo.llave = t.idGenero "
					               + "inner join parametros nac on nac.codigo = 'NACIONALIDAD' and nac.llave = t.idNacionalidad "
					               + "inner join parametros isapre on isapre.codigo = 'ISAPRE' and isapre.llave = t.idIsapre "
					               + "where cntt.idSociedad = "+empresa+" "
					               + "and t.codigo = "+codTrab+" "
					               + "and cntt.id = "+idContrato+"";
		        System.out.println("//////////////mutual");
				System.out.println(sql);
				db = ConexionBD.getConnection();
				ps = db.prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);
				
				while(rs.next()){
					Previred p = new Previred();
					 p.setCodTrabajador(rs.getInt("codTrabajador"));
					 p.setIdContrato(rs.getInt("idContrato"));
					String rut = rs.getString("rutTrabajador");
					String[] rutSplit = rut.replace(".", "").split("-");
					/*01*/ p.setRutTrabajador(rutSplit[0]);
					/*02*/ p.setDvTrabajador(rutSplit[1]);
					/*03*/ p.setApellidoPaterno(rs.getString("apellidoPaterno"));
					/*04*/ p.setApellidoMaterno(rs.getString("apellidoMaterno"));
					/*05*/ p.setNombres(rs.getString("nombres"));
					/*06*/ p.setSexo(rs.getString("sexo"));
					/*07*/ p.setNacionalidad(rs.getString("nacionalidad"));
					/*08*/ p.setTipoPago("1");
					/*09*/ p.setPeriodoDesde(periodoFinHasta);
					/*10*/ p.setPeriodoHasta(periodoFinHasta);
					/*11*/ p.setRegimenPrevisional(rs.getString("regimenPrevisional"));
					/*12*/ 
					p.setPensionadocotizante(rs.getInt("pensionadosCotizantes"));
					
					if(rs.getInt("pensionados") == 0 && rs.getInt("pensionadosCotizantes") == 0 ){
						p.setTipoTrabajador("0");
					}
					else if(rs.getInt("pensionados") == 1 && rs.getInt("pensionadosCotizantes") == 1 )
					{
						p.setTipoTrabajador("1");
					}
					else if(rs.getInt("pensionados") == 1 && rs.getInt("pensionadosCotizantes") == 0 )
					{
						p.setTipoTrabajador("2");
					}
					/*13*/ p.setDiasTrabajados("0");
					/*14*/ 
					if(repetido == 0){
						
						p.setTipoLinea("00");
					}else if(repetido == 1){
						
						p.setTipoLinea("02");
					}
					/*15*/ p.setCodigoMovimientoPersonal("00");
					/*16*/ p.setFechaDesde("");
				    /*17*/ p.setFechaHasta("");
				    /*18*/p.setTramoAsignacionFamiliar("");
					/*19*/p.setnCargasSimples("0");
					/*20*/p.setnCargasMaternales("0");
					/*21*/p.setnCargasInvalidas("0");
				    /*22*/p.setAsignacionFamiliar("0");
				    /*23*/p.setAsignacionFamiliarRetroactiva("0");
					/*24*/p.setReintegroCargasFamiliares(rs.getString("montoReintegro"));
					int numerotrabajadorJoven = rs.getInt("trabajadorJoven");
					if(numerotrabajadorJoven == 0){
						/*25*/p.setSolicitudTrabajadorJoven("N");
					}else{
					/*25*/p.setSolicitudTrabajadorJoven("S");
					}
					/*26*/p.setCodigoAfp(rs.getString("codigoAfp"));
					/*27*/p.setRentaImponibleAfp("0");
					/*28*/p.setCotizacionObligatoriaAfp("0");
					/*29*/p.setCotizacionSeguroInvalidezSobrevivencia("0");
				    /*30*/p.setCuentaAhorroVoluntarioAfp("0");
				    /*31*/p.setRentaImponibleSustAfp("0");
				    /*32*/p.setTasaPactadaSustit("0");
				    /*33*/p.setAporteIndemnSustit("0");
				    /*34*/p.setnPeriodosSustit("0");
				    /*35*/p.setPreiodoDesdeSustit("");
				    /*36*/p.setPeriosoHastaSustit("");
				    /*37*/p.setPuestoTrabajoPesado("");
				    /*38*/p.setPorcCotizacionTrabajoPesado("0");
				    /*39*/p.setCotizacionTrabajoPesado("0");
				    /*40*/p.setCodigoInstitucionApvi("0");
				    /*41*/p.setNumeroContratoApvi("");
				    /*42*/p.setFormaPagoApvi("1");
				    /*43*/p.setCotizacionApvi("0");
				    /*44*/p.setCotizacionDepositosConvenidos("0");
				    /*45*/p.setCodigoIntitucionAutorizadaApvc("0");
				    /*46*/p.setNumeroContratoApvc("");
				    /*47*/p.setFormaPagoApvc("0");
				    /*48*/p.setCotizacionTrabajadorApvc("0");
				    /*49*/p.setCotizacionEmpleadorApvc("0");
				    /*50*/p.setRutAfiliadoVoluntario("0");
				    /*51*/p.setDvAfiliadoVoluntario("");
				    /*52*/p.setApellidoPaternoAfiliado("");
				    /*53*/p.setApellidoMaternoAfiliado("");
				    /*54*/p.setNombresAfiliado("");
				    /*55*/p.setCodigoMovimientoPersonalAfiliado("00");
				    /*56*/p.setFechaDesdeAfililado("");
				    /*57*/p.setFechaHastaAfiliado("");
				    /*58*/p.setCodigoAfpAfiliado("0");
				    /*59*/p.setMontoCapitalizacionVoluntaria("0");
				    /*60*/p.setMontoAhorroVoluntarioAfiliado("0");
				    /*61*/p.setNumPeriodosCotizacionAfiliado("0");
				    /*62*/p.setCodigoExCajaRegimen("0");
				    /*63*/p.setTasaCotizacionExCajaRegimen("0");
				    /*64*/p.setRentaImponibleIPS("0");
				    /*65*/p.setCotizacionObligatoriaIPS("0");
				    /*66*/p.setRentaImponibleDesahucion("0");
				    /*67*/p.setCodigoExCajaRegimenDesahucion("0");
				    /*68*/p.setTasaCotDeshaExCajaPrevision("0");
				    /*69*/p.setCotizacionDesahucion("0");
				    /*70*/p.setCotizacionFonasa("0");
				    /*71*/p.setCotizacionAccidenteTrabajoISL("0");
				    /*72*/p.setBonificacionLey15386("0");
				    /*73*/p.setDescuentoCargasFamiliaresISL("0");
				    /*74*/p.setBonoGobierno("0");
				    /*75*/p.setCodigoInstitucionSalud(rs.getString("codigoInstitucionSalud"));
				    /*76*/p.setNumeroFUN("");
				    /*77*/p.setRentaImponibleIsapre("0");
				    /*78*/p.setMondedaPlanPactadoIsapre("0");
				    /*79*/p.setCotizacionPactada(rs.getString("cotizacionPactada"));
				    /*80*/p.setCotizacionObligatoriaIsapre("0");
				    /*81*/p.setCotizacionAdicionalVoluntaria("0");
				    /*82*/p.setMontoGarantiaGES("0");
				    /*83*/p.setCodigoCCAF(rs.getString("codigoCCAF"));
				    /*84*/p.setRentaImponibleCCAF("0");
				    /*85*/p.setCreditosPersonalesCCAF(rs.getString("creditosCCAF"));
				    /*86*/p.setDescuentoDentalCCAF("0");
				    /*87*/p.setDescuentosLeasing("0");
				    /*88*/p.setDescuentosSeguroVidaCCAF("0");
				    /*89*/p.setOtrosDescuentosCCAF("0");
				    /*90*/p.setCotizacionCCAFNoAfilIsapres("0");
				    /*91*/p.setDescCargasFamiliaresCCAF("0");
				    /*92*/p.setOtrosDescuentosCCAF1("0");
				    /*93*/p.setOtrosDescuentosCCAF2("0");
				    /*94*/p.setBonoGobiernoCCAF("0");
				    /*95*/p.setCodigoSucursalCCAF("");
				    /*96*/p.setCodigoMutualidad(rs.getString("codigomutual"));
				    /*97*/p.setReantaImponibleMutualidad("0");
				    /*98*/p.setCotizacionAccidenteTrabajo("0");
				    /*99*/p.setSucursalPagoMutual("1");
				   /*100*/p.setRentaImponibleSeguroCesantia("0");
				   /*101*/p.setAporteTrabajadorSeguroCesantia("0");
				   /*102*/p.setAporteEmpleadorSeguroCesantia("0");
				   /*103*/p.setRutPagadoraSubsidio(rs.getString("rutPagadora"));
				   /*104*/p.setDvPagadoraSubsidio(rs.getString("dvPagadora"));
				   
				    String CO_previred = rs.getString("centro_costo_previred");
					if(CO_previred == "" || CO_previred == null){
						
						/*105*/p.setCentroCostoSucAgencia("0");
					}
					else{
						/*105*/p.setCentroCostoSucAgencia(CO_previred);
					}
				   
				   
				   
				    p.setIsapreFonasa(rs.getInt("idIsapre"));
				    p.setValorsss(rs.getString("ssss"));
				    p.setIdafp(rs.getString("idAFP"));
				    p.setCodigosssExcaja(rs.getString("codigosssExcaja"));
				    p.setPensionado(rs.getInt("pensionados"));
				    p.setId_cajamutal(rs.getInt("idcajamutal"));
				    
				    p.setRutPagadoraSubsidioMutual(rs.getString("rutmutual"));
				    p.setDvPagadoraSubsidioMutual(rs.getString("dvrutmutual"));
				    
				 
					lista.add(p);
				
				}
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}
	
	public List<Previred> getDatosPreviredRetirados(String periodo) throws Exception {
		
		PreparedStatement ps = null;
		String sql="";
		List<Previred> lista = new ArrayList<>();
		try{
		    sql = "select t.codigo as codTrabajador, t.pensionados,t.pensionadosCotizantes, cntt.id as idContrato, t.rut as rutTrabajador, t.apellidoPaterno as apellidoPaterno, t.apellidoMaterno as apellidoMaterno, t.nombre as nombres, "+
		    		"sexo.codPrevired as sexo, nac.codPrevired as nacionalidad, date_format(cntt.fechaInicio_actividad, '%m%Y') as periodoDesde, "+
		    		"ifnull(date_format(cntt.FechaTerminoContrato, '%m%Y'), '0') as periodoHasta, "
		    		+ "CASE "
		    		+ "WHEN t.idAFP = 7 THEN 'INP' ELSE 'AFP' END regimenPrevisional, "+
		    		"CASE WHEN ifnull(cntt.FechaTerminoContrato, '0') != 0 THEN datediff(cntt.FechaTerminoContrato, cntt.fechaInicio_actividad) ELSE datediff(now(), cntt.fechaInicio_actividad) END diasTrabajados, "+
		    		"'2' as codigoMovimientoPersonal, ifnull(upper(fam.tramoFamiliar), 'D') as tramoAsignacionFamiliar, "+
		    		"afp.codPrevired as codigoAfp, t.valorAFP as cotizacionObligatoriaAfp, concat(0, ifnull(afp.codPrevired, '000')) as codigoInstitucionApvi, "+
		    		"t.valorDepositoAPV as cotizacionApvi, t.valorConvenido as cotizacionDepositosConvenidos, isapre.codPrevired as codigoInstitucionSalud, "+
		    		"CASE WHEN t.idMonedaPlan = 2 THEN 2 ELSE 1 END AS mondedaPlanPactadoIsapre, t.valorPlan as cotizacionPactada "+
		    		"from contratos cntt "+
		    		"inner join trabajadores t on cntt.codigo_trabajador = t.codigo "+
		    		"inner join parametros afp on afp.codigo = 'AFP' and afp.llave = t.idAFP "+
		    		"inner join parametros sexo on sexo.codigo = 'SEXO' and sexo.llave = t.idGenero "+
		    		"inner join parametros nac on nac.codigo = 'NACIONALIDAD' and nac.llave = t.idNacionalidad "+
		    		"inner join parametros isapre on isapre.codigo = 'ISAPRE' and isapre.llave = t.idIsapre "+
		    		"left join sw_familiar fam on t.id = fam.idTrabajador "+
		    		"where date_format(cntt.FechaTerminoContrato, '%Y%m') = "+ periodo +" and t.rut != '' ";
	                
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				Previred p = new Previred();
				p.setCodTrabajador(rs.getInt("codTrabajador"));
				p.setIdContrato(rs.getInt("idContrato"));
				String rut = rs.getString("rutTrabajador");
				String[] rutSplit = rut.replace(".", "").split("-");
				p.setRutTrabajador(rutSplit[0]);
				p.setDvTrabajador(rutSplit[1]);
				p.setApellidoPaterno(rs.getString("apellidoPaterno"));
				p.setApellidoMaterno(rs.getString("apellidoMaterno"));
				p.setNombres(rs.getString("nombres"));
				p.setSexo(rs.getString("sexo"));
				p.setNacionalidad(rs.getString("nacionalidad"));
				p.setPeriodoDesde(rs.getString("periodoDesde"));
				p.setPeriodoHasta(rs.getString("periodoHasta"));
				p.setRegimenPrevisional(rs.getString("regimenPrevisional"));
				p.setDiasTrabajados(rs.getString("diasTrabajados"));
				p.setCodigoMovimientoPersonal(rs.getString("codigoMovimientoPersonal"));
				p.setTramoAsignacionFamiliar(rs.getString("tramoAsignacionFamiliar"));
				p.setCodigoAfp(rs.getString("codigoAfp"));
				p.setCotizacionObligatoriaAfp(rs.getString("cotizacionObligatoriaAfp"));
				p.setCodigoInstitucionApvi(rs.getString("codigoInstitucionApvi"));
				p.setCotizacionApvi(rs.getString("cotizacionApvi"));
				p.setCotizacionDepositosConvenidos(rs.getString("cotizacionDepositosConvenidos"));
				p.setCodigoInstitucionSalud(rs.getString("codigoInstitucionSalud"));
				p.setMondedaPlanPactadoIsapre(rs.getString("mondedaPlanPactadoIsapre"));
				p.setCotizacionPactada(rs.getString("cotizacionPactada"));
				p.setTipoLinea("03");
				
				if(rs.getInt("pensionados") == 0 && rs.getInt("pensionadosCotizantes") == 0 ){
					p.setTipoTrabajador("0");
				}
				else if(rs.getInt("pensionados") == 1 && rs.getInt("pensionadosCotizantes") == 1 )
				{
					p.setTipoTrabajador("1");
				}
				else if(rs.getInt("pensionados") == 1 && rs.getInt("pensionadosCotizantes") == 0 )
				{
					p.setTipoTrabajador("2");
				}
				lista.add(p);
			}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}
	
	public List<Previred> getDatosPreviredCambioAfpIsapre(String periodo) throws Exception {
		
		PreparedStatement ps = null;
		String sql="";
		List<Previred> lista = new ArrayList<>();
		try{
		    sql = "select t.codigo as codTrabajador, t.pensionados,t.pensionadosCotizantes, cntt.id as idContrato, t.rut as rutTrabajador, t.apellidoPaterno as apellidoPaterno, t.apellidoMaterno as apellidoMaterno, t.nombre as nombres, "+
		    		"sexo.codPrevired as sexo, nac.codPrevired as nacionalidad, date_format(cntt.fechaInicio_actividad, '%m%Y') as periodoDesde, "+
		    		"ifnull(date_format(cntt.FechaTerminoContrato, '%m%Y'), '0') as periodoHasta, CASE t.idAFP WHEN t.idAFP > 0 THEN 'AFP' ELSE 'SIP' END regimenPrevisional, "+
		    		"CASE WHEN ifnull(cntt.FechaTerminoContrato, '0') != 0 THEN datediff(cntt.FechaTerminoContrato, cntt.fechaInicio_actividad) ELSE datediff(now(), cntt.fechaInicio_actividad) END diasTrabajados, "+
		    		"'2' as codigoMovimientoPersonal, ifnull(upper(fam.tramoFamiliar), 'D') as tramoAsignacionFamiliar, "+
		    		"afp.codPrevired as codigoAfp, t.valorAFP as cotizacionObligatoriaAfp, concat(0, ifnull(afp.codPrevired, '000')) as codigoInstitucionApvi, "+
		    		"t.valorDepositoAPV as cotizacionApvi, t.valorConvenido as cotizacionDepositosConvenidos, isapre.codPrevired as codigoInstitucionSalud, "+
		    		"CASE WHEN t.idMonedaPlan = 2 THEN 2 ELSE 1 END AS mondedaPlanPactadoIsapre, t.valorPlan as cotizacionPactada "+
		    		"from contratos cntt "+
		    		"inner join trabajadores t on cntt.codigo_trabajador = t.codigo "+
		    		"inner join parametros afp on afp.codigo = 'AFP' and afp.llave = t.idAFP "+
		    		"inner join parametros sexo on sexo.codigo = 'SEXO' and sexo.llave = t.idGenero "+
		    		"inner join parametros nac on nac.codigo = 'NACIONALIDAD' and nac.llave = t.idNacionalidad "+
		    		"inner join parametros isapre on isapre.codigo = 'ISAPRE' and isapre.llave = t.idIsapre "+
		    		"left join sw_familiar fam on t.id = fam.idTrabajador "+
		    		"inner join sw_r_trabajadorPeriodo mov on t.id = mov.idTrabajador"+
		    		" where date_format(mov.fechaBack, '%Y%m') = "+ periodo +" and t.rut != '' ";
			
			
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				Previred p = new Previred();
				p.setCodTrabajador(rs.getInt("codTrabajador"));
				p.setIdContrato(rs.getInt("idContrato"));
				String rut = rs.getString("rutTrabajador");
				String[] rutSplit = rut.replace(".", "").split("-");
				p.setRutTrabajador(rutSplit[0]);
				p.setDvTrabajador(rutSplit[1]);
				p.setApellidoPaterno(rs.getString("apellidoPaterno"));
				p.setApellidoMaterno(rs.getString("apellidoMaterno"));
				p.setNombres(rs.getString("nombres"));
				p.setSexo(rs.getString("sexo"));
				p.setNacionalidad(rs.getString("nacionalidad"));
				p.setPeriodoDesde(rs.getString("periodoDesde"));
				p.setPeriodoHasta(rs.getString("periodoHasta"));
				p.setRegimenPrevisional(rs.getString("regimenPrevisional"));
				p.setDiasTrabajados(rs.getString("diasTrabajados"));
				p.setCodigoMovimientoPersonal(rs.getString("codigoMovimientoPersonal"));
				p.setTramoAsignacionFamiliar(rs.getString("tramoAsignacionFamiliar"));
				p.setCodigoAfp(rs.getString("codigoAfp"));
				p.setCotizacionObligatoriaAfp(rs.getString("cotizacionObligatoriaAfp"));
				p.setCodigoInstitucionApvi(rs.getString("codigoInstitucionApvi"));
				p.setCotizacionApvi(rs.getString("cotizacionApvi"));
				p.setCotizacionDepositosConvenidos(rs.getString("cotizacionDepositosConvenidos"));
				p.setCodigoInstitucionSalud(rs.getString("codigoInstitucionSalud"));
				p.setMondedaPlanPactadoIsapre(rs.getString("mondedaPlanPactadoIsapre"));
				p.setCotizacionPactada(rs.getString("cotizacionPactada"));
				p.setTipoLinea("03");
				if(rs.getInt("pensionados") == 0 && rs.getInt("pensionadosCotizantes") == 0 ){
					p.setTipoTrabajador("0");
				}
				else if(rs.getInt("pensionados") == 1 && rs.getInt("pensionadosCotizantes") == 1 )
				{
					p.setTipoTrabajador("1");
				}
				else if(rs.getInt("pensionados") == 1 && rs.getInt("pensionadosCotizantes") == 0 )
				{
					p.setTipoTrabajador("2");
				}
				
				lista.add(p);
			}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}
	
public List<Previred> getDatosPreviredpermisoyLicencia(String periodo,String empresa,Previred datospermisolicencia) throws Exception {
	
	String anio = periodo.substring(0,4);
	String mes = periodo.substring(4,6);
	String dia = "01";
    String periodoFinal = anio+"-"+mes+"-"+dia;
    String periodoFinHasta = mes+anio;
    
    int cod = datospermisolicencia.getCodTrabajador();
    int idcont = datospermisolicencia.getIdContrato();
    
    String tipo_linea = datospermisolicencia.getTipoLinea();
    
    String n_movimiento = datospermisolicencia.getCodigoMovimientoPersonal();
    
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		String sql="";
		String sql2="";
		
		List<Previred> lista = new ArrayList<>();
		try{

		    sql = "SELECT pe.id,pe.accion,pe.tipo_licencia,pe.subtipo_licencia,t.pensionados,"
		    		+ "t.pensionadosCotizantes,t.rut as rutTrabajador,t.idCECO, "
		    		+ "t.apellidoPaterno as apellidoPaterno, t.apellidoMaterno as apellidoMaterno, "
		    		+ "t.nombre as nombres,"
		    		+ "(select codPrevired from parametros where codigo = 'SEXO' and llave = t.idGenero) "
		    		+ "as sexo,"
		    		+ "(select rutParametro from parametros where codigo = 'ISAPRE' and llave = t.idIsapre) as rutIsapre,"
		    		+ "(select codPrevired from parametros where codigo = 'NACIONALIDAD' "
		    		+ "and llave = t.idNacionalidad) as nacionalidad,"
		    		+ "(select codPrevired from parametros where codigo = "
		    		+ "'ISAPRE' and llave = t.idIsapre) as regimenPrevisional,"
		    		+ "pe.fecha_desde,pe.fecha_hasta,"
		    		+ "CASE "
		    		+ "WHEN "
		    		+ "pe.fecha_desde BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"')"
		    		+ " THEN "
		    		+ "pe.fecha_desde "
		    		+ "ELSE FIRST_DAY('"+periodoFinal+"') "
		    		+ "END AS fecha_inicio_previred, "
		    		+ "CASE "
		    		+ "WHEN (pe.fecha_hasta BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"')) "
		    		+ "THEN pe.fecha_hasta "
		    		+ "ELSE LAST_DAY('"+periodoFinal+"') "
		    		+ "END AS fecha_fin_previred " 
		    		+ "FROM permiso_licencia pe inner join trabajadores t on pe.codigo_trabajador = t.codigo "
		    		+ "where  pe.codigo_trabajador = "+cod+" "
		    		+ " And pe.idContrato = "+idcont+" and t.rut != '' and pe.accion != 1 "
		    		+ " AND (LAST_DAY('"+periodoFinal+"') BETWEEN pe.fecha_desde AND pe.fecha_hasta or '"+periodoFinal+"' BETWEEN pe.fecha_desde AND pe.fecha_hasta OR pe.fecha_desde BETWEEN '"+periodoFinal+"' AND LAST_DAY('"+periodoFinal+"'))";
		  
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			int existeData = 0;
			int existeData2 = 0;
			
			/// si el trabajador no tiene movimiento en permisos y licencia se mantiene la linea base 
			if(!rs.isBeforeFirst()){
	                existeData = 0;
	                Previred p = new Previred();
	                
	                p.setCodTrabajador(datospermisolicencia.getCodTrabajador());
					p.setIdContrato(datospermisolicencia.getIdContrato());
					/*01*/p.setRutTrabajador(datospermisolicencia.getRutTrabajador());
					/*02*/p.setDvTrabajador(datospermisolicencia.getDvTrabajador());
					/*03*/p.setApellidoPaterno(datospermisolicencia.getApellidoPaterno());
					/*04*/p.setApellidoMaterno(datospermisolicencia.getApellidoMaterno());
					/*05*/p.setNombres(datospermisolicencia.getNombres());
					/*06*/p.setSexo(datospermisolicencia.getSexo());
					/*07*/p.setNacionalidad(datospermisolicencia.getNacionalidad());
					/*08*/p.setTipoPago(datospermisolicencia.getTipoPago());
					/*09*/p.setPeriodoDesde(datospermisolicencia.getPeriodoDesde());
					/*10*/p.setPeriodoHasta(datospermisolicencia.getPeriodoHasta());
					/*11*/p.setRegimenPrevisional(datospermisolicencia.getRegimenPrevisional());
					/*12*/p.setTipoTrabajador(datospermisolicencia.getTipoTrabajador());
					/*13*/p.setDiasTrabajados(datospermisolicencia.getDiasTrabajados());
					/*14*/p.setTipoLinea(datospermisolicencia.getTipoLinea());
					/*15*/p.setCodigoMovimientoPersonal(datospermisolicencia.getCodigoMovimientoPersonal());
					/*16*/p.setFechaDesde(datospermisolicencia.getFechaDesde());
				    /*17*/p.setFechaHasta(datospermisolicencia.getFechaHasta());
					/*18*/p.setTramoAsignacionFamiliar(datospermisolicencia.getTramoAsignacionFamiliar());
					/*19*/p.setnCargasSimples(datospermisolicencia.getnCargasSimples());
					/*20*/p.setnCargasMaternales(datospermisolicencia.getnCargasMaternales());
					/*21*/p.setnCargasInvalidas(datospermisolicencia.getnCargasInvalidas());
				    /*22*/p.setAsignacionFamiliar(datospermisolicencia.getAsignacionFamiliar());
				    /*23*/p.setAsignacionFamiliarRetroactiva(datospermisolicencia.getAsignacionFamiliarRetroactiva());
					/*24*/p.setReintegroCargasFamiliares(datospermisolicencia.getReintegroCargasFamiliares());
					/*25*/p.setSolicitudTrabajadorJoven(datospermisolicencia.getSolicitudTrabajadorJoven());
					/*26*/p.setCodigoAfp(datospermisolicencia.getCodigoAfp());
					/*27*/p.setRentaImponibleAfp(datospermisolicencia.getRentaImponibleAfp());
					/*28*/p.setCotizacionObligatoriaAfp(datospermisolicencia.getCotizacionObligatoriaAfp());
				    /*29*/p.setCotizacionSeguroInvalidezSobrevivencia(datospermisolicencia.getCotizacionSeguroInvalidezSobrevivencia());
				    /*30*/p.setCuentaAhorroVoluntarioAfp(datospermisolicencia.getCuentaAhorroVoluntarioAfp());
				    /*31*/p.setRentaImponibleSustAfp(datospermisolicencia.getRentaImponibleSustAfp());
				    /*32*/p.setTasaPactadaSustit(datospermisolicencia.getTasaPactadaSustit());
				    /*33*/p.setAporteIndemnSustit(datospermisolicencia.getAporteIndemnSustit());
				    /*34*/p.setnPeriodosSustit(datospermisolicencia.getnPeriodosSustit());
				    /*35*/p.setPreiodoDesdeSustit(datospermisolicencia.getPreiodoDesdeSustit());
				    /*36*/p.setPeriosoHastaSustit(datospermisolicencia.getPeriosoHastaSustit());
				    /*37*/p.setPuestoTrabajoPesado(datospermisolicencia.getPuestoTrabajoPesado());
				    /*38*/p.setPorcCotizacionTrabajoPesado(datospermisolicencia.getPorcCotizacionTrabajoPesado());
				    /*39*/p.setCotizacionTrabajoPesado(datospermisolicencia.getCotizacionTrabajoPesado());
				    /*40*/p.setCodigoInstitucionApvi(datospermisolicencia.getCodigoInstitucionApvi());
				    /*41*/p.setNumeroContratoApvi(datospermisolicencia.getNumeroContratoApvi());
				    /*42*/p.setFormaPagoApvi(datospermisolicencia.getFormaPagoApvi());
				    /*43*/p.setCotizacionApvi(datospermisolicencia.getCotizacionApvi());
				    /*44*/p.setCotizacionDepositosConvenidos(datospermisolicencia.getCotizacionDepositosConvenidos());
				    /*45*/p.setCodigoIntitucionAutorizadaApvc(datospermisolicencia.getCodigoIntitucionAutorizadaApvc());
				    /*46*/p.setNumeroContratoApvc(datospermisolicencia.getNumeroContratoApvc());
				    /*47*/p.setFormaPagoApvc(datospermisolicencia.getFormaPagoApvc());
				    /*48*/p.setCotizacionTrabajadorApvc(datospermisolicencia.getCotizacionTrabajadorApvc());
				    /*49*/p.setCotizacionEmpleadorApvc(datospermisolicencia.getCotizacionEmpleadorApvc());
				    /*50*/p.setRutAfiliadoVoluntario(datospermisolicencia.getRutAfiliadoVoluntario());
				    /*51*/p.setDvAfiliadoVoluntario(datospermisolicencia.getDvAfiliadoVoluntario());
				    /*52*/p.setApellidoPaternoAfiliado(datospermisolicencia.getApellidoPaternoAfiliado());
				    /*53*/p.setApellidoMaternoAfiliado(datospermisolicencia.getApellidoMaternoAfiliado());
				    /*54*/p.setNombresAfiliado(datospermisolicencia.getNombresAfiliado());
				    /*55*/p.setCodigoMovimientoPersonalAfiliado(datospermisolicencia.getCodigoMovimientoPersonalAfiliado());
				    /*56*/p.setFechaDesdeAfililado(datospermisolicencia.getFechaDesdeAfililado());
				    /*57*/p.setFechaHastaAfiliado(datospermisolicencia.getFechaHastaAfiliado());
				    /*58*/p.setCodigoAfpAfiliado(datospermisolicencia.getCodigoAfpAfiliado());
				    /*59*/p.setMontoCapitalizacionVoluntaria(datospermisolicencia.getMontoCapitalizacionVoluntaria());
				    /*60*/p.setMontoAhorroVoluntarioAfiliado(datospermisolicencia.getMontoAhorroVoluntarioAfiliado());
				    /*61*/p.setNumPeriodosCotizacionAfiliado(datospermisolicencia.getNumPeriodosCotizacionAfiliado());
				    /*62*/p.setCodigoExCajaRegimen(datospermisolicencia.getCodigoExCajaRegimen());
				    /*63*/p.setTasaCotizacionExCajaRegimen(datospermisolicencia.getTasaCotizacionExCajaRegimen());
				    /*64*/p.setRentaImponibleIPS(datospermisolicencia.getRentaImponibleIPS());
				    /*65*/p.setCotizacionObligatoriaIPS(datospermisolicencia.getCotizacionObligatoriaIPS());
				    /*66*/p.setRentaImponibleDesahucion("0");
				    /*67*/p.setCodigoExCajaRegimenDesahucion("0");
				    /*68*/p.setTasaCotDeshaExCajaPrevision("0");
				    /*69*/p.setCotizacionDesahucion(datospermisolicencia.getCotizacionDesahucion());
				    /*70*/p.setCotizacionFonasa(datospermisolicencia.getCotizacionFonasa());
				    /*71*/p.setCotizacionAccidenteTrabajoISL(datospermisolicencia.getCotizacionAccidenteTrabajoISL());
				    /*72*/p.setBonificacionLey15386(datospermisolicencia.getBonificacionLey15386());
				    /*73*/p.setDescuentoCargasFamiliaresISL(datospermisolicencia.getDescuentoCargasFamiliaresISL());
				    /*74*/p.setBonoGobierno(datospermisolicencia.getBonoGobierno());
				    /*75*/p.setCodigoInstitucionSalud(datospermisolicencia.getCodigoInstitucionSalud());
				    /*76*/p.setNumeroFUN("");
				    /*77*/p.setRentaImponibleIsapre(datospermisolicencia.getRentaImponibleIsapre());
				    /*78*/p.setMondedaPlanPactadoIsapre(datospermisolicencia.getMondedaPlanPactadoIsapre());
				    /*79*/p.setCotizacionPactada(datospermisolicencia.getCotizacionPactada());
				    /*80*/p.setCotizacionObligatoriaIsapre(datospermisolicencia.getCotizacionObligatoriaIsapre());
				    /*81*/p.setCotizacionAdicionalVoluntaria(datospermisolicencia.getCotizacionAdicionalVoluntaria());
				    /*82*/p.setMontoGarantiaGES(datospermisolicencia.getMontoGarantiaGES());
				    /*83*/p.setCodigoCCAF(datospermisolicencia.getCodigoCCAF());
				    /*84*/p.setRentaImponibleCCAF(datospermisolicencia.getRentaImponibleCCAF());
				    /*85*/p.setCreditosPersonalesCCAF(datospermisolicencia.getCreditosPersonalesCCAF());
				    /*86*/p.setDescuentoDentalCCAF("0");
				    /*87*/p.setDescuentosLeasing(datospermisolicencia.getDescuentosLeasing());
				    /*88*/p.setDescuentosSeguroVidaCCAF(datospermisolicencia.getDescuentosSeguroVidaCCAF());
				    /*89*/p.setOtrosDescuentosCCAF("0");
				    /*90*/p.setCotizacionCCAFNoAfilIsapres(datospermisolicencia.getCotizacionCCAFNoAfilIsapres());
				    /*91*/p.setDescCargasFamiliaresCCAF(datospermisolicencia.getDescCargasFamiliaresCCAF());
				    /*92*/p.setOtrosDescuentosCCAF1("0");
				    /*93*/p.setOtrosDescuentosCCAF2("0");
				    /*94*/p.setBonoGobiernoCCAF("0");
				    /*95*/p.setCodigoSucursalCCAF("");
				    /*96*/p.setCodigoMutualidad(datospermisolicencia.getCodigoMutualidad());
				    /*97*/p.setReantaImponibleMutualidad(datospermisolicencia.getReantaImponibleMutualidad());
				    /*98*/p.setCotizacionAccidenteTrabajo(datospermisolicencia.getCotizacionAccidenteTrabajo());
				    /*99*/p.setSucursalPagoMutual(datospermisolicencia.getSucursalPagoMutual());
				   /*100*/p.setRentaImponibleSeguroCesantia(datospermisolicencia.getRentaImponibleSeguroCesantia());
				   /*101*/p.setAporteTrabajadorSeguroCesantia(datospermisolicencia.getAporteTrabajadorSeguroCesantia());
				   /*102*/p.setAporteEmpleadorSeguroCesantia(datospermisolicencia.getAporteEmpleadorSeguroCesantia());
				   /*103*/p.setRutPagadoraSubsidio(datospermisolicencia.getRutPagadoraSubsidio());
				   /*104*/p.setDvPagadoraSubsidio(datospermisolicencia.getDvPagadoraSubsidio());
				   /*105*/p.setCentroCostoSucAgencia(datospermisolicencia.getCentroCostoSucAgencia());
					
				           p.setIsapreFonasa(datospermisolicencia.getIsapreFonasa());
				           p.setTopeImL(datospermisolicencia.getTopeImL());
				           p.setImponibleL(datospermisolicencia.getImponibleL());
				           
				           p.setValorsss(datospermisolicencia.getValorsss());
						   p.setIdafp(datospermisolicencia.getIdafp());
						   p.setCodigosssExcaja(datospermisolicencia.getCodigosssExcaja());
						   p.setTopeseguro(datospermisolicencia.getTopeseguro());
						   p.setBaseAFC(datospermisolicencia.getBaseAFC());
						   p.setBasesanna(datospermisolicencia.getBasesanna());
						   p.setSueldo_afc(datospermisolicencia.getSueldo_afc());
						   p.setPensionadocotizante(datospermisolicencia.getPensionadocotizante());
						   p.setPensionado(datospermisolicencia.getPensionado());
						   p.setId_cajamutal(datospermisolicencia.getId_cajamutal());
						   
						   p.setRutPagadoraSubsidioMutual(datospermisolicencia.getRutPagadoraSubsidioMutual());
						   p.setDvPagadoraSubsidioMutual(datospermisolicencia.getDvPagadoraSubsidioMutual());
						   
						  
					
						   lista.add(p);
	            }

	           else{
	        	
	        	   existeData = 1;
	               }   
			
		
			 int count = 0;
		         
			while(rs.next()){
				
			 if(count == 0 && existeData == 1 && n_movimiento == "00" && tipo_linea == "00"){
				
					Previred p = new Previred();
					
					if(datospermisolicencia.getCodTrabajador() == 2004){
						System.out.println("");
					}
				
					p.setCodTrabajador(datospermisolicencia.getCodTrabajador());
					p.setIdContrato(datospermisolicencia.getIdContrato());
					/*01*/p.setRutTrabajador(datospermisolicencia.getRutTrabajador());
					/*02*/p.setDvTrabajador(datospermisolicencia.getDvTrabajador());
					/*03*/p.setApellidoPaterno(datospermisolicencia.getApellidoPaterno());
					/*04*/p.setApellidoMaterno(datospermisolicencia.getApellidoMaterno());
					/*05*/p.setNombres(datospermisolicencia.getNombres());
					/*06*/p.setSexo(datospermisolicencia.getSexo());
					/*07*/p.setNacionalidad(datospermisolicencia.getNacionalidad());
					/*08*/p.setTipoPago(datospermisolicencia.getTipoPago());
					/*09*/p.setPeriodoDesde(datospermisolicencia.getPeriodoDesde());
					/*10*/p.setPeriodoHasta(datospermisolicencia.getPeriodoHasta());
					/*11*/p.setRegimenPrevisional(datospermisolicencia.getRegimenPrevisional());
					/*12*/p.setTipoTrabajador(datospermisolicencia.getTipoTrabajador());
					/*13*/p.setDiasTrabajados(datospermisolicencia.getDiasTrabajados());
					/*14*/p.setTipoLinea(datospermisolicencia.getTipoLinea());
					/*15*//*CODIGO MOVIMIENTO PERSONAL*/
							if(rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") == 168 || rs.getInt("subtipo_licencia") == 184 ){
								p.setCodigoMovimientoPersonal("06");
							
							}else if(rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") == 168 || rs.getInt("subtipo_licencia") == 185 ){
								p.setCodigoMovimientoPersonal("06");
							
							}else if(rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") == 168 || rs.getInt("subtipo_licencia") == 526 ){
								p.setCodigoMovimientoPersonal("06");
							
							}
							else if (rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") != 168 && rs.getInt("subtipo_licencia") != 184){
								p.setCodigoMovimientoPersonal("03");
							}
						
							// PERMISO SIN GOSE DE SUELDO
							if(rs.getInt("accion") == 4){
								p.setCodigoMovimientoPersonal("04");
							// FALTA 
							} if(rs.getInt("accion") == 3){
								p.setCodigoMovimientoPersonal("11");
							}
					/*16*//*FECHA DESDE*/
							String fechainicio = rs.getString("fecha_inicio_previred");
							String[] fechainicioSplit = fechainicio.split("-");
							p.setFechaDesde(fechainicioSplit[2]+"/"+fechainicioSplit[1]+"/"+fechainicioSplit[0]);
				    /*17*//*FECHA HASTA*/
							String fechatermino = rs.getString("fecha_fin_previred");
							String[] fechafechaterminoSplit = fechatermino.split("-");
							p.setFechaHasta(fechafechaterminoSplit[2]+"/"+fechafechaterminoSplit[1]+"/"+fechafechaterminoSplit[0]);
					/*18*/p.setTramoAsignacionFamiliar(datospermisolicencia.getTramoAsignacionFamiliar());
					/*19*/p.setnCargasSimples(datospermisolicencia.getnCargasSimples());
					/*20*/p.setnCargasMaternales(datospermisolicencia.getnCargasMaternales());
					/*21*/p.setnCargasInvalidas(datospermisolicencia.getnCargasInvalidas());
				    /*22*/p.setAsignacionFamiliar(datospermisolicencia.getAsignacionFamiliar());
				    /*23*/p.setAsignacionFamiliarRetroactiva(datospermisolicencia.getAsignacionFamiliarRetroactiva());
					/*24*/p.setReintegroCargasFamiliares(datospermisolicencia.getReintegroCargasFamiliares());
					/*25*/p.setSolicitudTrabajadorJoven(datospermisolicencia.getSolicitudTrabajadorJoven());
					/*26*/p.setCodigoAfp(datospermisolicencia.getCodigoAfp());
					/*27*/p.setRentaImponibleAfp(datospermisolicencia.getRentaImponibleAfp());
					/*28*/p.setCotizacionObligatoriaAfp(datospermisolicencia.getCotizacionObligatoriaAfp());
				    /*29*/p.setCotizacionSeguroInvalidezSobrevivencia(datospermisolicencia.getCotizacionSeguroInvalidezSobrevivencia());
				    /*30*/p.setCuentaAhorroVoluntarioAfp(datospermisolicencia.getCuentaAhorroVoluntarioAfp());
				    /*31*/p.setRentaImponibleSustAfp("0");
				    /*32*/p.setTasaPactadaSustit("0");
				    /*33*/p.setAporteIndemnSustit("0");
				    /*34*/p.setnPeriodosSustit("0");
				    /*35*/p.setPreiodoDesdeSustit("");
				    /*36*/p.setPeriosoHastaSustit("");
				    /*37*/p.setPuestoTrabajoPesado("");
				    /*38*/p.setPorcCotizacionTrabajoPesado("0");
				    /*39*/p.setCotizacionTrabajoPesado("0");
				    /*40*/p.setCodigoInstitucionApvi(datospermisolicencia.getCodigoInstitucionApvi());
				    /*41*/p.setNumeroContratoApvi(datospermisolicencia.getNumeroContratoApvi());
				    /*42*/p.setFormaPagoApvi(datospermisolicencia.getFormaPagoApvi());
				    /*43*/p.setCotizacionApvi(datospermisolicencia.getCotizacionApvi());
				    /*44*/p.setCotizacionDepositosConvenidos(datospermisolicencia.getCotizacionDepositosConvenidos());
				    /*45*/p.setCodigoIntitucionAutorizadaApvc("0");
				    /*46*/p.setNumeroContratoApvc("");
				    /*47*/p.setFormaPagoApvc("0");
				    /*48*/p.setCotizacionTrabajadorApvc("0");
				    /*49*/p.setCotizacionEmpleadorApvc("0");
				    /*50*/p.setRutAfiliadoVoluntario("0");
				    /*51*/p.setDvAfiliadoVoluntario("");
				    /*52*/p.setApellidoPaternoAfiliado("");
				    /*53*/p.setApellidoMaternoAfiliado("");
				    /*54*/p.setNombresAfiliado("");
				    /*55*/p.setCodigoMovimientoPersonalAfiliado("00");
				    /*56*/p.setFechaDesdeAfililado("");
				    /*57*/p.setFechaHastaAfiliado("");
				    /*58*/p.setCodigoAfpAfiliado("0");
				    /*59*/p.setMontoCapitalizacionVoluntaria("0");
				    /*60*/p.setMontoAhorroVoluntarioAfiliado("0");
				    /*61*/p.setNumPeriodosCotizacionAfiliado("0");
				    /*62*/p.setCodigoExCajaRegimen(datospermisolicencia.getCodigoExCajaRegimen());
				    /*63*/p.setTasaCotizacionExCajaRegimen(datospermisolicencia.getTasaCotizacionExCajaRegimen());
				    /*64*/p.setRentaImponibleIPS(datospermisolicencia.getRentaImponibleIPS());
				    /*65*/p.setCotizacionObligatoriaIPS(datospermisolicencia.getCotizacionObligatoriaIPS());
				    /*66*/p.setRentaImponibleDesahucion("0");
				    /*67*/p.setCodigoExCajaRegimenDesahucion("0");
				    /*68*/p.setTasaCotDeshaExCajaPrevision("0");
				    /*69*/p.setCotizacionDesahucion("0");
				    /*70*/p.setCotizacionFonasa(datospermisolicencia.getCotizacionFonasa());
				    /*71*/p.setCotizacionAccidenteTrabajoISL(datospermisolicencia.getCotizacionAccidenteTrabajoISL());
				    /*72*/p.setBonificacionLey15386("0");
				    /*73*/p.setDescuentoCargasFamiliaresISL(datospermisolicencia.getDescuentoCargasFamiliaresISL());
				    /*74*/p.setBonoGobierno("0");
				    /*75*/p.setCodigoInstitucionSalud(datospermisolicencia.getCodigoInstitucionSalud());
				    /*76*/p.setNumeroFUN("");
				    /*77*/p.setRentaImponibleIsapre(datospermisolicencia.getRentaImponibleIsapre());
				    /*78*/p.setMondedaPlanPactadoIsapre(datospermisolicencia.getMondedaPlanPactadoIsapre());
				    /*79*/p.setCotizacionPactada(datospermisolicencia.getCotizacionPactada());
				    /*80*/p.setCotizacionObligatoriaIsapre(datospermisolicencia.getCotizacionObligatoriaIsapre());
				    /*81*/p.setCotizacionAdicionalVoluntaria(datospermisolicencia.getCotizacionAdicionalVoluntaria());
				    /*82*/p.setMontoGarantiaGES("0");
				    /*83*/p.setCodigoCCAF(datospermisolicencia.getCodigoCCAF());
				    /*84*/p.setRentaImponibleCCAF(datospermisolicencia.getRentaImponibleCCAF());
				    /*85*/p.setCreditosPersonalesCCAF(datospermisolicencia.getCreditosPersonalesCCAF());
				    /*86*/p.setDescuentoDentalCCAF("0");
				    /*87*/p.setDescuentosLeasing(datospermisolicencia.getDescuentosLeasing());
				    /*88*/p.setDescuentosSeguroVidaCCAF(datospermisolicencia.getDescuentosSeguroVidaCCAF());
				    /*89*/p.setOtrosDescuentosCCAF("0");
				    /*90*/p.setCotizacionCCAFNoAfilIsapres(datospermisolicencia.getCotizacionCCAFNoAfilIsapres());
				    /*91*/p.setDescCargasFamiliaresCCAF(datospermisolicencia.getDescCargasFamiliaresCCAF());
				    /*92*/p.setOtrosDescuentosCCAF1("0");
				    /*93*/p.setOtrosDescuentosCCAF2("0");
				    /*94*/p.setBonoGobiernoCCAF("0");
				    /*95*/p.setCodigoSucursalCCAF("");
				    /*96*/p.setCodigoMutualidad(datospermisolicencia.getCodigoMutualidad());
				    /*97*/p.setReantaImponibleMutualidad(datospermisolicencia.getReantaImponibleMutualidad());
				    /*98*/p.setCotizacionAccidenteTrabajo(datospermisolicencia.getCotizacionAccidenteTrabajo());
				    /*99*/p.setSucursalPagoMutual(datospermisolicencia.getSucursalPagoMutual());
				   /*100*/p.setRentaImponibleSeguroCesantia(datospermisolicencia.getRentaImponibleSeguroCesantia());
				   /*101*/p.setAporteTrabajadorSeguroCesantia(datospermisolicencia.getAporteTrabajadorSeguroCesantia());
				   /*102*/p.setAporteEmpleadorSeguroCesantia(datospermisolicencia.getAporteEmpleadorSeguroCesantia());
				   
			    if (rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") != 168 && rs.getInt("subtipo_licencia") != 184){
			    	/*103*/p.setRutPagadoraSubsidio(datospermisolicencia.getRutPagadoraSubsidio());
					/*104*/p.setDvPagadoraSubsidio(datospermisolicencia.getDvPagadoraSubsidio());
					   
				}else{
					/*103*/p.setRutPagadoraSubsidio("0");
					/*104*/p.setDvPagadoraSubsidio("0");
					   
				}
				   /*105*/p.setCentroCostoSucAgencia(datospermisolicencia.getCentroCostoSucAgencia());
							
				          p.setIsapreFonasa(datospermisolicencia.getIsapreFonasa());
				          p.setValorsss(datospermisolicencia.getValorsss());
						    p.setIdafp(datospermisolicencia.getIdafp());
						    p.setCodigosssExcaja(datospermisolicencia.getCodigosssExcaja());
						    p.setTopeseguro(datospermisolicencia.getTopeseguro());
						    p.setImponibleL(datospermisolicencia.getImponibleL());
						    p.setTopeImL(datospermisolicencia.getTopeImL());
						    p.setBaseAFC(datospermisolicencia.getBaseAFC()); 
						    p.setPensionadocotizante(datospermisolicencia.getPensionadocotizante());
						    p.setBasesanna(datospermisolicencia.getBasesanna());
						    p.setSueldo_afc(datospermisolicencia.getSueldo_afc());
						    p.setPensionado(datospermisolicencia.getPensionado());
						    p.setId_cajamutal(datospermisolicencia.getId_cajamutal());
						    p.setRutPagadoraSubsidioMutual(datospermisolicencia.getRutPagadoraSubsidioMutual());
							p.setDvPagadoraSubsidioMutual(datospermisolicencia.getDvPagadoraSubsidioMutual());
					
						    lista.add(p);	
			 }
			 
			 else if(count >= 1 && existeData == 1 && n_movimiento == "00" && tipo_linea == "00"){
				 
				 	Previred p = new Previred();
				 
					p.setCodTrabajador(datospermisolicencia.getCodTrabajador());
					p.setIdContrato(datospermisolicencia.getIdContrato());
					/*01*/p.setRutTrabajador(datospermisolicencia.getRutTrabajador());
					/*02*/p.setDvTrabajador(datospermisolicencia.getDvTrabajador());
					/*03*/p.setApellidoPaterno(datospermisolicencia.getApellidoPaterno());
					/*04*/p.setApellidoMaterno(datospermisolicencia.getApellidoMaterno());
					/*05*/p.setNombres(datospermisolicencia.getNombres());
					/*06*/p.setSexo(datospermisolicencia.getSexo());
					/*07*/p.setNacionalidad(datospermisolicencia.getNacionalidad());
					/*08*/p.setTipoPago("0");
					/*09*/p.setPeriodoDesde(datospermisolicencia.getPeriodoDesde());
					/*10*/p.setPeriodoHasta(datospermisolicencia.getPeriodoHasta());
					/*11*/p.setRegimenPrevisional(datospermisolicencia.getRegimenPrevisional());
					/*12*/p.setTipoTrabajador("0");
					/*13*/p.setDiasTrabajados("0");
					/*14*/p.setTipoLinea("01");
					/*15*//*CODIGO MOVIMIENTO PERSONAL*/
							if(rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") == 168 && rs.getInt("subtipo_licencia") == 184){
								p.setCodigoMovimientoPersonal("06");
							
							}else if (rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") != 168 && rs.getInt("subtipo_licencia") != 184){
								p.setCodigoMovimientoPersonal("03");
							}
						
							// PERMISO SIN GOSE DE SUELDO
							if(rs.getInt("accion") == 4){
								p.setCodigoMovimientoPersonal("04");
							// FALTA 
							} if(rs.getInt("accion") == 3){
								p.setCodigoMovimientoPersonal("11");
							}
					/*16*//*FECHA DESDE*/
							String fechainicio = rs.getString("fecha_inicio_previred");
							String[] fechainicioSplit = fechainicio.split("-");
							p.setFechaDesde(fechainicioSplit[2]+"/"+fechainicioSplit[1]+"/"+fechainicioSplit[0]);
				    /*17*//*FECHA HASTA*/
							String fechatermino = rs.getString("fecha_fin_previred");
							String[] fechafechaterminoSplit = fechatermino.split("-");
							p.setFechaHasta(fechafechaterminoSplit[2]+"/"+fechafechaterminoSplit[1]+"/"+fechafechaterminoSplit[0]);
					/*18*/p.setTramoAsignacionFamiliar("");
					/*19*/p.setnCargasSimples("0");
					/*20*/p.setnCargasMaternales("0");
					/*21*/p.setnCargasInvalidas("0");
				    /*22*/p.setAsignacionFamiliar("0");
				    /*23*/p.setAsignacionFamiliarRetroactiva("0");
					/*24*/p.setReintegroCargasFamiliares("0");
					/*25*/p.setSolicitudTrabajadorJoven("");
					/*26*/p.setCodigoAfp("0");
					/*27*/p.setRentaImponibleAfp("0");
					/*28*/p.setCotizacionObligatoriaAfp("0");
				    /*29*/p.setCotizacionSeguroInvalidezSobrevivencia("0");
				    /*30*/p.setCuentaAhorroVoluntarioAfp("0");
				    /*31*/p.setRentaImponibleSustAfp("0");
				    /*32*/p.setTasaPactadaSustit("0");
				    /*33*/p.setAporteIndemnSustit("0");
				    /*34*/p.setnPeriodosSustit("0");
				    /*35*/p.setPreiodoDesdeSustit("");
				    /*36*/p.setPeriosoHastaSustit("");
				    /*37*/p.setPuestoTrabajoPesado("");
				    /*38*/p.setPorcCotizacionTrabajoPesado("0");
				    /*39*/p.setCotizacionTrabajoPesado("0");
				    /*40*/p.setCodigoInstitucionApvi("0");
				    /*41*/p.setNumeroContratoApvi("");
				    /*42*/p.setFormaPagoApvi("0");
				    /*43*/p.setCotizacionApvi("0");
				    /*44*/p.setCotizacionDepositosConvenidos("0");
				    /*45*/p.setCodigoIntitucionAutorizadaApvc("0");
				    /*46*/p.setNumeroContratoApvc("");
				    /*47*/p.setFormaPagoApvc("0");
				    /*48*/p.setCotizacionTrabajadorApvc("0");
				    /*49*/p.setCotizacionEmpleadorApvc("0");
				    /*50*/p.setRutAfiliadoVoluntario("0");
				    /*51*/p.setDvAfiliadoVoluntario("");
				    /*52*/p.setApellidoPaternoAfiliado("");
				    /*53*/p.setApellidoMaternoAfiliado("");
				    /*54*/p.setNombresAfiliado("");
				    /*55*/p.setCodigoMovimientoPersonalAfiliado("00");
				    /*56*/p.setFechaDesdeAfililado("");
				    /*57*/p.setFechaHastaAfiliado("");
				    /*58*/p.setCodigoAfpAfiliado("0");
				    /*59*/p.setMontoCapitalizacionVoluntaria("0");
				    /*60*/p.setMontoAhorroVoluntarioAfiliado("0");
				    /*61*/p.setNumPeriodosCotizacionAfiliado("0");
				    /*62*/p.setCodigoExCajaRegimen("0");
				    /*63*/p.setTasaCotizacionExCajaRegimen("0");
				    /*64*/p.setRentaImponibleIPS("0");
				    /*65*/p.setCotizacionObligatoriaIPS("0");
				    /*66*/p.setRentaImponibleDesahucion("0");
				    /*67*/p.setCodigoExCajaRegimenDesahucion("0");
				    /*68*/p.setTasaCotDeshaExCajaPrevision("0");
				    /*69*/p.setCotizacionDesahucion("0");
				    /*70*/p.setCotizacionFonasa("0");
				    /*71*/p.setCotizacionAccidenteTrabajoISL("0");
				    /*72*/p.setBonificacionLey15386("0");
				    /*73*/p.setDescuentoCargasFamiliaresISL("0");
				    /*74*/p.setBonoGobierno("0");
				    /*75*/p.setCodigoInstitucionSalud("0");
				    /*76*/p.setNumeroFUN("");
				    /*77*/p.setRentaImponibleIsapre("0");
				    /*78*/p.setMondedaPlanPactadoIsapre("0");
				    /*79*/p.setCotizacionPactada("0");
				    /*80*/p.setCotizacionObligatoriaIsapre("0");
				    /*81*/p.setCotizacionAdicionalVoluntaria("0");
				    /*82*/p.setMontoGarantiaGES("0");
				    /*83*/p.setCodigoCCAF("0");
				    /*84*/p.setRentaImponibleCCAF("0");
				    /*85*/p.setCreditosPersonalesCCAF("0");
				    /*86*/p.setDescuentoDentalCCAF("0");
				    /*87*/p.setDescuentosLeasing(datospermisolicencia.getDescuentosLeasing());
				    /*88*/p.setDescuentosSeguroVidaCCAF(datospermisolicencia.getDescuentosSeguroVidaCCAF());
				    /*89*/p.setOtrosDescuentosCCAF("0");
				    /*90*/p.setCotizacionCCAFNoAfilIsapres("0");
				    /*91*/p.setDescCargasFamiliaresCCAF("0");
				    /*92*/p.setOtrosDescuentosCCAF1("0");
				    /*93*/p.setOtrosDescuentosCCAF2("0");
				    /*94*/p.setBonoGobiernoCCAF("0");
				    /*95*/p.setCodigoSucursalCCAF("");
				    /*96*/p.setCodigoMutualidad("0");
				    /*97*/p.setReantaImponibleMutualidad("0");
				    /*98*/p.setCotizacionAccidenteTrabajo("0");
				    /*99*/p.setSucursalPagoMutual("0");
				   /*100*/p.setRentaImponibleSeguroCesantia("0");
				   /*101*/p.setAporteTrabajadorSeguroCesantia("0");
				   /*102*/p.setAporteEmpleadorSeguroCesantia("0");
				   /*103*/p.setRutPagadoraSubsidio(datospermisolicencia.getRutPagadoraSubsidio());
				   /*104*/p.setDvPagadoraSubsidio(datospermisolicencia.getDvPagadoraSubsidio());
				   /*105*/p.setCentroCostoSucAgencia(datospermisolicencia.getCentroCostoSucAgencia());
				   
				   p.setTopeImL(datospermisolicencia.getTopeImL());
				   p.setImponibleL(datospermisolicencia.getImponibleL());
					
				           p.setIsapreFonasa(datospermisolicencia.getIsapreFonasa());
				           p.setValorsss(datospermisolicencia.getValorsss());
						    p.setIdafp(datospermisolicencia.getIdafp());
						    p.setCodigosssExcaja(datospermisolicencia.getCodigosssExcaja());
						    p.setTopeseguro(datospermisolicencia.getTopeseguro());
						    p.setBaseAFC(datospermisolicencia.getBaseAFC());
						    p.setPensionadocotizante(datospermisolicencia.getPensionadocotizante());
						    p.setBasesanna(datospermisolicencia.getBasesanna());
						    p.setSueldo_afc(datospermisolicencia.getSueldo_afc());
						    p.setPensionado(datospermisolicencia.getPensionado());
						    p.setId_cajamutal(datospermisolicencia.getId_cajamutal());
						    p.setRutPagadoraSubsidioMutual(datospermisolicencia.getRutPagadoraSubsidioMutual());
							p.setDvPagadoraSubsidioMutual(datospermisolicencia.getDvPagadoraSubsidioMutual());
							   
							  
					
				 lista.add(p);
			 }
			 else if(count == 0 && existeData == 1 && n_movimiento == "00" && tipo_linea == "02"){

				 Previred p = new Previred();
				 
					p.setCodTrabajador(datospermisolicencia.getCodTrabajador());
					p.setIdContrato(datospermisolicencia.getIdContrato());
					/*01*/p.setRutTrabajador(datospermisolicencia.getRutTrabajador());
					/*02*/p.setDvTrabajador(datospermisolicencia.getDvTrabajador());
					/*03*/p.setApellidoPaterno(datospermisolicencia.getApellidoPaterno());
					/*04*/p.setApellidoMaterno(datospermisolicencia.getApellidoMaterno());
					/*05*/p.setNombres(datospermisolicencia.getNombres());
					/*06*/p.setSexo(datospermisolicencia.getSexo());
					/*07*/p.setNacionalidad(datospermisolicencia.getNacionalidad());
					/*08*/p.setTipoPago(datospermisolicencia.getTipoPago());
					/*09*/p.setPeriodoDesde(datospermisolicencia.getPeriodoDesde());
					/*10*/p.setPeriodoHasta(datospermisolicencia.getPeriodoHasta());
					/*11*/p.setRegimenPrevisional(datospermisolicencia.getRegimenPrevisional());
					/*12*/p.setTipoTrabajador(datospermisolicencia.getTipoTrabajador());
					/*13*/p.setDiasTrabajados(datospermisolicencia.getDiasTrabajados());
					/*14*/p.setTipoLinea(datospermisolicencia.getTipoLinea());
					/*15*//*CODIGO MOVIMIENTO PERSONAL*/
							if(rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") == 168 && rs.getInt("subtipo_licencia") == 184){
								p.setCodigoMovimientoPersonal("06");
							
							}else if (rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") != 168 && rs.getInt("subtipo_licencia") != 184){
								p.setCodigoMovimientoPersonal("03");
							}
						
							// PERMISO SIN GOSE DE SUELDO
							if(rs.getInt("accion") == 4){
								p.setCodigoMovimientoPersonal("04");
							// FALTA 
							} if(rs.getInt("accion") == 3){
								p.setCodigoMovimientoPersonal("11");
							}
					/*16*//*FECHA DESDE*/
							String fechainicio = rs.getString("fecha_inicio_previred");
							String[] fechainicioSplit = fechainicio.split("-");
							p.setFechaDesde(fechainicioSplit[2]+"/"+fechainicioSplit[1]+"/"+fechainicioSplit[0]);
				    /*17*//*FECHA HASTA*/
							String fechatermino = rs.getString("fecha_fin_previred");
							String[] fechafechaterminoSplit = fechatermino.split("-");
							p.setFechaHasta(fechafechaterminoSplit[2]+"/"+fechafechaterminoSplit[1]+"/"+fechafechaterminoSplit[0]);
					/*18*/p.setTramoAsignacionFamiliar(datospermisolicencia.getTramoAsignacionFamiliar());
					/*19*/p.setnCargasSimples(datospermisolicencia.getnCargasSimples());
					/*20*/p.setnCargasMaternales(datospermisolicencia.getnCargasMaternales());
					/*21*/p.setnCargasInvalidas(datospermisolicencia.getnCargasInvalidas());
					/*22*/p.setAsignacionFamiliar(datospermisolicencia.getAsignacionFamiliar());
					/*23*/p.setAsignacionFamiliarRetroactiva(datospermisolicencia.getAsignacionFamiliarRetroactiva());
					/*24*/p.setReintegroCargasFamiliares(datospermisolicencia.getReintegroCargasFamiliares());
					/*25*/p.setSolicitudTrabajadorJoven(datospermisolicencia.getSolicitudTrabajadorJoven());
					/*26*/p.setCodigoAfp(datospermisolicencia.getCodigoAfp());
					/*27*/p.setRentaImponibleAfp(datospermisolicencia.getRentaImponibleAfp());
					/*28*/p.setCotizacionObligatoriaAfp(datospermisolicencia.getCotizacionObligatoriaAfp());
				    /*29*/p.setCotizacionSeguroInvalidezSobrevivencia(datospermisolicencia.getCotizacionSeguroInvalidezSobrevivencia());
				    /*30*/p.setCuentaAhorroVoluntarioAfp(datospermisolicencia.getCuentaAhorroVoluntarioAfp());
				    /*31*/p.setRentaImponibleSustAfp("0");
				    /*32*/p.setTasaPactadaSustit("0");
				    /*33*/p.setAporteIndemnSustit("0");
				    /*34*/p.setnPeriodosSustit("0");
				    /*35*/p.setPreiodoDesdeSustit("");
				    /*36*/p.setPeriosoHastaSustit("");
				    /*37*/p.setPuestoTrabajoPesado("");
				    /*38*/p.setPorcCotizacionTrabajoPesado("0");
				    /*39*/p.setCotizacionTrabajoPesado("0");
				    /*40*/p.setCodigoInstitucionApvi(datospermisolicencia.getCodigoInstitucionApvi());
				    /*41*/p.setNumeroContratoApvi(datospermisolicencia.getNumeroContratoApvi());
				    /*42*/p.setFormaPagoApvi(datospermisolicencia.getFormaPagoApvi());
				    /*43*/p.setCotizacionApvi(datospermisolicencia.getCotizacionApvi());
				    /*44*/p.setCotizacionDepositosConvenidos(datospermisolicencia.getCotizacionDepositosConvenidos());
				    /*45*/p.setCodigoIntitucionAutorizadaApvc("0");
				    /*46*/p.setNumeroContratoApvc("");
				    /*47*/p.setFormaPagoApvc("0");
				    /*48*/p.setCotizacionTrabajadorApvc("0");
				    /*49*/p.setCotizacionEmpleadorApvc("0");
				    /*50*/p.setRutAfiliadoVoluntario("0");
				    /*51*/p.setDvAfiliadoVoluntario("");
				    /*52*/p.setApellidoPaternoAfiliado("");
				    /*53*/p.setApellidoMaternoAfiliado("");
				    /*54*/p.setNombresAfiliado("");
				    /*55*/p.setCodigoMovimientoPersonalAfiliado("00");
				    /*56*/p.setFechaDesdeAfililado("");
				    /*57*/p.setFechaHastaAfiliado("");
				    /*58*/p.setCodigoAfpAfiliado("0");
				    /*59*/p.setMontoCapitalizacionVoluntaria("0");
				    /*60*/p.setMontoAhorroVoluntarioAfiliado("0");
				    /*61*/p.setNumPeriodosCotizacionAfiliado("0");
				    /*62*/p.setCodigoExCajaRegimen(datospermisolicencia.getCodigoExCajaRegimen());
				    /*63*/p.setTasaCotizacionExCajaRegimen(datospermisolicencia.getTasaCotizacionExCajaRegimen());
				    /*64*/p.setRentaImponibleIPS(datospermisolicencia.getRentaImponibleIPS());
				    /*65*/p.setCotizacionObligatoriaIPS(datospermisolicencia.getCotizacionObligatoriaIPS());
				    /*66*/p.setRentaImponibleDesahucion("0");
				    /*67*/p.setCodigoExCajaRegimenDesahucion("0");
				    /*68*/p.setTasaCotDeshaExCajaPrevision("0");
				    /*69*/p.setCotizacionDesahucion("0");
				    /*70*/p.setCotizacionFonasa(datospermisolicencia.getCotizacionFonasa());
				    /*71*/p.setCotizacionAccidenteTrabajoISL(datospermisolicencia.getCotizacionAccidenteTrabajoISL());
				    /*72*/p.setBonificacionLey15386("0");
				    /*73*/p.setDescuentoCargasFamiliaresISL(datospermisolicencia.getDescuentoCargasFamiliaresISL());
				    /*74*/p.setBonoGobierno("0");
				    /*75*/p.setCodigoInstitucionSalud(datospermisolicencia.getCodigoInstitucionSalud());
				    /*76*/p.setNumeroFUN("");
				    /*77*/p.setRentaImponibleIsapre(datospermisolicencia.getRentaImponibleIsapre());
				    /*78*/p.setMondedaPlanPactadoIsapre(datospermisolicencia.getMondedaPlanPactadoIsapre());
				    /*79*/p.setCotizacionPactada(datospermisolicencia.getCotizacionPactada());
				    /*80*/p.setCotizacionObligatoriaIsapre(datospermisolicencia.getCotizacionObligatoriaIsapre());
				    /*81*/p.setCotizacionAdicionalVoluntaria(datospermisolicencia.getCotizacionAdicionalVoluntaria());
				    /*82*/p.setMontoGarantiaGES("0");
				    /*83*/p.setCodigoCCAF(datospermisolicencia.getCodigoCCAF());
				    /*84*/p.setRentaImponibleCCAF(datospermisolicencia.getRentaImponibleCCAF());
				    /*85*/p.setCreditosPersonalesCCAF(datospermisolicencia.getCreditosPersonalesCCAF());
				    /*86*/p.setDescuentoDentalCCAF("0");
				    /*87*/p.setDescuentosLeasing(datospermisolicencia.getDescuentosLeasing());
				    /*88*/p.setDescuentosSeguroVidaCCAF(datospermisolicencia.getDescuentosSeguroVidaCCAF());
				    /*89*/p.setOtrosDescuentosCCAF("0");
				    /*90*/p.setCotizacionCCAFNoAfilIsapres(datospermisolicencia.getCotizacionCCAFNoAfilIsapres());
				    /*91*/p.setDescCargasFamiliaresCCAF(datospermisolicencia.getDescCargasFamiliaresCCAF());
				    /*92*/p.setOtrosDescuentosCCAF1("0");
				    /*93*/p.setOtrosDescuentosCCAF2("0");
				    /*94*/p.setBonoGobiernoCCAF("0");
				    /*95*/p.setCodigoSucursalCCAF("");
				    /*96*/p.setCodigoMutualidad(datospermisolicencia.getCodigoMutualidad());
				    /*97*/p.setReantaImponibleMutualidad(datospermisolicencia.getReantaImponibleMutualidad());
				    /*98*/p.setCotizacionAccidenteTrabajo(datospermisolicencia.getCotizacionAccidenteTrabajo());
				    /*99*/p.setSucursalPagoMutual(datospermisolicencia.getSucursalPagoMutual());
				   /*100*/p.setRentaImponibleSeguroCesantia(datospermisolicencia.getRentaImponibleSeguroCesantia());
				   /*101*/p.setAporteTrabajadorSeguroCesantia(datospermisolicencia.getAporteTrabajadorSeguroCesantia());
				   /*102*/p.setAporteEmpleadorSeguroCesantia(datospermisolicencia.getAporteEmpleadorSeguroCesantia());
				   
			    if (rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") != 168 && rs.getInt("subtipo_licencia") != 184){
			    	/*103*/p.setRutPagadoraSubsidio(datospermisolicencia.getRutPagadoraSubsidio());
					/*104*/p.setDvPagadoraSubsidio(datospermisolicencia.getDvPagadoraSubsidio());
					   
				}else{
					/*103*/p.setRutPagadoraSubsidio("0");
					/*104*/p.setDvPagadoraSubsidio("0");
					   
				}
				   /*105*/p.setCentroCostoSucAgencia(datospermisolicencia.getCentroCostoSucAgencia());
							
				          p.setIsapreFonasa(datospermisolicencia.getIsapreFonasa());
				          p.setValorsss(datospermisolicencia.getValorsss());
						    p.setIdafp(datospermisolicencia.getIdafp());
						    p.setCodigosssExcaja(datospermisolicencia.getCodigosssExcaja());
						    p.setTopeseguro(datospermisolicencia.getTopeseguro());
						    p.setImponibleL(datospermisolicencia.getImponibleL());
						    p.setTopeImL(datospermisolicencia.getTopeImL());
						    p.setBaseAFC(datospermisolicencia.getBaseAFC()); 
						    p.setPensionadocotizante(datospermisolicencia.getPensionadocotizante());
						    p.setBasesanna(datospermisolicencia.getBasesanna());
						    p.setSueldo_afc(datospermisolicencia.getSueldo_afc());
						    p.setPensionado(datospermisolicencia.getPensionado());
						    p.setId_cajamutal(datospermisolicencia.getId_cajamutal());
						    p.setRutPagadoraSubsidioMutual(datospermisolicencia.getRutPagadoraSubsidioMutual());
							p.setDvPagadoraSubsidioMutual(datospermisolicencia.getDvPagadoraSubsidioMutual());
							   
							  
					
				 lista.add(p);
			 }
			 else if(count >= 1 && existeData == 1 && n_movimiento == "00" && tipo_linea == "02"){

				 Previred p = new Previred();
				 
					p.setCodTrabajador(datospermisolicencia.getCodTrabajador());
					p.setIdContrato(datospermisolicencia.getIdContrato());
					/*01*/p.setRutTrabajador(datospermisolicencia.getRutTrabajador());
					/*02*/p.setDvTrabajador(datospermisolicencia.getDvTrabajador());
					/*03*/p.setApellidoPaterno(datospermisolicencia.getApellidoPaterno());
					/*04*/p.setApellidoMaterno(datospermisolicencia.getApellidoMaterno());
					/*05*/p.setNombres(datospermisolicencia.getNombres());
					/*06*/p.setSexo(datospermisolicencia.getSexo());
					/*07*/p.setNacionalidad(datospermisolicencia.getNacionalidad());
					/*08*/p.setTipoPago(datospermisolicencia.getTipoPago());
					/*09*/p.setPeriodoDesde(datospermisolicencia.getPeriodoDesde());
					/*10*/p.setPeriodoHasta(datospermisolicencia.getPeriodoHasta());
					/*11*/p.setRegimenPrevisional(datospermisolicencia.getRegimenPrevisional());
					/*12*/p.setTipoTrabajador("0");
					/*13*/p.setDiasTrabajados("0");
					/*14*/p.setTipoLinea("01");
					/*15*//*CODIGO MOVIMIENTO PERSONAL*/
							if(rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") == 168 && rs.getInt("subtipo_licencia") == 184){
								p.setCodigoMovimientoPersonal("06");
							
							}else if (rs.getInt("accion") == 2 && rs.getInt("tipo_licencia") != 168 && rs.getInt("subtipo_licencia") != 184){
								p.setCodigoMovimientoPersonal("03");
							}
						
							// PERMISO SIN GOSE DE SUELDO
							if(rs.getInt("accion") == 4){
								p.setCodigoMovimientoPersonal("04");
							// FALTA 
							} if(rs.getInt("accion") == 3){
								p.setCodigoMovimientoPersonal("11");
							}
					/*16*//*FECHA DESDE*/
							String fechainicio = rs.getString("fecha_inicio_previred");
							String[] fechainicioSplit = fechainicio.split("-");
							p.setFechaDesde(fechainicioSplit[2]+"/"+fechainicioSplit[1]+"/"+fechainicioSplit[0]);
				    /*17*//*FECHA HASTA*/
							String fechatermino = rs.getString("fecha_fin_previred");
							String[] fechafechaterminoSplit = fechatermino.split("-");
							p.setFechaHasta(fechafechaterminoSplit[2]+"/"+fechafechaterminoSplit[1]+"/"+fechafechaterminoSplit[0]);
					/*18*/p.setTramoAsignacionFamiliar("");
					/*19*/p.setnCargasSimples("0");
					/*20*/p.setnCargasMaternales("0");
					/*21*/p.setnCargasInvalidas("0");
				    /*22*/p.setAsignacionFamiliar("0");
				    /*23*/p.setAsignacionFamiliarRetroactiva("0");
					/*24*/p.setReintegroCargasFamiliares("0");
					/*25*/p.setSolicitudTrabajadorJoven("");
					/*26*/p.setCodigoAfp("0");
					/*27*/p.setRentaImponibleAfp("0");
					/*28*/p.setCotizacionObligatoriaAfp("0");
				    /*29*/p.setCotizacionSeguroInvalidezSobrevivencia("0");
				    /*30*/p.setCuentaAhorroVoluntarioAfp("0");
				    /*31*/p.setRentaImponibleSustAfp("0");
				    /*32*/p.setTasaPactadaSustit("0");
				    /*33*/p.setAporteIndemnSustit("0");
				    /*34*/p.setnPeriodosSustit("0");
				    /*35*/p.setPreiodoDesdeSustit("");
				    /*36*/p.setPeriosoHastaSustit("");
				    /*37*/p.setPuestoTrabajoPesado("");
				    /*38*/p.setPorcCotizacionTrabajoPesado("0");
				    /*39*/p.setCotizacionTrabajoPesado("0");
				    /*40*/p.setCodigoInstitucionApvi("0");
				    /*41*/p.setNumeroContratoApvi("");
				    /*42*/p.setFormaPagoApvi("0");
				    /*43*/p.setCotizacionApvi("0");
				    /*44*/p.setCotizacionDepositosConvenidos("0");
				    /*45*/p.setCodigoIntitucionAutorizadaApvc("0");
				    /*46*/p.setNumeroContratoApvc("");
				    /*47*/p.setFormaPagoApvc("0");
				    /*48*/p.setCotizacionTrabajadorApvc("0");
				    /*49*/p.setCotizacionEmpleadorApvc("0");
				    /*50*/p.setRutAfiliadoVoluntario("0");
				    /*51*/p.setDvAfiliadoVoluntario("");
				    /*52*/p.setApellidoPaternoAfiliado("");
				    /*53*/p.setApellidoMaternoAfiliado("");
				    /*54*/p.setNombresAfiliado("");
				    /*55*/p.setCodigoMovimientoPersonalAfiliado("00");
				    /*56*/p.setFechaDesdeAfililado("");
				    /*57*/p.setFechaHastaAfiliado("");
				    /*58*/p.setCodigoAfpAfiliado("0");
				    /*59*/p.setMontoCapitalizacionVoluntaria("0");
				    /*60*/p.setMontoAhorroVoluntarioAfiliado("0");
				    /*61*/p.setNumPeriodosCotizacionAfiliado("0");
				    /*62*/p.setCodigoExCajaRegimen("0");
				    /*63*/p.setTasaCotizacionExCajaRegimen("0");
				    /*64*/p.setRentaImponibleIPS("0");
				    /*65*/p.setCotizacionObligatoriaIPS("0");
				    /*66*/p.setRentaImponibleDesahucion("0");
				    /*67*/p.setCodigoExCajaRegimenDesahucion("0");
				    /*68*/p.setTasaCotDeshaExCajaPrevision("0");
				    /*69*/p.setCotizacionDesahucion("0");
				    /*70*/p.setCotizacionFonasa("0");
				    /*71*/p.setCotizacionAccidenteTrabajoISL("0");
				    /*72*/p.setBonificacionLey15386("0");
				    /*73*/p.setDescuentoCargasFamiliaresISL("0");
				    /*74*/p.setBonoGobierno("0");
				    /*75*/p.setCodigoInstitucionSalud("0");
				    /*76*/p.setNumeroFUN("");
				    /*77*/p.setRentaImponibleIsapre("0");
				    /*78*/p.setMondedaPlanPactadoIsapre("0");
				    /*79*/p.setCotizacionPactada("0");
				    /*80*/p.setCotizacionObligatoriaIsapre("0");
				    /*81*/p.setCotizacionAdicionalVoluntaria("0");
				    /*82*/p.setMontoGarantiaGES("0");
				    /*83*/p.setCodigoCCAF("0");
				    /*84*/p.setRentaImponibleCCAF("0");
				    /*85*/p.setCreditosPersonalesCCAF("0");
				    /*86*/p.setDescuentoDentalCCAF("0");
				    /*87*/p.setDescuentosLeasing(datospermisolicencia.getDescuentosLeasing());
				    /*88*/p.setDescuentosSeguroVidaCCAF(datospermisolicencia.getDescuentosSeguroVidaCCAF());
				    /*89*/p.setOtrosDescuentosCCAF("0");
				    /*90*/p.setCotizacionCCAFNoAfilIsapres("0");
				    /*91*/p.setDescCargasFamiliaresCCAF("0");
				    /*92*/p.setOtrosDescuentosCCAF1("0");
				    /*93*/p.setOtrosDescuentosCCAF2("0");
				    /*94*/p.setBonoGobiernoCCAF("0");
				    /*95*/p.setCodigoSucursalCCAF("");
				    /*96*/p.setCodigoMutualidad("0");
				    /*97*/p.setReantaImponibleMutualidad("0");
				    /*98*/p.setCotizacionAccidenteTrabajo("0");
				    /*99*/p.setSucursalPagoMutual("0");
				   /*100*/p.setRentaImponibleSeguroCesantia("0");
				   /*101*/p.setAporteTrabajadorSeguroCesantia("0");
				   /*102*/p.setAporteEmpleadorSeguroCesantia("0");
				   /*103*/p.setRutPagadoraSubsidio(datospermisolicencia.getRutPagadoraSubsidio());
				   /*104*/p.setDvPagadoraSubsidio(datospermisolicencia.getDvPagadoraSubsidio());
				   /*105*/p.setCentroCostoSucAgencia(datospermisolicencia.getCentroCostoSucAgencia());
					
				          p.setIsapreFonasa(datospermisolicencia.getIsapreFonasa());
				          p.setTopeImL("0");
				          p.setImponibleL("0");
				          p.setValorsss(datospermisolicencia.getValorsss());
						    p.setIdafp(datospermisolicencia.getIdafp());
						    p.setCodigosssExcaja(datospermisolicencia.getCodigosssExcaja());
						    p.setTopeseguro(datospermisolicencia.getTopeseguro());
						    p.setBaseAFC(datospermisolicencia.getBaseAFC());
						    p.setPensionadocotizante(datospermisolicencia.getPensionadocotizante());
						    p.setBasesanna(datospermisolicencia.getBasesanna());
						    p.setSueldo_afc(datospermisolicencia.getSueldo_afc());
						    p.setPensionado(datospermisolicencia.getPensionado());
						    p.setId_cajamutal(datospermisolicencia.getId_cajamutal());
						    p.setRutPagadoraSubsidioMutual(datospermisolicencia.getRutPagadoraSubsidioMutual());
							p.setDvPagadoraSubsidioMutual(datospermisolicencia.getDvPagadoraSubsidioMutual());
							   
							  
					
						    lista.add(p);
			 }
				
				count++;
				
			} // END WHILE
			
			sql2 = "  SELECT "
					    +"idsw_movimientoPrevired,"
					    +"cod_trabajador,"
					    +"id_contrato,"
					    +"periodo,"
					    +"IF(movimento_previred = 7,"
					        +"IF((SELECT "
					                    +"tipoContrato "
					                +"FROM "
					                    +"contratos "
					                +"WHERE "
					                    +"codigo_trabajador = "+cod+" AND id = "+idcont+") = 2,"
					            +"5,"
					                +"movimento_previred),"
					        +"movimento_previred) AS movimento_previred,"
					    +"id_sociedad, "
					    +"fecha_movimiento,"
					    +"fecha_termino "
					+"FROM "
					    +"sw_movimientoPrevired "
					+"WHERE "
					    +"cod_trabajador = "+cod+" "
					        +"AND id_contrato = "+idcont+" "
					        +"AND periodo = "+periodo+" "
					        +"AND id_sociedad = "+empresa+" ";
			
			ps2 = db.prepareStatement(sql2);
			ResultSet rs2 = ps.executeQuery(sql2);
			
			/// si el trabajador no tiene movimiento en permisos y licencia se mantiene la linea base 
			if(!rs2.isBeforeFirst()){ 
	                existeData2 = 0;
			}else{
	        	   existeData2 = 1;
	               }  

			
			int count2 = 0;
			while(rs2.next()){
				
		 
			if(count2 == 0 && existeData2 == 1 && lista.get(0).getCodigoMovimientoPersonal() == "00"){
				    
					Previred p = new Previred();
                    
					int codMovInt = rs2.getInt("movimento_previred");
					String cod_mov= Integer.toString(codMovInt);
					/*15*/p.setCodigoMovimientoPersonal(cod_mov);
					lista.get(0).setCodigoMovimientoPersonal(cod_mov);
					
					/*16*//*FECHA DESDE*/
					String fechainicio = rs2.getString("fecha_movimiento");
					if(fechainicio == "" || fechainicio == null){
						
						lista.get(0).setFechaDesde("");
					}
					else{
						String[] fechainicioSplit = fechainicio.split("-");
						lista.get(0).setFechaDesde(fechainicioSplit[2]+"/"+fechainicioSplit[1]+"/"+fechainicioSplit[0]);
					}
					
					/*17*//*FECHA HASTA*/
					String fechatermino = rs2.getString("fecha_termino");
					if(fechatermino == "" || fechatermino == null){
						lista.get(0).setFechaHasta("");
					}
					else{
						String[] fechafechaterminoSplit = fechatermino.split("-");
						lista.get(0).setFechaHasta(fechafechaterminoSplit[2]+"/"+fechafechaterminoSplit[1]+"/"+fechafechaterminoSplit[0]);
						
					}
	
					 }
			else if(count2 >= 1 && existeData2 == 1 && lista.get(0).getCodigoMovimientoPersonal() == "00"){
				
				Previred p = new Previred();
				
				p.setCodTrabajador(datospermisolicencia.getCodTrabajador());
				p.setIdContrato(datospermisolicencia.getIdContrato());
				/*01*/p.setRutTrabajador(datospermisolicencia.getRutTrabajador());
				/*02*/p.setDvTrabajador(datospermisolicencia.getDvTrabajador());
				/*03*/p.setApellidoPaterno(datospermisolicencia.getApellidoPaterno());
				/*04*/p.setApellidoMaterno(datospermisolicencia.getApellidoMaterno());
				/*05*/p.setNombres(datospermisolicencia.getNombres());
				/*06*/p.setSexo(datospermisolicencia.getSexo());
				/*07*/p.setNacionalidad(datospermisolicencia.getNacionalidad());
				/*08*/p.setTipoPago(datospermisolicencia.getTipoPago());
				/*09*/p.setPeriodoDesde(datospermisolicencia.getPeriodoDesde());
				/*10*/p.setPeriodoHasta(datospermisolicencia.getPeriodoHasta());
				/*11*/p.setRegimenPrevisional(datospermisolicencia.getRegimenPrevisional());
				/*12*/p.setTipoTrabajador("0");
				/*13*/p.setDiasTrabajados("0");
				/*14*/p.setTipoLinea("01");
				/*15*//*CODIGO MOVIMIENTO PERSONAL*/
				int codMovInt = rs2.getInt("movimento_previred");
				String cod_mov= Integer.toString(codMovInt);
				/*15*/p.setCodigoMovimientoPersonal(cod_mov);
				lista.get(0).setCodigoMovimientoPersonal(cod_mov);
				
				/*16*//*FECHA DESDE*/
				String fechainicio = rs2.getString("fecha_movimiento");
				if(fechainicio == "" || fechainicio == null){
					
					lista.get(0).setFechaDesde("");
				}
				else{
					String[] fechainicioSplit = fechainicio.split("-");
					lista.get(0).setFechaDesde(fechainicioSplit[2]+"/"+fechainicioSplit[1]+"/"+fechainicioSplit[0]);
				}
				
				/*17*//*FECHA HASTA*/
				String fechatermino = rs2.getString("fecha_termino");
				if(fechatermino == "" || fechatermino == null){
					lista.get(0).setFechaHasta("");
				}
				else{
					String[] fechafechaterminoSplit = fechatermino.split("-");
					lista.get(0).setFechaHasta(fechafechaterminoSplit[2]+"/"+fechafechaterminoSplit[1]+"/"+fechafechaterminoSplit[0]);
					
				}
				/*18*/p.setTramoAsignacionFamiliar("");
				/*19*/p.setnCargasSimples("0");
				/*20*/p.setnCargasMaternales("0");
				/*21*/p.setnCargasInvalidas("0");
			    /*22*/p.setAsignacionFamiliar("0");
			    /*23*/p.setAsignacionFamiliarRetroactiva("0");
				/*24*/p.setReintegroCargasFamiliares("0");
				/*25*/p.setSolicitudTrabajadorJoven("");
				/*26*/p.setCodigoAfp("0");
				/*27*/p.setRentaImponibleAfp("0");
				/*28*/p.setCotizacionObligatoriaAfp("0");
			    /*29*/p.setCotizacionSeguroInvalidezSobrevivencia("0");
			    /*30*/p.setCuentaAhorroVoluntarioAfp("0");
			    /*31*/p.setRentaImponibleSustAfp("0");
			    /*32*/p.setTasaPactadaSustit("0");
			    /*33*/p.setAporteIndemnSustit("0");
			    /*34*/p.setnPeriodosSustit("0");
			    /*35*/p.setPreiodoDesdeSustit("");
			    /*36*/p.setPeriosoHastaSustit("");
			    /*37*/p.setPuestoTrabajoPesado("");
			    /*38*/p.setPorcCotizacionTrabajoPesado("0");
			    /*39*/p.setCotizacionTrabajoPesado("0");
			    /*40*/p.setCodigoInstitucionApvi("0");
			    /*41*/p.setNumeroContratoApvi("");
			    /*42*/p.setFormaPagoApvi("0");
			    /*43*/p.setCotizacionApvi("0");
			    /*44*/p.setCotizacionDepositosConvenidos("0");
			    /*45*/p.setCodigoIntitucionAutorizadaApvc("0");
			    /*46*/p.setNumeroContratoApvc("");
			    /*47*/p.setFormaPagoApvc("0");
			    /*48*/p.setCotizacionTrabajadorApvc("0");
			    /*49*/p.setCotizacionEmpleadorApvc("0");
			    /*50*/p.setRutAfiliadoVoluntario("0");
			    /*51*/p.setDvAfiliadoVoluntario("");
			    /*52*/p.setApellidoPaternoAfiliado("");
			    /*53*/p.setApellidoMaternoAfiliado("");
			    /*54*/p.setNombresAfiliado("");
			    /*55*/p.setCodigoMovimientoPersonalAfiliado("00");
			    /*56*/p.setFechaDesdeAfililado("");
			    /*57*/p.setFechaHastaAfiliado("");
			    /*58*/p.setCodigoAfpAfiliado("0");
			    /*59*/p.setMontoCapitalizacionVoluntaria("0");
			    /*60*/p.setMontoAhorroVoluntarioAfiliado("0");
			    /*61*/p.setNumPeriodosCotizacionAfiliado("0");
			    /*62*/p.setCodigoExCajaRegimen("0");
			    /*63*/p.setTasaCotizacionExCajaRegimen("0");
			    /*64*/p.setRentaImponibleIPS("0");
			    /*65*/p.setCotizacionObligatoriaIPS("0");
			    /*66*/p.setRentaImponibleDesahucion("0");
			    /*67*/p.setCodigoExCajaRegimenDesahucion("0");
			    /*68*/p.setTasaCotDeshaExCajaPrevision("0");
			    /*69*/p.setCotizacionDesahucion("0");
			    /*70*/p.setCotizacionFonasa("0");
			    /*71*/p.setCotizacionAccidenteTrabajoISL("0");
			    /*72*/p.setBonificacionLey15386("0");
			    /*73*/p.setDescuentoCargasFamiliaresISL("0");
			    /*74*/p.setBonoGobierno("0");
			    /*75*/p.setCodigoInstitucionSalud("0");
			    /*76*/p.setNumeroFUN("");
			    /*77*/p.setRentaImponibleIsapre("0");
			    /*78*/p.setMondedaPlanPactadoIsapre("0");
			    /*79*/p.setCotizacionPactada("0");
			    /*80*/p.setCotizacionObligatoriaIsapre("0");
			    /*81*/p.setCotizacionAdicionalVoluntaria("0");
			    /*82*/p.setMontoGarantiaGES("0");
			    /*83*/p.setCodigoCCAF("0");
			    /*84*/p.setRentaImponibleCCAF("0");
			    /*85*/p.setCreditosPersonalesCCAF("0");
			    /*86*/p.setDescuentoDentalCCAF("0");
			    /*87*/p.setDescuentosLeasing(datospermisolicencia.getDescuentosLeasing());
			    /*88*/p.setDescuentosSeguroVidaCCAF(datospermisolicencia.getDescuentosSeguroVidaCCAF());
			    /*89*/p.setOtrosDescuentosCCAF("0");
			    /*90*/p.setCotizacionCCAFNoAfilIsapres("0");
			    /*91*/p.setDescCargasFamiliaresCCAF("0");
			    /*92*/p.setOtrosDescuentosCCAF1("0");
			    /*93*/p.setOtrosDescuentosCCAF2("0");
			    /*94*/p.setBonoGobiernoCCAF("0");
			    /*95*/p.setCodigoSucursalCCAF("");
			    /*96*/p.setCodigoMutualidad("0");
			    /*97*/p.setReantaImponibleMutualidad("0");
			    /*98*/p.setCotizacionAccidenteTrabajo("0");
			    /*99*/p.setSucursalPagoMutual("0");
			   /*100*/p.setRentaImponibleSeguroCesantia("0");
			   /*101*/p.setAporteTrabajadorSeguroCesantia("0");
			   /*102*/p.setAporteEmpleadorSeguroCesantia("0");
			   /*103*/p.setRutPagadoraSubsidio(datospermisolicencia.getRutPagadoraSubsidio());
			   /*104*/p.setDvPagadoraSubsidio(datospermisolicencia.getDvPagadoraSubsidio());
			   /*105*/p.setCentroCostoSucAgencia(datospermisolicencia.getCentroCostoSucAgencia());
			   p.setIsapreFonasa(datospermisolicencia.getIsapreFonasa());
			   p.setTopeImL(datospermisolicencia.getTopeImL());
	           p.setImponibleL(datospermisolicencia.getImponibleL());
	           p.setValorsss(datospermisolicencia.getValorsss());
			    p.setIdafp(datospermisolicencia.getIdafp());
			    p.setCodigosssExcaja(datospermisolicencia.getCodigosssExcaja());
			    p.setTopeseguro(datospermisolicencia.getTopeseguro());
			    p.setBaseAFC(datospermisolicencia.getBaseAFC());
			    p.setPensionadocotizante(datospermisolicencia.getPensionadocotizante());
			    p.setBasesanna(datospermisolicencia.getBasesanna());
			    p.setSueldo_afc(datospermisolicencia.getSueldo_afc());
			    p.setPensionado(datospermisolicencia.getPensionado());
			    p.setId_cajamutal(datospermisolicencia.getId_cajamutal());
			    p.setRutPagadoraSubsidioMutual(datospermisolicencia.getRutPagadoraSubsidioMutual());
				p.setDvPagadoraSubsidioMutual(datospermisolicencia.getDvPagadoraSubsidioMutual());
			   
			   lista.add(p);
			}
			
			else if(existeData2 == 1 && lista.get(0).getCodigoMovimientoPersonal() != "00"){
				
				Previred p = new Previred();
			
				p.setCodTrabajador(datospermisolicencia.getCodTrabajador());
				p.setIdContrato(datospermisolicencia.getIdContrato());
				/*01*/p.setRutTrabajador(datospermisolicencia.getRutTrabajador());
				/*02*/p.setDvTrabajador(datospermisolicencia.getDvTrabajador());
				/*03*/p.setApellidoPaterno(datospermisolicencia.getApellidoPaterno());
				/*04*/p.setApellidoMaterno(datospermisolicencia.getApellidoMaterno());
				/*05*/p.setNombres(datospermisolicencia.getNombres());
				/*06*/p.setSexo(datospermisolicencia.getSexo());
				/*07*/p.setNacionalidad(datospermisolicencia.getNacionalidad());
				/*08*/p.setTipoPago(datospermisolicencia.getTipoPago());
				/*09*/p.setPeriodoDesde(datospermisolicencia.getPeriodoDesde());
				/*10*/p.setPeriodoHasta(datospermisolicencia.getPeriodoHasta());
				/*11*/p.setRegimenPrevisional(datospermisolicencia.getRegimenPrevisional());
				/*12*/p.setTipoTrabajador("0");
				/*13*/p.setDiasTrabajados("0");
				/*14*/p.setTipoLinea("01");
				
				int codMovInt = rs2.getInt("movimento_previred");
				
				String cod_mov= Integer.toString(codMovInt);
				/*15*/p.setCodigoMovimientoPersonal(cod_mov);
				
				/*16*//*FECHA DESDE*/
				String fechainicio = rs2.getString("fecha_movimiento");
				
				if(fechainicio == null){
					p.setFechaDesde("");	
				}
				else{
					String[] fechainicioSplit = fechainicio.split("-");
					p.setFechaDesde(fechainicioSplit[2]+"/"+fechainicioSplit[1]+"/"+fechainicioSplit[0]);
				}
				
				/*17*//*FECHA HASTA*/
				String fechatermino = rs2.getString("fecha_termino");
				if(fechatermino == null){
					
					p.setFechaHasta("");
				}
				else{
					String[] fechafechaterminoSplit = fechatermino.split("-");
					p.setFechaHasta(fechafechaterminoSplit[2]+"/"+fechafechaterminoSplit[1]+"/"+fechafechaterminoSplit[0]);
					
				}
				
				/*18*/p.setTramoAsignacionFamiliar("");
				/*19*/p.setnCargasSimples("0");
				/*20*/p.setnCargasMaternales("0");
				/*21*/p.setnCargasInvalidas("0");
			    /*22*/p.setAsignacionFamiliar("0");
			    /*23*/p.setAsignacionFamiliarRetroactiva("0");
				/*24*/p.setReintegroCargasFamiliares("0");
				/*25*/p.setSolicitudTrabajadorJoven("");
				/*26*/p.setCodigoAfp("0");
				/*27*/p.setRentaImponibleAfp(datospermisolicencia.getRentaImponibleAfp());
				/*28*/p.setCotizacionObligatoriaAfp("0");
			    /*29*/p.setCotizacionSeguroInvalidezSobrevivencia("0");
			    /*30*/p.setCuentaAhorroVoluntarioAfp("0");
			    /*31*/p.setRentaImponibleSustAfp("0");
			    /*32*/p.setTasaPactadaSustit("0");
			    /*33*/p.setAporteIndemnSustit("0");
			    /*34*/p.setnPeriodosSustit("0");
			    /*35*/p.setPreiodoDesdeSustit("");
			    /*36*/p.setPeriosoHastaSustit("");
			    /*37*/p.setPuestoTrabajoPesado("");
			    /*38*/p.setPorcCotizacionTrabajoPesado("0");
			    /*39*/p.setCotizacionTrabajoPesado("0");
			    /*40*/p.setCodigoInstitucionApvi("0");
			    /*41*/p.setNumeroContratoApvi("");
			    /*42*/p.setFormaPagoApvi("0");
			    /*43*/p.setCotizacionApvi("0");
			    /*44*/p.setCotizacionDepositosConvenidos("0");
			    /*45*/p.setCodigoIntitucionAutorizadaApvc("0");
			    /*46*/p.setNumeroContratoApvc("");
			    /*47*/p.setFormaPagoApvc("0");
			    /*48*/p.setCotizacionTrabajadorApvc("0");
			    /*49*/p.setCotizacionEmpleadorApvc("0");
			    /*50*/p.setRutAfiliadoVoluntario("0");
			    /*51*/p.setDvAfiliadoVoluntario("");
			    /*52*/p.setApellidoPaternoAfiliado("");
			    /*53*/p.setApellidoMaternoAfiliado("");
			    /*54*/p.setNombresAfiliado("");
			    /*55*/p.setCodigoMovimientoPersonalAfiliado("00");
			    /*56*/p.setFechaDesdeAfililado("");
			    /*57*/p.setFechaHastaAfiliado("");
			    /*58*/p.setCodigoAfpAfiliado("0");
			    /*59*/p.setMontoCapitalizacionVoluntaria("0");
			    /*60*/p.setMontoAhorroVoluntarioAfiliado("0");
			    /*61*/p.setNumPeriodosCotizacionAfiliado("0");
			    /*62*/p.setCodigoExCajaRegimen("0");
			    /*63*/p.setTasaCotizacionExCajaRegimen("0");
			    /*64*/p.setRentaImponibleIPS("0");
			    /*65*/p.setCotizacionObligatoriaIPS("0");
			    /*66*/p.setRentaImponibleDesahucion("0");
			    /*67*/p.setCodigoExCajaRegimenDesahucion("0");
			    /*68*/p.setTasaCotDeshaExCajaPrevision("0");
			    /*69*/p.setCotizacionDesahucion("0");
			    /*70*/p.setCotizacionFonasa("0");
			    /*71*/p.setCotizacionAccidenteTrabajoISL("0");
			    /*72*/p.setBonificacionLey15386("0");
			    /*73*/p.setDescuentoCargasFamiliaresISL("0");
			    /*74*/p.setBonoGobierno("0");
			    /*75*/p.setCodigoInstitucionSalud("0");
			    /*76*/p.setNumeroFUN("");
			    /*77*/p.setRentaImponibleIsapre("0");
			    /*78*/p.setMondedaPlanPactadoIsapre("0");
			    /*79*/p.setCotizacionPactada("0");
			    /*80*/p.setCotizacionObligatoriaIsapre("0");
			    /*81*/p.setCotizacionAdicionalVoluntaria("0");
			    /*82*/p.setMontoGarantiaGES("0");
			    /*83*/p.setCodigoCCAF("0");
			    /*84*/p.setRentaImponibleCCAF("0");
			    /*85*/p.setCreditosPersonalesCCAF("0");
			    /*86*/p.setDescuentoDentalCCAF("0");
			    /*87*/p.setDescuentosLeasing(datospermisolicencia.getDescuentosLeasing());
			    /*88*/p.setDescuentosSeguroVidaCCAF(datospermisolicencia.getDescuentosSeguroVidaCCAF());
			    /*89*/p.setOtrosDescuentosCCAF("0");
			    /*90*/p.setCotizacionCCAFNoAfilIsapres("0");
			    /*91*/p.setDescCargasFamiliaresCCAF("0");
			    /*92*/p.setOtrosDescuentosCCAF1("0");
			    /*93*/p.setOtrosDescuentosCCAF2("0");
			    /*94*/p.setBonoGobiernoCCAF("0");
			    /*95*/p.setCodigoSucursalCCAF("");
			    /*96*/p.setCodigoMutualidad("0");
			    /*97*/p.setReantaImponibleMutualidad("0");
			    /*98*/p.setCotizacionAccidenteTrabajo("0");
			    /*99*/p.setSucursalPagoMutual("0");
			   /*100*/p.setRentaImponibleSeguroCesantia("0");
			   /*101*/p.setAporteTrabajadorSeguroCesantia("0");
			   /*102*/p.setAporteEmpleadorSeguroCesantia("0");
			   /*103*/p.setRutPagadoraSubsidio(datospermisolicencia.getRutPagadoraSubsidio());
			   /*104*/p.setDvPagadoraSubsidio(datospermisolicencia.getDvPagadoraSubsidio());
			   /*105*/p.setCentroCostoSucAgencia(datospermisolicencia.getCentroCostoSucAgencia());
			          p.setIsapreFonasa(datospermisolicencia.getIsapreFonasa());
			          
			   
			          	p.setImponibleL(datospermisolicencia.getImponibleL());
			          	p.setTopeImL(datospermisolicencia.getTopeImL());
			          	p.setValorsss(datospermisolicencia.getValorsss());
					    p.setIdafp(datospermisolicencia.getIdafp());
					    p.setCodigosssExcaja(datospermisolicencia.getCodigosssExcaja());
					    p.setTopeseguro(datospermisolicencia.getTopeseguro());
					    p.setBaseAFC(datospermisolicencia.getBaseAFC());
					    p.setPensionadocotizante(datospermisolicencia.getPensionadocotizante());
					    p.setBasesanna(datospermisolicencia.getBasesanna());
					    p.setSueldo_afc(datospermisolicencia.getSueldo_afc());
					    p.setPensionado(datospermisolicencia.getPensionado());
					    p.setId_cajamutal(datospermisolicencia.getId_cajamutal());
					    p.setRutPagadoraSubsidioMutual(datospermisolicencia.getRutPagadoraSubsidioMutual());
						p.setDvPagadoraSubsidioMutual(datospermisolicencia.getDvPagadoraSubsidioMutual());
					    
				 lista.add(p);
			}
			
			count2++;
				 }

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}

public List<LiquidacionPeriodo> obtenerLiquidacionPeriodotablaLiquidacion(String periodo, String empresa) throws Exception {
		
	
	

		PreparedStatement ps = null;
		String sql="";
		List<LiquidacionPeriodo> lista = new ArrayList<>();
		try{
		    sql = "select cod_trabajador,id_contrato from sw_liquidacion where periodo = "+periodo+" and id_sociedad = "+empresa+"  order by cod_trabajador,id_contrato asc";
		    
			db = ConexionBD.getConnection();
			ps = db.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()){
				LiquidacionPeriodo p = new LiquidacionPeriodo();
				
				p.setCodTrabajador(rs.getInt("cod_trabajador"));
				p.setIdContrato(rs.getInt("id_contrato"));
				lista.add(p);
			}

		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return lista;	
	}

}
