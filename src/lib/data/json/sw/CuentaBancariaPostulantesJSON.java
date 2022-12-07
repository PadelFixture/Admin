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

import lib.classSW.CuentaBancariaPostulantes;
import lib.db.sw.CuentaBancariaPostulantesDB;

import lib.security.session;

@Controller
public class CuentaBancariaPostulantesJSON {

	//Insert Cuenta Bancaria
	@RequestMapping(value = "/work/insertCuentaBancariaPostulantes/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean insertCuentaBancariaPostulantes(@RequestBody CuentaBancariaPostulantes CuentaBancariaPostulantes, HttpSession httpSession) throws Exception{

		session ses = new session(httpSession);
		if(ses.isValid()){
			return false;
		}

		return CuentaBancariaPostulantesDB.insertCuentaBancariaPostulantes(CuentaBancariaPostulantes);
	}

	//Actualizar Cuenta Bancaria
	@RequestMapping(value = "/work/updateCuentaBancariaPostulantes/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateCuentaBancaria(@RequestBody CuentaBancariaPostulantes CuentaBancariaPostulantes,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return CuentaBancariaPostulantesDB.updateCuentaBancariaPostulantes(CuentaBancariaPostulantes);
	}


	//Actualizar o Insertar Cuenta Bancaria
	@RequestMapping(value = "/work/updateOrInsertCuentaBancariaPostulantes/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateOrInsertCuentaBancariaPostulantes(@RequestBody CuentaBancariaPostulantes CuentaBancariaPostulantes,HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);

		if (ses.isValid()) {
			return false;
		}
		return CuentaBancariaPostulantesDB.updateOrInsertCuentaBancariaPostulantes(CuentaBancariaPostulantes);
	}


	//get cuenta bancaria por id
	@RequestMapping(value = "/work/getCuentaBancariaPostulantes/{id}", method = {RequestMethod.GET})
	public @ResponseBody CuentaBancariaPostulantes getCuentaBancariaPostulantes(@PathVariable int id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		CuentaBancariaPostulantes CuentaBancariaPostulantes = new CuentaBancariaPostulantes();

		if (ses.isValid()) {
			return CuentaBancariaPostulantes;
		}

		CuentaBancariaPostulantes = CuentaBancariaPostulantesDB.getCuentaBancariaPostulantes(id);
		return CuentaBancariaPostulantes;

	}

	//get cuenta bancaria por id trabajador
	@RequestMapping(value = "/work/getCuentaBancariaByIdTrabajadorPostulantes/{id}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<CuentaBancariaPostulantes> getCuentaBancariaByIdTrabajadorPostulantes(@PathVariable String id, HttpSession httpSession) throws Exception {

		session ses = new session(httpSession);
		ArrayList<CuentaBancariaPostulantes> CuentaBancariaPostulantes = new ArrayList<CuentaBancariaPostulantes>();

		if (ses.isValid()) {
			return CuentaBancariaPostulantes;
		}

		CuentaBancariaPostulantes = CuentaBancariaPostulantesDB.getCuentaBancariaByTrabajadorPostulantes(id);
		return CuentaBancariaPostulantes;

	}

	
	//get cuenta bancaria por id trabajador
	@RequestMapping(value = "/work/getCuentaBancariaByCodTrabajadorPostulantes/{cod}", method = {RequestMethod.GET})
	public @ResponseBody ArrayList<CuentaBancariaPostulantes> getCuentaBancariaByCodTrabajadorPostulantes(@PathVariable String cod, HttpSession httpSession) throws Exception {

			session ses = new session(httpSession);
			ArrayList<CuentaBancariaPostulantes> CuentaBancariaPostulantes = new ArrayList<CuentaBancariaPostulantes>();

			if (ses.isValid()) {
				return CuentaBancariaPostulantes;
			}

			return CuentaBancariaPostulantesDB.getCuentaBancariaByCodTrabajadorPostulantes(cod);

		}


}
