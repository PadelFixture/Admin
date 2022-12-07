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

import lib.classSW.trabajadorReclutamiento;
import lib.db.sw.trabajadorReclutamientoDB;
import lib.security.session;

@Controller
public class TrabajadorReclutamientoJSON {
	//Insert trabajadorReclutamiento
		@RequestMapping(value = "/work/insertTrabajadorReclutamiento/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertTrabajadorReclutamiento(@RequestBody trabajadorReclutamiento trabajadorReclutamiento, HttpSession httpSession) throws Exception{

			session ses = new session(httpSession);
			if(ses.isValid()){
				return false;
			}

			return trabajadorReclutamientoDB.insertTrabajadorReclutamiento(trabajadorReclutamiento);
		}
		
	//UPDATE trabajadorReclutamiento
		@RequestMapping(value = "/work/updateTrabajadorReclutamiento/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateTrabajadorReclutamiento(@RequestBody trabajadorReclutamiento trabajadorReclutamiento,HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return trabajadorReclutamientoDB.updateTrabajadorReclutamiento(trabajadorReclutamiento);
		}
		
	//Obtener trabajadorReclutamiento por IdTrabajadorReclutamiento
		@RequestMapping(value = "/work/getTrabReclutByIdTrabReclu/{id}", method = {RequestMethod.GET})
		public @ResponseBody trabajadorReclutamiento getTrabReclutByIdTrabReclu(@PathVariable String id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			trabajadorReclutamiento trabajadorReclutamiento = new trabajadorReclutamiento();

			if (ses.isValid()) {
				return trabajadorReclutamiento;
			}

			trabajadorReclutamiento = trabajadorReclutamientoDB.getTrabajadorReclutamuentoByIdTrabajadorReclutamiento(id);
			return trabajadorReclutamiento;

		}
		
	//Obtener trabajadorReclutamiento por IdTrabajador
		@RequestMapping(value = "/work/getTrabajadorReclutamientoByIdTrabajador/{id}", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<trabajadorReclutamiento> getTrabajadorReclutamientoByIdTrabajador(@PathVariable String id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<trabajadorReclutamiento> trabajadorReclutamiento = new ArrayList<trabajadorReclutamiento>();

			if (ses.isValid()) {
				return trabajadorReclutamiento;
			}

			trabajadorReclutamiento = trabajadorReclutamientoDB.getTrabajadorReclutamuentoByIdTrabajador(id);
			return trabajadorReclutamiento;

		}
	
	
	
}
