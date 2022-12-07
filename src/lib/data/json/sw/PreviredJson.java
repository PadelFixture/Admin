package lib.data.json.sw;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import SWDB.sw_OrdenPagoPREVIREDB;
import lib.classSW.DatosTrabajadorFiniquito;
import lib.classSW.LiquidacionPeriodo;
import lib.classSW.OrdenDePagoPREVIRED;
import lib.classSW.Previred;
import lib.db.ConnectionDB;
import lib.db.sw.FiniquitosBD;
import lib.db.sw.PreviredDB;
import lib.struc.systemMenu;
import wordCreator.utils;

@Controller
public class PreviredJson {
	
	private final static Logger LOG = LoggerFactory.getLogger(PreviredJson.class);
	
	PreviredDB previredDb = new PreviredDB();
	
	FiniquitosBD finiquitosBD = new FiniquitosBD();
	
	@RequestMapping(value = "/work/obtenerPrevired", method = {RequestMethod.GET})
	public @ResponseBody String obtenerPrevired(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		
		//LOG.info("Obtener archivo Previred...");
		String urlDocGenerado = utils.obtenerCarpetaServidor() + File.separator;
		String nombreArchivo = "Previred"+ Calendar.getInstance().getTimeInMillis()+".txt";
		String nombreArchivo2 = "Previred"+ Calendar.getInstance().getTimeInMillis()+"1.txt";
	
		 String filePath = "C:/sello.png";
		//String filePath = utils.obtenerCarpetaServidor()+nombreArchivo;

		try {
			
			String periodo = request.getParameter("periodo");
			String empresa = request.getParameter("empresa");
			//LOG.info("Periodo seleccionado >> {}",periodo);
			InputStream fileInputStreamReader = this.generaFilePrevired(periodo, urlDocGenerado + nombreArchivo, empresa,urlDocGenerado + nombreArchivo2);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			
//			response.setContentType("text/plain");
//			response.setContentType("application/octet-stream");
//			response.setContentLength(bytes.length);
//			response.setCharacterEncoding("iso-8859-1");
			
			response.setContentType("text/html; charset=windows-1252");
			response.setCharacterEncoding("windows-1252");
			
//			response.setContentType("text/html; charset=UTF-8");
//			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-disposition", "attachment; filename= " + nombreArchivo + "");
			
		

			
		
			
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "1";
	}
	
	private InputStream generaFilePrevired(String periodo, String urlDocGenerado, String empresa,String urlDocGenerado2){
		ByteArrayInputStream is = null;
		try {
			
			File previred = new File(urlDocGenerado);
			FileWriter fw = new FileWriter(previred);
            BufferedWriter bw = new BufferedWriter(fw);
            ArrayList<Previred> previredLista = new ArrayList<>();
            List<Previred> previredLista2 = new ArrayList<>();
            List<Previred> previredLista3 = new ArrayList<>();
            ArrayList<Previred> listaFinal=new ArrayList<Previred>();
            // obtiene codigo trabajador id contrato que tengan liquidacion para x periodo
            List<LiquidacionPeriodo> lista5 = previredDb.obtenerLiquidacionPeriodotablaLiquidacion(periodo,empresa);
           
           
            int count = 0;
            int contadorfor = 0;
            int codAmterior = 0;

            for (LiquidacionPeriodo datos : lista5) 
            {
            	
            	
            	if(contadorfor == 0)
            	{
            		codAmterior = datos.getCodTrabajador();
            	}
					
            	if(codAmterior == datos.getCodTrabajador() && contadorfor >= 1)
            	{
            		count = 1;	
            	}
            	codAmterior = datos.getCodTrabajador();
            	previredLista2.addAll(previredDb.getDatosPreviredContratados(periodo,empresa,datos.getCodTrabajador() + "", datos.getIdContrato() + "",count));
            	contadorfor ++;	
            	count = 0;
			}// end for
            
//            previredLista2.addAll(previredDb.getDatosPreviredContratados(periodo,empresa));
//            previredLista2.addAll(previredDb.getDatosPreviredRetirados(periodo));
//            previredLista2.addAll(previredDb.getDatosPreviredCambioAfpIsapre(periodo));
           
            for(Previred p : previredLista2){
            	LiquidacionPeriodo liquidacion = new LiquidacionPeriodo();	
				liquidacion.setCodTrabajador(p.getCodTrabajador());
				liquidacion.setIdContrato(p.getIdContrato());
				liquidacion.setPeriodo(Integer.parseInt(periodo));
				List<LiquidacionPeriodo> lista = finiquitosBD.obtenerLiquidacionPeriodo(liquidacion);
			
				 int DescuentoCargaFA = 0;
				 int DescuentoAhorro = 0;
				 int valor_accidente_trabajo = 0;
				 int cotizacion_voluntaria = 0;
				for(LiquidacionPeriodo liq : lista){
					
					
					switch (liq.getIdConcepto()){
//						case 1:
//							break;
						
						case 200: //Tramo Asignacion Familiar
							p.setTramoAsignacionFamiliar(liq.getConcepto());
							break;
						case 202: //Cargas Simples
							p.setnCargasSimples(liq.getValor());
							break;
						case 201: //Cargas Maternales
							p.setnCargasMaternales(liq.getValor());
							break;
						case 203: //Cargas Invalidas
							p.setnCargasInvalidas(liq.getValor());
							break;
						case 204: //Asignacion Familiar
							p.setAsignacionFamiliar(liq.getValor());
							break;
						case 210: //Cotizacion Obligatoria Afp
							p.setCotizacionObligatoriaAfp(liq.getValor());
							break;
						case 211: //Cotizacion Seguro Invalidez Sobrevivencia
							p.setCotizacionSeguroInvalidezSobrevivencia(liq.getValor());
							break;
						case 2: //Dias Trabajados
							p.setDiasTrabajados(liq.getValor());
							break;
						case 48: //Cuenta Ahorro Voluntario Afp
							p.setCuentaAhorroVoluntarioAfp(liq.getValor());
							break;
						case 213: //Codigo Institucion Apvi
							p.setCodigoInstitucionApvi(liq.getValor());
							break;
						case 214: //Codigo Institucion Apvi
							p.setNumeroContratoApvi(liq.getConcepto());
							break;
						case 218: //Tasa Cotizacion ExCaja Regimen
							p.setTasaCotizacionExCajaRegimen(liq.getConcepto());
							p.setCotizacionObligatoriaIPS(liq.getValor());
							break;
						case 224: //Cotizacion Fonasa
							p.setCotizacionFonasa(liq.getValor());
							break;
						case 14: //Descuento Cargas Familiares CCAF
							DescuentoCargaFA = DescuentoCargaFA + Integer.parseInt(liq.getValor());
							break;
						case 15: //Descuento Cargas Familiares CCAF
							DescuentoCargaFA = DescuentoCargaFA + Integer.parseInt(liq.getValor());
							break;
						case 16: //Descuento Cargas Familiares CCAF
							DescuentoCargaFA = DescuentoCargaFA + Integer.parseInt(liq.getValor());
							break;
						case 17: //Descuento Cargas Familiares CCAF
							DescuentoCargaFA = DescuentoCargaFA + Integer.parseInt(liq.getValor());
							p.setAsignacionFamiliarRetroactiva(liq.getValor());
							break;
						case 230: //Mondeda PlanPactado Isapre
							p.setMondedaPlanPactadoIsapre(liq.getValor());
							break;
						case 232: //Cotizacion Obligatoria Isapre
							p.setCotizacionObligatoriaIsapre(liq.getValor());
							break;
						case 42: //Cotizacion Adicional Voluntaria
							cotizacion_voluntaria = cotizacion_voluntaria + Integer.parseInt(liq.getValor());
							
							break;
						case 191: //sueldo AFC
							p.setSueldo_afc(liq.getValor());
							break;
						case 32: //Cotizacion Adicional Voluntaria
							 String a = liq.getConcepto();
							 if(a.contains("TRIB2") || a.contains("TRIB1")){
								 cotizacion_voluntaria = cotizacion_voluntaria + Integer.parseInt(liq.getValor());
							 }else{
							
							 }
							break;
			/**/		case 45: //Descuentos Seguro Vida CCAF
								String b = liq.getConcepto();
								if(b.contains("SEGURO DE VIDA"))
								{
									p.setDescuentosSeguroVidaCCAF(liq.getValor());	
								}else if(b.contains("CCAF AHORRO PARA LA VIVIENDA NO") || b.contains("AHORRO PARA LA VIVIENDA NO") || b.contains("CCAF LOS ANDES AHORRO") )
								{
									DescuentoAhorro = DescuentoAhorro + Integer.parseInt(liq.getValor());
								}
							break;
		    /**/		case 140: //Otros Descuentos CCAF
							p.setOtrosDescuentosCCAF(liq.getValor());
							break;
						case 241: //Cotizacion CCAF No Afil Isapres
							p.setCotizacionCCAFNoAfilIsapres(liq.getValor());
							break;
		   /**/			case 143: //Otros Descuentos CCAF 1
							p.setOtrosDescuentosCCAF1(liq.getValor());
							break;
	      /**/			case 144: //Otros Descuentos CCAF 2
							p.setOtrosDescuentosCCAF2(liq.getValor());
							break;
						case 94: //Cotizacion Accidente Trabajo
							valor_accidente_trabajo = valor_accidente_trabajo + Integer.parseInt(liq.getValor());
							break;
						case 95: 
							valor_accidente_trabajo = valor_accidente_trabajo + Integer.parseInt(liq.getValor());
							break;
						case 96: 
							valor_accidente_trabajo = valor_accidente_trabajo + Integer.parseInt(liq.getValor());
							break;
						case 97: 
							valor_accidente_trabajo = valor_accidente_trabajo + Integer.parseInt(liq.getValor());
							break;
		  /**/				case 150: //Sucursal Pago Mutual
							p.setSucursalPagoMutual(liq.getValor());
							break;
						case 252: //Aporte Trabajador Seguro Cesantia
							p.setAporteTrabajadorSeguroCesantia(liq.getValor());
							break;
						case 253: //Aporte Empleador Seguro Cesantia
							p.setAporteEmpleadorSeguroCesantia(liq.getValor());
							break;
						case 256: 
							p.setTopeImL(liq.getValor());
							break;
						case 34: 
							p.setCotizacionApvi(liq.getValor());
							break;
							
						case 257:
							p.setImponibleL(liq.getValor());
							p.setRentaImponibleIPS(liq.getValor());
							p.setRentaImponibleSeguroCesantia(liq.getValor());
							break;
						case 0:
							if(liq.getConcepto().equals("Tope Seguro")){
							p.setTopeseguro(liq.getValor());	
							}
							break;
						case 92: 
							p.setBaseAFC(liq.getValor());
							break;
						case 281: 
							p.setBasesanna(liq.getValor());
							break;
						default:
							
							break;
							
							
					} // end case
				} 
                previredLista3.add(p);
                //p.setDescuentoCargasFamiliaresISL(""+DescuentoCargaFA+"");
                p.setDescCargasFamiliaresCCAF(""+DescuentoCargaFA+"");
                p.setCotizacionAccidenteTrabajo(""+valor_accidente_trabajo+"");
                p.setCotizacionAdicionalVoluntaria(""+cotizacion_voluntaria);
                p.setDescuentosLeasing(""+DescuentoAhorro);
                
                DescuentoCargaFA = 0;
                DescuentoAhorro = 0;
                valor_accidente_trabajo = 0;
            }// end for previredLista2
            
         
            
			for (Previred datospermisolicencia : previredLista3) {
				   
				previredLista.addAll(previredDb.getDatosPreviredpermisoyLicencia(periodo,empresa,datospermisolicencia));
               
			}
			
			int codTrabajadorX=0;
			boolean flag=true;
			//codigo trabajador
			previredLista.get(0).getCodTrabajador();//integer
			//tipo linea
			previredLista.get(0).getTipoLinea(); //String 
			//
			boolean existe02=false;
			boolean existe01=false;
			boolean existe00=false;
			ArrayList<Previred> u=previredLista;
			
			while(flag==true)
			{			
				if(u.size()!=0){
					codTrabajadorX=u.get(0).getCodTrabajador();
					existe00=false;
					existe01=false;
					existe02=false;
					for(int a=0;a<u.size();a++){
						
						if(u.get(a).getCodTrabajador()==codTrabajadorX){
								if(u.get(a).getTipoLinea().equals("00")){
									existe00=true;
								}
								if(u.get(a).getTipoLinea().equals("01")){
									existe01=true;
								}
								// segundo contrato
								if(u.get(a).getTipoLinea().equals("02")){
									existe02=true;
								}
						}
						else{
							
						}
					}
					for(int i=0;i<u.size();i++){
						
						//
						if(existe00){
							if(u.get(i).getTipoLinea().equals("00")){
								listaFinal.add(u.remove(i));
								break;
							}
						}
						else{
							if(existe01 && existe02){
								if(u.get(i).getTipoLinea().equals("02")){
									listaFinal.add(u.remove(i));
									break;
								}
							}
							else if(existe01 && !existe02){
								if(u.get(i).getTipoLinea().equals("01")){
									listaFinal.add(u.remove(i));
									break;
								}
							
							}
							else if(!existe01 && existe02){
								if(u.get(i).getTipoLinea().equals("02")){
									listaFinal.add(u.remove(i));
									break;
								}
							
							}
							
						}
					}
				}
				else
				{
					flag=false;					
				}
			}// end while
			
	        
			String dattxt = "";
			// imprime en txt fila
            for(Previred p2 : listaFinal)
            {
                bw.write(this.contenidoPrevired(p2));
                dattxt = dattxt + this.contenidoPrevired(p2);
                
            }
           
            File f= new File(urlDocGenerado2);
    	    FileUtils.writeStringToFile(f,dattxt, "windows-1252");
    	    
    	    
    	    // INSERTAR DATOS EN TABLA PARA LUEGO OCUPARLOS PARA CENTRALIZAR 
    	    String sql = "";
    	    String sql2 = "";
    	    String sql3 = "";
    	    String sql4 = "";
    	    String sql5 = "";
    	    String sql6 = "";
    	    String sql7 = "";
    	    String sql8 = "";
    	    String sql9 = "";
			ConnectionDB db = new ConnectionDB();
			PreparedStatement ps = null;
			PreparedStatement ps4 = null;
			PreparedStatement ps6 = null;
			PreparedStatement ps7 = null;
			PreparedStatement ps8 = null;
	
			ArrayList<OrdenDePagoPREVIRED> Lista = new ArrayList<OrdenDePagoPREVIRED>();
			
			
			
			
    	    sql4 = "DELETE FROM Previred_txt_periodo WHERE periodo = "+periodo+" AND sociedad = "+empresa+"";
    	    ps4 = db.conn.prepareStatement(sql4);
 		    ps4.execute(sql4);
 		    sql6 = "DELETE FROM sw_previred_file WHERE periodo = "+periodo+" AND sociedad = "+empresa+"";
 		   	ps6 = db.conn.prepareStatement(sql6);
		    ps6.execute(sql6);
			
			sql2 = "SELECT "
					    +"UPPER((SELECT "
					                    +"denominacionSociedad "
					                +"FROM "
					                    +"sociedad "
					                +"WHERE "
					                    +"idSociedad = "+empresa+")) AS SOCIEDAD, "
					    +"concepto AS CONCEPTO,"
					    +"if(proveedor is null,'0010001644',proveedor ) AS PROVEEDOR," 
					    +"IF(UPPER((SELECT "
					                    +"descripcion "
					                +"FROM "
					                    +"parametros "
					                +"WHERE "
					                    +"codSap = proveedor "
					                +"LIMIT 1)) is null,'FONASA', UPPER((SELECT descripcion FROM parametros WHERE codSap = proveedor LIMIT 1))) AS NOMBRE_PROVEEDOR,"
					    +"CAST(SUM(valor) AS SIGNED) AS VALOR "
					+"FROM "
					    +"(SELECT "
					    		+"d.codSap as codsapnull,"
					            +"sociedad,"
					            +"idConcepto,"
					            +"CASE "
					                +"WHEN idConcepto IN (31 , 91, 48) THEN 'AFP' "
					                +"WHEN "
					                    +"idConcepto IN (92 , 33) "
					                        +"AND p.descripcion != 'S.S.S.' "
					                +"THEN "
					                    +"'AFP' "
					                +"WHEN "
					                    +"idConcepto IN (92 , 33) "
					                        +"AND p.descripcion = 'S.S.S.' "
					                +"THEN "
					                    +"'AFC' "
					                +"WHEN idConcepto IN (32 , 42) THEN 'SALUD' "
					                +"WHEN idConcepto IN (94 , 95, 96, 97, 98) THEN 'COSTO PATRONAL' "
					                +"WHEN idConcepto IN (34) THEN 'AFP' "
					                +"WHEN idConcepto IN (14 , 15, 16, 17) THEN 'ASIGNACIONES FAMILIARES' "
									+"WHEN idConcepto IN (45) AND d.descripcion = 'CCAF LOS ANDES AHORRO' THEN 'AHORRO' "
					                +"WHEN idConcepto IN (45) AND d.descripcion = 'CCAF LOS ANDES CREDITOS' THEN 'CREDITOS' "
					                +"WHEN idConcepto IN (45) AND d.descripcion = 'SEGURO DE VIDA' THEN 'SEGURO DE VIDA' "
					                +"ELSE idConcepto "
					            +"END AS concepto, "
					            +"CASE "
					                +"WHEN idConcepto IN (- 1) THEN 'COSTO PATRONAL' "
					                +"WHEN idConcepto IN (45) AND d.descripcion = 'CCAF LOS ANDES AHORRO' THEN d.proveedor "
					                +"WHEN idConcepto IN (45) AND d.descripcion = 'CCAF LOS ANDES CREDITOS' THEN d.proveedor "
					                +"WHEN idConcepto IN (45) AND d.descripcion = 'SEGURO DE VIDA' THEN d.proveedor "
					                +"WHEN idConcepto IN (98) THEN d.descripcion "
					                +"ELSE CASE "
					                    +"WHEN idConcepto = 34 THEN p2.codSap "
					                    +"WHEN "
					                        +"idConcepto IN (92 , 33) "
					                            +"AND p.descripcion = 'S.S.S.' "
					                    +"THEN "
					                        +"'0010001643' "
					                    +"WHEN idConcepto IN (98) THEN d.proveedor "
					                    +"ELSE p.codSap "
					                +"END "
					            +"END AS proveedor,"
					            +"valor "
					    +"FROM "
					        +"trabajadores t "
					    +"INNER JOIN sw_liquidacionDetalle d ON (periodo = "+periodo+" "
					        +"AND (d.idConcepto IN (14 , 15, 16, 17, 31, 32, 94, 95, 96, 97, 91, 92, 33, 34, 42, 48) or (d.idConcepto = 45   and d.descripcion = 'CCAF LOS ANDES AHORRO') or (d.idConcepto = 45 and d.descripcion = 'CCAF LOS ANDES CREDITOS') or (d.idConcepto = 45 and d.descripcion = 'SEGURO DE VIDA ')) "
					        +"AND t.codigo = d.codTrabajador) "
					    +"INNER JOIN contratos c ON (c.codigo_trabajador = d.codTrabajador "
					        +"AND c.id = d.idContrato) "
					    +"INNER JOIN sociedad s ON (s.idSociedad = c.idSociedad "
					        +"AND sociedad = (SELECT " 
					            +"sociedad "
					        +"FROM "
					            +"sociedad "
					        +"WHERE "
					            +"idSociedad = "+empresa+")) "
					    +"LEFT JOIN parametros p ON (p.codigo IN ('AFP' , 'ISAPRE', 'CAJA_COMPENSACION', 'MUTUALES') "
					        +"AND p.rutParametro = d.proveedor) "
					    +"LEFT JOIN parametros p2 ON (p2.codigo IN ('APV') "
					        +"AND p2.rutParametro = d.proveedor) "
					    +"WHERE "
					        +"periodo = "+periodo+" "
					            +"AND (idConcepto IN (14 , 15, 16, 17, 31, 32, 94, 95, 96, 97, 91, 92, 33, 34, 42, 48,45,241) or (d.idConcepto = 45 and d.descripcion = 'CCAF LOS ANDES AHORRO') or (d.idConcepto = 45 and d.descripcion = 'CCAF LOS ANDES CREDITOS') or (d.idConcepto = 45 and d.descripcion = 'SEGURO DE VIDA ') ) "
					            +"AND sociedad = CONCAT('', (SELECT " 
					                +"sociedad "
					            +"FROM "
					                +"sociedad "
					            +"WHERE "
					                +"idSociedad = "+empresa+"), '') "
					            +"AND valor > 0) AS m where m.codsapnull is not null "
					+"GROUP BY sociedad , concepto ,proveedor "
					+"ORDER BY concepto ";
			System.out.println(sql2);
			
			
			ps = db.conn.prepareStatement(sql2);
			ResultSet rs = ps.executeQuery(sql2);
			// obtener nombres de la cabecera para de la consulta sql para despues añadirlas al excel
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			OrdenDePagoPREVIRED tr1 = new OrdenDePagoPREVIRED();
			for (int i = 1; i <= columnCount; i++ ) {
			  String name = rsmd.getColumnName(i);
			    
				if(i == 1){tr1.setSociedad(name);}
				else if(i == 2){tr1.setConcepto(name);}
				else if(i == 3){tr1.setProveedor(name);}
				else if(i == 4){tr1.setNombreproveedor(name);}
				else if(i == 5){tr1.setValor(name);}
				
			}
			
			Lista.add(tr1);
			
			String r = "";
			
			
			while (rs.next()) {
				
				sql3 = "INSERT INTO Previred_txt_periodo ( nombre_proveedor, valor,sociedad,periodo,proveedor,concepto,estado) "
						+ "values (?,?,?,?,?,?,?)";
	            PreparedStatement statement3 = db.conn.prepareStatement(sql3);
	            

	            statement3.setString(1, rs.getString("NOMBRE_PROVEEDOR"));
	            statement3.setInt(2, rs.getInt("VALOR"));
	            statement3.setInt(3, Integer.parseInt(empresa)); // sociedad
	            statement3.setInt(4, Integer.parseInt(periodo) ); // periodo
	            statement3.setInt(5, rs.getInt("PROVEEDOR"));
	            statement3.setString(6, rs.getString("CONCEPTO"));
	            statement3.setInt(7, 1); // estado
	            statement3.executeUpdate();
	            OrdenDePagoPREVIRED tr = new OrdenDePagoPREVIRED();
				
				tr.setSociedad(rs.getString("SOCIEDAD"));
				tr.setConcepto(rs.getString("CONCEPTO"));
				tr.setProveedor(rs.getString("PROVEEDOR"));
				tr.setNombreproveedor(rs.getString("NOMBRE_PROVEEDOR"));
				tr.setValor(rs.getString("VALOR"));

				Lista.add(tr);

			}
			
			
			sql7 = "select idCajaCompensacion from sociedad where idSociedad = "+empresa+"";
			System.out.println(sql7);
			ps7 = db.conn.prepareStatement(sql7);
			ResultSet rs7 = ps7.executeQuery(sql7);
			
			while (rs7.next()) {
				if(rs7.getInt("idcajaCompensacion") ==1){
					
					
					sql8 = "select "
							+"(select upper(denominacionSociedad) from sociedad where idSociedad = "+empresa+") as SOCIEDAD," 
							+"'SALUD' AS CONCEPTO, "
							+"0010001644 AS PROVEEDOR,"
							+"'FONASA' AS NOMBRE_PROVEEDOR, "
							+"CAST(SUM(valor) AS SIGNED) AS VALOR from sw_liquidacionDetalle sw  "
							+"inner join contratos co on sw.idContrato = co. id where co.idSociedad = "+empresa+" and sw.periodo = "+periodo+" "
							+"and sw.descripcion = 'CAJA (0,6%)' and sw.idConcepto = 32 ";
					System.out.println(sql8);
					ps8 = db.conn.prepareStatement(sql8);
					ResultSet rs8 = ps8.executeQuery(sql8);
					System.out.println(sql8);
					while (rs8.next()) {
						
						sql9 = "INSERT INTO Previred_txt_periodo ( nombre_proveedor, valor,sociedad,periodo,proveedor,concepto,estado) "
								+ "values (?,?,?,?,?,?,?)";
						System.out.println(sql9);
			            PreparedStatement statement4 = db.conn.prepareStatement(sql9);
			            

			            statement4.setString(1,rs8.getString("NOMBRE_PROVEEDOR") );
			            statement4.setInt(2, rs8.getInt("VALOR"));
			            statement4.setInt(3, Integer.parseInt(empresa)); // sociedad
			            statement4.setInt(4, Integer.parseInt(periodo) ); // periodo
			            statement4.setInt(5, rs8.getInt("PROVEEDOR"));
			            statement4.setString(6, rs8.getString("CONCEPTO"));
			            statement4.setInt(7, 1); // estado
			            statement4.executeUpdate();
			            OrdenDePagoPREVIRED tr2 = new OrdenDePagoPREVIRED();
						
						tr2.setSociedad(rs8.getString("SOCIEDAD"));
						tr2.setConcepto(rs8.getString("CONCEPTO"));
						tr2.setProveedor(rs8.getString("PROVEEDOR"));
						tr2.setNombreproveedor(rs8.getString("NOMBRE_PROVEEDOR"));
						tr2.setValor(rs8.getString("VALOR"));

						Lista.add(tr2);

					}
				}
			}
			
			
			
			
			
			
			// crear excel orden de pago
			r = sw_OrdenPagoPREVIREDB.generarExcelPREVIREDinstitucion(Lista);
			
			sql = "INSERT INTO sw_previred_file (concepto,sociedad, periodo,archivo,estado) values (?, ?, ?, ?,?)";
            PreparedStatement statement = db.conn.prepareStatement(sql);
            Blob blob = null;
            statement.setString(1, "TXT");
            statement.setInt(2, Integer.parseInt(empresa));
            statement.setInt(3, Integer.parseInt(periodo));
            InputStream inputStream = new FileInputStream(new File(urlDocGenerado2));
            byte[] content = IOUtils.toByteArray(inputStream);
            blob = new SerialBlob(content);//debugger says content is empty here
          
            statement.setBlob(4, blob);
            statement.setInt(5, 1);
            
            int row = statement.executeUpdate();
            
            
            		// insertar excel en base de datos
         			 sql5 = "INSERT INTO sw_previred_file (concepto,sociedad, periodo,archivo,estado) values (?, ?, ?, ?,?)";
                     PreparedStatement statement6 = db.conn.prepareStatement(sql5);
                     Blob blob2 = null;
                     statement6.setString(1, "EXCEL");
                     statement6.setInt(2, Integer.parseInt(empresa));
                     statement6.setInt(3, Integer.parseInt(periodo));
                     InputStream inputStream6 = new FileInputStream(new File(r));
                     byte[] content6 = IOUtils.toByteArray(inputStream6);
                     blob2 = new SerialBlob(content6);//debugger says content is empty here
                   
                     statement6.setBlob(4, blob2);
                     statement6.setInt(5, 1);
                     
                     
                    
          
                     statement6.executeUpdate();
            if (row > 0) {
                
            }
            
            bw.close();			
			is = new ByteArrayInputStream(FileUtils.readFileToByteArray(previred));

			if (previred.exists()) {
				previred.delete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return is;
	}
	
	private String contenidoPrevired(Previred previred){
		String contenido = "";
		//Datos del trabajador
		
/*01*/	contenido += String.format("%011d", Integer.parseInt(previred.getRutTrabajador()));
/*02*/	contenido += String.format("%-1s", previred.getDvTrabajador());
/*03*/	contenido += String.format("%-30s", previred.getApellidoPaterno().toUpperCase());
/*04*/	contenido += String.format("%-30s", previred.getApellidoMaterno().toUpperCase());
/*05*/	contenido += String.format("%-30s", previred.getNombres().toUpperCase());
/*06*/	contenido += String.format("%-1s", previred.getSexo());
		
		if(previred.getNacionalidad() == null || previred.getNacionalidad() == "null"){
/*07*/			contenido += String.format("%01d", Integer.parseInt("0"));
		}else{
/*07*/			contenido += String.format("%01d", Integer.parseInt(previred.getNacionalidad()));
		}
		
/*08*/	contenido += String.format("%02d", Integer.parseInt(previred.getTipoPago()));
/*09*/	contenido += String.format("%06d", Integer.parseInt(previred.getPeriodoDesde()));
/*10*/	contenido += String.format("%06d", Integer.parseInt(previred.getPeriodoHasta()));
/*11*/	contenido += String.format("%-3s", previred.getRegimenPrevisional());
/*12*/	contenido += String.format("%01d", Integer.parseInt(previred.getTipoTrabajador()));
/*13*/	contenido += String.format("%02d", Integer.parseInt(previred.getDiasTrabajados()));




/*14*/	contenido += String.format("%-2s", previred.getTipoLinea());
/*15*/  contenido += String.format("%02d", Integer.parseInt(previred.getCodigoMovimientoPersonal()));

/*16*/	contenido += String.format("%-10s", previred.getFechaDesde());
/*17*/	contenido += String.format("%-10s", previred.getFechaHasta());
		
		if(previred.getTramoAsignacionFamiliar() == ""){
/*18*/			contenido += String.format("%-1s", "D");
		}else{
/*18*/			contenido += String.format("%-1s", previred.getTramoAsignacionFamiliar());
		}
/*19*/	contenido += String.format("%02d", Integer.parseInt(previred.getnCargasSimples()));
/*20*/	contenido += String.format("%01d", Integer.parseInt(previred.getnCargasMaternales()));
/*21*/	contenido += String.format("%01d", Integer.parseInt(previred.getnCargasInvalidas()));
/*22*/	contenido += String.format("%06d", Integer.parseInt(previred.getAsignacionFamiliar().replace(".", "")));
/*23*/	contenido += String.format("%06d", Integer.parseInt(previred.getAsignacionFamiliarRetroactiva()));
	
		String montoreintegro = previred.getReintegroCargasFamiliares();
		if(montoreintegro == null || montoreintegro == "null"){
			montoreintegro = "0";
		}
/*24*/		contenido += String.format("%06d", Integer.parseInt(montoreintegro));
		
		if(previred.getSolicitudTrabajadorJoven().equals("") || previred.getSolicitudTrabajadorJoven().equals("null") )
		{
/*25*/			contenido += String.format("%-1s", "N");
		}else{
/*25*/			contenido += String.format("%-1s", previred.getSolicitudTrabajadorJoven());
		}
		
		//Datos AFP    
		if("7".equals(previred.getIdafp())){
			
			if(previred.getValorsss() == null || previred.getValorsss() == "null"){
/*26*/				contenido += String.format("%02d", Integer.parseInt(""));
			}else{
/*26*/				contenido += String.format("%02d", Integer.parseInt(previred.getValorsss()));
			}
			
		}else{
/*26*/				contenido += String.format("%02d", Integer.parseInt(previred.getCodigoAfp()));
		}
		
		int imponibAFP = 0;
		int topelAFP = 0;
		String RentaImponibleCCAF_AFP = "";
		
		
		if(previred.getTopeImL() == null || previred.getTopeImL() == "null"){
			topelAFP = 0;
		}else{
			topelAFP = Integer.parseInt(previred.getTopeImL());
		}
		
		if(previred.getImponibleL() == null || previred.getImponibleL() == "null"){
			imponibAFP = 0;
		}else{
			imponibAFP = Integer.parseInt(previred.getImponibleL());
		}
		
		if(imponibAFP > topelAFP)
		{
			RentaImponibleCCAF_AFP = ""+topelAFP+"";
		}else
		{
			RentaImponibleCCAF_AFP = ""+imponibAFP+"";
		}
		
		
		if(previred.getRegimenPrevisional().equals("INP") || previred.getPensionado() == 1)
		{
/*27*/ 		contenido += String.format("%08d", Integer.parseInt("0"));
		}else
		{
			if(previred.getTipoLinea().equals("00"))
			{
/*27*/			contenido += String.format("%08d", Integer.parseInt(RentaImponibleCCAF_AFP.replace(".", "")));
			}
			else if(previred.getTipoLinea().equals("02"))
			{
/*27*/			contenido += String.format("%08d", Integer.parseInt(RentaImponibleCCAF_AFP.replace(".", "")));
			} 
			else
			{
/*27*/ 			contenido += String.format("%08d", Integer.parseInt("0"));
			}
		  }
		  
		  if(previred.getRegimenPrevisional().equals("INP"))
		  {
/*28*/		contenido += String.format("%08d", Integer.parseInt("0"));
		  }else
		  {
/*28*/		contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionObligatoriaAfp().replace(".", ""))).substring(0, 8);
		  }
		
/*29*/	contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionSeguroInvalidezSobrevivencia().replace(".", "")));
/*30*/	contenido += String.format("%08d", Integer.parseInt(previred.getCuentaAhorroVoluntarioAfp()));
/*31*/	contenido += String.format("%08d", Integer.parseInt(previred.getRentaImponibleSustAfp()));
/*32*/	contenido += String.format("%05d", Integer.parseInt(previred.getTasaPactadaSustit()));
/*33*/	contenido += String.format("%09d", Integer.parseInt(previred.getAporteIndemnSustit()));
/*34*/	contenido += String.format("%02d", Integer.parseInt(previred.getnPeriodosSustit()));
/*35*/	contenido += String.format("%-10s", previred.getPreiodoDesdeSustit());
/*36*/	contenido += String.format("%-10s", previred.getPeriosoHastaSustit());
/*37*/	contenido += String.format("%-40s", previred.getPuestoTrabajoPesado());
/*38*/	contenido += String.format("%05d", Integer.parseInt(previred.getPorcCotizacionTrabajoPesado()));
/*39*/	contenido += String.format("%06d", Integer.parseInt(previred.getCotizacionTrabajoPesado()));
		
