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

import lib.db.sw.IsapreDB;
import lib.security.session;

@Controller
public class IsapreJSON {
	@RequestMapping(value = "/work/Isapres/createIsapre/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createIsapre(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = IsapreDB.createParam(param, "ISAPRE");
        return resp;
    }
	 @RequestMapping(value = "/work/Isapres/getIsapres/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<parametros> getIsapres( HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			ArrayList<parametros> params = new ArrayList<parametros>();
			params = IsapreDB.getIsapres();
			if (ses.isValid()) {
				return params;
			}		
			return params;

		}
		@RequestMapping(value = "/work/Isapres/getIsapreById/{id}", method = {RequestMethod.GET})
		public @ResponseBody parametros getIsapreById(@PathVariable int id , HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			parametros params = new parametros();
			params = IsapreDB.getIsapreById(""+id);
			if (ses.isValid()) {
				return params;
			}		
			return params;

		}
		@RequestMapping(value = "/work/Isapres/updateIsapre/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody boolean updateIsapre(@RequestBody parametros param,HttpSession httpSession) throws Exception {
	    	 boolean resp = false;
	    	 
	    	 param.setCodigo("ISAPRE");
	    	 
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			
			resp = IsapreDB.updateParam(param);
	        return resp;
		}
		@RequestMapping(value = "/work/Isapres/deleteIsapre/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteIsapre(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return IsapreDB.deleteParById("ISAPRE",""+id);

		}
}
