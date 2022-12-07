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

import lib.db.sw.NacionalidadDB;
import lib.security.session;

@Controller
public class NacionalidadJSON {
	@RequestMapping(value = "/work/Nacionalidades/createNacionalidad/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createNacionalidad(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = NacionalidadDB.createParam(param, "NACIONALIDAD");
        return resp;
    }
	 @RequestMapping(value = "/work/Nacionalidades/getNacionalidades/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<parametros> getNacionalidades( HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			ArrayList<parametros> params = new ArrayList<parametros>();
			params = NacionalidadDB.getNacionalidades();
			if (ses.isValid()) {
				return params;
			}		
			return params;

		}
		@RequestMapping(value = "/work/Nacionalidades/getNacionalidadById/{id}", method = {RequestMethod.GET})
		public @ResponseBody parametros getNacionalidadById(@PathVariable int id , HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			parametros params = new parametros();
			params = NacionalidadDB.getNacionalidadById(""+id);
			if (ses.isValid()) {
				return params;
			}		
			return params;

		}
		@RequestMapping(value = "/work/Nacionalidades/updateNacionalidad/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody boolean updateNacionalidad(@RequestBody parametros param,HttpSession httpSession) throws Exception {
	    	 boolean resp = false;
	    	 
	    	 param.setCodigo("NACIONALIDAD");
	    	 
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			
			resp = NacionalidadDB.updateParam(param);
	        return resp;
		}
		@RequestMapping(value = "/work/Nacionalidades/deleteNacionalidad/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteIsapre(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return NacionalidadDB.deleteNacionalidadById(""+id);

		}
}
