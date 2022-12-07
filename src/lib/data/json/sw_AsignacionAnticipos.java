package lib.data.json;

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

import lib.classSW.AnticiposIndividuales;
import lib.classSW.Campo;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.PreNominaAnticipo;
import lib.classSW.TipoContrato;
import lib.db.sw.DocumentsDB;
import lib.security.session;
import wordCreator.DocxCreator;
import wordCreator.utils;
@Controller
public class sw_AsignacionAnticipos {

//-----------cargar por periodo-------------------------------------------------------------------------
	@RequestMapping(value = "/work/cargarPorPeriodoAsignacionSimple/{p}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<AnticiposIndividuales> getPeriodoAsignacionSimple(@PathVariable int p, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
		
		if (ses.isValid()) {
			return es;
		}
		
		es = SWDB.sw_AsignacionAnticiposDB.getPeriodoAsignacionSimple(p);
		return es;	
	}
//-----------cargar por Empresa-------------------------------------------------------------------------
	@RequestMapping(value = "/work/cargarPorEmpresaAsignacionSimple/{p}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<AnticiposIndividuales> getEmpresaAsignacionSimple(@PathVariable int p, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
		
		if (ses.isValid()) {
			return es;
		}
		
		es = SWDB.sw_AsignacionAnticiposDB.getEmpresaAsignacionSimple(p);
		return es;	
	}
//-----------cargar por Division-------------------------------------------------------------------------
		@RequestMapping(value = "/work/cargarPorDivisionAsignacionSimple/{p}", method = {RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody ArrayList<AnticiposIndividuales> getDivisionAsignacionSimple(@PathVariable int p, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
			
			if (ses.isValid()) {
				return es;
			}
			
			es = SWDB.sw_AsignacionAnticiposDB.getDivisionAsignacionSimple(p);
			return es;	
		}
//-----------cargar por SubDivision-------------------------------------------------------------------------
				@RequestMapping(value = "/work/cargarPorSubDivisionAsignacionSimple/{p}", method = {RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody ArrayList<AnticiposIndividuales> getSubDivisionAsignacionSimple(@PathVariable int p, HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);
					ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
					
					if (ses.isValid()) {
						return es;
					}
					
					es = SWDB.sw_AsignacionAnticiposDB.getSubDivisionAsignacionSimple(p);
					return es;	
				}
//-----------cargar por grupo-------------------------------------------------------------------------
				@RequestMapping(value = "/work/cargarPorGrupoAsignacionSimple/{p}", method = {RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody ArrayList<AnticiposIndividuales> getGrupoAsignacionSimple(@PathVariable int p, HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);
					ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
					
					if (ses.isValid()) {
						return es;
					}
					
					es = SWDB.sw_AsignacionAnticiposDB.getGrupoAsignacionSimple(p);
					return es;	
				}
				
			
//-----------cargar-------------------------------------------------------------------------
				@RequestMapping(value = "/work/BuscarAsignacionSimple/{periodo},{fechaPago},{nombre_trabajador},{sociedad},{tipo_division},{tipo_subdivision},{grupo},{tipo_contrato},{estado_proceso}", method = {RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody ArrayList<AnticiposIndividuales> getBuscarAsignacionSimple(
						@PathVariable String periodo,
						@PathVariable String fechaPago,
						@PathVariable String nombre_trabajador,
						@PathVariable String sociedad,
						@PathVariable String tipo_division,
						@PathVariable String tipo_subdivision,
						@PathVariable String grupo,
						@PathVariable String tipo_contrato,
						@PathVariable String estado_proceso,
						HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);
					ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
					
					if (ses.isValid()) {
						return es;
					}
//					
					es = SWDB.sw_AsignacionAnticiposDB.getBuscarAsignacionSimple(periodo,fechaPago,nombre_trabajador,sociedad,tipo_division,tipo_subdivision,grupo,tipo_contrato,estado_proceso);
					return es;	
				}
//---------------Enviar MAIL pre Nomina Asignacion Anticipo --------------------------------------------------------
				@RequestMapping(value = "/work/EnviarExecelPreNomina/", method = { RequestMethod.PUT,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody boolean getscvMAsEmail(@RequestBody ArrayList<PreNominaAnticipo> row, HttpSession httpSession)
						throws Exception {

					session ses = new session(httpSession);

					boolean r = false;
					if (ses.isValid()) {
						return r;

					}

					
					r = SWDB.sw_AsignacionAnticiposDB.SendMailExcelPreNominaAnticipos(row);
				

					return r;

				}
				
				//---------------Enviar MAIL pre Nomina Asignacion Anticipo --------------------------------------------------------
				@RequestMapping(value = "/work/GenerarExecelPreNomina/", method = { RequestMethod.PUT,
						RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
				public @ResponseBody String generarExcelPreNomina(@RequestBody ArrayList<PreNominaAnticipo> row, HttpSession httpSession)
						throws Exception {

					session ses = new session(httpSession);

					String r = "";
					if (ses.isValid()) {
						return "";

					}

					
					r = SWDB.sw_AsignacionAnticiposDB.GenerarExcelPreNominaAnticipos(row);
				 

					return r;
					
					

				}
				
/////////////////////////////////////////////////////////////////////////////////
				@RequestMapping(value = "/work/BuscarAsignacionSimpleImprimir/{per},{fec},{cod},{emp},{divi},{subd},{gru},{tipocuenta},{rolPrivado}", method = {RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody ArrayList<AnticiposIndividuales> getBuscarAsignacionSimpleImprimir(
						@PathVariable String per,
						@PathVariable String fec,
						@PathVariable String cod,
						@PathVariable String emp,
						@PathVariable String divi,
						@PathVariable String subd,
						@PathVariable String gru,
						@PathVariable String tipocuenta,
						@PathVariable int rolPrivado, 
						HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);
					ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
					
					if (ses.isValid()) {
						return es;
					}
					
					es = SWDB.sw_AsignacionAnticiposDB.getBuscarAsignacionSimpleImprimir(per,fec,cod,emp,divi,subd,gru,tipocuenta,rolPrivado);
					return es;	
				}
/////////////////////////////// GENERAR DOCUMENTO ASIGNACION ANTICIPO/////////////////////////////////////////
				@RequestMapping(value = "/work/generateDocumentoAnticipo/{idTemplate}", method = RequestMethod.PUT)
				public @ResponseBody Set<String> generateMultipleContrato2(HttpServletRequest request, HttpServletResponse response,
						 @PathVariable String idTemplate, @RequestBody AnticiposIndividuales informacionExtra) {
                        
					Date fechaActual = new Date();

					//Formateando la fecha:
					DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
					String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
					String horaf = formatoHora.replaceAll("[:]", "");
					
					try {
						String NOMBRE_UP = informacionExtra.getCodtrabajadorstring();
						String nombreArchivo = NOMBRE_UP + horaf;
						String idtablaAnticipo = informacionExtra.getIdstring();

						String urlDocGenerado = utils.DocumentoAnticipos();
						
						String nombreDoc = "Anticipo" + nombreArchivo + ".docx";
						String documentoWord = urlDocGenerado + "Anticipo" + NOMBRE_UP + ".docx";

						File archivoWord = new File(documentoWord);
	
						if (!archivoWord.exists()) {
							
							generateDocumentoAnticipo(nombreArchivo , idTemplate, informacionExtra,idtablaAnticipo);
						}

						return Collections.singleton(nombreDoc);

					} catch (Exception e) {
						e.printStackTrace();
						return Collections.singleton(e.getMessage());
					}

				}
				
/////////////////////////////////////////////////////////////////////////////////////////////////////
				
				public  String generateDocumentoAnticipo( String id,  String idTemplate,
						  AnticiposIndividuales informacionExtra,  String idtabla) throws Exception {
				
					String ruta = utils.DocumentoAnticipos();
					
					AnticiposIndividuales datosContratoTrabajador = new AnticiposIndividuales();
					datosContratoTrabajador = SWDB.sw_AsignacionAnticiposDB.obtenerDatosTrabajadorAnticipos(idtabla);
				

					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					String datosContratoTrabajadorJSON = ow.writeValueAsString(datosContratoTrabajador);
					
					DocxCreator dx = new DocxCreator();
					Blob Anticipo = DocumentsDB.getFileById(idTemplate);
					File file = File.createTempFile("Anticipo" + id + "@", ".docx",
							null);

					InputStream in = Anticipo.getBinaryStream();
					OutputStream out = new FileOutputStream(file);

					IOUtils.copy(in, out);
					in.close();
					out.close();

					FileInputStream fi = new FileInputStream(file.getAbsolutePath());
					
					dx.modifyDocument(ruta.replaceAll("\"", ""), datosContratoTrabajadorJSON, fi);
					return null;

				}
	////// descargar Archivo Anticipo///////////////////////////////////////////////////////////

				@RequestMapping(value = "/work/descargardocumentoAnticipo", method = RequestMethod.GET)
				public @ResponseBody String getAutorizacion(HttpServletRequest request, HttpServletResponse response,
						HttpSession session) {
					try {
						String fileName = request.getParameter("FILE");
						fileName = fileName.replaceAll("\"", "");
						
						String urlDocGenerado = utils.DocumentoAnticipos() + fileName;
					
						FileInputStream fileInputStreamReader = new FileInputStream(urlDocGenerado);
			            byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);			
						response.addHeader("Content-disposition", "attachment; filename= "+fileName+"");
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
				
/////////////////////////////// GENERAR DOCUMENTO ASIGNACION ANTICIPO MASIVO/////////////////////////////////////////
	@RequestMapping(value = "/work/generateDocumentoAnticipoMasivo/{idTemplate}", method = RequestMethod.PUT)
	public @ResponseBody Set<String> generateMultipleContrato3(HttpServletRequest request, HttpServletResponse response,
			 @PathVariable String idTemplate, @RequestBody AnticiposIndividuales informacionExtra) {
          
		Date fechaActual = new Date();

		//Formateando la fecha:
		DateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		String formatoHora =  ""+formatHora.format(fechaActual).toString()+"";
		String horaf = formatoHora.replaceAll("[:]", "");
		
		try {
			String NOMBRE_UP = informacionExtra.getCodtrabajadorstring();
			String nombreArchivo = NOMBRE_UP + horaf;
			String idtablaAnticipo = informacionExtra.getIdstring();

			String urlDocGenerado = utils.DocumentoAnticipos();
			
			String nombreDoc = "Anticipo" + nombreArchivo + ".docx";
			String documentoWord = urlDocGenerado + "Anticipo" + NOMBRE_UP + ".docx";

			File archivoWord = new File(documentoWord);

			if (!archivoWord.exists()) {
				
				generateDocumentoAnticipo2(nombreArchivo , idTemplate, informacionExtra,idtablaAnticipo);
			}

			return Collections.singleton(nombreDoc);

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.singleton(e.getMessage());
		}
	}
	////////////////////////////////////////////////////////////////////////////////
	public  String generateDocumentoAnticipo2( String id,  String idTemplate,
			  AnticiposIndividuales informacionExtra,  String idtabla) throws Exception {
	
		String ruta = utils.DocumentoAnticipos();
		
		ArrayList<AnticiposIndividuales> datosContratoTrabajador2 = new ArrayList<AnticiposIndividuales>();
		datosContratoTrabajador2 = SWDB.sw_AsignacionAnticiposDB.obtenerDatosTrabajadorAnticiposMasivos(idtabla);
	    
		ArrayList<String> datosContratoTrabajadorJSON = new ArrayList<String>();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		for (int i = 0; i < datosContratoTrabajador2.size(); i++) {
		String variable = ow.writeValueAsString(datosContratoTrabajador2.get(i));	
			datosContratoTrabajadorJSON.add(variable);
		}

		
		DocxCreator dx = new DocxCreator();
		Blob Anticipo = DocumentsDB.getFileById(idTemplate);
		File file = File.createTempFile("Anticipo" + id + "@", ".docx",
				null);

		InputStream in = Anticipo.getBinaryStream();
		OutputStream out = new FileOutputStream(file);

		IOUtils.copy(in, out);
		in.close();
		out.close();

		FileInputStream fi = new FileInputStream(file.getAbsolutePath());

		dx.modifyDocumentMasivo(ruta.replaceAll("\"", ""), datosContratoTrabajadorJSON, fi);

		return null;

	}
	
	@RequestMapping(value = "/work/LoadTrabajadorXfecha/{sociedad},{splitFecha}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getSociedaTrabAS2(@PathVariable String sociedad,
			@PathVariable String splitFecha, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = SWDB.sw_AsignacionAnticiposDB.getTrabajadoresFecha(sociedad, splitFecha);

		return r;

	}
	
////////////////////////LISTA TIPO DE CONTRATO//////////////////////////////////////////////////////////////////////
	
		@RequestMapping(value = "/work/ListaTipoContrato/", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<TipoContrato> getTipoContrato(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<TipoContrato> es = new ArrayList<TipoContrato>();

			if (ses.isValid()) {
				return es;
			}

			es = SWDB.sw_AsignacionAnticiposDB.getTipoContrato();
			return es;

		}
////////////////Get descripcion Huerto///////////////////////////////////////////////////////////////////////
		
		// todos las sociedades
		@RequestMapping(value = "/work/getDescripcionHuerto/{CodCampo}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<Campo> getCampoByCampo(@PathVariable String CodCampo,HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<Campo> es = new ArrayList<Campo>();

			if (ses.isValid()) {
				return es;
			}

			es = SWDB.sw_AsignacionAnticiposDB.getCampoByCampo(CodCampo);
			return es;

		}
		
		//-----------monto trabajado por codigo de trabajador-------------------------------------------------------------------------
		@RequestMapping(value = "/work/montoGanado/{codigo},{periodo},{idcontrato},{diaPago}", method = {RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody ArrayList<AnticiposIndividuales> getMontoGanado(@PathVariable int codigo, @PathVariable String periodo, @PathVariable int idcontrato, @PathVariable int diaPago, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
			
			if (ses.isValid()) {
				return es;
			}
			
			es = SWDB.sw_AsignacionAnticiposDB.getMontoGanadoTrabajador(codigo,periodo,idcontrato,diaPago);
			return es;	
		}
		
		//-----------monto trabajado por codigo de trabajador obteniendo el ultimo id contrato------------------------------------------------------------------------
				@RequestMapping(value = "/work/montoGanadoSinidContrato/{codigo},{periodo},{diaPago}", method = {RequestMethod.GET,RequestMethod.POST})
				public @ResponseBody ArrayList<AnticiposIndividuales> getMontoGanadosinidcontrato(@PathVariable int codigo, @PathVariable String periodo, @PathVariable int diaPago, HttpSession httpSession) throws Exception {

					session ses = new session(httpSession);
					ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();
					
					if (ses.isValid()) {
						return es;
					}
					
					es = SWDB.sw_AsignacionAnticiposDB.getMontoGanadosinidcontrato(codigo,periodo,diaPago);
					return es;	
				}
		
	//--------------------  obtener numero de cuenta SAP para anticipos ----------------------------------------
	@RequestMapping(value = "/work/numeroCuentaSAPanticipos/", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int obtenerNCuentaSapNominaAnticipo(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		int r = 0;
		if (ses.isValid()) {
			return 0;

		}

		r = SWDB.sw_AsignacionAnticiposDB.obtenerNCuentaSapNominaAnticipo();

		return r;

	}
	
	@RequestMapping(value = "/work/getAllFechaAnticipos/{periodo},{empresa}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<AnticiposIndividuales> getAllfechasxPereiodo(@PathVariable int periodo,@PathVariable String empresa,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<AnticiposIndividuales> es = new ArrayList<AnticiposIndividuales>();

		if (ses.isValid()) {
			return es;
		}

		es = SWDB.sw_AsignacionAnticiposDB.getAllfechasxPereiodo(periodo,empresa);
		return es;
	}
				
 // --------------------- importar excel Anticipos ------------------------------------------------
	@RequestMapping(value = "/work/insertExelmysqlAnticipos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertExcelAnticipos(@RequestBody AnticiposIndividuales row, HttpSession httpSession) throws Exception {
		String recc = "";
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}
        
		

			recc = SWDB.sw_AsignacionAnticiposDB.insertExcelAnticipos(row);

		
		
		return recc;

	}
		
}
