package lib.data.json.sw;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.security.session;
import wordCreator.utils;

@Controller
public class RutaJSON {
	
	@RequestMapping(value = "/work/getRuta/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String  getRuta(HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		String es = null;
		
		if (ses.isValid()) {
			return es;
		}
		
		String var = utils.obtenerCarpetaServidor();
		return var;

	}
////////////////////////////////obtener ruta permiso Sin Goce de sueldo////////////////////////////////////////
	@RequestMapping(value = "/work/getRutaSinGoceDeSueldo/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String  getRutaPSGS(HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		String es = null;
		
		if (ses.isValid()) {
			return es;
		}
		
		String var = utils.PermisoSGS();
		return var;
     
	}
////////////////// obtener ruta licencia///////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = "/work/getRutaLicencia/", method = {RequestMethod.GET,RequestMethod.POST})	
	public @ResponseBody String  getRutaLicencia(HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		String es = null;
		
		if (ses.isValid()) {
			return es;
		}
		
		String var = utils.Licencia();
		return var;

	}
	
	
	@RequestMapping(value = "/work/getServerFolder/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getServerFolder(@RequestParam("NAMEFOLDER") String nameFolder, HttpSession httpSession) throws Exception {
		session ses = new session(httpSession);
		String es = null;
		
		if (ses.isValid()) {
			return es;
		}
		
		String ruta = utils.getServerFolder(nameFolder);
		return ruta;

	}
	
	
	
	
	
	
}