		//Datos ahorro previsional voluntario individual
/*40*/	contenido += String.format("%03d", Integer.parseInt("00"+previred.getCodigoInstitucionApvi()));
/*41*/	contenido += String.format("%-20s", previred.getNumeroContratoApvi());
/*42*/	contenido += String.format("%01d", Integer.parseInt(previred.getFormaPagoApvi()));
/*43*/	contenido += String.format("%08d",  Integer.parseInt(previred.getCotizacionApvi().replace(".", "")));
/*44*/	contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionDepositosConvenidos()));
		
		//Datos ahorro previsional voluntario individual
/*45*/	contenido += String.format("%03d", Integer.parseInt(previred.getCodigoIntitucionAutorizadaApvc()));
/*46*/	contenido += String.format("%-20s", previred.getNumeroContratoApvc());
/*47*/	contenido += String.format("%01d", Integer.parseInt(previred.getFormaPagoApvc()));
/*48*/	contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionTrabajadorApvc()));
/*49*/	contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionEmpleadorApvc()));
		
		//Datos afiliado voluntario
/*50*/	contenido += String.format("%011d", Integer.parseInt(previred.getRutAfiliadoVoluntario()));
/*51*/	contenido += String.format("%-1s", previred.getDvAfiliadoVoluntario());
/*52*/	contenido += String.format("%-30s", previred.getApellidoPaternoAfiliado());
/*53*/	contenido += String.format("%-30s", previred.getApellidoMaternoAfiliado());
/*54*/	contenido += String.format("%-30s", previred.getNombresAfiliado());
/*55*/	contenido += String.format("%02d", Integer.parseInt(previred.getCodigoMovimientoPersonalAfiliado()));
/*56*/	contenido += String.format("%-10s", previred.getFechaDesdeAfililado());
/*57*/	contenido += String.format("%-10s", previred.getFechaHastaAfiliado());
/*58*/	contenido += String.format("%02d", Integer.parseInt(previred.getCodigoAfpAfiliado()));
/*59*/	contenido += String.format("%08d", Integer.parseInt(previred.getMontoCapitalizacionVoluntaria()));
/*60*/	contenido += String.format("%08d", Integer.parseInt(previred.getMontoAhorroVoluntarioAfiliado()));
/*61*/	contenido += String.format("%02d", Integer.parseInt(previred.getNumPeriodosCotizacionAfiliado()));
		
		//Datos IPS - ISL - FONASA
		
		if("7".equals(previred.getIdafp()))
		{
/*62*/  	contenido += String.format("%04d", Integer.parseInt(previred.getCodigosssExcaja()));
		}else
		{
/*62*/		contenido += String.format("%04d", Integer.parseInt("0"));;
		}
		
		// Tasa Cotizacion ExCaja Regimen
		if(previred.getTasaCotizacionExCajaRegimen() != "0")
		{
			String stringEXtasa = previred.getTasaCotizacionExCajaRegimen();
	        String[] partsEXtasa = stringEXtasa.split("\\(");
	        String porcentajeEXtasa = partsEXtasa[1];
	        String[] partsEXtasa2 = porcentajeEXtasa.split("\\%");
/*63*/ 		contenido += String.format("%5s", partsEXtasa2[0].replace(",", ".")).replace(' ','0');
		}else
		{
/*63*/  	contenido += String.format("%05d", Integer.parseInt(previred.getTasaCotizacionExCajaRegimen()));
		}
	
		 String RentaImponible_ips_ = "";
	     int imponible_ips = 0;
			
		 if(previred.getTopeImL() == null || previred.getTopeImL() == "null")
		 {
			 topelAFP = 0;
		 }else
		 {
			 topelAFP = Integer.parseInt(previred.getTopeImL());
		 }
			
		 if(previred.getImponibleL() == null || previred.getImponibleL() == "null")
		 {
			 imponible_ips = 0;
		 }else
		 {
			imponible_ips = Integer.parseInt(previred.getRentaImponibleIPS().replace(".", ""));
		 }
			
		 if(imponibAFP > topelAFP)
		 {
			 RentaImponible_ips_ = ""+topelAFP+"";
		 }else
		 {
			RentaImponible_ips_ = ""+imponible_ips+"";
		 }
			

  
		 if(previred.getIsapreFonasa() != 13)
		 {
/*64*/		contenido += String.format("%08d", Integer.parseInt("0")); 
		 }else
		 {
			if(previred.getTipoLinea().equals("00")){
/*64*/			contenido += String.format("%08d", Integer.parseInt(RentaImponible_ips_.replace(".", "")));
			}
			else if(previred.getTipoLinea().equals("02"))
			{
/*64*/			contenido += String.format("%08d", Integer.parseInt(RentaImponible_ips_.replace(".", "")));
			} 
			else
			{
/*64*/  		contenido += String.format("%08d", Integer.parseInt("0"));	
			}		
		 }
  
		contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionObligatoriaIPS()));
		contenido += String.format("%08d", Integer.parseInt(previred.getRentaImponibleDesahucion())); // DEFAULT 0
		contenido += String.format("%04d", Integer.parseInt(previred.getCodigoExCajaRegimenDesahucion()));
		contenido += String.format("%05d", Integer.parseInt(previred.getTasaCotDeshaExCajaPrevision()));
		contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionDesahucion()));
		
		String valorNoafiliado = "";
		if(previred.getIsapreFonasa() != 13){
			valorNoafiliado = "0";
		}else{
			valorNoafiliado = previred.getCotizacionCCAFNoAfilIsapres().replace(".", "");
			
		}
		
		if (Integer.parseInt("" + previred.getId_cajamutal()) == 1) {

			int totalfonasasincaja = 0;
			totalfonasasincaja = Integer.parseInt(previred.getCotizacionFonasa().replace(".", ""))
					+ Integer.parseInt(valorNoafiliado);

/* 70 */ contenido += String.format("%08d", totalfonasasincaja);
		} else {
			contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionFonasa().replace(".", "")));
