package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.Comuna;
import lib.db.sw.ComunaDB;
import lib.security.session;

@Controller
public class ComunaJson {

	
	//Obtener Todas las Comuna 
	@RequestMapping(value = "/work/getAllComuna", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<Comuna> getAllComuna(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Comuna> Comuna = new ArrayList<Comuna>();

		if (ses.isValid()) {
			return Comuna;
		}

		return Comuna = ComunaDB.getAllComuna();

	}
	
	
	
	//Obtener Comuna por Providencia
	@RequestMapping(value = "/work/getComunaByIdProvincia/{idProvincia}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<Comuna> getComunaByIdProvincia(@PathVariable String idProvincia ,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Comuna> comuna = new ArrayList<Comuna>();

		if (ses.isValid()) {
			return comuna;
		}

		return comuna = ComunaDB.getComunaByIdProvincia(idProvincia);

	}



}
