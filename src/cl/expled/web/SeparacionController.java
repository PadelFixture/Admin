package cl.expled.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.classSW.ArticuloTerminoContrato;
import lib.classSW.IncisoTerminoContrato;
import lib.classSW.LetraTerminoContrato;
import lib.db.sw.ArticuloTerminoContratoDB;
import lib.db.sw.IncisoTerminoContratoDB;
import lib.db.sw.LetraTerminoContratoDB;
import lib.security.session;

@Controller
public class SeparacionController {

	@RequestMapping("/separacion")
	public ModelAndView separacion(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		
		model.addAttribute("menuActual", "Colaboradores");
		model.addAttribute("paginaActual", "Separacion");
		model.addAttribute("content", "separacion");
		model.addAttribute("javaScriptPage","separacion");
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/separacion")
	public ModelAndView separacionContent(Model model,HttpSession httpSession) throws Exception {
		
		session ses= new session(httpSession);
		if (ses.isValid()){
			return new ModelAndView("redirect:/webApp/login");
		}
		
		// Articulos para Termino de Contrato
		List<ArticuloTerminoContrato> listaArticuloTerminoContrato = new ArrayList<ArticuloTerminoContrato>();
		listaArticuloTerminoContrato = ArticuloTerminoContratoDB.getArticuloTerminoContrato();
		model.addAttribute("listaArticuloTerminoContrato", listaArticuloTerminoContrato);
		
		// Inciso para Termino de Contrato
		List<IncisoTerminoContrato> listaIncisoTerminoContrato = new ArrayList<IncisoTerminoContrato>();
		listaIncisoTerminoContrato = IncisoTerminoContratoDB.getIncisoTerminoContrato();
		model.addAttribute("listaIncisoTerminoContrato", listaIncisoTerminoContrato);
		
		// Letra para Termino de Contrato
		List<LetraTerminoContrato> listaLetraTerminoContrato = new ArrayList<LetraTerminoContrato>();
		listaLetraTerminoContrato = LetraTerminoContratoDB.getLetraTerminoContrato();
		model.addAttribute("listaLetraTerminoContrato", listaLetraTerminoContrato);
		
		return new ModelAndView("content/separacion");
	}
	
}
