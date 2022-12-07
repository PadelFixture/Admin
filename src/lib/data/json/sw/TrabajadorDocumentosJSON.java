package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.TrabajadorDocumentos;
import lib.db.sw.TrabajadorDocumentosDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class TrabajadorDocumentosJSON {

	// Insert Contrato
	@RequestMapping(value = "/work/insertTrabajadorDocumentos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTrabajadorDocumentos(@RequestBody TrabajadorDocumentos trabajadorDocumentos,
			HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return TrabajadorDocumentosDB.insertTrabajadorDocumentos(trabajadorDocumentos);
	}

	// Obtener Todos los Contratos con Filtros
	@RequestMapping(value = "/work/getAllTrabajadorDocumentosWithFilter", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadorDocumentos> getAllTrabajadorDocumentosWithFilter(
			HttpServletRequest request, HttpSession httpSession) throws Exception {

		ArrayList<TrabajadorDocumentos> trabajadorDocumentos = new ArrayList<TrabajadorDocumentos>();
		session ses = new session(httpSession);

		if (ses.isValid()) {
			return trabajadorDocumentos;
		}

		Map<String, String[]> parameters = request.getParameterMap();
		ArrayList<filterSql> filter = new ArrayList<filterSql>();

		for (String key : parameters.keySet()) {
			String[] vals = parameters.get(key);
			for (String val : vals) {
				System.out.println(key + " -> " + val);
				filterSql fil = new filterSql();
				fil.setCampo(key);
				fil.setValue(val);
				filter.add(fil);
			}
		}

		trabajadorDocumentos = TrabajadorDocumentosDB.getAllTrabajadorDocumentosWithFilter(filter);
		return trabajadorDocumentos;

	}

	//Update or Insert Documento Trabajador
	@RequestMapping(value = "/work/updateTrabajadorDocumentos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajadorDocumentos(
			@RequestBody TrabajadorDocumentos trabajadorDocumentos, HttpSession httpSession)
			throws Exception {
		
		session ses = new session(httpSession);

		//Obtener el Id del Usuario
		trabajadorDocumentos.setGeneradoPor(ses.getIdPerfil());
		
		if (ses.isValid()) {
			return false;
		}
		return TrabajadorDocumentosDB.updateTrabajadorDocumentos(trabajadorDocumentos);
	}// fin

}
