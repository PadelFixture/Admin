package lib.data.json.sw;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.DiscapacidadTrabajadores;
import lib.db.sw.DiscapacidadTrabajadoresDB;
import lib.security.session;

@Controller
public class DiscapacidadTrabajadoresJSON {

	//Insert discapacidad
	@RequestMapping(value = "/work/insertDiscapacidad/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertDiscapacidad(@RequestBody DiscapacidadTrabajadores sw_discapacidad, HttpSession httpSession) throws Exception{

		session ses = new session(httpSession);
		if(ses.isValid()){
			return false;
		}

		return DiscapacidadTrabajadoresDB.insertDiscapacidad(sw_discapacidad);
	}

	//Actualizar Discapacidad
	@RequestMapping(value = "/work/updateDiscapacidad/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateDiscapacidad(@RequestBody DiscapacidadTrabajadores sw_discapacidad,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return DiscapacidadTrabajadoresDB.updateDiscapacidad(sw_discapacidad);
	}

	//Obtener Discapacidad por Id
	@RequestMapping(value = "/work/getDiscapacidadByIdTrabajadores/{id}", method = {RequestMethod.GET})
	public @ResponseBody DiscapacidadTrabajadores getDiscapacidadByIdTrabajadores(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		DiscapacidadTrabajadores sw_discapacidad = new DiscapacidadTrabajadores();

		if (ses.isValid()) {
			return sw_discapacidad;
		}

		sw_discapacidad = DiscapacidadTrabajadoresDB.getDiscapacidadByIdTrabajadores(id);
		return sw_discapacidad;
	}

	//Obtener Ultima Discapacidad por Id Trabajador
	@RequestMapping(value = "/work/getLastDiscapacidadByIdTrabajadores/{id}", method = {RequestMethod.GET})
	public @ResponseBody DiscapacidadTrabajadores getLastDiscapacidadByIdTrabajadores(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		DiscapacidadTrabajadores sw_discapacidad = new DiscapacidadTrabajadores();

		if (ses.isValid()) {
			return sw_discapacidad;
		}

		sw_discapacidad = DiscapacidadTrabajadoresDB.getLastDiscapacidadByIdTrabajadores(id);
		return sw_discapacidad;
	}

	//Obtener Discapacidad por Id
	@RequestMapping(value = "/work/getDiscapacidadById/{id}", method = {RequestMethod.GET})
	public @ResponseBody DiscapacidadTrabajadores getDiscapacidadById(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		DiscapacidadTrabajadores sw_discapacidad = new DiscapacidadTrabajadores();

		if (ses.isValid()) {
			return sw_discapacidad;
		}

		sw_discapacidad = DiscapacidadTrabajadoresDB.getDiscapacidadById(id);
		return sw_discapacidad;
	}



	//delete Discapacidad
	@RequestMapping(value = "/work/deleteDiscapacidad/{id}", method = {RequestMethod.GET})
	public @ResponseBody boolean deleteDiscapacidad(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if(ses.isValid()) {
			return false;
		}

		return DiscapacidadTrabajadoresDB.deleteDiscapacidad(id);
	}



}
