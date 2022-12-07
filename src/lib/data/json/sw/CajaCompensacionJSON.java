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

import lib.db.sw.CajaCompensacionDB;
import lib.security.session;

@Controller
public class CajaCompensacionJSON {
	@RequestMapping(value = "/work/CajasCompensacion/createCajaCompensacion/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createCajaCompensacion(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          Gson j=new Gson();
          System.out.println(j.toJson(param));
          
          resp = CajaCompensacionDB.createParam(param, "CAJA_COMPENSACION");
        return resp;
    }	
	 @RequestMapping(value = "/work/CajasCompensacion/getCajasCompensacion/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<parametros> getCajasCompensacions( HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			ArrayList<parametros> params = new ArrayList<parametros>();
			params = CajaCompensacionDB.getCajasCompensacion();
			if (ses.isValid()) {
				return params;
			}		
			return params;

		}
		@RequestMapping(value = "/work/CajasCompensacion/getCajaCompensacionById/{id}", method = {RequestMethod.GET})
		public @ResponseBody parametros getCajasCompensacionById(@PathVariable int id , HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			parametros params = new parametros();
			params = CajaCompensacionDB.getCajaCompensacionById(""+id);
			if (ses.isValid()) {
				return params;
			}		
			return params;

		}
		@RequestMapping(value = "/work/CajasCompensacion/updateCajaCompensacion/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody boolean updateCajaCompensacion(@RequestBody parametros param,HttpSession httpSession) throws Exception {
	    	 boolean resp = false;
	    	 
	    	 param.setCodigo("CAJA_COMPENSACION");
	    	 
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			
			resp = CajaCompensacionDB.updateParam(param);
	        return resp;
		}
		@RequestMapping(value = "/work/CajasCompensacion/deleteCajaCompensacion/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteCajaCompensacion(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return CajaCompensacionDB.deleteCajaCompensacionById(""+id);

		}
}
