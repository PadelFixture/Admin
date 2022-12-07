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
import lib.db.sw.AFPmDB;
import lib.security.session;

@Controller
public class AFPmJSON {
	@RequestMapping(value = "/work/AFPm/createAFPm/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createAFPm(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          resp = AFPmDB.createParam(param, "AFP");
        return resp;
    }
	
	
	
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/AFPm/getAFPms/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getAFPms( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = AFPmDB.getAFPms();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/AFPm/getAFPmById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getAFPmById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros params = new parametros();
		params = AFPmDB.getAFPmById(""+id);
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/AFPm/updateAFPm/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateAFPm(@RequestBody parametros param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 param.setCodigo("AFP");
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = AFPmDB.updateParam(param);
        return resp;
	}
	@RequestMapping(value = "/work/AFPm/deleteAFPm/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteAFPm(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}				
		return AFPmDB.deleteParById("AFP",""+id);
	}
}
