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

import lib.classSA.Estados;
import lib.classSW.Tbjdr;
import lib.classSW.Turno;
import lib.db.sw.TurnoDB;
import lib.security.session;

@Controller
public class TurnoJSON {
	  //Crear Turno
	@RequestMapping(value = "/work/turnos/createTurnoSimple/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean createTurnoSimple(@RequestBody Turno t,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = TurnoDB.createTurnoSimple(t);
        return resp;
    }
	
	
	
	
	@RequestMapping(value = "/work/turnos/createTurno/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean insertTurno(@RequestBody Turno t,HttpSession httpSession) throws Exception 
    {
          boolean resp = false;
         session ses= new session(httpSession);
          if (ses.isValid()) {        	  
                 return false;
          }     
          resp = TurnoDB.createTurno(t);
        return resp;
    }
	@RequestMapping(value = "/work/turnos/updateTurno/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateTurno(@RequestBody Turno t,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = TurnoDB.updateTurno(t);
        return resp;
	}
	@RequestMapping(value = "/work/turnos/updateTurnoSimple/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateTurnoSimple(@RequestBody Turno t,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = TurnoDB.updateTurnoSimple(t);
        return resp;
	}
    //Obtener AFP por Id
    @RequestMapping(value = "/work/turnos/getTurnoSimpleById/{id}", method = {RequestMethod.GET})
		public @ResponseBody Turno getTurnoById(@PathVariable("id") int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			
			Turno t = TurnoDB.getTurnoSimpleById(id);
			if (ses.isValid()) {
				return t;
				}			
			return t;
		}
    //Obtener AFP por Id
    @RequestMapping(value = "/work/turnos/getBlankTurno/", method = {RequestMethod.GET})
		public @ResponseBody Turno getBlankAFP(HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			Turno t = TurnoDB.getBlankTurno();
			
			if (ses.isValid()) {
				return t;
				}			
			return t;
		}
  //Obtener Todas las AFP
    @RequestMapping(value = "/work/turnos/getTurnos/", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Turno> getTurnos( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<Turno> ts = new ArrayList<Turno>();
		ts = TurnoDB.getAllTurnos();
		if (ses.isValid()) {
			return ts;
		}		
		return ts;
	}
    @RequestMapping(value = "/work/turnos/getTurnosSimple/", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody ArrayList<Turno> getTurnosSimple( HttpSession httpSession) throws Exception {
       	
   		session ses = new session(httpSession);
   		ArrayList<Turno> ts = new ArrayList<Turno>();
   		ts = TurnoDB.getAllTurnosSimple();
   		if (ses.isValid()) {
   			return ts;
   		}		
   		return ts;
   	}
    @RequestMapping(value = "/work/turnos/getAllTurnosFiltered/{Sociedad}/{Huerto}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody ArrayList<Turno> getAllTurnosFiltered(@PathVariable("Sociedad") String Sociedad,@PathVariable("Huerto") String Huerto, HttpSession httpSession) throws Exception {
       	
   		session ses = new session(httpSession);
   		ArrayList<Turno> ts = new ArrayList<Turno>();
   		ts = TurnoDB.getAllTurnosFiltered(Sociedad, Huerto);
   		if (ses.isValid()) {
   			return ts;
   		}		
   		return ts;
   	}
    @RequestMapping(value = "/work/turnos/deleteTurno/{id}", method = {RequestMethod.PUT})
	public @ResponseBody boolean deleteAFP(@PathVariable int id ,HttpSession httpSession) throws Exception {
						
		session ses = new session(httpSession);
						
		if (ses.isValid()) {
			return false;
		}
		return TurnoDB.deleteTurno(id);

	}
    @RequestMapping(value = "/work/turnos/updateTurnoTrabajadores/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateTurnoTrabajadores(@RequestBody ArrayList<Estados> a,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = TurnoDB.updateTurnoTrabajadores(a);
        return resp;
	}
    @RequestMapping(value = "/work/turnos/getTrabajadoresByTurno/{idTurno}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody ArrayList<Tbjdr> getTrabajadoresByTurno(@PathVariable("idTurno") int idTurno, HttpSession httpSession) throws Exception {
       	
   		session ses = new session(httpSession);
   		ArrayList<Tbjdr> ts = new ArrayList<Tbjdr>();
   		ts = TurnoDB.getTrabajadoresByTurno(idTurno);
   		if (ses.isValid()) {
   			return ts;
   		}		
   		return ts;
   	}
    @RequestMapping(value = "/work/turnos/getTrabajadoresWithoutFilter/", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody ArrayList<Tbjdr> getTrabajadoresWithoutFilter( HttpSession httpSession) throws Exception {
       	
   		session ses = new session(httpSession);
   		ArrayList<Tbjdr> ts = new ArrayList<Tbjdr>();
   		ts = TurnoDB.getTrabajadoresWithoutFilter();
   		if (ses.isValid()) {
   			return ts;
   		}		
   		return ts;
   	}
    @RequestMapping(value = "/work/turnos/getTrabajadoresBy/{Sociedad}/{Huerto}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody ArrayList<Tbjdr> getTrabajadoresWithoutFilter(@PathVariable("Sociedad") String Sociedad,@PathVariable("Huerto") String Huerto, HttpSession httpSession) throws Exception {
       	
   		session ses = new session(httpSession);
   		ArrayList<Tbjdr> ts = new ArrayList<Tbjdr>();
   		ts = TurnoDB.getTrabajadoresBy(Sociedad, Huerto);
   		if (ses.isValid()) {
   			return ts;
   		}		
   		return ts;
   	}
    
}
