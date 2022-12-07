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

import lib.classSW.TrabajadorPostulanteLicenciaAplicador;
import lib.db.sw.TrabajadorPostulanteLicenciaAplicadorDB;
import lib.security.session;

@Controller
public class TrabajadorPostulanteLicenciaAplicadorJSON {

	
	//insert trabajador licencia conducir
		@RequestMapping(value = "/work/insertTrabajadorPostulanteLicenciaAplicador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertTrabajadorPostulanteLicenciaAplicador(@RequestBody TrabajadorPostulanteLicenciaAplicador TrabajadorLicenciaAplicador, HttpSession httpSession)
				throws Exception{

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}

			return TrabajadorPostulanteLicenciaAplicadorDB.insertTrabajadorPostulanteLicenciaAplicador(TrabajadorLicenciaAplicador);
		} // fin insertar
	
	 
      // get licencia conducir
		@RequestMapping(value = "/work/getTrabajadorPostulanteLicenciaAplicador/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<TrabajadorPostulanteLicenciaAplicador> getTrabajadorPostulanteLicenciaAplicador( HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<TrabajadorPostulanteLicenciaAplicador> TrabajadorLicenciaAplicador = new ArrayList<TrabajadorPostulanteLicenciaAplicador>();

			if (ses.isValid()) {
				return TrabajadorLicenciaAplicador;
			}

			TrabajadorLicenciaAplicador = TrabajadorPostulanteLicenciaAplicadorDB.getTrabajadorPostulanteLicenciaAplicador();
			return TrabajadorLicenciaAplicador;

		}// fin get
		 // get licencia conducir con id
		@RequestMapping(value = "/work/getTrabajadorPostulanteLicenciaAplicadorByIdTrabajador/{id}", method = {RequestMethod.GET})
		public @ResponseBody TrabajadorPostulanteLicenciaAplicador getTrabajadorPostulanteLicenciaAplicadorByIdTrabajador(@PathVariable int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			TrabajadorPostulanteLicenciaAplicador TrabajadorLicenciaAplicadorByIdTrabajador = new TrabajadorPostulanteLicenciaAplicador();

			if (ses.isValid()) {
				return TrabajadorLicenciaAplicadorByIdTrabajador;
			}

			TrabajadorLicenciaAplicadorByIdTrabajador = TrabajadorPostulanteLicenciaAplicadorDB.getTrabajadorPostulanteLicenciaAplicadorByIdTrabajador(id);
			return TrabajadorLicenciaAplicadorByIdTrabajador;

		}// fin Get con id
		
		
	
		// update Trabajador licencia conducir
		@RequestMapping(value = "/work/updateTrabajadorPostulanteLicenciaAplicador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateTrabajadorPostulanteLicenciaAplicador(@RequestBody TrabajadorPostulanteLicenciaAplicador TrabajadorLicenciaAplicador, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return TrabajadorPostulanteLicenciaAplicadorDB.updateTrabajadorPostulanteLicenciaAplicador(TrabajadorLicenciaAplicador);
		}// fin 

	
		
		// Eliminar trabajador licencia conducir
		@RequestMapping(value = "/work/deleteTrabajadorPostulanteLicenciaAplicadorById/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteTrabajadorPostulanteLicenciaAplicador(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return TrabajadorPostulanteLicenciaAplicadorDB.deleteTrabajadorPostulanteLicenciaAplicadorById(id);
		}// fin metodo eliminar
	
	
}
