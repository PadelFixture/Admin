package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import lib.db.sw.TramoCargaDB;
import lib.security.session;

@Controller
public class tramoCarga {
	//Instanciamos clase que se conecta a BD
		private final static Logger LOG = LoggerFactory.getLogger(tramoCarga.class);
		TramoCargaDB emp = new TramoCargaDB();
		
		@RequestMapping("/tramoCarga")
		public ModelAndView tramoCargaModulo(Model model,HttpSession httpSession) {
			LOG.info("Iniciamos la configuracion del modulo.");
			session ses= new session(httpSession);	
			if (ses.isValid())
			{
				return new ModelAndView("redirect:/webApp/login");
			}
			LOG.info("Session correcta.");
			model.addAttribute("menuActual","Mantenedores");
			model.addAttribute("paginaActual", "Tramos Cargas");
			model.addAttribute("content", "tramoCarga");
			model.addAttribute("javaScriptPage","tramoCarga");
			LOG.info("Se termina de configurar modulo.");
			return new ModelAndView("layout/_main");
		}
		
		@RequestMapping("/content/tramoCarga")
		public ModelAndView tramoCargaContent(Model model,HttpSession httpSession) {
			LOG.info("Se empieza a obtener datos para cargar pagina.");
			session ses= new session(httpSession);
			if (ses.isValid())
			{
				return new ModelAndView("redirect:/webApp/login");
			}
			return new ModelAndView("content/tramoCarga");
		}
}
