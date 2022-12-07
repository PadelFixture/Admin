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


import lib.classSW.ImpuestoUnico;
import lib.db.sw.ImpuestoUnicoDB;
import lib.security.session;
@Controller
public class ImpuestoUnicoJSON {
	@RequestMapping(value = "/work/ImpuestoUnico/createImpuestoUnico/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createImpuestoUnico(@RequestBody ImpuestoUnico impuesto,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          resp = ImpuestoUnicoDB.createImpuestoUnico(impuesto);
        return resp;
    }
	
	
	
	//Obtener Todas las Empresas
    @RequestMapping(value = "/work/ImpuestoUnico/getImpuestosUnicos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<ImpuestoUnico> getImpuestosUnicos( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<ImpuestoUnico> impuestos = new ArrayList<ImpuestoUnico>();
		impuestos = ImpuestoUnicoDB.getImpuestosUnicos();
		if (ses.isValid()) {
			return impuestos;
		}		
		return impuestos;

	}
    @RequestMapping(value = "/work/ImpuestoUnico/getPeriodos/", method = {RequestMethod.GET})
  	public @ResponseBody ArrayList<ImpuestoUnico> getPeriodos( HttpSession httpSession) throws Exception {
      	
  		session ses = new session(httpSession);
  		ArrayList<ImpuestoUnico> impuestos = new ArrayList<ImpuestoUnico>();
  		impuestos = ImpuestoUnicoDB.getPeriodos();
  		if (ses.isValid()) {
  			return impuestos;
  		}		
  		return impuestos;

  	}
    @RequestMapping(value = "/work/ImpuestoUnico/getPeriodo/{periodo}", method = {RequestMethod.GET})
  	public @ResponseBody ArrayList<ImpuestoUnico> getPeriodo(@PathVariable String periodo, HttpSession httpSession) throws Exception {
      	
  		session ses = new session(httpSession);
  		ArrayList<ImpuestoUnico> impuestos = new ArrayList<ImpuestoUnico>();
  		impuestos = ImpuestoUnicoDB.getPeriodo(periodo);
  		if (ses.isValid()) {
  			return impuestos;
  		}		
  		return impuestos;

  	}
    @RequestMapping(value = "/work/ImpuestoUnico/getLastPeriodo/", method = {RequestMethod.GET})
  	public @ResponseBody ArrayList<ImpuestoUnico> getLastPeriodo(HttpSession httpSession) throws Exception {
      	
  		session ses = new session(httpSession);
  		ArrayList<ImpuestoUnico> impuestos = new ArrayList<ImpuestoUnico>();
  		impuestos = ImpuestoUnicoDB.getLastPeriodo();
  		if (ses.isValid()) {
  			return impuestos;
  		}		
  		return impuestos;

  	}
    
    
    
    
    @RequestMapping(value = "/work/ImpuestoUnico/getImpuestoByLastPeriod/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<ImpuestoUnico> getImpuestoByLastPeriod( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<ImpuestoUnico> impuestos = new ArrayList<ImpuestoUnico>();
		impuestos = ImpuestoUnicoDB.getImpuestoByLastPeriod();
		if (ses.isValid()) {
			return impuestos;
		}		
		return impuestos;

	}
    @RequestMapping(value = "/work/ImpuestoUnico/getImpuestosUnicosByPeriod/{periodo}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<ImpuestoUnico> getImpuestosUnicosByPeriodo(@PathVariable String periodo, HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<ImpuestoUnico> impuestos = new ArrayList<ImpuestoUnico>();
		impuestos = ImpuestoUnicoDB.getImpuestosUnicosByPeriodo(periodo);
		if (ses.isValid()) {
			return impuestos;
		}		
		return impuestos;

	}
	@RequestMapping(value = "/work/ImpuestoUnico/getImpuestoUnicoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody ImpuestoUnico getImpuestoUnicoById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ImpuestoUnico impuesto = new ImpuestoUnico();
		impuesto = ImpuestoUnicoDB.getImpuestoUnicoById(id);
				if (ses.isValid()) {
			return impuesto;
		}		
		return impuesto;

	}
	@RequestMapping(value = "/work/ImpuestoUnico/updateImpuestoUnicoById/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateImpuestoUnicoById(@RequestBody ImpuestoUnico impuesto,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    	 
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = ImpuestoUnicoDB.updateImpuestoUnicoById(impuesto);
        return resp;
	}
	
	
	
	
	 @RequestMapping(value = "/work/ImpuestoUnico/updateImpuestosUnicosByPeriodo/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody boolean updateImpuestosUnicosByPeriodo(@RequestBody ArrayList<ImpuestoUnico> impuestos,HttpSession httpSession) throws Exception {
	 	 boolean resp = false;
	 	 	
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			
			resp = ImpuestoUnicoDB.updateImpuestoUnicoMasivo(impuestos);
	     return resp;
		}
	@RequestMapping(value = "/work/ImpuestoUnico/deleteImpuestoUnicoById/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteImpuestoUnicoById(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}				
		return ImpuestoUnicoDB.deleteImpuestoUnicoById(id);
	}
}
