package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.Provincia;
import lib.db.sw.ProvinciaDB;
import lib.security.session;

@Controller
public class ProvinciaJson {


	//Obtener Todas las Provincias 
	@RequestMapping(value = "/work/getAllProvincia", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<Provincia> getAllProvincia(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Provincia> provincia = new ArrayList<Provincia>();

		if (ses.isValid()) {
			return provincia;
		}

		return provincia = ProvinciaDB.getAllProvincia();

	}
	
	
	//Obtener Provincias por IdRegion
	@RequestMapping(value = "/work/getProvinciaByIdRegion/{idRegion}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<Provincia> getProvinciaByIdRegion(@PathVariable String idRegion ,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Provincia> provincia = new ArrayList<Provincia>();

		if (ses.isValid()) {
			return provincia;
		}

		return provincia = ProvinciaDB.getProvinciaByIdRegion(idRegion);

	}




}
