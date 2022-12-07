package lib.data.json.SASW;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Collections;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import TarjaPallet.TarjaPalletCreator;
import lib.ClassSASW.ResponseObject;
import lib.ClassSASW.TarjaPallet;
import lib.classSW.AnticiposIndividuales;
import lib.db.sw.DocumentsDB;
import lib.security.session;
import lib.utils.GeneralUtility;
import wordCreator.DocxCreator;
import wordCreator.utils;

@Controller
public class TarjaPalletJSON {
	@RequestMapping(value = "/work/TarjaPallets/CreateTarjaDocument/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void CreateTarjaDocument(@RequestBody TarjaPallet tp,HttpSession httpSession, 
    	    HttpServletResponse response) throws Exception {
    	 
    	 ResponseObject RO= new ResponseObject();
    	
		session ses = new session(httpSession);
		if (ses.isValid()) {
			RO.setCode(403);
			RO.setResponse("Necesita Credenciales");
		
		}
		
				String pathFolder = utils.getServerFolder("TarjaPallet") + File.separator+tp.getPathDestino()+File.separator;
				
				//Crear nombre del documento
				String nombreDoc = "TarjaPallet_" + GeneralUtility.getCurrentDate() + ".docx";
				
				File file = File.createTempFile(nombreDoc+"@",null);
		
		
		
		TarjaPalletCreator tpc=new TarjaPalletCreator();
		RO.setCode(200);
		RO.setResponse("ingresé correctamente al servicio");
		
		XWPFDocument d=tpc.CreateDocument(tp);
		response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		
		
		d.write(response.getOutputStream()) ;
		response.flushBuffer();
		
		
	}
	@RequestMapping(value = "/work/TarjaPallets/CreateTarjaDocumento/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody ResponseObject CreateTarjaDocumento(@RequestBody TarjaPallet tp,HttpSession httpSession, 
    	    HttpServletResponse response) throws Exception {
		ResponseObject ro= new ResponseObject();
		try {
			String urlDocGenerado = utils.TarjaPallet();
			System.out.println(tp.getNombreArchivo());
			String nombreDoc = tp.getNombreArchivo() + ".docx";
			String documentoWord = urlDocGenerado +nombreDoc;
			System.out.println(nombreDoc);
			String fiche=utils.TarjaPallet() + nombreDoc;
			File archivoWord = new File(documentoWord);
				
				System.out.println("ingrese doc");
				
				if(!archivoWord.exists()){
				System.out.println("No Existe Documento");
					
					generateTarjaPallet(nombreDoc , 75, tp);
				}
				else{
					System.out.println("Existe Documento");
					archivoWord.delete();
					System.out.println("Borro Documento");
					generateTarjaPallet(nombreDoc , 75, tp);
				}
				
				ro= new ResponseObject();
				ro.setCode(200);
				System.out.println(nombreDoc);
				ro.setFile(nombreDoc);
				ro.setRuta(fiche);
			return ro;

		} catch (Exception e) {
			 ;
			ro= new ResponseObject();
			ro.setCode(500);
			ro.setResponse(e.getMessage());
			ro.setFile("");
			ro.setRuta("");
		return ro;
		}
		
		
	}
	@RequestMapping(value = "/work/TarjaPallets/descargarDocumentoTarjaPallet/{nombre}", method = RequestMethod.GET)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String descargarDocumentoTarjaPallet(@PathVariable String nombre ,HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String fileName = nombre;
			fileName = fileName.replaceAll("\"", "");
			System.out.println("ruta: {}"+fileName);
			
			String urlDocGenerado = utils.TarjaPallet() + fileName+".docx";
			
		
			System.out.println("aqui   "+urlDocGenerado);
			File file = new File(urlDocGenerado);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(fileInputStreamReader);			
			response.addHeader("Content-disposition", "attachment; filename= "+fileName+".docx");
			response.setContentType("application/msword");
			response.setContentLength(bytes.length);
			response.setCharacterEncoding("iso-8859-1");
			ServletOutputStream out = response.getOutputStream();
			
			out.write(bytes);
			out.flush();
			out.close();
			fileInputStreamReader.close();
			System.out.println("termine de hacer el archivo");
			return urlDocGenerado;

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	private String generateTarjaPallet(String nombreDoc, int i, TarjaPallet tp) throws Exception {
		
		System.out.println("Ingreso al generador De tarja");
		String ruta = utils.TarjaPallet();
		System.out.println("La ruta es: "+ruta);
		TarjaPalletCreator dx = new TarjaPalletCreator();
		System.out.println("Creo el archivo Temporal");
		System.out.println("R:"+ruta);
		tp.setPathDestino(ruta);
		dx.CreateDocumento(ruta,tp.getNombreArchivo(), tp);
		
		return null;
		
	}
}