/*70*/
		}
		
		
		
		
		contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionAccidenteTrabajoISL()));
		contenido += String.format("%08d", Integer.parseInt(previred.getBonificacionLey15386()));
		contenido += String.format("%08d", Integer.parseInt(previred.getDescuentoCargasFamiliaresISL()));
		contenido += String.format("%08d", Integer.parseInt(previred.getBonoGobierno()));
		
		//Datos salud
		contenido += String.format("%02d", Integer.parseInt(previred.getCodigoInstitucionSalud()));
  /*76*/contenido += String.format("%-16s", previred.getNumeroFUN());
		
		int imponib = 0;
		int topel = 0;
		
		if(previred.getTopeImL() == null || previred.getTopeImL() == "null"){
			topel = 0;
		}else{
			topel = Integer.parseInt(previred.getTopeImL());
		}
		
		if(previred.getImponibleL() == null || previred.getImponibleL() == "null"){
			imponib = 0;
		}else{
			imponib = Integer.parseInt(previred.getImponibleL());
		}
		
		// RENTA IMPONIBLE ISAPRE
		String  RentaImponibleIsapre = "";
		
		if(previred.getIsapreFonasa() != 13){
			if(imponib > topel){
				RentaImponibleIsapre = ""+topel+"";
			}else{
				RentaImponibleIsapre = ""+imponib+"";
			}
		}else{
			RentaImponibleIsapre = "0";
		}
		
		contenido += String.format("%08d", Integer.parseInt(RentaImponibleIsapre));
		// algunos trabajadores en la tabla de trabajadores tiene cambiado su moneda en 0
