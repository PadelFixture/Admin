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

import lib.classSW.TrabajadorLicenciaAplicador;
import lib.db.sw.TrabajadorLicenciaAplicadorDB;
import lib.security.session;

@Controller
public class TrabajadorLicenciaAplicadorJSON {

	
	//insert trabajador licencia conducir
		@RequestMapping(value = "/work/insertTrabajadorLicenciaAplicador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertTrabajadorLicenciaAplicador(@RequestBody TrabajadorLicenciaAplicador TrabajadorLicenciaAplicador, HttpSession httpSession)
				throws Exception{

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}

			return TrabajadorLicenciaAplicadorDB.insertTrabajadorLicenciaAplicador(TrabajadorLicenciaAplicador);
		} // fin insertar
	
	 
      // get licencia conducir
		@RequestMapping(value = "/work/getTrabajadorLicenciaAplicador/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<TrabajadorLicenciaAplicador> getTrabajadorLicenciaAplicador( HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<TrabajadorLicenciaAplicador> TrabajadorLicenciaAplicador = new ArrayList<TrabajadorLicenciaAplicador>();

			if (ses.isValid()) {
				return TrabajadorLicenciaAplicador;
			}

			TrabajadorLicenciaAplicador = TrabajadorLicenciaAplicadorDB.getTrabajadorLicenciaAplicador();
			return TrabajadorLicenciaAplicador;

		}// fin get
		 // get licencia conducir con id
		@RequestMapping(value = "/work/getTrabajadorLicenciaAplicadorByIdTrabajador/{id}", method = {RequestMethod.GET})
		public @ResponseBody TrabajadorLicenciaAplicador getTrabajadorLicenciaAplicadorByIdTrabajador(@PathVariable int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			TrabajadorLicenciaAplicador TrabajadorLicenciaAplicadorByIdTrabajador = new TrabajadorLicenciaAplicador();

			if (ses.isValid()) {
				return TrabajadorLicenciaAplicadorByIdTrabajador;
			}

			TrabajadorLicenciaAplicadorByIdTrabajador = TrabajadorLicenciaAplicadorDB.getTrabajadorLicenciaAplicadorByIdTrabajador(id);
			return TrabajadorLicenciaAplicadorByIdTrabajador;

		}// fin Get con id
		
		
	
		// update Trabajador licencia conducir
		@RequestMapping(value = "/work/updateTrabajadorLicenciaAplicador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateTrabajadorLicenciaAplicador(@RequestBody TrabajadorLicenciaAplicador TrabajadorLicenciaAplicador, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return TrabajadorLicenciaAplicadorDB.updateTrabajadorLicenciaAplicador(TrabajadorLicenciaAplicador);
		}// fin 

	
		
		// Eliminar trabajador licencia conducir
		@RequestMapping(value = "/work/deleteTrabajadorLicenciaAplicadorById/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteTrabajadorLicenciaAplicador(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return TrabajadorLicenciaAplicadorDB.deleteTrabajadorLicenciaAplicadorById(id);
		}// fin metodo eliminar
	
	
}
