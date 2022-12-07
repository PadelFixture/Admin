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
import lib.db.sw.BancoDB;
import lib.security.session;

@Controller
public class BancoJSON {
	@RequestMapping(value = "/work/Bancos/createBanco/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createBanco(@RequestBody parametros param,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          resp = BancoDB.createParam(param,"BANCO");
        return resp;
    }
	
	
	
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/Bancos/getBancos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<parametros> getBancos( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<parametros> params = new ArrayList<parametros>();
		params = BancoDB.getBancos();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Bancos/getBancoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody parametros getBancoById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		parametros params = new parametros();
		params = BancoDB.getBancoById(""+id);
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Bancos/updateBanco/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateBanco(@RequestBody parametros param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 param.setCodigo("BANCO");
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = BancoDB.updateParam(param);
        return resp;
	}
	@RequestMapping(value = "/work/Bancos/deleteBanco/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteBanco(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return BancoDB.deleteBancoById(""+id);

	}
}
