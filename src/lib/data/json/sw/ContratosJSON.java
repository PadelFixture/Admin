package lib.data.json.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lib.classSW.DocContrato;
import lib.classSW.GetDatosContratoTrabajador;
import lib.classSW.ImprimirInformacionTrabajador;
import lib.db.sw.DocumentsDB;
import lib.db.sw.InformacionDocumentoDB;
import lib.db.sw.trabajadoresDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import wordCreator.DocxCreator;
import wordCreator.utils;

@Controller
public class ContratosJSON {

	private final static Logger LOG = LoggerFactory.getLogger(PreviredJson.class);

	@RequestMapping(value = "/work/generateContrato/{id},{idTemplate}", method = { RequestMethod.GET,
			RequestMethod.PUT })
	public @ResponseBody String generateContrato(@PathVariable String id, @PathVariable String idTemplate,
			HttpSession session, @RequestBody ImprimirInformacionTrabajador informacionExtra) throws Exception {

		String ruta = utils.getServerFolder("ContratoTrabajador");

		LOG.info("Obtener Datos del Trabajador");
		GetDatosContratoTrabajador datosContratoTrabajador = new GetDatosContratoTrabajador();
		datosContratoTrabajador = InformacionDocumentoDB.obtenerDatosTrabajador(id);
		datosContratoTrabajador.setFechaContratacion(
				GeneralUtility.convertStringDDMMYYToDateInWord(informacionExtra.getFechaContratacion()));
		datosContratoTrabajador.setFechaPactoHorasExtra(
				GeneralUtility.convertStringDDMMYYToDateInWord(informacionExtra.getFechaPactoHorasExtra()));

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String datosContratoTrabajadorJSON = ow.writeValueAsString(datosContratoTrabajador);
		System.out.println("datos trabajador:" + datosContratoTrabajadorJSON);

		LOG.info("Obtener archivo Contrato...");
		DocxCreator dx = new DocxCreator();
		Blob Contrato = DocumentsDB.getFileById(idTemplate);
		File file = File.createTempFile("Contrato_" + datosContratoTrabajador.getCodigoTrabajador() + "@", ".docx",
				null);

		InputStream in = Contrato.getBinaryStream();
		OutputStream out = new FileOutputStream(file);

		IOUtils.copy(in, out);
		in.close();
		out.close();

		FileInputStream fi = new FileInputStream(file.getAbsolutePath());

		dx.modifyDocument(ruta.replaceAll("\"", ""), datosContratoTrabajadorJSON, fi);
		file.deleteOnExit();

		return null;

	}

