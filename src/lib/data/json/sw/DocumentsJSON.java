package lib.data.json.sw;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.Document;

import lib.db.sw.DocumentsDB;

import lib.security.session;
import lib.struc.filterSql;


@Controller
public class DocumentsJSON {
    //Crear Documento
    @RequestMapping(value = "/work/documents/insertDocument/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean insertDocument(@RequestBody Document doc,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {
        	  
                 return false;
          }
     
          resp = DocumentsDB.insertDocument(doc, ses.getIdUser());
          
         
          
        return resp;
           
          
    }
  //Actualizaci�n de todo el template by object

    @SuppressWarnings("unused")
	@RequestMapping(value = "/work/documents/uploadFile/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean uploadFile(@RequestBody Document doc,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		String nombre=doc.getDocumento();
		
		//int idusuario=ses.getIdUser();
		
		
		
		
	return false;
		
	}
    
    
    
    
    
  //Actualizaci�n de todo el template by object
    @RequestMapping(value = "/work/documents/updateDocument/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateDocument(@RequestBody Document doc,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		String nombre=doc.getDocumento();
		
		//int idusuario=ses.getIdUser();
		int idusuario=ses.getIdUser();
		int idtemplate=doc.getIdTemplate();
		int idEmpresa = doc.getIdEmpresa();
		String idHuerto = doc.getIdHuerto();
		
		return DocumentsDB.updateNombreDocument(idtemplate, nombre, idusuario, idEmpresa, idHuerto);
		
	}
    //Actualizaci�n de todo el template by object
    @RequestMapping(value = "/work/documents/updateDocuments/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateDocuments(@RequestBody Document doc,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return DocumentsDB.updateAllDocument(doc);
		
	}
    //Actualizaci�n de todo el template by Campos
    
	
	//Actualizaci�n partes del Documento		
        //actualizaci�n del nombre
        //actualizaci�n del archivo 	
        //actualizacion de fechaCreacion
        //actualizacion de fechaModificacion
        //actualizacion de estado 
        //actualizacion de idUsuario 
    //Borrar Documento por Id
  
  		
    //Obtener Documento por Id
    @RequestMapping(value = "/work/documents/getDocument/{id}", method = {RequestMethod.GET})
		public @ResponseBody Document getDocumentById(@PathVariable int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			Document dc = DocumentsDB.getDocumentById(id);
			if (ses.isValid()) {
				return dc;
				}			
			return dc;

		}
    //Obtener Documento por Id
    @RequestMapping(value = "/work/getBlankDocument/", method = {RequestMethod.GET})
		public @ResponseBody Document getBlankDocument(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			Document dc = DocumentsDB.getBlankDocument();
			if (ses.isValid()) {
				return dc;
				}			
			return dc;

		}
    //Obtener Todos los Documentos
    @RequestMapping(value = "/work/getDocuments/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Document> getDocuments(HttpServletRequest request, HttpSession httpSession) throws Exception {

    	ArrayList<Document> dcs = new ArrayList<Document>();
    	session ses = new session(httpSession);
    	
    	if (ses.isValid()) {
			return dcs;
		}
    	
    	Map<String, String[]> parameters = request.getParameterMap();
	    ArrayList<filterSql> filter = new ArrayList<filterSql>();
	    
	    for (String key : parameters.keySet()) {
	    	String[] vals = parameters.get(key);
	    		for (String val : vals) {
	    			filterSql fil = new filterSql();
	    			fil.setCampo(key);
	    			fil.setValue(val);
	    			filter.add(fil);
	    		}
	    }
    	
		 dcs = DocumentsDB.getDocumentos(filter);

		return dcs;

	}
    
    @RequestMapping(value = "/work/documents/getDocuments/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Document> getAllDocuments(HttpServletRequest request, HttpSession httpSession) throws Exception {

    	ArrayList<Document> dcs = new ArrayList<Document>();
    	session ses = new session(httpSession);
    	
    	if (ses.isValid()) {
			return dcs;
		}
    	System.out.println("pase dentro de los documentos");
		 dcs = DocumentsDB.getDocuments();

		return dcs;

	}
	@RequestMapping(value = "/work/documents/deleteDocument/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteDcoment(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return DocumentsDB.deleteDocumentById(id);

	}
}
