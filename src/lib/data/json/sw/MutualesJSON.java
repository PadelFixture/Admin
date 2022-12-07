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
import lib.db.sw.MutualesDB;
import lib.security.session;

@Controller
public class MutualesJSON {
	@RequestMapping(value = "/work/Mutuales/createMutual/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createMutual(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          resp = MutualesDB.createParam(param, "MUTUALES");
        return resp;
    }
	
	
	
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/Mutuales/getMutuales/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getMutuales( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = MutualesDB.getMutuales();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Mutuales/getMutualById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getMutualById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros params = new parametros();
		params = MutualesDB.getMutualById(""+id);
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Mutuales/updateMutual/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateMutual(@RequestBody parametros param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 param.setCodigo("MUTUALES");
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = MutualesDB.updateParam(param);
        return resp;
	}
	@RequestMapping(value = "/work/Mutuales/deleteMutual/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteMutual(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return MutualesDB.deleteParById("MUTUALES", ""+id);

	}
}
