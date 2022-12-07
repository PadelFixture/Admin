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
import lib.db.sw.InstitucionDB;
import lib.security.session;

@Controller
public class InstitucionJSON {
	@RequestMapping(value = "/work/Instituciones/createInstitucion/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createInstitucion(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
         
          
          resp = InstitucionDB.createParam(param, "INSTITUCIONES");
        return resp;
    }
	
	
	
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/Instituciones/getInstituciones/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getInstituciones( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = InstitucionDB.getInstituciones();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Instituciones/getInstitucionById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getInstitucionById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros params = new parametros();
		params = InstitucionDB.getParamById(id);
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Instituciones/updateInstitucion/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateInstitucion(@RequestBody parametros param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 param.setCodigo("INSTITUCIONES");
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = InstitucionDB.updateParam(param);
        return resp;
	}
	@RequestMapping(value = "/work/Instituciones/deleteInstitucion/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteInstitucion(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return InstitucionDB.deleteParamById(id);

	}
}
