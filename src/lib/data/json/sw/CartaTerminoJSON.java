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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lib.classSW.Contratos;
import lib.classSW.GetDatosCartaTerminoTrabajador;
import lib.db.sw.DocumentsDB;
import lib.db.sw.InformacionDocumentoDB;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;
import wordCreator.DocxCreator;
import wordCreator.utils;

@Controller
public class CartaTerminoJSON {

	private final static Logger LOG = LoggerFactory.getLogger(CartaTerminoJSON.class);

	//Crear la carta de Termino a los Trabajadores (Actualizar su contrato)
	@RequestMapping(value = "/work/createCartaTerminoMasivo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Set<String>> generateCartaTerminoMasivo(@RequestBody ArrayList<Contratos> contratosList, HttpServletRequest request) throws Exception {
		
		// Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<>();

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
		
		
		// Obtener ruta para guardar Carta de Termino
		String ruta = utils.getServerFolder("CartaTermino") + File.separator;
		String extension = ".docx";
		
		//Crear nombre del documento
		String nombreDoc = "CartaTermino_" + GeneralUtility.getCurrentDate();
		
		//Lista de Informacion para las Cartas de Termino
		ArrayList<String> listDatosTrabajador = new ArrayList<String>();
		
		try {
			
			for (Contratos contratos : contratosList) {
				
				//Obtener Informacion del Colaborador;
				GetDatosCartaTerminoTrabajador datosCartaTerminoByTrabajador = new GetDatosCartaTerminoTrabajador();
				datosCartaTerminoByTrabajador = InformacionDocumentoDB.obtenerDatosTrabajadorByCod(contratos.getCodigo_trabajador()+"");
				datosCartaTerminoByTrabajador.setFechaNotificacion(contratos.getFechaNotificacion()+"");
				//datosCartaTerminoByTrabajador.setHoraFinPagoFiniquito(contratos);
				
				//Pasar el Objeto a JSON string
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String datosCartaTerminoByTrabajadorJSON = ow.writeValueAsString(datosCartaTerminoByTrabajador);
				
				//Agregar a Lista de String
				listDatosTrabajador.add(datosCartaTerminoByTrabajadorJSON);
				
			}
			
			
			DocxCreator dx = new DocxCreator();
			LOG.info("Obtener Carta de Termino de la Base de Datos ");
			Blob templateCartaTermino = DocumentsDB.getFileById(filter.get(0).getValue());
			
			File file = File.createTempFile(nombreDoc+"@",extension,null);
			
			//Transformar a OutputStream
			InputStream in = templateCartaTermino.getBinaryStream();
			OutputStream out = new FileOutputStream(file);
			IOUtils.copy(in, out);
			in.close();
			out.close();
			
			FileInputStream fi = new FileInputStream(file.getAbsolutePath());
			dx.modifyDocumentMasivo(ruta, listDatosTrabajador, fi);
			file.deleteOnExit();
			
			return new ResponseEntity<>(Collections.singleton(nombreDoc), HttpStatus.OK);
			
		}catch(Exception e){
			
			return new ResponseEntity<>(Collections.singleton("El documento no se pudo generar por: "+ e.getMessage()), HttpStatus.BAD_REQUEST);

		}
		
		
		
	}
	
	
	@RequestMapping(value = "/work/showCartaTermino", method = RequestMethod.GET)
	public @ResponseBody String showCartaTermino(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		try {
			String fileName = request.getParameter("FILE");

			String urlDocGenerado = utils.getServerFolder("CartaTermino") + File.separator;
			String nombreDoc = fileName;
			String extension = ".docx";
			File file = new File(urlDocGenerado + nombreDoc + extension);

			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
			response.addHeader("Content-disposition", "attachment; filename= " + nombreDoc + extension);
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
	
	
	
	
//	//Crear la carta de Termino a los Trabajadores (Actualizar su contrato)
//	@RequestMapping(value = "/work/createCartaTermino/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ResponseEntity<Set<String>> generateCartaTermino(Contratos contratos, HttpServletRequest request) throws Exception {
//
//		String documentName = "";
//		//int codTrabajador = 0;
//
//		//try {
//
//			// Obtener todos los parametros del URL
//			Map<String, String[]> parameters = request.getParameterMap();
//
//			ArrayList<filterSql> filter = new ArrayList<filterSql>();
//
//			// Obtener todos los parametros enviados por el URL
//			for (String key : parameters.keySet()) {
//				String[] vals = parameters.get(key);
//				// Obtener cada uno de los parametros y valores
//				for (String val : vals) {
//					filterSql fil = new filterSql();
//					fil.setCampo(key);
//					fil.setValue(val);
//					// Añadir campo y valor
//					filter.add(fil);
//				}
//			}
//
////			// Obtener ruta para guardar liquidacion
////			String ruta = utils.getServerFolder("CartaTermino");
////			LOG.info("Ruta donde se guarda el Archivo de Liquidacion: " + ruta);
//
//			LOG.info("Generamos Cambios en la Carta de Termino del Trabajador");
//			//Generamos los Cambios de Carta de Termino a la Tabla Contratos
//			//TODO: CONFIGURACION AQUI CAMBIE DONDE SE ALOJA EL METODO AHORA SEPARACIONDB CartaTerminoDB.createCartaTermino(contratos);
//			
////			LOG.info("Obtener Datos del Trabajador");
////			GetDatosContratoTrabajador datosContratoTrabajador = new GetDatosContratoTrabajador();
////			datosContratoTrabajador = InformacionDocumentoDB.obtenerDatosTrabajadorByCod(String.valueOf(contratos.getCodigo_trabajador()));
//
////			// Transformar los datos a JSON
////			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
////			String datosContratoTrabajadorJSON = ow.writeValueAsString(datosContratoTrabajador);
//
////			LOG.info("Generando Reporte en Word de CartaTermino...");
//			
////			DocxCreator dx = new DocxCreator();
//			
////			LOG.info("Obtener Carta de Termino de la Base de Datos ");
////			Blob templateCartaTermino = TemplateDB.getFileById(idTemplate).getFile();
//
////			File file = File.createTempFile("CartaTermino_" + contratos.getCodigo_trabajador() + "_" + contratos.getId() + "@", ".docx",
////					null);
//
////			InputStream in = templateCartaTermino.getBinaryStream();
////			OutputStream out = new FileOutputStream(file);
//
//			// Copiar datos en nuevo File
////			IOUtils.copy(in, out);
//
//			// Cerrar Input/Output Stream
////			in.close();
////			out.close();
//
//			// Crear nuevo objeto File Input Stream del Archivo
////			FileInputStream fi = new FileInputStream(file.getAbsolutePath());
//
//			// Modificacion de Documento para Introducir Datos de Trabajador
////			dx.modifyDocument(ruta.replaceAll("\"", ""), datosContratoTrabajadorJSON, fi);
//
////			String pathDocument = ruta;
////			file.deleteOnExit();
//			
////			if (pathDocument != null) {
////
////				File archivoFinal = new File(pathDocument);
////
////				if (archivoFinal.exists()) {
////					documentName = archivoFinal.getName();
////				} else {
////					return new ResponseEntity<>(Collections.singleton(documentName), HttpStatus.BAD_REQUEST);
////				}
////			}
////
////		} catch (Exception e) {
////			return new ResponseEntity<>(Collections.singleton("Error con el Trabajador: " + codTrabajador),
////					HttpStatus.BAD_REQUEST);
////		}
//
//		// return Collections.singleton(file.getName());
////		return new ResponseEntity<>(Collections.singleton(documentName), HttpStatus.OK);
//
//		return new ResponseEntity<>(Collections.singleton(documentName), HttpStatus.OK);
//			
//		}
//
//	
//	
//	@RequestMapping(value = "/work/generateCartaTermino/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
//	public @ResponseBody String generateCartaTermino(@PathVariable String id, HttpSession session) throws Exception {
//		
//		String ruta = utils.getServerFolder("CartaTermino");
//		
//		LOG.info("Obtener Datos del Trabajador");
//		GetDatosContratoTrabajador datosContratoTrabajador = new GetDatosContratoTrabajador();
//		datosContratoTrabajador = InformacionDocumentoDB.obtenerDatosTrabajador(id);
//
//		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		String datosContratoTrabajadorJSON = ow.writeValueAsString(datosContratoTrabajador);
//		
//		LOG.info("Obtener archivo Carta de Termino...");
//		DocxCreator dx= new DocxCreator();
//		Blob cartaTermino = SeparacionDB.getCartaTermino();
//		//File file = new File("CartaTermino_"+datosContratoTrabajador.getCodigoTrabajador()+"@"+".docx");
//		
//		File file = File.createTempFile("CartaTermino_"+datosContratoTrabajador.getCodigoTrabajador()+"@", ".docx", null);
//		
//		InputStream in = cartaTermino.getBinaryStream();
//		OutputStream out = new FileOutputStream(file);
//		
//		IOUtils.copy(in, out);
//		in.close();
//		out.close();
//	
//		FileInputStream fi = new FileInputStream(file.getAbsolutePath());	
//		System.out.println("path absoluto: "+file.getAbsolutePath());
//		
//		dx.modifyDocument(ruta.replaceAll("\"", ""), datosContratoTrabajadorJSON, fi);
//		
//		return null;
//		
//	}
//	
//	
//	//TODO: Mostrar PDF de la Carta
//	@RequestMapping(value = "/work/showCartaTermino", method = RequestMethod.GET)
//	public @ResponseBody String showCartaTermino(HttpServletRequest request, HttpServletResponse response,
//			HttpSession session) {
//		
//		try {
//			String codTrabajador = request.getParameter("id");
//
//			ConviertePdfFromWordx obtenerContratoPdf = new ConviertePdfFromWordx();
//			String urlDocGenerado = utils.getServerFolder("CartaTermino") + File.separator;
//			String nombreDoc = "CartaTermino_" + codTrabajador + ".pdf";
//			
//			File archivoWord = new File(urlDocGenerado + "CartaTermino_" + codTrabajador + ".docx");
//			int idTrabajadador;
//			//Si el Archivo word no existe lo crea
//			if(!archivoWord.exists()){
//				
//				idTrabajadador = trabajadoresDB.getIdTrabajadorByCodigo(codTrabajador);
//				generateCartaTermino(idTrabajadador+"", session);
//				
//			}
//			
//			obtenerContratoPdf.convertToPDF(urlDocGenerado + "CartaTermino_"+codTrabajador+".docx", urlDocGenerado + nombreDoc, "");
//			
//			
//			File file = new File(urlDocGenerado + nombreDoc );
//			FileInputStream fileInputStreamReader = new FileInputStream(file);
//			byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);
//			response.addHeader("Content-disposition", "attachment; filename= " + nombreDoc + "");
//			response.setContentType("application/pdf");
//			response.setContentLength(bytes.length);
//			ServletOutputStream out = response.getOutputStream();
//			out.write(bytes);
//			out.flush();
//			out.close();
//			fileInputStreamReader.close();
//
//			if (file.exists()) {
//				file.delete();
//			}
//
//			return "Descargando Archivo...";
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return e.getMessage();
//		}
//		
//		
//	}
//	
//	
//	
//	
//	
}
