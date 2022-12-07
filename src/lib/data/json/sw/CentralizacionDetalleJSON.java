package lib.data.json.sw;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.CentralizacionDetalle;
import lib.db.sw.CentralizacionDetalleDB;
import lib.security.session;

@Controller
public class CentralizacionDetalleJSON {
	
	//InsertOrUpdate
	@RequestMapping(value = "/work/CentralizacionDetalle/insertOrUpdateCentralizacionDetalle/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<CentralizacionDetalle> insertOrUpdateCentralizacionDetalle(@RequestBody ArrayList<CentralizacionDetalle> CentralizacionDetalle, HttpSession httpSession) throws Exception{

		session ses = new session(httpSession);
		if (ses.isValid()) {
			return null;
		}

		try {
			CentralizacionDetalle = CentralizacionDetalleDB.insertOrUpdateCentralizacionDetalle(CentralizacionDetalle);
		} catch (Exception e) {
			return null;
		}
	
		return CentralizacionDetalle; 
	} // fin insertar
	

	//get
	
	
	
	
	//delete
	
	
	
	
}
