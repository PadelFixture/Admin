package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.ContratoTrabajador;
import lib.classSW.TrabajadorContrato;
import lib.db.sw.TrabajadorContratoDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class TrabajadorContratoJSON {

	// Obtener Todos los Trabajador con sus ultimos contratos
	@RequestMapping(value = "/work/getAllTrabajadorWithLastContrato", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadorContrato> getAllTrabajadorWithLastContrato(HttpServletRequest request,
			HttpSession httpSession) throws Exception {

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
		ArrayList<TrabajadorContrato> TrabajadorContrato = new ArrayList<TrabajadorContrato>();

		if (ses.isValid()) {
			return TrabajadorContrato;
		}

		TrabajadorContrato = TrabajadorContratoDB.getAllTrabajadorWithLastContrato(filter);
		return TrabajadorContrato;

	}

	// Obtener Todos los Trabajador con sus respectivos contratos
	@RequestMapping(value = "/work/getTrabajadorWithContratosByCodigoTrabajador/", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadorContrato> getTrabajadorWithContratosByCodigoTrabajador(
			HttpSession httpSession, HttpServletRequest request) throws Exception {

		ArrayList<TrabajadorContrato> TrabajadorContrato = new ArrayList<TrabajadorContrato>();
		session ses = new session(httpSession);

		if (ses.isValid()) {
			return TrabajadorContrato;
		}

		Map<String, String[]> parameters = request.getParameterMap();

		System.out.println(parameters);

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

		TrabajadorContrato = TrabajadorContratoDB.getTrabajadorWithContratosByCodigoTrabajador(filter);
		return TrabajadorContrato;

	}
	
	
	
	// Obtener Todos los Trabajador con sus respectivos contratos
		@RequestMapping(value = "/work/TrabajadorContrato/getAllTrabajadorContratoWithFilter/", method = { RequestMethod.GET })
		public @ResponseBody ArrayList<ContratoTrabajador> getAllTrabajadorContratoWithFilter(HttpSession httpSession, HttpServletRequest request) throws Exception {

			//Obtener todos los parametros del URL
			Map<String, String[]> parameters = request.getParameterMap();

			ArrayList<filterSql> filter = new ArrayList<filterSql>();

			//Obtener todos los parametros enviados por el URL
			for (String key : parameters.keySet()) {
				String[] vals = parameters.get(key);
				//Obtener cada uno de los parametros y valores
				for (String val : vals) {
					filterSql fil = new filterSql();
					fil.setCampo(key);
					fil.setValue(val);
					//Añadir campo y valor 
					filter.add(fil);
				}
			}

			return TrabajadorContratoDB.getAllTrabajadorContratoWithFilter(filter);

		}
	
	

}
