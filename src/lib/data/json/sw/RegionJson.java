package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.Region;
import lib.db.sw.RegionDB;
import lib.security.session;


@Controller
public class RegionJson {


	//Obtener Todas las Region 
	@RequestMapping(value = "/work/getAllRegion", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<Region> getAllRegion(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<Region> Region = new ArrayList<Region>();

		if (ses.isValid()) {
			return Region;
		}

		return Region = RegionDB.getAllRegion();

	}


}
