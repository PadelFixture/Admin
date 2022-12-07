//package lib.data.json;
//
//import java.util.ArrayList;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import lib.db.dteBD;
//import lib.db.sesionVar;
//import lib.db.simpleagroDB;
////import lib.jsonMap.purchaseMap;
//import lib.security.session;
//import lib.classSA.CAMPO;
//import lib.struc.IN_INCIDENCIA_ASIGNADA;
//import lib.struc.bodegaConection;
//import lib.struc.comuna;
//import lib.struc.departamentos;
//import lib.struc.files;
//import lib.struc.incidencia;
//import lib.struc.labores;
//import lib.struc.loginApp;
//import lib.struc.mapConection;
//import lib.struc.material;
////import lib.struc.notificacionPreseleccion;
//import lib.struc.perfil_usuario;
//import lib.struc.peticion;
//import lib.struc.productConection;
//import lib.struc.provincia;
////import lib.struc.region;
//import lib.struc.trabajadores;
////import lib.jsonMap.innerTrabajadores;
//import lib.jsonMap.jsonSesionVars;
//
//@Controller
//public class mapJson {
//	
//	@RequestMapping(value = "/map/add", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean setUser(@RequestBody  mapConection row,HttpSession httpSession) throws Exception {
//		
////		session ses = new session(httpSession);
////		
////		if (ses.isValid()) {
////			return false;
////		}
//		sesionVar mc= new sesionVar(httpSession);
//		mc.addCampo(row, row.idtest);
//		mc.saveCampo();
//		return dteBD.setmapConection(row);
//
//	}
//	
//	@RequestMapping(value = "/map/getCampos", method = { RequestMethod.GET })
//	public @ResponseBody jsonSesionVars getCampos(HttpServletRequest request, HttpSession httpSession)throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		
//		sesionVar mc= new sesionVar(httpSession);
//		
//		return mc.getCampo();
//
//	}
//	@RequestMapping(value = "/map/load/{id}", method = {RequestMethod.GET,RequestMethod.POST})
//	public @ResponseBody ArrayList<mapConection> setLoad(@PathVariable int id ,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<mapConection> es = new ArrayList<mapConection>();
//		
//		if (ses.isValid()) {
//			return es;
//		}
//		
//		es = dteBD.setCoordenada(id);
//		return es;
//
//	}
//	@RequestMapping(value = "/map/upd", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean updMap(@RequestBody  mapConection row,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//			return false;
//		}
//		return dteBD.updMap(row);
//
//	}
//	@RequestMapping(value = "/map/file", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean addDoc(@RequestBody  mapConection row,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//			return false;
//		}
//		return dteBD.updMap(row);
//
//	}
//
////	// notificación preseleccion
////	
////	@RequestMapping(value = "/map/loadNotiPreseleccion", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
////	public @ResponseBody ArrayList<notificacionPreseleccion> loadNotiPreseleccion(HttpSession httpSession) throws Exception {
////		
////		session ses = new session(httpSession);
////		
////		ArrayList<notificacionPreseleccion> r = new ArrayList<notificacionPreseleccion>();
////		
////		if (ses.isValid()) {
////			return r;
////		}
////		
////		r = dteBD.loadNotificacionPreseleccion();
////		
////		return r;
////
////	}
//	
//	
//	
//	
//	@RequestMapping(value = "/map/loadCellar", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<bodegaConection> loadCellar(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<bodegaConection> r = new ArrayList<bodegaConection>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.loadCellar();
//		return r;
//
//	}
//	@RequestMapping(value = "/map/loadProduct", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<productConection> loadProduct(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		ArrayList<productConection> r = new ArrayList<productConection>();
//		if (ses.isValid()) {
//			return r;
//		}
//		r = dteBD.loadProduct();
//		return r;
// 
//	}
//	@RequestMapping(value = "/map/loadRuta/{idInput}", method = {RequestMethod.GET,RequestMethod.POST})
//	public @ResponseBody ArrayList<files> loadRuta(@PathVariable int idInput ,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<files> es = new ArrayList<files>();
//		
//		if (ses.isValid()) {
//			return es;
//		}
//		
//		es = dteBD.loadRuta(idInput);
//		return es;
//
//	}
//	@RequestMapping(value = "/map/loadPersonal", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<trabajadores> loadPersonal(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<trabajadores> r = new ArrayList<trabajadores>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.loadPersonal();
//		
//		return r;
//
//	}
//	@RequestMapping(value = "/map/loadCargo", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<perfil_usuario> loadCargo(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<perfil_usuario> r = new ArrayList<perfil_usuario>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.loadCargo();
//		
//		return r;
//
//	}
////	@RequestMapping(value = "/AGRO/GETCAMPO", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
////	public @ResponseBody ArrayList<CAMPO> getCAMPO(HttpServletRequest rquest,HttpSession httpSession) throws Exception {
////		
////		session ses = new session(httpSession);
////		
////		ArrayList<CAMPO> r = new ArrayList<CAMPO>();
////		
////		if (ses.isValid()) {
////			return r;
////		}
////		
////		r = simpleagroDB.getCAMPO();
////		System.out.println("asdasd");
////		
////		return r;
////	}
//	@RequestMapping(value = "/map/insertTrabajador", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean insertTrabajador(@RequestBody  trabajadores row,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//			return false;
//		}
//		return dteBD.insertTrabajador(row);
//
//	}
//	
//	
//	
//	
//	
//	
//	@RequestMapping(value = "/map/delTrabajador/{id}", method = {RequestMethod.GET,RequestMethod.POST})
//	public @ResponseBody boolean delTrabajador(@PathVariable  int id,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//			return false;
//		}
//		return dteBD.delTrabjador(id);
//
//	}
//	@RequestMapping(value = "/map/insertLogin", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean insertLogin(@RequestBody  loginApp row,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//			return false;
//		}
//		return dteBD.insertLogin(row);
//
//	}
//	@RequestMapping(value = "/map/loadPersonalById/{id}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<trabajadores> loadPersonalById(@PathVariable  int id, HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<trabajadores> r = new ArrayList<trabajadores>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.loadPersonalById(id);
//		
//		return r;
//
//	}
//	@RequestMapping(value = "/map/loadDepartamentos", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<departamentos> loadDepartamentos(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<departamentos> r = new ArrayList<departamentos>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.loadDepartamentos();
//		
//		return r;
//
//	}
//	@RequestMapping(value = "/map/addIncidencia", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean addIncidencia(@RequestBody  incidencia row,HttpSession httpSession) throws Exception {
//		
////		session ses = new session(httpSession);
////		
////		if (ses.isValid()) {
////			return false;
////		}
//		return dteBD.addIncidencia(row);
//
//	}
//	@RequestMapping(value = "/map/innerPersonal", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<trabajadores> innerPersonal(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<trabajadores> r = new ArrayList<trabajadores>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.innerPersonal();
//		
//		return r;
//
//	}
//	@RequestMapping(value = "/map/loadComunas", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<comuna> loadComunas(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<comuna> r = new ArrayList<comuna>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.loadComunas();
//		
//		return r;
//
//	}
//	@RequestMapping(value = "/map/addLabores", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean addLabores(@RequestBody  labores row,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//			return false;
//		}
//		return dteBD.addLabores(row);
//		
//		
//
//	}
//	@RequestMapping(value = "/map/loadMaterial", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<material> loadMaterial(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<material> r = new ArrayList<material>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.loadMaterial();
//		
//		return r;
//
//	}
//	@RequestMapping(value = "/map/AsignarIncidencia", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean AsignarIncidencia(@RequestBody  ArrayList<IN_INCIDENCIA_ASIGNADA> row,HttpSession httpSession) throws Exception {
//		for(IN_INCIDENCIA_ASIGNADA ped: row)
//		{
//			dteBD.AsignarIncidencia(ped);
//		}
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//			return false;
//		}
//		return true;
//		
//	}
//	@RequestMapping(value = "/map/updEstadoIncidencia", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean updEstadoIncidencia(@RequestBody  incidencia row,HttpSession httpSession) throws Exception {
//		return dteBD.updEstadoIncidencia(row);
//	}
//	@RequestMapping(value = "/map/CargaTest", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ArrayList<trabajadores> CargaTest(HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		ArrayList<trabajadores> r = new ArrayList<trabajadores>();
//		
//		if (ses.isValid()) {
//			return r;
//		}
//		
//		r = dteBD.loadPersonal();
//		
//		return r;
//
//	}
//}
