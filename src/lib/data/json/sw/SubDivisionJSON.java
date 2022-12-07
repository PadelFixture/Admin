package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.SubDivision;
import lib.db.sw.SubDivisionDB;
import lib.security.session;

@Controller
public class SubDivisionJSON {

	//Obtener Todas las SubDivisiones
	@RequestMapping(value = "/work/getSubDivision/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<SubDivision> getSubDivision(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<SubDivision> SubDivisionList = new ArrayList<SubDivision>();

		if (ses.isValid()) {
			return SubDivisionList;
		}

		SubDivisionList = SubDivisionDB.getSubDivision();
		return SubDivisionList;

	}




}
