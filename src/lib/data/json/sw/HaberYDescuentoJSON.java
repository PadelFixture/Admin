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

import lib.classSW.HaberDescuento;
import lib.db.sw.HaberDescuentoDB;
import lib.security.session;

@Controller
public class HaberYDescuentoJSON {
	
	@RequestMapping(value = "/work/HaberesDescuentos/getMaxCodes/" , method= {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody int[] getMaxCodes(HttpSession httpSession) throws Exception 
    {
          int[] resp=new int[3];
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return resp;
          }     
          resp = HaberDescuentoDB.getMaxCodes();
        return resp;
    }
	
	@RequestMapping(value = "/work/HaberesDescuentos/createHaberDescuento/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createHaberDescuento(@RequestBody HaberDescuento hd,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = HaberDescuentoDB.createHaberDescuento(hd);
        return resp;
    }
	@RequestMapping(value = "/work/HaberesDescuentos/existCodigo/{codigo}" , method= {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean existCodigo(@PathVariable int codigo,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = HaberDescuentoDB.existCodigo(codigo);
        return resp;
    }
	
	
	
	
	//Obtener Todas las HaberesDescuentos
    @RequestMapping(value = "/work/HaberesDescuentos/getHaberesDescuentos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<HaberDescuento> getHaberesDescuentos( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<HaberDescuento> params = new ArrayList<HaberDescuento>();
		params = HaberDescuentoDB.getHaberesDescuentos();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
    @RequestMapping(value = "/work/HaberesDescuentos/getCodigos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<HaberDescuento> getCodigos( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<HaberDescuento> params = new ArrayList<HaberDescuento>();
		params = HaberDescuentoDB.getCodigos();
		if (ses.isValid()) {
			return params;
		}		
		return params;

	}
    
    
    
	@RequestMapping(value = "/work/HaberesDescuentos/getHaberDescuentoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody HaberDescuento getHaberDescuentoById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		HaberDescuento e = new HaberDescuento();
		e = HaberDescuentoDB.getHaberDescuentoById(id);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	@RequestMapping(value = "/work/HaberesDescuentos/getHaberDescuentoBySociedad/{id}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<HaberDescuento> getHaberDescuentoBySociedad(@PathVariable String soc , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<HaberDescuento> e = new ArrayList<HaberDescuento>();
		e = HaberDescuentoDB.getHaberDescuentoBySociedad(soc);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	@RequestMapping(value = "/work/HaberesDescuentos/updateHaberDescuento/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateHaberDescuento(@RequestBody HaberDescuento hd,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    
    	 
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		
		resp = HaberDescuentoDB.updateHaberDescuento(hd);
        return resp;
	}
	@RequestMapping(value = "/work/HaberesDescuentos/deleteHaberDescuentoById/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteHaberDescuentoById(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return HaberDescuentoDB.deleteHaberDescuentoById(id);

	}
	@RequestMapping(value = "/work/HaberesDescuentos/deleteHaberDescuentoBySociedad/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteHaberDescuentoBySociedad(@PathVariable String soc ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return HaberDescuentoDB.deleteHaberDescuentoBySociedad(soc);

	}
}
