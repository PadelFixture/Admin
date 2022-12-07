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

import SWDB.sw_MantenedorRepresentantesDB;
import lib.classSW.Representanteslegales;
import lib.security.session;

@Controller
public class sw_MantenedorRepresentantes {
	@RequestMapping(value = "/work/BuscarRepresentantesLegales/{huerto},{soci}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Representanteslegales> getallTrabajaCodNomHorasAsistencia(
			@PathVariable String huerto,@PathVariable String soci, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<Representanteslegales> r = new ArrayList<Representanteslegales>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_MantenedorRepresentantesDB.getrepresentantes(huerto,soci);

		return r;

	}
	
	@RequestMapping(value = "/work/UpdateHora_mantenedorRepresentantes", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateRepresentantesLegales(@RequestBody Representanteslegales row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return sw_MantenedorRepresentantesDB.updateRepresentantesLegales(row);
	}
}
