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

import lib.classSW.contrato;
import lib.classSW.sociedad;
import lib.data.json.dataTable;
import lib.db.sw.sociedadDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class sociedadJson {

	// Insert Sociedad
	
	
	
	
	@RequestMapping(value = "/work/insertSociedad/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertSociedad(@RequestBody sociedad sociedad, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}

		return sociedadDB.insertSociedad(sociedad);
	}

	@RequestMapping(value = "/work/getSociedadById/{id}", method = { RequestMethod.GET })
	public @ResponseBody sociedad getSociedadById(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		sociedad sociedad = new sociedad();

		if (ses.isValid()) {
			return sociedad;
		}

		sociedad = sociedadDB.getSociedadById(id);
		return sociedad;

	}
	// Obtener Todas las sociedades
		@RequestMapping(value = "/work/getSociedadBySoc/{soc}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody sociedad getSociedadBySoc(@PathVariable("soc") String soc, HttpServletRequest request, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			sociedad s = new sociedad();

			if (ses.isValid()) {
				return s;
			}
			s=sociedadDB.getSociedadByCod(soc);
			
			
			return s;

			

		}
	// Obtener Todas las sociedades
	@RequestMapping(value = "/work/getSociedad/", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<sociedad> getSociedad(HttpServletRequest request, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<sociedad> sociedadList = new ArrayList<sociedad>();

		if (ses.isValid()) {
			return sociedadList;
		}

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

		sociedadList = sociedadDB.getSociedad(filter);
		return sociedadList;

	}
	
	// Obtener Todas las sociedades
		@RequestMapping(value = "/work/getSociedad2/", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody ArrayList<sociedad> getSociedad2(HttpServletRequest request, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);

			ArrayList<sociedad> sociedadList = new ArrayList<sociedad>();

			if (ses.isValid()) {
				return sociedadList;
			}

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

			sociedadList = sociedadDB.getSociedad2(filter);
			return sociedadList;

		}

	// Obtener Todas las sociedad con Filtros
	@RequestMapping(value = "/work/getAllSociedad", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody dataTable getAllSociedad(HttpServletRequest request, HttpSession httpSession) {

		session ses = new session(httpSession);
		dataTable data = new dataTable();
		if (ses.isValid()) {
			data.setDraw(0);
			data.init();
			return data;
		}

		Map<String, String[]> parameters = request.getParameterMap();
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

			} // Fin del If para StartsWith

		} // Fin del For

		data.setDraw(0);
		data.init();

		// int start = Integer.parseInt(parameters.get("start")[0]);
		// int length = Integer.parseInt(parameters.get("length")[0]);

		ArrayList<contrato> datas;

		try {

			datas = new ArrayList<contrato>();/*
												 * contratoDB.getAllSociedad(
												 * filter, "", start, length);
												 * TODO: Metodo getAllSociedad
												 */
			Iterator<contrato> f = datas.iterator();

			// data.setRecordsFiltered(contratoDB.getAllSociedad(filter)); TODO:
			// metodo getAllSociedad()
			// data.setRecordsTotal(contratoDB.getAllSociedad(filter)); TODO:
			// metodo getAllSociedad()

			while (f.hasNext()) {
				// contrato row = f.next();
				String[] d = {};/*
								 * { row.getIdTrabajador()+"",
								 * row.getIdContrato()+"",
								 * row.getFechaIngreso(), row.getFechaTermino(),
								 * "", "" }; TODO: Colocar Datos
								 */
				data.setData(d);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;

	}

}
