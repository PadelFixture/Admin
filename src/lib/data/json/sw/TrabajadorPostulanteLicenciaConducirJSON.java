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
import lib.classSW.TrabajadorPostulanteLicenciaConducir;
import lib.db.sw.TrabajadorPostulanteLicenciaConducirDB;
import lib.security.session;


@Controller
public class TrabajadorPostulanteLicenciaConducirJSON {

	
	//insert TrabajadorPostulante licencia conducir
		@RequestMapping(value = "/work/insertTrabajadorPostulanteLicenciaConducir/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertTrabajadorPostulanteLicenciaConducir(@RequestBody TrabajadorPostulanteLicenciaConducir TrabajadorPostulanteLicenciaConducir, HttpSession httpSession)
				throws Exception{

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}

			return TrabajadorPostulanteLicenciaConducirDB.insertTrabajadorPostulanteLicenciaConducir(TrabajadorPostulanteLicenciaConducir);
		} // fin insertar
	
	 
      // get licencia conducir
		@RequestMapping(value = "/work/getTrabajadorPostulanteLicenciaConducir/", method = {RequestMethod.GET})
		public @ResponseBody ArrayList<TrabajadorPostulanteLicenciaConducir> getTrabajadorPostulanteLicenciaConducir( HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<TrabajadorPostulanteLicenciaConducir> TrabajadorPostulanteLicenciaConducir = new ArrayList<TrabajadorPostulanteLicenciaConducir>();

			if (ses.isValid()) {
				return TrabajadorPostulanteLicenciaConducir;
			}

			TrabajadorPostulanteLicenciaConducir = TrabajadorPostulanteLicenciaConducirDB.getTrabajadorPostulanteLicenciaConducir();
			return TrabajadorPostulanteLicenciaConducir;

		}// fin get
		 // get licencia conducir con id
		@RequestMapping(value = "/work/getTrabajadorPostulanteLicenciaConducirByIdTrabajadorPostulante/{id}", method = {RequestMethod.GET})
		public @ResponseBody TrabajadorPostulanteLicenciaConducir getTrabajadorPostulanteLicenciaConducirByIdTrabajadorPostulante(@PathVariable int id, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			TrabajadorPostulanteLicenciaConducir TrabajadorPostulanteLicenciaConducirByIdTrabajadorPostulante = new TrabajadorPostulanteLicenciaConducir();

			if (ses.isValid()) {
				return TrabajadorPostulanteLicenciaConducirByIdTrabajadorPostulante;
			}

			TrabajadorPostulanteLicenciaConducirByIdTrabajadorPostulante = TrabajadorPostulanteLicenciaConducirDB.getTrabajadorPostulanteLicenciaConducirByIdTrabajador(id);
			return TrabajadorPostulanteLicenciaConducirByIdTrabajadorPostulante;

		}// fin Get con id
		
		
	
		// update TrabajadorPostulante licencia conducir
		@RequestMapping(value = "/work/updateTrabajadorPostulanteLicenciaConducir/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateTrabajadorPostulanteLicenciaConducir(@RequestBody TrabajadorPostulanteLicenciaConducir TrabajadorPostulanteLicenciaConducir, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return TrabajadorPostulanteLicenciaConducirDB.updateTrabajadorPostulanteLicenciaConducir(TrabajadorPostulanteLicenciaConducir);
		}// fin 

	
		
		// Eliminar TrabajadorPostulante licencia conducir
		@RequestMapping(value = "/work/deleteTrabajadorPostulanteLicenciaConducirById/{id}", method = {RequestMethod.PUT})
		public @ResponseBody boolean deleteTrabajadorPostulanteLicenciaConducir(@PathVariable int id ,HttpSession httpSession) throws Exception {
							
			session ses = new session(httpSession);
							
			if (ses.isValid()) {
				return false;
			}
							
			return TrabajadorPostulanteLicenciaConducirDB.deleteTrabajadorPostulanteLicenciaConducirById(id);
		}// fin metodo eliminar
	
	
		
		
}// fin Clase
