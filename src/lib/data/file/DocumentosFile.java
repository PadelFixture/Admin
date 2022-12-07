package lib.data.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lib.ClassSASW.Template;
import lib.SADB.noIncidencia;
import lib.classSW.Document;
import lib.classSW.Documentos;
import lib.db.SASW.TemplateDB;
import lib.db.sw.DocumentosDB;
import lib.db.sw.DocumentsDB;
import lib.security.session;
import lib.struc.filterSql;
import pdfCreator.PdfToImage;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@Controller
public class DocumentosFile {

	//private final static Logger LOG = LoggerFactory.getLogger(DocumentosFile.class);

	// insertar documentos
	@RequestMapping(value = "/work/DocumentosFile/imagenes/insertDocumentos/", method = RequestMethod.POST)
	public @ResponseBody int insertDocumentos(HttpServletRequest request,
			@RequestParam("documento") MultipartFile multipartFile, HttpSession httpSession) throws Exception {

		// Obtener Documento en InputStream
		InputStream fileInputStream = multipartFile.getInputStream();
		String nombreDocumento = request.getParameter("nombreDocumento");
		
		//Convertir PDF a PNG
		if(request.getParameter("nombreDocumento").contains(".pdf") && "50".equals(request.getParameter("tipoDocumento"))){
			 fileInputStream = PdfToImage.convertPDFInputStreamToPNG(fileInputStream);
			 nombreDocumento = request.getParameter("nombreDocumento").replace(".pdf", ".png");
		}

		// Contenido en Byte
		byte[] contents;
		contents = IOUtils.toByteArray(fileInputStream);
		Blob fileBlob = new javax.sql.rowset.serial.SerialBlob(contents);

		// Crear Documento con lo enviado por parametro
		Documentos documentos = new Documentos();

		documentos.setCodTrabajador(Integer.parseInt(request.getParameter("codTrabajador")));
		documentos.setTipoDocumento(Integer.parseInt(request.getParameter("tipoDocumento")));
		documentos.setNombreDocumento(nombreDocumento);
		documentos.setDocumento(fileBlob);

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return 0;
		}

