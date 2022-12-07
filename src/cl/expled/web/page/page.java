package cl.expled.web.page;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import lib.db.systemMenuDB;
import lib.security.session;

import lib.struc.systemMenu;

@Controller
public class page {
	
	
	//constructor de toda la pagina
	@RequestMapping(value= "/page/{id:.+}", method = { RequestMethod.GET })
	public ModelAndView mainDefault(Model model, @PathVariable("id") String id, HttpSession httpSession) {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		System.out.println("entro al menu");
		systemMenu m = new systemMenu();
		try {
			m = systemMenuDB.getMenuUrl(id);
			model.addAttribute("proceso", m.getProceso());
			model.addAttribute("pagina", m.getMenu());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		model.addAttribute("content", id);
		model.addAttribute("controller", "content/"+id);
		model.addAttribute("javaScriptPage", id.replace(".", "/"));
		model.addAttribute("rand", ThreadLocalRandom.current().nextInt(10000, 99999 + 1));
		
		
		
		return new ModelAndView("layout/_main");
	}
	//constructor de toda la pagina
		@RequestMapping(value= "/pageAdm/{id:.+}", method = { RequestMethod.GET })
		public ModelAndView mainAdm(Model model, @PathVariable("id") String id, HttpSession httpSession) {
			session ses = new session(httpSession);
			if (ses.isValid()) {
				return new ModelAndView("redirect:/webApp/login");
			}
			systemMenu m;
			try {
				m = systemMenuDB.getMenuUrl(id);
				model.addAttribute("proceso", m.getProceso());
				model.addAttribute("pagina", m.getMenu());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			model.addAttribute("content", id);
			model.addAttribute("controller", "adm/"+id.replace(".", "_"));
			model.addAttribute("javaScriptPage", id.replace(".", "/"));
			model.addAttribute("rand", ThreadLocalRandom.current().nextInt(10000, 99999 + 1));
			
			
			return new ModelAndView("layout/_main");
		}
	@RequestMapping("/content/{id:.+}")
	public ModelAndView contentDefault(Model model, @PathVariable("id") String id, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return new ModelAndView("redirect:/webApp/login");
		}
		
		

		return new ModelAndView("content/"+id.replace(".jsp", "").replace(".", "/"));
	}

}
