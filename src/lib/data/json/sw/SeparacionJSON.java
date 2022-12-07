package lib.data.json.sw;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.SADB.SeparacionDB;
import lib.classSW.Contratos;
import lib.security.session;

@Controller
public class SeparacionJSON {

	private final static Logger LOG = LoggerFactory.getLogger(SeparacionJSON.class);

	//Actualizar Separacion (Tabla - Contrato)
	@RequestMapping(value = "/work/updateSeparacion/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Set<String>> updateSeparacion(@RequestBody Contratos contrato, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return new ResponseEntity<>(Collections.singleton("La sesion esta cerrada"), HttpStatus.FORBIDDEN);
		}
		
		try {
			LOG.info("Generando la Separacion");
			SeparacionDB.updateSeparacion(contrato);
			return new ResponseEntity<>(Collections.singleton("Separacion Generada para el Codigo Trabajador: " + contrato.getCodigo_trabajador()), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singleton(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
	//Actualizar Separacion (Tabla - Contrato)
	@RequestMapping(value = "/work/deleteSeparacion/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Set<String>> deleteSeparacion(@RequestBody Contratos contrato, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return new ResponseEntity<>(Collections.singleton("La sesion esta cerrada, vuelva a Iniciar sesion"), HttpStatus.FORBIDDEN);
		}
		
		try {
			LOG.info("Eliminando la Separacion");
			SeparacionDB.deleteSeparacion(contrato);
			return new ResponseEntity<>(Collections.singleton("Separacion Eliminada para el Codigo Trabajador: " + contrato.getCodigo_trabajador()), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singleton(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
	

}
