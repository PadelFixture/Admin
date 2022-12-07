package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.db.sw.TurnoSupervisorDB;
import lib.security.session;

public class TurnoSupervisor {
	//Instanciamos clase que se conecta a BD
			private final static Logger LOG = LoggerFactory.getLogger(TurnoSupervisor.class);
			TurnoSupervisorDB emp = new TurnoSupervisorDB();
			
			@RequestMapping("/TurnoSupervisor")
			public ModelAndView TurnoSupervisorModulo(Model model,HttpSession httpSession) {
				LOG.info("Iniciamos la configuracion del modulo.");
				session ses= new session(httpSession);	
				if (ses.isValid())
				{
					return new ModelAndView("redirect:/webApp/login");
				}
				LOG.info("Session correcta.");
				model.addAttribute("menuActual","Mantenedores");
				model.addAttribute("paginaActual", "Turnos Supervisor");
				model.addAttribute("content", "TurnoSupervisor");
				model.addAttribute("javaScriptPage","TurnoSupervisor");
				LOG.info("Se termina de configurar modulo.");
				return new ModelAndView("layout/_main");
			}
			
			@RequestMapping("/content/TurnoSupervisor")
			public ModelAndView TurnoSupervisorContent(Model model,HttpSession httpSession) {
				LOG.info("Se empieza a obtener datos para cargar pagina.");
				session ses= new session(httpSession);
				if (ses.isValid())
				{
					return new ModelAndView("redirect:/webApp/login");
				}
				return new ModelAndView("content/TurnoSupervisor");
			}
}
