package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.security.session;

@Controller
public class TrabajadoresReclutamiento {
	
	@RequestMapping("/Reclutamiento")
	public ModelAndView Contratistas(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Trabajador Reclutamiento");
		model.addAttribute("content", "Reclutamiento");
		model.addAttribute("javaScriptPage","Reclutamiento");
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/Reclutamiento")
	public ModelAndView contratistasContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/Reclutamiento");
	}
	
	

	@RequestMapping("/trabajadorReclutamiento")
	public ModelAndView trabajadorReclutamiento(Model model, HttpSession httpSession) {
		model.addAttribute("content", "trabajadorReclutamiento");
		model.addAttribute("javaScriptPage", "trabajadorReclutamiento");
		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Trabajadores Reclutamiento");
		
		session ses = new session(httpSession);
		if(ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}		
		return new ModelAndView("layout/_main");
				
	}
	
	
	@RequestMapping("/content/trabajadorReclutamiento")
	public ModelAndView trabajadorReclutamientoContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/trabajadorReclutamiento");
	}
	
}