/*78*/  contenido += String.format("%01d", Integer.parseInt(previred.getMondedaPlanPactadoIsapre()));
		
		//contenido += String.format("%08d", (int)Double.parseDouble((previred.getCotizacionPactada())));
		

/*79*/ contenido += String.format("%8s", previred.getCotizacionPactada().replace(",", ".")).replace(' ','0');
		
//		Double.parseDouble(liq.getValor().replace(".", "").replace(",", "."))
/*80*/	contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionObligatoriaIsapre().replace(".", ""))).substring(0, 8);
/*81*/	contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionAdicionalVoluntaria()));
/*82*/	contenido += String.format("%08d", Integer.parseInt(previred.getMontoGarantiaGES()));
		
		//Datos caja de compensacion
/*83*/	contenido += String.format("%02d", Integer.parseInt(previred.getCodigoCCAF()));
		
		
		String RentaImponibleCCAF2 = "";
		
		
		
		
		
		
		if(previred.getIsapreFonasa() == 13){
			if(imponib > topel){
				RentaImponibleCCAF2 = ""+topel+"";
			}else{
				RentaImponibleCCAF2 = ""+imponib+"";
			}
		}else{
			RentaImponibleCCAF2 = "0";
		}

		
		if(previred.getTipoLinea().equals("00")){
			
			if (Integer.parseInt("" + previred.getId_cajamutal()) == 1) {
				/*84*/	contenido += String.format("%08d", Integer.parseInt("0"));
			}else{
				/*84*/	contenido += String.format("%08d", Integer.parseInt(RentaImponibleCCAF2));
			}
			
		}
		else if(previred.getTipoLinea().equals("02")){
			if (Integer.parseInt("" + previred.getId_cajamutal()) == 1) {
				/*84*/	contenido += String.format("%08d", Integer.parseInt("0"));
			}else{
				/*84*/	contenido += String.format("%08d", Integer.parseInt(RentaImponibleCCAF2));
			}
	  } 
		else{
			/*84*/  contenido += String.format("%08d", Integer.parseInt("0"));
			
		}		

		
		
		String creditoccaf = previred.getCreditosPersonalesCCAF();
		if(creditoccaf == null || montoreintegro == "null"){
			creditoccaf = "0";
		}