		// Devolvemos el Id Generado por el Insert
		return DocumentosDB.insertDocumentos(documentos);
	} // fin insertar
	
	
	// insertar documentos
		@RequestMapping(value = "/work/DocumentosFile/imagenes/downloadDocumentosWithFilter/", method = RequestMethod.GET)
		public @ResponseBody String downloadDocumentos(HttpServletRequest request, HttpServletResponse response) throws Exception {

			Documentos documentos = new Documentos();
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

			// Obtener Objetos
			documentos = DocumentosDB.getDocumentosWithFilter(filter);

			// Documento Blob
			Blob documentoBlob = documentos.getDocumento();

			if (documentoBlob == null) {
				return "";
			}

			File file = File.createTempFile("imagen"+"@",null);
			// Pasar Blob a Input Stream
			InputStream in = documentoBlob.getBinaryStream();
			OutputStream out = new FileOutputStream(file);
			IOUtils.copy(in, out);
			in.close();
			out.close();
			FileInputStream fi = new FileInputStream(file.getAbsolutePath());
			byte[] bytes = IOUtils.toByteArray(fi);
			response.addHeader("Content-disposition", "attachment; filename= " + documentos.getNombreDocumento());
			response.setContentType("application/msword");
			response.setContentLength(bytes.length);
			ServletOutputStream servletOut = response.getOutputStream();
			servletOut.write(bytes);
			servletOut.flush();
			servletOut.close();
			fi.close();
			file.delete();
			
			return "Descargando Archivo...";
			
		} // fin insertar
	
	
	
	
	
	

	// Obtener Todos los Trabajador con sus respectivos contratos
	@RequestMapping(value = "/work/getDocumentosWithFilter/", method = {
			RequestMethod.GET }, produces = MediaType.IMAGE_PNG_VALUE + "; charset=utf-8")
	public @ResponseBody String getDocumentosWithFilter(HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Documentos documentos = new Documentos();
		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
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

		// Obtener Objetos
		documentos = DocumentosDB.getDocumentosWithFilter(filter);

		if(documentos.getDocumento() == null){
			return "";
		}
		
		// Documento Blob
		Blob documentoBlob = documentos.getDocumento();

		
		// Pasar Blob a Input Stream
		InputStream in = documentoBlob.getBinaryStream();
		
		//Transformar PDF a PNG
		if(documentos.getNombreDocumento().contains(".pdf")){
			in = PdfToImage.convertPDFInputStreamToPNG(in);
		}

		// Convertir InputStram en array de Bytes
		byte[] bytes = IOUtils.toByteArray(in);

		BASE64Encoder encoder = new BASE64Encoder();
		String imagenCode = encoder.encode(bytes);

		return imagenCode;

	}

	// Crear Documento en Tabla sw_template
	@RequestMapping(value = "/work/documents/insertDocument/", method = { RequestMethod.POST })
	public @ResponseBody boolean insertDocument(HttpServletRequest request,
			@RequestParam("file") MultipartFile multipartFile, HttpSession httpSession) throws Exception {
		boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {

			return false;
		}

		Document doc = new Document();
		doc.setDocumento(request.getParameter("documento"));
		doc.setTipoDocumento(Integer.parseInt(request.getParameter("tipoDocumento")));
		doc.setIdEmpresa(Integer.parseInt(request.getParameter("idEmpresa")));
		doc.setIdHuerto(request.getParameter("idHuerto"));

		// Obtener Documento en InputStream
		InputStream fileInputStream = multipartFile.getInputStream();
		// Contenido en Byte
		byte[] contents;
		contents = IOUtils.toByteArray(fileInputStream);
		Blob fileBlob = new javax.sql.rowset.serial.SerialBlob(contents);
		doc.setFile(fileBlob);

		// file

		resp = DocumentsDB.insertDocument(doc, ses.getIdUser());

		return resp;

	}

	// Actualizaciï¿½n de todo el template by object
	@RequestMapping(value = "/work/documents/updateDocuments/", method = RequestMethod.POST)
	public @ResponseBody boolean updateDocuments(HttpServletRequest request,
			@RequestParam("file") MultipartFile multipartFile, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		Document doc = new Document();
		doc.setDocumento(request.getParameter("documento"));
		doc.setTipoDocumento(Integer.parseInt(request.getParameter("tipoDocumento")));
		doc.setIdEmpresa(Integer.parseInt(request.getParameter("idEmpresa")));
		doc.setIdHuerto(request.getParameter("idHuerto"));
		doc.setIdTemplate(Integer.parseInt(request.getParameter("idTemplate")));
		// Obtener Documento en InputStream
		InputStream fileInputStream = multipartFile.getInputStream();
		// Contenido en Byte
		byte[] contents;
		contents = IOUtils.toByteArray(fileInputStream);
		Blob fileBlob = new javax.sql.rowset.serial.SerialBlob(contents);
		doc.setFile(fileBlob);

		return DocumentsDB.updateAllDocument(doc);

	}
	
	
    //Obtener Documento por Id
    @RequestMapping(value = "/work/Documents/getTemplateById/{id}", method = {RequestMethod.GET})
		public @ResponseBody String getTemplateById(@PathVariable int id, HttpSession httpSession, HttpServletResponse response) throws Exception {

			session ses = new session(httpSession);
			
			if (ses.isValid()) {
				return null;
				}		
			 Template archivoDocx = TemplateDB.getFileById(id);
			
			// Documento Blob
			Blob documentoBlob = archivoDocx.getFile();
			if (documentoBlob == null) {
				return null;
			}
			 
			// Pasar Blob a Input Stream
			InputStream in = documentoBlob.getBinaryStream();

			// Convertir InputStram en array de Bytes
			byte[] bytes = IOUtils.toByteArray(in);
			BASE64Encoder encoder = new BASE64Encoder();
			String string64Encoder = encoder.encode(bytes);

			response.addHeader("Content-disposition", "attachment; filename= " + "prueba" + ".docx");
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setContentLength(bytes.length);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			in.close();

			
			 
			return string64Encoder;

		}
	

	// Delete documentos
	@RequestMapping(value = "/work/DocumentosFile/imagenes/deleteDocumentos/", method = RequestMethod.GET)
	public @ResponseBody boolean deleteDocumentos(HttpServletRequest request, HttpSession httpSession) throws Exception {

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

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		// Devolvemos el Id Generado por el delete
		return DocumentosDB.deleteDocumentos(filter);
	} 
	
	@RequestMapping(value = "/work/DocumentosFile/imagenes/downloadDocumentoExcelPrevired/", method = RequestMethod.GET)
	public @ResponseBody String downloadExcelPrevired(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Documentos documentos = new Documentos();

		String sociedad_ = request.getParameter("sociedad");
		String periodo_ = request.getParameter("periodo");

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		
		// Obtener Objetos
		documentos = DocumentosDB.getDocumentosWithFilter2(sociedad_,periodo_);

		// Documento Blob
		Blob documentoBlob = documentos.getDocumento();

		if (documentoBlob == null) {
			return "";
		}

		File file = File.createTempFile("imagen"+"@",null);
		// Pasar Blob a Input Stream
		InputStream in = documentoBlob.getBinaryStream();
		OutputStream out = new FileOutputStream(file);
		IOUtils.copy(in, out);
		in.close();
		out.close();
		FileInputStream fi = new FileInputStream(file.getAbsolutePath());
		byte[] bytes = IOUtils.toByteArray(fi);
		response.addHeader("Content-disposition", "attachment; filename= DetalleOrdenPago.xlsx");
		response.setContentType("application/msword");
		response.setContentLength(bytes.length);
		ServletOutputStream servletOut = response.getOutputStream();
		servletOut.write(bytes);
		servletOut.flush();
		servletOut.close();
		fi.close();
		file.delete();
		
		return "Descargando Archivo...";
		
	}

}
