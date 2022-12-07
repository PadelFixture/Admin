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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lib.classSW.GetDatosContratoTrabajador;
import lib.classSW.Liquidacion;
import lib.classSW.LiquidacionDetalle;
import lib.classSW.LiquidacionTrabajador;
import lib.data.json.ConviertePdfFromWordx;
import lib.db.sw.InformacionDocumentoDB;
import lib.db.sw.LiquidacionesDB;
import lib.security.session;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import pdfCreator.PdfToImage;
import sun.misc.BASE64Encoder;
import wordCreator.DocxCreator;
import wordCreator.utils;

@Controller
public class LiquidacionJSON {

	private final static Logger LOG = LoggerFactory.getLogger(LiquidacionJSON.class);

	// Para generar La liquidacion se necesita pasar el codigoTrabajador de la
	// tabla trabajador
	@RequestMapping(value = "/work/generateLiquidacionZipFiles/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String generateLiquidacionZipFiles(
			@RequestBody ArrayList<LiquidacionDetalle> listadoLiquidacion) throws Exception {

		// Obtener ruta para guardar liquidacion
		String ruta = utils.getServerFolder("Liquidacion");
		LOG.info("Ruta donde se guarda el Archivo de Liquidacion: " + ruta);

		System.out.println(listadoLiquidacion);

		File f = new File(
				ruta + File.separator + "LiquidacionesMasivas" + Calendar.getInstance().getTimeInMillis() + ".zip");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));

		for (LiquidacionDetalle liquidacionDetalle : listadoLiquidacion) {

			InputStream in = new FileInputStream(ruta + File.separator + "Liquidacion_"
					+ liquidacionDetalle.getCodTrabajador() + "_" + liquidacionDetalle.getPeriodo() + ".docx");

			ZipEntry e = new ZipEntry("Liquidacion_" + liquidacionDetalle.getCodTrabajador() + "_"
					+ liquidacionDetalle.getPeriodo() + ".docx");
			out.putNextEntry(e);

			// Copiar datos en nuevo File
			IOUtils.copy(in, out);
			in.close();

		}

		out.closeEntry();
		out.close();

		return null;

	}

	// NEW
	// Para generar La liquidacion se necesita pasar el codigoTrabajador de la
	// tabla trabajador
	@RequestMapping(value = "/work/generateMultipleLiquidacion/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Set<String> generateMultipleLiquidacion(
			@RequestBody ArrayList<LiquidacionDetalle> listadoLiquidacion, HttpServletRequest request ) throws Exception {

		//Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		//Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			//Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				//Añadir campo y valor 
				filter.add(fil);
			}
		}
		
		// Obtener ruta para guardar liquidacion
		String ruta = utils.getServerFolder("Liquidacion");

		// Obtener la fecha para Guardar le Nombre del Archivo
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM-yyyy");
		Date now = new Date();
		String strDate = sdfDate.format(now);

		System.out.println(listadoLiquidacion);
		
		//Unir docx
		@SuppressWarnings("resource")
		XWPFDocument doc2=new XWPFDocument();
				
		@SuppressWarnings("resource")
		XWPFDocument doc3=new XWPFDocument();
				
		for(int i=0;i<listadoLiquidacion.size();i++){
					
			String[] fecha = listadoLiquidacion.get(i).getPeriodo().split("-");
			String periodo = fecha[1]+fecha[0];
					
			String nameFile = "Liquidacion_" + listadoLiquidacion.get(i).getCodTrabajador() + "_" + periodo + "_" + listadoLiquidacion.get(i).getIdContrato() + ".docx";
					
			InputStream in =  new FileInputStream(ruta + nameFile);
			OPCPackage pkg = OPCPackage.open(in);
			XWPFDocument doc = new XWPFDocument(pkg);
			if(i==0){
				doc2=doc;
				doc.createParagraph().setPageBreak(true);
			}
			else{
				doc3=doc;
				if(i<listadoLiquidacion.size()-1){
				doc3.createParagraph().setPageBreak(true);
				}
				doc2=merge(doc2,doc3);
			}	
		}
				
		/* Constructor del path */
		String fileName = "Liquidacion Masiva" + GeneralUtility.getCurrentDate() + ".docx";
		String outPath = ruta + fileName;
				
		FileOutputStream ou=new FileOutputStream(outPath);
		doc3.close();
		doc2.write(ou);
		doc2.close();
		ou.close();		
				
	return Collections.singleton(fileName);

}

	// NEW

	// Para generar La liquidacion se necesita pasar el codigoTrabajador de la
	// tabla trabajador
	@RequestMapping(value = "/work/generateLiquidacion/{id},{periodo}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String generateLiquidacion(@PathVariable String id, @PathVariable String periodo,
			@RequestBody String datosLiquidacion, HttpServletRequest request) throws Exception {

		// Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		// Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			// Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				// Añadir campo y valor
				filter.add(fil);
			}
		}

		// Obtener ruta para guardar liquidacion
		String ruta = utils.getServerFolder("Liquidacion");
		LOG.info("Ruta donde se guarda el Archivo de Liquidacion: " + ruta);

		LOG.info("Obtener Datos del Trabajador");
		GetDatosContratoTrabajador datosContratoTrabajador = new GetDatosContratoTrabajador();
		datosContratoTrabajador = InformacionDocumentoDB.obtenerDatosTrabajador(id);

		// Transformar los datos a JSON
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String datosContratoTrabajadorJSON = ow.writeValueAsString(datosContratoTrabajador);

		LOG.info("Generando Reporte en Word de Liquidacion...");
		DocxCreator dx = new DocxCreator();
		Blob liquidacion = LiquidacionesDB.getLiquidacion();
		// File file = new
		// File("CartaTermino_"+datosContratoTrabajador.getCodigoTrabajador()+"@"+".docx");

		File file = File.createTempFile(
				"Liquidacion_" + datosContratoTrabajador.getCodigoTrabajador() + "_" + periodo + "@", ".docx", null);

		InputStream in = liquidacion.getBinaryStream();
		OutputStream out = new FileOutputStream(file);

		// Copiar datos en nuevo File
		IOUtils.copy(in, out);

		// Cerrar Input/Output Stream
		in.close();
		out.close();

		// Crear nuevo objeto File Input Stream del Archivo
		FileInputStream fi = new FileInputStream(file.getAbsolutePath());
		ObjectMapper objectMapper = new ObjectMapper();

		// add this line
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		List<Map<String, Object>> myObjects = objectMapper.readValue(datosLiquidacion,
				new TypeReference<List<Map<String, Object>>>() {
				});

		// Modificacion de Documento para Introducir Datos de Trabajador
		dx.modifyDocumentWithTable(ruta.replaceAll("\"", ""), file.getName(), datosContratoTrabajadorJSON, fi,
				myObjects, filter);

		return null;

	}

	// Mostrar PDF de la Liquidacion
	@RequestMapping(value = "/work/showLiquidacion", method = RequestMethod.GET)
	public @ResponseBody String showLiquidacion(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		try {
			String idTrabajador = request.getParameter("ID");
			String periodo = request.getParameter("PERIODO");

			ConviertePdfFromWordx obtenerLiquidacionPdf = new ConviertePdfFromWordx();
			String urlDocGenerado = utils.getServerFolder("Liquidacion") + File.separator;
			String nombreDoc = "Liquidacion_" + idTrabajador + "_" + periodo + ".pdf";
			obtenerLiquidacionPdf.convertToPDF(urlDocGenerado + "Liquidacion_" + idTrabajador + "_" + periodo + ".docx",
					urlDocGenerado + nombreDoc, "");

			File file = new File(urlDocGenerado + nombreDoc);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			response.addHeader("Content-disposition", "attachment; filename= " + nombreDoc + "");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			fileInputStreamReader.close();

			if (file.exists()) {
				file.delete();
			}

			return "Descargando Archivo...";

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}
	
	// Mostrar PDF de la Liquidacion
		@RequestMapping(value = "/work/showLiquidacionPDF", method = RequestMethod.GET)
		public @ResponseBody String showLiquidacionPDF(HttpServletRequest request, HttpServletResponse response,
				HttpSession session) {

			try {
				String nameFile = request.getParameter("FILE");

				ConviertePdfFromWordx obtenerLiquidacionPdf = new ConviertePdfFromWordx();
				String urlDocGenerado = utils.getServerFolder("Liquidacion") + File.separator;
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
				
				//fileInputStreamReader.close();
				
				return imagenCode;
				
				/*
				response.addHeader("Content-disposition", "attachment; filename= " + nombreDoc + "");
				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream out = response.getOutputStream();
				out.write(bytes);
				out.flush();
				out.close();
			
				return "Descargando Archivo...";
				*/

			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}

		}
	
	

	// Mostrar PDF de la Liquidacion
	@RequestMapping(value = "/work/showMultipleLiquidacion", method = RequestMethod.GET)
	public @ResponseBody String showMultipleLiquidacion(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		try {
			String fileName = request.getParameter("FILE");

			String urlDocGenerado = utils.getServerFolder("Liquidacion") + File.separator;
			String nombreDoc = fileName;
			File file = new File(urlDocGenerado + nombreDoc);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			response.addHeader("Content-disposition", "attachment; filename= " + nombreDoc + "");
			response.setContentType("application/msword");
			response.setContentLength(bytes.length);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			fileInputStreamReader.close();

			if (file.exists()) {
				file.delete();
			}

			return "Descargando Archivo...";

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

	// insert Liquidacion
	@RequestMapping(value = "/work/insertLiquidacion/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTrabajadorLicenciaConducir(@RequestBody Liquidacion Liquidacion,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return LiquidacionesDB.insertLiquidacion(Liquidacion);
	} // fin insertar

	// get Liquidacion
	@RequestMapping(value = "/work/getLiquidacion/", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Liquidacion> getLiquidacion(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Liquidacion> Liquidacion = new ArrayList<Liquidacion>();

		if (ses.isValid()) {
			return Liquidacion;
		}

		Liquidacion = LiquidacionesDB.getLiquidacionLista();
		return Liquidacion;

	}// fin get

	// get Liquidacion con id
	@RequestMapping(value = "/work/getLiquidacionBycod_trabajador/{id}", method = { RequestMethod.GET })
	public @ResponseBody Liquidacion getLiquidacionByIdTrabajador(@PathVariable int id, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		Liquidacion LiquidacionBycod_trabajador = new Liquidacion();

		if (ses.isValid()) {
			return LiquidacionBycod_trabajador;
		}

		LiquidacionBycod_trabajador = LiquidacionesDB.getLiquidacionByCod_trabajador(id);
		return LiquidacionBycod_trabajador;

	}// fin Get con id

	// get Liquidacion con inner join
	@RequestMapping(value = "/work/getLiquidacionTrabajdor/", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<LiquidacionTrabajador> getLiquidacionTrabajador(HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<LiquidacionTrabajador> LiquidacionTrabajador = new ArrayList<LiquidacionTrabajador>();

		if (ses.isValid()) {
			return LiquidacionTrabajador;
		}

		LiquidacionTrabajador = LiquidacionesDB.getLiquidacionTrabajador();
		return LiquidacionTrabajador;

	}// fin get

	// get Liquidacion con id (inner join)
	@RequestMapping(value = "/work/getLiquidacionTrabajadorBycod_trabajador/{id}", method = { RequestMethod.GET })
	public @ResponseBody LiquidacionTrabajador getLiquidacionTrabajadorByIdTrabajador(@PathVariable int id,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		LiquidacionTrabajador LiquidacionTrabajadorBycod_trabajador = new LiquidacionTrabajador();

		if (ses.isValid()) {
			return LiquidacionTrabajadorBycod_trabajador;
		}

		LiquidacionTrabajadorBycod_trabajador = LiquidacionesDB.getLiquidacionTrabajadorByCod_trabajador(id);
		return LiquidacionTrabajadorBycod_trabajador;

	}// fin Get con id

	// get Liquidacion con filtros
	@RequestMapping(value = "/work/liquidacion/getLiquidacionTrabajadorWithFilter/", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<LiquidacionTrabajador> getLiquidacionTrabajadorWithFilter(HttpSession httpSession,
			HttpServletRequest request) throws Exception {

		// Crear Lista de Trabajadores con sus liquidaciones vacias
		ArrayList<LiquidacionTrabajador> liquidacionesTrabajador = new ArrayList<LiquidacionTrabajador>();
		session ses = new session(httpSession);

		if (ses.isValid()) {
			return liquidacionesTrabajador;
		}

		// Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		// Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			// Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				// Añadir campo y valor
				filter.add(fil);
			}
		}

		liquidacionesTrabajador = LiquidacionesDB.getLiquidacionTrabajadorWithFilter(filter);
		return liquidacionesTrabajador;

	}// fin Get con id
	
	
	// get Liquidacion con filtros
		@RequestMapping(value = "/work/liquidacion/getLiquidacionTrabajadorWithFilter2/", method = { RequestMethod.GET })
		public @ResponseBody ArrayList<LiquidacionTrabajador> getLiquidacionTrabajadorWithFilter2(HttpSession httpSession,
				HttpServletRequest request) throws Exception {

			// Crear Lista de Trabajadores con sus liquidaciones vacias
			ArrayList<LiquidacionTrabajador> liquidacionesTrabajador = new ArrayList<LiquidacionTrabajador>();
			session ses = new session(httpSession);

			if (ses.isValid()) {
				return liquidacionesTrabajador;
			}

			// Obtener todos los parametros del URL
			Map<String, String[]> parameters = request.getParameterMap();

			ArrayList<filterSql> filter = new ArrayList<filterSql>();

			// Obtener todos los parametros enviados por el URL
			for (String key : parameters.keySet()) {
				String[] vals = parameters.get(key);
				// Obtener cada uno de los parametros y valores
				for (String val : vals) {
					filterSql fil = new filterSql();
					fil.setCampo(key);
					fil.setValue(val);
					// Añadir campo y valor
					filter.add(fil);
				}
			}

			liquidacionesTrabajador = LiquidacionesDB.getLiquidacionTrabajadorWithFilter2(filter);
			return liquidacionesTrabajador;

		}

	// update Liquidacion
	@RequestMapping(value = "/work/updateLiquidacion/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateLiquidacion(@RequestBody Liquidacion Liquidacion, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return LiquidacionesDB.updateLiquidacion(Liquidacion);
	}// fin

	// Eliminar
	@RequestMapping(value = "/work/deleteLiquidacionById/{id}", method = { RequestMethod.PUT })
	public @ResponseBody boolean deleteLiquidacion(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return LiquidacionesDB.deleteLiquidacionById(id);
	}// fin metodo eliminar

	@RequestMapping(value = "/work/calculoMensualLiquidaciones/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCargo(@RequestBody ArrayList<Liquidacion> row, HttpSession httpSession)
			throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		long in = System.currentTimeMillis();
		// for (Liquidacion rec : row) {
		// //System.out.println("Hola ");
		// //recc = impexp_trabajador.insertarAnticiposInd(rec);
		// impexp_trabajador.getCreateLiquidacion(/*rec.getCodtrabajador(),rec.getIdcontrato(),rec.getPeriodo()*/1,1,1);
		// }
		date = new Date();
		System.out.println(dateFormat.format(date));
		long fin = System.currentTimeMillis();
		int tiempo = (int) ((fin - in) / 1000);
		System.out.println("tiempo ejecucion : " + tiempo);

		return true;
	}

	@RequestMapping(value = "/work/allTrabajadoresConLiquidacion/{cod}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ArrayList<Liquidacion> getallTrabajaConLiqui(@PathVariable int cod, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		ArrayList<Liquidacion> es = new ArrayList<Liquidacion>();

		if (ses.isValid()) {
			return es;
		}

		es = LiquidacionesDB.getallTrabajaConLiqui(cod);
		return es;

	}

	@RequestMapping(value = "/work/LoadSelectIdContratoLIquidacion/{cod}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Liquidacion> LoadSelectIdContratoLIqui(@PathVariable int cod,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<Liquidacion> r = new ArrayList<Liquidacion>();

		if (ses.isValid()) {
			return r;
		}
		r = LiquidacionesDB.LoadSelectIdContratoLIqui(cod);

		return r;

	}

	@RequestMapping(value = "/work/buscarLiquidacionParaEliminar/{soci},{periodo},{idcontrato},{codtrabajador},{tipo_division},{tipo_subdivision},{grupo}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Liquidacion> LoadLiquidacionEliminar(@PathVariable String soci,
			@PathVariable String periodo, @PathVariable String idcontrato, @PathVariable String codtrabajador,
			@PathVariable String tipo_division, @PathVariable String tipo_subdivision, @PathVariable String grupo,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<Liquidacion> r = new ArrayList<Liquidacion>();

		if (ses.isValid()) {
			return r;
		}
		r = LiquidacionesDB.LoadLiquidacionEliminar(soci, periodo, idcontrato, codtrabajador, tipo_division,
				tipo_subdivision, grupo);

		return r;

	}

	@RequestMapping(value = "/work/EliminarLiquidaciontrabajador/{periodo},{id_liquidacion},{cod_trabajador},{id_contrato}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean eliminarLIQUITRAB(@PathVariable int periodo, @PathVariable int id_liquidacion,
			@PathVariable int cod_trabajador, @PathVariable int id_contrato, HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}

		recc = LiquidacionesDB.eliminarLIQUITRAB(periodo, id_liquidacion, cod_trabajador, id_contrato);

		return recc;

	}

	private XWPFDocument merge(XWPFDocument doc0, XWPFDocument doc1) {

		for (IBodyElement e : doc1.getBodyElements()) {
			if (e instanceof XWPFParagraph) {
				XWPFParagraph p = (XWPFParagraph) e;
				if (p.getCTP().getPPr() != null && p.getCTP().getPPr().getSectPr() != null) {
					continue;
				} else {
					doc0.createParagraph();
					doc0.setParagraph(p, doc0.getParagraphs().size() - 1);
				}
			} else if (e instanceof XWPFTable) {
				XWPFTable t = (XWPFTable) e;
				doc0.createTable();
				doc0.setTable(doc0.getTables().size() - 1, t);
			} else if (e instanceof XWPFSDT) {
				// boh!
			}
		}
		return doc0;
	}

}