/*85*/  contenido += String.format("%08d", Integer.parseInt(creditoccaf));
/*86*/	contenido += String.format("%08d", Integer.parseInt(previred.getDescuentoDentalCCAF()));

		if(previred.getTipoLinea().equals("00"))
		{
/*87*/		contenido += String.format("%08d", Integer.parseInt(previred.getDescuentosLeasing()));
		}
		else if(previred.getTipoLinea().equals("02"))
		{
/*87*/	contenido += String.format("%08d", Integer.parseInt(previred.getDescuentosLeasing()));
		} 
		else{
/*87*/	contenido += String.format("%08d", Integer.parseInt("0"));			
		}

		if(previred.getTipoLinea().equals("00"))
		{
/*88*/	contenido += String.format("%08d", Integer.parseInt(previred.getDescuentosSeguroVidaCCAF()));
		}
		else if(previred.getTipoLinea().equals("02"))
		{
/*88*/	contenido += String.format("%08d", Integer.parseInt(previred.getDescuentosSeguroVidaCCAF()));
		} 
		else{
/*88*/	contenido += String.format("%08d", Integer.parseInt("0"));			
		}


/*89*/	contenido += String.format("%08d", Integer.parseInt(previred.getOtrosDescuentosCCAF()));
		
		
		
		System.out.println("///// id caja");
		System.out.println();
		
		if(Integer.parseInt(""+previred.getId_cajamutal()) == 1){

/*90*/	contenido += String.format("%08d", Integer.parseInt("0"));
		}else{
/*90*/	contenido += String.format("%08d", Integer.parseInt(valorNoafiliado));
		}
		

