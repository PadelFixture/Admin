package cl.expled.web;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lib.SADB.INCIDENCIAS;
import lib.classSW.Usuario;
import lib.db.conSimpleAgro;
import lib.db.simpleagroDB;
import lib.db.sw.UsuarioDB;
import lib.db.sw.trabajadoresDB;
import lib.sesionSA.SESION;
import lib.struc.filterSql;
import lib.struc.loginApp;
import lib.utils.GeneralUtility;


/*
 * author: Crunchify.com
 * 
 */

@Controller
public class login extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HttpSession sesion;
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView loginPage(Model model, HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
		Map<String, String[]> parameters = request.getParameterMap();
		
		
		try {
			sesion = request.getSession();
			String username =new String(parameters.get("username")[0].getBytes("ISO-8859-1"),"UTF-8");
			String pass     =new String(parameters.get("password")[0].getBytes("ISO-8859-1"),"UTF-8");
			
			//Obtener todos los parametros del URL

			ArrayList<filterSql> filter = new ArrayList<filterSql>();

			//Obtener todos los parametros enviados por el URL
			filterSql fil1= new filterSql();
			fil1.setCampo("usuario");
			fil1.setValue(GeneralUtility.FormatearRUT(username));
			filter.add(fil1);
			filterSql fil2= new filterSql();
			fil2.setCampo("clave");
			fil2.setValue(pass);
			filter.add(fil2);
			
//			ArrayList<Usuario> usuario = UsuarioDB.getUsuarios(filter);
			
			
//			if(!usuario.isEmpty()){
//				lib.security.session  ses= new lib.security.session(httpSession);
//				sesion.setAttribute("perfil", "trabajador");
//				sesion.setAttribute("idPerfil", "9");
//				sesion.setAttribute("id",usuario.get(0).getCodigo());
//				SESION mc= new SESION(httpSession);
//				mc.addIdUser(trabajadoresDB.getIdTrabajadorByCodigo(usuario.get(0).getCodigo()));
//				mc.addUser(usuario.get(0).getCodigo());
//				sesion.setAttribute("actualSesion", mc.getView());
//				model.addAttribute("cuartel", simpleagroDB.getCUARTEL(httpSession));
//				ses.setRolPrivado(0);
//				ses.setIdPerfil(9);
//				ses.init();
//				return new ModelAndView("redirect:/webApp/Intranet");
//			}
			
			
			loginApp us = conSimpleAgro.getLogin(username, pass);
			if ( us != null)  {
				JSONObject user = new JSONObject();
				System.out.println("club" + us.getClub());
				user.put("ID", us.getId());
				user.put("USUARIO", us.getUsuario());
				//user.put("PerfilText", us.getPerfilText());
				user.put("PERFIL", us.getPerfil());
				user.put("TIPO_RECEPTOR", us.getGrupoCompra());
				user.put("CLUB", us.getClub());
				user.put("PADRE", us.getSolicitante());
				System.out.println(user.toString());
				sesion.setAttribute("USER", user.toString());
				sesion.setAttribute("usuario", us.getUsuario());
				sesion.setAttribute("perfil", us.perfilText);
				sesion.setAttribute("id",us.getId());
				sesion.setAttribute("PERFIL",String.valueOf(us.getPerfil()) );
				lib.security.session  ses= new lib.security.session(httpSession);
				ses.setIdUser(us.getId());
				ses.setIdPerfil(us.getPerfil());
				ses.setGrupoCompra(us.getGrupoCompra());
				ses.setSolicitante(us.getSolicitante());
				ses.setRolPrivado(us.getClub());
				ses.init();
				return new ModelAndView("redirect:/webApp/index");
			} else {
				return new ModelAndView("login");
			}
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("alerta", "display-hide");
			return new ModelAndView("login");
		}
	}

	/*@RequestMapping("/index")
	public ModelAndView helloWorld2(Model model) {
		model.addAttribute("id", "jajajajaj :)");
		String message = "esto es una maldita prueba";
		return new ModelAndView("index", "message", message);
	}*/
	
	@RequestMapping("/exit")
	public ModelAndView exit(Model model, HttpSession httpSession) {
		lib.security.session  ses= new lib.security.session(httpSession);
		ses.close();
		return new ModelAndView("redirect:/webApp/login");
	}

}