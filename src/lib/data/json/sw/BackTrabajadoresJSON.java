package lib.data.json.sw;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.BackTrabajadores;
import lib.db.sw.BackTrabajadoresDB;
import lib.security.session;

@Controller
public class BackTrabajadoresJSON {


	//Insert Trabajador
	@RequestMapping(value = "/work/insertBackTrabajador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertBackTrabajador(@RequestBody BackTrabajadores backTrabajadores, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if(ses.isValid()){
			return false;
		}

		return BackTrabajadoresDB.insertBackTrabajador(backTrabajadores);
	}

	//Actualizar tabla sw_b_trabajadores
	@RequestMapping(value = "/work/updateBackTrabajador/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateBackTrabajador(@RequestBody BackTrabajadores backTrabajadores,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return BackTrabajadoresDB.updateBackTrabajador(backTrabajadores);
	}

	//Borrar Trabajador de la tabla back por Id
	@RequestMapping(value = "/work/deleteBackTrabajadorById/{id}", method = {RequestMethod.DELETE})
	public @ResponseBody boolean deleteBackTrabajadorById(@PathVariable String id ,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return BackTrabajadoresDB.deleteBackTrabajadorById(id);

	}

	//Obtener BackTrabajador por Id
	@RequestMapping(value = "/work/getBackTrabajadorById/{id}", method = {RequestMethod.GET})
	public @ResponseBody BackTrabajadores getBackTrabajadorById(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		BackTrabajadores backTrabajadores = new BackTrabajadores();

		if (ses.isValid()) {
			return backTrabajadores;
		}

		backTrabajadores = BackTrabajadoresDB.getBackTrabajadorById(id);
		return backTrabajadores;

	}


	//Obtener fechaBack's por IdTrabajador
	@RequestMapping(value = "/work/getFechaBackByIdTrabajador/{idTrabajador}", method = {RequestMethod.GET})
	public @ResponseBody String getFechaBackByIdTrabajador(@PathVariable String idTrabajador, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return "";
		}

		String map = BackTrabajadoresDB.getFechaBackByIdTrabajador(idTrabajador);

		return map;

	}





}
