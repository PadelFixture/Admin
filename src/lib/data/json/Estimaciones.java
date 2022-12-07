package lib.data.json;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.SADB.Categoria;
import lib.SADB.MAPA;
import lib.SADB.RENDIMIENTO;
import lib.classSA.CATEGORIA;
import lib.classSA.LIQUIDACION;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.RENDIMIENTO_GENERAL;
import lib.classSA.calibre_prd;
import lib.classSA.estimacion_data_prd;
import lib.classSA.estimacion_productiva;
import lib.classSA.parametros_estimacion;
import lib.classSA.respuesta;
import lib.db.ConnectionDB;
import lib.security.session;
import lib.struc.trabajadores;
@Controller
public class Estimaciones {

	@RequestMapping(value = "/AGRO/ADD_Categoria/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_Categoria(@RequestBody  CATEGORIA row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Categoria.ADD_Categoria(row);
	}
	@RequestMapping(value = "/AGRO/ADD_PARAMETRO_ESTIMACION/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_PARAMETRO_ESTIMACION(@RequestBody parametros_estimacion row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Categoria.ADD_PARAMETRO_ESTIMACION(row);
	}
	@RequestMapping(value = "/AGRO/GET_PARAMETRO_ESTIMACION/{campo}/{especie}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<parametros_estimacion> GET_PARAMETRO_ESTIMACION(@PathVariable String campo, @PathVariable int especie, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<parametros_estimacion> r = new ArrayList<parametros_estimacion>();
		if (ses.isValid()) {
			return r;
		}
		r = Categoria.GET_PARAMETRO_ESTIMACION(campo, especie);
		return r;
	}
	@RequestMapping(value = "/AGRO/DELETE_PARAMETRO_ESTIMACION/{codigo}", method = {RequestMethod.PUT,RequestMethod.POST})
	public @ResponseBody boolean DELETE_PARAMETRO_ESTIMACION(@PathVariable int codigo,HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		return Categoria.DELETE_PARAMETRO_ESTIMACION(codigo);

	}
	@RequestMapping(value = "/AGRO/ADD_ESTIMACION_PRODUCTIVA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_ESTIMACION_PRODUCTIVA(@RequestBody estimacion_productiva row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Categoria.ADD_ESTIMACION_PRODUCTIVA(row);
	}
	@RequestMapping(value = "/AGRO/GET_ESTIMACION_PRODUCTIVA/{campo}/{especie}/{cuartel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<estimacion_productiva> GET_ESTIMACION_PRODUCTIVA(@PathVariable String campo, @PathVariable int especie, @PathVariable int cuartel, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<estimacion_productiva> r = new ArrayList<estimacion_productiva>();
		if (ses.isValid()) {
			return r;
		}
		r = Categoria.GET_ESTIMACION_PRODUCTIVA(campo, especie, cuartel);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_SEMANAS_21", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<String> GET_SEMANAS_21(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<String> r = new ArrayList<String>();
		if (ses.isValid()) {
			return r;
		}
		r = Categoria.GET_SEMANAS_21();
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_ESTIMACION_21PRD", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<respuesta> ADD_ESTIMACION_21PRD(@RequestBody  ArrayList<estimacion_data_prd> row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<respuesta> res = new ArrayList<respuesta>();
		if (ses.isValid()) {
			return res;
		}
		for(estimacion_data_prd r: row){
			ConnectionDB db = new ConnectionDB();
			respuesta e = new respuesta();
			e = Categoria.ADD_ESTIMACION_21PRD(db, r,httpSession);
			res.add(e);
		}
		return res;
	}
	@RequestMapping(value = "/AGRO/GET_ESTIMACION_PRD21", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<String[]> GET_ESTIMACION_PRD21(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<String[]> r = new ArrayList<String[]>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		int especie = Integer.parseInt(request.getParameter("ESPECIE"));
		int variedad = Integer.parseInt(request.getParameter("VARIEDAD"));
		r = Categoria.GET_ESTIMACION_PRD21(campo, especie, variedad);
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_CALIBRE_PRD21", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<calibre_prd> GET_CALIBRE_PRD21 (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<calibre_prd> r = new ArrayList<calibre_prd>();
		if (ses.isValid()) {
			return r;
		}
		int especie = Integer.parseInt(request.getParameter("ESPECIE"));
		r = Categoria.GET_CALIBRE_PRD21(especie);  
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CALIBREEST_PRD21", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<respuesta> ADD_CALIBREEST_PRD21(@RequestBody  ArrayList<calibre_prd> row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<respuesta> res = new ArrayList<respuesta>();
		if (ses.isValid()) {
			return res;
		}
		for(calibre_prd r: row){
			ConnectionDB db = new ConnectionDB();
			respuesta e = new respuesta();
			e = Categoria.ADD_CALIBREEST_PRD21(db, r,httpSession);
			res.add(e);
		}
		return res;
	}
	@RequestMapping(value = "/AGRO/GET_CATEGORIA_PRD21", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<calibre_prd> GET_CATEGORIA_PRD21 (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<calibre_prd> r = new ArrayList<calibre_prd>();
		if (ses.isValid()) {
			return r;
		}
		int especie = Integer.parseInt(request.getParameter("ESPECIE"));
		r = Categoria.GET_CATEGORIA_PRD21(especie);  
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CATEGORIAEST_PRD21", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<respuesta> ADD_CATEGORIAEST_PRD21(@RequestBody  ArrayList<calibre_prd> row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<respuesta> res = new ArrayList<respuesta>();
		if (ses.isValid()) {
			return res;
		}
		System.out.println("e");
		for(calibre_prd r: row){
			ConnectionDB db = new ConnectionDB();
			respuesta e = new respuesta();
			e = Categoria.ADD_CATEGORIAEST_PRD21(db, r,httpSession);
			res.add(e);
		}
		return res;
	}
	@RequestMapping(value = "/AGRO/GET_CALIBRE_ESPECIE", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<calibre_prd> GET_CALIBRE_ESPECIE (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<calibre_prd> r = new ArrayList<calibre_prd>();
		if (ses.isValid()) {
			return r;
		}
		int especie = Integer.parseInt(request.getParameter("ESPECIE"));
		r = Categoria.GET_CALIBRE_ESPECIE(especie);  
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_CALIBRE_CATEGORIA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_CALIBRE_CATEGORIA(@RequestBody calibre_prd row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return Categoria.ADD_CALIBRE_CATEGORIA(row);
	}
	@RequestMapping(value = "/AGRO/GET_CATEGORIA_ESPECIE", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<calibre_prd> GET_CATEGORIA_ESPECIE (HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<calibre_prd> r = new ArrayList<calibre_prd>();
		if (ses.isValid()) {
			return r;
		}
		int especie = Integer.parseInt(request.getParameter("ESPECIE"));
		r = Categoria.GET_CATEGORIA_ESPECIE(especie);  
		return r;
	}
	@RequestMapping(value = "/AGRO/UPD_CALIBRE_CATEGORIA/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_CALIBRE_CATEGORIA(@RequestBody  calibre_prd row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		ConnectionDB db = new ConnectionDB();
		return Categoria.UPD_CALIBRE_CATEGORIA(row);
	}
	@RequestMapping(value = "/AGRO/GET_REPORTE_ESTIMACIONPRD", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<String[]> GET_REPORTE_ESTIMACIONPRD(HttpServletRequest request, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<String[]> r = new ArrayList<String[]>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		int variedad = Integer.parseInt(request.getParameter("VARIEDAD"));
		r = Categoria.GET_REPORTE_ESTIMACIONPRD(campo, variedad);
		return r;
	}
}
