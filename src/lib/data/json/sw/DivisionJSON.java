package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.Division;
import lib.db.sw.DivisionDB;
import lib.security.session;

@Controller
public class DivisionJSON {

	//Obtener Todas las Divisiones
	@RequestMapping(value = "/work/getDivision/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<Division> getDivision(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<Division> DivisionList = new ArrayList<Division>();

		if (ses.isValid()) {
			return DivisionList;
		}

		DivisionList = DivisionDB.getDivision();
		return DivisionList;

	}



}
