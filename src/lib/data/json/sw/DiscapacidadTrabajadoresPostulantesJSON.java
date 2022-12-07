package lib.data.json.sw;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.DiscapacidadTrabajadoresPostulantes;
import lib.db.sw.DiscapacidadTrabajadoresPostulantesDB;
import lib.security.session;

@Controller
public class DiscapacidadTrabajadoresPostulantesJSON {

	//Insert discapacidad
	@RequestMapping(value = "/work/insertDiscapacidadPostulantes/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertDiscapacidadPostulantes(@RequestBody DiscapacidadTrabajadoresPostulantes sw_discapacidad, HttpSession httpSession) throws Exception{

		session ses = new session(httpSession);
		if(ses.isValid()){
			return false;
		}

		return DiscapacidadTrabajadoresPostulantesDB.insertDiscapacidadPostulantes(sw_discapacidad);
	}

	//Actualizar Discapacidad
	@RequestMapping(value = "/work/updateDiscapacidadPostulantes/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateDiscapacidadPostulantes(@RequestBody DiscapacidadTrabajadoresPostulantes sw_discapacidad,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return DiscapacidadTrabajadoresPostulantesDB.updateDiscapacidadPostulantes(sw_discapacidad);
	}

	//Obtener Discapacidad por Id
	@RequestMapping(value = "/work/getDiscapacidadPostulantesByIdTrabajadores/{id}", method = {RequestMethod.GET})
	public @ResponseBody DiscapacidadTrabajadoresPostulantes getDiscapacidadPostulantesByIdTrabajadores(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		DiscapacidadTrabajadoresPostulantes sw_discapacidad = new DiscapacidadTrabajadoresPostulantes();

		if (ses.isValid()) {
			return sw_discapacidad;
		}

		sw_discapacidad = DiscapacidadTrabajadoresPostulantesDB.getDiscapacidadByIdTrabajadoresPostulantes(id);
		return sw_discapacidad;
	}

	//Obtener Ultima Discapacidad por Id Trabajador
	@RequestMapping(value = "/work/getLastDiscapacidadPostulantesByIdTrabajadores/{id}", method = {RequestMethod.GET})
	public @ResponseBody DiscapacidadTrabajadoresPostulantes getLastDiscapacidadPostulantesByIdTrabajadores(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		DiscapacidadTrabajadoresPostulantes sw_discapacidad = new DiscapacidadTrabajadoresPostulantes();

		if (ses.isValid()) {
			return sw_discapacidad;
		}

		sw_discapacidad = DiscapacidadTrabajadoresPostulantesDB.getLastDiscapacidadPostulantesByIdTrabajadores(id);
		return sw_discapacidad;
	}

	//Obtener Discapacidad por Id
	@RequestMapping(value = "/work/getDiscapacidadPostulantesById/{id}", method = {RequestMethod.GET})
	public @ResponseBody DiscapacidadTrabajadoresPostulantes getDiscapacidadPostulantesById(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		DiscapacidadTrabajadoresPostulantes sw_discapacidad = new DiscapacidadTrabajadoresPostulantes();

		if (ses.isValid()) {
			return sw_discapacidad;
		}

		sw_discapacidad = DiscapacidadTrabajadoresPostulantesDB.getDiscapacidadPostulantesById(id);
		return sw_discapacidad;
	}



	//delete Discapacidad
	@RequestMapping(value = "/work/deleteDiscapacidadPostulantes/{id}", method = {RequestMethod.GET})
	public @ResponseBody boolean deleteDiscapacidadPostulantes(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if(ses.isValid()) {
			return false;
		}

		return DiscapacidadTrabajadoresPostulantesDB.deleteDiscapacidadPostulantes(id);
	}



}
