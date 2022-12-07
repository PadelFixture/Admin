package lib.data.json.SASW;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lib.ClassSASW.Template;
import lib.db.SASW.TemplateDB;
import lib.db.sw.DocumentsDB;
import lib.security.session;


@Controller
public class TemplateJSON {
	@RequestMapping(value = "/work/Templates/createTemplate/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createTemplate(@RequestBody Template t,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {
                 return false;
          }
          t.setIdUsuario(ses.getIdUser());
          resp = TemplateDB.createTemplate(t);
        return resp;
    }

	    
	    
	    
	    
	  //Actualizaci�n de todo el template by object
	    @RequestMapping(value = "/work/Templates/updateTemplate/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateTemplate(@RequestBody Template t,HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			t.setIdUsuario(ses.getIdUser());
			return TemplateDB.updateTemplate(t);
			
		}
	    @RequestMapping(value = "/work/Templates/uploadFile/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean uploadFile(@PathVariable int id,HttpServletRequest request,HttpServletResponse response,@RequestParam("file") MultipartFile file,HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			
			return DocumentsDB.uploadFile(file, id);
			
		}
	    
	    //Obtener Documento por Id
	    @RequestMapping(value = "/work/Templates/getTemplateById/{id}", method = {RequestMethod.GET})
			public @ResponseBody Template getTemplateById(@PathVariable int id, HttpSession httpSession) throws Exception {

				session ses = new session(httpSession);
				Template t=new Template();
				
				if (ses.isValid()) {
					return t;
					}		
				 t= TemplateDB.getTemplateById(id);
				return t;

			}
	   
	   
	    @RequestMapping(value = "/work/Templates/getTemplates/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<Template> getTemplates(HttpServletRequest request, HttpSession httpSession) throws Exception {

	    	ArrayList<Template> ts = new ArrayList<Template>();
	    	session ses = new session(httpSession);
	    	
	    	if (ses.isValid()) {
				return ts;
			}
	    	System.out.println("pase dentro de los documentos");
			 ts = TemplateDB.getTemplates();

			return ts;

		}
	    @RequestMapping(value = "/work/Templates/getFileById/{id}", method = {RequestMethod.GET})
		public @ResponseBody Template getFileById(@PathVariable int id, HttpSession httpSession) throws Exception {

	    	Template ts = new Template();
	    	session ses = new session(httpSession);
	    	
	    	if (ses.isValid()) {
				return ts;
			}
	    	System.out.println("pase dentro de los documentos");
			 ts = TemplateDB.getFileById(id);

			return ts;

		}
		@RequestMapping(value = "/work/Templates/deleteTemplate/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteDocument(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return TemplateDB.deleteTemplate(id);

		}
		
		
		//Show Templates
		@RequestMapping(value = "/work/Templates/showTemplate/{idTemplate}", method = RequestMethod.GET)
		public @ResponseBody String showTemplate(HttpServletRequest request, HttpServletResponse response,
				HttpSession session, @PathVariable int idTemplate) {

			try {

				 Template archivoDocx = TemplateDB.getFileById(idTemplate);
				 
				// Documento Blob
				Blob documentoBlob = archivoDocx.getFile();
				if (documentoBlob == null) {
					return "El registro con nombre: "+request.getParameter("nombreDocumento")+" no tiene archivo adjunto";
				}
				
				// Pasar Blob a Input Stream
				InputStream in = documentoBlob.getBinaryStream();
				
				byte[] bytes = IOUtils.toByteArray(in);
				response.addHeader("Content-disposition", "attachment; filename= " + request.getParameter("nombreDocumento") + "." +  request.getParameter("extension")  );
				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream out = response.getOutputStream();
				out.write(bytes);
				out.flush();
				out.close();
				in.close();

				return "Descargando Archivo...";
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		
		
	}
	
	
}
