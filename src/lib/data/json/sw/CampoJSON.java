package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSA.CUARTEL;
import lib.classSA.CampoEspecie;
import lib.classSA.VARIEDAD;
import lib.classSW.Campo;
import lib.db.sw.CampoDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class CampoJSON {

	// Obtener por Campo por los Grupos
	@RequestMapping(value = "/work/getCampoByGrupo/{grupo}", method = { RequestMethod.GET })
	public @ResponseBody Campo getCampoByGrupo(@PathVariable String grupo, HttpSession httpSession) throws Exception {

		Campo campo = new Campo();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampoByGrupo(grupo);

	}

	// Obtener por Campo por el Codigo de Campo
	@RequestMapping(value = "/work/getCampoByCampo/{CodCampo}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Campo> getCampoByCampo(@PathVariable String CodCampo, HttpSession httpSession)
			throws Exception {

		ArrayList<Campo> campo = new ArrayList<Campo>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampoByCampo(CodCampo);

	}

	// Obtener por Campo por las Sociedad
	@RequestMapping(value = "/work/getCampoBySociedad/{sociedad}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Campo> getCampoBySociedad(@PathVariable int sociedad, HttpSession httpSession)
			throws Exception {

		ArrayList<Campo> campo = new ArrayList<Campo>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampoBySociedad(sociedad);

	}
	@RequestMapping(value = "/work/getCampoBySoc/{sociedad}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Campo> getCampoBySoc(@PathVariable String sociedad, HttpSession httpSession)
			throws Exception {

		ArrayList<Campo> campo = new ArrayList<Campo>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampoBySoc(sociedad);

	}

	// Obtener por Campo por las Sociedad
	@RequestMapping(value = "/work/getCampoWithFilter/", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Campo> getCampoWithFilter(HttpSession httpSession, HttpServletRequest request) throws Exception {

		ArrayList<Campo> campo = new ArrayList<Campo>();

		// Obtener todos los parametros del URL
		Map<String, String[]> parameters = request.getParameterMap();

		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		// Obtener todos los parametros enviados por el URL
		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			// Obtener cada uno de los parametros y valores
			for (String val : vals) {
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				// Añadir campo y valor
				filter.add(fil);
			}
		}

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampoWithFilter(filter);

	}

	// Obtener por Campo por Id
	@RequestMapping(value = "/work/getCampoById/{id}", method = { RequestMethod.GET })
	public @ResponseBody Campo getCampoById(@PathVariable String id, HttpSession httpSession) throws Exception {

		Campo campo = new Campo();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampoById(id);

	}

	@RequestMapping(value = "/work/getCampo", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Campo> getCampo(HttpSession httpSession) throws Exception {

		ArrayList<Campo> campo = new ArrayList<Campo>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampo();

	}

	@RequestMapping(value = "/work/getCampoWithCuartel", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Campo> getCampoWithCuartel(HttpSession httpSession) throws Exception {

		ArrayList<Campo> campo = new ArrayList<Campo>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampoWithCuartel();

	}

	@RequestMapping(value = "/work/getEspeciesByCampo/{codigoCampo}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<CampoEspecie> getEspecieByCampo(@PathVariable String codigoCampo,
			HttpSession httpSession) throws Exception {

		ArrayList<CampoEspecie> campos = new ArrayList<CampoEspecie>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campos;
		}
		return campos = CampoDB.getEspeciesByCampo(codigoCampo);
	}

	@RequestMapping(value = "/work/getVariedadByCampoEspecie/{codigoCampo}/{codigoEspecie}", method = {
			RequestMethod.GET })
	public @ResponseBody ArrayList<VARIEDAD> getVariedadByCampoEspecie(@PathVariable String codigoCampo,
			@PathVariable String codigoEspecie, HttpSession httpSession) throws Exception {

		ArrayList<VARIEDAD> campos = new ArrayList<VARIEDAD>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campos;
		}
		return campos = CampoDB.getVariedadByCampoEspecie(codigoCampo, codigoEspecie);
	}

	@RequestMapping(value = "/work/getCuartelByVariedadCampoEspecie/{codigoCampo}/{codigoEspecie}/{codigoVariedad}", method = {
			RequestMethod.GET })
	public @ResponseBody ArrayList<CUARTEL> getCuartelByVariedadCampoEspecie(@PathVariable String codigoCampo,
			@PathVariable String codigoEspecie, @PathVariable String codigoVariedad, HttpSession httpSession)
			throws Exception {

		ArrayList<CUARTEL> campos = new ArrayList<CUARTEL>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campos;
		}
		return campos = CampoDB.getCuartelByVariedadCampoEspecie(codigoCampo, codigoEspecie, codigoVariedad);
	}
	
	@RequestMapping(value = "/work/getCampoByCodigoSociedad/{sociedad}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<Campo> getCampoByCodigoSociedad(@PathVariable String sociedad, HttpSession httpSession)
			throws Exception {

		ArrayList<Campo> campo = new ArrayList<Campo>();

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return campo;
		}

		return campo = CampoDB.getCampoByCodigoSociedad(sociedad);
		
	}

}
