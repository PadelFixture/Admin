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
import lib.classSW.TrabajadorLicenciaConducir;
import lib.db.sw.TrabajadorLicenciaConducirDB;
import lib.security.session;


@Controller
public class TrabajadorLicenciaConducirJSON {

	
	//insert trabajador licencia conducir
		@RequestMapping(value = "/work/insertTrabajadorLicenciaConducir/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertTrabajadorLicenciaConducir(@RequestBody TrabajadorLicenciaConducir TrabajadorLicenciaConducir, HttpSession httpSession)
				throws Exception{

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}

			return TrabajadorLicenciaConducirDB.insertTrabajadorLicenciaConducir(TrabajadorLicenciaConducir);
		} // fin insertar
	
	 
      // get licencia conducir
		@RequestMapping(value = "/work/getTrabajadorLicenciaConducir/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<TrabajadorLicenciaConducir> getTrabajadorLicenciaConducir( HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<TrabajadorLicenciaConducir> TrabajadorLicenciaConducir = new ArrayList<TrabajadorLicenciaConducir>();

			if (ses.isValid()) {
				return TrabajadorLicenciaConducir;
			}

			TrabajadorLicenciaConducir = TrabajadorLicenciaConducirDB.getTrabajadorLicenciaConducir();
			return TrabajadorLicenciaConducir;

		}// fin get
		 // get licencia conducir con id
		@RequestMapping(value = "/work/getTrabajadorLicenciaConducirByIdTrabajador/{id}", method = {RequestMethod.GET})
		public @ResponseBody TrabajadorLicenciaConducir getTrabajadorLicenciaConducirByIdTrabajador(@PathVariable int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			TrabajadorLicenciaConducir TrabajadorLicenciaConducirByIdTrabajador = new TrabajadorLicenciaConducir();

			if (ses.isValid()) {
				return TrabajadorLicenciaConducirByIdTrabajador;
			}

			TrabajadorLicenciaConducirByIdTrabajador = TrabajadorLicenciaConducirDB.getTrabajadorLicenciaConducirByIdTrabajador(id);
			return TrabajadorLicenciaConducirByIdTrabajador;

		}// fin Get con id
		
		
	
		// update Trabajador licencia conducir
		@RequestMapping(value = "/work/updateTrabajadorLicenciaConducir/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateTrabajadorLicenciaConducir(@RequestBody TrabajadorLicenciaConducir TrabajadorLicenciaConducir, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return TrabajadorLicenciaConducirDB.updateTrabajadorLicenciaConducir(TrabajadorLicenciaConducir);
		}// fin 

	
		
		// Eliminar trabajador licencia conducir
		@RequestMapping(value = "/work/deleteTrabajadorLicenciaConducirById/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteTrabajadorLicenciaConducir(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return TrabajadorLicenciaConducirDB.deleteTrabajadorLicenciaConducirById(id);
		}// fin metodo eliminar
	
	
		
		
}// fin Clase
