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
import lib.SADB.RENDIMIENTO;
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
public class SimpleAgroFile {

	@RequestMapping(value = "/AGRO/INSERT_DOC/", method = RequestMethod.POST)
	public @ResponseBody int INSERT_DOC(HttpServletRequest request,
			@RequestParam("documento") MultipartFile multipartFile, HttpSession httpSession) throws Exception {

		InputStream fileInputStream = multipartFile.getInputStream();
		String nombreDocumento = request.getParameter("nombreDocumento");
//		if(request.getParameter("nombreDocumento").contains(".pdf") && "50".equals(request.getParameter("tipoDocumento"))){
//			 fileInputStream = PdfToImage.convertPDFInputStreamToPNG(fileInputStream);
//			 nombreDocumento = request.getParameter("nombreDocumento").replace(".pdf", ".png");
//		}
		byte[] contents;
		contents = IOUtils.toByteArray(fileInputStream);
		Blob fileBlob = new javax.sql.rowset.serial.SerialBlob(contents);
		Documentos documentos = new Documentos();

		documentos.setNombreDocumento(nombreDocumento);
		documentos.setDocumento(fileBlob);
		System.out.println(fileBlob);
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return 0;
		}
		return noIncidencia.INSERT_DOC(documentos);
	}
	@RequestMapping(value = "/AGRO/GET_IMAGEN_INCIDENCIA", method = {RequestMethod.GET }, produces = MediaType.IMAGE_PNG_VALUE + "; charset=utf-8")
	public @ResponseBody String GET_IMAGEN_INCIDENCIA(HttpSession httpSession, HttpServletRequest request,HttpServletResponse response) throws Exception {

		Documentos documentos = new Documentos();
		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}
		int codigo = Integer.parseInt(request.getParameter("CODIGO"));
		documentos = noIncidencia.GET_IMAGEN_INCIDENCIA(codigo);

		if(documentos.getDocumento() == null){
			return "";
		}
		System.out.println(documentos);
		Blob documentoBlob = documentos.getDocumento();
		InputStream in = documentoBlob.getBinaryStream();
//		if(documentos.getNombreDocumento().contains(".pdf")){
//			in = PdfToImage.convertPDFInputStreamToPNG(in);
//		}
		byte[] bytes = IOUtils.toByteArray(in);

		BASE64Encoder encoder = new BASE64Encoder();
		String imagenCode = encoder.encode(bytes);

		return imagenCode;

	}
	@RequestMapping(value = "/AGRO/GET_IMAGEN_LIQUIDACION", method = {RequestMethod.GET }, produces = MediaType.IMAGE_PNG_VALUE + "; charset=utf-8")
	public @ResponseBody String GET_IMAGEN_LIQUIDACION(HttpSession httpSession, HttpServletRequest request,HttpServletResponse response) throws Exception {

		Documentos documentos = new Documentos();
		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}
		int codigo = Integer.parseInt(request.getParameter("CODIGO"));
		documentos = RENDIMIENTO.GET_IMAGEN_LIQUIDACION(codigo);

		if(documentos.getDocumento() == null){
			return "";
		}
		System.out.println(documentos);
		Blob documentoBlob = documentos.getDocumento();
		InputStream in = documentoBlob.getBinaryStream();
		byte[] bytes = IOUtils.toByteArray(in);

		BASE64Encoder encoder = new BASE64Encoder();
		String imagenCode = encoder.encode(bytes);

		return imagenCode;

	}

}
