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

import lib.classSW.GrupoINE;
import lib.db.sw.GrupoINEDB;
import lib.security.session;

@Controller
public class GrupoINEJSON {
	@RequestMapping(value = "/work/GruposINE/createGrupoINE/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createGrupoINE(@RequestBody GrupoINE g,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = GrupoINEDB.createGrupoINE(g);
        return resp;
    }
	//Obtener Todas las GruposINE
    @RequestMapping(value = "/work/GruposINE/getGruposINE/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<GrupoINE> getGruposINE( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<GrupoINE> params = new ArrayList<GrupoINE>();
		params = GrupoINEDB.getGruposINE();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
	@RequestMapping(value = "/work/GruposINE/getGrupoINEById/{id}", method = {RequestMethod.GET})
	public @ResponseBody GrupoINE getGrupoINEById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		GrupoINE e = new GrupoINE();
		e = GrupoINEDB.getGrupoINEById(id);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	@RequestMapping(value = "/work/GruposINE/getGrupoINEBySociedad/{id}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<GrupoINE> getGrupoINEBySociedad(@PathVariable int soc , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<GrupoINE> e = new ArrayList<GrupoINE>();
		e = GrupoINEDB.getGruposINEBySociedad(soc);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	@RequestMapping(value = "/work/GruposINE/updateGrupoINE/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateGrupoINE(@RequestBody GrupoINE e,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    
    	 
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		
		resp = GrupoINEDB.updateGrupoINE(e);
        return resp;
	}
	@RequestMapping(value = "/work/GruposINE/deleteGrupoINEById/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteGrupoINEById(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return GrupoINEDB.deleteGrupoINEById(id);

	}
	@RequestMapping(value = "/work/GruposINE/deleteGrupoINEBySociedad/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteGrupoINEBySociedad(@PathVariable int soc ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return GrupoINEDB.deleteGrupoINEBySociedad(soc);

	}
}
