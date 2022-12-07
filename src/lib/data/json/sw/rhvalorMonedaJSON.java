package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.rhvalorMoneda;
import lib.db.sw.rhvalorMonedaDB;
import lib.security.session;

@Controller
public class rhvalorMonedaJSON {
	@RequestMapping(value = "/work/valorUTM/getLastByPeriodo/{periodo}", method = {RequestMethod.GET})
  	public @ResponseBody ArrayList<rhvalorMoneda> getLastByPeriodo(@PathVariable("periodo") String periodo,HttpSession httpSession) throws Exception {
      	
  		session ses = new session(httpSession);
  		ArrayList<rhvalorMoneda> rhvalorMoneda = new ArrayList<rhvalorMoneda>();
  		rhvalorMoneda = rhvalorMonedaDB.getLastByPeriodo(periodo);
  		if (ses.isValid()) {
  			return rhvalorMoneda;
  		}		
  		return rhvalorMoneda;
  	}
}
