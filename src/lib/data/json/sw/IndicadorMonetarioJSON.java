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

import lib.classSW.IndicadorMonetario;

import lib.db.sw.IndicadorMonetarioDB;
import lib.security.session;

@Controller
public class IndicadorMonetarioJSON {
	@RequestMapping(value = "/work/indicadoresMonetarios/createIndicador/" , method= {RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createIndicador(@RequestBody IndicadorMonetario indicador,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }
          
          resp = IndicadorMonetarioDB.createIndicador(indicador);
        return resp;
    }
	 @RequestMapping(value = "/work/indicadoresMonetarios/updateIndicador/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody boolean updateIndicador(@RequestBody IndicadorMonetario indicador,HttpSession httpSession) throws Exception {
	    	 boolean resp = false;
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			resp = IndicadorMonetarioDB.updateIndicador(indicador);
	        return resp;
		}
	    //Obtener AFP por Id
	    @RequestMapping(value = "/work/indicadoresMonetarios/getIndicadorById/{id}", method = {RequestMethod.GET})
			public @ResponseBody IndicadorMonetario getIndicadorById(@PathVariable("id") int id, HttpSession httpSession) throws Exception {

				session ses = new session(httpSession);
				
				IndicadorMonetario indicador = IndicadorMonetarioDB.getIndicadorById(id);
				if (ses.isValid()) {
					return indicador;
					}			
				return indicador;

			}

	  //Obtener Todas las solicitudes
	    @RequestMapping(value = "/work/indicadoresMonetarios/getIndicadores/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<IndicadorMonetario> getIndicadores( HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			ArrayList<IndicadorMonetario> indicadores = new ArrayList<IndicadorMonetario>();
			indicadores = IndicadorMonetarioDB.getIndicadores();
			if (ses.isValid()) {
				return indicadores;
			}		
			return indicadores;

		}
	    @RequestMapping(value = "/work/indicadoresMonetarios/getIndicadoresByType/{tipo}", method = {RequestMethod.GET})
	  		public @ResponseBody ArrayList<IndicadorMonetario> getIndicadoresByType(@PathVariable("tipo") int tipo, HttpSession httpSession) throws Exception {
	  	    	
	  			session ses = new session(httpSession);
	  			ArrayList<IndicadorMonetario> indicadores = new ArrayList<IndicadorMonetario>();
	  			indicadores = IndicadorMonetarioDB.getIndicadoresByType(tipo);
	  			if (ses.isValid()) {
	  				return indicadores;
	  			}		
	  			return indicadores;

	  		}
	    //Obtener Todas las solicitudes
	    @RequestMapping(value = "/work/indicadoresMonetarios/getPeriodos/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<IndicadorMonetario> getPeriodos( HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			ArrayList<IndicadorMonetario> indicadores = new ArrayList<IndicadorMonetario>();
			indicadores = IndicadorMonetarioDB.getPeriodos();
			if (ses.isValid()) {
				return indicadores;
			}		
			return indicadores;

		}
	    @RequestMapping(value = "/work/indicadoresMonetarios/getPeriodosByTipo/{tipo}", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<IndicadorMonetario> getPeriodosByTipo(@PathVariable("tipo") int tipo, HttpSession httpSession) throws Exception {
	    	
			session ses = new session(httpSession);
			ArrayList<IndicadorMonetario> indicadores = new ArrayList<IndicadorMonetario>();
			indicadores = IndicadorMonetarioDB.getPeriodosByTipo(tipo);
			if (ses.isValid()) {
				return indicadores;
			}		
			return indicadores;

		}
	    @RequestMapping(value = "/work/indicadoresMonetarios/deleteIndicador/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteIndicador(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return IndicadorMonetarioDB.deleteIndicadorById(id);

		}
	   
	    
}
