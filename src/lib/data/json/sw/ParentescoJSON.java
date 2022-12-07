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
import lib.db.sw.ParentescoDB;
import lib.security.session;

@Controller
public class ParentescoJSON {
	@RequestMapping(value = "/work/Parentescos/createParentesco/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createParentesco(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          resp = ParentescoDB.createParentesco(param, "PARENTESCO");
        return resp;
    }
	
	
	
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/Parentescos/getParentescos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getParentescos( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = ParentescoDB.getParentescos();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Parentescos/getParentescoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getParentescoById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros params = new parametros();
		params = ParentescoDB.getParentescoById(""+id);
				if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Parentescos/updateParentesco/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateParentesco(@RequestBody parametros param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 param.setCodigo("PARENTESCO");
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = ParentescoDB.updateParam(param);
        return resp;
	}
	@RequestMapping(value = "/work/Parentescos/deleteParentesco/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteParentesco(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}				
		return ParentescoDB.deleteParById("PARENTESCO",""+id);
	}
}
