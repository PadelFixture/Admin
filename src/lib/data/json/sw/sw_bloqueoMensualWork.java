package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import SWDB.sw_BloqueoMensualWorkDB;
import lib.security.session;
@Controller
public class sw_bloqueoMensualWork {
	
	@RequestMapping(value = "/work/CierrePeriodoWork/{empr},{huerto},{periodo},{workagro},{UD}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertarBloqueoMensualWork(@PathVariable int empr, @PathVariable String huerto, @PathVariable int periodo,@PathVariable int  workagro,@PathVariable int UD,
			HttpSession httpSession) throws Exception {
		
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		

			recc = sw_BloqueoMensualWorkDB.insertarBloqueoMensualWork(empr,huerto,periodo,workagro, UD);
		

		return recc;

	}
	
	@RequestMapping(value = "/work/CierrePeriodoWorkData/{empr},{huerto},{periodo},{workagro}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String SelectBloqueoMensualWorkData(@PathVariable int empr, @PathVariable String huerto, @PathVariable int periodo,@PathVariable int  workagro,
			HttpSession httpSession) throws Exception {
		
		
		session ses = new session(httpSession);
		String es = null;
	
		if (ses.isValid()) {
			return es;
		}
		
		String	recc = "";
		

			recc = sw_BloqueoMensualWorkDB.SelectBloqueoMensualWorkData(empr,huerto,periodo,workagro);
		

		return recc;

	}
	
	

}
