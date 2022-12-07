package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.security.session;

@Controller
public class VacacionesController {

	@RequestMapping("/vacaciones")
	public ModelAndView vacaciones(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		
		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Vacaciones");
		model.addAttribute("content", "vacaciones");
		model.addAttribute("javaScriptPage","vacaciones");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/vacaciones")
	public ModelAndView vacacionesContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/vacaciones");
	}
	
	
	@RequestMapping("/vacacionesPrograma")
	public ModelAndView vacacionesPrograma(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		
		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Vacaciones");
		model.addAttribute("content", "vacacionesPrograma");
		model.addAttribute("javaScriptPage","vacacionesPrograma");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/vacacionesPrograma")
	public ModelAndView vacacionesProgramaContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/vacacionesPrograma");
	}
	
	
	@RequestMapping("/SolicitudDeVacacionesVA")
	public ModelAndView SolicitudDeVacacionesVA(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Vacaciones");
		model.addAttribute("paginaActual", "Solicitud de Vacaciones");
		model.addAttribute("content", "SolicitudDeVacacionesVA");
		model.addAttribute("javaScriptPage","SolicitudDeVacacionesVA");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/SolicitudDeVacacionesVA")
	public ModelAndView SolicitudDeVacacionesVAContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/SolicitudDeVacacionesVA");
	}
	
	
	@RequestMapping("/SolicitudDeVacaciones")
	public ModelAndView SolicitudDeVacaciones(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Vacaciones");
		model.addAttribute("paginaActual", "Solicitud de Vacaciones");
		model.addAttribute("content", "SolicitudDeVacaciones");
		model.addAttribute("javaScriptPage","SolicitudDeVacaciones");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/SolicitudDeVacaciones")
	public ModelAndView SolicitudDeVacacionesContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/SolicitudDeVacaciones");
	}
	
	@RequestMapping("/SolicitudDeVacacionesProgresivas")
	public ModelAndView SolicitudDeVacacionesProgresivas(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Vacaciones");
		model.addAttribute("paginaActual", "Ingresador Vacaciones Progresivas");
		model.addAttribute("content", "SolicitudDeVacacionesProgresivas");
		model.addAttribute("javaScriptPage","SolicitudDeVacacionesProgresivas");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/SolicitudDeVacacionesProgresivas")
	public ModelAndView SolicitudDeVacacionesProgresivasContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/SolicitudDeVacacionesProgresivas");
	}
	
	@RequestMapping("/vacacionesPanel")
	public ModelAndView vacacionesPanelContract(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("content", "vacacionesPanel");
		model.addAttribute("javaScriptPage","vacacionesPanel");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/vacacionesPanel")
	public ModelAndView vacacionesPanelContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/vacacionesPanel");
	}
	
	
	
}
