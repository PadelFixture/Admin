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


import lib.classSW.TramoCarga;

import lib.db.sw.TramoCargaDB;
import lib.security.session;

@Controller
public class TramoCargaJSON {
	 //Crear Documento
    @RequestMapping(value = "/work/tramo/createTramo/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean insertTramoCarga(@RequestBody TramoCarga tramo,HttpSession httpSession) throws Exception 
    {
   
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {
        	  
                 return false;
          }     
          resp = TramoCargaDB.createTramo(tramo);
          
        return resp;
           
          
    }

 @RequestMapping(value = "/work/tramo/updateTramo/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
 public @ResponseBody boolean updateTramoCarga(@RequestBody TramoCarga tramocarga,HttpSession httpSession) throws Exception {
 	 boolean resp = false;
 	 	
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = TramoCargaDB.updateTramoCarga(tramocarga);
     return resp;
	}
 @RequestMapping(value = "/work/tramo/updateTramosByPeriodo/{periodo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
 public @ResponseBody boolean updateTramosByPeriodo(@PathVariable("periodo") int periodo,@RequestBody ArrayList<TramoCarga> tramocarga,HttpSession httpSession) throws Exception {
 	 boolean resp = false;
 	 	
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = TramoCargaDB.updateTramoCargaMasivo(tramocarga, periodo);
     return resp;
	}
 //Obtener AFP por Id
 @RequestMapping(value = "/work/tramo/readTramo/{id}", method = {RequestMethod.GET})
		public @ResponseBody TramoCarga readTramoCarga(@PathVariable("id") int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			TramoCarga tramocarga = TramoCargaDB.getTramoCargaById(id);
			if (ses.isValid()) {
				return tramocarga;
				}			
			return tramocarga;
		}
 //Obtener AFP por Id
 @RequestMapping(value = "/work/tramo/getBlankTramo/", method = {RequestMethod.GET})
		public @ResponseBody TramoCarga getBlankTramo(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			TramoCarga tramocarga = TramoCargaDB.getBlankTramo();
			
			if (ses.isValid()) {
				return tramocarga;
				}			
			return tramocarga;
		}
//Obtener Todas las AFP
 @RequestMapping(value = "/work/tramo/getTramos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<TramoCarga> getTramos( HttpSession httpSession) throws Exception {
 	
	 System.out.println("pase read");
		session ses = new session(httpSession);
		ArrayList<TramoCarga> tramos = new ArrayList<TramoCarga>();
		
		if (ses.isValid()) {
			return tramos;
		}		
		tramos = TramoCargaDB.getAllTramos();
		System.out.println("termine read");
		return tramos;

	}
 @RequestMapping(value = "/work/tramo/getTramosByLastPeriod/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<TramoCarga> getTramosByLastPeriod( HttpSession httpSession) throws Exception {
 	
	 System.out.println("pase read");
		session ses = new session(httpSession);
		ArrayList<TramoCarga> tramos = new ArrayList<TramoCarga>();
		
		if (ses.isValid()) {
			return tramos;
		}		
		tramos = TramoCargaDB.getTramosByLastPeriod();
		System.out.println("termine read");
		return tramos;

	}
 @RequestMapping(value = "/work/tramo/getPeriodos/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<TramoCarga> getPeriodos( HttpSession httpSession) throws Exception {
	
	 
		session ses = new session(httpSession);
		ArrayList<TramoCarga> tramos = new ArrayList<TramoCarga>();
		
		if (ses.isValid()) {
			return tramos;
		}		
		tramos = TramoCargaDB.getPeriodos();
		
		return tramos;

	}
 @RequestMapping(value = "/work/tramo/getTramosByPeriodo/{periodo}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<TramoCarga> getTramosByPeriodo(@PathVariable("periodo") int periodo, HttpSession httpSession) throws Exception {
	
	
		session ses = new session(httpSession);
		ArrayList<TramoCarga> tramos = new ArrayList<TramoCarga>();
		
		if (ses.isValid()) {
			return tramos;
		}		
		tramos = TramoCargaDB.getTramosByPeriodo(periodo);
		
		return tramos;

	}
 @RequestMapping(value = "/work/tramo/deleteTramo/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteTramo(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return TramoCargaDB.deleteTramoById(id);

	}
}
