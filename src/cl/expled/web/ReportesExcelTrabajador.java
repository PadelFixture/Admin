package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.security.session;

@Controller
public class ReportesExcelTrabajador {

	private final static Logger LOG = LoggerFactory.getLogger(ReportesExcelTrabajador.class);
	
	@RequestMapping("/ReportesTrabajador")
	public ModelAndView ReportesTrabajador(Model model,HttpSession httpSession) {
	
		session ses= new session(httpSession);	
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		
		model.addAttribute("menuActual","Informes");
		model.addAttribute("paginaActual", "Reportes Trabajador");
		model.addAttribute("content", "reportesTrabajador");
		model.addAttribute("javaScriptPage","reportesTrabajador");
	
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/ReportesTrabajador")
	public ModelAndView ReportesTrabajadorContent(Model model,HttpSession httpSession) {
		
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/ReportesTrabajador");
	}
	
	
}
