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

import lib.classSW.Colaboradores;
import lib.classSW.Tsimple;
import lib.classSW.Usuario;
import lib.classSW.trabajadores;
import lib.db.sw.IntranetDB;
import lib.db.sw.UsuarioDB;
import lib.db.sw.trabajadoresDB;
import lib.security.session;
import lib.struc.filterSql;
import lib.utils.GeneralUtility;

@Controller
public class IntranetJSON {

	/**
	 * Creacion de USUARIO con el correo y la contraseña
	 * @param trabajadores
	 * @param httpSession
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/work/Intranet/insertKiosco/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertKiosco(@RequestBody trabajadores trabajadores, HttpSession httpSession)
			throws Exception {

		Usuario usuario = new Usuario();
		
		usuario = IntranetDB.insertKiosco(trabajadores);
		
		GeneralUtility.EnviarMailAutoConsulta(trabajadores, usuario);
		
		return true;
		
	}
	
	
	// Actualizar Trabajador
	@RequestMapping(value = "/work/updateKiosco/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateKiosco(@RequestBody String codigo, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		
		ArrayList<Usuario> usuarios = new ArrayList<>();
		Usuario usuario = new Usuario();
		trabajadores trabajador = new trabajadores();
		
		ArrayList<filterSql> filters = new ArrayList<>();
		filterSql filter = new filterSql();
		filter.setCampo("codigo_persona");
		filter.setValue(codigo);
		
		usuarios = UsuarioDB.getUsuarios(filters);
		
		int idTrabajador = trabajadoresDB.getIdTrabajadorByCodigo(codigo);
		trabajador = trabajadoresDB.getTrabajadorById(idTrabajador+"");
		
		if(usuarios.isEmpty()){
			usuario = IntranetDB.insertKiosco(trabajador);
			GeneralUtility.EnviarMailAutoConsulta(trabajador, usuario);
		}
		else{
			GeneralUtility.EnviarMailAutoConsulta(trabajador, usuarios.get(0));
		}
		return true;
				
	}
//
//	// Actualizar Trabajador CECO HUERTO FAENA
//	@RequestMapping(value = "/work/trabajadores/updateTrabajadorCECO/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean updateTrabajadorCECO(@RequestBody trabajadores trabajadores, HttpSession httpSession)
//			throws Exception {
//
////		session ses = new session(httpSession);
////
////		if (ses.isValid()) {
////			return false;
////		}
//		return trabajadoresDB.updateTrabajadorCECO(trabajadores);
//	}
//	
//	// Actualizar Trabajador Estado de Proceso
//	@RequestMapping(value = "/work/trabajadores/updateTrabajadorEstadoProceso/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean updateTrabajadorEstadoProceso(@RequestBody trabajadores trabajadores, HttpSession httpSession)
//			throws Exception {
//
//		return trabajadoresDB.updateTrabajadorEstadoProceso(trabajadores);
//	}
//
//	// Borrar Trabajador por Id
//	@RequestMapping(value = "/work/deleteTrabajadorById/{id}", method = { RequestMethod.DELETE })
//	public @ResponseBody boolean deleteTrabajadorById(@PathVariable String id, HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//
//		if (ses.isValid()) {
//			return false;
//		}
//
//		return trabajadoresDB.deleteTrabajadorById(id);
//
//	}
//
//	// Obtener Trabajador por Id
//	@RequestMapping(value = "/work/getTrabajadorById/{id}", method = { RequestMethod.GET })
//	public @ResponseBody trabajadores getTrabajadorById(@PathVariable String id, HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//		trabajadores trabajadores = new trabajadores();
//
//		if (ses.isValid()) {
//			return trabajadores;
//		}
//
//		trabajadores = trabajadoresDB.getTrabajadorById(id);
//		return trabajadores;
//
//	}
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
//		trabajadores = trabajadoresDB.getTrabajadorById2(id);
//		return trabajadores;
//
//	}
//
//	// Obtener Trabajador por Columna
//	@RequestMapping(value = "/work/getTrabajadorByColumnAndValue/{column}={value}", method = { RequestMethod.GET })
//	public @ResponseBody ArrayList<trabajadores> getTrabajadorByColumnAndValue(@PathVariable String column,
//			@PathVariable String value, HttpSession httpSession) throws Exception {
//
//		session ses = new session(httpSession);
//		ArrayList<trabajadores> trabajadores = new ArrayList<trabajadores>();
//
//		if (ses.isValid()) {
//			return trabajadores;
//		}
//
//		trabajadores = trabajadoresDB.getTrabajadorByColumnAndValue(column, value);
//		return trabajadores;
//
//	}
//
//	// Obtener Ultimo Trabajador
//	@RequestMapping(value = "/work/getUltimoCodigoTrabajador", method = { RequestMethod.GET })
//	public @ResponseBody int getUltimoCodigoTrabajador(HttpSession httpSession) throws Exception {
//
//		session ses = new session(httpSession);
//		int trabajadores = 0;
//
//		if (ses.isValid()) {
//			return 0;
//		}
//
//		trabajadores = trabajadoresDB.getUltimoCodigoTrabajador();
//		return trabajadores;
//
//	}
//
//	// Obtener idTrabajador por codigo Trabajador
//	@RequestMapping(value = "/work/getIdTrabajadorByCodigo/{codigo}", method = { RequestMethod.GET })
//	public @ResponseBody int getIdTrabajadorByCodigo(@PathVariable String codigo, HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//		int idTrabajadores = 0;
//
//		if (ses.isValid()) {
//			return 0;
//		}
//
//		idTrabajadores = trabajadoresDB.getIdTrabajadorByCodigo(codigo);
//		return idTrabajadores;
//
//	}
//
//	// Obtener Codigo por idTrabajador
//	@RequestMapping(value = "/work/getCodigoByIdTrabajador/{idTrabajador}", method = { RequestMethod.GET })
//	public @ResponseBody int getCodigoByIdTrabajador(@PathVariable String idTrabajador, HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//		int idTrabajadores = 0;
//
//		if (ses.isValid()) {
//			return 0;
//		}
//
//		idTrabajadores = trabajadoresDB.getCodigoByIdTrabajador(idTrabajador);
//		return idTrabajadores;
//
//	}
//
//	// Obtener trabajadores por Rut
//	@RequestMapping(value = "/work/getTrabajadorByRut/{rut:.+}", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody trabajadores getTrabajadorByRut(@PathVariable String rut, HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//		trabajadores trabajadores = new trabajadores();
//
//		if (ses.isValid()) {
//			return trabajadores;
//		}
//
//		trabajadores = trabajadoresDB.getTrabajadorByRut(rut);
//		return trabajadores;
//
//	}
//	
//	// Obtener trabajadores por Rut
////		@RequestMapping(value = "/work/getPostulantesByRut/{rut:.+}", method = { RequestMethod.GET, RequestMethod.POST })
////		public @ResponseBody trabajadores getPostulantesByRut(@PathVariable String rut, HttpSession httpSession)
////				throws Exception {
////
////			session ses = new session(httpSession);
////			trabajadores trabajadores = new trabajadores();
////
////			if (ses.isValid()) {
////				return trabajadores;
////			}
////
////			trabajadores = trabajadoresDB.getPostulantesByRut(rut);
////			return trabajadores;
////
////		}
//	
//	
//	// Obtener trabajadores por Rut
//	@RequestMapping(value = "/work/getTrabajadorByRutTemporal/{rut:.+}", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody trabajadores getTrabajadorByRutTemporal(@PathVariable String rut, HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//		trabajadores trabajadores = new trabajadores();
//
//		if (ses.isValid()) {
//			return trabajadores;
//		}
//
//		trabajadores = trabajadoresDB.getTrabajadorByRutTemporal(rut);
//		return trabajadores;
//
//	}
//	
//
//	// Obtener trabajadores por Rut
//	@RequestMapping(value = "/work/existTrabajadorByRut", method = { RequestMethod.POST })
//	public @ResponseBody trabajadores existTrabajadorByRut(@RequestBody String rut, HttpSession httpSession)
//			throws Exception {
//
//		String rutNumber = rut.split("=")[1];
//
//		session ses = new session(httpSession);
//
//		if (ses.isValid()) {
//			return null;
//		}
//
//		return trabajadoresDB.getTrabajadorByRut(rutNumber);
//
//	}
//	
//	
//	// Obtener trabajadores por Rut
////		@RequestMapping(value = "/work/existPostulantesByRut", method = { RequestMethod.POST })
////		public @ResponseBody trabajadores existPostulantesByRut(@RequestBody String rut, HttpSession httpSession)
////				throws Exception {
////			String rutNumber = rut.split("=")[1];
////			session ses = new session(httpSession);
////			if (ses.isValid()) {
////				return null;
////			}
////			return trabajadoresDB.getPostulantesByRut(rutNumber);
////		}
//	
//
//	// Obtener trabajadores por Rut
//	@RequestMapping(value = "/work/existTrabajadorByRutTemporal", method = { RequestMethod.POST })
//	public @ResponseBody trabajadores existTrabajadorByRutTemporal(@RequestBody String rutTemporal, HttpSession httpSession)
//			throws Exception {
//
//		String rutNumber = rutTemporal.split("=")[1];
//
//		session ses = new session(httpSession);
//
//		if (ses.isValid()) {
//			return null;
//		}
//
//		return trabajadoresDB.getTrabajadorByRutTemporal(rutNumber);
//	}
//
//	// Obtener Todos los Trabajadores
//	@RequestMapping(value = "/work/getAllTrabajador", method = { RequestMethod.POST, RequestMethod.GET })
//	public @ResponseBody ArrayList<trabajadores> getAllTrabajador(HttpSession httpSession) throws Exception {
//
//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return null;
//		}
//
//		ArrayList<trabajadores> trabajadores = new ArrayList<trabajadores>();
//
//		trabajadores = trabajadoresDB.getAllTrabajador();
//
//		return trabajadores;
//
//	}
//
//	// Obtener Todos los Trabajadores Contratista
//	@RequestMapping(value = "/work/getAllTrabajadorWithContratoActivo", method = { RequestMethod.POST,
//			RequestMethod.GET })
//	public @ResponseBody ArrayList<trabajadores> getAllTrabajadorWithContratoActivo(HttpSession httpSession)
//			throws Exception {
//
//		session ses = new session(httpSession);
//		if (ses.isValid()) {
//			return null;
//		}
//
//		ArrayList<trabajadores> trabajadores = new ArrayList<trabajadores>();
//
//		trabajadores = trabajadoresDB.getAllTrabajadorWithContratoActivo();
//
//		return trabajadores;
//
//	}
//
//	// Obtener Todos los Trabajador con sus respectivos contratos
//	@RequestMapping(value = "/work/getAllTrabajadorWithFilter/", method = { RequestMethod.GET })
//	public @ResponseBody ArrayList<trabajadores> getAllTrabajadorWithFilter(HttpSession httpSession, HttpServletRequest request) throws Exception {
//
//		//Crear la lista de Trabajadores vacia
//		ArrayList<trabajadores> trabajadores = new ArrayList<trabajadores>();
//		session ses = new session(httpSession);
//
//		if (ses.isValid()) {
//			return trabajadores;
//		}
//
//		//Obtener todos los parametros del URL
//		Map<String, String[]> parameters = request.getParameterMap();
//
//		ArrayList<filterSql> filter = new ArrayList<filterSql>();
//
//		//Obtener todos los parametros enviados por el URL
//		for (String key : parameters.keySet()) {
//			String[] vals = parameters.get(key);
//			//Obtener cada uno de los parametros y valores
//			for (String val : vals) {
//				filterSql fil = new filterSql();
//				fil.setCampo(key);
//				fil.setValue(val);
//				//Añadir campo y valor 
//				filter.add(fil);
//			}
//		}
//
//		trabajadores = trabajadoresDB.getAllTrabajadorWithFilter(filter);
//		return trabajadores;
//
//	}
//	
//	
//	// Obtener Todos los Trabajador con sus respectivos contratos
//		@RequestMapping(value = "/work/trabajadores/getTrabajadorWithFilter/", method = { RequestMethod.GET })
//		public @ResponseBody ArrayList<Colaboradores> getTrabajadorWithFilter(HttpSession httpSession, HttpServletRequest request) throws Exception {
//
//			//Crear la lista de Trabajadores vacia
//			ArrayList<Colaboradores> trabajadores = new ArrayList<Colaboradores>();
//			session ses = new session(httpSession);
//
//			if (ses.isValid()) {
//				return trabajadores;
//			}
//
//			//Obtener todos los parametros del URL
//			Map<String, String[]> parameters = request.getParameterMap();
//
//			ArrayList<filterSql> filter = new ArrayList<filterSql>();
//
//			//Obtener todos los parametros enviados por el URL
//			for (String key : parameters.keySet()) {
//				String[] vals = parameters.get(key);
//				//Obtener cada uno de los parametros y valores
//				for (String val : vals) {
//					filterSql fil = new filterSql();
//					fil.setCampo(key);
//					fil.setValue(val);
//					//Añadir campo y valor 
//					filter.add(fil);
//				}
//			}
//
//			trabajadores = trabajadoresDB.getTrabajadorWithFilter(filter);
//			return trabajadores;
//
//		}
//	
//	
//	// Obtener Todos los Estados de Proceso
//		@RequestMapping(value = "/work/trabajadores/getEstadoProceso/", method = { RequestMethod.GET })
//		public @ResponseBody ArrayList<trabajadores> getEstadoProceso(HttpSession httpSession, HttpServletRequest request) throws Exception {
//
//			//Crear la lista de Trabajadores vacia
//			ArrayList<trabajadores> trabajadores = new ArrayList<trabajadores>();
//			session ses = new session(httpSession);
//
//			trabajadores = trabajadoresDB.getEstadoProceso();
//			return trabajadores;
//
//		}
	

}
