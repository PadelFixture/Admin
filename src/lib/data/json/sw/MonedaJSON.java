package lib.data.json.sw;


import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.classSW.Moneda;
import lib.db.sw.MonedaDB;
import lib.security.session;

@Controller
public class MonedaJSON {

	//Insert Moneda
	@RequestMapping(value = "/work/insertMoneda/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertMoneda(@RequestBody Moneda moneda, HttpSession httpSession) throws Exception{

		session ses = new session(httpSession);
		if(ses.isValid()){
			return false;
		}

		return MonedaDB.insertMoneda(moneda);
	}


	//Actualizar Moneda

	//Borrar sociedad por Id

	//Obtener Moneda por Id
	@RequestMapping(value = "/work/getMonedaById/{id}", method = {RequestMethod.GET})
	public @ResponseBody Moneda getMonedaById(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		Moneda moneda = new Moneda();

		if (ses.isValid()) {
			return moneda;
		}

		moneda = MonedaDB.getMonedaById(id);
		return moneda;

	}

	//Obtener Todas las Monedas
	@RequestMapping(value = "/work/getMonedas/", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ArrayList<Moneda> getMonedas(HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		ArrayList<Moneda> monedaList = new ArrayList<Moneda>();

		if (ses.isValid()) {
			return monedaList;
		}

		monedaList = MonedaDB.getMonedas();
		return monedaList;

	}



}

