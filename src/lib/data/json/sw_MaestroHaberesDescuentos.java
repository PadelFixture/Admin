package lib.data.json;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.haberesDescuentos;
import lib.security.session;

@Controller
public class sw_MaestroHaberesDescuentos {

//------------------------Lista codigo--------------------------------------------
	@RequestMapping(value = "/work/ListaCodigoPantallaMaestro/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<haberesDescuentos> getCodigoM(HttpSession httpSession) throws Exception {
		
		session ses = new session(httpSession);
		ArrayList<haberesDescuentos> es = new ArrayList<haberesDescuentos>();
		
		if (ses.isValid()) {
			return es;
		}
		
		es = SWDB.sw_MaestroDescuentosDB.getCodigoM();
		return es;

	}
}
