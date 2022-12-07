package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.ClassSASW.parametros;
import lib.db.sw.CorreosDB;
import lib.security.session;

@Controller
public class CorreosJSON {
	@RequestMapping(value = "/work/Correos/createCorreo/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createCorreo(@RequestBody parametros e,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = CorreosDB.createCorreo(e);
        return resp;
    }
	//Obtener Todas las Correos
    @RequestMapping(value = "/work/Correos/getCorreos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getCorreos( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = CorreosDB.getCorreos();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Correos/getCorreoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getCorreoById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros e = new parametros();
		e = CorreosDB.getCorreoById(id);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	
	@RequestMapping(value = "/work/Correos/updateCorreo/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateCorreo(@RequestBody parametros e,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    
    	 
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		
		resp = CorreosDB.updateCorreo(e);
        return resp;
	}
	@RequestMapping(value = "/work/Correos/deleteCorreoById/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteCorreoById(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return CorreosDB.deleteCorreoById(id);

	}
	
}
