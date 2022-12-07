package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.security.session;

/*
 * author: Crunchify.com
 * 
 */

@Controller
public class mapeo {
	@RequestMapping("/homePage")
	public ModelAndView homePage(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("google_map", "src='https://maps.googleapis.com/maps/api/js?key=AIzaSyA-gfDufUPg8zkB-VRiVUiudqMfTYWa3GY&libraries=drawing,geometry,places&callback=initMap' async defer");
		model.addAttribute("content", "homePage");
		model.addAttribute("menuActual", "Mapeo");
		model.addAttribute("paginaActual", "Crear y buscar Cuarteles");
		model.addAttribute("javaScriptPage","map");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/homePage")
	public ModelAndView homeContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/homePage");
	}
	@RequestMapping("/infoPage")
	public ModelAndView infoPage(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("content", "infoPage");
		model.addAttribute("menuActual", "Mapeo");
		model.addAttribute("paginaActual", "Detalle Cuartel");
		model.addAttribute("javaScriptPage","infoPage");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/infoPage")
	public ModelAndView infoContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/infoPage");
	}
	
	@RequestMapping("/incidencias")
	public ModelAndView incidencias(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("content", "incidencias");
		model.addAttribute("menuActual", "SimpleAgro");
		model.addAttribute("paginaActual", "Incidencias");
		model.addAttribute("javaScriptPage","incidencias");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/incidencias")
	public ModelAndView incidenciasContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/incidencias");
	}
	
	@RequestMapping("/index")
	public ModelAndView index(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("content", "index");
		model.addAttribute("menuActual", "Recotec ");
		model.addAttribute("paginaActual", "");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}

	@RequestMapping("/content/index")
	public ModelAndView indexContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/index");
	}
}
