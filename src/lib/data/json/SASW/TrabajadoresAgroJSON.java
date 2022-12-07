package lib.data.json.SASW;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.ClassSASW.parametros;
import lib.classSA.TrabajadoresAgro;
import lib.db.SASW.TrabajadoresAgroDB;
import lib.security.session;

@Controller
public class TrabajadoresAgroJSON {
	
	@RequestMapping(value = "/AGRO/getTrabajadoresAgro", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody ArrayList<TrabajadoresAgro> getTrabajadoresAgro(HttpServletRequest request) throws Exception {
		
		String trabajadorAgricola = request.getParameter("TRABAJADOR");
		String campo = request.getParameter("CAMPO");
		String rut = request.getParameter("RUT");
		String cargo = request.getParameter("CARGO");
		String fecha = request.getParameter("FECHA");
		String contratista = request.getParameter("CONTRATISTA");
		
		ArrayList<TrabajadoresAgro> trabajadores = new ArrayList<TrabajadoresAgro>();
		trabajadores = TrabajadoresAgroDB.getTrabajadoresAgro(trabajadorAgricola, campo, rut, cargo, fecha,contratista);
		return trabajadores;
		
	}
	
	@RequestMapping(value = "/AGRO/getTrabajadoresAgro2", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody ArrayList<TrabajadoresAgro> getTrabajadoresAgro2(HttpServletRequest request) throws Exception {
		
		String trabajadorAgricola = request.getParameter("TRABAJADOR");
		String campo = request.getParameter("CAMPO");
		String rut = request.getParameter("RUT");
		String cargo = request.getParameter("CARGO");
		String fecha = request.getParameter("FECHA");
		String contratista = request.getParameter("CONTRATISTA");
		int digitador = 0;
		if(request.getParameter("DIGITADOR") != null) {
			digitador = Integer.parseInt(request.getParameter("DIGITADOR"));
		}
		System.out.println(digitador);
		ArrayList<TrabajadoresAgro> trabajadores = new ArrayList<TrabajadoresAgro>();
		trabajadores = TrabajadoresAgroDB.getTrabajadoresAgro2(trabajadorAgricola, campo, rut, cargo, fecha, contratista,digitador);
		return trabajadores;
		
	}
	
	
	
	
	@RequestMapping(value = "/AGRO/GET_CARGOS/", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody ArrayList<parametros> getSECTOR(HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		ArrayList<parametros> r = new ArrayList<parametros>();
		if (ses.isValid()) {
			return r;
		}
		r = TrabajadoresAgroDB.GET_CARGOS();
		return r;
	}
	@RequestMapping(value = "/AGRO/getDetalleTrabajadorAgro", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody ArrayList<TrabajadoresAgro> getDetalleTrabajadorAgro(HttpServletRequest request) throws Exception {
		
		String trabajadorAgricola = request.getParameter("TRABAJADOR");
		String campo = request.getParameter("CAMPO");
		int codigo = Integer.parseInt(request.getParameter("CODIGO"));
		String cargo = request.getParameter("CARGO");
		String fecha = request.getParameter("FECHA");
		String contratista = request.getParameter("CONTRATISTA");
		
		ArrayList<TrabajadoresAgro> trabajadores = new ArrayList<TrabajadoresAgro>();
		trabajadores = TrabajadoresAgroDB.getDetalleTrabajadorAgro(trabajadorAgricola, campo, codigo, cargo, fecha,contratista);
		return trabajadores;
		
	}
	

	@RequestMapping(value = "/AGRO/GET_TRABAJADORES_AGRO", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody ArrayList<TrabajadoresAgro> GET_TRABAJADORES_AGRO(HttpServletRequest request,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		int user = ses.getIdUser();
		String fecha = request.getParameter("FECHA");
		ArrayList<TrabajadoresAgro> trabajadores = new ArrayList<TrabajadoresAgro>();
		trabajadores = TrabajadoresAgroDB.GET_TRABAJADORES_AGRO(fecha, user);
		return trabajadores;
		
	}

	@RequestMapping(value = "/AGRO/SA_GETGETALLETRABAJADOR", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = {"*"})
	public @ResponseBody String SA_GETGETALLETRABAJADOR(HttpServletRequest request) throws Exception {
		
	
		int codigo = Integer.parseInt(request.getParameter("CODIGO"));
		String fecha = request.getParameter("FECHA");
		
		String trabajadores = "";
		trabajadores = TrabajadoresAgroDB.SA_GETGETALLETRABAJADOR(codigo, fecha);
		return trabajadores;
		
	}
	

}
