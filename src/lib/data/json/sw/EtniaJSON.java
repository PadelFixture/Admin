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
import lib.db.sw.EtniaDB;
import lib.security.session;

@Controller
public class EtniaJSON {
	@RequestMapping(value = "/work/Etnias/createEtnia/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createEtnia(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
         
          
          resp = EtniaDB.createParam(param, "ETNIA");
        return resp;
    }
	
	
	
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/Etnias/getEtnias/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getEtnias( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = EtniaDB.getEtnias();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Etnias/getEtniaById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getEtniaById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros params = new parametros();
		params = EtniaDB.getEtniaById(""+id);
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Etnias/updateEtnia/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateEtnia(@RequestBody parametros param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 param.setCodigo("ETNIA");
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = EtniaDB.updateParam(param);
        return resp;
	}
	@RequestMapping(value = "/work/Etnias/deleteEtnia/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteEtnia(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return EtniaDB.deleteParById("ETNIA", ""+id);

	}
}
