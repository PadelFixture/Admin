package lib.data.json.sw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.sw_documentos;
import lib.data.json.dataTable;
import lib.db.sw.sw_documentosDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class sw_documentosJson {
	// Actualizar documentos
	@RequestMapping(value = "/WORK/updateDocumentos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateDocumentos(@RequestBody sw_documentos sw_documentos, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return sw_documentosDB.updateDocumentos(sw_documentos);
	}

	// Insert documentos
	@RequestMapping(value = "/WORK/insertDocumentos/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertDocumentos(@RequestBody sw_documentos sw_documentos, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return sw_documentosDB.insertDocumentos(sw_documentos);
	}

	// Delete documentos
	@RequestMapping(value = "/WORK/deleteDocumentos/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody boolean deleteDocumentos(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return sw_documentosDB.deleteDocumentos(id);
	}

	// new
	// Obtener Todos los Trabajadores con Filtros
	@RequestMapping(value = "/work/getAllDocumentos", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getAllDocumentos(HttpServletRequest request, HttpSession httpSession) {

		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {

			data.setDraw(0);
			data.init();
			return data;
		}

		Map<String, String[]> parameters = request.getParameterMap(); // Colocar
																		// Nombres
																		// de
																		// Columna

		ArrayList<filterSql> filter = new ArrayList<filterSql>();
		for (String key : parameters.keySet()) {

			if (key.startsWith("vw_")) {
				String[] vals = parameters.get(key);

				for (String val : vals) {

					filterSql fil = new filterSql();
					fil.setCampo(key.substring(3));
					fil.setValue(val);
					filter.add(fil);
				}
			}
		}

		data.setDraw(0);
		data.init();

		int start = Integer.parseInt(parameters.get("start")[0]);
		int length = Integer.parseInt(parameters.get("length")[0]);

		ArrayList<sw_documentos> datas;
		try {
			datas = sw_documentosDB.getAllDocumentos(filter, "", start, length); // getAllTrabajador(filter,
																					// "",
																					// start,
																					// length);

			Iterator<sw_documentos> f = datas.iterator();

			while (f.hasNext()) {
				sw_documentos row = f.next();
				String[] d = { row.getNombre(), "" };

				data.setData(d);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;

	}
	// new

}
