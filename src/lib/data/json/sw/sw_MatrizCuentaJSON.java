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

import SWDB.sw_IncidentesMutualidadDB;
import SWDB.sw_MatrizCuentasDB;
import lib.classSW.IncidentesMutualidad;
import lib.security.session;

@Controller
public class sw_MatrizCuentaJSON {

	@RequestMapping(value = "/work/getCuentasporceco/{ceco},{anio}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<IncidentesMutualidad> getdatosceco(
			@PathVariable String ceco,@PathVariable int anio,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<IncidentesMutualidad> r = new ArrayList<IncidentesMutualidad>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_MatrizCuentasDB.getdatosceco(ceco,anio);

		return r;

	}
	
	@RequestMapping(value = "/work/getFactorAnioDeclaracionJurada/{anio}", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<IncidentesMutualidad> getFactorAnioDeclaracionJurada(
		   @PathVariable int anio,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<IncidentesMutualidad> r = new ArrayList<IncidentesMutualidad>();

		if (ses.isValid()) {
			return r;
		}
		r = sw_MatrizCuentasDB.getFactorAnioDeclaracionJurada(anio);

		return r;

	}
	
	
	@RequestMapping(value = "/work/insertUpdateMatrizcuenta/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertRupdateMatriz(@RequestBody ArrayList<IncidentesMutualidad> row,
			HttpSession httpSession) throws Exception {
		
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		for (IncidentesMutualidad rec : row) {

			recc = sw_MatrizCuentasDB.insertRupdateMatriz(rec);
		}

		return recc;

	}
	
	@RequestMapping(value = "/work/insertUpdateFactorDeclaracionjurada/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertUpdateFactorDeclaracionjurada(@RequestBody ArrayList<IncidentesMutualidad> row,
			HttpSession httpSession) throws Exception {
		
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		for (IncidentesMutualidad rec : row) {

			recc = sw_MatrizCuentasDB.insertUpdateFactorDeclaracionjurada(rec);
		}

		return recc;

	}
}
