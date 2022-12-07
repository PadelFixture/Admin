package cl.expled.web.layout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lib.classSW.MenuPerfil;
import lib.db.systemMenuDB;
import lib.security.session;
import lib.sesionSA.SESION;

@Controller
public class Element {
	
	
	
	
	
	@RequestMapping(value = "/layout_menu/{id:.+}", method = { RequestMethod.GET })
	public ModelAndView menu(Model model, @PathVariable String id, HttpSession httpSession, HttpServletRequest request) throws Exception {
		session ses = new session(httpSession);
		
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		/*jcwhite: Obtener privilegios de la pantalla (ver, modificar, eliminar)
		 * */
		MenuPerfil menuPerfil = new MenuPerfil();
		menuPerfil = systemMenuDB.getSystemMenuPerfil(id, ses.getIdPerfil());
		
		HttpSession sesion = request.getSession();
		SESION mc= new SESION(httpSession);
		mc.addEliminar(menuPerfil.getEliminar());
		mc.addEditar(menuPerfil.getEditar());
		mc.addVer(menuPerfil.getVer());
		
		sesion.setAttribute("actualSesion", mc.getView());
		
		System.out.println(  ses.getIdPerfil());
		System.out.println(id);
		menu m= new menu();
		String[] strMenu= m.create(0,id,ses.getIdPerfil());

		model.addAttribute("menu", strMenu[1]);
		model.addAttribute("hola", "2");
		System.out.println("////////");
		for(int i = 0; i < strMenu.length; i++){
			System.out.println(strMenu[i]);
		}
		return new ModelAndView("layout/menu");
	}

	@RequestMapping("/layout_user")
	public ModelAndView menu_user(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("nombre", ses.getValue("nombre"));
		String message = "esto es una maldita prueba";
		return new ModelAndView("layout/user", "message", message);
	}

	@RequestMapping("/layout_alert")
	public ModelAndView Alert(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("id", "jajajajaj :)");
		String message = "esto es una maldita prueba";
		return new ModelAndView("layout/alert", "message", message);
	}

	@RequestMapping("/layout_config")
	public ModelAndView Config(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("id", "jajajajaj :)");
		String message = "esto es una maldita prueba";
		return new ModelAndView("layout/config", "message", message);
	}

}
