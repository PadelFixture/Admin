package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.security.session;

@Controller
public class RutNoAutorizadosController {

	
	@RequestMapping("/RutNoAutorizados")
	public ModelAndView RutNoAutorizados(Model model, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}

		model.addAttribute("menuActual", "Mantenedores");
		model.addAttribute("paginaActual", "Rut No Autorizados");
		model.addAttribute("content", "RutNoAutorizados");
		model.addAttribute("javaScriptPage", "RutNoAutorizados");
		model.addAttribute("idRandom", Math.random());

		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/RutNoAutorizados")
	public ModelAndView RutNoAutorizadosContent(Model model, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/RutNoAutorizados");
	}
	
	
	
}
