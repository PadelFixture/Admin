package lib.data.json.sw;


import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import lib.classSW.Supervisor;

import lib.db.sw.SupervisoresDB;

import lib.security.session;

@Controller
public class SupervisoresJSON {
	// Insert Sociedad
		@RequestMapping(value = "/work/Supervisores/crearSupervisor/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean crearSupervisor(@RequestBody Supervisor s, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}
			
			return SupervisoresDB.crearSupervisor(s);
			
		}

		@RequestMapping(value = "/work/Supervisores/obtenerSupervisorPorId/{id}", method = { RequestMethod.GET })
		public @ResponseBody Supervisor obtenerSupervisorPorId(@PathVariable int id, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			Supervisor s=new Supervisor();
			if (ses.isValid()) {
				return s;
			}
				s=SupervisoresDB.obtenerSupervisorPorId(id);
				return s;
		}

		// Obtener Todas las sociedades
		@RequestMapping(value = "/work/Supervisores/obtenerSupervisorPorCodigo/{codigo}", method = { RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE )
		public @ResponseBody Supervisor obtenerSupervisorPorCodigo(@PathVariable String id,HttpServletRequest request, HttpSession httpSession) throws Exception {
			session ses = new session(httpSession);
			Supervisor s=new Supervisor();
			if (ses.isValid()) {
				return s;
			}
			s=SupervisoresDB.obtenerSupervisorPorCodigo(id);
			return s;
		}

		// Obtener Todas las sociedad con Filtros
		@RequestMapping(value = "/work/Supervisores/obtenerSupervisores", method = { RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE )
		public @ResponseBody ArrayList<Supervisor> obtenerSupervisores(HttpServletRequest request, HttpSession httpSession) {
			session ses = new session(httpSession);
			ArrayList<Supervisor> ss=new ArrayList<Supervisor>();
			if (ses.isValid()) {
				return ss;
			}
			ss=SupervisoresDB.ObtenerSupervisores();
			return ss;
			
		}
		
		
		
		
		
		
		
}
