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

import SWDB.sw_HorasAsistenciaDB;
import SWDB.sw_turnosEmpresaDB;
import lib.classSW.HorasAsistencia;
import lib.classSW.TurnosEmpresa;
import lib.db.sw.FiniquitosBD;
import lib.security.session;

@Controller
public class sw_turnosEmpresaJson {
	
	@RequestMapping(value = "/work/BuscarTurnos/{soci},{huerto},{zona}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<TurnosEmpresa> getallTrabajaCodNomHorasAsistencia(
			@PathVariable int soci,@PathVariable String huerto,@PathVariable String zona, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<TurnosEmpresa> r = new ArrayList<TurnosEmpresa>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_turnosEmpresaDB.getTurnosE(soci,huerto,zona);

		return r;

	}
	
	@RequestMapping(value = "/work/EliminarTurnoEmpresa/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean eliminarHDF(@PathVariable int id, HttpSession httpSession) throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return recc;
		}

		recc = sw_turnosEmpresaDB.eliminarTE(id);

		return recc;

	}
	
	@RequestMapping(value = "/work/Updateturnosempresa", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTurnos(@RequestBody TurnosEmpresa row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return sw_turnosEmpresaDB.updateTurnose(row);
	}
	
//	@RequestMapping(value = "/work/insertTurnoEmpresa/", method = { RequestMethod.PUT,
//			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody String insertturnosemp(@RequestBody TurnosEmpresa row, HttpSession httpSession) throws Exception {
//		String recc = "";
//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return recc;
//		}
//			
//		
//		return recc;
//
//	}
	@RequestMapping(value = "/work/insertTurnoEmpresa/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertturnosemp(@RequestBody ArrayList<TurnosEmpresa> row,
			HttpSession httpSession) throws Exception {
		
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		for (TurnosEmpresa rec : row) {

			recc = sw_turnosEmpresaDB.insertturnosemp(rec);
		}

		return recc;

	}
}
