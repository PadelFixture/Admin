package lib.data.json.SASW;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.ClassSASW.CentroCosto;
import lib.db.SASW.CentroCostoDB;
import lib.security.session;

@Controller
public class CentroCostoJSON {
	@RequestMapping(value = "/work/CentroCostos/getCentroCostoBy/{soc}/{gru}/{ceco}/{version}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<CentroCosto> getCentroCostoBy(@PathVariable("soc") String soc,@PathVariable("gru") String gru,@PathVariable("ceco") String ceco,@PathVariable("version") String version, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CentroCosto> cecos = new ArrayList<CentroCosto>();

		if (ses.isValid()) {
			return cecos;
		}
		return cecos = CentroCostoDB.getCentrosCostos(soc,"","",version);

	}
	@RequestMapping(value = "/work/CentroCostos/getCentroCostoBySoc/{soc}/{version}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<CentroCosto> getCentroCostoBySoc(@PathVariable("soc") String soc,@PathVariable("version") String version, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CentroCosto> cecos = new ArrayList<CentroCosto>();

		if (ses.isValid()) {
			return cecos;
		}
		return cecos = CentroCostoDB.getCentrosCostosBySoc(soc,version);

	}
	@RequestMapping(value = "/work/CentroCostos/getCentroCostoByCeco/{ceco}/{version}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<CentroCosto> getCentroCostoByCeco(@PathVariable("ceco") String ceco,@PathVariable("version") String version, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CentroCosto> cecos = new ArrayList<CentroCosto>();

		if (ses.isValid()) {
			return cecos;
		}
		return cecos = CentroCostoDB.getCentrosCostosByCECO(ceco,version);

	}
}
