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

import lib.classSW.EvMut;
import lib.classSW.LoadTrabajadorSociedad;
import lib.classSW.trabajadores;
import lib.db.sw.EvMutDB;
import lib.security.session;

@Controller
public class EvMutJSON {
	   //Crear AFP
    @RequestMapping(value = "/work/eventos/createEvMut/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createEventoMutualidad(@RequestBody EvMut evento,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = EvMutDB.createEvMut(evento);
        return resp;
    }
    @RequestMapping(value = "/work/eventos/updateEvMut/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateEventoMutualidad(@RequestBody EvMut evento,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = EvMutDB.updateEvMut(evento);
        return resp;
	}
    //Obtener AFP por Id
    @RequestMapping(value = "/work/eventos/readEvMut/{id}", method = {RequestMethod.GET})
		public @ResponseBody EvMut readEventoMutualidad(@PathVariable("id") int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			EvMut evento = EvMutDB.getEvMutById(id);
			if (ses.isValid()) {
				return evento;
				}			
			return evento;

		}
    @RequestMapping(value = "/work/eventos/readDataByLicenseId/{id}", method = {RequestMethod.GET})
		public @ResponseBody EvMut getDatosByRut(@PathVariable("id") int id, HttpSession httpSession) throws Exception {
    		session ses = new session(httpSession);
			EvMut evento=new EvMut();
			if (ses.isValid()) {
				return evento;
				}		
			evento = EvMutDB.getDataByidLicencia(id);
			return evento;

		}
    @RequestMapping(value = "/work/eventos/readDataWithLicenses/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<EvMut> getDataForLicenses( HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<EvMut> eventos=new ArrayList<EvMut>();
		if (ses.isValid()) {
			return eventos;
			}		
		eventos = EvMutDB.getDataForLicenses();
		return eventos;

	}
        
    
    
    
    //Obtener AFP por Id
    @RequestMapping(value = "/work/eventos/getBlankEvMut/", method = {RequestMethod.GET})
		public @ResponseBody EvMut getBlankEventoMutualidad(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			EvMut evento = EvMutDB.getBlankEvMut();
			
			if (ses.isValid()) {
				return evento;
				}			
			return evento;

		}
    
    @RequestMapping(value = "/work/eventos/getEvMuts/{sociedad_},{huerto_},{zona_},{ceco_},{mes_},{estado_},{codTrab_},{rolPrivado}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<EvMut> 
    getEventosMutualidad( 
    						@PathVariable String sociedad_,
    						@PathVariable String huerto_,
    						@PathVariable String zona_,
    						@PathVariable String ceco_,
    						@PathVariable String mes_,
    						@PathVariable String estado_,
    						@PathVariable String codTrab_,
    						@PathVariable String rolPrivado,
    						HttpSession httpSession
    					 ) throws Exception 
    {
    	
		session ses = new session(httpSession);
		ArrayList<EvMut> eventos = new ArrayList<EvMut>();
		
		if (ses.isValid()) {
			return eventos;
		}		
		eventos = EvMutDB.getAllEvMut(sociedad_,huerto_,zona_,ceco_,mes_,estado_,codTrab_,rolPrivado);
		return eventos;

	}
    @RequestMapping(value = "/work/eventos/deleteEvMut/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteEventoMutualidadById(@PathVariable("id") int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
						
		return EvMutDB.deleteEvMutById(id);

	}
    @RequestMapping(value = "/work/eventos/getTrabajadoresById/{id}", method = {RequestMethod.GET})
	public @ResponseBody trabajadores getTrabajadorById(@PathVariable("id") int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
		trabajadores t= new trabajadores();				
		if (ses.isValid()) {
			return t;
		}
						
		return EvMutDB.getTrabajadorById(id);

	}
    
    @RequestMapping(value = "/work/LoadTrabajadorXSociedadEventoMutualidad/{idSociedad},{huerto},{zona},{ceco},{estado_proceso},{rolPrivado}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadTrabajadorSociedad> getSociedadTrabListadoEVM(
			@PathVariable String idSociedad,
			@PathVariable String huerto,
			@PathVariable String zona,
			@PathVariable String ceco,
			@PathVariable String estado_proceso,
			@PathVariable int rolPrivado,
			HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadTrabajadorSociedad> r = new ArrayList<LoadTrabajadorSociedad>();

		if (ses.isValid()) {
			return r;
		}
		r = EvMutDB.getSociedadTrabListadoEVM(idSociedad,huerto,zona,ceco,estado_proceso,rolPrivado);

		return r;

	}
    
    
   
    
    
    @RequestMapping(value = "/work/eventos/getEstadoProceso/{soc}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<trabajadores> getEstadoproceso(
			@PathVariable int soc,
			HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		ArrayList<trabajadores> r = new ArrayList<trabajadores>();

		if (ses.isValid()) {
			return r;
		}
		r = EvMutDB.getEstadoproceso(soc);

		return r;

	}
	
	  @RequestMapping(value = "/work/eventos/getTrabajadoresEventoMutual/", method = { RequestMethod.GET,
				RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<trabajadores> getTrabajadoresMutual(
				HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			ArrayList<trabajadores> r = new ArrayList<trabajadores>();

			if (ses.isValid()) {
				return r;
			}
			r = EvMutDB.getTrabajadoresMutual();

			return r;

		}
		
		 @RequestMapping(value = "/work/eventos/getCargoTrabajadorMutual/{cod}", method = { RequestMethod.GET,
					RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
			public @ResponseBody ArrayList<trabajadores> getTrabajadorCargoMutual(
					@PathVariable int cod,HttpSession httpSession)
					throws Exception {

				session ses = new session(httpSession);

				ArrayList<trabajadores> r = new ArrayList<trabajadores>();

				if (ses.isValid()) {
					return r;
				}
				r = EvMutDB.getTrabajadorCargoMutual(cod);

				return r;

			}
    
 
}
