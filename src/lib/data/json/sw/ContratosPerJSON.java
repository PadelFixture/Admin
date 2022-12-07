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
import lib.classSW.ContratosPer;
import lib.db.sw.ContratosPerDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class ContratosPerJSON {

	
	// Actualizar Contrato
	@RequestMapping(value = "/work/ContratosPer/updateContratosPer/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajador(@RequestBody ContratosPer contrato, HttpSession httpSession)
			throws Exception {

	//	session ses = new session(httpSession);

//		if (ses.isValid()) {
//			return false;
//		}
		return ContratosPerDB.updateContratosPer(contrato);
	}
	
	// Get Contrato con Filtros
	@RequestMapping(value = "/work/ContratosPer/getContratosPerWithFilter/", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody ArrayList<ContratosPer> getContratosPerWithFilter(HttpServletRequest request,
				HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<ContratosPer> contratos = new ArrayList<ContratosPer>();
			// Contratos contratos = new Contratos();

			if (ses.isValid()) {
				return contratos;
			}
			
			Map<String, String[]> parameters = request.getParameterMap();
			ArrayList<filterSql> filter = new ArrayList<filterSql>();

			for (String key : parameters.keySet()) {
				String[] vals = parameters.get(key);

				for (String val : vals) {
					filterSql fil = new filterSql();
					fil.setCampo(key);
					fil.setValue(val);
					filter.add(fil);
				} // Fin del If para StartsWith

			} // Fin del For

			contratos = ContratosPerDB.getContratosPerWithFilter(filter);

			return contratos;

		}
	
	
}
