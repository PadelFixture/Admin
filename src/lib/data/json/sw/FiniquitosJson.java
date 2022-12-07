package lib.data.json.sw;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

import SWDB.impexp_trabajador;
import lib.ClassSASW.parametros;
import lib.classSW.DatosTrabajadorFiniquito;
import lib.classSW.DocFiniquito;
import lib.classSW.InsertHD;
import lib.classSW.ListaSociedad;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.NominaAnticipos;
import lib.classSW.UpdateTrabajadorHD;
import lib.data.json.ConviertePdfFromWordx;
import lib.db.sw.DocumentsDB;
import lib.db.sw.FiniquitosBD;
import lib.security.session;
import pdfCreator.PdfToImage;
import sun.misc.BASE64Encoder;
import wordCreator.DocxCreator;
import wordCreator.utils;

@Controller
public class FiniquitosJson {

	// private final static Logger LOG =
	// LoggerFactory.getLogger(FiniquitosJson.class);
	//
	// //Instanciamos clase que se conecta a BD


	// obtener los sueldos liquidos del los ultimos 3 meses
	@RequestMapping(value = "/work/obtenerSueldoLiquido/{cod}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getSueldoLiquido(@PathVariable int cod,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getSueldoLiquido(cod);
		return es;

	}

	@RequestMapping(value = "/work/ListaArticulo/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<parametros> getListadoArticulo(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<parametros> es = new ArrayList<parametros>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getListadoArticulo();
		return es;

	}

	@RequestMapping(value = "/work/ListaInciso/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<parametros> getListadoInciso(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<parametros> es = new ArrayList<parametros>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getListadoInciso();
		return es;

	}

	@RequestMapping(value = "/work/ListaLetra/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<parametros> getListadoLetra(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<parametros> es = new ArrayList<parametros>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getListadoLetra();
		return es;

	}

	///// CALCULAR TOPE

	@RequestMapping(value = "/work/CalcularTope/{fecha}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getTopeFecha(@PathVariable String fecha,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getTopeFecha(fecha);
		return es;

	}

	// CALCULAR DIAS PROPORCIONALES
	/// individual
	@RequestMapping(value = "/work/DiasProporcionalesMasivo/{fechaIEnviar},{fecha},{dias:.+},{cod},{idcontrato},{periodot}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getFeriadosProporcionalMasivo(@PathVariable String fechaIEnviar,@PathVariable String fecha,
			@PathVariable String dias, @PathVariable int cod, @PathVariable int idcontrato, @PathVariable String periodot, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getFeriadosProporcionalMasivo(fechaIEnviar,fecha, dias, cod, idcontrato,periodot);
		return es;

	}

	/// individual
	@RequestMapping(value = "/work/DiasProporcionalesIndividual/{fecha},{dias:.+},{cod}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getFeriadosProporcionalIndividual(
			@PathVariable String fecha, @PathVariable String dias, @PathVariable int cod, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getFeriadosProporcionalIndividual(fecha, dias, cod);
		return es;

	}

	/// individual
	@RequestMapping(value = "/work/DiasProporcionales/{fecha},{dias:.+}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getFeriadosProporcional(@PathVariable String fecha,
			@PathVariable String dias, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getFeriadosProporcional(fecha, dias);
		return es;

	}

	
	@RequestMapping(value = "/work/updateTrabHDF", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTHDF(@RequestBody DatosTrabajadorFiniquito row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return FiniquitosBD.updateTHDF(row);
	}
	
	@RequestMapping(value = "/work/EliminarHaberDescuentoF/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean eliminarHDF(@PathVariable int id, HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}

		recc = FiniquitosBD.eliminarHDF(id);

		return recc;

	}
	
	
	// INSERT TABLA SW_FINIQUITOS

	@RequestMapping(value = "/work/insertSWFiniquitos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarFiniquito(@RequestBody ArrayList<DatosTrabajadorFiniquito> row,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		boolean r = false;
		if (ses.isValid()) {
			return r;

		}

		// r = finiquitosBD.insertarFiniquito(row.get(0));

		for (DatosTrabajadorFiniquito rec : row) {
			r = FiniquitosBD.insertFiniquitoDetalle(rec);
		}

		return r;

	}

	@RequestMapping(value = "/work/allTrabajadorFiniquitoDeTemporada/{empr},{div},{subdiv},{gru},{fecha},{tipoContra},{estado_proceso},{rolPrivado}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getallTrabajadorTemporadaFiniquito(
			@PathVariable String empr,
			@PathVariable String div, 
			@PathVariable String subdiv, 
			@PathVariable String gru,
			@PathVariable String fecha,
			@PathVariable String tipoContra,
			@PathVariable String estado_proceso,
			@PathVariable int rolPrivado,
			
			HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = FiniquitosBD.getallTrabajadorTemporadaFiniquito(empr, div, subdiv, gru,fecha,tipoContra,estado_proceso,rolPrivado);

		return r;

	}

	@RequestMapping(value = "/work/obtenerSueldoLiquidoTemporal/{cod}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getSueldoLiquidoTemporal(@PathVariable int cod,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getSueldoLiquidoTemporal(cod);
		return es;

	}

	/// insertar finiquito masivo
	@RequestMapping(value = "/work/insertFiniquitoMasivo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarFiniquitoMasivo(@RequestBody ArrayList<DatosTrabajadorFiniquito> row,
			HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}

		for (DatosTrabajadorFiniquito rec : row) {

			recc = FiniquitosBD.insertarFiniquitoMasivo(rec);
		
		}

		return recc;

	}

	/////////////////////////////// GENERAR DOCUMENTO
	/////////////////////////////// FINIQUITO/////////////////////////////////////////
	@RequestMapping(value = "/work/generateDocumentoFiniquito/{idTemplate}", method = RequestMethod.PUT)
	public @ResponseBody Set<String> generateMultipleContrato2(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String idTemplate, @RequestBody DocFiniquito informacion) {

		Date fechaActual = new Date();
		System.out.println(fechaActual);
		
		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		@SuppressWarnings("unused")
		String formatoFecha = "" + formatFecha.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");

		try {
			String NOMBRE_UP = informacion.getCod_trabajador_string();
			String nombreArchivo = NOMBRE_UP + horaf;
			String idtablaDetalle_finiquito = informacion.getIdString();
			String urlDocGenerado = utils.DocumentoFiniquitos();

			System.out.println(urlDocGenerado);
			String nombre2="Finiquito" + nombreArchivo;
			
			String nombreDoc = "Finiquito" + nombreArchivo + ".docx";
			String documentoWord = urlDocGenerado + "Finiquito" + NOMBRE_UP + ".docx";

			File archivoWord = new File(documentoWord);

			if (!archivoWord.exists()) {

				generateDocumentoFiniquito(nombre2, idTemplate, informacion, idtablaDetalle_finiquito);

			}

			return Collections.singleton(nombreDoc);

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.singleton(e.getMessage());
		}

	}