/*91*/	contenido += String.format("%08d", Integer.parseInt(previred.getDescCargasFamiliaresCCAF()));
/*92*/	contenido += String.format("%08d", Integer.parseInt(previred.getOtrosDescuentosCCAF1()));
/*93*/	contenido += String.format("%08d", Integer.parseInt(previred.getOtrosDescuentosCCAF2()));
/*94*/	contenido += String.format("%08d", Integer.parseInt(previred.getBonoGobiernoCCAF()));
/*95*/	contenido += String.format("%-20s", previred.getCodigoSucursalCCAF());
		
		//Datos mutualildad
		
/*96*/	contenido += String.format("%02d",  Integer.parseInt(previred.getCodigoMutualidad()));
		
		// RENTA IMPONIBLE MUTUALIDAD
		String RentaImponibleMutualidad = "";
		if(imponib > topel){
			RentaImponibleMutualidad = ""+topel+"";
		}else{
			RentaImponibleMutualidad = ""+imponib+"";
		}
		
		 String RentaImponible_seguro = "";
	      int imponible_seguro_ce = 0;
	      int tope_seg = 0;
			
			if(previred.getTopeseguro() == null || previred.getTopeseguro() == "null"){
				tope_seg = 0;
			}else{
				tope_seg = Integer.parseInt(previred.getTopeseguro());
			}
			
			if(previred.getRentaImponibleSeguroCesantia() == null || previred.getRentaImponibleSeguroCesantia() == "null"){
				imponible_seguro_ce = 0;
			}else{
				imponible_seguro_ce = Integer.parseInt(previred.getRentaImponibleSeguroCesantia().replace(".", ""));
			}
			
			if(imponible_seguro_ce > tope_seg)
			{
				RentaImponible_seguro = ""+tope_seg+"";
			}
			else
			{
				RentaImponible_seguro = ""+imponible_seguro_ce+"";
			}
		
			if(previred.getCodTrabajador().equals("3652")){
				System.out.println("");
			}
			if(previred.getCodTrabajador() == 3652){
				System.out.println("");
			}	
			
		if(imponib == 0){
/*97*/  	

			if(previred.getPensionadocotizante() == 1 ){
				
				if(previred.getSueldo_afc() != "0" && previred.getTipoLinea().equals("00")){
					
					contenido += String.format("%08d",Integer.parseInt(previred.getSueldo_afc().replace(".", "")));
				}else{
					contenido += String.format("%08d",Integer.parseInt("0"));
				}	
				
			}else{
				
				if(previred.getSueldo_afc() != "0" && previred.getTipoLinea().equals("00")){
					
					if(previred.getPensionado() == 1){
						contenido += String.format("%08d",Integer.parseInt(previred.getSueldo_afc().replace(".", ""))).substring(0, 8);
					}else{
						contenido += String.format("%08d",Integer.parseInt(previred.getSueldo_afc().replace(".", ""))).substring(0, 8);
					}
					
					
				}else{
					contenido += String.format("%08d",Integer.parseInt(previred.getBaseAFC().replace(".", "")));
				}	
				
				
			}
//			if(Integer.parseInt(previred.getBaseAFC()) == 0){
//				contenido += String.format("%08d",Integer.parseInt(previred.getBasesanna().replace(".", "")));
//			}else{
//				contenido += String.format("%08d",Integer.parseInt(previred.getBaseAFC().replace(".", "")));
//			}
//			
		}
		
		else
		{
			if(previred.getTipoLinea().equals("00"))
			{
/*97*/  		contenido += String.format("%08d", Integer.parseInt(RentaImponibleMutualidad.replace(".", "")));
			}
			else if(previred.getTipoLinea().equals("02"))
			{
/*97*/		  contenido += String.format("%08d", Integer.parseInt(RentaImponibleMutualidad.replace(".", "")));
			} 
			else
			{
/*97*/  		contenido += String.format("%08d", Integer.parseInt("0"));
			}
		}
			

