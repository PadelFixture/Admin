package lib.data.json.sw;

import java.util.ArrayList;
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

import lib.classSW.TrabajadoresPostulantes;
import lib.classSW.trabajadores;
import lib.db.sw.TrabajadoresPostulantesDB;
import lib.security.session;
import lib.struc.filterSql;

@Controller
public class TrabajadoresPostulantesJSON {

	// Insert Trabajador
	@RequestMapping(value = "/work/insertTrabajadorPostulantes2/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertTrabajadores(@RequestBody TrabajadoresPostulantes trabajadores, HttpSession httpSession)
			throws Exception {

//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return false;
//		}

		return TrabajadoresPostulantesDB.insertTrabajadorPostulante(trabajadores);
	}
	
	// Actualizar Trabajador
	@RequestMapping(value = "/work/updateTrabajadorPostulante/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajador(@RequestBody TrabajadoresPostulantes trabajadores, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return TrabajadoresPostulantesDB.updateTrabajadorPostulante(trabajadores);
	}

	// Actualizar Trabajador CECO HUERTO FAENA
	@RequestMapping(value = "/work/trabajadores/updateTrabajadorPostulanteCECO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajadorPostulanteCECO(@RequestBody TrabajadoresPostulantes trabajadores, HttpSession httpSession)
			throws Exception {

//		session ses = new session(httpSession);
//
//		if (ses.isValid()) {
//			return false;
//		}
		return TrabajadoresPostulantesDB.updateTrabajadorPostulanteCECO(trabajadores);
	}
	
	// Actualizar Trabajador Estado de Proceso
	@RequestMapping(value = "/work/trabajadores/updateTrabajadorPostulanteEstadoProceso/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateTrabajadorEstadoProceso(@RequestBody TrabajadoresPostulantes trabajadores, HttpSession httpSession)
			throws Exception {

		return TrabajadoresPostulantesDB.updateTrabajadorPostulanteEstadoProceso(trabajadores);
	}

	// Borrar Trabajador por Id
	@RequestMapping(value = "/work/deleteTrabajadorPostulanteById/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody boolean deleteTrabajadorPostulanteById(@PathVariable String id, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}

		return TrabajadoresPostulantesDB.deleteTrabajadorPostulanteById(id);

	}

	// Obtener Trabajador por Id
	@RequestMapping(value = "/work/getTrabajadorPostulanteById/{id}", method = { RequestMethod.GET })
	public @ResponseBody trabajadores getTrabajadorPostulanteById(@PathVariable String id, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		trabajadores trabajadores = new trabajadores();

		if (ses.isValid()) {
			return trabajadores;
		}

		trabajadores = TrabajadoresPostulantesDB.getTrabajadorPostulantesById(id);
		return trabajadores;

	}
//	@RequestMapping(value = "/work/getTrabajadorById2/{id}", method = { RequestMethod.GET })
//	public @ResponseBody Tsimple getTrabajadorById2(@PathVariable String id, HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//		Tsimple trabajadores = new Tsimple();
//
//		if (ses.isValid()) {
//			return trabajadores;
//		}
//
//		trabajadores = TrabajadoresPostulantesDB.getTrabajadorById2(id);
//		return trabajadores;
//
//	}

	// Obtener Trabajador por Columna
	@RequestMapping(value = "/work/getTrabajadorPostulanteByColumnAndValue/{column}={value}", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadoresPostulantes> getTrabajadorPostulanteByColumnAndValue(@PathVariable String column,
			@PathVariable String value, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<TrabajadoresPostulantes> trabajadores = new ArrayList<TrabajadoresPostulantes>();

		if (ses.isValid()) {
			return trabajadores;
		}

		trabajadores = TrabajadoresPostulantesDB.getTrabajadorPostulanteByColumnAndValue(column, value);
		return trabajadores;

	}

	// Obtener Ultimo Trabajador
	@RequestMapping(value = "/work/getUltimoCodigoTrabajadorPostulante", method = { RequestMethod.GET })
	public @ResponseBody int getUltimoCodigoTrabajadorPostulante(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		int trabajadores = 0;

		if (ses.isValid()) {
			return 0;
		}

		trabajadores = TrabajadoresPostulantesDB.getUltimoCodigoTrabajadorPostulante();
		return trabajadores;

	}

	// Obtener idTrabajador por codigo Trabajador
	@RequestMapping(value = "/work/getIdTrabajadorPostulanteByCodigo/{codigo}", method = { RequestMethod.GET })
	public @ResponseBody int getIdTrabajadorPostulanteByCodigo(@PathVariable String codigo, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		int idTrabajadores = 0;

		if (ses.isValid()) {
			return 0;
		}

		idTrabajadores = TrabajadoresPostulantesDB.getIdTrabajadorPostulanteByCodigo(codigo);
		return idTrabajadores;

	}

	// Obtener Codigo por idTrabajador
	@RequestMapping(value = "/work/getCodigoByIdTrabajadorPostulante/{idTrabajador}", method = { RequestMethod.GET })
	public @ResponseBody int getCodigoByIdTrabajadorPostulante(@PathVariable String idTrabajador, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		int idTrabajadores = 0;

		if (ses.isValid()) {
			return 0;
		}

		idTrabajadores = TrabajadoresPostulantesDB.getCodigoByIdTrabajadorPostulante(idTrabajador);
		return idTrabajadores;

	}

	// Obtener trabajadores por Rut
	@RequestMapping(value = "/work/getTrabajadorPostulanteByRut/{rut:.+}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody TrabajadoresPostulantes getTrabajadorPostulanteByRut(@PathVariable String rut, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		TrabajadoresPostulantes trabajadores = new TrabajadoresPostulantes();

		if (ses.isValid()) {
			return trabajadores;
		}

		trabajadores = TrabajadoresPostulantesDB.getTrabajadorPostulanteByRut(rut);
		return trabajadores;

	}
	
	// Obtener trabajadores por Rut
		@RequestMapping(value = "/work/getPostulantesByRut/{rut:.+}", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody TrabajadoresPostulantes getPostulantesByRut(@PathVariable String rut, HttpSession httpSession)
				throws Exception {

			session ses = new session(httpSession);
			TrabajadoresPostulantes trabajadores = new TrabajadoresPostulantes();

			if (ses.isValid()) {
				return trabajadores;
			}

			trabajadores = TrabajadoresPostulantesDB.getPostulantesByRut(rut);
			return trabajadores;

		}
	
	
	// Obtener trabajadores por Rut
	@RequestMapping(value = "/work/getTrabajadorPostulantesByRutTemporal/{rut:.+}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody TrabajadoresPostulantes getTrabajadorPostulantesByRutTemporal(@PathVariable String rut, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		TrabajadoresPostulantes trabajadores = new TrabajadoresPostulantes();

		if (ses.isValid()) {
			return trabajadores;
		}

		trabajadores = TrabajadoresPostulantesDB.getTrabajadorPostulanteByRutTemporal(rut);
		return trabajadores;

	}
	

	// Obtener trabajadores por Rut
	@RequestMapping(value = "/work/existTrabajadorPostulanteByRut", method = { RequestMethod.POST })
	public @ResponseBody TrabajadoresPostulantes existTrabajadorPostulanteByRut(@RequestBody String rut, HttpSession httpSession)
			throws Exception {

		String rutNumber = rut.split("=")[1];

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}

		return TrabajadoresPostulantesDB.getTrabajadorPostulanteByRut(rutNumber);

	}
	
	
	// Obtener trabajadores por Rut
		@RequestMapping(value = "/work/existPostulantesByRut", method = { RequestMethod.POST })
		public @ResponseBody TrabajadoresPostulantes existPostulantesByRut(@RequestBody String rut, HttpSession httpSession)
				throws Exception {
			String rutNumber = rut.split("=")[1];
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return null;
			}
			return TrabajadoresPostulantesDB.getPostulantesByRut(rutNumber);
		}
	

	// Obtener trabajadores por Rut
	@RequestMapping(value = "/work/existTrabajadorPostulanteByRutTemporal", method = { RequestMethod.POST })
	public @ResponseBody TrabajadoresPostulantes existTrabajadorPostulanteByRutTemporal(@RequestBody String rutTemporal, HttpSession httpSession)
			throws Exception {

		String rutNumber = rutTemporal.split("=")[1];

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return null;
		}

		return TrabajadoresPostulantesDB.getTrabajadorPostulanteByRutTemporal(rutNumber);
	}

	// Obtener Todos los Trabajadores
	@RequestMapping(value = "/work/getAllTrabajadorPostulantes", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadoresPostulantes> getAllTrabajadorPostulantes(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return null;
		}

		ArrayList<TrabajadoresPostulantes> trabajadores = new ArrayList<TrabajadoresPostulantes>();

		trabajadores = TrabajadoresPostulantesDB.getAllTrabajadorPostulantes();

		return trabajadores;

	}

	// Obtener Todos los Trabajadores Contratista
	@RequestMapping(value = "/work/getAllTrabajadorPostulantesWithContratoActivo", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadoresPostulantes> getAllTrabajadorPostulantesWithContratoActivo(HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return null;
		}

		ArrayList<TrabajadoresPostulantes> trabajadores = new ArrayList<TrabajadoresPostulantes>();

		trabajadores = TrabajadoresPostulantesDB.getAllTrabajadorPostulanteWithContratoActivo();

		return trabajadores;

	}

	// Obtener Todos los Trabajador con sus respectivos contratos
	@RequestMapping(value = "/work/getAllTrabajadorPostulanteWithFilter/", method = { RequestMethod.GET })
	public @ResponseBody ArrayList<TrabajadoresPostulantes> getAllTrabajadorPostulanteWithFilter(HttpSession httpSession, HttpServletRequest request) throws Exception {

		//Crear la lista de Trabajadores vacia
		ArrayList<TrabajadoresPostulantes> trabajadores = new ArrayList<TrabajadoresPostulantes>();
		session ses = new session(httpSession);

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

		trabajadores = TrabajadoresPostulantesDB.getAllTrabajadorPostulanteWithFilter(filter);
		return trabajadores;

	}
	
	
//	// Obtener Todos los Trabajador con sus respectivos contratos
		@RequestMapping(value = "/work/trabajadores/getTrabajadorPostulantesWithFilter/", method = { RequestMethod.GET })
		public @ResponseBody ArrayList<TrabajadoresPostulantes> getTrabajadorPostulantesWithFilter(HttpSession httpSession, HttpServletRequest request) throws Exception {

			//Crear la lista de Trabajadores vacia
			ArrayList<TrabajadoresPostulantes> trabajadores = new ArrayList<TrabajadoresPostulantes>();
			session ses = new session(httpSession);

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

			trabajadores = TrabajadoresPostulantesDB.getTrabajadorPostulantesWithFilter(filter);
			return trabajadores;

		}
	
	
	// Obtener Todos los Estados de Proceso
		@RequestMapping(value = "/work/TrabajadoresPostulantes/getEstadoProceso/", method = { RequestMethod.GET })
		public @ResponseBody ArrayList<TrabajadoresPostulantes> getEstadoProceso(HttpSession httpSession, HttpServletRequest request) throws Exception {

			//Crear la lista de Trabajadores vacia
			ArrayList<TrabajadoresPostulantes> trabajadores = new ArrayList<TrabajadoresPostulantes>();
			

			trabajadores = TrabajadoresPostulantesDB.getEstadoProceso();
			return trabajadores;

		}
	

}
