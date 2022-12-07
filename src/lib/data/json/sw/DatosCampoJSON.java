package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.DatosCampo;
import lib.db.sw.DatosCampoDB;


import lib.security.session;
@Controller
public class DatosCampoJSON {
	@RequestMapping(value = "/work/DatosCampo/getDatosCampo/", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<DatosCampo> getDatosCampo( HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		ArrayList<DatosCampo> params = new ArrayList<DatosCampo>();
		params = DatosCampoDB.getDatosCampo();
		if (ses.isValid()) {
			return params;
		}		
		return params;
	}
	@RequestMapping(value = "/work/DatosCampo/getDatosCampoById/{id}", method = {RequestMethod.GET})
	public @ResponseBody DatosCampo getEmpresaById(@PathVariable int id , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		DatosCampo e = new DatosCampo();
		e = DatosCampoDB.getDatosCampoById(id);
		if (ses.isValid()) {
			return e;
		}		
		return e;
	}
	@RequestMapping(value = "/work/DatosCampo/getDatosCampoBySociedad/{id}", method = {RequestMethod.GET})
	public @ResponseBody DatosCampo getDatosCampoBySociedad(@PathVariable String soc , HttpSession httpSession) throws Exception {
    	
		session ses = new session(httpSession);
		DatosCampo e = new DatosCampo();
		e = DatosCampoDB.getDatosCampoBySociedad(soc);
		if (ses.isValid()) {
			return e;
		}		
		return e;

	}
	@RequestMapping(value = "/work/DatosCampo/updateDatosCampo/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean updateDatosCampo(@RequestBody DatosCampo e,HttpSession httpSession) throws Exception {
    	 boolean resp = false;
    	 
    
    	 
		session ses = new session(httpSession);
		if (ses.isValid()) {
			return false;
		}
		
		resp = DatosCampoDB.updateDatosCampo(e);
        return resp;
	}
}