/*98*/	contenido += String.format("%08d", Integer.parseInt(previred.getCotizacionAccidenteTrabajo().replace(".", "")));
/*99*/	contenido += String.format("%03d", Integer.parseInt(previred.getSucursalPagoMutual()));
		
		//Datos administradora seguro cesantia

			
/*100*/			if("7".equals(previred.getIdafp())){
				contenido += String.format("%08d",Integer.parseInt(RentaImponible_seguro.replace(".", ""))).substring(0, 8);
			}else{
				
			if(imponib == 0)
			{
				if(previred.getPensionadocotizante() == 1 ){
					
					if(previred.getSueldo_afc() != "0" && previred.getTipoLinea().equals("00")){
						
						contenido += String.format("%08d",Integer.parseInt(previred.getSueldo_afc().replace(".", "")));
					}else{
						contenido += String.format("%08d",Integer.parseInt("0"));
					}	
					
				}else{
					
					if(previred.getSueldo_afc() != "0" && previred.getTipoLinea().equals("00")){
						
						if(previred.getPensionado() == 1){
							contenido += String.format("%08d",Integer.parseInt("0"));
						}else{
							contenido += String.format("%08d",Integer.parseInt(previred.getSueldo_afc().replace(".", ""))).substring(0, 8);
						}
						
						
					}else{
						contenido += String.format("%08d",Integer.parseInt(previred.getBaseAFC().replace(".", "")));
					}	
					
					
				}
/*100*/  		
			}
			else
			{
	
				
				
				if(previred.getTipoTrabajador() == "2")
				{
					if(previred.getPensionadocotizante() == 1){
						
						if(previred.getSueldo_afc() != "0" && previred.getTipoLinea().equals("00")){
							if(previred.getPensionado() == 1){
								contenido += String.format("%08d",Integer.parseInt("0"));
							}else{
								contenido += String.format("%08d",Integer.parseInt(previred.getSueldo_afc().replace(".", "")));
							}
							
						}else{
							contenido += String.format("%08d",Integer.parseInt("0"));
						}	
						
						
					}else{
						if(previred.getSueldo_afc() != "0" && previred.getTipoLinea().equals("00")){
							if(previred.getPensionado() == 1){
								contenido += String.format("%08d",Integer.parseInt("0"));
							}else{
								contenido += String.format("%08d",Integer.parseInt(previred.getSueldo_afc().replace(".", "")));
							}
							
						}else{
							contenido += String.format("%08d", Integer.parseInt("0")).substring(0, 8);
						}
/*100*/				
					}
				}
				else
				{
					if(previred.getPensionadocotizante() == 1){
						if(previred.getSueldo_afc() != "0" && previred.getTipoLinea().equals("00")){
							if(previred.getPensionado() == 1){
								contenido += String.format("%08d",Integer.parseInt("0"));
							}else{
								contenido += String.format("%08d",Integer.parseInt(previred.getSueldo_afc().replace(".", "")));

							}
						}else{
							contenido += String.format("%08d",Integer.parseInt("0"));
						}
						
					}else{
						if(previred.getSueldo_afc() != "0" && previred.getTipoLinea().equals("00")){
							
							if(previred.getPensionado() == 1){
								contenido += String.format("%08d",Integer.parseInt("0"));
							}else{
								contenido += String.format("%08d",Integer.parseInt(RentaImponible_seguro.replace(".", ""))).substring(0, 8);
							}
							
						}else{
							
							if(previred.getPensionado() == 1){
								contenido += String.format("%08d",Integer.parseInt("0"));
							}else{
								contenido += String.format("%08d", Integer.parseInt(RentaImponible_seguro.replace(".", ""))).substring(0, 8);

							}
						}
/*100*/				
					}
				}	
			}
			}
		contenido += String.format("%08d", Integer.parseInt(previred.getAporteTrabajadorSeguroCesantia().replace(".", ""))).substring(0, 8);
		
		
		contenido += String.format("%08d", Integer.parseInt(previred.getAporteEmpleadorSeguroCesantia().replace(".", ""))).substring(0, 8);

		//Datos afiliado voluntario
		
		// si el movimiento es numero 6 es por accidente de mutualidad y se debe pagar por el rut mutualidad asociado a la empresa
		
		if(Integer.parseInt(previred.getCodigoMovimientoPersonal()) == 3 ){
			contenido += String.format("%011d", Integer.parseInt(previred.getRutPagadoraSubsidio()));
		}else if(Integer.parseInt(previred.getCodigoMovimientoPersonal()) == 6 ){
			contenido += String.format("%011d", Integer.parseInt(previred.getRutPagadoraSubsidioMutual()));
		}
		
		else{
			contenido += String.format("%011d", Integer.parseInt("0"));
		}
		
		if(Integer.parseInt(previred.getCodigoMovimientoPersonal()) == 3){
			contenido += String.format("%-1s", previred.getDvPagadoraSubsidio());
		}
		else if(Integer.parseInt(previred.getCodigoMovimientoPersonal()) == 6 ){
			contenido += String.format("%-1s", previred.getDvPagadoraSubsidioMutual());
		}
		else{
			contenido += String.format("%-1s", "0");
		}
		
		
		
		//Datos afiliado voluntario
		contenido += String.format("%-20s", previred.getCentroCostoSucAgencia());
		contenido += "\r\n";
		
		return contenido;
	}

}