	@RequestMapping(value = "/work/Contratos/generateContratosIndividuales/", method = { RequestMethod.GET,
			RequestMethod.PUT })
	public @ResponseBody ResponseEntity<Set<String>> generateContratosIndividuales(HttpServletRequest request,
			HttpSession session, @RequestBody DocContrato docContrato) throws Exception {

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		String nameFile = "Contrato_" + docContrato.getCodTrabajador() + "_" + docContrato.getIdContrato();
		String extension = ".docx";

		try {

			for (String key : parameters.keySet()) {
				String[] vals = parameters.get(key);

				for (String val : vals) {
					filterSql fil = new filterSql();
					fil.setCampo(key);
					fil.setValue(val);
					filter.add(fil);
				} // Fin del If para StartsWith

			} // Fin del For

			String idTemplate = request.getParameter("idTemplate");

			String ruta = utils.getServerFolder("ContratoTrabajador");

			File fileExist = new File(ruta + File.separator + nameFile + extension);

			if (fileExist.exists()) {
				fileExist.delete();
			}

			LOG.info("Obtener Datos del Trabajador");
			GetDatosContratoTrabajador datosContratoTrabajador = new GetDatosContratoTrabajador();
			datosContratoTrabajador = InformacionDocumentoDB.obtenerDatosTrabajadorByCodAndContratoForContrato(
					docContrato.getCodTrabajador(), docContrato.getIdContrato());
			datosContratoTrabajador.setFechaContratacion(
					GeneralUtility.convertStringDDMMYYToDateInWord(docContrato.getFechaEscrituracion()));
			datosContratoTrabajador.setFechaPactoHorasExtra(
					GeneralUtility.convertStringDDMMYYToDateInWord(docContrato.getFechaPactoHorasExtra()));
			datosContratoTrabajador.setFechaTerminoContratoDocumento(GeneralUtility.convertStringDDMMYYToDateInWord(docContrato.getFechaTerminoDocumento()));

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String datosContratoTrabajadorJSON = ow.writeValueAsString(datosContratoTrabajador);
			System.out.println("datos trabajador:" + datosContratoTrabajadorJSON);

			LOG.info("Obtener archivo Contrato...");
			DocxCreator dx = new DocxCreator();
			Blob Contrato = DocumentsDB.getFileById(idTemplate);
			File file = File.createTempFile(nameFile + "@", ".docx", null);

			InputStream in = Contrato.getBinaryStream();
			OutputStream out = new FileOutputStream(file);

			IOUtils.copy(in, out);
			in.close();
			out.close();

			FileInputStream fi = new FileInputStream(file.getAbsolutePath());

			dx.modifyDocument(ruta.replaceAll("\"", ""), datosContratoTrabajadorJSON, fi);
			file.deleteOnExit();

		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singleton("Error al generar el documento para el Trabajador: "
					+ docContrato.getCodTrabajador() + " <br></br> " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}

		// return Collections.singleton(file.getName());
		return new ResponseEntity<>(Collections.singleton(nameFile + extension), HttpStatus.OK);

	}

	@RequestMapping(value = "/work/showContrato", method = RequestMethod.GET)
	public @ResponseBody String showContrato(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String codTrabajador = request.getParameter("id");

			String urlDocGenerado = utils.getServerFolder("ContratoTrabajador") + File.separator;
			String nombreDoc = "Contrato_" + codTrabajador + ".docx";
			String documentoWord = urlDocGenerado + "Contrato_" + codTrabajador + ".docx";

			File archivoWord = new File(documentoWord);

			int idTrabajadador;
			if (!archivoWord.exists()) {

				idTrabajadador = trabajadoresDB.getIdTrabajadorByCodigo(codTrabajador);
				generateContrato(idTrabajadador + "", 69 + "", session, null);

			}

			File file = new File(documentoWord);
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

	@RequestMapping(value = "/work/generateOneContrato/{idTemplate}", method = RequestMethod.PUT)
	public @ResponseBody Set<String> generateOneContrato(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable String idTemplate,
			@RequestBody ImprimirInformacionTrabajador informacionExtra) {

		try {
			String codTrabajador = informacionExtra.getListaTrabajador();

			String urlDocGenerado = utils.getServerFolder("ContratoTrabajador") + File.separator;
			String nombreDoc = "Contrato_" + codTrabajador + ".docx";
			String documentoWord = urlDocGenerado + "Contrato_" + codTrabajador + ".docx";

			File archivoWord = new File(documentoWord);

			int idTrabajadador;
			if (!archivoWord.exists()) {

				idTrabajadador = trabajadoresDB.getIdTrabajadorByCodigo(codTrabajador);
				generateContrato(idTrabajadador + "", idTemplate, session, informacionExtra);

			}

			return Collections.singleton(nombreDoc);

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.singleton(e.getMessage());
		}

	}

	@RequestMapping(value = "/work/generateMultipleContrato/{idTemplate}", method = RequestMethod.PUT)
	public @ResponseBody Set<String> generateMultipleContrato(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable String idTemplate,
			@RequestBody ArrayList<ImprimirInformacionTrabajador> listaInformacionExtra) throws Exception {

		// Obtener Fecha de Hoy

		// Obtener Ruta del Servidor
		String pathFolder = utils.getServerFolder("ContratoTrabajador") + File.separator;

		// Crear nombre del documento
		String nombreDoc = "Contratos_" + GeneralUtility.getCurrentDate() + ".docx";
		// String pathCompleto = pathFolder + nombreDoc;

		// File archivoWord = new File(pathCompleto);

		// Lista de Informacion para los Contratos
		ArrayList<String> ListdeDatosContratosTrabajador = new ArrayList<String>();

		// Iterar sobre la lista de trabajadores
		for (ImprimirInformacionTrabajador imprimirInformacionTrabajador : listaInformacionExtra) {

			int idTrabajadador;
			// Obtener el Id del Trabajador
			idTrabajadador = trabajadoresDB.getIdTrabajadorByCodigo(imprimirInformacionTrabajador.getListaTrabajador());

			// Obtener Informacion del Colaborador;
			GetDatosContratoTrabajador datosContratoTrabajador = new GetDatosContratoTrabajador();
			datosContratoTrabajador = InformacionDocumentoDB.obtenerDatosTrabajadorForContrato(idTrabajadador + "", imprimirInformacionTrabajador.getIdContrato() );
			datosContratoTrabajador.setFechaContratacion(GeneralUtility
					.convertStringDDMMYYToDateInWord(imprimirInformacionTrabajador.getFechaContratacion()));

			// Pasar el Objeto a JSON string
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String datosContratoTrabajadorJSON = ow.writeValueAsString(datosContratoTrabajador);

			ListdeDatosContratosTrabajador.add(datosContratoTrabajadorJSON);

		}

		DocxCreator dx = new DocxCreator();
		Blob Contrato = DocumentsDB.getFileById(idTemplate);
		File file = File.createTempFile(nombreDoc + "@", null);

		InputStream in = Contrato.getBinaryStream();
		OutputStream out = new FileOutputStream(file);

		IOUtils.copy(in, out);
		in.close();
		out.close();

		FileInputStream fi = new FileInputStream(file.getAbsolutePath());

		dx.modifyDocumentMasivo(pathFolder, ListdeDatosContratosTrabajador, fi);
		file.deleteOnExit();

		return Collections.singleton(nombreDoc);

	}

	@RequestMapping(value = "/work/showOneContrato", method = RequestMethod.GET)
	public @ResponseBody String showOneContrato(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		try {
			String fileName = request.getParameter("FILE");

			String urlDocGenerado = utils.getServerFolder("ContratoTrabajador") + File.separator;
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

	//generar Multiples Contratos
	@RequestMapping(value = "/work/Contratos/generateMultipleContratos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Set<String> generateMultipleContratos(
			@RequestBody ArrayList<DocContrato> docContrato, HttpServletRequest request)
			throws Exception {

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
		String ruta = utils.getServerFolder("ContratoTrabajador") + File.separator;
		String extension = ".docx";
			
		
		@SuppressWarnings("resource")
		XWPFDocument doc2=new XWPFDocument();
		
		@SuppressWarnings("resource")
		XWPFDocument doc3=new XWPFDocument();
		
		for(int i=0;i<docContrato.size();i++){
			
			String nameFile = "Contrato_" + docContrato.get(i).getCodTrabajador() + "_" + docContrato.get(i).getIdContrato() + extension;
			
			InputStream in =  new FileInputStream(ruta + nameFile);
			OPCPackage pkg = OPCPackage.open(in);
			XWPFDocument doc = new XWPFDocument(pkg);
			if(i==0){
				doc2=doc;
				doc.createParagraph().setPageBreak(true);
			}
			else{
				doc3=doc;
				if(i<docContrato.size()-1){
				doc3.createParagraph().setPageBreak(true);
				}
				doc2=merge(doc2,doc3);
			}	
		}
		
		/* Constructor del path */
		String fileName = "Contratos Masivos" + GeneralUtility.getCurrentDate() + ".docx";
		String outPath = ruta + fileName;
		
		FileOutputStream ou=new FileOutputStream(outPath);
		doc3.close();
		doc2.write(ou);
		doc2.close();
		ou.close();		
		
		return Collections.singleton(fileName);

	}

	
	
	private XWPFDocument merge(XWPFDocument doc0, XWPFDocument doc1) {
		
		for(IBodyElement e : doc1.getBodyElements()){
	        if(e instanceof XWPFParagraph){
	            XWPFParagraph p = (XWPFParagraph) e;
	            if(p.getCTP().getPPr()!=null && p.getCTP().getPPr().getSectPr()!=null){
	                continue;
	            }else{
	                doc0.createParagraph();
	                doc0.setParagraph(p, doc0.getParagraphs().size()-1);
	            }
	        }else if(e instanceof XWPFTable){
	            XWPFTable t = (XWPFTable)e;
	            doc0.createTable();
	            doc0.setTable(doc0.getTables().size()-1,t);
	        }else if(e instanceof XWPFSDT){
	            // boh!
	        }
	    }
		return doc0;
	}
	
	
}
