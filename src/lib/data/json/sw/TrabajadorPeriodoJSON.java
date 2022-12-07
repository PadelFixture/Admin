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

import lib.classSW.TrabajadorPeriodo;
import lib.db.sw.TrabajadorPeriodoDB;
import lib.security.session;

@Controller
public class TrabajadorPeriodoJSON {

	// Insert Trabajador
	@RequestMapping(value = "/work/insertTrabajadorPeriodo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTrabajadorPeriodo(@RequestBody TrabajadorPeriodo TrabajadorPeriodo,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return TrabajadorPeriodoDB.insertTrabajadorPeriodo(TrabajadorPeriodo);
	}

	// Actualizar tabla sw_b_trabajadores
	@RequestMapping(value = "/work/updateTrabajadorPeriodo/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajadorPeriodo(@RequestBody TrabajadorPeriodo TrabajadorPeriodo,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return TrabajadorPeriodoDB.updateTrabajadorPeriodo(TrabajadorPeriodo);
	}

	// Borrar Trabajador de la tabla back por Id
	@RequestMapping(value = "/work/deleteTrabajadorPeriodoById/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody boolean deleteBackTrabajadorById(@PathVariable String id, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return TrabajadorPeriodoDB.deleteTrabajadorPeriodoById(id);

	}

	// Obtener BackTrabajador por Id
	@RequestMapping(value = "/work/getTrabajadorPeriodoById/{id}", method = { RequestMethod.GET })
	public @ResponseBody TrabajadorPeriodo getTrabajadorPeriodoById(@PathVariable String id, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		TrabajadorPeriodo TrabajadorPeriodo = new TrabajadorPeriodo();

		if (ses.isValid()) {
			return TrabajadorPeriodo;
		}

		TrabajadorPeriodo = TrabajadorPeriodoDB.getTrabajadorPeriodoById(id);
		return TrabajadorPeriodo;

	}

	// Obtener fechaBack's por IdTrabajador
	@RequestMapping(value = "/work/getTrabajadorPeriodoByIdTrabajador/{idTrabajador}", method = { RequestMethod.GET })
	public @ResponseBody String getTrabajadorPeriodoByIdTrabajador(@PathVariable String idTrabajador,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return "";
		}

		String map = TrabajadorPeriodoDB.getTrabajadorPeriodoByIdTrabajador(idTrabajador);

		return map;

	}

	// Obtener Todos los Periodos de los Trabajadores por IdTrabajador
	@RequestMapping(value = "/work/getAllTrabajadorPeriodoByIdTrabajador/{idTrabajador}", method = {
			RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadorPeriodo> getAllTrabajadorPeriodoByIdTrabajador(
			@PathVariable String idTrabajador, HttpSession httpSession) throws Exception {

		ArrayList<TrabajadorPeriodo> TrabajadorPeriodo = new ArrayList<TrabajadorPeriodo>();

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}

		TrabajadorPeriodo = TrabajadorPeriodoDB.getAllTrabajadorPeriodoByIdTrabajador(idTrabajador);

		return TrabajadorPeriodo;

	}

	// Obtener Todos los Periodos de los Trabajadores por IdTrabajador y Periodo
	@RequestMapping(value = "/work/getTrabajadorPeriodoByIdTrabajadorAndPeriodo/{idTrabajador},{periodo}", method = {
			RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadorPeriodo> getTrabajadorPeriodoByIdTrabajadorAndPeriodo(
			@PathVariable String idTrabajador, @PathVariable String periodo, HttpSession httpSession) throws Exception {

		ArrayList<TrabajadorPeriodo> TrabajadorPeriodo = new ArrayList<TrabajadorPeriodo>();

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}

		TrabajadorPeriodo = TrabajadorPeriodoDB.getTrabajadorPeriodoByIdTrabajadorAndPeriodo(idTrabajador, periodo);

		return TrabajadorPeriodo;

	}
	
	//Obtener el periodo Anterior de los trabajadores por IdTrabajador y periodo
	@RequestMapping(value = "/work/getPreviousTrabajadorPeriodoByIdTrabajadorAndPeriodo/", method = {
			RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadorPeriodo> getPreviousTrabajadorPeriodoByIdTrabajadorAndPeriodo(HttpServletRequest request, HttpSession httpSession) throws Exception {

		String idTrabajador = request.getParameter("idTrabajador");
		String periodo = request.getParameter("periodo");

		
		ArrayList<TrabajadorPeriodo> TrabajadorPeriodo = new ArrayList<TrabajadorPeriodo>();

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}

		TrabajadorPeriodo = TrabajadorPeriodoDB.getPreviousTrabajadorPeriodoByIdTrabajadorAndPeriodo(idTrabajador, periodo);

		return TrabajadorPeriodo;

	}
	
	
	
	
	
	
	
	
	

}
