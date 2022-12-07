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

import lib.SADB.MantecionDB;
import lib.SADB.PROGRAMA_FITOSANITARIO;
import lib.SADB.RENDIMIENTO;
import lib.SADB.ServicioExterno;
import lib.classSA.Consumo_Combustible;
import lib.classSA.RENDIMIENTO_DIARIO;
import lib.classSA.RENDIMIENTO_GENERAL;
import lib.classSA.Servicio_Externo;
import lib.classSA.gestion_material;
import lib.classSA.motivo_ingreso;
import lib.classSA.reingreso_taller;
import lib.classSA.taller;
import lib.classSW.trabajador;
import lib.db.ConnectionDB;
import lib.security.session;

@Controller
public class MantencionServices {
	@RequestMapping(value = "/AGRO/GET_MOTIVO_INGRESO/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<motivo_ingreso> GET_MOTIVO_INGRESO(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<motivo_ingreso> r = new ArrayList<motivo_ingreso>();
		if (ses.isValid()) {
			return r;
		}
		r = MantecionDB.GET_MOTIVO_INGRESO();
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_OPERADOR/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<trabajador> GET_OPERADOR(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		
		ArrayList<trabajador> r = new ArrayList<trabajador>();
		if (ses.isValid()) {
			return r;
		}
		r = MantecionDB.GET_OPERADOR();
		return r;
	}
	@RequestMapping(value = "/AGRO/ADD_TALLER/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_TALLER(@RequestBody  taller row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MantecionDB.ADD_TALLER(row);
	}

	@RequestMapping(value = "/AGRO/GET_TALLER_ALL/{campo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<taller> GET_TALLER_ALL(@PathVariable String[] campo ,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<taller> r = new ArrayList<taller>();
		if (ses.isValid()) {
			return r;
		}
		r = MantecionDB.GET_TALLER_ALL(campo);
		return r;
	}
	@RequestMapping(value = "/AGRO/CERRAR_INGRESO_TALLER/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean CERRAR_INGRESO_TALLER(@RequestBody taller row,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}	
		return MantecionDB.CERRAR_INGRESO_TALLER(row);
	}
	@RequestMapping(value = "/AGRO/ADD_REINGRESO_TALLER/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean ADD_REINGRESO_TALLER(@RequestBody  reingreso_taller row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MantecionDB.ADD_REINGRESO_TALLER(row);
	}
	
	@RequestMapping(value = "/AGRO/UPDATE_RESERVA_SOLPED/{codigo}/{numero}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPDATE_RESERVA_SOLPED(@PathVariable String codigo, @PathVariable String numero, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MantecionDB.UPDATE_RESERVA_SOLPED(codigo, numero);
	}

	@RequestMapping(value = "/AGRO/INSERT_GESTION_MATERIAL/{codigo}/{numero}/{tipo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean INSERT_GESTION_MATERIAL(@PathVariable String codigo, @PathVariable String numero, @PathVariable int tipo, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MantecionDB.INSERT_GESTION_MATERIAL(codigo, numero, tipo);
	}
	@RequestMapping(value = "/AGRO/UPDATE_GESTION_MATERIAL/{reserva}/{consumo}/{devolucion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean INSERT_GESTION_MATERIAL(@PathVariable String reserva, @PathVariable String consumo, @PathVariable String devolucion, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MantecionDB.UPDATE_GESTION_MATERIAL(reserva, consumo, devolucion);
	}
	@RequestMapping(value = "/AGRO/GET_DETALLE_SAP/{folio}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<gestion_material> GET_DETALLE_SAP(@PathVariable int folio,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<gestion_material> r = new ArrayList<gestion_material>();
		if (ses.isValid()) {
			return r;
		}
		r = MantecionDB.GET_DETALLE_SAP(folio);
		return r;
	}
	@RequestMapping(value = "/AGRO/DELETE_GESTION_MATERIAL/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DELETE_GESTION_MATERIAL(@PathVariable int id,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<gestion_material> r = new ArrayList<gestion_material>();
		if (ses.isValid()) {
			return false;
		}
		return MantecionDB.DELETE_GESTION_MATERIAL(id);
	}
	@RequestMapping(value = "/AGRO/GET_MAQUINAS_EN_TALLER", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<taller> GET_MAQUINAS_EN_TALLER(HttpServletRequest request,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<taller> r = new ArrayList<taller>();
		if (ses.isValid()) {
			return r;
		}
		String fecha = request.getParameter("FECHA");
		r = MantecionDB.GET_MAQUINAS_EN_TALLER(fecha);
		return r;
	}
	@RequestMapping(value = "/AGRO/DEL_INGRESO_TALLER/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DEL_INGRESO_TALLER(@PathVariable int id,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<gestion_material> r = new ArrayList<gestion_material>();
		if (ses.isValid()) {
			return false;
		}
		return MantecionDB.DEL_INGRESO_TALLER(id);
	}
	@RequestMapping(value = "/AGRO/GET_TALLER_ID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody taller GET_TALLER_ID(HttpServletRequest request,HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		taller r = new taller();
		if (ses.isValid()) {
			return r;
		}
		int codigo = Integer.parseInt(request.getParameter("CODIGO"));
		r = MantecionDB.GET_TALLER_ID(codigo);
		return r;
	}
	@RequestMapping(value = "/AGRO/UPD_TALLER/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean UPD_TALLER(@RequestBody  taller row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return MantecionDB.UPD_TALLER(row);
	}
	@RequestMapping(value = "/AGRO/REPORTE_CONSUMO_EQUIPO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<taller> REPORTE_CONSUMO_EQUIPO(HttpServletRequest request,  HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<taller> r = new ArrayList<taller>();
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		String fecha_desde = request.getParameter("FECHA_DESDE");
		String fecha_hasta = request.getParameter("FECHA_HASTA");
		if(campo.equals("0")){
			campo = "SELECT codigo_campo from usuario_campo where codigo_usuario = "+ses.getIdUser()+"";
		}else{
			campo = "'"+campo+"'";
		}
		
		return MantecionDB.REPORTE_CONSUMO_EQUIPO(campo, fecha_desde, fecha_hasta);
	}
	@RequestMapping(value = "/AGRO/GET_CONSUMO_PETROLEO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GET_CONSUMO_PETROLEO(HttpSession httpSession, HttpServletRequest request) throws Exception {
		session ses = new session(httpSession);
		String r = "";
		if (ses.isValid()) {
			return r;
		}
		String campo = request.getParameter("CAMPO");
		r = PROGRAMA_FITOSANITARIO.GET_CONSUMO_PETROLEO(campo); 			
		return r;
	}
	@RequestMapping(value = "/AGRO/GET_DETALLE_CONSUMO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<Consumo_Combustible> GET_DETALLE_CONSUMO(HttpSession httpSession, HttpServletRequest request) throws Exception {
		session ses = new session(httpSession);
		ArrayList<Consumo_Combustible> r = new ArrayList<Consumo_Combustible>();
		if (ses.isValid()) {
			return r;
		}
		String material = request.getParameter("MATERIAL");
		r = PROGRAMA_FITOSANITARIO.GET_DETALLE_CONSUMO(material); 			
		return r;
	}
	@RequestMapping(value = "/AGRO/DEL_CONSUMO_MATERIAL/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean DEL_CONSUMO_MATERIAL(@RequestBody  Consumo_Combustible row, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		return PROGRAMA_FITOSANITARIO.DEL_CONSUMO_MATERIAL(row);
	}
}