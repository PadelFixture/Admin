package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.db.sw.ParentescoDB;
import lib.security.session;

@Controller
public class PruebasMauro {
	//Instanciamos clase que se conecta a BD
			private final static Logger LOG = LoggerFactory.getLogger(PruebasMauro.class);
			ParentescoDB emp = new ParentescoDB();
			
			@RequestMapping("/PruebasMauro")
			public ModelAndView PruebasMauroModulo(Model model,HttpSession httpSession) {
				LOG.info("Iniciamos la configuracion del modulo.");
				session ses= new session(httpSession);	
				if (ses.isValid())
				{
					return new ModelAndView("redirect:/webApp/login");
				}
				LOG.info("Session correcta.");
				model.addAttribute("menuActual","Mantenedores");
				model.addAttribute("paginaActual", "Pruebas Mauro");
				model.addAttribute("content", "pruebasMauro");
				model.addAttribute("javaScriptPage","pruebasMauro");
				LOG.info("Se termina de configurar modulo.");
				return new ModelAndView("layout/_main");
			}
			
			@RequestMapping("/content/PruebasMauro")
			public ModelAndView PruebasMauroContent(Model model,HttpSession httpSession) {
				LOG.info("Se empieza a obtener datos para cargar pagina.");
				session ses= new session(httpSession);
				if (ses.isValid())
				{
					return new ModelAndView("redirect:/webApp/login");
				}
				return new ModelAndView("content/PruebasMauro");
			}
}
