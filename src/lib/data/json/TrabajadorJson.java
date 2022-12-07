package lib.data.json;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lib.db.dteBD;
import lib.db.sesionVar;
//import lib.db.sesionWork;
import lib.jsonMap.jsonReclutamiento;
import lib.jsonMap.jsonSesionVars;
//import lib.jsonMap.purchaseMap;
import lib.db.conSimpleAgro;
import lib.security.session;
import lib.struc.cuadrilla;
import lib.struc.laborCuadrilla;
import lib.struc.loginApp;
import lib.struc.mapConection;
import lib.struc.miembrosCuadrilla;
import lib.struc.reclutamiento;
import lib.struc.trabajadores;

@Controller
public class TrabajadorJson {
	
//	@RequestMapping(value = "/work/add", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody boolean addReclutamiento(@RequestBody  ArrayList<trabajadores> row,HttpSession httpSession) throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		if (ses.isValid()) {
//			return false;
//		}
//		sesionWork rc= new sesionWork(httpSession);
//		
//		for(trabajadores trab: row){
//			rc.addTrab(trab, trab.getId());
//		}
//		return true;
//	}
//	@RequestMapping(value = "/work/getReclutamiento", method = { RequestMethod.GET })
//	public @ResponseBody jsonReclutamiento getReclutamiento(HttpServletRequest request, HttpSession httpSession)throws Exception {
//		
//		session ses = new session(httpSession);
//		
//		
//		sesionWork rc= new sesionWork(httpSession);
//		
//		return rc.getReclutamineto();
//
//	}
	@RequestMapping(value = "/trabajador/getTrabajador", method={RequestMethod.GET})
	public @ResponseBody trabajadores getTrabajador(HttpServletRequest request) throws Exception
	{
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		trabajadores trab = dteBD.consultaTrabajadores(codigo);
		return trab;
	}
	@RequestMapping(value = "/trabajador/insertCuadrilla", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean sendInfo(@RequestBody  ArrayList<cuadrilla> row,HttpSession httpSession) throws Exception {
		int capataz = 0;
		for(cuadrilla ped: row)
		{
			capataz = ped.getId_encargado();
		}
		conSimpleAgro.inCuadrilla(capataz);
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		return true;
		
	}
	@RequestMapping(value = "/trabajador/getLogueo", method={RequestMethod.GET})
	public @ResponseBody loginApp getLogueo(HttpServletRequest request) throws Exception
	{
		loginApp user = conSimpleAgro.getLogin(request.getParameter("User"), request.getParameter("Pass"));
		return user;
	}
	@RequestMapping(value = "/trabajador/getTrabajadorId", method={RequestMethod.GET})
	public @ResponseBody trabajadores getTRabajadorId(HttpServletRequest request) throws Exception
	{
		int id = Integer.parseInt(request.getParameter("id"));
		trabajadores trab = conSimpleAgro.consultaTrabajadoresID(id);
		return trab;
	}
	@RequestMapping(value = "/trabajador/buscarIdCuadrilla", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<cuadrilla> buscar(HttpSession httpSession) throws Exception {
		
		ArrayList<cuadrilla> r = new ArrayList<cuadrilla>();
		r = conSimpleAgro.buscarIdCuadrilla();
		return r;

	}
	@RequestMapping(value = "/trabajador/insertMiemCuadrilla", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean holaMundo(@RequestBody  ArrayList<miembrosCuadrilla> row,HttpSession httpSession) throws Exception {
		
		for(miembrosCuadrilla mim: row)
		{
			int id_cuadrilla = mim.getId_cuadrilla();
			int id_trabajador = mim.getId_trabajador();
			conSimpleAgro.insertMiembros(id_cuadrilla, id_trabajador);
		}
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return false;
		}
		return true;
		
	}
	@RequestMapping(value = "/trabajador/getCuadrilla", method={RequestMethod.GET})
	public @ResponseBody ArrayList<cuadrilla> getCuadrilla(HttpServletRequest request) throws Exception
	{
		ArrayList<cuadrilla> cua = new ArrayList<cuadrilla>();
		cua = conSimpleAgro.getCuadrilla();
		return cua;
	}
	@RequestMapping(value = "/trabajador/getMiemCuadrilla", method={RequestMethod.GET})
	public @ResponseBody ArrayList<trabajadores> getMiemCuadrilla(HttpServletRequest request) throws Exception
	{
		int id_cuadrilla = Integer.parseInt(request.getParameter("id_cuadrilla"));
		ArrayList<trabajadores> trab = conSimpleAgro.getMiemCuadrilla(id_cuadrilla);
		return trab;
	}
	@RequestMapping(value = "/trabajador/insertActividad", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertActividad(@RequestBody laborCuadrilla row,HttpSession httpSession) throws Exception {

		return conSimpleAgro.insertActividad(row);

	}
	@RequestMapping(value = "/map/loadActCuadrilla", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<laborCuadrilla> loadActCuadrilla(HttpSession httpSession) throws Exception {
		
		ArrayList<laborCuadrilla> cua = new ArrayList<laborCuadrilla>();
		cua = conSimpleAgro.getActCuadrilla();
		return cua;

	}
//	@RequestMapping(value = "/incidencias/loadAllIncidencia", method={RequestMethod.GET})
//	public @ResponseBody ArrayList<purchaseMap> loadAllIncidencia(HttpServletRequest request) throws Exception
//	{
//		ArrayList<purchaseMap> trab = new ArrayList<purchaseMap>();
//		trab = conSimpleAgro.loadAllIncidencia();
//		return trab;
//	}
}
