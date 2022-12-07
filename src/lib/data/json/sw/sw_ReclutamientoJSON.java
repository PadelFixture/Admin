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

import SWDB.sw_ReclutamientoJSONDB;
import lib.classSW.LoadCargoPreseleccion;
import lib.classSW.reclutamiento;
import lib.security.session;

@Controller
public class sw_ReclutamientoJSON {
	
	@RequestMapping(value = "/work/selectBuscarPeticiontrab/{nReclutamiento}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<LoadCargoPreseleccion> getselectBuscarPEticionReclutamientoModificar(
			@PathVariable int nReclutamiento, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<LoadCargoPreseleccion> r = new ArrayList<LoadCargoPreseleccion>();

		if (ses.isValid()) {
			return r;
		}
		
		r = sw_ReclutamientoJSONDB.getselectBuscarPEticionReclutamientoModificar(nReclutamiento);

		return r;

	}
	
	@RequestMapping(value = "/work/UpdateReclutamiento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateReclutamiento(@RequestBody ArrayList<LoadCargoPreseleccion> row, HttpSession httpSession)
			throws Exception {
		boolean recc = false;
		session ses = new session(httpSession);

		if (ses.isValid()) {
			return recc;
		}
		recc =  sw_ReclutamientoJSONDB.updateReclutamiento(row.get(0));
		
		for (LoadCargoPreseleccion rec : row) {
			recc = sw_ReclutamientoJSONDB.updatePeticion(rec);
		}
		
		return recc;
	}

}
