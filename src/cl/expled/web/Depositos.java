package cl.expled.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.security.session;

@Controller
public class Depositos { 
	@RequestMapping("/DepositoSub")
	public ModelAndView DepositoSub(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Empresas");
		model.addAttribute("content", "DepositoSub");
		model.addAttribute("javaScriptPage","DepositoSub");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/DepositoSub")
	public ModelAndView DepositoSubContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/DepositoSub");
	}
	@RequestMapping("/DepositoCom")
	public ModelAndView DepositoCom(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Empresas");
		model.addAttribute("content", "DepositoCom");
		model.addAttribute("javaScriptPage","DepositoCom");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/DepositoCom")//DepositoReport
	public ModelAndView DepositoComContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/DepositoCom");
	}
	@RequestMapping("/DepositoReport")
	public ModelAndView DepositoReport(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		model.addAttribute("menuActual", "Importacion");
		model.addAttribute("paginaActual", "Empresas");
		model.addAttribute("content", "DepositoReport");
		model.addAttribute("javaScriptPage","DepositoReport");
		model.addAttribute("idRandom", Math.random());
		return new ModelAndView("layout/_main");
	}
	
	@RequestMapping("/content/DepositoReport")//DepositoReport
	public ModelAndView DepositoReportContent(Model model,HttpSession httpSession) {
		session ses= new session(httpSession);
		if (ses.isValid())
		{
			return new ModelAndView("redirect:/webApp/login");
		}
		return new ModelAndView("content/DepositoReport");
	}
}
