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

import lib.classSW.Faena;
import lib.db.sw.FaenaDB;
import lib.security.session;

@Controller
public class FaenaJSON {
	@RequestMapping(value = "/work/Faenas/createFaena/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createFaena(@RequestBody Faena e,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = FaenaDB.createFaena(e);
        return resp;
    }
	//Obtener Todas las Faenas
    @RequestMapping(value = "/work/Faenas/getFaenas/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Faena> getFaenas( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<Faena> params = new ArrayList<Faena>();
		params = FaenaDB.getFaenas();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/Faenas/getFaenaById/{id}", method = {RequestMethod.GET})
	public @ResponseBody Faena getFaenaById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		Faena e = new Faena();
		e = FaenaDB.getFaenaById(id);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	@RequestMapping(value = "/work/Faenas/getFaenaBySociedad/{soc}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<Faena> getFaenaBySociedad(@PathVariable String soc , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<Faena> e = new ArrayList<Faena>();
		e = FaenaDB.getFaenaBySociedad(soc);
		if (ses.isValid()) {
			return e;
		}		
		return e;
	}
	
	@RequestMapping(value = "/work/Faenas/updateFaena/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateFaena(@RequestBody Faena e,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    
    	 
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		
		resp = FaenaDB.updateFaena(e);
        return resp;
	}
	@RequestMapping(value = "/work/Faenas/deleteFaenaById/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteFaenaById(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return FaenaDB.deleteFaenaById(id);

	}
	
}
