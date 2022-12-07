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

import com.google.gson.Gson;

import lib.ClassSASW.parametros;
import lib.db.sw.APVDB;

import lib.security.session;
@Controller
public class APVJSON {
	@RequestMapping(value = "/work/APVs/createAPV/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createAPV(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          resp = APVDB.createParam(param, "APV");
        return resp;
    }
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/APVs/getAPVs/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getAPVs( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = APVDB.getAPVs();
		if (ses.isValid()) {
			return params;
		}		
		return params;
	}
	@RequestMapping(value = "/work/APVs/getAPVById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getAPVById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros params = new parametros();
		params = APVDB.getAPVById(""+id);
		if (ses.isValid()) {
			return params;
		}		
		return params;
	}
	@RequestMapping(value = "/work/APVs/updateAPV/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateAPV(@RequestBody parametros param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 param.setCodigo("APV");
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		resp = APVDB.updateParam(param);
        return resp;
	}
	@RequestMapping(value = "/work/APVs/deleteAPV/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteAPV(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		
		System.out.println(id);
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}			
		return APVDB.deleteParById("APV",""+id);
	}
}
