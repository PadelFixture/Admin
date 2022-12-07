package lib.db.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lib.ClassSASW.CentroCosto;
import lib.classSW.HaberDescuento;
import lib.classSW.LibroRemuneraciones;
import lib.db.ConnectionDB;
import lib.db.SASW.CentroCostoDB;
import wordCreator.utils;

public class LibroSIIDB {
	

	public static ArrayList<LibroRemuneraciones> crearLibro(String empresa, int periodo, String Huerto, int rolPrivado) throws SQLException {
		ArrayList<HaberDescuento> haberesImponibles=HaberDescuentoDB.getHaberImponibleUtilizado(periodo);
		ArrayList<HaberDescuento> haberesNoImponibles=HaberDescuentoDB.getHaberNoImponibleUtilizado(periodo);
		ArrayList<HaberDescuento> Descuentos=HaberDescuentoDB.getDescuentosUtilizados(periodo);
		ArrayList<HaberDescuento> costoempresa=HaberDescuentoDB.getCostoEmpresaUtilizados(periodo);
		
		// haberes y descuento finiquito
	    ArrayList<HaberDescuento> haberesImponiblesFiniquito=HaberDescuentoDB.getHaberesydescuentoFiniquito(periodo);
		ArrayList<HaberDescuento> haberesNoImponiblesFiniquito=HaberDescuentoDB.getHaberesyDescuentoNoImponibleFiniquito(periodo);
	    ArrayList<HaberDescuento> DescuentosFiniquito=HaberDescuentoDB.getDescuentosUtilizadosFiniquito(periodo);
		ArrayList<HaberDescuento> costoempresaFiniquito=HaberDescuentoDB.getCostoEmpresaUtilizadosFiniquito(periodo);
		
		String camposSql="";
		String HIUS="";
		String camposSql2="";
		String camposSql5="";
		String HNIUS="";
		String camposSql3="";
		String camposSql4 = "";
		String camposSql8 = "";
		String camposSql6 = "";
		String camposSql7 = "";
		String DUS="";
		String COE = "";
		
		// haberes no imponibles finiquito
		String HDF_HN = "";
		// haberes finiquito
		String HDF_H = "";
		// descuentos finiquito
		String HDF_D = "";
		// costo empresa finiquito
		String HDF_CE = "";
		
		for(int i=0;i<haberesImponibles.size();i++){
			camposSql=camposSql+", c"+haberesImponibles.get(i).getCodigo();
			
			if(haberesImponibles.get(i).getCodigo() == 1003 || haberesImponibles.get(i).getCodigo() == 1034 ){
				HIUS=HIUS+","+"SUM(CASE WHEN idConcepto = 4 and sw_liquidacionDetalle.descripcion in ( (SELECT descripcion FROM sw_p_haberesDescuentos WHERE codigo = "+haberesImponibles.get(i).getCodigo()+")  ,  CONCAT('',(SELECT descripcion FROM sw_p_haberesDescuentos WHERE codigo = "+haberesImponibles.get(i).getCodigo()+"),'180'  )) THEN valor ELSE 0 END)  c"+haberesImponibles.get(i).getCodigo()+" ";
				
			}
			else if(haberesImponibles.get(i).getCodigo() == 1023){
				HIUS=HIUS+","+"SUM(CASE WHEN idConcepto = 4 and sw_liquidacionDetalle.descripcion in ( (SELECT descripcion FROM sw_p_haberesDescuentos WHERE codigo = "+haberesImponibles.get(i).getCodigo()+")  ,  CONCAT((select descripcion from sw_p_haberesDescuentos where codigo = "+haberesImponibles.get(i).getCodigo()+"),'182'),CONCAT((select descripcion from sw_p_haberesDescuentos where codigo = "+haberesImponibles.get(i).getCodigo()+"),'180')) THEN valor ELSE 0 END)  c"+haberesImponibles.get(i).getCodigo()+" ";
			}
			else if(haberesImponibles.get(i).getCodigo() == 1010){
				HIUS=HIUS+","+"SUM(CASE WHEN idConcepto = 4 and sw_liquidacionDetalle.descripcion in ( (SELECT descripcion FROM sw_p_haberesDescuentos WHERE codigo = "+haberesImponibles.get(i).getCodigo()+")  ,  CONCAT((select descripcion from sw_p_haberesDescuentos where codigo = "+haberesImponibles.get(i).getCodigo()+"),'182')) THEN valor ELSE 0 END)  c"+haberesImponibles.get(i).getCodigo()+" ";
			}
			else if(haberesImponibles.get(i).getCodigo() == 1034){
				HIUS=HIUS+","+"SUM(CASE WHEN idConcepto = 4 and sw_liquidacionDetalle.descripcion in ( (SELECT descripcion FROM sw_p_haberesDescuentos WHERE codigo = "+haberesImponibles.get(i).getCodigo()+")  ,  CONCAT((select descripcion from sw_p_haberesDescuentos where codigo = "+haberesImponibles.get(i).getCodigo()+"),'182'),CONCAT((select descripcion from sw_p_haberesDescuentos where codigo = "+haberesImponibles.get(i).getCodigo()+"),'180')) THEN valor ELSE 0 END)  c"+haberesImponibles.get(i).getCodigo()+" ";
			}
			else{
				HIUS=HIUS+","+"SUM(CASE WHEN idConcepto = 4 and sw_liquidacionDetalle.descripcion LIKE CONCAT('%', (select descripcion from sw_p_haberesDescuentos where codigo = "+haberesImponibles.get(i).getCodigo()+") , '%') THEN valor ELSE 0 END)  c"+haberesImponibles.get(i).getCodigo()+" ";
			}
			
			
			
			
			
		}
		
		for(int i=0;i<costoempresa.size();i++){
			camposSql4=camposSql4+", c"+costoempresa.get(i).getCodigo();
			COE=COE+", "+"sum(case when codigo_hd='"+costoempresa.get(i).getCodigo()+"' then sw_haberesDescuentos.monto Else 0 END) c"+costoempresa.get(i).getCodigo()+"";
		}

		for(int j=0;j<haberesNoImponibles.size();j++){
			camposSql2=camposSql2+", c"+haberesNoImponibles.get(j).getCodigo();
			HNIUS=HNIUS+", "+"sum(case when codigo_hd='"+haberesNoImponibles.get(j).getCodigo()+"' then sw_haberesDescuentos.monto Else 0 END) c"+haberesNoImponibles.get(j).getCodigo()+"";
		}
	    
		
		
		// descuentos 
		for(int k=0;k<Descuentos.size();k++){
			
			 String nombreconcepU= Descuentos.get(k).getDescripcion().replace(' ', '_');
			 String nombreconcepU2 = nombreconcepU.replaceAll("\\.", "");
		   
		        
			camposSql3=camposSql3+", c"+nombreconcepU2;
			
			if(Descuentos.get(k).getCodigo() == 3011  ){
				DUS=DUS+" "+"SUM(CASE WHEN idConcepto = 45  THEN valor ELSE 0 END)  c"+nombreconcepU2+",";
				
			}else{
			DUS=DUS+" "+"SUM(CASE WHEN idConcepto = 45 and sw_liquidacionDetalle.descripcion = '"+Descuentos.get(k).getDescripcion()+"' THEN valor ELSE 0 END)  c"+nombreconcepU2+",";
			}
			}
	        
		
		

		
		// haberes no imponibles finiquitos
		for(int i=0;i<haberesNoImponiblesFiniquito.size();i++){
			camposSql5=camposSql5+", hdf_tipo_hn"+haberesNoImponiblesFiniquito.get(i).getCodigo();
			HDF_HN=HDF_HN+", "+"sum(case when codigo_hd='"+haberesNoImponiblesFiniquito.get(i).getCodigo()+"' then sw_haberesDescuentosFiniquito.monto Else 0 END) hdf_tipo_hn"+haberesNoImponiblesFiniquito.get(i).getCodigo()+"";
		}
		
		// haberes finiquito
			for(int i=0;i<haberesImponiblesFiniquito.size();i++){
				camposSql6=camposSql6+", hdf_tipo_h"+haberesImponiblesFiniquito.get(i).getCodigo();
				HDF_H=HDF_H+", "+"sum(case when codigo_hd='"+haberesImponiblesFiniquito.get(i).getCodigo()+"' then sw_haberesDescuentosFiniquito.monto Else 0 END) hdf_tipo_h"+haberesImponiblesFiniquito.get(i).getCodigo()+"";
			}
			
			// descuentos finiquito
					for(int i=0;i<DescuentosFiniquito.size();i++){
						camposSql7=camposSql7+", hdf_tipo_d"+DescuentosFiniquito.get(i).getCodigo();
						HDF_D=HDF_D+", "+"sum(case when codigo_hd='"+DescuentosFiniquito.get(i).getCodigo()+"' then sw_haberesDescuentosFiniquito.monto Else 0 END) hdf_tipo_d"+DescuentosFiniquito.get(i).getCodigo()+"";
					}
					
					// costo empresa finiquito
					for(int i=0;i<costoempresaFiniquito.size();i++){
						camposSql8=camposSql8+", hdf_tipo_ce"+costoempresaFiniquito.get(i).getCodigo();
						HDF_CE=HDF_CE+", "+"sum(case when codigo_hd='"+costoempresaFiniquito.get(i).getCodigo()+"' then sw_haberesDescuentosFiniquito.monto Else 0 END) hdf_tipo_ce"+costoempresaFiniquito.get(i).getCodigo()+"";
					}
		
		
		
		PreparedStatement ps = null;
		ConnectionDB db = new ConnectionDB();
		String sql="";
		
		ArrayList<LibroRemuneraciones> lista=new ArrayList<LibroRemuneraciones>();
		try{
			sql = 	"select distinct "
					//ceco
					+"IFNULL(contratos_per.idCECOContrato,'nulo') as ididCECOTabla_con_per,"
					//faena
					+"IFNULL(upper((select nombreFaena from sw_m_faena where idFaena =  contratos_per.idFaenaContrato)),'nulo') as idFaenaTabla_con_per, "
					// cargo
					+"IFNULL((select cargos from cargos where id_cargo = contratos_per.cargo),'nulo') as idACargoTabla_con_per,"
					// huerto
					+"IFNULL((select descripcion from campo where campo = contratos_per.idHuertoContrato),'nulo') as idAHuertoToabla_con_per, "
					+"IFNULL((select descripcion from parametros where codigo = 'AFP' and llave = trabajadores_per.idAFP),'nulo') as idAFPTabla_tra_per, "
					+"IFNULL((select descripcion from parametros where codigo = 'ISAPRE' and llave = trabajadores_per.idIsapre),'nulo') as ididIsapreTabla_tra_per, "
					+ "trabajadores.codigo as codTrabajador,IF(detaF.valor is null, 0, detaF.valor ) AS tiempo_servido, UPPER(trabajadores.apellidoPaterno) as apellidoPaterno, "+
					"UPPER(trabajadores.apellidoMaterno) as apellidoMaterno, UPPER(trabajadores.nombre) as nombreTrabajador, "+
					" CASE WHEN trabajadores.rut = '' THEN trabajadores.rutTemporal ELSE trabajadores.rut END as rut ,cargos.cargos,campo.descripcion as nombreCampo, contratos.idCECOContrato as idCECO, (select descripcion from campo where campo = contratos.idHuertoContrato) as nombrehuerto,"
					+ "upper((select nombreFaena from sw_m_faena where idFaena =  contratos.idFaenaContrato)) as faena,"
					+ "contratos.id as idContrato, "+
					"p.descripcion as nombreCuenta,"
					+ "DATE_FORMAT(liq.fecha_pago, '%d-%m-%Y') as fecha_pago,"
					+ " d.tipoTrabajador,tt.descripcion as nombreTipoTrabajor, d.diasTrabajados, d.sueldoBase2, d.bonosImponibles "+camposSql+", d.horaExtra, d.gratificacion, d.totalHaberesImponibles, "+
					"d.bonosNoImponibles"+camposSql2+", d.cargaFamiliarSimple,d.cargaFamiliarMaternal, d.cargaFamiliaresRetro, d.totalHabNoImponible, d.totalHaberes, "+
					"pa.descripcion as AFP,d.valorAFP, par.descripcion as institucionSalud, d.salud,d.caja,d.adicionalSalud,d.adicionalSaludTRIBU,d.seguroCesantiaAFCTrabajador,d.APV, papv.descripcion as nombreAPV,d.impuestoUnico, "+
					"d.sueldoBase,d.totalDescuentosImp,d.anticipos,d.descuentos "+camposSql3+",d.ahoroVoluntarioAfp,d.totalDescuentosNoImp, d.totalDescuentos, "+ 
					"(select descripcion from parametros where codigo = 'ISAPRE' and llave = d.llaveisapre) as llaveisapre, "+ 
					// nombre afp
					"(select descripcion from parametros where codigo = 'AFP' and llave = d.llaveafp) as llaveafp, "+ 
					"d.totalPago,d.totalLiquidoMes,d.cotizacionSIS,d.seguroCesantiaAFC, d.cotizacionBasica,d.cotizacionAdicional,d.baseTributable, "+
					"d.cotizacionExtraordinaria, d.SANNA, sociedad.sociedad, sociedad.denominacionSociedad, d.horasfalta,d.descripcionHfalta,"
					+ "d.horasextra, d.descripcionHextra, "
					+ "DATE_FORMAT(contratos.fechaInicio_actividad, '%d-%m-%Y') as fechaInicio_actividad,"
//					+ "if( date_format(FechaTerminoContrato, '%Y%m') = "+periodo+" ,DATE_FORMAT(FechaTerminoContrato, '%d-%m-%Y'),'') as FechaTerminoContrato, "+
					+"DATE_FORMAT(contratos.FechaTerminoContrato, '%d-%m-%Y')AS FechaTerminoContrato,"+
					"(select nombre from sw_m_articuloTerminoContrato where idArticuloTerminoContrato = contratos.articuloTerminoContrato) as articulo,"+
					"(select descripcion from sw_m_incisoTerminoContrato where idArticuloTerminoContrato = contratos.articuloTerminoContrato and idIncisoTerminoContrato = contratos.incisoTerminoContrato) as causal "+
					""+camposSql4 + camposSql5+camposSql6+camposSql7+camposSql8+",d.liquidoagro,d.bonoproduccionagro,valorFeriadoproporcional,d.costovidacamara,d.sobregiro_ "+
					" from contratos "+
					"inner join sociedad on sociedad.idSociedad=contratos.idSociedad "+
					"left join sw_haberesDescuentosFiniquito swf on contratos.codigo_trabajador=swf.codigo_trabajador "+
					"inner join cargos on contratos.cargo=cargos.id_cargo "+
					"inner join trabajadores on contratos.codigo_trabajador=trabajadores.codigo "+
					"left join trabajadores_per on trabajadores_per.codigo = trabajadores.codigo and trabajadores_per.periodo = '"+periodo+"' " +
					"LEFT JOIN contratos_per ON contratos_per.id = contratos.id  AND contratos_per.periodo = '"+periodo+"' " +
					"left join sw_m_faena on trabajadores.idFaena=sw_m_faena.idFaena "+
					"left join (select distinct(codigoTrabajador) as codigoTrabajador, idTipoCuenta from cuentaBancaria WHERE cuentaPrimaria=1) as cb on trabajadores.codigo=cb.codigoTrabajador "+
					"left join (SELECT * FROM parametros WHERE codigo='TIPO_DE_CUENTA') AS p on cb.idTipoCuenta=p.codPrevired "+
					"left join (SELECT * from sw_detalleFiniquito where idConcepto = 20000 and periodo='"+periodo+"') AS detaF ON trabajadores.codigo = detaF.codTrabajador and contratos.id = detaF.idContrato "+
					"left join (SELECT * FROM sw_liquidacion WHERE periodo='"+periodo+"') as liq on contratos.id=liq.id_contrato "+
					"left join (SELECT * FROM parametros WHERE codigo='AFP') as pa on trabajadores.idAFP=pa.llave "+
					"left join (SELECT * FROM parametros WHERE codigo='ISAPRE') as par on trabajadores.idIsapre=par.llave "+
					"inner join campo on contratos.idHuertoContrato=campo.campo "+
					
					" LEFT JOIN "+
					"(SELECT codTrabajador, idContrato, periodo "+
					HIUS+
					"FROM sw_liquidacionDetalle WHERE periodo = '"+periodo+"' "+
					"GROUP BY codTrabajador , idContrato , periodo) AS xx ON contratos.id = xx.idContrato "+
					
					"left join "+
					"(SELECT codigo_trabajador,idContrato "+
					HNIUS+COE+
					" from sw_haberesDescuentos "+
					"where periodo='"+periodo+"' or frecuencia = 182 "+
					"group by codigo_trabajador, idContrato "+
					"order by 1)as hd on contratos.id=hd.idContrato "+
					
					// haberes no imponibles  finiquito
	                 "left join (SELECT codigo_trabajador,idContrato  "+
	                 HDF_HN+
	                 " from sw_haberesDescuentosFiniquito "+
	                 "where periodo='"+periodo+"' group by codigo_trabajador, idContrato,periodo order by 1)as hd2 "+
	        		 "on contratos.id=hd2.idContrato "+
	                 
					//haberes  imponibles finiquito
					"left join (SELECT codigo_trabajador,idContrato  "+
					HDF_H+
					" from sw_haberesDescuentosFiniquito "+
					"where periodo='"+periodo+"' and tipo = 'h' group by codigo_trabajador, idContrato,periodo order by 1)as hd3 "+
					"on contratos.id=hd3.idContrato "+
					
					//descuentos finiquito
					"left join (SELECT codigo_trabajador,idContrato  "+
					HDF_D+
					" from sw_haberesDescuentosFiniquito "+
					"where periodo='"+periodo+"' and tipo = 'd' group by codigo_trabajador, idContrato,periodo order by 1)as hd4 "+
					"on contratos.id=hd4.idContrato "+
					
					//costo empresa finiquito
					"left join (SELECT codigo_trabajador,idContrato  "+
					HDF_CE+
					" from sw_haberesDescuentosFiniquito "+
					"where periodo='"+periodo+"' and tipo = 'c' group by codigo_trabajador, idContrato,periodo order by 1)as hd5 "+
					"on contratos.id=hd5.idContrato "+
					
					
					//Feriado proporcional Finiquito
					
					"left join (SELECT codTrabajador,idContrato," +
					"SUM(CASE "+
					"WHEN idConcepto = 2006 THEN sw_detalleFiniquito.valor "+
					"ELSE 0 "+ 
					"END) valorFeriadoproporcional "+
				    "from sw_detalleFiniquito "+
				    "where periodo= '"+periodo+"'  group by codTrabajador, idContrato order by 1)as hd6 "+
					"on contratos.id=hd6.idContrato "+
			 
					
					"inner join "+
					"(SELECT codTrabajador,idContrato, periodo, "+
					DUS+
					"SUM(CASE WHEN idConcepto = 0 and sw_liquidacionDetalle.descripcion  like 'Tipo Trabajador (0 FIJO, 1 INDEFINIDO)%' THEN round(valor) ELSE 0 END)  tipoTrabajador, "+
					"SUM(CASE WHEN idConcepto = 1 THEN valor ELSE 0 END)  sueldoBase, "+
					"SUM(CASE WHEN idConcepto = 2 THEN round(valor) ELSE 0 END)  diasTrabajados, "+
					"SUM(CASE WHEN idConcepto = 3 THEN valor ELSE 0 END)  sueldoBase2, "+
					"SUM(CASE WHEN idConcepto = 4 THEN valor ELSE 0 END)  bonosImponibles, "+
					"SUM(CASE WHEN idConcepto = 7 THEN valor ELSE 0 END)  horaExtra, "+
					"SUM(CASE WHEN idConcepto = 9 THEN valor ELSE 0 END)  gratificacion, "+
					"SUM(CASE WHEN idConcepto = 10 THEN valor ELSE 0 END)  totalHaberesImponibles, "+
					"SUM(CASE WHEN idConcepto = 11 THEN valor ELSE 0 END)  bonosNoImponibles, "+
					"SUM(CASE WHEN idConcepto = 15 THEN valor ELSE 0 END)  cargaFamiliarSimple, "+
					"SUM(CASE WHEN idConcepto = 14 THEN valor ELSE 0 END)  cargaFamiliarMaternal, "+
					"SUM(CASE WHEN idConcepto = 17 THEN valor ELSE 0 END)  cargaFamiliaresRetro, "+
					"SUM(CASE WHEN idConcepto = 20 THEN valor ELSE 0 END)  totalHabNoImponible, "+
					"SUM(CASE WHEN idConcepto = 21 THEN valor ELSE 0 END)  totalHaberes, "+
					"SUM(CASE WHEN idConcepto = 22 THEN valor ELSE 0 END)  baseTributable, "+
					"SUM(CASE WHEN idConcepto = 31 THEN valor ELSE 0 END)  valorAFP, "+
					"SUM(CASE WHEN idConcepto = 32 and sw_liquidacionDetalle.descripcion not like 'CAJA%' THEN valor ELSE 0 END)  salud, "+
					"SUM(CASE WHEN idConcepto = 32 and sw_liquidacionDetalle.descripcion  like 'CAJA%' THEN valor ELSE 0 END)  caja, "+
					"SUM(CASE WHEN idConcepto = 32 and sw_liquidacionDetalle.descripcion  like '%ADIC%' THEN valor ELSE 0 END)  adicionalSaludTRIBU, "+
					"SUM(CASE WHEN idConcepto = 98 THEN valor ELSE 0 END)  costovidacamara, "+
					"SUM(CASE WHEN idConcepto = 33 THEN valor ELSE 0 END)  seguroCesantiaAFCTrabajador, "+
					"SUM(CASE WHEN idConcepto = 34 THEN valor ELSE 0 END)  APV, "+
					"SUM(CASE WHEN idConcepto = 39 THEN valor ELSE 0 END)  impuestoUnico, "+
					"SUM(CASE WHEN idConcepto = 40 THEN valor ELSE 0 END)  totalDescuentosImp, "+
					"SUM(CASE WHEN idConcepto = 42 THEN valor ELSE 0 END)  adicionalSalud, "+
					"SUM(CASE WHEN idConcepto = 43 THEN valor ELSE 0 END)  anticipos, "+
					"SUM(CASE WHEN idConcepto = 44 THEN valor ELSE 0 END)  descuentos, "+
					"SUM(CASE WHEN idConcepto = 48 THEN valor ELSE 0 END)  ahoroVoluntarioAfp, "+
					"SUM(CASE WHEN idConcepto = 50 THEN valor ELSE 0 END)  totalDescuentosNoImp, "+
					"SUM(CASE WHEN idConcepto = 51 THEN valor ELSE 0 END)  totalDescuentos, "+
					"SUM(CASE WHEN idConcepto = 91 THEN valor ELSE 0 END)  cotizacionSIS, "+
					"SUM(CASE WHEN idConcepto = 92 THEN valor ELSE 0 END)  seguroCesantiaAFC, "+
					"SUM(CASE WHEN idConcepto = 94 THEN valor ELSE 0 END)  cotizacionBasica, "+
					"SUM(CASE WHEN idConcepto = 95 THEN valor ELSE 0 END)  cotizacionAdicional, "+
					"SUM(CASE WHEN idConcepto = 96 THEN valor ELSE 0 END)  cotizacionExtraordinaria, "+ 
					"SUM(CASE WHEN idConcepto = 97 THEN valor ELSE 0 END)  SANNA, "+
					"SUM(CASE WHEN idConcepto = 100 THEN valor ELSE 0 END)  totalPago, "+ 
					"SUM(CASE WHEN idConcepto = 234 THEN valor ELSE 0 END)  llaveisapre, "+
					"SUM(CASE WHEN idConcepto = 235 THEN valor ELSE 0 END)  llaveafp, "+
					" -1 * SUM(CASE WHEN idConcepto = 81 THEN valor ELSE 0 END)  sobregiro_, "+ 
					"SUM(CASE WHEN idConcepto = 101 THEN valor ELSE 0 END)  totalLiquidoMes, "+
					"SUM(CASE WHEN idConcepto = 6 THEN REPLACE(CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(valor, '(', -1), ')', 1) AS DECIMAL(10,4)) , '-', '')  ELSE 0 END) horasfalta, "+
					"SUM(CASE WHEN idConcepto = 6 THEN  CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(descripcion, '(', -1), ')', 1) AS DECIMAL(10,4)) "+
					"ELSE 0 END )descripcionHfalta, "+
					"SUM(CASE WHEN idConcepto = 7 THEN REPLACE(CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(valor, '(', -1), ')', 1) AS DECIMAL(10,4)) , '-', '')  ELSE 0 END) horasextra, "+
					"SUM(CASE WHEN idConcepto = 7 THEN  CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(descripcion, '(', -1), ')', 1) AS DECIMAL(10,4)) "+
					"ELSE 0 END )descripcionHextra, "+
					// liquido agro
	 				"SUM(CASE WHEN idConcepto = -1 AND sw_liquidacionDetalle.descripcion LIKE '%LIQUIDO AGRO%' THEN valor ELSE 0 END) liquidoagro, "+
					// bono produccion agro
					"SUM(CASE WHEN idConcepto = 4 AND sw_liquidacionDetalle.descripcion LIKE '%BONO PRODUCCION(AGRO)%' THEN valor ELSE 0 END) bonoproduccionagro "+
					
					"FROM sw_liquidacionDetalle "+
					"where periodo='"+periodo+"' "+ 
					"group by  codTrabajador,idContrato, periodo) as d on contratos.id =d.idContrato "+
					"left join (SELECT * FROM parametros WHERE codigo='APV' and activo=1) as papv on trabajadores.institucionAPV=papv.llave "+
					"left join (SELECT * FROM parametros WHERE codigo='TIPO_CONTRATO' and activo=1) as tt on contratos.tipoContrato=tt.llave "+
					"where sociedad.sociedad='"+empresa+"' ";
			
					if(rolPrivado == 1){
						sql += " and  trabajadores.rolPrivado in (1,0) ";
					}else{
						sql += " and  trabajadores.rolPrivado in (0) ";
					}
					
					if("null".equals(Huerto)){}else{sql += " and contratos.idHuertoContrato = '"+Huerto+"'";}
					sql += " order by apellidoPaterno, apellidoMaterno, nombreTrabajador";
					
			
					System.out.println("///////////////////////// query ultima //////////////");
					System.out.println(sql);		
			ps = db.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);			
			while(rs.next()){				
				LibroRemuneraciones row= new LibroRemuneraciones();				
				row.setCodTrabajador(rs.getInt("codTrabajador"));
				row.setNombre(rs.getString("nombreTrabajador"));
				row.setApellidoPaterno(rs.getString("apellidoPaterno"));
				row.setApellidoMaterno(rs.getString("apellidoMaterno"));
				row.setRut(rs.getString("rut"));

				if(rs.getString("idACargoTabla_con_per").equals("nulo")){
					row.setCargo(rs.getString("cargos"));
				}else{
					row.setCargo(rs.getString("idACargoTabla_con_per"));
				}
				
				if(rs.getString("idAHuertoToabla_con_per").equals("nulo")){
					row.setHuerto(rs.getString("nombrehuerto"));
				}else{
					row.setHuerto(rs.getString("idAHuertoToabla_con_per"));
				}
				
				if(rs.getString("ididCECOTabla_con_per").equals("nulo")){
					row.setCeco(rs.getString("idCECO"));
				}else{
					row.setCeco(rs.getString("ididCECOTabla_con_per"));
				}
				
				ArrayList<CentroCosto> cecos = new ArrayList<CentroCosto>();
				cecos=CentroCostoDB.getCentrosCostosByCECO(row.getCeco(), "p");
				row.setNombreCECO(cecos.get(0).getDESCRIPT());
				row.setTiemposervido(rs.getBigDecimal("tiempo_servido"));
				if(rs.getString("idFaenaTabla_con_per").equals("nulo")){
					row.setFaena(rs.getString("faena"));
				}else{
					row.setFaena(rs.getString("idFaenaTabla_con_per"));
				}
				row.setIdContrato(rs.getInt("idContrato"));
				row.setTipoCuenta(rs.getString("nombreCuenta"));
				row.setFechaPago(rs.getString("fecha_pago"));
				row.setTipoTrabajador(rs.getBigDecimal("tipoTrabajador"));
				row.setNombreTipoTrabajador(rs.getString("nombreTipoTrabajor"));
				row.setSueldoBase(rs.getBigDecimal("sueldoBase"));
				row.setDiasTrabajados(rs.getBigDecimal("diasTrabajados"));
				row.setSueldoBase2(rs.getBigDecimal("sueldoBase2"));
				row.setBonosImponibles(rs.getBigDecimal("bonosImponibles"));
				if(rs.getString("ididIsapreTabla_tra_per").equals("nulo")){
					row.setNombreSalud(rs.getString("institucionSalud"));
				}else{
					row.setNombreSalud(rs.getString("ididIsapreTabla_tra_per"));
				}
				row.setHoraExtra(rs.getBigDecimal("horaExtra"));
				row.setGratificacion(rs.getBigDecimal("gratificacion"));
				row.setTotalHaberesImponibles(rs.getBigDecimal("totalHaberesImponibles"));
				row.setBonosNoImponibles(rs.getBigDecimal("bonosNoImponibles"));
				row.setPeriodo(periodo);
				if(rs.getString("idAFPTabla_tra_per").equals("nulo")){
					row.setNombreAFP(rs.getString("AFP"));
				}else{
					row.setNombreAFP(rs.getString("idAFPTabla_tra_per"));
				}
				row.setBonosNoImponibles(rs.getBigDecimal("bonosNoImponibles"));
				row.setMontoHorafalta(rs.getBigDecimal("horasfalta"));
				row.setHoraFalta(rs.getBigDecimal("descripcionHfalta"));
				
				row.setMontoHoraExtra(rs.getBigDecimal("horasextra"));
				row.setHoraExtraJ(rs.getBigDecimal("descripcionHextra"));
				row.setSobregiro_(rs.getBigDecimal("sobregiro_"));
				row.setFechaInicio_actividad(rs.getString("fechaInicio_actividad"));
				row.setFechaTerminoContrato(rs.getString("FechaTerminoContrato"));
				row.setArticulo(rs.getInt("articulo"));
				row.setCausal(rs.getString("causal"));
				
				row.setLiquidoAgro(rs.getBigDecimal("liquidoagro"));
				row.setBonoProduccionAgro(rs.getBigDecimal("bonoproduccionagro"));
				
				
				row.setValorFeriadoProporcional(rs.getBigDecimal("valorFeriadoproporcional"));
				
				
			
				
				ArrayList<HaberDescuento> hds=new ArrayList<HaberDescuento>();
				
				for(int z=0;z<haberesImponibles.size();z++){
					HaberDescuento aux=new HaberDescuento();
					aux.setDescripcion(haberesImponibles.get(z).getDescripcion());
					aux.setCodigo(haberesImponibles.get(z).getCodigo());
					aux.setImponible(haberesImponibles.get(z).getImponible());
					aux.setTributable(haberesImponibles.get(z).getTributable());
					BigDecimal BD=rs.getBigDecimal("c"+haberesImponibles.get(z).getCodigo());
					aux.setValor(BD==null?BigDecimal.ZERO:BD);
					hds.add(aux);
				}
				
				row.setHaberesImponibles(hds);
				ArrayList<HaberDescuento> hni=new ArrayList<HaberDescuento>();
				for(int y=0;y<haberesNoImponibles.size();y++){
					HaberDescuento ni=new HaberDescuento();
					ni.setDescripcion(haberesNoImponibles.get(y).getDescripcion());
					ni.setCodigo(haberesNoImponibles.get(y).getCodigo());
					ni.setImponible(haberesNoImponibles.get(y).getImponible());
					ni.setTributable(haberesNoImponibles.get(y).getTributable());
					BigDecimal BD=rs.getBigDecimal("c"+haberesNoImponibles.get(y).getCodigo());
					ni.setValor(BD==null?BigDecimal.ZERO:BD);
					hni.add(ni);
				}
				
				row.setHaberesNoImponibles2(hni);
				
				// haberes imponibles finiquito
							ArrayList<HaberDescuento> hifiniquito=new ArrayList<HaberDescuento>();
							for(int y=0;y<haberesImponiblesFiniquito.size();y++){
								HaberDescuento niF=new HaberDescuento();
								niF.setDescripcion(haberesImponiblesFiniquito.get(y).getDescripcion());
								niF.setCodigo(haberesImponiblesFiniquito.get(y).getCodigo());
								niF.setImponible(haberesImponiblesFiniquito.get(y).getImponible());
								niF.setTributable(haberesImponiblesFiniquito.get(y).getTributable());
								BigDecimal BD=rs.getBigDecimal("hdf_tipo_h"+haberesImponiblesFiniquito.get(y).getCodigo());
								niF.setValor(BD==null?BigDecimal.ZERO:BD);
								hifiniquito.add(niF);
//								
							}
							
					row.setHaberesImponiblesF(hifiniquito);
					
					// haberes NO imponibles finiquito
					ArrayList<HaberDescuento> HNIfiniquito=new ArrayList<HaberDescuento>();
					for(int y=0;y<haberesNoImponiblesFiniquito.size();y++){
						HaberDescuento niFn=new HaberDescuento();
						niFn.setDescripcion(haberesNoImponiblesFiniquito.get(y).getDescripcion());
						niFn.setCodigo(haberesNoImponiblesFiniquito.get(y).getCodigo());
						niFn.setImponible(haberesNoImponiblesFiniquito.get(y).getImponible());
						niFn.setTributable(haberesNoImponiblesFiniquito.get(y).getTributable());
						BigDecimal BD=rs.getBigDecimal("hdf_tipo_hn"+haberesNoImponiblesFiniquito.get(y).getCodigo());
						niFn.setValor(BD==null?BigDecimal.ZERO:BD);
						HNIfiniquito.add(niFn);
					}
					
			row.setHaberesNoImponiblesF(HNIfiniquito);
			
			
			// DESCUENTOS finiquito
			ArrayList<HaberDescuento> DESCUENTOfiniquito=new ArrayList<HaberDescuento>();
			for(int y=0;y<DescuentosFiniquito.size();y++){
				HaberDescuento DESC_FINIQ=new HaberDescuento();
				DESC_FINIQ.setDescripcion(DescuentosFiniquito.get(y).getDescripcion());
				DESC_FINIQ.setCodigo(DescuentosFiniquito.get(y).getCodigo());
				DESC_FINIQ.setImponible(DescuentosFiniquito.get(y).getImponible());
				DESC_FINIQ.setTributable(DescuentosFiniquito.get(y).getTributable());
				BigDecimal BD=rs.getBigDecimal("hdf_tipo_d"+DescuentosFiniquito.get(y).getCodigo());
				DESC_FINIQ.setValor(BD==null?BigDecimal.ZERO:BD);
				DESCUENTOfiniquito.add(DESC_FINIQ);
			}
			
			row.setDescuentoF(DESCUENTOfiniquito);
			
			
			// COSTO EMPRESA FINIQUITO
					ArrayList<HaberDescuento> COSTOPATRONALfiniquito=new ArrayList<HaberDescuento>();
					for(int y=0;y<costoempresaFiniquito.size();y++){
						HaberDescuento CP_FINIQ=new HaberDescuento();
						CP_FINIQ.setDescripcion(costoempresaFiniquito.get(y).getDescripcion());
						CP_FINIQ.setCodigo(costoempresaFiniquito.get(y).getCodigo());
						CP_FINIQ.setImponible(costoempresaFiniquito.get(y).getImponible());
						CP_FINIQ.setTributable(costoempresaFiniquito.get(y).getTributable());
						BigDecimal BD=rs.getBigDecimal("hdf_tipo_ce"+costoempresaFiniquito.get(y).getCodigo());
						CP_FINIQ.setValor(BD==null?BigDecimal.ZERO:BD);
						COSTOPATRONALfiniquito.add(CP_FINIQ);
					}
					
					row.setCostoempresaF(COSTOPATRONALfiniquito);
					
					
					
							
				for(int h=0;h<row.getHaberesNoImponibles2().size();h++){
				}
				
				// DESCUENTOS finiquito
				ArrayList<HaberDescuento> DESCUENTOS=new ArrayList<HaberDescuento>();
				for(int y=0;y<Descuentos.size();y++){
					HaberDescuento DESC_=new HaberDescuento();
					DESC_.setDescripcion(Descuentos.get(y).getDescripcion());
					
					 String nombreconcepU2= Descuentos.get(y).getDescripcion().replace(' ', '_');
					 String nombreconcepU22 = nombreconcepU2.replaceAll("\\.", "");
					
					BigDecimal BD=rs.getBigDecimal("c"+nombreconcepU22);
					DESC_.setValor(BD==null?BigDecimal.ZERO:BD);
					DESCUENTOS.add(DESC_);
				}
				
				row.setDescuento2(DESCUENTOS);
				
				row.setCargaFamiliarSimple(rs.getBigDecimal("cargaFamiliarSimple"));
				row.setCargaFamiliarMaternal(rs.getBigDecimal("cargaFamiliarMaternal"));
				row.setCargaFamiliaresRetro(rs.getBigDecimal("cargaFamiliaresRetro"));
				row.setTotalHabNoImponible(rs.getBigDecimal("totalHabNoImponible"));
				row.setTotalHaberes(rs.getBigDecimal("totalHaberes"));
				row.setBaseTributable(rs.getBigDecimal("baseTributable"));
				row.setAFP(rs.getBigDecimal("valorAFP"));
				row.setSalud(rs.getBigDecimal("salud"));
				row.setCaja(rs.getBigDecimal("caja"));		
				row.setAdicionalSalud(rs.getBigDecimal("adicionalSalud"));
				row.setAdicionalTribu(rs.getBigDecimal("adicionalSaludTRIBU"));
				
				row.setSeguroCesantiaAFCTrabajador(rs.getBigDecimal("seguroCesantiaAFCTrabajador"));
				row.setAPV(rs.getBigDecimal("APV"));
				row.setNombreAPV(rs.getString("nombreAPV"));
				row.setImpuestoUnico(rs.getBigDecimal("impuestoUnico"));
				row.setTotalDescuentosImp(rs.getBigDecimal("totalDescuentosImp"));
				row.setAnticipo(rs.getBigDecimal("anticipos"));
				row.setDescuentos(rs.getBigDecimal("descuentos"));
				row.setAhoroVoluntarioAfp(rs.getBigDecimal("ahoroVoluntarioAfp"));
				row.setTotalDescuentosNoImp(rs.getBigDecimal("totalDescuentosNoImp"));
				row.setTotalDescuentos(rs.getBigDecimal("totalDescuentos"));
				row.setCotizacionSIS(rs.getBigDecimal("cotizacionSIS"));
				row.setSeguroSesantiaAFC(rs.getBigDecimal("seguroCesantiaAFC"));
				row.setCotizacionBasica(rs.getBigDecimal("cotizacionBasica"));
				row.setCotizacionAdicional(rs.getBigDecimal("cotizacionAdicional"));
				row.setCotizacionExtraordinaria(rs.getBigDecimal("cotizacionExtraordinaria"));
				row.setSANNA(rs.getBigDecimal("SANNA"));
				row.setTotalPago(rs.getBigDecimal("totalPago"));
				row.setTotalLiquidoMes(rs.getBigDecimal("totalLiquidoMes"));
				row.setSociedad(rs.getString("sociedad"));
				row.setDenominacionSociedad(rs.getString("denominacionSociedad"));
				row.setCostoVidaCamara(rs.getBigDecimal("costovidacamara"));
				
				
				
				
				
				lista.add(row);
			}		
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}finally {
			ps.close();
		}		
		return lista;
		
	}
		
		
	///////////////////// excel SII //////////////////////////////////////////////
	
	public static String CrearLibro_SII(ArrayList<LibroRemuneraciones> l, String nombre2) throws SQLException {
		ArrayList<LibroRemuneraciones> libro= l;
			if(l.size()==0){
				return "0";
			}
			else{
			LibroRemuneraciones r=new LibroRemuneraciones();
			r=l.get(0);
			
			// CANTIDAD HABERES IMPONIBLES FINIQUITO
			int variablef=l.get(0).getHaberesImponiblesF().size();
			// CANTIDAD HABERES NO IMPONIBLES FINIQUITO
			int variableHaberNoImponibleF =l.get(0).getHaberesNoImponiblesF().size();
			// CANTIDAD DESCUENTOS FINIQUITO
			int variableDescuentoF =l.get(0).getDescuentoF().size();
			// COSTO EMPRESA FINIQUITO
			int variableCostoEmpresaF =l.get(0).getCostoempresaF().size();
			
			// total item columna en blanco + COSTO EMPRESA + TOTAL AFP
			int cantidadItemFinal = 3;
			
			
			
			String urlDocGenerado = utils.reportesExcel();
			
			urlDocGenerado=urlDocGenerado+nombre2+".xlsx";
			
			File archivo = new File(urlDocGenerado);
		    Workbook workbook = new XSSFWorkbook(); 
		    Sheet pagina = workbook.createSheet("Reporte");
		    CellStyle style0=workbook.createCellStyle();

		    CellStyle style = workbook.createCellStyle();
		    style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    
		    CellStyle style3 = workbook.createCellStyle();
		    style3.setBorderTop(CellStyle.BORDER_DOUBLE);
		    
		    int variables=l.get(0).getHaberesImponibles().size();
			variables=variables+l.get(0).getHaberesNoImponibles2().size();
		   
		    int variableDescuentos =l.get(0).getDescuento2().size();
		   
			variables=variables+60;
			String[] Titulos=new String[variableCostoEmpresaF+variableDescuentoF+variableHaberNoImponibleF+variablef+variables+3+cantidadItemFinal+variableDescuentos];
			Titulos[0]="N°";
			Titulos[1]="Cod. Trabajador";
			Titulos[2]="Apellido Paterno";
			Titulos[3]="Apellido Materno";
			Titulos[4]="Nombre";
			Titulos[5]="Rut Trab.";
			Titulos[6]="Cargo";
			Titulos[7]="FECHA DE INICIO";
			Titulos[8]="FECHA DE TERMINO";
			Titulos[9]="CECO";
			Titulos[10]="HUERTO";
			Titulos[11]="Fecha de Pago";
			Titulos[12]="Tipo Contrato";
			Titulos[13]="Dias Trabajados";
			Titulos[14]="Sueldo Base";
			Titulos[15]="Total Bonos Imponibles";
			int add=0;
			
			int x=16;
			
			Titulos[x]="Hora Extra Monto";
			Titulos[x+1]="Hora Falta Monto";
			Titulos[x+2]="Gratificacion";
			Titulos[x+3]="Total Haberes Imponibles";
			Titulos[x+4]="Total Haberes No Imponibles";
			
			x=x+5;
			
			Titulos[x]="Carga Familiar Simple";
			Titulos[x+1]="Carga Familiar Maternal";
			Titulos[x+2]="Carga Familiar Retroactivo";
			Titulos[x+3]="Total Haberes";
			Titulos[x+4]="Nombre AFP";
			Titulos[x+5]="Valor AFP";
			Titulos[x+6]="Institucion Salud";
			Titulos[x+7]="Valor Salud";
			Titulos[x+8]="valor Caja";
			Titulos[x+9]="Adicional Salud";
			Titulos[x+10]="Seguro Cesantia AFC Trabajador";
			Titulos[x+11]="Institucion APV";
			Titulos[x+12]="APV";
			Titulos[x+13]="Impuesto Unico";
			Titulos[x+14]="Anticipos";
			Titulos[x+15]="Ahorro Voluntario";
			
			int totalHastaAhorro =  x+16;
			Titulos[totalHastaAhorro]="OTROS DESCUENTOS";
					
			int xxxxx = totalHastaAhorro + 1;
			int totalHastaDesc_F = xxxxx ;
			int xx = totalHastaDesc_F;
			
			Titulos[xx]="Total Pago";
			Titulos[xx+1]="Total Liquido Mes";
			Titulos[xx+2]="Cotizacion SIS";
			Titulos[xx+3]="Seguro Cesantia AFC Emp.";
			Titulos[xx+4]="Total ACHS";
			Titulos[xx+5]="COSTO E° VIDA CAMARA";
			int xxe = xx+6;
			// costo empresa finiquito
				if(variableCostoEmpresaF == 0){
					
				}else
				{
				for(int b=0;b < variableCostoEmpresaF;b++)
				{
					Titulos[xxe] = "FINIQUITO C.E"+l.get(0).getCostoempresaF().get(b).getDescripcion();
					xxe++;
				}
				
				}
				
				// COSTO EMPRESA
				Titulos[xxe]="COSTO EMPRESA";
				
			Row fila = pagina.createRow(0);
			Cell celda = fila.createCell(1);
			celda.setCellValue(l.get(0).getDenominacionSociedad().toUpperCase());
			 fila = pagina.createRow(1);
			 celda = fila.createCell(1);
			 
			 String p0=""+l.get(0).getPeriodo();
			 String p1="";
			 p1=p0.substring(4);
			 String p2=p0.substring(0, 4);
			 if(p1.equals("01")){
				 p1="ENERO DE ";
			 }
			 else if(p1.equals("02")){
				 p1="FEBRERO DE ";
			 }
			 else if(p1.equals("03")){
				 p1="MARZO DE ";
			 }else if(p1.equals("04")){
				 p1="ABRIL DE ";
			 }else if(p1.equals("05")){
				 p1="MAYO DE ";
			 }else if(p1.equals("06")){
				 p1="JUNIO DE ";
			 }else if(p1.equals("07")){
				 p1="JULIO DE ";
			 }else if(p1.equals("08")){
				 p1="AGOSTO DE ";
			 }else if(p1.equals("09")){
				 p1="SEPTIEMBRE DE ";
			 }else if(p1.equals("10")){
				 p1="OCTUBRE DE ";
			 }else if(p1.equals("11")){
				 p1="NOVIEMBRE DE ";
			 }else if(p1.equals("12")){
				 p1="DICIEMBRE DE ";
			 }
			 String pp=p1+p2;
			 
			celda.setCellValue(pp);
			
			fila = pagina.createRow(2);
			fila = pagina.createRow(3);
			fila = pagina.createRow(4);
			 for(int i = 0; i < 45; i++) {
			        Cell celda1 = fila.createCell(i);
			        
			        celda1.setCellStyle(style); 
			        Titulos[i]=Titulos[i]!=null? Titulos[i].toUpperCase():Titulos[i];
			        celda1.setCellValue(Titulos[i]);
			    }
			
			 for(int i = 0; i <libro.size() ; i++) {
				 
			    	fila = pagina.createRow((i+5));
			    	Cell celda2 = fila.createCell(0);
			        celda2.setCellValue((i+1));
			        celda2 = fila.createCell(1);
			        celda2.setCellValue(libro.get(i).getCodTrabajador()); 
			        celda2 = fila.createCell(2);
			        celda2.setCellValue(libro.get(i).getApellidoPaterno());
			        celda2 = fila.createCell(3);
			        celda2.setCellValue(libro.get(i).getApellidoMaterno());
			        celda2 = fila.createCell(4);
			        celda2.setCellValue(libro.get(i).getNombre());
			        celda2 = fila.createCell(5);
			        celda2.setCellValue(libro.get(i).getRut());
			        celda2 = fila.createCell(6);
			        celda2.setCellValue(libro.get(i).getCargo());
			        celda2 = fila.createCell(7);
			        celda2.setCellValue(libro.get(i).getFechaInicio_actividad().toUpperCase());
			        celda2 = fila.createCell(8);
			        if(libro.get(i).getFechaTerminoContrato() == null || libro.get(i).getFechaTerminoContrato() == "null")
			        {
			         celda2.setCellValue("");
			        }else
			        {
			        	celda2.setCellValue(libro.get(i).getFechaTerminoContrato().toUpperCase());
			        }
			        
			        celda2 = fila.createCell(9);
			        celda2.setCellValue(""+libro.get(i).getNombreCECO());
			        
			        celda2 = fila.createCell(10);
			        celda2.setCellValue(""+libro.get(i).getHuerto());
			        
			        // fecha de pago 
			        celda2 = fila.createCell(11);
			        if(libro.get(i).getFechaPago() == null || libro.get(i).getFechaPago() == "null")
			        {
			         celda2.setCellValue("");
			        }else
			        {
			        	String fecha=""+libro.get(i).getFechaPago();
				        String[] D=fecha.split("-");
				        String Formato=D[2]+"-"+D[1]+"-"+D[0];
				        
				        celda2.setCellValue(Formato);
			        }
			        
			        celda2= fila.createCell(12);
			        celda2.setCellValue(""+libro.get(i).getNombreTipoTrabajador());
			        celda2 = fila.createCell(13);
			        // dias trabajados
			        celda2.setCellValue(""+libro.get(i).getDiasTrabajados());
			        celda2 = fila.createCell(14);
			        
			        CellStyle style1;
			        style1=workbook.createCellStyle();

			        DataFormat format=workbook.createDataFormat();
			        style1.setDataFormat(format.getFormat("#,##0"));
			        
			        CellStyle styleDecimal;
			        styleDecimal=workbook.createCellStyle();

			        DataFormat formatDecimal=workbook.createDataFormat();

			        styleDecimal.setDataFormat(formatDecimal.getFormat("0.00"));
			        
			        
			        int sueldoBase2TA=libro.get(i).getSueldoBase2().intValue();
			       
			        // sueldo base 
			        celda2.setCellValue(sueldoBase2TA);
			        celda2.setCellStyle(style1);

			        celda2 = fila.createCell(15);
			        // total bonos imponibles
			        int bonosImponibles=libro.get(i).getBonosImponibles().intValue();
			        celda2.setCellValue(bonosImponibles);
			        celda2.setCellStyle(style1);
			       	        
			        add=16;
			        
			        // MONTO HORA EXTRA
			        celda2 = fila.createCell(add);
			        celda2.setCellValue(libro.get(i).getMontoHoraExtra().doubleValue());
			        celda2.setCellStyle(style1);
			        
			        // MONTO HORA FALTA
			        celda2 = fila.createCell(add+1);
			        celda2.setCellValue(libro.get(i).getMontoHorafalta().doubleValue());
			        celda2.setCellStyle(style1);
			        
			        // GRATIFICACION
			        celda2 = fila.createCell(add+2);
			        int grat=libro.get(i).getGratificacion().intValue();
			        celda2.setCellValue(grat);
			        celda2.setCellStyle(style1);
			        
			        int forhif = add+3;
			        
			        // TOTAL HABERES IMPONIBLES
			        celda2 = fila.createCell(forhif);
			        int thi=libro.get(i).getTotalHaberesImponibles().intValue();
			        celda2.setCellValue(thi);
			        celda2.setCellStyle(style1);
			        
			        int valorFeProFiniquito = 0;
			        int valorINDEMNIZACIONANOSERVICIO = 0;
			        int valorINDEMNIZACIONMESDENOAVISO = 0;
			        int totalnoimponiblefiniquito = 0;	
			    
			        // HABERES NO IMPONIBLE FINIQUITO
			     	if(variableHaberNoImponibleF == 0){
			     		
			     	}else
			     	{
			     		
				     	
				     	for(int b=0;b < variableHaberNoImponibleF;b++)
				     	{
				     		
				     		if(libro.get(i).getHaberesNoImponiblesF().get(b).getValor() != null){
				     			
				     		}
				     		
				     		
				 	        int valor_f_hiN=libro.get(i).getHaberesNoImponiblesF().get(b).getValor().intValue();
				 	        
				 	        int valorFeriadoProNormal = libro.get(i).getValorFeriadoProporcional()==null?0:libro.get(i).getValorFeriadoProporcional().intValue();
				 	       
				 	        
				 	        
				 	        
				 	       if(libro.get(i).getHaberesNoImponiblesF().get(b).getDescripcion().equals("FERIADO PROPORCIONAL") )
				 	       {
				 	    	  valor_f_hiN =  valorFeriadoProNormal;
				 	    	   if(valorFeriadoProNormal <= 0 )
				 	    	   {
				 	    		   valorFeProFiniquito = libro.get(i).getHaberesNoImponiblesF().get(b).getValor().intValue();
				 	    		   valor_f_hiN = valorFeProFiniquito;
				 	    	   }
				 	    	     
				 	       }

				 	       
				 	       if(libro.get(i).getHaberesNoImponiblesF().get(b).getCodigo() == 2007 ){
				 	    	  valorINDEMNIZACIONANOSERVICIO = libro.get(i).getHaberesNoImponiblesF().get(b).getValor().intValue();
				 	        }
				 	      if(libro.get(i).getHaberesNoImponiblesF().get(b).getCodigo() == 2008 ){
				 	    	 valorINDEMNIZACIONMESDENOAVISO = libro.get(i).getHaberesNoImponiblesF().get(b).getValor().intValue();
				 	        }
				 	       
				 	        
				 	       if(libro.get(i).getHaberesNoImponiblesF().get(b).getDescripcion().equals("FERIADO PROPORCIONAL") )
				 	       {
				 	    	   
				 	       }else{
				 	    	  totalnoimponiblefiniquito = totalnoimponiblefiniquito + valor_f_hiN;
				 	       }
				 	        
				 	        celda2.setCellStyle(style1);
				     	}
				     	}
			        
			     	 //TOTAL Haberes NO IMPONIBLES
			        celda2 = fila.createCell(forhif+1);
			        int feriadop = libro.get(i).getValorFeriadoProporcional()==null?0:libro.get(i).getValorFeriadoProporcional().intValue();
			        int bni = libro.get(i).getTotalHabNoImponible().intValue()+feriadop+libro.get(i).getTiemposervido().intValue() + totalnoimponiblefiniquito;
			        celda2.setCellValue(bni);
			        celda2.setCellStyle(style1);
			       
			     	x=forhif+2;
			     	
			        // CARGA FAMILIAR SIMPLE
			        celda2 = fila.createCell(x);
			        celda2.setCellValue(libro.get(i).getCargaFamiliarSimple().intValue());
			        celda2.setCellStyle(style1);
			        // CARGA FAMILIAR MATERNAL
			        celda2 = fila.createCell(x+1);
			        celda2.setCellValue(libro.get(i).getCargaFamiliarMaternal().intValue());
			        celda2.setCellStyle(style1);
			        // CARGA FAMILIAR RETROACTIVA
			        celda2 = fila.createCell(x+2);
			        celda2.setCellValue(libro.get(i).getCargaFamiliaresRetro().intValue());
			        celda2.setCellStyle(style1);
			        
			        int totalItemCargas = libro.get(i).getCargaFamiliarSimple().intValue() + libro.get(i).getCargaFamiliarMaternal().intValue() + libro.get(i).getCargaFamiliaresRetro().intValue();
			        int valorFeriadoPro = libro.get(i).getValorFeriadoProporcional()==null?0:libro.get(i).getValorFeriadoProporcional().intValue();
			        
			        // TOTAL HABERES
			        celda2 = fila.createCell(x+3);
			        celda2.setCellValue(libro.get(i).getTotalHaberes().intValue()+valorFeProFiniquito + valorFeriadoPro - totalItemCargas + libro.get(i).getTiemposervido().intValue()   + totalnoimponiblefiniquito  );
			        celda2.setCellStyle(style1);
			        
			        // NOMBRE AFP   
			        celda2 = fila.createCell(x+4);
			        celda2.setCellValue(libro.get(i).getNombreAFP().toUpperCase());
			        
			        // VALOR AFP
			        celda2 = fila.createCell(x+5);
			        celda2.setCellValue(libro.get(i).getAFP().intValue());
			        celda2.setCellStyle(style1);
			        celda2 = fila.createCell(x+6);
			        celda2.setCellValue(libro.get(i).getNombreSalud().toUpperCase());
			        int adisaluTri = libro.get(i).getAdicionalTribu().intValue();
			        
			        // valor salud
			        celda2 = fila.createCell(x+7);
			        celda2.setCellValue(libro.get(i).getSalud().intValue()-adisaluTri);
			        celda2.setCellStyle(style1);
			        celda2 = fila.createCell(x+8);
			        celda2.setCellValue(libro.get(i).getCaja().intValue());
			        celda2.setCellStyle(style1);
			        
			        // adicional salud
			        celda2 = fila.createCell(x+9);
			        int adisalunoTri = libro.get(i).getAdicionalSalud().intValue();
			        celda2.setCellValue(adisalunoTri + adisaluTri);
			        celda2.setCellStyle(style1);
			        
			        // SEGURO CESANTIA AFC TRABAJADOR
			        celda2 = fila.createCell(x+10);
			        celda2.setCellValue(libro.get(i).getSeguroCesantiaAFCTrabajador().intValue());
			        celda2.setCellStyle(style1);
			        
			        celda2 = fila.createCell(x+11);
			        celda2.setCellValue(libro.get(i).getNombreAPV()!=null?libro.get(i).getNombreAPV().toUpperCase():"");
			        
			        celda2 = fila.createCell(x+12);
			        celda2.setCellValue(libro.get(i).getAPV().intValue());
			        celda2.setCellStyle(style1);
			        
			        celda2 = fila.createCell(x+13);
			        celda2.setCellValue(libro.get(i).getImpuestoUnico().intValue());
			        celda2.setCellStyle(style1);
			        
			        celda2 = fila.createCell(x+14);
			        celda2.setCellValue(libro.get(i).getAnticipo().intValue());
			        celda2.setCellStyle(style1);
			        
			        // AHORRO VOLUNTARIO AFP
			        celda2 = fila.createCell(x+15);
			        celda2.setCellValue(libro.get(i).getAhoroVoluntarioAfp().intValue());
			        celda2.setCellStyle(style1);
			        
			        // DESCUENTOS FINIQUITO
			        int aporteAFCEmpleador = 0;
			        int totaldescuentoFiniquito = 0;
			        
			        int totalHastaAnticipo = x+16;
			        
			        int total_descu  = 0;
					if (variableDescuentoF == 0) {
	
					} else {
						for (int b = 0; b < variableDescuentoF; b++) {
	
							if (libro.get(i).getDescuentoF().get(b).getValor() != null) {
	
							}
	
							int valor_f_descuento = libro.get(i).getDescuentoF().get(b).getValor().intValue();
	
							if (libro.get(i).getDescuentoF().get(b).getCodigo() == 3002) {
								aporteAFCEmpleador = libro.get(i).getDescuentoF().get(b).getValor().intValue();
							}
	
							totaldescuentoFiniquito = totaldescuentoFiniquito + valor_f_descuento;
	
							total_descu = total_descu + valor_f_descuento;
	
						}// end for
					}// end else
			        
			          int total_descu2 = 0;
				
					if (variableDescuentos == 0) {
	
					} else {
						for (int b = 0; b < variableDescuentos; b++) {
							if (libro.get(i).getDescuento2().get(b).getValor() != null) {
	
							}
	
							if (libro.get(i).getDescuento2().get(b).getDescripcion().equals("OTROS DESCUENTOS")) {
								total_descu2 = libro.get(i).getDescuento2().get(b).getValor().intValue();
							}
						} // end for
					} // end else
				     	
				     	celda2 = fila.createCell(totalHastaAnticipo);
				     	celda2.setCellValue(total_descu2);
			 	        celda2.setCellStyle(style1);
				     	
				    int xxxy =  totalHastaAnticipo +1;
			        
			        // total pago
			        celda2 = fila.createCell(xxxy);
			        celda2.setCellValue(libro.get(i).getTotalPago().intValue()+valorFeProFiniquito + valorFeriadoPro  + libro.get(i).getTiemposervido().intValue() - libro.get(i).getSobregiro_().intValue() + totalnoimponiblefiniquito - totaldescuentoFiniquito);

			        celda2.setCellStyle(style1);
			        
			        // total liquido mes
			        celda2= fila.createCell(xxxy+1);
			        celda2.setCellValue(libro.get(i).getTotalLiquidoMes().intValue()+valorFeProFiniquito + valorFeriadoPro   - aporteAFCEmpleador + libro.get(i).getTiemposervido().intValue() + totalnoimponiblefiniquito);

			        celda2.setCellStyle(style1);
			        
			        // COTIZACION SIS
			        celda2 = fila.createCell(xxxy+2);
			        celda2.setCellValue(libro.get(i).getCotizacionSIS().intValue());
			        celda2.setCellStyle(style1);
			        
			        // SEGURO CESANTIA AFC
			        celda2 = fila.createCell(xxxy+3);
			        celda2.setCellValue(libro.get(i).getSeguroSesantiaAFC().intValue());
			        celda2.setCellStyle(style1);
			        
			        
			        
			       
			        celda2 = fila.createCell(xxxy+4);
			        int  sumaC=libro.get(i).getCotizacionBasica().intValue()+libro.get(i).getCotizacionAdicional().intValue()+
			        libro.get(i).getCotizacionExtraordinaria().intValue()+libro.get(i).getSANNA().intValue();
			        celda2.setCellValue(sumaC);
			        celda2.setCellStyle(style1);
			        
			        celda2 = fila.createCell(xxxy+5);
			        celda2.setCellValue(libro.get(i).getCostoVidaCamara().intValue());
			        celda2.setCellStyle(style1);
			        
			        
			        
			        int totalHastaCO_Fi = xxxy+6;
			   	 // COSTO EMPRESA FINIQUITO
			     	if(variableCostoEmpresaF == 0){
			     		
			     	}else
			     	{
			     	for(int b=0;b < variableCostoEmpresaF;b++)
			     	{
			     		
			     		celda2 = fila.createCell(totalHastaCO_Fi);
			     		if(libro.get(i).getCostoempresaF().get(b).getValor() != null){
			     			
			     		}
			     		
			   	        int valor_f_descuento=libro.get(i).getCostoempresaF().get(b).getValor().intValue();
			   	        
			   	        celda2.setCellValue(valor_f_descuento);
			   	        celda2.setCellStyle(style1);
			   	        totalHastaCO_Fi ++;
			     		
			     	}
			     	
			     	}
			     	
			     	
			     // columna en blanco
			     	celda2 = fila.createCell(totalHastaCO_Fi);
			        celda2.setCellValue("");
					// COSTO EMPRESA
			        int total_COSTO_E = ((((
			        		libro.get(i).getTotalHaberesImponibles().intValue() +
			        		libro.get(i).getTotalHabNoImponible().intValue()+feriadop 
			        		) 
			        		- libro.get(i).getCargaFamiliarSimple().intValue())
			        		- libro.get(i).getCargaFamiliarMaternal().intValue())
			        		- libro.get(i).getCargaFamiliaresRetro().intValue()) 
			        		+ (
			        		libro.get(i).getCotizacionSIS().intValue() + 
			        		libro.get(i).getSeguroSesantiaAFC().intValue() + sumaC +
			        		libro.get(i).getCostoVidaCamara().intValue() +
			        		libro.get(i).getTiemposervido().intValue() +
			        		valorINDEMNIZACIONANOSERVICIO + 
			        		valorINDEMNIZACIONMESDENOAVISO

			        		);
			        celda2 = fila.createCell(totalHastaCO_Fi);
			        celda2.setCellValue(total_COSTO_E);
			        celda2.setCellStyle(style1);
					
			    }
			 
			
			 
			 //suma:::
			 //espacio en blanco 
			 CellStyle style1;
		     style1=workbook.createCellStyle();

		     DataFormat format=workbook.createDataFormat();
		     style1.setDataFormat(format.getFormat("#,##0"));
		     
		     CellStyle style4 = workbook.createCellStyle();
			    style4.setBorderTop(CellStyle.BORDER_DOUBLE);
			    style4.setDataFormat(format.getFormat("#,##0"));
		     
		     CellStyle styleDecimal;
		     styleDecimal=workbook.createCellStyle();

		     DataFormat formatDecimal=workbook.createDataFormat();

		     styleDecimal.setDataFormat(formatDecimal.getFormat("0.00"));
		     
		     
			 int lineaBlanca=libro.size()+5;
			 fila= pagina.createRow((lineaBlanca));
			 fila=pagina.createRow((lineaBlanca+1));
			 
			 Cell celda2 = fila.createCell(0);
			 
			 celda2.setCellValue("Total General");
			 celda2.setCellStyle(style3); 
			 int libroSize=libro.size()+5;
			 
			 Cell celda2_2 = fila.createCell(1);
			 celda2_2.setCellStyle(style3);
			 Cell celda2_3 = fila.createCell(2);
			 celda2_3.setCellStyle(style3);
			 Cell celda2_4 = fila.createCell(3);
			 celda2_4.setCellStyle(style3);
			 Cell celda2_5 = fila.createCell(4);
			 celda2_5.setCellStyle(style3);
			 Cell celda2_6 = fila.createCell(5);
			 celda2_6.setCellStyle(style3);
			 Cell celda2_7 = fila.createCell(6);
			 celda2_7.setCellStyle(style3);
			 Cell celda2_8 = fila.createCell(7);
			 celda2_8.setCellStyle(style3);
			 Cell celda2_9 = fila.createCell(8);
			 celda2_9.setCellStyle(style3);
			 Cell celda2_10 = fila.createCell(9);
			 celda2_10.setCellStyle(style3);
			 Cell celda2_11 = fila.createCell(10);
			 celda2_11.setCellStyle(style3);
			 Cell celda2_12 = fila.createCell(11);
			 celda2_12.setCellStyle(style3);
			 Cell celda2_13 = fila.createCell(12);
			 celda2_13.setCellStyle(style3);
			 Cell celda2_14 = fila.createCell(13);
			 celda2_14.setCellStyle(style3);
			

			 // 14
			 celda2=fila.createCell(14);
			 celda2.setCellType(CellType.FORMULA);
			 String re1=CellReference.convertNumToColString(14);
			 String formu14="SUM("+re1+"6:"+re1+libroSize+")";
			 celda2.setCellFormula(formu14);
			 celda2.setCellStyle(style4);
	
			
			 
			 // 15
			 celda2=fila.createCell(15);
			 celda2.setCellType(CellType.FORMULA);
			 String re2=CellReference.convertNumToColString(15);
			 String formu15="SUM("+re2+"6:"+re2+libroSize+")";
			 celda2.setCellFormula(formu15);
			 celda2.setCellStyle(style4);
			 
			// 16
			 celda2=fila.createCell(16);
			 celda2.setCellType(CellType.FORMULA);
			 String re3=CellReference.convertNumToColString(16);
			 String formu16="SUM("+re3+"6:"+re3+libroSize+")";
			 celda2.setCellFormula(formu16);
			 celda2.setCellStyle(style4);
			 
			// 17
			 celda2=fila.createCell(17);
			 celda2.setCellType(CellType.FORMULA);
			 String re4=CellReference.convertNumToColString(17);
			 String formu17="SUM("+re4+"6:"+re4+libroSize+")";
			 celda2.setCellFormula(formu17);
			 celda2.setCellStyle(style4);
			 
			// 18
			 celda2=fila.createCell(18);
			 celda2.setCellType(CellType.FORMULA);
			 String re5=CellReference.convertNumToColString(18);
			 String formu18="SUM("+re5+"6:"+re5+libroSize+")";
			 celda2.setCellFormula(formu18);
			 celda2.setCellStyle(style4);
			 
			// 19
			 celda2=fila.createCell(19);
			 celda2.setCellType(CellType.FORMULA);
			 String re6=CellReference.convertNumToColString(19);
			 String formu19="SUM("+re6+"6:"+re6+libroSize+")";
			 celda2.setCellFormula(formu19);
			 celda2.setCellStyle(style4);
			 
			// 20
			 celda2=fila.createCell(20);
			 celda2.setCellType(CellType.FORMULA);
			 String re7=CellReference.convertNumToColString(20);
			 String formu20="SUM("+re7+"6:"+re7+libroSize+")";
			 celda2.setCellFormula(formu20);
			 celda2.setCellStyle(style4);
			 
			// 21
			 celda2=fila.createCell(21);
			 celda2.setCellType(CellType.FORMULA);
			 String re8=CellReference.convertNumToColString(21);
			 String formu21="SUM("+re8+"6:"+re8+libroSize+")";
			 celda2.setCellFormula(formu21);
			 celda2.setCellStyle(style4);
			 
			// 22
			 celda2=fila.createCell(22);
			 celda2.setCellType(CellType.FORMULA);
			 String re9=CellReference.convertNumToColString(22);
			 String formu22="SUM("+re9+"6:"+re9+libroSize+")";
			 celda2.setCellFormula(formu22);
			 celda2.setCellStyle(style4);
			 
			// 23
			 celda2=fila.createCell(23);
			 celda2.setCellType(CellType.FORMULA);
			 String re10=CellReference.convertNumToColString(23);
			 String formu23="SUM("+re10+"6:"+re10+libroSize+")";
			 celda2.setCellFormula(formu23);
			 celda2.setCellStyle(style4);
			 
			// 24
			 celda2=fila.createCell(24);
			 celda2.setCellType(CellType.FORMULA);
			 String re11=CellReference.convertNumToColString(24);
			 String formu24="SUM("+re11+"6:"+re11+libroSize+")";
			 celda2.setCellFormula(formu24);
			 celda2.setCellStyle(style4);
			 
			 // 25 AFP 
			 
			// 26
			 celda2=fila.createCell(26);
			 celda2.setCellType(CellType.FORMULA);
			 String re12=CellReference.convertNumToColString(26);
			 String formu26="SUM("+re12+"6:"+re12+libroSize+")";
			 celda2.setCellFormula(formu26);
			 celda2.setCellStyle(style4);
			 
			
			 
			// 28
			 celda2=fila.createCell(28);
			 celda2.setCellType(CellType.FORMULA);
			 String re13=CellReference.convertNumToColString(28);
			 String formu28="SUM("+re13+"6:"+re13+libroSize+")";
			 celda2.setCellFormula(formu28);
			 celda2.setCellStyle(style4);
			 
			// 29
			 celda2=fila.createCell(29);
			 celda2.setCellType(CellType.FORMULA);
			 String re14=CellReference.convertNumToColString(29);
			 String formu29="SUM("+re14+"6:"+re14+libroSize+")";
			 celda2.setCellFormula(formu29);
			 celda2.setCellStyle(style4);
			 
			// 30
			 celda2=fila.createCell(30);
			 celda2.setCellType(CellType.FORMULA);
			 String re15=CellReference.convertNumToColString(30);
			 String formu30="SUM("+re15+"6:"+re15+libroSize+")";
			 celda2.setCellFormula(formu30);
			 celda2.setCellStyle(style4);
			 
			
			 
			// 31
			 celda2=fila.createCell(31);
			 celda2.setCellType(CellType.FORMULA);
			 String re131=CellReference.convertNumToColString(31);
			 String formu31="SUM("+re131+"6:"+re131+libroSize+")";
			 celda2.setCellFormula(formu31);
			 celda2.setCellStyle(style4);
			 
			// 33
			 celda2=fila.createCell(33);
			 celda2.setCellType(CellType.FORMULA);
			 String re17=CellReference.convertNumToColString(33);
			 String formu33="SUM("+re17+"6:"+re17+libroSize+")";
			 celda2.setCellFormula(formu33);
			 celda2.setCellStyle(style4);
			 
			// 34
			 celda2=fila.createCell(34);
			 celda2.setCellType(CellType.FORMULA);
			 String re18=CellReference.convertNumToColString(34);
			 String formu34="SUM("+re18+"6:"+re18+libroSize+")";
			 celda2.setCellFormula(formu34);
			 celda2.setCellStyle(style4);
			 
			// 35
			 celda2=fila.createCell(35);
			 celda2.setCellType(CellType.FORMULA);
			 String re19=CellReference.convertNumToColString(35);
			 String formu35="SUM("+re19+"6:"+re19+libroSize+")";
			 celda2.setCellFormula(formu35);
			 celda2.setCellStyle(style4);
			 
			// 36
			 celda2=fila.createCell(36);
			 celda2.setCellType(CellType.FORMULA);
			 String re20=CellReference.convertNumToColString(36);
			 String formu36="SUM("+re20+"6:"+re20+libroSize+")";
			 celda2.setCellFormula(formu36);
			 celda2.setCellStyle(style4);
			 
			// 37
			 celda2=fila.createCell(37);
			 celda2.setCellType(CellType.FORMULA);
			 String re21=CellReference.convertNumToColString(37);
			 String formu37="SUM("+re21+"6:"+re21+libroSize+")";
			 celda2.setCellFormula(formu37);
			 celda2.setCellStyle(style4);
			 
			// 38
			 celda2=fila.createCell(38);
			 celda2.setCellType(CellType.FORMULA);
			 String re22=CellReference.convertNumToColString(38);
			 String formu38="SUM("+re22+"6:"+re22+libroSize+")";
			 celda2.setCellFormula(formu38);
			 celda2.setCellStyle(style4);
			 
			// 39
			 celda2=fila.createCell(39);
			 celda2.setCellType(CellType.FORMULA);
			 String re23=CellReference.convertNumToColString(39);
			 String formu39="SUM("+re23+"6:"+re23+libroSize+")";
			 celda2.setCellFormula(formu39);
			 celda2.setCellStyle(style4);
			 
			// 40
			 celda2=fila.createCell(40);
			 celda2.setCellType(CellType.FORMULA);
			 String re124=CellReference.convertNumToColString(40);
			 String formu40="SUM("+re124+"6:"+re124+libroSize+")";
			 celda2.setCellFormula(formu40);
			 celda2.setCellStyle(style4);
			 
			// 41
			 celda2=fila.createCell(41);
			 celda2.setCellType(CellType.FORMULA);
			 String re125=CellReference.convertNumToColString(41);
			 String formu41="SUM("+re125+"6:"+re125+libroSize+")";
			 celda2.setCellFormula(formu41);
			 celda2.setCellStyle(style4);
			 
			// 42
			 celda2=fila.createCell(42);
			 celda2.setCellType(CellType.FORMULA);
			 String re126=CellReference.convertNumToColString(42);
			 String formu42="SUM("+re126+"6:"+re126+libroSize+")";
			 celda2.setCellFormula(formu42);
			 celda2.setCellStyle(style4);
			 
			// 43
			 celda2=fila.createCell(43);
			 celda2.setCellType(CellType.FORMULA);
			 String re127=CellReference.convertNumToColString(43);
			 String formu43="SUM("+re127+"6:"+re127+libroSize+")";
			 celda2.setCellFormula(formu43);
			 celda2.setCellStyle(style4);
			 
			// 44
			 celda2=fila.createCell(44);
			 celda2.setCellType(CellType.FORMULA);
			 String re128=CellReference.convertNumToColString(44);
			 String formu44="SUM("+re128+"6:"+re128+libroSize+")";
			 celda2.setCellFormula(formu44);
			 celda2.setCellStyle(style4);
			 
			 
			 
			    pagina.autoSizeColumn(0);
				pagina.autoSizeColumn(1);
				pagina.autoSizeColumn(2);
				pagina.autoSizeColumn(3);
				pagina.autoSizeColumn(4);
				pagina.autoSizeColumn(5);
				pagina.autoSizeColumn(6);
				pagina.autoSizeColumn(7);
				pagina.autoSizeColumn(8);
				pagina.autoSizeColumn(9);
				pagina.autoSizeColumn(10);
				pagina.autoSizeColumn(11);
				pagina.autoSizeColumn(12);
				pagina.autoSizeColumn(13);
				pagina.autoSizeColumn(14);
				pagina.autoSizeColumn(15);
				pagina.autoSizeColumn(16);
				pagina.autoSizeColumn(17);
				pagina.autoSizeColumn(18);
				pagina.autoSizeColumn(19);
				pagina.autoSizeColumn(20);
				pagina.autoSizeColumn(21);
				pagina.autoSizeColumn(22);
				pagina.autoSizeColumn(23);
				pagina.autoSizeColumn(24);
				pagina.autoSizeColumn(25);
				pagina.autoSizeColumn(26);
				pagina.autoSizeColumn(27);
				pagina.autoSizeColumn(28);
				pagina.autoSizeColumn(29);
				pagina.autoSizeColumn(30);
				pagina.autoSizeColumn(31);
				pagina.autoSizeColumn(32);
				pagina.autoSizeColumn(33);
				pagina.autoSizeColumn(34);
				pagina.autoSizeColumn(35);
				pagina.autoSizeColumn(36);
				pagina.autoSizeColumn(37);
				pagina.autoSizeColumn(38);
				pagina.autoSizeColumn(39);
				pagina.autoSizeColumn(40);
				pagina.autoSizeColumn(41);
				pagina.autoSizeColumn(42);
				pagina.autoSizeColumn(43);
			 
			 try {
			        FileOutputStream salida = new FileOutputStream(archivo);
			        workbook.write(salida);
			        workbook.close();
			        salida.close();
			        System.out.println("Archivo creado existosamente");
			        return archivo.getName();
			    } catch (FileNotFoundException ex) {
			       System.out.println("Archivo no localizable en sistema de archivos");
			       return "0";
			    } catch (IOException ex) {
			        System.out.println("Error de entrada/salida");
			        return "0";
			    }
			 
			}	
		}

}
