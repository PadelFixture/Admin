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

import lib.classSW.TrabajadoresPer;
import lib.db.sw.TrabajadoresPerDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class TrabajadoresPerJSON {

	// Insert Trabajador
	@RequestMapping(value = "/work/TrabajadoresPer/insertTrabajadorPer/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTrabajadores(@RequestBody TrabajadoresPer trabajadores, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return TrabajadoresPerDB.insertTrabajadorPer(trabajadores);
	}

	// Actualizar Trabajador
	@RequestMapping(value = "/work/TrabajadoresPer/updateTrabajadorPer/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajador(@RequestBody TrabajadoresPer trabajadores, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return TrabajadoresPerDB.updateTrabajadorPer(trabajadores);
	}

	// Obtener Trabajador por Id
	@RequestMapping(value = "/work/TrabajadoresPer/getTrabajadorPerWithFilter/", method = { RequestMethod.GET })
	public @ResponseBody TrabajadoresPer getTrabajadorPerWithFilter(HttpServletRequest request, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		TrabajadoresPer trabajadores = new TrabajadoresPer();

		if (ses.isValid()) {
			return trabajadores;
		}
		
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

		trabajadores = TrabajadoresPerDB.getTrabajadorPerWithFilter(filter);
		return trabajadores;

	}

}
