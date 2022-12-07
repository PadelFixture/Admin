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

import lib.classSW.AnexoContrato;
import lib.db.sw.AnexoContratoDB;
import lib.security.session;

@Controller
public class AnexoContratoJSON {

	// Insert Trabajador
		@RequestMapping(value = "/work/insertAnexoContrato/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean insertAnexoContrato(@RequestBody AnexoContrato AnexoContrato,
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			if (ses.isValid()) {
				return false;
			}

			return AnexoContratoDB.insertAnexoContrato(AnexoContrato);
		}

		// Actualizar tabla sw_b_trabajadores
		@RequestMapping(value = "/work/updateAnexoContrato/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody boolean updateAnexoContrato(@RequestBody AnexoContrato AnexoContrato,
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}
			return AnexoContratoDB.updateAnexoContrato(AnexoContrato);
		}

		// Borrar Trabajador de la tabla back por Id
		@RequestMapping(value = "/work/deleteAnexoContratoById/{id}", method = { RequestMethod.DELETE })
		public @ResponseBody boolean deleteBackTrabajadorById(@PathVariable String id, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return false;
			}

			return AnexoContratoDB.deleteAnexoContratoById(id);

		}

		// Obtener BackTrabajador por Id
		@RequestMapping(value = "/work/getAnexoContratoById/{id}", method = { RequestMethod.GET })
		public @ResponseBody AnexoContrato getAnexoContratoById(@PathVariable String id, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);
			AnexoContrato AnexoContrato = new AnexoContrato();

			if (ses.isValid()) {
				return AnexoContrato;
			}

			AnexoContrato = AnexoContratoDB.getAnexoContratoById(id);
			return AnexoContrato;

		}

		// Obtener fechaBack's por IdTrabajador
		@RequestMapping(value = "/work/getAnexoContratoByIdTrabajador/{idTrabajador}", method = { RequestMethod.GET })
		public @ResponseBody String getAnexoContratoByIdTrabajador(@PathVariable String idTrabajador,
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return "";
			}

			String map = AnexoContratoDB.getAnexoContratoByIdTrabajador(idTrabajador);

			return map;

		}

		// Obtener Todos los Periodos de los Trabajadores por IdTrabajador
		@RequestMapping(value = "/work/getAllAnexoContratoByIdTrabajador/{idTrabajador}", method = {
				RequestMethod.GET })
		public @ResponseBody ArrayList<AnexoContrato> getAllAnexoContratoByIdTrabajador(
				@PathVariable String idTrabajador, HttpSession httpSession) throws Exception {

			ArrayList<AnexoContrato> AnexoContrato = new ArrayList<AnexoContrato>();

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return null;
			}

			AnexoContrato = AnexoContratoDB.getAllAnexoContratoByIdTrabajador(idTrabajador);

			return AnexoContrato;

		}

		// Obtener Todos los Periodos de los Trabajadores por IdTrabajador y Periodo
		@RequestMapping(value = "/work/getAnexoContratoByIdTrabajadorAndPeriodo/{idTrabajador},{periodo}", method = {
				RequestMethod.GET })
		public @ResponseBody ArrayList<AnexoContrato> getAnexoContratoByIdTrabajadorAndPeriodo(
				@PathVariable String idTrabajador, @PathVariable String periodo, HttpSession httpSession) throws Exception {

			ArrayList<AnexoContrato> AnexoContrato = new ArrayList<AnexoContrato>();

			session ses = new session(httpSession);

			if (ses.isValid()) {
				return null;
			}

			AnexoContrato = AnexoContratoDB.getAnexoContratoByIdTrabajadorAndPeriodo(idTrabajador, periodo);

			return AnexoContrato;

		}
	
	
	
	
	
}
