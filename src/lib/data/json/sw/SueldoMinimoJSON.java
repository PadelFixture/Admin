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

import lib.classSW.SueldoMinimoMasivo;
import lib.db.sw.SueldoMinimoMasivoDB;
import lib.security.session;

@Controller
public class SueldoMinimoJSON {
	@RequestMapping(value = "/work/SueldoMinimoMasivo/createSueldoMinimoMasivo/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createSueldoMinimoMasivo(@RequestBody SueldoMinimoMasivo sueldo,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          resp = SueldoMinimoMasivoDB.createSueldoMinimoMasivo(sueldo);
        return resp;
    }
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/SueldoMinimoMasivo/getSueldosMinimosMasivos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<SueldoMinimoMasivo> getSueldoMinimoMasivos( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<SueldoMinimoMasivo> params = new ArrayList<SueldoMinimoMasivo>();
		params = SueldoMinimoMasivoDB.getSueldosMinimosMasivos();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/SueldoMinimoMasivo/getSueldoMinimoMasivoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody SueldoMinimoMasivo getSueldoMinimoMasivoById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		SueldoMinimoMasivo params = new SueldoMinimoMasivo();
		params = SueldoMinimoMasivoDB.getSueldoMinimoMasivoById(id);
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/SueldoMinimoMasivo/getLastSueldoMinimoMasivo/", method = {RequestMethod.GET})
	public @ResponseBody SueldoMinimoMasivo getLastSueldoMinimoMasivoById( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		SueldoMinimoMasivo params = new SueldoMinimoMasivo();
		params = SueldoMinimoMasivoDB.getLastSueldoMinimoMasivoById();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	
	@RequestMapping(value = "/work/SueldoMinimoMasivo/updateSueldoMinimoMasivo/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateSueldoMinimoMasivo(@RequestBody SueldoMinimoMasivo param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		resp = SueldoMinimoMasivoDB.updateSueldoMinimoMasivo(param);
        return resp;
	}
	@RequestMapping(value = "/work/SueldoMinimoMasivo/updateSueldos/{sueldobase}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateSueldos(@RequestBody ArrayList<SueldoMinimoMasivo> param,@PathVariable int sueldobase,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		resp = SueldoMinimoMasivoDB.updateSueldos(param, sueldobase);
        return resp;
	}
	
	
	
	@RequestMapping(value = "/work/SueldoMinimoMasivo/deleteSueldoMinimoMasivo/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteBanco(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return SueldoMinimoMasivoDB.deleteSueldoMinimoMasivoById(id);

	}
	@RequestMapping(value = "/work/SueldoMinimoMasivo/getSueldosMinimosByContract/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<SueldoMinimoMasivo> getSueldosMinimosByContract( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<SueldoMinimoMasivo> params = new ArrayList<SueldoMinimoMasivo>();
		params = SueldoMinimoMasivoDB.getSueldosMinimosByContract();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/SueldoMinimoMasivo/updateSueldoMinimoMasivoContract/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateSueldoMinimoMasivoContract(@RequestBody SueldoMinimoMasivo param,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		resp = SueldoMinimoMasivoDB.updateSueldoMinimoMasivoContract(param);
        return resp;
	}
}