	//////////////// generateDocumentoFiniquito

	public String generateDocumentoFiniquito(String nombre, String idTemplate, DocFiniquito informacion,
			String idtabla) throws Exception {
//		System.out.println(informacion);
		String ruta = utils.DocumentoFiniquitos();

	
		
		/// OBTENER TODOS LOS HABERES NO IMPONIBLES DEL TRABAJADOR
		String COD_TRAB = informacion.getCod_trabajador_string();
		int COD_TRAB_INT = Integer.parseInt(COD_TRAB);
		
		
		int id_cont = informacion.getId_contrato();
		
		ArrayList<DatosTrabajadorFiniquito> haberesTrabajador = new ArrayList<DatosTrabajadorFiniquito>();
		haberesTrabajador = FiniquitosBD.obtenerHaberesFiniquitos(COD_TRAB_INT,id_cont);
		
		// SOBRE GIRO 
		ArrayList<DatosTrabajadorFiniquito> sobre_giro = new ArrayList<DatosTrabajadorFiniquito>();
		sobre_giro = FiniquitosBD.obtenersobregiro(COD_TRAB_INT,id_cont);
		
		
		
		/// OBTENER TODOS LOS DESCUENTOS DEL TRABAJADOR
				
				ArrayList<DatosTrabajadorFiniquito> descuentoTrabajador = new ArrayList<DatosTrabajadorFiniquito>();
				descuentoTrabajador = FiniquitosBD.obtenerDescuentosFiniquitos(COD_TRAB_INT,id_cont);
          
				DatosTrabajadorFiniquito datosContratoTrabajador = new DatosTrabajadorFiniquito();
				datosContratoTrabajador = FiniquitosBD.obtenerDatosTrabajadorFiniquitos(idtabla,COD_TRAB_INT);
		
		        System.out.println("pase por aqui");
				int montoTotalH = 0;
				int montoTotaldescuento = 0;
				
				String[][] tablaUno= new String[haberesTrabajador.size()][3];
				
				for(int i=0;i<descuentoTrabajador.size();i++){
					montoTotaldescuento = montoTotaldescuento + descuentoTrabajador.get(i).getMontoHaberes();
				}
				String total_Hdesc = String.valueOf(montoTotaldescuento);
				
				
				for(int i=0;i<haberesTrabajador.size();i++){
					
					if(haberesTrabajador.get(i).getMontoHaberes() != 0){
						tablaUno[i][0]=haberesTrabajador.get(i).getNombreHD();
						
						montoTotalH = montoTotalH + haberesTrabajador.get(i).getMontoHaberes();
						
						if("0.0".equals(haberesTrabajador.get(i).getTotaldiasproporcional()+""))
						{
							tablaUno[i][1]= "";
						}
						else{
							tablaUno[i][1]= ""+haberesTrabajador.get(i).getTotaldiasproporcional()+"";
						}
						
						
						double amountHT = haberesTrabajador.get(i).getMontoHaberes();    
						String totalfHT = String.format("%,.0f", amountHT);
						String total_hT = String.valueOf(totalfHT);
						
						tablaUno[i][2]= total_hT;
					}else{
						
					}
					
					
					
				}
				informacion.setTablaUno(tablaUno);
		
				String total_H = String.valueOf(montoTotalH);		
				
				
		if(descuentoTrabajador.size() >= 1){
		
			String[][] datosDuros;	
		if(sobre_giro.size() >= 1){	
			 datosDuros= new String[31][2]; 
		}else{
			datosDuros= new String[27][2]; 
		}
		
		
		
		
		datosDuros[0][0]="$$nombreEmpresa$$";
		datosDuros[0][1]=datosContratoTrabajador.getNombre_sociedad();
		
		datosDuros[1][0]="$$rutEmpresa$$";
		datosDuros[1][1]=datosContratoTrabajador.getRut_sociedad();
		
		datosDuros[2][0]="$$direccionEmpresa$$";
		datosDuros[2][1]=datosContratoTrabajador.getDireccion_huerto();
		
		datosDuros[3][0]="$$codigoTrabajador$$";
		String codigo_trab = String.valueOf(datosContratoTrabajador.getCodigo_trabajador());
		datosDuros[3][1]= codigo_trab;
		
		datosDuros[4][0]="$$ciudadEmpresa$$";
		datosDuros[4][1]=datosContratoTrabajador.getCiudad_huerto();
		
		datosDuros[5][0]="$$nombreTrabajador$$";
		datosDuros[5][1]=datosContratoTrabajador.getNombre();
		
		datosDuros[6][0]="$$cargoTrabajador$$";
		datosDuros[6][1]=datosContratoTrabajador.getCargotrabajador();
		
		datosDuros[7][0]="$$fechaInicioTrabajador$$";
		datosDuros[7][1]=datosContratoTrabajador.getFecha_inicio();
		
		datosDuros[8][0]="$$fechaTerminoTrabajador$$";
		datosDuros[8][1]=datosContratoTrabajador.getFecha_termino();
		
		datosDuros[9][0]="$$articulo$$";
		String articulo_trab = String.valueOf(datosContratoTrabajador.getArticulostring());
		datosDuros[9][1]= articulo_trab;
		
		datosDuros[10][0]="$$inciso$$";
		String inciso_trab = String.valueOf("");
		datosDuros[10][1]= inciso_trab;
		
		if(datosContratoTrabajador.getLetra() == 0)
		{
			
			datosDuros[11][0]="$$letra$$"; //letra E
			datosDuros[11][1]= "";
		}
		else
		{
			String letra_trab = String.valueOf(datosContratoTrabajador.getLetra());
			datosDuros[11][0]="$$letra$$"; //letra E
			datosDuros[11][1]= "Letra "+letra_trab;
		}
		
		datosDuros[12][0]="$$totalhaberes$$"; 
		int numEnteroHaber = Integer.parseInt(total_H);
		double amountH = numEnteroHaber;    
		String totalfH = String.format("%,.0f", amountH);
		String total_ha = String.valueOf(totalfH);
		datosDuros[12][1]= total_ha;
		
		datosDuros[13][0]="$$Liquidoapagar$$"; 
		double amount = datosContratoTrabajador.getTotal_finiquito();    
		String totalf = String.format("%,.0f", amount);
		String total_finiquito_trab = String.valueOf(totalf);
		datosDuros[13][1]= total_finiquito_trab;
	
		String textoValorFiniquito = informacion.getValor_texto();
		datosDuros[14][0]="$$valorenPalabras$"; 
		datosDuros[14][1]= textoValorFiniquito;
		
		datosDuros[15][0]="$$rutTrabajador$$"; 
		datosDuros[15][1]=datosContratoTrabajador.getRut();
		
		
		datosDuros[16][0]="$$totaldescuentos$$";
		
		int numEnterod = Integer.parseInt(total_Hdesc);
		double amountd = numEnterod;    
		String totalfd = String.format("%,.0f", amountd);
		String total_d = String.valueOf(totalfd);
		
		if("0".equals(total_Hdesc))
		{
			datosDuros[16][1]= "";
		}else{
			datosDuros[16][1]= total_d;
		}
		
		datosDuros[17][0]="$$tituloDescuentos$$"; 
		datosDuros[17][1]="DESCUENTOS";
		
		datosDuros[18][0]="$$linea1Descuentos$$"; 
		datosDuros[18][1]="----------------------------------------------------------------------------------------------------------------";
		
		datosDuros[19][0]="$$linea2Descuentos$$"; 
		datosDuros[19][1]="----------------------------------------------------------------------------------------------------------------";
		
		datosDuros[20][0]="$$tituloTotalDescuentos$$"; 
		datosDuros[20][1]="TOTAL DESCUENTOS";
		
		if(datosContratoTrabajador.getComunaHurto() == null){
			datosDuros[21][0]="$$comuna$$"; 
			datosDuros[21][1]= "";
		}else{
			datosDuros[21][0]="$$comuna$$"; 
			datosDuros[21][1]= datosContratoTrabajador.getNombre();
		}
		
		String nombreCompletoRe = datosContratoTrabajador.getApprepresentante()+" "+datosContratoTrabajador.getApmrepresentante()+" "+datosContratoTrabajador.getNombrerepresentante();
		datosDuros[22][0]="$$nombre_repre$$"; 
		datosDuros[22][1]= nombreCompletoRe;
		
		datosDuros[23][0]="$$rut_repre$$"; 
		datosDuros[23][1]= datosContratoTrabajador.getRutrepresentante();
		
		datosDuros[24][0] = "$$incisoDescripcion$$";
		datosDuros[24][1] = datosContratoTrabajador.getIncisoDescripcion();
		
		
		informacion.setDatosDuros(datosDuros);
		
		
		}else{
			String[][] datosDuros;	
			if(sobre_giro.size() >= 1){	
				 datosDuros= new String[31][2]; 
			}else{
				datosDuros= new String[27][2]; 
			}
			 
			
			datosDuros[0][0]="$$nombreEmpresa$$";
			datosDuros[0][1]=datosContratoTrabajador.getNombre_sociedad();
			
			datosDuros[1][0]="$$rutEmpresa$$";
			datosDuros[1][1]=datosContratoTrabajador.getRut_sociedad();
			
			datosDuros[2][0]="$$direccionEmpresa$$";
			datosDuros[2][1]=datosContratoTrabajador.getDireccion_huerto();
			
			datosDuros[3][0]="$$codigoTrabajador$$";
			String codigo_trab = String.valueOf(datosContratoTrabajador.getCodigo_trabajador());
			datosDuros[3][1]= codigo_trab;
			
			datosDuros[4][0]="$$ciudadEmpresa$$";
			datosDuros[4][1]=datosContratoTrabajador.getCiudad_huerto();
			
			datosDuros[5][0]="$$nombreTrabajador$$";
			datosDuros[5][1]=datosContratoTrabajador.getNombre();
			
			datosDuros[6][0]="$$cargoTrabajador$$";
			datosDuros[6][1]=datosContratoTrabajador.getCargotrabajador();
			
			datosDuros[7][0]="$$fechaInicioTrabajador$$";
			datosDuros[7][1]=datosContratoTrabajador.getFecha_inicio();
			
			datosDuros[8][0]="$$fechaTerminoTrabajador$$";
			datosDuros[8][1]=datosContratoTrabajador.getFecha_termino();
			
			datosDuros[9][0]="$$articulo$$";
			String articulo_trab = String.valueOf(datosContratoTrabajador.getArticulostring());
			datosDuros[9][1]= articulo_trab;
			
			datosDuros[10][0]="$$inciso$$";
			String inciso_trab = String.valueOf("");
			datosDuros[10][1]= inciso_trab;
			
			if(datosContratoTrabajador.getLetra() == 0)
			{
				
				datosDuros[11][0]="$$letra$$"; //letra E
				datosDuros[11][1]= "";
			}
			else
			{
				String letra_trab = String.valueOf(datosContratoTrabajador.getLetra());
				datosDuros[11][0]="$$letra$$"; //letra E
				datosDuros[11][1]= "Letra "+letra_trab;
			}
			
			datosDuros[12][0]="$$totalhaberes$$"; 
			int numEnteroHaber = Integer.parseInt(total_H);
			double amountH = numEnteroHaber;    
			String totalfH = String.format("%,.0f", amountH);
			String total_ha = String.valueOf(totalfH);
			datosDuros[12][1]= total_ha;
			
			datosDuros[13][0]="$$Liquidoapagar$$"; 
			double amount = datosContratoTrabajador.getTotal_finiquito();    
			String totalf = String.format("%,.0f", amount);
			String total_finiquito_trab = String.valueOf(totalf);
			datosDuros[13][1]= total_finiquito_trab;
			
			
			String textoValorFiniquito = informacion.getValor_texto();
			datosDuros[14][0]="$$valorenPalabras$"; 
			datosDuros[14][1]= textoValorFiniquito;
			
			datosDuros[15][0]="$$rutTrabajador$$"; 
			datosDuros[15][1]=datosContratoTrabajador.getRut();
			
			datosDuros[16][0]="$$totaldescuentos$$";
			int numEnterod = Integer.parseInt(total_Hdesc);
			double amountd = numEnterod;    
			String totalfd = String.format("%,.0f", amountd);
			String total_d = String.valueOf(totalfd);
			
			if("0".equals(total_Hdesc))
			{
				datosDuros[16][1]= "";
			}else{
				datosDuros[16][1]= total_d;
			}
			
			
			datosDuros[17][0]="$$tituloDescuentos$$"; 
			datosDuros[17][1]="";
			
			datosDuros[18][0]="$$linea1Descuentos$$"; 
			datosDuros[18][1]= "";
			
			datosDuros[19][0]="$$linea2Descuentos$$"; 
			datosDuros[19][1]="";
			
			datosDuros[20][0]="$$tituloTotalDescuentos$$"; 
			datosDuros[20][1]="";
			
			if(datosContratoTrabajador.getComunaHurto() == null){
				datosDuros[21][0]="$$comuna$$"; 
				datosDuros[21][1]= "";
			}else{
				datosDuros[21][0]="$$comuna$$"; 
				datosDuros[21][1]= datosContratoTrabajador.getNombre();
			}
			
			String nombreCompletoRe = datosContratoTrabajador.getApprepresentante()+" "+datosContratoTrabajador.getApmrepresentante()+" "+datosContratoTrabajador.getNombrerepresentante();
			datosDuros[22][0]="$$nombre_repre$$"; 
			datosDuros[22][1]= nombreCompletoRe;
			
			datosDuros[23][0]="$$rut_repre$$"; 
			datosDuros[23][1]= datosContratoTrabajador.getRutrepresentante();
			
			datosDuros[24][0] = "$$incisoDescripcion$$";
			datosDuros[24][1] = datosContratoTrabajador.getIncisoDescripcion();
				
			informacion.setDatosDuros(datosDuros);
		}
		
		if(descuentoTrabajador.size() >= 1){
			
		String[][] tablaTres=new String[descuentoTrabajador.size()][3];
		
		for(int i=0;i<descuentoTrabajador.size();i++){
			tablaTres[i][0]=descuentoTrabajador.get(i).getNombreHD();
			tablaTres[i][1]="";
			
			double amountDT = descuentoTrabajador.get(i).getMontoHaberes();    
			String totalfDT = String.format("%,.0f", amountDT);
			String total_DT = String.valueOf(totalfDT);
			tablaTres[i][2]= total_DT;
		}
		
		informacion.setTablaTres(tablaTres);
		}
		
		Gson g= new Gson();
		String JsonGenerado=g.toJson(informacion);
		System.out.println(JsonGenerado);

		
		DocxCreator d= new DocxCreator();
	
		String rutaConNombre= d.crearFiniquito(ruta, nombre, informacion, idTemplate);
				

		return rutaConNombre;


	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////// CARGAR TRABAJADORES FINIQUITOS PARA
	///////////////////////////////////////////////////////////////////////////////////////////////////// IMPRIMIR//////////////////////////

	@RequestMapping(value = "/work/LoadTrabajadorXSociedadFiniquitooImprimir/{id},{estado_proceso},{rolPrivado}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getTrabajadoresFiniquitoImprimir(
			@PathVariable int id,
			@PathVariable String estado_proceso,
			@PathVariable int rolPrivado,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<DatosTrabajadorFiniquito> r = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return r;
		}
		r = FiniquitosBD.getTrabajadoresFiniquitoImprimir(id,estado_proceso,rolPrivado);

		return r;

	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////// BUSCAR TRABAJADORES Y CARGAR EN TABLA IMPRIMIR FINIQUITOS ///////////
	///////////////////////////////////////////////////////////////////////////////////////////////////// 

	@RequestMapping(value = "/work/BuscarFiniquitosSimpleImprimir/{fec},{cod},{emp},{divi},{subd},{gru},{tipo_cuenta},{estado_proceso},{rolPrivado}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getBuscarFiniquitosSimpleImprimir(@PathVariable String fec,
			@PathVariable String cod, @PathVariable String emp, @PathVariable String divi, @PathVariable String subd,
			@PathVariable String gru,@PathVariable String tipo_cuenta,
			@PathVariable String estado_proceso,
			@PathVariable int rolPrivado,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getBuscarFiniquitosSimpleImprimir(fec, cod, emp, divi, subd, gru,tipo_cuenta,estado_proceso,rolPrivado);
		return es;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////// IMPRIMIR FINIQUITOS MASIVOS //////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/work/generateDocumentoFiniquitoMasivo/{idTemplate}", method = RequestMethod.PUT)
	public @ResponseBody Set<String> generateMultipleContrato3(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String idTemplate, @RequestBody ArrayList<DatosTrabajadorFiniquito> trabajadores) {

		Date fechaActual = new Date();
	
		// Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		DateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		String formatoHora = "" + formatHora.format(fechaActual).toString() + "";
		@SuppressWarnings("unused")
		String formatoFecha = "" + formatFecha.format(fechaActual).toString() + "";

		String horaf = formatoHora.replaceAll("[:]", "");

		try {
			String NOMBRE_UP = "Finiquito";
			String nombreArchivo = NOMBRE_UP + horaf;

			String urlDocGenerado = utils.DocumentoFiniquitos();

			System.out.println(urlDocGenerado);
			//String nombreDoc = "Finiquito" + nombreArchivo + ".docx";
			String documentoWord = urlDocGenerado + "Finiquito" + NOMBRE_UP + ".docx";
		
			File archivoWord = new File(documentoWord);
			String retorno="";
			if (!archivoWord.exists()) {

				retorno=generateDocumentoFiniquito2(nombreArchivo, idTemplate, trabajadores);

			}

			return Collections.singleton(retorno);

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.singleton(e.getMessage());
		}

	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////GENERAR FINIQUITO MASIVO/////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	public String generateDocumentoFiniquito2(String nombre, String idTemplate, ArrayList<DatosTrabajadorFiniquito> trabajadores) throws Exception {
		
		ArrayList<DocFiniquito> listaInformacion = new ArrayList<>();
		String ruta = utils.DocumentoFiniquitos();
		System.out.println(nombre);
		for (DatosTrabajadorFiniquito rec : trabajadores) 
		{
			
			DocFiniquito informacion = new  DocFiniquito();
			
			int COD_TRAB_INT = rec.getCodigo_trabajador();
			int IDCONT = rec.getId_contrato();
			
			/// OBTENER TODOS LOS HABERES NO IMPONIBLES DEL TRABAJADOR
			ArrayList<DatosTrabajadorFiniquito> haberesTrabajador = new ArrayList<DatosTrabajadorFiniquito>();
			haberesTrabajador = FiniquitosBD.obtenerHaberesFiniquitos(COD_TRAB_INT,IDCONT);
			
			ArrayList<DatosTrabajadorFiniquito> sobre_giro = new ArrayList<DatosTrabajadorFiniquito>();
			sobre_giro = FiniquitosBD.obtenersobregiro(COD_TRAB_INT,IDCONT);
			
			/// OBTENER TODOS LOS DESCUENTOS DEL TRABAJADOR
			ArrayList<DatosTrabajadorFiniquito> descuentoTrabajador = new ArrayList<DatosTrabajadorFiniquito>();
			descuentoTrabajador = FiniquitosBD.obtenerDescuentosFiniquitos(COD_TRAB_INT,IDCONT);
	          
			DatosTrabajadorFiniquito datosContratoTrabajador = new DatosTrabajadorFiniquito();
			datosContratoTrabajador = FiniquitosBD.obtenerDatosTrabajadorFiniquitos(""+rec.getId()+"",COD_TRAB_INT);
			
			int montoTotalH = 0;
			int montoTotaldescuento = 0;
			
			for(int i=0;i<descuentoTrabajador.size();i++){
				montoTotaldescuento = montoTotaldescuento + descuentoTrabajador.get(i).getMontoHaberes();
			}
			String total_Hdesc = String.valueOf(montoTotaldescuento);
					
					
			String[][] tablaUno= new String[haberesTrabajador.size()][3];
					
			for(int i=0;i<haberesTrabajador.size();i++)
			{
						
				if(haberesTrabajador.get(i).getMontoHaberes() != 0)
				{
					tablaUno[i][0]=haberesTrabajador.get(i).getNombreHD();
							
					montoTotalH = montoTotalH + haberesTrabajador.get(i).getMontoHaberes();
							
					if("0.0".equals(haberesTrabajador.get(i).getTotaldiasproporcional()+""))
					{
						tablaUno[i][1]= "";
					}
					else
					{
						tablaUno[i][1]= ""+haberesTrabajador.get(i).getTotaldiasproporcional()+"";
					}
					
					double amountHT = haberesTrabajador.get(i).getMontoHaberes();    
					String totalfHT = String.format("%,.0f", amountHT);
					String total_hT = String.valueOf(totalfHT);
								
						tablaUno[i][2]= total_hT;
				  }else
				  {
							
				  }
				}// end for
					
				informacion.setTablaUno(tablaUno);
			
				String total_H = String.valueOf(montoTotalH);		
					
			if(descuentoTrabajador.size() >= 1)
			{
			
				String[][] datosDuros;	
				if(sobre_giro.size() >= 1){	
					 datosDuros= new String[31][2]; 
				}else{
					datosDuros= new String[27][2]; 
				}
				
				datosDuros[0][0]="$$nombreEmpresa$$";
				datosDuros[0][1]=datosContratoTrabajador.getNombre_sociedad();
				
				datosDuros[1][0]="$$rutEmpresa$$";
				datosDuros[1][1]=datosContratoTrabajador.getRut_sociedad();
				
				if(datosContratoTrabajador.getDireccion_huerto() == null)
				{
					datosDuros[2][0]="$$direccionEmpresa$$";
					datosDuros[2][1]= "";
				}
				else
				{
					datosDuros[2][0]="$$direccionEmpresa$$";
					datosDuros[2][1]=datosContratoTrabajador.getDireccion_huerto();
				}
				
				datosDuros[3][0]="$$codigoTrabajador$$";
				String codigo_trab = String.valueOf(datosContratoTrabajador.getCodigo_trabajador());
				datosDuros[3][1]= codigo_trab;
				
				if(datosContratoTrabajador.getCiudad_huerto() == null)
				{
					datosDuros[4][0]="$$ciudadEmpresa$$";
					datosDuros[4][1]= "";
				}
				else
				{
					datosDuros[4][0]="$$ciudadEmpresa$$";
					datosDuros[4][1]=datosContratoTrabajador.getCiudad_huerto();
				}
				
				
				datosDuros[5][0]="$$nombreTrabajador$$";
				datosDuros[5][1]=datosContratoTrabajador.getNombre();
			
				datosDuros[6][0]="$$cargoTrabajador$$";
				datosDuros[6][1]=datosContratoTrabajador.getCargotrabajador();
				
				datosDuros[7][0]="$$fechaInicioTrabajador$$";
				datosDuros[7][1]=datosContratoTrabajador.getFecha_inicio();
				
				datosDuros[8][0]="$$fechaTerminoTrabajador$$";
				datosDuros[8][1]=datosContratoTrabajador.getFecha_termino();
				
				datosDuros[9][0]="$$articulo$$";
				String articulo_trab = String.valueOf(datosContratoTrabajador.getArticulostring());
				datosDuros[9][1]= articulo_trab;
				
				datosDuros[10][0]="$$inciso$$";
				String inciso_trab = String.valueOf("");
				datosDuros[10][1]= inciso_trab;
			
				if(datosContratoTrabajador.getLetra() == 0)
				{
				
					datosDuros[11][0]="$$letra$$"; //letra E
					datosDuros[11][1]= "";
				}
				else
				{
					String letra_trab = String.valueOf(datosContratoTrabajador.getLetra());
					datosDuros[11][0]="$$letra$$"; //letra E
					datosDuros[11][1]= "Letra "+letra_trab;
				}
			
				datosDuros[12][0]="$$totalhaberes$$"; 
				int numEnteroHaber = Integer.parseInt(total_H);
				double amountH = numEnteroHaber;    
				String totalfH = String.format("%,.0f", amountH);
				String total_ha = String.valueOf(totalfH);
				datosDuros[12][1]= total_ha;
				
				datosDuros[13][0]="$$Liquidoapagar$$"; 
				double amount = datosContratoTrabajador.getTotal_finiquito();    
				String totalf = String.format("%,.0f", amount);
				String total_finiquito_trab = String.valueOf(totalf);
				datosDuros[13][1]= total_finiquito_trab;
			
				String textoValorFiniquito = rec.getValor_texto();
				datosDuros[14][0]="$$valorenPalabras$"; 
				datosDuros[14][1]= textoValorFiniquito;
				
				datosDuros[15][0]="$$rutTrabajador$$"; 
				datosDuros[15][1]=datosContratoTrabajador.getRut();
			
				datosDuros[16][0]="$$totaldescuentos$$";
				int numEnterod = Integer.parseInt(total_Hdesc);
				double amountd = numEnterod;    
				String totalfd = String.format("%,.0f", amountd);
				String total_d = String.valueOf(totalfd);
				
			
				if("0".equals(total_Hdesc))
				{
					datosDuros[16][1]= "";
				}else{
					datosDuros[16][1]= total_d;
				}
				
				datosDuros[17][0]="$$tituloDescuentos$$"; 
				datosDuros[17][1]="DESCUENTOS";
				
				datosDuros[18][0]="$$linea1Descuentos$$"; 
				datosDuros[18][1]="----------------------------------------------------------------------------------------------------------------";
				
				datosDuros[19][0]="$$linea2Descuentos$$"; 
				datosDuros[19][1]="----------------------------------------------------------------------------------------------------------------";
				
				datosDuros[20][0]="$$tituloTotalDescuentos$$"; 
				datosDuros[20][1]="TOTAL DESCUENTOS";
			
				if(datosContratoTrabajador.getComunaHurto() == null)
				{
					datosDuros[21][0]="$$comuna$$"; 
					datosDuros[21][1]= "";
				}else
				{
					datosDuros[21][0]="$$comuna$$"; 
					datosDuros[21][1]= datosContratoTrabajador.getNombre();
				}
			
				String nombreCompletoRe = datosContratoTrabajador.getApprepresentante()+" "+datosContratoTrabajador.getApmrepresentante()+" "+datosContratoTrabajador.getNombrerepresentante();
				datosDuros[22][0]="$$nombre_repre$$"; 
				datosDuros[22][1]= nombreCompletoRe;
			
				datosDuros[23][0]="$$rut_repre$$"; 
				datosDuros[23][1]= datosContratoTrabajador.getRutrepresentante();
				
				datosDuros[24][0] = "$$incisoDescripcion$$";
				datosDuros[24][1] = datosContratoTrabajador.getIncisoDescripcion();
				
				
			
				informacion.setDatosDuros(datosDuros);
			
			
			}else
			{
				String[][] datosDuros;	
				if(sobre_giro.size() >= 1){	
					 datosDuros= new String[31][2]; 
				}else{
					datosDuros= new String[27][2]; 
				}
				
				datosDuros[0][0]="$$nombreEmpresa$$";
				datosDuros[0][1]=datosContratoTrabajador.getNombre_sociedad();
				
				datosDuros[1][0]="$$rutEmpresa$$";
				datosDuros[1][1]=datosContratoTrabajador.getRut_sociedad();
				
				if(datosContratoTrabajador.getDireccion_huerto() == null){
					datosDuros[2][0]="$$direccionEmpresa$$";
					datosDuros[2][1]= "";
				}
				else
				{
					datosDuros[2][0]="$$direccionEmpresa$$";
					datosDuros[2][1]=datosContratoTrabajador.getDireccion_huerto();
				}
				
				datosDuros[3][0]="$$codigoTrabajador$$";
				String codigo_trab = String.valueOf(datosContratoTrabajador.getCodigo_trabajador());
				datosDuros[3][1]= codigo_trab;
				
				if(datosContratoTrabajador.getCiudad_huerto() == null)
				{
					datosDuros[4][0]="$$ciudadEmpresa$$";
					datosDuros[4][1]= "";
				}
				else
				{
					datosDuros[4][0]="$$ciudadEmpresa$$";
					datosDuros[4][1]=datosContratoTrabajador.getCiudad_huerto();
				}
				
				datosDuros[5][0]="$$nombreTrabajador$$";
				datosDuros[5][1]=datosContratoTrabajador.getNombre();
				
				datosDuros[6][0]="$$cargoTrabajador$$";
				datosDuros[6][1]=datosContratoTrabajador.getCargotrabajador();
				
				datosDuros[7][0]="$$fechaInicioTrabajador$$";
				datosDuros[7][1]=datosContratoTrabajador.getFecha_inicio();
				
				datosDuros[8][0]="$$fechaTerminoTrabajador$$";
				datosDuros[8][1]=datosContratoTrabajador.getFecha_termino();
				
				datosDuros[9][0]="$$articulo$$";
				String articulo_trab = String.valueOf(datosContratoTrabajador.getArticulostring());
				datosDuros[9][1]= articulo_trab;
				
				datosDuros[10][0]="$$inciso$$";
				String inciso_trab = String.valueOf("");
				datosDuros[10][1]= inciso_trab;
				
				if(datosContratoTrabajador.getLetra() == 0)
				{
					
					datosDuros[11][0]="$$letra$$";
					datosDuros[11][1]= "";
				}
				else
				{
					String letra_trab = String.valueOf(datosContratoTrabajador.getLetra());
					datosDuros[11][0]="$$letra$$"; 
					datosDuros[11][1]= "Letra "+letra_trab;
				}
				
				datosDuros[12][0]="$$totalhaberes$$"; 
				int numEnteroHaber = Integer.parseInt(total_H);
				double amountH = numEnteroHaber;    
				String totalfH = String.format("%,.0f", amountH);
				String total_ha = String.valueOf(totalfH);
				datosDuros[12][1]= total_ha;
				
				datosDuros[13][0]="$$Liquidoapagar$$"; 
				double amount = datosContratoTrabajador.getTotal_finiquito();    
				String totalf = String.format("%,.0f", amount);
				String total_finiquito_trab = String.valueOf(totalf);
				datosDuros[13][1]= total_finiquito_trab;
				
				String textoValorFiniquito = rec.getValor_texto();
				datosDuros[14][0]="$$valorenPalabras$"; 
				datosDuros[14][1]= textoValorFiniquito;
				
				datosDuros[15][0]="$$rutTrabajador$$"; 
				datosDuros[15][1]=datosContratoTrabajador.getRut();
				
				datosDuros[16][0]="$$totaldescuentos$$";
				int numEnterod = Integer.parseInt(total_Hdesc);
				double amountd = numEnterod;    
				String totalfd = String.format("%,.0f", amountd);
				String total_d = String.valueOf(totalfd);
				
				if("0".equals(total_Hdesc))
				{
					datosDuros[16][1]= "";
				}else{
					datosDuros[16][1]= total_d;
				}
				
				datosDuros[17][0]="$$tituloDescuentos$$"; 
				datosDuros[17][1]="";
				
				datosDuros[18][0]="$$linea1Descuentos$$"; 
				datosDuros[18][1]= "";
				
				datosDuros[19][0]="$$linea2Descuentos$$"; 
				datosDuros[19][1]="";
				
				datosDuros[20][0]="$$tituloTotalDescuentos$$"; 
				datosDuros[20][1]="";
				
				if(datosContratoTrabajador.getComunaHurto() == null){
					datosDuros[21][0]="$$comuna$$"; 
					datosDuros[21][1]= "";
				}else{
					datosDuros[21][0]="$$comuna$$"; 
					datosDuros[21][1]= datosContratoTrabajador.getNombre();
				}
				
				String nombreCompletoRe = datosContratoTrabajador.getApprepresentante()+" "+datosContratoTrabajador.getApmrepresentante()+" "+datosContratoTrabajador.getNombrerepresentante();
				datosDuros[22][0]="$$nombre_repre$$"; 
				datosDuros[22][1]= nombreCompletoRe;
				
				datosDuros[23][0]="$$rut_repre$$"; 
				datosDuros[23][1]= datosContratoTrabajador.getRutrepresentante();
				
				datosDuros[24][0] = "$$incisoDescripcion$$";
				datosDuros[24][1] = datosContratoTrabajador.getIncisoDescripcion();
				
				if(sobre_giro.size() >= 1){
					for(int i=0;i<sobre_giro.size();i++){
						
						if(i == 0){
							
							datosDuros[25][0] = "$$TitulosSobregiros$$";
							datosDuros[25][1] = sobre_giro.get(i).getNombreHD();
							
							datosDuros[26][0] = "$$totalsobregiro$$";
							datosDuros[26][1] = ""+sobre_giro.get(i).getValor();
						}else if(i == 1){
							datosDuros[27][0] = "$$TitulosSaldoSobreGiro$$";
							datosDuros[27][1] = sobre_giro.get(i).getNombreHD();
							
							datosDuros[28][0] = "$$totalsaldosobregiro$$";
							datosDuros[28][1] = ""+sobre_giro.get(i).getValor();
						}
					}}	
				
				informacion.setDatosDuros(datosDuros);
			}
			if(descuentoTrabajador.size() >= 1)
			{
			
				String[][] tablaTres=new String[descuentoTrabajador.size()][3];
			
				for(int i=0;i<descuentoTrabajador.size();i++)
				{
					tablaTres[i][0]=descuentoTrabajador.get(i).getNombreHD();
					tablaTres[i][1]="";
					double amountDT = descuentoTrabajador.get(i).getMontoHaberes();    
					String totalfDT = String.format("%,.0f", amountDT);
					String total_DT = String.valueOf(totalfDT);
					tablaTres[i][2]= total_DT;
				}
			
				informacion.setTablaTres(tablaTres);
			}
			
			listaInformacion.add(informacion);

		}// end for 
		
		
		Gson g= new Gson();
		String JsonGenerado=g.toJson(listaInformacion);
		System.out.println(JsonGenerado);
		
		DocxCreator d= new DocxCreator();
		String rutaConNombre= d.crearFiniquitoMasivo(ruta, nombre, listaInformacion, idTemplate);

		return rutaConNombre;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////// DESCARGAR DOCUMENTO FINIQUITO /////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/work/descargardocumentofiniquito", method = RequestMethod.GET)
	public @ResponseBody String getAutorizacion(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String fileName = request.getParameter("FILE");
			fileName = fileName.replaceAll("\"", "");
			System.out.println("ruta: {}" + fileName);

			String urlDocGenerado = utils.DocumentoFiniquitos() + fileName;

			@SuppressWarnings("unused")
			File file = new File(urlDocGenerado);
			System.out.println("aqui   " + urlDocGenerado);

			FileInputStream fileInputStreamReader = new FileInputStream(urlDocGenerado);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			response.addHeader("Content-disposition", "attachment; filename= " + fileName + "");
			response.setContentType("application/msword");
			response.setContentLength(bytes.length);
			response.setCharacterEncoding("iso-8859-1");
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();

			return "1";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@RequestMapping(value = "/work/LoadTrabajadorXSociedadFiniquitosModulo/{empr},{div},{subdiv},{gru},{tipo_contrato}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getSociedaTrabAS2(@PathVariable String empr,
			@PathVariable String div, @PathVariable String subdiv, @PathVariable String gru,
			@PathVariable String tipo_contrato, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<DatosTrabajadorFiniquito> r = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return r;
		}
		r = FiniquitosBD.getSociedaTrabAS2(empr, div, subdiv, gru, tipo_contrato);

		return r;

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////// INSERTAR HABERES Y DESCUENTOS FINIQUITO ////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
	@RequestMapping(value = "/work/insertHDFiniquito/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertarHDFiniquito(@RequestBody ArrayList<InsertHD> row, HttpSession httpSession)
			throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
		for (InsertHD rec : row) {

			recc = FiniquitosBD.insertarHDFiniquito(rec);
		}

		return recc;

	}
	
	// todos los tipo de contratos
		@RequestMapping(value = "/work/getAllTipoContrato/", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<ListaSociedad> getTipocontratoFiniquito(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<ListaSociedad> es = new ArrayList<ListaSociedad>();

			if (ses.isValid()) {
				return es;
			}

			es = FiniquitosBD.getTipocontratoFiniquito();
			return es;

		}

   ///////////////////////////BUSCAR FINIQUITOS PARA ELIMINAR///////////////////////////////////////
		@RequestMapping(value = "/work/allTrabajadorFiniquitoEliminar/{sociedad},{huerto},{zona},{ceco},{fechaTermino},{tipocontrato},{codtrab},{estado_proceso},{rolPrivado}", method = {
				RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getBuscarFiniquitoEliminar(
				@PathVariable String sociedad,
				@PathVariable String huerto,
				@PathVariable String zona,
				@PathVariable String ceco,
				@PathVariable String fechaTermino,
				@PathVariable String tipocontrato, 
				@PathVariable String codtrab,
				@PathVariable String estado_proceso, 
				@PathVariable int rolPrivado,
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

			if (ses.isValid()) {
				return es;
			}

			es = FiniquitosBD.getBuscarFiniquitoEliminar(sociedad, huerto, zona, ceco, fechaTermino, tipocontrato, codtrab, estado_proceso,rolPrivado);
			return es;
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////ELIMIR FINIQUITOS/////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		@RequestMapping(value = "/work/EliminarFiniquitos/{id},{idcontrato},{codtrab}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean eliminarFiniquitos(@PathVariable int id,@PathVariable int idcontrato,@PathVariable int codtrab, HttpSession httpSession) throws Exception {
			boolean recc = false;
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return recc;
			}

			recc = FiniquitosBD.eliminarFiniquitos(id,idcontrato,codtrab);

			return recc;

		}

///////////////////////////BUSCAR TRABAJADORES HD FINIQUITOS///////////////////////////////////////
	@RequestMapping(value = "/work/allTrabajadores_HD_Finiquitos/{sociedad},{huerto},{zona},{ceco},{tipocontrato},{codtrab},{periodo},{estado_proceso},{rolPrivado}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getBuscarHDFiniquitoTrabajadores(@PathVariable String sociedad,
			@PathVariable String huerto, @PathVariable String zona, @PathVariable String ceco,
			@PathVariable String tipocontrato, @PathVariable String codtrab,
			@PathVariable String periodo,
			@PathVariable String estado_proceso,
			@PathVariable int rolPrivado,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getBuscarHDFiniquitoTrabajadores(sociedad, huerto, zona, ceco, tipocontrato, codtrab, periodo,estado_proceso,rolPrivado);
		return es;
	}
	
///////////////////////////BUSCAR TRABAJADORES HD FINIQUITOS///////////////////////////////////////
	@RequestMapping(value = "/work/allTrabajadores_HD_FiniquitosTabla/{sociedad},{huerto},{zona},{ceco},"
			+ "{tipocontrato},{codtrab},{periodo},{estado_proceso},{rolPrivado}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getBuscarHDFiniquitoTrabajadoresTabla(
			@PathVariable String sociedad, @PathVariable String huerto, @PathVariable String zona,
			@PathVariable String ceco, @PathVariable String tipocontrato, @PathVariable String codtrab,
			@PathVariable String periodo, 
			@PathVariable String estado_proceso,
			@PathVariable int rolPrivado,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

		if (ses.isValid()) {
			return es;
		}

		es = FiniquitosBD.getBuscarHDFiniquitoTrabajadoresTabla(sociedad, huerto, zona, ceco, tipocontrato, codtrab,
				periodo,estado_proceso,rolPrivado);
		return es;
	}
//////////////////// GET datos trabajador HD Finiquitos para modificar///////////////////////////////////////////
			@RequestMapping(value = "/work/getModificarHDFiniquitos/{id},{contrato},{codtra}", method = { RequestMethod.GET, RequestMethod.POST })
			public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getModifiHDFiniquito(
					@PathVariable int id,@PathVariable int contrato,@PathVariable int codtra,   HttpSession httpSession) throws Exception {

				session ses = new session(httpSession);
				ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

				if (ses.isValid()) {
					return es;
				}

				es = FiniquitosBD.getModifiHDFiniquito(id,contrato,codtra);
				return es;

			}
			
			///////////   todas las fechas de termino  por periodo finiquito
			@RequestMapping(value = "/work/getAllFechaFiniquito/{periodo},{empresa}", method = { RequestMethod.GET, RequestMethod.POST })
			public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getAllfechasxPereiodoF(@PathVariable int periodo,@PathVariable String empresa,
					HttpSession httpSession) throws Exception {

				session ses = new session(httpSession);
				ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

				if (ses.isValid()) {
					return es;
				}

				es = FiniquitosBD.getAllfechasxPereiodoF(periodo,empresa);
				return es;
			}	
			
	///////////   todas las fechas para imprimir finiquito por periodo seleccionado ///////
				@RequestMapping(value = "/work/getAllFechaImprimirFiniquito/{periodo},{empresa}", method = { RequestMethod.GET, RequestMethod.POST })
				public @ResponseBody ArrayList<DatosTrabajadorFiniquito> getAllfechasxPereiodoFImprimir(@PathVariable int periodo,@PathVariable String empresa,
						HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);
					ArrayList<DatosTrabajadorFiniquito> es = new ArrayList<DatosTrabajadorFiniquito>();

					if (ses.isValid()) {
						return es;
					}

					es = FiniquitosBD.getAllfechasxPereiodoFImprimir(periodo,empresa);
					return es;
				}	
				
				
		// Mostrar PDF del Finiquito
		@RequestMapping(value = "/work/finiquitosJSON/showFiniquitoPDF/", method = RequestMethod.GET)
		public @ResponseBody String showFiniquitoPDF(HttpServletRequest request, HttpServletResponse response,
				HttpSession session) {

			try {
				
				String nameFile = request.getParameter("FILE");

				ConviertePdfFromWordx obtenerLiquidacionPdf = new ConviertePdfFromWordx();
				String urlDocGenerado = utils.getServerFolder("DocumentoFiniquito") + File.separator;
				String nombreDoc = nameFile.replace(".docx", ".pdf");
				obtenerLiquidacionPdf.convertToPDF(urlDocGenerado + nameFile,
						urlDocGenerado + nombreDoc, "");

				File file = new File(urlDocGenerado + nombreDoc);
				FileInputStream fileInputStreamReader = new FileInputStream(file);
				byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
						
				InputStream targetStream = new ByteArrayInputStream(bytes);
				fileInputStreamReader.close();
						
				//Transformar PDF a PNG
				InputStream in = PdfToImage.convertPDFInputStreamToPNG(targetStream);
						
				// Convertir InputStram en array de Bytes
				byte[] bytes2 = IOUtils.toByteArray(in);

				BASE64Encoder encoder = new BASE64Encoder();
				String imagenCode = encoder.encode(bytes2);

				if (file.exists()) {
					file.delete();
				}
						
						
				return imagenCode;
						

			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}

		}		
				
				
				
				
				
				
				
			
}