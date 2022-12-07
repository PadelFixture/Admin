package lib.data.json;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import lib.classSW.CuentasSap;
import lib.security.session;

@Controller
public class sw_CuentasSap {
	
	@RequestMapping(value = "/work/BuscarCuentasSap/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<CuentasSap> getCuentasSAP(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CuentasSap> es = new ArrayList<CuentasSap>();
		
		if (ses.isValid()) {
			return es;
		}
		
		es = SWDB.sw_CuentasSapDB.getCuentasSAP();
		return es;	
	}
	
	//-------------------ACTUALIZAR NUMERO DE CUENTA ---------------------------------------------------------------
	@RequestMapping(value = "/work/UpdateNumeroCuenta", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateCuentaSap(@RequestBody CuentasSap row, HttpSession httpSession)
			throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return SWDB.sw_CuentasSapDB.updateCuentaSap(row);
	}

}
